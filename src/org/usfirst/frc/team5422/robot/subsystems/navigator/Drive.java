package org.usfirst.frc.team5422.robot.subsystems.navigator;

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
	
}
