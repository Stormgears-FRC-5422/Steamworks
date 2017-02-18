package org.usfirst.frc.team5422.robot.subsystems.dsio;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team5422.robot.Robot;
import org.usfirst.frc.team5422.robot.commands.ShootCommand;
import org.usfirst.frc.team5422.utils.SteamworksConstants.autonomousModeOptions;
import org.usfirst.frc.team5422.utils.SteamworksConstants.intake_motor_mode;
import org.usfirst.frc.team5422.utils.SteamworksConstants.shooter_mode;

/**
 * Driver Station Input Output (D.S.I.O.)
 * Handles everything related to the button board and joystick
 * If you want to use a button, YOU MUST do it through here; ask Michael to add it
 */
public class DSIO {
    public static Joystick joystick;
    public static Joystick buttonBoard;
    public static SendableChooser<autonomousModeOptions> autonomousModeChooser;

	JoystickButton bigBlue, smallBlue, greenSwitch, orangeSwitch, redSwitch;

    intake_motor_mode robotIntakeMotorMode = intake_motor_mode.INTAKE;
    shooter_mode robotShooterMode = shooter_mode.MANUAL;

    public DSIO(int joystickUsbChannel, int buttonBoardUsbChannel) {
        // Initialize joystick and buttons
        joystick = new Joystick(joystickUsbChannel);
        buttonBoard = new Joystick(buttonBoardUsbChannel);
        
        autonomousModeChooser = new SendableChooser<autonomousModeOptions>();
        
        autonomousModeChooser.addObject("Place Gear Left", autonomousModeOptions.PLACE_GEAR_LEFT_AIRSHIP);
        autonomousModeChooser.addDefault("Place Gear Center", autonomousModeOptions.PLACE_GEAR_CENTER_AIRSHIP);
        autonomousModeChooser.addObject("Place Gear Right", autonomousModeOptions.PLACE_GEAR_RIGHT_AIRSHIP);
        autonomousModeChooser.addObject("Cross the Line", autonomousModeOptions.JUST_CROSS_LINE);
        autonomousModeChooser.addObject("Not Moving in Autonomous", autonomousModeOptions.NONE);
        SmartDashboard.putData("Autonomous Mode Chooser", autonomousModeChooser);

		bigBlue = new JoystickButton(buttonBoard, ButtonIds.BIG_BLUE_BUTTON_ID);
		smallBlue = new JoystickButton(buttonBoard, ButtonIds.SMALL_BLUE_BUTTON_ID);
		greenSwitch = new JoystickButton(buttonBoard, ButtonIds.GREEN_SWITCH_ID);
		orangeSwitch = new JoystickButton(buttonBoard,ButtonIds.ORANGE_SWITCH_ID);
		redSwitch = new JoystickButton(buttonBoard,ButtonIds.RED_SWITCH_ID);

        // Assign commands to pushable buttons

		//Temporarily commented out this bigblue whenpressed - shooter until the code is all aligned
		
        // Big Blue Button
//        bigBlue.whenPressed(new ShootCommand(3, robotShooterMode));

    }

	public void checkSwitches() {
		System.out.println("in check switches method...");
		// RED SWITCH (Climb when in ON position)
		if (buttonBoard.getRawButton(ButtonIds.RED_SWITCH_ID))
			//System.out.println("RED SWITCH Pressed...");
			Robot.climberIntakeSubsystem.climb(getSliderValueClimber());
		else
			Robot.climberIntakeSubsystem.stop();

		
		// GREEN SWITCH
		if (buttonBoard.getRawButton(ButtonIds.GREEN_SWITCH_ID))
			//System.out.println("GREEN SWITCH Pressed...");
			robotShooterMode = shooter_mode.AUTONOMOUS;
		else
			robotShooterMode = shooter_mode.MANUAL;


		// ORANGE SWITCH
		if (buttonBoard.getRawButton(ButtonIds.ORANGE_SWITCH_ID))
			//System.out.println("ORANGE SWITCH Pressed...");
			Robot.climberIntakeSubsystem.takeIn();
		else
			Robot.climberIntakeSubsystem.stop();


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

    public Joystick getJoystick() {
		return joystick;
	}

	public void setJoystick(Joystick joystick) {
		this.joystick = joystick;
	}

	public Joystick getButtonBoard() {
		return buttonBoard;
	}

	public void setButtonBoard(Joystick buttonBoard) {
		this.buttonBoard = buttonBoard;
	}
	    
}
