package org.usfirst.frc.team5422.robot.subsystems.navigator;

import org.usfirst.frc.team5422.utils.NetworkConstants;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class SplineFollowThread implements Runnable{
	
	private static NetworkTable networkTable;

	private static boolean _isFollowingSpline = false;
	
	private static Spline spline;
	
	public static SplineFollowThread instance;
	
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
		// TODO Auto-generated method stub
		double x = networkTable.getNumber(NetworkConstants.GP_X, -1);
		double y = networkTable.getNumber(NetworkConstants.GP_Y, -1);
		double vx = networkTable.getNumber(NetworkConstants.GP_VX, -1);
		double vy = networkTable.getNumber(NetworkConstants.GP_VY, -1);
		spline.updatePose(0, new Pose(x, y, vx, vy));
		
		double desiredX = spline.x(1, 0.05);
		double desiredY = spline.y(1, 0.05);
		double desiredVX = spline.vx(1, 0.05);
		double desiredVY = spline.vy(1, 0.05);
		
		//TODO::
	}

	public static Runnable getInstance() {
		// TODO Auto-generated method stub
		if(instance!=null){
			instance = new SplineFollowThread();
		}
		return instance;
	}

}
