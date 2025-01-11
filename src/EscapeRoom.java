import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class EscapeRoom extends JPanel{
	private Menu menu = new Menu();
	private Player p = new Player('m', menu);
	private BaseLock baseLock = new BaseLock();
	private Keys[] key = {new Keys(1), new Keys(2), new Keys(3), new Keys(4)};
	private Shovel shovel = new Shovel(3);
	private Tablets[] tablet = {new Tablets(1, 5), new Tablets(2, 6), new Tablets(3, 4)};
	private Inventory inv = new Inventory(this, key);
	private SpotDiff sd = new SpotDiff(p, inv);
	private SBP sbp = new SBP(this, inv);
	private MainRoom mr = new MainRoom(p, sd, key, baseLock, tablet, shovel, this);
	private Security sec = new Security(p, sbp, key, inv, tablet, shovel);
	private Basement base = new Basement(p, key);
	private BufferedImage textbox = null;
	private FinalRoom fr;
	private int timer = 0;
	private static boolean close = false;

	public EscapeRoom()  
	{
		try {
			textbox = ImageIO.read(new File("textbox.png"));

		} catch (IOException e) {
			System.out.println("No Escape room Image");
		}
		
		p.setRooms(mr, sec, base);

		
		addKeyListener(new KeyListener()
		{

			@Override
			public void keyTyped(KeyEvent e)
			{
			}
			
			@Override
			public void keyReleased(KeyEvent e) 
			{
				if(!p.getInGame() && p.getCanMove())
				{
					p.keyReleased(e);
				}
				sbp.keyReleased(e);

			}

			@Override
			public void keyPressed(KeyEvent e) 
			{
				if(!p.getInGame() && p.getCanMove())
					p.keyPressed(e);
				if(p.getCurrentRoom() == 'm')
				{
					if(!fr.getWin())
						mr.keyPressed(e);
				}
				if(p.getCurrentRoom() == 's')	
				{
					sec.keyPressed(e);
					sbp.keyPressed(e);
				}
				if (p.getCurrentRoom() == 'b')
					base.keyPressed(e);

				baseLock.keyPressed(e);


			}
				
		});
				setFocusable(true);
				addMouseListener (new MouseListener() {
					@Override
					public void mouseClicked(MouseEvent e)
					{
					}
					@Override
					public void mousePressed(MouseEvent e)
					{
						sec.mousePressed(e);
						sbp.mousePressed(e);
						if(sd.getInteract())
						{
							sd.mousePressed(e);
						}
						baseLock.mousePressed(e);
						mr.mousePressed(e);

					}
					@Override
					public void mouseReleased(MouseEvent e)
					{
						menu.mouseReleased(e);
					}
					@Override
					public void mouseEntered(MouseEvent e)
					{
						
					}
					@Override
					public void mouseExited(MouseEvent e)
					{
						
					}});
				
				addMouseMotionListener(new MouseMotionListener() {
					@Override
					public void mouseDragged(MouseEvent e)
					{
						
					}
					
					public void mouseMoved (MouseEvent e)
					{
						menu.mouseMoved(e);
					}});

	}
	
	public void setFinalRoom(FinalRoom f)
	{
		fr = f;
	}
	
	private void move() 
	{
		if(!fr.getWin())
		{
			p.move();
			
			if(p.getCurrentRoom() == 's')
			{
				sec.move();
			}
			
			if(fr.getWin())
			{
				menu.winPage();
			}
		}
		
		if(menu.close())
		{
			close = true;
		}
	}
	
	public void paint(Graphics g)
	{
		super.paint(g);
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, 1250, 962);


		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		
			g2d.drawImage(textbox, 0, 812, 1250, 150, null);
	
	
			if(p.getCurrentRoom() == 'm')
				mr.paint(g2d);
			else if(p.getCurrentRoom() == 's')
				sec.paint(g2d);		
			else if (p.getCurrentRoom() == 'b')
				base.paint(g2d);
			inv.paint(g2d);
			for(Keys k : key)
	    		if(!k.getIsUsed() && k.getIsFound())
	    			k.paint(g2d);
			
			for(Tablets t : tablet)
			{
				if(t.getIsFound())
				{
					if(!t.getIsUsed())
					{
						t.paint(g2d);
					}
				}
			}
			
			if (shovel.getIsFound())
			{
				if (!shovel.getIsUsed())
				{
					shovel.paint(g2d);
				}
			}
		
		if(fr.getWin())
		{
			if(timer < 255)
			{
				timer++;
			}
			else
			{
				menu.winPage();
			}
			
			g2d.setColor(new Color(0,0,0,timer));
			g2d.fillRect(0, 0, 1250, 962);
		}
		menu.paint(g2d);
	
	}
	
	public static void main(String[] args) throws InterruptedException
	{
		JFrame frame = new JFrame("Escape The Museum");
		EscapeRoom er = new EscapeRoom();
		frame.add(er);
		frame.setSize(1250, 962);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
			
		
		
		while(true)
		{
			er.move();
			er.repaint();
			Thread.sleep(10);
			if(close)
				frame.dispose();
		}
	}
}