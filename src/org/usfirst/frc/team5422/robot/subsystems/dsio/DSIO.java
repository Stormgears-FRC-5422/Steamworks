package org.usfirst.frc.team5422.robot.subsystems.dsio;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team5422.robot.Robot;
import org.usfirst.frc.team5422.robot.commands.GearFlapCommand;
import org.usfirst.frc.team5422.robot.commands.ReverseImpellerCommand;
import org.usfirst.frc.team5422.robot.commands.ShootCommand;
import org.usfirst.frc.team5422.robot.commands.TurnLightOnOffCommand;
import org.usfirst.frc.team5422.robot.commands.VisionAlignToGearCommand;
import org.usfirst.frc.team5422.utils.ButtonIds;
import org.usfirst.frc.team5422.utils.SteamworksConstants;
import org.usfirst.frc.team5422.utils.SteamworksConstants.alliances;
import org.usfirst.frc.team5422.utils.SteamworksConstants.flapPositions;
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
//	public SendableChooser<flapPositions> flapPositionsChooser;

	JoystickButton bigBlue, greenSwitch, orangeSwitch, redSwitch, 
	smallBlue, smallGreen, smallYellow, smallBlack, smallWhite, smallRed;

	shooterMode robotShooterMode = shooterMode.MANUAL;

	public DSIO(int joystickUsbChannel, int buttonBoardUsbChannel) {
		// Initialize joystick and buttons
		joystick = new Joystick(joystickUsbChannel);
		buttonBoard = new Joystick(buttonBoardUsbChannel);

		bigBlue = new JoystickButton(buttonBoard, ButtonIds.BIG_BLUE_BUTTON_ID);

		greenSwitch = new JoystickButton(buttonBoard, ButtonIds.GREEN_SWITCH_ID);
		orangeSwitch = new JoystickButton(buttonBoard, ButtonIds.ORANGE_SWITCH_ID);
		redSwitch = new JoystickButton(buttonBoard, ButtonIds.RED_SWITCH_ID);

		smallBlue = new JoystickButton(buttonBoard, ButtonIds.SMALL_BLUE_BUTTON_ID);
		smallGreen = new JoystickButton(buttonBoard, ButtonIds.GREEN_BUTTON_ID);
		smallYellow = new JoystickButton(buttonBoard, ButtonIds.YELLOW_BUTTON_ID);
		smallBlack = new JoystickButton(buttonBoard, ButtonIds.BLACK_BUTTON_ID);
		smallWhite = new JoystickButton(buttonBoard, ButtonIds.WHITE_BUTTON_ID);
		smallRed = new JoystickButton(buttonBoard, ButtonIds.RED_BUTTON_ID);

		initializeChoosers();

		// Assign commands to pushable buttons

		// Big Blue Button
//		bigBlue.whenPressed(new ShootCommand(3, robotShooterMode));

		// Small buttons
		smallBlue.whenPressed(new GearFlapCommand(SteamworksConstants.FLAPS_RECEIVING));
		smallGreen.whenPressed(new GearFlapCommand(SteamworksConstants.FLAPS_NEUTRAL));

		smallYellow.whenPressed(new GearFlapCommand(SteamworksConstants.FLAPS_DISPENSE));
		
		//temporary fix in Hartford Competition 
//		smallWhite.whenPressed(new GearFlapCommand(SteamworksConstants.FLAPS_DISPENSE));
		smallBlack.whenPressed(new TurnLightOnOffCommand());
		//smallWhite.whenPressed(new VisionAlignToGearCommand());
		smallRed.whenPressed(new ReverseImpellerCommand(true));
		smallRed.whenReleased(new ReverseImpellerCommand(false));
		
		// This is special
		greenSwitch.whenPressed(new ShootCommand());
		//Robot.shooterSubsystem.setShootVelocity(55750);

	}

	public void checkSwitches() {
		// Error Check for both switches
		if (buttonBoard.getRawButton(ButtonIds.RED_SWITCH_ID) && buttonBoard.getRawButton(ButtonIds.ORANGE_SWITCH_ID))
			Robot.climberIntakeSubsystem.stop();

		// RED SWITCH (Climb when in ON position)
		if (buttonBoard.getRawButton(ButtonIds.RED_SWITCH_ID)) {
			Robot.climberIntakeSubsystem.climb(getSliderValueClimber());
		}
		// ORANGE SWITCH
		else if (buttonBoard.getRawButton(ButtonIds.ORANGE_SWITCH_ID))
			Robot.climberIntakeSubsystem.takeIn();
		else
			Robot.climberIntakeSubsystem.stop();

		// GREEN SWITCH
		if (!buttonBoard.getRawButton(ButtonIds.GREEN_SWITCH_ID)) {
			Robot.shooterSubsystem.setEnabled(false);			
		}
	}

	public double getManualShooterVelocity() {
		return (joystick.getThrottle() - 1) * 5000 - 46000;
	}

	public double getSliderValueClimber() {

		double climberVelocity = (-1*(joystick.getThrottle() - 1) / 2);


		return climberVelocity;
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
		autonomousGearPlacementOptionsChooser.addObject("Hopper Autonomous", autonomousGearPlacementOptions.HOPPER_AUTONOMOUS);
		autonomousGearPlacementOptionsChooser.addObject("Not Moving in Autonomous", autonomousGearPlacementOptions.NONE);
		SmartDashboard.putData("Autonomous Gear Placement Chooser", autonomousGearPlacementOptionsChooser);

		autonomousDropOffLocationOptionsChooser = new SendableChooser<>();
		autonomousDropOffLocationOptionsChooser.addDefault("Drop Off at Gear Pickup", autonomousDropOffLocationOptions.GEAR_PICKUP);
		autonomousDropOffLocationOptionsChooser.addObject("Drop Off at Baseline", autonomousDropOffLocationOptions.BASELINE);
		SmartDashboard.putData("Drop off location Chooser", autonomousDropOffLocationOptionsChooser);

//		flapPositionsChooser = new SendableChooser<flapPositions>();
//		flapPositionsChooser.addDefault("Flap Neutral Position", flapPositions.NEUTRAL);
//		flapPositionsChooser.addObject("Flap Receive Position", flapPositions.RECEIVING);
//		flapPositionsChooser.addObject("Flap Dispense Position", flapPositions.DISPENSE);
//		SmartDashboard.putData("Flap Position Chooser", flapPositionsChooser);

	}
}
