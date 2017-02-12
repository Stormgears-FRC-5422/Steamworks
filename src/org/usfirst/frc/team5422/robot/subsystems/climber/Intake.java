package org.usfirst.frc.team5422.robot.subsystems.climber;

import org.usfirst.frc.team5422.utils.SteamworksConstants;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Intake extends Subsystem {
	public CANTalon climberIntakeTalon;


	public Intake(){
		climberIntakeTalon = new CANTalon(SteamworksConstants.CLIMBER_INTAKE_TALON_ID);


	}
	
	public void takeIn(boolean climberSwitch, boolean intakeSwitch){
		if(climberIntakeTalon.getControlMode() != TalonControlMode.PercentVbus){
			climberIntakeTalon.changeControlMode(TalonControlMode.PercentVbus);
		}
		
		if(climberSwitch = false){
			if(intakeSwitch = true){
				
				climberIntakeTalon.set(0.3);
			}
			
		}
		
	}


	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}
}