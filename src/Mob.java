
public class Mob extends Hitbox{
	/* characteristics */
	public Characteristics charac;
	public Weapon weapon;
	
	/* Parameters */
	private double width;
	private double height;
	private double tanAlpha;
	public int typeMob; 							// type of the mob
	/* 0:nothing, 1: slug, 2: gunMob */
	private double detectRange;		// range in witch the mob start to attack the player
	private double visionRange;		// range in witch the mob stop to attack the player
	private double cpRange;			// maximum distance to the checkpoint

	public int state; 				// state of the character
	/* 0:fly, 1:floor, 2:top, 3:left, 4:right, 5: botleft, 6:botright, 7:slideleft, 8:slideright, 9: slidefloor*/
	private double maxSpeed;		// max speed for the free fall
	private double moveSpeed;		// walk speed
	private double jumpSpeed;		// speed with which the character jumps
	private double maxJump;			// max high of a jump
	private double frictionCoef;	// friction coefficient
	private double cosFloorSlope;	// angle of the floor segment you stand on (rad)
	private double slideCoef;
	private double waterAcceleration;
	private double waterSpeed;
	private double waterFricCoef;

	private int inactiveTime;
	private int inactiveJump;
	private int inactiveLeft;
	private int inactiveRight;

	public double[] checkPoint;
	// animations
	public int animation;
	/* 1: death, 2: death by void, 3:death by lava */
	public int time;
	public int times[];
	public int nbTimes;

	public boolean keyJump;	// to verify if the jump has been released
	private int nbJump;			// count the number of successive jumps
	private int maxNbJump;

	private int indArea;		// indice of the current area
	
	// mob control
	private boolean keyLeft;
	private boolean keyRight;
	private boolean keyUp;
	public boolean attack;
	private int maxTimeMove;
	private int timeMove;
	private int maxTimeRelowed;
	private int timeRelowed;
	private int maxTimeShot;
	private int timeShot;

	
	public Mob(int typeIn) {
		width = 45;
		height = 35;
		tanAlpha = 0.75;
		typeMob = typeIn;
		if(typeMob>0) {
			type = 1;
		}
		else {
			type = 0;
		}

		state = 0;
		attack = false;
		maxSpeed = 25;
		moveSpeed = 7;
		maxJump = 90;
		jumpSpeed = Math.sqrt(2 * Main.gravity * maxJump);
		frictionCoef = Main.gravity / maxSpeed;
		slideCoef = 0.8;
		detectRange = 250;
		visionRange = 1750;
		cpRange = 850;

		weapon = new Weapon();
		charac = new Characteristics();
		charac.hitTime = 0;
		switch(typeMob) {
		case 1:
		{
			charac.defence = 10;
			charac.maxHp = 25;
			charac.hp = charac.maxHp;
			maxTimeMove = 30;
		}
		break;
		case 2:
		{
			charac.defence = 10;
			charac.maxHp = 50;
			charac.hp = charac.maxHp;
			width = 35;
			height = 55;

			weapon.type = 6;
			maxTimeMove = 20;
			maxTimeShot = 10;
			maxTimeRelowed = 50;
			timeShot = 0;
			timeMove = maxTimeMove;
			detectRange = 350;
			visionRange = 850;
		}
		break;
		}

		waterAcceleration = 1;
		waterSpeed = 7;
		waterFricCoef = waterAcceleration / waterSpeed;

		keyLeft = false;
		keyRight = false;
		keyUp = false;

		inactiveTime = 10;
		inactiveLeft = 0;
		inactiveRight = 0;
		inactiveJump = 0;
		keyJump = true;
		nbJump = 0;
		maxNbJump = 2;

		checkPoint = new double[2];
		checkPoint[0] = 100;
		checkPoint[1] = 100;

		animation = 0;
		time = 0;
		times = new int[10];
		nbTimes = 0;

		indArea = 0;

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
	
	
	public void update() {
		int areaType;
		
		charac.update();
		if( charac.hp<charac.maxHp) {
			attack = true;
		}
		
		if(typeMob > 0) {
			
			if (animation == 0) {
				if(charac.hp<=0) {
					attack = false;
					animation = 1;
					time = 0;
					animate();
				}
				else {
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
						attack = false;
						animation = 1;
						time = 0;
						animate();
					}
						break;
					case 2:
					{
						// lava
						attack = false;
						animation = 1;
						time = 0;
						animate();
					}
						break;
					case 3:
					{
						//void
						attack = false;
						animation = 1;
						time = 0;
						animate();
					}
						break;
					case 4:
					{
						// scale
						updateAir();
					}
						break;
					case 5:
					{
						updateAir();
					}
						break;
					case 6:
					{
						updateAir();
					}
					break;
					case 7:
					{
						// trampoline
						updateTrampoline();
					}
					break;
					case 8:
					{
						updateAir();
					}
					break;
					case 9:
					{
						updateAir();
					}
					break;
					}
				}
			}
			else {
				animate();
			}
		}
	}
	
	private void updateAir() {

		decision();
		updateState();
		hit();
		move();


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
	
	private void updateState() {

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
	
	private void decision() {
		
		if(Math.pow(Main.mainChar.position[0]-this.position[0],2)+Math.pow(Main.mainChar.position[1]-this.position[1],2)<Math.pow(detectRange,2)) {
			attack = true;
		}
		if(Math.pow(Main.mainChar.position[0]-this.position[0],2)+Math.pow(Main.mainChar.position[1]-this.position[1],2)>Math.pow(visionRange,2)) {
			attack = false;
			timeShot = maxTimeShot;
			timeRelowed = 0;
			charac.hp = charac.maxHp;
		}

		double a = 0;
		switch(typeMob) {
		case 1:
		{
			
			if (attack) {
				if (timeMove>0) {
					timeMove = timeMove - 1;
				}
				else {
					a = Math.random();
					if (a<0.2+0.005*Math.abs(Main.mainChar.position[1]-this.position[1])) {
						a = Math.random();
						if (a>0.5+0.01*(Main.mainChar.position[1]-this.position[1])) {
							keyLeft = true;
							keyRight = false;
							timeMove = maxTimeMove/3;
						}
						else {
							keyLeft = false;
							keyRight = true;
							timeMove = maxTimeMove/3;
						}
					}
					else {
						keyLeft = false;
						keyRight = false;
					}
				}

				a = Math.random();
				if (a<0.1) {
					keyUp = true;
				}
				else {
					keyUp = false;
				}
			} 
			else { // if(attack)
				if (timeMove>0) {
					timeMove = timeMove - 1;
				}
				else {
					a = Math.random();
					if (a<0.05) {
						a = Math.random();
						if (a<0.5) {
							keyLeft = true;
							keyRight = false;
							timeMove = maxTimeMove;
						}
						else {
							keyLeft = false;
							keyRight = true;
							timeMove = maxTimeMove;
						}
					}
					else {
						keyLeft = false;
						keyRight = false;
					}
				}

				a = Math.random();
				if (a<0.01) {
					keyUp = true;
				}
				else {
					keyUp = false;
				}
			}
		}
		break;
		case 2:
		{
			double distLim = 300;
			if(attack) {
				// move
				if (timeRelowed>0) {
					timeRelowed = timeRelowed - 1;
					weapon.updateMob(false, position[0] - 30, position[1], Main.mainChar.position[0]-10 - position[0] , Main.mainChar.position[1] - position[1]);
					
					
					
				}
				else {
					// fire
					if (timeShot>0) {
						timeShot = timeShot - 1;
						timeRelowed = 0;
					}
					else {
						timeRelowed = maxTimeRelowed;
						timeShot = maxTimeShot;
					}
					weapon.updateMob(true, position[0] - 30, position[1], Main.mainChar.position[0]-10 - position[0] , Main.mainChar.position[1] - position[1]);
				}
				
				// move
				if (timeMove>0) {
					timeMove = timeMove - 1;
				}
				else {
					if(checkPoint[1]+cpRange-position[1]<0) {
						keyLeft = true;
						keyRight = false;
						timeMove = 5*maxTimeMove;
					}
					else if(checkPoint[1]-cpRange-position[1]>0) {
						keyLeft = false;
						keyRight = true;
						timeMove = 5*maxTimeMove;
					}
					else {
						a = Math.random();
						if(position[1]-Main.mainChar.position[1]>0) {
							if (a<Math.pow(Main.mainChar.position[1]-this.position[1]+distLim,2)/Math.pow(distLim,2)) {
								if (this.position[1]-Main.mainChar.position[1]-distLim>0) {
									keyLeft = true;
									keyRight = false;
									timeMove = maxTimeMove;
								}
								else {
									keyLeft = false;
									keyRight = true;
									timeMove = maxTimeMove;
								}
							}
							else {
								keyLeft = false;
								keyRight = false;
								timeMove = maxTimeMove/4;
							}
						}
						else { //position[1]-Main.mainChar.position[1]>0
							if (a<Math.pow(Main.mainChar.position[1]-this.position[1]-distLim,2)/Math.pow(distLim,2)) {
								if (this.position[1]-Main.mainChar.position[1]+distLim>0) {
									keyLeft = true;
									keyRight = false;
									timeMove = maxTimeMove;
								}
								else {
									keyLeft = false;
									keyRight = true;
									timeMove = maxTimeMove;
								}
							}
							else {
								keyLeft = false;
								keyRight = false;
								timeMove = maxTimeMove/4;
							}
						}
					}
				}

				a = Math.random();
				if (a<0.05) {
					keyUp = true;
				}
				else {
					keyUp = false;
				}
			}
			else { // if(attack)
				if (timeMove>0) {
					timeMove = timeMove - 1;
				}
				else {
					a = Math.random();
					if (a<0.05) {
						a = Math.random();
						if (a<0.5 + (position[1]-checkPoint[1])*0.5/cpRange) {
							keyLeft = true;
							keyRight = false;
							timeMove = maxTimeMove;
						}
						else {
							keyLeft = false;
							keyRight = true;
							timeMove = maxTimeMove;
						}
					}
					else {
						keyLeft = false;
						keyRight = false;
					}
				}

				a = Math.random();
				if (a<0.01) {
					keyUp = true;
				}
				else {
					keyUp = false;
				}
			}
		}
		} // end switch
		
		
	}
	
	private void updateTrampoline() {


		this.speed[1] = 0;
		if(keyRight){
			this.speed[1] = moveSpeed;
		}
		if(keyLeft) {
			this.speed[1] = - moveSpeed;
		}
		if(Math.abs(this.speed[0])<1)
		{
			if(keyUp) {
				this.speed[0] = - jumpSpeed;
				nbJump = maxNbJump;
			}
		}
		else {
			this.speed[0] = -Math.abs(this.speed[0])*Main.areas[indArea].getSpeedMultJump()-jumpSpeed;
			nbJump = maxNbJump;

		}

		this.position[1] = this.position[1] + this.speed[1];
		this.position[0] = this.position[0] + this.speed[0];
	}

	
	// CONTACT DETECTION

		private boolean contactFloor() {
			double t = -1;
			for (int i = 0 ; i < Main.nbSceneries ; i++) {
				if(Main.sceneries[i].type > 0)
				{
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
							nbJump = 0;
							return true;
						}
					}
				}
			}
			return false;
		}

		private boolean contactBotLeft() {
			tMin = 1;
			for (int i = 0 ; i < Main.nbSceneries ; i++) {
				if(Main.sceneries[i].type == 1)
				{
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
			}
			return false;
		}

		private boolean contactBotRight() {
			tMin = 1;
			for (int i = 0 ; i < Main.nbSceneries ; i++) {
				if(Main.sceneries[i].type == 1)
				{
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
			}
			return false;
		}

		private boolean contactLeft() {
			tMin = 1;
			for (int i = 0 ; i < Main.nbSceneries ; i++)
			{
				if(Main.sceneries[i].type == 1)
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
			}
			return false;
		}

		private boolean contactRight() {
			tMin = 1;
			for (int i = 0 ; i < Main.nbSceneries ; i++) {
				if(Main.sceneries[i].type == 1)
				{
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
			}
			return false;
		}

		private boolean contactTop() {
			tMin = 1;
			for (int i = 0 ; i < Main.nbSceneries ; i++) {
				if(Main.sceneries[i].type == 1)
				{
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
			}
			return false;
		}

		private boolean contactSlideLeft() {

			tMin = 1;
			for (int i = 0 ; i < Main.nbSceneries ; i++) {
				if(Main.sceneries[i].type == 1)
				{
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
			}

			return false;
		}

		private boolean contactSlideRight() {
			tMin = 1;
			for (int i = 0 ; i < Main.nbSceneries ; i++) {
				if(Main.sceneries[i].type == 1)
				{
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
			}

			return false;
		}




		// ACTIONS

		private void fly() {
			this.speed[0] = this.speed[0] + Main.gravity - frictionCoef * this.speed[0];
			this.speed[1] = this.speed[1] - frictionCoef * this.speed[1];

			
			
			if(keyRight && inactiveRight == 0){
				this.speed[1] = moveSpeed;
			}
			if(keyLeft && inactiveLeft == 0){
				this.speed[1] = - moveSpeed;
			}
			if(keyUp && inactiveJump == 0 && nbJump < maxNbJump){
				nbJump = nbJump + 1;
				this.speed[0] = - jumpSpeed;
			}
		}

		private void walk() {
			// Reset of the walk speed to stop if any key is pressed
			this.speed[0] = 0;
			this.speed[1] = 0;

			if (inactiveLeft > 4) {
				inactiveLeft = 4;
			}
			if (inactiveRight > 4) {
				inactiveRight = 4;
			}
			if (inactiveJump > 4) {
				inactiveJump = 4;
			}

			if(keyRight && inactiveRight == 0){
				this.speed[1] = Math.sqrt(1 - Math.pow(cosFloorSlope, 2)) * moveSpeed;
				this.speed[0] = cosFloorSlope * moveSpeed;
			}
			if(keyLeft && inactiveLeft == 0) {
				this.speed[1] = - Math.sqrt(1 - Math.pow(cosFloorSlope, 2)) * moveSpeed;
				this.speed[0] = - cosFloorSlope * moveSpeed;
			}
			if(keyUp && inactiveJump == 0) {
				this.speed[0] = - jumpSpeed;
				nbJump = 1;
			}
		}

		private void reboundBotLeft() {
			inactiveLeft = inactiveTime + 1;
			nbJump = maxNbJump;
			inactiveRight = 4;
			this.speed[0] = -3;
			this.speed[1] = 5;
			this.position[0] = this.position[0] - 0.01;
			this.position[1] = this.position[1] + 0.01;
		}

		private void reboundBotRight() {
			inactiveRight = inactiveTime + 1;
			nbJump = maxNbJump;
			inactiveLeft = 4;
			this.speed[0] = -3;
			this.speed[1] = -5;
			this.position[0] = this.position[0] - 0.01;
			this.position[1] = this.position[1] - 0.01;
		}

		private void reboundLeft() {
			inactiveLeft = inactiveTime + 1;
			nbJump = maxNbJump;
			inactiveRight = 4;
			this.speed[0] = -2;
			this.speed[1] = 3;
			this.position[0] = this.position[0] - 0.01;
			this.position[1] = this.position[1] + 0.01;
		}

		private void reboundRight() {
			inactiveRight = inactiveTime + 1;
			nbJump = maxNbJump;
			inactiveLeft = 4;
			this.speed[0] = -2;
			this.speed[1] = -3;
			this.position[0] = this.position[0] - 0.01;
			this.position[1] = this.position[1] - 0.01;
		}

		private void reboundTop() {
			nbJump = 1;
			this.speed[0] = 3;
			this.position[0] = this.position[0] + 0.01;
		}

		private void slideLeft(){
			inactiveLeft = 2 * inactiveTime;

			double speedNorm;
			speedNorm = -Math.sqrt(1-cosFloorSlope*cosFloorSlope)*this.speed[1] - cosFloorSlope*this.speed[0] - 1;

			this.speed[1] = -Math.sqrt(1-cosFloorSlope*cosFloorSlope)*(speedNorm*(1-frictionCoef) - slideCoef*Main.gravity*Math.abs(cosFloorSlope));
			this.speed[0] = -cosFloorSlope*(speedNorm*(1-frictionCoef) - slideCoef*Main.gravity*Math.abs(cosFloorSlope));

			if(keyUp && inactiveJump == 0) {
				this.speed[0] = -jumpSpeed;
				this.speed[1] = 0.2 * jumpSpeed;
				nbJump = 1;
			}
		}

		private void slideRight(){
			inactiveRight = 2 * inactiveTime;

			double speedNorm;
			speedNorm = Math.sqrt(1-cosFloorSlope*cosFloorSlope)*this.speed[1] + cosFloorSlope*this.speed[0] + 1;

			this.speed[1] = Math.sqrt(1-cosFloorSlope*cosFloorSlope)*(speedNorm - slideCoef*Main.gravity*Math.abs(cosFloorSlope));
			this.speed[0] = cosFloorSlope*(speedNorm - slideCoef*Main.gravity*Math.abs(cosFloorSlope));

			if(keyUp && inactiveJump == 0) {
				this.speed[0] = -jumpSpeed;
				this.speed[1] = - 0.2 * jumpSpeed;
				nbJump = 1;
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

			if(keyUp) {
				this.speed[0] = -jumpSpeed;
				inactiveLeft = 10;
				inactiveRight = 10;
			}
		}

	
	private void animate() {

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
		case 4:
		{
		}
		break;
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
		
		charac.hp = charac.maxHp;
		attack = false;
		this.speed[0] = 0;
		this.speed[1] = 0;

		this.position[0] = checkPoint[0];
		this.position[1] = checkPoint[1];
	}
	
	private int isIn() {

		for (int i = 0 ; i < Main.nbAreas ; i++) {
			if (Main.areas[i].isIn(this.position[0], this.position[1]))
			{
				indArea = i;
				return Main.areas[i].getType();
			}
		}
		indArea = -1;
		return 0;
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
	
	private void hit() {
		tMin = 1;
		intersect(Main.mainChar);
		if(tMin<1) {
			Main.mainChar.charac.hit(charac.damages);
		}
	}
}
