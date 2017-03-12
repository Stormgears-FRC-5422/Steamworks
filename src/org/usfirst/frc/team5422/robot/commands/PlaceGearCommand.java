package org.usfirst.frc.team5422.robot.commands;

import java.util.ArrayList;

import org.usfirst.frc.team5422.robot.Robot;
import org.usfirst.frc.team5422.robot.subsystems.navigator.AutoRoutes;
import org.usfirst.frc.team5422.robot.subsystems.navigator.FieldPositions;
import org.usfirst.frc.team5422.robot.subsystems.navigator.Navigator;
import org.usfirst.frc.team5422.robot.subsystems.navigator.Pose;
import org.usfirst.frc.team5422.robot.subsystems.navigator.motionprofile.MotionManager;
import org.usfirst.frc.team5422.robot.subsystems.navigator.motionprofile.TrapezoidalProfile;
import org.usfirst.frc.team5422.utils.SteamworksConstants.alliances;
import org.usfirst.frc.team5422.utils.SteamworksConstants.autonomousDropOffLocationOptions;
import org.usfirst.frc.team5422.utils.SteamworksConstants.autonomousGearPlacementOptions;

import edu.wpi.first.wpilibj.command.Command;

public class PlaceGearCommand extends Command {
	private ArrayList<Pose> routeToGear, routeToDropOff;
	private autonomousGearPlacementOptions selectedAutonomousGearPlacementLocation;
	private autonomousDropOffLocationOptions selectedAutonomousDropOffLocation;
	private alliances selectedAlliance;
	
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
		FieldPositions.initialize(this.selectedAlliance);
		AutoRoutes.initialize(this.selectedAlliance);
	}
		
	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		System.out.println("Robot executing Autonomous PlaceGearCommand...");
		
		System.out.println("In Execute() of PlaceGearCommand Constructor..." + 
				" Alliance: " + this.selectedAlliance.toString() + 
				" GearPlacement Location: " + this.selectedAutonomousGearPlacementLocation.toString() +
				" Robot DropOff Location: " + this.selectedAutonomousDropOffLocation.toString());
		
		switch (selectedAutonomousGearPlacementLocation) {
			case PLACE_GEAR_LEFT_AIRSHIP:
				System.out.println("[Autonomous Routing] Starting at left starting position, going to left gear hook.");
				routeToGear = AutoRoutes.leftStartToGear;

				switch (selectedAutonomousDropOffLocation) {
					case BASELINE:
						System.out.println("[Autonomous Routing] Continuing on to baseline from left gear.");
						routeToDropOff = AutoRoutes.leftGearToBaseline;
						break;
					case GEAR_PICKUP:
						System.out.println("[Autonomous Routing] Continuing on to gear pickup from left gear.");
						routeToDropOff = AutoRoutes.leftGearToGearPickup;
						break;
					default:
						routeToDropOff = new ArrayList<>();
						break;
				}
				break;
			case PLACE_GEAR_RIGHT_AIRSHIP:
				System.out.println("[Autonomous Routing] Starting at right starting position, going to right gear hook.");
				routeToGear = AutoRoutes.rightStartToGear;

				switch (selectedAutonomousDropOffLocation) {
					case BASELINE:
						System.out.println("[Autonomous Routing] Continuing on to baseline from right gear.");
						routeToDropOff = AutoRoutes.rightGearToBaseline;
						break;
					case GEAR_PICKUP:
						System.out.println("[Autonomous Routing] Continuing on to gear pickup from right gear.");
						routeToDropOff = AutoRoutes.rightGearToGearPickup;
						break;
					default:
						routeToDropOff = new ArrayList<>();
						break;
				}
				break;
			case PLACE_GEAR_CENTER_AIRSHIP:
				System.out.println("[Autonomous Routing] Starting at center starting position, going to center gear hook.");
				routeToGear = AutoRoutes.centerStartToGear;

				switch (selectedAutonomousDropOffLocation) {
					case BASELINE:
						System.out.println("[Autonomous Routing] Continuing on to baseline from center gear.");
						routeToDropOff = AutoRoutes.centerGearToBaseline;
						break;
					case GEAR_PICKUP:
						System.out.println("[Autonomous Routing] Continuing on to gear pickup from center gear.");
						routeToDropOff = AutoRoutes.centerGearToGearPickup;
						break;
					default:
						routeToDropOff = new ArrayList<>();
						break;
				}
				break;
			case NONE:
				return;
			default:
				routeToGear = new ArrayList<>();
				routeToDropOff = new ArrayList<>();
				break;
		}

		for (int i = 0; i < routeToGear.size(); i++) {
			System.out.println("X: " + routeToGear.get(i).x + " Y: " + routeToGear.get(i).y);
		}
		for (int i = 0; i < routeToDropOff.size(); i++) {
			System.out.println("X: " + routeToDropOff.get(i).x + " Y: " + routeToDropOff.get(i).y);
		}

		MotionManager m = Navigator.motionManager;		
		  // Test profile.  Keep this around somewhere
		m.pushProfile(TrapezoidalProfile.getTrapezoidZero(76/6.0/Math.PI, 70, 3*Math.PI/2, 0), true, true); //GEAR CENTER AUTO
//		SensorManager.vision.alignToGear();
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
