import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class ColorDemo {
    public static void main (String[] arg) {
        JFrame vindu = new JFrame("Farger");
        vindu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        vindu.add(panel);

	JLabel blue = 
	    new JLabel("xxx.setForeground(Color.BLUE)");
	blue.setForeground(Color.BLUE);
	blue.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
	panel.add(blue);

	JLabel green = 
	    new JLabel("xxx.setForeground(Color.GREEN)");
	green.setForeground(Color.GREEN);
	green.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
	panel.add(green);

	JLabel uio = 
	    new JLabel("xxx.setForeground(new Color(218, 41, 28))");
	uio.setForeground(new Color(218, 41, 28));
	uio.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
	panel.add(uio);

        vindu.pack();
        vindu.setVisible(true);
    }
}
