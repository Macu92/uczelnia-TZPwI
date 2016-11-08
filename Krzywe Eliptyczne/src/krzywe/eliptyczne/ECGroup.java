/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krzywe.eliptyczne;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Maciek
 */
public class ECGroup {

    int a;
    int b;
    int M;

    public ECGroup(int a, int b, int M) throws Exception {
        this.a = a;
        this.b = b;
        this.M = M;
        if (!isValid(a, b)) {
            throw new Exception("Nie można stworzyć krzywej.");
        }
    }

    public List<ECPoint> generateGroupElemets() {
        List<ECPoint> groupPoints = new LinkedList<>();
        groupPoints.add(new ECPoint());
        for (int x = 0; x < M; x++) {
            for (int y = 0; y < M; y++) {
                if(funcY(y)==funcX(x)){
                    groupPoints.add(new ECPoint(x,y));
                }
            }
        }
        return groupPoints;
    }

    private int funcX(int x) {
        return ((x * x * x) + (a * x) + b) % M;
    }
    private int funcY(int y) {
        return (y*y) % M;
    }

    private boolean isValid(int a, int b) throws Exception {
        if (a > M) {
            throw new Exception("a nie może być większe M");
        } else if (b > M) {
            throw new Exception("b nie może być większe M");
        }
//         System.out.println(new BigInteger("4").multiply(a.pow(3)).add(new BigInteger("27").multiply(b.pow(2))));
        System.out.println((4 * (a * a * a) + 27 * (b * b)));
        return 4 * (a * a * a) + 27 * (b * b) != 0;
    }

}
