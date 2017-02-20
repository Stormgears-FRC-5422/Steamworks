package org.usfirst.frc.team5422.robot.subsystems.sensors;

import java.util.Set;

import org.usfirst.frc.team5422.robot.subsystems.RunnableNotifier;
import org.usfirst.frc.team5422.utils.SteamworksConstants;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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

	private double getCenterX(int index) {
	      double [] defaultArray = new double[0];
	      double [] visionArray = visionTable.getNumberArray("centerX", defaultArray);

	      if(visionArray.length > 0)
	        try {
	        	return visionArray[index];
	        }
	        catch(Exception e) {
	        	return -1;
	        }
	      else
	         return -1;
	}

   private double getRectWidth(int index) {
      double [] defaultArray = new double[1];
      double [] visionArray = visionTable.getNumberArray("width", defaultArray);

      if(visionArray.length > 0) {
         try {
        	 return visionArray[index];
         }
         catch(Exception e) {
        	 return -1;
         }
      } else {

         return -1;
      }
   }

   public void alignToGear() {
      // Angular displacement
      double distLeft = NetworkTable.getTable("StormNet").getNumber("ULTRASONIC_1", 6.0);
      double distRight = NetworkTable.getTable("StormNet").getNumber("ULTRASONIC_2", 6.0);
      double diffAng = Math.asin((distLeft - distRight) / SteamworksConstants.ROBOT_ULTRASONIC_SEPARATION_IN);

//      System.out.println("DiffAngle:" + diffAng);
      // TODO: Turn in place by -diffAng
      SmartDashboard.putNumber("Angular Displacement to Gear Hook", diffAng * 180 / Math.PI);

      // Distance
      double distIn = (distLeft + distRight) / 2;
      // TODO: Calculate this after the robot has straightened itself out
      SmartDashboard.putNumber("Distance to Gear Hook", distIn);
//      System.out.println("Distance in Inches from Gear:" + distIn);

      // Horizontal Displacement
//	    double pixelsPerIn = (Math.pow(83.328 * Math.E, -0.018 * distIn)) / 5;
      double pixelsPerIn = (getRectWidth(0) + getRectWidth(1)) / 4;
//      System.out.println("Vision CenterX1: " + getCenterX(0) + " Vision CenterX2: " + getCenterX(1) );
      double diffHorizIn = (SteamworksConstants.FRAME_WIDTH / 2.0 - (getCenterX(0) + getCenterX(1)) / 2.0) / pixelsPerIn;
      SmartDashboard.putNumber("Horizontal Displacement to Gear Hook", diffHorizIn);
//      System.out.println("Horizontal Displacement :" + diffHorizIn);

      //if displacement < 0, move left
      //if displacement > 0, move right
   }
}
