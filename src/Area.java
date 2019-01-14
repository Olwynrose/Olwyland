
public class Area {

	public int type; 
	/* 1: water, 2: lava, 3: void, 4: scale, 5: teleporter, 6: auto-teleporter, 7: trampoline, 8: switch HB */
	private double[] position;
	private double width;
	private double height;
	private int indTp;
	private double speedMultTp;
	private double speedMultJump;
	private int indHB;
	
	
	public Area(int t, double posi, double posj, double w, double h) {
		this.type = t;
		this.position = new double[2];
		this.position[0] = posi;
		this.position[1] = posj;
		this.width = w;
		this.height = h;
		this.indTp = 0;
		this.speedMultTp = 0;
		this.speedMultJump = 3;
		this.indHB = 0;
	}
	
	public boolean isIn(double i, double j) {
		if (i > this.position[0] 
				&& i < this.position[0] + this.height 
				&& j > this.position[1] 
				&& j < this.position[1] + this.width) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public double getPositionI() {
		return this.position[0];
	}
	
	public double getPositionJ() {
		return this.position[1];
	}
	
	public double getWidth() {
		return this.width;
	}
	
	public double getHeight() {
		return this.height;
	}
	
	public int getIndTp() {
		return this.indTp;
	}
	
	public void setIndTp(int ind) {
		this.indTp = ind;
	}
	
	public double getSpeedMultTp() {
		return this.speedMultTp;
	}
	
	public void setSpeedMultTp(double mult) {
		this.speedMultTp = mult;
	}
	
	public double getSpeedMultJump() {
		return this.speedMultJump;
	}
	
	public int getIndHB() {
		return this.indHB;
	}
	
	public void setIndHB(int ind) {
		this.indHB = ind;
	}
}
