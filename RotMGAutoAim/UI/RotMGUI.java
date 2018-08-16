/*
 * Name:	  RotMGUI
 * Date:      August 16th
 * Author:    Sr Duckie (Nova Ardent)
 * Version:   1.0.0
 * Reference: --
 * Purpose:	  An auto aim and port home bot for realm of the mad god.
 * Bugs:	  it's possible that the graphics detail may update from time to
 *			  time in rotmg, in that instance you may need to change the values
 *		      read in for the hp bar, and the radar.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Robot;

public class RotMGUI extends JPanel
{
	Robot rb;
	private Timer timer;


	HPbar hpreader;
	RadarReader rdreader;
	ScreenViewer scrview;

	JPanel pause = new JPanel();
	boolean paused = true;

	/*
	 * Name:		RotMGUI
	 * Purpose:		to instantiate a rotmg bot and gui.
	 * Arguments:	--
	 * Output:		--
	 * Modifies:	rb is instantiated to a new robot class.
	 *				width set to screensize - 800
	 *				height set to 600
	 *				screensize changed to width and height.
	 *				background color changed to new Color(200, 200, 200)
	 *				DataViewer
	 *					hpreader positioned and set.
	 *					rdreader positioned and set.
	 *					scrview positioned and set.
	 *				pause button added and set to paused.
	 *				timer loop started.
	 * Returns:		RotMGUI
	 * Assumptions: --
	 * Bugs:		--
	 * Notes:		--
	 */
	public RotMGUI()
	{
		int width = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 800;
		int height = 600;

		setPreferredSize(new Dimension(width, height));
        setBackground(new Color(200, 200, 200));
        setLayout(null);

        try
        {
            rb = new Robot();
        }
        catch (Exception E) {}

        hpreader = new HPbar(rb, 10, 10, "./healthbar.txt");
        hpreader.load();
        add(hpreader);

        rdreader = new RadarReader(rb, 10, 100, "./RadarReader.txt");
        rdreader.load();
        add(rdreader);

        scrview = new ScreenViewer(rb, 220, 10, "./ScreenViewer.txt");
        scrview.load();
        add(scrview);

        pause.setBounds(10, height - 40, 100, 30);
        pause.setBackground(Color.RED);
        pause.addMouseListener(new LineListener());
        add(pause);

        hpreader.update();
        rdreader.update();
        scrview.update();

        timer = new Timer(20, new TimerListener());
        timer.start( );
	}

    private class TimerListener implements ActionListener
    {
		/*
		 * Name:		actionPerformed
		 * Purpose:		reads from the screen the hp bad, and updates its values.
		 *				if the hp drops below 60% it will press the teleport out
		 *				button 10 times to ensure it works.
		 *				reads from both the radar and the scrview and updates
		 *				their data. Moves the mouse to the aimx and aimy
		 *				position from the radar reader relative to the
		 *				scrview.
		 * Arguments:	--
		 * Output:		--
		 * Modifies:	moves the mouse, could press 'r' 10 times.
		 * Returns:
		 * Assumptions:
		 * Bugs:		there is an error I haven't bothered looking into if
		 *				scrview isn't updated quickly enough.
		 * Notes:		all functionality is disabled when paused.
		 */
        public void actionPerformed (ActionEvent event)
        {
        	if (!paused)
        	{
                hpreader.update();
                if (hpreader.getHealth() < 60)
                {
                    System.out.println("run!");
                    for (int i1 = 0; i1 < 10; i1++)
                    {
                        rb.keyPress(KeyEvent.VK_R);
                        rb.delay(40);
                        rb.keyRelease(KeyEvent.VK_R);
                    }
                }

        		rdreader.update();
        		scrview.update();
        		if (rdreader.aiming)
	        	{
	        		int x = scrview.capx + scrview.width / 2;
	        		int y = scrview.capy + scrview.height / 2 + 10;
	        		int rdx = (rdreader.aimX) * 12;
	        		int rdy = (rdreader.aimY) * 12;
	        		double dis = (rdx * rdx + rdy * rdy);

	        		if (dis < 80000)
	        			rb.mouseMove(x + rdx, y + rdy);
	        		else
	        		{
	        			dis = Math.sqrt(dis);
	        			rdx = (int)(200 * rdx / dis);
	        			rdy = (int)(200 * rdy / dis);
	        			rb.mouseMove(x + rdx, y + rdy);
	        		}
	        	}
        	}
        }
    }

    private class LineListener implements MouseListener
    {
        public void mousePressed (MouseEvent event)
        {
        	paused = !paused;
            if (paused)
            	pause.setBackground(Color.RED);
            else
            	pause.setBackground(Color.GREEN);

        }
        public void mouseDragged (MouseEvent event)
        {
            // clicking and dragging your mouse will run this
        }
        public void mouseClicked (MouseEvent event)
        {
            // clicking your mouse will run this
        }
        public void mouseReleased (MouseEvent event)
        {

            // releasing the mouse will drag this
        }
        public void mouseEntered (MouseEvent event)
        {
            // the mouse entering the frame, will activate this.
        }
        public void mouseExited (MouseEvent event)
        {
            // mouse leaving the frame will activate this.
        }
    }
}
