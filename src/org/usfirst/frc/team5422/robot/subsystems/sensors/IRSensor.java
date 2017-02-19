package org.usfirst.frc.team5422.robot.subsystems.sensors;

import org.usfirst.frc.team5422.utils.SteamworksConstants;
import org.usfirst.frc.team5422.utils.SteamworksConstants.gearStates;

import edu.wpi.first.wpilibj.I2C;

public class IRSensor extends StormgearsI2CSensor {
	private float[] sensorValues;

	IRSensor(int deviceAddress, int numSensors) {
		super(I2C.Port.kOnboard, deviceAddress);
		super.setSensorCount(numSensors);	
		sensorValues = new float[numSensors];		
	}

	public void pollDistance() {
		fetchFloats("I", "Infrared", sensorValues);	
	}

	public float getDistance(int sensorNumber) {
		return sensorValues[sensorNumber];
	}

	public gearStates getState(){
		return SteamworksConstants.gearStates.EMPTY;

	}
	
}
