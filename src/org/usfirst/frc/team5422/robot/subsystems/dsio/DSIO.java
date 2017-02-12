package org.usfirst.frc.team5422.robot.subsystems.dsio;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

import org.usfirst.frc.team5422.robot.Robot;
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

	shooter_mode robotShooterMode = shooter_mode.MANUAL;

	public DSIO(int joystickUsbChannel, int buttonBoardUsbChannel) {
		System.out.println("[DSIO] initializing...");
		// Initialize joystick and buttons

		joystick = new Joystick(joystickUsbChannel);
		buttonBoard = new Joystick(buttonBoardUsbChannel);

		bigBlue = new JoystickButton(buttonBoard, ButtonIds.BIG_BLUE);
		smallBlue = new JoystickButton(buttonBoard, ButtonIds.SMALL_BLUE);
		greenSwitch = new JoystickButton(buttonBoard, ButtonIds.GREEN_SWITCH);
		orangeSwitch = new JoystickButton(buttonBoard,ButtonIds.ORANGE_SWITCH);
		redSwitch = new JoystickButton(buttonBoard,ButtonIds.RED_SWITCH);

		// Assign commands to pushable buttons

		// Big Blue Button
		bigBlue.whenPressed(new ShootCommand(3, robotShooterMode));


		// TODO: add the rest of the buttons we are using
	}
	

	public void checkSwitches() {

		
		// RED SWITCH (Climb when in ON position)
		if (buttonBoard.getRawButton(ButtonIds.RED_SWITCH))
			Robot.climberIntake.climb(getSliderValueClimber());
		else
			Robot.climberIntake.stop();

		
		// GREEN SWITCH
		if (buttonBoard.getRawButton(ButtonIds.GREEN_SWITCH))
			robotShooterMode = shooter_mode.AUTONOMOUS;
		else
			robotShooterMode = shooter_mode.MANUAL;
		bigBlue.whenPressed(new ShootCommand(3, robotShooterMode)); // Update shooter mode

		// ORANGE SWITCH
		if (buttonBoard.getRawButton(ButtonIds.ORANGE_SWITCH))
			Robot.climberIntake.takeIn();
		else
			Robot.climberIntake.stop();
		
		
		//Error Check for both switches
		if(buttonBoard.getRawButton(ButtonIds.RED_SWITCH) && buttonBoard.getRawButton(ButtonIds.RED_SWITCH))
			Robot.climberIntake.stop();
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
