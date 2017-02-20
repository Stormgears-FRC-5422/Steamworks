package org.usfirst.frc.team5422.robot.subsystems.navigator;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Navigator extends Subsystem{
	
	private Notifier splineFollowThreadNotifier;
	
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
	
	//for convenience
	public synchronized void driveSpline(){
		
	}
	
	public synchronized void driveSpline(Spline spline){
		
		SplineFollowThread.loadInitialSpline(spline);
		splineFollowThreadNotifier = new Notifier(SplineFollowThread.getInstance());
		splineFollowThreadNotifier.startPeriodic(0.01);
		
		while(SplineFollowThread.isFollowingSpline()){
			
			Timer.delay(0.001);
		}
	}
	
	//TODO:: write rotate wrapper, and straight-line moving methods

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}

	
}
