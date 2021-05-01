import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class TregTeller {
    static int tellerverdi = 0;

    static void ventAktivt (int n) {
        // Kjør kode som tar omtrent n sekunder.
        for (int i = 1;  i <= n;  i++) {
            long x = 1;
            for (long j = 1;  j <= 250*1000*1000;  j++) {
                x = (3+j)*x % (1000*1000);
            }
        }
    }

    public static void main (String[] arg) {
        JFrame vindu = new JFrame("Treg teller");
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
                antall.setText(" " + tellerverdi);
                ventAktivt(10);
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

        // main-tråden fortsetter på egen hånd:
        for (int sek = 0;  sek < 30;  ++sek) {
            System.out.print(sek + " ");
            ventAktivt(1);
        }
        System.out.println("ferdig");
    }
}
