package org.usfirst.frc.team5422.robot.subsystems.navigator;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Navigator extends Subsystem{
	
	public Notifier splineFollowThread;
	
	private static Navigator instance;
	private static MecanumDrive mecanumDrive;

	
	public Navigator() {
        mecanumDrive = new MecanumDrive();
	}
	
	public static MecanumDrive getMecanumDrive() {
		return mecanumDrive;
	}

	public static Navigator getInstance(){
		if(instance==null){
			instance = new Navigator();
		}
		return instance;
	}
	
	public synchronized void driveSpline(Spline spline){
		splineFollowThread = new Notifier(SplineFollowThread.getInstance());
		splineFollowThread.startPeriodic(0.01);
		while(true){
			SplineFollowThread.isFollowingSpline();
			Timer.delay(0.001);
		}
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}

	
}
