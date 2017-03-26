package org.usfirst.frc.team5422.robot.subsystems.navigator;

public class Pose {

	public double x;
	public double y;
	public double theta;
	public double v_x;
	public double v_y;

	public static Pose createPoseTheta(double x, double y, double theta, double v) {
		Pose p = new Pose();
		p.x = x;
		p.y = y;
		p.v_x = Math.cos(theta) * v;
		p.v_y = Math.sin(theta) * v;
		p.theta = theta;

		return p;
	}

	public static Pose createCopy(Pose p) {
		Pose q = new Pose();

		q.x = p.x;
		q.y = p.y;
		q.v_x = p.v_x;
		q.v_y = p.v_y;
		q.theta = p.theta;

		return q;
	}

	public static Pose createPose(double x, double y, double v_x, double v_y) {
		Pose p = new Pose();

		p.x = x;
		p.y = y;
		p.v_x = v_x;
		p.v_y = v_y;

		return p;
	}

	public void print() {
		System.out.println("( " + this.x + ", " + this.y + ", " + this.v_x + ", " + this.v_y + " )");
	}

	public void setV(float v) {
		this.v_x = Math.cos(theta) * v;
		this.v_y = Math.sin(theta) * v;
	}
}