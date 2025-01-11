import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpotDiff {
	private int x = 40;
	private int y = 100;
	private int w = 575;
	private int h = 500;
	private int mouseX;
	private int mouseY;
	private boolean[] diff = {false, false, false, false, false};
	//paintings
 	private BufferedImage P1 = null;
	private BufferedImage P2 = null;
	//textbox
	private BufferedImage D1 = null;
	private BufferedImage D2 = null;
	private BufferedImage D3 = null;
	private BufferedImage D4 = null;
	private BufferedImage D5 = null;
	private BufferedImage wall = null;
	private BufferedImage textbox = null;
	private Player p;
	private boolean interact = false; //if player clicks on painting while while being in range they can play the game
	private int winTimer = 0;
	private boolean win = false;
	private Inventory inv;
	
	public SpotDiff(Player p, Inventory inv)
	{
		this.inv = inv;
		try {
			wall = ImageIO.read(new File("wall1.png"));
			P1 = ImageIO.read(new File("SD\\StarryNight.png"));
			P2 = ImageIO.read(new File("SD\\StarryNight2.png"));
			D5 = ImageIO.read(new File ("SD\\diff5.png"));
			D4 = ImageIO.read(new File ("SD\\diff4.png"));
			D3 = ImageIO.read(new File ("SD\\diff3.png"));
			D2 = ImageIO.read(new File ("SD\\diff2.png"));
			D1 = ImageIO.read(new File ("SD\\diff1.png"));
			
	    	textbox = ImageIO.read(new File("SD\\textbox.png"));

		} catch (IOException e) {
			System.out.println("No SpotDiff Image");
		}
		
		this.p = p;
	}
	
	public boolean getInteract(){
		return interact;
	}
	
	public void setInteract(boolean a) {
		interact = a;
	}
	
	public void setTimer()
    {
    	winTimer++;
    }
    
    public int getTimer()
    {
    	return winTimer;
    }
    
	public void mousePressed(MouseEvent e) {
	        mouseX = e.getX();
	        mouseY = e.getY();
	        //System.out.println(e.getX() + ", " +  e.getY());
	        //if plater clicks a difference set corresponding difference in array to true (found)
            if ((mouseX > 975 && mouseX < 1050 && mouseY > 240 && mouseY < 300) || (mouseX > 400 && mouseX < 475 && mouseY > 240 && mouseY < 300))//swirl                                                            )//swirl
            {
            	diff[0] = true;
            }
            	
            else if ((mouseX > 720 && mouseX < 740 && mouseY > 220 && mouseY < 240) || (mouseX > 145 && mouseX < 165 && mouseY > 220 && mouseY < 240))//star
            {
            	diff[1] = true;
            }
            
            else if ((mouseX > 690 && mouseX < 750 && mouseY > 470 && mouseY < 520) || (mouseX > 115 && mouseX < 175 && mouseY > 470 && mouseY < 520))//building
            {
            	diff[2] = true;
            }
            
            else if ((mouseX > 1033 && mouseX < 1081 && mouseY > 455 && mouseY < 475) || (mouseX > 458 && mouseX < 506 && mouseY > 455 && mouseY < 475))//house
            {
            	diff[3] = true;
            }
            
            else if ((mouseX > 958 && mouseX < 972 && mouseY > 515 && mouseY < 525) || (mouseX > 383 && mouseX < 397 && mouseY > 515 && mouseY < 525))//light
            {
            	diff[4] = true;
            }
	            	
	    }
	 
	 public int mousePressedX(MouseEvent e) {
		 return e.getX();
	    }
	 public int mousePressedY(MouseEvent e) {
		 return e.getY();
	    }
	 
	 public boolean canInteract() //check if in range of painting
	 {
		 if (p.getX() > 480 && p.getX() < 540 && p.getY() > 0 && p.getY() < 90)
		 {
			 return true;
		 }
		 return false;
	 }
	 
	 public boolean getDiffNum()
	 {
		 return win;
	 }
	
	public void paint(Graphics2D g)
	{
		int diffNum = 0; //how many difference player has found 
		//check how many difference player has found
		for (int i = 0; i < diff.length; i++) 
		{
			if (diff[i] == true)
			{
				diffNum += 1;
			}
		}
		
		if (diffNum < 5) //if player has not found all differences keep playing
		{
			p.setInGame(true); //don't let player move
			g.drawImage(wall, 0, 0, 1250, 962, null);
			inv.paint(g);
			
			g.drawImage(P1, x, y, w, h, null);
			g.drawImage(P2, x+575, y, w, h, null);
			g.setStroke(new BasicStroke (4));
			g.setColor(Color.RED);
			
			//paint textboxes based on how many differences found
			if (diffNum == 0)
			{
				g.drawImage(D5, 990, 625, 200, 100, null);
			}
			else if (diffNum == 1)
			{
				g.drawImage(D4, 990, 625, 200, 100, null);
			}
			else if (diffNum == 2)
			{
				g.drawImage(D3, 990, 625, 200, 100, null);
			}
			else if (diffNum == 3)
			{
				g.drawImage(D2, 990, 625, 200, 100, null);
			}
			else if (diffNum == 4)
			{
				g.drawImage(D1, 990, 625, 200, 100, null);
			}
			
			//keep painting circles over found differences 
			if (diff[0] == true)
			{
				g.drawOval(975, 230, 75, 75);
				g.drawOval(400, 235, 75, 75);
			}
			if (diff[1] == true)
			{
				g.drawOval(710, 210, 40, 40);
				g.drawOval(135, 215, 40, 40);
			}
			if (diff[2] == true)
			{
				g.drawOval(690, 450, 75, 75);
				g.drawOval(115, 455, 75, 75);
			}
			if (diff[3] == true)
			{
				g.drawOval(1030, 445, 50, 50);
				g.drawOval(455, 450, 50, 50);
			}
			if (diff[4] == true)
			{
				g.drawOval(952, 510, 25, 25);
				g.drawOval(377, 515, 25, 25);
			}
			
            g.drawImage(textbox, 0, 812, 1250, 150, null);


		}
		
		else { //stop playing game and let player move
			p.setInGame(false);
			win = true;
		}
	}
}

