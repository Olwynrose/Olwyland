import java.io.IOException;

public class Debug {

	public static void testMap(int num) throws InterruptedException, IOException {

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
				Main.sceneries[2].typeMove = 2;

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
				Main.sceneries[3].typeMove = 2;

				Main.sceneries[3].newTrajectory(5);

				Main.sceneries[3].setOneTrajectory(0, 200, 50);
				Main.sceneries[3].setOneTrajectory(1, 200, 100);
				Main.sceneries[3].setOneTrajectory(2, 420, 100);
				Main.sceneries[3].setOneTrajectory(3, 420, 50);
				Main.sceneries[3].setOneTrajectory(4, 200, 50);
				Main.sceneries[3].setImage("files/2.png");
				Main.sceneries[3].transi = 40;
				Main.sceneries[3].transj = 70;

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
				Main.sceneries[6].typeMove = 1;
				Main.sceneries[6].period = 120;
				Main.sceneries[6].newTrajectory(2);

				Main.sceneries[6].setOneTrajectory(0, 1000, 950);
				Main.sceneries[6].setOneTrajectory(1, 850, 950);
				//=======================================================
				Main.nbSceneries = 8;
				Main.sceneries[7] = new Scenery(2);

				Main.sceneries[7].setOnePoint(0, 0, 0);
				Main.sceneries[7].setOnePoint(1, 0, 50);
				Main.sceneries[7].typeMove = 1;
				Main.sceneries[7].period = 120;
				Main.sceneries[7].newTrajectory(2);

				Main.sceneries[7].setOneTrajectory(0, 750, 1050);
				Main.sceneries[7].setOneTrajectory(1, 900, 1050);
				//=======================================================
				Main.nbSceneries = 9;
				Main.sceneries[8] = new Scenery(2);

				Main.sceneries[8].setOnePoint(0, 0, 0);
				Main.sceneries[8].setOnePoint(1, 0, 50);
				Main.sceneries[8].typeMove = 2;
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
				Main.sceneries[9].typeMove = 2;
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
				Main.sceneries[10].typeMove = 2;
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
				Main.sceneries[11].typeMove = 2;
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
				Main.sceneries[12].typeMove = 2;
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
				Main.sceneries[6].typeMove = 1;
				Main.sceneries[6].period = 120;
				Main.sceneries[6].newTrajectory(2);

				Main.sceneries[6].setOneTrajectory(0, 1000, 950);
				Main.sceneries[6].setOneTrajectory(1, 850, 950);
				//=======================================================
				Main.nbSceneries = 8;
				Main.sceneries[7] = new Scenery(2);

				Main.sceneries[7].setOnePoint(0, 0, 0);
				Main.sceneries[7].setOnePoint(1, 0, 50);
				Main.sceneries[7].typeMove = 1;
				Main.sceneries[7].period = 120;
				Main.sceneries[7].newTrajectory(2);

				Main.sceneries[7].setOneTrajectory(0, 750, 1050);
				Main.sceneries[7].setOneTrajectory(1, 900, 1050);
				//=======================================================
				Main.nbSceneries = 9;
				Main.sceneries[8] = new Scenery(2);

				Main.sceneries[8].setOnePoint(0, 0, 0);
				Main.sceneries[8].setOnePoint(1, 0, 50);
				Main.sceneries[8].typeMove = 2;
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
				Main.sceneries[9].typeMove = 2;
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
				Main.sceneries[10].typeMove = 2;
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
				Main.sceneries[11].typeMove = 2;
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
				Main.sceneries[12].typeMove = 2;
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
				Main.sceneries[6].typeMove = 1;
				Main.sceneries[6].period = 120;
				Main.sceneries[6].newTrajectory(2);

				Main.sceneries[6].setOneTrajectory(0, 1000, 950);
				Main.sceneries[6].setOneTrajectory(1, 850, 950);
				//=======================================================
				Main.nbSceneries = 8;
				Main.sceneries[7] = new Scenery(2);

				Main.sceneries[7].setOnePoint(0, 0, 0);
				Main.sceneries[7].setOnePoint(1, 0, 50);
				Main.sceneries[7].typeMove = 1;
				Main.sceneries[7].period = 120;
				Main.sceneries[7].newTrajectory(2);

				Main.sceneries[7].setOneTrajectory(0, 750, 1050);
				Main.sceneries[7].setOneTrajectory(1, 900, 1050);
				//=======================================================
				Main.nbSceneries = 9;
				Main.sceneries[8] = new Scenery(2);

				Main.sceneries[8].setOnePoint(0, 0, 0);
				Main.sceneries[8].setOnePoint(1, 0, 50);
				Main.sceneries[8].typeMove = 2;
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
				Main.sceneries[9].typeMove = 2;
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
				Main.sceneries[10].typeMove = 2;
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
				Main.sceneries[11].typeMove = 2;
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
				Main.sceneries[12].typeMove = 2;
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
			}
			break;
			case 5:
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
				Main.sceneries[6].typeMove = 1;
				Main.sceneries[6].period = 120;
				Main.sceneries[6].newTrajectory(2);

				Main.sceneries[6].setOneTrajectory(0, 1000, 950);
				Main.sceneries[6].setOneTrajectory(1, 850, 950);
				//=======================================================
				Main.nbSceneries = 8;
				Main.sceneries[7] = new Scenery(2);

				Main.sceneries[7].setOnePoint(0, 0, 0);
				Main.sceneries[7].setOnePoint(1, 0, 50);
				Main.sceneries[7].typeMove = 1;
				Main.sceneries[7].period = 120;
				Main.sceneries[7].newTrajectory(2);

				Main.sceneries[7].setOneTrajectory(0, 750, 1050);
				Main.sceneries[7].setOneTrajectory(1, 900, 1050);
				//=======================================================
				Main.nbSceneries = 9;
				Main.sceneries[8] = new Scenery(2);

				Main.sceneries[8].setOnePoint(0, 0, 0);
				Main.sceneries[8].setOnePoint(1, 0, 50);
				Main.sceneries[8].typeMove = 2;
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
				Main.sceneries[9].typeMove = 2;
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
				Main.sceneries[10].typeMove = 2;
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
				Main.sceneries[11].typeMove = 2;
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
				Main.sceneries[12].typeMove = 2;
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
				Main.areas[0] = new Area(3, 1300, -2000, 5000, 200);
				//========================================================
				Main.nbAreas = 2;
				Main.areas[1] = new Area(2, 770, 1250, 300, 130);
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
				Main.nbAreas = 3;
				Main.areas[2] = new Area(5, 100, 30, 50, 100);
				Main.areas[2].setIndTp(3);
				//========================================================
				Main.nbAreas = 4;
				Main.areas[3] = new Area(0, 550, 1170, 50, 100);
				Main.areas[3].setIndTp(2);
				//========================================================
				Main.nbAreas = (5);
				Main.areas[4] = new Area(5, 650, 1600, 50, 100);
				Main.areas[4].setIndTp(2);
				//========================================================
				Main.nbAreas = (6);
				Main.areas[5] = new Area(6, 1000, -300, 100, 50);
				Main.areas[5].setIndTp(3);
				Main.areas[5].setSpeedMultTp(-3);
				//========================================================
				Main.nbSceneries = 15;
				Main.sceneries[14] = new Scenery(2);

				Main.sceneries[14].setOnePoint(0, 720, 1315);
				Main.sceneries[14].setOnePoint(1, 720, 1330);
				//========================================================
				Main.nbSceneries = 16;
				Main.sceneries[15] = new Scenery(2);
				Main.sceneries[15].setOnePoint(0, 0, 0);
				Main.sceneries[15].setOnePoint(1, 0, 90);

				Main.sceneries[15].typeMove = 1;
				Main.sceneries[15].period = 100;
				Main.sceneries[15].time = 0;

				Main.sceneries[15].newTrajectory(2);
				Main.sceneries[15].setOneTrajectory(0, 750, 1400);
				Main.sceneries[15].setOneTrajectory(1, 825, 1400);
				//========================================================
				Main.nbAreas = 7;
				Main.areas[6] = new Area(6, 600, 1000, 100, 50);
				Main.areas[6].setIndTp(7);
				Main.areas[6].setSpeedMultTp(1);
				Main.nbAreas = 8;
				Main.areas[7] = new Area(0, 100, 1000, 100, 50);
				Main.areas[7].setIndTp(6);
				Main.areas[7].setSpeedMultTp(-3);

			}
			break;
			case 6:
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
				Main.sceneries[6].typeMove = 1;
				Main.sceneries[6].period = 120;
				Main.sceneries[6].newTrajectory(2);

				Main.sceneries[6].setOneTrajectory(0, 1000, 950);
				Main.sceneries[6].setOneTrajectory(1, 850, 950);
				//=======================================================
				Main.nbSceneries = 8;
				Main.sceneries[7] = new Scenery(2);

				Main.sceneries[7].setOnePoint(0, 0, 0);
				Main.sceneries[7].setOnePoint(1, 0, 50);
				Main.sceneries[7].typeMove = 1;
				Main.sceneries[7].period = 120;
				Main.sceneries[7].newTrajectory(2);

				Main.sceneries[7].setOneTrajectory(0, 750, 1050);
				Main.sceneries[7].setOneTrajectory(1, 900, 1050);
				//=======================================================
				Main.nbSceneries = 9;
				Main.sceneries[8] = new Scenery(2);

				Main.sceneries[8].setOnePoint(0, 0, 0);
				Main.sceneries[8].setOnePoint(1, 0, 50);
				Main.sceneries[8].typeMove = 2;
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
				Main.sceneries[9].typeMove = 2;
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
				Main.sceneries[10].typeMove = 2;
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
				Main.sceneries[11].typeMove = 2;
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
				Main.sceneries[12].typeMove = 2;
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
				Main.areas[0] = new Area(3, 1300, -5000, 15000, 200);
				//========================================================
				Main.nbAreas = 2;
				Main.areas[1] = new Area(2, 770, 1250, 300, 130);
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
				Main.nbAreas = 3;
				Main.areas[2] = new Area(5, 100, 30, 50, 100);
				Main.areas[2].setIndTp(3);
				//========================================================
				Main.nbAreas = 4;
				Main.areas[3] = new Area(0, 550, 1170, 50, 100);
				Main.areas[3].setIndTp(2);
				//========================================================
				Main.nbAreas = 5;
				Main.areas[4] = new Area(5, 650, 1600, 50, 100);
				Main.areas[4].setIndTp(2);
				//========================================================
				Main.nbAreas = 6;
				Main.areas[5] = new Area(6, 1000, -300, 100, 50);
				Main.areas[5].setIndTp(3);
				Main.areas[5].setSpeedMultTp(-3);
				//========================================================
				Main.nbSceneries = 15;
				Main.sceneries[14] = new Scenery(2);
				Main.sceneries[14].typeMove = 3;

				Main.sceneries[14].setOnePoint(0, 720, 1310);
				Main.sceneries[14].setOnePoint(1, 720, 1340);
				//========================================================
				Main.nbSceneries = 16;
				Main.sceneries[15] = new Scenery(2);
				Main.sceneries[15].setOnePoint(0, 0, 0);
				Main.sceneries[15].setOnePoint(1, 0, 90);

				Main.sceneries[15].typeMove = 1;
				Main.sceneries[15].period = 100;
				Main.sceneries[15].time = 0;

				Main.sceneries[15].newTrajectory(2);
				Main.sceneries[15].setOneTrajectory(0, 750, 1400);
				Main.sceneries[15].setOneTrajectory(1, 825, 1400);
				//========================================================
				Main.nbAreas = 7;
				Main.areas[6] = new Area(6, 1000, 1100, 100, 50);
				Main.areas[6].setIndTp(7);
				Main.areas[6].setSpeedMultTp(1);
				Main.nbAreas = 8;
				Main.areas[7] = new Area(0, 200, 3000, 0, 0);
				Main.areas[7].setIndTp(6);
				Main.areas[7].setSpeedMultTp(0);

				//========================================================
				Main.nbAreas = 9;
				Main.areas[8] = new Area(4, 200, 1800, 50, 1000);
				//========================================================
				Main.nbSceneries = 17;
				Main.sceneries[16] = new Scenery(7);
				Main.sceneries[16].setOnePoint(0, 1000, 1850);
				Main.sceneries[16].setOnePoint(1, 300, 1850);
				Main.sceneries[16].setOnePoint(2, 300, 2100);
				Main.sceneries[16].setOnePoint(3, 800, 2100);
				Main.sceneries[16].setOnePoint(4, 800, 3000);
				Main.sceneries[16].setOnePoint(5, 280, 3000);
				Main.sceneries[16].setOnePoint(6, 280, 3100);
				//========================================================
				Main.nbSceneries = 18;
				Main.sceneries[17] = new Scenery(6);
				Main.sceneries[17].setOnePoint(0, 0, 2500);
				Main.sceneries[17].setOnePoint(1, 700, 2500);
				Main.sceneries[17].setOnePoint(2, 700, 2900);
				Main.sceneries[17].setOnePoint(3, 150, 2900);
				Main.sceneries[17].setOnePoint(4, 150, 2700);
				Main.sceneries[17].setOnePoint(5, -700, 2700);
				//========================================================
				Main.nbAreas = 10;
				Main.areas[9] = new Area(1, 315, 2100, 900, 495);
				//========================================================
				Main.nbSceneries = 19;
				Main.sceneries[18] = new Scenery(2);
				Main.sceneries[18].setOnePoint(0, 300, 2330);
				Main.sceneries[18].setOnePoint(1, 285, 2390);
				Main.sceneries[18].type = 2;
				Main.sceneries[18].typeMove = 1;
				Main.sceneries[18].period = 100;
				Main.sceneries[18].time = 0;

				Main.sceneries[18].newTrajectory(2);
				Main.sceneries[18].setOneTrajectory(0, 0, 0);
				Main.sceneries[18].setOneTrajectory(1, 10, 0);
				//========================================================
				Main.nbSceneries = 20;
				Main.sceneries[19] = new Scenery(2);
				Main.sceneries[19].setOnePoint(0, 260, 2400);
				Main.sceneries[19].setOnePoint(1, 260, 2500);
				Main.sceneries[19].type = 2;
				//========================================================
				Main.sceneries[Main.nbSceneries] = new Scenery(2);
				Main.sceneries[Main.nbSceneries].setOnePoint(0, 0, 0);
				Main.sceneries[Main.nbSceneries].setOnePoint(1, -10, 50);

				Main.sceneries[Main.nbSceneries].type = 2;
				Main.sceneries[Main.nbSceneries].typeMove = 1;
				Main.sceneries[Main.nbSceneries].period = 100;
				Main.sceneries[Main.nbSceneries].time = 0;

				Main.sceneries[Main.nbSceneries].newTrajectory(2);
				Main.sceneries[Main.nbSceneries].setOneTrajectory(0, 300, 2900);
				Main.sceneries[Main.nbSceneries].setOneTrajectory(1, 320, 2900);
				Main.nbSceneries = Main.nbSceneries + 1;
				
				//========================================================
				Main.areas[Main.nbAreas] = new Area(7, 260, 3050, 50, 20);
				Main.nbAreas = Main.nbAreas + 1;
				//========================================================
				Main.areas[Main.nbAreas] = new Area(9, 100, 2750, 50, 50);
				Main.nbAreas = Main.nbAreas + 1;

				//========================================================
				Main.areas[Main.nbAreas] = new Area(9, 150, 100, 50, 50);
				Main.nbAreas = Main.nbAreas + 1;
				//========================================================
				Main.sceneries[Main.nbSceneries] = new Scenery(5);
				Main.sceneries[Main.nbSceneries].setOnePoint(0, -50, 3150);
				Main.sceneries[Main.nbSceneries].setOnePoint(1, -50, 3800);
				Main.sceneries[Main.nbSceneries].setOnePoint(2, -250, 3800);
				Main.sceneries[Main.nbSceneries].setOnePoint(3, -250, 3500);
				Main.sceneries[Main.nbSceneries].setOnePoint(4, -150, 3500);
				Main.nbSceneries = Main.nbSceneries + 1;
				//========================================================
				Main.sceneries[Main.nbSceneries] = new Scenery(2);
				Main.sceneries[Main.nbSceneries].setOnePoint(0, -50, 3500);
				Main.sceneries[Main.nbSceneries].setOnePoint(1, -150, 3500);
				Main.areas[Main.nbAreas] = new Area(8, -100, 3250, 50, 50);
				Main.areas[Main.nbAreas].setIndHB(Main.nbSceneries);
				Main.nbAreas = Main.nbAreas + 1;
				Main.areas[Main.nbAreas] = new Area(8, -100, 3550, 50, 50);
				Main.areas[Main.nbAreas].setIndHB(Main.nbSceneries);
				Main.nbAreas = Main.nbAreas + 1;

				Main.nbSceneries = Main.nbSceneries + 1;
				//========================================================
				Main.areas[Main.nbAreas] = new Area(1, 50, 500, 0, 0);
				Main.areas[Main.nbAreas].setEllipse(200, 70, -1.1);
				Main.nbAreas = Main.nbAreas + 1;
				
				/* Mobs */
				
				double ci = -100;
				double cj = 3550;
				int indmob, buf_ind;
				for(indmob = 0; indmob < 20 ; indmob++) {
					Main.mobs[indmob] = new Mob(1);
					Main.mobs[indmob].checkPoint[0] = ci;
					Main.mobs[indmob].checkPoint[1] = cj;
					Main.mobs[indmob].position[0] = ci;
					Main.mobs[indmob].position[1] = cj;
				}

				
				
				/* Arena */
				
				Main.areas[Main.nbAreas] = new Area(4, -200, -50, 50, 300);
				Main.areas[Main.nbAreas].setIndHB(Main.nbSceneries);
				Main.nbAreas = Main.nbAreas + 1;
				
				Main.sceneries[Main.nbSceneries] = new Scenery(5);
				Main.sceneries[Main.nbSceneries].setOnePoint(0, -160, 0);
				Main.sceneries[Main.nbSceneries].setOnePoint(1, -300, 600);
				Main.sceneries[Main.nbSceneries].setOnePoint(2, -200, 600);
				Main.sceneries[Main.nbSceneries].setOnePoint(3, -200, 2600);
				Main.sceneries[Main.nbSceneries].setOnePoint(4, -600, 2600);
				Main.nbSceneries = Main.nbSceneries + 1;

				Main.sceneries[Main.nbSceneries] = new Scenery(4);
				Main.sceneries[Main.nbSceneries].setOnePoint(0, 100, 0);
				Main.sceneries[Main.nbSceneries].setOnePoint(1, 0, 0);
				Main.sceneries[Main.nbSceneries].setOnePoint(2, 0, 100);
				Main.sceneries[Main.nbSceneries].setOnePoint(3, 100, 100);

				Main.sceneries[Main.nbSceneries].type = 1;
				Main.sceneries[Main.nbSceneries].typeMove = 1;
				Main.sceneries[Main.nbSceneries].period = 100;
				Main.sceneries[Main.nbSceneries].time = 0;

				Main.sceneries[Main.nbSceneries].newTrajectory(2);
				Main.sceneries[Main.nbSceneries].setOneTrajectory(0, -300, 800);
				Main.sceneries[Main.nbSceneries].setOneTrajectory(1, -200, 800);
				Main.nbSceneries = Main.nbSceneries + 1;
				
				Main.sceneries[Main.nbSceneries] = new Scenery(4);
				Main.sceneries[Main.nbSceneries].setOnePoint(0, 100, 0);
				Main.sceneries[Main.nbSceneries].setOnePoint(1, 0, 0);
				Main.sceneries[Main.nbSceneries].setOnePoint(2, 0, 100);
				Main.sceneries[Main.nbSceneries].setOnePoint(3, 100, 100);

				Main.sceneries[Main.nbSceneries].type = 1;
				Main.sceneries[Main.nbSceneries].typeMove = 1;
				Main.sceneries[Main.nbSceneries].period = 100;
				Main.sceneries[Main.nbSceneries].time = 0;

				Main.sceneries[Main.nbSceneries].newTrajectory(2);
				Main.sceneries[Main.nbSceneries].setOneTrajectory(0, -200, 1200);
				Main.sceneries[Main.nbSceneries].setOneTrajectory(1, -300, 1200);
				Main.nbSceneries = Main.nbSceneries + 1;

				ci = -202;
				cj = 1600;
				Main.mobs[indmob] = new Mob(2);
				Main.mobs[indmob].checkPoint[0] = ci;
				Main.mobs[indmob].checkPoint[1] = cj;
				Main.mobs[indmob].position[0] = Main.mobs[indmob].checkPoint[0];
				Main.mobs[indmob].position[1] = Main.mobs[indmob].checkPoint[1];
				buf_ind = indmob + 1;
				for(indmob=buf_ind; indmob < buf_ind+5 ; indmob++) {
					Main.mobs[indmob] = new Mob(1);
					Main.mobs[indmob].checkPoint[0] = ci;
					Main.mobs[indmob].checkPoint[1] = cj;
					Main.mobs[indmob].position[0] = ci;
					Main.mobs[indmob].position[1] = cj;
				}
				ci = -202;
				cj = 1000;
				buf_ind = indmob;
				for(indmob=buf_ind; indmob < buf_ind+5 ; indmob++) {
					Main.mobs[indmob] = new Mob(1);
					Main.mobs[indmob].checkPoint[0] = ci;
					Main.mobs[indmob].checkPoint[1] = cj;
					Main.mobs[indmob].position[0] = ci;
					Main.mobs[indmob].position[1] = cj;
				}
				ci = -202;
				cj = 2200;
				buf_ind = indmob;
				for(indmob=buf_ind; indmob < buf_ind+5 ; indmob++) {
					Main.mobs[indmob] = new Mob(1);
					Main.mobs[indmob].checkPoint[0] = ci;
					Main.mobs[indmob].checkPoint[1] = cj;
					Main.mobs[indmob].position[0] = ci;
					Main.mobs[indmob].position[1] = cj;
				}
			}
			break;
			case 7:
			{
				Main.hitboxFileImage = "files/HB_Egouts_dessin1.png";
				Main.backgroundFileImage = "files/HB_Egouts_dessin1.png";
				Main.rappImage = 2;
				new ImageToHitbox();
				Main.mainChar.checkPoint[0] = 620;
				Main.mainChar.checkPoint[1] = 7770;
				Main.mainChar.position[0] = Main.mainChar.checkPoint[0];
				Main.mainChar.position[1] = Main.mainChar.checkPoint[1];


				Main.areas[Main.nbAreas] = new Area(1, 5072, 876, 4000-876,5465-5072);
				Main.nbAreas = Main.nbAreas + 1;

				//========================================================
				Main.sceneries[Main.nbSceneries] = new Scenery(2);
				Main.sceneries[Main.nbSceneries].setOnePoint(0, 0, 0);
				Main.sceneries[Main.nbSceneries].setOnePoint(1, 0, 50);
				Main.sceneries[Main.nbSceneries].type = 2;

				Main.nbSceneries = Main.nbSceneries + 1;
				//========================================================
				Main.sceneries[Main.nbSceneries] = new Scenery(2);
				Main.sceneries[Main.nbSceneries].setOnePoint(0, 200, 2400);
				Main.sceneries[Main.nbSceneries].setOnePoint(1, 200, 2500);
				Main.sceneries[Main.nbSceneries].type = 2;
				Main.sceneries[Main.nbSceneries].typeMove = 1;
				Main.sceneries[Main.nbSceneries].period = 100;
				Main.sceneries[Main.nbSceneries].time = 0;

				Main.sceneries[Main.nbSceneries].newTrajectory(2);
				Main.sceneries[Main.nbSceneries].setOneTrajectory(0, 1310, 1230);
				Main.sceneries[Main.nbSceneries].setOneTrajectory(1, 1310, 1820);

				Main.nbSceneries = Main.nbSceneries + 1;
				//========================================================
				Main.sceneries[Main.nbSceneries] = new Scenery(2);
				Main.sceneries[Main.nbSceneries].setOnePoint(0, 1100, 5700);
				Main.sceneries[Main.nbSceneries].setOnePoint(1, 1100, 5760);
				Main.sceneries[Main.nbSceneries].type = 3;
				Main.sceneries[Main.nbSceneries].typeMove = 3;
				Main.sceneries[Main.nbSceneries].period = 200;
				Main.sceneries[Main.nbSceneries].time = 0;
				Main.nbSceneries = Main.nbSceneries + 1;
			}
			break;
		}
	}
}
