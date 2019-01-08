
public class Character extends Hitbox {

	/* Parameters */
	private double width;
	private double height;
	private double tanAlpha; 
	
	public int state; 				// state of the character
	/* 0:fly , 1:floor, 2:top, 3:left, 4:right, 5: botleft, 6:botright, 7:slideleft, 8:slideright, 9: slidefloor*/
	private double maxSpeed;		// max speed for the free fall
	private double moveSpeed;		// walk speed
	private double jumpSpeed;		// speed with which the character jumps
	private double maxJump;			// max high of a jump
	private double frictionCoef;	// friction coefficient
	private double cosFloorSlope;	// angle of the floor segment you stand on (rad)
	private double slideCoef;
	
	private int inactiveTime;
	private int inactiveJump;	
	private int inactiveLeft;
	private int inactiveRight;
	
	public double[] checkPoint;
	
	public int animation;			
	/* 1: respawn, 2: death by void, 3:death by lava */
	public int time;
	public int times[];
	public int nbTimes;
	
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
		inactiveLeft = 0;
		inactiveRight = 0;
		inactiveJump = 0;
		
		checkPoint = new double[2];
		checkPoint[0] = 100;
		checkPoint[1] = 100;
		
		animation = 0;
		time = 0;
		times = new int[10];
		nbTimes = 0;
		
		if (Main.debug[4]) {
			System.out.println("friction coefficient : " + frictionCoef + "   [Character][Character]");
		}
		if (Main.debug[5]) {
			System.out.println("jump speed : " + jumpSpeed + "   [Character][Character]");
		}
		
		setPoints();
		this.position = new double[2];
		this.position[0] = checkPoint[0];
		this.position[1] = checkPoint[1];
		this.speed = new double[2];
	}
	
	public void update() {
		int areaType;
		
		if (animation == 0) {
			areaType = isIn();
			
			switch(areaType) {
			case 0:
			{
				// air
				updateAir();
			}
				break;
			case 1:
			{
				// water
			}
				break;
			case 2:
			{
				// lava
				animation = 3;
				time = 0;		
			}
				break;
			case 3:
			{
				//void
				animation = 2;
				time = 0;
			}
				break;
			case 4:
			{
				// scale
			}
				break;
			}
		}
		else {
			animates();
		}
		
	}
	
	private void updateAir() {
		
		updateState();
		move();
		
		if (Main.debug[6]) {
			System.out.println("inactiveLeft remaining time : " + inactiveLeft + "   [Character][updateAir]");
			System.out.println("inactiveRight remaining time : " + inactiveRight + "   [Character][updateAir]");
			System.out.println("inactiveJump remaining time : " + inactiveJump + "   [Character][updateAir]");
		}
		
		if (inactiveLeft > 0) {
			inactiveLeft--;
		}
		if (inactiveRight > 0) {
			inactiveRight--;
		}
		if (inactiveJump > 0) {
			inactiveJump--;
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
		if (Main.debug[7] && Main.keySpace == true) {
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
		
		if (Main.debug[2]) {
			System.out.println("State : " + state + "   [Character][updateState]");
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
					if (Main.debug[3]) {
						System.out.println("cosFloorSlope : " + cosFloorSlope + "   [Character][contactFloor]");
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
				
		if(Main.keyRight && inactiveRight == 0){
			this.speed[1] = moveSpeed;
		}
		if(Main.keyLeft && inactiveLeft == 0){
			this.speed[1] = - moveSpeed;
		}
	}
	
	
	private void walk() {
		// Reset of the walk speed to stop if any key is pressed
		this.speed[0] = 0;
		this.speed[1] = 0;
		
		if (inactiveLeft > 4) {
			inactiveLeft = 2;
		}
		if (inactiveRight > 4) {
			inactiveRight = 2;
		}
		if (inactiveJump > 4) {
			inactiveJump = 2;
		}
		
		if(Main.keyRight && inactiveRight == 0){
			this.speed[1] = Math.sqrt(1 - Math.pow(cosFloorSlope, 2)) * moveSpeed;
			this.speed[0] = cosFloorSlope * moveSpeed;
		}
		if(Main.keyLeft && inactiveLeft == 0) {
			this.speed[1] = - Math.sqrt(1 - Math.pow(cosFloorSlope, 2)) * moveSpeed;
			this.speed[0] = - cosFloorSlope * moveSpeed;
		}
		if(Main.keyUp && inactiveJump == 0) {
			this.speed[0] = - jumpSpeed;
		}
	}
	
	private void reboundBotLeft() {
		inactiveLeft = inactiveTime + 1;
		inactiveRight = 4;
		this.speed[0] = -3;
		this.speed[1] = 5;
		this.position[0] = this.position[0] - 0.01;
		this.position[1] = this.position[1] + 0.01;
	}

	private void reboundBotRight() {
		inactiveRight = inactiveTime + 1;
		inactiveLeft = 4;
		this.speed[0] = -3;
		this.speed[1] = -5;
		this.position[0] = this.position[0] - 0.01;
		this.position[1] = this.position[1] - 0.01;
	}
	
	private void reboundLeft() {
		inactiveLeft = inactiveTime + 1;
		inactiveRight = 4;
		this.speed[0] = -2;
		this.speed[1] = 3;
		this.position[0] = this.position[0] - 0.01;
		this.position[1] = this.position[1] + 0.01;
	}
	
	private void reboundRight() {
		inactiveRight = inactiveTime + 1;
		inactiveLeft = 4;
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
		inactiveLeft = 2 * inactiveTime;
		
		double speedNorm;
		speedNorm = -Math.sqrt(1-cosFloorSlope*cosFloorSlope)*this.speed[1] - cosFloorSlope*this.speed[0];
		
		this.speed[1] = -Math.sqrt(1-cosFloorSlope*cosFloorSlope)*(speedNorm*(1-frictionCoef) - slideCoef*Main.gravity*Math.abs(cosFloorSlope));
		this.speed[0] = -cosFloorSlope*(speedNorm*(1-frictionCoef) - slideCoef*Main.gravity*Math.abs(cosFloorSlope));
		
		if(Main.keyUp && inactiveJump == 0) {
			this.speed[0] = -jumpSpeed;
			this.speed[1] = 0.2 * jumpSpeed;
			inactiveJump = 2 * inactiveTime;
		}
	}
	
	private void slideRight(){
		inactiveRight = 2 * inactiveTime;

		double speedNorm;
		speedNorm = Math.sqrt(1-cosFloorSlope*cosFloorSlope)*this.speed[1] + cosFloorSlope*this.speed[0];
		
		this.speed[1] = Math.sqrt(1-cosFloorSlope*cosFloorSlope)*(speedNorm - slideCoef*Main.gravity*Math.abs(cosFloorSlope));
		this.speed[0] = cosFloorSlope*(speedNorm - slideCoef*Main.gravity*Math.abs(cosFloorSlope));
		
		if(Main.keyUp && inactiveJump == 0) {
			this.speed[0] = -jumpSpeed;
			this.speed[1] = - 0.2 * jumpSpeed;
			inactiveJump = 2 * inactiveTime;
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
			inactiveLeft = 4;
			inactiveRight = 4;
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
	
	public void respawn() {
		this.speed[0] = 0;
		this.speed[1] = 0;
		
		this.position[0] = checkPoint[0];
		this.position[1] = checkPoint[1];
		Main.screen.centerChar();
	}
	
	private int isIn() {
		
		for (int i = 0 ; i < Main.nbAreas ; i++) {
			if (this.position[0] > Main.areas[i].position[0] 
					&& this.position[0] < Main.areas[i].position[0] + Main.areas[i].height 
					&& this.position[1] > Main.areas[i].position[1] 
					&& this.position[1] < Main.areas[i].position[1] + Main.areas[i].width) {
				if (Main.debug[8]) {
					System.out.println("Entry in area type : " + Main.areas[i].type + "   [Character][isIn]");
				}
				return Main.areas[i].type;
			}
		}
		return 0;
	}
	
	private void animates() {
		
		switch (animation) {
		case 1:
		{
			//respawn			
			times[0] = 10;
			times[1] = 2;
			times[2] = 10;
			nbTimes = 3;
			
			if (time == times[0]+1)
			{
				respawn();
			}
			if(time == times[0]+times[1]+times[2])
			{
				nbTimes = 0;
				animation = 0;
				Main.screen.translationType = 1;
			}
			time++;
		}
		break;
		case 2:
		//death by void
		{
			double coef0 = 0.1;
			double coef1 = 0.45;
			double coef2 = 1.5;
			times[0] = 5;
			times[1] = 10;
			times[2] = 4;
			times[3] = 20;
			nbTimes = 4;
			
			Main.screen.translationType = 0;
			
			if (time < times[0]) {
				this.speed[0] = maxSpeed - time * (1-coef0) * maxSpeed / times[0];
				this.speed[1] = 0;
				this.position[0] += this.speed[0];
			}
			else {
				if (time < times[0] + times[1]) {
					this.speed[0] = coef0 * maxSpeed - (time - times[0]) * (2 * coef0 * maxSpeed) / times[1];
					this.speed[1] = 0;
					this.position[0] += this.speed[0];
				}
				else {
					if (time < times[0] + times[1] + times[2]) {
						this.speed[0] = - coef0 * maxSpeed - (time - times[0] - times[1]) * ((coef1 - coef0) * maxSpeed) / times[2];
						this.speed[1] = 0;
						this.position[0] += this.speed[0];
					}
					else {
						if (time < times[0] + times[1] + times[2] + times[3])
						{
							this.speed[0] = - coef1 * maxSpeed + (time - times[0] - times[1] - times[2]) * ((coef1 + coef2) * maxSpeed) / times[3];
							this.speed[1] = 0;
							this.position[0] += this.speed[0];
						}
						else {
							time = 0;
							nbTimes = 0;
							animation = 1;
							return;
						}
					}
				}	
			}
			time++;
		}
		break;
		case 3:
		{
			//death by lava
			times[0] = 3;
			times[1] = 10;
			times[2] = 30;
			nbTimes = 3;
			
			Main.screen.translationType = 0;
			
			if (time < times[0]) {
				this.speed[0] = 3;
				this.speed[1] = 0;
				this.position[0] += this.speed[0];
			}
			else {
				if (time < times[0] + times[1]) {
					this.speed[0] = 0;
					this.speed[1] = 0;
					this.position[0] += this.speed[0];
				}
				else {
					if (time < times[0] + times[1] + times[2]) {
						this.speed[0] = 1.3*this.width / (double)times[2];
						this.speed[1] = 0;
						this.position[0] += this.speed[0];
					}
					else {
						time = 0;
						nbTimes = 0;
						animation = 1;
						return;
					}
				}
			}
			time++;
		}
		break;
		}
		
	}
}
