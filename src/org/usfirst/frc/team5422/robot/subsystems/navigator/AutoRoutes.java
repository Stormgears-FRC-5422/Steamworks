package org.usfirst.frc.team5422.robot.subsystems.navigator;

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

    public static void initialize() {
        leftStartToGear[0] = new Pose(FieldPositions.leftStarting);
        leftStartToGear[1] = new Pose(FieldPositions.leftGearPeg);
        
        leftGearToBaseline[0] = new Pose(FieldPositions.leftGearPeg);
        leftGearToBaseline[1] = new Pose(FieldPositions.leftIntermediateStop);

	    leftGearToGearPickup[0] = new Pose(FieldPositions.leftGearPeg);
	    leftGearToGearPickup[1] = new Pose(FieldPositions.leftIntermediateStop);
	    leftGearToGearPickup[2] = new Pose(FieldPositions.left_CenterDropOff);
        
    }
}
