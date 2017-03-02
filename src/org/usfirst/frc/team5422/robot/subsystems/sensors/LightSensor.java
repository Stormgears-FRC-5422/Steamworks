package org.usfirst.frc.team5422.robot.subsystems.sensors;

import edu.wpi.first.wpilibj.I2C;

public class LightSensor extends StormgearsI2CSensor {
	private byte[] lightCommand;
	private byte[] receiveBuffer;

	LightSensor(int deviceAddress) {
		super(I2C.Port.kOnboard, deviceAddress);
		lightCommand = new byte[5];
		lightCommand[0] = 'L';
		receiveBuffer = new byte[1];
	}
	
	// generic command 
	// e.g. turn ring light off
	//ID - NEOPIXEL_SEGMENTID
	//MODE - BEHAVIOR - ONLY ONE BEHAVIOR = 1 (All on or off) instead of flickering different LEDs in the neopixel
	//COLOR - 0-OFF, 1-RED, 2-GREEN, 3-BLUE,  ... (this depends on the mode, in our case Behavior =1)
	//BRIGHTNESS - 0(darkest) - 255 (brightest)

	void pushCommand(int id, int mode, int arg1, int arg2) {
		// L[id, mode, arg1, arg2]
		lightCommand[1] = (byte)id;
		lightCommand[2] = (byte)mode;
		lightCommand[3] = (byte)arg1;
		lightCommand[4] = (byte)arg2;
		// receivebuffer ends up holding a basic counter.  not really used.
		fetchCommand(lightCommand, "PushCommand", receiveBuffer);
	}

}
