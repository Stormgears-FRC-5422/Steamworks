package org.usfirst.frc.team5422.robot.subsystems.sensors;

import org.usfirst.frc.team5422.robot.Robot;
import org.usfirst.frc.team5422.robot.subsystems.RunnableSubsystem;
import org.usfirst.frc.team5422.robot.subsystems.navigator.Navigator;
import org.usfirst.frc.team5422.robot.subsystems.navigator.motionprofile.TrapezoidalProfile;
import org.usfirst.frc.team5422.utils.NetworkConstants;
import org.usfirst.frc.team5422.utils.SteamworksConstants;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Vision extends RunnableSubsystem {
	public static NetworkTable visionTable = NetworkTable.getTable(NetworkConstants.GRIP_MY_CONTOURS_REPORT);
	public static NetworkTable shooterTable = NetworkTable.getTable(NetworkConstants.GRIP_MY_CONTOURS_REPORT);
	
	public Vision() {
		super(NetworkConstants.VISION);
		System.out.println("Vision system constructed");
	}

	@Override
	public void run() {
		super.run();	
	}
	
	public static void turnOffLights() {
		SensorManager.lightGearRing(false);
		SensorManager.lightShooterRing(false);
	}
	
	public static void turnOnLights() {
		SensorManager.lightGearRing(true);
		SensorManager.lightShooterRing(true);
	}
	
	public void getVisionCoordinatesFromNetworkTable() { 
		double [] defaultXArray = new double[0];
		double [] defaultYArray = new double[0];  
		
		double [] centerX = visionTable.getNumberArray(NetworkConstants.CENTER_X, defaultXArray);
		double [] centerY = visionTable.getNumberArray(NetworkConstants.CENTER_Y, defaultYArray);		
	}

	private double getCenterX(int index) {
	      double [] defaultArray = new double[0];
	      double [] visionArray = visionTable.getNumberArray(NetworkConstants.CENTER_X, defaultArray);

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
      double [] visionArray = visionTable.getNumberArray(NetworkConstants.WIDTH, defaultArray);

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
	  System.out.println("Vision...align to gear...");
      // Angular displacement from US Sensors
      double distLeft = NetworkTable.getTable(NetworkConstants.STORM_NET).getNumber(NetworkConstants.US_1_KEY, 6.0);
      double distRight = NetworkTable.getTable(NetworkConstants.STORM_NET).getNumber(NetworkConstants.US_2_KEY, 6.0);

      double diffAng = Math.asin((distLeft - distRight) / SteamworksConstants.ROBOT_ULTRASONIC_SEPARATION_IN);

      // Sanity checks
      if (distLeft > 120 || distRight > 120 || Math.abs(diffAng) > Math.PI / 6) {
    	  System.out.println("Bailing because turn values look wrong. distLeft : " + distLeft + " distRight : " + distRight + " diffAng :" + diffAng);
    	  return;
      }

      // TODO - DLM remove for real work
      //diffAng = Math.PI/12.0;  // 15 degrees      

      System.out.println("DiffAngle:" + diffAng + "left: " + distLeft + " right: " + distRight);
      /* ********** TURN ********** */
      if (diffAng != 0) {
    	  if (Math.abs(diffAng) > Math.PI / 12.0) {
    		  Navigator.getInstance().motionManager.pushTurn(diffAng, true, true); 
        	  Navigator.getInstance().motionManager.waitUntilProfileFinishes(100);
    	  }
    	  else { // small angles - turn twice!
    		  if (diffAng < 0) {
        		  Navigator.getInstance().motionManager.pushTurn(Math.PI / 9.0, true, false); 
        		  Navigator.getInstance().motionManager.pushTurn(diffAng - (Math.PI/9.0), false, true); 
    			  
    		  } else {
        		  Navigator.getInstance().motionManager.pushTurn(-Math.PI / 9.0, true, false); 
        		  Navigator.getInstance().motionManager.pushTurn(diffAng + (Math.PI/9.0), false, true); 
    		  }
        	  Navigator.getInstance().motionManager.waitUntilProfileFinishes(100);
    	  }
      }
      
      SmartDashboard.putNumber("Angular Displacement to Gear Hook", diffAng * 180 / Math.PI);
      NetworkTable visionTable = NetworkTable.getTable("VisionTable");
      visionTable.putNumber("Angular Displacement", diffAng * 180 / Math.PI);

      // Assume right orientation - get average distance
      distLeft = NetworkTable.getTable(NetworkConstants.STORM_NET).getNumber(NetworkConstants.US_1_KEY, 6.0);
      distRight = NetworkTable.getTable(NetworkConstants.STORM_NET).getNumber(NetworkConstants.US_2_KEY, 6.0);
      double distY = (distLeft + distRight) / 2;
      System.out.println("Vision distLeft: " + distLeft + " ,  distRight: " + distRight + ", distY: " + distY);
      
      //double pixelsPerIn = (getRectWidth(0) + getRectWidth(1)) / 4;
      //double distX = (SteamworksConstants.FRAME_WIDTH / 2.0 - (getCenterX(0) + getCenterX(1)) / 2.0) / pixelsPerIn;
      double distX = (SteamworksConstants.FRAME_WIDTH / 2.0 - (getCenterX(0) + getCenterX(1)) / 2.0);
      System.out.println("Vision CenterX0: " + getCenterX(0) + ", CenterX1: " + getCenterX(1) + ", distXPixel:" + distX);
      
      while (distX != 1000){
    	  getVisionCoordinatesFromNetworkTable();
      	  distX = (SteamworksConstants.FRAME_WIDTH / 2.0 - (getCenterX(0) + getCenterX(1)) / 2.0);
          System.out.println("Vision CenterX0: " + getCenterX(0) + ", CenterX1: " + getCenterX(1) + ", distXPixel:" + distX);
      }
          
      distX = 	distX * (67.0/320.0); // pixels to degrees
      System.out.println("Vision distXangle:" + distX);
      double offset = distY * Math.tan(Math.toRadians(distX));
      System.out.println("Vision Offset Pre Constant:" + offset);
      offset -= 8.8125; // camera to gear
      System.out.println("Vision Offset Post Constant:" + offset);
      System.out.println("Vision Info: cX0: " + getCenterX(0) + " , distX: " + distX);
      SmartDashboard.putNumber("Distance from Gear-X: ", distX);
      SmartDashboard.putNumber("Distance from Gear-Y: ", distY);
      visionTable.putNumber("Distance from Gear-X", distX);
      visionTable.putNumber("Distance from Gear-Y", distY);

//      // Sanity Check
//      // distY < 0 if we can't see anything
//      // distY > 120 if we are more than 10 feet away
//      // |distX - 9| > 24 we are more than 2 feet to the left or right
      if ( distY < 0 || distY > 120 || (distX - 9 > 1) || (distX - 9 < -1) ) {
    	  System.out.println("Bailing because US values look wrong. distX: " + distX + " distY: " + distY);
    	  return; // bail out
      }
      
      // TODO - DLM remove for real work
      // positive numbers mean move left 
//      distX = 18;
//      distY = 24;
      
      // Strafing left or right     
      /* ********** Strafe ********** */
//      double offset = distX - 8.8125;  // 8 13/16 from gear center to camera
      if (offset > 0) {
    	  Navigator.getInstance().motionManager.pushProfile(TrapezoidalProfile.getTrapezoidZero( offset/6.0/Math.PI , 70, 0, 0), true, false); /**move X**/
//          Navigator.getInstance().motionManager.waitUntilProfileFinishes(100);
      }
      else {
    	  // then go the other direction
    	  offset = -offset;
    	  Navigator.getInstance().motionManager.pushProfile(TrapezoidalProfile.getTrapezoidZero( offset/6.0/Math.PI , 70, Math.PI, 0), true, false); /**move X**/
//          Navigator.getInstance().motionManager.waitUntilProfileFinishes(100);
      }
      
      // Move forward to peg      
      /* ********** Forward ********** */
      Navigator.getInstance().motionManager.pushProfile(TrapezoidalProfile.getTrapezoidZero((distY - 4.0)/6.0/Math.PI *0.7, 70, 3*Math.PI/2.0, 0), false, true); /**move Y**/ 
      Navigator.getInstance().motionManager.waitUntilProfileFinishes(100);
//      Navigator.getInstance().motionManager.waitUntilProfileFinishes(100);
//      Robot.gearManipulatorSubsystem.setFlaps(SteamworksConstants.FLAPS_DISPENSE);
            

//      Navigator.driveStraightRelativeInches(distX+9, distY*0.6);
//      Navigator.driveStraightRelativeInches(0, -12);
//      Navigator.getInstance().motionManager.pushProfile(TrapezoidalProfile.getTrapezoidZero(3, 300, 3*Math.PI/2, 0), true, false);
   }
   
   public double returnGoalAngleDisplacement() {
	   
  		final double degreePerPixelHorizontal  = 67.5/320.0;
  		final double radiansPerPixelHorizontal = Math.toRadians(degreePerPixelHorizontal );
  	
  		//assume the deviation/correction between the robot drive moving to angle accuracy to camera angle accuracy
  		double centerX = getGoalCenterX(); //not a method right now
  		double errorTheta = radiansPerPixelHorizontal * (centerX- 160);
  		
  		return errorTheta;
  		//FINAL STEP, just turn errorTheta(no extra negation needed)
  	}
    
    public double getDistanceToGoal() {
   	 
		double centerY = getGoalCenterY();
		
		System.out.println("Center Y: " + centerY);
		//22.0/48.0
		double viewAngle = Math.toDegrees(Math.atan(.4848) * 2);//convert to degrees | .4848 --> retake measurements of px height at different distances
		
		double difAngle = centerY * viewAngle/240;			// 240 --> 600 (vertical px)
		double theta = viewAngle/2.0 + 30.0 - difAngle;			// 21.5 --> new camera angle
		
		double radialDistance = 84.5/Math.tan(Math.toRadians(theta));	// 65.5 --> 90" - camera height
		double power = .3/14 * radialDistance - 3.4;
		radialDistance = 0.97 * radialDistance + 11;
		//there may be some other modifications that must be made here --> such as changing units of centerY.
	//	double radialDistance = Math.atan(centerY);
	
		return radialDistance;
   	
    }
    
    public double getGoalCenterY() {
   	 double [] visionArray = shooterTable.getNumberArray(NetworkConstants.CENTER_Y, new double[1]);
   	 try {
   		 return (visionArray[0] + visionArray[1])/2.0;
   	 }
   	 catch(Exception e) {
   		 return -1;
   	 }
    }
    
    public double getGoalCenterX() {
   	 double [] visionArray = shooterTable.getNumberArray(NetworkConstants.CENTER_X, new double[1]);
   	 try {
   		 return (visionArray[0] + visionArray[1])/2.0;
   	 }
   	 catch(Exception e) {
   		 return -1;
   	 }
    }   
}