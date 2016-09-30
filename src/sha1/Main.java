/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sha1;

/**
 *
 * @author ziniewiczm
 */
public class Main {
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SHA1 sha1 = new SHA1();
        sha1.setText("test");
        sha1.cipher();
    }
}
