package org.usfirst.frc.team5422.robot.commands;

import org.usfirst.frc.team5422.robot.Robot;
import org.usfirst.frc.team5422.robot.subsystems.navigator.Navigator;
import org.usfirst.frc.team5422.robot.subsystems.sensors.SensorManager;
import org.usfirst.frc.team5422.robot.subsystems.sensors.Vision;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class VisionAlignToGearCommand extends Command {
	private Vision vision;
	
    public VisionAlignToGearCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.navigatorSubsystem);
    	requires(Robot.gearManipulatorSubsystem);
    	//requires(Robot.visionSubsystem)
    }

    // Called just before this Command runs the first time
    protected void initialize() {
		System.out.println("Initializing Vision based Align to Gear command");
    	vision = SensorManager.getVisionSubsystem();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
		vision.alignToGear();
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
		System.out.println("End of Vision based Align to Gear command");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
