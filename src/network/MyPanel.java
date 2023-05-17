package network;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
import java.awt.*;

import javax.swing.JFrame;



class MyPanel extends JPanel implements KeyListener {
    static char c = 'e';

    public MyPanel() {
        this.setPreferredSize(new Dimension(500, 500));
        addKeyListener(this);
    }

    public void addNotify() {
        super.addNotify();
        requestFocus();
    }

    public void paintComponent(Graphics g) {
        g.clearRect(0, 0, getWidth(), getHeight());
        g.drawString("the key that pressed is " + c, 250, 250);

    }

    public void keyPressed(KeyEvent e) { }
    public void keyReleased(KeyEvent e) { }
    public void keyTyped(KeyEvent e) {
        c = e.getKeyChar();
        if (c == KeyEvent.VK_LEFT)
            FieldAnimation.TestPane.x = -1;

        if (c == KeyEvent.VK_RIGHT) {
            FieldAnimation.TestPane.x = 1;
        }

        if (c == KeyEvent.VK_UP) {
            FieldAnimation.TestPane.y = -1;
        }

        if (c == KeyEvent.VK_DOWN) {
            FieldAnimation.TestPane.x = 1;
        }
        repaint();
    }

    public static void main(String[] s) {
      //  JFrame f = new JFrame();
     //   f.getContentPane().add(new MyPanel());
       // f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      //  f.pack();
    //    f.setVisible(true);

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                }

                JFrame frame = new JFrame("Testing");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.getContentPane().add(new MyPanel());
                frame.add(new FieldAnimation.TestPane());
                //  addKeyListener(this);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }


}

