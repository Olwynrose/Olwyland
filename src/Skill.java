
public class Skill {
	
	private int type;
	private double value;
	private int cost;

	public Skill(int t, double v, int c) {
		this.type = t;
		this.value = v;
		this.cost = c;
	}
	
	public int getType() {
		return this.type;
	}
	
	public void setType(int t) {
		this.type = t;
	}

	public double getValue() {
		return this.value;
	}
	
	public void setValue(double v) {
		this.value = v;
	}

	public int getCost() {
		return this.cost;
	}
	
	public void setCost(int c) {
		this.cost = c;
	}
}
