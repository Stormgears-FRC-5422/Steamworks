package org.usfirst.frc.team5422.robot.subsystems.navigator;

import org.usfirst.frc.team5422.robot.Robot;
import org.usfirst.frc.team5422.utils.RobotTalonConstants;
import org.usfirst.frc.team5422.utils.RobotTalonConstants.RobotDriveProfile;
import org.usfirst.frc.team5422.utils.SteamworksConstants.RobotModes;

import org.usfirst.frc.team5422.utils.SafeTalon;
import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/*
 * @author Aditya Naik
 */

public class RealBotMecanumDrive extends Drive {

	public RealBotMecanumDrive() {		
		super();
		
		for (SafeTalon t : talons) {
			t.reverseOutput(true);
			t.changeControlMode(TalonControlMode.Speed);
			//Velocity PID Values
			t.setPID(RobotTalonConstants.REALBOT_VELOCITY_P, 
							 RobotTalonConstants.REALBOT_VELOCITY_I, 
							 RobotTalonConstants.REALBOT_VELOCITY_D);
			t.setF(RobotTalonConstants.REALBOT_VELOCITY_F);
			t.setIZone(RobotTalonConstants.REALBOT_VELOCITY_IZONE);	
    	}
	}
	
	public void initializeDriveMode(RobotModes robotRunMode, RobotDriveProfile driveProfile) {
		if (robotRunMode == RobotModes.AUTONOMOUS) {
			for (SafeTalon t : talons) {
				t.reverseOutput(true);
			//	t.reverseSensor(true);
				t.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
				t.configEncoderCodesPerRev(2048);
				t.changeControlMode(TalonControlMode.MotionProfile);
				
				//MOTION PROFILE PID for talons 0, 1, 3
				t.setP(RobotTalonConstants.REALBOT_MOTIONPROFILE_P);
				t.setI(RobotTalonConstants.REALBOT_MOTIONPROFILE_I); //0.0002
				t.setD(RobotTalonConstants.REALBOT_MOTIONPROFILE_D); //10.24
				t.setIZone(RobotTalonConstants.REALBOT_MOTIONPROFILE_IZONE); //1500
				t.setF(RobotTalonConstants.REALBOT_MOTIONPROFILE_F);
			}						
		} else { //RobotModes.TELEOP
			for (SafeTalon t : talons) {
				t.reverseOutput(true);
				//t.reverseSensor(true);
				t.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
				t.configEncoderCodesPerRev(2048);
				t.changeControlMode(TalonControlMode.Speed);
				//Velocity PID Values
				t.setPID(RobotTalonConstants.REALBOT_VELOCITY_P, 
						RobotTalonConstants.REALBOT_VELOCITY_I, 
						RobotTalonConstants.REALBOT_VELOCITY_D);
				t.setF(RobotTalonConstants.REALBOT_VELOCITY_F);
				t.setIZone(RobotTalonConstants.REALBOT_VELOCITY_IZONE);
			}
						
		}
	}

	public void autoMove() {
		
	}
	
	public void move() {
	//	System.out.println("Mecanum Drive moving...");
		Joystick joy = Robot.dsio.getJoystick();

		double theta = Math.atan2(joy.getX(), joy.getY());
		if(theta < 0) theta = 2 * Math.PI + theta;

		if(Math.abs(joy.getX()) > 0.1 || Math.abs(joy.getY()) > 0.1 || Math.abs(joy.getZ()) > 0.2)
			mecMove(6300 * Math.sqrt(joy.getX() * joy.getX() + joy.getY() * 
					joy.getY()), theta, joy.getZ());
		else {
			setDriveTalonsZeroVelocity();
		}
		
		
		

		
	}
	
	
	/*
	 * private void mecMove(double tgtVel, double theta, double changeVel, double currTheta) {
		
		double[] vels = new double[talons.length];
		
		
		
		if(currTheta < 0) currTheta += 360;
		theta -= currTheta / 180.0 * Math.PI;
		
		vels[0] = -(Math.sin(theta + Math.PI / 2.0) + Math.cos(theta + Math.PI / 2.0));
		vels[1] = (Math.sin(theta + Math.PI / 2.0) - Math.cos(theta + Math.PI / 2.0));
		vels[2] = -(Math.sin(theta + Math.PI / 2.0) - Math.cos(theta + Math.PI / 2.0));
		vels[3] = (Math.sin(theta + Math.PI / 2.0) + Math.cos(theta + Math.PI / 2.0));
		
		if(Math.abs(changeVel) > 0.5) {
			for(int i = 0; i < vels.length; i ++) {
				vels[i] -= changeVel;
			}
		}
		
		while(Math.abs(vels[0]) > 1.0 || Math.abs(vels[1]) > 1.0 || Math.abs(vels[2]) > 1.0 || Math.abs(vels[3]) > 1.0) {
			double max = Math.max(Math.max(Math.max(Math.abs(vels[0]), Math.abs(vels[1])), Math.abs(vels[2])), Math.abs(vels[3]));
			for(int i = 0; i < vels.length; i ++) {
				vels[i] /= max;
			}
		}

		if(Math.abs(joy.getX()) < 0.2 && Math.abs(joy.getY()) < 0.2) {
			for(int i = 0; i < vels.length; i ++) {
				vels[i] = -changeVel;
			}
		}
		
		for(int i = 0; i < vels.length; i ++) {
			vels[i] *= tgtVel;
		}
		
		SmartDashboard.putNumber("Set value for 0:", vels[0]);
		SmartDashboard.putNumber("Set value for 1:", vels[1]);
		SmartDashboard.putNumber("Set value for 2:", vels[2]);
		SmartDashboard.putNumber("Set value for 3:", vels[3]);
		
		for(int i = 0; i < talons.length; i ++) {
			talons[i].changeControlMode(TalonControlMode.Speed);
			talons[i].set(vels[i]);
		}
	}
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */

	public void mecMove(double tgtVel, double theta, double changeVel) {
		
		double[] vels = new double[talons.length];
		Joystick joy = Robot.dsio.getJoystick();
		
		// If +/- 15 degrees of a special angle, assume that angle was the intended direction
		// TODO - constrain theta to be from -pi to pi.		
		if(Math.abs(theta - 0) <= Math.PI / 12.0 || Math.abs(theta - 2.0 * Math.PI) <= Math.PI / 12.0) {
			theta = 0;
		}
		if(Math.abs(theta - Math.PI / 2.0) <= Math.PI / 12.0) {
			theta = Math.PI / 2.0;
		}
		if(Math.abs(theta - Math.PI) <= Math.PI / 12.0) {
			theta = Math.PI;
		}
		if(Math.abs(theta - 3.0 * Math.PI / 2.0) <= Math.PI / 12.0) {
			theta = 3.0 * Math.PI / 2.0;
		}
		
		vels[0] = -(Math.sin(theta + Math.PI / 2.0) + Math.cos(theta + Math.PI / 2.0));
		vels[1] = (Math.sin(theta + Math.PI / 2.0) - Math.cos(theta + Math.PI / 2.0));
		vels[2] = -(Math.sin(theta + Math.PI / 2.0) - Math.cos(theta + Math.PI / 2.0));
		vels[3] = (Math.sin(theta + Math.PI / 2.0) + Math.cos(theta + Math.PI / 2.0));
		
		if(Math.abs(changeVel) > 0.5) {
			for(int i = 0; i < vels.length; i ++) {
				vels[i] -= changeVel;
			}
		}
		
		while(Math.abs(vels[0]) > 1.0 || Math.abs(vels[1]) > 1.0 || Math.abs(vels[2]) > 1.0 || Math.abs(vels[3]) > 1.0) {
			double max = Math.max(Math.max(Math.max(Math.abs(vels[0]), Math.abs(vels[1])), Math.abs(vels[2])), Math.abs(vels[3]));
			for(int i = 0; i < vels.length; i ++) {
				vels[i] /= max;
			}
		}

		if(Math.abs(joy.getX()) < 0.2 && Math.abs(joy.getY()) < 0.2) {
			for(int i = 0; i < vels.length; i ++) {
				vels[i] = -changeVel;
			}
		}
		
		for(int i = 0; i < vels.length; i ++) {
			vels[i] *= tgtVel;
		}
		
		SmartDashboard.putNumber("Set value for 0:", vels[0]);
		SmartDashboard.putNumber("Set value for 1:", vels[1]);
		SmartDashboard.putNumber("Set value for 2:", vels[2]);
		SmartDashboard.putNumber("Set value for 3:", vels[3]);
		
		for(int i = 0; i < talons.length; i ++) {
			talons[i].changeControlMode(TalonControlMode.Speed);
			talons[i].set(vels[i]/8192.0 * 600);
		}
	}
	
} 
