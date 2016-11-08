/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krzywe.eliptyczne;

import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Maciek
 */
public class KrzyweEliptyczne {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            for(ECPoint p : new ECGroup(10,10,23).generateGroupElemets()){
                System.out.println("POINT X:"+p.x+" y:"+p.y);
            }
        } catch (Exception ex) {
            Logger.getLogger(KrzyweEliptyczne.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
