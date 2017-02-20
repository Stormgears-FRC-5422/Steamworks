package org.usfirst.frc.team5422.robot.subsystems.navigator;

import org.usfirst.frc.team5422.robot.subsystems.navigator.motionprofile.MotionManager;
import org.usfirst.frc.team5422.utils.HardwareConstants;
import org.usfirst.frc.team5422.utils.NetworkConstants;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class SplineFollowThread implements Runnable{
	
	private static NetworkTable networkTable;
	
	private static MotionManager motionManager = new MotionManager(Drive.talons);
	
	private static Spline spline;

	public static SplineFollowThread instance;
	
	private static Object[][] motionProfileBuffer;
	
	private static boolean _isFollowingSpline = false;
	
	private final double CLOSE_ENOUGH = 0.1; //meters
	
	private static double lastMotionProfilePushTime;
	
	public static void loadInitialSpline(Spline intialSpline){
		spline = intialSpline;
	}

	public static boolean isFollowingSpline(){
		return _isFollowingSpline; 
	}
	
	private SplineFollowThread(){
		networkTable = NetworkTable.getTable(NetworkConstants.GLOBAL_MAPPING);
		lastMotionProfilePushTime = Timer.getFPGATimestamp();
	}
	
	public void updateSpline(double x, double y, double vx, double vy){
		double error_x = spline.x(1, 1.0) - x;
		double error_y = spline.y(1, 1.0) - y;
		
		spline.removePose(0);
		
		
		//if the poses are close, then remove one pose and segment
		if( Math.sqrt(error_x*error_x + error_y*error_y) < CLOSE_ENOUGH ){
			
			spline.updatePose(0, new Pose(x, y, vx, vy));
			
		}else{//did not move past spline
			
			spline.addPose(0, new Pose(x, y, vx, vy));
		}
		
		if(spline.getNumSegments() < 1){
			
			_isFollowingSpline = false;
			
		}
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
			
			
			
			//update spline
			double x = networkTable.getNumber(NetworkConstants.GP_X, -1);
			double y = networkTable.getNumber(NetworkConstants.GP_Y, -1);
			double vx = networkTable.getNumber(NetworkConstants.GP_VX, -1);
			double vy = networkTable.getNumber(NetworkConstants.GP_VY, -1);
			
			updateSpline(x, y, vx, vy);
			
			
			calculateVelocityBuffer(150);//recalculate
			if(!_isFollowingSpline){
				motionManager.endProfile();
				motionManager.pushProfile(null, true, true);
				motionManager.shutDownProfiling();
				
			}else{
				if(Timer.getFPGATimestamp() - lastMotionProfilePushTime > 1.0){//every second, push a new profile
					
					motionManager.endProfile();
					motionManager.pushProfile(null, true, false);
					motionManager.startProfile();
					
					lastMotionProfilePushTime = Timer.getFPGATimestamp(); 
					
				}
			}
		}
	}
	
	private static void calculateVelocityBuffer(int points){
		
		if(!_isFollowingSpline){
			for(int i = 0; i < points; i++){
				motionProfileBuffer[0][i] = 0;
				motionProfileBuffer[1][i] = 0;
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
		for(int i=0; i < points; i++){
			
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
			
			motionProfileBuffer[0][i] = Math.pow(vx*vx + vy*vy, 0.5)/(2*Math.PI*HardwareConstants.WHEEL_RADIUS)*60;//rpm
			motionProfileBuffer[1][i] = Math.atan2(vy, vx) - theta;//radians
		}	
	}
	
	public static Runnable getInstance() {
		
		if(instance!=null){
			instance = new SplineFollowThread();
		}
		return instance;
	}
}
