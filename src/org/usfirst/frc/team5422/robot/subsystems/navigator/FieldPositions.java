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
			leftStarting = Pose.createPoseTheta(84, 12, 1.57, 0.01f);
			rightStarting = Pose.createPoseTheta(243, 12, 1.57, 0.01f);
			centerStarting = Pose.createPoseTheta(164, 12, 1.57, 0.01f);

			leftGearPeg = Pose.createPoseTheta(108, 120, Math.PI / 6.0, 0.01f);
			rightGearPeg = Pose.createPoseTheta(220, 120, 5 * Math.PI / 6.0, 0.01f);
			centerGearPeg = Pose.createPoseTheta(164, 83, 1.57, 0.01f);

			leftIntermediateStop = Pose.createPoseTheta(48, 214, 2.08, 0.0f);

			rightIntermediateStop = Pose.createPoseTheta(250, 214, 1.93, 0.0f);

			centerIntermediate1Stop = Pose.createPoseTheta(82, 83, 3.14, 0.0f);
			centerIntermediate2Stop = Pose.createPoseTheta(48, 214, 1.84, 0.0f);

			leftIntermediate1Continue = Pose.createPoseTheta(84, 96, 1.57, 0.01f);
			leftIntermediate2Continue = Pose.createPoseTheta(48, 214, 2.08, 2.44f);

			rightIntermediate1Continue = Pose.createPoseTheta(243, 96, 1.57, 0.01f);
			rightIntermediate2Continue = Pose.createPoseTheta(250, 214, 1.93, 2.44f);

			centerIntermediate1Continue = Pose.createPoseTheta(82, 83, 3.14, 2.44f);
			centerIntermediate2Continue = Pose.createPoseTheta(48, 214, 1.84, 2.44f);

			rightDropOff = Pose.createPoseTheta(36, 582, 2.10, 0.01f);
			left_CenterDropOff = Pose.createPoseTheta(36, 582, 1.60, 0.01f);
		} else {
			leftStarting = Pose.createPoseTheta(width - 84, 12, 1.57, 0.01f);
			rightStarting = Pose.createPoseTheta(width - 243, 12, 1.57, 0.01f);
			centerStarting = Pose.createPoseTheta(width - 164, 12, 1.57, 0.01f);

			leftGearPeg = Pose.createPoseTheta(width - 108, 120, Math.PI / 6.0, 0.01f);
			rightGearPeg = Pose.createPoseTheta(width - 220, 120, 5 * Math.PI / 6.0, 0.01f);
			centerGearPeg = Pose.createPoseTheta(width - 164, 83, 1.57, 0.01f);

			leftIntermediateStop = Pose.createPoseTheta(width - 48, 214, 2.08, 0.0f);

			rightIntermediateStop = Pose.createPoseTheta(width - 250, 214, 1.93, 0.0f);

			centerIntermediate1Stop = Pose.createPoseTheta(width - 82, 83, 3.14, 0.0f);
			centerIntermediate2Stop = Pose.createPoseTheta(width - 48, 214, 1.84, 0.0f);

			leftIntermediate1Continue = Pose.createPoseTheta(width - 84, 108, 1.57, 0.01f);
			leftIntermediate2Continue = Pose.createPoseTheta(width - 48, 214, 2.08, 2.44f);

			rightIntermediate1Continue = Pose.createPoseTheta(width - 243, 108, 1.57, 0.01f);
			rightIntermediate2Continue = Pose.createPoseTheta(width - 250, 214, 1.93, 2.44f);

			centerIntermediate1Continue = Pose.createPoseTheta(width - 82, 83, 3.14, 2.44f);
			centerIntermediate2Continue = Pose.createPoseTheta(width - 48, 214, 1.84, 2.44f);

			rightDropOff = Pose.createPoseTheta(width - 36, 582, 2.10, 0.01f);
			left_CenterDropOff = Pose.createPoseTheta(width - 36, 582, 1.60, 0.01f);
		}
	}
}
