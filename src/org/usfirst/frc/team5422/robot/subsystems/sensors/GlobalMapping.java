package org.usfirst.frc.team5422.robot.subsystems.sensors;

import org.usfirst.frc.team5422.robot.subsystems.RunnableSubsystem;
import org.usfirst.frc.team5422.robot.subsystems.navigator.Drive;
import org.usfirst.frc.team5422.utils.NetworkConstants;
import org.usfirst.frc.team5422.utils.RobotTalonConstants;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GlobalMapping extends RunnableSubsystem {

	static final double SQRT_2 = Math.sqrt(2);
	static final int ENCODER_RESOLUTION = 2048;//?????
	static final double PI = Math.PI;
	static final double RADIANS_PER_TICK = 2*PI/(float)ENCODER_RESOLUTION;
	static final double WHEEL_RADIUS = 0.479;//meters
	
	//encodervalues are dummies
	static long enc_fl;
	static long enc_fr;
	static long enc_bl;
	static long enc_br;
	
	static double currTimeStamp;
	
	static long prev_enc_fl;
	static long prev_enc_fr;
	static long prev_enc_bl;
	static long prev_enc_br;
	
	static double prevTimeStamp;
	
	static double x;
	static double y;
	static double vx;
	static double vy;
	
	static double smoothingFactor = 0.9;
	
	static public AHRS ahrs = new AHRS(Port.kMXP);
	
	public GlobalMapping() {
		super(NetworkConstants.GLOBAL_MAPPING);
		
		enc_fl = 0;
		enc_fr = 0;
		enc_bl = 0;
		enc_br = 0;
		
		prev_enc_fl = 0;
		prev_enc_fr = 0;
		prev_enc_bl = 0;
		prev_enc_br = 0;
		  
		prevTimeStamp = Timer.getFPGATimestamp();
		
//		resetPose(0, 0, Math.PI/2);
		
		AHRS.BoardYawAxis yawAxis = ahrs.getBoardYawAxis();
//		yawAxis.up = false;
		ahrs.zeroYaw();
		SmartDashboard.putString("YawAxisDirection", yawAxis.up ? "Up" : "Down");
		SmartDashboard.putNumber("YawAxis", yawAxis.board_axis.getValue());
		SmartDashboard.putNumber("NavX Angle", ahrs.getAngle());
		SmartDashboard.putNumber("NavX Yaw", ahrs.getYaw());
	}
	
	@Override
	public void run(){		
		updatePose();
		
		SmartDashboard.putNumber("NavX Angle", ahrs.getAngle());
		SmartDashboard.putNumber("NavX Yaw", ahrs.getYaw());
		networkPublish(NetworkConstants.GP_THETA, getTheta());
		networkPublish(NetworkConstants.GP_X, x);
		networkPublish(NetworkConstants.GP_Y, y);
		networkPublish(NetworkConstants.GP_VX, vx);
		networkPublish(NetworkConstants.GP_VY, vy);
	};
	
	static void resetPose(double X, double Y, double theta){//meters, meters, radians
		x = X;
		y = Y;
		vx = 0;
		vy = 0;
		
		ahrs.setAngleAdjustment(theta*180.0/PI-ahrs.getAngle());
	}
	
	public static void updatePose(){
		//TODO:: get encoder values
		enc_fl = Drive.talons[RobotTalonConstants.DRIVE_TALON_LEFT_FRONT].getEncPosition();
		enc_fr = Drive.talons[RobotTalonConstants.DRIVE_TALON_RIGHT_FRONT].getEncPosition();
		enc_bl = Drive.talons[RobotTalonConstants.DRIVE_TALON_LEFT_REAR].getEncPosition();
		enc_br = Drive.talons[RobotTalonConstants.DRIVE_TALON_RIGHT_REAR].getEncPosition();
		
		int d_enc_fl = (int) (enc_fl - prev_enc_fl);
		int d_enc_fr = (int) (enc_fr - prev_enc_fr);
		int d_enc_bl = (int) (enc_bl - prev_enc_bl);
		int d_enc_br = (int) (enc_br - prev_enc_br);
		
		prev_enc_fl = enc_fl;
		prev_enc_fr = enc_fr;
		prev_enc_bl = enc_bl;
		prev_enc_br = enc_br;
		
		//robot's coordinate frame
		double dRobotX = (d_enc_fr + d_enc_bl - (d_enc_fl + d_enc_br))*RADIANS_PER_TICK*WHEEL_RADIUS/4.f;
		double dRobotY = (d_enc_fl + d_enc_bl + (d_enc_fl + d_enc_br))*RADIANS_PER_TICK*WHEEL_RADIUS/4.f;
		
		double angle = getTheta();
		
		double dFieldX = dRobotX * Math.cos(angle);
		double dFieldY = dRobotY * Math.cos(angle);
		
		x += dFieldX;
		y += dFieldY;
		
		
		//velocity stuff
		double dt = Timer.getFPGATimestamp() - prevTimeStamp;
		double temp_vx = dFieldX / dt; 
		double temp_vy = dFieldY / dt; 
		
		//exponential filtering
		vx = smoothingFactor*temp_vx + (1 - smoothingFactor)*vx;
		vy = smoothingFactor*temp_vy + (1 - smoothingFactor)*vy;
				
		prevTimeStamp = Timer.getFPGATimestamp();
		
	}
	
	static double getX(){
		return x;
	}
	
	static double getY(){
		return y;
	}
	
	static double getTheta(){//in radians
		return ahrs.getAngle()*PI/180.0;
	}

	public PIDSource getPIDSource() {
		return ahrs;
	}
}
