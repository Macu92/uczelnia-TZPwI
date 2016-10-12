/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tzpwil.sha.pkg1.zad1;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Maciek
 */
public class Window extends JFrame {

    static final int witdh = 400;
    static final int height = 200;
    JButton propButton = new JButton("Wybierz plik");
    JFileChooser jfc = new JFileChooser();
    JPanel listPane = new JPanel();
    JTextField jtf = new JTextField();

    public Window() throws HeadlessException {
        setSize(witdh, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        propButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    byte[] encoded;
                    try {
                        encoded = Files.readAllBytes(selectedFile.toPath());
                        SHA1 sha1 = new SHA1();
                        jtf.setText(sha1.cipher(new String(encoded)));
                    } catch (IOException ex) {
                        Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    try {
                        java.awt.Desktop.getDesktop().open(selectedFile);//<-- here
                    } catch (IOException ex) {
                        Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        listPane.setLayout(new BoxLayout(listPane, BoxLayout.PAGE_AXIS));
        listPane.add(propButton);
        listPane.add(jtf);

        getContentPane().add(listPane);
    }
}
