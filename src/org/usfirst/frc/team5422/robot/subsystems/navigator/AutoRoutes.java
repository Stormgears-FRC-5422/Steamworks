package org.usfirst.frc.team5422.robot.subsystems.navigator;

import org.usfirst.frc.team5422.utils.SteamworksConstants.alliances;

import java.util.ArrayList;

/**
 * Routes for robot in AUTONOMOUS ONLY!!!
 */
public class AutoRoutes {
	public static ArrayList<Pose> leftStartToGear = new ArrayList<>();
	public static ArrayList<Pose> leftGearToBaseline = new ArrayList<>();
	public static ArrayList<Pose> leftGearToGearPickup = new ArrayList<>();

	public static ArrayList<Pose> rightStartToGear = new ArrayList<>();
	public static ArrayList<Pose> rightGearToBaseline = new ArrayList<>();
	public static ArrayList<Pose> rightGearToGearPickup = new ArrayList<>();

	public static ArrayList<Pose> centerStartToGear = new ArrayList<>();
	public static ArrayList<Pose> centerGearToBaseline = new ArrayList<>();
	public static ArrayList<Pose> centerGearToGearPickup = new ArrayList<>();

	// Private constructor to prevent instantiation
	private AutoRoutes() {

	}

	public static void initialize(alliances alliance) {
		if (alliance == alliances.BLUE) {
			leftStartToGear.add(0, Pose.createCopy(FieldPositions.rightStarting));
			leftStartToGear.add(1, Pose.createCopy(FieldPositions.rightIntermediate1Continue));
			leftStartToGear.add(2, Pose.createCopy(FieldPositions.rightGearPeg));

			leftGearToBaseline.add(0, Pose.createCopy(FieldPositions.rightGearPeg));
			leftGearToBaseline.add(1, Pose.createCopy(FieldPositions.rightIntermediateStop));

			leftGearToGearPickup.add(0, Pose.createCopy(FieldPositions.rightGearPeg));
			leftGearToGearPickup.add(1, Pose.createCopy(FieldPositions.rightIntermediate2Continue));
			leftGearToGearPickup.add(2, Pose.createCopy(FieldPositions.rightDropOff));


			rightStartToGear.add(0, Pose.createCopy(FieldPositions.leftStarting));
			rightStartToGear.add(1, Pose.createCopy(FieldPositions.leftIntermediate1Continue));
			rightStartToGear.add(2, Pose.createCopy(FieldPositions.leftGearPeg));

			rightGearToBaseline.add(0, Pose.createCopy(FieldPositions.leftGearPeg));
			rightGearToBaseline.add(1, Pose.createCopy(FieldPositions.leftIntermediateStop));

			rightGearToGearPickup.add(0, Pose.createCopy(FieldPositions.leftGearPeg));
			rightGearToGearPickup.add(1, Pose.createCopy(FieldPositions.leftIntermediate2Continue));
			rightGearToGearPickup.add(2, Pose.createCopy(FieldPositions.left_CenterDropOff));


			centerStartToGear.add(0, Pose.createCopy(FieldPositions.centerStarting));
			centerStartToGear.add(1, Pose.createCopy(FieldPositions.centerGearPeg));

			centerGearToBaseline.add(0, Pose.createCopy(FieldPositions.centerGearPeg));
			centerGearToBaseline.add(1, Pose.createCopy(FieldPositions.centerIntermediate1Continue));
			centerGearToBaseline.add(2, Pose.createCopy(FieldPositions.centerIntermediate2Stop));

			centerGearToGearPickup.add(0, Pose.createCopy(FieldPositions.centerGearPeg));
			centerGearToGearPickup.add(1, Pose.createCopy(FieldPositions.centerIntermediate1Continue));
			centerGearToGearPickup.add(2, Pose.createCopy(FieldPositions.centerIntermediate2Continue));
			centerGearToGearPickup.add(3, Pose.createCopy(FieldPositions.left_CenterDropOff));
		} else {
			leftStartToGear.add(0, Pose.createCopy(FieldPositions.leftStarting));
			leftStartToGear.add(1, Pose.createCopy(FieldPositions.leftIntermediate1Continue));
			leftStartToGear.add(2, Pose.createCopy(FieldPositions.leftGearPeg));

			leftGearToBaseline.add(0, Pose.createCopy(FieldPositions.leftGearPeg));
			leftGearToBaseline.add(1, Pose.createCopy(FieldPositions.leftIntermediateStop));

			leftGearToGearPickup.add(0, Pose.createCopy(FieldPositions.leftGearPeg));
			leftGearToGearPickup.add(1, Pose.createCopy(FieldPositions.leftIntermediate2Continue));
			leftGearToGearPickup.add(2, Pose.createCopy(FieldPositions.left_CenterDropOff));


			rightStartToGear.add(0, Pose.createCopy(FieldPositions.rightStarting));
			rightStartToGear.add(1, Pose.createCopy(FieldPositions.rightIntermediate1Continue));
			rightStartToGear.add(2, Pose.createCopy(FieldPositions.rightGearPeg));

			rightGearToBaseline.add(0, Pose.createCopy(FieldPositions.rightGearPeg));
			rightGearToBaseline.add(1, Pose.createCopy(FieldPositions.rightIntermediateStop));

			rightGearToGearPickup.add(0, Pose.createCopy(FieldPositions.rightGearPeg));
			rightGearToGearPickup.add(1, Pose.createCopy(FieldPositions.rightIntermediate2Continue));
			rightGearToGearPickup.add(2, Pose.createCopy(FieldPositions.rightDropOff));


			centerStartToGear.add(0, Pose.createCopy(FieldPositions.centerStarting));
			centerStartToGear.add(1, Pose.createCopy(FieldPositions.centerGearPeg));

			centerGearToBaseline.add(0, Pose.createCopy(FieldPositions.centerGearPeg));
			centerGearToBaseline.add(1, Pose.createCopy(FieldPositions.centerIntermediate1Continue));
			centerGearToBaseline.add(2, Pose.createCopy(FieldPositions.centerIntermediate2Stop));

			centerGearToGearPickup.add(0, Pose.createCopy(FieldPositions.centerGearPeg));
			centerGearToGearPickup.add(1, Pose.createCopy(FieldPositions.centerIntermediate1Continue));
			centerGearToGearPickup.add(2, Pose.createCopy(FieldPositions.centerIntermediate2Continue));
			centerGearToGearPickup.add(3, Pose.createCopy(FieldPositions.left_CenterDropOff));
		}

	}
}