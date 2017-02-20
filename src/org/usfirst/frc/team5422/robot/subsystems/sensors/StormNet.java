package org.usfirst.frc.team5422.robot.subsystems.sensors;
import java.util.Arrays;

import org.usfirst.frc.team5422.robot.subsystems.RunnableNotifier;
import org.usfirst.frc.team5422.utils.SteamworksConstants;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class StormNet extends RunnableNotifier{
	USSensor usSensor;
	IRSensor irSensor;
	LightSensor lightSensor;
	
	public StormNet(){
		super("StormNet", 0.001);
		usSensor = new USSensor(SteamworksConstants.STORMNET_ULTRASONIC_ARDUINO_ADDRESS, SteamworksConstants.NUMBER_OF_STORMNET_ULTRASONIC_SENSORS);
		irSensor =  new IRSensor(SteamworksConstants.STORMNET_IR_ARDUINO_ADDRESS, SteamworksConstants.NUMBER_OF_STORMNET_IRSENSOR);
		lightSensor = new LightSensor(SteamworksConstants.STORMNET_LIGHTS_ARDUINO_ADDRESS);
	}
	
	@Override
	public void run() {
		super.run();
		usSensor.pollDistance();
		for (int sensorNumber=0; sensorNumber < 4; sensorNumber++) {
			networkPublish("ULTRASONIC_" + Integer.toString(sensorNumber + 1),  
							usSensor.getDistance(sensorNumber));
		}
		
		irSensor.pollGearState();
		SmartDashboard.putString("IR Gear State", irSensor.getState().toString());

		irSensor.pollSensorStatus();
		SmartDashboard.putString("IR Alignment Offset", Float.toString(irSensor.getAlignmentOffset()));	
		SmartDashboard.putString("IR Sensor Details", Arrays.toString(irSensor.getAllDetails()));			
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
