package org.usfirst.frc.team5422.robot.subsystems.dsio;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;


import org.usfirst.frc.team5422.robot.Robot;
import org.usfirst.frc.team5422.robot.commands.ShootCommand;
import org.usfirst.frc.team5422.utils.SteamworksConstants.intake_motor_mode;
import org.usfirst.frc.team5422.utils.SteamworksConstants.shooter_mode;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team5422.robot.Robot;
import org.usfirst.frc.team5422.utils.ButtonIds;
import org.usfirst.frc.team5422.utils.SteamworksConstants.alliances;
import org.usfirst.frc.team5422.utils.SteamworksConstants.autonomousDropOffLocationOptions;
import org.usfirst.frc.team5422.utils.SteamworksConstants.autonomousGearPlacementOptions;
import org.usfirst.frc.team5422.utils.SteamworksConstants.shooterMode;


/**
 * Driver Station Input Output (D.S.I.O.)
 * Handles everything related to the button board and joystick
 * If you want to use a button, YOU MUST do it through here; ask Michael to add it
 */
public class DSIO {

	Joystick joystick;
	Joystick buttonBoard;

	public Joystick joystick;
	public Joystick buttonBoard;
	public SendableChooser<alliances> allianceChooser;
	public SendableChooser<autonomousGearPlacementOptions> autonomousGearPlacementOptionsChooser;
	public SendableChooser<autonomousDropOffLocationOptions> autonomousDropOffLocationOptionsChooser;

	JoystickButton bigBlue, smallBlue, greenSwitch, orangeSwitch, redSwitch;

	shooter_mode robotShooterMode = shooter_mode.MANUAL;
	shooterMode robotShooterMode = shooterMode.MANUAL;

	public DSIO(int joystickUsbChannel, int buttonBoardUsbChannel) {
		System.out.println("[DSIO] initializing...");
		// Initialize joystick and buttons

		// Initialize joystick and buttons
		joystick = new Joystick(joystickUsbChannel);
		buttonBoard = new Joystick(buttonBoardUsbChannel);


		joystick = new Joystick(joystickUsbChannel);
		buttonBoard = new Joystick(buttonBoardUsbChannel);

		bigBlue = new JoystickButton(buttonBoard, ButtonIds.BIG_BLUE_BUTTON_ID);
		smallBlue = new JoystickButton(buttonBoard, ButtonIds.SMALL_BLUE_BUTTON_ID);
		greenSwitch = new JoystickButton(buttonBoard, ButtonIds.GREEN_SWITCH_ID);
		orangeSwitch = new JoystickButton(buttonBoard, ButtonIds.ORANGE_SWITCH_ID);
		redSwitch = new JoystickButton(buttonBoard, ButtonIds.RED_SWITCH_ID);

		bigBlue = new JoystickButton(buttonBoard, ButtonIds.BIG_BLUE);
		smallBlue = new JoystickButton(buttonBoard, ButtonIds.SMALL_BLUE);
		greenSwitch = new JoystickButton(buttonBoard, ButtonIds.GREEN_SWITCH);
		orangeSwitch = new JoystickButton(buttonBoard,ButtonIds.ORANGE_SWITCH);
		redSwitch = new JoystickButton(buttonBoard,ButtonIds.RED_SWITCH);

		initializeChoosers();


		// Assign commands to pushable buttons

		// Big Blue Button

		bigBlue.whenPressed(new ShootCommand(3, robotShooterMode));

		//		bigBlue.whenPressed(new ShootCommand(3, robotShooterMode));

	}
		// TODO: add the rest of the buttons we are using
	}
	
	public void checkSwitches() {
		System.out.println("in check switches method...");
		// RED SWITCH (Climb when in ON position)
		while (buttonBoard.getRawButton(ButtonIds.RED_SWITCH_ID) ) {
			//System.out.println("RED SWITCH Pressed...");
			
			Robot.climberIntakeSubsystem.climb(getSliderValueClimber());
			if(buttonBoard.getRawButton(ButtonIds.RED_SWITCH_ID) == false) {
				break;
			}
		}

	public void checkSwitches() {

		// GREEN SWITCH
		if (buttonBoard.getRawButton(ButtonIds.GREEN_SWITCH_ID))
			//System.out.println("GREEN SWITCH Pressed...");
			robotShooterMode = shooterMode.AUTONOMOUS;
		else
			robotShooterMode = shooterMode.MANUAL;

		
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
		if (buttonBoard.getRawButton(ButtonIds.ORANGE_SWITCH_ID))
			//System.out.println("ORANGE SWITCH Pressed...");
			Robot.climberIntakeSubsystem.takeIn();
		else

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
		//Error Check for both switches
		if (buttonBoard.getRawButton(ButtonIds.RED_SWITCH_ID) && buttonBoard.getRawButton(ButtonIds.ORANGE_SWITCH_ID))
			Robot.climberIntakeSubsystem.stop();


		SmartDashboard.putBoolean("BUTTON 7:", false);
		SmartDashboard.putBoolean("BUTTON 8:", false);

		//TODO: get rid of this once testing is done
		if (joystick.getRawButton(7)) {
			Robot.shooterSubsystem.startImpeller();
			SmartDashboard.putBoolean("BUTTON 7:", true);
		} else if (joystick.getRawButton(8)) {
			Robot.shooterSubsystem.runImpellerReversed();
			SmartDashboard.putBoolean("BUTTON 8:", true);
		} else {
			Robot.shooterSubsystem.stopImpeller();
		}
	}

	public double getManualShooterVelocity() {
		return (joystick.getZ());
	}


	public double getSliderValueClimber() {
		double climberVelocity = (-1*(joystick.getThrottle() - 1) / 2);

		return climberVelocity;
	}

	public Joystick getJoystick() {
		return joystick;
	}
	private void initializeChoosers() {

		allianceChooser = new SendableChooser<alliances>();
		allianceChooser.addDefault("Red Alliance (Boiler to the right)", alliances.RED);
		allianceChooser.addObject("Blue Alliance (Boiler to the left)", alliances.BLUE);
		SmartDashboard.putData("Alliance Chooser", allianceChooser);

		autonomousGearPlacementOptionsChooser = new SendableChooser<autonomousGearPlacementOptions>();
		autonomousGearPlacementOptionsChooser.addObject("Place Gear Left", autonomousGearPlacementOptions.PLACE_GEAR_LEFT_AIRSHIP);
		autonomousGearPlacementOptionsChooser.addDefault("Place Gear Center", autonomousGearPlacementOptions.PLACE_GEAR_CENTER_AIRSHIP);
		autonomousGearPlacementOptionsChooser.addObject("Place Gear Right", autonomousGearPlacementOptions.PLACE_GEAR_RIGHT_AIRSHIP);
		autonomousGearPlacementOptionsChooser.addObject("Not Moving in Autonomous", autonomousGearPlacementOptions.NONE);
		SmartDashboard.putData("Autonomous Gear Placement Chooser", autonomousGearPlacementOptionsChooser);

		autonomousDropOffLocationOptionsChooser = new SendableChooser<autonomousDropOffLocationOptions>();
		autonomousDropOffLocationOptionsChooser.addDefault("Drop Off at Gear Pickup", autonomousDropOffLocationOptions.GEAR_PICKUP);
		autonomousDropOffLocationOptionsChooser.addObject("Drop Off at Baseline", autonomousDropOffLocationOptions.BASELINE);
		SmartDashboard.putData("Drop off location Chooser", autonomousDropOffLocationOptionsChooser);

	}
}
