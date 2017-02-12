package org.usfirst.frc.team5422.robot.subsystems.climber;

import org.usfirst.frc.team5422.utils.SteamworksConstants;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;

public class ClimberIntake extends Subsystem {
	public CANTalon climberTalon;

	public ClimberIntake() {
		climberTalon = new CANTalon(SteamworksConstants.CLIMBER_INTAKE_TALON_ID);
	}

	public void climb(double throttleValue, boolean climberSwitch) {
		if(climberTalon.getControlMode() != TalonControlMode.PercentVbus){
			climberTalon.changeControlMode(TalonControlMode.PercentVbus);
		}

		if(climberSwitch){
			climberTalon.set(throttleValue);	
		}
	}

	@Override
	protected void initDefaultCommand() {



	}




}




