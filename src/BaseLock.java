import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class BaseLock {

	private BufferedImage base = null;
	private BufferedImage[] i = {null, null, null, null, null, null, null, null, null, null};
	
	private int dialY = 405;
	private int dialH = 230;
	private int dialW = 92;
	private int dialX1 = 372;
	private int dialX2 = dialX1+dialW+46;
 	private int dialX3 = dialX2+dialW+46;
	private int dialX4 = dialX3+dialW+46;
	
	private int[]dialNum = {0, 0, 0, 0};
	private boolean[] dialOn = {false, false, false, false};

		
	private boolean locked = true;
	private boolean display = false;
	
	private int mouseX = 0;
	private int mouseY = 0;
	public BaseLock()
	{
		
		try {
			i[0] = ImageIO.read(new File("BaseLock\\lock0.png"));
			i[1] = ImageIO.read(new File("BaseLock\\lock1.png"));
			i[2] = ImageIO.read(new File("BaseLock\\lock2.png"));
			i[3] = ImageIO.read(new File("BaseLock\\lock3.png"));
			i[4] = ImageIO.read(new File("BaseLock\\lock4.png"));
			i[5] = ImageIO.read(new File("BaseLock\\lock5.png"));
			i[6] = ImageIO.read(new File("BaseLock\\lock6.png"));
			i[7] = ImageIO.read(new File("BaseLock\\lock7.png"));
			i[8] = ImageIO.read(new File("BaseLock\\lock8.png"));
			i[9] = ImageIO.read(new File("BaseLock\\lock9.png"));
			base = ImageIO.read(new File("BaseLock\\lock.png"));

		} catch (IOException e) {
			System.out.println("No Lock Image");
		}
	}
	
	public void keyPressed(KeyEvent e)
	{
		if (getDisplay())
		{
			if (e.getKeyCode() == KeyEvent.VK_W)
			{
				
				for(int a = 0; a < 4; a++)
				{
					if (dialOn[a])
					{
						dialNum[a]++;
						if (dialNum[a] > 9)
						{
							dialNum[a] = 0;
						}
					}
				}
			}
			else if (e.getKeyCode() == KeyEvent.VK_S)
			{
				for(int a = 3; a >= 0; a--)
				{
					if (dialOn[a])
					{
						dialNum[a]--;
						if (dialNum[a] < 0)
						{
							dialNum[a] = 9;
						}
					}
				}	
			}
		}
	}
	
	public void mousePressed(MouseEvent e)
	{
		mouseX = e.getX();
	    mouseY = e.getY();
	     
	     if (mouseX > dialX1 && mouseX < dialX1+dialW && mouseY > dialY && mouseY < dialY+dialH)
	     {
	    	 for(int a = 0; a < 4; a++)
				{
					dialOn[a] = false;
				}	    	 
	    	 dialOn[0] = true;
	     }
	     else if (mouseX > dialX2 && mouseX < dialX2+dialW && mouseY > dialY && mouseY < dialY+dialH)
	     {
	    	 for(int a = 0; a < 4; a++)
				{
					dialOn[a] = false;
				}	    	 
	    	 dialOn[1] = true;
	     }
	     else if (mouseX > dialX3 && mouseX < dialX3+dialW && mouseY > dialY && mouseY < dialY+dialH)
	     {
	    	 for(int a = 0; a < 4; a++)
				{
					dialOn[a] = false;
				}	    	 
	    	 dialOn[2] = true;
	     }
	     else if (mouseX > dialX4 && mouseX < dialX4+dialW && mouseY > dialY && mouseY < dialY+dialH)	     
	     {
	    	 for(int a = 0; a < 4; a++)
				{
					dialOn[a] = false;
				}	    	 
	    	 dialOn[3] = true;
	     }
	}
	
	public boolean getLocked()
	{
		return locked;
	}
	
	public boolean getDisplay()
	{
		return display;
	}
	
	public void setDisplay(boolean a)
	{
		display = a;
	}
	
	public void paint (Graphics2D g)
	{
		g.drawImage(base, 0, 50, 1250, 802, null);
		
		g.drawImage(i[dialNum[0]], dialX1, dialY, dialW, dialH, null);
		g.drawImage(i[dialNum[1]], dialX2, dialY, dialW, dialH, null);
		g.drawImage(i[dialNum[2]], dialX3, dialY, dialW, dialH, null);
		g.drawImage(i[dialNum[3]], dialX4, dialY, dialW, dialH, null);
			
		if (dialNum[0] == 1 && dialNum[1] == 0 && dialNum[2] == 4 && dialNum[3] == 9)
		{
			locked = false;
		}
	}
}
