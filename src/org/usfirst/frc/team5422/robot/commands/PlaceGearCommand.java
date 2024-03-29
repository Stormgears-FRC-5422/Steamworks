package org.usfirst.frc.team5422.robot.commands;

import java.util.ArrayList;

import org.usfirst.frc.team5422.robot.Robot;
import org.usfirst.frc.team5422.robot.subsystems.navigator.AutoRoutes;
import org.usfirst.frc.team5422.robot.subsystems.navigator.FieldPositions;
import org.usfirst.frc.team5422.robot.subsystems.navigator.Navigator;
import org.usfirst.frc.team5422.robot.subsystems.navigator.Pose;
import org.usfirst.frc.team5422.robot.subsystems.navigator.motionprofile.MotionManager;
import org.usfirst.frc.team5422.robot.subsystems.navigator.motionprofile.TrapezoidalProfile;
import org.usfirst.frc.team5422.robot.subsystems.sensors.SensorManager;
import org.usfirst.frc.team5422.robot.subsystems.sensors.Vision;
import org.usfirst.frc.team5422.utils.HardwareConstants;
import org.usfirst.frc.team5422.utils.SteamworksConstants;
import org.usfirst.frc.team5422.utils.SteamworksConstants.alliances;
import org.usfirst.frc.team5422.utils.SteamworksConstants.autonomousDropOffLocationOptions;
import org.usfirst.frc.team5422.utils.SteamworksConstants.autonomousGearPlacementOptions;
import org.usfirst.frc.team5422.utils.SteamworksConstants.flapPositions;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class PlaceGearCommand extends Command {
	private ArrayList<Pose> routeToGear;
	private autonomousGearPlacementOptions selectedAutonomousGearPlacementLocation;
	private flapPositions selectedAutoStartFlapPosition;
	private flapPositions selectedAutoEndFlapPosition;
	private alliances selectedAlliance;
	private MotionManager m;		
	
	public PlaceGearCommand() {
    	requires(Robot.navigatorSubsystem);
    	requires(Robot.gearManipulatorSubsystem);    	
	}
	
	public PlaceGearCommand(alliances selectedAlliance, 
							autonomousGearPlacementOptions selectedAutonomousGearPlacementLocation, 
							flapPositions autoStartFlapPosition,
							flapPositions autoEndFlapPosition) {
		requires(Robot.navigatorSubsystem);
    	requires(Robot.gearManipulatorSubsystem);    	

    	this.selectedAlliance = selectedAlliance;
		this.selectedAutonomousGearPlacementLocation = selectedAutonomousGearPlacementLocation;
		this.selectedAutoStartFlapPosition = autoStartFlapPosition;
		this.selectedAutoEndFlapPosition = autoEndFlapPosition;

		System.out.println("In PlaceGearCommand Constructor..." + 
				" Alliance: " + this.selectedAlliance.toString() + 
				" GearPlacement Location: " + this.selectedAutonomousGearPlacementLocation.toString());
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
				" GearPlacement Location: " + this.selectedAutonomousGearPlacementLocation.toString());
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
				distanceToIntermediatePosition = interimPosition.y - srcPosition.y - 10.0;
				System.out.println("Distance to intermediate pos: " + distanceToIntermediatePosition);
				System.out.println("[Autonomous Routing] Starting at left and going " + distanceToIntermediatePosition + " inches to left interim position.");
				m.pushProfile(TrapezoidalProfile.getTrapezoidZero(distanceToIntermediatePosition/HardwareConstants.ROTATION_CALC_FACTOR, 70, 3*Math.PI/2, 0), true, true);
				m.waitUntilProfileFinishes(100);
			//	m.pushProfile(TrapezoidalProfile.getTrapezoidZero(76.0/6.0/Math.PI, 70, 3*Math.PI/2, 0), true, true); 
				m.rotateToAngle(Math.PI/3.0);
				m.waitUntilProfileFinishes(100);
				
				System.out.println("entering delay after forward");
				System.out.println("Exiting delay after forward");
				//Timer.delay(10);
				dstPosition = routeToGear.get(2);
				//rotate towards Left Gear position
				System.out.println("Theta = " + dstPosition.theta);
			//	m.pushTurn((Math.PI/3.0), false, false); //OG
			//	m.pushTurn(0.46, false, false);
//				System.out.println("entering delay after turn");
		//		Timer.delay(10);
//				System.out.println("Exiting delay after turn");
				//Navigator.rotateAbsolute(dstPosition.theta);
				//go the next segment from interim position to the left peg
				xseg = Math.abs(dstPosition.x - interimPosition.x);
				yseg = Math.abs(dstPosition.y - interimPosition.y);
				yseg -= 4.0;
				distanceToPeg = Math.sqrt(xseg*xseg + yseg*yseg);
				System.out.println("[Autonomous Routing] Starting at left and going " + distanceToPeg + " inches to left gear hook.");
				m.pushProfile(TrapezoidalProfile.getTrapezoidZero((distanceToPeg + 12.0)/HardwareConstants.ROTATION_CALC_FACTOR, 70, 3*Math.PI/2, 0), false, true); 
				
//				Timer.delay(12);
				m.waitUntilProfileFinishes(100);
//				Robot.gearManipulatorSubsystem.setFlaps(SteamworksConstants.FLAPS_NEUTRAL);				
				Robot.gearManipulatorSubsystem.setFlaps(selectedAutoEndFlapPosition.toInt());

//				// Back up
				//m.pushProfile(TrapezoidalProfile.getTrapezoidZero(24.0/HardwareConstants.ROTATION_CALC_FACTOR, 70, Math.PI/2, 0), true, true); //GEAR CENTER AUTO
				m.waitUntilProfileFinishes(100);
				break;
			case PLACE_GEAR_RIGHT_AIRSHIP:
				System.out.println("[Autonomous Routing] Starting at right starting position, going to right gear hook.");				
				routeToGear = AutoRoutes.rightStartToGear;
				srcPosition = routeToGear.get(0);
				interimPosition = routeToGear.get(1);
				distanceToIntermediatePosition = interimPosition.y - srcPosition.y - 10.0;
				System.out.println("Distance to intermediate pos: " + distanceToIntermediatePosition);
				System.out.println("[Autonomous Routing] Starting at right and going " + distanceToIntermediatePosition + " inches to right interim position.");
				m.pushProfile(TrapezoidalProfile.getTrapezoidZero(distanceToIntermediatePosition/HardwareConstants.ROTATION_CALC_FACTOR, 70, 3*Math.PI/2, 0), true, true); 
				m.waitUntilProfileFinishes(100);
				//	m.pushProfile(TrapezoidalProfile.getTrapezoidZero(76.0/6.0/Math.PI, 70, 3*Math.PI/2, 0), true, true); 
//				System.out.println("entering delay after forward");
//				System.out.println("Exiting delay after forward");
				//Timer.delay(10);
				dstPosition = routeToGear.get(2);
				//rotate towards Left Gear position
				System.out.println("Theta = " + dstPosition.theta);
				//m.pushTurn((-Math.PI/3.0), false, true); //OG
				
				m.rotateToAngle(-Math.PI/3.0);
				m.waitUntilProfileFinishes(100);
				
//				//	m.pushTurn(0.46, false, false);
//				System.out.println("entering delay after turn");
//		//		Timer.delay(10);
//				System.out.println("Exiting delay after turn");
//				//Navigator.rotateAbsolute(dstPosition.theta);
//				//go the next segment from interim position to the left peg
				xseg = Math.abs(dstPosition.x - interimPosition.x);
				yseg = Math.abs(dstPosition.y - interimPosition.y);
				yseg -= 4.0;
				distanceToPeg = Math.sqrt(xseg*xseg + yseg*yseg);
				System.out.println("[Autonomous Routing] Starting at right and going " + distanceToPeg + " inches to right gear hook.");
				m.pushProfile(TrapezoidalProfile.getTrapezoidZero((distanceToPeg+12.0)/HardwareConstants.ROTATION_CALC_FACTOR, 70, 3*Math.PI/2, 0), false, true); 
				
//				Timer.delay(12);
				m.waitUntilProfileFinishes(100);
//				Robot.gearManipulatorSubsystem.setFlaps(SteamworksConstants.FLAPS_NEUTRAL);
				Robot.gearManipulatorSubsystem.setFlaps(selectedAutoEndFlapPosition.toInt());
				Timer.delay(0.3);
////				// Back up
				m.pushProfile(TrapezoidalProfile.getTrapezoidZero(24.0/HardwareConstants.ROTATION_CALC_FACTOR, 70, Math.PI/2, 0), true, true); //GEAR CENTER AUTO
				m.waitUntilProfileFinishes(100);
				break;
			case PLACE_GEAR_CENTER_AIRSHIP:
// DLM added this to test rotation
		//		m.rotateToAngle(Math.PI/3.0);
		//		m.waitUntilProfileFinishes(100);
				//m.waitUntilProfileFinishes(100);
// //DLM commented this out to test rotation
//				System.out.println("[Autonomous Routing] Starting at center starting position");
				routeToGear = AutoRoutes.centerStartToGear;
				srcPosition = routeToGear.get(0);
				dstPosition = routeToGear.get(1);
				distanceToPeg = dstPosition.y - srcPosition.y + 3.0; 				
				System.out.println("[Autonomous Routing] Starting at center and going " + distanceToPeg + " inches to center gear hook.");
			//	m.pushProfile(TrapezoidalProfile.getTrapezoidZero(distanceToPeg/HardwareConstants.ROTATION_CALC_FACTOR, 70, 3*Math.PI/2, 0), true, true); //GEAR CENTER AUTO
//				Timer.delay(1.0);
				Vision vision = SensorManager.getVisionSubsystem();
				vision.alignToBoiler();
////				m.pushProfile(TrapezoidalProfile.getTrapezoidZero(36.0/HardwareConstants.ROTATION_CALC_FACTOR, 70, Math.PI, 0), true, true); //GEAR CENTER AUTO
//				m.waitUntilProfileFinishes(100);
//				Robot.gearManipulatorSubsystem.setFlaps(SteamworksConstants.FLAPS_DISPENSE);
//				Timer.delay(1.0);
		//		m.pushProfile(TrapezoidalProfile.getTrapezoidZero(24.0/HardwareConstants.ROTATION_CALC_FACTOR, 70, Math.PI/2, 0), true, true); //GEAR CENTER AUTO
		//		m.waitUntilProfileFinishes(100);
				break;
			case NONE:
				return;
			default:
				routeToGear = new ArrayList<>();
				break;
		}

//		Vision vision = SensorManager.getVisionSubsystem();
//		vision.alignToGear();

//		for (int i = 0; i < routeToGear.size(); i++) {
//			System.out.println("X: " + routeToGear.get(i).x + " Y: " + routeToGear.get(i).y);
//		}

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
