package org.usfirst.frc.team5422.robot.subsystems.navigator;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Navigator extends Subsystem{
	private static boolean _isDrivingSpline = false;
	
	private static boolean _isTurning = false;
	
	public boolean isDrivingSpline(){
		return _isDrivingSpline; 
	}
	
	public boolean isTurning(){
		return _isTurning; 
	}
	
	public synchronized void driveSpline(Spline spline){
		_isDrivingSpline = true;
		
		//TODO::
		
		_isDrivingSpline = false;
	}
	
	public synchronized void rotateToTheta(double theta){
		_isTurning = true;
		
		//TODO::
			
		_isTurning = false;
	}
	
	public synchronized void rotateByTheta(double theta){
		_isTurning = true;
		
		//TODO::
			
		_isTurning = false;
	}

	
}
