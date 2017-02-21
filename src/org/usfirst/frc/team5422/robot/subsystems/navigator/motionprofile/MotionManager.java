package org.usfirst.frc.team5422.robot.subsystems.navigator.motionprofile;
import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;
import com.ctre.CANTalon.TrajectoryPoint;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.util.ArrayList;


public class MotionManager {
	
	private ArrayList<double[][]> paths = new ArrayList<double[][]>();
	private ArrayList<TurnDetails> turns = new ArrayList<TurnDetails>();
//	private double[][] pathArray;
	private boolean immediate, done, go = false, interrupt = false;
	private int batchSize = 256;
	private int currIndex = 0;
	
	class PeriodicRunnable implements java.lang.Runnable {
		public void run() {
			synchronized (this) {
//				System.out.println(currIndex);
				if(immediate) {
					currIndex = 0;
					for(int i = 0; i < controls.length; i ++) controls[i].clearMotionProfileTrajectories();
					//remove all other profiles from the list
					while(paths.size() > 1) {
						paths.remove(0);
						turns.remove(0);
					}
					immediate = false;
				}
				if(go) {
					if(turns.get(0) == null) pushLinear();
					else pushTurn();
					if(currIndex >= paths.get(0).length) {
						currIndex = 0;
						paths.remove(0);
						turns.remove(0);
						if(paths.size() == 0) go = false;
					}
				}
			}
		}	
	}
	
	class TurnDetails {
		double theta;
		boolean direction;
	}
	
	private Notifier notifier = new Notifier(new PeriodicRunnable());
	private MotionControl [] controls;
	private final double [][] table = generateTable();
	
	/*
	 * Preconditions: All talons must be set to the following
	 * 
	 * reverseOutput(true)
	 * feedbackDevice = quadEncoder
	 * configEncoderCodesPerRev(2048)
	 * P = 0.08
	 * I = 0.0002
	 * D = 10.24
	 * IZone = 1500
	 * F = 0.16
	 * 
	 */
	
	public double getRobotRPM() {
		int[] vels = getEncVels();
		double root = Math.sqrt(2);
		double x = 0;
		double y = 0;
		for(int i = 0; i < vels.length; i ++) {
			if(i == 0 || i == 3) x -= vels[i] / root;
			else x += vels[i] / root;
			y += vels[i] / root;
		}
		x /= vels.length;
		y /= vels.length;
		//double[] solution = {Math.sqrt(x * x + y * y) * 10.0 * 60.0 / 8192.0, Math.atan(y / x)};
		return Math.sqrt(x * x + y * y) * 10.0 * 60.0 / 8192.0;
	}
	
	private int[] getEncVels() {
		int[] vels = new int[controls.length];
		for(int i = 0; i < controls.length; i ++) {
			vels[i] = controls[i].getEncVel();
		}
		return vels;
	}
	
	public MotionManager(CANTalon [] talons) {
		controls = new MotionControl[talons.length];
		for(int i = 0; i < controls.length; i ++) controls[i] = new MotionControl(talons[i]);
		notifier.startPeriodic(0.005);
	}
	
	public synchronized void pushProfile(double [][] pathArray, boolean immediate, boolean done) {
		this.done = done;
		this.immediate = immediate;
		interrupt = immediate;
		paths.add(pathArray);
		turns.add(null);
		go = true;
	}
	public synchronized void pushTurn(double theta, boolean immediate, boolean done) {
		this.immediate =  immediate;
		interrupt = immediate;
		this.done = done;
		TurnDetails d = new TurnDetails();
		d.theta = theta;
		turns.add(d);
		paths.add(getTurnProfile(d));
		go = true;
	}
	
	private double [][] generateTable() {
		double [][] table = new double[4][1000];
		table[0] = getFuncs1(true);
		table[1] = getFuncs2(true); //FOR TURN: false
		table[2] = getFuncs2(false); //FOR TURN: true
		table[3] = getFuncs1(false);
		return table;
	}

	/*
	 * Precondition: path array a path in the form of v(velocity of center of robot), theta(direction of motion of center of robot)
	 * 
	 * immediate = whether profile should start immediately or wait for other guy to end
	 * done = whether profile is last part(if robot should stop after or not)
	 */
	
	public double[][] getTurnProfile(TurnDetails d) {
		double robotRadius = 15.00; //TODO: make constant, in inches (14.25 x 13.25)
		double wheelRadius = 3; //TODO: make constant, in inches
		double maxVel = 240; //RPM
		d.theta %= (2 * Math.PI);
		double tTheta = d.theta - Math.PI;
		if(tTheta > 0) {d.theta = Math.PI - tTheta; d.direction = true; }
		d.direction = false;
		double dist = robotRadius * d.theta/(2.0 * Math.PI * wheelRadius);
		return TrapezoidalProfile.getTrapezoidZero(dist, maxVel, d.theta, getRobotRPM());
	}

	public void pushTurn() {
		SmartDashboard.putString("push profile started", "");
		//clear existing profiles
		double [] positions = new double[4];
		TrajectoryPoint pt = new TrajectoryPoint();
		double[][] pathArray = paths.get(0);
		boolean direc = turns.get(0).direction;
		for(int i = currIndex; i < currIndex + batchSize; i ++) {
			if(i >= pathArray.length) break;
			if(interrupt) {
				interrupt = false;
				return;
			}
			int colIndex = (int)(pathArray[i][1] * 500/Math.PI);
			for(int j = 0; j < controls.length; j ++) {
				pt.position = 0;
				pt.timeDurMs = 10;
				pt.velocityOnly = false;
				pt.zeroPos = (i == currIndex); //needed for successive profiles, only first pt should be set to true
				pt.velocity = pathArray[i][0] * table[j][colIndex]; //TODO: change signs as appropriate for turning
				if((j == 0 || j == 2) && direc) pt.velocity *= -1;
				else if((j == 1 || j == 3) && !direc) pt.velocity *= -1;
				positions[j] += pt.velocity * 0.01/60; //NEW LINE(conversion from RPM to 10ms)
 				pt.position = positions[j]; //NEW LINE
				pt.isLastPoint = (i + 1 == pathArray.length && done);
				//System.out.println("point vel: " + pt.velocity);
				controls[j].pushMotionProfileTrajectory(pt);
			}
		}
		startProfile();
		currIndex += batchSize;
	}
	
	public void pushLinear() {
		double[][] pathArray = paths.get(0);
		SmartDashboard.putString("push profile started", "");
		TrajectoryPoint pt = new TrajectoryPoint();
		double [] positions = new double[4];
		for(int i = currIndex; i < currIndex + batchSize; i ++) {
			if(i >= pathArray.length) break;
			if(interrupt) {
				interrupt = false;
				return;
			}
			int colIndex = (int)(pathArray[i][1] * 500/Math.PI);
			for(int j = 0; j < controls.length; j ++) {
				pt.position = 0;
				pt.timeDurMs = 10;
				pt.velocityOnly = false;
				pt.zeroPos = (i == currIndex); //needed for successive profiles, only first pt should be set to true
				pt.velocity = pathArray[i][0] * table[j][colIndex];
				positions[j] += pt.velocity * 0.01/60; //NEW LINE(conversion from RPM to 10ms)
 				pt.position = positions[j]; //NEW LINE
				pt.isLastPoint = (i + 1 == pathArray.length && done);
 				//System.out.println("point vel: " + pt.velocity);
				controls[j].pushMotionProfileTrajectory(pt);
			}
		}
		startProfile();
		currIndex += batchSize;
	}
	
	public void startProfile() {
		for(int i = 0; i < controls.length; i ++) controls[i].enable();
	}
	
	public void endProfile() {
		for(int i = 0; i < controls.length; i ++) controls[i].disable();
	}
	
	public void shutDownProfiling() {
		for(int i = 0; i < controls.length; i ++) {
			controls[i].clearMotionProfileTrajectories();
			controls[i].clearUnderrun();
			controls[i].changeControlMode(TalonControlMode.Speed); //may need to be vbus
		}
	}

	//helper methods for generating table
	private double[] getFuncs1(boolean neg) {
		double[] temp =  new double[1000];
		for(int i = 0; i < 1000; i ++) {
			if(neg) temp[i] = -Math.sqrt(2) * (Math.sin(2 * Math.PI  * i / 1000.0 + Math.PI / 2.0) - Math.cos(2 * Math.PI * i / 1000.0 + Math.PI / 2.0));
			else temp[i] = Math.sqrt(2) * (Math.sin(2 * Math.PI * i / 1000.0 + Math.PI / 2.0) - Math.cos(2 * Math.PI * i / 1000.0 + Math.PI / 2.0));
		}
		return temp;
	}
	
	private double[] getFuncs2(boolean neg) {
		double[] temp = new double[1000];
		for(int i = 0; i < 1000; i ++) {
			if(neg) temp[i] = -Math.sqrt(2) * (Math.sin(2 * Math.PI * i / 1000.0 + Math.PI / 2.0) + Math.cos(2 * Math.PI * i / 1000.0 + Math.PI / 2.0));
			else temp[i] = Math.sqrt(2) * (Math.sin(2 * Math.PI * i / 1000.0 + Math.PI / 2.0) + Math.cos(2 * Math.PI * i / 1000.0 + Math.PI / 2.0));
		}
		return temp;
	}
	
}
