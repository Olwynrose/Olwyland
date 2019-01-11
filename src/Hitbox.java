
public class Hitbox {

	public double[][] points;
	public double[] position;
	public double[] speed;
	public int nbPoints;
	public int type; 		
	/* 0: not visible, 1: solid, 2: plateform */
	
	protected double s;
	protected double t;
	protected double tMin;
	
	
	
	
	
	double[] getPosition() {
		return position;
	}
	
	double[][] getPoints() {
		return points;
	}
	
	double getOnePoint(int i, int j) {
		return points[i][j] + position[j];
	}
	
	int getNbPoints() {
		return points.length;
	}
	
	public void setOnePoint(int ind, double i, double j) {
		points[ind][0] = i;
		points[ind][1] = j;
	}
	
	protected void intersect(Hitbox hitbox) {
		double t; 
		
		for (int i = 0 ; i < nbPoints ; i++) {
			if(hitbox.type == 1)
			{
				for (int j = 0 ; j < hitbox.getNbPoints() - 1 ; j++) {
					t = lineIntersection(speed[0], speed[1], 
							position[0] + points[i][0], position[1] + points[i][1],
							hitbox.position[0] + hitbox.points[j][0], hitbox.position[1] + hitbox.points[j][1],
							hitbox.position[0] + hitbox.points[j+1][0], hitbox.position[1] + hitbox.points[j+1][1]);
					if (t >= 0 && t <= tMin) {
						tMin = t;
					}
				}
			}
			else {
				if(hitbox.type == 2 && i == 0 && speed[0] > 0)
				{
					for (int j = 0 ; j < hitbox.getNbPoints() - 1 ; j++) {
						t = lineIntersection(speed[0], speed[1], 
								position[0] + points[i][0], position[1] + points[i][1],
								hitbox.position[0] + hitbox.points[j][0], hitbox.position[1] + hitbox.points[j][1],
								hitbox.position[0] + hitbox.points[j+1][0], hitbox.position[1] + hitbox.points[j+1][1]);
						if (t >= 0 && t <= tMin) {
							tMin = t;
						}
					}
				}
			}
		}
	}
	
	protected double lineIntersection(double iv, double jv, double ip, double jp, double ia, double ja, double ib, double jb) {
		/**
		 * iv, jv : speed
		 * ip, jp : position
		 * ia, ja, ib, jb : [AB] segment coordinates
		 */
		if ((iv *(jb - ja) + jv * (ia - ib)) != 0) {
			s = (iv * (ja - jp) + jv * (ip - ia)) / (- iv * (jb - ja) + jv * (ib - ia));
			t = ((ib - ia) * (jp - ja) - (jb - ja) * (ip - ia)) / (iv *(jb - ja) - jv * (ib - ia));
			if (s >= 0 && s <=1) {
				return t;
			}
			else {
				return -1;
			}
		}
		else {
			return -1;
		}
	}
}
