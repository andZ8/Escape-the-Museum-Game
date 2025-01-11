import java.awt.*;
import javax.imageio.*;
import java.awt.image.*;
import java.io.*;

public class Shovel extends Item{
    
    private boolean isFound;
    private boolean isUsed;
    private BufferedImage shovel = null;
    private int size = 500;
    private int y = 200;
    private int x = 401;
    private int h = 100;
    private boolean shrunk = false;
    private int invNum;
    public Shovel(int invNum)
    {
    	this.invNum = invNum;
        isFound = false;
        isUsed = false;
        // use different key image based on which key is added in the constructor
        try {
            shovel = ImageIO.read(new File("Items\\shovel.png"));
		} catch (IOException e) {
			System.out.println("No Shovel Image");
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
    		x = 23+(45*invNum-1);
    		shrunk = true;
    	}
    	
           g.drawImage(shovel, x, y, size, size, null);
    }
}