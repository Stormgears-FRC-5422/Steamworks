package org.usfirst.frc.team5422.robot.subsystems.shooter;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.stormgears.WebDashboard.Diagnostics.Diagnostics;

public class Shooter extends Subsystem {
	CANTalon motor;
	double shootVelocity;

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public Shooter(int talonId) {
		motor = new CANTalon(talonId);
		motor.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		motor.setVoltageCompensationRampRate(20.0);
	}

	public void initDefaultCommand()
	{
		motor.set(0);
	}

	public void setShootVelocity(double shootVelocity) {
		this.shootVelocity = shootVelocity;
	}

	public void shoot() {
		motor.set(shootVelocity);
		Diagnostics.log("shootVelocity: " + shootVelocity);
		Diagnostics.log("shootVoltage: " + motor.getBusVoltage());
	}

	public void stop()
	{
		motor.set(0);
	}
}
