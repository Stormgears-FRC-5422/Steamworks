package org.usfirst.frc.team5422.robot.subsystems.navigator;

public class Pose {

    public double x;
    public double y;
    public double theta;
    public double v_x;
    public double v_y;

    public Pose(double x, double y, double v_x, double v_y) {
        this.x = x;
        this.y = y;
        this.v_x = v_x;
        this.v_y = v_y;
    }

    public Pose(double x, double y, double theta, float v) {
        this.x = x;
        this.y = y;
        this.v_x = Math.cos(theta) * v;
        this.v_y = Math.sin(theta) * v;
    }

    public Pose(Pose p) {
        this.x = p.x;
        this.y = p.y;
        this.v_x = p.v_x;
        this.v_y = p.v_y;
    }

    public void print() {
        System.out.println("( " + this.x + ", " + this.y + ", " + this.v_x + ", " + this.v_y + " )");
    }

    public void setV(float v) {
        this.v_x = Math.cos(theta) * v;
        this.v_y = Math.sin(theta) * v;
    }
}