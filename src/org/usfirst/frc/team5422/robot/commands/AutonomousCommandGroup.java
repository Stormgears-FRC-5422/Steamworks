package org.usfirst.frc.team5422.robot.commands;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5422.robot.Robot;
import org.usfirst.frc.team5422.robot.subsystems.navigator.Pose;
import org.usfirst.frc.team5422.utils.SteamworksConstants.alliances;
import org.usfirst.frc.team5422.utils.SteamworksConstants.autonomousDropOffLocationOptions;
import org.usfirst.frc.team5422.utils.SteamworksConstants.autonomousGearPlacementOptions;
import org.usfirst.frc.team5422.utils.SteamworksConstants.flapPositions;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonomousCommandGroup extends CommandGroup {
	public Command autoPlaceGearCommand;
//	public RobotAutoDropOffCommand autoRobotDropOffCommand;
	
	private autonomousGearPlacementOptions selectedAutonomousGearPlacementLocation;
//	private autonomousDropOffLocationOptions selectedAutonomousDropOffLocation;
	private alliances selectedAlliance;
	private flapPositions selectedAutoEndFlapPosition;
	private flapPositions selectedAutoStartFlapPosition;	

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

	public AutonomousCommandGroup(alliances selectedAlliance, 
			  autonomousGearPlacementOptions selectedAutonomousGearPlacementLocation,
			  flapPositions selectedAutoStartFlapPosition,
			  flapPositions selectedAutoEndFlapPosition) {
		requires(Robot.navigatorSubsystem);
		requires(Robot.gearManipulatorSubsystem);
		
		this.selectedAlliance = selectedAlliance;
		this.selectedAutonomousGearPlacementLocation = selectedAutonomousGearPlacementLocation;
		this.selectedAutoStartFlapPosition = selectedAutoStartFlapPosition;
		this.selectedAutoEndFlapPosition = selectedAutoEndFlapPosition;
		
		System.out.println("creating autonomous PlaceGearCommand ");
		
		if (selectedAutonomousGearPlacementLocation == autonomousGearPlacementOptions.HOPPER_AUTONOMOUS) {
		autoPlaceGearCommand = new HopperShootCommand(this.selectedAlliance);			
		} else if (selectedAutonomousGearPlacementLocation == autonomousGearPlacementOptions.NONE) {
		//do nothing
		} else {
		autoPlaceGearCommand = new PlaceGearCommand(this.selectedAlliance, 
											this.selectedAutonomousGearPlacementLocation,
											this.selectedAutoStartFlapPosition,
											this.selectedAutoEndFlapPosition);
		}

		addSequential(autoPlaceGearCommand);

	}

	protected void initialize() {
		System.out.println("Initializing Steamworks autonomous command group.");
		Robot.gearManipulatorSubsystem.setFlaps(selectedAutoStartFlapPosition.toInt());
   }
}