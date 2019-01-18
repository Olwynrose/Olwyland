
public class Shot extends Hitbox{

	// type
	/* 1:simple shot, 2: bomb */
	public int time;
	public boolean hitMob;
	public boolean hitCharac;
	public double damages;
	private double rayAOE;
	
	
	public Shot() {

		this.type = 0;
		this.position = new double[2];
		this.speed = new double[2];
		time = 0;
		rayAOE = 0;
	}
	
	public void update() {
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
		}
		
		if(time > 0) {
			time = time - 1;
		}

		
		
		if(time > 0) {
			tMin = 1;
			if(hitMob) {
				for (int i = 0 ; i < Main.maxNbMobs ; i++) {
					intersect(Main.mobs[i]);
					if(tMin < 1) {
						Main.mobs[i].hp = Main.mobs[i].hp - damages / Main.mobs[i].defence;
						break;
					}
				}
			}
			for (int i = 0 ; i < Main.nbSceneries ; i++) {
				intersect(Main.sceneries[i]);
			}
			this.position[0] = this.position[0] + tMin*speed[0];
			this.position[1] = this.position[1] + tMin*speed[1];
		}
		else {
			
			if(rayAOE>0 && type > 0) {
				for (int j = 0 ; j < Main.maxNbMobs ; j++) {
					if((Math.pow(Main.mobs[j].position[0]-position[0], 2)+Math.pow(Main.mobs[j].position[1]-position[1], 2))<Math.pow(rayAOE,2)) {
						Main.mobs[j].hp = Main.mobs[j].hp - damages / Main.mobs[j].defence;
					}
				}
			}
			type = 0;
		}
		
		if(tMin < 1) {
			time = 0;
		}
	}
	
	public void fire(int typeIn, double i0, double j0, double speedi, double speedj) {
		this.position[0] = i0;
		this.position[1] = j0;
		this.type = typeIn;
		switch(typeIn) {
		case 1:
		{
			rayAOE = 0;
			damages = 100;
			time = 40;
			this.position[0] = i0;
			this.position[1] = j0 + speedj;
			this.speed[0] = 0;
			this.nbPoints = 2;
			this.points = new double[nbPoints][2];
			this.points[0][0] = 0;
			this.points[0][1] = 0;
			this.points[1][0] = 0;
			if(speedj>0) {
				this.speed[1] = 50;
				this.points[1][1] = 20;
			}
			else {
				this.speed[1] = -50;
				this.points[1][1] = -20;
			}
			
		}
		break;
		case 2:
		{
			rayAOE = 100;
			time = 100;
			damages = 1000;
			this.position[0] = i0;
			this.position[1] = j0 + speedj;
			this.speed[0] = -20;
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
			if(speedj>0) {
				this.speed[1] = 25;
			}
			else {
				this.speed[1] = -25;
			}
			
		}
		break;
		}
	}
	
}
