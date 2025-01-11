
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class MainRoom {
	private int w = 1250;
	private int h = 802;
	private Player p;
	private Pedestal[] ped = new Pedestal[5];
	private Door secDoor;
	private Door baseDoor;
	private BaseLock baseLock;
	private BufferedImage background = null, back1 = null, back2 = null, back3 = null;
	private BufferedImage pillar = null;
	private SpotDiff sd;
	private boolean sdPlay = false;
	private BufferedImage textbox = null;
	private boolean errorMessage = false;
	private int timer = 0;
	private Keys[] key = new Keys[4];
	private Shovel shovel;
	private Stand stand;
	private Tablets[] tab = new Tablets[3];
	private FinalRoom vault = new FinalRoom(p);
	private int mouseX = -10;
    private int mouseY = -10;
    private int digCount = 0;
	private EscapeRoom er;
	
	public MainRoom(Player player, SpotDiff sd, Keys[] key, BaseLock baseLock, Tablets[] tab, Shovel shovel, EscapeRoom er)
	{
		this.key = key;
		this.baseLock = baseLock;
		stand = new Stand(p);
		this.tab = tab;
		this.shovel = shovel;
		this.er = er;
		
		//main room background
		try {
			background = ImageIO.read(new File("Backgrounds\\full image.png"));
			back1 = ImageIO.read(new File("Backgrounds\\full image1.png"));
			back2 = ImageIO.read(new File("Backgrounds\\full image2.png"));
			back3 = ImageIO.read(new File("Backgrounds\\full image3.png"));
			pillar = ImageIO.read(new File("Decoration\\pillar.png"));
	    	textbox = ImageIO.read(new File("winTextbox.png"));

		} catch (IOException e) {
			System.out.println("No Mainroom Image");
		}
		
		p = player;
		this.sd = sd;
		er.setFinalRoom(vault);
		
		secDoor = new Door(p, "secDoorOpen.png", "secDoorClosed.png", 1130, 270, key);
		baseDoor = new Door(p, "secDoorOpen.png", "secDoorClosed.png", 1130, 590, key);
		
		ped[0] = new Pedestal("steelOff.png", "steelOn.png", p, 684, 470, false);
		ped[1] = new Pedestal("goldOff.png", "goldOn.png", p, 548, 552, true);
		ped[2] = new Pedestal("silverOff.png", "silverOn.png", p, 820, 550, true);
		ped[3] = new Pedestal("clayOff.png", "clayOn.png", p, 596, 688, false);
		ped[4] = new Pedestal("bronzeOff.png", "bronzeOn.png", p, 770, 688, true);


	}
	
	public void keyPressed(KeyEvent e)
	{
		for(Pedestal pedestals : ped)
		 {
			 if(pedestals.canInteract())
			 {
				 if(e.getKeyCode() == KeyEvent.VK_E)
						pedestals.setIsOn(!pedestals.getIsOn());
			 }
		 }
		
		if(secDoor.getCanInteract())
		{
			if(key[3].getIsFound())
			{
				if(e.getKeyCode() == KeyEvent.VK_E)
				{
					secDoor.setIsOpen(true);
					key[3].setIsUsed(true);
				}
			}
			else
			{
				if(e.getKeyCode() == KeyEvent.VK_E)
				{
					errorMessage = true;
				}
			}
		}
		
		if(baseDoor.getCanInteract() && e.getKeyCode() == KeyEvent.VK_E && baseLock.getLocked() == false)
		{
				baseDoor.setIsOpen(true);
		}
		
		else if (baseDoor.getCanInteract() && e.getKeyCode() == KeyEvent.VK_E && baseLock.getLocked())
		{
			baseLock.setDisplay(!baseLock.getDisplay());
		}

		
		if (sd.canInteract())
		{
			if(e.getKeyCode() == KeyEvent.VK_E)
			{
				sd.setInteract(!sd.getInteract());
				p.setInGame(!p.getInGame());
			}
		}
		
		if(stand.canInteract(p) && !p.getInGame())
		{
			if(e.getKeyCode() == KeyEvent.VK_E)
			{
				stand.setOpen(!stand.getOpen());
				p.setCanMove(!p.getCanMove());
			}
		}
		
		if(vault.canInteract(p) && !p.getInGame())
		{
			if(e.getKeyCode() == KeyEvent.VK_E)
			{
				vault.setOpen(!vault.getOpen());
				p.setCanMove(!p.getCanMove());
			}
		}

		if (sandbox())
		{
			if (shovel.getIsFound() && !shovel.getIsUsed() && e.getKeyCode() == KeyEvent.VK_E)
			{
				digCount++;
				if(digCount == 3)
				{
					tab[2].collect();
					shovel.setIsUsed(true);
				}
			}
		}
	}
	
	public void mousePressed(MouseEvent e) {
		mouseX = e.getX();
        mouseY = e.getY();
        
		if(stand.getOpen() && !p.getInGame())
		{
			if(!tab[0].getIsUsed() && tab[0].getIsFound() &&  mouse(0, 0, 1250, 802))
			{
				tab[0].setIsUsed(true);
				stand.setTop();		
			}
			else if(!tab[1].getIsUsed() && tab[1].getIsFound() && mouse(0, 0, 1250, 802))
			{
				tab[1].setIsUsed(true);
				stand.setBottom();
			}
			else if(!tab[2].getIsUsed() && tab[2].getIsFound() && mouse(0, 0, 1250, 802))
			{
				tab[2].setIsUsed(true);
				stand.setRight();
			}
		}
		
		if(vault.getOpen() && !p.getInGame())
		{
			if(!key[0].getIsUsed() && key[0].getIsFound())
			{

				if(mouse(690, 190, 70, 80) && !vault.getTop())
				{
					vault.setTop();	
					key[0].setIsUsed(true);
				}
				else if(mouse(690, 370, 70, 80) && !vault.getMiddle())
				{
					vault.setMiddle();
					key[0].setIsUsed(true);
				}
				else if(mouse(690, 530, 70, 80) && !vault.getBottom())
				{
					vault.setBottom();
					key[0].setIsUsed(true);
				}
			}
			else if(!key[1].getIsUsed() && key[1].getIsFound())
			{
				if(mouse(690, 190, 70, 80) && !vault.getTop())
				{
					vault.setTop();	
					key[1].setIsUsed(true);
				}
				else if(mouse(690, 370, 70, 80) && !vault.getMiddle())
				{
					vault.setMiddle();
					key[1].setIsUsed(true);
				}
				else if(mouse(690, 530, 70, 80) && !vault.getBottom())
				{
					vault.setBottom();
					key[1].setIsUsed(true);
				}
			}
			else if(!key[2].getIsUsed() && key[2].getIsFound())
			{
				if(mouse(690, 190, 70, 80) && !vault.getTop())
				{
					vault.setTop();	
					key[2].setIsUsed(true);
				}
				else if(mouse(690, 370, 70, 80) && !vault.getMiddle())
				{
					vault.setMiddle();
					key[2].setIsUsed(true);
				}
				else if(mouse(690, 530, 70, 80) && !vault.getBottom())
				{
					vault.setBottom();
					key[2].setIsUsed(true);
				}
			}
		}
	
	}
	
	public boolean mouse(int boxX, int boxY, int boxW, int boxH)
	{
		if(mouseX > boxX &&
				mouseX < boxX + boxW && 
				mouseY > boxY && 
				mouseY < boxY + boxH)
			return true;
		else
			return false;
	}
	public boolean hitboxes(int boxX, int boxY, int boxW, int boxH)
	{
		if(p.getX()+p.getW()+p.getXA() > boxX &&
				p.getX()+p.getXA() < boxX + boxW && 
				p.getY()+p.getH()+p.getYA() > boxY && 
				p.getY()+p.getYA()+p.getH() < boxY + boxH)
			return true;
		else
			return false;
	}

	private boolean sandbox() {

        double centerX = 554 + 315 / 2.0; 
        double centerY = 175 + 200 / 2.0; 
        double radiusX = 315 / 2.0;       
        double radiusY = 200 / 2.0;       



        return ((Math.pow(p.getX() - centerX, 2) / Math.pow(radiusX, 2)) + (Math.pow((p.getY()+24) - centerY, 2) / Math.pow(radiusY, 2)) <= 1);
    }

	public void collision()
	{
		//Collision for main room borders
		if(
				/*left walls*/
				hitboxes(1068, 418, 62, 195) ||//basement door
				hitboxes(1175, 418, 75, 195) ||//basement door
				hitboxes(1068, 418, 182, 85) ||//basement door
				hitboxes(1068, 738, 182, 64) ||
				hitboxes(1068, 90, 60, 197) || //security door
				hitboxes(1175, 90, 75, 197) ||//security door
				hitboxes(1219, 0, 31, 802) ||
				/*right walls*/
				hitboxes(0, 0, 330, 350) ||
				hitboxes(0, 0, 68, 802) ||
				hitboxes(0, 547, 330, 255) ||
				/*top wall*/
				hitboxes(0, 0, 1250, 105) ||
				/*bottom wall*/
				hitboxes(0, 802, 1250, 10))
		{
			p.setXA(p.getXA() * -1);  
			p.setYA(p.getYA() * -1);
		}
		
		//collision for benches
		 if(hitboxes(398, 90, 104, 45) || //left front facing bench
				 hitboxes(666, 90, 64, 45) ||//stand
				 hitboxes(896, 90, 104, 45) ||//right front facing bench
				 hitboxes(330, 178, 48, 140) || //top two benches
				 hitboxes(330, 630, 48, 140) || // bottom two benches
				 hitboxes(334, 571, 32, 32) || // bottom pillar
				 hitboxes(334, 343, 32, 32)) // top pillar
		{
			p.setXA(p.getXA() * -1);  
			p.setYA(p.getYA() * -1);
		}
		
		
		 //collision for pedestals
		 for(Pedestal pedes: ped)
			 pedes.collision();
		 
		 secDoor.collision();
		 
		 //Changes to security room
		 if(hitboxes(1130, 270, 35, 18) && secDoor.getIsOpen())
		 {
			 p.setCurrentRoom('s');
			 p.setX(441);
			 p.setY(571);
		 }
		 
		 if (hitboxes(1133, 550, 30, 45) && baseDoor.getIsOpen())
		 {
			 p.setCurrentRoom('b');
			 p.setX(766);
			 p.setY(590);
		 }
		baseDoor.collision();
		
	}
	
	public void paintError(Graphics2D g)
	{
		timer++;
		g.fillRect(0, 0, 100, 100);
	}
	
	public void paint(Graphics2D g)
	{
		switch(digCount)
		{
		case 0:
			g.drawImage(background, 0, 0, 1250, 814, null);
			break;
		case 1:
			g.drawImage(back1, 0, 0, 1250, 814, null);
			break;
		case 2:
			g.drawImage(back2, 0, 0, 1250, 814, null);
			break;
		case 3:
			g.drawImage(back3, 0, 0, 1250, 814, null);
			break;
		}
		
		boolean solved = true;
		for(Pedestal p : ped)
		{
			if(!p.isCorrect())
				solved = false;
		}
		
		for(Pedestal p : ped)
		{
			p.setIsComplete(solved);
		}
		
		for(Pedestal p : ped)
		{
			if(solved)
			{
				if(p.getTimer() < 500)
				{
					p.setTimer();
					g.drawImage(textbox, 0, 812, 1250, 150, null);
					key[3].collect();
				}

			}
		}
		
		if(timer < 300)
		{
			if(errorMessage)
				paintError(g);
		}
		else
		{
			errorMessage = false;
			timer = 0;
		}
		
		for(Pedestal p : ped)
			p.paint(g);
		
		secDoor.paint(g);
		baseDoor.paint(g);
		p.paint(g);
		
		//will paint the pedestals again depending on the players y cords
		for(Pedestal pedestals : ped)
		{
			if(p.getY() < pedestals.getY()+50)
				pedestals.paint(g);
		}
		
		//will paint pedestals depending on the players y cords
		if(p.getY() + p.getH() < 370)
			g.drawImage(pillar, 334, 262, 32, 114, null);
		if(p.getY() + p.getH() < 595)
			g.drawImage(pillar, 334, 486, 32, 114, null);
		
		if(stand.getOpen() && !p.getInGame())
			stand.paint(g);
		
		if(vault.getOpen() && !p.getInGame())
			vault.paint(g);
		
		if (sd.getInteract())
		{
			sd.paint(g);
		}
		
		if(sd.getDiffNum())
		{
			if(sd.getTimer() < 500)
			{
				sd.setTimer();
				g.drawImage(textbox, 0, 812, 1250, 150, null);
				key[1].collect();

			}
		}
		
		if (baseLock.getLocked() && baseLock.getDisplay())
		{
			p.setInGame(true);
			baseLock.paint(g);
		}
		else
		{
			p.setInGame(false);
		}
		

		g.setColor(Color.red);
		//g.fillRect();
	}

	
}
