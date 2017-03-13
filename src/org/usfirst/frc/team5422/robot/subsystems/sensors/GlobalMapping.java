package org.usfirst.frc.team5422.robot.subsystems.sensors;

import org.usfirst.frc.team5422.robot.subsystems.navigator.Drive;
import org.usfirst.frc.team5422.robot.subsystems.navigator.Navigator;
import org.usfirst.frc.team5422.robot.subsystems.RunnableNotifier;
import org.usfirst.frc.team5422.utils.NetworkConstants;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.Timer;

public class GlobalMapping extends RunnableNotifier{

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
	
	static AHRS ahrs = new AHRS(Port.kMXP);
	
	public GlobalMapping(){
		super(NetworkConstants.GLOBAL_MAPPING, 0.001);
		
		enc_fl = 0;
		enc_fr = 0;
		enc_bl = 0;
		enc_br = 0;
		
		prev_enc_fl = 0;
		prev_enc_fr = 0;
		prev_enc_bl = 0;
		prev_enc_br = 0;
		
		prevTimeStamp = Timer.getFPGATimestamp();
		
		resetPose(0, 0, Math.PI/2);
		
		
	}
	
	@Override
	public void run(){
		
		synchronized(Navigator.talonLock) {
			//updatePose();
		}
		
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
		
		Navigator.getMecanumDrive();
		//TODO:: get encoder values
		enc_fl = Drive.talons[0].getEncPosition();
		enc_fr = Drive.talons[1].getEncPosition();
		enc_bl = Drive.talons[2].getEncPosition();
		enc_br = Drive.talons[3].getEncPosition();
		
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
}
