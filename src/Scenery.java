
public class Scenery extends Hitbox {
	
	private double trajectory[][];
	private int nbTrajectory;
	public int time;			// frame number
	public double period;
	public short type; 		
	/* 0: fixed, 1: opentrajectory, 2: loop */


	public Scenery(int npts) {
		this.nbPoints = npts;
		this.points = new double[npts][2];
		this.position = new double[2];
		this.speed = new double[2];
		
		period = 150;
		time = 0;
		type = 0;
	}
	
	public void update() throws InterruptedException{
		switch(type){
		case 0:
		{
			
		}
		break;
		case 1:
		{
			posTrajectory(0.5-0.5*Math.cos(2*Math.PI*(((double)time/period)%1)));
			
			moveCharac();
			this.position[0] = this.position[0] + this.speed[0];
			this.position[1] = this.position[1] + this.speed[1];
			time = time + 1;
		}
		break;
		case 2:
		{
			double param = ((double)time/period)%1;
			posTrajectory(param);
			
			moveCharac();
			this.position[0] = this.position[0] + this.speed[0];
			this.position[1] = this.position[1] + this.speed[1];
			time = time + 1;
			if(((double)time/period)%1 < param){
				this.position[0] = trajectory[0][0];
				this.position[1] = trajectory[0][1];
			}
		}
		break;
		}
	}
	
	private void moveCharac() {
		boolean move_charac = false;
		
		// verify if a character is on the hitbox
		for(int i=0; i<this.nbPoints-1; i++) {
			t = lineIntersection(1, 0, 
					Main.mainChar.getOnePoint(0, 0) - 0.5, Main.mainChar.getOnePoint(0, 1),
					position[0] + points[i][0], position[1] + points[i][1],
					position[0] + points[i+1][0], position[1] + points[i+1][1]);

			if (t >= 0 && t < 1) {
				move_charac = true;
			}
		}
		if(move_charac){
			Main.mainChar.forcedMove(this.speed[0], this.speed[1]);
		}
		else {
			
			// verify if the hitbox intersect the character
			tMin = 1;
			intersect(Main.mainChar);
			
			for(int i=0; i<this.nbPoints-1; i++) {
				for(int j=0; j<Main.mainChar.getNbPoints(); j++) {
					t = lineIntersection(-this.speed[0], -this.speed[1], 
							Main.mainChar.getOnePoint(0, 0), Main.mainChar.getOnePoint(0, 1),
							position[0] + points[i][0], position[1] + points[i][1],
							position[0] + points[i+1][0], position[1] + points[i+1][1]);
				}		
				if (t >= 0 && t < tMin) {
					tMin = t;
				}
			}
			if(tMin>0 && tMin<1) {
				Main.mainChar.forcedMove(speed[0]*(1-tMin), speed[1]*(1-tMin));
			}
		}
	}
	
	
	private void posTrajectory(double t) {
		double sum = 0;
		int buf_i = 0;
		double buf_sum = 0;
		double buf_sumMin = 0;
		double buf_sumMax = 0;
		
		double len = 0;
		
		for(int i=0; i<nbTrajectory-1; i++) {
			len = len + Math.sqrt((trajectory[i][0]-trajectory[i+1][0])*(trajectory[i][0]-trajectory[i+1][0]) + 
					(trajectory[i][1]-trajectory[i+1][1])*(trajectory[i][1]-trajectory[i+1][1]));
		}
		
		len = len*t;
		
		for(int i=0; i<nbTrajectory-1; i++) {
			sum = sum + Math.sqrt((trajectory[i][0]-trajectory[i+1][0])*(trajectory[i][0]-trajectory[i+1][0]) + 
					(trajectory[i][1]-trajectory[i+1][1])*(trajectory[i][1]-trajectory[i+1][1]));

			buf_i = i;
			buf_sumMin = buf_sum;
			buf_sumMax = sum;
			if(sum > len) {
				break;
			}
			buf_sum = sum;
		}

		this.speed[0] = trajectory[buf_i][0] + (len-buf_sumMin)/(buf_sumMax-buf_sumMin)*(trajectory[buf_i+1][0]-trajectory[buf_i][0]) - this.position[0];
		this.speed[1] = trajectory[buf_i][1] + (len-buf_sumMin)/(buf_sumMax-buf_sumMin)*(trajectory[buf_i+1][1]-trajectory[buf_i][1]) - this.position[1];
	}
	
	public void newTrajectory(int num) {
		this.nbTrajectory = num;
		this.trajectory = new double[num][2];
	}
	
	public void setOneTrajectory(int ind, double i, double j) {
		trajectory[ind][0] = i;
		trajectory[ind][1] = j;
		if(ind == 0) {
			this.position[0] = i;
			this.position[1] = j;
		}
	}
	
}
