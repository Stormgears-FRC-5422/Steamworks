package org.usfirst.frc.team5422.robot.commands;

import org.usfirst.frc.team5422.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5422.robot.subsystems.navigator.Pose;
import org.usfirst.frc.team5422.robot.subsystems.sensors.SensorManager;

import java.util.ArrayList;

public class AutonomousCommand extends Command {
	private ArrayList<Pose> routeToGear, routeToDropOff;

	public AutonomousCommand() {
		// When this is called, autonomous isn't doing anything
	}

	public AutonomousCommand(ArrayList<Pose> routeToGear, ArrayList<Pose> routeToDropOff) {
		requires(Robot.navigatorSubsystem);

		this.routeToGear = routeToGear;
		this.routeToDropOff = routeToDropOff;
	}
	
	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		System.out.println("Automous Command initialized...");
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		System.out.println("Robot executing AutonomousCommand...");

		Robot.navigatorSubsystem.getInstance().getMecanumDrive().autoMove();
		SensorManager.vision.alignToGear();
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return true;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		System.out.println("Autonomous Command ended...");
		
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {

	}
}
