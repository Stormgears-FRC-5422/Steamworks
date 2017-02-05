package org.usfirst.frc.team5422.robot.subsystems.navigator;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Navigator extends Subsystem{
	
	public CANTalon[] talons = new CANTalon[4];
	
	private static Navigator instance;
	
	public static Navigator getInstance(){
		if(instance==null){
			instance = new Navigator();
		}
		return instance;
	}
	
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

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}

	
}
