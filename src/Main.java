import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;

public class Main {

	static Character mainChar;
	static DisplayGame screen;
	static Scenery[] sceneries;
	static int nbSceneries;
	static int maxNbSceneries;
	static int indScene;

	static Sound sounds;
	
	static Area[] areas;
	static int nbAreas;

	static Spawn[] spawns;
	static int maxNbSpawns;
	static int nbSpawns;
	
	static Mob[] mobs;
	static int maxNbMobs;
	
	static Shot[] friendlyShots;
	static Shot[] ennemyShots;
	static int maxNbShots;

	static Item[] items;
	static int maxNbItems;
	static double gravity = 3.5;

	// KEYS
	static boolean keyUp;
	static boolean keyDown;
	static boolean keyRight;
	static boolean keyLeft;
	static boolean keySpace;
	static boolean keyEscape;
	static boolean key1;
	static boolean key2;
	static boolean key3;
	static boolean key4;
	static boolean key5;
	static boolean key6;
	static boolean key7;
	static boolean key8;
	static boolean changeWeapon;
	
	static boolean mouseLeft;
	static double mouseI;
	static double mouseJ;

	static int menu;
	static DisplayMenu screenMenu;

	static boolean[] debug;
	static String hitboxFileImage;
	static String backgroundFileImage;
	static String foregroundFileImage;
	static double rappImage;

	public static void main(String[] args) throws InterruptedException, IOException {
		long tic, toc;
		Main.backgroundFileImage = "files/blk.png";
		Main.foregroundFileImage = "";
		
		changeWeapon = true;
		
		debug = new boolean[50];
		//debug[1] = true; 		//print the compute time
		//debug[2] = true;		//print the state of the character
		//debug[3] = true;		//print the cosinus of the angle of the segment the character stands on
		//debug[4] = true;		//print the friction coefficient of the air (defined by the max fall speed)
		//debug[5] = true;		//print the jump speed
		//debug[6] = true;		//print the inactivity time of the left/right/jump keys
		debug[7] = true;		//activates the fly mode (maintain space bar to fly without caring of the hitboxes)
		//debug[8] = true;		//print the area the character enters in
		debug[9] = true;		//display the areas
		debug[10] = true;		//display the hitboxes
		//debug[11] = true;		//display character position
		debug[12] = true;		//print the parameters of the detected areas
		//debug[13] = true;		//print 
		debug[14] = true;		//print the parameters of the detected moving hitboxes
		debug[15] = true;		//display the character hitbox
		debug[16] = true;		//display the mobs hitbox
		debug[17] = true;		//display the shots hitbox
		debug[18] = true;		//infinite defense
		debug[19] = true;		//load debug map instead of image map
		//debug[20] = true;		//print the total exp and its progression and the total skill points of the current weapon
		//debug[21] = true;		//print the values and types of the skill trees when loaded
		//debug[22] = true;		//print the values of the weapons' characteristics multipliers of the skill trees
		//debug[23] = true;		//print the munitions of the current weapon
		//debug[24] = true;		//print the number of coins
		//debug[25] = true;		//print informations about upgrading weapons
		
		sounds = new Sound();
		
		mainChar = new Character();
		maxNbSceneries = 1000;
		maxNbMobs = 100;
		sceneries = new Scenery[maxNbSceneries];
		nbSceneries = 0;
		areas = new Area[maxNbSceneries];
		nbAreas = 0;
		maxNbSpawns = 100;
		spawns = new Spawn[maxNbSpawns];
		nbSpawns = 0;
		
		mobs = new Mob[maxNbMobs];
		for(int i = 0; i<maxNbMobs ; i++) {
			mobs[i] = new Mob(0);
		}
		maxNbShots = 500;
		friendlyShots = new Shot[maxNbShots];
		ennemyShots = new Shot[maxNbShots];
		for(int i = 0; i<maxNbShots ; i++) {
			friendlyShots[i] = new Shot();
			ennemyShots[i] = new Shot();
		}
		
		maxNbItems = 300;
		items = new Item[maxNbItems];
		for(int i = 0; i<maxNbItems ; i++) {
			items[i] = new Item();
		}
		
		if (debug[19]) {
			Debug.testMap(6);
		}else {
			Map.load(4);
		}
		
		items[0].activeItem(-400, 800, 3);
		items[1].activeItem(-420, 840, 3);
		items[2].activeItem(220, 800, 3);
		items[3].activeItem(-100, 3700, 3);
		items[4].activeItem(-120, 3750, 3);
		items[5].activeItem(-160, 3300, 3);
		
		screen = new DisplayGame();
		screen.window.addKeyListener(new KeyListener() {
			  public void keyTyped(KeyEvent e) {}
		      public void keyPressed(KeyEvent e) {
		    	  int key = e.getKeyCode();
		    	  screen.updatePressedKeys(key);
		      }
		      public void keyReleased(KeyEvent e) {
		    	  int key = e.getKeyCode();
		    	  screen.updateReleasedKeys(key);
		      }
		});
			
		menu = 1; /* 0:homepage, 1:game, 2:pause */
		screenMenu = new DisplayMenu();
		
		screen.window.addMouseListener( new MouseListener() 
		{
			@Override
			public void mouseClicked(java.awt.event.MouseEvent arg0) {
			}
			@Override
			public void mouseEntered(java.awt.event.MouseEvent arg0) {}
			@Override
			public void mouseExited(java.awt.event.MouseEvent arg0) {}
			@Override
			public void mousePressed(java.awt.event.MouseEvent arg0) {
				screen.updatePressedMouse(arg0);
			}
			@Override
			public void mouseReleased(java.awt.event.MouseEvent arg0) {
				mouseLeft = false;
	  	  		Main.mainChar.weapon.setKeyShot(true);
			}
		} );
		screen.window.addMouseMotionListener( new MouseMotionAdapter() 
		{
			public void mouseDragged(java.awt.event.MouseEvent e )
			{
				screen.updatePressedMouse(e);
			}
		} );

		while(true) {
			if (keyEscape && menu == 1) {
				keyEscape = false;
				screen.resetTrans();
				menu = 2;
			}
			else if (keyEscape && menu == 2) {
				keyEscape = false;
				menu = 1;
				screen.centerChar();
			}
			switch(menu)
			{
				case 1:
				{
					tic = System.currentTimeMillis();
					
					mainChar.update();
					for(int n=0 ; n<maxNbShots ; n++){
						friendlyShots[n].update();
						ennemyShots[n].update();
					}
					for(int n=0 ; n<maxNbMobs ; n++){
						mobs[n].update();
					}
					for(int n=0 ; n<nbSpawns ; n++){
						spawns[n].update();
					}
					for(int n=0 ; n<maxNbItems ; n++){
						items[n].update();
					}
					for(indScene=0 ; indScene<nbSceneries ; indScene++){
						sceneries[indScene].update();
					}
					
					screen.global();
		
					toc = System.currentTimeMillis();
					if (toc - tic < 50)
					{
						Thread.sleep(50 - (toc-tic));
					}
					if (debug[1]) {
						System.out.println("Computing time : " + (toc-tic) + "   [Main]");
					}
				}
				break;
				case 2:
				{
					screenMenu.global();
					
				}
				break;
			}
		}
	}
}
