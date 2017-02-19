package org.usfirst.frc.team5422.utils;

public class SteamworksConstants
{
    // USB CHANNELS
    public static final int JOYSTICK_USB_CHANNEL = 0;
    public static final int BUTTON_BOARD_USB_CHANNEL = 1;
    
    // TALON IDS GO HERE
    public static final int SHOOTER_TALON_ID = 888;   //TODO: Change this to the real talon ID
    public static final int SHOOTER_RELAY_ID = 0;
    public static final int CLIMBER_INTAKE_TALON_ID = 1;

	public static int DRIVE_TALON_LEFT_FRONT = 0;
	public static int DRIVE_TALON_RIGHT_FRONT  = 1;
	public static int DRIVE_TALON_LEFT_BACK = 2;
	public static int DRIVE_TALON_RIGHT_BACK  = 3;

	// Velocity PID Values
	public static double VELOCITY_F = 0.16; //1023.0/6700.0  //1.705;
	public static double VELOCITY_P = 0.08;
	public static double VELOCITY_I = 0.002;
	public static double VELOCITY_D = 10.24;
	public static int VELOCITY_IZONE = 1500;

	// Position PID Values
	public static double POSITION_F = 0; //1023.0/6700.0  //1.705;
	public static double POSITION_P = 0.64;
	public static double POSITION_I = 0.001;
	public static double POSITION_D = 2.56;
	public static int POSITION_IZONE = 1000;

	// StormNet IDs
	public static int STORMNET_ULTRASONIC_ARDUINO_ADDRESS = 0;
	public static int NUMBER_OF_STORMNET_ULTRASONIC_SENSORS = 4;

	// Alliance options
	public enum alliances {
		RED,
		BLUE
	}

	// Autonomous gear placement options
	public enum autonomousGearPlacementOptions {
		PLACE_GEAR_LEFT_AIRSHIP,
		PLACE_GEAR_RIGHT_AIRSHIP,
		PLACE_GEAR_CENTER_AIRSHIP,
		NONE
	}

	// Autonomous drop off location options
	public enum autonomousDropOffLocationOptions {
		BASELINE,
		GEAR_PICKUP
	}

    // SHOOTER MODE
    public enum shooterMode {
        MANUAL,
        AUTONOMOUS
    }
    
    public enum gearStates {
    	FULL,
    	EMPTY,
    	ENTERING,
    	EXITING
    
    }
    // Field width in inches
	public static final int FIELD_WIDTH_IN = 327;
}
