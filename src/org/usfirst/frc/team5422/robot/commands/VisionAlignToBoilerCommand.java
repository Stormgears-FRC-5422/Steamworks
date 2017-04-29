package org.usfirst.frc.team5422.robot.commands;

import org.usfirst.frc.team5422.robot.Robot;
import org.usfirst.frc.team5422.robot.subsystems.sensors.SensorManager;
import org.usfirst.frc.team5422.robot.subsystems.sensors.Vision;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class VisionAlignToBoilerCommand extends Command {
	private Vision vision;
	
    public VisionAlignToBoilerCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
//    	requires(Robot.navigatorSubsystem);
    	requires(Robot.shooterSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
		System.out.println("Initializing Vision based Align to Boiler command");
    	vision = SensorManager.getVisionSubsystem();
		vision.alignToBoiler();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
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
