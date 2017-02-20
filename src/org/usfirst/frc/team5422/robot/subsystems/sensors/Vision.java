package org.usfirst.frc.team5422.robot.subsystems.sensors;

import org.usfirst.frc.team5422.robot.subsystems.RunnableNotifier;
import org.usfirst.frc.team5422.utils.SteamworksConstants;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class Vision extends RunnableNotifier {

	private LightSensor lightSensor;
	public static NetworkTable visionTable = NetworkTable.getTable("GRIP/myContoursReport");
	
	public Vision() {
		super("Vision", 0.001);
		System.out.println("Vision system constructed");
		lightSensor = new LightSensor(SteamworksConstants.STORMNET_LIGHTS_ARDUINO_ADDRESS);
	}

	@Override
	public void run() {
		super.run();
				
	}
	
	@Override
	public void start() {
		super.start();
	}
	
	@Override
	public void stop() {
		super.stop();
	}
		
	public void turnOffLights() {
		//ID - NEOPIXEL_SEGMENTID
		//MODE - BEHAVIOR - ONLY ONE BEHAVIOR = 1 (All on or off) instead of flickering different LEDs in the neopixel
		//COLOR - 0-OFF, 1-RED, 2-GREEN, 3-BLUE,  ... (this depends on the mode, in our case Behavior =1)
		//BRIGHTNESS - 0(darkest) - 255 (brightest)

		// Turn ring light off
		lightSensor.pushCommand(1, 1, 0, 0);
	}
	
	public void turnOnLights() {
		// Turn ring light off
		//ID - NEOPIXEL_SEGMENTID
		//MODE - BEHAVIOR - ONLY ONE BEHAVIOR = 1 (All on or off) instead of flickering different LEDs in the neopixel
		//COLOR - 0-OFF, 1-RED, 2-GREEN, 3-BLUE,  ... (this depends on the mode, in our case Behavior =1)
		//BRIGHTNESS - 0(darkest) - 255 (brightest)

		// Turn ring light on, green, brightness 128
		lightSensor.pushCommand(1, 1, 2, 128);
	}
	
	public void getVisionCoordinatesFromNetworkTable() { 
		double [] defaultXArray = new double[0];
		double [] defaultYArray = new double[0];
		
		double [] centerX = visionTable.getNumberArray("centerX", defaultXArray);
		double [] centerY = visionTable.getNumberArray("centerY", defaultYArray);
		
	}


	public void alignToGear() {
		//target: (centerX[0] + centerX[1])/2 = pixel width/2

//		double pegHorizDisplacement = 160.0 - (centerX[0] + centerX[1]) / 2;

		//if displacement < 0, move left
		//if displacement > 0, move right
	}
}
