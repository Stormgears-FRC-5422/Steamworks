package org.usfirst.frc.team5422.robot.subsystems.gear;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team5422.robot.Robot;
import org.usfirst.frc.team5422.utils.SteamworksConstants;

public class Manipulator extends Subsystem {

	private Servo[] flaps = new Servo[2];

	public Manipulator(int leftFlapChannel, int rightFlapChannel) {
		flaps[0] = new Servo(leftFlapChannel);
		flaps[1] = new Servo(rightFlapChannel);
	}

	@Override
	protected void initDefaultCommand() {

	}

	public void setFlaps(int mode) {
		switch (mode) {
			case SteamworksConstants.FLAPS_NEUTRAL: {
				flaps[0].set(SteamworksConstants.FLAPS_NEUTRAL_POS);
				flaps[1].set(1 - SteamworksConstants.FLAPS_NEUTRAL_POS);
				SmartDashboard.putString("Flap Position: ", "NEUTRAL");
				break;
			}
			case SteamworksConstants.FLAPS_RECEIVING: {
				flaps[0].set(SteamworksConstants.FLAPS_RECEIVING_POS);
				flaps[1].set(1 - SteamworksConstants.FLAPS_RECEIVING_POS);
				SmartDashboard.putString("Flap Position: ", "RECEIVING");
				break;
			}
			case SteamworksConstants.FLAPS_DISPENSE: {
				flaps[0].set(SteamworksConstants.FLAPS_DISPENSE_POS);
				flaps[1].set(1 - SteamworksConstants.FLAPS_DISPENSE_POS);
				SmartDashboard.putString("Flap Position: ", "DISPENSE");
				break;
			}
		}
	}

	public void tests() {
		double left = Robot.dsio.joystick.getThrottle();
		double right = 1 - left;

		SmartDashboard.putNumber("flap 0: ", left);
		SmartDashboard.putNumber("flap 1: ", right);

		flaps[0].set(left);
		flaps[1].set(right);

	}

}
