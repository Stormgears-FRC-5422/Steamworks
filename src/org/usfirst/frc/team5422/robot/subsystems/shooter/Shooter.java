package org.usfirst.frc.team5422.robot.subsystems.shooter;

//import org.stormgears.WebDashboard.Diagnostics.Diagnostics;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.stormgears.StormUtils.SafeTalon;
import org.usfirst.frc.team5422.utils.SteamworksConstants;

public class Shooter extends Subsystem {
	SafeTalon motor;
	SafeTalon impeller;

	double shootVelocity;

	public boolean alreadyRunning = false;

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public Shooter(int shooterId, int impellerId) {
		SmartDashboard.putNumber("Relay ID: ", impellerId);

		motor = new SafeTalon(shooterId);
		motor.changeControlMode(CANTalon.TalonControlMode.Speed);

		impeller = new SafeTalon(impellerId);
	}

	public void initDefaultCommand() {
		motor.set(0);
	}

	public void setShootVelocity(double shootVelocity) {
		this.shootVelocity = shootVelocity;
	}

	public void startImpeller() {
		impeller.set(1);
	}

	public void stopImpeller() {
		impeller.set(0);
	}

	public void runImpellerReversed() {
		impeller.set(-1);
	}

	public void shoot() {
		motor.set(shootVelocity * 81.92 * 0.5);
		startImpeller();

		alreadyRunning = true;
	}

	public void stop() {
		motor.set(0);
		stopImpeller();

		alreadyRunning = false;
	}
}
