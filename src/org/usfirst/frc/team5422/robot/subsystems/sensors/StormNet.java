package org.usfirst.frc.team5422.robot.subsystems.sensors;


import org.usfirst.frc.team5422.robot.subsystems.RunnableNotifier;
import org.usfirst.frc.team5422.utils.NetworkConstants;
import org.usfirst.frc.team5422.utils.SteamworksConstants;

import java.util.Arrays;

public class StormNet extends RunnableNotifier {
	USSensor usSensor;
	IRSensor irSensor;

	public StormNet() {
		super(NetworkConstants.STORM_NET, 0.001);
		usSensor = new USSensor(SteamworksConstants.STORMNET_ULTRASONIC_ARDUINO_ADDRESS,
				SteamworksConstants.NUMBER_OF_STORMNET_ULTRASONIC_SENSORS);
		usSensor.setDebug(true);
		usSensor.ping(); // say hello
		usSensor.setDebug(false);
		irSensor = new IRSensor(SteamworksConstants.STORMNET_IR_ARDUINO_ADDRESS,
				SteamworksConstants.NUMBER_OF_STORMNET_IRSENSOR);
		irSensor.setDebug(true);
		irSensor.ping(); // say hello
		irSensor.setDebug(false);
	}

	@Override
	public void run() {
		super.run();

		usSensor.pollDistance();
		for (int sensorNumber = 0; sensorNumber < SteamworksConstants.NUMBER_OF_STORMNET_ULTRASONIC_SENSORS; sensorNumber++) {
			networkPublish("ULTRASONIC_" + Integer.toString(sensorNumber + 1), usSensor.getDistance(sensorNumber));
		}

		irSensor.pollGearState();
		networkPublish("IR Gear State", irSensor.getState().toString());

		irSensor.pollSensorStatus();
		networkPublish("IR Alignment Offset", irSensor.getAlignmentOffset());
		networkPublish("IR Sensor Details", Arrays.toString(irSensor.getAllDetails()));
	}

}
