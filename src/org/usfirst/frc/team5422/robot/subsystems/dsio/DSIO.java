package org.usfirst.frc.team5422.robot.subsystems.dsio;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Scheduler;

import org.usfirst.frc.team5422.robot.commands.ClimbCommand;
import org.usfirst.frc.team5422.robot.commands.IntakeCommand;
import org.usfirst.frc.team5422.robot.commands.ShootCommand;
import org.usfirst.frc.team5422.utils.SteamworksConstants.intake_motor_mode;
import org.usfirst.frc.team5422.utils.SteamworksConstants.shooter_mode;

/**
 * Driver Station Input Output (D.S.I.O.)
 * Handles everything related to the button board and joystick
 * If you want to use a button, YOU MUST do it through here; ask Michael to add it
 */
public class DSIO {
	Joystick joystick;
	Joystick buttonBoard;

	JoystickButton bigBlue, smallBlue, greenSwitch, orangeSwitch, redSwitch;

	intake_motor_mode robotIntakeMotorMode = intake_motor_mode.INTAKE;
	intake_motor_mode robotClimberMotorMode = intake_motor_mode.CLIMB;
	shooter_mode robotShooterMode = shooter_mode.MANUAL;

	public DSIO(int joystickUsbChannel, int buttonBoardUsbChannel) {
		// Initialize joystick and buttons

		joystick = new Joystick(joystickUsbChannel);
		buttonBoard = new Joystick(buttonBoardUsbChannel);

		bigBlue = new JoystickButton(buttonBoard, ButtonIds.BIG_BLUE);
		smallBlue = new JoystickButton(buttonBoard, ButtonIds.SMALL_BLUE);
		greenSwitch = new JoystickButton(buttonBoard, ButtonIds.GREEN_SWITCH);
		orangeSwitch = new JoystickButton(buttonBoard,ButtonIds.INTAKE_ORANGE_SWITCH_ID);
		redSwitch = new JoystickButton(buttonBoard,ButtonIds.INTAKE_CLIMBER_RED_SWITCH_ID);

		// Assign commands to pushable buttons

		// Big Blue Button
		bigBlue.whenPressed(new ShootCommand(3, robotShooterMode));

		//Red Switch Button
		if(getFunctionForRedSwitch()){
			//Forcefully stops intake
			Scheduler.getInstance().add(new IntakeCommand (false));
			redSwitch.whenPressed(new ClimbCommand (getSliderValueClimber()));

		} else{ 
			//Forcefully stops climber
			Scheduler.getInstance().add(new ClimbCommand (0));
			redSwitch.whenReleased(new IntakeCommand(orangeSwitch.get()));


		// TODO: add the rest of the buttons we are using
		}
	}

	public void checkSwitches() {

		// RED SWITCH
		if (buttonBoard.getRawButton(ButtonIds.RED_SWITCH))
			robotIntakeMotorMode = intake_motor_mode.INTAKE;
		else
			robotIntakeMotorMode = intake_motor_mode.CLIMB;

		// GREEN SWITCH
		if (buttonBoard.getRawButton(ButtonIds.GREEN_SWITCH))
			robotShooterMode = shooter_mode.AUTONOMOUS;
		else
			robotShooterMode = shooter_mode.MANUAL;
		bigBlue.whenPressed(new ShootCommand(3, robotShooterMode)); // Update shooter mode

		// TODO: add the rest of the switches we are using
	}

	public double getManualShooterVelocity()
	{
		return (joystick.getZ());
	}

	public double getSliderValueClimber() {
		double climberVelocity = (joystick.getThrottle()-1)/2;


		return climberVelocity;
	} 

	public boolean getFunctionForRedSwitch (){
		boolean isClimber;

		if(redSwitch.get()){
			isClimber = true;
		}else isClimber = false;

		return isClimber;
	}
}
