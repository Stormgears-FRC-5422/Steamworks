package org.usfirst.frc.team5422.robot.subsystems.shooter;

//import org.stormgears.WebDashboard.Diagnostics.Diagnostics;
import org.usfirst.frc.team5422.utils.SafeTalon;
import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team5422.utils.SteamworksConstants;

public class Shooter extends Subsystem {
	SafeTalon motor;
	Relay impeller;
	private boolean enabled = false;

	double shootVelocity;

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public Shooter(int talonId, int relayId) {
		SmartDashboard.putNumber("Relay ID: ", relayId);

		motor = new SafeTalon(talonId);
		motor.changeControlMode(CANTalon.TalonControlMode.Speed);
		motor.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
	//	motor.configEncoderCodesPerRev(8192);

		impeller = new Relay(relayId);
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
		motor.set(-shootVelocity);
//		Diagnostics.log("shootVelocity: " + shootVelocity);
//		Diagnostics.log("shootVoltage: " + motor.getBusVoltage());
	}

	public void stop() {
		motor.set(0);
		stopImpeller();
	}
}
