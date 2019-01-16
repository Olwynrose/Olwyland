
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
			Im2Hb.getMovingHitbox("files/Olwyland_maptest_trajectory_1.png",
					"files/Olwyland_maptest_hitbox_caisseflottante.png",
					"files/Olwyland_maptest_icone_caisseflottante.png");
			Im2Hb.getArea("files/Olwyland_maptest_areas_1.png");
			Im2Hb.getArea("files/Olwyland_maptest_areas_2.png");

			// generate map hitboxes from images
			Im2Hb.getHitbox("files/Olwyland_maptest_hb_1.png");
		}
		break;
		}
	}
}
