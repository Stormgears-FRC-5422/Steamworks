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
	void pushCommand(byte id, byte mode, byte arg1, byte arg2) {
		// L[id, mode, arg1, arg2]
		lightCommand[1] = id;
		lightCommand[2] = mode;
		lightCommand[3] = arg1;
		lightCommand[4] = arg2;
		// receivebuffer ends up holding a basic counter.  not really used.
		fetchCommand(lightCommand, "PushCommand", receiveBuffer);
	}

}
