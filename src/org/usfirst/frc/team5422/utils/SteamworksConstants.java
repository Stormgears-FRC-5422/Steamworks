package org.usfirst.frc.team5422.utils;

public class SteamworksConstants
{
    // USB CHANNELS
    public static final int JOYSTICK_USB_CHANNEL = 0;
    public static final int BUTTON_BOARD_USB_CHANNEL = 1;
    
	// StormNet IDs
	public static int STORMNET_ULTRASONIC_ARDUINO_ADDRESS = 8;
	public static int NUMBER_OF_STORMNET_ULTRASONIC_SENSORS = 4; // most are currently ignored

	public static int STORMNET_IR_ARDUINO_ADDRESS = 11;
	public static int NUMBER_OF_STORMNET_IRSENSOR = 1;
	
	public static int STORMNET_LIGHTS_ARDUINO_ADDRESS = 13;
	
	public static int ROBOT_ULTRASONIC_SEPARATION_IN = 24;
	public static int FRAME_WIDTH = 320;//in pixels
	public static int FRAME_HEIGHT = 240;//in pixels

	// Gear Manipulator Constants
	public static final int FLAPS_NEUTRAL = 0;
	public static final int FLAPS_RECEIVING = 1;
	public static final int FLAPS_DISPENSE = 2;
	public static final double FLAPS_NEUTRAL_POS = 0.69;
	public static final double FLAPS_RECEIVING_POS = 1.0;
	public static final double FLAPS_DISPENSE_POS = 0.32;
	public static final int LEFT_FLAP_CHANNEL = 0;
	public static final int RIGHT_FLAP_CHANNEL = 1;


	
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

	// Alliance options
	public enum flapPositions{
		NEUTRAL(0), RECEIVING(1), DISPENSE(2);
		
		private int pos;
		flapPositions(int positions) {
			pos = positions;
		}
		
		public int getPosition() {
			return pos;
		}
		
    	@Override
		public String toString() {
			switch(this) {
			case NEUTRAL: return "Neutral";
			case RECEIVING: return "Receiving";
			case DISPENSE: return "Dispense";
			default: return "Unknown";
			}
    	}

		public int toInt() {
			switch(this) {
			case NEUTRAL: return SteamworksConstants.FLAPS_NEUTRAL;
			case RECEIVING: return SteamworksConstants.FLAPS_RECEIVING;
			case DISPENSE: return SteamworksConstants.FLAPS_DISPENSE;
			default: return SteamworksConstants.FLAPS_NEUTRAL;
			}
    	}
	}

	// Autonomous gear placement options
	public enum autonomousGearPlacementOptions {
		PLACE_GEAR_LEFT_AIRSHIP,
		PLACE_GEAR_RIGHT_AIRSHIP,
		PLACE_GEAR_CENTER_AIRSHIP,
		HOPPER_AUTONOMOUS,
		NONE;
		
    	@Override
		public String toString() {
			switch(this) {
			case PLACE_GEAR_LEFT_AIRSHIP: return "Place gear left of airship";
			case PLACE_GEAR_RIGHT_AIRSHIP: return "Place gear right of airship";
			case PLACE_GEAR_CENTER_AIRSHIP: return "Place gear center airship";
			case HOPPER_AUTONOMOUS: return "Hopper autonomous";
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
