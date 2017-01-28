package org.usfirst.frc.team5422.robot.subsystems.sensors;

import edu.wpi.first.wpilibj.I2C;

public class USSensor extends StormgearsI2CSensor {
	private float[] sensorValues;
	
	USSensor(int deviceAddress, int numSensors) {
		super(I2C.Port.kOnboard, deviceAddress);
		setSensorCount(numSensors);	
		sensorValues = new float[numSensors];		
	}

	public void pollDistance() {
		fetchFloats("U", "Ultrasonic", sensorValues);	
	}
	
	public float getDistance(int sensorNumber) {
		return sensorValues[sensorNumber];
	}
	
}
