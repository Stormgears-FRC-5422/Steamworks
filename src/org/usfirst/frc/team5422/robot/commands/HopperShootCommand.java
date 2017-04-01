package org.usfirst.frc.team5422.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5422.robot.Robot;
import org.usfirst.frc.team5422.robot.subsystems.navigator.Navigator;
import org.usfirst.frc.team5422.robot.subsystems.navigator.motionprofile.MotionManager;
import org.usfirst.frc.team5422.robot.subsystems.navigator.motionprofile.TrapezoidalProfile;
import org.usfirst.frc.team5422.robot.subsystems.shooter.shooter_thread.ShooterRunnable;
import org.usfirst.frc.team5422.utils.HardwareConstants;
import org.usfirst.frc.team5422.utils.SteamworksConstants;
import org.usfirst.frc.team5422.utils.SteamworksConstants.alliances;

/**
 * Shoots from the hopper; it's what everybody wants because it can get us points. This better work.
 */
public class HopperShootCommand extends Command {
	private ShooterRunnable shooterRunnable;
	private Thread shooterThread;
	private MotionManager m;
	private alliances alliance;

	public HopperShootCommand(alliances alliance) {
		requires(Robot.navigatorSubsystem);
		requires(Robot.shooterSubsystem);
		this.alliance = alliance;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		System.out.println("Shooting without a person doing anything! LeBROWN CURRY!!");
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		shooterRunnable = new ShooterRunnable();
		m = Navigator.motionManager;

		// Drive 104 inches forward
		m.pushProfile(TrapezoidalProfile.getTrapezoidZero(104.0 / HardwareConstants.ROTATION_CALC_FACTOR, 70, Math.PI/2, 0), true, false);


		// Go left or right by 43 inches
		if (alliance == alliances.BLUE) {
			m.pushProfile(TrapezoidalProfile.getTrapezoidZero(50.0 / HardwareConstants.ROTATION_CALC_FACTOR, 70, Math.PI, 0), false, true);
			// Don't need to wait for balls since we're not moving away from hopper
		}
		else {
			m.pushProfile(TrapezoidalProfile.getTrapezoidZero(50.0 / HardwareConstants.ROTATION_CALC_FACTOR, 70, 0.0, 0), false, true);
			m.waitUntilProfileFinishes(100);

			// Wait for balls to fall in
			try {
				wait(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// Go out six inches and turn slightly
			m.pushProfile(TrapezoidalProfile.getTrapezoidZero(6.0 / HardwareConstants.ROTATION_CALC_FACTOR, 70, Math.PI, 0), true, false);
			m.pushTurn(-0.1311795572, false, true);
		}
		m.waitUntilProfileFinishes(100);

		// t f (beginning)
		// t f (any middle)
		// f t (end)

		// SHOOT!!
		Robot.shooterSubsystem.initializeShooter();

		shooterThread = new Thread(shooterRunnable);
		shooterThread.start();
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return true;
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
