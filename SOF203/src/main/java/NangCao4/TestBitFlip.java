/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NangCao4;

/**
 *
 * @author jason
 */
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class TestBitFlip extends JPanel {
   private JLabel label = new JLabel("Fubarlicious!");

   public TestBitFlip() {
      add(label);
      add(new JButton(new FontAction("Bold", KeyEvent.VK_B, Font.BOLD)));
      add(new JButton(new FontAction("Italic", KeyEvent.VK_I, Font.ITALIC)));
   }

   private class FontAction extends AbstractAction {
      private int mask;

      public FontAction(String name, int mnemonic, int mask) {
         super(name);
         putValue(MNEMONIC_KEY, mnemonic);
         this.mask = mask;
      }

      @Override
      public void actionPerformed(ActionEvent e) {
         Font font = label.getFont();
         int style = font.getStyle();
         style ^= mask;
         font = font.deriveFont(style);
         label.setFont(font);
      }
   }

   private static void createAndShowGui() {
      TestBitFlip mainPanel = new TestBitFlip();

      JFrame frame = new JFrame("TestBitFlip");
      frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      frame.getContentPane().add(mainPanel);
      frame.pack();
      frame.setLocationByPlatform(true);
      frame.setVisible(true);
   }

   public static void main(String[] args) {
      SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            createAndShowGui();
         }
      });
   }
}
