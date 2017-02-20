package org.usfirst.frc.team5422.robot.commands;

import java.util.ArrayList;

import org.usfirst.frc.team5422.robot.Robot;
import org.usfirst.frc.team5422.robot.subsystems.navigator.Pose;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonomousCommandGroup extends CommandGroup {
	public AutonomousCommand autonomousCommand;
	private ArrayList<Pose> routeToGear, routeToDropOff;

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

	public AutonomousCommandGroup(ArrayList<Pose> routeToGear, ArrayList<Pose> routeToDropOff) {
		requires(Robot.navigatorSubsystem);

		this.routeToGear = routeToGear;
		this.routeToDropOff = routeToDropOff;

		autonomousCommand = new AutonomousCommand(this.routeToGear, this.routeToDropOff );
		addSequential(autonomousCommand);
	}

	protected void initialize() {
		System.out.println("Initializing Steamworks autonomous command group.");
    }
}