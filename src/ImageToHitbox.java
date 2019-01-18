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
			redChan[n] = (255*n)/(nbColor-1);
			greenChan[n] = (255*n)/(nbColor-1);
			blueChan[n] = (255*n)/(nbColor-1);
		}
	}
	
	
	
	public void getArea(String fileName) {
		int i, j;
		int color, colorG;
		int ind;
		
		int i0, j0, i1, j1; 
		double ci, cj;
		double rSup, rRect; 
		
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
					colorG = colorMatch(i, j, 1);
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
							rRect = rSup;
							if(Math.abs(rSup * Math.cos(theta))>(i1-ci+1) ) {
								rRect = (i1-ci+1)/Math.cos(theta);
							}
							if(Math.abs(rRect * Math.sin(theta))>(j1-cj+1) ) {
								rRect = (j1-cj+1)/Math.sin(theta);
							}
							r = getEllipseRadius(ci, cj, ci - rRect * Math.cos(theta), cj + rRect * Math.sin(theta));
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
							Main.areas[Main.nbAreas] = new Area(colorToAreaType(color), i0, j0, (j1-j0), (i1-i0));
							
							// trampoline
							if(colorToAreaType(color) == 7) {
								Main.areas[Main.nbAreas].setSpeedMultTp(colorToAreaJumpSpeed(colorG));
							}
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
							
							// trampoline
							if(colorToAreaType(color) == 7) {
								Main.areas[Main.nbAreas].setSpeedMultTp(colorToAreaJumpSpeed(colorG));
							}
							
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
	
	public void getHitbox(String fileName) {
		int i, j;
		int color;
		int ind;
		
		
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
		for(i=0; i<idim; i++) {
			img[i][0][0] = 255;
			img[i][0][1] = 255;
			img[i][0][2] = 255;
			img[i][jdim-1][0] = 255;
			img[i][jdim-1][1] = 255;
			img[i][jdim-1][2] = 255;
		}
		for(j=0; j<jdim; j++) {
			img[0][j][0] = 255;
			img[0][j][1] = 255;
			img[0][j][2] = 255;
			img[idim-1][j][0] = 255;
			img[idim-1][j][1] = 255;
			img[idim-1][j][2] = 255;
		}
		
		coord = new int[idim * jdim][2];

		for (i = 0; i < idim; i++) {
			for (j = 0; j < jdim; j++) {
				color = colorMatch(i, j, 0);

				if (color != nbColor - 1) {
					
					ind = connectedComponent(i, j, color);
					if (ind > 100) {
						ind = getContour();
						if (ind > 20) {
							ind = simplifyContour(ind);
							
							Main.sceneries[Main.nbSceneries] = new Scenery(ind);
							Main.sceneries[Main.nbSceneries].type = 1;
							ind = ind - 1;
							for (int n = 0; n < ind; n++) {
								Main.sceneries[Main.nbSceneries].setOnePoint(n, Main.rappImage*(double) coord[n][0], Main.rappImage*(double) coord[n][1]);
							}
							Main.sceneries[Main.nbSceneries].setOnePoint(ind, Main.rappImage*(double) coord[0][0], Main.rappImage*(double) coord[0][1]);
	
							Main.nbSceneries = Main.nbSceneries + 1;
							
							if (Main.debug[12]) {
								System.out.println("Hitbox n°" + Main.nbSceneries + " : " + ind + " edges");
							}
						}
					}
					supprConnectedComponent();
				}
			}
		}
	}
	
	public void getHitboxLine(String fileName) {
		int i, j;
		int color;
		int ind;
		
		// opening of the texture file
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
					if (ind > 100) {
						ind = getTrajectory();
						if (ind > 20) {
							ind = simplifyContour(ind);

							Main.sceneries[Main.nbSceneries] = new Scenery(2);
							Main.sceneries[Main.nbSceneries].type = 2;
								Main.sceneries[Main.nbSceneries].setOnePoint(0, Main.rappImage*(double) coord[0][0], Main.rappImage*(double) coord[0][1]);
								Main.sceneries[Main.nbSceneries].setOnePoint(1, Main.rappImage*(double) coord[ind-1][0], Main.rappImage*(double) coord[ind-1][1]);
							Main.nbSceneries = Main.nbSceneries + 1;
						}
					}
				}
			}
		}
		
	}
	
	public void getMovingHitbox(String fileNameTrajectory, String fileNameHitbox, String fileNameTexture) {
		int i, j;
		int color, colorR, colorG, colorB;
		int ind;
		int i0, i1, j0, j1;
		int[][][] imgTexture;
		int idimTexture, jdimTexture;
		
		
		// opening of the texture file
		BufferedImage bufferedImg = null;
		try {
			bufferedImg = ImageIO.read(new File(fileNameTexture));
		} catch (IOException e) {
			e.printStackTrace();
		}
		img = convertTo2DUsingGetRGB(bufferedImg);
		bufferedImg = null;
		
		idim = img.length;
		jdim = img[0].length;
		
		i0 = idim;
		i1 = 0;
		j0 = jdim;
		j1 = 0;
		for(i=0 ; i<idim ; i++) {
			for(j=0 ; j<jdim ; j++) {
				if(!(img[i][j][0] > 251 &&
						img[i][j][1] < 4  &&
						img[i][j][2] > 251 )) {
					if(i<i0) {
						i0 = i;
					}
					if(j<j0) {
						j0 = j;
					}
					if(i>i1) {
						i1 = i;
					}
					if(j>j1) {
						j1 = j;
					}
				}
			}
		}
		
		idimTexture = i1 - i0;
		jdimTexture = j1 - j0;
		imgTexture = new int[idimTexture][jdimTexture][3];
		for(i=0 ; i<idimTexture ; i++) {
			for(j=0 ; j<jdimTexture ; j++) {
				imgTexture[i][j][0] = img[i+i0][j+j0][0];
				imgTexture[i][j][1] = img[i+i0][j+j0][1];
				imgTexture[i][j][2] = img[i+i0][j+j0][2];
			}
		}
		
		System.out.println("texture loaded");
				
		// opening of the hitbox file
		bufferedImg = null;
		try {
			bufferedImg = ImageIO.read(new File(fileNameHitbox));
		} catch (IOException e) {
			e.printStackTrace();
		}
		img = convertTo2DUsingGetRGB(bufferedImg);
		bufferedImg = null;
		
		idim = img.length;
		jdim = img[0].length;
		
		coord = new int[idim * jdim][2];
		double[][] coordHB = new double[1000][2];
		int indHB = 0;

		for (i = 0; i < idim; i++) {
			for (j = 0; j < jdim; j++) {
				color = colorMatch(i, j, 0);

				if (color != nbColor - 1) {
					
					ind = connectedComponent(i, j, color);
					if (ind > 40) {
						ind = getContour();
						if (ind > 10) {
							ind = simplifyContour(ind);
							
							//ind = ind;
							indHB = ind;
							for (int n = 0; n < ind; n++) {
								coordHB[n][0] = Main.rappImage*(double) coord[n][0];
								coordHB[n][1] = Main.rappImage*(double) coord[n][1];
							}
							System.out.println("ind : "+ ind);
						}
					}
					supprConnectedComponent();
				}
			}
		} // End hitbox
		System.out.println("hitbox loaded");
		
		
		// opening of the Trajectory file
		bufferedImg = null;
		try {
			bufferedImg = ImageIO.read(new File(fileNameTrajectory));
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
				colorR = colorMatch(i, j, 0);

				if (colorR != nbColor - 1) {
					colorG = colorMatch(i, j, 1);
					colorB = colorMatch(i, j, 2);
					
					ind = connectedComponent(i, j, colorR);
					if (ind > 100) {
						ind = getTrajectory();
						if (ind > 20) {
							ind = simplifyContour(ind);
							
							for(int nb=0; nb<colorToMovingHBPlatformNumber(colorB);nb++) {
								Main.sceneries[Main.nbSceneries] = new Scenery(indHB);
								Main.sceneries[Main.nbSceneries].type = colorToMovingHBType(colorR);
								Main.sceneries[Main.nbSceneries].typeMove = colorToMovingHBMoveType(colorR);
								Main.sceneries[Main.nbSceneries].period = colorToMovingHBPeriod(colorG);
								Main.sceneries[Main.nbSceneries].time = nb*(int)Math.floor(colorToMovingHBPeriod(colorG))/colorToMovingHBPlatformNumber(colorB);
								
								Main.sceneries[Main.nbSceneries].newTrajectory(ind);
								
								if (Math.abs(coord[ind][0]-coord[0][0]) > Math.abs(coord[ind][1]-coord[0][1])) {
									if ((coord[ind][0] - coord[0][0] > 0 && colorToMovingHBDirection(colorR) == 0)
											|| (coord[ind][0] - coord[0][0] < 0 && colorToMovingHBDirection(colorR) == 1))
									{
										for (int n = 0; n < ind; n++) {
											Main.sceneries[Main.nbSceneries].setOneTrajectory(n, Main.rappImage*(double) coord[n][0], Main.rappImage*(double) coord[n][1]);	
										}
									}
									else {
										for (int n = 0; n < ind; n++) {
											Main.sceneries[Main.nbSceneries].setOneTrajectory((ind-1)-n, Main.rappImage*(double) coord[n][0], Main.rappImage*(double) coord[n][1]);
										}
									}
								}
								else {
									if ((coord[ind][1] - coord[0][1] > 0 && colorToMovingHBDirection(colorR) == 0)
											|| (coord[ind][1] - coord[0][1] < 0 && colorToMovingHBDirection(colorR) == 1))
									{
										for (int n = 0; n < ind; n++) {
											Main.sceneries[Main.nbSceneries].setOneTrajectory(n, Main.rappImage*(double) coord[n][0], Main.rappImage*(double) coord[n][1]);	
										}
									}
									else {
										for (int n = 0; n < ind; n++) {
											Main.sceneries[Main.nbSceneries].setOneTrajectory((ind-1)-n, Main.rappImage*(double) coord[n][0], Main.rappImage*(double) coord[n][1]);
										}
									}
								}

								for(int iHB=0; iHB<indHB; iHB++) {
									Main.sceneries[Main.nbSceneries].setOnePoint(iHB, coordHB[iHB][0] - Main.sceneries[Main.nbSceneries].getOneTrajectory(0,0)
											, coordHB[iHB][1] - Main.sceneries[Main.nbSceneries].getOneTrajectory(0,1));
								}
								Main.sceneries[Main.nbSceneries].transi = - i0 + (int)Main.sceneries[Main.nbSceneries].getOneTrajectory(0,0);
								Main.sceneries[Main.nbSceneries].transj = - j0 + (int)Main.sceneries[Main.nbSceneries].getOneTrajectory(0,1);
								Main.sceneries[Main.nbSceneries].img = new int[idimTexture][jdimTexture][3];
								Main.sceneries[Main.nbSceneries].idim = idimTexture;
								Main.sceneries[Main.nbSceneries].jdim = jdimTexture;
								for(int ii=0 ; ii<idimTexture ; ii++) {
									for(int jj=0 ; jj<jdimTexture ; jj++) {
										Main.sceneries[Main.nbSceneries].img[ii][jj][0] = imgTexture[ii][jj][0];
										Main.sceneries[Main.nbSceneries].img[ii][jj][1] = imgTexture[ii][jj][1];
										Main.sceneries[Main.nbSceneries].img[ii][jj][2] = imgTexture[ii][jj][2];
									}
								}
								
								if (Main.debug[14]) {
									System.out.println("Hitbox n°" + Main.nbSceneries);
									System.out.println("Type: " + Main.sceneries[Main.nbSceneries].type + " - colorR: " + colorR);
									System.out.println("TypeMove: " + Main.sceneries[Main.nbSceneries].typeMove);
									System.out.println("Period: " + Main.sceneries[Main.nbSceneries].period + " - colorG: " + colorG);
									System.out.println("Time: " + Main.sceneries[Main.nbSceneries].time + " - colorB: " + colorB);
								
								}

								Main.nbSceneries = Main.nbSceneries + 1;
							}
						}
					}
					supprConnectedComponent();
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
		/* red chanel */
		switch (color) {
		case 0: {
			// nothing
			return 0;
		}
		case 1: {
			// water
			return 1;
		}
		case 2: {
			// lava
			return 2;
		}
		case 3: {
			// void
			return 3;
		}
		case 4: {
			// scale
			return 4;
		}
		case 5: {
			// trampoline
			return 7;
		}
		}
		return 0;
	}
	
	private double colorToAreaJumpSpeed(int color) {
		/* green chanel */
		switch (color) {
		case 0: {
			return 1.5;
		}
		case 1: {
			return 2;
		}
		case 2: {
			return 2.5;
		}
		case 3: {
			return 3;
		}
		case 4: {
			return 3.5;
		}
		case 5: {
			return 4;
		}
		case 6: {
			return 4.5;
		}
		case 7: {
			return 5;
		}
		case 8: {
			return 5.5;
		}
		}
		return 0;
	}
	
	private int colorToMovingHBType(int color) {
		/* red chanel */
		return color/4;
	}
	
	private int colorToMovingHBMoveType(int color) {
		/* red chanel */
		return (color/2)%2+1;
	}
	
	private int colorToMovingHBDirection(int color) {
		/* red chanel */
		/*
		 * 0 : left to right or top to bottom
		 * 1 : right to left or bottom to top
		 */
		return color%2;
	}
	
	private double colorToMovingHBPeriod(int color) {
		/* green chanel */
		switch (color) {
		case 0: {
			return 50;
		}
		case 1: {
			return 75;
		}
		case 2: {
			return 100;
		}
		case 3: {
			return 125;
		}
		case 4: {
			return 150;
		}
		case 5: {
			return 175;
		}
		case 6: {
			return 200;
		}
		case 7: {
			return 250;
		}
		case 8: {
			return 300;
		}
		case 9: {
			return 350;
		}
		case 10: {
			return 400;
		}
		case 11: {
			return 500;
		}
		case 12: {
			return 600;
		}
		}
		return 1;
	}
	
	private int colorToMovingHBPlatformNumber(int color) {
		/* blue chanel */
		return color+1;
	}
	
	private int getContour() {
		int ind; // indice courant
		int ii, jj;
		int buf_i; // c est pour savoir dans quelle direction ton bord etait (a l iteration d avant)
		int i = 0;
		boolean cond;
		boolean in;
		boolean out;
		int maxIter = idim*jdim-1;

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
			if (ind == maxIter) // debug
			{

				for (int p=0; p<idim*jdim; p++)
				{
					img[coord[p][0]][coord[p][1]][0] = 500;
				}
			}
		}
		return ind;
	} 

	private int simplifyContour(int nb_coord)
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
					dist = dist2LineToPoint((double)coord[i0][0], (double)coord[i0][1], (double)coord[i1][0], (double)coord[i1][1], (double)coord[i][0],	(double)coord[i][1]);

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

	private void supprConnectedComponent()
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

	private int getTrajectory() {
		int ind = 0;
		int indRev1, indRev2;
		int coordi, coordj;
		int n, buf_n, init_n;
		
		int[][] deltaP = new int[20][2];
		
		for(int k=0 ; k<5 ; k++) {
			deltaP[k][0] = -3;
			deltaP[k][1] = k-2;
		}
		for(int k=0 ; k<5 ; k++) {
			deltaP[k+5][0] = k-2;
			deltaP[k+5][1] = 3;
		}
		for(int k=0 ; k<5 ; k++) {
			deltaP[k+10][0] = 3;
			deltaP[k+10][1] = 2-k;
		}
		for(int k=0 ; k<5 ; k++) {
			deltaP[k+15][0] = 2-k;
			deltaP[k+15][1] = -3;
		}
		n = 0;
		buf_n = 10;
		// coord[0] already contains a pixel in the trajectory
		
		n = getNextTrajPoint(coord[ind][0], coord[ind][1], deltaP, buf_n);
		ind = ind + 1;
		coord[ind][0] = coord[ind-1][0] + deltaP[n][0];
		coord[ind][1] = coord[ind-1][1] + deltaP[n][1];
		buf_n = n;
		init_n = n;
		n = 0;
		
		while(n > -1) {
			n = getNextTrajPoint(coord[ind][0], coord[ind][1], deltaP, buf_n);
			if(n > -1) {
				ind = ind + 1;
				coord[ind][0] = coord[ind-1][0] + deltaP[n][0];
				coord[ind][1] = coord[ind-1][1] + deltaP[n][1];
			}
			buf_n = n;
			
		}

		indRev1 = 0;
		indRev2 = ind;
		n = getNextTrajPoint(coord[0][0], coord[0][1], deltaP, init_n-10);
		ind = ind + 1;
		coord[ind][0] = coord[0][0] + deltaP[n][0];
		coord[ind][1] = coord[0][1] + deltaP[n][1];
		buf_n = n;
		while(n > -1) {
			n = getNextTrajPoint(coord[ind][0], coord[ind][1], deltaP, buf_n);
			if(n > -1) {
				ind = ind + 1;
				coord[ind][0] = coord[ind-1][0] + deltaP[n][0];
				coord[ind][1] = coord[ind-1][1] + deltaP[n][1];
			}
			buf_n = n;
			
		}
		
		while(indRev1<indRev2) {
			coordi = coord[indRev1][0];
			coordj = coord[indRev1][1];
			
			coord[indRev1][0] = coord[indRev2][0];
			coord[indRev1][1] = coord[indRev2][1];
			
			coord[indRev2][0] = coordi;
			coord[indRev2][1] = coordj;
			indRev1 = indRev1 + 1;
			indRev2 = indRev2 - 1;
		}
		
		
		return ind;
	}
	
	private int getNextTrajPoint(int i, int j, int[][] deltaP, int buf_n) {
		
		int minErr, err;
		int new_n;
		
		minErr = 10; // max error is 9
		new_n = 0;
		
		for(int n=0 ; n<20 ; n++) {
			err = 0;
			for(int ii=-1 ; ii<2 ; ii++) {
				for(int jj=-1 ; jj<2 ; jj++) {
					if(i+deltaP[n][0]+ii>=0 && i+deltaP[n][0]+ii<idim && j+deltaP[n][1]+jj>=0 && j+deltaP[n][1]+jj<jdim) {
						if(img[i+deltaP[n][0]+ii][j+deltaP[n][1]+jj][0]<257) {
							err = err + 1;
						}
						if(((20+n-buf_n)%20)>5 && ((20+n-buf_n)%20)<15) {
							err = err + 1;
						}
					}
				}
			}
			if(err<minErr) {
				new_n = n;
				minErr = err;
			}
		}
		if(minErr<6) {
			return new_n;
		}
		else {
			return -1;
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
	
	private double dist2LineToPoint(double ax,double ay,double bx,double by,double cx,double cy)
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
