package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class Entity {
	
	GamePanel gp;
	public int worldX, worldY;
	public int speed;
	
	public BufferedImage up1, up2, up3, down1, down2, down3, left1 ,left2, left3, right1, right2, right3;
	public String direction = "down";

	public int spriteCounter =0;
	public int spriteNum =0;
	public Rectangle solidArea = new Rectangle(0,0,48,48);
	public int solidAreaDefaultX, solidAreaDefaultY;
	public boolean collision = false;
	public int actionLockCounter =0;
	String dialogues[] = new String[20];
	int dialougeIndex = 0;
	public BufferedImage image, image2, image3;

	
	
	//character attributes
	public int type; // 0 = player 1 = npc and 2 = monster
	public String name;
	public int level ;
	public int movement;
	public int maxLife;
	public int hitpoints;
	public int str;
	public int dex;
	public int con;
	public int INT;
	public int wis;
	public int cha;
	public int strMOD;
	public int dexMOD;
	public int conMOD;
	public int INTMOD;
	public int wisMOD;
	public int chaMOD;
	public int coin;
	public Entity currentWeapon;
	public Entity currentShield;
	public Entity currentArmor;
	public int attack;
	public int armorClass;
	public String descriptions = "";
	
	
	// item attributes
	public String attackValue; //d8 for example
	public int sheildValue;
	public int armorValue;
	public Entity(GamePanel gp) {
		this.gp =gp;
	}
	public void setAction() {}
	public void speak() {
		if (dialogues[dialougeIndex] == null){
			dialougeIndex = 0;
		}
		gp.ui.currentDialogue = dialogues[dialougeIndex];
		dialougeIndex++;
		
		switch(gp.player.direction) {
		case "up":
			direction = "down";
			break;
		case "down":
			direction = "up";
			break;
		case "left":
			direction = "right";
			break;
		case "right":
			direction = "left";
			break;
		}
	}
	public void update() {
		setAction();
		
		collision = false;
		gp.colchecker.checkTile(this);
		gp.colchecker.checkObject(this, false);
		gp.colchecker.checkPlayer(this);
		
		//if collision is false player can move
		if (collision == false) {
			switch(direction) {
			case "up": worldY -= speed; break;
			case "down": worldY += speed; break;
			case "left": worldX -= speed; break;
			case "right": worldX += speed; break;
				
			}
			spriteCounter++; 
			if(spriteCounter > 10) {
				if(spriteNum == 0) {
					spriteNum = 1;
				}
				else if (spriteNum ==1) {
					spriteNum = 0;
				}
				spriteCounter =0;
			}
		}
	}
	public void draw(Graphics2D g2) {
		
		BufferedImage image =  null;
		
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;
		
		if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
			worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
			worldY + gp.tileSize > gp.player.worldY -gp.player.screenY &&
			worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
			
			switch(direction) {
			case"up":
				if(spriteNum ==0) {
					image =up1;
				}
				if(spriteNum ==1) {
					image =up2;
				}
			
				break;
			case "down":
				if(spriteNum ==0) {
					image = down1;
				}
				if(spriteNum ==1) {
					image =down2;
				}
			
				break;
			case "left":
				if(spriteNum ==0) {
					image = left1;
				}
				if(spriteNum ==1) {
					image =left2;
				}
				
				break;
			case "right":
				if(spriteNum ==0) {
					image = right1;
				}
				if(spriteNum ==1) {
					image =right2;
				}
	
				break;
			}
			g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
		}
	}
	public BufferedImage setup(String imagepath) {
		UtilityTool uTool = new UtilityTool();
		BufferedImage image = null;
	
		try {
			image = ImageIO.read(getClass().getResourceAsStream( imagepath + ".png"));
			image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
		} catch (IOException e) {
		
		}
		return image;
	}
	//DICE STUFF FOR ALL THE ENTITY
		public String diceType;

	    public void setDiceType(String d) {
	        diceType = d;
	    }

	    public String getDiceType() {
	        return diceType;
	    }



	    //Should probably be only public (besides constructor/getDiceType, maybe setDiceType for switching between two and one handed), covers all rolls
	    public  int roll() {
	        if(this.getDiceType().equals("d4")) {
	            return d4();
	        } else if(this.getDiceType().equals("d6")) {
	            return d6();
	        } else if(this.getDiceType().equals("d8")) {
	            return d8();
	        } else if(this.getDiceType().equals("d10")) {
	            return d10();
	        } else if(this.getDiceType().equals("d12")) {
	            return d12();
	        } else if(this.getDiceType().equals("d20")) {
	            return d20();
	        } else if(this.getDiceType().equals("d100")) {
	            return d100();
	        } else {
	            //unarmed
	            return 1;
	        }
	    }

	    /**
	     * Returns an integer between 1 and 4
	     * @return int
	     */
	    public int d4() {
	        Random n = new Random();
	        return n.nextInt(3);
	    
	    }  

	    /**
	     * Returns an integer between 1 and 6
	     * @return int
	     */
	    public int d6() {
	        Random n = new Random();
	        return n.nextInt(5);
	    }

	    /**
	     * Returns an integer between 1 and 8
	     * @return int
	     */
	    public int d8() {
	        Random n = new Random();
	        return n.nextInt(7);
	    }

	    /**
	     * Returns an integer between 0 and 10
	     * @return int
	     */
	    public int d10() {
	        Random n = new Random();
	        return n.nextInt(9);
	    }

	    /**
	     * Returns an integer between 1 and 12
	     * @return int
	     */
	    public int d12() {
	        Random n = new Random();
	        return n.nextInt(11);
	    }

	    /**
	     * Returns an integer between 1 and 20
	     * @return int
	     */
	    public int d20() {
	        Random n = new Random();
	        return n.nextInt(19);
	    }

	    /**
	     * Returns an integer between 0 and 100
	     * @return int
	     */
	    public int d100() {
	        Random n = new Random();
	        return n.nextInt(99);
	    }

	    
}

