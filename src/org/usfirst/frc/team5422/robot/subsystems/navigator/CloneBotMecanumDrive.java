package org.usfirst.frc.team5422.robot.subsystems.navigator;

import org.usfirst.frc.team5422.robot.Robot;
import org.usfirst.frc.team5422.robot.subsystems.navigator.motionprofile.MotionManager;
import org.usfirst.frc.team5422.robot.subsystems.navigator.motionprofile.TrapezoidalProfile;
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

public class CloneBotMecanumDrive extends Drive {

	public CloneBotMecanumDrive() {		
		super();
	}
	
	public void initializeDriveMode(RobotModes robotRunMode, RobotDriveProfile driveProfile) {
		if (robotRunMode == RobotModes.AUTONOMOUS) {
			for(int i = 0; i < talons.length; i ++) {
				SafeTalon talon = talons[i];
				talon.reverseOutput(true); 
				talon.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
				talon.configEncoderCodesPerRev(2048);
				talon.changeControlMode(TalonControlMode.MotionProfile);
				
				//MOTION PROFILE PID for talons 0, 1, 3
				talon.setP(RobotTalonConstants.CLONEBOT_MOTIONPROFILE_P);
				talon.setI(RobotTalonConstants.CLONEBOT_MOTIONPROFILE_I); //0.0002
				talon.setD(RobotTalonConstants.CLONEBOT_MOTIONPROFILE_D); //10.24
				talon.setIZone(RobotTalonConstants.CLONEBOT_MOTIONPROFILE_IZONE); //1500
				talon.setF(RobotTalonConstants.CLONEBOT_MOTIONPROFILE_F);
			}
						
		} else { //RobotModes.TELEOP
			for(int i = 0; i < talons.length; i ++) {			
				talons[i].reverseOutput(true);
				talons[i].changeControlMode(TalonControlMode.Speed);
				//Velocity PID Values
				talons[i].setPID(RobotTalonConstants.CLONEBOT_VELOCITY_P, 
						RobotTalonConstants.CLONEBOT_VELOCITY_I, 
						RobotTalonConstants.CLONEBOT_VELOCITY_D);
				talons[i].setF(RobotTalonConstants.CLONEBOT_VELOCITY_F);
				talons[i].setIZone(RobotTalonConstants.CLONEBOT_VELOCITY_IZONE);	
				
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
		MotionManager m = new MotionManager(talons);
		SmartDashboard.putString("created motion manager", "");
		
		m.pushProfile(TrapezoidalProfile.getTrapezoidZero(5,300,Math.PI/3,0), true, true);
		SmartDashboard.putString("pushed profile", "");
		m.startProfile();		
	}
	
	
	public void move() {
		System.out.println("Mecanum Drive moving...");
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
			talons[i].set(vels[i]);
		}
	}
	
} 
