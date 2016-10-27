/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podpiscyfrowyrsa;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 *
 * @author Maciek
 */
class RSA {
    int bits, gcd;
    BigInteger n, publicKeyPart, privateKeyPart;
    public RSA(int bits) {
        this.bits = bits;
        SecureRandom r = new SecureRandom();
        BigInteger p = new BigInteger(bits / 2, 100, r);
        BigInteger q = new BigInteger(bits / 2, 100, r);
        n = p.multiply(q);
        BigInteger m = (p.subtract(BigInteger.ONE)).multiply(q
                .subtract(BigInteger.ONE));
        publicKeyPart = new BigInteger("3"); // warunek nieparzystosci
        while (m.gcd(publicKeyPart).intValue() > 1) {
            publicKeyPart = publicKeyPart.add(new BigInteger("2")); // warunek nieparzystosci
        }
        privateKeyPart = publicKeyPart.modInverse(m);
    }
    public String encrypt(String message) {
        return (new BigInteger(message.getBytes())).modPow(publicKeyPart, n).toString();
    }
    public BigInteger encrypt(BigInteger message) {
        return message.modPow(publicKeyPart, n);
    }
    public String decrypt(String message) {
        return new String((new BigInteger(message)).modPow(privateKeyPart, n).toByteArray());
    }
    public BigInteger decrypt(BigInteger message) {
        return message.modPow(privateKeyPart, n);
    }

}
