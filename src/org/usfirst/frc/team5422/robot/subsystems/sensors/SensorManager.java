package org.usfirst.frc.team5422.robot.subsystems.sensors;

public class SensorManager {
	
	private static GlobalMapping globalMapping;
	private static Vision vision;
	private static ShootingSensors shootingSensors;
	private static StormNet stormNet;
	
	public static void initiateSensorSystems(){
		globalMapping = new GlobalMapping();
//		vision = new Vision();
//		shootingSensors = new ShootingSensors();
//		stormNet = new StormNet();
	}
	
	public static void startPublishingToNetwork(){
		globalMapping.start();
//		vision.start();
//		shootingSensors.start();
//		stormNet.start();
	}
	
	public static void stopPublishingToNetwork(){
		globalMapping.stop();
		vision.stop();
		shootingSensors.stop();
		stormNet.stop();
	}
}
