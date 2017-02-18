package org.usfirst.frc.team5422.robot.subsystems.navigator;

import org.usfirst.frc.team5422.robot.Robot;
import org.usfirst.frc.team5422.utils.SteamworksConstants;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/*
 * @author Aditya Naik
 */


public class MecanumDrive {
	public static CANTalon[] talons = new CANTalon[4];

	public MecanumDrive() {		
		for(int i = 0; i < talons.length; i ++) {
			
			talons[i] = new CANTalon(i);
			
			talons[i].reverseOutput(true);
			talons[i].changeControlMode(TalonControlMode.Speed);
			//Velocity PID Values
			talons[i].setPID(SteamworksConstants.VELOCITY_P, 
							 SteamworksConstants.VELOCITY_I, 
							 SteamworksConstants.VELOCITY_D);
			talons[i].setF(SteamworksConstants.VELOCITY_F);
			talons[i].setIZone(SteamworksConstants.VELOCITY_IZONE);	
			
			//Position PID Values
//			talons[i].setPID(SteamworksConstants.POSITION_P, 
//							 SteamworksConstants.POSITION_I, 
//							 SteamworksConstants.POSITION_D);
//			talons[i].setF(SteamworksConstants.POSITION_F);
//			talons[i].setIZone(SteamworksConstants.POSITION_IZONE);
		}
	}
	
	
	public void initializeDriveTalons() {
		for(int i = 0; i < talons.length; i ++) {
			talons[i].set(0);
		}
		
	}
	
	public void move() {
		System.out.println("Mecanum Drive moving...");
		Joystick joy = Robot.dsio.getJoystick();

		double theta = Math.atan2(joy.getX(), joy.getY());
		if(theta < 0) theta = 2 * Math.PI + theta;

		if(Math.abs(joy.getX()) > 0.1 || Math.abs(joy.getY()) > 0.1 || Math.abs(joy.getZ()) > 0.2)
			mecMove(6300 * Math.sqrt(joy.getX() * joy.getX() + joy.getY() * 
					joy.getY() + joy.getZ() * joy.getZ()), theta, joy.getZ());
		else {
			initializeDriveTalons();
		}
		
	}
	
	public void mecMove(double tgtVel, double theta, double changeVel) {
		
		double[] vels = new double[talons.length];
		Joystick joy = Robot.dsio.getJoystick();
		
			
		if(Math.abs(theta - 0) <= Math.PI / 6.0 || Math.abs(theta - 2.0 * Math.PI) <= Math.PI / 12.0) {
			theta = 0;
		}
		if(Math.abs(theta - Math.PI / 2.0) <= Math.PI / 12.0) {
			theta = Math.PI / 2.0;
		}
		if(Math.abs(theta - Math.PI) <= Math.PI / 12.0) {
			theta = Math.PI;
		}
		if(Math.abs(theta - 3.0 * Math.PI / 2.0) <= Math.PI / 12.0) {
			theta = 3.0 * Math.PI / 2.0;
		}
		vels[0] = -(Math.sin(theta + Math.PI / 2.0) + Math.cos(theta + Math.PI / 2.0));
		vels[1] = (Math.sin(theta + Math.PI / 2.0) - Math.cos(theta + Math.PI / 2.0));
		vels[2] = -(Math.sin(theta + Math.PI / 2.0) - Math.cos(theta + Math.PI / 2.0));
		vels[3] = (Math.sin(theta + Math.PI / 2.0) + Math.cos(theta + Math.PI / 2.0));
		
		if(Math.abs(changeVel) > 0.5) {
			for(int i = 0; i < vels.length; i ++) {
				vels[i] -= changeVel;
			}
		}
		
		while(Math.abs(vels[0]) > 1.0 || Math.abs(vels[1]) > 1.0 || Math.abs(vels[2]) > 1.0 || Math.abs(vels[3]) > 1.0) {
			double max = Math.max(Math.max(Math.max(Math.abs(vels[0]), Math.abs(vels[1])), Math.abs(vels[2])), Math.abs(vels[3]));
			for(int i = 0; i < vels.length; i ++) {
				vels[i] /= max;
			}
		}

		if(Math.abs(joy.getX()) < 0.2 && Math.abs(joy.getY()) < 0.2) {
			for(int i = 0; i < vels.length; i ++) {
				vels[i] = -changeVel;
			}
		}
		
		for(int i = 0; i < vels.length; i ++) {
			vels[i] *= tgtVel;
		}
		
		SmartDashboard.putNumber("Set value for 0:", vels[0]);
		SmartDashboard.putNumber("Set value for 1:", vels[1]);
		SmartDashboard.putNumber("Set value for 2:", vels[2]);
		SmartDashboard.putNumber("Set value for 3:", vels[3]);
		
		for(int i = 0; i < talons.length; i ++) {
			talons[i].set(vels[i]);
		}
	}
	
} 
