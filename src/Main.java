import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

public class Main {

	static Character mainChar;
	static Display screen;
	static Scenery[] sceneries;
	static int nbSceneries;
	static int maxNbSceneries;
	static int indScene;

	static Area[] areas;
	static int nbAreas;
	
	static Mob[] mobs;
	static int maxNbMobs;
	
	static Shot[] friendlyShots;
	static int maxNbShots;

	static Shot[] ennemyShots;
	
	static double gravity = 3.5;

	// KEYS
	static boolean keyUp;
	static boolean keyDown;
	static boolean keyRight;
	static boolean keyLeft;
	static boolean keySpace;
	static boolean key1;
	static boolean key2;
	static boolean key3;
	static boolean key4;
	static boolean key5;
	static boolean key6;
	static boolean key7;
	static boolean key8;


	static boolean[] debug;
	static String hitboxFileImage;
	static String backgroundFileImage;
	static String foregroundFileImage;
	static double rappImage;
	static int interpol;

	public static void main(String[] args) throws InterruptedException, IOException {
		long tic, toc;
		Main.backgroundFileImage = "files/blk.png";
		Main.foregroundFileImage = "";
		debug = new boolean[50];
		//debug[1] = true; 		//print the compute time
		//debug[2] = true;		//print the state of the character
		//debug[3] = true;		//print the cosinus of the angle of the segment the character stands on
		//debug[4] = true;		//print the friction coefficient of the air (defined by the max fall speed)
		//debug[5] = true;		//print the jump speed
		//debug[6] = true;		//print the inactivity time of the left/right/jump keys
		//debug[7] = true;		//activates the fly mode (maintain space bar to fly without caring of the hitboxes)
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


		mainChar = new Character();
		maxNbSceneries = 1000;
		maxNbMobs = 100;
		sceneries = new Scenery[maxNbSceneries];
		nbSceneries = 0;
		areas = new Area[maxNbSceneries];
		nbAreas = 0;
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

		Debug.testMap(6);
		//Map.load(2);
		
		screen = new Display();
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

		while(true) {
			tic = System.currentTimeMillis();

			mainChar.update();
			for(int n=0 ; n<maxNbShots ; n++){
				friendlyShots[n].update();
			}
			for(int n=0 ; n<maxNbMobs ; n++){
				mobs[n].update();
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
	}
}
