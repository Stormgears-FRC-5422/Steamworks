package org.usfirst.frc.team5422.robot;

import java.util.ArrayList;
import java.util.List;

import org.usfirst.frc.team5422.robot.commands.AutonomousCommandGroup;
import org.usfirst.frc.team5422.robot.subsystems.climber_intake.ClimberIntake;
import org.usfirst.frc.team5422.robot.subsystems.dsio.DSIO;
import org.usfirst.frc.team5422.robot.subsystems.gear.Manipulator;
import org.usfirst.frc.team5422.robot.subsystems.navigator.AutoRoutes;
import org.usfirst.frc.team5422.robot.subsystems.navigator.Drive;
import org.usfirst.frc.team5422.robot.subsystems.navigator.FieldPositions;
import org.usfirst.frc.team5422.robot.subsystems.navigator.Navigator;
import org.usfirst.frc.team5422.robot.subsystems.sensors.SensorManager;
import org.usfirst.frc.team5422.robot.subsystems.sensors.Vision;
import org.usfirst.frc.team5422.robot.subsystems.shooter.Shooter;
import org.usfirst.frc.team5422.utils.RegisteredNotifier;
import org.usfirst.frc.team5422.utils.RobotTalonConstants;
import org.usfirst.frc.team5422.utils.RobotTalonConstants.RobotDriveProfile;
import org.usfirst.frc.team5422.utils.SteamworksConstants;
import org.usfirst.frc.team5422.utils.SteamworksConstants.RobotModes;
import org.usfirst.frc.team5422.utils.SteamworksConstants.alliances;
import org.usfirst.frc.team5422.utils.SteamworksConstants.autonomousDropOffLocationOptions;
import org.usfirst.frc.team5422.utils.SteamworksConstants.autonomousGearPlacementOptions;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	// Subsystems
	public static Navigator navigatorSubsystem;
	public static Shooter shooterSubsystem;
	public static ClimberIntake climberIntakeSubsystem;
	public static Manipulator gearManipulatorSubsystem;
	public static DSIO dsio;
	public static RobotModes robotMode = RobotModes.AUTONOMOUS;
	public static final List<RegisteredNotifier> notifierRegistry = new ArrayList<>();

	public alliances allianceSelected = alliances.RED;
	public autonomousGearPlacementOptions autonomousGearPlacementSelected = autonomousGearPlacementOptions.NONE;
	public autonomousDropOffLocationOptions autonomousDropOffLocationSelected = autonomousDropOffLocationOptions.BASELINE;

	public Command autonomousCommand = null;

	public Robot() {
		NetworkTable.globalDeleteAll(); //Removes unused garbage from NetworkTable
		NetworkTable.initialize();

		dsio = new DSIO(SteamworksConstants.JOYSTICK_USB_CHANNEL, SteamworksConstants.BUTTON_BOARD_USB_CHANNEL);
		navigatorSubsystem = Navigator.getInstance();
		shooterSubsystem = new Shooter(RobotTalonConstants.SHOOTER_TALON_ID, RobotTalonConstants.SHOOTER_RELAY_ID);
		climberIntakeSubsystem = new ClimberIntake(RobotTalonConstants.CLIMBER_TALON_ID);
 		gearManipulatorSubsystem = new Manipulator(SteamworksConstants.LEFT_FLAP_CHANNEL, SteamworksConstants.RIGHT_FLAP_CHANNEL);
		SensorManager.initiateSensorSystems();
		SensorManager.startPublishingToNetwork();			
	}

	public void robotInit() {
		System.out.println("robot init started.");
	}

	public void autonomousInit() {
		System.out.println("autonomous init started.");
		robotMode = RobotModes.AUTONOMOUS;
		
		//select the autonomous command for this run
		selectAutonomousCommand();

		//these two lines should follow the selectAutonomousCommand 
		//so that the alliance is initialized
		FieldPositions.initialize(allianceSelected);
		AutoRoutes.initialize(allianceSelected);

		//if any residual commands exist, cancel them
		if (autonomousCommand != null) {
			autonomousCommand.cancel();
		}

		//initializing the Robot for motion profile mode
		Navigator.getMecanumDrive().initializeDriveMode(robotMode, RobotDriveProfile.MOTIONPROFILE); 
		
		SensorManager.startPublishingToNetwork();
		Vision.turnOnLights();

		//execute autonomous command
		if (autonomousCommand != null) {
			System.out.println("starting the autonomous command...from autonomousInit()");
			autonomousCommand.start();
		} else {
			System.out.println("AUTONOMOUS COMMAND IS NOT INITIALIZED");
		}		
	}
		
	public void teleopInit() {
		System.out.println("teleop init started.");
		//Robot in Teleop Mode
		robotMode = RobotModes.TELEOP;
		
		if (autonomousCommand != null){			
			autonomousCommand.cancel();
		}

		//initializing the Robot for joystick Velocity mode
		Navigator.getMecanumDrive().initializeDriveMode(robotMode, RobotDriveProfile.VELOCITY); 		

		SensorManager.startPublishingToNetwork();
		Vision.turnOnLights();		
	}

	public void disabledInit() {
		System.out.println("disabled init started.");

		SensorManager.stopPublishingToNetwork();
		Vision.turnOffLights();
		
		Navigator.motionManager.endProfile();
		
		// shut down all notifiers.  This is a bit aggressive
		for (RegisteredNotifier r : notifierRegistry) {
			r.stop();
		}
		
		
	}

	public void autonomousPeriodic() {
		if (autonomousCommand != null) {
			Scheduler.getInstance().run();
		}

	}

	
	
	public void teleopPeriodic() {
	//	robotMode = RobotModes.TELEOP;
		//Navigator.getMecanumDrive().initializeDriveMode(robotMode, RobotDriveProfile.VELOCITY); 
	//	Navigator.getInstance();
		//Move the MecanumDrive
		Navigator.getMecanumDrive().move();
		dsio.checkSwitches();
		
		SmartDashboard.putNumber("0 POS: " ,Drive.talons[0].getEncPosition());
		SmartDashboard.putNumber("0 VEL: ", Drive.talons[0].getEncVelocity());
		
		SmartDashboard.putNumber("1 POS: " ,Drive.talons[1].getEncPosition());
		SmartDashboard.putNumber("1 VEL: ", Drive.talons[1].getEncVelocity());
		
		//Run WPILib commands
		Scheduler.getInstance().run();
	}

	public void disabledPeriodic() {

	}

	public void robotPeriodic() {

	}

	private void selectAlliance() {
		allianceSelected = dsio.allianceChooser.getSelected();

		switch (allianceSelected) {
			case RED:
				// BOILER IS TO THE RIGHT
				break;
			case BLUE:
				// BOILER IS TO THE LEFT
				break;
		}
	}

	private void selectAutonomousDropOffLocation() {
		autonomousDropOffLocationSelected = dsio.autonomousDropOffLocationOptionsChooser.getSelected();
	}

	private void selectAutonomousGearPlacement() {
		autonomousGearPlacementSelected = dsio.autonomousGearPlacementOptionsChooser.getSelected();
	}

	private void selectAutonomousCommand() {
		selectAlliance();
		selectAutonomousGearPlacement();
		selectAutonomousDropOffLocation();

		System.out.println("ALLIANCE: " + allianceSelected.toString());
		System.out.println("GEAR PLACEMENT LOCATION: " + autonomousGearPlacementSelected.toString());
		System.out.println("DROP OFF LOCATION: " + autonomousDropOffLocationSelected.toString());

		System.out.println("creating autonomous command group");
		autonomousCommand = new AutonomousCommandGroup(allianceSelected, autonomousGearPlacementSelected, autonomousDropOffLocationSelected);
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