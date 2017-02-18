package org.usfirst.frc.team5422.robot.commands;

import org.usfirst.frc.team5422.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class AutonomousCommand extends Command {
	public AutonomousCommand() {
    	requires(Robot.navigatorSubsystem);
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

	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
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
