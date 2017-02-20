package org.usfirst.frc.team5422.robot.subsystems.navigator;

import org.usfirst.frc.team5422.robot.subsystems.navigator.motionprofile.MotionManager;
import org.usfirst.frc.team5422.robot.subsystems.navigator.motionprofile.TrapezoidalProfile;
import org.usfirst.frc.team5422.utils.NetworkConstants;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class Navigator extends Subsystem{
	
	private NetworkTable networkTable = NetworkTable.getTable(NetworkConstants.GLOBAL_MAPPING);
	
	private Notifier splineFollowThreadNotifier;
	
	private static Navigator instance;
	
	private static Drive mecanumDrive;
	
	private static boolean _isRotating;
	
	private static boolean _isMovingStraight;
	
	private static MotionManager motionManager = new MotionManager(Drive.talons);
	
	public static boolean isRotating(){
		
		return _isRotating;
	}
	
	public static boolean isMovingStraight(){
		
		return _isMovingStraight;
	}
	
	public static boolean isMoving(){
		return _isMovingStraight || _isRotating || SplineFollowThread.isFollowingSpline();
	}

	public Navigator() {
		//using Stormgears CloneBot Mecanum Drive
        mecanumDrive = new CloneBotMecanumDrive();
		
		//using Stormgears CloneBot Mecanum Drive
        //mecanumDrive = new RealBotMecanumDrive();

        //to test using WPI Mecanum Drive
        //mecanumDrive = new WPIMecanumDrive();        
	}
	
	public static Drive getMecanumDrive(){
		
		return mecanumDrive;
	}

	public static Navigator getInstance(){
		
		if(instance==null){
			instance = new Navigator();
		}
		return instance;
	}
	
	public synchronized void driveSpline(Spline spline){
		
		try{
			if(isMoving()){
				throw new Exception("cannot call two maneuvers at once!");
			}else{
				SplineFollowThread.loadInitialSpline(spline);
				splineFollowThreadNotifier = new Notifier(SplineFollowThread.getInstance());
				splineFollowThreadNotifier.startPeriodic(0.01);
				
				while(SplineFollowThread.isFollowingSpline()){
					
					Timer.delay(0.001);
				}
			}
		}catch(Exception e){
			
			e.printStackTrace();
		}
	}
	
	private synchronized void pushToMotionManagerTrap(double x, double y, double theta){
		motionManager.pushTurn(theta, false, true); //should work after profiling updates pushed
		//autogenerates trap profile
		double[][] profile = TrapezoidalProfile.getTrapezoidZero(Math.sqrt(x*x  + y*y), 300, 0, 0);
		motionManager.pushProfile(profile, false, true); //waits for previous profile and is last profile
		motionManager.startProfile();
	} 
	
	public synchronized void driveStraightRelative(double x, double y, double theta){
		try{
			if(isMoving()){
				
				throw new Exception("cannot call two maneuvers at once!");
			}else{
				
				
				
				_isMovingStraight = true;
				
				pushToMotionManagerTrap(x, y, theta);
				
				_isMovingStraight = false;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private synchronized void driveStraightAbsolute(double field_x, double field_y, double field_theta){
		try{
			if(isMoving()){
				
				throw new Exception("cannot call two maneuvers at once!");
			}else{
				
				double robot_x = networkTable.getNumber(NetworkConstants.GP_X, -1);
				double robot_y = networkTable.getNumber(NetworkConstants.GP_Y, -1);
				double robot_theta = networkTable.getNumber(NetworkConstants.GP_THETA, -1);
				
				_isMovingStraight = true;
				
				double rel_x = field_x - robot_x;
				double rel_y = field_y - robot_y;
				double rel_theta = field_theta - robot_theta;
				
				pushToMotionManagerTrap(rel_x, rel_y, rel_theta);
				
				
				
				
				_isMovingStraight = false;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public synchronized void driveRotate(double fieldTheta){
		try{
			if(isMoving()){
				throw new Exception("cannot call two maneuvers at once!");
			}else{
				
				_isRotating = true;
				
				//TODO:: call aditya's rotate-in-place function, and block this thread
				
				
				
				_isRotating = false;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
}
