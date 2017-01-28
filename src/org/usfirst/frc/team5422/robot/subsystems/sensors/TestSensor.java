package org.usfirst.frc.team5422.robot.subsystems.sensors;

import edu.wpi.first.wpilibj.I2C;

// The build teams sample i2c sensor
public class TestSensor extends StormgearsI2CSensor {

	TestSensor(int deviceAddress) {
		super(I2C.Port.kOnboard, deviceAddress);
	}

	public void test() {
		log("Test returned " + (ping() ? "true" : "false") );
	}
}


