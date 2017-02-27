package org.usfirst.frc.team5422.robot.subsystems.navigator;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import org.usfirst.frc.team5422.robot.subsystems.navigator.motionprofile.MotionManager;
import org.usfirst.frc.team5422.robot.subsystems.navigator.motionprofile.TrapezoidalProfile;
import org.usfirst.frc.team5422.utils.HardwareConstants;
import org.usfirst.frc.team5422.utils.NetworkConstants;
import org.usfirst.frc.team5422.utils.RegisteredNotifier;

import java.util.ArrayList;

public class Navigator extends Subsystem {

	private static NetworkTable networkTable;

	private static RegisteredNotifier splineFollowThreadNotifier;

	private static Navigator instance;

	private static Drive mecanumDrive;

	private static boolean _isRotating;

	private static boolean _isMovingStraight;

	public static MotionManager motionManager;


	public static double inchesToMeters(double inches) {
		return inches * 2.54 / 100.0;
	}

	private Navigator() {

		networkTable = NetworkTable.getTable(NetworkConstants.GLOBAL_MAPPING);

		//using Stormgears CloneBot Mecanum Drive
		mecanumDrive = new CloneBotMecanumDrive();

		motionManager = new MotionManager(CloneBotMecanumDrive.talons);

		SplineFollowThread.setMotionManager(motionManager);

		//using Stormgears CloneBot Mecanum Drive
		//mecanumDrive = new RealBotMecanumDrive();

		//to test using WPI Mecanum Drive
		//mecanumDrive = new WPIMecanumDrive();
	}

	public static boolean isRotating() {

		return _isRotating;
	}

	public static boolean isMovingStraight() {

		return _isMovingStraight;
	}

	public static boolean isMoving() {
		return _isMovingStraight || _isRotating || SplineFollowThread.isFollowingSpline();
	}

	public static Drive getMecanumDrive() {
		return mecanumDrive;
	}

	public static Navigator getInstance() {

		if (instance == null) {
			instance = new Navigator();
		}
		return instance;
	}

	public static void driveSplineInches(Pose[] poses) {

		double k = 2.54 / 100.0;

		for (Pose pose : poses) {
			pose.x *= k;
			pose.y *= k;
			pose.v_x *= k;
			pose.v_y *= k;
		}

		driveSplineMeters(poses);
	}

	public static void driveSplineInches(ArrayList<Pose> poses) {
		double k = 2.54 / 100.0;

		for (Pose pose : poses) {
			pose.x *= k;
			pose.y *= k;
			pose.v_x *= k;
			pose.v_y *= k;
		}

		driveSplineMeters(poses);
	}

	public static void driveSplineInches(Spline spline) {
		double k = 2.54 / 100.0;

		for (int i = 0; i < spline.getNumPoses(); i++) {

			Pose pose = spline.poses.get(i);

			spline.updatePose(i, new Pose(pose.x * k, pose.y * k, pose.v_x * k, pose.v_y * k));
		}

		driveSplineMeters(spline);
	}

	public synchronized static void driveSplineMeters(Pose[] poses) {//meters

		Spline spline = new Spline(poses);

		driveSplineMeters(spline);
	}

	//driveSpline takes arraylist or poses, array of poses, or spline

	public synchronized static void driveSplineMeters(ArrayList<Pose> poses) {//meters

		Spline spline = new Spline(poses);

		driveSplineMeters(spline);
	}

	public synchronized static void driveSplineMeters(Spline spline) {//meters

		try {
			if (isMoving()) {
				throw new Exception("cannot call two maneuvers at once!");
			} else {
				SplineFollowThread.loadInitialSpline(spline);
				splineFollowThreadNotifier = new RegisteredNotifier(SplineFollowThread.getInstance(), "SplineFollowThread");
				splineFollowThreadNotifier.startPeriodic(0.01);

				while (SplineFollowThread.isFollowingSpline()) {
					//System.out.println("waiting for spline to finish...");
					Timer.delay(0.01);
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	//both trap wrappers will use this utility
	public synchronized static void pushToMotionManagerTrapMeters(double x, double y) {//meters

		//autogenerates trap profile
		double rotations = Math.sqrt(x * x + y * y) / (2 * Math.PI * HardwareConstants.WHEEL_RADIUS);
		double theta = Math.atan2(y, x);

		theta %= 2 * Math.PI;

		double[][] profile = TrapezoidalProfile.getTrapezoidZero(rotations, 300, theta, 0);
		motionManager.pushProfile(profile, false, true); //waits for previous profile and is last profile
		motionManager.startProfile();
	}

	public static void driveStraightRelativeInches(double x, double y) {
		System.out.println("In Drive Straight Relative inches");
		driveStraightRelativeMeters(x * 2.54 / 100.0, y * 2.54 / 100.0);
	}

	public static void driveStraightAbsoluteInches(double x, double y) {
		driveStraightAbsoluteMeters(x * 2.54 / 100, y * 2.54 / 100);
	}

	public synchronized static void driveStraightRelativeMeters(double x, double y) {//meters
		try {
			if (isMoving()) {

				throw new Exception("cannot call two maneuvers at once!");
			} else {


				_isMovingStraight = true;

				pushToMotionManagerTrapMeters(x, y);

				//TODO:: poll aditya's function to find out when trap ends

				_isMovingStraight = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized static void driveStraightAbsoluteMeters(double field_x, double field_y) {//meters
		try {
			if (isMoving()) {

				throw new Exception("cannot call two maneuvers at once!");
			} else {

				double robot_x = networkTable.getNumber(NetworkConstants.GP_X, -1);
				double robot_y = networkTable.getNumber(NetworkConstants.GP_Y, -1);

				_isMovingStraight = true;

				double rel_x = field_x - robot_x;
				double rel_y = field_y - robot_y;

				pushToMotionManagerTrapMeters(rel_x, rel_y);

				//TODO:: poll aditya's function to find out when trap ends

				_isMovingStraight = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized static void rotateAbsolute(double fieldTheta) {//radians
		try {
			if (isMoving()) {
				throw new Exception("cannot call two maneuvers at once!");
			} else {

				_isRotating = true;

				double robot_theta = networkTable.getNumber(NetworkConstants.GP_THETA, -1);

				double turnBy = fieldTheta - robot_theta;

				//normalize difference to -PI to +PI

				turnBy = (turnBy % 2 * Math.PI);
				if (turnBy > Math.PI) {
					turnBy -= 2 * Math.PI;
				}

				motionManager.pushTurn(turnBy, true, true);

				//TODO:: poll aditya's function to find out when turning ends

				_isRotating = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized static void rotateRelative(double theta) {//radians
		try {
			if (isMoving()) {
				throw new Exception("cannot call two maneuvers at once!");
			} else {

				_isRotating = true;

				//normalize difference to -PI to +PI

				theta = (theta % 2 * Math.PI);
				if (theta > Math.PI) {
					theta -= 2 * Math.PI;
				}

				motionManager.pushTurn(theta, true, true);

				//TODO:: poll aditya's function to find out when turning ends

				_isRotating = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}
}
