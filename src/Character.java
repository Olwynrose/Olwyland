
public class Character extends Hitbox {

	/* Characteristics */
	public Characteristics charac;
	public Weapon weapon;
	
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
	private double waterAcceleration;
	private double waterSpeed;
	private double waterFricCoef;

	private int inactiveTime;
	private int inactiveJump;
	private int inactiveLeft;
	private int inactiveRight;
	public int direction; 	/* -1: left, 1: Right */

	public double[] checkPoint;

	public int animation;
	/* 1: respawn, 2: death by void, 3:death by lava, 4:teleportation */
	public int time;
	public int times[];
	public int nbTimes;

	public boolean keyJump;	// to verify if the jump has been released
	private int nbJump;			// count the number of successive jumps
	private int maxNbJump;

	private int indArea;		// indice of the current area
	

	public Character() {
		width = 38;
		height = 65;
		tanAlpha = 0.75;

		type = 1;
		weapon = new Weapon();
		state = 0;
		direction = 1;
		maxSpeed = 25;
		moveSpeed = 7;
		maxJump = 90;
		jumpSpeed = Math.sqrt(2 * Main.gravity * maxJump);
		frictionCoef = Main.gravity / maxSpeed;
		slideCoef = 0.8;

		waterAcceleration = 1;
		waterSpeed = 7;
		waterFricCoef = waterAcceleration / waterSpeed;

		inactiveTime = 10;
		inactiveLeft = 0;
		inactiveRight = 0;
		inactiveJump = 0;
		keyJump = true;
		nbJump = 0;
		maxNbJump = 2;
		
		charac = new Characteristics();
		if(Main.debug[18]) {
			charac.defence = 10000000000.0;
		}
		else {
			charac.defence = 10;
		}
		charac.indSound = 1;
		charac.maxHp = 50;
		charac.hp = charac.maxHp;

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

	public void update() {
		int areaType;
		if(Main.key1) {
			weapon.type = 1;
		}
		if(Main.key2) {
			weapon.type = 2;
		}
		if(Main.key3) {
			weapon.type = 3;
		}
		if(Main.key4) {
			weapon.type = 4;
		}
		if(Main.key5) {
			weapon.type = 5;
		}
		if(Main.key6) {
			weapon.type = 6;
		}
		if(Main.key7) {
			weapon.type = 7;
		}
		if(Main.key8) {
			weapon.type = 8;
		}
		if(Main.debug[11]) {
			System.out.println(this.position[0] + " - " + this.position[1]);
		}

		if(charac.hp < 0 && animation == 0) {
			animation = 1;
			time = 0;
		}

		charac.update();
		
		if (animation == 0) {
			if (Main.debug[7] && Main.keySpace == true) {
				debugFly();
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
					updateWater();
				}
					break;
				case 2:
				{
					// lava
					animation = 3;
					time = 0;
					charac.hp = 0;
					animate();
				}
					break;
				case 3:
				{
					//void
					animation = 2;
					time = 0;
					charac.hp = 0;
					animate();
				}
					break;
				case 4:
				{
					// scale
					updateScale();
				}
					break;
				case 5:
				{
					// teleporter
					if (Main.keyDown) {
						Main.sounds.play(2);
						animation = 4;
						time = 0;
						animate();
					}
					else {
						updateAir();
					}
				}
					break;
				case 6:
				{
					// auto TP
					Main.sounds.play(2);
					animation = 4;
					time = 0;
					animate();
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
					// switch HB
					if (Main.keyDown ) {
						if(time <= 0) {
							Main.sceneries[Main.areas[indArea].getIndHB()].type = (Main.sceneries[Main.areas[indArea].getIndHB()].type + 1) %2;
							time = 20;
						}
						else {
							time = time - 1;
						}
					}
					else {
						time = 0;
					}

					updateAir();
				}
				break;
				case 9:
				{
					// check point
					if (Main.keyDown ) {
						if(time <= 0) {
							checkPoint[0] = Main.areas[indArea].getPositionI() + Main.areas[indArea].getHeight() / 2;
							checkPoint[1] = Main.areas[indArea].getPositionJ() + Main.areas[indArea].getWidth() / 2;
							time = 20;
						}
						else {
							time = time - 1;
						}
					}
					else {
						time = 0;
					}

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

	private void updateAir() {
		int buf_dir;
		updateState();
		move();

		buf_dir = weapon.updateCharac(position[0]-40, position[1], Main.mouseI-Main.mainChar.position[0] , Main.mouseJ-Main.mainChar.position[1]);
		if(buf_dir != 0) {
			direction = buf_dir;
		}
		
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

	private void updateWater() {
		double bufspeed;
		if(Main.keyRight){
			this.speed[1] = this.speed[1] + waterAcceleration;
		}
		if(Main.keyLeft) {
			this.speed[1] = this.speed[1] - waterAcceleration;
		}
		this.speed[1] = this.speed[1]- this.speed[1] * waterFricCoef;


		bufspeed = this.speed[0];
		this.speed[0] = 0;
		tMin = 1;
		for (int i = 0 ; i < Main.nbSceneries ; i++) {
			intersect(Main.sceneries[i]);
			if(tMin<1) {
				this.speed[1] = 0;
				break;
			}
		}
		this.position[1] = this.position[1] + this.speed[1];

		this.speed[0] = bufspeed;
		bufspeed = this.speed[1];
		this.speed[1] = 0;
		if(Main.keyUp) {
			this.speed[0] = this.speed[0] - 1.5*waterAcceleration;
		}
		if(Main.keyDown) {
			this.speed[0] = this.speed[0] + waterAcceleration;
		}

		this.speed[0] = this.speed[0]- this.speed[0] * waterFricCoef;

		tMin = 1;
		for (int i = 0 ; i < Main.nbSceneries ; i++) {
			intersect(Main.sceneries[i]);
			if(tMin<1) {
				this.speed[0] = 0;
				break;
			}
		}

		this.position[0] = this.position[0] + this.speed[0];

		this.speed[1] = bufspeed;

		if(isIn() != 1 && this.speed[0]<0) {
			this.speed[0] = - jumpSpeed;
			nbJump = 1;
			keyJump = false;
		}
	}

	private void updateScale() {
		this.speed[0] = 0;
		this.speed[1] = 0;
		if(Main.keyRight){
			this.speed[1] = moveSpeed;
		}
		if(Main.keyLeft) {
			this.speed[1] = - moveSpeed;
		}

		tMin = 1;
		for (int i = 0 ; i < Main.nbSceneries ; i++) {
			intersect(Main.sceneries[i]);
			if(tMin<1) {
				this.speed[1] = 0;
				break;
			}
		}

		this.position[1] = this.position[1] + this.speed[1];


		if(Main.keyUp) {
			this.speed[0] = - moveSpeed;
		}
		if(Main.keyDown) {
			this.speed[0] = moveSpeed;
		}


		tMin = 1;
		for (int i = 0 ; i < Main.nbSceneries ; i++) {
			intersect(Main.sceneries[i]);
			if(tMin<1) {
				this.speed[0] = 0;
				break;
			}
		}


		this.position[0] = this.position[0] + this.speed[0];
	}

	private void updateTrampoline() {


		this.speed[1] = 0;
		if(Main.keyRight){
			this.speed[1] = moveSpeed;
		}
		if(Main.keyLeft) {
			this.speed[1] = - moveSpeed;
		}
		if(Math.abs(this.speed[0])<1)
		{
			if(Main.keyUp && this.keyJump) {
				this.speed[0] = - jumpSpeed;
				nbJump = maxNbJump;
				Main.sounds.play(4);
			}
		}
		else {
			this.speed[0] = -Math.abs(this.speed[0])*Main.areas[indArea].getSpeedMultJump()-jumpSpeed;
			nbJump = maxNbJump;
			Main.sounds.play(4);
		}

		this.position[1] = this.position[1] + this.speed[1];
		this.position[0] = this.position[0] + this.speed[0];
	}

	private void move() {
		tMin = 1;
		for (int i = 0 ; i < Main.nbSceneries ; i++) {
			intersect(Main.sceneries[i]);
		}

		if(this.speed[0]>5 && tMin<1 && state!=7 && state!=8) {
			Main.sounds.play(1);
		}
		this.speed[0] = tMin * this.speed[0];
		this.speed[1] = tMin * this.speed[1];
		this.position[0] = this.position[0] + this.speed[0] - 0.001;
		this.position[1] = this.position[1] + this.speed[1];
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
						if (Main.debug[3]) {
							System.out.println("cosFloorSlope : " + cosFloorSlope + "   [Character][contactFloor]");
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

		if(Main.keyRight && inactiveRight == 0){
			this.speed[1] = moveSpeed;
			direction = 1;
		}
		if(Main.keyLeft && inactiveLeft == 0){
			this.speed[1] = - moveSpeed;
			direction = -1;
		}
		if(Main.keyUp && inactiveJump == 0 && nbJump < maxNbJump && keyJump){
			nbJump = nbJump + 1;
			keyJump = false;
			this.speed[0] = - jumpSpeed;
			Main.sounds.play(0);
		}
	}

	private void walk() {
		// Reset of the walk speed to stop if any key is pressed
		this.speed[0] = 0;
		this.speed[1] = 0;
		keyJump = true;

		if (inactiveLeft > 4) {
			inactiveLeft = 4;
		}
		if (inactiveRight > 4) {
			inactiveRight = 4;
		}
		if (inactiveJump > 4) {
			inactiveJump = 4;
		}

		if(Main.keyRight && inactiveRight == 0){
			this.speed[1] = Math.sqrt(1 - Math.pow(cosFloorSlope, 2)) * moveSpeed;
			this.speed[0] = cosFloorSlope * moveSpeed;
			direction = 1;
		}
		if(Main.keyLeft && inactiveLeft == 0) {
			this.speed[1] = - Math.sqrt(1 - Math.pow(cosFloorSlope, 2)) * moveSpeed;
			this.speed[0] = - cosFloorSlope * moveSpeed;
			direction = -1;
		}
		if(Main.keyUp && inactiveJump == 0 && keyJump) {
			this.speed[0] = - jumpSpeed;
			nbJump = 1;
			keyJump = false;
			Main.sounds.play(0);
		}
		
	}

	private void reboundBotLeft() {
		inactiveLeft = inactiveTime + 1;
		direction = 1;
		nbJump = 1;
		inactiveRight = 4;
		this.speed[0] = -3;
		this.speed[1] = 5;
		this.position[0] = this.position[0] - 0.01;
		this.position[1] = this.position[1] + 0.01;
	}

	private void reboundBotRight() {
		inactiveRight = inactiveTime + 1;
		direction = -1;
		nbJump = 1;
		inactiveLeft = 4;
		this.speed[0] = -3;
		this.speed[1] = -5;
		this.position[0] = this.position[0] - 0.01;
		this.position[1] = this.position[1] - 0.01;
	}

	private void reboundLeft() {
		inactiveLeft = inactiveTime + 1;
		direction = 1;
		nbJump = 1;
		inactiveRight = 4;
		this.speed[0] = -2;
		this.speed[1] = 3;
		this.position[0] = this.position[0] - 0.01;
		this.position[1] = this.position[1] + 0.01;
	}

	private void reboundRight() {
		inactiveRight = inactiveTime + 1;
		direction = -1;
		nbJump = 1;
		inactiveLeft = 4;
		this.speed[0] = -2;
		this.speed[1] = -3;
		this.position[0] = this.position[0] - 0.01;
		this.position[1] = this.position[1] - 0.01;
	}

	private void reboundTop() {
		inactiveJump = inactiveTime + 1;
		nbJump = 1;
		this.speed[0] = 3;
		this.position[0] = this.position[0] + 0.01;
	}

	private void slideLeft(){
		inactiveLeft = 2 * inactiveTime;

		direction = 1;
		double speedNorm;
		speedNorm = -Math.sqrt(1-cosFloorSlope*cosFloorSlope)*this.speed[1] - cosFloorSlope*this.speed[0] - 1;

		this.speed[1] = -Math.sqrt(1-cosFloorSlope*cosFloorSlope)*(speedNorm*(1-frictionCoef) - slideCoef*Main.gravity*Math.abs(cosFloorSlope));
		this.speed[0] = -cosFloorSlope*(speedNorm*(1-frictionCoef) - slideCoef*Main.gravity*Math.abs(cosFloorSlope));

		if(Main.keyUp && inactiveJump == 0 && keyJump) {
			this.speed[0] = -jumpSpeed;
			this.speed[1] = 0.2 * jumpSpeed;
			nbJump = 1;
			keyJump = false;
			Main.sounds.play(0);
		}
	}

	private void slideRight(){
		inactiveRight = 2 * inactiveTime;

		direction = -1;
		double speedNorm;
		speedNorm = Math.sqrt(1-cosFloorSlope*cosFloorSlope)*this.speed[1] + cosFloorSlope*this.speed[0] + 1;

		this.speed[1] = Math.sqrt(1-cosFloorSlope*cosFloorSlope)*(speedNorm - slideCoef*Main.gravity*Math.abs(cosFloorSlope));
		this.speed[0] = cosFloorSlope*(speedNorm - slideCoef*Main.gravity*Math.abs(cosFloorSlope));

		if(Main.keyUp && inactiveJump == 0 && keyJump) {
			this.speed[0] = -jumpSpeed;
			this.speed[1] = - 0.2 * jumpSpeed;
			nbJump = 1;
			keyJump = false;
			Main.sounds.play(0);
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
			direction = 1;
		}
		else {
			this.speed[1] = -Math.sqrt(1-cosFloorSlope*cosFloorSlope)*moveSpeed/2;
			this.speed[0] = -cosFloorSlope*moveSpeed/2;
			direction = -1;
		}

		if(Main.keyUp) {
			this.speed[0] = -jumpSpeed;
			inactiveLeft = 10;
			inactiveRight = 10;
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
			if (Main.areas[i].isIn(this.position[0], this.position[1]))
			{
				if (Main.debug[8]) {
					System.out.println("Entry in area type : " + Main.areas[i].getType() + "   [Character][isIn]");
				}
				indArea = i;
				return Main.areas[i].getType();
			}
		}
		indArea = -1;
		return 0;
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
				charac.hp = charac.maxHp;
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
			
			if(time == 15)
			{
				Main.sounds.play(3);
			}
			
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
		case 4:
		{
			times[0] = 5;
			times[1] = 2;
			times[2] = 5;
			nbTimes = 3;
			
			if (time == times[0]+1)
			{
				teleport();
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
		}
	}

	private void teleport() {
		int indTp = Main.areas[indArea].getIndTp();
		double mult = Main.areas[indArea].getSpeedMultTp();

		this.speed[0] = mult * this.speed[0];
		this.speed[1] = mult * this.speed[1];

		this.position[0] = Main.areas[indTp].getPositionI() + Main.areas[indTp].getHeight() / 2;
		this.position[1] = Main.areas[indTp].getPositionJ() + Main.areas[indTp].getWidth() / 2;
		Main.screen.centerChar();
	}
}
