package org.usfirst.frc.team5422.robot;

import org.usfirst.frc.team5422.robot.subsystems.climber.Climber;
import org.usfirst.frc.team5422.robot.subsystems.climber.Intake;
import org.usfirst.frc.team5422.robot.subsystems.dsio.DSIO;
import org.usfirst.frc.team5422.robot.subsystems.gear.Manipulator;
import org.usfirst.frc.team5422.robot.subsystems.navigator.MecanumDrive;
import org.usfirst.frc.team5422.robot.subsystems.navigator.Navigator;
import org.usfirst.frc.team5422.robot.subsystems.shooter.Shooter;
import org.usfirst.frc.team5422.utils.SteamworksConstants;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//TODO: get max vel
//TODO: PID tune with F val
//TODO: figure out twist 
// Ferd was here.

public class Robot extends IterativeRobot {
	//subsystems
	public static Navigator navigatorSubsystem;
	public static Shooter shooterSubsystem;
	public static Climber climberSubsystem;
	public static Manipulator gearManipulatorSubsystem;
	public static Intake intakeSubsystem;
	public static MecanumDrive mecanumDrive;
	public static DSIO dsio;
	
    public Command autonomousCommand = null;
	
	public Robot() {
        NetworkTable.globalDeleteAll(); //Removes unused garbage from SmartDashboard
        dsio = new DSIO(SteamworksConstants.JOYSTICK_USB_CHANNEL, SteamworksConstants.BUTTON_BOARD_USB_CHANNEL);
        
        mecanumDrive = new MecanumDrive();
        
        navigatorSubsystem = new Navigator();
        shooterSubsystem = new Shooter(SteamworksConstants.SHOOTER_TALON_ID);
        gearManipulatorSubsystem = new Manipulator();
        climberSubsystem = new Climber();		
        intakeSubsystem = new Intake();
	}

	public void robotInit() {
        System.out.println("robot init started.");
	}

	public void autonomousInit() {
        System.out.println("autonomous init started.");
		//select the autonomous command for this run
		
		//execute autonomous command
		if (autonomousCommand != null) {

            autonomousCommand.start();
    	}

	}
	
	public void autonomousPeriodic() {
		if (autonomousCommand != null) {
            Scheduler.getInstance().run();
    	}
	}

	public void teleopInit() {
        System.out.println("teleop init started.");
        if (autonomousCommand != null) {
        	autonomousCommand.cancel();
        }
	}

	public void teleopPeriodic() {
        System.out.println("teleop periodic started.");
		
        //Run the openDrive() method
        //driver.openDrive(DSIO.getLinearX(), DSIO.getLinearY(), CANTalon.TalonControlMode.Speed);
		//while(isOperatorControl() && isEnabled()) {
			mecanumDrive.move();
		//}

		boolean climberButtonPressed = DSIO.buttonBoard.getRawButton(SteamworksConstants.INTAKE_CLIMBER_RED_SWITCH_ID);
		double climberVelocity = (DSIO.joystick.getThrottle()-1)/2;
		climberSubsystem.climb(climberVelocity, climberButtonPressed );
		boolean intakeButtonPressed = DSIO.buttonBoard.getRawButton(SteamworksConstants.INTAKE_ORANGE_SWITCH_ID);
		intakeSubsystem.takeIn(climberButtonPressed, intakeButtonPressed);
		
        //Run WPILib commands
        Scheduler.getInstance().run();

	}
	
	public static Shooter getShooterSubsystem() {
		return shooterSubsystem;
	}

	public static void setShooterSubsystem(Shooter shooterSubsystem) {
		Robot.shooterSubsystem = shooterSubsystem;
	}

	public static Climber getClimberSubsystem() {
		return climberSubsystem;
	}

	public static void setClimberSubsystem(Climber climberSubsystem) {
		Robot.climberSubsystem = climberSubsystem;
	}

	public static Manipulator getGearManipulatorSubsystem() {
		return gearManipulatorSubsystem;
	}

	public static void setGearManipulatorSubsystem(Manipulator gearManipulatorSubsystem) {
		Robot.gearManipulatorSubsystem = gearManipulatorSubsystem;
	}

	public static Intake getIntakeSubsystem() {
		return intakeSubsystem;
	}

	public static void setIntakeSubsystem(Intake intakeSubsystem) {
		Robot.intakeSubsystem = intakeSubsystem;
	}

}