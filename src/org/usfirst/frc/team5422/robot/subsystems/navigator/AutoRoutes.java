package org.usfirst.frc.team5422.robot.subsystems.navigator;

import org.usfirst.frc.team5422.utils.SteamworksConstants.alliances;

import java.util.ArrayList;

/**
 * Routes for robot in AUTONOMOUS ONLY!!!
 */
public class AutoRoutes {
	public static ArrayList<Pose> leftStartToGear = new ArrayList<Pose>();
	public static ArrayList<Pose> leftGearToBaseline = new ArrayList<Pose>();
	public static ArrayList<Pose> leftGearToGearPickup = new ArrayList<Pose>();

	public static ArrayList<Pose> rightStartToGear = new ArrayList<Pose>();
	public static ArrayList<Pose> rightGearToBaseline = new ArrayList<Pose>();
	public static ArrayList<Pose> rightGearToGearPickup = new ArrayList<Pose>();

	public static ArrayList<Pose> centerStartToGear = new ArrayList<Pose>();
	public static ArrayList<Pose> centerGearToBaseline = new ArrayList<Pose>();
	public static ArrayList<Pose> centerGearToGearPickup = new ArrayList<Pose>();

	// Private constructor to prevent instantiation
	private AutoRoutes() {

	}

	public static void initialize(alliances alliance) {
		if (alliance == alliances.BLUE) {
			leftStartToGear.add(0, new Pose(FieldPositions.rightStarting));
			leftStartToGear.add(1, new Pose(FieldPositions.rightGearPeg));

			leftGearToBaseline.add(0, new Pose(FieldPositions.rightGearPeg));
			leftGearToBaseline.add(1, new Pose(FieldPositions.rightIntermediateStop));

			leftGearToGearPickup.add(0, new Pose(FieldPositions.rightGearPeg));
			leftGearToGearPickup.add(1, new Pose(FieldPositions.rightIntermediateContinue));
			leftGearToGearPickup.add(2, new Pose(FieldPositions.rightDropOff));


			rightStartToGear.add(0, new Pose(FieldPositions.leftStarting));
			rightStartToGear.add(1, new Pose(FieldPositions.leftGearPeg));

			rightGearToBaseline.add(0, new Pose(FieldPositions.leftGearPeg));
			rightGearToBaseline.add(1, new Pose(FieldPositions.leftIntermediateStop));

			rightGearToGearPickup.add(0, new Pose(FieldPositions.leftGearPeg));
			rightGearToGearPickup.add(1, new Pose(FieldPositions.leftIntermediateContinue));
			rightGearToGearPickup.add(2, new Pose(FieldPositions.left_CenterDropOff));


			centerStartToGear.add(0, new Pose(FieldPositions.centerStarting));
			centerStartToGear.add(1, new Pose(FieldPositions.centerGearPeg));

			centerGearToBaseline.add(0, new Pose(FieldPositions.centerGearPeg));
			centerGearToBaseline.add(1, new Pose(FieldPositions.centerIntermediate1Continue));
			centerGearToBaseline.add(2, new Pose(FieldPositions.centerIntermediate2Stop));

			centerGearToGearPickup.add(0, new Pose(FieldPositions.centerGearPeg));
			centerGearToGearPickup.add(1, new Pose(FieldPositions.centerIntermediate1Continue));
			centerGearToGearPickup.add(2, new Pose(FieldPositions.centerIntermediate2Continue));
			centerGearToGearPickup.add(3, new Pose(FieldPositions.left_CenterDropOff));
		} else {
			leftStartToGear.add(0, new Pose(FieldPositions.leftStarting));
			leftStartToGear.add(1, new Pose(FieldPositions.leftGearPeg));

			leftGearToBaseline.add(0, new Pose(FieldPositions.leftGearPeg));
			leftGearToBaseline.add(1, new Pose(FieldPositions.leftIntermediateStop));

			leftGearToGearPickup.add(0, new Pose(FieldPositions.leftGearPeg));
			leftGearToGearPickup.add(1, new Pose(FieldPositions.leftIntermediateContinue));
			leftGearToGearPickup.add(2, new Pose(FieldPositions.left_CenterDropOff));


			rightStartToGear.add(0, new Pose(FieldPositions.rightStarting));
			rightStartToGear.add(1, new Pose(FieldPositions.rightGearPeg));

			rightGearToBaseline.add(0, new Pose(FieldPositions.rightGearPeg));
			rightGearToBaseline.add(1, new Pose(FieldPositions.rightIntermediateStop));

			rightGearToGearPickup.add(0, new Pose(FieldPositions.rightGearPeg));
			rightGearToGearPickup.add(1, new Pose(FieldPositions.rightIntermediateContinue));
			rightGearToGearPickup.add(2, new Pose(FieldPositions.rightDropOff));


			centerStartToGear.add(0, new Pose(FieldPositions.centerStarting));
			centerStartToGear.add(1, new Pose(FieldPositions.centerGearPeg));

			centerGearToBaseline.add(0, new Pose(FieldPositions.centerGearPeg));
			centerGearToBaseline.add(1, new Pose(FieldPositions.centerIntermediate1Continue));
			centerGearToBaseline.add(2, new Pose(FieldPositions.centerIntermediate2Stop));

			centerGearToGearPickup.add(0, new Pose(FieldPositions.centerGearPeg));
			centerGearToGearPickup.add(1, new Pose(FieldPositions.centerIntermediate1Continue));
			centerGearToGearPickup.add(2, new Pose(FieldPositions.centerIntermediate2Continue));
			centerGearToGearPickup.add(3, new Pose(FieldPositions.left_CenterDropOff));
		}
		
	}
}
