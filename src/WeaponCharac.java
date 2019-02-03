import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class WeaponCharac {

	private int type;

	private boolean keyShot;	// to verify if the shot has been released
	private int nbWeapons;
	private double[] dispersion;//initial dispersion
	private double[] times;		//time
	private double[] coolDown;	//cooldown (frame)
	private int[] scope; 		//initial scope
	private double[] damages;	//initial damages
	private int[] munitions;	//initial munitions

	private SkillTree[] skillTrees;
	private double[] multDmg;
	private double[] multDisp;
	private int[] addMun;
	private double[] multCoolDown;
	private int[] addScope;
	private boolean[] stopMob;
	
	public double[] expCurrent;
	private double[] expFirstLevel;
	private int[] skillPoints;
	
	public WeaponCharac() throws NumberFormatException, IOException {
		nbWeapons = 7;
		type = 1;
		keyShot = true;
		
		initialisation();

		getSkillTrees();

		loadIndSkillTrees();
		
		getSkillTreeMultipliers();
	}
	
	public int updateCharac(double posi, double posj, double diri, double dirj) {
		int direction = 0;
		if (Main.mouseLeft && (keyShot || type == 3 || type == 5) && times[type] <0.01) {
			keyShot = false;
			direction = (int) Math.signum(Main.mouseJ-Main.mainChar.position[1]);
			
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
		for (int n = 1 ; n < nbWeapons ; n++) {
			multDmg[n] = 1;
			addMun[n] = 0;
			multCoolDown[n] = 1;
			multDisp[n] = 1;
			addScope[n] = 0;
			stopMob[n] = true;
			
			if (skillTrees[n].indCC > 0) {
				for (int i = 0 ; i < skillTrees[n].indCC ; i++) {
					System.out.println("common");
					System.out.println(skillTrees[n].getSkillType(0, i));
					System.out.println(skillTrees[n].getSkillValue(0, i));
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
						System.out.println("First");
						System.out.println(skillTrees[n].getSkillType(1, i));
						System.out.println(skillTrees[n].getSkillValue(1, i));
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
						System.out.println("Second");
						System.out.println(skillTrees[n].getSkillType(2, i));
						System.out.println(skillTrees[n].getSkillValue(2, i));
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
						System.out.println("Third");
						System.out.println(skillTrees[n].getSkillType(3, i));
						System.out.println(skillTrees[n].getSkillValue(3, i));
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
		munitions = new int[nbWeapons];
		munitions[1] = 0;	// bomb
		munitions[2] = 0;	// sniper
		munitions[3] = 0;	// fire
		munitions[4] = 0;	// jack3 yeallow 3
		munitions[5] = 100;	// simple machingun 
		munitions[6] = 15;	// shotgun
		
		coolDown = new double[nbWeapons];
		coolDown[0] = 8; 	// simple shot
		coolDown[1] = 15; 	// bomb
		coolDown[2] = 10;	// sniper
		coolDown[3] = 4;	// fire
		coolDown[4] = 115;	// jack3 yeallow 3
		coolDown[5] = 4;	// simple machingun
		coolDown[6] = 15;	// shotgun
		
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
		
		scope = new int[nbWeapons];
		scope[0] = 8;
		scope[1] = 40;
		scope[2] = 2;
		scope[3] = 20;
		scope[4] = 100;
		scope[5] = 12;
		scope[6] = 5;

		times = new double[nbWeapons];
		
		skillTrees = new SkillTree[nbWeapons];		
		
		multDmg = new double[nbWeapons];
		multDisp = new double[nbWeapons];
		addMun = new int[nbWeapons];
		multCoolDown = new double[nbWeapons];
		addScope = new int[nbWeapons];
		stopMob = new boolean[nbWeapons];

		expCurrent = new double[nbWeapons];
		expFirstLevel = new double[nbWeapons];
		skillPoints = new int[nbWeapons];
		
		loadExp();
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
		System.out.println("multDmg : " + this.multDmg[type]);
		System.out.println("multDisp : " + this.multDisp[type]);
		System.out.println("addMun : " + this.addMun[type]);
		System.out.println("multcd : " + this.multCoolDown[type]);
		System.out.println("addscope : " + this.addScope[type]);
		System.out.println("stopMob : " + this.stopMob[type]);
	}
	
	public void loadExp() throws IOException {
		File fileCurrentExp = new File("files/Save/Weapons/CurrentExperience.txt");
		BufferedReader brCE = new BufferedReader(new FileReader(fileCurrentExp));
		for (int i = 1 ; i < nbWeapons ; i++) {
			expCurrent[i] = Double.parseDouble(brCE.readLine());
		}
		brCE.close();
		
		File fileExpFirstLevel = new File("files/Data/Weapons/SkillTree/ExperienceFirstLevel.txt");
		BufferedReader brEFL = new BufferedReader(new FileReader(fileExpFirstLevel));
		for (int i = 1 ; i < nbWeapons ; i++) {
			expFirstLevel[i] = Double.parseDouble(brEFL.readLine());
		}
		brEFL.close();
	}
	
	public boolean getKeyShot() {
		return this.keyShot;
	}
	
	public void setKeyShot(boolean ks) {
		this.keyShot = ks;
	}
}
