import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Block {
    private int x;
    private int y;
    private int w;
    private int h;
    private char color;
    private boolean selected = false;
	private BufferedImage block = null;
	private BufferedImage selectedBlock = null;

    private int newX;
    private int newY;

    public Block(int x, int y, int w, int h, char color)
    {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.color = color;
        this.newX = x;
        this.newY = y;
        
        try {
        	if(color == 'b')
        	{
        		block = ImageIO.read(new File("sbp\\red box.png"));
        		selectedBlock = ImageIO.read(new File("sbp\\red box selected.png"));
        	}
        	else if(w == 1 && h == 1)
        	{
        		block = ImageIO.read(new File("sbp\\brown box 1.png"));
        		selectedBlock = ImageIO.read(new File("sbp\\brown box 1 selected.png"));
        	}
        	else
        	{
        		block = ImageIO.read(new File("sbp\\brown box 2.png"));
        		selectedBlock = ImageIO.read(new File("sbp\\brown box 2 selected.png"));
        	}
        
		} catch (IOException e) {
			System.out.println("No block Image");
		}
    }    

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public int getW()
    {
        return w;
    }

    public int getH()
    {
        return h;
    }

    public boolean getSelect()
    {
        return selected;
    }
    public void setNewX(int newX) {
        this.newX = newX;
    }

    public void setNewY(int newY) {
        this.newY = newY;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public char getColor()
    {
        return color;
    }

    // move the blocks (tried to create moving animation by shifting x and y slowly)
    public void move()
    {  
        while (x != newX || y != newY)
        {
            if (x != newX)
            { 
                if (x > newX)
                {
                    x -= 1;
                }
                else
                {
                    x += 1;
                }
                
            }
            else if (y != newY)
            {
                if (y > newY)
                {
                    y -= 1;
                }
                else
                {
                    y += 1;
                }
            }
        }
    }

    public void paint(Graphics2D g2d){

    	if(w == 2)
            g2d.drawImage(block, x, y, 145*2, 144, null);
    	else if(h == 2)
            g2d.drawImage(block, x, y, 144, 145*2, null);
    	else
    		g2d.drawImage(block, x, y, 144, 144, null);
    	
        // shows selected photo
        if (selected)
        {
        	if(w == 2)
                g2d.drawImage(selectedBlock, x, y, 145*2, 144, null);
        	else if(h == 2)
        		g2d.drawImage(selectedBlock, x, y, 144, 145*2, null);
        	else
        		g2d.drawImage(selectedBlock, x, y, 144, 144, null);
        }
    }
}