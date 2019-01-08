
public class Debug {

	public static void testMap(int num) {
		
		switch(num){
			case 0:
			{
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
			break;
			case 1:
			{
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
	
				Main.nbSceneries = 3;
				Main.sceneries[2] = new Scenery(2);
				
				Main.sceneries[2].setOnePoint(0, 0, 0);
				Main.sceneries[2].setOnePoint(1, 0, 100);
				Main.sceneries[2].type = 2;
				
				Main.sceneries[2].newTrajectory(5);
				Main.sceneries[2].setOneTrajectory(0, 250, 160);
				Main.sceneries[2].setOneTrajectory(1, 200, 170);
				Main.sceneries[2].setOneTrajectory(2, 170, 190);
				Main.sceneries[2].setOneTrajectory(3, 150, 220);
				Main.sceneries[2].setOneTrajectory(4, -50, 700);
				
				Main.nbSceneries = 4;
				Main.sceneries[3] = new Scenery(2);
				
				Main.sceneries[3].setOnePoint(0, 0, 0);
				Main.sceneries[3].setOnePoint(1, 0, 70);
				Main.sceneries[3].type = 2;
				
				Main.sceneries[3].newTrajectory(5);
	
				Main.sceneries[3].setOneTrajectory(0, 200, 50);
				Main.sceneries[3].setOneTrajectory(1, 200, 100);
				Main.sceneries[3].setOneTrajectory(2, 420, 100);
				Main.sceneries[3].setOneTrajectory(3, 420, 50);
				Main.sceneries[3].setOneTrajectory(4, 200, 50);
			}
			break;
			case 2:
			{
				Main.nbSceneries = 1;
				Main.sceneries[0] = new Scenery(6);
				
				Main.sceneries[0].setOnePoint(0, 200, 0);
				Main.sceneries[0].setOnePoint(1, 200, 200);
				Main.sceneries[0].setOnePoint(2, 350, 350);
				Main.sceneries[0].setOnePoint(3, 750, 450);
				Main.sceneries[0].setOnePoint(4, 900, 600);
				Main.sceneries[0].setOnePoint(5, 900, 800);
				//=======================================================
				Main.nbSceneries = 2;
				Main.sceneries[1] = new Scenery(4);
				
				Main.sceneries[1].setOnePoint(0, 975, 820);
				Main.sceneries[1].setOnePoint(1, 975, 900);
				Main.sceneries[1].setOnePoint(2, 1050, 850);
				Main.sceneries[1].setOnePoint(3, 1050, 700);
				//=======================================================
				Main.nbSceneries = 3;
				Main.sceneries[2] = new Scenery(8);
				
				Main.sceneries[2].setOnePoint(0, 450, 450);
				Main.sceneries[2].setOnePoint(1, 450, 500);
				Main.sceneries[2].setOnePoint(2, 500, 500);
				Main.sceneries[2].setOnePoint(3, 500, 600);
				Main.sceneries[2].setOnePoint(4, 550, 600);
				Main.sceneries[2].setOnePoint(5, 550, 725);
				Main.sceneries[2].setOnePoint(6, 600, 750);
				Main.sceneries[2].setOnePoint(7, 600, 850);
				//=======================================================
				Main.nbSceneries = 4;
				Main.sceneries[3] = new Scenery(5);
				
				Main.sceneries[3].setOnePoint(0, 450, 650);
				Main.sceneries[3].setOnePoint(1, 450, 700);
				Main.sceneries[3].setOnePoint(2, 400, 850);
				Main.sceneries[3].setOnePoint(3, 350, 850);
				Main.sceneries[3].setOnePoint(4, 350, 900);
				//=======================================================
				Main.nbSceneries = 5;
				Main.sceneries[4] = new Scenery(3);
				
				Main.sceneries[4].setOnePoint(0, 100, 250);
				Main.sceneries[4].setOnePoint(1, 325, 700);
				Main.sceneries[4].setOnePoint(2, 325, 800);
				//=======================================================
				Main.nbSceneries = 6;
				Main.sceneries[5] = new Scenery(2);
				
				Main.sceneries[5].setOnePoint(0, 700, 0);
				Main.sceneries[5].setOnePoint(1, 700, 200);
				//=======================================================
				Main.nbSceneries = 7;
				Main.sceneries[6] = new Scenery(2);
				
				Main.sceneries[6].setOnePoint(0, 0, 0);
				Main.sceneries[6].setOnePoint(1, 0, 50);
				Main.sceneries[6].type = 1;
				Main.sceneries[6].period = 120;
				Main.sceneries[6].newTrajectory(2);
	
				Main.sceneries[6].setOneTrajectory(0, 1000, 950);
				Main.sceneries[6].setOneTrajectory(1, 850, 950);
				//=======================================================
				Main.nbSceneries = 8;
				Main.sceneries[7] = new Scenery(2);
				
				Main.sceneries[7].setOnePoint(0, 0, 0);
				Main.sceneries[7].setOnePoint(1, 0, 50);
				Main.sceneries[7].type = 1;
				Main.sceneries[7].period = 120;
				Main.sceneries[7].newTrajectory(2);
	
				Main.sceneries[7].setOneTrajectory(0, 750, 1050);
				Main.sceneries[7].setOneTrajectory(1, 900, 1050);
				//=======================================================
				Main.nbSceneries = 9;
				Main.sceneries[8] = new Scenery(2);
				
				Main.sceneries[8].setOnePoint(0, 0, 0);
				Main.sceneries[8].setOnePoint(1, 0, 50);
				Main.sceneries[8].type = 2;
				Main.sceneries[8].period = 120;
				Main.sceneries[8].newTrajectory(4);
	
				Main.sceneries[8].setOneTrajectory(0, 750, 925);
				Main.sceneries[8].setOneTrajectory(1, 750, 975);
				Main.sceneries[8].setOneTrajectory(2, 600, 900);
				Main.sceneries[8].setOneTrajectory(3, 750, 925);
				
				//=======================================================
				Main.nbSceneries = 10;
				Main.sceneries[9] = new Scenery(2);
				
				Main.sceneries[9].setOnePoint(0, 0, 0);
				Main.sceneries[9].setOnePoint(1, 0, 50);
				Main.sceneries[9].type = 2;
				Main.sceneries[9].period = 200;
				Main.sceneries[9].newTrajectory(6);
	
				Main.sceneries[9].setOneTrajectory(0, 1075, 850);
				Main.sceneries[9].setOneTrajectory(1, 1075, 300);
				Main.sceneries[9].setOneTrajectory(2, 850, 300);
				Main.sceneries[9].setOneTrajectory(3, 350, 100);
				Main.sceneries[9].setOneTrajectory(4, 350, -100);
				Main.sceneries[9].setOneTrajectory(5, 0, -100);
				//=======================================================
				Main.nbSceneries = 11;
				Main.sceneries[10] = new Scenery(2);
				
				Main.sceneries[10].setOnePoint(0, 0, 0);
				Main.sceneries[10].setOnePoint(1, 0, 50);
				Main.sceneries[10].type = 2;
				Main.sceneries[10].period = 200;
				Main.sceneries[10].time = 50;
				Main.sceneries[10].newTrajectory(6);
	
				Main.sceneries[10].setOneTrajectory(0, 1075, 850);
				Main.sceneries[10].setOneTrajectory(1, 1075, 300);
				Main.sceneries[10].setOneTrajectory(2, 850, 300);
				Main.sceneries[10].setOneTrajectory(3, 350, 100);
				Main.sceneries[10].setOneTrajectory(4, 350, -100);
				Main.sceneries[10].setOneTrajectory(5, 0, -100);
				//=======================================================
				Main.nbSceneries = 12;
				Main.sceneries[11] = new Scenery(2);
				
				Main.sceneries[11].setOnePoint(0, 0, 0);
				Main.sceneries[11].setOnePoint(1, 0, 50);
				Main.sceneries[11].type = 2;
				Main.sceneries[11].period = 200;
				Main.sceneries[11].time = 100;
				Main.sceneries[11].newTrajectory(6);
	
				Main.sceneries[11].setOneTrajectory(0, 1075, 850);
				Main.sceneries[11].setOneTrajectory(1, 1075, 300);
				Main.sceneries[11].setOneTrajectory(2, 850, 300);
				Main.sceneries[11].setOneTrajectory(3, 350, 100);
				Main.sceneries[11].setOneTrajectory(4, 350, -100);
				Main.sceneries[11].setOneTrajectory(5, 0, -100);
				//=======================================================
				Main.nbSceneries = 13;
				Main.sceneries[12] = new Scenery(2);
				
				Main.sceneries[12].setOnePoint(0, 0, 0);
				Main.sceneries[12].setOnePoint(1, 0, 50);
				Main.sceneries[12].type = 2;
				Main.sceneries[12].period = 200;
				Main.sceneries[12].time = 150;
				Main.sceneries[12].newTrajectory(6);
	
				Main.sceneries[12].setOneTrajectory(0, 1075, 850);
				Main.sceneries[12].setOneTrajectory(1, 1075, 300);
				Main.sceneries[12].setOneTrajectory(2, 850, 300);
				Main.sceneries[12].setOneTrajectory(3, 350, 100);
				Main.sceneries[12].setOneTrajectory(4, 350, -100);
				Main.sceneries[12].setOneTrajectory(5, 0, -100);
			}
			case 3:
			{
				Main.nbSceneries = 1;
				Main.sceneries[0] = new Scenery(6);
				
				Main.sceneries[0].setOnePoint(0, 200, 0);
				Main.sceneries[0].setOnePoint(1, 200, 200);
				Main.sceneries[0].setOnePoint(2, 350, 350);
				Main.sceneries[0].setOnePoint(3, 750, 450);
				Main.sceneries[0].setOnePoint(4, 900, 600);
				Main.sceneries[0].setOnePoint(5, 900, 800);
				//=======================================================
				Main.nbSceneries = 2;
				Main.sceneries[1] = new Scenery(4);
				
				Main.sceneries[1].setOnePoint(0, 975, 820);
				Main.sceneries[1].setOnePoint(1, 975, 900);
				Main.sceneries[1].setOnePoint(2, 1050, 850);
				Main.sceneries[1].setOnePoint(3, 1050, 700);
				//=======================================================
				Main.nbSceneries = 3;
				Main.sceneries[2] = new Scenery(8);
				
				Main.sceneries[2].setOnePoint(0, 450, 450);
				Main.sceneries[2].setOnePoint(1, 450, 500);
				Main.sceneries[2].setOnePoint(2, 500, 500);
				Main.sceneries[2].setOnePoint(3, 500, 600);
				Main.sceneries[2].setOnePoint(4, 550, 600);
				Main.sceneries[2].setOnePoint(5, 550, 725);
				Main.sceneries[2].setOnePoint(6, 600, 750);
				Main.sceneries[2].setOnePoint(7, 600, 850);
				//=======================================================
				Main.nbSceneries = 4;
				Main.sceneries[3] = new Scenery(5);
				
				Main.sceneries[3].setOnePoint(0, 450, 650);
				Main.sceneries[3].setOnePoint(1, 450, 700);
				Main.sceneries[3].setOnePoint(2, 400, 850);
				Main.sceneries[3].setOnePoint(3, 350, 850);
				Main.sceneries[3].setOnePoint(4, 350, 900);
				//=======================================================
				Main.nbSceneries = 5;
				Main.sceneries[4] = new Scenery(3);
				
				Main.sceneries[4].setOnePoint(0, 100, 250);
				Main.sceneries[4].setOnePoint(1, 325, 700);
				Main.sceneries[4].setOnePoint(2, 325, 800);
				//=======================================================
				Main.nbSceneries = 6;
				Main.sceneries[5] = new Scenery(2);
				
				Main.sceneries[5].setOnePoint(0, 700, 0);
				Main.sceneries[5].setOnePoint(1, 700, 200);
				//=======================================================
				Main.nbSceneries = 7;
				Main.sceneries[6] = new Scenery(2);
				
				Main.sceneries[6].setOnePoint(0, 0, 0);
				Main.sceneries[6].setOnePoint(1, 0, 50);
				Main.sceneries[6].type = 1;
				Main.sceneries[6].period = 120;
				Main.sceneries[6].newTrajectory(2);
	
				Main.sceneries[6].setOneTrajectory(0, 1000, 950);
				Main.sceneries[6].setOneTrajectory(1, 850, 950);
				//=======================================================
				Main.nbSceneries = 8;
				Main.sceneries[7] = new Scenery(2);
				
				Main.sceneries[7].setOnePoint(0, 0, 0);
				Main.sceneries[7].setOnePoint(1, 0, 50);
				Main.sceneries[7].type = 1;
				Main.sceneries[7].period = 120;
				Main.sceneries[7].newTrajectory(2);
	
				Main.sceneries[7].setOneTrajectory(0, 750, 1050);
				Main.sceneries[7].setOneTrajectory(1, 900, 1050);
				//=======================================================
				Main.nbSceneries = 9;
				Main.sceneries[8] = new Scenery(2);
				
				Main.sceneries[8].setOnePoint(0, 0, 0);
				Main.sceneries[8].setOnePoint(1, 0, 50);
				Main.sceneries[8].type = 2;
				Main.sceneries[8].period = 120;
				Main.sceneries[8].newTrajectory(4);
	
				Main.sceneries[8].setOneTrajectory(0, 750, 925);
				Main.sceneries[8].setOneTrajectory(1, 750, 975);
				Main.sceneries[8].setOneTrajectory(2, 600, 900);
				Main.sceneries[8].setOneTrajectory(3, 750, 925);
				
				//=======================================================
				Main.nbSceneries = 10;
				Main.sceneries[9] = new Scenery(2);
				
				Main.sceneries[9].setOnePoint(0, 0, 0);
				Main.sceneries[9].setOnePoint(1, 0, 50);
				Main.sceneries[9].type = 2;
				Main.sceneries[9].period = 200;
				Main.sceneries[9].newTrajectory(6);
	
				Main.sceneries[9].setOneTrajectory(0, 1075, 850);
				Main.sceneries[9].setOneTrajectory(1, 1075, 300);
				Main.sceneries[9].setOneTrajectory(2, 850, 300);
				Main.sceneries[9].setOneTrajectory(3, 350, 100);
				Main.sceneries[9].setOneTrajectory(4, 350, -100);
				Main.sceneries[9].setOneTrajectory(5, 0, -100);
				//=======================================================
				Main.nbSceneries = 11;
				Main.sceneries[10] = new Scenery(2);
				
				Main.sceneries[10].setOnePoint(0, 0, 0);
				Main.sceneries[10].setOnePoint(1, 0, 50);
				Main.sceneries[10].type = 2;
				Main.sceneries[10].period = 200;
				Main.sceneries[10].time = 50;
				Main.sceneries[10].newTrajectory(6);
	
				Main.sceneries[10].setOneTrajectory(0, 1075, 850);
				Main.sceneries[10].setOneTrajectory(1, 1075, 300);
				Main.sceneries[10].setOneTrajectory(2, 850, 300);
				Main.sceneries[10].setOneTrajectory(3, 350, 100);
				Main.sceneries[10].setOneTrajectory(4, 350, -100);
				Main.sceneries[10].setOneTrajectory(5, 0, -100);
				//=======================================================
				Main.nbSceneries = 12;
				Main.sceneries[11] = new Scenery(2);
				
				Main.sceneries[11].setOnePoint(0, 0, 0);
				Main.sceneries[11].setOnePoint(1, 0, 50);
				Main.sceneries[11].type = 2;
				Main.sceneries[11].period = 200;
				Main.sceneries[11].time = 100;
				Main.sceneries[11].newTrajectory(6);
	
				Main.sceneries[11].setOneTrajectory(0, 1075, 850);
				Main.sceneries[11].setOneTrajectory(1, 1075, 300);
				Main.sceneries[11].setOneTrajectory(2, 850, 300);
				Main.sceneries[11].setOneTrajectory(3, 350, 100);
				Main.sceneries[11].setOneTrajectory(4, 350, -100);
				Main.sceneries[11].setOneTrajectory(5, 0, -100);
				//=======================================================
				Main.nbSceneries = 13;
				Main.sceneries[12] = new Scenery(2);
				
				Main.sceneries[12].setOnePoint(0, 0, 0);
				Main.sceneries[12].setOnePoint(1, 0, 50);
				Main.sceneries[12].type = 2;
				Main.sceneries[12].period = 200;
				Main.sceneries[12].time = 150;
				Main.sceneries[12].newTrajectory(6);
	
				Main.sceneries[12].setOneTrajectory(0, 1075, 850);
				Main.sceneries[12].setOneTrajectory(1, 1075, 300);
				Main.sceneries[12].setOneTrajectory(2, 850, 300);
				Main.sceneries[12].setOneTrajectory(3, 350, 100);
				Main.sceneries[12].setOneTrajectory(4, 350, -100);
				Main.sceneries[12].setOneTrajectory(5, 0, -100);
				//========================================================
				Main.nbAreas = 1;
				Main.areas[0] = new Area(3, 1300, -800, 3000, 200); 
			}
			break;

			case 4:
			{
				Main.nbSceneries = 1;
				Main.sceneries[0] = new Scenery(6);
				
				Main.sceneries[0].setOnePoint(0, 200, 0);
				Main.sceneries[0].setOnePoint(1, 200, 200);
				Main.sceneries[0].setOnePoint(2, 350, 350);
				Main.sceneries[0].setOnePoint(3, 750, 450);
				Main.sceneries[0].setOnePoint(4, 900, 600);
				Main.sceneries[0].setOnePoint(5, 900, 800);
				//=======================================================
				Main.nbSceneries = 2;
				Main.sceneries[1] = new Scenery(4);
				
				Main.sceneries[1].setOnePoint(0, 975, 820);
				Main.sceneries[1].setOnePoint(1, 975, 900);
				Main.sceneries[1].setOnePoint(2, 1050, 850);
				Main.sceneries[1].setOnePoint(3, 1050, 700);
				//=======================================================
				Main.nbSceneries = 3;
				Main.sceneries[2] = new Scenery(8);
				
				Main.sceneries[2].setOnePoint(0, 450, 450);
				Main.sceneries[2].setOnePoint(1, 450, 500);
				Main.sceneries[2].setOnePoint(2, 500, 500);
				Main.sceneries[2].setOnePoint(3, 500, 600);
				Main.sceneries[2].setOnePoint(4, 550, 600);
				Main.sceneries[2].setOnePoint(5, 550, 725);
				Main.sceneries[2].setOnePoint(6, 600, 750);
				Main.sceneries[2].setOnePoint(7, 600, 850);
				//=======================================================
				Main.nbSceneries = 4;
				Main.sceneries[3] = new Scenery(5);
				
				Main.sceneries[3].setOnePoint(0, 450, 650);
				Main.sceneries[3].setOnePoint(1, 450, 700);
				Main.sceneries[3].setOnePoint(2, 400, 850);
				Main.sceneries[3].setOnePoint(3, 350, 850);
				Main.sceneries[3].setOnePoint(4, 350, 900);
				//=======================================================
				Main.nbSceneries = 5;
				Main.sceneries[4] = new Scenery(3);
				
				Main.sceneries[4].setOnePoint(0, 100, 250);
				Main.sceneries[4].setOnePoint(1, 325, 700);
				Main.sceneries[4].setOnePoint(2, 325, 800);
				//=======================================================
				Main.nbSceneries = 6;
				Main.sceneries[5] = new Scenery(2);
				
				Main.sceneries[5].setOnePoint(0, 700, 0);
				Main.sceneries[5].setOnePoint(1, 700, 200);
				//=======================================================
				Main.nbSceneries = 7;
				Main.sceneries[6] = new Scenery(2);
				
				Main.sceneries[6].setOnePoint(0, 0, 0);
				Main.sceneries[6].setOnePoint(1, 0, 50);
				Main.sceneries[6].type = 1;
				Main.sceneries[6].period = 120;
				Main.sceneries[6].newTrajectory(2);
	
				Main.sceneries[6].setOneTrajectory(0, 1000, 950);
				Main.sceneries[6].setOneTrajectory(1, 850, 950);
				//=======================================================
				Main.nbSceneries = 8;
				Main.sceneries[7] = new Scenery(2);
				
				Main.sceneries[7].setOnePoint(0, 0, 0);
				Main.sceneries[7].setOnePoint(1, 0, 50);
				Main.sceneries[7].type = 1;
				Main.sceneries[7].period = 120;
				Main.sceneries[7].newTrajectory(2);
	
				Main.sceneries[7].setOneTrajectory(0, 750, 1050);
				Main.sceneries[7].setOneTrajectory(1, 900, 1050);
				//=======================================================
				Main.nbSceneries = 9;
				Main.sceneries[8] = new Scenery(2);
				
				Main.sceneries[8].setOnePoint(0, 0, 0);
				Main.sceneries[8].setOnePoint(1, 0, 50);
				Main.sceneries[8].type = 2;
				Main.sceneries[8].period = 120;
				Main.sceneries[8].newTrajectory(4);
	
				Main.sceneries[8].setOneTrajectory(0, 750, 925);
				Main.sceneries[8].setOneTrajectory(1, 750, 975);
				Main.sceneries[8].setOneTrajectory(2, 600, 900);
				Main.sceneries[8].setOneTrajectory(3, 750, 925);
				
				//=======================================================
				Main.nbSceneries = 10;
				Main.sceneries[9] = new Scenery(2);
				
				Main.sceneries[9].setOnePoint(0, 0, 0);
				Main.sceneries[9].setOnePoint(1, 0, 50);
				Main.sceneries[9].type = 2;
				Main.sceneries[9].period = 200;
				Main.sceneries[9].newTrajectory(6);
	
				Main.sceneries[9].setOneTrajectory(0, 1075, 850);
				Main.sceneries[9].setOneTrajectory(1, 1075, 300);
				Main.sceneries[9].setOneTrajectory(2, 850, 300);
				Main.sceneries[9].setOneTrajectory(3, 350, 100);
				Main.sceneries[9].setOneTrajectory(4, 350, -100);
				Main.sceneries[9].setOneTrajectory(5, 0, -100);
				//=======================================================
				Main.nbSceneries = 11;
				Main.sceneries[10] = new Scenery(2);
				
				Main.sceneries[10].setOnePoint(0, 0, 0);
				Main.sceneries[10].setOnePoint(1, 0, 50);
				Main.sceneries[10].type = 2;
				Main.sceneries[10].period = 200;
				Main.sceneries[10].time = 50;
				Main.sceneries[10].newTrajectory(6);
	
				Main.sceneries[10].setOneTrajectory(0, 1075, 850);
				Main.sceneries[10].setOneTrajectory(1, 1075, 300);
				Main.sceneries[10].setOneTrajectory(2, 850, 300);
				Main.sceneries[10].setOneTrajectory(3, 350, 100);
				Main.sceneries[10].setOneTrajectory(4, 350, -100);
				Main.sceneries[10].setOneTrajectory(5, 0, -100);
				//=======================================================
				Main.nbSceneries = 12;
				Main.sceneries[11] = new Scenery(2);
				
				Main.sceneries[11].setOnePoint(0, 0, 0);
				Main.sceneries[11].setOnePoint(1, 0, 50);
				Main.sceneries[11].type = 2;
				Main.sceneries[11].period = 200;
				Main.sceneries[11].time = 100;
				Main.sceneries[11].newTrajectory(6);
	
				Main.sceneries[11].setOneTrajectory(0, 1075, 850);
				Main.sceneries[11].setOneTrajectory(1, 1075, 300);
				Main.sceneries[11].setOneTrajectory(2, 850, 300);
				Main.sceneries[11].setOneTrajectory(3, 350, 100);
				Main.sceneries[11].setOneTrajectory(4, 350, -100);
				Main.sceneries[11].setOneTrajectory(5, 0, -100);
				//=======================================================
				Main.nbSceneries = 13;
				Main.sceneries[12] = new Scenery(2);
				
				Main.sceneries[12].setOnePoint(0, 0, 0);
				Main.sceneries[12].setOnePoint(1, 0, 50);
				Main.sceneries[12].type = 2;
				Main.sceneries[12].period = 200;
				Main.sceneries[12].time = 150;
				Main.sceneries[12].newTrajectory(6);
	
				Main.sceneries[12].setOneTrajectory(0, 1075, 850);
				Main.sceneries[12].setOneTrajectory(1, 1075, 300);
				Main.sceneries[12].setOneTrajectory(2, 850, 300);
				Main.sceneries[12].setOneTrajectory(3, 350, 100);
				Main.sceneries[12].setOneTrajectory(4, 350, -100);
				Main.sceneries[12].setOneTrajectory(5, 0, -100);
				//========================================================
				Main.nbAreas = 1;
				Main.areas[0] = new Area(3, 1300, -800, 3000, 200); 
				//========================================================
				Main.nbAreas = 2;
				Main.areas[1] = new Area(2, 800, 1250, 300, 100);
				//========================================================
				Main.nbSceneries = 14;
				Main.sceneries[13] = new Scenery(6);
				
				Main.sceneries[13].setOnePoint(0, 750, 1150);
				Main.sceneries[13].setOnePoint(1, 750, 1250);
				Main.sceneries[13].setOnePoint(2, 900, 1250);
				Main.sceneries[13].setOnePoint(3, 900, 1550);
				Main.sceneries[13].setOnePoint(4, 750, 1550);
				Main.sceneries[13].setOnePoint(5, 750, 1800);
				//========================================================
				
				//========================================================
				
				//========================================================
				
				//========================================================
			}
			break;
		}
	}
}
