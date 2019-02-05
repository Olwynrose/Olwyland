
public class ButtonSkill extends Button {
	
	private int indWeapon;
	private int indBranch;
	private int indSkill;
	private boolean isActive;
	private boolean isUnlocked;
	
	public ButtonSkill() {
		this.position = new int[2];
		this.idim = 30;
		this.jdim = 60;
		this.isActive = false;
		this.isUnlocked = false;
	}
	
	public void setIndWeapon(int i) {
		this.indWeapon = i;
	}
	public void setIndBranch(int i) {
		this.indBranch = i;
	}
	public void setIndSkill(int i) {
		this.indSkill = i;
	}
	public void setActivity(boolean b) {
		this.isActive = b;
	}
	public void setState(boolean b) {
		this.isUnlocked = b;
	}
	public int getIndWeapon() {
		return this.indWeapon;
	}
	public int getIndBranch() {
		return this.indBranch;
	}
	public int getIndSkill() {
		return this.indSkill;
	}
	public boolean getActivity() {
		return isActive;
	}
	public boolean getState() {
		return isUnlocked;
	}
}
