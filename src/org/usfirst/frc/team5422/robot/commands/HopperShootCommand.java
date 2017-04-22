package org.usfirst.frc.team5422.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5422.robot.Robot;
import org.usfirst.frc.team5422.robot.subsystems.navigator.Navigator;
import org.usfirst.frc.team5422.robot.subsystems.navigator.motionprofile.MotionManager;
import org.usfirst.frc.team5422.robot.subsystems.navigator.motionprofile.TrapezoidalProfile;
import org.usfirst.frc.team5422.robot.subsystems.sensors.SensorManager;
import org.usfirst.frc.team5422.robot.subsystems.sensors.StormgearsI2CSensor;
import org.usfirst.frc.team5422.robot.subsystems.sensors.Vision;
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
		Robot.gearManipulatorSubsystem.setFlaps(SteamworksConstants.FLAPS_DISPENSE);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		shooterRunnable = new ShooterRunnable();
		m = Navigator.motionManager;
		
		// Drive 84 (used to be 112) inches forward
		m.pushProfile(TrapezoidalProfile.getTrapezoidZero(96.0 / HardwareConstants.ROTATION_CALC_FACTOR, 580, 3*Math.PI/2, 0), true, false);

		// Go left or right by 64 inches (really just 43 but it hits the wall to align)
		if (alliance == alliances.RED) {
			m.pushProfile(TrapezoidalProfile.getTrapezoidZero(75.0 / HardwareConstants.ROTATION_CALC_FACTOR, 135, Math.PI+0.5, 0), false, true);
			m.waitUntilProfileFinishes(100);
			// Don't need to wait for balls since we're not moving away from hopper
			
			//m.pushProfile(TrapezoidalProfile.getTrapezoidZero(10.0 / HardwareConstants.ROTATION_CALC_FACTOR, 45, 0.2, 0), false, true);
		//	m.waitUntilProfileFinishes(100);
			m.pushProfile(TrapezoidalProfile.getTrapezoidZero(24.0 / HardwareConstants.ROTATION_CALC_FACTOR, 180, Math.PI/2, 0), false, true);
			m.waitUntilProfileFinishes(100);	
			m.pushProfile(TrapezoidalProfile.getTrapezoidZero(6.0 / HardwareConstants.ROTATION_CALC_FACTOR, 180, -0.2, 0), false, false);
			m.waitUntilProfileFinishes(100);
			// Back away from hopper wall 6 inches
		//	m.pushProfile(TrapezoidalProfile.getTrapezoidZero(10.0 / HardwareConstants.ROTATION_CALC_FACTOR, 45, 0.2, 0), false, true);
		//	m.waitUntilProfileFinishes(100);
		}
		else {
			m.pushProfile(TrapezoidalProfile.getTrapezoidZero(75.0 / HardwareConstants.ROTATION_CALC_FACTOR, 135, -0.5, 0), false, false);
			m.waitUntilProfileFinishes(100);

			m.pushProfile(TrapezoidalProfile.getTrapezoidZero(24.0 / HardwareConstants.ROTATION_CALC_FACTOR, 180, Math.PI/2, 0), false, true);
			m.waitUntilProfileFinishes(100);	
			m.pushProfile(TrapezoidalProfile.getTrapezoidZero(6.0 / HardwareConstants.ROTATION_CALC_FACTOR, 180, Math.PI+0.2, 0), false, false);
			m.waitUntilProfileFinishes(100);
		
	//		m.pushProfile(TrapezoidalProfile.getTrapezoidZero(13.0 / HardwareConstants.ROTATION_CALC_FACTOR, 90, 3*Math.PI/2, 0), false, true);
	//		m.waitUntilProfileFinishes(100);
			
			// Go out six inches and turn slightly
		}

		// t f (beginning)  
		// t f (any middle)
		// f t (end)

		// Align with vision!!
		Vision vision = SensorManager.getVisionSubsystem();
		vision.alignToBoiler();

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
