package org.usfirst.frc.team5422.utils;

public class HardwareConstants {
	/*
	 * this is for physical distances of the robot
	 */

	public static final int ENCODER_RESOLUTION = 2048;//?????
	public static final double PI = Math.PI;
	public static final double RADIANS_PER_TICK = 2 * PI / (float) ENCODER_RESOLUTION;
	public static final double WHEEL_RADIUS = 0.479;//meters
	public static final double WHEEL_DIAMETER_INCHES = 6.0;
	public static final double METERS_PER_TICK = 2 * PI * WHEEL_RADIUS / (float) ENCODER_RESOLUTION;
	public static final double ROTATION_CALC_FACTOR = HardwareConstants.WHEEL_DIAMETER_INCHES * Math.PI;
}