import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Scenery extends Hitbox {
	
	private double trajectory[][];
	private int nbTrajectory;
	public int time;			// frame number
	public double period;
	public int typeMove; 		
	/* 0: fixed, 1: RoundTrip, 2: loop, 3: disappear */


	public Scenery(int npts) {
		this.nbPoints = npts;
		this.points = new double[npts][2];
		this.position = new double[2];
		this.speed = new double[2];
		
		this.idim = 0;
		this.jdim = 0;
		this.transi = 0;
		this.transj = 0;
		
		
		period = 150;
		time = 0;
		type = 1;
		typeMove = 0;
	}
	
	public void update() throws InterruptedException{
		switch(typeMove){
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
		case 3:
		{
			double param = ((double)time/period)%1;
			if(param>0.5) {
				this.type = 0;
			}
			else {
				this.type = 2;
			}
			time = time + 1;
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
	
	public double getOneTrajectory(int ind, int k) {
		return trajectory[ind][k];
		
	}
	
	public void setImage(String fileImage) {
		// loading image
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(fileImage));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.img = convertTo2DUsingGetRGB(img);
		this.idim = this.img.length;
		this.jdim = this.img[0].length;
		img = null;
	}
	
	private static int[][][] convertTo2DUsingGetRGB(BufferedImage image) 
	{
		int width = image.getWidth();
		int height = image.getHeight();
		int[][][] result = new int[height][width][3];
		//Color coul;

		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				int clr=  image.getRGB(col,row); 
				result[row][col][0]   = (clr & 0x00ff0000) >> 16;
			result[row][col][1]  = (clr & 0x0000ff00) >> 8;
			result[row][col][2]   =  clr & 0x000000ff;
			}
		}
		return result;
	} // fin convert
}
