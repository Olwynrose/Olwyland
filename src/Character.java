
public class Character extends Hitbox {

	/* Parameters */
	private double width;
	private double height;
	private double tanAlpha; 
	
	public int state; /* 0:fly , 
						  1:floor */
	private double maxSpeed;		// max speed for the free fall
	private double frictionCoef;	// friction coefficient
	
	public Character() {
		width = 35;
		height = 55;
		tanAlpha = 0.75;
		
		state = 0;
		maxSpeed = 25;
		frictionCoef = Main.gravity / maxSpeed;
		
		setPoints();
		this.position = new double[2];
		this.position[0] = 100;
		this.position[1] = 100;
		this.speed = new double[2];
	}
	
	
	public void update() {
		/**
		 * verify state & update speed
		 * move
		 */
		
		updateState();
		move();
	}
	
	private void move() {
		tMin = 1;
		for (int i = 0 ; i < Main.nbSceneries ; i++) {
			intersect(Main.sceneries[i]);
		}
		
		position[0] = position[0] + tMin * speed[0] - 0.0001;
		position[1] = position[1] + tMin * speed[1];
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
		 * CREATION DE LA HITBOX DU PERSONNAGE
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
	
	// DETECTION DE CONTACT
	
	private boolean contactFloor() {
		/**
		 * 
		 */
		double t = -1;
		for (int i = 0 ; i < Main.nbSceneries ; i++) {
			for (int j = 0 ; j < Main.sceneries[i].getNbPoints() - 1 ; j++) {
				t = lineIntersection(1, 0, 
						position[0] + points[0][0] - 0.5, position[1] + points[0][1],
						Main.sceneries[i].position[0] + Main.sceneries[i].points[j][0], Main.sceneries[i].position[1] + Main.sceneries[i].points[j][1],
						Main.sceneries[i].position[0] + Main.sceneries[i].points[j+1][0], Main.sceneries[i].position[1] + Main.sceneries[i].points[j+1][1]);
				if (t >= 0 && t <= 1) {
					return true;
				}
			}
		}
		return false;
	}
	
	// ACTIONS EN CAS DE CONTACT
	
	private void fly() {
		speed[0] = speed[0] + Main.gravity - frictionCoef * speed[0];
		speed[1] = speed[1] - frictionCoef * speed[1];
	}
	
	private void walk() {
		
	}
	
}
