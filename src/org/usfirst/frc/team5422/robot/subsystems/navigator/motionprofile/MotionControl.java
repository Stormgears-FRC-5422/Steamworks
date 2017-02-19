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
	
	class PeriodicRunnable implements java.lang.Runnable {
		public void run() {  
	    	SmartDashboard.putString("pushing to btm buffer: ", "");
	    	SmartDashboard.putNumber("enc vel(in run): ", talon.getEncVelocity());
	    	clearUnderrun();
	    	talon.processMotionProfileBuffer();
	    	talon.getMotionProfileStatus(status);
	    	i.process(status,talon);
		}	
	}

	public MotionControl(CANTalon talon) {
		this.talon = talon;
		this.talon.changeControlMode(TalonControlMode.MotionProfile);
		notifier.startPeriodic(0.005);
		this.talon.changeMotionControlFramePeriod(5);
		this.talon.setEncPosition(0);
	}
	
	public void pushMotionProfileTrajectory(TrajectoryPoint pt) {
		talon.pushMotionProfileTrajectory(pt);
		talon.getMotionProfileStatus(status);
		SmartDashboard.putNumber("Btm Buffer Count: ", status.btmBufferCnt);
		SmartDashboard.putNumber("Top Buffer Count: ", status.topBufferCnt);
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
