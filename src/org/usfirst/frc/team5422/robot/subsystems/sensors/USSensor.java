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
	
	// Distance in inches
	public int getDistance(int sensorNumber) {
		return (0xFF & sensorValues[sensorNumber]); // Java wants bytes to be signed.  We want unsigned value
	}
}
