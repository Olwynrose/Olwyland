
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
	private double slideCoef;
	
	private int inactiveTime;
	private int activeJump;	
	private int activeLeft;
	private int activeRight;
	
	
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
		slideCoef = 0.8;
		
		inactiveTime = 10;
		activeLeft = 0;
		activeRight = 0;
		activeJump = 0;
		
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
		
		if (Main.debug == 6) {
			System.out.println("activeLeft : " + activeLeft);
			System.out.println("activeRight : " + activeRight);
			System.out.println("activeJump : " + activeJump);
		}
		
		if (activeLeft > 0) {
			activeLeft--;
		}
		if (activeRight > 0) {
			activeRight--;
		}
		if (activeJump > 0) {
			activeJump--;
		}
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
		if (Main.debug == 7 && Main.keySpace == true) {
			debugFly();
		}
		else {
			if (contactTop())
			{
				state = 2;
				reboundTop();
			}
			else {
				if (contactLeft()) {
					state = 3;
					reboundLeft();
				}
				else {
					if (contactRight()) {
						state = 4;
						reboundRight();
					}
					else {
						if (contactBotLeft()) {
							state = 5;
							reboundBotLeft();
						} 
						else {
							if (contactBotRight()) {
								state = 6;
								reboundBotRight();
							}
							else {
								if((contactSlideLeft() || contactSlideRight()) && state == 1) {
									if(contactSlideLeft()){
										state = 2;
										slideLeft();
									}
									else {
										if(contactSlideRight()){
											state = 3;
											slideRight();
										}
									}
								}
								else {
									if((contactSlideLeft() || contactSlideRight()) && contactFloor()) {
										if(contactSlideLeft()){
											state = 9;
											slideFloor();
										}
										else {
											if(contactSlideRight()){
												state = 10;
												slideFloor();
											}
										}
									}
									else {
										if(contactFloor()){
											state = 1;
											walk();
										}
										else {
											if(contactSlideLeft()){
												state = 2;
												slideLeft();
											}
											else {
												if(contactSlideRight()){
													state = 3;
													slideRight();
												}
												else {
													state = 0;
													fly();
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
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
					cosFloorSlope = Math.abs(Main.sceneries[i].points[j+1][0] - Main.sceneries[i].points[j][0]) / 
							Math.sqrt((Main.sceneries[i].points[j+1][0] - Main.sceneries[i].points[j][0])*(Main.sceneries[i].points[j+1][0] - Main.sceneries[i].points[j][0]) + 
									+ (Main.sceneries[i].points[j+1][1] - Main.sceneries[i].points[j][1])*(Main.sceneries[i].points[j+1][1] - Main.sceneries[i].points[j][1]));
					if(Math.signum(Main.sceneries[i].points[j+1][0] - Main.sceneries[i].points[j][0])*
							Math.signum(Main.sceneries[i].points[j+1][1] - Main.sceneries[i].points[j][1])<0) {
						cosFloorSlope = - cosFloorSlope;
					}
					if (Main.debug == 3) {
						System.out.println(cosFloorSlope);
					}
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean contactBotLeft() {
		tMin = 1;
		for (int i = 0 ; i < Main.nbSceneries ; i++) {
			for (int j = 0 ; j < Main.sceneries[i].getNbPoints() - 1 ; j++) {
				t = lineIntersection(points[4][0] - points[0][0], points[4][1] - points[0][1], 
						position[0] + points[0][0], position[1] + points[0][1],
						Main.sceneries[i].position[0] + Main.sceneries[i].points[j][0], Main.sceneries[i].position[1] + Main.sceneries[i].points[j][1],
						Main.sceneries[i].position[0] + Main.sceneries[i].points[j+1][0], Main.sceneries[i].position[1] + Main.sceneries[i].points[j+1][1]);
				if (t >= 0 && t < 1) {
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean contactBotRight() {
		tMin = 1;
		for (int i = 0 ; i < Main.nbSceneries ; i++) {
			for (int j = 0 ; j < Main.sceneries[i].getNbPoints() - 1 ; j++) {
				t = lineIntersection(points[1][0] - points[0][0], points[1][1] - points[0][1], 
						position[0] + points[0][0], position[1] + points[0][1],
						Main.sceneries[i].position[0] + Main.sceneries[i].points[j][0], Main.sceneries[i].position[1] + Main.sceneries[i].points[j][1],
						Main.sceneries[i].position[0] + Main.sceneries[i].points[j+1][0], Main.sceneries[i].position[1] + Main.sceneries[i].points[j+1][1]);
				if (t >= 0 && t < 1) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean contactLeft() {
		tMin = 1;
		for (int i = 0 ; i < Main.nbSceneries ; i++)
		{
			for (int j = 0 ; j < Main.sceneries[i].getNbPoints() - 1 ; j++) {
				t = lineIntersection(1, 1, 
						position[0] + points[3][0]-0.5, position[1] + points[3][1]-0.5,
						Main.sceneries[i].position[0] + Main.sceneries[i].points[j][0], Main.sceneries[i].position[1] + Main.sceneries[i].points[j][1],
						Main.sceneries[i].position[0] + Main.sceneries[i].points[j+1][0], Main.sceneries[i].position[1] + Main.sceneries[i].points[j+1][1]);
				if (t >= 0 && t < 1) {
					return true;
				}
				t = lineIntersection(points[3][0] - points[4][0], points[3][1] - points[4][1], 
						position[0] + points[4][0], position[1] + points[4][1],
						Main.sceneries[i].position[0] + Main.sceneries[i].points[j][0], Main.sceneries[i].position[1] + Main.sceneries[i].points[j][1],
						Main.sceneries[i].position[0] + Main.sceneries[i].points[j+1][0], Main.sceneries[i].position[1] + Main.sceneries[i].points[j+1][1]);
				if (t >= 0 && t < 1) {
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean contactRight() {
		tMin = 1;
		for (int i = 0 ; i < Main.nbSceneries ; i++) {
			for (int j = 0 ; j < Main.sceneries[i].getNbPoints() - 1 ; j++) {
				t = lineIntersection(-1, 1, 
						position[0] + points[2][0]+0.5, position[1] + points[2][1]-0.5,
						Main.sceneries[i].position[0] + Main.sceneries[i].points[j][0], Main.sceneries[i].position[1] + Main.sceneries[i].points[j][1],
						Main.sceneries[i].position[0] + Main.sceneries[i].points[j+1][0], Main.sceneries[i].position[1] + Main.sceneries[i].points[j+1][1]);
				if (t >= 0 && t < 1) {
					return true;
				}
				t = lineIntersection(points[2][0] - points[1][0], points[2][1] - points[1][1], 
						position[0] + points[1][0], position[1] + points[1][1],
						Main.sceneries[i].position[0] + Main.sceneries[i].points[j][0], Main.sceneries[i].position[1] + Main.sceneries[i].points[j][1],
						Main.sceneries[i].position[0] + Main.sceneries[i].points[j+1][0], Main.sceneries[i].position[1] + Main.sceneries[i].points[j+1][1]);
				if (t >= 0 && t < 1) {
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean contactTop() {
		tMin = 1;
		for (int i = 0 ; i < Main.nbSceneries ; i++) {
			for (int j = 0 ; j < Main.sceneries[i].getNbPoints() - 1 ; j++) {
				t = lineIntersection(1, 0, 
						position[0] + points[2][0] - 0.5, position[1] + points[2][1]-0.5,
						Main.sceneries[i].position[0] + Main.sceneries[i].points[j][0], Main.sceneries[i].position[1] + Main.sceneries[i].points[j][1],
						Main.sceneries[i].position[0] + Main.sceneries[i].points[j+1][0], Main.sceneries[i].position[1] + Main.sceneries[i].points[j+1][1]);
				if (t >= 0 && t < 1) {
					return true;
				}
				t = lineIntersection(1, 0, 
						position[0] + points[3][0] - 0.5, position[1] + points[3][1]+0.5,
						Main.sceneries[i].position[0] + Main.sceneries[i].points[j][0], Main.sceneries[i].position[1] + Main.sceneries[i].points[j][1],
						Main.sceneries[i].position[0] + Main.sceneries[i].points[j+1][0], Main.sceneries[i].position[1] + Main.sceneries[i].points[j+1][1]);
				if (t >= 0 && t < 1) {
					return true;
				}
				t = lineIntersection(points[2][0] - points[3][0], points[2][1] - points[3][1] - 1, 
						position[0] + points[3][0], position[1] + points[3][1] + 0.5,
						Main.sceneries[i].position[0] + Main.sceneries[i].points[j][0], Main.sceneries[i].position[1] + Main.sceneries[i].points[j][1],
						Main.sceneries[i].position[0] + Main.sceneries[i].points[j+1][0], Main.sceneries[i].position[1] + Main.sceneries[i].points[j+1][1]);
				if (t >= 0 && t < 1) {
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean contactSlideLeft() {

		tMin = 1;
		for (int i = 0 ; i < Main.nbSceneries ; i++) {
			for (int j = 0 ; j < Main.sceneries[i].getNbPoints() - 1 ; j++) {
				t = lineIntersection(1, 0, 
						position[0] + points[4][0] - 0.5, position[1] + points[4][1],
						Main.sceneries[i].position[0] + Main.sceneries[i].points[j][0], Main.sceneries[i].position[1] + Main.sceneries[i].points[j][1],
						Main.sceneries[i].position[0] + Main.sceneries[i].points[j+1][0], Main.sceneries[i].position[1] + Main.sceneries[i].points[j+1][1]);
				if (t >= 0 && t < 1) {
					cosFloorSlope = Math.abs(Main.sceneries[i].points[j+1][0] - Main.sceneries[i].points[j][0]) / 
							Math.sqrt((Main.sceneries[i].points[j+1][0] - Main.sceneries[i].points[j][0])*(Main.sceneries[i].points[j+1][0] - Main.sceneries[i].points[j][0]) + 
									+ (Main.sceneries[i].points[j+1][1] - Main.sceneries[i].points[j][1])*(Main.sceneries[i].points[j+1][1] - Main.sceneries[i].points[j][1]));
					if(Math.signum(Main.sceneries[i].points[j+1][0] - Main.sceneries[i].points[j][0])*
							Math.signum(Main.sceneries[i].points[j+1][1] - Main.sceneries[i].points[j][1])<0) {
						cosFloorSlope = - cosFloorSlope;
					}
					return true;
				}
			}
		}
		
		return false;
	}
	
	private boolean contactSlideRight() {
		tMin = 1;
		for (int i = 0 ; i < Main.nbSceneries ; i++) {
			for (int j = 0 ; j < Main.sceneries[i].getNbPoints() - 1 ; j++) {
				t = lineIntersection(1, 0, 
						position[0] + points[1][0] - 0.5, position[1] + points[1][1],
						Main.sceneries[i].position[0] + Main.sceneries[i].points[j][0], Main.sceneries[i].position[1] + Main.sceneries[i].points[j][1],
						Main.sceneries[i].position[0] + Main.sceneries[i].points[j+1][0], Main.sceneries[i].position[1] + Main.sceneries[i].points[j+1][1]);
				if (t >= 0 && t < 1) {
					cosFloorSlope = Math.abs(Main.sceneries[i].points[j+1][0] - Main.sceneries[i].points[j][0]) / 
						Math.sqrt((Main.sceneries[i].points[j+1][0] - Main.sceneries[i].points[j][0])*(Main.sceneries[i].points[j+1][0] - Main.sceneries[i].points[j][0]) + 
								+ (Main.sceneries[i].points[j+1][1] - Main.sceneries[i].points[j][1])*(Main.sceneries[i].points[j+1][1] - Main.sceneries[i].points[j][1]));
				if(Math.signum(Main.sceneries[i].points[j+1][0] - Main.sceneries[i].points[j][0])*
						Math.signum(Main.sceneries[i].points[j+1][1] - Main.sceneries[i].points[j][1])<0) {
					cosFloorSlope = - cosFloorSlope;
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
				
		if(Main.keyRight && activeRight == 0){
			this.speed[1] = moveSpeed;
		}
		if(Main.keyLeft && activeLeft == 0){
			this.speed[1] = - moveSpeed;
		}
	}
	
	
	private void walk() {
		// Reset of the walk speed to stop if any key is pressed
		this.speed[0] = 0;
		this.speed[1] = 0;
		
		if (activeLeft > 4) {
			activeLeft = 2;
		}
		if (activeRight > 4) {
			activeRight = 2;
		}
		if (activeJump > 4) {
			activeJump = 2;
		}
		
		if(Main.keyRight && activeRight == 0){
			this.speed[1] = Math.sqrt(1 - Math.pow(cosFloorSlope, 2)) * moveSpeed;
			this.speed[0] = cosFloorSlope * moveSpeed;
		}
		if(Main.keyLeft && activeLeft == 0) {
			this.speed[1] = - Math.sqrt(1 - Math.pow(cosFloorSlope, 2)) * moveSpeed;
			this.speed[0] = - cosFloorSlope * moveSpeed;
		}
		if(Main.keyUp && activeJump == 0) {
			this.speed[0] = - jumpSpeed;
		}
	}
	
	private void reboundBotLeft() {
		activeLeft = inactiveTime + 1;
		activeRight = 4;
		this.speed[0] = -3;
		this.speed[1] = 5;
		this.position[0] = this.position[0] - 0.01;
		this.position[1] = this.position[1] + 0.01;
	}

	private void reboundBotRight() {
		activeRight = inactiveTime + 1;
		activeLeft = 4;
		this.speed[0] = -3;
		this.speed[1] = -5;
		this.position[0] = this.position[0] - 0.01;
		this.position[1] = this.position[1] - 0.01;
	}
	
	private void reboundLeft() {
		activeLeft = inactiveTime + 1;
		activeRight = 4;
		this.speed[0] = -2;
		this.speed[1] = 3;
		this.position[0] = this.position[0] - 0.01;
		this.position[1] = this.position[1] + 0.01;
	}
	
	private void reboundRight() {
		activeRight = inactiveTime + 1;
		activeLeft = 4;
		this.speed[0] = -2;
		this.speed[1] = -3;
		this.position[0] = this.position[0] - 0.01;
		this.position[1] = this.position[1] - 0.01;
	}
	
	private void reboundTop() {
		this.speed[0] = 3;
		this.position[0] = this.position[0] + 0.01;
	}
	
	private void slideLeft(){
		activeLeft = 2 * inactiveTime;
		
		double speedNorm;
		speedNorm = -Math.sqrt(1-cosFloorSlope*cosFloorSlope)*this.speed[1] - cosFloorSlope*this.speed[0];
		
		this.speed[1] = -Math.sqrt(1-cosFloorSlope*cosFloorSlope)*(speedNorm*(1-frictionCoef) - slideCoef*Main.gravity*Math.abs(cosFloorSlope));
		this.speed[0] = -cosFloorSlope*(speedNorm*(1-frictionCoef) - slideCoef*Main.gravity*Math.abs(cosFloorSlope));
		
		if(Main.keyUp && activeJump == 0) {
			this.speed[0] = -jumpSpeed;
			this.speed[1] = 0.2 * jumpSpeed;
			activeJump = 2 * inactiveTime;
		}
	}
	
	private void slideRight(){
		activeRight = 2 * inactiveTime;

		double speedNorm;
		speedNorm = Math.sqrt(1-cosFloorSlope*cosFloorSlope)*this.speed[1] + cosFloorSlope*this.speed[0];
		
		this.speed[1] = Math.sqrt(1-cosFloorSlope*cosFloorSlope)*(speedNorm - slideCoef*Main.gravity*Math.abs(cosFloorSlope));
		this.speed[0] = cosFloorSlope*(speedNorm - slideCoef*Main.gravity*Math.abs(cosFloorSlope));
		
		if(Main.keyUp && activeJump == 0) {
			this.speed[0] = -jumpSpeed;
			this.speed[1] = - 0.2 * jumpSpeed;
			activeJump = 2 * inactiveTime;
		}
	}
	
	private void slideFloor(){
		// compute cosFloor
		contactFloor();
		
		// reset speed
		this.speed[0] = 0;
		this.speed[1] = 0;
		
		if(state == 9){
			this.speed[1] = Math.sqrt(1-cosFloorSlope*cosFloorSlope)*moveSpeed/2;
			this.speed[0] = cosFloorSlope*moveSpeed/2;
		}
		else {
			this.speed[1] = -Math.sqrt(1-cosFloorSlope*cosFloorSlope)*moveSpeed/2;
			this.speed[0] = -cosFloorSlope*moveSpeed/2;
		}
		
		if(Main.keyUp) {
			this.speed[0] = -jumpSpeed;
			activeLeft = 4;
			activeRight = 4;
		}
	}
	
	private void debugFly() {
		this.speed[0] = 0;
		this.speed[1] = 0;
		
		if(Main.keyUp) {
			this.position[0] -= moveSpeed;
		}
		if(Main.keyDown) {
			this.position[0] += moveSpeed;
		}
		if(Main.keyLeft) {
			this.position[1] -= moveSpeed;
		}
		if(Main.keyRight) {
			this.position[1] += moveSpeed;
		}
	}
	
	public void forcedMove(double speedi, double speedj){
		this.speed[0] = speedi;
		this.speed[1] = speedj;
		this.position[0] = this.position[0];
		this.position[1] = this.position[1];
		
		tMin = 1;
		for (int i = 0 ; i < Main.nbSceneries ; i++) {
			if(i < Main.indScene || i > Main.indScene) {
				intersect(Main.sceneries[i]);
			}
		}
		
		this.speed[0] = tMin * this.speed[0];
		this.speed[1] = tMin * this.speed[1];

		this.position[0] = this.position[0] + this.speed[0] - 0.001;
		this.position[1] = this.position[1] + this.speed[1];
		
		this.speed[0] = 0;
		this.speed[1] = 0;
		
	}
}
