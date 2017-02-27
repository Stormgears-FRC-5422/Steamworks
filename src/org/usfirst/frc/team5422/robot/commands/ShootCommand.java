package org.usfirst.frc.team5422.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.stormgears.WebDashboard.Diagnostics.Diagnostics;
import org.usfirst.frc.team5422.robot.Robot;
import org.usfirst.frc.team5422.robot.subsystems.shooter.shooter_thread.ShooterRunnable;
import org.usfirst.frc.team5422.utils.SteamworksConstants;

/**
 * Created by michael on 1/29/17.
 */
public class ShootCommand extends Command {
	ShooterRunnable shooterRunnable;
	Thread shooterThread;
	SteamworksConstants.shooterMode shooterMode;

	public ShootCommand(int shootTimeSeconds, SteamworksConstants.shooterMode shooterMode) {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.shooterSubsystem);

		shooterRunnable = new ShooterRunnable(shootTimeSeconds);
		this.shooterMode = shooterMode;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		if (shooterMode == SteamworksConstants.shooterMode.MANUAL)
			Robot.shooterSubsystem.setShootVelocity(Robot.dsio.getManualShooterVelocity());

		Diagnostics.log("shootCommand initializing...");
		shooterThread = new Thread(shooterRunnable);
		shooterThread.start();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.shooterSubsystem.shoot();
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return true;
	}

	// Called once after isFinished returns true
	protected void end() {
		Diagnostics.log("It shot a ball!!");
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		shooterThread.interrupt();
	}
}