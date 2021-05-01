import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class BorderDemo {
    public static void main (String[] arg) {
        JFrame vindu = new JFrame("Rammer");
        vindu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        vindu.add(panel);

	JLabel stdLabel = new JLabel("Vanlig JLabel");
	panel.add(stdLabel);

	JLabel egenLabel = new JLabel(" med strekramme ");
	egenLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	panel.add(egenLabel);

	JButton stdButton = new JButton("Vanlig JButton");
	panel.add(stdButton);

	JButton egenButton = new JButton(" med strekramme ");
	egenButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	panel.add(egenButton);

        vindu.pack();
        vindu.setVisible(true);
    }
}
