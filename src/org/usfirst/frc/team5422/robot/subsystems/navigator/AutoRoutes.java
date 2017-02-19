package org.usfirst.frc.team5422.robot.subsystems.navigator;

import org.usfirst.frc.team5422.utils.SteamworksConstants.alliances;

/**
 * Routes for robot in AUTONOMOUS ONLY!!!
 */
public class AutoRoutes {
	public static Pose[] leftStartToGear = new Pose[2];
	public static Pose[] leftGearToBaseline = new Pose[2];
	public static Pose[] leftGearToGearPickup = new Pose[3];

	public static Pose[] rightStartToGear = new Pose[2];
	public static Pose[] rightGearToBaseline = new Pose[2];
	public static Pose[] rightGearToGearPickup = new Pose[3];

	public static Pose[] centerStartToGear = new Pose[2];
	public static Pose[] centerGearToBaseline = new Pose[3];
	public static Pose[] centerGearToGearPickup = new Pose[4];

	// Private constructor to prevent instantiation
	private AutoRoutes() {

	}

	public static void initialize(alliances alliance) {
		if (alliance == alliances.BLUE) {
			leftStartToGear[0] = new Pose(FieldPositions.rightStarting);
			leftStartToGear[1] = new Pose(FieldPositions.rightGearPeg);

			leftGearToBaseline[0] = new Pose(FieldPositions.rightGearPeg);
			leftGearToBaseline[1] = new Pose(FieldPositions.rightIntermediateStop);

			leftGearToGearPickup[0] = new Pose(FieldPositions.rightGearPeg);
			leftGearToGearPickup[1] = new Pose(FieldPositions.rightIntermediateContinue);
			leftGearToGearPickup[2] = new Pose(FieldPositions.rightDropOff);


			rightStartToGear[0] = new Pose(FieldPositions.leftStarting);
			rightStartToGear[1] = new Pose(FieldPositions.leftGearPeg);

			rightGearToBaseline[0] = new Pose(FieldPositions.leftGearPeg);
			rightGearToBaseline[1] = new Pose(FieldPositions.leftIntermediateStop);

			rightGearToGearPickup[0] = new Pose(FieldPositions.leftGearPeg);
			rightGearToGearPickup[1] = new Pose(FieldPositions.leftIntermediateContinue);
			rightGearToGearPickup[2] = new Pose(FieldPositions.left_CenterDropOff);


			centerStartToGear[0] = new Pose(FieldPositions.centerStarting);
			centerStartToGear[1] = new Pose(FieldPositions.centerGearPeg);

			centerGearToBaseline[0] = new Pose(FieldPositions.centerGearPeg);
			centerGearToBaseline[1] = new Pose(FieldPositions.centerIntermediate1Continue);
			centerGearToBaseline[2] = new Pose(FieldPositions.centerIntermediate2Stop);

			centerGearToGearPickup[0] = new Pose(FieldPositions.centerGearPeg);
			centerGearToGearPickup[1] = new Pose(FieldPositions.centerIntermediate1Continue);
			centerGearToGearPickup[2] = new Pose(FieldPositions.centerIntermediate2Continue);
			centerGearToGearPickup[3] = new Pose(FieldPositions.left_CenterDropOff);
		} else {
			leftStartToGear[0] = new Pose(FieldPositions.leftStarting);
			leftStartToGear[1] = new Pose(FieldPositions.leftGearPeg);

			leftGearToBaseline[0] = new Pose(FieldPositions.leftGearPeg);
			leftGearToBaseline[1] = new Pose(FieldPositions.leftIntermediateStop);

			leftGearToGearPickup[0] = new Pose(FieldPositions.leftGearPeg);
			leftGearToGearPickup[1] = new Pose(FieldPositions.leftIntermediateContinue);
			leftGearToGearPickup[2] = new Pose(FieldPositions.left_CenterDropOff);


			rightStartToGear[0] = new Pose(FieldPositions.rightStarting);
			rightStartToGear[1] = new Pose(FieldPositions.rightGearPeg);

			rightGearToBaseline[0] = new Pose(FieldPositions.rightGearPeg);
			rightGearToBaseline[1] = new Pose(FieldPositions.rightIntermediateStop);

			rightGearToGearPickup[0] = new Pose(FieldPositions.rightGearPeg);
			rightGearToGearPickup[1] = new Pose(FieldPositions.rightIntermediateContinue);
			rightGearToGearPickup[2] = new Pose(FieldPositions.rightDropOff);


			centerStartToGear[0] = new Pose(FieldPositions.centerStarting);
			centerStartToGear[1] = new Pose(FieldPositions.centerGearPeg);

			centerGearToBaseline[0] = new Pose(FieldPositions.centerGearPeg);
			centerGearToBaseline[1] = new Pose(FieldPositions.centerIntermediate1Continue);
			centerGearToBaseline[2] = new Pose(FieldPositions.centerIntermediate2Stop);

			centerGearToGearPickup[0] = new Pose(FieldPositions.centerGearPeg);
			centerGearToGearPickup[1] = new Pose(FieldPositions.centerIntermediate1Continue);
			centerGearToGearPickup[2] = new Pose(FieldPositions.centerIntermediate2Continue);
			centerGearToGearPickup[3] = new Pose(FieldPositions.left_CenterDropOff);
		}
		
	}
}
