
public class Scenery extends Hitbox {

	public Scenery(int npts) {
		this.nbPoints = npts;
		this.points = new double[npts][2];
		this.position = new double[2];
		this.speed = new double[2];
	}
	
}
