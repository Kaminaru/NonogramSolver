import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class Hallo {
    public static void main (String[] arg) {
        JFrame vindu = new JFrame("Hei");
        vindu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        vindu.add(panel);

        String bruker = System.getProperty("user.name");
        JLabel hilsen = new JLabel("Hallo, " +  bruker + "!");
        panel.add(hilsen);

        vindu.pack();
        vindu.setVisible(true);
    }
}
