package org.usfirst.frc.team5422.robot;

import org.usfirst.frc.team5422.robot.subsystems.climber.ClimberIntake;
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

public class Robot extends IterativeRobot {
	//subsystems
	public static Navigator navigatorSubsystem;
	public static Shooter shooterSubsystem;
	public static ClimberIntake climberIntakeSubsystem;
	public static Manipulator gearManipulatorSubsystem;
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
        climberIntakeSubsystem = new ClimberIntake();		
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
	
	public void teleopInit() {
        System.out.println("teleop init started.");
        if (autonomousCommand != null) {
        	autonomousCommand.cancel();
        }
	}

	public void disabledInit() {
		
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
		climberIntakeSubsystem.climb(climberVelocity, climberButtonPressed );
		boolean intakeButtonPressed = DSIO.buttonBoard.getRawButton(SteamworksConstants.INTAKE_ORANGE_SWITCH_ID);
//		climberIntakeSubsystem.takeIn(climberButtonPressed, intakeButtonPressed);
		
        //Run WPILib commands
        Scheduler.getInstance().run();

	}
	
	public void disabledPeriodic() {
		
	}
	
	public void robotPeriodic() {
		
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