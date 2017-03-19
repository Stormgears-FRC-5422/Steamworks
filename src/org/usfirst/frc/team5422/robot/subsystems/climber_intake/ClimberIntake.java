package org.usfirst.frc.team5422.robot.subsystems.climber_intake;

import org.usfirst.frc.team5422.utils.SteamworksConstants;
import org.usfirst.frc.team5422.utils.RobotTalonConstants;
import org.usfirst.frc.team5422.utils.SafeTalon;
import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;

public class ClimberIntake extends Subsystem {
	public SafeTalon climberIntakeTalon;
	public SafeTalon secondaryIntakeTalon;


	public ClimberIntake(int climberIntakeTalonId){
		climberIntakeTalon = new SafeTalon(RobotTalonConstants.CLIMBER_TALON_ID);
		secondaryIntakeTalon = new SafeTalon (RobotTalonConstants.INTAKE_TALON_ID);
		
	}

	public void climb(double throttleValue) {
		
		if(climberIntakeTalon.getControlMode() != TalonControlMode.PercentVbus){
			climberIntakeTalon.changeControlMode(TalonControlMode.PercentVbus);
		}
	
		climberIntakeTalon.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		
		climberIntakeTalon.set(throttleValue);	
		
	}
	public void takeIn() {
		if (climberIntakeTalon.getControlMode() != TalonControlMode.Speed)			
			climberIntakeTalon.changeControlMode(TalonControlMode.Speed);

		climberIntakeTalon.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		climberIntakeTalon.set(5000);
		secondaryIntakeTalon.set(5000);

	}
	

	public void stop()
	{
		climberIntakeTalon.set(0);
		secondaryIntakeTalon.set(0);
	}

	@Override
	protected void initDefaultCommand() {
		climberIntakeTalon.set(0);
	}

}