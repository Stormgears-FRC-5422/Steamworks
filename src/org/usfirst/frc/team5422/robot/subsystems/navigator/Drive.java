package org.usfirst.frc.team5422.robot.subsystems.navigator;

import org.usfirst.frc.team5422.utils.RobotTalonConstants;
import org.usfirst.frc.team5422.utils.RobotTalonConstants.RobotDriveProfile;
import org.usfirst.frc.team5422.utils.SteamworksConstants.RobotModes;

import org.usfirst.frc.team5422.utils.SafeTalon;
//import com.ctre.CANTalon;

public abstract class Drive {
	public static SafeTalon[] talons = new SafeTalon[RobotTalonConstants.NUM_DRIVE_TALONS];

	public Drive() {
		for(int i = 0; i < talons.length; i ++) {
			talons[i] = new SafeTalon(RobotTalonConstants.DRIVE_IDS[i]);
		}	
	}
	
	//implement this method in classes derived from this Drive class
	abstract public void move();
	
	abstract public void autoMove();

	abstract public void initializeDriveMode(RobotModes robotRunMode, RobotDriveProfile driveProfile);

	public void setDriveTalonsZeroVelocity() {
		for(int i = 0; i < talons.length; i ++) {
			talons[i].set(0);
		}
		
	}
	
	
}
