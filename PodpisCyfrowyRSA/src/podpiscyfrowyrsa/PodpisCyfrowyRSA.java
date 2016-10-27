/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podpiscyfrowyrsa;

import java.security.SecureRandom;

/**
 *
 * @author Maciek
 */
public class PodpisCyfrowyRSA {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        RSA rsa = new RSA(1024);
        String encrypted = rsa.encrypt("Zakodowana wiadomość");
        System.out.println(encrypted);
        System.out.println(rsa.decrypt(encrypted));
    }

}
