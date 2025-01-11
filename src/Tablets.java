import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Tablets extends Item{

    private EscapeRoom game;
    private boolean isFound;
    private boolean isUsed;
    private int tabNum;
    private BufferedImage tab;
    private int size = 500;
    private int y = 200;
    private int x = 401;
    private int h = 100;
    private boolean shrunk = false;
    private int invNum;
    
    public Tablets(int tabNum, int invNum)
    {
        this.tabNum = tabNum;
        this.invNum = invNum;
        isFound = false;
        isUsed = false;
        // use different tablet image based on which tablet is added in the constructor
        try {
            if (tabNum == 1)
            {
			    tab = ImageIO.read(new File("items\\bottomTablet.png"));
            }
            else if (tabNum == 2)
            {
			    tab = ImageIO.read(new File("items\\topTablet.png"));
            }
            else if (tabNum == 3)
            {
			    tab = ImageIO.read(new File("items\\rightTablet.png"));
            }
		} catch (IOException e) {
			System.out.println("No Tablet Image");
		}
    }
    
    public void paint(Graphics2D g){
        
    	if(h > 0)
    		h--;
    	if(h <= 0 && !shrunk)
    	{
    		if(size >= 0)
    		{
    			size-= 6;
    			x += 3;
    			y += 3;
    		}

    	}
    	
    	if(size <= 0)
    	{
    		size = 28;
    		y = 770;
    		x = 23+(45*(invNum-1));
    		shrunk = true;
    	}
    	
           g.drawImage(tab, x, y, size, size, null);
    }
}