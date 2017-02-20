package org.usfirst.frc.team5422.robot.subsystems.navigator;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Navigator extends Subsystem{
	
	private Notifier splineFollowThread;
	
	private static Navigator instance;
	
	private static Drive mecanumDrive;

	
	public Navigator() {
		//using Stormgears CloneBot Mecanum Drive
        mecanumDrive = new CloneBotMecanumDrive();
		
		//using Stormgears CloneBot Mecanum Drive
        //mecanumDrive = new RealBotMecanumDrive();

        //to test using WPI Mecanum Drive
        //mecanumDrive = new WPIMecanumDrive();        
	}
	
	public static Drive getMecanumDrive() {
		return mecanumDrive;
	}

	public static Navigator getInstance(){
		if(instance==null){
			instance = new Navigator();
		}
		return instance;
	}
	
	public synchronized void driveSpline(Spline spline){
		splineFollowThread = (Notifier) SplineFollowThread.getInstance();
		splineFollowThread.startPeriodic(0.01);
		while(SplineFollowThread.isFollowingSpline()){
			
			Timer.delay(0.001);
		}
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}

	
}
