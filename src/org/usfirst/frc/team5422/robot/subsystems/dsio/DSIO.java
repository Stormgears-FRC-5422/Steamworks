package org.usfirst.frc.team5422.robot.subsystems.dsio;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import org.usfirst.frc.team5422.robot.commands.ShootCommand;
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

    JoystickButton bigBlue, smallBlue, redSwitch, greenSwitch;

    intake_motor_mode robotIntakeMotorMode = intake_motor_mode.INTAKE;
    shooter_mode robotShooterMode = shooter_mode.MANUAL;

    public DSIO(int joystickUsbChannel, int buttonBoardUsbChannel) {
        // Initialize joystick and buttons

        joystick = new Joystick(joystickUsbChannel);
        buttonBoard = new Joystick(buttonBoardUsbChannel);

        bigBlue = new JoystickButton(buttonBoard, ButtonIds.BIG_BLUE);
        smallBlue = new JoystickButton(buttonBoard, ButtonIds.SMALL_BLUE);
        redSwitch = new JoystickButton(buttonBoard, ButtonIds.RED_SWITCH);
        greenSwitch = new JoystickButton(buttonBoard, ButtonIds.GREEN_SWITCH);

        // Assign commands to pushable buttons

        // Big Blue Button
        bigBlue.whenPressed(new ShootCommand(3, robotShooterMode));

        // TODO: add the rest of the buttons we are using

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
