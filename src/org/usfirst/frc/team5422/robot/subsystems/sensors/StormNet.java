package org.usfirst.frc.team5422.robot.subsystems.sensors;

import org.usfirst.frc.team5422.robot.subsystems.RunnableNotifier;
import org.usfirst.frc.team5422.utils.NetworkConstants;
import org.usfirst.frc.team5422.utils.SteamworksConstants;

public class StormNet extends RunnableNotifier{
	USSensor usSensors;
	
	public StormNet(){
		super("StormNet", 0.001);
		usSensors = new USSensor(SteamworksConstants.STORMNET_ULTRASONIC_ARDUINO_ADDRESS, SteamworksConstants.NUMBER_OF_STORMNET_ULTRASONIC_SENSORS);
	}
	
	@Override
	public void run() {
		super.run();
		usSensors.pollDistance();
		for (int sensorNumber=0; sensorNumber < 4; sensorNumber++) {
			float distance = usSensors.getDistance(sensorNumber);
			networkPublish("ULTRASONIC_" + Integer.toString(sensorNumber + 1),  distance);
		}
	}
	
	@Override
	public void start() {
		super.start();
	}
	
	@Override
	public void stop() {
		// TODO Auto-generated method stub
		super.stop();
	}
}
