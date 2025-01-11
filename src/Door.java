
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Door {
	private int x;
	private int y;
	private boolean isDoorOpen = false;
	private boolean canInteract = false;
	private Player p;
	private BufferedImage textbox = null;
	private BufferedImage open = null;
	private BufferedImage closed = null;
	private Keys[] key;

	
	public Door(Player p, String open, String closed, int x, int y, Keys[] key)
	{
		this.x = x;
		this.y = y;
		this.p = p;
		this.key = key;

		try
		{
			this.open = ImageIO.read(new File("Doors\\"+open));
			this.closed = ImageIO.read(new File("Doors\\"+closed));
			textbox = ImageIO.read(new File("Doors\\textbox.png"));

		} catch (IOException e) {
			System.out.println("No Door Image");
		}
	}
	
	public int getY()
	{
		return y;
	}
	
	public boolean getCanInteract()
	{
		return canInteract;
	}
	
	public boolean getIsOpen()
	{
		return isDoorOpen;
	}
	
	public void setIsOpen(boolean b)
	{
		isDoorOpen = b;
	}
	
	public void interact()
	{
		if(p.getX()+p.getW()+p.getXA() > x-7 &&
				p.getX()+p.getXA() < x + 50 && 
				p.getY()+p.getH()+p.getYA() > y-55 && 
				p.getY()+p.getYA()+p.getH() < y + 42)
			canInteract = true;
		else
			canInteract = false;
	}
	
	public void collision()
	{
		if(!isDoorOpen)
		{
			interact();
			if((p.getX()+p.getW()+p.getXA() > x-2 && 
					 p.getX()+p.getXA() < x+45 && 
					 p.getY() + p.getH() + p.getYA() < y+17) &&
					p.getY() + p.getH() + p.getYA() > y)
				{
					p.setXA(p.getXA() * -1);  
					p.setYA(p.getYA() * -1);
				}
		}
	}
	
	public void paint(Graphics2D g)
	{
		if(getIsOpen())
			g.drawImage(open, x, y-52, 80, 68, null);
		else
			g.drawImage(closed, x, y-52, 80, 68, null);

		if(canInteract)
		{
			if(!getIsOpen())
			{
				if (y == 270)
				{
					if (key[3].getIsFound())
					{
						g.drawImage(textbox, 0, 812, 1250, 150, null);
					}
					// add else here to make it say door is locked if necessary
				}
				else
				{
				g.drawImage(textbox, 0, 812, 1250, 150, null);
				}
			}
				
		}
	}
}
