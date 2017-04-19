package org.usfirst.frc.team5422.robot.subsystems.shooter;

import org.usfirst.frc.team5422.utils.RobotTalonConstants;
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
	double shootVelocity = -54500;

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public Shooter(int shooterId, int propellerId) {
		motor = new SafeTalon(shooterId);
		motor.changeControlMode(CANTalon.TalonControlMode.Speed);
		motor.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		motor.setP(RobotTalonConstants.SHOOTER_P);
		motor.setI(0);
		motor.setD(0);
		motor.setIZone(0);
		motor.setF(RobotTalonConstants.SHOOTER_F);

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

	public void setShootVelocity(double shootVelocity) {
		this.shootVelocity = -54500;
	}

	// Reverse is the normal direction
	public void startImpeller()
	{
		System.out.println("propeller running");
		impeller.set(Relay.Value.kReverse);
	}

	// this back off in the wrong direction
	public void reverseImpeller()
	{
		System.out.println("propeller running the other direction");
		impeller.set(Relay.Value.kForward);
	}

	public void stopImpeller()
	{
		impeller.set(Relay.Value.kOff);
	}

	public void initializeShooter() {
		enabled = true;
//		shoot();
	}
	public void shoot() {
		motor.set(shootVelocity);
//		motor.set(RobotTalonConstants.SHOOT_HIGH_SPEED);
	}

	public void stop() {
		motor.set(0);
		stopImpeller();
	}
}
