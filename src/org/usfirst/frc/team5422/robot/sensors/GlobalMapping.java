package org.usfirst.frc.team5422.robot.sensors;

import org.usfirst.frc.team5422.robot.subsystems.navigator.Navigator;
import org.usfirst.frc.team5422.subsystems.RunnableNotifier;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.I2C.Port;

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
	
	static long prev_enc_fl;
	static long prev_enc_fr;
	static long prev_enc_bl;
	static long prev_enc_br;
	
	static double x;
	static double y;
	
	static AHRS ahrs = new AHRS(Port.kMXP);
	
	public GlobalMapping(){
		super("GlobalMapping", 0.01);
		
		enc_fl = 0;
		enc_fr = 0;
		enc_bl = 0;
		enc_br = 0;
		
		prev_enc_fl = 0;
		prev_enc_fr = 0;
		prev_enc_bl = 0;
		prev_enc_br = 0;
		
		resetPose(0, 0, 0);
		
	}
	
	@Override
	public void run(){
		updatePose();
		this.networkTable.putNumber("x-position", x);
		this.networkTable.putNumber("y-position", y);
		//TODO::
	};
	
	static void resetPose(double X, double Y, double theta){//meters, meters, radians
		x = X;
		y = Y;
		ahrs.setAngleAdjustment(theta*180.0/PI-ahrs.getAngle());
	}
	
	static void updatePose(){
		
		//TODO:: get encoder values
		
		enc_fl = Navigator.getInstance().talons[0].getEncPosition();
		enc_fr = Navigator.getInstance().talons[1].getEncPosition();
		enc_bl = Navigator.getInstance().talons[3].getEncPosition();
		enc_br = Navigator.getInstance().talons[2].getEncPosition();
		
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
