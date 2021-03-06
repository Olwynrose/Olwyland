
public class Area {

	private int type;
	/* 1: water, 2: lava, 3: void, 4: scale, 5: teleporter, 6: auto-teleporter, 7: trampoline, 8: switch, 9: Check point */
	public boolean active;
	private double[] position;
	private double width;
	private double height;
	private int indTp;
	private double speedMultTp;
	private double speedMultJump;
	private int[] indHB;
	private int[] indArea;
	private int[] indSpawn;
	private int nbHB;
	private int nbAreas;
	private int nbSpawns;
	private int form;				// form of the area
	/* 0: rectangle, 1: ellipse */
	private double a, b, c; 		// ellipse coefficients

	public Area(int t, double posi, double posj, double w, double h) {
		this.type = t;
		this.active = true;
		this.position = new double[2];
		this.position[0] = posi;
		this.position[1] = posj;
		this.width = w;
		this.height = h;
		this.indTp = 0;
		this.speedMultTp = 0;
		this.speedMultJump = 3;
		this.indHB = new int[10];
		this.indArea = new int[10];
		this.indSpawn = new int[10];
		nbHB = 0;
		nbAreas = 0;
		nbSpawns = 0;
		this.form = 0;
	}

	public boolean isIn(double i, double j) {
		if(active) {
			if (this.form == 0) {
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
			else {
				if (a*Math.pow(i - this.position[0], 2)
						+ 2*b*(i - this.position[0])*(j - this.position[1])
						+ c*Math.pow(j - this.position[1], 2) < 1) {
					return true;
				}
				else {
					return false;
				}
			}
		}
		else {
			return false;
		}
	
	}

	public int getType() {
		return this.type;
	}

	public void setType(int type) {
		this.type = type;
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
	
	public void setSpeedMultJump(double mult) {
		this.speedMultJump = mult;
	}

	public int[] getIndHB() {
		return this.indHB;
	}

	public void setIndHB(int ind) {
		this.indHB[nbHB] = ind;
		nbHB = nbHB + 1;
	}
	
	public int[] getIndArea() {
		return this.indArea;
	}

	public void setIndArea(int ind) {
		this.indArea[nbAreas] = ind;
		nbAreas = nbAreas + 1;
	}

	public int[] getIndSpawn() {
		return this.indSpawn;
	}

	public void setIndSpawn(int ind) {
		this.indSpawn[nbSpawns] = ind;
		nbSpawns = nbSpawns + 1;
	}

	public int getForm() {
		return this.form;
	}

	public void setForm(int f) {
		this.form = f;
	}
	
	public void actSwitch() {

		for(int i = 0; i < nbHB; i++ ) {
			Main.sceneries[indHB[i]].type = (Main.sceneries[indHB[i]].type + 1) %2;
		}
		for(int i = 0; i < nbAreas; i++ ) {
			Main.areas[indArea[i]].active = !Main.areas[indArea[i]].active;
		}
		for(int i = 0; i < nbSpawns; i++ ) {
			Main.spawns[indSpawn[i]].reset();
		}
			
	}

	public void setEllipse(double r1, double r2, double theta) {
		a = Math.pow(Math.cos(theta), 2)/Math.pow(r1, 2) + Math.pow(Math.sin(theta), 2)/Math.pow(r2, 2);
		b = -Math.sin(theta)*Math.cos(theta)/Math.pow(r1, 2) + Math.sin(theta)*Math.cos(theta)/Math.pow(r2, 2);
		c = Math.pow(Math.cos(theta), 2)/Math.pow(r2, 2) + Math.pow(Math.sin(theta), 2)/Math.pow(r1, 2);
		this.form = 1;
	}
}
