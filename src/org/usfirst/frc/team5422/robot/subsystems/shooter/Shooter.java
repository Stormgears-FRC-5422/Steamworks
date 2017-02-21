package org.usfirst.frc.team5422.robot.subsystems.shooter;

//import org.stormgears.WebDashboard.Diagnostics.Diagnostics;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter extends Subsystem {
	CANTalon motor;
	Relay impeller;

	double shootVelocity;

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public Shooter(int talonId, int relayId) {
		SmartDashboard.putNumber("Relay ID: ", relayId);

		motor = new CANTalon(talonId);
		motor.changeControlMode(CANTalon.TalonControlMode.Speed);
		motor.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		motor.configEncoderCodesPerRev(8192);

		impeller = new Relay(relayId);
	}

	public void initDefaultCommand()
	{
		motor.set(0);
	}

	public void setShootVelocity(double shootVelocity) {
		this.shootVelocity = shootVelocity;
	}

	public void startImpeller()
	{
		impeller.set(Relay.Value.kForward);
	}

	public void stopImpeller()
	{
		impeller.set(Relay.Value.kOff);
	}

	public void runImpellerReversed()
	{
		impeller.set(Relay.Value.kReverse);
	}

	public void shoot() {
		motor.set(shootVelocity * 81.92 * 0.5);
//		Diagnostics.log("shootVelocity: " + shootVelocity);
//		Diagnostics.log("shootVoltage: " + motor.getBusVoltage());
	}

	public void stop()
	{
		motor.set(0);
		stopImpeller();
	}
}
