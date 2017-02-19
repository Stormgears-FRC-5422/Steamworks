package org.usfirst.frc.team5422.robot.subsystems.navigator;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;

import org.usfirst.frc.team5422.robot.Robot;
import org.usfirst.frc.team5422.utils.SteamworksConstants;

public class WPIMecanumDrive extends Drive {
	public RobotDrive robotDrive;
	
	public WPIMecanumDrive() {
		super();
		robotDrive = new RobotDrive(talons[SteamworksConstants.kFrontLeftChannel], 
									talons[SteamworksConstants.kRearLeftChannel], 
									talons[SteamworksConstants.kFrontRightChannel], 
									talons[SteamworksConstants.kRearRightChannel]);

		robotDrive.setInvertedMotor(MotorType.kFrontLeft, true); // invert the
		// left side motors
		robotDrive.setInvertedMotor(MotorType.kRearLeft, true); // you may need
	// 	to change or remove this to match your robot
		robotDrive.setExpiration(0.1);
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
}
