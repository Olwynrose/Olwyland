
public class Debug {

	
	public static void testMap(int num) {
		Main.nbSceneries = 1;
		Main.sceneries[0] = new Scenery(11);
		
		
		Main.sceneries[0].setOnePoint(0, 0, 20);
		Main.sceneries[0].setOnePoint(1, 500, 20);
		Main.sceneries[0].setOnePoint(2, 500, 150);
		Main.sceneries[0].setOnePoint(3, 550, 150);
		Main.sceneries[0].setOnePoint(4, 550, 350);
		Main.sceneries[0].setOnePoint(5, 530, 450);
		
		Main.sceneries[0].setOnePoint(6, 490, 550);
		Main.sceneries[0].setOnePoint(7, 430, 650);
		Main.sceneries[0].setOnePoint(8, 350, 750);
		Main.sceneries[0].setOnePoint(9, 250, 850);
		Main.sceneries[0].setOnePoint(10, 0, 900);
	
		Main.nbSceneries = 2;
		Main.sceneries[1] = new Scenery(9);
		
		Main.sceneries[1].setOnePoint(0, 460, 200);
		Main.sceneries[1].setOnePoint(1, 460, 250);
		Main.sceneries[1].setOnePoint(2, 440, 300);
		Main.sceneries[1].setOnePoint(3, 400, 300);
		Main.sceneries[1].setOnePoint(4, 400, 400);
		Main.sceneries[1].setOnePoint(5, 350, 400);
		Main.sceneries[1].setOnePoint(6, 220, 600);
		Main.sceneries[1].setOnePoint(7, 160, 700);
		Main.sceneries[1].setOnePoint(8, 220, 740);
	}
}
