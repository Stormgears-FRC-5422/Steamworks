package org.usfirst.frc.team5422.robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team5422.robot.commands.ExampleCommand;
import org.usfirst.frc.team5422.robot.subsystems.ExampleSubsystem;

//TODO: get max vel
//TODO: PID tune with F val
//TODO: figure out twist

public class Robot extends IterativeRobot {
	public static final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();
	
	Joystick joy = new Joystick(0);
	CANTalon[] talons = new CANTalon[4];

	public Robot() {
		
	}
	public void autonomousInit() {
		
	}
	public void teleopInit() {
		while(isOperatorControl() && isEnabled()) {
			double theta = joy.getDirectionRadians();
			if(theta < 0) theta = 2 * Math.PI + theta;
			if(Math.abs(joy.getX()) > 0.2 || Math.abs(joy.getY()) > 0.2 || Math.abs(joy.getZ()) > 0.2)
				mecMove(0.5, theta, joy.getZ());
			else mecMove(0, theta, 0);
			SmartDashboard.putNumber("0: ", talons[0].getEncVelocity());
			SmartDashboard.putNumber("1: ", talons[1].getEncVelocity());
			SmartDashboard.putNumber("2: ", talons[2].getEncVelocity());
			SmartDashboard.putNumber("3: ", talons[3].getEncVelocity());
		}
	}
	public void robotInit() {
		for(int i = 0; i < talons.length; i ++) {
			talons[i] = new CANTalon(i);
			if(i % 2 == 0) talons[i].setInverted(true);
		}
	}
	public void autonomous() {

	}
	public void teleop() {
		
	}
	public void test() {
		
	}
	
	private void mecTwist() {
		
	}
	
	private void mecMove(double tgtVel, double theta, double changeVel) {
		
		double[] vels = new double[talons.length];
		
		vels[0] = tgtVel * Math.sin(theta + Math.PI / 4.0) + changeVel;
		vels[1] = tgtVel * Math.cos(theta + Math.PI / 4.0) - changeVel;
		vels[2] = tgtVel * Math.cos(theta + Math.PI / 4.0) + changeVel;
		vels[3] = tgtVel * Math.sin(theta + Math.PI / 4.0) - changeVel;
		
		while(Math.abs(vels[0]) > 1.0 || Math.abs(vels[1]) > 1.0 || Math.abs(vels[2]) > 1.0 || Math.abs(vels[3]) > 1.0) {
			double max = Math.max(Math.max(Math.max(Math.abs(vels[0]), Math.abs(vels[1])), Math.abs(vels[2])), Math.abs(vels[3]));
			for(int i = 0; i < vels.length; i ++) {
				vels[i] /= max;
			}
		}
		
		for(int i = 0; i < talons.length; i ++) {
			talons[i].set(vels[i]);
		}
	}
}