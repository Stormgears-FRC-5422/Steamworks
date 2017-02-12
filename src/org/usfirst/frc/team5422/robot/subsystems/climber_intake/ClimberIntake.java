package org.usfirst.frc.team5422.robot.subsystems.climber_intake;

import org.usfirst.frc.team5422.utils.SteamworksConstants;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;

public class ClimberIntake extends Subsystem {
	public CANTalon climberIntakeTalon;


	public ClimberIntake(int climberIntakeTalonId){
		climberIntakeTalon = new CANTalon(SteamworksConstants.CLIMBER_INTAKE_TALON_ID);
	}

	public void climb(double throttleValue) {
		if(climberIntakeTalon.getControlMode() != TalonControlMode.PercentVbus){
			climberIntakeTalon.changeControlMode(TalonControlMode.PercentVbus);
		}

		climberIntakeTalon.set(throttleValue);	
	}
	public void takeIn() {
		if (climberIntakeTalon.getControlMode() != TalonControlMode.PercentVbus)			
			climberIntakeTalon.changeControlMode(TalonControlMode.PercentVbus);

		climberIntakeTalon.set(0.3);

	}

	public void stop()
	{
		climberIntakeTalon.set(0);
	}

	@Override
	protected void initDefaultCommand() {
	}

}

