package org.usfirst.frc.team5422.robot.subsystems.shooter;

//import org.stormgears.WebDashboard.Diagnostics.Diagnostics;
import org.usfirst.frc.team5422.utils.SafeTalon;
import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team5422.utils.SteamworksConstants;

public class Shooter extends Subsystem {
	public SafeTalon motor;
	private Relay impeller;
	private boolean enabled = false;

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public Shooter(int shooterId, int propellerId) {
		motor = new SafeTalon(shooterId);
		motor.changeControlMode(CANTalon.TalonControlMode.Speed);
		motor.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		motor.setP(SteamworksConstants.SHOOTER_P);
		motor.setI(0);
		motor.setD(0);
		motor.setIZone(0);
		motor.setF(SteamworksConstants.SHOOTER_F);

		impeller = new Relay(propellerId);
	}

	public void initDefaultCommand()
	{
		motor.set(0);
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void startImpeller()
	{
		System.out.println("propeller running");
		impeller.set(Relay.Value.kReverse);
	}

	public void stopImpeller()
	{
		impeller.set(Relay.Value.kOff);
	}

	public void initializeShooter() {
		enabled = true;
		shoot();
	}
	public void shoot() {
		motor.set(SteamworksConstants.SHOOT_HIGH_SPEED);
	}

	public void stop() {
		motor.set(0);
		stopImpeller();
	}
}
