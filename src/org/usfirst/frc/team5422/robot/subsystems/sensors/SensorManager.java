package org.usfirst.frc.team5422.robot.subsystems.sensors;

import edu.wpi.first.wpilibj.Notifier;

public class SensorManager {
	
	private static Notifier globalMappingNotifier;
	private static Notifier visionNotifier;
	//private static Notifier shootingNotifier;
	private static Notifier stormNetNotifier;
	
	private static GlobalMapping globalMapping;
	private static Vision vision;
	//private static ShootingSensors shootingSensors;
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
		
		globalMappingNotifier = new Notifier(globalMapping);
		visionNotifier = new Notifier(vision);
		stormNetNotifier = new Notifier(stormNet);
		
		
		_isInitiated = true;
	}
	
	public static void startPublishingToNetwork(){
		globalMappingNotifier.startPeriodic(0.01);
		stormNetNotifier.startPeriodic(0.01);
		visionNotifier.startPeriodic(0.01);
//		shootingSensors.start();
		
		_isPublishing = true;
	}
	
	public static void stopPublishingToNetwork(){
		globalMappingNotifier.stop();
		stormNetNotifier.stop();
		visionNotifier.stop();
//		shootingSensors.stop();
		
		System.out.println("Stopping threads...");
		
		_isPublishing = false;
	}
}
