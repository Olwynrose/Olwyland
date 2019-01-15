import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageToHitbox {

	int[][][] img;
	int[][] coord; // liste des coordonnées des pixels contenus dans la composante / dans le contour de la composante / ...
	int idim, jdim;
	
	int nb_couleur;
	int nbColor;
	int seuil_couleur;
	int[] redChan, greenChan, blueChan;

	int err_max_hb; // erreur en pixels au carré
	
	double cx, cy;
	double d_hauteur, d_largeur;
	int forme_zone; 
	
	

	
	public ImageToHitbox() {
		nbColor = 16;
		redChan = new int[nbColor];
		greenChan = new int[nbColor];
		blueChan = new int[nbColor];
		
		for (int n = 0 ; n < nbColor ; n++) {
			redChan[n] = (255*n)/nbColor;
			greenChan[n] = (255*n)/nbColor;
			blueChan[n] = (255*n)/nbColor;
		}
	}
	
	public void getArea(String fileName) {
		int i, j;
		int color;
		int ind;
		
		int i0, j0, i1, j1; 
		double ci, cj;
		double rSup; 
		
		int errRect = 0;
		int errEll = 0;
		
		double theta;
		double thetaEllipse = 0;
		double deltaTheta = 0.03;
		double r1, r2;
		double r;
		
		Area area;
		
		// opening of the file
		BufferedImage bufferedImg = null;
		try {
			bufferedImg = ImageIO.read(new File(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		img = convertTo2DUsingGetRGB(bufferedImg);
		bufferedImg = null;
		
		idim = img.length;
		jdim = img[0].length;
		
		coord = new int[idim * jdim][2];

		for (i = 0; i < idim; i++) {
			for (j = 0; j < jdim; j++) {
				color = colorMatch(i, j, 0);

				if (color != nbColor - 1) {
					
					ind = connectedComponent(i, j, color);
					if (ind > 10) {
						if (Main.debug[12]) {
							System.out.println();
							System.out.println("Detection of an area");
						}
						i0 = idim;
						j0 = jdim;
						i1 = 0;
						j1 = 0;
						// determination of the rectangle surrounding the area
						for (int k = 0 ; k < ind ; k++) {
							i0 = Math.min(i0, coord[k][0]);
							j0 = Math.min(j0, coord[k][1]);
							i1 = Math.max(i1, coord[k][0]);
							j1 = Math.max(j1, coord[k][1]);
						}
						// calculation of the error if the area is a rectangle
						errRect = (i1 - i0 + 1)*(j1 - j0 + 1) - ind;
						if(Main.debug[13]) {
							System.out.println("idim - jdim rect : " + (i1 -i0)+ " - " +(j1 - j0));
							System.out.println("nb pixel in rect - in connected comp : "+ ((i1 - i0)*(j1 - j0)) + " - " + ind);
						}
						
						//determination of the parameters of the possible ellipse defining the area
						ci = (i0 + i1) / 2;
						cj = (j0 + j1) / 2;
						rSup = Math.sqrt(Math.pow(ci - (double)i0, 2) + Math.pow(cj - (double)j0, 2));
						r1 = 0;
						r2 = rSup;
						theta = 0;
						while (theta < Math.PI) {
							r = getEllipseRadius(ci, cj, ci - rSup * Math.cos(theta), cj + rSup * Math.sin(theta));
							if (r > r1) {
								thetaEllipse = theta;
								r1 = r;
							}
							r2 = Math.min(r, r2);
							theta += deltaTheta;
						}
						area = new Area(colorToAreaType(color), ci, cj, 0, 0);
						area.setEllipse(r1, r2, thetaEllipse);
						
						//calculation of the error if the area is the supposed ellipse 
						errEll = 0;
						for (int m = i0 ; m < i1 ; m++) {
							for (int n = j0 ; n < j1 ; n++) {
								if (img[m][n][0] == 257) {
									if (!area.isIn((double)m, (double)n)) {
										errEll++;
									}
								}
								else {
									if (area.isIn((double)m, (double)n)) {
										errEll++;
									}
								}
							}
						}
						if (Main.debug[12]) {
							System.out.println("rectangle error - ellipse error : " + errRect + " - " + errEll);
						}
						
						
						if (errEll > errRect) {
							// creation of a new rectangle
							Main.areas[Main.nbAreas] = new Area(colorToAreaType(color), ci, cj, (j1-j0), (i1-i0));
							Main.nbAreas = Main.nbAreas + 1;
							if (Main.debug[12]) {
								System.out.println("Area n°" + Main.nbAreas + " : rectangle");
								System.out.println("type : " + colorToAreaType(color));
								System.out.println("width : " + (j1-j0) + " - height : " + (i1-i0));
							}
						}
						else {
							// creation of a new ellipse
							Main.areas[Main.nbAreas] = area;
							Main.nbAreas = Main.nbAreas + 1;
							if (Main.debug[12]) {
								System.out.println("Area n°" + Main.nbAreas + " : ellipse");
								System.out.println("type : " + colorToAreaType(color));
								System.out.println("r1 : " + r1 + " - r2 : " + r2);
								System.out.println("theta : " + thetaEllipse);
							}
						}
					}
				}
			}
		}
	}
	
	private static int[][][] convertTo2DUsingGetRGB(BufferedImage image) {
		int width = image.getWidth();
		int height = image.getHeight();
		int[][][] result = new int[height][width][3];

		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				int clr = image.getRGB(col, row);
				result[row][col][0] = (clr & 0x00ff0000) >> 16;
				result[row][col][1] = (clr & 0x0000ff00) >> 8;
				result[row][col][2] = clr & 0x000000ff;
			}
		}
		return result;
	}
	
	private int colorMatch(int i, int j, int channel) {
		int ind= 0;
		int minD = 255;
		int dist = 255;
		
		for (int n = 0 ; n < nbColor ; n++) {
			switch(channel) {
			case 0:
			{
				dist = Math.abs(img[i][j][channel] - redChan[n]);
			}
			break;
			case 1:
			{
				dist = Math.abs(img[i][j][channel] - greenChan[n]);
			}
			break;
			case 2:
			{
				dist = Math.abs(img[i][j][channel] - blueChan[n]);
			}
			break;
			}
			if (dist < minD) {
				minD = dist;
				ind = n;
			}
		}
		return ind;
	} 
	
	private int connectedComponent(int i, int j, int color)
	{
		boolean cond = true;
		int imax = 0; // taille actuelle du vecteur de coord
		int ind = 0; // indice courant du vecteur de coord

		coord[ind][0] = i;
		coord[ind][1] = j;

		while (cond) {
			img[coord[ind][0]][coord[ind][1]][0] = 257;
			img[coord[ind][0]][coord[ind][1]][1] = 257;
			img[coord[ind][0]][coord[ind][1]][2] = 257;

			if (coord[ind][0] - 1 >= 0) {
				if (colorMatch(coord[ind][0] - 1, coord[ind][1], 0) == color) {
					img[coord[ind][0] - 1][coord[ind][1]][0] = 256;
					img[coord[ind][0] - 1][coord[ind][1]][1] = 256;
					img[coord[ind][0] - 1][coord[ind][1]][2] = 256;

					imax = imax + 1;
					coord[imax][0] = coord[ind][0] - 1;
					coord[imax][1] = coord[ind][1];
				}
			}
			if (coord[ind][0] + 1 < idim) {
				if (colorMatch(coord[ind][0] + 1, coord[ind][1], 0) == color) {
					img[coord[ind][0] + 1][coord[ind][1]][0] = 256;
					img[coord[ind][0] + 1][coord[ind][1]][1] = 256;
					img[coord[ind][0] + 1][coord[ind][1]][2] = 256;

					imax = imax + 1;
					coord[imax][0] = coord[ind][0] + 1;
					coord[imax][1] = coord[ind][1];
				}
			}
			if (coord[ind][1] - 1 >= 0) {
				if (colorMatch(coord[ind][0], coord[ind][1] - 1, 0) == color) {
					img[coord[ind][0]][coord[ind][1] - 1][0] = 256;
					img[coord[ind][0]][coord[ind][1] - 1][1] = 256;
					img[coord[ind][0]][coord[ind][1] - 1][2] = 256;

					imax = imax + 1;
					coord[imax][0] = coord[ind][0];
					coord[imax][1] = coord[ind][1] - 1;
				}
			}
			if (coord[ind][1] + 1 < jdim) {
				if (colorMatch(coord[ind][0], coord[ind][1] + 1, 0) == color) {
					img[coord[ind][0]][coord[ind][1] + 1][0] = 256;
					img[coord[ind][0]][coord[ind][1] + 1][1] = 256;
					img[coord[ind][0]][coord[ind][1] + 1][2] = 256;

					imax = imax + 1;
					coord[imax][0] = coord[ind][0];
					coord[imax][1] = coord[ind][1] + 1;
				}
			}

			if (ind == imax) {
				cond = false;
			}

			ind = ind + 1;
		} 

		//System.out.println(ind + " _ " + imax);
		// nombre de pixels par composantes

		return ind;

	}
	
	private double getEllipseRadius(double ci, double cj, double pi, double pj) {
		double ia, ja, ib, jb;
		double i, j;
		
		boolean bufIn;
		double r = 0;
		
		if (Math.abs(ci - pi) < Math.abs(cj - pj)) {
			if (cj < pj) {
				ia = ci;
				ja = cj;
				ib = pi;
				jb = pj;
			}
			else {
				ia = pi;
				ja = pj;
				ib = ci;
				jb = cj;
			}

			j = Math.floor(ja);
			i = Math.floor(ia);
			
			if (img[(int)i][(int)j][0] == 257) {
				bufIn = true;
			}
			else {
				bufIn = false;
			}
			do {
				i = Math.floor(ia + (j-ja) * (ib - ia) / (jb - ja));
				if (bufIn)
				{
					if (img[(int)i][(int)j][0] == 257) {
						r = Math.sqrt(Math.pow(ia-i, 2) + Math.pow(ja-j, 2));
					}
					else {
						return r;
					}
				}
				else {
					if (img[(int)i][(int)j][0] == 257) {
						r = Math.sqrt(Math.pow(ib-i, 2) + Math.pow(jb-j, 2));
						return r;
					}
				}
				j++;
			} while(j < jb);
		}
		else {
			if (ci < pi) {
				ia = ci;
				ja = cj;
				ib = pi;
				jb = pj;
			}
			else {
				ia = pi;
				ja = pj;
				ib = ci;
				jb = cj;
			}

			i = Math.floor(ia);
			j = Math.floor(ja);
			
			if (img[(int)i][(int)j][0] == 257) {
				bufIn = true;
			}
			else {
				bufIn = false;
			}
			do {
				j = Math.floor(ja + (i-ia) * (jb - ja) / (ib - ia));
				if (bufIn)
				{
					if (img[(int)i][(int)j][0] == 257) {
						r = Math.sqrt(Math.pow(ia-i, 2) + Math.pow(ja-j, 2));
					}
					else {
						return r;
					}
				}
				else {
					if (img[(int)i][(int)j][0] == 257) {
						r = Math.sqrt(Math.pow(ib-i, 2) + Math.pow(jb-j, 2));
						return r;
					}
				}
				i++;
			} while(i < ib);
		}
		return r;
	}
	
	private int colorToAreaType(int color) {
		switch (color) {
		case 0: {
			return 0;
		}
		case 1: {
			return 1;
		}
		case 2: {
			return 2;
		}
		case 3: {
			return 3;
		}
		case 4: {
			return 4;
		}
		}
		return 0;
	}
	
	private int get_contour() {
		int ind; // indice courant
		int ii, jj;
		int buf_i; // c est pour savoir dans quelle direction ton bord etait (a l iteration d avant)
		int i = 0;
		boolean cond;
		boolean in;
		boolean out;

		ind = 0;
		buf_i = 0;
		cond = true;
		in = false;
		out = false;

		while (cond) {
			ii = coord[ind][0];
			jj = coord[ind][1];


			//buf_i = 0;
			buf_i = buf_i + 5; // pour partir dans la direction opposée
			for (int iii = buf_i; iii < buf_i+8; iii++) 
			{
				i = iii;
				while(i>=8)
				{
					i = i - 8;
				}
				switch (i) {
				case 0: {
					if (ii - 1 >= 0) {
						if (img[ii - 1][jj][0] != 257) 
						{
							out = true;
						} else {
							out = false;
						}
					} else {
						out = true;
					}
					if (ii - 1 >= 0 && jj + 1 < jdim) {
						if (img[ii - 1][jj + 1][0] == 257) 
						{
							in = true;
						} else {
							in = false;
						}
					} else {
						in = false;
					}
				}
				break;
				case 1: {
					if (ii - 1 >= 0 && jj + 1 < jdim) 
					{
						if (img[ii - 1][jj + 1][0] != 257) 
						{
							out = true;
						} else {
							out = false;
						}
					} else {
						out = true;
					}
					if (jj + 1 < jdim) 
					{
						if (img[ii][jj + 1][0] == 257) 
						{
							in = true;
						} else {
							in = false;
						}
					} else {
						in = false;
					}
				}
				break;
				case 2: {
					if (jj + 1 < jdim) 
					{
						if (img[ii][jj + 1][0] != 257) 
						{
							out = true;
						} else {
							out = false;
						}
					} else {
						out = true;
					}
					if (ii + 1 < idim && jj + 1 < jdim) 
					{
						if (img[ii + 1][jj + 1][0] == 257) 
						{
							in = true;
						} else {
							in = false;
						}
					} else {
						in = false;
					}
				}
				break;
				case 3: {
					if (ii + 1 < idim && jj + 1 < jdim) 
					{
						if (img[ii + 1][jj + 1][0] != 257) 
						{
							out = true;
						} else {
							out = false;
						}
					} else {
						out = true;
					}
					if (ii + 1 < idim) 
					{
						if (img[ii + 1][jj][0] == 257) 
						{
							in = true;
						} else {
							in = false;
						}
					} else {
						in = false;
					}
				}
				break;
				case 4: {
					if (ii + 1 < idim) 
					{
						if (img[ii + 1][jj][0] != 257) 
						{
							out = true;
						} else {
							out = false;
						}
					} else {
						out = true;
					}
					if (ii + 1 < idim && jj - 1 >= 0) 
					{
						if (img[ii + 1][jj - 1][0] == 257) 
						{
							in = true;
						} else {
							in = false;
						}
					} else {
						in = false;
					}
				}
				break;
				case 5: {
					if (ii + 1 < idim && jj - 1 >= 0) 
					{
						if (img[ii + 1][jj - 1][0] != 257) 
						{
							out = true;
						} else 
						{
							out = false;
						}
					} else 
					{
						out = true;
					}
					if (jj - 1 >= 0) 
					{
						if (img[ii][jj - 1][0] == 257) 
						{
							in = true;
						} else {
							in = false;
						}
					} else {
						in = false;
					}
				}
				break;
				case 6: {
					if (jj - 1 >= 0) {
						if (img[ii][jj - 1][0] != 257) {
							out = true;
						} else {
							out = false;
						}
					} else {
						out = true;
					}
					if (ii - 1 >= 0 && jj - 1 >= 0) {
						if (img[ii - 1][jj - 1][0] == 257) {
							in = true;
						} else {
							in = false;
						}
					} else {
						in = false;
					}
				}
				break;
				case 7: {
					if (ii - 1 >= 0 && jj - 1 >= 0) {
						if (img[ii - 1][jj - 1][0] != 257) {
							out = true;
						} else {
							out = false;
						}
					} else {
						out = true;
					}
					if (ii - 1 >= 0) {
						if (img[ii - 1][jj][0] == 257) {
							in = true;
						} else {
							in = false;
						}
					} else {
						in = false;
					}
				}
				break;
				}

				if (in && out) {
					ind = ind + 1;
					switch (i) {
					case 0: {
						coord[ind][0] = ii - 1;
						coord[ind][1] = jj + 1;
					}
					break;
					case 1: {
						coord[ind][0] = ii;
						coord[ind][1] = jj + 1;
					}
					break;
					case 2: {
						coord[ind][0] = ii + 1;
						coord[ind][1] = jj + 1;
					}
					break;
					case 3: {
						coord[ind][0] = ii + 1;
						coord[ind][1] = jj;
					}
					break;
					case 4: {
						coord[ind][0] = ii + 1;
						coord[ind][1] = jj - 1;
					}
					break;
					case 5: {
						coord[ind][0] = ii;
						coord[ind][1] = jj - 1;
					}
					break;
					case 6: {
						coord[ind][0] = ii - 1;
						coord[ind][1] = jj - 1;
					}
					break;
					case 7: {
						coord[ind][0] = ii - 1;
						coord[ind][1] = jj;
					}
					break;
					}
					iii = 10000;
				}
			} 

			buf_i = i;

			if (coord[ind][0] == coord[0][0] && coord[ind][1] == coord[0][1]) {
				cond = false;
			}
			if (ind == idim*jdim -1) // debug
			{

				for (int p=0; p<idim*jdim; p++)
				{
					img[coord[p][0]][coord[p][1]][0] = 500;
				}
			}
		}
		return ind;
	} 

	private int simplif_contour(int nb_coord) throws InterruptedException 
	{
		int nb_coord_true; // nombre de coord à true
		int i0, i1;
		double dist;
		int buf_ind;
		double buf_dist;
		boolean cond;
		boolean ind_chos[];

		cond = true;
		ind_chos = new boolean[nb_coord];
		ind_chos[0] = true;
		ind_chos[nb_coord - 1] = true;

		for (int i = 1; i < nb_coord - 1; i++) 
		{
			ind_chos[i] = false;
		}

		while (cond) 
		{
			i0 = 0;
			i1 = 0;

			buf_ind = 0;
			buf_dist = 0;

			while (i0 < nb_coord-1) 
			{
				i1 = i0 + 1;

				while (ind_chos[i1] == false) 
				{
					i1 = i1 + 1;
				}

				//System.out.println(coord[i0][0] + " _ " + coord[i0][1] + " _ " + coord[i1][0] + " _ " + coord[i1][1]);

				for (int i = i0 + 1; i < i1; i++) 
				{
					dist = dist2DroiteToPoint((double)coord[i0][0], (double)coord[i0][1], (double)coord[i1][0], (double)coord[i1][1], (double)coord[i][0],	(double)coord[i][1]);

					if (dist > buf_dist) 
					{
						buf_dist = dist;
						buf_ind = i;
					}
				}

				i0 = i1;

			}

			if (buf_dist > err_max_hb) 
			{
				ind_chos[buf_ind] = true;
			} 
			else 
			{
				cond = false;
			}

		}

		nb_coord_true = 0;

		for (int i=0; i<nb_coord; i++)
		{
			if (ind_chos[i] == true)
			{
				coord[nb_coord_true][0] = coord[i][0];
				coord[nb_coord_true][1] = coord[i][1];
				nb_coord_true = nb_coord_true + 1;
			}
		} // fin du for

		return nb_coord_true;
	}

	private void suppr_compo_connexe()
	{
		for (int i=0; i<idim; i++)
		{
			for( int j=0; j<jdim; j++)
			{
				if (img[i][j][0] == 257)
				{
					img[i][j][0] = 255;
					img[i][j][1] = 255;
					img[i][j][2] = 255;
				}

			}
		}
	}

	private int get_ligne()
	{
		int ind = 0;
		for(int j=0; j<jdim; j++)
		{
			for(int i=0; i<idim; i++)
			{
				if(img[i][j][0] == 257)
				{
					coord[ind][0] = i;
					coord[ind][1] = j;
					i = idim;
					ind = ind + 1;
				}
			}
		}
		return ind;
	}
	
	private double dist2DroiteToPoint(double ax,double ay,double bx,double by,double cx,double cy)
	{
		/** Renvoie la distance au carré du point C à la droite (AB) */
		double dist2;
		double a2,b2,c2; // carré des distances

		a2 = (bx-cx)*(bx-cx)+(by-cy)*(by-cy);
		b2 = (ax-cx)*(ax-cx)+(ay-cy)*(ay-cy);
		c2 = (bx-ax)*(bx-ax)+(by-ay)*(by-ay);

		dist2 = a2 - ((c2 - b2 + a2)*(c2 - b2 + a2))/(4*c2);
		
		return dist2;
	}
} 
