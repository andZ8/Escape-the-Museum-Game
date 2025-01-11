// all you need to do is create a menu object in EscapeRoom.java and also paint it in the paint method
import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;

public class Menu {

    private BufferedImage menu;
    // 0 is start with the play button visible, 1 is storyline, 2 is controls, 3 is character select, anything else means menu is gone
    private int menuPage = 0;
    private int mouseX;
    private int mouseY;
    // used to store which character is being used, check this variable in player where the player image is loaded
    // 1 is default character, 2 and 3 are other options
    private int character = 1;
    private BufferedImage char1 = null, char2 = null, char3 = null;
    private BufferedImage[] selectedChar = {null, null, null};
    private boolean[] clickedChar = {false, false, false};
    private boolean[] chara = {false, false, false};
    private boolean frontArrow, backArrow;
    private BufferedImage next = null, nextSel = null;
    private BufferedImage background = null, instru = null, base = null, play = null, story = null, backstory = null, quit = null, win = null;
    private boolean playSel, storySel;
    private Player p;
    private boolean close = false;
    private boolean quitSel = false;

    public Menu()
    {
        try {
			char1 = ImageIO.read(new File("Sprite\\robber\\sprite forward.png"));
			char2 = ImageIO.read(new File("Sprite\\MsK\\sprite forward.png"));
			char3 = ImageIO.read(new File("Sprite\\red\\sprite forward.png"));
   			selectedChar[0] = ImageIO.read(new File("menu\\robber.png"));
   			selectedChar[1] = ImageIO.read(new File("menu\\msk.png"));
   			selectedChar[2] = ImageIO.read(new File("menu\\red.png"));
   			next = ImageIO.read(new File("menu\\arrow.png"));
   			nextSel = ImageIO.read(new File("menu\\arrowSel.png"));
			background = ImageIO.read(new File("Backgrounds\\menu backgroumd.png"));
			instru = ImageIO.read(new File("menu\\instructions.png"));
			base = ImageIO.read(new File("menu\\base.png"));
			play = ImageIO.read(new File("menu\\play.png"));
			story = ImageIO.read(new File("menu\\story.png"));
			backstory = ImageIO.read(new File("menu\\backstory.png"));
			quit = ImageIO.read(new File("menu\\quit.png"));
			win = ImageIO.read(new File("menu\\end.png"));

		} catch (IOException e) {
			System.out.println("No menu Image");
		}
    }    

    public void setPlayer (Player p)
    {
    	this.p = p;
    }
    // use in if statement in player for loading character sprite
    public int getCharacter()
    {
        return character;
    }

    public void winPage()
    {
    	menuPage = 5;
    }
    public void mouseReleased(MouseEvent e) {
        // when mouse is clicked, check where it's clicked and select the appropriate block (block changes color)
        mouseX = e.getX();
        mouseY = e.getY();
        
        if (menuPage == 0)
        {
            // coordinates from the rectange below (also commented)
            if (mouse(470, 423, 310, 80))
            {
                menuPage = 1;
                playSel = false;
            }
            else if (mouse(540, 561, 170, 80))
            {
                // story
                menuPage = 2;
                storySel = false;
            }
        }
        else if (menuPage == 1)
        {
        	if(mouse(1020, 862, 210, 80))
        	{
        		menuPage = 3;
        	}
        	else if(mouse(20, 862, 210, 80))
        	{
        		menuPage = 0;
        	}
            
        }
        else if (menuPage == 2)
        {
        	if(mouse(1020, 862, 210, 80))
            {
                menuPage = 0;
            }
        }
        else if (menuPage == 3)
        {
            if (mouseX > 247 && mouseX < 247 + 170 && mouseY > 356 && mouseY < 356 + 250)
            {
                character = 1;
                clickedChar[0] = true;
                clickedChar[1] = false;
                clickedChar[2] = false;
            }
            else if (mouseX > 531 && mouseX < 531 + 170 && mouseY > 356 && mouseY < 356 + 250)
            {
                character = 2;
                clickedChar[0] = false;
                clickedChar[1] = true;
                clickedChar[2] = false;
            }
            else if (mouseX > 814 && mouseX < 814 + 170 && mouseY > 356 && mouseY < 356 + 250)
            {
                character = 3;
                clickedChar[0] = false;
                clickedChar[1] = false;
                clickedChar[2] = true;
            }
            else if(mouse(1020, 862, 210, 80))
            {
                menuPage = 4;
                p.setCanMove(true);
            }
            else if(mouse(20, 862, 210, 80))
            {
            	menuPage = 1;
            }
        }
        
        if(menuPage == 5)
        {
        	if(mouse(520, 685, 210, 110))
        	{
        		close = true;
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
    
    public void mouseMoved(MouseEvent p) {
    	 mouseX = p.getX();
         mouseY = p.getY();
         
        if (menuPage == 0)
         {
        	if (mouse(470, 423, 310, 80))
        	{
        		playSel = true;
        	}
        	else
	        {
        		playSel = false;
	        }
	        if (mouse(540, 561, 170, 80))
	        {
	            storySel = true;
	        }
	        else
            {
	        	storySel = false;
            }
         }         
         if (menuPage == 1)
         {
         	if(mouse(1020, 862, 210, 80))
         	{
         		frontArrow = true;
         	}
         	else 
         	{
         		frontArrow = false;
         	}
         		
         	if(mouse(20, 862, 210, 80))
         	{
         		backArrow = true;
         	}
         	else
         	{
         		backArrow = false;
         	}
             
         }
         
         if (menuPage == 2)
         {
         	if(mouse(1020, 862, 210, 80))
         	{
         		frontArrow = true;
         	}
         	else 
         	{
         		frontArrow = false;
         	}
         }
         else if (menuPage == 3)
         {
             if (mouseX > 247 && mouseX < 247 + 170 && mouseY > 356 && mouseY < 356 + 250)
             {
            	chara[0] = true;
             }
             else 
             {
            	 chara[0] = false;
             }
            	 
            
             if (mouseX > 531 && mouseX < 531 + 170 && mouseY > 356 && mouseY < 356 + 250)
             {
            	 chara[1] = true;
             }
             else 
             {
            	 chara[1] = false;
             }
            	 
             if (mouseX > 814 && mouseX < 814 + 170 && mouseY > 356 && mouseY < 356 + 250)
             {
            	 chara[2] = true;
             }
             else 
             {
            	 chara[2] = false;
             }
             
             if(mouse(1020, 862, 210, 80))
             {
          		frontArrow = true;
             }
             else 
             {
          		frontArrow = false;
             }
             
             if(mouse(20, 862, 210, 80))
             {
          		backArrow = true;
             }
             else
             {
          		backArrow = false;
             }
         }
         if(menuPage == 5)
         {
        	 if(mouse(520, 685, 210, 110))
        	 {
        		 quitSel = true;
        	 }
        	 else
        	 {
        		 quitSel = false;
        	 }
         }
	}
    
    public boolean close()
    {
    	return close;
    }
    
    public void paint(Graphics2D g){
        if (menuPage == 0)
        {
            // should show play button with other stuff
            g.drawImage(background, 0, 0, 1250, 962, null);
            if(playSel)
            {
                g.drawImage(play, 0, 0, 1250, 962, null);
            }
            if(storySel)
            {
                g.drawImage(story, 0, 0, 1250, 962, null);
            }
        }
        else if (menuPage == 1)
        {
            // should show story of the game after play button is pressed
            g.drawImage(instru, 0, 0, 1250, 962, null);

            if(!frontArrow)
            {
                g.drawImage(next, 1020, 861, 210, 90, null);
            }
            else
            {
                g.drawImage(nextSel, 1010, 851, 230, 110, null);
            }
            if(!backArrow)
            {
                g.drawImage(next, 230, 861, -210, 90, null);
            }
            else
            {
                g.drawImage(nextSel, 240, 851, -230, 110, null);
            }
        }
        else if (menuPage == 2)
        {
            // should show controls of the game
            g.drawImage(backstory, 0, 0, 1250, 962, null);

            if(!frontArrow)
            {
                g.drawImage(next, 1020, 861, 210, 90, null);
            }
            else
            {
                g.drawImage(nextSel, 1010, 851, 230, 110, null);
            }

        }
        else if (menuPage == 3)
        {
            g.drawImage(base, 0, 0, 1250, 962, null);

            g.drawImage(char1, 247, 356, 170, 250, null);
            g.drawImage(char2, 531, 356, 170, 250, null);
            g.drawImage(char3, 814, 356, 170, 250, null);

            if(chara[0] || clickedChar[0])
            {
            	g.drawImage(selectedChar[0], 237, 346, 190, 270, null);
            }
            if(chara[1] || clickedChar[1])
            {
            	g.drawImage(selectedChar[1], 521, 346, 190, 270, null);
            }
            if(chara[2] || clickedChar[2])
            {
            	g.drawImage(selectedChar[2], 804, 346, 190, 270, null);
            }
            
            if(!frontArrow)
            {
                g.drawImage(next, 1020, 861, 210, 90, null);
            }
            else
            {
                g.drawImage(nextSel, 1010, 851, 230, 110, null);
            }
            if(!backArrow)
            {
                g.drawImage(next, 230, 861, -210, 90, null);
            }
            else
            {
                g.drawImage(nextSel, 240, 851, -230, 110, null);
            }
        }
        else if (menuPage == 5)
        {
            g.drawImage(win, 0, 0, 1250, 962, null);
            if(quitSel)
            {
                g.drawImage(quit, 0, 0, 1250, 962, null);
            } 
        }
    }
}
