// needs to figure out what happens after they win but atm everything just disappears

import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SBP{
    private EscapeRoom game;
    private int x;
    private int y;
    private boolean right = false, left = false;
    private boolean up = false, down = false;
    Block[] blocks = new Block[8];
    int mouseX = -10;
    int mouseY = -10;
    boolean collision = false;
    boolean canInteract = true;
    boolean played = false;
	private BufferedImage box = null;
	private BufferedImage textbox = null;
	private int winTimer = 0;
	private Inventory inv;
	
    public SBP(EscapeRoom game, Inventory inv)
    {
    	this.inv = inv;
        this.game = game;
        // all the movable blocks, different colored one ('b') is the one the player is trying to get out 
        blocks[0] = new Block(400, 91, 2, 1, 'b');
        blocks[1] = new Block(700, 91, 1, 1, 'r');
        blocks[2] = new Block(400, 241, 1, 1, 'r');
        blocks[3] = new Block(550, 241, 1, 1, 'r');
        blocks[4] = new Block(400, 391, 1, 2, 'r');
        blocks[5] = new Block(700, 391, 1, 1, 'r');
        blocks[6] = new Block(550, 541, 1, 1, 'r');
        blocks[7] = new Block(700, 541, 1, 1, 'r');
        
        try {
    	box = ImageIO.read(new File("sbp\\box.png"));
    	textbox = ImageIO.read(new File("sbp\\textbox.png"));
        } catch (IOException e) {
			System.out.println("No SBP Image");
		}
    }

    public void reset()
    {
    	 right = false; left = false; up = false; down = false;
    	 mouseX = -10;
    	 mouseY = -10;
    	 collision = false;
    	 canInteract = true;
    	 played = false;
    	 winTimer = 0;
    	 blocks[0] = new Block(400, 91, 2, 1, 'b');
    	 blocks[1] = new Block(700, 91, 1, 1, 'r');
    	 blocks[2] = new Block(400, 241, 1, 1, 'r');
    	 blocks[3] = new Block(550, 241, 1, 1, 'r');
    	 blocks[4] = new Block(400, 391, 1, 2, 'r');
    	 blocks[5] = new Block(700, 391, 1, 1, 'r');
    	 blocks[6] = new Block(550, 541, 1, 1, 'r');
    	 blocks[7] = new Block(700, 541, 1, 1, 'r');
    }
    
    public void keyPressed(KeyEvent e){//Sets the appropriate Boolean to true
      if(!played) {
    	if (e.getKeyCode() == KeyEvent.VK_A){
            left = true;}  
        if (e.getKeyCode() == KeyEvent.VK_D){
            right = true;}
        if (e.getKeyCode() == KeyEvent.VK_W){
            up = true;}
        if (e.getKeyCode() == KeyEvent.VK_S){
            down = true;}

      }
    }

    public void keyReleased(KeyEvent e){//When the key is released, set the Boolean to false
        if (e.getKeyCode() == KeyEvent.VK_A){
            left = false;}
        if (e.getKeyCode() == KeyEvent.VK_D){
            right = false;}
        if (e.getKeyCode() == KeyEvent.VK_W){
            up = false;}
        if (e.getKeyCode() == KeyEvent.VK_S){
            down = false;}}

    public void mousePressed(MouseEvent e) {
        // when mouse is clicked, check where it's clicked and select the appropriate block (block changes color)
        mouseX = e.getX();
        mouseY = e.getY();
        for(Block b: blocks)
        {
            if (mouseX > b.getX() && mouseX < b.getX() + b.getW() * 150 && mouseY > b.getY() && mouseY < b.getY() + b.getH()*150)
            {
                b.setSelected(true);

            }
            else
            {
                b.setSelected(false);
            }
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
    
    public boolean getPlayed()
    {
    	return played;
    }
    
    public boolean getInteract()
    {
    	return canInteract;
    }
    
    public void setInteract(boolean b)
    {
    	canInteract = b;
    }

    public void move(){
        // loop through all blocks, make them move
        if (!win()){
        for (Block b: blocks)
        {
            b.move();
            // if block is selected, make change their newX and newY so they can move (depends on direction of arrow pressed)
            if (b.getSelect())
            {
                // loops through blocks again to check for collision 
                // since all blocks are in a fix "grid" uses a workaround that compares if the block that's being moved has an x and y that matches another block after moving 
                // needs a few other cases for the taller or wider blocks as well as the borders
                // collision variable used in while loop to determine if any of the blocks collided 
                if (left == true)
                {
                    for (Block block: blocks)
                    {
                        if (((b.getX() - 150) == block.getX() + (block.getW()-1) * 150 && ((b.getY() + (b.getH() - 1) * 150 == block.getY() + (block.getH() - 1) * 150) || b.getY() == block.getY())) || b.getX() == 400)
                        { 
                            collision = true;
                        }
                    }
                    if (!collision)
                    {
                        b.setNewX(b.getX() - 150);
                    }
                    collision = false;
                }
                else if (right == true)
                {
                    for (Block block: blocks)
                    {
                        if (((b.getX() + (b.getW() * 150)) == block.getX() && ((b.getY() + (b.getH() - 1) * 150 == block.getY() + (block.getH() - 1) * 150) || b.getY() == block.getY())) || b.getX() == 700 - (b.getW() - 1) * 150)
                        { 
                            collision = true;
                        }
                    }
                    if (!collision)
                    {
                        b.setNewX(b.getX() + 150);
                    }
                    collision = false;
                }
                else if (up == true)
                {
                    for (Block block: blocks)
                    {
                        if (((b.getY() - 150) == block.getY() + (block.getH()-1) * 150 && ((b.getX() == block.getX() + (block.getW() - 1) * 150) || b.getX() == block.getX())) || b.getY() == 91)
                        { 
                            collision = true;
                        }
                    }
                    if (!collision)
                    {
                        b.setNewY(b.getY()-150);
                    }
                    
                    collision = false;
                }
                else if (down == true)
                {
                    for (Block block: blocks)
                    {
                        if (((b.getY() + (b.getH() * 150)) == block.getY() && ((b.getX() + (b.getW() - 1) * 150 == block.getX() + (block.getW() - 1) * 150) || b.getX() == block.getX())) || b.getY() ==  541 - (b.getH() - 1) * 150)
                        { 
                            collision = true;
                        }
                    }
                    if (!collision)
                    {
                        b.setNewY(b.getY() + 150);
                    }
                    collision = false;
                }
            }
        }
    }
    }

    // checks if the different color block reached the end, return true if it did
    public boolean win()
    {
        for (Block b: blocks)
        {
            if (b.getColor() == 'b')
            {
                if (b.getX() == 400 && b.getY() == 541)
                {
                    b.setNewY(b.getX() + 150);
                   played = true;
                    return true;
                }
            }
        }
        return false;
    }

    
    public void paint(Graphics2D g2d){
        
        if (!win()){
        	g2d.setColor(Color.LIGHT_GRAY);
        	g2d.fillRect(330, 30, 590, 720);
        	g2d.drawImage(box, 345, 39, 554, 702, null); //draws main box

            for (Block b:blocks)
            {
                b.paint(g2d);
            }
            inv.paint(g2d);
            g2d.drawImage(textbox, 0, 812, 1250, 150, null);
        }
    }
    
}