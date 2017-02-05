package org.usfirst.frc.team5422.robot.sensors;

import org.usfirst.frc.team5422.robot.subsystems.navigator.Navigator;
import org.usfirst.frc.team5422.subsystems.RunnableNotifier;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.I2C.Port;

public class GlobalMapping extends RunnableNotifier{
	
	final double SQRT_2 = Math.sqrt(2);
	final double PI = Math.PI;
	
	
	//encodervalues are dummies
	long enc_fl;
	long enc_fr;
	long enc_bl;
	long enc_br;
	
	long prev_enc_fl;
	long prev_enc_fr;
	long prev_enc_bl;
	long prev_enc_br;
	
	double x;
	double y;
	
	AHRS ahrs = new AHRS(Port.kMXP);
	
	GlobalMapping(){
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
	
	void resetPose(double X, double Y, double theta){//meters, meters, radians
		x = X;
		y = Y;
		ahrs.setAngleAdjustment(theta*180.0/PI-ahrs.getAngle());
	}
	
	void updatePose(){
		
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
		double dRobotX = (d_enc_fr + d_enc_bl - (d_enc_fl + d_enc_br))/4/SQRT_2;
		double dRobotY = (d_enc_fl + d_enc_bl - (d_enc_fl + d_enc_br))/4/SQRT_2;
		
		double angle = getTheta();
		
		double dFieldX = dRobotX * Math.cos(angle);
		double dFieldY = dRobotY * Math.cos(angle);
		
		x += dFieldX;
		y += dFieldY;
		
	}
	
	double getX(){
		return x;
	}
	
	double getY(){
		return y;
	}
	
	double getTheta(){//in radians
		return ahrs.getAngle()*PI/180.0;
	}
}
