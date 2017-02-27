package org.usfirst.frc.team5422.robot.subsystems.navigator.motionprofile;

import org.usfirst.frc.team5422.utils.SafeTalon;
import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;
import com.ctre.CANTalon.TrajectoryPoint;

import org.usfirst.frc.team5422.utils.RegisteredNotifier;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MotionControl {
	private boolean stopNotifier = false;	
	private SafeTalon talon;
	private CANTalon.MotionProfileStatus status = new CANTalon.MotionProfileStatus();
	private RegisteredNotifier notifier = new RegisteredNotifier(new PeriodicRunnable());
	
	class PeriodicRunnable implements java.lang.Runnable {
		
		// The purpose of this thread is just to push data into the firmware buffer
		// other details of the control come through the motion manager thread.
		public void run() {  
			boolean stopNow;			
			synchronized(this) {
				stopNow = stopNotifier;
			}

			// We want to stop if the manager thread told us to or if we are out of work to do.
			talon.getMotionProfileStatus(status);
			//stopNow |= (status.btmBufferCnt == 0 && status.topBufferCnt == 0);

			
			
			if (stopNow) {
				// This may be redundant, but can't hurt
				System.out.println("++STOPPING FROM RUN++");
				stopControlThread();
				return;
			}
			
	    	SmartDashboard.putString("pushing to btm buffer: ", "");
	    	SmartDashboard.putNumber("enc vel(in run): ", talon.getEncVelocity());
	    	clearUnderrun();
	    	talon.processMotionProfileBuffer();
	    	talon.getMotionProfileStatus(status);
	    	Instrumentation.process(status,talon);
			SmartDashboard.putNumber("Btm Buffer Count: ", status.btmBufferCnt);
			SmartDashboard.putNumber("Top Buffer Count: ", status.topBufferCnt);				
		}	

	}

	public MotionControl(SafeTalon talon) {
		this.talon = talon;
		this.talon.changeControlMode(TalonControlMode.MotionProfile);
		this.talon.changeMotionControlFramePeriod(5);
		this.talon.setEncPosition(0);
	}
	
	public void stopControlThread() {
		synchronized(this) {
			stopNotifier = true;
			notifier.stop();
			System.out.println("In stopControlThread");
		}
	}
	public void startControlThread() {
		synchronized(this) {
			stopNotifier = false;
			notifier.startPeriodic(0.005);
			System.out.println("In startControlThread");
		}
	}
	
	public boolean pushMotionProfileTrajectory(TrajectoryPoint pt) {
		return talon.pushMotionProfileTrajectory(pt);
	}
	
	public void clearMotionProfileTrajectories() {
		talon.clearMotionProfileTrajectories();
	}
	
	public void clearUnderrun() {
		talon.clearMotionProfileHasUnderrun();
	}
	
	public void changeControlMode(TalonControlMode mode) {
		talon.changeControlMode(mode);
	}
	
	public int getEncVel() {
		return talon.getEncVelocity();
	}
	
	//TODO: add in some edge case error checking
	public void enable() {
		talon.getMotionProfileStatus(status);
		SmartDashboard.putNumber("btm buffer cnt: ", status.btmBufferCnt);
		/*if(status.btmBufferCnt > 5) {
			SmartDashboard.putString("talon enabled:", "");
			talon.set(1);
		}*/
		
		talon.set(1);
	}
	
	public void disable() {
		talon.set(0);
	}
	
	public void holdProfile() {
		talon.set(2);
	}
	
	//all the end cases need to be monitored
	//enable()
	//disable()
	//clear profile(from bottom buffer)
	//pushToTopBuffer(point) --> Wrapper (name same as talon function)
	//pushToBottomBuffer(point) --> runnable  
}
