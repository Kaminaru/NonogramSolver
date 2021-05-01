import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.io.*;

class FileChooserDemo {
    public static void main (String[] arg) {
        JFileChooser velger = 
            new JFileChooser();
        int resultat = 
            velger.showOpenDialog(null);

        if (resultat == 
            JFileChooser.APPROVE_OPTION) 
        {
            File f = 
                velger.getSelectedFile();
        } else {
            // Cancel
        }
    }
}
