
public class Shot extends Hitbox{

	// type
	/* 1:simple shot, 2: bomb */
	public boolean init;
	public int time;
	public boolean hitMob;
	public boolean hitCharac;
	public boolean stopMob;
	public double damages;
	private double rayAOE;
	private double acceleration;
	
	
	public Shot() {

		this.type = 0;
		this.position = new double[2];
		this.speed = new double[2];
		time = 0;
		rayAOE = 0;
		stopMob = false;
	}
	
	public void update() {
		
		double buf_tMin;
		if(init) {
			init = false;
		}
		else {
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
							Main.friendlyShots[i].damages = 200;
							Main.friendlyShots[i].stopMob = false;
							break;
						}
					}
				}
			}
			break;
			}
			
			if(time > 0) {
				time = time - 1;
			}
	
			
			// control the intersections
			if(time > 0) {
				tMin = 1;

				for (int i = 0 ; i < Main.nbSceneries ; i++) {
					intersect(Main.sceneries[i]);
				}
				buf_tMin = tMin;
				if(hitMob) {
					for (int i = 0 ; i < Main.maxNbMobs ; i++) {
						intersect(Main.mobs[i]);
						if(tMin < buf_tMin) {
							Main.mobs[i].charac.hit(damages);
							if(stopMob) {
								break;
							}
							else {
								tMin = 1;
							}
						}
					}
				}
				tMin = Math.min(tMin, buf_tMin);
				this.position[0] = this.position[0] + 0.99*tMin*speed[0];
				this.position[1] = this.position[1] + 0.99*tMin*speed[1];
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
								Main.friendlyShots[i].damages = 200;
								Main.friendlyShots[i].stopMob = false;
								break;
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
				time = 0;
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
			rayAOE = 0;
			damages = 100;
			time = 40;
			this.position[0] = i0;
			this.position[1] = j0;
			this.speed[0] = 50*speedi/norm;
			this.speed[1] = 50*speedj/norm;
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
			this.points[1][0] = -10;
			this.points[1][1] = -4;
			this.points[2][0] = -10;
			this.points[2][1] = 4;
			this.points[3][0] = 0;
			this.points[3][1] = 0;

			this.speed[0] = speedi*0.2;
			this.speed[1] = speedj*0.2;
			
		}
		break;
		// sniper
		case 3:
		{
			stopMob = false;
			double norm = Math.sqrt(Math.pow(speedi,2)+Math.pow(speedj,2));
			rayAOE = 0;
			damages = 1500;
			time = 1;
			this.position[0] = i0;
			this.position[1] = j0;
			this.speed[0] = 5000*speedi/norm;
			this.speed[1] = 5000*speedj/norm;
			this.nbPoints = 2;
			this.points = new double[nbPoints][2];
			this.points[0][0] = 0;
			this.points[0][1] = 0;
			this.points[1][0] = 1000*speedi/norm;
			this.points[1][1] = 1000*speedj/norm;
			
		}
		break;
		// fire
		case 4:
		{
			stopMob = false;
			double norm = Math.sqrt(Math.pow(speedi,2)+Math.pow(speedj,2));
			rayAOE = 0;
			damages = 50;
			time = 20;
			acceleration = -0.05 + 0.2*Math.random();
			this.position[0] = i0+10*(Math.random()-0.5);
			this.position[1] = j0+5*(Math.random()-0.5);
			this.speed[0] = 9*speedi/norm;
			this.speed[1] = 9*speedj/norm;
			this.nbPoints = 2;
			this.points = new double[nbPoints][2];
			this.points[0][0] = 20;
			this.points[0][1] = 0;
			this.points[1][0] = -20;
			this.points[1][1] = 0;
			
		}
		break;
		// machingun tornado
		case 5:
		{
			stopMob = false;
			double norm = Math.sqrt(Math.pow(speedi,2)+Math.pow(speedj,2));
			rayAOE = 0;
			damages = 0;
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
		}
	}
	
}
