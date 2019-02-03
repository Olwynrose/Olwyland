
public class Characteristics {
	public double defense;
	public double maxHp;
	public double hp;
	public double damages;
	public int time;
	public int hitTime;
	public int indSound; 
	/* 1:carac, 2: ennemy1, 3: ennemy2 */
	public int oxygen;
	public int maxOxygen;
	
	
	public Characteristics() {
		indSound = 0;
		defense = 10;
		maxHp = 25;
		hp = maxHp;
		damages = 100;
		time = 0;
		hitTime = 6;
		maxOxygen = 0;
	}
	
	public void update() {
		if(time > 0) {
			time = time - 1;
		}
		else {
			time = 0;
		}
	}
	
	public void addOxygen() {
		oxygen = Math.min(oxygen+maxOxygen/10, maxOxygen);
	}
	public void consumeOxygen() {
		oxygen = oxygen - 1;
		if(oxygen <= 0 ) { 
			hp = 0;
		}
	}
	
	public double hit(double damageHit) {
		double effectiveDmg = 0;
		if(time == 0 && hp > 0) {
			effectiveDmg = Math.min(damageHit, hp * defense);
			hp = hp - effectiveDmg / defense - 0.001;
			time = hitTime;
			switch(indSound) {
			case 1:
			{
				
			}
			break;
			case 2:
			{
				if(hp>0) {
					Main.sounds.play(14);
				}
				else {
					Main.sounds.play(15);
				}
			}
			break;
			case 3:
			{
				if(hp>0) {
					Main.sounds.play(11);
				}
				else {
					Main.sounds.play(12);
				}
			}
			break;
			}
		}
		return weaponExp(effectiveDmg);
	}
	
	public double weaponExp(double effecDmg) {
		return (effecDmg * defense)/1000;
	}
}
