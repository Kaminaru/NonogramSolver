import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class Vokaler {
    public static void main (String[] arg) {
        JFrame vindu = new JFrame("Vokaler");
        vindu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        vindu.add(panel);

	JLabel antall = new JLabel("     ");

	JTextField tekst = new JTextField("Skriv en tekst:", 30);
	panel.add(tekst);

	JButton tellA = new JButton("Tell A");
        class TellerA implements ActionListener {
            @Override
            public void actionPerformed (ActionEvent e) {
		String s = tekst.getText();
		int n = 0;
		for (int i = 0;  i < s.length();  i++) {
		    char c = s.charAt(i);
		    if (c=='A' || c=='a') n++;
		}
		antall.setText(n + " A-er");
            }
        }
        tellA.addActionListener(new TellerA());
	panel.add(tellA);

	JButton tellE = new JButton("Tell E");
        class TellerE implements ActionListener {
            @Override
            public void actionPerformed (ActionEvent e) {
		String s = tekst.getText();
		int n = 0;
		for (int i = 0;  i < s.length();  i++) {
		    char c = s.charAt(i);
		    if (c=='E' || c=='e') n++;
		}
		antall.setText(n + " E-er");
            }
        }
        tellE.addActionListener(new TellerE());
	panel.add(tellE);

	panel.add(antall);

        vindu.pack();
        vindu.setVisible(true);
    }
}
