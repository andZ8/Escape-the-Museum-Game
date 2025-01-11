import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;

public class Inventory {
    private int x = 10;
    private int y = 757;
    private int w = 278;
    private int h = 57;
    private EscapeRoom escape;
    private BufferedImage inv;
	private Keys[] key = new Keys[5];


    public Inventory(EscapeRoom escape, Keys[] key)
    {
        this.escape = escape;
        this.key = key;
        
        try {
			inv = ImageIO.read(new File("inventory.png"));
        }
        catch (IOException e) {
			System.out.println("No Inventory Image");
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

    public void paint(Graphics2D g){
        g.drawImage(inv, x, y, w, h, null);
    }
}