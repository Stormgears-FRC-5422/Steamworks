package org.usfirst.frc.team5422.robot.subsystems.sensors;

import org.usfirst.frc.team5422.utils.SteamworksConstants;

import edu.wpi.first.wpilibj.I2C;

public class IRSensor extends StormgearsI2CSensor {
	private float[] sensorValues;
	private short[] gearState;
	private byte[] irDetails;
	private byte numLinePins = 5;
	
/*	EMPTY_BIN = 0, // Bin is empty 
	GEAR_LIFTING, // Gear is leaving, no longer present but has not broken through the beam sensor 
	FULL_BIN, // Gear is in the bin and is sensed by the line sensor 
	GEAR_EXITING, // Gear is breaking beam on the way out 
	UNKNOWN_STATE // Undefined states (Need more sensor data)  
*/
	public enum GearState {
		EMPTY_BIN, GEAR_LIFTING, FULL_BIN, GEAR_EXITING, UNKNOWN_STATE
	}
		
	IRSensor(int deviceAddress, int numSensors) {
		super(I2C.Port.kOnboard, deviceAddress);
		super.setSensorCount(numSensors);	
		sensorValues = new float[numSensors];		
		gearState = new short[1];
		
		// first byte here is beamBroke, rest are proximity detectors
		irDetails = new byte[1 + numLinePins];
	}

	public void pollDistance() {
		fetchFloats("I", "Infrared", sensorValues);	
	}
	
	public void pollGearState() {
		fetchShorts("G", "GearState", gearState);
	}
	
	public void pollSensorStatus() {
		fetchBytes("D", "Details", irDetails);
	}
	
	public float getDistance(int sensorNumber) {
		return sensorValues[sensorNumber];
	}

	public GearState getState() {
		switch (gearState[0]) {
		case 0:
			return GearState.EMPTY_BIN;
		case 1: 
			return GearState.GEAR_LIFTING;
		case 2:
			return GearState.FULL_BIN;
		case 3:
			return GearState.GEAR_EXITING;
		case 4:
		default:
			return GearState.UNKNOWN_STATE;
		}
	}
	
	// Negative means robot needs to move to the left
	public float getAlignmentOffset() {
		/*	1-?-0-?-1 --> 0” (i.e. center robot on lift peg)
			0-0-1-1-1 --> 1.75” left (offset robot to left of lift peg)
			0-1-1-1-0 --> 1.25” left or right (offset robot to left or right of lift peg)
			1-1-1-0-0 --> 1.75” right (offset robot to right of lift peg)
		 */
		// note that these sensors start at index 1, not 0 (0 is the beam...)
		boolean[] on = new boolean[numLinePins];
		for (int i = 0; i < numLinePins; i++) {
			on[i] = (irDetails[i+1] == 1);
		}
		
		if (on[0] && on[1] && on[2] && !on[3] && !on[4])
			return 1.75f; // robot need to move to the right
		
		if (!on[0] && on[1] && on[2] && on[3] && !on[4])
			return 1.25f; // pick one
		
		if (!on[0] && !on[1] && on[2] && on[3] && on[4])
			return -1.75f; // robot needs to move to the left

		return 0;
	}
	
	// probably don't want to call this method at all
	public boolean getBeamBroken() {
		return (irDetails[0] == 1);
	}
	
	// probably don't want to call this method at all
	public byte[] getAllDetails() {
		return irDetails;
	}
}
