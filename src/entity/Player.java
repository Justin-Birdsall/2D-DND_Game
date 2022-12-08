package entity;
import java.util.ArrayList;
import java.util.Random;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;
import object.OBJ_Chainmail;
import object.OBJ_Key;
import object.OBJ_Sheild;
import object.OBJ_Shortsword_Normal;

public class Player extends Entity {
	GamePanel gp;
	KeyHandler keyH;
	
	public final int screenX;
	public final int screenY;
	public ArrayList<Entity> inventory = new ArrayList<>();
	public final int invnetorysize = 20;
	
	
	public Player(GamePanel gp,KeyHandler keyH) {
		super(gp);
		
		this.gp =gp;
		this.keyH= keyH;
		
		screenX = gp.screenWidth/2 -(gp.tileSize)/2;
		screenY = gp.screenHeight/2 - (gp.tileSize)/2;
		
		solidArea = new Rectangle();
		solidArea.x = 8;
		solidArea.y = 16;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 32;
		solidArea.height =32;
		
		setDeafaultValues();
		getPlayerImage();
		setItems();
		
	}
	public void setDeafaultValues() {
		worldX = gp.tileSize * 25;
		worldY= gp.tileSize * 22;
		speed =4;
		direction = "down";
		
		//Player Status
		level = 1;
		maxLife = 12;
		movement = 30;
		hitpoints = 12;
		str = 16;
		dex = 9;
		con = 15;
		INT = 13;
		wis = 11;
		cha = 14;
		strMOD = +3;
		dexMOD = -1;
		conMOD = +2;
		INTMOD = +1;
		wisMOD = +0;
		chaMOD = +2;
		coin = +0;
		currentWeapon = new OBJ_Shortsword_Normal(gp);
		currentShield = new OBJ_Sheild(gp);
		currentArmor = new OBJ_Chainmail(gp);
		attack = getAttack();
		armorClass = getArmorClass();
		
	}
	
	
	
	public void setItems() {
		inventory.add(currentWeapon);
		inventory.add(currentShield);
		inventory.add(new OBJ_Key(gp));
	}
	public int getAttack() {
		setDiceType(currentWeapon.attackValue);
		int holder  = roll();
		attack = holder + 1 + strMOD;
		return attack;	
	}
	
	public int getArmorClass() {
		return armorClass = currentShield.sheildValue + currentArmor.armorValue;
	}
	public void getPlayerImage() {

			up1 = setup("/player/up1");
			up2 = setup("/player/up2");
			up3 = setup("/player/up2");
			down1 = setup("/player/down1");
			down2 = setup("/player/down2");
			down3 = setup("/player/down3");
			left1 = setup("/player/left1");
			left2 = setup("/player/left2");
			left3 = setup("/player/left3");
			right1 = setup("/player/right1");
			right2 = setup("/player/right2");
			right3 = setup("/player/right3");
				
	}

	public void update() {
		
		if(keyH.upPressed == true || keyH.downPressed == true || keyH.rightPressed == true || keyH.leftPressed ==true) {
			if (keyH.upPressed == true) {
				direction ="up";
				
			}
			else if (keyH.downPressed == true) {
				direction="down";
				
			}
			else if (keyH.leftPressed == true){
				direction="left";
				
			}
			else if (keyH.rightPressed == true) {
				direction=	"right";
				
			}
			//Checking tile collision
			collision = false;
			gp.colchecker.checkTile(this);
			
			//check object collision
			int objIndex = gp.colchecker.checkObject(this, true);
			pickUpObject(objIndex);
			
			//Checking NPC collision
			int npcIndex = gp.colchecker.checkEntity(this, gp.npc);
			interactNPC(npcIndex);
			
			//CHECK EVENT
			gp.eHandler.checkEvent();
			
			gp.keyH.enterPressed = false;
			
			
			//if collision is false player can move
			if (collision == false) {
				switch(direction) {
				case "up":
					worldY -= speed;
					break;
				case "down":
					worldY += speed;
					break;
				case "left":
					worldX -= speed;
					break;
				case "right":
					worldX += speed;
					break;
					
				}
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
	public void pickUpObject(int i) {
		if(i != 999) {
			
		}
	}
	public void interactNPC(int i) {
		if(i != 999) {
			if(gp.keyH.enterPressed) {
				gp.gameState = gp.dialougeState;
				gp.npc[i].speak();
			}	
		}
	
	}
	public void draw(Graphics2D g2) {
		BufferedImage image =  null;
		switch(direction) {
		case"up":
			if(spriteNum ==0) {
				image =up1;
			}
			if(spriteNum ==1) {
				image =up2;
			}
			if(spriteNum ==15) {
				image =up3;
			}
			break;
		case "down":
			if(spriteNum ==0) {
				image = down1;
			}
			if(spriteNum ==1) {
				image =down2;
			}
			if(spriteNum ==16) {
				image =down3;
			}
			break;
		case "left":
			if(spriteNum ==0) {
				image = left1;
			}
			if(spriteNum ==1) {
				image =left2;
			}
			if(spriteNum ==17) {
				image =left3;
			}
			break;
		case "right":
			if(spriteNum ==0) {
				image = right1;
			}
			if(spriteNum ==1) {
				image =right2;
			}
			if(spriteNum ==18) {
				image =right3;
			}
			break;
		}
		g2.drawImage(image, screenX, screenY, null);
	}
}
