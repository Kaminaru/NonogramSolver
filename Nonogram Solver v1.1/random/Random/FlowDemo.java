import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class FlowDemo {
    public static void main (String[] arg) {
        JFrame vindu = new JFrame("Flyt");
        vindu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        vindu.add(panel);

        JPanel flyt = new JPanel();
        flyt.add(new JButton("Valg 1"));
        flyt.add(new JButton("Valg 2"));
        flyt.add(new JButton("Valg 3"));
        flyt.add(new JButton("Valg 4"));
        panel.add(flyt);

        JPanel ruter = new JPanel();
        ruter.setLayout(new GridLayout(2,2));
        ruter.add(new JButton("Knapp 1"));
        ruter.add(new JButton("Knapp 2"));
        ruter.add(new JButton("Knapp 3"));
        ruter.add(new JButton("Knapp 4"));
        panel.add(ruter);

        vindu.pack();
        vindu.setVisible(true);
    }
}
