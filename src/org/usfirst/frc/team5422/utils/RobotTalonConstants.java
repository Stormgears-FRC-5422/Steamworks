package org.usfirst.frc.team5422.utils;

public class RobotTalonConstants extends SteamworksConstants{
	public static int NUM_DRIVE_TALONS = 4;

	// Common array indices - these must remain 0 - 3 (NUM_DRIVE_TALONS - 1)
	// Note that each of these is also the index into the DRIVE_ID array above. If you want to change
	// a drive ID, do so above, not by changing these numbers.
	public static int DRIVE_TALON_LEFT_FRONT = 0;
	public static int DRIVE_TALON_RIGHT_FRONT  = 1;
	public static int DRIVE_TALON_LEFT_REAR = 2;
	public static int DRIVE_TALON_RIGHT_REAR  = 3;
	
    // TALON IDS GO HERE
	// Channels (IDs) for the CANTalon wheels
	public static int[] DRIVE_IDS = {0, 1, 2, 3};
    public static final int CLIMBER_INTAKE_TALON_ID = 4;
    public static final int SECONDARY_INTAKE_TALON_ID = 5;
    public static final int SHOOTER_TALON_ID = 6;
    public static final int SHOOTER_RELAY_ID = 0;

	
	//Motion Profile PID Values for Clone Robot
	public static double CLONEBOT_MOTIONPROFILE_F = 0;
	public static double CLONEBOT_MOTIONPROFILE_P = 0.16;
	public static double CLONEBOT_MOTIONPROFILE_I = 0.0001;
	public static double CLONEBOT_MOTIONPROFILE_D = 10.24;
	public static int CLONEBOT_MOTIONPROFILE_IZONE = 0;
	

	//Motion Profile PID Values for Real Robot
	public static double REALBOT_MOTIONPROFILE_F = 0;
	public static double REALBOT_MOTIONPROFILE_P = 0.16;
	public static double REALBOT_MOTIONPROFILE_I = 0.0001;
	public static double REALBOT_MOTIONPROFILE_D = 10.24;
	public static int REALBOT_MOTIONPROFILE_IZONE = 0;
	
	// CLONEBOT Velocity PID Values
	public static double CLONEBOT_VELOCITY_F = 0.16; //1023.0/6700.0  //1.705;
	public static double CLONEBOT_VELOCITY_P = 0.08;
	public static double CLONEBOT_VELOCITY_I = 0.0002;
	public static double CLONEBOT_VELOCITY_D = 10.24;
	public static int CLONEBOT_VELOCITY_IZONE = 1500;

	// REALBOT Velocity PID Values
	public static double REALBOT_VELOCITY_F = 0.16; //1023.0/6700.0  //1.705;
	public static double REALBOT_VELOCITY_P = 0.08;
	public static double REALBOT_VELOCITY_I = 0.0002;
	public static double REALBOT_VELOCITY_D = 10.24;
	public static int REALBOT_VELOCITY_IZONE = 1500;

	// CLONEBOT Position PID Values
	public static double CLONEBOT_POSITION_F = 0; //1023.0/6700.0  //1.705;
	public static double CLONEBOT_POSITION_P = 0.64;
	public static double CLONEBOT_POSITION_I = 0.001;
	public static double CLONEBOT_POSITION_D = 2.56;
	public static int CLONEBOT_POSITION_IZONE = 1000;

	// REALBOT Position PID Values
	public static double REALBOT_POSITION_F = 0; //1023.0/6700.0  //1.705;
	public static double REALBOT_POSITION_P = 0.64;
	public static double REALBOT_POSITION_I = 0.001;
	public static double REALBOT_POSITION_D = 2.56;
	public static int REALBOT_POSITION_IZONE = 1000;	
	
	public enum RobotDriveProfile {
		MOTIONPROFILE,
		VELOCITY,
		PERCENTVBUS
	}

}
