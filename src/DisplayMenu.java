import java.awt.Image;

import javax.swing.ImageIcon;

public class DisplayMenu {
	
	private int indMenu; /* 0: options, 1: map, 2: inventory, 3: quests, 4: skill tree */
	private ButtonSkill[][] skillButtons;
	private int maxNbSkills;
	private int nbSkills;
	private Button buttons;
	private int indWeapon;
	
	
	private int[][][] img;
	private int idimWin;
	private int jdimWin;
	
	public DisplayMenu() {		
		indMenu = 4;
		idimWin = Main.screen.idimWin;
		jdimWin = Main.screen.jdimWin;
		img = new int[idimWin][jdimWin][3];
		maxNbSkills = 100;
		indWeapon = 1;
		
		initializeSkillButton();
		
		
	}
	
	public void global() {	
		indWeapon = Main.mainChar.weapon.getIndWeapon();
		
		clickButtonSkill();
		
		background();
		
		switch(indMenu) {
		case 0:
		{
			
		}
		break;
		case 1:
		{
			
		}
		break;
		case 2:
		{
			
		}
		break;
		case 3:
		{
			
		}
		break;
		case 4:
		{
			skillTree();
		}
		break;
		}
		
		for(int i = 0; i < Main.screen.idim ; i++)
		{
			for(int j = 0; j <  Main.screen.jdim ; j++)
			{
				for(int k = 0; k < 3; k++)
				{
					Main.screen.arrayimage[(i*Main.screen.jdim+j)*3+k] = img[i][j][k];
				}

			}
		}

		Main.screen.ii = new ImageIcon(Main.screen.getImageFromArray(Main.screen.arrayimage, Main.screen.jdim, Main.screen.idim).getScaledInstance(jdimWin, idimWin, Image.SCALE_AREA_AVERAGING));
		Main.screen.pan.remove(Main.screen.lab);
		Main.screen.lab.setIcon(Main.screen.ii);
		Main.screen.lab.setBounds(0, 0, jdimWin, idimWin + Main.screen.h);
		Main.screen.pan.add(Main.screen.lab);
		Main.screen.pan.repaint();
		Main.screen.pan.revalidate();
	}
	
	public void background() {
		for(int i = 0 ; i < idimWin ; i++) {
			for(int j = 0 ; j < jdimWin ; j++) {
				img[i][j][0] = 0;
				img[i][j][1] = 0;
				img[i][j][2] = 0;
			}
		}
	}
	
	public void skillTree() {
		int posI;
		int  posJ;
		for (int i = 0 ; i < maxNbSkills ; i++) {
			if (skillButtons[indWeapon][i].getActivity()) {
				posI = skillButtons[indWeapon][i].position[0];
				posJ = skillButtons[indWeapon][i].position[1];
				for (int j = 0 ; j < skillButtons[indWeapon][i].idim ; j++) {
					for (int k = 0 ; k < skillButtons[indWeapon][i].jdim ; k++) {
						if (skillButtons[indWeapon][i].getState()) {
							//is unlocked
							img[posI+j][posJ+k][0] = 255;
							img[posI+j][posJ+k][1] = 255;
							img[posI+j][posJ+k][2] = 0;
						}
						else {
							img[posI+j][posJ+k][0] = 255;
							img[posI+j][posJ+k][1] = 255;
							img[posI+j][posJ+k][2] = 255;
						}
					}
				}
			}
			else {
				break;
			}
		}
	}
	
	private void initializeSkillButton() {
		nbSkills = 0;
		
		skillButtons = new ButtonSkill[Main.mainChar.weapon.getNbWeapon()][maxNbSkills];
		for (int i = 1 ; i < Main.mainChar.weapon.getNbWeapon(); i++) {
			nbSkills = 0;
			for(int j = 0 ; j < Main.mainChar.weapon.skillTrees[i].nbSkillsCC ; j++) {
				skillButtons[i][nbSkills] = new ButtonSkill();
				skillButtons[i][nbSkills].setIndWeapon(i);
				skillButtons[i][nbSkills].setIndBranch(0);
				skillButtons[i][nbSkills].setIndSkill(j);
				skillButtons[i][nbSkills].setActivity(true);
				if(j < Main.mainChar.weapon.skillTrees[i].indCC) {
					skillButtons[i][nbSkills].setState(true);
				}

				skillButtons[i][nbSkills].position[0] = 50 + j * 50;
				skillButtons[i][nbSkills].position[1] = 100;
				
				nbSkills += 1;
			}
			for(int j = 0 ; j < Main.mainChar.weapon.skillTrees[i].nbSkillsFB ; j++) {
				skillButtons[i][nbSkills] = new ButtonSkill();
				skillButtons[i][nbSkills].setIndWeapon(i);
				skillButtons[i][nbSkills].setIndBranch(1);
				skillButtons[i][nbSkills].setIndSkill(j);
				skillButtons[i][nbSkills].setActivity(true);
				if(j < Main.mainChar.weapon.skillTrees[i].indFB) {
					skillButtons[i][nbSkills].setState(true);
				}
				
				skillButtons[i][nbSkills].position[0] = 50 + j * 50;
				skillButtons[i][nbSkills].position[1] = 300;
				
				nbSkills += 1;
			}
			for(int j = 0 ; j < Main.mainChar.weapon.skillTrees[i].nbSkillsSB ; j++) {
				skillButtons[i][nbSkills] = new ButtonSkill();
				skillButtons[i][nbSkills].setIndWeapon(i);
				skillButtons[i][nbSkills].setIndBranch(2);
				skillButtons[i][nbSkills].setIndSkill(j);
				skillButtons[i][nbSkills].setActivity(true);
				if(j < Main.mainChar.weapon.skillTrees[i].indSB) {
					skillButtons[i][nbSkills].setState(true);
				}

				skillButtons[i][nbSkills].position[0] = 50 + j * 50;
				skillButtons[i][nbSkills].position[1] = 500;
				
				nbSkills += 1;
			}
			for(int j = 0 ; j < Main.mainChar.weapon.skillTrees[i].nbSkillsTB ; j++) {
				skillButtons[i][nbSkills] = new ButtonSkill();
				skillButtons[i][nbSkills].setIndWeapon(i);
				skillButtons[i][nbSkills].setIndBranch(3);
				skillButtons[i][nbSkills].setIndSkill(j);
				skillButtons[i][nbSkills].setActivity(true);
				if(j < Main.mainChar.weapon.skillTrees[i].indTB) {
					skillButtons[i][nbSkills].setState(true);
				}
				
				skillButtons[i][nbSkills].position[0] = 50 + j * 50;
				skillButtons[i][nbSkills].position[1] = 700;
				
				nbSkills += 1;
			}
			for(int j = nbSkills ; j < maxNbSkills ; j++) {
				skillButtons[i][j] = new ButtonSkill();
				skillButtons[i][j].setActivity(false);
			}
		}
	}
	
	public void clickButtonSkill() {
		for (int i = 0 ; i < maxNbSkills ; i++) {
			if (skillButtons[indWeapon][i].isIn() && Main.mouseLeft) {
				if (Main.mainChar.weapon.updgrade(indWeapon, skillButtons[indWeapon][i].getIndBranch(), skillButtons[indWeapon][i].getIndSkill())) {
					skillButtons[indWeapon][i].setState(true);
				}
			}
		}
	}
	
}
