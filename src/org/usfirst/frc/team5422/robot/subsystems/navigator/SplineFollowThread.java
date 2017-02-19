package org.usfirst.frc.team5422.robot.subsystems.navigator;

import org.usfirst.frc.team5422.robot.subsystems.navigator.motionprofile.MotionManager;
import org.usfirst.frc.team5422.utils.HardwareConstants;
import org.usfirst.frc.team5422.utils.NetworkConstants;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class SplineFollowThread implements Runnable{
	
	private static NetworkTable networkTable;
	
	private static MotionManager motionManager = new MotionManager(null);

	private static boolean _isFollowingSpline = false;
	
	private static Spline spline;
	
	public static SplineFollowThread instance;
	
	private static double lastMotionProfilePushTime = Timer.getFPGATimestamp();

	private static Object[][] motionProfileBuffer;
	
	public static void loadInitialSpline(Spline intialSpline){
		spline = intialSpline;
	}

	public static boolean isFollowingSpline(){
		return _isFollowingSpline; 
	}
	
	private SplineFollowThread(){
		networkTable = NetworkTable.getTable(NetworkConstants.GLOBAL_MAPPING);
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
			calculateVelocityBuffer(150);//recalculate
			if(Timer.getFPGATimestamp() - lastMotionProfilePushTime > 1.0){
				
			}
		}
	}
	private static void calculateVelocityBuffer(int points){
		double dt_wanted = 0.01;
		
		double du = 0.0001;
		double u0 = 0;
		double u1 = u0+du;
		
		double dt_guess = 0; 
		//find next u, assuming constant acceleration
		//TODO:: write safety for end of segment extrapolations
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
			motionProfileBuffer[1][i] = Math.atan2(vy, vx);//radians
		}	
	}

	public static Runnable getInstance() {
		// TODO Auto-generated method stub
		if(instance!=null){
			instance = new SplineFollowThread();
		}
		return instance;
	}

}
