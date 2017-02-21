package org.usfirst.frc.team5422.robot.subsystems.navigator.motionprofile;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;
import com.ctre.CANTalon.TrajectoryPoint;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MotionControl {

	private CANTalon talon;
	private CANTalon.MotionProfileStatus status = new CANTalon.MotionProfileStatus();
	private Notifier notifier = new Notifier(new PeriodicRunnable());
	private Instrumentation i = new Instrumentation();
	public int j = 0;
	private boolean stop = false;
	
	
	class PeriodicRunnable implements java.lang.Runnable {
		public void run() {  
			if(!stop) {
				if(status.btmBufferCnt == 0 && status.topBufferCnt == 0) j ++;
				else j = 0;
				//System.out.println("Control ran");
		    	SmartDashboard.putString("pushing to btm buffer: ", "");
		    	SmartDashboard.putNumber("enc vel(in run): ", talon.getEncVelocity());
		    	clearUnderrun();
		    	talon.processMotionProfileBuffer();
		    	talon.getMotionProfileStatus(status);
		    	i.process(status,talon);
				SmartDashboard.putNumber("Btm Buffer Count: ", status.btmBufferCnt);
				SmartDashboard.putNumber("Top Buffer Count: ", status.topBufferCnt);
			} else {
				notifier.stop();
			}
		}	
	}

	public MotionControl(CANTalon talon) {
		this.talon = talon;
		this.talon.changeControlMode(TalonControlMode.MotionProfile);
		this.talon.changeMotionControlFramePeriod(5);
		this.talon.setEncPosition(0);
	}
	
	public void stopControlThread() {
		stop = true;
	}
	public void startControlThread() {
		stop = false;
		notifier.startPeriodic(0.005);
	}
	
	public void pushMotionProfileTrajectory(TrajectoryPoint pt) {
		talon.pushMotionProfileTrajectory(pt);
		talon.getMotionProfileStatus(status);
//		SmartDashboard.putNumber("Btm Buffer Count: ", status.btmBufferCnt);
//		SmartDashboard.putNumber("Top Buffer Count: ", status.topBufferCnt);
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
		SmartDashboard.putNumber("btm buffer cnt: ", status.btmBufferCnt);
		talon.getMotionProfileStatus(status);
		if(status.btmBufferCnt > 5) {
			SmartDashboard.putString("talon enabled:", "");
			talon.set(1);
		}
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
