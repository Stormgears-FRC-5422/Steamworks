package org.usfirst.frc.team5422.robot;

import java.util.ArrayList;
import java.util.List;

import org.usfirst.frc.team5422.robot.commands.AutonomousCommandGroup;
import org.usfirst.frc.team5422.robot.subsystems.climber_intake.ClimberIntake;
import org.usfirst.frc.team5422.robot.subsystems.dsio.DSIO;
import org.usfirst.frc.team5422.robot.subsystems.gear.Manipulator;
import org.usfirst.frc.team5422.robot.subsystems.navigator.Drive;
import org.usfirst.frc.team5422.robot.subsystems.navigator.Navigator;
import org.usfirst.frc.team5422.robot.subsystems.sensors.SensorManager;
import org.usfirst.frc.team5422.robot.subsystems.sensors.Vision;
import org.usfirst.frc.team5422.robot.subsystems.shooter.Shooter;
import org.usfirst.frc.team5422.utils.RegisteredNotifier;
import org.usfirst.frc.team5422.utils.RobotDriveConstants.RobotDriveProfile;
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
	public static List<RegisteredNotifier> notifierRegistry = new ArrayList<RegisteredNotifier>();

	public alliances allianceSelected = alliances.RED;
	public autonomousGearPlacementOptions autonomousGearPlacementSelected = autonomousGearPlacementOptions.NONE;
	public autonomousDropOffLocationOptions autonomousDropOffLocationSelected = autonomousDropOffLocationOptions.BASELINE;

	public Command autonomousCommand = null;

	public Robot() {
		NetworkTable.globalDeleteAll(); //Removes unused garbage from NetworkTable
		NetworkTable.initialize();

		dsio = new DSIO(SteamworksConstants.JOYSTICK_USB_CHANNEL, SteamworksConstants.BUTTON_BOARD_USB_CHANNEL);
		navigatorSubsystem = Navigator.getInstance();
		shooterSubsystem = new Shooter(SteamworksConstants.SHOOTER_TALON_ID, SteamworksConstants.SHOOTER_RELAY_ID);
 		gearManipulatorSubsystem = new Manipulator();
		climberIntakeSubsystem = new ClimberIntake(SteamworksConstants.CLIMBER_INTAKE_TALON_ID);
		
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
		
/*
		Vision.turnOnLights();

		//initializing the Robot for motion profile mode
		Navigator.getMecanumDrive().initializeDriveMode(robotMode, RobotDriveProfile.MOTIONPROFILE); 

		MotionManager m = Navigator.motionManager;
		
		  // Test profile.  Keep this around somewhere
		m.pushProfile(TrapezoidalProfile.getTrapezoidZero(76/6.0/Math.PI, 70, 3*Math.PI/2, 0), true, true); //GEAR CENTER AUTO
*/		
		
/*		ArrayList<Pose> poses = new ArrayList<Pose>();
		poses.add(new Pose(0,0,0,0));
		poses.add(new Pose(0,0.5,0,2));
		poses.add(new Pose(0,1,0,0));//in meters
		
		Spline spline = new Spline(poses);
		
		System.out.println("started spline");
		Navigator.driveSplineMeters(spline);
		System.out.println("finished following spline");
*/
		//Robot in Autonomous mode
		
		/*ArrayList<Pose> poses = new ArrayList<Pose>();
		poses.add(new Pose(0, 0, 0, 0));
		poses.add(new Pose(0, 2, 0, 0));
		Spline spline = new Spline(poses);
		Navigator.driveSpline(spline);*/
	}
		
	public void teleopInit() {
		System.out.println("teleop init started.");
		//Robot in Teleop Mode
		robotMode = RobotModes.TELEOP;
		
		if (autonomousCommand != null){			
			autonomousCommand.cancel();
		}

		SensorManager.startPublishingToNetwork();
		Vision.turnOnLights();
		
		//initializing the Robot for joystick Velocity mode
		Navigator.getMecanumDrive().initializeDriveMode(robotMode, RobotDriveProfile.VELOCITY); 		
	}

	public void disabledInit() {
		System.out.println("disabled init started.");

		SensorManager.stopPublishingToNetwork();
		Vision.turnOffLights();
		
		//Navigator.motionManager.endProfile();
		
		// shut down all notifiers.  This is a bit aggressive
//		for (RegisteredNotifier r : notifierRegistry) {
//			r.stop();
//		}	
		
		
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
		allianceSelected = (alliances) dsio.allianceChooser.getSelected();

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
		autonomousDropOffLocationSelected = (autonomousDropOffLocationOptions) dsio.autonomousDropOffLocationOptionsChooser.getSelected();
	}

	private void selectAutonomousGearPlacement() {
		autonomousGearPlacementSelected = (autonomousGearPlacementOptions) dsio.autonomousGearPlacementOptionsChooser.getSelected();
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