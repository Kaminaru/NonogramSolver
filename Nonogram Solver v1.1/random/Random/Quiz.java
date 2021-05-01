import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.io.*;
import java.util.Scanner;

class Quiz {
    static Svar[] svar = new Svar[4];

    public static void main (String[] arg) {
        JFileChooser velger = new JFileChooser();
        int resultat = velger.showOpenDialog(null);
        if (resultat != JFileChooser.APPROVE_OPTION) 
            System.exit(1);
        File f = velger.getSelectedFile();
        Scanner leser = null;
        try {
            leser = new Scanner(f);
        } catch (FileNotFoundException e) {
            System.exit(1);
        }

        JFrame vindu = new JFrame("Quiz");
        vindu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        vindu.add(panel);

        JLabel spoersmaal = new JLabel(leser.nextLine());
        panel.add(spoersmaal);

        JPanel alternativer = new JPanel();
        alternativer.setLayout(new GridLayout(2,2));
        for (int i = 0;  i < 4;  i++) {
            String s = leser.nextLine();
            if (s.startsWith("*")) {
                svar[i] = new Svar(s.substring(1), true);
            } else {
                svar[i] = new Svar(s, false);
            }
            svar[i].initGUI();
            alternativer.add(svar[i]);
        }
        panel.add(alternativer);

        vindu.pack();
        vindu.setVisible(true);
    }
}

class Svar extends JButton {
    String svar;
    boolean riktig;

    Svar (String s, boolean r) {
        super(s);
        svar = s;  riktig = r;
    }

    class Velger implements ActionListener {
        @Override
        public void actionPerformed (ActionEvent e) {
            if (riktig) setText("OK");
            else setText("NEI");
        }
    }

    void initGUI () {
        addActionListener(new Velger());
    }
}
