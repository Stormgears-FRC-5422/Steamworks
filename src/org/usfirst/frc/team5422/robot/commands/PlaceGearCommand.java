package org.usfirst.frc.team5422.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5422.robot.Robot;

public class PlaceGearCommand extends Command {
	public PlaceGearCommand() {
		requires(Robot.navigatorSubsystem);
		requires(Robot.gearManipulatorSubsystem);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		System.out.println("Place Gear Command initialized...");
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		System.out.println("Entering isFinished method of PlaceGearCommand...");
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		System.out.println("Place Gear Command ended...");
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}
