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
		for (int sensorNumber=0; sensorNumber < SteamworksConstants.NUMBER_OF_STORMNET_ULTRASONIC_SENSORS; sensorNumber++) {
			networkPublish("ULTRASONIC_" + Integer.toString(sensorNumber + 1),  
							usSensor.getDistance(sensorNumber));
			SmartDashboard.putString("ULTRASONIC_" + Integer.toString(sensorNumber + 1),  
					Float.toString(usSensor.getDistance(sensorNumber)));
		}
		
		irSensor.pollGearState();
		SmartDashboard.putString("IR Gear State", irSensor.getState().toString());
		networkPublish("IR Gear State", irSensor.getState().toString());

		irSensor.pollSensorStatus();
		SmartDashboard.putString("IR Alignment Offset", Float.toString(irSensor.getAlignmentOffset()));	
		networkPublish("IR Alignment Offset", irSensor.getAlignmentOffset());
		SmartDashboard.putString("IR Sensor Details", Arrays.toString(irSensor.getAllDetails()));			
		networkPublish("IR Sensor Details", Arrays.toString(irSensor.getAllDetails()));
		
		// Turn ring light off
		//ID - NEOPIXEL_SEGMENTID
		//MODE - BEHAVIOR - ONLY ONE BEHAVIOR = 1 (All on or off) instead of flickering different LEDs in the neopixel
		//COLOR - 0-OFF, 1-RED, 2-GREEN, 3-BLUE,  ... (this depends on the mode, in our case Behavior =1)
		//BRIGHTNESS - 0(darkest) - 255 (brightest)
		lightSensor.pushCommand(1, 1, 0, 0);
		
		// Turn ring light on, green, brightness 128
		//lightSensor.pushCommand(1, 1, 2, 128);
		
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
