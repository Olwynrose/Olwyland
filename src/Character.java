
public class Character extends Hitbox {

	/* Parameters */
	private double width;
	private double height;
	private double tanAlpha; 
	
	public int state; 				// state of the character
	/* 0:fly , 1:floor */
	private double maxSpeed;		// max speed for the free fall
	private double moveSpeed;		// walk speed
	private double jumpSpeed;		// speed with which the character jumps
	private double maxJump;			// max high of a jump
	private double frictionCoef;	// friction coefficient
	private double cosFloorSlope;	// angle of the floor segment you stand on (rad)
	
	public Character() {
		width = 35;
		height = 55;
		tanAlpha = 0.75;
		
		state = 0;
		maxSpeed = 25;
		moveSpeed = 7;
		maxJump = 90;
		jumpSpeed = Math.sqrt(2 * Main.gravity * maxJump);
		frictionCoef = Main.gravity / maxSpeed;
		
		if (Main.debug == 4) {
			System.out.println(frictionCoef);
		}
		if (Main.debug == 5) {
			System.out.println(jumpSpeed);
		}
		
		setPoints();
		this.position = new double[2];
		this.position[0] = 100;
		this.position[1] = 100;
		this.speed = new double[2];
	}
	
	
	public void update() {
		updateState();
		move();
	}
	
	private void move() {
		tMin = 1;
		for (int i = 0 ; i < Main.nbSceneries ; i++) {
			intersect(Main.sceneries[i]);
		}

		this.speed[0] = tMin * this.speed[0];
		this.speed[1] = tMin * this.speed[1];
		this.position[0] = this.position[0] + this.speed[0] - 0.001;
		this.position[1] = this.position[1] + this.speed[1];
	}
	
	private void updateState() {
		if (contactFloor()) {
			state = 1;
			walk();
		}
		else {
			state = 0;
			fly();
		}
		
		if (Main.debug == 2) {
			System.out.println(state);
		}
	}
	
	public void setPoints() {
		/**
		 * character's hitbox creation
		 */
		
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
	
	// CONTACT DETECTION
	
	private boolean contactFloor() {
		double t = -1;
		for (int i = 0 ; i < Main.nbSceneries ; i++) {
			for (int j = 0 ; j < Main.sceneries[i].getNbPoints() - 1 ; j++) {
				t = lineIntersection(1, 0, 
						position[0] + points[0][0] - 0.5, position[1] + points[0][1],
						Main.sceneries[i].position[0] + Main.sceneries[i].points[j][0], Main.sceneries[i].position[1] + Main.sceneries[i].points[j][1],
						Main.sceneries[i].position[0] + Main.sceneries[i].points[j+1][0], Main.sceneries[i].position[1] + Main.sceneries[i].points[j+1][1]);
				if (t >= 0 && t <= 1) {
					cosFloorSlope = (Math.atan2(Main.sceneries[i].points[j+1][0] - Main.sceneries[i].points[j][0], 
							Main.sceneries[i].points[j+1][1] - Main.sceneries[i].points[j][1]))%Math.PI;
					if (Main.debug == 3) {
						System.out.println(cosFloorSlope);
					}
					return true;
				}
			}
		}
		return false;
	}
	
	// ACTIONS
	
	private void fly() {
		
		this.speed[0] = this.speed[0] + Main.gravity - frictionCoef * this.speed[0];
		this.speed[1] = this.speed[1] - frictionCoef * this.speed[1];
				
		if(Main.keyRight){
			this.speed[1] = moveSpeed;
		}
		if(Main.keyLeft){
			this.speed[1] = - moveSpeed;
		}
	}
	
	
	private void walk() {
		// Reset of the walk speed to stop if any key is pressed
		this.speed[0] = 0;
		this.speed[1] = 0;
		
		if(Main.keyRight){
			this.speed[1] = Math.sqrt(1 - Math.pow(cosFloorSlope, 2)) * moveSpeed;
			this.speed[0] = cosFloorSlope * moveSpeed;
		}
		if(Main.keyLeft) {
			this.speed[1] = - Math.sqrt(1 - Math.pow(cosFloorSlope, 2)) * moveSpeed;
			this.speed[0] = - cosFloorSlope * moveSpeed;
		}
		if(Main.keyUp) {
			this.speed[0] = - jumpSpeed;
		}
	}
	
}
