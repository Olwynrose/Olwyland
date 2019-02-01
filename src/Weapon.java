
public class Weapon {
	int type;
	boolean hitMob;
	public boolean keyShot;	// to verify if the shot has been released
	int nbWeapons;
	double dispersion;
	double[] times;
	double[] maxTimes;
	
	
	public Weapon() {
		nbWeapons = 50;
		type = 1;
		keyShot = true;
		hitMob = false;
		times = new double[nbWeapons];
		maxTimes = new double[nbWeapons];
		maxTimes[0] = 8; 	// simple shot
		maxTimes[1] = 15; 	// bomb
		maxTimes[2] = 10;	// sniper
		maxTimes[3] = 4;	// fire
		maxTimes[4] = 115;	// jack3 yeallow 3
		maxTimes[5] = 3;	// simple machingun
		maxTimes[6] = 8;	// shotgun
		dispersion = 0.05;
	}

	public int updateCharac(double posi, double posj, double diri, double dirj) {
		int direction = 0;
		if (Main.mouseLeft && (keyShot || type == 4 || type == 6) && times[type-1] <0.01) {
			keyShot = false;
			direction = (int) Math.signum(Main.mouseJ-Main.mainChar.position[1]);
			
			switch(type) {
			case 1:
			{
				for(int i = 0 ; i < Main.maxNbShots ; i++) {
					if(Main.friendlyShots[i].type == 0) {
						Main.friendlyShots[i].fire(type, posi, posj, diri, dirj);
						Main.friendlyShots[i].hitMob = true;
						Main.friendlyShots[i].time = 8;
						Main.friendlyShots[i].damages = 50;
						Main.sounds.play(5);
						break;
					}
				}
			}
			break;
			case 2:
			{
				for(int i = 0 ; i < Main.maxNbShots ; i++) {
					if(Main.friendlyShots[i].type == 0) {
						Main.friendlyShots[i].fire(type, posi, posj, diri, dirj);
						Main.friendlyShots[i].hitMob = true;
						Main.sounds.play(6);
						break;
					}
				}
			}
			break;
			case 3:
			{
				for(int i = 0 ; i < Main.maxNbShots ; i++) {
					if(Main.friendlyShots[i].type == 0) {
						Main.friendlyShots[i].fire(type, posi, posj, diri, dirj);
						Main.friendlyShots[i].hitMob = true;
						Main.sounds.play(8);
						break;
					}
				}
			}
			break;
			case 4:
			{
				for(int i = 0 ; i < Main.maxNbShots ; i++) {
					if(Main.friendlyShots[i].type == 0) {
						Main.friendlyShots[i].fire(type, posi, posj, diri, dirj);
						Main.friendlyShots[i].hitMob = true;
						Main.sounds.play(9);
						Main.friendlyShots[i].damages = 40;
						break;
					}
				}
			}
			break;
			case 5:
			{
				for(int i = 0 ; i < Main.maxNbShots ; i++) {
					if(Main.friendlyShots[i].type == 0) {
						Main.friendlyShots[i].fire(type, posi, posj, diri, dirj);
						Main.friendlyShots[i].hitMob = true;
						break;
					}
				}
			}
			break;
			case 6:
			{
				double theta = Math.atan2(diri, dirj);
				double rand_thata = theta-dispersion + 2*dispersion*Math.random();
				diri = Math.sin(rand_thata);
				dirj = Math.cos(rand_thata);
				for(int i = 0 ; i < Main.maxNbShots ; i++) {
					if(Main.friendlyShots[i].type == 0) {
						Main.friendlyShots[i].fire(1, posi, posj, diri, dirj);
						Main.friendlyShots[i].hitMob = true;
						Main.friendlyShots[i].damages = 80;
						Main.sounds.play(5);
						break;
					}
				}
			}
			break;
			case 7:
			{
				double theta = Math.atan2(diri, dirj);
				double rand_thata;
				double disp = 0.2;
				int nbProj = 10;
				for(int j=0; j<nbProj; j++) {
					rand_thata = theta - disp + 2*disp*Math.random();
					diri = Math.sin(rand_thata);
					dirj = Math.cos(rand_thata);
					for(int i = 0 ; i < Main.maxNbShots ; i++) {
						if(Main.friendlyShots[i].type == 0) {
							Main.friendlyShots[i].fire(1, posi, posj, diri, dirj);
							Main.friendlyShots[i].hitMob = true;
							Main.friendlyShots[i].stopMob = true;
							Main.friendlyShots[i].time = 5;
							Main.friendlyShots[i].damages = 20;
							Main.sounds.play(10);
							break;
						}
					}
				}
			}
			break;
			}
			
			times[type - 1] = times[type - 1] + maxTimes[type - 1];
		}
		for(int i = 0; i<nbWeapons; i++) {
			if(times[i]>-1) {
				times[i] = times[i] - 1;
			}
			else {
				times[i] = -1;
			}
		}
		return direction;
	}
	
	public int updateMob(boolean shot, double posi, double posj, double diri, double dirj) {
		int direction = 0;
		if (shot && times[type-1] == 0) {
			direction = (int) Math.signum(dirj);

			switch(type) {
			case 1:
			{
				for(int i = 0 ; i < Main.maxNbShots ; i++) {
					if(Main.ennemyShots[i].type == 0) {
						Main.ennemyShots[i].fire(type, posi, posj, diri, dirj);
						Main.ennemyShots[i].hitMob = this.hitMob;
						Main.ennemyShots[i].stopMob = this.hitMob;
						Main.ennemyShots[i].hitCharac = true;
						Main.sounds.play(5);
						break;
					}
				}
			}
			break;
			case 2:
			{
				for(int i = 0 ; i < Main.maxNbShots ; i++) {
					if(Main.ennemyShots[i].type == 0) {
						Main.ennemyShots[i].fire(type, posi, posj, diri, dirj);
						Main.ennemyShots[i].hitMob = this.hitMob;
						Main.ennemyShots[i].stopMob = this.hitMob;
						Main.ennemyShots[i].hitCharac = true;
						break;
					}
				}
			}
			break;
			case 3:
			{
				for(int i = 0 ; i < Main.maxNbShots ; i++) {
					if(Main.ennemyShots[i].type == 0) {
						Main.ennemyShots[i].fire(type, posi, posj, diri, dirj);
						Main.ennemyShots[i].hitMob = this.hitMob;
						Main.ennemyShots[i].stopMob = this.hitMob;
						Main.ennemyShots[i].hitCharac = true;
						break;
					}
				}
			}
			break;
			case 4:
			{
				for(int i = 0 ; i < Main.maxNbShots ; i++) {
					if(Main.ennemyShots[i].type == 0) {
						Main.ennemyShots[i].fire(type, posi, posj, diri, dirj);
						Main.ennemyShots[i].hitMob = this.hitMob;
						Main.ennemyShots[i].stopMob = this.hitMob;
						Main.ennemyShots[i].hitCharac = true;
						break;
					}
				}
			}
			break;
			case 5:
			{
				for(int i = 0 ; i < Main.maxNbShots ; i++) {
					if(Main.ennemyShots[i].type == 0) {
						Main.ennemyShots[i].fire(type, posi, posj, diri, dirj);
						Main.ennemyShots[i].hitMob = this.hitMob;
						Main.ennemyShots[i].stopMob = this.hitMob;
						Main.ennemyShots[i].hitCharac = true;
						break;
					}
				}
			}
			break;
			case 6:
			{
				double theta = Math.atan2(diri, dirj);
				double rand_thata = theta-dispersion + 2*dispersion*Math.random();
				diri = Math.sin(rand_thata);
				dirj = Math.cos(rand_thata);
				for(int i = 0 ; i < Main.maxNbShots ; i++) {
					if(Main.ennemyShots[i].type == 0) {
						Main.ennemyShots[i].fire(1, posi, posj, diri, dirj);
						Main.ennemyShots[i].hitMob = this.hitMob;
						Main.ennemyShots[i].stopMob = this.hitMob;
						Main.ennemyShots[i].hitCharac = true;
						Main.ennemyShots[i].damages = 150;
						Main.sounds.play(5);
						break;
					}
				}
			}
			break;
			case 7:
			{
				double theta = Math.atan2(diri, dirj);
				double rand_thata;
				for(int j=0; j<7; j++) {
					rand_thata = theta-0.3 + 0.6*Math.random();
					diri = Math.sin(rand_thata);
					dirj = Math.cos(rand_thata);
					for(int i = 0 ; i < Main.maxNbShots ; i++) {
						if(Main.ennemyShots[i].type == 0) {
							Main.ennemyShots[i].fire(1, posi, posj, diri, dirj);
							Main.ennemyShots[i].hitMob = this.hitMob;
							Main.ennemyShots[i].stopMob = this.hitMob;
							Main.ennemyShots[i].hitCharac = true;
							Main.ennemyShots[i].time = 10;
							Main.ennemyShots[i].damages = 150;
							Main.sounds.play(8);
							break;
						}
					}
				}
			}
			break;
			}
			
			times[type - 1] = maxTimes[type - 1];
		}
		for(int i = 0; i<nbWeapons; i++) {
			if(times[i]>0) {
				times[i] = times[i] - 1;
			}
			else {
				times[i] = 0;
			}
		}
		return direction;
	}
}
