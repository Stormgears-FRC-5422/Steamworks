package org.usfirst.frc.team5422.robot.subsystems.navigator;

import org.stormgears.StormUtils.SafeTalon;
import org.usfirst.frc.team5422.utils.RobotDriveConstants;
import org.usfirst.frc.team5422.utils.RobotDriveConstants.RobotDriveProfile;
import org.usfirst.frc.team5422.utils.SteamworksConstants.RobotModes;
//import com.ctre.CANTalon;

public abstract class Drive {
	public static SafeTalon[] talons = new SafeTalon[RobotDriveConstants.NUM_DRIVE_TALONS];

	public Drive() {
		for (int i = 0; i < talons.length; i++) {
			talons[i] = new SafeTalon(i);
		}
	}

	//implement this method in classes derived from this Drive class
	abstract public void move();

	abstract public void autoMove();

	abstract public void initializeDriveMode(RobotModes robotRunMode, RobotDriveProfile driveProfile);

	public void setDriveTalonsZeroVelocity() {
		for (SafeTalon talon : talons) {
			talon.set(0);
		}

	}


}
