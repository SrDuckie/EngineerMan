import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Toolkit;

public class RotMGAA
{
   public static void main(String[] args)
   {
      JFrame frame = new JFrame ("RotMG Auto-Aim");
      frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
      //frame.setResizable(false);
      frame.getContentPane().add (new RotMGUI());
      frame.pack();
      frame.setVisible(true);

   }
   

}