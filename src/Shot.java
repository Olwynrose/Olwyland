
public class Shot extends Hitbox{

	// type
	/* 0: inactive, 1:simple shot, 2: bomb, 3: sniper */
	public boolean init;
	public int time;
	public boolean hitMob;
	public boolean hitCharac;
	public boolean stopMob;
	public double damages;
	public int indWeapon;		//index of the weapon that shot the shot
	public int numBombs;
	private double rayAOE;
	private double acceleration;
	private int indArea;		// index of the current area
	
	
	public Shot() {

		this.type = 0;
		this.position = new double[2];
		this.speed = new double[2];
		indWeapon = -1;
		time = 0;
		rayAOE = 0;
		stopMob = false;
	}
	
	public void update() {
		if(this.type > 0) {
			if(isIn() == 6) {
				teleport();
			}
		}
		double buf_Sceneries;
		double buf_Mobs;
		boolean impactMob = false;
		double weaponExp;
		if(init) {
			init = false;
		}
		else {
			if(time > 0) {

				time = time - 1;
				
				// control the fly
				switch(this.type) {
				case 1:
				{
				}
				break;
				case 2:
				{
					speed[0] = speed[0] + Main.gravity;
				}
				break;
				case 3:
				{
				}
				break;
				case 4:
				{
					speed[0] = speed[0] - acceleration;
				}
				break;
				case 5:
				{
					speed[0] = Math.min(speed[0] - acceleration, 0);
					for(int j=0 ; j < 2 ; j++) {
						for(int i = 0 ; i < Main.maxNbShots ; i++) {
							if(Main.friendlyShots[i].type == 0) {
								double a = Math.random();
								Main.friendlyShots[i].fire(1, position[0], position[1], Math.sin(Math.PI*a) , Math.cos(Math.PI*a));
								Main.friendlyShots[i].hitMob = true;
								Main.friendlyShots[i].time = 10;
								Main.friendlyShots[i].damages = this.damages;
								Main.friendlyShots[i].stopMob = false;
								Main.friendlyShots[i].indWeapon = this.indWeapon;
								Main.sounds.play(5);
								break;
							}
						}
					}
				}
				break;
				case 6:
				{
					speed[0] = speed[0] + Main.gravity;
				}
				break;
				}
				
				// control the intersections
				tMin = 1;

				for (int i = 0 ; i < Main.nbSceneries ; i++) {
					intersect(Main.sceneries[i]);
				}
				buf_Sceneries = tMin;
				if(hitMob) {
					for (int i = 0 ; i < Main.maxNbMobs ; i++) {
						intersect(Main.mobs[i]);
						if(tMin < buf_Sceneries) {
							if(stopMob && Main.mobs[i].charac.hp>0) {
								weaponExp = Main.mobs[i].charac.hit(damages);
								if (indWeapon > 0) {
									Main.mainChar.weapon.updateExp(indWeapon, weaponExp);
									impactMob = true;
								}
								break;
							}
							else {
								weaponExp = Main.mobs[i].charac.hit(damages);
								if (indWeapon > 0) {
									Main.mainChar.weapon.updateExp(indWeapon, weaponExp);
								}
								tMin = 1;
							}
						}
					}
				}
				buf_Mobs = Math.min(tMin, buf_Sceneries);
				if(hitCharac) {
					intersect(Main.mainChar);
					if(tMin < buf_Mobs) {
						Main.mainChar.charac.hit(damages);
					}
				}
				tMin = Math.min(Math.min(tMin, buf_Mobs), buf_Sceneries);
				this.position[0] = this.position[0] + 0.999*tMin*speed[0];
				this.position[1] = this.position[1] + 0.999*tMin*speed[1];
				
				if(type == 6) {
					for (int j = 0 ; j < Main.maxNbMobs ; j++) {
						if((Math.pow(Main.mobs[j].position[0]-position[0], 2)+Math.pow(Main.mobs[j].position[1]-position[1], 2))<Math.pow(50,2)) {
							Main.mobs[j].charac.hit(damages);
							impactMob = true;
						}
					}
				}
			}
			else {
				// control the change of behavior (ex explosions)
				switch(this.type) {
				case 1:
				{
				}
				break;
				case 2:
				{
					double maxProjectiles = 20;
					for(int j = 0 ; j < maxProjectiles ; j++) {
						for(int i = 0 ; i < Main.maxNbShots ; i++) {
							if(Main.friendlyShots[i].type == 0) {
								Main.friendlyShots[i].fire(1, position[0], position[1], Math.cos(2*Math.PI*j/maxProjectiles) , Math.sin(2*Math.PI*j/maxProjectiles));
								Main.friendlyShots[i].hitMob = true;
								Main.friendlyShots[i].time = 4;
								Main.friendlyShots[i].damages = this.damages;
								Main.friendlyShots[i].stopMob = false;
								Main.friendlyShots[i].hitCharac = false;
								Main.friendlyShots[i].indWeapon = this.indWeapon;
								Main.sounds.play(7);
								break;
							}
						}
					}
				}
				break;
				case 6:
				{
					if(numBombs>0) {
						for(int j = 0 ; j < numBombs ; j++) {
							for(int i = 0 ; i < Main.maxNbShots ; i++) {
								if(Main.friendlyShots[i].type == 0) {
									Main.friendlyShots[i].fire(6, position[0]-5, position[1], 150*Math.cos(2*Math.PI*j/((double)numBombs)) , 90*Math.sin(2*Math.PI*j/((double)numBombs)) );
									Main.friendlyShots[i].hitMob = true;
									Main.friendlyShots[i].time = 25 + ((int) (15.0*Math.random()));
									Main.friendlyShots[i].damages = this.damages;
									Main.friendlyShots[i].stopMob = false;
									Main.friendlyShots[i].hitCharac = false;
									Main.friendlyShots[i].indWeapon = this.indWeapon;
									Main.friendlyShots[i].numBombs = -1;
									Main.sounds.play(7);
									break;
								}
							}
						}
					}
					else {
						double maxProjectiles = 10;
						for(int j = 0 ; j < maxProjectiles ; j++) {
							for(int i = 0 ; i < Main.maxNbShots ; i++) {
								if(Main.friendlyShots[i].type == 0) {
									Main.friendlyShots[i].fire(1, position[0]-2, position[1], Math.cos(2*Math.PI*j/maxProjectiles) , Math.sin(2*Math.PI*j/maxProjectiles));
									Main.friendlyShots[i].hitMob = true;
									Main.friendlyShots[i].time = 1;
									Main.friendlyShots[i].damages = this.damages;
									Main.friendlyShots[i].stopMob = false;
									Main.friendlyShots[i].hitCharac = false;
									Main.friendlyShots[i].indWeapon = this.indWeapon;
									Main.sounds.play(7);
									break;
								}
							}
						}
					}
				}
				break;
				case 30:
				{
					if(hitMob) {
						for (int j = 0 ; j < Main.maxNbMobs ; j++) {
							if((Math.pow(Main.mobs[j].position[0]-position[0], 2)+Math.pow(Main.mobs[j].position[1]-position[1], 2))<Math.pow(rayAOE,2)) {
								Main.mobs[j].charac.hit(damages);
							}
						}
					}
				}
				break;
				case 31:
				{
					if(hitMob) {
						for (int j = 0 ; j < Main.maxNbMobs ; j++) {
							if((Math.pow(Main.mobs[j].position[0]-position[0], 2)+Math.pow(Main.mobs[j].position[1]-position[1], 2))<Math.pow(rayAOE,2)) {
								Main.mobs[j].charac.hit(damages);
							}
						}
					}
				}
				break;
				}
					
				type = 0;
			}
			
			if(tMin < 1) {
				if(type != 6 || impactMob) {
					time = 0;
				}
				else {
					tMin = 1;
					boolean onFloor = false;
					for (int i = 0 ; i < Main.nbSceneries ; i++) {
						if(Main.sceneries[i].type == 1)
						{
							for (int j = 0 ; j < Main.sceneries[i].getNbPoints() - 1 ; j++) {
								t = lineIntersection( 1, 0,
										position[0] + points[0][0], position[1] + points[0][1],
										Main.sceneries[i].position[0] + Main.sceneries[i].points[j][0], Main.sceneries[i].position[1] + Main.sceneries[i].points[j][1],
										Main.sceneries[i].position[0] + Main.sceneries[i].points[j+1][0], Main.sceneries[i].position[1] + Main.sceneries[i].points[j+1][1]);
								if (t >= 0 && t < 1) {
									onFloor = true;
								}
							}
						}
					}
					if(onFloor) {
						    position[0] = position[0] - 0.01;
							if(numBombs>0) {
								speed[0] = -0.5*speed[0];
							}
							else {
								speed[0] = -speed[0];
							}
					}
					else {
						position[1] = position[1] - 0.1*Math.signum(speed[1]);
						speed[0] = -0.1;
						speed[1] = -0.5*speed[1] - 7*Math.signum(speed[1]);
					}
				}
			}
		}
	}
	
	public void fire(int typeIn, double i0, double j0, double speedi, double speedj) {
		this.position[0] = i0;
		this.position[1] = j0;
		this.type = typeIn;
		init = true; 
		switch(typeIn) {
		// simple shot
		case 1:
		{
			stopMob = true;
			double norm = Math.sqrt(Math.pow(speedi,2)+Math.pow(speedj,2));
			double a = 0.9 + 0.2*Math.random();
			rayAOE = 0;
			damages = 100;
			time = 40;
			this.position[0] = i0;
			this.position[1] = j0;
			this.speed[0] = a*50*speedi/norm;
			this.speed[1] = a*50*speedj/norm;
			this.nbPoints = 2;
			this.points = new double[nbPoints][2];
			this.points[0][0] = 0;
			this.points[0][1] = 0;
			this.points[1][0] = 20*speedi/norm;
			this.points[1][1] = 20*speedj/norm;
			
		}
		break;
		// bomb launcher
		case 2:
		{
			stopMob = true;
			rayAOE = 0;
			time = 100;
			damages = 500;
			this.position[0] = i0;
			this.position[1] = j0;
			this.nbPoints = 4;
			this.points = new double[nbPoints][2];
			this.points[0][0] = 0;
			this.points[0][1] = 0;
			this.points[1][0] = -8;
			this.points[1][1] = -4;
			this.points[2][0] = -8;
			this.points[2][1] = 4;
			this.points[3][0] = 0;
			this.points[3][1] = 0;

			this.speed[0] = speedi*0.15;
			this.speed[1] = speedj*0.15;
			
		}
		break;
		// sniper
		case 3:
		{
			double buf_tMin;
			stopMob = false;
			double norm = Math.sqrt(Math.pow(speedi,2)+Math.pow(speedj,2));
			rayAOE = 0;
			damages = 1500;
			time = 2;
			this.position[0] = i0;
			this.position[1] = j0;
			this.speed[0] = 5000*speedi/norm;
			this.speed[1] = 5000*speedj/norm;
			this.nbPoints = 1;
			this.points = new double[nbPoints][2];
			this.points[0][0] = 0;
			this.points[0][1] = 0;
			
			tMin = 1;
			for (int i = 0 ; i < Main.nbSceneries ; i++) {
				intersect(Main.sceneries[i]);
			}
			buf_tMin = tMin;
			if(hitMob) {
				for (int i = 0 ; i < Main.maxNbMobs ; i++) {
					intersect(Main.mobs[i]);
					if(tMin < buf_tMin) {
						double weaponExp;
						weaponExp = Main.mobs[i].charac.hit(damages);
						if (indWeapon > 0) {
							Main.mainChar.weapon.updateExp(indWeapon, weaponExp);
						}
						tMin = 1;
					}
				}
			}
			if(hitCharac) {
				intersect(Main.mainChar);
				if(tMin < buf_tMin) {
					Main.mainChar.charac.hit(damages);
					tMin = 1;
				}
			}
			tMin = buf_tMin;
			this.nbPoints = 2;
			this.points = new double[nbPoints][2];
			this.points[0][0] = 0;
			this.points[0][1] = 0;
			this.points[1][0] = this.speed[0]*tMin*0.999;
			this.points[1][1] = this.speed[1]*tMin*0.999;
			
		}
		break;
		// fire
		case 4:
		{
			stopMob = false;
			double norm = Math.sqrt(Math.pow(speedi,2)+Math.pow(speedj,2));
			rayAOE = 0;
			damages = 150;
			time = 20;
			acceleration = -0.05 + 0.2*Math.random();
			this.position[0] = i0+10*(Math.random()-0.5);
			this.position[1] = j0+5*(Math.random()-0.5);
			this.speed[0] = 9*speedi/norm;
			this.speed[1] = 9*speedj/norm;
			this.nbPoints = 2;
			this.points = new double[nbPoints][2];
			this.points[0][0] = 10;
			this.points[0][1] = 0;
			this.points[1][0] = -10;
			this.points[1][1] = 0;
			
		}
		break;
		// machingun tornado
		case 5:
		{
			stopMob = false;
			double norm = Math.sqrt(Math.pow(speedi,2)+Math.pow(speedj,2));
			rayAOE = 0;
			damages = 200;
			time = 100;
			acceleration = -0.2;
			this.position[0] = i0+10*(Math.random()-0.5);
			this.position[1] = j0+5*(Math.random()-0.5);
			this.speed[0] = -8;
			this.speed[1] = 3*Math.signum(speedj);
			this.nbPoints = 2;
			this.points = new double[nbPoints][2];
			this.points[0][0] = 0;
			this.points[0][1] = -10;
			this.points[1][0] = 0;
			this.points[1][1] = 10;
			
		}
		break;
		// bouncer
		case 6:
		{
			stopMob = true;
			rayAOE = 0;
			time = 100;
			damages = 500;
			numBombs = 15;
			this.position[0] = i0;
			this.position[1] = j0;
			this.nbPoints = 4;
			this.points = new double[nbPoints][2];
			this.points[0][0] = 0;
			this.points[0][1] = 0;
			this.points[1][0] = -5;
			this.points[1][1] = -4;
			this.points[2][0] = -5;
			this.points[2][1] = 4;
			this.points[3][0] = 0;
			this.points[3][1] = 0;

			this.speed[0] = speedi*0.15;
			this.speed[1] = speedj*0.15;
			
		}
		break;
		}
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
	
	private void teleport() {
		int indTp = Main.areas[indArea].getIndTp();
		double mult = Main.areas[indArea].getSpeedMultTp();

		this.speed[0] = mult * this.speed[0];
		this.speed[1] = mult * this.speed[1];

		this.position[0] = Main.areas[indTp].getPositionI() + Main.areas[indTp].getHeight() / 2;
		this.position[1] = Main.areas[indTp].getPositionJ() + Main.areas[indTp].getWidth() / 2;
	}
	
}
