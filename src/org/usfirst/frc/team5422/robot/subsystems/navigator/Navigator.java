package org.usfirst.frc.team5422.robot.subsystems.navigator;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Navigator extends Subsystem{
	
	private Notifier splineFollowThreadNotifier;
	
	private static Navigator instance;
	
	private static Drive mecanumDrive;
	
	private static boolean _isRotating;
	
	private static boolean _isMovingStraight;
	
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
	
	private synchronized void driveStraight(){
		try{
			if(isMoving()){
				
				throw new Exception("cannot call two maneuvers at once!");
			}else{
				
				_isMovingStraight = true;
				
				//TODO:: drive straight and block this thread
				
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
