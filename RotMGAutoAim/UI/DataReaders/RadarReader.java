/*
 * Name:      RadarReader
 * Date:      August 16th
 * Author:    Sr Duckie (Nova Ardent)
 * Version:   1.0.0
 * Reference: --
 * Purpose:   read the radar to location the enemies for targetting.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.File;

public class RadarReader extends DataViewer
{
    public boolean aiming;
    public int aimX;
    public int aimY;
    public int pvx;
    public int pvy;
    public int ppvx;
    public int ppvy;
    private int disSqr;

	LineListener ll = new LineListener();

    /*
     * Name:        RadarReader
     * Purpose:     instantiate an Radar reader.
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
	public RadarReader(Robot rb, int x, int y, String s)
	{
        super(rb, x, y, s);

        discrim = 3;

        add(save);
        save.setBackground(Color.GREEN);
        save.addMouseListener(ll);

        for (int i1 = 0; i1 < adjs.length; i1++)
            adjs[i1].addMouseListener(ll);
	}

    /*
     * Name:        update
     * Purpose:     to read the screen for a radar.
     * Arguments:   --
     * Output:      --
     * Modifies:    latestCap is updated.
     * Returns:     --
     * Assumptions: --
     * Bugs:        --
     * Notes:       --
     */
	public void update()
	{
		latestCap = robot.createScreenCapture(new Rectangle(capx, capy, width, height));

		repaint();
	}

    /*
     * Name:        paintComponent
     * Purpose:     to draw information on the screen and update the latestCap
     *              to read for the nearest enemy position.
     * Arguments:   --
     * Output:      Displays the radar and the coordinates of the nearest
     *              enemy.
     * Modifies:    sets the aiming position of the nearest enemy.
     * Returns:     --
     * Assumptions: --
     * Bugs:        --
     * Notes:       --
     */
	public void paintComponent (Graphics page)
    {
    	super.paintComponent (page);

        super.updateSizeTally();

    	page.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
    	page.setColor(Color.RED);

    	page.drawImage(latestCap, 0, 0, null);
    	page.drawString("x: ", 10, height + 24);
    	page.drawString("y: ", 60, height + 24);
    	page.drawString("w: ", 10, height + 40);
    	page.drawString("h: ", 60, height + 40);

        page.setColor(new Color(255, 0, 0, 55));
        page.drawLine(width / 2, 0, width / 2, height);
        page.drawLine(width / 2 + 1, 0, width / 2 + 1, height);
        page.drawLine(0, height / 2, width, height / 2);
        page.drawLine(0, height / 2 + 1, width, height / 2 + 1);

        page.setColor(Color.WHITE);
        disSqr = Integer.MAX_VALUE;
        int midw = width / 2;
        int midh = height / 2;
        aimX = midw;
        aimY = midh;
        int x = 0;
        int y = 0;
        aiming = false;
        for (int i1 = 20; i1 < width - 20; i1++)
        {
            for (int i2 = 20; i2 < height - 20; i2++)
            {
                Color c = new Color(latestCap.getRGB(i1, i2));
                if (c.getRed() > 250 && c.getBlue() < 10 && c.getGreen() < 10)
                {
                    x = (midw - i1);
                    y = (midh - i2);
                    if (disSqr > ((x * x) + (y * y)))
                    {
                        disSqr = (x * x) + (y * y);
                        aimX = i1;
                        aimY = i2;

                        aiming = true;
                    }
                }
            }
        }

        page.drawLine(aimX, aimY, midw, midh);

        aimX -= midw;
        aimY -= midh;

        ppvx = pvx;
        ppvy = pvy;
        pvx = aimX;
        pvy = aimY;

        if (Math.abs(pvx - ppvx) < 15)
            aimX += pvx - ppvx;
        if (Math.abs(pvy - ppvy) < 15)
            aimY += pvy - ppvy;

        page.setColor(Color.RED);
        page.drawString("(" + (aimX - midw) + "," + (aimY - midh) + " )", 10, height + 12);
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
