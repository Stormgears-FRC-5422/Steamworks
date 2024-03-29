package org.usfirst.frc.team5422.robot.subsystems.sensors;

import org.usfirst.frc.team5422.utils.RegisteredNotifier;

public class SensorManager {
	
	private static RegisteredNotifier globalMappingNotifier;
	private static RegisteredNotifier visionNotifier;
	private static RegisteredNotifier stormNetNotifier;
	//private static Notifier shootingNotifier;
	
	private static GlobalMapping globalMapping;
	private static Vision vision;
	private static StormNet stormNet;
	//private static ShootingSensors shootingSensors;
	
	private static boolean _isPublishing = false;
	
	private static boolean _isInitiated = false;
	private static boolean _lightOn = false;

	public static double getTheta() { 
		return globalMapping.getTheta(); 
	}
	
	public static boolean isPublishing(){
		return _isPublishing;
	}
	
	public static boolean isInitiated(){
		
		return _isInitiated;
	}
	
	public static boolean isLightOn() {
		return _lightOn;
	}

	public static void initiateSensorSystems(){
		if (!_isInitiated) {
			globalMapping = new GlobalMapping();
			stormNet = new StormNet();
			vision = new Vision();
			//shootingSensors = new ShootingSensors();
			
			globalMappingNotifier = new RegisteredNotifier(globalMapping, "GlobalMapping");
			visionNotifier = new RegisteredNotifier(vision, "Vision");
			stormNetNotifier = new RegisteredNotifier(stormNet, "StormNet");
					
			_isInitiated = true;
		}		
	}
	
	public static void startPublishingToNetwork(){
		globalMappingNotifier.startPeriodic(0.1);
		stormNetNotifier.startPeriodic(0.1);
		visionNotifier.startPeriodic(0.1);
		//shootingSensors.start();
		
		_isPublishing = true;
	}
	
	public static void stopPublishingToNetwork(){
		globalMappingNotifier.stop();
		stormNetNotifier.stop();
		visionNotifier.stop();
//		shootingSensors.stop();
		
		_isPublishing = false;
	}
	
	public static void lightGearRing(boolean lightOn) {
		stormNet.usSensor.lightGearRing(lightOn);
	}
	
	public static void lightShooterRing(boolean lightOn) {
		stormNet.usSensor.lightShooterRing(lightOn);
	}
	
	public static void toggleLights() {
		stormNet.usSensor.toggleLightGearRing();
		stormNet.usSensor.toggleLightShooterRing();
	}
	
	public static Vision getVisionSubsystem() {
		return vision;
	}
	
	public static GlobalMapping getGlobalMappingSubsystem() {
		return globalMapping;
	}
}
