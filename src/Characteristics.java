
public class Characteristics {
	public double defence;
	public double maxHp;
	public double hp;
	public double damages;
	public int time;
	public int hitTime;
	
	public Characteristics() {
		defence = 10;
		maxHp = 25;
		hp = maxHp;
		damages = 100;
		time = 0;
		hitTime = 6;
	}
	
	public void update() {
		if(time > 0) {
			time = time - 1;
		}
		else {
			time = 0;
		}
	}
	
	public void hit(double damageHit) {
		if(time == 0) {
			hp = hp - damageHit / defence;
			time = hitTime;
		}
		
	}
}
