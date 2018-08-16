import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AdjustButton extends JPanel
{
	String label;

	public AdjustButton(String s)
	{
		label = s;

		setBackground(Color.BLACK);

		repaint();
	}
	public void paintComponent (Graphics page)
    {
    	super.paintComponent (page);

    	page.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
    	page.setColor(Color.WHITE);
    	page.drawString(label, 3, 12);
    }
}