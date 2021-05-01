import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class FontDemo {
    public static void main (String[] arg) {
        JFrame vindu = new JFrame("Fonter");
        vindu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        vindu.add(panel);

        JLabel monoPlain20 = 
	    new JLabel("new Font(Font.MONOSPACED, Font.PLAIN, 20)");
	monoPlain20.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
        panel.add(monoPlain20);

        JLabel monoPlain30 = 
	    new JLabel("new Font(Font.MONOSPACED, Font.PLAIN, 30)");
	monoPlain30.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 30));
        panel.add(monoPlain30);

        JLabel monoBold30 = 
	    new JLabel("new Font(Font.MONOSPACED, Font.BOLD, 30)");
	monoBold30.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
        panel.add(monoBold30);

        JLabel sansPlain30 = 
	    new JLabel("new Font(Font.SANS_SERIF, Font.PLAIN, 30)");
	sansPlain30.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        panel.add(sansPlain30);

        JLabel sansBold30 = 
	    new JLabel("new Font(Font.SANS_SERIF, Font.BOLD, 30)");
	sansBold30.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
        panel.add(sansBold30);

        vindu.pack();
        vindu.setVisible(true);
    }
}
