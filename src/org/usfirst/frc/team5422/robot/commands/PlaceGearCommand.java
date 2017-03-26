package org.usfirst.frc.team5422.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5422.robot.Robot;
import org.usfirst.frc.team5422.robot.subsystems.navigator.AutoRoutes;
import org.usfirst.frc.team5422.robot.subsystems.navigator.Navigator;
import org.usfirst.frc.team5422.robot.subsystems.navigator.Pose;
import org.usfirst.frc.team5422.robot.subsystems.navigator.motionprofile.MotionManager;
import org.usfirst.frc.team5422.robot.subsystems.navigator.motionprofile.TrapezoidalProfile;
import org.usfirst.frc.team5422.utils.HardwareConstants;
import org.usfirst.frc.team5422.utils.SteamworksConstants.alliances;
import org.usfirst.frc.team5422.utils.SteamworksConstants.autonomousDropOffLocationOptions;
import org.usfirst.frc.team5422.utils.SteamworksConstants.autonomousGearPlacementOptions;

import java.util.ArrayList;

public class PlaceGearCommand extends Command {
	private ArrayList<Pose> routeToGear;
	private autonomousGearPlacementOptions selectedAutonomousGearPlacementLocation;
	private autonomousDropOffLocationOptions selectedAutonomousDropOffLocation;
	private alliances selectedAlliance;
	private MotionManager m;

	public PlaceGearCommand() {
		requires(Robot.navigatorSubsystem);
		requires(Robot.gearManipulatorSubsystem);
	}

	public PlaceGearCommand(alliances selectedAlliance, autonomousGearPlacementOptions selectedAutonomousGearPlacementLocation, autonomousDropOffLocationOptions selectedAutonomousDropOffLocation) {
		requires(Robot.navigatorSubsystem);
		requires(Robot.gearManipulatorSubsystem);

		this.selectedAlliance = selectedAlliance;
		this.selectedAutonomousGearPlacementLocation = selectedAutonomousGearPlacementLocation;
		this.selectedAutonomousDropOffLocation = selectedAutonomousDropOffLocation;
		System.out.println("In PlaceGearCommand Constructor..." +
				" Alliance: " + this.selectedAlliance.toString() +
				" GearPlacement Location: " + this.selectedAutonomousGearPlacementLocation.toString() +
				" Robot DropOff Location: " + this.selectedAutonomousDropOffLocation.toString());
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		System.out.println("Place Gear Command initialized...with FieldPositions and AutoRoutes");
		m = Navigator.motionManager;
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		System.out.println("Robot executing Autonomous PlaceGearCommand...");

		System.out.println("In Execute() of PlaceGearCommand Constructor..." +
				" Alliance: " + this.selectedAlliance.toString() +
				" GearPlacement Location: " + this.selectedAutonomousGearPlacementLocation.toString() +
				" Robot DropOff Location: " + this.selectedAutonomousDropOffLocation.toString());
		selectedAutonomousGearPlacementLocation = autonomousGearPlacementOptions.PLACE_GEAR_CENTER_AIRSHIP;
		Pose srcPosition;
		Pose interimPosition;
		Pose dstPosition;
		double distanceToIntermediatePosition = 0.0;
		double distanceToPeg = 0.0;
		double xseg = 0.0;
		double yseg = 0.0;
		switch (selectedAutonomousGearPlacementLocation) {
			case PLACE_GEAR_LEFT_AIRSHIP:
				System.out.println("[Autonomous Routing] Starting at left starting position, going to interim position towards left gear hook.");
				routeToGear = AutoRoutes.leftStartToGear;
				srcPosition = routeToGear.get(0);
				interimPosition = routeToGear.get(1);
				distanceToIntermediatePosition = interimPosition.y - srcPosition.y;
				System.out.println("[Autonomous Routing] Starting at left and going " + distanceToIntermediatePosition + " inches to left interim position.");
				m.pushProfile(TrapezoidalProfile.getTrapezoidZero(distanceToIntermediatePosition / HardwareConstants.ROTATION_CALC_FACTOR, 70, 3 * Math.PI / 2, 0), true, true);

				dstPosition = routeToGear.get(2);
				//rotate towards Left Gear position
				Navigator.rotateAbsolute(dstPosition.theta);
				//go the next segment from interim position to the left peg
				xseg = Math.abs(dstPosition.x - interimPosition.x);
				yseg = Math.abs(dstPosition.y - interimPosition.y);
				distanceToPeg = Math.sqrt(xseg * xseg + yseg * yseg);
				System.out.println("[Autonomous Routing] Starting at left and going " + distanceToPeg + " inches to left gear hook.");
				m.pushProfile(TrapezoidalProfile.getTrapezoidZero(distanceToPeg / HardwareConstants.ROTATION_CALC_FACTOR, 70, 3 * Math.PI / 2, 0), true, true);


				break;
			case PLACE_GEAR_RIGHT_AIRSHIP:
				System.out.println("[Autonomous Routing] Starting at right starting position, going to right gear hook.");
				routeToGear = AutoRoutes.rightStartToGear;
				srcPosition = routeToGear.get(0);
				interimPosition = routeToGear.get(1);
				distanceToIntermediatePosition = interimPosition.y - srcPosition.y;
				System.out.println("[Autonomous Routing] Starting at left and going " + distanceToIntermediatePosition + " inches to left interim position.");
				m.pushProfile(TrapezoidalProfile.getTrapezoidZero(distanceToIntermediatePosition / HardwareConstants.ROTATION_CALC_FACTOR, 70, 3 * Math.PI / 2, 0), true, true);

				dstPosition = routeToGear.get(2);
				//rotate towards Left Gear position
				Navigator.rotateAbsolute(dstPosition.theta);
				//go the next segment from interim position to the left peg
				xseg = Math.abs(dstPosition.x - interimPosition.x);
				yseg = Math.abs(dstPosition.y - interimPosition.y);
				distanceToPeg = Math.sqrt(xseg * xseg + yseg * yseg);
				System.out.println("[Autonomous Routing] Starting at right and going " + distanceToPeg + " inches to right gear hook.");
				m.pushProfile(TrapezoidalProfile.getTrapezoidZero(distanceToPeg / HardwareConstants.ROTATION_CALC_FACTOR, 70, 3 * Math.PI / 2, 0), true, true); //GEAR CENTER AUTO
				break;
			case PLACE_GEAR_CENTER_AIRSHIP:
				System.out.println("[Autonomous Routing] Starting at center starting position");
				routeToGear = AutoRoutes.centerStartToGear;
				srcPosition = routeToGear.get(0);
				dstPosition = routeToGear.get(1);
				distanceToPeg = dstPosition.y - srcPosition.y;
				System.out.println("[Autonomous Routing] Starting at center and going " + distanceToPeg + " inches to center gear hook.");
				// Test profile.  Keep this around somewhere
				m.pushProfile(TrapezoidalProfile.getTrapezoidZero(distanceToPeg / HardwareConstants.ROTATION_CALC_FACTOR, 70, 3 * Math.PI / 2, 0), true, true); //GEAR CENTER AUTO
//				Vision vision = SensorManager.getVisionSubsystem();
//				vision.alignToGear();
				break;
			case NONE:
				return;
			default:
				routeToGear = new ArrayList<>();
				break;
		}

		for (int i = 0; i < routeToGear.size(); i++) {
			System.out.println("X: " + routeToGear.get(i).x + " Y: " + routeToGear.get(i).y);
		}

	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		System.out.println("Entering isFinished method of PlaceGearCommand...");

		//default value returned was "false"
		return true;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		System.out.println("Place Gear Command ended...");
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}
