/*
 * Name:	  HPbar.java
 * Date:      August 16th
 * Author:    Sr Duckie (Nova Ardent)
 * Version:   1.0.0
 * Purpose:	  A DataViewer module used to read the hp bar of RotMG and then
 *			  tele home if appropriate.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.File;

public class HPbar extends DataViewer
{
	LineListener ll = new LineListener();

    private int hpPercent;

    /*
     * Name:		HPbar
     * Purpose:		to instantiate an hp bar reader.
     * Arguments:	The Java robot object, x, and y position in the panel,
	 *				and the string name of the file used to load coordinates
     * Output:		--
     * Modifies:	adds a mouse listener to the adjust buttons, and the save
	 *				button,
	 *				Calles the super constructor with the same args.
     * Returns:		HPBar object : DataViewer
     * Assumptions:	--
     * Bugs:		--
     * Notes:		--
     */
	public HPbar(Robot rb, int x, int y, String s)
	{
		super(rb, x, y, s);

        discrim = 5;

        for (int i1 = 0; i1 < adjs.length; i1++)
            adjs[i1].addMouseListener(ll);

        add(save);
        save.setBackground(Color.GREEN);
        save.addMouseListener(ll);
	}

    /*
     * Name:		update
     * Purpose:		To take a screencap of the hp bar and read the red on it
	 *				to get how much health is available in it.
     * Arguments:	--
     * Output:		--
     * Modifies:	hpPerfect
	 *				latestCap
     * Returns:		--
     * Assumptions: --
     * Bugs:		--
     * Notes:		read paintComponent for information on repaint().
     */
    public void update()
    {
        latestCap = robot.createScreenCapture(new Rectangle(capx, capy, width, height));

        hpPercent = 101;
        for (int i1 = width - 1; i1 >= 0; i1--)
        {
            if ((new Color(latestCap.getRGB(i1, 0))).getRed() > 200)
            {
                hpPercent = 1 + i1 * 100 / width;
                break;
            }

        }
        repaint();
    }

    /*
     * Name:        paintComponent
     * Purpose:     To draw the data on the screen
     * Output:      --
     * Modifies:    Draws the content on the screen, with the robot image of
	 *				the hp, along with the values associated with it.
     * Returns:		--
     * Assumptions: --
     * Bugs:		--
     * Notes:		--
     */
	public void paintComponent (Graphics page)
    {
    	super.paintComponent (page);

    	page.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
    	page.setColor(Color.RED);

        super.updateSizeTally();

    	page.drawImage(latestCap, 0, 0, null);
    	if (hpPercent != 101)
    		page.drawString("hp: " + hpPercent, 10, height + 12);
    	else
    		page.drawString("hp: ????", 10, height + 12);
    	page.drawString("x: ", 10, height + 24);
    	page.drawString("y: ", 60, height + 24);
    	page.drawString("w: ", 10, height + 40);
    	page.drawString("h: ", 60, height + 40);
    }

    /*
     * Name:        getHealth
     * Purpose:     returns the current HP of this modules read in.
     * Arguments:   --
     * Output:      --
     * Modifies:    --
     * Returns:     hpPercent (int)
     * Assumptions: --
     * Bugs:        --
     * Notes:       --
     */
    public int getHealth()
    {
        return hpPercent;
    }

    private class LineListener implements MouseListener
    {
        public void mousePressed (MouseEvent event)
        {
            if (event.getButton() == MouseEvent.BUTTON1 )
            {
            	if (event.getSource() == save)
        		{
        			save();
        			return;
        		}

            	for (int i1 = 0; i1 < 8; i1++)
            	{
            		if (adjs[i1] == event.getSource())
            		{
            			pressed = i1;
                        System.out.println(i1);
                        break;
            		}
            	}
            }
            else if(event.getButton() == MouseEvent.BUTTON3)
            {
                // right click will run this.
            }
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
        	if( event.getButton() == MouseEvent.BUTTON1 )
            {
            	pressed = -1;
            }
            else if(event.getButton() == MouseEvent.BUTTON3)
            {
                // right click will run this.
            }
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
