import java.awt.*;
import javax.imageio.*;
import java.awt.image.*;
import java.io.*;

public class Keys extends Item{
    
    private boolean isFound;
    private boolean isUsed;
    private int keyNum;
    private BufferedImage key = null;
    private int size = 500;
    private int y = 200;
    private int x = 401;
    private int h = 100;
    private boolean shrunk = false;
    private int invNum;
    public Keys(int keyNum)
    {
        this.keyNum = keyNum;
        isFound = false;
        isUsed = false;
        // use different key image based on which key is added in the constructor
        try {
            if (keyNum == 1)
            {
			    key = ImageIO.read(new File("Key\\key1.png"));
            }
            else if (keyNum == 2)
            {
			    key = ImageIO.read(new File("Key\\key2.png"));
            }
            else if (keyNum == 3)
            {
			    key = ImageIO.read(new File("Key\\key3.png"));
            }
            else if (keyNum == 4)
            {
			    key = ImageIO.read(new File("Key\\key4.png"));
            }
		} catch (IOException e) {
			System.out.println("No Key Image");
		}
    }

    public void paint(Graphics2D g)
    { 	
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
    		x = 23+(45*(keyNum-1));
    		shrunk = true;
    	}
    	
           g.drawImage(key, x, y, size, size, null);
    }
}