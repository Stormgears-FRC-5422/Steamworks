package org.usfirst.frc.team5422.utils;

public class SteamworksConstants
{
    // USB CHANNELS
    public static final int JOYSTICK_USB_CHANNEL = 0;
    public static final int BUTTON_BOARD_USB_CHANNEL = 1;

    // INTAKE MOTOR MODE
    public enum intake_motor_mode {
        INTAKE,
        CLIMB
    }

    // SHOOTER MODE
    public enum shooter_mode
    {
        MANUAL,
        AUTONOMOUS
    }

    // TALON IDS GO HERE
    public static final int SHOOTER_TALON_ID = 0;   //TODO: Change this to the real talon ID
    public static final int CLIMBER_INTAKE_TALON_ID = 2;
    
    //SWITCH IDS
	public static final int INTAKE_CLIMBER_RED_SWITCH_ID = 3;
	public static final int INTAKE_ORANGE_SWITCH_ID = 15;

}
