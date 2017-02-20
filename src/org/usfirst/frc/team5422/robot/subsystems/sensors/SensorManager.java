package org.usfirst.frc.team5422.robot.subsystems.sensors;

public class SensorManager {
	
	private static GlobalMapping globalMapping;
	private static Vision vision;
	private static ShootingSensors shootingSensors;
	private static StormNet stormNet;
	
	public static void initiateSensorSystems(){
		globalMapping = new GlobalMapping();
		stormNet = new StormNet();
//		vision = new Vision();
//		shootingSensors = new ShootingSensors();
	}
	
	public static void startPublishingToNetwork(){
		globalMapping.start();
		stormNet.start();
//		vision.start();
//		shootingSensors.start();
	}
	
	public static void stopPublishingToNetwork(){
		globalMapping.stop();
		stormNet.stop();
//		vision.stop();
//		shootingSensors.stop();
	}
}
