
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Locker {
	private int x;
	private int y;
	private Player p;
	private boolean open = false;
	private BufferedImage lock = null;
	private BufferedImage openLocker = null;
	private BufferedImage spb = null;
	private BufferedImage shovel = null;
	private BufferedImage wall = null;
	private boolean locker1 = false;
	private boolean locker2 = false;
	private boolean locker3 = false;
	private char left_right_locker;
	private Shovel shovelItem;
	private Tablets[] tab = new Tablets[3];
	private BufferedImage bottomTab = null;
	
	public Locker(Player p, int x, int y, char left_right_locker, Tablets[] tab, Shovel shovelItem)
	{
		this.x = x;
		this.y = y;
		this.p = p;
		this.left_right_locker = left_right_locker;
		this.tab = tab;
		this.shovelItem = shovelItem;
		
		try {
			lock = ImageIO.read(new File("locker.png"));
			wall = ImageIO.read(new File("wall.png"));
			openLocker = ImageIO.read(new File("openLocker.png"));
			spb = ImageIO.read(new File("SBP\\sbp.png"));
			shovel = ImageIO.read(new File("Items\\shovel.png"));
			bottomTab = ImageIO.read(new File("Items\\bottomTablet.png"));
		} catch (IOException e) {
			System.out.println("No locker Image");
		}
	}
	
	public int getY()
	{
		return y;
	}
	
	public boolean canInteract()
	{
		if(p.getX()+p.getW()+p.getXA() > x-5 && p.getX()+p.getXA() < x+131 && p.getY()+p.getH()+p.getYA() > y && p.getY()+p.getYA()+p.getH() < y+128)
			return true;
		else
			return false;
	}
	
	public void setOpen(boolean b)
	{
		open = b;
	}
	
	public boolean getOpen()
	{
		return open;
	}
	
	public void setLocker1(boolean b)
	{
		locker1 = b;
	}

	public void setLocker2(boolean b)
	{
		locker2 = b;
	}
	
	public void setLocker3(boolean b)
	{
		locker3 = b;
	}
	
	public boolean getLocker1()
	{
		return locker1;
	}
	
	public boolean getLocker2()
	{
		return locker2;
	}

	public boolean getLocker3()
	{
		return locker3;
	}
	
	public void collision()
	{
		if(p.getX()+p.getW()+p.getXA() > x-1 && p.getX()+p.getXA() < x+126 && p.getY()+p.getH()+p.getYA() > y-1 && p.getY()+p.getYA()+p.getH() < y + 104)
		{
			p.setXA(p.getXA() * -1);  
			p.setYA(p.getYA() * -1);
		}
	}
	
	public void paint(Graphics2D g)
	{
		if (left_right_locker == 'l')
		{
			g.drawImage(wall, 0, 0, 1250, 802, null);
			if (locker1)
			{
				g.drawImage(openLocker, 101, 0, 288, 802, null);
			}
			else
			{
				g.drawImage(lock, 101, 0, 288, 802, null);
			}

			if (locker2)
			{
				g.drawImage(openLocker, 481, 0, 288, 802, null);
				g.drawImage(spb, 600, 300, 50, 75, null);
			}
			else
			{
				g.drawImage(lock, 481, 0, 288, 802, null);
			}
			
			if (locker3)
			{
				g.drawImage(openLocker, 861, 0, 288, 802, null);
			}
			else
			{			
				g.drawImage(lock, 861, 0, 288, 802, null);
			}
		}
		else if (left_right_locker == 'r')
		{
			g.drawImage(wall, 0, 0, 1250, 802, null);
			if (locker1)
			{
				g.drawImage(openLocker, 101, 0, 288, 802, null);
				if (!shovelItem.getIsFound())
				{
					g.drawImage(shovel, 94, 459, 296, 296, null);
				}
			}
			else
			{
				g.drawImage(lock, 101, 0, 288, 802, null);
			}

			if (locker2)
			{
				g.drawImage(openLocker, 481, 0, 288, 802, null);
			}
			else
			{
				g.drawImage(lock, 481, 0, 288, 802, null);
			}
			
			if (locker3)
			{
				g.drawImage(openLocker, 861, 0, 288, 802, null);
				if (!tab[0].getIsFound())
				{
					g.drawImage(bottomTab, 961, 700, 84, 84, null);
				}
			}
			else
			{			
				g.drawImage(lock, 861, 0, 288, 802, null);
			}
		}
	}

}