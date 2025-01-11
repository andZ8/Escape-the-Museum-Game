import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player {
	private int x;
	private int y;
	private int xa;
	private int ya;
	private int w;
	private int h;
	private boolean right = false, left = false;
	private boolean up = false, down = false;
	private char currentRoom = 'm';
	private BufferedImage downStill = null, down1 = null, down2 = null;
	private BufferedImage upStill = null, up1 = null, up2 = null;
	private BufferedImage leftStill = null, left1 = null, left2 = null;
	private int move = 10;
	private int counter = 0;
	private char lastMoved = 'S';
	private MainRoom mr;
	private Security sec;
	private Basement base;
	private boolean inGame = false;
	private boolean canMove = false;
	private Menu menu;
	public Player(char c, Menu menu)
	{
		x = 625;
		y = 401;
		w = 35;
		h = 49;
		this.menu = menu;
		menu.setPlayer(this);
	}
	
	public void setPlayer(Player p)
	{
	}
	public void setSprite(String s)
	{
		try {
			downStill = ImageIO.read(new File(s+"\\sprite forward.png"));
			down1 = ImageIO.read(new File(s+"\\sprite forward1.png"));
			down2 = ImageIO.read(new File(s+"\\sprite forward2.png"));
			
			upStill = ImageIO.read(new File(s+"\\sprite back.png"));
			up1 = ImageIO.read(new File(s+"\\sprite back1.png"));
			up2 = ImageIO.read(new File(s+"\\sprite back2.png"));
			
			leftStill = ImageIO.read(new File(s+"\\sprite left.png"));
			left1 = ImageIO.read(new File(s+"\\sprite left1.png"));
			left2 = ImageIO.read(new File(s+"\\sprite left2.png"));
		} catch (IOException e) {
			System.out.println("No Player Image");
		}
	}
	//Getters and setters
	public void setInGame(boolean b)
	{
		inGame = b;
	}
	
	public boolean getInGame()
	{
		return inGame;
	}
	
	public void setCanMove(boolean b)
	{
		canMove = b;
	}
	
	public boolean getCanMove()
	{
		return canMove;
	}
	
	public void setRooms(MainRoom mr, Security sec, Basement base)
	{
		this.mr = mr;
		this.sec = sec;
		this.base = base;
	}
	
	public void setCurrentRoom(char c)
	{
		currentRoom = c;
	}
	
	public char getCurrentRoom()
	{
		return currentRoom;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public void setX(int x)
	{
		this.x = x;
	}
	
	public void setY(int y)
	{
		this.y = y;
	}
	
	public int getW()
	{
		return w;
	}
	
	public int getH()
	{
		return h;
	}
	
	public int getXA()
	{
		return xa;
	}
	
	public int getYA()
	{
		return ya;
	}
	
	public void setXA(int i)
	{
		xa = i;
	}
	
	public void setYA(int i)
	{
		ya = i;
	}
	
	public void move()
	{
		if(base.getGameStarted())
		{
			if(menu.getCharacter() == 1)
			{
				setSprite("basement\\Sprite");
			}
			else if(menu.getCharacter() == 2)
			{
				setSprite("basement\\MsK");
			}
			else if(menu.getCharacter() == 3)
			{
				setSprite("basement\\Red");
			}
		}
		else
		{
			if(menu.getCharacter() == 1)
			{
				setSprite("Sprite\\robber");
			}
			else if(menu.getCharacter() == 2)
			{
				setSprite("Sprite\\MsK");
			}
			else if(menu.getCharacter() == 3)
			{
				setSprite("Sprite\\red");
			}
		}
		if(right)
			xa = 3;
		
		if(left)
			xa = -3;
		
		if(down)
			ya = 3;
		
		if(up)
			ya = -3;
			
		if(currentRoom == 'm')
			mr.collision();
		if(currentRoom == 's')
			sec.collision();
		if(currentRoom == 'b')
			base.collision();
		
		if(!inGame && canMove)
		{
			x = x + xa;
			y = y + ya;
		}
		else
		{
			left = false;
			down = false;
			right = false;
			up = false;
			lastMoved = 'W';
			xa = 0;
			ya= 0;
		}
		
		if(!down && !up && !left && !right)
			counter = 0;
		else
			counter++;
		
		if(counter > move*2)
			counter = 0;
	}

	public void keyPressed(KeyEvent e)
	{
		 if (e.getKeyCode() == KeyEvent.VK_A)
		 {
			 left = true;
			 right = false;
			 up = false;
			 down = false;
			 ya = 0;
		 }
		 if (e.getKeyCode() == KeyEvent.VK_D)
		 {
			 left = false;
			 right = true;
			 up = false;
			 down = false;
			 ya = 0;
		 }
		 if (e.getKeyCode() == KeyEvent.VK_W)
		 {
			 left = false;
			 right = false;
			 up = true;
			 down = false;
			 xa = 0;
		 }
		 if (e.getKeyCode() == KeyEvent.VK_S)
		 {
			 left = false;
			 right = false;
			 up = false;
			 down = true;
			 xa = 0;
		 }	 
	}
	
	public void keyReleased(KeyEvent e)
	{	
		if (e.getKeyCode() == KeyEvent.VK_A) 
		{
			left = false;
			xa=0;
			lastMoved = 'A';
		}     
		
		if (e.getKeyCode() == KeyEvent.VK_D)
		{
			right = false;
			xa=0;
			lastMoved = 'D';
		}
		
		if (e.getKeyCode() == KeyEvent.VK_W)
		{
			up = false;
			ya=0;
			lastMoved = 'W';
		}
		if (e.getKeyCode() == KeyEvent.VK_S)
		{
			down = false;
			ya=0;
			lastMoved = 'S';
		}
	}
	
	public void paint(Graphics2D g)
	{
		if(!down && !up && !left && !right)
		{
			switch(lastMoved)
			{
			case 'W':
				g.drawImage(upStill, x, y, w, h, null);
				break;
			case 'S':
				g.drawImage(downStill, x, y, w, h, null);
				break;
			case 'A':
				g.drawImage(leftStill, x, y, w, h, null);
				break;
			case 'D':
				g.drawImage(leftStill, x+w, y, -w, h, null);
				break;
			}
		}
		
		if(down)
		{
			if(counter < move)
				g.drawImage(down1, x, y, w, h, null);
			else
				g.drawImage(down2, x, y, w, h, null);
		}
		
		if(up)
		{
			if(counter < move)
				g.drawImage(up1, x, y, w, h, null);
			else
				g.drawImage(up2, x, y, w, h, null);
		}
		
		if(left)
		{
			if(counter < move)
				g.drawImage(left1, x, y, w, h, null);
			else
				g.drawImage(left2, x, y, w, h, null);
		}
		
		if(right)
		{
			if(counter < move)
				g.drawImage(left1, x+w, y, -w, h, null);
			else
				g.drawImage(left2, x+w, y, -w, h, null);
		}
	}

	
}