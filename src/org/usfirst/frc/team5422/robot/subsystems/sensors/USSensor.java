package org.usfirst.frc.team5422.robot.subsystems.sensors;

import edu.wpi.first.wpilibj.I2C;

public class USSensor extends StormgearsI2CSensor {
	private byte[] sensorValues;
	
	USSensor(int deviceAddress, int numSensors) {
		super(I2C.Port.kOnboard, deviceAddress);
		setSensorCount(numSensors);	
		sensorValues = new byte[numSensors];		
	}

	public void pollDistance() {
		fetchBytes("U", "Ultrasonic", sensorValues);	
	}
	
	public float getDistance(int sensorNumber) {
		return sensorValues[sensorNumber];
	}
	
}
