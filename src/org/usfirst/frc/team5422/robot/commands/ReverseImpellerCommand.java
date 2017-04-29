package org.usfirst.frc.team5422.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5422.robot.Robot;
import org.usfirst.frc.team5422.robot.subsystems.shooter.shooter_thread.ShooterRunnable;
import org.usfirst.frc.team5422.utils.SteamworksConstants;

/**
 * Created by michael on 1/29/17.
 */
public class ReverseImpellerCommand extends Command
{
    boolean doReverse;
    
    public ReverseImpellerCommand(boolean reverse)
    {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.shooterSubsystem);
        doReverse = reverse;
    }
    
    // Called just before this Command runs the first time
    protected void initialize()
    {
    	if (doReverse) 
    		Robot.shooterSubsystem.reverseImpeller();
    	else {
    		Robot.shooterSubsystem.stopImpeller();    		
//    		Robot.shooterSubsystem.startImpeller();
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished()
    {
        return true;
    }
}