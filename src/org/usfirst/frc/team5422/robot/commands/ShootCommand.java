package org.usfirst.frc.team5422.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.stormgears.WebDashboard.Diagnostics.Diagnostics;
import org.usfirst.frc.team5422.robot.Robot;
import org.usfirst.frc.team5422.robot.subsystems.shooter.shooter_thread.ShooterRunnable;
import org.usfirst.frc.team5422.utils.SteamworksConstants;

/**
 * Command that shoots the balls
 */
public class ShootCommand extends Command
{
    private ShooterRunnable shooterRunnable;
    private Thread shooterThread;

    public ShootCommand()
    {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.shooterSubsystem);

        shooterRunnable = new ShooterRunnable();
    }

    // Called just before this Command runs the first time
    protected void initialize()
    {
//        Diagnostics.log("shootCommand initializing...");

        Robot.shooterSubsystem.initializeShooter();

        shooterThread = new Thread(shooterRunnable);
        shooterThread.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute()
    {

    }

    protected boolean isFinished()
    {
//        Diagnostics.log("shootCommand stopped.");
        return true;
    }

    // Called once after isFinished returns true
    protected void end()
    {

    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted()
    {
        shooterThread.interrupt();
    }
}