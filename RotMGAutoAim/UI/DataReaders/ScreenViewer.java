/*
 * Name:	  ScreenViewer.java
 * Date:      August 16th
 * Author:    Sr Duckie (Nova Ardent)
 * Version:   1.0.0
 * Reference: --
 * Purpose:   To show the main play area of the player, and use that as a
 *			  positional basis for aiming.
 * warning:   this screen translates your radar direction to the mouse motion
 *			  position. So make sure to center it and the radar so it's not
 *			  miss firing. (in the way it was used in RotMGUI)
 */


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.File;

public class ScreenViewer extends DataViewer
{
	LineListener ll = new LineListener();

	/*
     * Name:        ScreenViewer
     * Purpose:     instantiate an Screen viewer reader.
     * Arguments:   The Java robot object, x, and y position in the panel,
	 *				and the string name of the file used to load coordinates
     * Output:      --
     * Modifies:    Sets a mouse listner for  AdjustButtons and save button.
     *              Calles the super constructor with the same args.
     * Returns:     RadarReader Object : DataViewer
     * Assumptions: --
     * Bugs:        --
     * Notes:       --
     */
	public ScreenViewer(Robot rb, int x, int y, String s)
	{
		super(rb, x, y, s);

        discrim = 3;

        for (int i1 = 0; i1 < adjs.length; i1++)
            adjs[i1].addMouseListener(ll);

        //setBackground(new Color(5, 5, 5));

        add(save);
        save.setBackground(Color.GREEN);
        save.addMouseListener(ll);

		for (int i1 = 0; i1 < 8; i1++)
		{
			System.out.println("RD:" + adjs[i1].getBounds().x + " " + adjs[i1].getBounds().y);
		}
	}

	/*
	 * Name:        setSizeValues
	 * Purpose:		to set up the view.
	 * Arguments:   width and height.
	 * Output:      --
	 * Modifies:    the bounds. (the parenting object needs to have its layout
	 				set to null.)
	 * Returns:		--
	 * Assumptions:	--
	 * Bugs:		--
	 * Notes:		--
	 */
	public void setSizeValues(int w, int h)
	{
		w /= 2;
        h /= 2;
		setBounds(posx, posy, w, h + 48);

		for (int i1 = 0; i1 < 8; i1++)
		{
			adjs[i1].setBounds(
				25 + 15 * (i1 % 2) + 50 * ((i1 % 4) / 2),
				height / 2 + 14 + (i1 / 4) * 16,
				14, 14);
		}

		save.setBounds(width / 2 - 16, height / 2 + 32, 14, 14);
	}

	/*
	 * Name:        update
	 * Purpose:		to update the latestCap
	 * Arguments: 	--
	 * Output:		--
	 * Modifies:	latestCap
	 * Returns:		--
	 * Assumptions:	--
	 * Bugs:		--
	 * Notes:		--
	 */
	public void update()
	{
		latestCap = robot.createScreenCapture(new Rectangle(capx, capy, width, height));

		repaint();
	}

	/*
	 * Name:        paintComponent
	 * Purpose:		paints a miniature version of the screencap.
	 * Arguments:	Graphics p.
	 * Output:		A miniature version of the screencap
	 * Modifies:	page
	 * Returns:		--
	 * Assumptions:	--
	 * Bugs:		--
	 * Notes:		--
	 */
	public void paintComponent (Graphics page)
    {
    	super.paintComponent (page);

        super.updateSizeTally();

    	page.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
    	page.setColor(Color.RED);

    	page.drawImage(latestCap, 0, 0, width / 2, height / 2, null);
    	page.drawString("x: ", 10, height / 2 + 24);
    	page.drawString("y: ", 60, height / 2 + 24);
    	page.drawString("w: ", 10, height / 2 + 40);
    	page.drawString("h: ", 60, height / 2 + 40);
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
        	if (event.getButton() == MouseEvent.BUTTON1)
            {
            	pressed = -1;
            }
            else if (event.getButton() == MouseEvent.BUTTON3)
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
