
public class Weapon {
	int type;
	public boolean keyShot;	// to verify if the shot has been released
	int nbWeapons;
	double dispersion;
	int[] times;
	int[] maxTimes;
	
	
	public Weapon() {
		nbWeapons = 50;
		type = 1;
		keyShot = true;
		times = new int[nbWeapons];
		maxTimes = new int[nbWeapons];
		maxTimes[0] = 3; 	// simple shot
		maxTimes[1] = 15; 	// bomb
		maxTimes[2] = 10;	// sniper
		maxTimes[3] = 0;	// fire
		maxTimes[4] = 115;	// jack3 yeallow 3
		maxTimes[5] = 3;	// simple machingun
		maxTimes[6] = 8;	// shotgun
		dispersion = 0.05;
	}

	public int updateCharac(double posi, double posj, double diri, double dirj) {
		int direction = 0;
		if (Main.mouseLeft && (keyShot || type == 4 || type == 6) && times[type-1] == 0) {
			keyShot = false;
			direction = (int) Math.signum(Main.mouseJ-Main.mainChar.position[1]);
			
			switch(type) {
			case 1:
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
			case 2:
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
			case 3:
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
			case 4:
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
						Main.friendlyShots[i].damages = 150;
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
						if(Main.friendlyShots[i].type == 0) {
							Main.friendlyShots[i].fire(1, posi, posj, diri, dirj);
							Main.friendlyShots[i].hitMob = true;
							Main.friendlyShots[i].stopMob = false;
							Main.friendlyShots[i].time = 10;
							Main.friendlyShots[i].damages = 150;
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
						Main.ennemyShots[i].hitMob = false;
						Main.ennemyShots[i].stopMob = false;
						Main.ennemyShots[i].hitCharac = true;
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
						Main.ennemyShots[i].hitMob = false;
						Main.ennemyShots[i].stopMob = false;
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
						Main.ennemyShots[i].hitMob = false;
						Main.ennemyShots[i].stopMob = false;
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
						Main.ennemyShots[i].hitMob = false;
						Main.ennemyShots[i].stopMob = false;
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
						Main.ennemyShots[i].hitMob = false;
						Main.ennemyShots[i].stopMob = false;
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
						Main.ennemyShots[i].hitMob = false;
						Main.ennemyShots[i].stopMob = false;
						Main.ennemyShots[i].hitCharac = true;
						Main.ennemyShots[i].damages = 150;
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
							Main.ennemyShots[i].hitMob = false;
							Main.ennemyShots[i].stopMob = false;
							Main.ennemyShots[i].hitCharac = true;
							Main.ennemyShots[i].time = 10;
							Main.ennemyShots[i].damages = 150;
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
