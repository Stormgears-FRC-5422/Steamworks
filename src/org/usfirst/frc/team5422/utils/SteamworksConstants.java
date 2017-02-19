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

	// StormNet IDs
	public static int STORMNET_ULTRASONIC_ARDUINO_ADDRESS = 0;
	public static int NUMBER_OF_STORMNET_ULTRASONIC_SENSORS = 4;

	public static int STORMNET_IR_ARDUINO_ADDRESS = 0;
	public static int NUMBER_OF_STORMNET_IRSENSOR = 1;
	
	
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
    	LIFTING,
    	EXITING, 
    	UNKNOWN   
    }
    
    // Field width in inches
	public static final int FIELD_WIDTH_IN = 327;
	
	// Vision constants
	public static final int RINGLIGHT_PORT = 13;	
	
}
