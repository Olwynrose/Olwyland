import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageToHitbox {

	int[][][] mat_img;
	int[][] coord; // liste des coordonnées des pixels contenus dans la
	// composante
	// liste des coordonnées des pixels contenus dans le contour de la
	// composante
	// liste .....
	int idim, jdim;
	int[] noir, vert, cyan, rouge, bleu, jaune, magenta;
	int nb_couleur;
	int seuil_couleur;

	int err_max_hb; // erreur en pixels au carré
	
	double cx, cy;
	double d_hauteur, d_largeur;
	int forme_zone; 

	public ImageToHitbox() throws InterruptedException, IOException {
		seuil_couleur = 75;
		nb_couleur = 7;

		err_max_hb = 3*3;

		int couleur;
		int i, j;
		int ind;

		// hitbox
		noir = new int[3];
		noir[0] = 0;
		noir[1] = 0;
		noir[2] = 0;
		// dégat périodique (poison)
		vert = new int[3];
		vert[0] = 0;
		vert[1] = 255;
		vert[2] = 0;
		// glace
		cyan = new int[3];
		cyan[0] = 0;
		cyan[1] = 255;
		cyan[2] = 255;
		// one shot (lave)
		rouge = new int[3];
		rouge[0] = 255;
		rouge[1] = 0;
		rouge[2] = 0;
		// eau 
		bleu = new int[3];
		bleu[0] = 0;
		bleu[1] = 0;
		bleu[2] = 255;
		// échelles
		jaune = new int[3]; 
		jaune[0] = 255;
		jaune[1] = 255;
		jaune[2] = 0;
		// plateformes
		magenta = new int[3];
		magenta[0] = 255;
		magenta[1] = 0;
		magenta[2] = 255;

		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(Main.hitboxFileImage));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mat_img = convertTo2DUsingGetRGB(img);
		img = null;
		idim = mat_img.length;
		jdim = mat_img[0].length;


		System.out.println("image hitbox chargée");
		coord = new int[idim * jdim][2];

		for (i = 0; i < idim; i++) {
			for (j = 0; j < jdim; j++) {
				couleur = color_match(i, j);

				if (couleur == 1) {
					ind = compo_connexe(i, j, couleur);
					//System.out.println("compo connexe : " + ind) ;
					// ///////////////////////////////////////// DEBUG ///////////////////////////////////				
					/*
					BufferedWriter writer = new BufferedWriter(new FileWriter("tmp.txt"));
			        writer.write(Integer.toString(idim));
			           writer.newLine();
			           writer.flush();
			           writer.write(Integer.toString(jdim));
			        writer.newLine();
			        writer.flush();
			        for(int iii=0;iii<idim;iii++)
					 {
			       	 for(int jjj=0;jjj<jdim;jjj++)
			   		 {
						 	writer.write(Integer.toString(mat_img[iii][jjj][0]));
			       	          writer.newLine();
			       	          writer.flush();

			   		 }
					 }
			        writer.close();
			        System.out.println("ok");
			        Thread.sleep(4000);
					 */
					/////////////////////////////////////////////////////////////////////////////////////////
					if(ind>10)
					{
						ind = get_contour();
	
						suppr_compo_connexe();
	
						if (ind > 2)
						{
							ind = simplif_contour(ind);
	
							

							System.out.println("Il y a " + ind + " aretes dans la hitbox " + Main.nbSceneries);
	
							Main.sceneries[Main.nbSceneries] = new Scenery(ind);
							Main.sceneries[Main.nbSceneries].type = 1;
							ind = ind - 1;
							for (int n = 0; n < ind; n++) {
								Main.sceneries[Main.nbSceneries].setOnePoint(n, Main.rappImage*(double) coord[n][0], Main.rappImage*(double) coord[n][1]);
							}
							Main.sceneries[Main.nbSceneries].setOnePoint(ind, Main.rappImage*(double) coord[0][0], Main.rappImage*(double) coord[0][1]);
	
							Main.nbSceneries = Main.nbSceneries + 1;
						} // fin du if ind
					} // fin du premier if ind
				} // fin du noir (hitbox)
				if (couleur == 6)
				{
					compo_connexe(i, j, couleur);
					ind = get_ligne();
					suppr_compo_connexe();

					if (ind > 2 && Main.nbSceneries<Main.maxNbSceneries)
					{
						ind = simplif_contour(ind);

						Main.sceneries[Main.nbSceneries] = new Scenery(ind);
						Main.sceneries[Main.nbSceneries].type = 2;
						
						for (int n = 0; n < ind; n++) {
							Main.sceneries[Main.nbSceneries].setOnePoint(n, Main.rappImage*(double) coord[n][0], Main.rappImage*(double) coord[n][1]);
						}

						System.out.println("Il y a " + ind + " aretes dans la plateforme " + Main.nbSceneries);

						

						Main.nbSceneries = Main.nbSceneries + 1;
					} // fin du if ind
				} // fin du magenta (plateforme)
				
				if (couleur == 7)
				{
					compo_connexe(i, j, couleur);
					get_zone();
					
					suppr_compo_connexe();

					System.out.println("La zone " + Main.nbAreas + " est de type 2 et de forme " + forme_zone);
					
					Main.areas[Main.nbAreas] = new Area(4, Main.rappImage*cy-Main.rappImage*d_hauteur, Main.rappImage*cx-Main.rappImage*d_largeur, 2*Main.rappImage*d_largeur, 2*Main.rappImage*d_hauteur);
					//Main.zone_map[Main.nb_zone].forme = forme_zone;
					
					Main.nbAreas = Main.nbAreas + 1;
					

				} // fin du jaune (échelle)
			} // fin du for sur j
		} // fin du for sur i
		

	}// fin constructeur

	private int color_match(int i, int j) {
		int couleur = 0;
		int dist;

		for (int n = 1; n <= nb_couleur; n++) {
			dist = 0;
			for (int k = 0; k < 3; k++) {
				switch (n) {
				case 1: {
					dist = dist + (mat_img[i][j][k] - noir[k]) * (mat_img[i][j][k] - noir[k]);
				}
				break;
				case 2: {
					dist = dist + (mat_img[i][j][k] - rouge[k]) * (mat_img[i][j][k] - rouge[k]);
				}
				break;
				case 3: {
					dist = dist + (mat_img[i][j][k] - vert[k]) * (mat_img[i][j][k] - vert[k]);
				}
				break;
				case 4: {
					dist = dist + (mat_img[i][j][k] - bleu[k]) * (mat_img[i][j][k] - bleu[k]);
				}
				break;
				case 5: {
					dist = dist + (mat_img[i][j][k] - cyan[k]) * (mat_img[i][j][k] - cyan[k]);
				}
				break;
				case 6: {
					dist = dist + (mat_img[i][j][k] - magenta[k]) * (mat_img[i][j][k] - magenta[k]);
				}
				break;
				case 7: {
					dist = dist + (mat_img[i][j][k] - jaune[k]) * (mat_img[i][j][k] - jaune[k]);
				}
				break;
				}// fin du switch sur n
			} // fin du for sur k
			if (dist < seuil_couleur) {
				couleur = n;
				break;
			}
		} // fin du for sur n

		return couleur;
	}// fin methode color_match

	private static int[][][] convertTo2DUsingGetRGB(BufferedImage image) {
		int width = image.getWidth();
		int height = image.getHeight();
		int[][][] result = new int[height][width][3];
		// Color coul;

		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				int clr = image.getRGB(col, row);
				result[row][col][0] = (clr & 0x00ff0000) >> 16;
			result[row][col][1] = (clr & 0x0000ff00) >> 8;
			result[row][col][2] = clr & 0x000000ff;
			}
		}
		return result;
	} // fin methode convert..

	private int compo_connexe(int i, int j, int couleur) throws IOException, InterruptedException
	/**
	 * Donne la composante connexe. demande les coordonées du premier point dans
	 * la composante.
	 */
	{
		boolean cond = true;
		int imax = 0; // taille actuelle du vecteur de coord
		int ind = 0; // indice courant du vecteur de coord

		coord[ind][0] = i;
		coord[ind][1] = j;

		while (cond) {
			mat_img[coord[ind][0]][coord[ind][1]][0] = 257;
			mat_img[coord[ind][0]][coord[ind][1]][1] = 257;
			mat_img[coord[ind][0]][coord[ind][1]][2] = 257;

			if (coord[ind][0] - 1 >= 0) {
				if (color_match(coord[ind][0] - 1, coord[ind][1]) == couleur) {
					mat_img[coord[ind][0] - 1][coord[ind][1]][0] = 256;
					mat_img[coord[ind][0] - 1][coord[ind][1]][1] = 256;
					mat_img[coord[ind][0] - 1][coord[ind][1]][2] = 256;

					imax = imax + 1;
					coord[imax][0] = coord[ind][0] - 1;
					coord[imax][1] = coord[ind][1];
				}
			}
			if (coord[ind][0] + 1 < idim) {
				if (color_match(coord[ind][0] + 1, coord[ind][1]) == couleur) {
					mat_img[coord[ind][0] + 1][coord[ind][1]][0] = 256;
					mat_img[coord[ind][0] + 1][coord[ind][1]][1] = 256;
					mat_img[coord[ind][0] + 1][coord[ind][1]][2] = 256;

					imax = imax + 1;
					coord[imax][0] = coord[ind][0] + 1;
					coord[imax][1] = coord[ind][1];
				}
			}
			if (coord[ind][1] - 1 >= 0) {
				if (color_match(coord[ind][0], coord[ind][1] - 1) == couleur) {
					mat_img[coord[ind][0]][coord[ind][1] - 1][0] = 256;
					mat_img[coord[ind][0]][coord[ind][1] - 1][1] = 256;
					mat_img[coord[ind][0]][coord[ind][1] - 1][2] = 256;

					imax = imax + 1;
					coord[imax][0] = coord[ind][0];
					coord[imax][1] = coord[ind][1] - 1;
				}
			}
			if (coord[ind][1] + 1 < jdim) {
				if (color_match(coord[ind][0], coord[ind][1] + 1) == couleur) {
					mat_img[coord[ind][0]][coord[ind][1] + 1][0] = 256;
					mat_img[coord[ind][0]][coord[ind][1] + 1][1] = 256;
					mat_img[coord[ind][0]][coord[ind][1] + 1][2] = 256;

					imax = imax + 1;
					coord[imax][0] = coord[ind][0];
					coord[imax][1] = coord[ind][1] + 1;
				}
			}

			if (ind == imax) {
				cond = false;
			}

			ind = ind + 1;
		} // fin while

		//System.out.println(ind + " _ " + imax);
		// nombre de pixels par composantes

		return ind;

	} // fin methode compo connexe

	private int get_contour() throws InterruptedException, IOException {
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
						if (mat_img[ii - 1][jj][0] != 257) 
						{
							out = true;
						} else {
							out = false;
						}
					} else {
						out = true;
					}
					if (ii - 1 >= 0 && jj + 1 < jdim) {
						if (mat_img[ii - 1][jj + 1][0] == 257) 
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
						if (mat_img[ii - 1][jj + 1][0] != 257) 
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
						if (mat_img[ii][jj + 1][0] == 257) 
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
						if (mat_img[ii][jj + 1][0] != 257) 
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
						if (mat_img[ii + 1][jj + 1][0] == 257) 
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
						if (mat_img[ii + 1][jj + 1][0] != 257) 
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
						if (mat_img[ii + 1][jj][0] == 257) 
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
						if (mat_img[ii + 1][jj][0] != 257) 
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
						if (mat_img[ii + 1][jj - 1][0] == 257) 
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
						if (mat_img[ii + 1][jj - 1][0] != 257) 
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
						if (mat_img[ii][jj - 1][0] == 257) 
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
						if (mat_img[ii][jj - 1][0] != 257) {
							out = true;
						} else {
							out = false;
						}
					} else {
						out = true;
					}
					if (ii - 1 >= 0 && jj - 1 >= 0) {
						if (mat_img[ii - 1][jj - 1][0] == 257) {
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
						if (mat_img[ii - 1][jj - 1][0] != 257) {
							out = true;
						} else {
							out = false;
						}
					} else {
						out = true;
					}
					if (ii - 1 >= 0) {
						if (mat_img[ii - 1][jj][0] == 257) {
							in = true;
						} else {
							in = false;
						}
					} else {
						in = false;
					}
				}
				break;
				}// fin du switch

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

					/////////////////////////////////////////////////////////////////////////////
					/*
					System.out.println(coord[ind][0] + " _ " +
					coord[ind][1]);
					Thread.sleep(50);
					 */
					////////////////////////////////////////////////////////////////////////////

					iii = 10000;
				}
			} // fin du for

			buf_i = i;

			if (coord[ind][0] == coord[0][0] && coord[ind][1] == coord[0][1]) {
				cond = false;
			}
			if (ind == idim*jdim -1) // debug
			{

				for (int p=0; p<idim*jdim; p++)
				{
					mat_img[coord[p][0]][coord[p][1]][0] = 500;
				}

				// ///////////////////////////////////////// DEBUG ///////////////////////////////////				

				BufferedWriter writer = new BufferedWriter(new FileWriter("tmp.txt"));
				writer.write(Integer.toString(idim));
				writer.newLine();
				writer.flush();
				writer.write(Integer.toString(jdim));
				writer.newLine();
				writer.flush();
				for(int iii=0;iii<idim;iii++)
				{
					for(int jjj=0;jjj<jdim;jjj++)
					{
						writer.write(Integer.toString(mat_img[iii][jjj][0]));
						writer.newLine();
						writer.flush();

					}
				}
				writer.close();
				System.out.println("ok");
				Thread.sleep(17000);

				/////////////////////////////////////////////////////////////////////////////////////////
			}
		} // fin while



		return ind;
	} // fin methode get contour

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
				} // fin du for

				i0 = i1;

			} // fin while sur les droites

			if (buf_dist > err_max_hb) 
			{
				ind_chos[buf_ind] = true;
			} 
			else 
			{
				cond = false;
			}

		} // fin while cond

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

	}// fin methode simplif

	private void suppr_compo_connexe()
	{
		for (int i=0; i<idim; i++)
		{
			for( int j=0; j<jdim; j++)
			{
				if (mat_img[i][j][0] == 257)
				{
					mat_img[i][j][0] = 255;
					mat_img[i][j][1] = 255;
					mat_img[i][j][2] = 255;
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
				if(mat_img[i][j][0] == 257)
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

	private void get_zone()
	{
		int x_min, x_max, y_min, y_max;
		int err_rect, err_ellip;

		x_min = jdim;
		x_max = 0;
		y_min = idim;
		y_max = 0;

		cx = 0;
		cy = 0;

		d_largeur = 0;
		d_hauteur = 0;

		err_rect = 0;
		err_ellip = 0;

		for (int i=0; i<idim; i++)
		{
			for (int j=0; j<jdim; j++)
			{
				if (mat_img[i][j][0] == 257)
				{
					if (i < y_min)
					{
						y_min = i;
					}
					if (i > y_max)
					{
						y_max = i;
					}
					if (j < x_min)
					{
						x_min = j;
					}
					if (j > x_max)
					{
						x_max = j;
					}
				} // fin if compo connexe
			} // fin du for sur j
		} // fin du for sur i

		cx = (double) (x_min + x_max)/2;
		cy = (double) (y_min + y_max)/2;

		d_largeur = (double) (x_max - x_min)/2;
		d_hauteur = (double) (y_max - y_min)/2;
		
		for (int i=y_min; i<y_max; i++)
		{
			for (int j=x_min; j<x_max; j++)
			{
				if (mat_img[i][j][0] < 257)
				{
					err_rect = err_rect  + 1;
				}
				if (is_in(cx, cy, d_hauteur, d_largeur, (double) j, (double) i, 2))
				{
					if (mat_img[i][j][0] < 257)
					{
						err_ellip = err_ellip  + 1;
					}	
				}
				else 
				{
					if (mat_img[i][j][0] == 257)
					{
						err_ellip = err_ellip + 1;
					}
				}
			} // fin du for sur j
		} // fin du for sur i

		System.out.println(err_rect + " _ " + err_ellip);
		
		if (err_rect > err_ellip)
		{
			forme_zone = 2;
		}
		else
		{
			forme_zone = 1;
		}
	} // fin get_zone 

	private boolean is_in(double cx, double cy, double d_hauteur, double d_largeur, double x, double y, int forme)
	{
		double result;

		if (forme == 1)
		{
			if (x > (cx-d_largeur) && x < (cx+d_largeur) && y > (cy-d_hauteur) && cy < (cy+d_hauteur))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			result = ((x-cx)*(x-cx))/(d_largeur*d_largeur)+((y-cy)*(y-cy))/(d_hauteur*d_hauteur);
			if (result <= 1)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
	} // fin is_in
	
	static public double dist2DroiteToPoint(double ax,double ay,double bx,double by,double cx,double cy)
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

}// fin classe
