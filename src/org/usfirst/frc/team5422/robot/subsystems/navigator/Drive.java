package org.usfirst.frc.team5422.robot.subsystems.navigator;

import org.usfirst.frc.team5422.utils.RobotDriveConstants;
import org.usfirst.frc.team5422.utils.RobotDriveConstants.RobotDriveProfile;
import org.usfirst.frc.team5422.utils.SteamworksConstants.RobotModes;

import org.usfirst.frc.team5422.utils.SafeTalon;
//import com.ctre.CANTalon;

public abstract class Drive {
	public static SafeTalon[] talons = new SafeTalon[RobotDriveConstants.NUM_DRIVE_TALONS];

	public Drive() {
		talons[0] = new SafeTalon(RobotDriveConstants.DRIVE_TALON_LEFT_FRONT);
		talons[1] = new SafeTalon(RobotDriveConstants.DRIVE_TALON_RIGHT_FRONT);
		talons[2] = new SafeTalon(RobotDriveConstants.DRIVE_TALON_LEFT_REAR);
		talons[3] = new SafeTalon(RobotDriveConstants.DRIVE_TALON_RIGHT_REAR);
	}
	
	//implement this method in classes derived from this Drive class
	abstract public void move();
	
	abstract public void autoMove();

	abstract public void initializeDriveMode(RobotModes robotRunMode, RobotDriveProfile driveProfile);

	public void setDriveTalonsZeroVelocity() {
		for (SafeTalon t : talons) {
			t.set(0);
    	}		
	}
	
	
}
