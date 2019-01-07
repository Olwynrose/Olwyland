
public class Main {

	static Character mainChar;
	static Scenery[] sceneries;
	static Display screen;
	static int nbSceneries;
	static int indScene;
	
	static double gravity = 3.5;
	
	
	static int debug;
	
	
	public static void main(String[] args) throws InterruptedException {
		long tic, toc;
		
		
		mainChar = new Character();
		sceneries = new Scenery[1000];
		nbSceneries = 0;
		
		Debug.testMap(0);
		screen = new Display();
		
		
		
		while(true) {
			tic = System.currentTimeMillis();
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
