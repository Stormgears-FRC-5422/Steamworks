package org.usfirst.frc.team5422.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

import org.usfirst.frc.team5422.robot.commands.AutonomousCommandGroup;
import org.usfirst.frc.team5422.robot.subsystems.climber_intake.ClimberIntake;
import org.usfirst.frc.team5422.robot.subsystems.dsio.DSIO;
import org.usfirst.frc.team5422.robot.subsystems.gear.Manipulator;
import org.usfirst.frc.team5422.robot.subsystems.navigator.AutoRoutes;
import org.usfirst.frc.team5422.robot.subsystems.navigator.FieldPositions;
import org.usfirst.frc.team5422.robot.subsystems.navigator.Navigator;
import org.usfirst.frc.team5422.robot.subsystems.navigator.Pose;
import org.usfirst.frc.team5422.robot.subsystems.navigator.Spline;
import org.usfirst.frc.team5422.robot.subsystems.navigator.motionprofile.TrapezoidalProfile;
//import org.usfirst.frc.team5422.robot.subsystems.navigator.motionprofile.MotionManager.TurnDetails;
import org.usfirst.frc.team5422.robot.subsystems.sensors.SensorManager;
import org.usfirst.frc.team5422.robot.subsystems.sensors.Vision;
import org.usfirst.frc.team5422.robot.subsystems.shooter.Shooter;
import org.usfirst.frc.team5422.utils.RobotDriveConstants.RobotDriveProfile;
import org.usfirst.frc.team5422.utils.SteamworksConstants;
import org.usfirst.frc.team5422.utils.SteamworksConstants.RobotModes;
import org.usfirst.frc.team5422.utils.SteamworksConstants.alliances;
import org.usfirst.frc.team5422.utils.SteamworksConstants.autonomousDropOffLocationOptions;
import org.usfirst.frc.team5422.utils.SteamworksConstants.autonomousGearPlacementOptions;

import org.usfirst.frc.team5422.robot.subsystems.navigator.motionprofile.MotionManager;
import org.usfirst.frc.team5422.robot.subsystems.navigator.motionprofile.FRCSampleProfile;
import org.usfirst.frc.team5422.robot.subsystems.navigator.motionprofile.MotionProfileExample;
import org.usfirst.frc.team5422.utils.RegisteredNotifier;

import java.util.ArrayList;
import java.util.List;

import org.usfirst.frc.team5422.utils.SafeTalon; 
import com.ctre.CANTalon;
import com.ctre.CANTalon.*;

public class Robot extends IterativeRobot {
	// Subsystems
	public static Navigator navigatorSubsystem;
	public static Shooter shooterSubsystem;
	public static ClimberIntake climberIntakeSubsystem;
	public static Manipulator gearManipulatorSubsystem;
	public static DSIO dsio;
	public static RobotModes robotMode =  RobotModes.AUTONOMOUS;
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
		
		if(!SensorManager.isInitiated()){
			SensorManager.initiateSensorSystems();
		}
		if (!SensorManager.isPublishing()) {
			SensorManager.startPublishingToNetwork();			
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

	public void robotInit() {
		System.out.println("robot init started.");


	}

	public void autonomousInit() {
		System.out.println("autonomous init started.");

		//initializing the Robot for motionprofile mode
		Navigator.getMecanumDrive().initializeDriveMode(robotMode, RobotDriveProfile.MOTIONPROFILE); 
		MotionManager m = Navigator.motionManager;
		
		// Test profile.  Keep this around somewhere
		m.pushProfile(FRCSampleProfile.transform(), true, true);
		
		//starts publishing all sensors here
		
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
			
		
		/*robotMode = RobotModes.AUTONOMOUS;

		//if any residual commands exist, cancel them
		if (autonomousCommand != null) {
			autonomousCommand.cancel();
		}
		SensorManager.vision.turnOnLights();
		
		//initializing the Robot for motionprofile mode
		Navigator.getMecanumDrive().initializeDriveMode(robotMode, RobotDriveProfile.MOTIONPROFILE); 

		
		Navigator.motionManager.pushProfile(TrapezoidalProfile.getTrapezoidZero(3, 300, 3*Math.PI/2, 0), true, false);
		SensorManager.vision.alignToGear();
		
		//select the autonomous command for this run
		selectAutonomousCommand();

		//execute autonomous command
		if (autonomousCommand != null) {
			autonomousCommand.start();
		}else{
			System.out.println("AUTONOMOUS COMMAND IS NOT INITIALIZED");
		}
		*/
	}
	

	
	public void teleopInit() {
		System.out.println("teleop init started.");
		//Robot in Teleop Mode
		robotMode = RobotModes.TELEOP;
		Vision.turnOffLights();
		
		//initializing the Robot for joystick Velocity mode
		Navigator.getMecanumDrive().initializeDriveMode(robotMode, RobotDriveProfile.VELOCITY); 
		
		if (autonomousCommand != null){			
			autonomousCommand.cancel();
		}
		
	}

	public void disabledInit() {
		System.out.println("disabled init started.");
		if(SensorManager.isPublishing()){
			SensorManager.stopPublishingToNetwork();
		}
		
		//Navigator.motionManager.endProfile();
		
		// shut down all notifiers.  This is a bit aggressive
		for (RegisteredNotifier r : notifierRegistry) {
			r.stop();
		}	
	}

	public void autonomousPeriodic() {
//		dummyExample();
		/*
		if (autonomousCommand != null) {
			Scheduler.getInstance().run();
		}
		*/
		if(!SensorManager.isPublishing()){
			SensorManager.startPublishingToNetwork();
		}
	}

// CLIP HERE---vvv
// THIS STUFF NEEDS TO GO	
//	/** The Talon we want to motion profile. */
//	SafeTalon _talon = Navigator.getInstance().getMecanumDrive().talons[0];
//
//	/** some example logic on how one can manage an MP */
//	MotionProfileExample _example = new MotionProfileExample(_talon);
//	
//	/** joystick for testing */
//	Joystick _joy;//= new Joystick(0);
//
//	/** cache last buttons so we can detect press events.  In a command-based project you can leverage the on-press event
//	 * but for this simple example, lets just do quick compares to prev-btn-states */
//	boolean [] _btnsLast = {false,false,false,false,false,false,false,false,false,false};
//
//	boolean [] btns= new boolean [_btnsLast.length];
//	
//	/**  function is called periodically during operator control */
//    public void dummyExample() {
//		/* get buttons */
//    	
//		for(int i=1;i<_btnsLast.length;++i)
//			btns[i] = _joy.getRawButton(i);
//
//		/* get the left joystick axis on Logitech Gampead */
//		double leftYjoystick = -1 * _joy.getY(); /* multiple by -1 so joystick forward is positive */
//
//		/* call this periodically, and catch the output.  Only apply it if user wants to run MP. */
//		_example.control();
//		btns[5] = true;
//		btns[6] = true;
//		
//		if (btns[5] == false) { /* Check button 5 (top left shoulder on the logitech gamead). */
//			/*
//			 * If it's not being pressed, just do a simple drive.  This
//			 * could be a RobotDrive class or custom drivetrain logic.
//			 * The point is we want the switch in and out of MP Control mode.*/
//		
//			/* button5 is off so straight drive */
//			_talon.changeControlMode(TalonControlMode.Voltage);
//			_talon.set(12.0 * leftYjoystick);
//
//			_example.reset();
//		} else {
//			/* Button5 is held down so switch to motion profile control mode => This is done in MotionProfileControl.
//			 * When we transition from no-press to press,
//			 * pass a "true" once to MotionProfileControl.
//			 */
//			_talon.changeControlMode(TalonControlMode.MotionProfile);
//			
//			CANTalon.SetValueMotionProfile setOutput = _example.getSetValue();
//					
//			_talon.set(setOutput.value);
//
//			/* if btn is pressed and was not pressed last time,
//			 * In other words we just detected the on-press event.
//			 * This will signal the robot to start a MP */
//			if( (btns[6] == true) && (_btnsLast[6] == false) ) {
//				/* user just tapped button 6 */
//				_example.startMotionProfile();
//			}
//		}
//
//		/* save buttons states for on-press detection */
//		for(int i=1;i<10;++i)
//			_btnsLast[i] = btns[i];
//
//	}
//// THIS STUFF NEEDS TO GO	
//// CLIP HERE---^^^
	
	
	public void teleopPeriodic() {
		

		Navigator.getInstance();
		//Move the MecanumDrive
		Navigator.getMecanumDrive().move();
		
		dsio.checkSwitches();

		//Run WPILib commands
		Scheduler.getInstance().run();
		
		if(!SensorManager.isPublishing()){
			SensorManager.startPublishingToNetwork();
		}

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
		ArrayList<Pose> routeToGear, routeToDropOff;

		selectAlliance();
		selectAutonomousGearPlacement();
		selectAutonomousDropOffLocation();

		System.out.println("ALLIANCE: " + allianceSelected.toString());
		System.out.println("GEAR PLACEMENT LOCATION: " + autonomousGearPlacementSelected.toString());
		System.out.println("DROP OFF LOCATION: " + autonomousDropOffLocationSelected.toString());

		FieldPositions.initialize(allianceSelected);
		AutoRoutes.initialize(allianceSelected);

		switch (autonomousGearPlacementSelected) {
			case PLACE_GEAR_LEFT_AIRSHIP:
				System.out.println("[Autonomous Routing] Starting at left starting position, going to left gear hook.");
				routeToGear = AutoRoutes.leftStartToGear;

				switch (autonomousDropOffLocationSelected) {
					case BASELINE:
						System.out.println("[Autonomous Routing] Continuing on to baseline from left gear.");
						routeToDropOff = AutoRoutes.leftGearToBaseline;
						break;
					case GEAR_PICKUP:
						System.out.println("[Autonomous Routing] Continuing on to gear pickup from left gear.");
						routeToDropOff = AutoRoutes.leftGearToGearPickup;
						break;
					default:
						routeToDropOff = new ArrayList<>();
						break;
				}
				break;
			case PLACE_GEAR_RIGHT_AIRSHIP:
				System.out.println("[Autonomous Routing] Starting at right starting position, going to right gear hook.");
				routeToGear = AutoRoutes.rightStartToGear;

				switch (autonomousDropOffLocationSelected) {
					case BASELINE:
						System.out.println("[Autonomous Routing] Continuing on to baseline from right gear.");
						routeToDropOff = AutoRoutes.rightGearToBaseline;
						break;
					case GEAR_PICKUP:
						System.out.println("[Autonomous Routing] Continuing on to gear pickup from right gear.");
						routeToDropOff = AutoRoutes.rightGearToGearPickup;
						break;
					default:
						routeToDropOff = new ArrayList<>();
						break;
				}
				break;
			case PLACE_GEAR_CENTER_AIRSHIP:
				System.out.println("[Autonomous Routing] Starting at center starting position, going to center gear hook.");
				routeToGear = AutoRoutes.centerStartToGear;

				switch (autonomousDropOffLocationSelected) {
					case BASELINE:
						System.out.println("[Autonomous Routing] Continuing on to baseline from center gear.");
						routeToDropOff = AutoRoutes.centerGearToBaseline;
						break;
					case GEAR_PICKUP:
						System.out.println("[Autonomous Routing] Continuing on to gear pickup from center gear.");
						routeToDropOff = AutoRoutes.centerGearToGearPickup;
						break;
					default:
						routeToDropOff = new ArrayList<>();
						break;
				}
				break;
			case NONE:
				//autonomousCommand = new AutonomousCommand();
				return;
			default:
				routeToGear = new ArrayList<>();
				routeToDropOff = new ArrayList<>();
				break;
		}

		for (int i = 0; i < routeToGear.size(); i++) {
			System.out.println("X: " + routeToGear.get(i).x + " Y: " + routeToGear.get(i).y);
		}
		for (int i = 0; i < routeToDropOff.size(); i++) {
			System.out.println("X: " + routeToDropOff.get(i).x + " Y: " + routeToDropOff.get(i).y);
		}

		System.out.println("creating autonomous command group");
		autonomousCommand = new AutonomousCommandGroup(routeToGear, routeToDropOff);
	}
}