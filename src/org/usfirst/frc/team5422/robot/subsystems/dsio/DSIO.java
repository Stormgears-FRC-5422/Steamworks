package org.usfirst.frc.team5422.robot.subsystems.dsio;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
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
	public Joystick joystick;
	public Joystick buttonBoard;
	public SendableChooser<alliances> allianceChooser;
	public SendableChooser<autonomousGearPlacementOptions> autonomousGearPlacementOptionsChooser;
	public SendableChooser<autonomousDropOffLocationOptions> autonomousDropOffLocationOptionsChooser;

	JoystickButton bigBlue, smallBlue, greenSwitch, orangeSwitch, redSwitch;

	shooterMode robotShooterMode = shooterMode.MANUAL;

	public DSIO(int joystickUsbChannel, int buttonBoardUsbChannel) {
		// Initialize joystick and buttons
		joystick = new Joystick(joystickUsbChannel);
		buttonBoard = new Joystick(buttonBoardUsbChannel);

		bigBlue = new JoystickButton(buttonBoard, ButtonIds.BIG_BLUE_BUTTON_ID);
		smallBlue = new JoystickButton(buttonBoard, ButtonIds.SMALL_BLUE_BUTTON_ID);
		greenSwitch = new JoystickButton(buttonBoard, ButtonIds.GREEN_SWITCH_ID);
		orangeSwitch = new JoystickButton(buttonBoard, ButtonIds.ORANGE_SWITCH_ID);
		redSwitch = new JoystickButton(buttonBoard, ButtonIds.RED_SWITCH_ID);

		initializeChoosers();

		// Assign commands to pushable buttons

		// Big Blue Button
		//		bigBlue.whenPressed(new ShootCommand(3, robotShooterMode));

	}

	public void checkSwitches() {
		//System.out.println("check switch...");	
		//Error Check for both switches
		if (buttonBoard.getRawButton(ButtonIds.RED_SWITCH_ID) && buttonBoard.getRawButton(ButtonIds.ORANGE_SWITCH_ID))
			Robot.climberIntakeSubsystem.stop();

		//	System.out.println("in check switches method...");
		// RED SWITCH (Climb when in ON position)
		if (buttonBoard.getRawButton(ButtonIds.RED_SWITCH_ID)) {
			//System.out.println("RED SWITCH Pressed..." + getSliderValueClimber());
			Robot.climberIntakeSubsystem.climb(getSliderValueClimber());
			//	Robot.climberIntakeSubsystem.climb(1);
		}
		// ORANGE SWITCH
		else if (buttonBoard.getRawButton(ButtonIds.ORANGE_SWITCH_ID))
			//System.out.println("ORANGE SWITCH Pressed...");
			Robot.climberIntakeSubsystem.takeIn();
		else
			Robot.climberIntakeSubsystem.stop();

		// GREEN SWITCH
		if (buttonBoard.getRawButton(ButtonIds.GREEN_SWITCH_ID))
			//System.out.println("GREEN SWITCH Pressed...");
			robotShooterMode = shooterMode.AUTONOMOUS;
		else
			robotShooterMode = shooterMode.MANUAL;


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
		return (-1 * (joystick.getThrottle() - 1) / 2);
	}

	public Joystick getJoystick() {
		return joystick;
	}

	private void initializeChoosers() {

		allianceChooser = new SendableChooser<>();
		allianceChooser.addDefault("Red Alliance (Boiler to the right)", alliances.RED);
		allianceChooser.addObject("Blue Alliance (Boiler to the left)", alliances.BLUE);
		SmartDashboard.putData("Alliance Chooser", allianceChooser);

		autonomousGearPlacementOptionsChooser = new SendableChooser<>();
		autonomousGearPlacementOptionsChooser.addObject("Place Gear Left", autonomousGearPlacementOptions.PLACE_GEAR_LEFT_AIRSHIP);
		autonomousGearPlacementOptionsChooser.addDefault("Place Gear Center", autonomousGearPlacementOptions.PLACE_GEAR_CENTER_AIRSHIP);
		autonomousGearPlacementOptionsChooser.addObject("Place Gear Right", autonomousGearPlacementOptions.PLACE_GEAR_RIGHT_AIRSHIP);
		autonomousGearPlacementOptionsChooser.addObject("Not Moving in Autonomous", autonomousGearPlacementOptions.NONE);
		SmartDashboard.putData("Autonomous Gear Placement Chooser", autonomousGearPlacementOptionsChooser);

		autonomousDropOffLocationOptionsChooser = new SendableChooser<>();
		autonomousDropOffLocationOptionsChooser.addDefault("Drop Off at Gear Pickup", autonomousDropOffLocationOptions.GEAR_PICKUP);
		autonomousDropOffLocationOptionsChooser.addObject("Drop Off at Baseline", autonomousDropOffLocationOptions.BASELINE);
		SmartDashboard.putData("Drop off location Chooser", autonomousDropOffLocationOptionsChooser);

	}
}
