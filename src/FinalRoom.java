import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class FinalRoom {
	private Player p;
	private BufferedImage locked = null, unlocked = null, bottom = null, top = null, middle = null, empTop = null, empBottom = null, empMiddle = null;

	private boolean topLock, middleLock, bottomLock;
	private int mouseX = -10;
    private int mouseY = -10;
	private boolean open = false;
	private BufferedImage textbox = null;
	private boolean win;
	
	public FinalRoom(Player player)
	{
		p = player;
		try {
			locked = ImageIO.read(new File("vault\\locked.png"));
			unlocked = ImageIO.read(new File("vault\\unlocked.png"));
			bottom = ImageIO.read(new File("vault\\bottom.png"));
			top = ImageIO.read(new File("vault\\top.png"));
			middle = ImageIO.read(new File("vault\\middle.png"));
			empTop = ImageIO.read(new File("vault\\middleBottom.png"));
			empBottom = ImageIO.read(new File("vault\\topMiddle.png"));
			empMiddle = ImageIO.read(new File("vault\\topBottom.png"));
			textbox = ImageIO.read(new File("textbox.png"));

		} catch (IOException e) {
			System.out.println("No final room Image");
		}
	}
	
	
	public void setOpen(boolean b)
	{
		open = b;
	}
	
	public boolean getOpen()
	{
		return open;
	}
	
	public void setTop()
	{
		topLock = true;
	}
	
	public void setBottom()
	{
		bottomLock = true;
	}
	
	public void setMiddle()
	{
		middleLock = true;
	}
	
	public boolean getTop()
	{
		return topLock;
	}
	
	public boolean getBottom()
	{
		return bottomLock;
	}
	
	public boolean getMiddle()
	{
		return middleLock;
	}
	
	public boolean getWin()
	{
		return win;
	}
	
	public boolean canInteract(Player p)
	{
		if(p.getX()+p.getW()+p.getXA() > 0 && p.getX()+p.getXA() < 100 && p.getY()+p.getH()+p.getYA() > 0 && p.getY()+p.getYA()+p.getH() < 802)
			return true;
		else
			return false;
	}
	
	public void paint(Graphics2D g)
	{
		if(!topLock && !bottomLock && !middleLock)
		{
			g.drawImage(locked, -410, 0, 1660, 800, null);
		}
		else if(topLock && bottomLock && middleLock)
		{
			g.drawImage(unlocked, -410, 0, 1660, 800, null);
		}
		else
		{
			if(topLock && bottomLock)
			{
				g.drawImage(empMiddle, -410, 0, 1660, 800, null);
			}
			else if(bottomLock && middleLock)
			{
				g.drawImage(empTop, -410, 0, 1660, 800, null);
			}
			else if(middleLock && topLock)
			{
				g.drawImage(empBottom, -410, 0, 1660, 800, null);
			}
			else
			{
				if(topLock)
				{
					g.drawImage(top, -410, 0, 1660, 800, null);
				}
				else if(bottomLock)
				{
					g.drawImage(bottom, -410, 0, 1660, 800, null);
				}
				else
				{
					g.drawImage(middle, -410, 0, 1660, 800, null);
				}
			}
		}
		
		if(topLock && middleLock && bottomLock)
		{
			win = true;
		}
		
		g.setColor(new Color(57,57,69));
		g.fillRect(0, 800, 1250, 162);
		
		g.drawImage(textbox, 0, 812, 1250, 150, null);
		
		
	}
}