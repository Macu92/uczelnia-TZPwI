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
            int na = getInt("Podaj liczbę(secret key A) mniejsza niż " + i);
            ECPoint pa = grup.get(na - 1);
            int nb = getInt("Podaj liczbę(secret key B) mniejsza niż " + i);
            ECPoint pb = grup.get(nb - 1);
            List<ECPoint> grup1 = group.generateG(pa);
            List<ECPoint> grup2 = group.generateG(pb); //35/25/33 19,38  //60,11,17
            ECPoint pp;
             if (grup1.size() < nb) {
                pp = grup1.get((nb%(grup1.size()+1)) - 1);
            } else {
                pp= grup1.get(nb - 1);
            }
            System.out.println("Klucz wspolny na*Pb=na*(nb*G):" + na + "*P" + pb.toString() + "=" + pp.toString());
            ECPoint pbb;
             if (grup2.size() < na) {
                pbb = grup2.get((na%(grup2.size()+1) ) - 1);
            } else {
                pbb = grup2.get(na - 1);
            }
            System.out.println("Klucz wspolny nb*Pa=nb*(na*G):" + nb + "*P" + pa.toString() + "=" + pbb.toString());
//            System.out.println("ZOBACZMY CO WYSZLo");

            //coding
            Integer s = 0;
            int r = 0;
            MessageDigest md;
            byte[] bytesOfMessage;
            Integer c = group.getC();
            do {
                Double randKD = new Double("0");
                Integer randK = 0;
                do {
                    randKD = (Math.random() * 100) % c;
                    randK = 5;//randKD.intValue()+1;
                    ECPoint kpoint = grup.get(randK-1);
                    r = kpoint.x % c;
                } while (r == 0);
                System.out.println("bi"+randK+" "+c);
                Integer kmod = new BigInteger(randK.toString()).modInverse(new BigInteger(c.toString())).intValue();
                bytesOfMessage = "MOJ SUPER STRING".getBytes("UTF-8");
                md = MessageDigest.getInstance("SHA-1");
                Integer hashMessage = new BigInteger(md.digest(bytesOfMessage)).intValue() % c;
                Integer temp = hashMessage + na * r;
                s = (kmod * temp) % c;
            } while (s == 0);
            ECPoint pair = new ECPoint(r, s);

            // decoding
            byte[] bytesOfMessage2 = "MOJ SUPER STRING".getBytes("UTF-8");
            MessageDigest md2 = MessageDigest.getInstance("SHA-1");
            Integer hashMessage2 = new BigInteger(md.digest(bytesOfMessage)).intValue() % c;
            Integer r2 = pair.x;
            Integer s2 = pair.y;
            if (r2 < 1 || r2 > c - 1) {
                System.out.println("warunek r2 niespelnoiny");
            }
            if (s2 < 1 || s2 > c - 1) {
                System.out.println("wrunek s2 niespelniony");
            }
            Integer w = new BigInteger(s2.toString()).modInverse(new BigInteger(c.toString())).intValue();
            Integer u1 = (hashMessage2 * w) % c;
            Integer u2 = (r2 * w) % c;
            List<ECPoint> upa = group.generateG(pa);
            ECPoint d;
            if (upa.size() < u2) {
                d = upa.get((upa.size() % u2) - 1);
            } else {
                d = upa.get(u2 - 1);
            }
            System.out.println(d.toString()+" "+grup.get(u1).toString());
            ECPoint fi = group.add2(grup.get(u1 - 1), d);

            System.out.println("R:" + r + " R*:" + fi.x % c);

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
