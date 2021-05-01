import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.Random;

/* Brettet:
      +---+---+---+
      | 1 | 2 | 3 |
      +---+---+---+
      | 4 | 5 | 6 |
      +---+---+---+
      | 7 | 8 | 9 |
      +---+---+---+

   Spillerne:
      X - maskinen
      O - brukeren
 */

class TTTGUI {
    public static void main (String[] args) {
        JFrame vindu = new JFrame("Tripp trapp tresko");
        vindu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	Spillbrett brett = new Spillbrett();
	brett.initGUI();
	vindu.add(brett);

        vindu.pack();
        vindu.setVisible(true);

	// Start spillet:
        brett.spillet.spillX();
    }
}

class Spillbrett extends JPanel {
    JButton sluttknapp;
    JLabel statustekst;
    Spill spillet;

    Spillbrett () {
	spillet = new Spill(this);
    }

    void initGUI () {
        spillet.initGUI();
        add(spillet);

        statustekst = new JLabel("Velg en rute");
        add(statustekst);
    
        sluttknapp = new JButton("Exit");
        class Stoppbehandler implements ActionListener {
            @Override
            public void actionPerformed (ActionEvent e) {
                System.exit(0);
            }
        }
        sluttknapp.addActionListener(new Stoppbehandler());
        add(sluttknapp);
    }
}

class Spill extends JPanel {
    Spillbrett brettet;
    Rute ruter[] = new Rute[9+1];
    boolean ferdig = false;

    Spill (Spillbrett b) {
	brettet = b;
	for (int i = 1;  i <= 9;  i++)
	    ruter[i] = new Rute(this);
    }

    void initGUI () {
        setLayout(new GridLayout(3,3));

        for (int i = 1;  i <= 9;  i++) {
            ruter[i].initGUI();
            add(ruter[i]);
        }
    }

    void spillO (Rute r) {
        if (r.merke != ' ') {
            brettet.statustekst.setText("Opptatt; velg en annen");
            return;
        }
        
        r.settMerke('O');
        if (harVunnet('O')) utropVinner('O');
        else if (erUavgjort()) utropUavgjort();

        if (! ferdig) {
	    brettet.statustekst.setText("Velg en rute");
	    spillX();
	}
    }

    Random tilfeldig = new Random();

    void spillX () {
        int p;
        do { 
            p = tilfeldig.nextInt(9)+1;
        } while (ruter[p].merke != ' ');
        ruter[p].settMerke('X');

        if (harVunnet('X')) utropVinner('X');
        else if (erUavgjort()) utropUavgjort();
    }

    boolean harVunnet (char c) {
        return 
            trePaaRad(1, 2, 3, c) ||  // Vannrett
            trePaaRad(4, 5, 6, c) ||
            trePaaRad(7, 8, 9, c) ||
            trePaaRad(1, 4, 7, c) ||  // Loddrett
            trePaaRad(2, 5, 8, c) ||
            trePaaRad(3, 6, 9, c) ||
            trePaaRad(1, 5, 9, c) ||  // Diagonal
            trePaaRad(3, 5, 7, c);
    }

    boolean trePaaRad (int r1, int r2, int r3, char c) {
        return ruter[r1].merke == c && 
	    ruter[r2].merke == c &&
            ruter[r3].merke == c;
    }

    boolean erUavgjort () {
        for (int i = 1;  i <= 9;  i++)
            if (ruter[i].merke == ' ') return false;
        return true;
    }

    void utropVinner (char c) {
        brettet.statustekst.setText(c + " har vunnet!");
        ferdig = true;
    }

    void utropUavgjort () {
        brettet.statustekst.setText("Det ble uavgjort!");
        ferdig = true;
    }
}

class Rute extends JButton {
    Spill spillet;
    char merke = ' ';

    Rute (Spill s) {
	spillet = s;
    }

    void initGUI () {
        setBorder(BorderFactory.createLineBorder(Color.black));
        setFont(new Font("Monospaced", Font.BOLD, 50));
        setPreferredSize(new Dimension(80, 80));
        setText(" ");

	Rute denneRuten = this;
	class Rutevelger implements ActionListener {
	    @Override
	    public void actionPerformed (ActionEvent e) {
		if (! spillet.ferdig)
		    spillet.spillO(denneRuten);
	    }
	}
	addActionListener(new Rutevelger());
    }

    void settMerke (char c) { 
        setText(""+c);  merke = c;
    }
}
