package org.usfirst.frc.team5422.robot.subsystems.navigator;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.Timer;

public class DummyRobot {
	
	static boolean isFinished = false;
	
	static Pose currentPose;
	
	static Spline path;
	
	static double a_x = 0.001;
	static double a_y = 0.001;
	
	public static void test(){
		
		currentPose = new Pose(0.001,0.001,0.001,0.001);
		
		ArrayList<Pose> test_poses = new ArrayList<Pose>();
		
    	test_poses.add(new Pose(0,0,0,0));
    	test_poses.add(new Pose(10,10,0,10));
    	test_poses.add(new Pose(-10,20,0,10));
    	test_poses.add(new Pose(0,30,0,0));
    	
    	path = new Spline(test_poses);
    	
    	while(!isFinished){
    		
    		//TODO:: make dummy robot follow spline
    		
    		path.updatePose(0, currentPose);
    		Timer.delay(100);
    	}
    	
    }
}
