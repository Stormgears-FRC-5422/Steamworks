package org.usfirst.frc.team5422.robot.subsystems.sensors;

import edu.wpi.first.wpilibj.I2C;

public class USSensor extends StormgearsI2CSensor {
	private byte[] sensorValues;
	private byte[] lightValues;
	private boolean gearLightOn;
	private boolean shooterLightOn;
	
	USSensor(int deviceAddress, int numSensors) {
		super(I2C.Port.kOnboard, deviceAddress);
		setSensorCount(numSensors);	
		sensorValues = new byte[numSensors];	
		lightValues = new byte[2];  // short
	}

	public void pollDistance() {
		fetchBytes("U", "Ultrasonic", sensorValues);	
	}
	
	// Distance in inches
	public int getDistance(int sensorNumber) {
		return (0xFF & sensorValues[sensorNumber]); // Java wants bytes to be signed.  We want unsigned value
	}
	
	public boolean getShooterLightStatus() { return shooterLightOn; }

	public boolean getGearLightStatus() { return gearLightOn; }
	
	
	final static String MODE_GEAR_RING_ON = "1";
	final static String MODE_GEAR_RING_OFF = "2";
	final static String MODE_SHOOTER_RING_ON = "3";
	final static String MODE_SHOOTER_RING_OFF = "4";

	public void lightGearRing(boolean lightOn) {
		if(lightOn)
			fetchBytes(MODE_GEAR_RING_ON, "GearRingOn", lightValues);
		else
			fetchBytes(MODE_GEAR_RING_OFF, "GearRingOff", lightValues);
		
		// naively assume that the command worked
		gearLightOn = lightOn;
	}
	
	public void lightShooterRing(boolean lightOn) {
		if(lightOn)
			fetchBytes(MODE_SHOOTER_RING_ON, "ShooterRingOn", lightValues);
		else
			fetchBytes(MODE_SHOOTER_RING_OFF, "ShooterRingOff", lightValues);
		
		// naively assume that the command worked
		shooterLightOn = lightOn;
	}
	
	public void toggleLightShooterRing() {
		lightShooterRing(!shooterLightOn);
	}
	
	public void toggleLightGearRing() {
		lightGearRing(!gearLightOn);
	}
}
