package org.usfirst.frc.team5422.robot.subsystems.navigator;

import org.usfirst.frc.team5422.robot.Robot;
import org.usfirst.frc.team5422.utils.RobotDriveConstants;
import org.usfirst.frc.team5422.utils.RobotDriveConstants.RobotDriveProfile;
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
		
		for(int i = 0; i < talons.length; i ++) {			
			talons[i].reverseOutput(true);
			talons[i].changeControlMode(TalonControlMode.Speed);
			//Velocity PID Values
			talons[i].setPID(RobotDriveConstants.REALBOT_VELOCITY_P, 
							 RobotDriveConstants.REALBOT_VELOCITY_I, 
							 RobotDriveConstants.REALBOT_VELOCITY_D);
			talons[i].setF(RobotDriveConstants.REALBOT_VELOCITY_F);
			talons[i].setIZone(RobotDriveConstants.REALBOT_VELOCITY_IZONE);	
			
			//Position PID Values
//			talons[i].setPID(SteamworksConstants.REALBOT_POSITION_P, 
//							 SteamworksConstants.REALBOT_POSITION_I, 
//							 SteamworksConstants.REALBOT_POSITION_D);
//			talons[i].setF(SteamworksConstants.REALBOT_POSITION_F);
//			talons[i].setIZone(SteamworksConstants.CLONEBOT_POSITION_IZONE);
		}
	}
	
	public void initializeDriveMode(RobotModes robotRunMode, RobotDriveProfile driveProfile) {
		if (robotRunMode == RobotModes.AUTONOMOUS) {
			for(int i = 0; i < talons.length; i ++) {
				SafeTalon talon = talons[i];
				talon.reverseOutput(true);
			//	talon.reverseSensor(true);
				talon.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
				talon.configEncoderCodesPerRev(2048);
				talon.changeControlMode(TalonControlMode.MotionProfile);
				
				//MOTION PROFILE PID for talons 0, 1, 3
				talon.setP(RobotDriveConstants.CLONEBOT_MOTIONPROFILE_P);
				talon.setI(RobotDriveConstants.CLONEBOT_MOTIONPROFILE_I); //0.0002
				talon.setD(RobotDriveConstants.CLONEBOT_MOTIONPROFILE_D); //10.24
				talon.setIZone(RobotDriveConstants.CLONEBOT_MOTIONPROFILE_IZONE); //1500
				talon.setF(RobotDriveConstants.CLONEBOT_MOTIONPROFILE_F);
			}
						
		} else { //RobotModes.TELEOP
			//System.out.println("Teleop Working");
			for(int i = 0; i < talons.length; i ++) {			
				SafeTalon talon = talons[i];
				talon.reverseOutput(true);
				//talon.reverseSensor(true);
				talon.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
				talon.configEncoderCodesPerRev(2048);
				talon.changeControlMode(TalonControlMode.Speed);
				//Velocity PID Values
				talon.setPID(RobotDriveConstants.CLONEBOT_VELOCITY_P, 
						RobotDriveConstants.CLONEBOT_VELOCITY_I, 
						RobotDriveConstants.CLONEBOT_VELOCITY_D);
				talon.setF(RobotDriveConstants.CLONEBOT_VELOCITY_F);
				talon.setIZone(RobotDriveConstants.CLONEBOT_VELOCITY_IZONE);
				//Position PID Values
//				talons[i].setPID(SteamworksConstants.CLONEBOT_POSITION_P, 
//								 SteamworksConstants.CLONEBOT_POSITION_I, 
//								 SteamworksConstants.CLONEBOT_POSITION_D);
//				talons[i].setF(SteamworksConstants.CLONEBOT_POSITION_F);
//				talons[i].setIZone(SteamworksConstants.CLONEBOT_POSITION_IZONE);
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
					joy.getY() + joy.getZ() * joy.getZ()), theta, joy.getZ());
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
		
			
		if(Math.abs(theta - 0) <= Math.PI / 6.0 || Math.abs(theta - 2.0 * Math.PI) <= Math.PI / 12.0) {
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
