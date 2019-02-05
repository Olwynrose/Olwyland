
public class Button {
	String textureFile;
	int[] position;
	int	idim;
	int jdim;
		
	public boolean isIn() {
		if ((int)Main.mouseI > position[0] && (int)Main.mouseI < position[0] + idim && (int)Main.mouseJ > position[1] && (int)Main.mouseJ < position[1] + jdim) {
			return true;
		}
		else {
			return false;
		}
	}
}
