package org.usfirst.frc.team5422.robot.subsystems.sensors;

public class SensorManager {
	
	private static GlobalMapping globalMapping;
	public static Vision vision;
	private static ShootingSensors shootingSensors;
	private static StormNet stormNet;
	
	private static boolean _isPublishing = false;
	
	private static boolean _isInitiated = false;
	
	public static boolean isPublishing(){
		
		return _isPublishing;
	}
	
	public static boolean isInitiated(){
		
		return _isInitiated;
	}
	
	public static void initiateSensorSystems(){
		
		globalMapping = new GlobalMapping();
		stormNet = new StormNet();
		vision = new Vision();
//		shootingSensors = new ShootingSensors();
		
		_isInitiated = true;
	}
	
	public static void startPublishingToNetwork(){
		globalMapping.start();
		stormNet.start();
		vision.start();
//		shootingSensors.start();
		
		_isPublishing = true;
	}
	
	public static void stopPublishingToNetwork(){
		globalMapping.stop();
		stormNet.stop();
		vision.stop();
//		shootingSensors.stop();
		
		_isPublishing = false;
	}
}
