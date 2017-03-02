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
	public SafeTalon[] talons;
	public CANTalon.MotionProfileStatus[] statuses = new CANTalon.MotionProfileStatus[4];
	private int pointCount = 0;
	private boolean isLastPoint = false;
	
	
	
	private RegisteredNotifier notifier = new RegisteredNotifier(new PeriodicRunnable(), "MotionControl");
	
	class PeriodicRunnable implements java.lang.Runnable {		
		// The purpose of this thread is just to push data into the firmware buffer
		// other details of the control come through the motion manager thread.
		public void run() {  		
//			System.out.println(talon.getDeviceID() + ": In run");
//			talon.getMotionProfileStatus(status);
//			System.out.println(talon.getDeviceID() + ": Before - top: " + status.topBufferCnt + " , bottom: " + status.btmBufferCnt);

			synchronized(this) { 
				if (stopNotifier) return; 

				if (pointCount > 0) {
					for (SafeTalon t : talons) {
						t.clearMotionProfileHasUnderrun();
						t.processMotionProfileBuffer();
					}
				}
				// out of points.  Nothing to do now
				if (pointCount-- == 0 && isLastPoint) stopNotifier = true;
			}
		}
	}
	    
	public MotionControl(SafeTalon[] talons) {
		this.talons = talons;
		int i = 0;
		for (SafeTalon t : talons) {
			t.changeControlMode(TalonControlMode.MotionProfile);
			t.clearMotionProfileTrajectories();
			t.changeMotionControlFramePeriod(5);
			t.setEncPosition(0);
			t.set(0);
			statuses[i++] = new CANTalon.MotionProfileStatus();
		}
	}

	public void stopControlThread() {
		synchronized(this) {
			stopNotifier = true;
			notifier.stop();
		}
	}
	
	public void startControlThread() {
		synchronized(this) {
			stopNotifier = false;
			notifier.startPeriodic(notifierRate);
		}
	}
		
	public void printStatus() {
		int i = 0;
		for (SafeTalon t : talons) {
			t.getMotionProfileStatus(statuses[i++]);
		}
	}

	public void shutDownProfiling() {
		for (SafeTalon t : talons) {
			t.clearMotionProfileTrajectories();
			t.clearMotionProfileHasUnderrun();
			t.changeControlMode(TalonControlMode.Speed); //may need to be vbus
		}		
	}	
	
	public void clearMotionProfileTrajectories() {
		for (SafeTalon t : talons) {
			t.clearMotionProfileTrajectories();
		}		
	}
	
	// wrapper functions for talon
	public boolean pushMotionProfileTrajectory(int talonIndex, TrajectoryPoint pt) { 
		// We are assuming that we are always getting all talons at the same time.  
		// As long as we aren't ever more than one point behind on any talon this works fine 
		synchronized(this) { 
			isLastPoint = false;  // most points
			if (talonIndex == talons.length) {
				pointCount++;
				isLastPoint = pt.isLastPoint;
			}
		}
		return talons[talonIndex].pushMotionProfileTrajectory(pt); 
	}
	
	public void clearMotionProfileTrajectories(int talonIndex) { talons[talonIndex].clearMotionProfileTrajectories(); }

	public int getEncVel(int talonIndex) { return talons[talonIndex].getEncVelocity();	}

	//TODO: add in some edge case error checking
	public void enable() {
		int i = 0;
		for (SafeTalon t : talons) {
			t.getMotionProfileStatus(statuses[i++]);
			t.set(1);
		}
	}
	
	public void disable() {
		for (SafeTalon t : talons) {
			t.set(0);
		}
	}
	
	public void holdProfile() {
		for (SafeTalon t : talons) {
			t.set(2);
		}
	}
	
	//all the end cases need to be monitored
	//enable()
	//disable()
	//clear profile(from bottom buffer)
	//pushToTopBuffer(point) --> Wrapper (name same as talon function)
	//pushToBottomBuffer(point) --> runnable  
}
