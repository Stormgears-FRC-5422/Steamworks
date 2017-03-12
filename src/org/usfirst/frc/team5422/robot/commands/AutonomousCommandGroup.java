package org.usfirst.frc.team5422.robot.commands;

import java.util.ArrayList;

import org.usfirst.frc.team5422.robot.Robot;
import org.usfirst.frc.team5422.robot.subsystems.navigator.Pose;
import org.usfirst.frc.team5422.utils.SteamworksConstants.alliances;
import org.usfirst.frc.team5422.utils.SteamworksConstants.autonomousDropOffLocationOptions;
import org.usfirst.frc.team5422.utils.SteamworksConstants.autonomousGearPlacementOptions;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonomousCommandGroup extends CommandGroup {
	public PlaceGearCommand autoPlaceGearCommand;
	public RobotAutoDropOffCommand autoRobotDropOffCommand;
	
	private autonomousGearPlacementOptions selectedAutonomousGearPlacementLocation;
	private autonomousDropOffLocationOptions selectedAutonomousDropOffLocation;
	private alliances selectedAlliance;
    public AutonomousCommandGroup() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    	//autonomousCommand = new AutonomousCommand();
		//addSequential(autonomousCommand);
	}

	public AutonomousCommandGroup(alliances selectedAlliance, autonomousGearPlacementOptions selectedAutonomousGearPlacementLocation, autonomousDropOffLocationOptions selectedAutonomousDropOffLocation) {
		requires(Robot.navigatorSubsystem);
		this.selectedAlliance = selectedAlliance;
		this.selectedAutonomousGearPlacementLocation = selectedAutonomousGearPlacementLocation;
		this.selectedAutonomousDropOffLocation = selectedAutonomousDropOffLocation;
		
		System.out.println("creating autonomous PlaceGearCommand ");
		autoPlaceGearCommand = new PlaceGearCommand(this.selectedAlliance, this.selectedAutonomousGearPlacementLocation, this.selectedAutonomousDropOffLocation);
		autoRobotDropOffCommand = new RobotAutoDropOffCommand(this.selectedAlliance, this.selectedAutonomousGearPlacementLocation, this.selectedAutonomousDropOffLocation);
		
		addSequential(autoPlaceGearCommand);
		addSequential(autoRobotDropOffCommand);
		
	}

	protected void initialize() {
		System.out.println("Initializing Steamworks autonomous command group.");
    }
}