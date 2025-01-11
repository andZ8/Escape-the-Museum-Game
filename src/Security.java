import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;

public class Security {
	private Player p;
	private BufferedImage background = null;
	private BufferedImage box = null;
	private BufferedImage textbox = null;
	private SBP sbp;
	private Shovel shovel;
	private Locker locker;
	private Locker locker2;
	private int mouseX = -10;
	private int mouseY = -10;
	private Keys[] key = new Keys[4];
	private Tablets[] tab = new Tablets[3];
	private Inventory inv;
	private BufferedImage tablet2 = null;
	private EscapeRoom game;
	
	public Security(Player player, SBP sbp, Keys[] key, Inventory inv, Tablets[] tab, Shovel shovel)
	{
		p = player;
		this.sbp = sbp;
		this.key = key;
		this.inv = inv;
		this.tab = tab;
		this.shovel = shovel;
		this.game = game;
		
		locker = new Locker(p, 382, 180, 'l', tab, shovel);
		locker2 = new Locker(p, 742, 180, 'r', tab, shovel);
		try {
			background = ImageIO.read(new File("Backgrounds\\security background.png"));
			box = ImageIO.read(new File("Decoration\\box.png"));
			textbox = ImageIO.read(new File("winTextbox.png"));
			tablet2 = ImageIO.read(new File("Items\\topTablet.png"));
		} catch (IOException e) {
			System.out.println("No Security Image");
		}
	}
	
	public void keyPressed(KeyEvent e)
	{
		if(locker.canInteract())
		{
			if(p.getInGame())
			{
				if(locker.getLocker2())
				{
					if(!sbp.getPlayed())
					{
						if(e.getKeyCode() == KeyEvent.VK_E)
						{
							p.setInGame(false);
							sbp.reset();
						}	
					}
				}
			}
			else
			{
				if(e.getKeyCode() == KeyEvent.VK_E)
				{
					locker.setOpen(!locker.getOpen());
					p.setCanMove(!p.getCanMove());
					locker.setLocker1(false);
					locker.setLocker2(false);
					locker.setLocker3(false);
				}
			}
		}

		if(locker2.canInteract() && !p.getInGame())
		{
			if(e.getKeyCode() == KeyEvent.VK_E)
			{
				locker2.setOpen(!locker2.getOpen());
				p.setCanMove(!p.getCanMove());
				locker2.setLocker1(false);
				locker2.setLocker2(false);
				locker2.setLocker3(false);
			}
		}

		if(p.getX()+p.getW() > 645 && p.getX() < 645 + 50 &&  p.getY()+p.getH() > 300 &&  p.getY()+p.getH() < 300 + 100)
		{
			if (e.getKeyCode() == KeyEvent.VK_E)
			{
				tab[1].collect();
			}
		}
		
	}
	
	public void mousePressed(MouseEvent e) {
        // when mouse is clicked, check where it's clicked and select the appropriate block (block changes color)
        mouseX = e.getX();
        mouseY = e.getY();
        if(locker.getLocker2())
		{
			if(!sbp.getPlayed())
			{
				if(mouse(600, 300, 50, 75))
				{
					p.setInGame(true);
				}
			}
		}
        
		if (locker.getOpen()){
			if (mouse(140, 180, 210, 585))
			{
				locker.setLocker1(true);
			}
			else if (mouse(520, 180, 210, 585))
			{
				locker.setLocker2(true);
			}
			else if (mouse(900, 180, 210, 585))
			{
				locker.setLocker3(true);
			}
		}
		
		// right locker (locker2)
		
		if(locker2.getLocker1())
		{
			if(!shovel.getIsFound())
			{
				if(mouse(149, 460, 192, 296))
				{
					shovel.collect();
				}
			}
		}
        
		if (locker2.getOpen()){
			if (mouse(140, 180, 210, 585))
			{
				locker2.setLocker1(true);
			}
			else if (mouse(520, 180, 210, 585))
			{
				locker2.setLocker2(true);
			}
			else if (mouse(900, 180, 210, 585))
			{
				locker2.setLocker3(true);
			}
		}

		if (locker2.getLocker3())
		{
			if(!tab[0].getIsFound())
			{
				if(mouse(961, 700, 84, 84))
				{
					tab[0].collect();
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
	
	public void move()
	{
		if(p.getInGame())
		{
			sbp.move();
			if(sbp.win())
				p.setInGame(false);
		}
	}
	public void collision()
	{
		//Collision for room borders
		if(hitboxes(0, 0, 1250, 255) || //back wall
				hitboxes(0, 0, 372, 802) || //left wall
				hitboxes(878, 0, 372, 802) || //right wall
				hitboxes(540, 494, 710, 308) || //bottom and right wall
				hitboxes(0, 628, 1250, 174)) //bottom wall 
		{
			p.setXA(p.getXA() * -1);  
			p.setYA(p.getYA() * -1);
		}
		
		//collision for objects in the room
		if(hitboxes(550, 328, 120, 43) || //top bench
				hitboxes(548, 402, 120, 43) || //bottom bench
				hitboxes(792, 388, 120, 18) || //boxes
				hitboxes(804, 400, 120, 36) ||
				hitboxes(664, 200, 80, 77) || //water and cabinet
				hitboxes(500, 180, 56, 97))
		{
			p.setXA(p.getXA() * -1);  
			p.setYA(p.getYA() * -1);
		}
		
		//player can interact with the sbp game
		if(hitboxes(555, 333, 130, 53))
		{
			sbp.setInteract(true);
		}
		else
		{
			sbp.setInteract(false);
		}
		
		locker.collision();
		locker2.collision();

		//changes room to main room
		if(hitboxes(445, 625, 10, 10))
		 {
			 p.setCurrentRoom('m');
			 p.setX(1134);
			 p.setY(252);
		 }
	}
	
	public void paint(Graphics2D g)
	{
		g.setColor(Color.blue);
		g.drawImage(background, 0, 0, 1250, 802, null);	
		
		
		p.paint(g);
		
		if(p.getY() < 345)
			g.drawImage(box, 792, 332, 82, 104, null);	
		if (!tab[1].getIsFound())
		{
			g.drawImage(tablet2, 645, 328, 28, 28, null);
		}

		if(locker.getLocker2())
		{
			if(!sbp.getPlayed())
			{
				if(p.getInGame())
				{
					sbp.paint(g);
				}
			}
		}
		
		if(sbp.win())
		{
			if(sbp.getTimer() < 500)
			{
				sbp.setTimer();
				g.drawImage(textbox, 0, 812, 1250, 150, null);
				key[0].collect();
			}
		}
		
		if(locker.getOpen() && !p.getInGame())
			locker.paint(g);
		
		if(locker2.getOpen() && !p.getInGame())
			locker2.paint(g);
		inv.paint(g);
		

	}

}