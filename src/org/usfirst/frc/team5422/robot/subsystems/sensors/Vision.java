package org.usfirst.frc.team5422.robot.subsystems.sensors;

import org.usfirst.frc.team5422.robot.subsystems.RunnableNotifier;
import org.usfirst.frc.team5422.utils.SteamworksConstants;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class Vision extends RunnableNotifier{
	private DigitalOutput lights;
	public static NetworkTable visionTable = NetworkTable.getTable("GRIP/myContoursReport");
	
	public Vision() {
		super("Vision", 0.001);
		System.out.println("Vision system constructed");
		lights = new DigitalOutput(SteamworksConstants.RINGLIGHT_PORT);
		lights.set(false);
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
		lights.set(false);
	}
	
	public void turnOnLights() {
		lights.set(true);
	}
	
	public void getVisionCoordinatesFromNetworkTable() { 
		double [] defaultXArray = new double[0];
		double [] defaultYArray = new double[0];
		
//		double [] centerX = visionTable.getNumberArray("centerX", defaultXArray);
//		double [] centerY = visionTable.getNumberArray("centerY", defaultYArray);
		
	}
}
