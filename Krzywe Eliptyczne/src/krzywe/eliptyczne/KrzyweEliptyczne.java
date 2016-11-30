/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krzywe.eliptyczne;

import java.util.List;
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
            ECGroup group = new ECGroup(0,1,5);
            List<ECPoint> listpkt = group.generateGroupElemets();
            for(ECPoint p : listpkt){
                System.out.println("POINT X:"+p.x+" y:"+p.y);
            }
           List<ECPoint> grup = group.generateG(listpkt.get(5));
           for(ECPoint p : grup){
                System.out.println("Group Point X:"+p.x+" y:"+p.y);
            }
           System.out.println("Rzad:"+group.getC());
        } catch (Exception ex) {
            Logger.getLogger(KrzyweEliptyczne.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
