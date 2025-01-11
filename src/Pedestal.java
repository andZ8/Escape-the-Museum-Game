import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Pedestal {
	private int x;
	private int y;
	private boolean isPedOn = false;
	private boolean isComplete = false;
	private BufferedImage pedOff = null;
	private BufferedImage pedOn = null;
	private BufferedImage textOn = null;
	private BufferedImage textOff = null;
	private Player p;
	private boolean answer;
	private int winTimer = 0;
	
	public Pedestal(String off, String on, Player p, int x, int y, boolean answer)
	{
		this.x = x;
		this.y = y;
		this.p = p;
		this.answer = answer;
		
		try {
			pedOff = ImageIO.read(new File("Pedestals\\"+off));
			pedOn = ImageIO.read(new File("Pedestals\\"+on));
			textOn = ImageIO.read(new File("Pedestals\\textboxOn.png"));
			textOff = ImageIO.read(new File("Pedestals\\textboxOff.png"));

		} catch (IOException e) {
			System.out.println("No Pedestal Image");
		}
	}
	
	public void setTimer()
    {
    	winTimer++;
    }
    
    public int getTimer()
    {
    	return winTimer;
    }
    
	public int getY()
	{
		return y;
	}
	
	public boolean isCorrect()
	{
		return isPedOn == answer;
	}
	
	public void setIsComplete(boolean b)
	{
		isComplete = b;
	}
	
	public boolean canInteract()
	{
		if(!isComplete)
		{
			if(p.getX()+p.getW()+p.getXA() > x-15 && p.getX()+p.getXA() < x+75 && p.getY()+p.getH()+p.getYA() > y+30 && p.getY()+p.getYA()+p.getH() < y+130)
				return true;
			else
				return false;
		}
		else
		{
			return false;
		}
	}
	
	public boolean getIsOn()
	{
		return isPedOn;
	}
	
	public void setIsOn(boolean b)
	{
		isPedOn = b;
	}
	
	public void collision()
	{
		 if((p.getX()+p.getW()+p.getXA() > x && 
				 p.getX()+p.getXA() < x+60 && 
				 p.getY() + p.getH() + p.getYA() > y+62 && 
				 p.getY() + p.getH() + p.getYA() < y+102))
			{
				p.setXA(p.getXA() * -1);  
				p.setYA(p.getYA() * -1);
			}
	}
	
	public void paint(Graphics2D g)
	{
		if(getIsOn())
		{
			g.drawImage(pedOn, x, y, 60, 102, null);
			isPedOn = true;
		}
		else
		{
			g.drawImage(pedOff, x, y, 60, 102, null);
			isPedOn = false;
		}
	}

}