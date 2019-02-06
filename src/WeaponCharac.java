import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class WeaponCharac {

	private int type;

	private boolean keyShot;			// to verify if the shot has been released
	private int nbWeapons;
	private double[] dispersion;		//initial dispersion
	private double[] times;				//time
	private double[] coolDown;			//cooldown (frame)
	private int[] scope; 				//initial scope
	private double[] damages;			//initial damages
	public int[] maxMunitions;			//initial max munitions
	public int[] munitions;				//current munitions
	public int[] collectableMunitions; 	//number of collectable munitions

	public SkillTree[] skillTrees;
	public int[] skillPoints;
	private double[] multDmg;
	private double[] multDisp;
	public int[] addMun;
	private double[] multCoolDown;
	private int[] addScope;
	private boolean[] stopMob;
	public int nbRebounds; 				// number of rebounds for the machingun
	public double chanceRebound; 		// percentage of chance of rebounds for the machingun
	public int nbBombs;					// number of bombs for the bouncer
	
	
	public double[] expCurrent;
	private double[] expFirstLevel;
	private int[] skillPointsTotal;
	
	private double r;		//reason of the arithmetic suite that calculates the exp required for the next level of the weapon
	private double q;		//reason of the geometric suite that calculates the exp required for the next level of the weapon
	
	private double[] expMaxLevel;
	private double[] expMinLevel;
	public double[] progressionExp;
	
	public WeaponCharac() throws NumberFormatException, IOException {
		nbWeapons = 8;
		type = 1;
		keyShot = true;
		
		initialisation();

		getSkillTrees();

		loadIndSkillTrees();
		
		getSkillTreeMultipliers();
		
		// ==================================== TEST ======================
		chanceRebound = 20;
		nbRebounds = 3;
		skillPoints[5] = 9; // ====================================== TEST ===============================
		
		
		munitions = new int[nbWeapons];
		for (int i = 1 ; i < nbWeapons ; i++) {
			munitions[i] = maxMunitions[i] + addMun[i];
		}
	}
	
	public int updateCharac(double posi, double posj, double diri, double dirj) {		
		int direction = 0;
		if (Main.mouseLeft && (keyShot || type == 3 || type == 5) && times[type] <0.01 && (munitions[type] > 0 || type == 0)) {
			keyShot = false;
			direction = (int) Math.signum(Main.mouseJ-Main.mainChar.position[1]);
			munitions[type] -= 1;
			if (Main.debug[23]) {
				System.out.println("munitions de l'arme " + type + " : " + munitions[type]);
			}
			
			switch(type) {
			case 0:
			{
				for(int i = 0 ; i < Main.maxNbShots ; i++) {
					if(Main.friendlyShots[i].type == 0) {
						Main.friendlyShots[i].fire(1, posi, posj, diri, dirj);
						Main.friendlyShots[i].hitMob = true;
						Main.friendlyShots[i].time = scope[type] + addScope[type];
						Main.friendlyShots[i].damages = damages[type] * multDmg[type];
						
						Main.friendlyShots[i].indWeapon = type;
						Main.friendlyShots[i].nbRebounds = 6;
						
						Main.sounds.play(5);
						break;
					}
				}
			}
			break;
			case 1:
			{
				for(int i = 0 ; i < Main.maxNbShots ; i++) {
					if(Main.friendlyShots[i].type == 0) {
						Main.friendlyShots[i].fire(2, posi, posj, diri, dirj);
						Main.friendlyShots[i].hitMob = true;
						Main.friendlyShots[i].time = scope[type] + addScope[type];
						Main.friendlyShots[i].damages = damages[type] * multDmg[type];
						Main.friendlyShots[i].indWeapon = type;
						Main.sounds.play(6);
						break;
					}
				}
			}
			break;
			case 2:
			{
				for(int i = 0 ; i < Main.maxNbShots ; i++) {
					if(Main.friendlyShots[i].type == 0) {
						Main.friendlyShots[i].fire(3, posi, posj, diri, dirj);
						Main.friendlyShots[i].hitMob = true;
						Main.friendlyShots[i].stopMob = stopMob[type];
						Main.friendlyShots[i].time = scope[type] + addScope[type];
						Main.friendlyShots[i].damages = damages[type] * multDmg[type];
						Main.friendlyShots[i].indWeapon = type;
						Main.sounds.play(8);
						break;
					}
				}
			}
			break;
			case 3:
			{
				for(int i = 0 ; i < Main.maxNbShots ; i++) {
					if(Main.friendlyShots[i].type == 0) {
						Main.friendlyShots[i].fire(4, posi, posj, diri, dirj);
						Main.friendlyShots[i].hitMob = true;
						Main.friendlyShots[i].time = scope[type] + addScope[type];
						Main.friendlyShots[i].damages = damages[type] * multDmg[type];
						Main.friendlyShots[i].indWeapon = type;
						Main.sounds.play(9);
						break;
					}
				}
			}
			break;
			case 4:
			{
				for(int i = 0 ; i < Main.maxNbShots ; i++) {
					if(Main.friendlyShots[i].type == 0) {
						Main.friendlyShots[i].fire(5, posi, posj, diri, dirj);
						Main.friendlyShots[i].hitMob = true;
						Main.friendlyShots[i].time = scope[type] + addScope[type];
						Main.friendlyShots[i].damages = damages[type] * multDmg[type];
						Main.friendlyShots[i].indWeapon = type;
						break;
					}
				}
			}
			break;
			case 5:
			{
				double theta = Math.atan2(diri, dirj);
				double rand_thata = theta-(dispersion[type] * multDisp[type]) + 2*(dispersion[type] * multDisp[type])*Math.random();
				diri = Math.sin(rand_thata);
				dirj = Math.cos(rand_thata);
				for(int i = 0 ; i < Main.maxNbShots ; i++) {
					if(Main.friendlyShots[i].type == 0) {
						Main.friendlyShots[i].fire(1, posi, posj, diri, dirj);
						Main.friendlyShots[i].hitMob = true;
						Main.friendlyShots[i].time = scope[type] + addScope[type];
						Main.friendlyShots[i].damages = damages[type] * multDmg[type];
						if(100*Math.random()<chanceRebound) {
							Main.friendlyShots[i].nbRebounds = nbRebounds;
						}
						Main.friendlyShots[i].indWeapon = type;
						Main.sounds.play(5);
						break;
					}
				}
			}
			break;
			case 6:
			{
				double theta = Math.atan2(diri, dirj);
				double rand_thata;
				int nbProj = 10;
				for(int j=0; j<nbProj; j++) {
					rand_thata = theta - (dispersion[type] * multDisp[type]) + 2*(dispersion[type] * multDisp[type])*Math.random();
					diri = Math.sin(rand_thata);
					dirj = Math.cos(rand_thata);
					for(int i = 0 ; i < Main.maxNbShots ; i++) {
						if(Main.friendlyShots[i].type == 0) {
							Main.friendlyShots[i].fire(1, posi, posj, diri, dirj);
							Main.friendlyShots[i].hitMob = true;
							Main.friendlyShots[i].stopMob = stopMob[type];
							Main.friendlyShots[i].time = scope[type] + addScope[type];
							Main.friendlyShots[i].damages = damages[type] * multDmg[type];
							Main.friendlyShots[i].indWeapon = type;
							Main.sounds.play(10);
							break;
						}
					}
				}
			}
			break;
			case 7:
			{
				for(int i = 0 ; i < Main.maxNbShots ; i++) {
					if(Main.friendlyShots[i].type == 0) {
						Main.friendlyShots[i].fire(6, posi, posj, diri, dirj);
						Main.friendlyShots[i].hitMob = true;
						Main.friendlyShots[i].time = scope[type] + addScope[type];
						Main.friendlyShots[i].damages = damages[type] * multDmg[type];
						Main.friendlyShots[i].indWeapon = type;
						Main.friendlyShots[i].nbBombs = nbBombs;
						Main.sounds.play(6);
						break;
					}
				}
			}
			break;
			}
			
			times[type] = times[type] + multCoolDown[type] * coolDown[type];
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
	
	public void getSkillTrees() throws NumberFormatException, IOException {
		File file = new File("files/Data/Weapons/SkillTree/SkillTreesNames.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		for (int i = 1 ; i < nbWeapons ; i++) {
			skillTrees[i] = new SkillTree(br.readLine());
		}
		br.close();
	}
	
	public void getSkillTreeMultipliers() {
		multDmg[0] = 1;
		multCoolDown[0] = 1;
		multDisp[0] = 1;
		stopMob[0] = true;
		nbBombs = 5;
		nbRebounds = 1;
		chanceRebound = 0;
		
		for (int n = 1 ; n < nbWeapons ; n++) {
			multDmg[n] = 1;
			addMun[n] = 0;
			multCoolDown[n] = 1;
			multDisp[n] = 1;
			addScope[n] = 0;
			stopMob[n] = true;
			
			if (skillTrees[n].indCC > 0) {
				for (int i = 0 ; i < skillTrees[n].indCC ; i++) {
					if (Main.debug[21]) {
						System.out.println("common");
						System.out.println(skillTrees[n].getSkillType(0, i));
						System.out.println(skillTrees[n].getSkillValue(0, i));
					}
					
					switch(skillTrees[n].getSkillType(0, i)) {
					case 1:
					{
						multDmg[n] = multDmg[n] * (1+skillTrees[n].getSkillValue(0, i)/100);
					}
						break;
					case 2:
					{
						addMun[n] = addMun[n] + (int)skillTrees[n].getSkillValue(0, i);
					}
						break;
					case 3:
					{
						multCoolDown[n] = multCoolDown[n] * (1-skillTrees[n].getSkillValue(0, i)/100);
					}
						break;
					case 4:
					{
						multDisp[n] = multDisp[n] * (1+skillTrees[n].getSkillValue(0, i)/100);
					}
					break;
					case 5:
					{
						addScope[n] = addScope[n] + (int)skillTrees[n].getSkillValue(0, i);
					}
					break;
					case 6: {
						if (skillTrees[n].getSkillValue(0, i) == 0) {
							stopMob[n] = true;
						}
						else {
							stopMob[n] = false;
						}
					}
					}
				}
				if (skillTrees[n].indFB > 0) {
					for (int i = 0 ; i < skillTrees[n].indFB ; i++) {
						if (Main.debug[21]) {
							System.out.println("First branch");
							System.out.println(skillTrees[n].getSkillType(1, i));
							System.out.println(skillTrees[n].getSkillValue(1, i));
						}
						switch(skillTrees[n].getSkillType(1, i)) {
						case 1: 
						{
							multDmg[n] = multDmg[n] * (1+skillTrees[n].getSkillValue(1, i)/100);
						}
							break;
						case 2:
						{
							addMun[n] = addMun[n] + (int)skillTrees[n].getSkillValue(1, i);
						}
							break;
						case 3:
						{
							multCoolDown[n] = multCoolDown[n] * (1-skillTrees[n].getSkillValue(1, i)/100);
						}
							break;
						case 4:
						{
							multDisp[n] = multDisp[n] * (1+skillTrees[n].getSkillValue(1, i)/100);
						}
						break;
						case 5:
						{
							addScope[n] = addScope[n] + (int)skillTrees[n].getSkillValue(1, i);

							System.out.println(skillTrees[n].getSkillValue(1, i));
						}
						break;
						case 6: {
							if (skillTrees[n].getSkillValue(1, i) == 0) {
								stopMob[n] = true;
							}
							else {
								stopMob[n] = false;
							}
						}
						}
					}
				}
				if (skillTrees[n].indSB > 0) {
					for (int i = 0 ; i < skillTrees[n].indSB ; i++) {
						if (Main.debug[21]) {
							System.out.println("Second branch");
							System.out.println(skillTrees[n].getSkillType(2, i));
							System.out.println(skillTrees[n].getSkillValue(2, i));
						}
						switch(skillTrees[n].getSkillType(2, i)) {
						case 1: 
						{
							multDmg[n] = multDmg[n] * (1+skillTrees[n].getSkillValue(2, i)/100);
						}
							break;
						case 2:
						{
							addMun[n] = addMun[n] + (int)skillTrees[n].getSkillValue(2, i);
						}
							break;
						case 3:
						{
							multCoolDown[n] = multCoolDown[n] * (1-skillTrees[n].getSkillValue(2, i)/100);
						}
							break;
						case 4:
						{
							multDisp[n] = multDisp[n] * (1+skillTrees[n].getSkillValue(2, i)/100);
						}
						break;
						case 5:
						{
							addScope[n] = addScope[n] + (int)skillTrees[n].getSkillValue(2, i);
						}
						break;
						case 6: {
							if (skillTrees[n].getSkillValue(2, i) == 0) {
								stopMob[n] = true;
							}
							else {
								stopMob[n] = false;
							}
						}
						}
					}
				}
				if (skillTrees[n].indTB > 0) {
					for (int i = 0 ; i < skillTrees[n].indTB ; i++) {
						if (Main.debug[21]) {
							System.out.println("Third branch");
							System.out.println(skillTrees[n].getSkillType(3, i));
							System.out.println(skillTrees[n].getSkillValue(3, i));
						}
						switch(skillTrees[n].getSkillType(3, i)) {
						case 1: 
						{
							multDmg[n] = multDmg[n] * (1+skillTrees[n].getSkillValue(3, i)/100);
						}
							break;
						case 2:
						{
							addMun[n] = addMun[n] + (int)skillTrees[n].getSkillValue(3, i);
						}
							break;
						case 3:
						{
							multCoolDown[n] = multCoolDown[n] * (1-skillTrees[n].getSkillValue(3, i)/100);
						}
							break;
						case 4:
						{
							multDisp[n] = multDisp[n] * (1+skillTrees[n].getSkillValue(3, i)/100);
						}
						break;
						case 5:
						{
							addScope[n] = addScope[n] + (int)skillTrees[n].getSkillValue(3, i);
						}
						break;
						case 6: {
							if (skillTrees[n].getSkillValue(3, i) == 0) {
								stopMob[n] = true;
							}
							else {
								stopMob[n] = false;
							}
						}
						}
					}
				}
			}
		}
	}
	
	private void initialisation() throws NumberFormatException, IOException {
		maxMunitions = new int[nbWeapons];
		maxMunitions[1] = 5;	// bomb
		maxMunitions[2] = 5;	// sniper
		maxMunitions[3] = 50;	// fire
		maxMunitions[4] = 2;	// jack3 yeallow 3
		maxMunitions[5] = 100;	// simple machingun 
		maxMunitions[6] = 20;	// shotgun
		maxMunitions[7] = 8;	// bouncer
		
		collectableMunitions = new int[nbWeapons];
		collectableMunitions[1] = 2;	// bomb
		collectableMunitions[2] = 2;	// sniper
		collectableMunitions[3] = 10;	// fire
		collectableMunitions[4] = 1;	// jack3 yeallow 3
		collectableMunitions[5] = 20;	// simple machingun 
		collectableMunitions[6] = 5;	// shotgun
		collectableMunitions[6] = 2;	// bouncer
		
		coolDown = new double[nbWeapons];
		coolDown[0] = 8; 	// simple shot
		coolDown[1] = 15; 	// bomb
		coolDown[2] = 10;	// sniper
		coolDown[3] = 4;	// fire
		coolDown[4] = 115;	// jack3 yeallow 3
		coolDown[5] = 4;	// simple machingun
		coolDown[6] = 15;	// shotgun
		coolDown[7] = 60;	// shotgun
		
		dispersion = new double[nbWeapons];
		dispersion[5] = 0.1;	// simple machingun 
		dispersion[6] = 0.2;	// shotgun
		
		damages = new double[nbWeapons];
		damages[0] = 50; 
		damages[1] = 500;
		damages[2] = 1500;
		damages[3] = 40;
		damages[4] = 200;
		damages[5] = 80;
		damages[6] = 20;
		damages[7] = 80;
		
		scope = new int[nbWeapons];
		scope[0] = 8;
		scope[1] = 40;
		scope[2] = 2;
		scope[3] = 20;
		scope[4] = 100;
		scope[5] = 12;
		scope[6] = 5;
		scope[7] = 40;
		
		expFirstLevel = new double[nbWeapons];
		expFirstLevel[1] = 10;
		expFirstLevel[2] = 10;
		expFirstLevel[3] = 10;
		expFirstLevel[4] = 10;
		expFirstLevel[5] = 10;
		expFirstLevel[6] = 10;
		expFirstLevel[7] = 10;

		times = new double[nbWeapons];
		
		skillTrees = new SkillTree[nbWeapons];		
		
		skillPoints = new int [nbWeapons];
		
		multDmg = new double[nbWeapons];
		multDisp = new double[nbWeapons];
		addMun = new int[nbWeapons];
		multCoolDown = new double[nbWeapons];
		addScope = new int[nbWeapons];
		stopMob = new boolean[nbWeapons];

		expCurrent = new double[nbWeapons];
		skillPointsTotal = new int[nbWeapons];
		expMinLevel = new double[nbWeapons];
		expMaxLevel = new double[nbWeapons];

		loadExp();
		
		q = 1.05;
		r = 0.2;
		progressionExp = new double[nbWeapons];
		
		for (int i = 1 ; i < nbWeapons ; i++) {
			expMaxLevel[i] = expFirstLevel[i];
			expMinLevel[i] = 0;
			
			updateExp(i, 0);
		}
		
		
		
	}
	
	public void loadIndSkillTrees() throws NumberFormatException, IOException {
		File fileInd = new File("files/Save/Weapons/SkillTrees.txt");
		BufferedReader brInd = new BufferedReader(new FileReader(fileInd));
		
		for (int i = 1 ; i < nbWeapons ; i++) {
			skillTrees[i].indCC = Integer.parseInt(brInd.readLine());
			skillTrees[i].indFB = Integer.parseInt(brInd.readLine());
			skillTrees[i].indSB = Integer.parseInt(brInd.readLine());
			skillTrees[i].indTB = Integer.parseInt(brInd.readLine());
		}
		brInd.close();
	}
	
	public int getType() {
		return this.type;
	}
	
	public void setType(int t) {
		this.type = t;
		if (Main.debug[22]) {
			System.out.println("multDmg : " + this.multDmg[type]);
			System.out.println("multDisp : " + this.multDisp[type]);
			System.out.println("addMun : " + this.addMun[type]);
			System.out.println("multcd : " + this.multCoolDown[type]);
			System.out.println("addscope : " + this.addScope[type]);
			System.out.println("stopMob : " + this.stopMob[type]);
		}
	}
	
	public void loadExp() throws IOException {
		File fileCurrentExp = new File("files/Save/Weapons/CurrentExperience.txt");
		BufferedReader brCE = new BufferedReader(new FileReader(fileCurrentExp));
		for (int i = 1 ; i < nbWeapons ; i++) {
			expCurrent[i] = Double.parseDouble(brCE.readLine());
		}
		brCE.close();
	}
	
	public void updateExp(int indWeapon, double additionalExp) {
		double diffExp;
		expCurrent[indWeapon] += additionalExp;
		while(expCurrent[indWeapon] > expMaxLevel[indWeapon]) {
			diffExp = expMaxLevel[indWeapon] - expMinLevel[indWeapon];
			expMinLevel[indWeapon] = expMaxLevel[indWeapon];
			expMaxLevel[indWeapon] += diffExp*q+r*expFirstLevel[indWeapon];
			
			skillPointsTotal[indWeapon] += 1;
			skillPoints[indWeapon] += 1; 
			
			if (Main.debug[20]) {
				System.out.println("Level up !");
				System.out.println("skill points total : " + skillPointsTotal[indWeapon]);
				System.out.println("Skill points of the weapon " + indWeapon + " : " + skillPoints[indWeapon]);
			}
		}
		if (Main.debug[20]) {
			System.out.println("Weapon : " + indWeapon);
			System.out.println("exp : " + expCurrent[indWeapon]);
			System.out.println("progression : " + (expCurrent[indWeapon]-expMinLevel[indWeapon])/(expMaxLevel[indWeapon]-expMinLevel[indWeapon]));
			System.out.println("Skill points : " + skillPoints[indWeapon]);
		}
		progressionExp[indWeapon] = (expCurrent[indWeapon]-expMinLevel[indWeapon])/(expMaxLevel[indWeapon]-expMinLevel[indWeapon]);
	}
	
	public boolean updgrade(int iW, int iB, int iS) {	
		switch(iB) {
		case 0:
		{
			if (Main.debug[25]) {
				System.out.println("Arme : " + iW);
				System.out.println("indCC : " + skillTrees[iW].indCC);
				System.out.println("iS : " + iS);
				System.out.println("skillPoints : " + skillPoints[iW]);
				System.out.println("cost : " + skillTrees[iW].CommonCore[iS].getCost());
			}
			
			if (iS == skillTrees[iW].indCC && skillPoints[iW] >= skillTrees[iW].CommonCore[iS].getCost()) {
				skillTrees[iW].indCC += 1;
				skillPoints[iW] -= 1;
				getSkillTreeMultipliers();
				return true;
			}
		}
		break;
		case 1:
		{
			if (Main.debug[25]) {
				System.out.println("indFB : " + skillTrees[iW].indFB);
				System.out.println("iS : " + iS);
				System.out.println("skillPoints : " + skillPoints[iW]);
				System.out.println("cost : " + skillTrees[iW].FirstBranch[iS].getCost());
			}
			
			if ((iS == skillTrees[iW].indFB && skillTrees[iW].indCC == skillTrees[iW].nbSkillsCC) && skillPoints[iW] >= skillTrees[iW].FirstBranch[iS].getCost()) {
				skillTrees[iW].indFB += 1;
				skillPoints[iW] -= 1;
				getSkillTreeMultipliers();
				return true;
			}
		}
		break;
		case 2:
		{
			if (Main.debug[25]) {
				System.out.println("indSB : " + skillTrees[iW].indSB);
				System.out.println("iS : " + iS);
				System.out.println("skillPoints : " + skillPoints[iW]);
				System.out.println("cost : " + skillTrees[iW].SecondBranch[iS].getCost());
			}
			
			if ((iS == skillTrees[iW].indSB && skillTrees[iW].indCC == skillTrees[iW].nbSkillsCC) && skillPoints[iW] >= skillTrees[iW].FirstBranch[iS].getCost()) {
				skillTrees[iW].indSB += 1;
				skillPoints[iW] -= 1;
				getSkillTreeMultipliers();
				return true;
			}
		}
		break;
		case 3:
		{
			if (Main.debug[25]) {
				System.out.println("indTB : " + skillTrees[iW].indTB);
				System.out.println("iS : " + iS);
				System.out.println("skillPoints : " + skillPoints[iW]);
				System.out.println("cost : " + skillTrees[iW].ThirdBranch[iS].getCost());
			}
			
			if ((iS == skillTrees[iW].indTB && skillTrees[iW].indCC == skillTrees[iW].nbSkillsCC) && skillPoints[iW] >= skillTrees[iW].FirstBranch[iS].getCost()) {
				skillTrees[iW].indTB += 1;
				skillPoints[iW] -= 1;
				getSkillTreeMultipliers();
				return true;
			}
		}
		break;
		}
		return false;
	}
	
	public void collectMunitions(int t) {
		munitions[t] = Math.min(maxMunitions[t]+addMun[t], munitions[t]+collectableMunitions[t]);
	}
	
	public boolean fullMunitions(int t) {
		if (munitions[t] == maxMunitions[t] + addMun[t]) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean getKeyShot() {
		return this.keyShot;
	}
	
	public void setKeyShot(boolean ks) {
		this.keyShot = ks;
	}
	
	public int getNbMaxMunitions() {
		return maxMunitions[type] + addMun[type];
	}
	
	public int getNbWeapon() {
		return nbWeapons;
	}
	public int getIndWeapon() {
		if (this.type != 0) {
			return this.type;
		}
		else {
			return 1;
		}
	}
}
