package org.usfirst.frc.team5422.robot.subsystems.navigator;

/**
 * Field Positions (final Pose objects)
 */
public class FieldPositions {


    // Private constructor to prevent instantiation
    private FieldPositions() {

    }
    public static final Pose leftStarting = new Pose (84, 12, 1.57, 0.01f);
    public static final Pose rightStarting = new Pose(243, 12, 1.57, 0.01f);
    public static final Pose centerStarting = new Pose(164, 12, 1.57, 0.01f);

    public static final Pose leftGearPeg = new Pose(108, 120, 0.46, 0.01f);
    public static final Pose rightGearPeg = new Pose(220, 120, 2.68, 0.01f);
    public static final Pose centerGearPeg = new Pose(164, 83, 1.57, 0.01f);

    public static final Pose leftIntermediate = new Pose(48, 214, 2.08, 2.44f);
    public static final Pose rightIntermediate = new Pose(250, 214, 1.93, 2.44f);
    public static final Pose centerIntermediate1 = new Pose(82, 83, 3.14, 2.44f);
    public static final Pose centerIntermediate2 = new Pose(48, 214, 1.84, 2.44f);

    public static final Pose rightDropOff = new Pose(36, 582, 2.10, 0.01f);
    public static final Pose left_CenterDropOff = new Pose(36, 582, 1.60, 0.01f);

}