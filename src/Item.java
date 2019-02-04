
public class Item extends Hitbox {

	private int typeItem;
	/* -9: 1000 coins, -8: 500 coins, -7: 100 coins, -6: 50 coins, -5: 10 coins, -4: 5 coins, -3: 1 coin
	 * -2: life*5, -1:life*2, 0: life,
	 * 1: bomb mun , 2: sniper mun, 3: fire mun, 4: yellow3 mun, 5: machinegun mun, 6: shotgun mun */
	private double value;
	private int state; /* 0:selfstanding, 1:detected, 2:attracted */
	
	private double maxSpeed;	
	private double frictionCoef;
	double rMax;
	
	double dMin;
	double dMax;
		
	public Item() {
		this.points = new double[1][2];
		this.points[0][0] = 0;
		this.points[0][1] = 0;
		
		this.position = new double[2];
		this.position[0] = 0;
		this.position[1] = 0;
		
		this.speed = new double[2];
		this.speed[0] = 0;
		this.speed[1] = 0;
		
		type = 0;
		
		state = 0;
		value = 0;
		
		frictionCoef = 0.3;
		
		rMax = 30;
		dMin = 20;
		dMax = 150;
	}
	
	public void update() {
		double theta;
		double r;

		double vpospi;
		double vpospj;
		double norme;
		
		double acceleration = 2*Main.gravity + 0.1;
		
		if (type == 1) {
			vpospi = Main.mainChar.position[0] - this.position[0];
			vpospj = Main.mainChar.position[1] - this.position[1]; 
			norme = (Math.sqrt(Math.pow(vpospi, 2) + Math.pow(vpospj, 2)));
			
			switch(state) {
			case 0 :
			{
				if (norme > dMax || !isUseful()) {
					
				}
				else {
					state = 1;
				}
			}
			break;
			case 1: 
			{
				vpospi = (0.5*rMax*vpospi)/norme;
				vpospj = (0.5*rMax*vpospj)/norme;
				
				theta = Math.random()*2*Math.PI;
				r = Math.random()*rMax;
				
				this.speed[0] = r*Math.cos(theta) + vpospi;
				this.speed[1] = r*Math.sin(theta) + vpospj;
				
				this.position[0] += this.speed[0];
				this.position[1] += this.speed[1];
				
				state = 2;
			}
			break;
			case 2:
			{
				vpospi = (acceleration*vpospi)/norme;
				vpospj = (acceleration*vpospj)/norme;
				
				this.speed[0] += vpospi - frictionCoef * this.speed[0];
				this.speed[1] += vpospj - frictionCoef * this.speed[1];
				
				this.position[0] += this.speed[0];
				this.position[1] += this.speed[1];
				
				if (norme < dMin) {
					if (isUseful()) {
						if(typeItem > 0) {
							//munitions
							Main.mainChar.weapon.collectMunitions(typeItem);
						}
						else if (typeItem > -3) {
							//life
							
						}
						else {
							//coins
							switch(typeItem) {
								case -3 :
								{
									Main.mainChar.charac.coins += 1;
								}
								break;
								case -4 :
								{
									Main.mainChar.charac.coins += 5;
								}
								break;
								case -5 :
								{
									Main.mainChar.charac.coins += 10;
								}
								break;
								case -6 :
								{
									Main.mainChar.charac.coins += 50;
								}
								break;
								case -7 :
								{
									Main.mainChar.charac.coins += 100;
								}
								break;
								case -8 :
								{
									Main.mainChar.charac.coins += 500;
								}
								break;
								case -9 :
								{
									Main.mainChar.charac.coins += 1000;
								}
								break;
							}
							if(Main.debug[24]) {
								System.out.println(Main.mainChar.charac.coins);
							}
						}

						desactivateItem();
					}
					else {
						this.state = 0; 
					}
					
				}
			}
			break;
			}
		}
	}
	
	public void activeItem(double posi, double posj, int t) {
		this.position[0] = posi;
		this.position[1] = posj;
		
		this.type = 1;
		
		this.typeItem = t;
		
		this.state = 0;
	}
	
	public void desactivateItem() {
		this.position[0] = 0;
		this.position[1] = 0;
		
		this.speed[0] = 0;
		this.speed[1] = 0;
		
		this.typeItem = 0;
		this.value = 0;
		
		this.type = 0;
	}
	
	public boolean isUseful() {
		if (typeItem > 0) {
			//munitions
			if(Main.mainChar.weapon.fullMunitions(typeItem)) {
				return false;
			}
			else {
				return true;
			}
		}
		else if (typeItem > -3) {
			//life
			if (Main.mainChar.charac.hp == Main.mainChar.charac.maxHp) {
				return false;
			}
			else {
				return true;
			}
		}
		else {
			//coins
			return true;
		}
	}
}
