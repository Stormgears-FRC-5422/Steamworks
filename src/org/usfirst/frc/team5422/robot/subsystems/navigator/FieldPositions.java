package org.usfirst.frc.team5422.robot.subsystems.navigator;

import org.usfirst.frc.team5422.utils.SteamworksConstants;
import org.usfirst.frc.team5422.utils.SteamworksConstants.alliances;

/**
 * Field Positions (Pose objects)
 */
public class FieldPositions {
	public static Pose leftStarting;
	public static Pose rightStarting;
	public static Pose centerStarting;

	public static Pose leftGearPeg;
	public static Pose rightGearPeg;
	public static Pose centerGearPeg;

	public static Pose leftIntermediateStop;

	public static Pose rightIntermediateStop;

	public static Pose centerIntermediate1Stop;
	public static Pose centerIntermediate2Stop;

	public static Pose leftIntermediate1Continue;
	public static Pose leftIntermediate2Continue;

	public static Pose rightIntermediate1Continue;
	public static Pose rightIntermediate2Continue;

	public static Pose centerIntermediate1Continue;
	public static Pose centerIntermediate2Continue;

	public static Pose rightDropOff;
	public static Pose left_CenterDropOff;

	// Private constructor to prevent instantiation
	private FieldPositions() {

	}

	public static void initialize(alliances alliance) {
		int width = SteamworksConstants.FIELD_WIDTH_IN;

		if (alliance == alliances.RED) {
			leftStarting = new Pose(84, 12, 1.57, 0.01f);
			rightStarting = new Pose(243, 12, 1.57, 0.01f);
			centerStarting = new Pose(164, 12, 1.57, 0.01f);

			leftGearPeg = new Pose(108, 120, Math.PI/6.0, 0.01f);
			rightGearPeg = new Pose(220, 120, 5*Math.PI/6.0, 0.01f);
			centerGearPeg = new Pose(164, 83, 1.57, 0.01f);

			leftIntermediateStop = new Pose(48, 214, 2.08, 0.0f);

			rightIntermediateStop = new Pose(250, 214, 1.93, 0.0f);

			centerIntermediate1Stop = new Pose(82, 83, 3.14, 0.0f);
			centerIntermediate2Stop = new Pose(48, 214, 1.84, 0.0f);

			leftIntermediate1Continue = new Pose(84, 96, 1.57, 0.01f);
			leftIntermediate2Continue = new Pose(48, 214, 2.08, 2.44f);

			rightIntermediate1Continue = new Pose(243, 96, 1.57, 0.01f);
			rightIntermediate2Continue = new Pose(250, 214, 1.93, 2.44f);

			centerIntermediate1Continue = new Pose(82, 83, 3.14, 2.44f);
			centerIntermediate2Continue = new Pose(48, 214, 1.84, 2.44f);

			rightDropOff = new Pose(36, 582, 2.10, 0.01f);
			left_CenterDropOff = new Pose(36, 582, 1.60, 0.01f);
		} else {
			leftStarting = new Pose(width - 84, 12, 1.57, 0.01f);
			rightStarting = new Pose(width - 243, 12, 1.57, 0.01f);
			centerStarting = new Pose(width - 164, 12, 1.57, 0.01f);

			leftGearPeg = new Pose(width - 108, 120, Math.PI/6.0, 0.01f);
			rightGearPeg = new Pose(width - 220, 120, 5*Math.PI/6.0, 0.01f);
			centerGearPeg = new Pose(width - 164, 83, 1.57, 0.01f);

			leftIntermediateStop = new Pose(width - 48, 214, 2.08, 0.0f);

			rightIntermediateStop = new Pose(width - 250, 214, 1.93, 0.0f);

			centerIntermediate1Stop = new Pose(width - 82, 83, 3.14, 0.0f);
			centerIntermediate2Stop = new Pose(width - 48, 214, 1.84, 0.0f);

			leftIntermediate1Continue = new Pose(width - 84, 108, 1.57, 0.01f);
			leftIntermediate2Continue = new Pose(width - 48, 214, 2.08, 2.44f);

			rightIntermediate1Continue = new Pose(width - 243, 108, 1.57, 0.01f);
			rightIntermediate2Continue = new Pose(width - 250, 214, 1.93, 2.44f);

			centerIntermediate1Continue = new Pose(width - 82, 83, 3.14, 2.44f);
			centerIntermediate2Continue = new Pose(width - 48, 214, 1.84, 2.44f);

			rightDropOff = new Pose(width - 36, 582, 2.10, 0.01f);
			left_CenterDropOff = new Pose(width - 36, 582, 1.60, 0.01f);
		}
	}
}