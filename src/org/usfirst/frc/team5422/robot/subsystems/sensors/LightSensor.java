package org.usfirst.frc.team5422.robot.subsystems.sensors;

import edu.wpi.first.wpilibj.I2C;

public class LightSensor extends StormgearsI2CSensor {
	
	LightSensor(int deviceAddress) {
		super(I2C.Port.kOnboard, deviceAddress);
	}

}
