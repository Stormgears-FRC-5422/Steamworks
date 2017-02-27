package org.usfirst.frc.team5422.robot.subsystems.shooter.shooter_thread;

import org.stormgears.WebDashboard.Diagnostics.Diagnostics;
import org.usfirst.frc.team5422.robot.Robot;

/**
 * Created by michael on 1/29/17.
 */
public class ShooterRunnable implements Runnable {
	int shootTimeSeconds;

	public ShooterRunnable(int shootTimeSeconds) {
		this.shootTimeSeconds = shootTimeSeconds;
	}

	@Override
	public void run() {
		Diagnostics.log("shooterThread is started.");

		try {
			Thread.sleep(shootTimeSeconds * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Robot.shooterSubsystem.stop();
		Diagnostics.log("shooterThread is stopped.");
	}
}
