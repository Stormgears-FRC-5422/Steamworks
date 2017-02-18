package org.usfirst.frc.team5422.robot;

import org.usfirst.frc.team5422.robot.subsystems.sensors.SensorManager;
import org.usfirst.frc.team5422.robot.commands.AutonomousCommand;
import org.usfirst.frc.team5422.robot.commands.PlaceGearCommand;
import org.usfirst.frc.team5422.robot.subsystems.climber_intake.ClimberIntake;
import org.usfirst.frc.team5422.robot.subsystems.dsio.DSIO;
import org.usfirst.frc.team5422.robot.subsystems.gear.Manipulator;
import org.usfirst.frc.team5422.robot.subsystems.navigator.Navigator;
import org.usfirst.frc.team5422.robot.subsystems.shooter.Shooter;
import org.usfirst.frc.team5422.utils.SteamworksConstants;
import org.usfirst.frc.team5422.utils.SteamworksConstants.autonomousModeOptions;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class Robot extends IterativeRobot {
	//subsystems
	public static Navigator navigatorSubsystem;
	public static Shooter shooterSubsystem;
	public static ClimberIntake climberIntakeSubsystem;
	public static Manipulator gearManipulatorSubsystem;
	public static DSIO dsio;

	public autonomousModeOptions autonomousModeSelected = autonomousModeOptions.CROSS_BASELINE;
    public Command autonomousCommand = null;
	
	public Robot() {
        NetworkTable.globalDeleteAll(); //Removes unused garbage from SmartDashboard
		NetworkTable.initialize();

        dsio = new DSIO(SteamworksConstants.JOYSTICK_USB_CHANNEL, SteamworksConstants.BUTTON_BOARD_USB_CHANNEL);        
        navigatorSubsystem = new Navigator();
        shooterSubsystem = new Shooter(SteamworksConstants.SHOOTER_TALON_ID);
        gearManipulatorSubsystem = new Manipulator();
        climberIntakeSubsystem = new ClimberIntake(SteamworksConstants.CLIMBER_INTAKE_TALON_ID);				

        //TODO:: initialize sensors here 
		//TODO: turn on these two lines of code when ready to test
		SensorManager.initiateSensorSystems();
		SensorManager.startPublishingToNetwork();
	}

	public void robotInit() {
        System.out.println("robot init started.");
        
        
	}

	public void autonomousInit() {
        System.out.println("autonomous init started.");

        //select the autonomous command for this run
        selectAutonomousCommand();
        
		//execute autonomous command
		if (autonomousCommand != null) {

            autonomousCommand.start();
    	}
	}
	
	public void teleopInit() {
        System.out.println("teleop init started.");
        if (autonomousCommand != null) {
        	autonomousCommand.cancel();
        }
	}

	public void disabledInit() {
		
	}
	
	public void autoPeriodic() {
        System.out.println("auto periodic started.");
        if (autonomousCommand != null) {
            Scheduler.getInstance().run();
        }

	}
	
	public void teleopPeriodic() {
        System.out.println("teleop periodic started.");
		
        //Move the MecanumDrive
		Navigator.getInstance().getMecanumDrive().move();

		dsio.checkSwitches();
		
        //Run WPILib commands
        Scheduler.getInstance().run();

	}
	
	public void disabledPeriodic() {
		
	}
	
	public void robotPeriodic() {
		
	}
		
    private void selectAutonomousCommand() {

        autonomousModeSelected = (autonomousModeOptions)dsio.autonomousModeChooser.getSelected();
        switch (autonomousModeSelected) {
            case PLACE_GEAR_LEFT_AIRSHIP:
                //System.out.println("selecting Place Gear Left of Airship command.");
            	autonomousCommand = new PlaceGearCommand();
            	break;
            case PLACE_GEAR_RIGHT_AIRSHIP:
            	//for autonomous reach, cross and shoot independently using separate trapezoidal motion profile
                //System.out.println("selecting reach 'n cross 'n shoot command.");
            	autonomousCommand = new PlaceGearCommand();
            	break;
            case PLACE_GEAR_CENTER_AIRSHIP:
            	//autonomous reach using SINGLE trapezoidal motion profile
                //System.out.println("selecting reach command.");
            	autonomousCommand = new PlaceGearCommand();
            case CROSS_BASELINE:
            case NONE:
            default:
            	//autonomous reach AND cross with NO shoot using SINGLE trapezoidal motion profile
            	autonomousCommand = new AutonomousCommand();
            	break;
        }
    	
    }

    public static Shooter getShooterSubsystem() {
		return shooterSubsystem;
	}

	public static void setShooterSubsystem(Shooter shooterSubsystem) {
		Robot.shooterSubsystem = shooterSubsystem;
	}

	public static ClimberIntake getClimberSubsystem() {
		return climberIntakeSubsystem;
	}

	public static void setClimberSubsystem(ClimberIntake climberSubsystem) {
		Robot.climberIntakeSubsystem = climberSubsystem;
	}

	public static Manipulator getGearManipulatorSubsystem() {
		return gearManipulatorSubsystem;
	}

	public static void setGearManipulatorSubsystem(Manipulator gearManipulatorSubsystem) {
		Robot.gearManipulatorSubsystem = gearManipulatorSubsystem;
	}

	
}