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
	private int[][][] img_map;
	private int[] arrayimage;

	public int idim;
	public int jdim;
	public int idim_map;
	public int jdim_map;
	private double margini, marginj;
	private double transi, transj;
	public int translationType;
	/* 0: no translation, 1: following, 2:centered */

	double inter3[][];		// Interpolation coefficients 3x3
	double inter2[][];		// Interpolation coefficients 2x2

	private int coefTransparency;

	public Display() {
		window = new JFrame();
		pan = new JPanel();
		lab = new JLabel();
		idim = 720;
		jdim = 1080;
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

		// bilinear interpolation coefficients

		inter3 = new double[4][4];
		inter2 = new double[3][3];

		inter3[0][0] = 1.0;
		inter3[0][1] = 2.0/3.0;
		inter3[0][2] = 1.0/3.0;
		inter3[0][3] = 0.0;
		inter3[1][0] = 2.0/3.0;
		inter3[1][1] = 4.0/9.0;
		inter3[1][2] = 2.0/9.0;
		inter3[1][3] = 0;
		inter3[2][0] = 1.0/3.0;
		inter3[2][1] = 2.0/9.0;
		inter3[2][2] = 1.0/9.0;
		inter3[2][3] = 0;
		inter3[3][0] = 0;
		inter3[3][1] = 0;
		inter3[3][2] = 0;
		inter3[3][3] = 0;

		inter2[0][0] = 1.0;
		inter2[0][1] = 0.5;
		inter2[0][2] = 0.0;
		inter2[1][0] = 0.5;
		inter2[1][1] = 0.25;
		inter2[1][2] = 0.0;
		inter2[2][0] = 0.0;
		inter2[2][1] = 0.0;
		inter2[2][2] = 0.0;

		// map loading
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(Main.backgroundFileImage));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		img_map = convertTo2DUsingGetRGB(img);

		img = null;

		idim_map = img_map.length;
		jdim_map = img_map[0].length;
		if(Main.interpol == 3)
		{
			idim_map = 3*idim_map-3;
			jdim_map = 3*jdim_map-3;
		}
		if(Main.interpol == 2)
		{
			idim_map = 2*idim_map-2;
			jdim_map = 2*jdim_map-2;
		}

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
		int i, j, ii, jj, di, dj;

		for (i=0; i<idim; i++)
		{
			for (j=0; j<jdim; j++)
			{
				if(Main.interpol == 3)
				{
					ii = (i-(int)transi)/3;
					jj = (j-(int)transj)/3;
					di = (i-(int)transi) % 3;
					dj = (j-(int)transj) % 3;
					img[i][j][0] = (int) ( ((double)img_map[ii][jj][0])*inter3[di][dj]+
							((double)img_map[ii+1][jj][0])*inter3[3-di][dj]+
							((double)img_map[ii][jj+1][0])*inter3[di][3-dj]+
							((double)img_map[ii+1][jj+1][0])*inter3[3-di][3-dj]);
					img[i][j][1] = (int) ( ((double)img_map[ii][jj][1])*inter3[di][dj]+
							((double)img_map[ii+1][jj][1])*inter3[3-di][dj]+
							((double)img_map[ii][jj+1][1])*inter3[di][3-dj]+
							((double)img_map[ii+1][jj+1][1])*inter3[3-di][3-dj]);
					img[i][j][2] = (int) ( ((double)img_map[ii][jj][2])*inter3[di][dj]+
							((double)img_map[ii+1][jj][2])*inter3[3-di][dj]+
							((double)img_map[ii][jj+1][2])*inter3[di][3-dj]+
							((double)img_map[ii+1][jj+1][2])*inter3[3-di][3-dj]);

				}
				else
				{
					if (Main.interpol == 2)
					{
						ii = (i+(int)transi)/2;
						jj = (j+(int)transj)/2;
						di = (i+(int)transi) % 2;
						dj = (j+(int)transj) % 2;

						if(ii>0 && ii<idim_map/2-2 && jj>0 && jj<jdim_map/2-2)
						{
							img[i][j][0] = (int) ( ((double)img_map[ii][jj][0])*inter2[di][dj]+
									((double)img_map[ii+1][jj][0])*inter2[2-di][dj]+
									((double)img_map[ii][jj+1][0])*inter2[di][2-dj]+
									((double)img_map[ii+1][jj+1][0])*inter2[2-di][2-dj]);
							img[i][j][1] = (int) ( ((double)img_map[ii][jj][1])*inter2[di][dj]+
									((double)img_map[ii+1][jj][1])*inter2[2-di][dj]+
									((double)img_map[ii][jj+1][1])*inter2[di][2-dj]+
									((double)img_map[ii+1][jj+1][1])*inter2[2-di][2-dj]);
							img[i][j][2] = (int) ( ((double)img_map[ii][jj][2])*inter2[di][dj]+
									((double)img_map[ii+1][jj][2])*inter2[2-di][dj]+
									((double)img_map[ii][jj+1][2])*inter2[di][2-dj]+
									((double)img_map[ii+1][jj+1][2])*inter2[2-di][2-dj]);
						}
						else {
							img[i][j][0] = 0;
							img[i][j][1] = 0;
							img[i][j][2] = 0;
						}
					}
					else {
						ii = (i+(int)transi);
						jj = (j+(int)transj);
						if(ii>0 && ii<idim_map && jj>0 && jj<jdim_map)
						{
							img[i][j][0] = img_map[ii][jj][0];
							img[i][j][1] = img_map[ii][jj][1];
							img[i][j][2] = img_map[ii][jj][2];
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

		// display moving hitbox

		for(int ind=0; ind<Main.nbSceneries; ind++) {
			if(Main.sceneries[ind].idim > 0) {
				for(i=0; i<Main.sceneries[ind].idim; i++) {
					for(j=0; j<Main.sceneries[ind].jdim; j++) {
						ii = i + (int) Math.floor(Main.sceneries[ind].position[0] - Main.sceneries[ind].transi - transi);
						jj = j + (int) Math.floor(Main.sceneries[ind].position[1] - Main.sceneries[ind].transj - transj);
						if(ii>0 && ii<idim && jj>0 && jj<jdim &&
								!(Main.sceneries[ind].img[i][j][0] > 251 &&
								Main.sceneries[ind].img[i][j][1] < 4  &&
								Main.sceneries[ind].img[i][j][2] > 251 )) {

							img[ii][jj][0] = Main.sceneries[ind].img[i][j][0];
							img[ii][jj][1] = Main.sceneries[ind].img[i][j][1];
							img[ii][jj][2] = Main.sceneries[ind].img[i][j][2];
						}
					}
				}
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
			if(Main.sceneries[i].type == 1)
			{
				for(int j = 0 ; j < Main.sceneries[i].getNbPoints() - 1 ; j++)
				segment(Main.sceneries[i].getOnePoint(j, 0)-transi, Main.sceneries[i].getOnePoint(j, 1)-transj,
						Main.sceneries[i].getOnePoint(j+1, 0)-transi, Main.sceneries[i].getOnePoint(j+1, 1)-transj,
						255, 255, 255);
			}
			else {
				if(Main.sceneries[i].type == 2)
				{
					for(int j = 0 ; j < Main.sceneries[i].getNbPoints() - 1 ; j++)
						segment(Main.sceneries[i].getOnePoint(j, 0)-transi, Main.sceneries[i].getOnePoint(j, 1)-transj,
								Main.sceneries[i].getOnePoint(j+1, 0)-transi, Main.sceneries[i].getOnePoint(j+1, 1)-transj,
								255, 255, 0);
				}
			}
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
		case 4:
		{
			for (int i = 0 ; i < idim ; i++)
			{
				for (int j = 0 ; j < jdim ; j++)
				{
					if (Main.mainChar.time < Main.mainChar.times[0] + 1) {
						img[i][j][0] = Math.min(255, img[i][j][0] + (255 * Main.mainChar.time) / Main.mainChar.times[0]);
						img[i][j][1] = Math.min(255, img[i][j][1] + (255 * Main.mainChar.time) / Main.mainChar.times[0]);
						img[i][j][2] = Math.min(255, img[i][j][2] + (255 * Main.mainChar.time) / Main.mainChar.times[0]);
					}
					else {
						if (Main.mainChar.time > Main.mainChar.times[0] + Main.mainChar.times[1]) {
							img[i][j][0] = Math.min(255, img[i][j][0] + (255 * (Main.mainChar.times[0] + Main.mainChar.times[1] + Main.mainChar.times[2] - Main.mainChar.time)) / Main.mainChar.times[2]);
							img[i][j][1] = Math.min(255, img[i][j][1] + (255 * (Main.mainChar.times[0] + Main.mainChar.times[1] + Main.mainChar.times[2] - Main.mainChar.time)) / Main.mainChar.times[2]);
							img[i][j][2] = Math.min(255, img[i][j][2] + (255 * (Main.mainChar.times[0] + Main.mainChar.times[1] + Main.mainChar.times[2] - Main.mainChar.time)) / Main.mainChar.times[2]);
						}
						else {
							img[i][j][0] = 255;
							img[i][j][1] = 255;
							img[i][j][2] = 255;
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
			if (Main.areas[n].getForm() == 0) {
				for(int i = (int)Math.max(0, Main.areas[n].getPositionI() - transi) ; i < (int)Math.min(idim, Main.areas[n].getPositionI() + Main.areas[n].getHeight() - transi); i++) {
					for(int j = (int)Math.max(0, Main.areas[n].getPositionJ() - transj) ; j < (int)Math.min(jdim, Main.areas[n].getPositionJ() + Main.areas[n].getWidth() - transj); j++) {
						colorPixel(i, j, Main.areas[n].getType());
					}
				}
			}
			else {
				//ellipse
				for (int i = 0 ; i < idim ; i++) {
					for (int j = 0 ; j < jdim ; j++) {
						if (Main.areas[n].isIn((double)i + transi, (double)j + transj)) {
							colorPixel(i, j, Main.areas[n].getType());
						}
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
  	  		Main.mainChar.keyJump = true;
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

	private void colorPixel(int i, int j, int ind) {
		switch (ind)
		{
		case 1:
		{
			img[i][j][0] = (img[i][j][0] * coefTransparency) / 100 + (0 * (100 - coefTransparency)) / 100;
			img[i][j][1] = (img[i][j][1] * coefTransparency) / 100 + (0 * (100 - coefTransparency)) / 100;
			img[i][j][2] = (img[i][j][2] * coefTransparency) / 100 + (255 * (100 - coefTransparency)) / 100;
		}
		break;
		case 2:
		{
			img[i][j][0] = (img[i][j][0] * coefTransparency) / 100 + (255 * (100 - coefTransparency)) / 100;
			img[i][j][1] = (img[i][j][1] * coefTransparency) / 100 + (127 * (100 - coefTransparency)) / 100;
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
		case 4:
		{
			img[i][j][0] = (img[i][j][0] * coefTransparency) / 100 + (255 * (100 - coefTransparency)) / 100;
			img[i][j][1] = (img[i][j][1] * coefTransparency) / 100 + (255 * (100 - coefTransparency)) / 100;
			img[i][j][2] = (img[i][j][2] * coefTransparency) / 100 + (255 * (100 - coefTransparency)) / 100;
		}
		break;
		case 5:
		{
			img[i][j][0] = (img[i][j][0] * coefTransparency) / 100 + (0 * (100 - coefTransparency)) / 100;
			img[i][j][1] = (img[i][j][1] * coefTransparency) / 100 + (255 * (100 - coefTransparency)) / 100;
			img[i][j][2] = (img[i][j][2] * coefTransparency) / 100 + (255 * (100 - coefTransparency)) / 100;
		}
		break;
		case 6:
		{
			img[i][j][0] = (img[i][j][0] * coefTransparency) / 100 + (255 * (100 - coefTransparency)) / 100;
			img[i][j][1] = (img[i][j][1] * coefTransparency) / 100 + (0 * (100 - coefTransparency)) / 100;
			img[i][j][2] = (img[i][j][2] * coefTransparency) / 100 + (255 * (100 - coefTransparency)) / 100;
		}
		break;
		case 7:
		{
			img[i][j][0] = (img[i][j][0] * coefTransparency) / 100 + (0 * (100 - coefTransparency)) / 100;
			img[i][j][1] = (img[i][j][1] * coefTransparency) / 100 + (255 * (100 - coefTransparency)) / 100;
			img[i][j][2] = (img[i][j][2] * coefTransparency) / 100 + (0 * (100 - coefTransparency)) / 100;
		}
		break;
		case 8:
		{
			img[i][j][0] = (img[i][j][0] * coefTransparency) / 100 + (255 * (100 - coefTransparency)) / 100;
			img[i][j][1] = (img[i][j][1] * coefTransparency) / 100 + (0 * (100 - coefTransparency)) / 100;
			img[i][j][2] = (img[i][j][2] * coefTransparency) / 100 + (0 * (100 - coefTransparency)) / 100;
		}
		break;
		}
	}
}
