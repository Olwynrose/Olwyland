
public class Spawn {
	

	public boolean active;			// the mobs spawn if active == true
	
	private int totalNbMobs;			// total number of mobs
	private int maxNbMobs;			// number of mobs at the same time
	private int nbMobs;				//number of spawned mobs
	private int nbSpawns;			// number of spawn points
	private double[][] spawnPoints;	// coordinates of the spawn points
	private int[] indMobs;			// index of the mobs alives
	public int type;				// mobs type
	public boolean hitMob;
	public int idSpawn;			// to identify the mobs
	public double limI0, limI1, limJ0, limJ1;	// localization constrains
	
	public Spawn(int nbSpawn, int total, int maxMob) {
		nbSpawns = nbSpawn;
		spawnPoints = new double[nbSpawn][2];
		active = false;
		type = 1;
		totalNbMobs = total;
		maxNbMobs = maxMob;
		indMobs = new int[maxMob];
		for(int n = 0; n < maxNbMobs; n++) {
			indMobs[n] = -1;
		}
		nbMobs = 0;
		hitMob = false;
		limI0 = -100000000.0;
		limI1 = 100000000.0;
		limJ0 = -100000000.0;
		limJ1 = 100000000.0;
	}
	
	public void update() {
		
		double ci, cj;
		int a;
		if(active) {
			for (int nb = 0; nb<maxNbMobs; nb++) {
				if(indMobs[nb] < 0 && nbMobs < totalNbMobs) {
					// create a new mob
					for(int n = 0; n < Main.maxNbMobs; n++) {
						if(Main.mobs[n].typeMob==0) {
							a = (int) Math.floor(nbSpawns*Math.random()-0.0001);
							ci = spawnPoints[a][0];
							cj = spawnPoints[a][1];
							Main.mobs[n] = new Mob(type);
							Main.mobs[n].idSpawn = idSpawn;
							Main.mobs[n].checkPoint[0] = ci;
							Main.mobs[n].checkPoint[1] = cj;
							Main.mobs[n].position[0] = ci;
							Main.mobs[n].position[1] = cj;
							Main.mobs[n].weapon.hitMob = hitMob;
							Main.mobs[n].limI0 = limI0;
							Main.mobs[n].limI1 = limI1;
							Main.mobs[n].limJ0 = limJ0;
							Main.mobs[n].limJ1 = limJ1;
							nbMobs = nbMobs + 1;
							indMobs[nb] = n;
							break;
						}
					}
				}
			}
			
			a = 0;
			for (int nb = 0; nb<maxNbMobs; nb++) {
				if(indMobs[nb]>=0) {
					if(Main.mobs[indMobs[nb]].idSpawn != idSpawn ) {
						indMobs[nb] = -1;
					}
					else {
						a = a + 1;
					}
				}
			}

			if(a == 0 && nbMobs == totalNbMobs) {
				// all the mobs have been spawned and are dead
				
			}
			
		}
	}
	
	public void setOnePoint(int ind, double i, double j) {
		spawnPoints[ind][0] = i;
		spawnPoints[ind][1] = j;
	}
	
	
	
}
