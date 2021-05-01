import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class Grafikkdemo {
    public static void main (String[] arg) {
        JFrame vindu = new JFrame("Grafikkdemo");
        vindu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        vindu.add(panel);

        panel.add(new GPanel());

        vindu.pack();
        vindu.setVisible(true);
    }
}

class GPanel extends JComponent {
    GPanel () {
        setPreferredSize(new Dimension(300,300));
    }

    @Override
    public void paintComponent (Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        g2.setColor(Color.YELLOW);
        g2.fillOval(100,40, 100,100);
        g2.setColor(Color.RED);
        g2.setStroke(new BasicStroke(10));
        g2.drawOval(100,40, 100,100);

        g2.setColor(Color.BLUE);
        g2.setStroke(new BasicStroke(4));
        g2.drawRect(50,160, 200,100);

        g2.setColor(Color.BLACK);
        g2.fillOval(100-10,40-10, 20,20);
        g2.fillOval(50-10,160-10, 20,20);

        g2.drawString("(100, 40)", 70,20);
        g2.drawString("(50, 160)", 20,140);
    }
}
