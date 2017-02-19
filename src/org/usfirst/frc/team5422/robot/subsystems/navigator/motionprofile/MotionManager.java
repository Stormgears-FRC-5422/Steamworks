package org.usfirst.frc.team5422.robot.subsystems.navigator.motionprofile;
import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;
import com.ctre.CANTalon.TrajectoryPoint;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MotionManager {
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
	public MotionManager(CANTalon [] talons) {
		controls = new MotionControl[talons.length];
		for(int i = 0; i < controls.length; i ++) controls[i] = new MotionControl(talons[i]);
	}
	
	private double [][] generateTable() {
		double [][] table = new double[4][1000];
		table[0] = getFuncs1(true);
		table[1] = getFuncs2(true);
		table[2] = getFuncs2(false);
		table[3] = getFuncs1(false);
		return table;
	}

	/*
	 * Precondition: path array a path in the form of v(velocity of center of robot), theta(direction of motion of center of robot)
	 * 
	 * immediate = whether profile should start immediately or wait for other guy to end
	 * done = whether profile is last part(if robot should stop after or not)
	 */
	public void pushProfile(double [][] pathArray, boolean immediate, boolean done) {
		SmartDashboard.putString("push profile started", "");
		//clear existing profiles
		if(immediate) for(int i = 0; i < controls.length; i ++) controls[i].clearMotionProfileTrajectories();
	   
		double [] positions = new double[4];
		for(int i = 0; i < pathArray.length; i ++) {
			int colIndex = (int)(pathArray[i][1] * 500/Math.PI);
			for(int j = 0; j < controls.length; j ++) {
				TrajectoryPoint pt = new TrajectoryPoint();
				pt.position = 0;
				pt.timeDurMs = 10;
				pt.velocityOnly = false;
				pt.zeroPos = false; //needed for successive profiles, only first pt should be set to true
				pt.velocity = pathArray[i][0] * table[j][colIndex];
				positions[j] += pt.velocity * 0.01/60; //NEW LINE(conversion from RPM to 10ms)
 				pt.position = positions[j]; //NEW LINE
				System.out.println("point vel: " + pt.velocity);
				controls[j].pushMotionProfileTrajectory(pt);
			}
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
		}
		
		SmartDashboard.putString("points pushed", "");
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
			if(neg) temp[i] = -Math.sqrt(2) * (Math.sin(2 * Math.PI  * i / 1000.0 +  Math.PI / 2) - Math.cos(2 * Math.PI * i / 1000.0 + Math.PI / 2));
			else temp[i] = Math.sqrt(2) * (Math.sin(2 * Math.PI * i / 1000.0 + Math.PI / 2) - Math.cos(2 * Math.PI * i / 1000.0 + Math.PI / 2));
		}
		return temp;
	}
	
	private double[] getFuncs2(boolean neg) {
		double[] temp = new double[1000];
		for(int i = 0; i < 1000; i ++) {
			if(neg) temp[i] = -Math.sqrt(2) * (Math.sin(2 * Math.PI * i / 1000.0 + Math.PI / 2) + Math.cos(2 * Math.PI * i / 1000.0 + Math.PI / 2));
			else temp[i] = Math.sqrt(2) * (Math.sin(2 * Math.PI * i / 1000.0 + Math.PI / 2) + Math.cos(2 * Math.PI * i / 1000.0 + Math.PI / 2));
		}
		return temp;
	}
	
}
