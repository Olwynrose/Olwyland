
public class Character extends Hitbox {

	/* Parameters */
	private double width;
	private double height;
	private double tanAlpha; 
	
	public Character() {
		width = 35;
		height = 55;
		tanAlpha = 0.75;
		
		setPoints();
		this.position = new double[2];
		this.position[0] = 100;
		this.position[1] = 100;
		this.speed = new double[2];
	}
	public void setPoints() {
		this.points = new double[6][2];
		this.nbPoints = 6;
		
		this.points[0][0] = 0;
		this.points[0][1] = 0;
		
		this.points[1][0] = - tanAlpha * (width / 2);
		this.points[1][1] = width / 2;
		
		this.points[2][0] = - height;
		this.points[2][1] = width / 2;
		
		this.points[3][0] = - height;
		this.points[3][1] = - width / 2;
		
		this.points[4][0] = - tanAlpha * (width / 2);
		this.points[4][1] = - width / 2;
		
		this.points[5][0] = 0;
		this.points[5][1] = 0;
	}
}
