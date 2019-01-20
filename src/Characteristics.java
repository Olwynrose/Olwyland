
public class Characteristics {
	public double defence;
	public double maxHp;
	public double hp;
	public double damages;
	
	public Characteristics() {
		defence = 10;
		maxHp = 25;
		hp = maxHp;
		damages = 100;
	}
	
	public void hit(double damageHit) {
		hp = hp - damageHit / defence;
	}
}
