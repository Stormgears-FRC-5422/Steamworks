package org.usfirst.frc.team5422.utils;

public class RobotDriveConstants extends SteamworksConstants{
	public static int DRIVE_TALON_LEFT_FRONT = 0;
	public static int DRIVE_TALON_RIGHT_FRONT  = 1;
	public static int DRIVE_TALON_LEFT_REAR = 2;
	public static int DRIVE_TALON_RIGHT_REAR  = 3;
	
	public static int NUM_DRIVE_TALONS = 4;

	// Channels for the CANTalon wheels
	public static final int kFrontLeftChannel = 0;
	public static final int kFrontRightChannel = 1;
	public static final int kRearLeftChannel = 2;
	public static final int kRearRightChannel = 3;
	
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
