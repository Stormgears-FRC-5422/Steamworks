package spline;

public class Pose {
	
	public double x;
	public double y;
	public double v_x;
	public double v_y;
	
	public void print(){
		System.out.println("( " + this.x + ", " + this.y + ", " + this.v_x + ", " + this.v_y+ " )");
	}
	
	Pose(double x, double y, double v_x, double v_y){
		this.x = x;
		this.y = y;
		this.v_x = v_x;
		this.v_y = v_y;
	};

}
