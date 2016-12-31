/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krzywe.eliptyczne;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Maciek
 */
public class KrzyweEliptyczne {

    static int M = 23;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            ECGroup group = new ECGroup(10, 10, M);
            List<ECPoint> listpkt = group.generateGroupElemets();
            int i = 1;
            for (ECPoint p : listpkt) {
                System.out.println(i + ". POINT X:" + p.x + " y:" + p.y);
                i++;
            }
//            group.add2(listpkt.get(1), listpkt.get(2));
            List<ECPoint> grup = group.generateG(listpkt.get(getInt("Podaj nr punktu do generacji") - 1));
            i = 1;
            for (ECPoint p : grup) {
                System.out.println("Group Point P" + i + "(" + p.x + "," + p.y + ")");
                i++;
            }
            System.out.println("Group Point P" + i + "(O)");
            System.out.println("Rzad:" + group.getC());
            
            ECSystem ecsys = new ECSystem();
            
            int na = getInt("Podaj liczbę(secret key A) mniejsza niż " + i);
            ECPoint pa = ecsys.generateOpenKey(na, grup);
            int nb = getInt("Podaj liczbę(secret key B) mniejsza niż " + i);
            ECPoint pb =  ecsys.generateOpenKey(nb, grup);
            
            ECPoint userASecretKey = ecsys.generateSecretKey(na,pb,group);
            System.out.println("Klucz wspolny na*Pb=na*(nb*G):" + na + "*P"
                    + pb.toString() + "=" + userASecretKey.toString());
            ECPoint userBSecretKey = ecsys.generateSecretKey(nb,pa,group);
            System.out.println("Klucz wspolny nb*Pa=nb*(na*G):" + nb + "*P"
                    + pa.toString() + "=" + userBSecretKey.toString());
//            //sign
            ECPoint signature =  ecsys.sign("ZINIEWICZ", group, grup, na);
              System.out.println("PODPIS: "+signature.toString());
            // verify
            ECPoint verified = ecsys.verifySIgn("ZINIEWICZ", signature, group, pa, grup);
            System.out.println("R:" + signature.x + " R*:" + verified.x % group.getC());
//
        } catch (Exception ex) {
            Logger.getLogger(KrzyweEliptyczne.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static int getInt(String msg) {
        System.out.println(msg);
        Scanner scanner = new Scanner(System.in);
        int i = 1;
        // find the next int token and print it
        // loop for the whole scanner
        while (true) {

            // if the next is a int, print found and the int
            if (scanner.hasNextInt()) {
                i = scanner.nextInt();
                System.out.println("Found :" + i);
                break;
            }
            // if no int is found, print "Not Found:" and the token
            System.out.println("Not Found :" + scanner.next());
        }
        return i;
    }

}
