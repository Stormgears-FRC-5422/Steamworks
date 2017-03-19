package org.usfirst.frc.team5422.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5422.robot.Robot;

/**
 * MOVE THE FLAPS!
 */
public class GearFlapCommand extends Command {
	private int mode;

	public GearFlapCommand(int mode) {
		this.mode = mode;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.gearManipulatorSubsystem.setFlaps(mode);
	}

	@Override
	protected boolean isFinished() {
		return true;
	}
}
