package org.usfirst.frc.team5422.robot.subsystems.navigator.motionprofile;
import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;
import com.ctre.CANTalon.TrajectoryPoint;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MotionManager {
	
	class PeriodicRunnable implements java.lang.Runnable {
		public void run() {  
			
		}	
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
	
	public void pushTurn(double theta, boolean immediate, boolean done) {
		double robotRadius = 15.00; //TODO: make constant, in inches (14.25 x 13.25)
		double wheelRadius = 3; //TODO: make constant, in inches
		double maxVel = 240; //RPM
		boolean direc;
		theta %= (2 * Math.PI);
		double tTheta = theta - Math.PI;
		if(tTheta > 0) {theta = Math.PI - tTheta; direc = true; }
		else direc = false;
		double dist = robotRadius * theta/(2.0 * Math.PI * wheelRadius);
		SmartDashboard.putNumber("theta: ", theta);
		SmartDashboard.putBoolean("direc", direc);
		pushTurn(TrapezoidalProfile.getTrapezoidZero(dist, maxVel, theta, 0), false, true, direc);
	}

	public void pushTurn(double [][] pathArray, boolean immediate, boolean done, boolean direc) {
		SmartDashboard.putString("push profile started", "");
		//clear existing profiles
		if(immediate) for(int i = 0; i < controls.length; i ++) controls[i].clearMotionProfileTrajectories();
	   
		double [] positions = new double[4];
		TrajectoryPoint pt = new TrajectoryPoint();
		for(int i = 0; i < pathArray.length; i ++) {
			int colIndex = (int)(pathArray[i][1] * 500/Math.PI);
			for(int j = 0; j < controls.length; j ++) {
				pt.position = 0;
				pt.timeDurMs = 10;
				pt.velocityOnly = false;
				pt.zeroPos = false; //needed for successive profiles, only first pt should be set to true
				pt.velocity = pathArray[i][0] * table[j][colIndex]; //TODO: change signs as appropriate for turning
				if((j == 0 || j == 2) && direc) pt.velocity *= -1;
				else if((j == 1 || j == 3) && !direc) pt.velocity *= -1;
				positions[j] += pt.velocity * 0.01/60; //NEW LINE(conversion from RPM to 10ms)
 				pt.position = positions[j]; //NEW LINE
				//System.out.println("point vel: " + pt.velocity);
				controls[j].pushMotionProfileTrajectory(pt);
			}
//			if(i == 128) startProfile();
		}
		if(done) {
			pt.position = 0;
			pt.timeDurMs = 10;
			pt.velocityOnly = true;
			pt.zeroPos = false; //needed for successive profiles
			pt.velocity = 0;
			pt.isLastPoint = true;
			for(int j = 0; j < controls.length; j ++) {
				pt.position = positions[j];
				controls[j].pushMotionProfileTrajectory(pt);
			}
		}
	}
	
	public void pushProfile(double [][] pathArray, boolean immediate, boolean done) {
		SmartDashboard.putString("push profile started", "");
		//clear existing profiles
		if(immediate) {
			//endProfile();
			for(int i = 0; i < controls.length; i ++) controls[i].clearMotionProfileTrajectories();
		}
	   
		double [] positions = new double[4];
		for(int i = 0; i < pathArray.length; i ++) {
			int colIndex = (int)(pathArray[i][1] * 500/Math.PI);
			for(int j = 0; j < controls.length; j ++) {
				TrajectoryPoint pt = new TrajectoryPoint();
				pt.position = 0;
				pt.timeDurMs = 10;
				pt.velocityOnly = false;
				pt.zeroPos = (i == 0); //needed for successive profiles, only first pt should be set to true
				pt.velocity = pathArray[i][0] * table[j][colIndex];
				positions[j] += pt.velocity * 0.01/60; //NEW LINE(conversion from RPM to 10ms)
 				pt.position = positions[j]; //NEW LINE
				pt.isLastPoint = (i == pathArray.length - 1 && done);
 				//System.out.println("point vel: " + pt.velocity);
				controls[j].pushMotionProfileTrajectory(pt);
			}
//			if(i == 128) startProfile();
		}
		
		//push profile point with V = 0 so that when talon holds it stays as 0
		if(done) {
			TrajectoryPoint pt = new TrajectoryPoint();
			pt.position = 0;
			pt.timeDurMs = 10;
			pt.velocityOnly = true;
			pt.zeroPos = false; //needed for successive profiles
			pt.velocity = 0;
			pt.isLastPoint = true;
			for(int j = 0; j < controls.length; j ++) {
				pt.position = positions[j];
				controls[j].pushMotionProfileTrajectory(pt);
			}
		}
		
		//SmartDashboard.putString("points pushed", "");
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
