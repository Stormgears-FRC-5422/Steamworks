package org.usfirst.frc.team5422.robot.commands;

import org.usfirst.frc.team5422.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class IntakeCommand extends Command{
	private boolean intakeOn;

	public IntakeCommand(boolean toggleIntake) {
		requires(Robot.intake);
		intakeOn = toggleIntake;
	}



	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		System.out.println("Initializing button command for: Orange Switch");
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		if(intakeOn){
			Robot.intake.takeIn();
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}
