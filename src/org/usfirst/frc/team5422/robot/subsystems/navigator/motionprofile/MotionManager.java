package org.usfirst.frc.team5422.robot.subsystems.navigator.motionprofile;
import java.util.ArrayList;
import java.util.List;

import org.usfirst.frc.team5422.robot.subsystems.navigator.Navigator;
import org.usfirst.frc.team5422.robot.subsystems.navigator.RealBotMecanumDrive;
import org.usfirst.frc.team5422.robot.subsystems.sensors.GlobalMapping;
import org.usfirst.frc.team5422.robot.subsystems.sensors.SensorManager;
import org.usfirst.frc.team5422.utils.RegisteredNotifier;
import org.usfirst.frc.team5422.utils.RobotTalonConstants;
import org.usfirst.frc.team5422.utils.RobotTalonConstants.RobotDriveProfile;
import org.usfirst.frc.team5422.utils.SafeTalon;
import org.usfirst.frc.team5422.utils.SteamworksConstants.RobotModes;

import com.ctre.CANTalon.TalonControlMode;
import com.ctre.CANTalon.TrajectoryPoint;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class MotionManager {
	private List<double[][]> paths = new ArrayList<double[][]>();
	private List<ProfileDetails> profileDetails = new ArrayList<ProfileDetails>();
	private boolean loading = false;
	private boolean interrupt = false;
	private boolean rotateToAngle = false;
	private int batchSize = 256 * 4;
	private int currIndex = 0;
	private MotionControl control;
	private int numTalons;
	private static Instrumentation instrumentation;
	
	private RegisteredNotifier notifier = new RegisteredNotifier(new PeriodicRunnable(), "MotionManager");
	private final double [][] table = generateTable();
	private static final double deltaT = 0.01/60;
	private static final double scale = 500d/Math.PI;
	// TODO - make 1000 and 500 not magic numbers
	
	// For pid-based turning
	// TODO - get these tuned and make them constants in the appropriate file
    public PIDController turnController;
    double rotateToAngleRate;
    
    static final double kP = 0.40;
    static final double kI = 0.00;
    static final double kD = 0.18;	
    static final double kF = 0.00;
    double recentError = 0.0;
    
    static final double kToleranceDegrees = 1.0f;
    
	private boolean isLoaded = false;
	private SafeTalon[] talons;
	
	class PeriodicRunnable implements java.lang.Runnable {
		
		// The job of this thread is to push data into the top api buffer.
		// it pushes up to 'batchSize' points at a time (but never points 
		// from more than one path in a single pass)
		public void run() {
			// synchronize to avoid MT conflicts with the input of profiles
			
			// called from synchronized methods - which effectively sync(this)
			synchronized(this) {
				SmartDashboard.putNumber("Pos 0: ", control.getEncPos(0));
				SmartDashboard.putNumber("Pos 1: ", control.getEncPos(1));
				SmartDashboard.putNumber("Pos 2: ", control.getEncPos(2));
				SmartDashboard.putNumber("Pos 3: ", control.getEncPos(3));
				
				if (!rotateToAngle) {
					runMotionProfile();
				}
				else {
					runRotateToAngle();
				}
			}
		}
		
		private void runMotionProfile() {
			if (isLoaded) {
//					System.out.println("Ready to react to loaded buffers");
				control.enable();
			}
			
			if (loading == false) return;

			// Are we done?
			if(paths.isEmpty()) {  // TODO: need a more elegant stop condition??
				loading = false;
				isLoaded = true;
				// anything else? disable talons?
				return;
			}

			// If the last profile was marked "immediate" we need to abandon the current path and clean up
			if(interrupt) {
				currIndex = 0;
				control.clearMotionProfileTrajectories();
				//remove all other profiles from the list except the most recent one
				while(paths.size() > 1) {
					paths.remove(0);
					profileDetails.remove(0);
				}
				interrupt = false;
			}
			
			// Push the next section
			if(profileDetails.get(0).turn == true) pushTurn();
			else pushLinear();

			System.out.println("Points remaining: " + control.getPointsRemaining());
			
			// If we have pushed the entire path, remove it and let the next path run on the next time through
			// this could lead to a short cycle, but that is probably OK since we push points more quickly 
			// than they can run anyway.
			if(currIndex >= paths.get(0).length) {
				currIndex = 0;
				paths.remove(0);
				profileDetails.remove(0);
			}
		}

		private void runRotateToAngle() {
			if (loading == false) return;

			SmartDashboard.putNumber("Rotate to angle rate", rotateToAngleRate);
			
			// are we there yet?
			// exponential averaging of recent error values
			recentError = Math.abs(0.25 * recentError) + Math.abs(0.75 * turnController.getError()); 
			SmartDashboard.putNumber("Average error", recentError);  // not really average error
			if ( recentError < kToleranceDegrees) {
				SmartDashboard.putString("OnTarget", "True");
				System.out.println("On Target");
				loading = false;
				adjustPIDTurnRate(0);  // stop!
				turnController.disable();
				paths.remove(0);
				profileDetails.remove(0);
			}
			else {				
				SmartDashboard.putString("OnTarget", "False");
				adjustPIDTurnRate(rotateToAngleRate);
			}
		}
	}
	
	class PIDOutput implements edu.wpi.first.wpilibj.PIDOutput {
	    @Override
	    /* This function is invoked periodically by the PID Controller, */
	    /* based upon navX MXP yaw angle input and PID Coefficients.    */
	    public void pidWrite(double output) {
	        synchronized(MotionManager.this) {
				System.out.println("in PIDWrite - rotateToAngleRate = " + output);
				System.out.println("Error: " + MotionManager.this.turnController.getAvgError() +
								   " Output: " + MotionManager.this.turnController.get() + 
								   " Set: " + MotionManager.this.turnController.getSetpoint());
				rotateToAngleRate = output;
	        }
	    }
	    
	}

	
	// information that needs to be stored per profile path
	class ProfileDetails {
		boolean isPIDTurn;
		boolean turn;
		double theta;
		boolean direction;
		boolean done;
	}
	
	public MotionManager(SafeTalon [] talons) {
		control = new MotionControl(talons);
		this.talons = talons;
		numTalons = talons.length;
	}	
	
	// Theta is a heading change. 0 is straight ahead, 
	// +pi/2 is 90 degrees to the right (clockwise), 
	// -pi/2 is 90 degrees to the left (counterclockwise)
	public synchronized void rotateToAngle(double theta) {
		double turnAngle = theta * 180.0 / Math.PI;
		System.out.println("Started rotateAngle with angle = " + turnAngle);

		double [][] dummyPathArray = new double[0][0];
		AHRS ahrs = GlobalMapping.ahrs;
		ProfileDetails d = new ProfileDetails();

		rotateToAngle = true;
		// other details ignored if isPIDTurn = true;
		d.isPIDTurn = true;
		profileDetails.add(d);
		paths.add(dummyPathArray);

		// pidControl turning is independent of motion profiling. This just sets things up. Actual work happens elsewhere
		turnController = new PIDController(kP, kI, kD, kF, SensorManager.getGlobalMappingSubsystem().getPIDSource(), new PIDOutput());
		turnController.setInputRange(-180.0f,  180.0f);
        turnController.setOutputRange(-2.5, 2.5);
        turnController.setAbsoluteTolerance(kToleranceDegrees);
        turnController.setContinuous(true);
//        turnController.setToleranceBuffer(10);

        Navigator.getMecanumDrive().initializeDriveMode(RobotModes.TELEOP, RobotDriveProfile.VELOCITY);
        talons[RobotTalonConstants.DRIVE_TALON_LEFT_FRONT].changeControlMode(TalonControlMode.Speed);
		talons[RobotTalonConstants.DRIVE_TALON_LEFT_REAR].changeControlMode(TalonControlMode.Speed);
		talons[RobotTalonConstants.DRIVE_TALON_RIGHT_FRONT].changeControlMode(TalonControlMode.Speed);
		talons[RobotTalonConstants.DRIVE_TALON_RIGHT_REAR].changeControlMode(TalonControlMode.Speed);

        turnController.setSetpoint(turnAngle);
        turnController.enable();  //Go!

        loading = true; // hijack this handy variable to indicate that there is work to do
		notifier.startPeriodic(0.05);  // how often do we adjust to new set rate
	}
	
    // using rotateToAngleRate set above
    protected void adjustPIDTurnRate(double angleRate) {
    	// negative velocity translates to clockwise rotation - this is positive for NavX and
    	// for our reference rotation direction
    	double vel = -60.0 * angleRate;
    	
//		System.out.println("Adjust PIDTurnRate with angleRate = " + angleRate);
		// Note that the left and right wheels turning the same direction at the same speed causes
		// a rotation since the wheels face opposite directions.
    	talons[RobotTalonConstants.DRIVE_TALON_LEFT_FRONT].set(vel);
		talons[RobotTalonConstants.DRIVE_TALON_LEFT_REAR].set(vel);
		talons[RobotTalonConstants.DRIVE_TALON_RIGHT_FRONT].set(vel);
		talons[RobotTalonConstants.DRIVE_TALON_RIGHT_REAR].set(vel);	    	
    }

    /*
	 * Preconditions: All talons must be set to the following
	 * 
	 * reverseOutput(true)
	 * feedbackDevice = quadEncoder
	 * configEncoderCodesPerRev(2048)
	 * P = 0.08
	 * I = 0.0002
	 * D = 10.24
	 * IZone = 1500
	 * F = 0.16
	 * 
	 */
	
	public synchronized void pushProfile(double [][] pathArray, boolean immediate, boolean done) {
		ProfileDetails d = new ProfileDetails();
		d.done = done;
		d.turn = false;
		// other details ignored if turn is false
		profileDetails.add(d);
		rotateToAngle = false;

		interrupt = immediate;
		paths.add(pathArray);
		loading = true;

//		System.out.println("Starting controlThreads in pushProfile");
		notifier.startPeriodic(0.05);  // pushing batchsize points at a time
		control.startControlThread();
	}
		
	public synchronized void pushTurn(double theta, boolean immediate, boolean done) {
		ProfileDetails d = new ProfileDetails();
		d.done = done;
		d.theta = theta;
		d.turn = true;
		profileDetails.add(d);

		rotateToAngle = false;

		interrupt = immediate;
		paths.add(getTurnProfile(d));
		loading = true;

//		System.out.println("Starting controlThreads in pushTurn");
		notifier.startPeriodic(0.05);
		control.startControlThread();
	}
	
	/*
	 * Precondition: path array a path in the form of v(velocity of center of robot), theta(direction of motion of center of robot)
	 * 
	 * immediate = whether profile should start immediately or wait for other guy to end
	 * done = whether profile is last part(if robot should stop after or not)
	 */
	
	public double[][] getTurnProfile(ProfileDetails d) {
		double robotRadius = 15.00; //TODO: make constant, in inches (14.25 x 13.25)
		double wheelRadius = 3; //TODO: make constant, in inches
		double maxVel = 240; //RPM
		double ogTheta = d.theta;
		
		if(ogTheta > 0) ogTheta = Math.PI/2.0;
		else ogTheta = 3 * Math.PI/2.0;
		d.theta %= (2 * Math.PI);
		double tTheta = d.theta - Math.PI;
		if(tTheta > 0) {d.theta = Math.PI - tTheta; d.direction = true; }
		d.direction = false;
		double dist = robotRadius * d.theta/(2.0 * Math.PI * wheelRadius);
		System.out.println("ogTheta: " + ogTheta);
		return TrapezoidalProfile.getTrapezoidZero(dist, maxVel, ogTheta, getRobotRPM());
	}

	public void pushTurn() {
		System.out.println("pushTurn started");
		//clear existing profiles
		double [] positions = new double[4];
		TrajectoryPoint pt = new TrajectoryPoint();
		double[][] pathArray = paths.get(0);
		boolean direc = profileDetails.get(0).direction;
		boolean done = profileDetails.get(0).done;
		
		for(int i = currIndex; i < currIndex + batchSize; i ++) {
			if(i >= pathArray.length) break;
		
			int colIndex = (int)(pathArray[i][1] * 500/Math.PI);
			
			for(int j = 0; j < numTalons; j ++) {
				pt.position = 0;
				pt.timeDurMs = 10;
				pt.velocityOnly = false;
				pt.zeroPos = (i == currIndex); //needed for successive profiles, only first pt should be set to true
				pt.velocity = pathArray[i][0] * table[j][colIndex]; //TODO: change signs as appropriate for turning
				if((j == 0 || j == 2) && direc) pt.velocity = -pt.velocity;
				else if((j == 1 || j == 3) && !direc) pt.velocity = -pt.velocity;
				positions[j] += pt.velocity * deltaT; 
 				pt.position = positions[j];
 				pt.isLastPoint = false;//(done && (i + 1 == pathArray.length));  // TODO
				control.pushMotionProfileTrajectory(j, pt);
			}
		}
		
		if (currIndex == 0) startProfile();
		currIndex += batchSize;
		
	}
	
	public void pushLinear() {
		System.out.println("pushLinear started");
		double[][] pathArray = paths.get(0);
		TrajectoryPoint pt = new TrajectoryPoint();
		double [] positions = new double[4];
		boolean done = profileDetails.get(0).done;

		for(int i = currIndex; i < currIndex + batchSize; i ++) {
			if(i >= pathArray.length) break;
		
			int colIndex = (int)(((pathArray[i][1] + 2*Math.PI) % (2*Math.PI)) * 500/Math.PI);
			
			for(int j = 0; j < numTalons; j ++) {
				pt.position = 0;
				pt.timeDurMs = 10;
				pt.velocityOnly = false;
				pt.zeroPos = (i == currIndex); //needed for successive profiles, only first pt should be set to true
				pt.velocity = pathArray[i][0] * table[j][colIndex];
				positions[j] += pt.velocity * deltaT; 
 				pt.position = positions[j]; 
				// TODO - probably want the commented setting, but need to test it.
 				pt.isLastPoint = false; //(done && ( (i + 1) == pathArray.length));  //TODO
				control.pushMotionProfileTrajectory(j, pt);
			}
		}

		currIndex += batchSize;
	}
	
	public void startProfile() {
		control.enable();
	}
	
	public void endProfile() {
		control.disable();
	}
	
	public void shutDownProfiling() {
		control.stopControlThread();
		control.shutDownProfiling();
	}
	
	// Intended to be called by the command thread
	// the poll interval doesn't affect the 
	public void waitUntilProfileFinishes(long pollMillis) {
		boolean wait = false;
		int count = 0;
		
		while(true) {
			synchronized(this) {
				if (loading || control.getPointsRemaining() > 0) {
					wait = true;
				}					
			}
		
			if (wait) {
				wait = false;  // reset
				if ( count % 10 == 0) {
					System.out.println("Waited " + count + " intervals");
				}

				try {
					Thread.sleep(pollMillis);
				} catch (InterruptedException e) {
					System.out.println("Ignoring Interrupted exception in waitUntilProfileFinishes: " + e.getMessage());
				}
			} else {
				System.out.println("Return from waitUntilProfileFinishes after waiting " + count + " interval(s)");
				if (!rotateToAngle) {
					shutDownProfiling();
				}
				return;
			}
			
			count++;
			wait = false;
		}
	}

	public double getRobotRPM() {
		int[] vels = getEncVels();
		double root = Math.sqrt(2);
		double x = 0;
		double y = 0;
		for(int i = 0; i < vels.length; i ++) {
			if(i == 0 || i == 3) x -= vels[i] / root;
			else x += vels[i] / root;
			y += vels[i] / root;
		}
		x /= vels.length;
		y /= vels.length;
		//double[] solution = {Math.sqrt(x * x + y * y) * 10.0 * 60.0 / 8192.0, Math.atan(y / x)};
		return Math.sqrt(x * x + y * y) * 10.0 * 60.0 / 8192.0;
	}
	
	private int[] getEncVels() {
		int[] vels = new int[numTalons];
		for(int i = 0; i < numTalons ; i ++) {
			vels[i] = control.getEncVel(i);
		}
		return vels;
	}
	

	//helper methods for generating table
	private double [][] generateTable() {
		double [][] table = new double[4][1000];
		table[0] = getFuncs1(true);
		table[1] = getFuncs2(true);  //FOR TURN: false
		table[2] = getFuncs2(false); //FOR TURN: true
		table[3] = getFuncs1(false);
		return table;
	}

	private double[] getFuncs1(boolean neg) {
		double[] temp =  new double[1000];
		for(int i = 0; i < 1000; i ++) {
			if(neg) temp[i] = -Math.sqrt(2) * (Math.sin(2 * Math.PI  * i / 1000.0 + Math.PI / 2.0) - Math.cos(2 * Math.PI * i / 1000.0 + Math.PI / 2.0));
			else temp[i] = Math.sqrt(2) * (Math.sin(2 * Math.PI * i / 1000.0 + Math.PI / 2.0) - Math.cos(2 * Math.PI * i / 1000.0 + Math.PI / 2.0));
		}
		return temp;
		
	}
	
	private double[] getFuncs2(boolean neg) {
		double[] temp = new double[1000];
		for(int i = 0; i < 1000; i ++) {
			if(neg) temp[i] = -Math.sqrt(2) * (Math.sin(2 * Math.PI * i / 1000.0 + Math.PI / 2.0) + Math.cos(2 * Math.PI * i / 1000.0 + Math.PI / 2.0));
			else temp[i] = Math.sqrt(2) * (Math.sin(2 * Math.PI * i / 1000.0 + Math.PI / 2.0) + Math.cos(2 * Math.PI * i / 1000.0 + Math.PI / 2.0));
		}
		return temp;
	}
	
}