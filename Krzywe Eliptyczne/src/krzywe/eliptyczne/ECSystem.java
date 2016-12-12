/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krzywe.eliptyczne;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Maciek
 */
public class ECSystem {

    public ECPoint generateOpenKey(int privateKey, List<ECPoint> group) {
        return group.get(privateKey - 1);
    }

    public ECPoint generateSecretKey(int privateKey, ECPoint openKey, ECGroup group) {
        List<ECPoint> grup1 = group.generateG(openKey);
        ECPoint secretKey = null;
        if (grup1.size() < privateKey) {
            secretKey = grup1.get((privateKey % (grup1.size() + 1)) - 1);
        } else {
            secretKey = grup1.get(privateKey - 1);
        }
        return secretKey;
    }

    public ECPoint sign(String message, ECGroup group, List<ECPoint> generatedGPoints, int privateKey) {
        Integer s = 0;
        int r = 0;
        MessageDigest md;
        byte[] bytesOfMessage;
        Integer c = group.getC();
        do {
            try {
                Double randKD = new Double("0");
                Integer randK = 0;
                do {
                    randKD = (Math.random() * 100) % c;
                    randK = randKD.intValue()+1;
                    ECPoint kpoint = generatedGPoints.get(randK - 1);
                    r = kpoint.x % c;
                } while (r == 0);
                System.out.println("bi" + randK + " " + c);
                Integer kmod = new BigInteger(randK.toString()).modInverse(new BigInteger(c.toString())).intValue();
                bytesOfMessage = message.getBytes("UTF-8");
                md = MessageDigest.getInstance("SHA-1");
                Integer hashMessage = new BigInteger(md.digest(bytesOfMessage)).intValue() % c;
                Integer temp = hashMessage + privateKey * r;
                s = (kmod * temp) % c;
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(ECSystem.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(ECSystem.class.getName()).log(Level.SEVERE, null, ex);
            }
        } while (s == 0);
        return new ECPoint(r, s);
    }

    public ECPoint verifySIgn(String message, ECPoint signature, ECGroup group, ECPoint openPaKey, List<ECPoint> generatedGPoints) {
         ECPoint fi = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            Integer c = group.getC();
            byte[] bytesOfMessage2 = message.getBytes("UTF-8");
            Integer hashMessage2 = new BigInteger(md.digest(bytesOfMessage2)).intValue() % c;
            Integer r2 = signature.x;
            Integer s2 = signature.y;
            if (r2 < 1 || r2 > c - 1) {
                System.out.println("warunek r2 niespelnoiny");
            }
            if (s2 < 1 || s2 > c - 1) {
                System.out.println("wrunek s2 niespelniony");
            }
            Integer w = new BigInteger(s2.toString()).modInverse(new BigInteger(c.toString())).intValue();
            Integer u1 = (hashMessage2 * w) % c;
            Integer u2 = (r2 * w) % c;
            List<ECPoint> upa = group.generateG(openPaKey);
            ECPoint d;
            if (upa.size() < u2) {
                d = upa.get((upa.size() % u2) - 1);
            } else {
                d = upa.get(u2 - 1);
            }
            System.out.println(d.toString() + " " + generatedGPoints.get(u1).toString());
            fi = group.add2(generatedGPoints.get(u1 - 1), d);
        } catch (Exception ex) {
            Logger.getLogger(ECSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fi;
    }
}
