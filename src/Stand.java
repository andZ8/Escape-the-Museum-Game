
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Stand {
	private int x;
	private int y;
	private Player p;
	private BufferedImage empty = null, full = null, bottom = null, top = null, right = null, empTop = null, empBottom = null, empRight = null;
	private boolean topTablet;
	private boolean bottomTablet;
	private boolean rightTablet;
	private boolean open = false;
	private BufferedImage textbox = null;
	
	public Stand(Player player)
	{
		this.x = x;
		this.y = y;
		p = player;
		
		try {
			empty = ImageIO.read(new File("Stand\\emptyTablet.png"));
			full = ImageIO.read(new File("Stand\\fullTablet.png"));
			bottom = ImageIO.read(new File("Stand\\bottom.png"));
			top = ImageIO.read(new File("Stand\\top.png"));
			right = ImageIO.read(new File("Stand\\right.png"));
			empTop = ImageIO.read(new File("Stand\\emptyTop.png"));
			empBottom = ImageIO.read(new File("Stand\\emptyBottom.png"));
			empRight = ImageIO.read(new File("Stand\\emptyRight.png"));
			textbox = ImageIO.read(new File("textbox.png"));


		} catch (IOException e) {
			System.out.println("No Stand Image");
		}
	}
	
	public int getY()
	{
		return y;
	}
	
	public void setTop()
	{
		topTablet = true;
	}
	
	public void setBottom()
	{
		bottomTablet = true;
	}
	
	public void setRight()
	{
		rightTablet = true;
	}
	
	public boolean getRight()
	{
		return rightTablet;
	}
	
	public void setOpen(boolean b)
	{
		open = b;
	}
	
	public boolean getOpen()
	{
		return open;
	}
	public boolean canInteract(Player p)
	{
		if(p.getX()+p.getW()+p.getXA() > 660 && p.getX()+p.getXA() < 660+76 && p.getY()+p.getH()+p.getYA() > 90 && p.getY()+p.getYA()+p.getH() < 90+60)
			return true;
		else
			return false;
	}
	
	
	public void paint(Graphics2D g)
	{
		if(!topTablet && !bottomTablet && !rightTablet)
		{
			g.drawImage(empty, 0, 0, 1250, 962, null);
		}
		else if(topTablet && bottomTablet && rightTablet)
		{
			g.drawImage(full, 0, 0, 1250, 962, null);
		}
		else
		{
			if(topTablet && bottomTablet)
			{
				g.drawImage(empRight, 0, 0, 1250, 962, null);
			}
			else if(bottomTablet && rightTablet)
			{
				g.drawImage(empTop, 0, 0, 1250, 962, null);
			}
			else if(rightTablet && topTablet)
			{
				g.drawImage(empBottom, 0, 0, 1250, 962, null);
			}
			else
			{
				if(topTablet)
				{
					g.drawImage(top, 0, 0, 1250, 962, null);
				}
				else if(bottomTablet)
				{
					g.drawImage(bottom, 0, 0, 1250, 962, null);
				}
				else
				{
					g.drawImage(right, 0, 0, 1250, 962, null);
				}
			}
		}
		
		g.drawImage(textbox, 0, 812, 1250, 150, null);

	}

}
