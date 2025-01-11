import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
public class Basement extends JPanel {
	
		private Player p;
		private int pX;
		private int pY;
		private BufferedImage background = null;
		private BufferedImage noLights = null, noLightsFloor = null;
		private BufferedImage light1 = null, light2 = null, light3 = null, redLights = null, greenLights = null, greenTile = null, blueTile = null;
		private BufferedImage floor1 = null, floor2 = null, floor3 = null;
		private BufferedImage green = null, blue = null, red = null;
		private BufferedImage buttonOn = null, buttonOff = null;
		private int counter;
		private int tileSpeed = 60;
		private int wrongCounter = 0;
		private int [] tilesV = new int[4];
		private int [] tilesH = new int[4];
		private int tX = 544;
		private int tY = 284;
		private int tW = 62;
		private int tH = 62;
		private int pV = 0; //what column is player in
		private int pH = 0; //what row is player in
		private int [] pattern = new int[5];
		private boolean click = false;
		private boolean gameStarted = false;
		private boolean win = false;
		
		private int currTileStep = 0;
		private int currRound = 0;
		
		private boolean wrongInput = false;
		private int inputNum = 0;
		private int numCorrectInput = 0;
		
		private Timer tileTimer; // flashing tiles
		private Timer tileTimerP; // player clicked tile
		
		private boolean isTileVis = false;
		private boolean isTileVisP = false;
		
		private Keys[] key = new Keys[4];
		private BufferedImage textbox = null;
		private int timer = 0;


	public Basement(Player player, Keys[] key)
	{
		this.key = key;
		//basement background
		try {
			background = ImageIO.read(new File("Backgrounds\\basement background.png"));
			noLights = ImageIO.read(new File("basement\\noLights.png"));
			noLightsFloor = ImageIO.read(new File("basement\\noLightsFloor.png"));
			
			light1 = ImageIO.read(new File("basement\\lights1.png"));
			light2 = ImageIO.read(new File("basement\\lights2.png"));
			light3 = ImageIO.read(new File("basement\\lights3.png"));
			
			redLights = ImageIO.read(new File("basement\\redLights.png"));
			greenLights = ImageIO.read(new File("basement\\greenLights.png"));
			
			greenTile = ImageIO.read(new File("basement\\greenTileLight.png"));
			blueTile = ImageIO.read(new File("basement\\blueTileLight.png"));

			floor1 = ImageIO.read(new File("basement\\floor1.png"));
			floor2 = ImageIO.read(new File("basement\\floor2.png"));
			floor3 = ImageIO.read(new File("basement\\floor3.png"));
			
			green = ImageIO.read(new File("basement\\greenTile.png"));
			blue = ImageIO.read(new File("basement\\blueTile.png"));
			red = ImageIO.read(new File("basement\\redTile.png"));

			buttonOn = ImageIO.read(new File("basement\\buttonOn.png"));
			buttonOff = ImageIO.read(new File("basement\\buttonOff.png"));

	    	textbox = ImageIO.read(new File("winTextbox.png"));


		} catch (IOException e) {
			System.out.println("No Basement Image");
		}
		
		p = player;
		
		tileTimer = new Timer(900, e -> {
			isTileVis = !isTileVis;
			
			if (isTileVis)
			{
				if (currTileStep < currRound) // Increment the tile count
				{
					currTileStep++;
				}
			}
			else
			{
				if (currTileStep == currRound) // stop timer when finished showing round pattern
				{
					tileTimer.stop();
					p.setInGame(false);
				}
			}
			
			repaint();
		});
		
		tileTimerP = new Timer(200, e -> {
			isTileVisP = !isTileVisP;
			
			if (!isTileVisP)
			{
				tileTimerP.stop();
			}
			
			repaint();
		});	
	}
	
	
	public void keyPressed(KeyEvent e)
	{
		if(!p.getInGame())
		{
			if (e.getKeyCode() == KeyEvent.VK_E)
			{
				if (hitboxes(419, 436, 50, 50) && gameStarted != true && !win)//button
				{
					for (int i = 0; i < pattern.length; i++)
					{
						pattern[i] = (int)(16 * Math.random());
					}
					gameStarted = true;
			        currRound = 0;
			        currTileStep = -1;
			        tileTimer.start();
					p.setInGame(true);
					
				}
				// Check if player is inside grid
				if (pX > tX && pX < tX+(tW*4)+6 && pY > tY && pY < tY+(tH*4)+6 && gameStarted)
				{
					// Player has clicked a tile
					click = true;
					tileTimerP.start();
					inputNum++;
					// Find which tile player is standing on
					// Tile column
					for (int h = 0; h < 4; h++)
					{	
						if (pX > tX+h*tW+h*2 && pX < tX+(h+1)*tW+h*2)
						{
							pH = h;
						}
					}
					
					// Tile row
					for (int v = 0; v < 4; v++)
					{
						
						if (pY > tY+v*tH+v*2 && pY < tY+(v+1)*tH+v*2)
						{
							pV = v;
						}
					}
					
						
				//	System.out.println(pattern[inputNum-1]);
					
					// Check if clicked tile matches correct pattern
					if (pH == pattern[inputNum-1]%4 && pV == pattern[(inputNum-1)]/4)
					{
						numCorrectInput++;
						wrongInput = false;
					}
					else // WRONG restart game
					{
					
						wrongInput = true;
						inputNum = 0;
						numCorrectInput = 0;
						
						gameStarted = false;
						tileTimer.stop();
						wrongCounter = 1;
						
					}
					
					// Check if all player inputs matched the pattern for the round (currRound starts at 0)
					if (numCorrectInput == currRound + 1)
					{
						if (currRound == pattern.length - 1) // Finished last round
						{
							win = true;
						}
						else
						{
							inputNum = 0;
							numCorrectInput = 0;
							// Increment round and restart tile counter
							currRound = currRound + 1;
							currTileStep = -1;
							tileTimer.start();
							p.setInGame(true);
						}
					}
					
				}	
			}
		}
	}
	
	public boolean getGameStarted()
	{
		return gameStarted;
	}
	
	public boolean hitboxes(int boxX, int boxY, int boxW, int boxH)
	{
		if(p.getX()+p.getW()+p.getXA() > boxX &&
				p.getX()+p.getXA() < boxX + boxW && 
				p.getY()+p.getH()+p.getYA() > boxY && 
				p.getY()+p.getYA()+p.getH() < boxY + boxH)
			return true;
		else
			return false;
	}
	
	public void collision()
	{
		if(hitboxes(0, 0, 372, 802) || //left wall
				hitboxes(0, 0, 1250, 255) || //top wall
				hitboxes(878, 0, 372, 802) || //right wall
				hitboxes(0, 660, 1250, 142))//bottom wall
		{
			p.setXA(p.getXA() * -1);  
			p.setYA(p.getYA() * -1);
		}
		
		if(hitboxes(429, 444, 30, 26))//button
		{
			p.setXA(p.getXA() * -1);  
			p.setYA(p.getYA() * -1);
		}
		
		if(!gameStarted || win)
		{
			if(hitboxes(750, 650, 70, 49))
			 {
				 p.setCurrentRoom('m');
				 p.setX(1134);
				 p.setY(560);
			 }
		}
		
	}
	public void paint(Graphics2D g)
	{
		//counter for dormant disco state
		if(counter >= tileSpeed*3)
			counter = 0;
		else
			counter++;
		
		pX = p.getX()+17;
		pY = p.getY()+49;
		
		
		
		//dormant disco state floor
		g.drawImage(background, 0, 0, 1250, 802, null);
		
		
		if(!gameStarted)
		{
			if(wrongCounter >= 1)
			{
				g.drawImage(noLightsFloor, 0, 0, 1250, 802, null);

				for (int h = 0; h < tilesH.length; h++)
				{
					for (int v = 0; v < tilesV.length; v++)
					{
						g.drawImage(red, tX + tW*h + h*2, tY + tH*v + v*2, tW, tH, null);
					}
				}
				wrongCounter++;
				if(wrongCounter == 100)
				{
					wrongCounter = 0;
				}
				if (p.getY() >= 410)
				{
					g.drawImage(buttonOff, 425, 410, 38, 56, null);
				}		
				p.paint(g);
				if (p.getY() < 410)
				{
					g.drawImage(buttonOff, 425, 410, 38, 56, null);
				}
				
				g.drawImage(redLights, 0, 0, 1250, 802, null);
			}
			else if (win == true)
			{
				g.drawImage(noLightsFloor, 0, 0, 1250, 802, null);

				if (win == true)
				{
					for (int h = 0; h < tilesH.length; h++)
					{
						for (int v = 0; v < tilesV.length; v++)
						{
							g.drawImage(green, tX + tW*h + h*2, tY + tH*v + v*2, tW, tH, null);
						}
					}
				}
				if (p.getY() >= 410)
				{
					g.drawImage(buttonOff, 425, 410, 38, 56, null);
				}		
				p.paint(g);
				if (p.getY() < 410)
				{
					g.drawImage(buttonOff, 425, 410, 38, 56, null);
				}
				
				g.drawImage(greenLights,  0, 0, 1250, 802, null);
			}
			else
			{
			if(counter <= tileSpeed) 
				g.drawImage(floor1, 0, 0, 1250, 802, null);
			
			if(counter <= tileSpeed*2 && counter > tileSpeed) 
				g.drawImage(floor2, 0, 0, 1250, 802, null);
	
			if(counter <= tileSpeed*3 && counter > tileSpeed*2) 
				g.drawImage(floor3, 0, 0, 1250, 802, null);
	
			if (p.getY() >= 410)
			{
				g.drawImage(buttonOff, 425, 410, 38, 56, null);
			}		
			p.paint(g);
			if (p.getY() < 410)
			{
				g.drawImage(buttonOff, 425, 410, 38, 56, null);
			}
			
			if(counter <= tileSpeed) 
				g.drawImage(light1, 0, 0, 1250, 802, null);
			
			if(counter <= tileSpeed*2 && counter > tileSpeed) 
				g.drawImage(light2, 0, 0, 1250, 802, null);
	
			if(counter <= tileSpeed*3 && counter > tileSpeed*2) 
				g.drawImage(light3, 0, 0, 1250, 802, null);
			}
			
			
		}
		else
		{
			g.drawImage(noLights, 0, 0, 1250, 802, null);
			g.drawImage(noLightsFloor, 0, 0, 1250, 802, null);
			
			// Display flashing tiles for player to memorize	
			if (isTileVis) {
				g.drawImage(blue, tX + ((pattern[currTileStep]) % 4)*(tW+2) ,tY + (pattern[currTileStep])/4*(tH+2) , tW, tH, null);
			}
			
			// Display tile that player clicks
			if (click == true)
			{
				if (wrongInput == false && isTileVisP)
				{
					g.drawImage(green, tX+tW*pH+pH*2, tY+tH*pV+pV*2, tW, tH, null);
	
				}
			}
			
			if (p.getY() >= 410)
			{
				g.drawImage(buttonOn, 425, 410, 38, 56, null);
			}		
			p.paint(g);
			if (p.getY() < 410)
			{
				g.drawImage(buttonOn, 425, 410, 38, 56, null);
			}
			
			if (isTileVis) {
				g.drawImage(blueTile, tX + ((pattern[currTileStep]) % 4)*(tW+2)-31 ,tY + (pattern[currTileStep])/4*(tH+2)-31 , tW*2, tH*2, null);
				
			}
			
			if (click == true)
			{
				if (wrongInput == false && isTileVisP)
				{
					g.drawImage(greenTile, tX+tW*pH+pH*2-31, tY+tH*pV+pV*2-31, tW*2, tH*2, null);
	
				}
			}
			
			if(win == true)
			{
				gameStarted = false;
			}
		}
		
		
		if(win == true)
		{
			if(timer < 500)
			{
				key[2].collect();
				g.drawImage(textbox, 0, 812, 1250, 150, null);
				timer++;
			}

		}
		
	}
}

