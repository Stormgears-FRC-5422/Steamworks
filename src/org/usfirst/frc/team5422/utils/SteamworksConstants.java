package org.usfirst.frc.team5422.utils;

public class SteamworksConstants
{
    // USB CHANNELS
    public static final int JOYSTICK_USB_CHANNEL = 0;
    public static final int BUTTON_BOARD_USB_CHANNEL = 1;
    
    // TALON IDS GO HERE
    public static final int SHOOTER_TALON_ID = 888;   //TODO: Change this to the real talon ID
    public static final int SHOOTER_RELAY_ID = 0;
    public static final int CLIMBER_INTAKE_TALON_ID = 4;
    public static final int SECONDARY_INTAKE_TALON_ID = 66;

	// StormNet IDs
	public static int STORMNET_ULTRASONIC_ARDUINO_ADDRESS = 8;
	public static int NUMBER_OF_STORMNET_ULTRASONIC_SENSORS = 4; // most are currently ignored

	public static int STORMNET_IR_ARDUINO_ADDRESS = 11;
	public static int NUMBER_OF_STORMNET_IRSENSOR = 1;
	
	public static int STORMNET_LIGHTS_ARDUINO_ADDRESS = 13;
	
	public static int ROBOT_ULTRASONIC_SEPARATION_IN = 24;
	public static int FRAME_WIDTH = 320;//in pixels
	public static int FRAME_HEIGHT = 240;//in pixels
	
	// Alliance options
	public enum alliances {
		RED, BLUE;
    	@Override
		public String toString() {
			switch(this) {
			case RED: return "Red";
			case BLUE: return "Blue";
			default: return "Unknown";
			}
    	}
	}

	// Autonomous gear placement options
	public enum autonomousGearPlacementOptions {
		PLACE_GEAR_LEFT_AIRSHIP,
		PLACE_GEAR_RIGHT_AIRSHIP,
		PLACE_GEAR_CENTER_AIRSHIP,
		NONE;
		
    	@Override
		public String toString() {
			switch(this) {
			case PLACE_GEAR_LEFT_AIRSHIP: return "Place gear left of airship";
			case PLACE_GEAR_RIGHT_AIRSHIP: return "Place gear right of airship";
			case PLACE_GEAR_CENTER_AIRSHIP: return "Place gear center airship";
			case NONE: return "None";
			default: return "Unknown";
			}
    	}
	}

	// Autonomous drop off location options
	public enum autonomousDropOffLocationOptions {
		BASELINE, GEAR_PICKUP;

		@Override
		public String toString() {
			switch(this) {
			case BASELINE: return "Baseline";
			case GEAR_PICKUP: return "Gear pickup";
			default: return "Unknown";
			}
    	}
	}

    // SHOOTER MODE
    public enum shooterMode {
        MANUAL, AUTONOMOUS;

    	@Override
		public String toString() {
			switch(this) {
			case AUTONOMOUS: return "Autonomous";
			case MANUAL: return "Manual";
			default: return "Unknown";
			}
    	}
    }
    
	public enum gearState {
		EMPTY, FULL, LIFTING, EXITING, UNKNOWN;
		
		@Override
		public String toString() {
			switch(this) {
			case EMPTY: return "Emtpy Bin";
			case LIFTING: return "Gear Lifting";
			case FULL: return "Full Bin";
			case EXITING: return "Gear Exiting";
			case UNKNOWN:
			default: return "Unknown gearState";
			}
		}	
	}

    public enum RobotModes {
    	AUTONOMOUS, TELEOP;

    	@Override
		public String toString() {
			switch(this) {
			case AUTONOMOUS: return "Autonomous";
			case TELEOP: return "TeleOp";
			default: return "Unknown";
			}
		}	
    }
    
    // Field width in inches
	public static final int FIELD_WIDTH_IN = 327;
	
	// Vision constants
	public static final int RINGLIGHT_PORT = 13;	
	
}
