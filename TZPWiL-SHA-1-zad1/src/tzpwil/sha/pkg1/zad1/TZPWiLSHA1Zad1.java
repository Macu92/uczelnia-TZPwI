/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tzpwil.sha.pkg1.zad1;

import java.awt.FileDialog;
import java.awt.Frame;
import javax.swing.JFrame;

/**
 *
 * @author Maciek
 */
public class TZPWiLSHA1Zad1{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        SHA1 sha1 = new SHA1();
//        sha1.setText();
        sha1.cipher("testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest");
        
        Window w = new Window();
        w.setVisible(true);
    }

}
