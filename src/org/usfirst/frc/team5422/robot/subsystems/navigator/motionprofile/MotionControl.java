package org.usfirst.frc.team5422.robot.subsystems.navigator.motionprofile;


import org.usfirst.frc.team5422.utils.SafeTalon;
import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;
import com.ctre.CANTalon.TrajectoryPoint;

import org.usfirst.frc.team5422.utils.RegisteredNotifier;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class MotionControl {
	private static final double notifierRate = 0.005;
	private boolean stopNotifier = false;	
	public SafeTalon talon;
	public CANTalon.MotionProfileStatus status = new CANTalon.MotionProfileStatus();
	
	private RegisteredNotifier notifier = new RegisteredNotifier(new PeriodicRunnable(), "MotionControl");
	
	class PeriodicRunnable implements java.lang.Runnable {		
		// The purpose of this thread is just to push data into the firmware buffer
		// other details of the control come through the motion manager thread.
		public void run() {  		
//			System.out.println(talon.getDeviceID() + ": In run");
//			talon.getMotionProfileStatus(status);
//			System.out.println(talon.getDeviceID() + ": Before - top: " + status.topBufferCnt + " , bottom: " + status.btmBufferCnt);

//			synchronized(this) { 
//				if (stopNotifier) return; 
//			}			
	    	talon.clearMotionProfileHasUnderrun();
			talon.processMotionProfileBuffer();
			
		}
	}
	    
	public MotionControl(SafeTalon talon) {
		System.out.println(talon.getDeviceID() + ": Created");
		this.talon = talon;
		this.talon.changeControlMode(TalonControlMode.MotionProfile);
		this.talon.clearMotionProfileTrajectories();
		this.talon.changeMotionControlFramePeriod(5);
		this.talon.setEncPosition(0);
		this.talon.set(0);
	}

	public void stopControlThread() {
		synchronized(this) {
//			System.out.println(talon.getDeviceID() + ": In stopControlThread");
			stopNotifier = true;
			notifier.stop();
		}
	}
	
	public void startControlThread() {
		synchronized(this) {
//			System.out.println("In startControlThread for id " + talon.getDeviceID());
			stopNotifier = false;
			notifier.startPeriodic(notifierRate);
		}
	}
		
	public void printStatus() {
		talon.getMotionProfileStatus(status);
//		System.out.println(talon.getDeviceID() + ": Now - top: " + status.topBufferCnt + " , bottom: " + status.btmBufferCnt);
	}
	
	// wrapper functions for talon
	public boolean pushMotionProfileTrajectory(TrajectoryPoint pt) { return talon.pushMotionProfileTrajectory(pt); }
	
	public void clearMotionProfileTrajectories() { talon.clearMotionProfileTrajectories(); }
	
	public void clearUnderrun() { talon.clearMotionProfileHasUnderrun(); }
	
	public void changeControlMode(TalonControlMode mode) { talon.changeControlMode(mode); }
	
	public int getEncVel() { return talon.getEncVelocity();	}
	
	//TODO: add in some edge case error checking
	public void enable() {
//		System.out.println(talon.getDeviceID() + ": In enable");
		talon.getMotionProfileStatus(status);
//		System.out.println(talon.getDeviceID() + ": Before - top: " + status.topBufferCnt + " , bottom: " + status.btmBufferCnt);
//		SmartDashboard.putNumber("btm buffer cnt: ", status.btmBufferCnt);

		talon.set(1);
		
//		if(status.btmBufferCnt > 5) {
//			System.out.println(talon.getDeviceID() + ": enabled");
//			SmartDashboard.putString("talon enabled:", "");
//			
//			talon.set(1);
//		} else {
//			System.out.println(talon.getDeviceID() + ": NOT enabled");
//		}
	}
	
	public void disable() {
//		System.out.println(talon.getDeviceID() + ": In disable");
		talon.set(0);
	}
	
	public void holdProfile() {
//		System.out.println(talon.getDeviceID() + ": In holdProfile");
		talon.set(2);
	}
	
	//all the end cases need to be monitored
	//enable()
	//disable()
	//clear profile(from bottom buffer)
	//pushToTopBuffer(point) --> Wrapper (name same as talon function)
	//pushToBottomBuffer(point) --> runnable  
}
