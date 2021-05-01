import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class Teller {
    static int tellerverdi = 0;

    public static void main (String[] arg) {
        JFrame vindu = new JFrame("Teller");
        vindu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        vindu.add(panel);

        JLabel antall = new JLabel(" 0  ");

        JButton tell = new JButton(" +1 ");
        class OekTeller implements ActionListener {
            @Override
            public void actionPerformed (ActionEvent e) {
                ++tellerverdi;  
                antall.setText(" " + tellerverdi + " ");
            }
        }
        tell.addActionListener(new OekTeller());

        JButton resett = new JButton(" = 0 ");
        class Nuller implements ActionListener {
            @Override
            public void actionPerformed (ActionEvent e) {
                tellerverdi = 0;
                antall.setText(" " + tellerverdi + " ");
            }
        }
        resett.addActionListener(new Nuller());

        JButton slutt = new JButton("Exit");
        class Stopper implements ActionListener {
            @Override
            public void actionPerformed (ActionEvent e) {
                System.exit(0);
            }
        }
        slutt.addActionListener(new Stopper());

        panel.add(antall);  panel.add(tell);  panel.add(resett);
        panel.add(slutt);

        vindu.pack();
        vindu.setVisible(true);
    }
}
