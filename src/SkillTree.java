import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class SkillTree {
	
	private Skill[] CommonCore;
	private int nbSkillsCC;
	private Skill[] FirstBranch;
	private int nbSkillsFB;
	private Skill[] SecondBranch;
	private int nbSkillsSB;
	private Skill[] ThirdBranch;
	private int nbSkillsTB;
	
	public int indCC;
	public int indFB;
	public int indSB;
	public int indTB;
	
	public SkillTree(String skillTreeName) throws NumberFormatException, IOException {
		File fileCC = new File("files/Data/Weapons/SkillTree/"+ skillTreeName +"/CommonCore.txt");
		BufferedReader brCC = new BufferedReader(new FileReader(fileCC));
		File fileFB = new File("files/Data/Weapons/SkillTree/"+ skillTreeName +"/FirstBranch.txt");
		BufferedReader brFB = new BufferedReader(new FileReader(fileFB));
		File fileSB = new File("files/Data/Weapons/SkillTree/"+ skillTreeName +"/SecondBranch.txt");
		BufferedReader brSB = new BufferedReader(new FileReader(fileSB));
		File fileTB = new File("files/Data/Weapons/SkillTree/"+ skillTreeName +"/ThirdBranch.txt");
		BufferedReader brTB = new BufferedReader(new FileReader(fileTB));
		
		this.nbSkillsCC = Integer.parseInt(brCC.readLine());
		this.nbSkillsFB = Integer.parseInt(brFB.readLine());
		this.nbSkillsSB = Integer.parseInt(brSB.readLine());
		this.nbSkillsTB = Integer.parseInt(brTB.readLine());
		
		CommonCore = new Skill[nbSkillsCC];
		FirstBranch = new Skill[nbSkillsFB];
		SecondBranch = new Skill[nbSkillsSB];
		ThirdBranch = new Skill[nbSkillsTB];
		
		this.indCC = 0;
		this.indFB = 0;
		this.indSB = 0;
		this.indTB = 0;
		
		for (int i = 0 ; i < nbSkillsCC ; i++) {
			addSkill(0, Integer.parseInt(brCC.readLine()), Double.parseDouble(brCC.readLine()), Integer.parseInt(brCC.readLine()), i);
		}
		for (int i = 0 ; i < nbSkillsFB ; i++) {
			addSkill(1, Integer.parseInt(brFB.readLine()), Double.parseDouble(brFB.readLine()), Integer.parseInt(brFB.readLine()), i);
		}
		for (int i = 0 ; i < nbSkillsSB ; i++) {
			addSkill(2, Integer.parseInt(brSB.readLine()), Double.parseDouble(brSB.readLine()), Integer.parseInt(brSB.readLine()), i);
		}
		for (int i = 0 ; i < nbSkillsTB ; i++) {
			addSkill(3, Integer.parseInt(brTB.readLine()), Double.parseDouble(brTB.readLine()), Integer.parseInt(brTB.readLine()), i);
		}
		
		brCC.close();
		brFB.close();
		brSB.close();
		brTB.close();
	}
	
	private void addSkill(int branch, int type, double value, int cost, int i) {
		switch(branch) {
		case 0: CommonCore[i] = new Skill(type, value, cost);
		break;
		case 1: FirstBranch[i] = new Skill(type, value, cost);
		break;
		case 2: SecondBranch[i] = new Skill(type, value, cost);
		break;
		case 3: ThirdBranch[i] = new Skill(type, value, cost);
		break;
		}
	}
	
	public int getSkillType(int branch, int i) {
		switch(branch) {
		case 0: return CommonCore[i].getType();
		case 1: return FirstBranch[i].getType();
		case 2: return SecondBranch[i].getType();
		case 3: return ThirdBranch[i].getType();
		}
		return 0;
	}
	
	public double getSkillValue(int branch, int i) {
		switch(branch) {
		case 0: return CommonCore[i].getValue();
		case 1: return FirstBranch[i].getValue();
		case 2: return SecondBranch[i].getValue();
		case 3: return ThirdBranch[i].getValue();
		}
		return 0;
	}
	
	public int getSkillCost(int branch, int i) {
		switch(branch) {
		case 0: return CommonCore[i].getCost();
		case 1: return FirstBranch[i].getCost();
		case 2: return SecondBranch[i].getCost();
		case 3: return ThirdBranch[i].getCost();
		}
		return 0;
	}
	
}
