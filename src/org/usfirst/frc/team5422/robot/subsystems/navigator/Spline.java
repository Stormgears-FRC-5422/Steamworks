package org.usfirst.frc.team5422.robot.subsystems.navigator;

import java.util.ArrayList;
import java.util.Collections;

public class Spline {
	
	/* a spline is a set of parametric cubic polynomial for Cartesian coordinates, each set in the form:
	 * 
	 * 		x(t) = a_x*t^3 + b_x*t^2 + c_x*t + d_x
	 *		y(t) = a_y*t^3 + b_y*t^2 + c_y*t + d_y
	 * 
	 * This describes a path form an initial pose (x_i, y_i, v_xi, v_yi) to a final pose (x_f, y_f, v_xf, v_yf).
	 * A path that describes continuous movement between two poses is defined to be a segment.
	 * 
	 * A spline is a collection of ordered segments such that the initial pose of any segment 
	 * is equal to the final pose of its preceding segment, 
	 * where the final and initial segment does not share its end and beginning pose, respectively
	 * 
	 * A spline has n poses {1,2,3,...,n}, the first pose is the current pose of the robot
	 * the number of segments is always n-1 {1,2,3,...,n-1}
	 */
	
	ArrayList<Pose> poses;
	
	public int getNumPoses(){
		
		return this.poses.size();
	}
	
	public int getNumSegments(){
		
		return this.poses.size() - 1;
	}
	
	public void printSplinePos(String note){
		System.out.print("Spline printout:" + note +"\n");
		for(Pose pose: poses){
			System.out.println("\t( " + pose.x + ", " + pose.y + ")");
		}
	}
	
	//constructor
	public Spline(ArrayList<Pose> initial_poses){
		
		this.poses = initial_poses;
		
	}
	
	//constructor
	public Spline(Pose[] initial_poses){
		Collections.addAll(poses, initial_poses);
	}
	
    //these are the coefficients (a,b,c,d) of the cubic polynomials
	private double a(int seg, int axis) { //axis 0 means x, axis 1 means y
		
    	Pose pose_initial = this.poses.get(seg-1);
    	Pose pose_final = this.poses.get(seg);
    	
    	if(axis==0){
    		
    		return pose_final.v_x + pose_initial.v_x -2*pose_final.x + 2*pose_initial.x;
    		
    	}else{
    		
    		return pose_final.v_y + pose_initial.v_y -2*pose_final.y + 2*pose_initial.y;
    	}
	}
    
    private double b(int seg, int axis) {
    	
    	Pose pose_initial = this.poses.get(seg-1);
    	Pose pose_final = this.poses.get(seg);
    	
    	if(axis==0){
    		
    		return -pose_final.v_x -2*pose_initial.v_x + 3*pose_final.x - 3*pose_initial.x;
    		
    	}else{
    		
    		return -pose_final.v_y -2*pose_initial.v_y + 3*pose_final.y - 3*pose_initial.y;
    	}
	}
    
    private double c(int seg, int axis) {
    	
    	Pose pose_initial = this.poses.get(seg-1);
    	
    	if(axis==0){
    		
    		return pose_initial.v_x;
    		
    	}else{
    		
    		return pose_initial.v_y;
    	}
	}
    
    private double d(int seg, int axis) {
    	
    	Pose pose_initial = this.poses.get(seg-1);
    	
    	if(axis==0){
    		
    		return pose_initial.x;
    		
    	}else{
    		
    		return pose_initial.y;
    	}
	}
    
    /* returns movement function of any segment of spline, 
     * with internal parameter u to each segment, 
     * u must be between 0.0 and 1.0
     */
    
    //position
    public double x(int seg, double u) {
    	
		return a(seg, 0)*(u*u*u) + b(seg, 0)*(u*u) + c(seg, 0)*u + d(seg, 0);
	}
    
    public double y(int seg, double u) {
    	
		return a(seg, 1)*(u*u*u) + b(seg, 1)*(u*u) + c(seg, 1)*u + d(seg, 1);
	}
    
    //velocity
    public double vx(int seg, double u) {
    	
    	return 3*a(seg, 0)*(u*u) + 2*b(seg, 0)*u + c(seg, 0);
	}
    
    public double vy(int seg, double u) {
    	
		return 3*a(seg, 1)*(u*u) + 2*b(seg, 1)*u + c(seg, 1);
	}
    
    //acceleration
    public double ax(int seg, double u) {
    	
    	return 6*a(seg, 0)*u + 2*b(seg, 0);
	}
    
    public double ay(int seg, double u) {
    	
		return 6*a(seg, 1)*u + 2*b(seg, 1);
	}
    
    //methods to update spline poses
    public void updatePose(int index, Pose argPose){
    	
    	poses.remove(index);
    	poses.add(index, argPose);
    }
    
    public void addPose(int index, Pose argPose){
    	
    	this.poses.add(index, argPose);
    }
    
    public void removePose(int index){
    	
    	this.poses.remove(index);
    }
}
