import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class Exit {
    public static void main (String[] arg) {
        JFrame vindu = new JFrame("Exit");
        vindu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        vindu.add(panel);

        JButton exitKnapp = new JButton("Exit");
	class Stopper implements ActionListener {
	    @Override
	    public void actionPerformed (ActionEvent e) {
		System.exit(0);
	    }
	}
	exitKnapp.addActionListener(new Stopper());
        panel.add(exitKnapp);

        vindu.pack();
        vindu.setVisible(true);
    }
}
