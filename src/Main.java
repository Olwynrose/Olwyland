import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Main {

	static Character mainChar;
	static Display screen;
	static Scenery[] sceneries;
	static int nbSceneries;
	static int indScene;
	
	static Area[] areas;
	static int nbAreas;
	
	static double gravity = 3.5;
	
	// KEYS 
	static boolean keyUp;
	static boolean keyDown;
	static boolean keyRight;
	static boolean keyLeft;
	static boolean keySpace;
	
	static int debug = 0;
	
	
	public static void main(String[] args) throws InterruptedException {
		long tic, toc;
		
		mainChar = new Character();
		sceneries = new Scenery[1000];
		nbSceneries = 0;
		areas = new Area[1000];
		nbAreas = 0;
		
		Debug.testMap(2);
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
			for(indScene=0 ; indScene<nbSceneries ; indScene++){
				sceneries[indScene].update();
			}
			screen.global();
			
			toc = System.currentTimeMillis();
			if (toc - tic < 50)
			{
				Thread.sleep(50 - (toc-tic));
			}
			if (debug == 1) {
				System.out.println(toc-tic);
			}
			
		}		
		
	}
		
}
