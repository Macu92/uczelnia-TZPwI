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
    int c;
    
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
                if (funcY(y) == funcX(x)) {
                    groupPoints.add(new ECPoint(x, y));
                }
            }
        }
        return groupPoints;
    }

    private int funcX(int x) {
        return ((x * x * x) + (a * x) + b) % M;
    }

    private int funcY(int y) {
        return (y * y) % M;
    }

    private boolean isValid(int a, int b) throws Exception {
        if (a > M) {
            throw new Exception("a nie może być większe M");
        } else if (b > M) {
            throw new Exception("b nie może być większe M");
        }
        return 4 * (a * a * a) + 27 * (b * b) != 0;
    }

    public List<ECPoint> generateG(ECPoint g) {
        c=1;
        ECPoint current = new ECPoint(g.x, g.y);
        List<ECPoint> kGGroup = new LinkedList<>();
        kGGroup.add(g);
        try {
            c++;
            current = doubling2(g);
        } catch (Exception ex) {
            ex.printStackTrace();
            return kGGroup;
        }
        kGGroup.add(current);

        while (true) {
            c++;
            try {
                current = add2(g, current);
            } catch (Exception ex) {
                break;
            }
            kGGroup.add(current);
        }
        return kGGroup;
    }

    public ECPoint add2(ECPoint p, ECPoint q) throws Exception {
        if (p.x == 0 && p.y == 0) {
            return q;
        } else if (q.x == 0 && q.y == 0) {
            return p;
        } else if (p.x == q.x && (p.y == -q.y + M ||
                (p.y == q.y) && (p.y == 0))) {
            throw new Exception("ZERO");
        }else if(p.x==q.x && p.y==q.y){
            return doubling2(p);
        }else  {//
            int s = divideModulo(p.y - q.y, p.x - q.x, M);
            Double xr = (Math.pow(s, 2) - p.x - q.x) % M;
            if (xr < 0) {
                xr += M;
            }
            Double yr = (-p.y + (s * (p.x - xr))) % M;
            if (yr < 0) {
                yr += M;
            }
            return new ECPoint(xr.intValue(), yr.intValue());
        }
    }
    private int divideModulo(Integer aa, Integer bb, Integer M) {
        return ((aa % M) * new BigInteger(bb.toString())
                .modInverse(new BigInteger(M.toString()))
                .intValue()) % M;
    }
    private ECPoint doubling2(ECPoint p) throws Exception {
        if (p.y == 0) {
            throw new Exception("ZERO");
        }
        int s = divideModulo(((Double) (3 * Math.pow(p.x, 2)))
                .intValue() + a, 2 * p.y, M);
        Double xr = (Math.pow(s, 2) - 2 * p.x) % M;
        if (xr < 0) {
            xr += M;
        }
        Double yr = (-p.y + (s * (p.x - xr))) % M;
        if (yr < 0) {
            yr += M;
        }
        return new ECPoint(xr.intValue(), yr.intValue());
    }
    
    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public int getM() {
        return M;
    }

    public void setM(int M) {
        this.M = M;
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }
    
    
}
