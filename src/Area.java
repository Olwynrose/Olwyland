
public class Area {

	public int type; 
	/* 1: water, 2: lava, 3: void, 4: scale, 5: teleporter, 6: auto-teleporter, 7: trampoline, 8: switch HB */
	public double[] position;
	public double width;
	public double height;
	public int indTp;
	public double speedMultTp;
	public double speedMultJump;
	public int indHB;
	
	public Area(int t, double posi, double posj, double w, double h) {
		this.type = t;
		this.position = new double[2];
		this.position[0] = posi;
		this.position[1] = posj;
		this.width = w;
		this.height = h;
		this.indTp = 0;
		this.speedMultTp = 0;
		this.speedMultJump = 4;
		this.indHB = 0;
	}
}
