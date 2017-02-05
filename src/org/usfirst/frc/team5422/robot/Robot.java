package org.usfirst.frc.team5422.robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	
	Joystick joy = new Joystick(0);
	CANTalon[] talons = new CANTalon[4];

	public Robot() {
		NetworkTable.initialize();
		//TODO:: initialize sensors here
	}
	
	public void autonomousInit() {
		
	}
	public void teleopInit() {
		while(isOperatorControl() && isEnabled()) {
			double theta = Math.atan2(joy.getX(), joy.getY());
			if(theta < 0) theta = 2 * Math.PI + theta;
			if(Math.abs(joy.getX()) > 0.1 || Math.abs(joy.getY()) > 0.1 || Math.abs(joy.getZ()) > 0.2)
				mecMove(6300 * Math.sqrt(joy.getX() * joy.getX() + joy.getY() * 
						joy.getY() + joy.getZ() * joy.getZ()), theta, joy.getZ());
			else {
				for(int i = 0; i < talons.length; i ++) {
					talons[i].set(0);
				}
			}
			SmartDashboard.putNumber("Joy in Radians", theta);
			SmartDashboard.putNumber("0: ", talons[0].getEncVelocity());
			SmartDashboard.putNumber("1: ", talons[1].getEncVelocity());
			SmartDashboard.putNumber("2: ", talons[2].getEncVelocity());
			SmartDashboard.putNumber("3: ", talons[3].getEncVelocity());
		}
	}
	public void robotInit() {
		for(int i = 0; i < talons.length; i ++) {
			talons[i] = new CANTalon(i);
			talons[i].reverseOutput(true);
			talons[i].changeControlMode(TalonControlMode.Speed);
			//Velocity PID Values
			talons[i].setP(0.08);
			talons[i].setI(0.0002);
			talons[i].setD(10.24);
			talons[i].setF(0.16);
			talons[i].setIZone(1500);	
			
			//Position PID Values
//			talons[i].setP(0.64);
//			talons[i].setI(0.001);
//			talons[i].setD(2.56);
//			talons[i].setF(0);
//			talons[i].setIZone(1000);
		}
	}
	public void autonomous() {

	}
	public void teleop() {
		
	}
	public void test() {
		
	}
	
	
	private void mecMove(double tgtVel, double theta, double changeVel) {
		
		double[] vels = new double[talons.length];
		
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
				vels[i] += changeVel;
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
				vels[i] = changeVel;
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