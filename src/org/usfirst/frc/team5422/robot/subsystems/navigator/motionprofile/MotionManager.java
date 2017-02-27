package org.usfirst.frc.team5422.robot.subsystems.navigator.motionprofile;

import org.stormgears.StormUtils.SafeTalon;
//import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;
import com.ctre.CANTalon.TrajectoryPoint;

import org.usfirst.frc.team5422.utils.RegisteredNotifier;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.util.ArrayList;
import java.util.List;

public class MotionManager {
	private List<double[][]> paths = new ArrayList<double[][]>();
	private List<ProfileDetails> turns = new ArrayList<ProfileDetails>();
	private boolean immediate, go = false, interrupt = false;
	private int batchSize = 256;
	private int currIndex = 0;
	private MotionControl [] controls;
	
	private RegisteredNotifier notifier = new RegisteredNotifier(new PeriodicRunnable());
	private final double [][] table = generateTable();
	
	class PeriodicRunnable implements java.lang.Runnable {
		
		// The job of this thread is to push data into the top api buffer.
		// it pushes up to 'batchSize' points at a time (but never points 
		// from more than one path in a single pass)
		public void run() {
			// synchronize to avoid MT conflicts with the input of profiles
			// called from synchronized methods - which effectively sync(this)
			synchronized(this) {
				// Are we done?
				if(paths.isEmpty()) {
//					for(int i = 0; i < controls.length; i ++) {
//						controls[i].stopControlThread();
//					}
					notifier.stop();
					go = false;
					System.out.println("Stopped pushing points because paths are done.");
					// anything else? disable talons?
					return;
				}

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
					if(turns.get(0).turn == false) pushLinear();
					else pushTurn();
					// If we have pushed the entire path, remove it and let the next path run on the next time through
					// this could lead to a short cycle, but that is probably OK since we push points more quickly 
					// than they can run anyway.
					if(currIndex >= paths.get(0).length) {
						currIndex = 0;
						paths.remove(0);
						turns.remove(0);
					}	
				}
			}
		}	

	}
	
	// information that needs to be stored per profile path
	class ProfileDetails {
		boolean turn;
		double theta;
		boolean direction;
		boolean done;
	}
	
	public MotionManager(SafeTalon [] talons) {
		controls = new MotionControl[talons.length];
		for(int i = 0; i < controls.length; i ++) 
			controls[i] = new MotionControl(talons[i]);
	}	
	
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
	
	public synchronized void pushProfile(double [][] pathArray, boolean immediate, boolean done) {
		ProfileDetails d = new ProfileDetails();
		d.done = done;
		d.turn = false;
		turns.add(d);

		this.immediate = immediate;
		interrupt = immediate;
		paths.add(pathArray);
		go = true;
		notifier.startPeriodic(0.005);
		for(int i = 0; i < controls.length; i ++) {
			controls[i].startControlThread();
			System.out.println("Control started again in pushProfile");
		}
	}
	
	public synchronized void pushTurn(double theta, boolean immediate, boolean done) {
		ProfileDetails d = new ProfileDetails();
		d.done = done;
		d.theta = theta;
		d.turn = true;
		
		turns.add(d);

		this.immediate =  immediate;
		interrupt = immediate;
		paths.add(getTurnProfile(d));
		go = true;
		notifier.startPeriodic(0.005);
		for(int i = 0; i < controls.length; i ++) {
			controls[i].startControlThread();
			System.out.println("Control started again profile");
		}
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
	
	public double[][] getTurnProfile(ProfileDetails d) {
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
		boolean done = turns.get(0).done;
		
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
		SmartDashboard.putString("pushLinear started", "");
		TrajectoryPoint pt = new TrajectoryPoint();
		double [] positions = new double[4];
		boolean done = turns.get(0).done;

		for(int i = currIndex; i < currIndex + batchSize; i ++) {
			if(i >= pathArray.length) break;
		
			if(interrupt) {
				interrupt = false;
				return;
			}
			
			int colIndex = (int)(((pathArray[i][1] + 2*Math.PI) % (2*Math.PI)) * 500/Math.PI);
			
			
			//System.out.println("i is " + i + ", pathArray[i][1] is " + pathArray[i][1] + ", colIndex is " + colIndex);

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
		for (int i = 0; i < controls.length; i ++) 
			controls[i].enable();
	}
	
	public void endProfile() {
		for(int i = 0; i < controls.length; i ++) 
			controls[i].disable();
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
