/*
 * Name:      DataViewer.java
 * Date:      August 16th
 * Author:    Sr Duckie (Nova Ardent)
 * Version:   1.0.0
 * Reference: --
 * Purpose:   An extended class for data viewer modules for Java.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.File;

public class DataViewer extends JPanel
{
	protected Robot robot;
	protected int posx;
	protected int posy;

	protected int capx = 0;
	protected int capy = 0;
	protected int width = 200;
	protected int height = 200;

	BufferedImage latestCap;

	AdjustButton[] adjs = new AdjustButton[8];
	int pressed = -1;
	int sizeTally = 0;
    int discrim;

	AdjustButton save = new AdjustButton("s");

    String filename;

    /*
     * Name:        DataViewer
     * Purpose:     To intialize a DataViewer object and set the
     *              button positions.
     * Arguments:   --
     * Output:      --
     * Modifies:    Position, height, width, sets Robot obj, and
     *              background color.
     * Returns:     DataViewer (OBJ)
     * Assumptions: --
     * Bugs:        The parenting class of a Dataviewer object has their
     *              layout set to null.
     * Notes:       --
     */
	public DataViewer(Robot rb, int x, int y, String s)
	{
        discrim = 1;

        filename = s;

		robot = rb;
		posx = x;
		posy = y;

		setLayout(null);
		setBackground(new Color(200, 200, 200));

		for (int i1 = 0; i1 < 8; i1++)
		{
			adjs[i1] = (i1 % 2 == 0) ? new AdjustButton("<"): new AdjustButton(">");
			add(adjs[i1]);
			//adjs[i1].addMouseListener(ll);
		}

		setSizeValues(width, height);
	}

    /*
     * Name:        
     * Purpose:     updates the size based on whether or not an 
     *              adjust button was held.
     * Arguments:   --
     * Output:      --
     * Modifies:    updates sizeTally, when sizeTally hits the
     *              disciminator value (discrim) it'll update
     *              the screen. This is to prevent overbearing
     *              size changes.
     * Returns:     --
     * Assumptions: --
     * Bugs:        --
     * Notes:       Discrim is to prevent overbearing
     *              size changes.
     */
    protected void updateSizeTally()
    {
        sizeTally = (sizeTally + 1) % discrim;

        switch (pressed)
        {
            case 0:
                capx--;
                break;
            case 1:
                capx++;
                break;
            case 2:
                capy--;
                break;
            case 3:
                capy++;
                break;
            case 4:
                if (sizeTally == discrim - 1)
                {
                    width--;
                    if (width < 10)
                        width = 10;
                    setSizeValues(width, height);
                }
                break;
            case 5:
                if (sizeTally == discrim - 1)
                    width++;
                setSizeValues(width, height);
                break;
            case 6:
                if (sizeTally == discrim - 1)
                    height--;
                if (height < 10)
                    height = 10;
                setSizeValues(width, height);
                break;
            case 7:
                if (sizeTally == discrim - 1)
                    height++;
                setSizeValues(width, height);
                break;
            default:
                break;
        }
    }

    /*
     * Name:        setSizeValues
     * Purpose:     to set the window bounds and move the adjust buttons
     *              to match.
     * Arguments:   width, height.
     * Output:      --
     * Modifies:    Sets the extended JPanels boundaries provided that the
     *              parenting class has layout set to NULL from the swing
     *              library.
     * Returns:     --
     * Assumptions: The parenting class of a Dataviewer object has their
     *              layout set to null.
     * Bugs:        --
     * Notes:       --
     */
	public void setSizeValues(int w, int h)
	{
		setBounds(posx, posy, w, h + 48);

		for (int i1 = 0; i1 < 8; i1++)
		{
			adjs[i1].setBounds(
				25 + 15 * (i1 % 2) + 50 * ((i1 % 4) / 2),
				height + 14 + (i1 / 4) * 16,
				14, 14);
		}

		save.setBounds(width - 16, height + 32, 14, 14);
	}

    /*
     * Name:        save
     * Purpose:     to save the coordinates, width and height
     *              of a given module that extends DataViewer
     * Arguments:   --
     * Output:      Error:
     *                   failed to save data.
     *                   <filename>
     * Modifies:    Creates a file if it doesn't exist and writes the 
     *              specified coordinates.
     * Returns:     --
     * Assumptions: --
     * Bugs:        --
     * Notes:       --
     */
    public void save()
    {
    	try
    	{
    		PrintWriter writer = new PrintWriter(filename, "UTF-8");
    		writer.println(capx);
    		writer.println(capy);
    		writer.println(width);
    		writer.println(height);
    		writer.close();

    	}
    	catch (Exception e) 
        {
            System.err.println("failed to save data.");
            System.err.println(filename);
        }
    }

    /*
     * Name:        load
     * Purpose:     To load the data relevant to the location
     *              and size of a DataViewer Module.
     * Arguments:   --
     * Output:      Errors:
     *                  failed to load data.
     *                  <filename> 
     * Modifies:    --
     * Returns:     void
     * Assumptions: filename has been set.
     * Bugs:        --
     * Notes:       --
     */
    public void load()
    {
        try 
        {
            Scanner scanner = new Scanner(new File(filename));
            int[] rin = new int[4];
            int i = 0;
            while(scanner.hasNextInt())
            {
                rin[i] = scanner.nextInt();
                i++;
            }

            capx = rin[0];
            capy = rin[1];
            width = rin[2];
            height = rin[3];
            setSizeValues(width, height);
        }
        catch (Exception e) 
        {
            System.err.println("failed to load data.");
            System.err.println(filename);
        }
    }
}