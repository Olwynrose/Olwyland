import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.io.IOException;
import java.awt.Color;

public class Display {
	
	public JFrame window;
	private JPanel pan;
	private JLabel lab;
	private ImageIcon ii;
	private int[][][] img;
	private int[] arrayimage;
	
	public int idim;
	public int jdim;
	private double margini, marginj;
	private double transi, transj;
	public int translationType;
	/* 0: no translation, 1: following, 2:centered */

	private int coefTransparency;
	
	public Display() {
		window = new JFrame();
		pan = new JPanel();
		lab = new JLabel();
		idim = 620;
		jdim = 920;
		margini = 0.3*(double)idim;
		marginj = 0.3*(double)jdim;
		transi = 0;
		transj = 0;
		translationType = 1;
		img = new int[idim][jdim][3];
		arrayimage = new int[idim*jdim*3];
		
		coefTransparency = 80;
		
		window.setTitle("Olwyland");
		window.setSize(jdim, idim);
		window.setResizable(false);
		window.setLocationRelativeTo(null);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);

		pan.setSize(jdim, idim);
		pan.setLocation(0, 0);
		pan.setBackground(Color.WHITE);
		pan.setLayout(null);
		pan.setOpaque(true);
		pan.add(lab);

		pan.repaint(); 
		pan.validate();

		window.setContentPane(pan);
	}
	
	public void global() {
		
		
		translation();
		background();	
		if (Main.debug[9])
		{
			areas();
		}
		if (Main.debug[10])
		{
			hitbox();
		}
		animations();
		
		int i, j, k;
		for(i = 0; i < idim ; i++)
		{
			for(j = 0; j <  jdim ; j++) 
			{
				for(k = 0; k < 3; k++)
				{
					arrayimage[(i*jdim+j)*3+k] = img[i][j][k];
				}

			}
		}

		ii = new ImageIcon(getImageFromArray(arrayimage, jdim, idim));
		pan.remove(lab);
		lab.setIcon(ii);
		lab.setBounds(0, 0, jdim, idim);
		pan.add(lab);
		pan.repaint();
		pan.revalidate();
	}
	
	public void background() {
		for(int i = 0 ; i < idim ; i++)
		{
			for (int j = 0 ; j < jdim ; j++)
			{
				img[i][j][0] = 0;
				img[i][j][1] = 0;
				img[i][j][2] = 0;
			}
		}
	}

	public void hitbox() {
		for (int i = 0 ; i < Main.mainChar.getNbPoints() - 1 ; i++) {
			segment(Main.mainChar.getOnePoint(i, 0)-transi, Main.mainChar.getOnePoint(i, 1)-transj, 
					Main.mainChar.getOnePoint(i+1, 0)-transi, Main.mainChar.getOnePoint(i+1, 1)-transj,
					255, 55, 255);
		}
		
		for (int i = 0 ; i < Main.nbSceneries ; i++) {
			for(int j = 0 ; j < Main.sceneries[i].getNbPoints() - 1 ; j++)
			segment(Main.sceneries[i].getOnePoint(j, 0)-transi, Main.sceneries[i].getOnePoint(j, 1)-transj, 
					Main.sceneries[i].getOnePoint(j+1, 0)-transi, Main.sceneries[i].getOnePoint(j+1, 1)-transj,
					255, 255, 255);
		}
	}
	
	public void characters() {
		
	}
	
	public void animations() {
		switch (Main.mainChar.animation)
		{
		case 1:
		{
			for (int i = 0 ; i < idim ; i++)
			{
				for (int j = 0 ; j < jdim ; j++)
				{
					if (Main.mainChar.time < Main.mainChar.times[0] + 1) {
						img[i][j][0] = (img[i][j][0]*(Main.mainChar.times[0]-Main.mainChar.time)) / Main.mainChar.times[0];
						img[i][j][1] = (img[i][j][1]*(Main.mainChar.times[0]-Main.mainChar.time)) / Main.mainChar.times[0];
						img[i][j][2] = (img[i][j][2]*(Main.mainChar.times[0]-Main.mainChar.time)) / Main.mainChar.times[0];
					}
					else {
						if (Main.mainChar.time > Main.mainChar.times[0] + Main.mainChar.times[1]) {
							img[i][j][0] = (img[i][j][0]*(Main.mainChar.time - Main.mainChar.times[0] - Main.mainChar.times[1])) / Main.mainChar.times[2];
							img[i][j][1] = (img[i][j][1]*(Main.mainChar.time - Main.mainChar.times[0] - Main.mainChar.times[1])) / Main.mainChar.times[2];
							img[i][j][2] = (img[i][j][2]*(Main.mainChar.time - Main.mainChar.times[0] - Main.mainChar.times[1])) / Main.mainChar.times[2];
						}
						else {
							img[i][j][0] = 0;
							img[i][j][1] = 0;
							img[i][j][2] = 0;
						}
					}
				}
			}
			
		}
		break;
		}
	}
	
	public void areas() {
		for (int n = 0 ; n < Main.nbAreas ; n++) {
			for(int i = (int)Math.max(0, Main.areas[n].position[0] - transi) ; i < (int)Math.min(idim, Main.areas[n].position[0] + Main.areas[n].height - transi); i++) {
				for(int j = (int)Math.max(0, Main.areas[n].position[1] - transj) ; j < (int)Math.min(jdim, Main.areas[n].position[1] + Main.areas[n].width - transj); j++) {
					switch (Main.areas[n].type)
					{
					case 1:
					{
						img[i][j][0] = (img[i][j][0] * coefTransparency) / 100 + (255 * (100 - coefTransparency)) / 100;
						img[i][j][1] = (img[i][j][1] * coefTransparency) / 100 + (0 * (100 - coefTransparency)) / 100;
						img[i][j][2] = (img[i][j][2] * coefTransparency) / 100 + (255 * (100 - coefTransparency)) / 100;
					}
					break;
					case 2:
					{
						img[i][j][0] = (img[i][j][0] * coefTransparency) / 100 + (255 * (100 - coefTransparency)) / 100;
						img[i][j][1] = (img[i][j][1] * coefTransparency) / 100 + (0 * (100 - coefTransparency)) / 100;
						img[i][j][2] = (img[i][j][2] * coefTransparency) / 100 + (0 * (100 - coefTransparency)) / 100;
					}
					break;
					case 3:
					{
						img[i][j][0] = (img[i][j][0] * coefTransparency) / 100 + (255 * (100 - coefTransparency)) / 100;
						img[i][j][1] = (img[i][j][1] * coefTransparency) / 100 + (255 * (100 - coefTransparency)) / 100;
						img[i][j][2] = (img[i][j][2] * coefTransparency) / 100 + (0 * (100 - coefTransparency)) / 100;
					}
					break;
					}
				}
			}
		}
	}
	
	private void segment(double i1, double j1, double i2, double j2, int red, int green, int blue) {		
		double ia, ja, ib, jb;
		double i, j;
		
		if (i1 != i2 || j1 != j2) {
			if (Math.abs(i1 - i2) < Math.abs(j1 - j2)) {
				if (j1 < j2) {
					ia = i1;
					ja = j1;
					ib = i2;
					jb = j2;
				}
				else {
					ia = i2;
					ja = j2;
					ib = i1;
					jb = j1;
				}
				
				j = Math.floor(ja);
				
				do {
					i = Math.floor(ia + (j-ja) * (ib - ia) / (jb - ja));
					pixel(i, j, red, green, blue);
					j++;
				} while(j < jb);
			}
			else {
				if (i1 < i2) {
					ia = i1;
					ja = j1;
					ib = i2;
					jb = j2;
				}
				else {
					ia = i2;
					ja = j2;
					ib = i1;
					jb = j1;
				}
				
				i = Math.floor(ia);
				
				do {
					j = Math.floor(ja + (i-ia) * (jb - ja) / (ib - ia));
					pixel(i, j, red, green, blue);
					i++;
				} while(i < ib);
			}
		}
		else {
			pixel(i1, j1, 255, 105, 255);
		}
	}
	
	private void pixel(double i, double j, int red, int green, int blue) {
		int ii = (int) i;
		int jj = (int) j;
		
		if (ii > 0 && ii < idim && jj > 0 && jj < jdim) {
			img[ii][jj][0] = red;
			img[ii][jj][1] = green;
			img[ii][jj][2] = blue;
		}
	}
	
	public static Image getImageFromArray(int[] pixels, int width, int height)
	{
		BufferedImage image =
				new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		WritableRaster raster = (WritableRaster) image.getData();
		raster.setPixels(0, 0, width, height, pixels);
		image.setData(raster);
		return image;
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

	public int[][][] imageToMatrix(String name)
	{
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(name));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int[][][] im_coul = convertTo2DUsingGetRGB(img);
		return im_coul;
	}
	
	public void updatePressedKeys(int key) {
		if(key == 37)
		{
			Main.keyLeft = true;
  	  	}
  	  	if(key == 39)
  	  	{
  	  		Main.keyRight = true;
  	  	}
  	  	if(key == 38)
  	  	{
  	  		Main.keyUp = true;
  	  	}
  	  	if(key == 40)
  	  	{
  	  		Main.keyDown = true;
  	  	}
  	  	if(key == 32) 
	  	{
	  		Main.keySpace = true;
	  	}
	}
	
	public void updateReleasedKeys(int key) {
		if(key == 37)
		{
			Main.keyLeft = false;
  	  	}
  	  	if(key == 39)
  	  	{
  	  		Main.keyRight = false;
  	  	}
  	  	if(key == 38)
  	  	{
  	  		Main.keyUp = false;
  	  	}
  	  	if(key == 40)
  	  	{
  	  		Main.keyDown = false;
  	  	}
  	  	if(key == 32) 
  	  	{
  	  		Main.keySpace = false;
  	  	}
	}
	
	private void translation() {
		switch (translationType)
		{
		case 1:
		{
			if (Main.mainChar.position[0] < transi + margini) {
				transi = Main.mainChar.position[0] - margini;
			}
			if (Main.mainChar.position[0] > transi + (double)idim - margini) {
				transi = Main.mainChar.position[0] + margini - (double)idim;
			}
			if (Main.mainChar.position[1] < transj + marginj) {
				transj = Main.mainChar.position[1] - marginj;
			}
			if (Main.mainChar.position[1] > transj + (double)jdim - marginj) {
				transj = Main.mainChar.position[1] + marginj - (double)jdim;	
			}
		}
		break;
		case 2:
		{
			centerChar();
		}
		break;
		}
	}
	
	public void centerChar() {
		transi = Main.mainChar.position[0] - (double)idim / 2;
		transj = Main.mainChar.position[1] - (double)jdim / 2;
	}
}
