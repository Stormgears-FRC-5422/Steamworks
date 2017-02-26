package org.usfirst.frc.team5422.robot.subsystems.navigator;

import org.usfirst.frc.team5422.utils.SafeTalon;
import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;

import org.usfirst.frc.team5422.robot.Robot;
import org.usfirst.frc.team5422.utils.RobotDriveConstants;
import org.usfirst.frc.team5422.utils.RobotDriveConstants.RobotDriveProfile;
import org.usfirst.frc.team5422.utils.SteamworksConstants.RobotModes;

public class WPIMecanumDrive extends Drive {
	public RobotDrive robotDrive;
	
	public WPIMecanumDrive() {
		super();
		robotDrive = new RobotDrive(talons[RobotDriveConstants.kFrontLeftChannel], 
									talons[RobotDriveConstants.kRearLeftChannel], 
									talons[RobotDriveConstants.kFrontRightChannel], 
									talons[RobotDriveConstants.kRearRightChannel]);

		robotDrive.setInvertedMotor(MotorType.kFrontLeft, true); // invert the
		// left side motors
		robotDrive.setInvertedMotor(MotorType.kRearLeft, true); // you may need
	// 	to change or remove this to match your robot
		robotDrive.setExpiration(0.1);
	}

	public void autoMove() {
		
	}
	

	public void move() {
		System.out.println("Mecanum Drive moving...");
		Joystick joy = Robot.dsio.getJoystick();

		robotDrive.setSafetyEnabled(true);
			// Use the joystick X axis for lateral movement, Y axis for forward
			// movement, and Z axis for rotation.
			// This sample does not use field-oriented drive, so the gyro input
			// is set to zero.
		robotDrive.mecanumDrive_Cartesian(joy.getX(), joy.getY(), joy.getZ(), 0);
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
				talon.setP(RobotDriveConstants.CLONEBOT_MOTIONPROFILE_P);
				talon.setI(RobotDriveConstants.CLONEBOT_MOTIONPROFILE_I); //0.0002
				talon.setD(RobotDriveConstants.CLONEBOT_MOTIONPROFILE_D); //10.24
				talon.setIZone(RobotDriveConstants.CLONEBOT_MOTIONPROFILE_IZONE); //1500
				talon.setF(RobotDriveConstants.CLONEBOT_MOTIONPROFILE_F);
			}
						
		} else { //RobotModes.TELEOP
			robotDrive.setInvertedMotor(MotorType.kFrontLeft, true); // invert the
			// left side motors
			robotDrive.setInvertedMotor(MotorType.kRearLeft, true); // you may need
		// 	to change or remove this to match your robot
			robotDrive.setExpiration(0.1);

//			for(int i = 0; i < talons.length; i ++) {			
//				talons[i].reverseOutput(true);
//				talons[i].changeControlMode(TalonControlMode.Speed);
//				//Velocity PID Values
//				talons[i].setPID(RobotDriveConstants.CLONEBOT_VELOCITY_P, 
//						RobotDriveConstants.CLONEBOT_VELOCITY_I, 
//						RobotDriveConstants.CLONEBOT_VELOCITY_D);
//				talons[i].setF(RobotDriveConstants.CLONEBOT_VELOCITY_F);
//				talons[i].setIZone(RobotDriveConstants.CLONEBOT_VELOCITY_IZONE);	
//			}
						
		}
	}
	
}
