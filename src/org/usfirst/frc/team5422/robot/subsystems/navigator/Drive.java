package org.usfirst.frc.team5422.robot.subsystems.navigator;

import org.usfirst.frc.team5422.utils.RobotDriveConstants.RobotDriveProfile;
import org.usfirst.frc.team5422.utils.SteamworksConstants.RobotModes;

import com.ctre.CANTalon;

public abstract class Drive {
	public static CANTalon[] talons = new CANTalon[4];

	public Drive() {
		for(int i = 0; i < talons.length; i ++) {
			
			talons[i] = new CANTalon(i);
		}	
	}
	
	//implement this method in classes derived from this Drive class
	abstract public void move();

	abstract public void initializeDriveMode(RobotModes robotRunMode, RobotDriveProfile driveProfile);

	public void setDriveTalonsZeroVelocity() {
		for(int i = 0; i < talons.length; i ++) {
			talons[i].set(0);
		}
		
	}
	
	
}
