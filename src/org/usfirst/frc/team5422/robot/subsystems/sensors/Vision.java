package org.usfirst.frc.team5422.robot.subsystems.sensors;

import org.usfirst.frc.team5422.robot.Robot;
import org.usfirst.frc.team5422.robot.subsystems.RunnableSubsystem;
import org.usfirst.frc.team5422.robot.subsystems.navigator.Navigator;
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
      // Angular displacement
      double distLeft = NetworkTable.getTable(NetworkConstants.STORM_NET).getNumber(NetworkConstants.US_1_KEY, 6.0);
      double distRight = NetworkTable.getTable(NetworkConstants.STORM_NET).getNumber(NetworkConstants.US_2_KEY, 6.0);
      double diffAng = Math.asin((distLeft - distRight) / SteamworksConstants.ROBOT_ULTRASONIC_SEPARATION_IN);

//      System.out.println("DiffAngle:" + diffAng);
      // TODO: Turn in place by -diffAng

      Navigator.rotateRelative(diffAng); /*MOVING ROBOT**/

      SmartDashboard.putNumber("Angular Displacement to Gear Hook", diffAng * 180 / Math.PI);
      NetworkTable visionTable = NetworkTable.getTable("VisionTable");
      visionTable.putNumber("Angular Displacement", diffAng * 180 / Math.PI);

      // Distance
      
      distLeft = NetworkTable.getTable(NetworkConstants.STORM_NET).getNumber(NetworkConstants.US_1_KEY, 6.0);
      distRight = NetworkTable.getTable(NetworkConstants.STORM_NET).getNumber(NetworkConstants.US_2_KEY, 6.0);
      double distY = (distLeft + distRight) / 2;
       
      double pixelsPerIn = (getRectWidth(0) + getRectWidth(1)) / 4;
      double distX = (SteamworksConstants.FRAME_WIDTH / 2.0 - (getCenterX(0) + getCenterX(1)) / 2.0) / pixelsPerIn;
      SmartDashboard.putNumber("Distance from Gear-X: ", distX);
      SmartDashboard.putNumber("Distance from Gear-Y: ", distY);
      visionTable.putNumber("Distance from Gear-X", distX);
      visionTable.putNumber("Distance from Gear-Y", distY);
      Navigator.driveStraightRelativeInches(distX+9, distY*0.6);
      Robot.gearManipulatorSubsystem.setFlaps(SteamworksConstants.FLAPS_DISPENSE);
      Navigator.driveStraightRelativeInches(0, -12);
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