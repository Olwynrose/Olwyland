
public class Map {

	public Map() {
		
	}
	
	public static void load(int indLevel) {
		
		switch (indLevel) {
		case 1:
		{

			Main.mainChar.checkPoint[0] = 430;
			Main.mainChar.checkPoint[1] = 350;
			Main.mainChar.position[0] = Main.mainChar.checkPoint[0];
			Main.mainChar.position[1] = Main.mainChar.checkPoint[1];
			
			Main.rappImage = 1;
			
			// load map image
			Main.backgroundFileImage = "files/Olwyland_maptest_image.png";
			
			// generate map areas from images
			ImageToHitbox Im2Hb = new ImageToHitbox();
			Im2Hb.getArea("files/Olwyland_maptest_areas_1.png");
			Im2Hb.getArea("files/Olwyland_maptest_areas_2.png");

			// generate map hitboxes from images
			Im2Hb.getHitbox("files/Olwyland_maptest_hb_1.png");
			
			Im2Hb.getMovingHitbox("files/Olwyland_maptest_trajectory_1.png",
					"files/Olwyland_maptest_hitbox_caisseflottante.png",
					"files/Olwyland_maptest_icone_caisseflottante.png");
		}
		break;
		case 2:
		{

			Main.mainChar.checkPoint[0] = 0;
			Main.mainChar.checkPoint[1] = 50;
			Main.mainChar.position[0] = Main.mainChar.checkPoint[0];
			Main.mainChar.position[1] = Main.mainChar.checkPoint[1];
			
			Main.rappImage = 1;
			
			// load map image
			Main.backgroundFileImage = "files/map_test2/Olwyland_maptest2_decor.png";
			Main.foregroundFileImage = "files/map_test2/Olwyland_maptest2_foreground.png";
			

			ImageToHitbox Im2Hb = new ImageToHitbox();
			// generate map hitboxes from images
			Im2Hb.getHitbox("files/map_test2/Olwyland_maptest2_hitbox.png");

			System.out.println("hitbox ok");
			
			Im2Hb.getMovingHitbox("files/map_test2/Olwyland_maptest2_trajectoire_caisseflottante1.png",
					"files/map_test2/Olwyland_maptest2_hitbox_caisseflottante1.png",
					"files/map_test2/Olwyland_maptest2_texture_caisseflottante1.png");
				
			System.out.println("hitbox ok");
			// generate map areas from images
			Im2Hb.getArea("files/map_test2/Olwyland_maptest2_zone1.png");
			Im2Hb.getArea("files/map_test2/Olwyland_maptest2_zone2.png");

			
		}
		break;
		case 3:
		{

			Main.mainChar.checkPoint[0] = 2350;
			Main.mainChar.checkPoint[1] = 2000;
			Main.mainChar.position[0] = Main.mainChar.checkPoint[0];
			Main.mainChar.position[1] = Main.mainChar.checkPoint[1];
			
			Main.rappImage = 3;
			
			// load map image
			Main.backgroundFileImage = "files/training_map/Olwyland_training_map_decor.png";
			//Main.foregroundFileImage = "files/map_test2/Olwyland_maptest2_foreground.png";
			

			ImageToHitbox Im2Hb = new ImageToHitbox();
			// generate map hitboxes from images
			Im2Hb.getHitboxLine("files/training_map/Olwyland_training_map_hb2.png");
			Im2Hb.getHitbox("files/training_map/Olwyland_training_map_hb1.png");
			
			System.out.println("hitbox ok");
			
			Im2Hb.getMovingHitbox("files/training_map/Olwyland_training_map_p1_traj.png",
					"files/training_map/Olwyland_training_map_p1_hb.png",
					"files/training_map/Olwyland_training_map_p1_text.png");
			Im2Hb.getMovingHitbox("files/training_map/Olwyland_training_map_p2_traj.png",
					"files/training_map/Olwyland_training_map_p2_hb.png",
					"files/training_map/Olwyland_training_map_p2_text.png");
			Im2Hb.getMovingHitbox("files/training_map/Olwyland_training_map_p3_traj.png",
					"files/training_map/Olwyland_training_map_p3_hb.png",
					"files/training_map/Olwyland_training_map_p3_text.png");
			Im2Hb.getMovingHitbox("files/training_map/Olwyland_training_map_p4_traj.png",
					"files/training_map/Olwyland_training_map_p4_hb.png",
					"files/training_map/Olwyland_training_map_p4_text.png");
			
			System.out.println("hitbox ok");
			// generate map areas from images
			Im2Hb.getArea("files/training_map/Olwyland_training_map_area.png");
	
			
		}
		break;
		case 4:
		{

			Main.mainChar.checkPoint[0] = 4000;//5790;//200;//
			Main.mainChar.checkPoint[1] = 6000;//9000;//150;//
			Main.mainChar.position[0] = Main.mainChar.checkPoint[0];
			Main.mainChar.position[1] = Main.mainChar.checkPoint[1];
			Main.mainChar.charac.maxHp = 250;
			Main.mainChar.charac.hp = 250;
			
			Main.rappImage = 3;
			
			// load map image
			Main.backgroundFileImage = "files/Egouts/Egouts_bg.png";
			//Main.foregroundFileImage = "files/map_test2/Olwyland_maptest2_foreground.png";
			

			ImageToHitbox Im2Hb = new ImageToHitbox();

			Im2Hb.getMovingHitbox("files/Egouts/Egouts_traj1.png",
					"files/Egouts/Egouts_mhb1.png",
					"files/Egouts/Egouts_mtx1.png");
			System.out.println("traj 1 ok");
			Im2Hb.getMovingHitbox("files/Egouts/Egouts_traj2.png",
					"files/Egouts/Egouts_mhb2.png",
					"files/Egouts/Egouts_mtx2.png");
			System.out.println("traj 2 ok");
			Im2Hb.getMovingHitbox("files/Egouts/Egouts_traj3.png",
					"files/Egouts/Egouts_mhb3.png",
					"files/Egouts/Egouts_mtx3.png");
			System.out.println("traj 3 ok");
			Im2Hb.getMovingHitbox("files/Egouts/Egouts_traj4.png",
					"files/Egouts/Egouts_mhb4.png",
					"files/Egouts/Egouts_mtx4.png");
			System.out.println("traj 4 ok");

			
			// generate map areas from images
			Im2Hb.getArea("files/Egouts/Egouts_area2.png");
			Im2Hb.getArea("files/Egouts/Egouts_area1.png");
			// checkpoints
			Main.areas[Main.nbAreas] = new Area(9, 4850, 7520, 100, 100);
			Main.nbAreas = Main.nbAreas + 1;
			System.out.println("areas ok");
			
			// generate map hitboxes from images
			Im2Hb.getHitboxLine("files/Egouts/Egouts_line.png");
			System.out.println("lines ok");
			Im2Hb.getHitbox("files/Egouts/Egouts_hb.png");
			System.out.println("hitbox ok");

			// big void under the map
			Main.areas[Main.nbAreas] = new Area(3, 8000, -5000, 20000, 500);
			Main.nbAreas = Main.nbAreas + 1;
			

			// mobs after the first fall
			double ci = 4000;
			double cj = 150;
			Main.spawns[Main.nbSpawns] = new Spawn(1, 5, 1);
			Main.spawns[Main.nbSpawns].setOnePoint(0, ci, cj);
			Main.spawns[Main.nbSpawns].active = true;
			Main.spawns[Main.nbSpawns].type = 2;
			Main.spawns[Main.nbSpawns].hitMob = true;
			Main.spawns[Main.nbSpawns].idSpawn = Main.nbSpawns;
			Main.nbSpawns = Main.nbSpawns + 1;
			
			Main.spawns[Main.nbSpawns] = new Spawn(1, 30, 6);
			Main.spawns[Main.nbSpawns].setOnePoint(0, ci, cj);
			Main.spawns[Main.nbSpawns].active = true;
			Main.spawns[Main.nbSpawns].idSpawn = Main.nbSpawns;
			Main.nbSpawns = Main.nbSpawns + 1;
			
			// mobs after the water bonus
			ci = 5790;
			cj = 9000;
			Main.spawns[Main.nbSpawns] = new Spawn(1, 6, 2);
			Main.spawns[Main.nbSpawns].setOnePoint(0, ci, cj);
			Main.spawns[Main.nbSpawns].active = true;
			Main.spawns[Main.nbSpawns].type = 2;
			Main.spawns[Main.nbSpawns].hitMob = true;
			Main.spawns[Main.nbSpawns].idSpawn = Main.nbSpawns;
			Main.nbSpawns = Main.nbSpawns + 1;
			
			Main.spawns[Main.nbSpawns] = new Spawn(1, 30, 6);
			Main.spawns[Main.nbSpawns].setOnePoint(0, ci, cj);
			Main.spawns[Main.nbSpawns].active = true;
			Main.spawns[Main.nbSpawns].idSpawn = Main.nbSpawns;
			Main.nbSpawns = Main.nbSpawns + 1;

			ci = 7020;
			cj = 4000;
			Main.spawns[Main.nbSpawns] = new Spawn(7, 50, 8);
			Main.spawns[Main.nbSpawns].setOnePoint(0, ci, cj);
			Main.spawns[Main.nbSpawns].setOnePoint(1, ci, cj+500);
			Main.spawns[Main.nbSpawns].setOnePoint(2, ci, cj+1000);
			Main.spawns[Main.nbSpawns].setOnePoint(3, ci, cj+1500);
			Main.spawns[Main.nbSpawns].setOnePoint(4, ci, cj+2000);
			Main.spawns[Main.nbSpawns].setOnePoint(5, ci, cj+2500);
			Main.spawns[Main.nbSpawns].setOnePoint(6, ci, cj+3000);
			Main.spawns[Main.nbSpawns].active = true;
			Main.spawns[Main.nbSpawns].idSpawn = Main.nbSpawns;
			Main.nbSpawns = Main.nbSpawns + 1;
			
			// mobs in level 2
			ci = 4000;
			cj = 5500;
			Main.spawns[Main.nbSpawns] = new Spawn(4, 10, 4);
			Main.spawns[Main.nbSpawns].setOnePoint(0, ci, cj);
			Main.spawns[Main.nbSpawns].setOnePoint(1, ci, cj+600);
			Main.spawns[Main.nbSpawns].setOnePoint(2, ci, cj+1200);
			Main.spawns[Main.nbSpawns].setOnePoint(3, ci, cj+1800);
			Main.spawns[Main.nbSpawns].type = 2;
			Main.spawns[Main.nbSpawns].active = true;
			Main.spawns[Main.nbSpawns].limJ0 = 5175;
			Main.spawns[Main.nbSpawns].limJ1 = 7440;
			Main.spawns[Main.nbSpawns].idSpawn = Main.nbSpawns;
			Main.nbSpawns = Main.nbSpawns + 1;
			
			ci = 4000;
			cj = 5500;
			Main.spawns[Main.nbSpawns] = new Spawn(4, 30, 9);
			Main.spawns[Main.nbSpawns].setOnePoint(0, ci, cj);
			Main.spawns[Main.nbSpawns].setOnePoint(1, ci, cj+600);
			Main.spawns[Main.nbSpawns].setOnePoint(2, ci, cj+1200);
			Main.spawns[Main.nbSpawns].setOnePoint(3, ci, cj+1800);
			Main.spawns[Main.nbSpawns].type = 1;
			Main.spawns[Main.nbSpawns].active = true;
			Main.spawns[Main.nbSpawns].limJ0 = 5175;
			Main.spawns[Main.nbSpawns].limJ1 = 7440;
			Main.spawns[Main.nbSpawns].idSpawn = Main.nbSpawns;
			Main.nbSpawns = Main.nbSpawns + 1;
			
			// switch in the water
			Main.areas[Main.nbAreas] = new Area(8, 6465, 6990, 70, 100);
			Main.areas[Main.nbAreas].setIndArea(Main.nbAreas + 1);
			Main.nbAreas = Main.nbAreas + 1;
			Main.areas[Main.nbAreas] = new Area(1, 5130, 8310, 300, 900);
			Main.nbAreas = Main.nbAreas + 1;
			
			
			
		}
		break;
		}
	}
}
