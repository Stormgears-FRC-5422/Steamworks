package org.usfirst.frc.team5422.robot.subsystems.navigator;


import org.usfirst.frc.team5422.robot.subsystems.navigator.motionprofile.MotionManager;
import org.usfirst.frc.team5422.utils.HardwareConstants;
import org.usfirst.frc.team5422.utils.NetworkConstants;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class SplineFollowThread implements Runnable{
	
	private static MotionManager motionManager;//is not only copy of motionManager
	
	private static NetworkTable networkTable;
	
	private static Spline spline;

	public static SplineFollowThread instance;
	
	private static double[][] motionProfileBuffer = new double[150][2];
	
	private static boolean _isFollowingSpline = false;
	
	private final double DISTANCE_TOLERANCE = 0.1; //meters
	
	private static double lastMotionProfilePushTime;
	
	private static boolean lastMotionProfilePushed;
	
	public static void loadInitialSpline(Spline intialSpline){
		spline = intialSpline;
		_isFollowingSpline = true;
		lastMotionProfilePushed = false;
	}

	public static boolean isFollowingSpline(){
		return _isFollowingSpline; 
	}
	
	private SplineFollowThread(){
		networkTable = NetworkTable.getTable(NetworkConstants.GLOBAL_MAPPING);
		//motionManager = new MotionManager(Drive.talons);//TODO:: maybe should only have one instance (in navigator)
		lastMotionProfilePushTime = Timer.getFPGATimestamp();
	}
	
	public static void setMotionManager(MotionManager manager){
		motionManager = manager;
	}
	
	public void updateSpline(Pose argPose){//arguments are robot's pose
		
		double x = argPose.x;
		double y = argPose.y;
		double vx = argPose.v_x;
		double vy = argPose.v_y;
		
		// update spline so it points the robot toward the next waypoint by turning starting velocity
		
		double error_x = spline.x(1, 1.0) - x;
		double error_y = spline.y(1, 1.0) - y;
		
		spline.printSplinePos("");
		
		double theta = Math.atan2(error_x, error_y);
		
		double spline_v_mag = Math.sqrt(vx * vx + vy * vy);
		
		double corrected_spline_vx = spline_v_mag*Math.cos(theta);
		double corrected_spline_vy = spline_v_mag*Math.sin(theta);
		
		
		
		
		double corrected_weight = 0.4;//this weight controls how much the robot is not under pure spline control
		
		//weighted av between robot v and spline desired v
		double weighted_v_x = corrected_spline_vx * corrected_weight + vx*(1-corrected_weight);
		double weighted_v_y = corrected_spline_vy * corrected_weight + vy*(1-corrected_weight);
		
		//if close to next waypoint, then remove the waypoint to continue to next waypoint
		
		double err = Math.sqrt(error_x * error_x + error_y * error_y);
		
		if(err < DISTANCE_TOLERANCE){
			
			System.out.println("POSITION ERROR: " + err);
			
			spline.removePose(1);
			spline.printSplinePos("after removing spline 1");
			
			if(spline.getNumSegments() < 1){
				
				System.out.println("NUM SEGMENTS LESS THAN 1");
				
				_isFollowingSpline = false;
				
				return;
			}
		}
		
		//update the current pose with robot's manipulated
		spline.updatePose(0, new Pose(x, y, weighted_v_x, weighted_v_y));
		spline.printSplinePos("after 0 update");
		
		System.out.println("number of segments: " + spline.getNumSegments());
		//change direction of waypoint's velocity, so robot can follow easier
		double waypt_vy = spline.vy(1, 1.0);
		double waypt_vx = spline.vx(1, 1.0);
		
		double v_mag = Math.sqrt(waypt_vx*waypt_vx + waypt_vy*waypt_vy);
		
		spline.updatePose(1, new Pose(spline.x(1, 1.0), spline.y(1, 1.0), v_mag*Math.sin(theta), v_mag*Math.sin(theta)) );
		spline.printSplinePos("after 1 update");
	}
	
	@Override
	public void run() {
		
		if(spline==null){
			try {
				throw new Exception("spline has not been loaded to SplineFollowThread!");
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}else{
				
			if(lastMotionProfilePushed){
				return;
			}
			
			//update spline
			double x = networkTable.getNumber(NetworkConstants.GP_X, -1);
			double y = networkTable.getNumber(NetworkConstants.GP_Y, -1);
			double vx = networkTable.getNumber(NetworkConstants.GP_VX, -1);
			double vy = networkTable.getNumber(NetworkConstants.GP_VY, -1);
			
			System.out.println("Update spline will be called");
			
			System.out.println("X: " + x);
			System.out.println("Y: " + y);
			System.out.println("VX: " + vx);
			System.out.println("VY: " + vy);
			
			updateSpline(new Pose(x, y, vx, vy));
			
			
			
			calculateVelocityBuffer(150);//recalculate
			
			if(!_isFollowingSpline){
				
				motionManager.pushProfile(motionProfileBuffer, true, true);
				lastMotionProfilePushed = true;
				
				
			}else{
				if(Timer.getFPGATimestamp() - lastMotionProfilePushTime > 1.0){//every fraction of a second, push a new profile
					
					
					motionManager.pushProfile(motionProfileBuffer, true, false);
					
					
					lastMotionProfilePushTime = Timer.getFPGATimestamp(); 
					
				}
			}
		}
	}
	
	private static void calculateVelocityBuffer(int points){
		System.out.println("Calculating Buffer");
		if(!_isFollowingSpline){
			for(int i = 0; i < points; i++){
				motionProfileBuffer[i][0] = 0;
				motionProfileBuffer[i][1] = 0;
			}
			
			return;
		}
		
		
		double dt_wanted = 0.01;
		
		double du = 0.0001;
		double u0 = 0;
		double u1 = u0+du;
		
		double dt_guess = 0; 
		//find next u, assuming constant acceleration
		//TODO:: write safety for end of segment extrapolations
		double theta = networkTable.getNumber(NetworkConstants.GP_THETA, -1);
		for(int i = 0; i < points; i++){
			
			while(dt_guess < dt_wanted){
				
				double vy0 = spline.vy(1, u0);
				double ay0 = spline.ay(1, u0);
				
				double vy1 = spline.vy(1, u1);
				double ay1 = spline.ay(1, u1);
				
				//estimate constant acceleration
				double ay = (ay0 + ay1)/2.0;
				
				dt_guess = (vy1 -vy0)/ay;
				
				u1+=du;
			}
			
			double vx = spline.vx(1, u1);
			double vy = spline.vy(1, u1);
			
			motionProfileBuffer[i][0] = Math.pow(vx*vx + vy*vy, 0.5)/(2*Math.PI*HardwareConstants.WHEEL_RADIUS)*60;//rpm
			motionProfileBuffer[i][1] = Math.atan2(vy, vx) - theta;//radians
		}	
	}
	
	public static Runnable getInstance() {
		
		if(instance==null){
			instance = new SplineFollowThread();
		}
		return instance;
	}
}
