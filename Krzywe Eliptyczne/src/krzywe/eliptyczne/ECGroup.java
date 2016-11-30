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
//         System.out.println(new BigInteger("4").multiply(a.pow(3)).add(new BigInteger("27").multiply(b.pow(2))));
        System.out.println((4 * (a * a * a) + 27 * (b * b)));
        return 4 * (a * a * a) + 27 * (b * b) != 0;
    }

    public List<ECPoint> generateG(ECPoint g) {
        c=1;
        ECPoint current = new ECPoint(g.x, g.y);
        List<ECPoint> kGGroup = new LinkedList<>();
        kGGroup.add(g);
        try {
            c=1;
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
//        kGGroup.remove(kGGroup.size() - 1);
        return kGGroup;
    }

    private ECPoint doubling(ECPoint p) {
        Integer s = lambda2(p, p);
        while (s < 0) {
            s += M;
        }
        Integer xr = ((s * s) - (2 * p.x)) % M;
        if (xr < 0) {
            xr += M;
        }
        Integer yr = (-p.y + s * (p.x - xr)) % M;
        if (yr < 0) {
            yr += M;
        }
        return new ECPoint(xr, yr);
    }

    private ECPoint add(ECPoint p, ECPoint q) {
        Integer s = lambda1(p, q);
        while (s < 0) {
            s += M;
        }
        Integer xr = ((s * s) - p.x - q.x) % M;
        if (xr < 0) {
            xr += M;
        }
        Integer yr = (-p.y + s * (p.x - xr)) % M;
        if (yr < 0) {
            yr += M;
        }
        return new ECPoint(xr, yr);
    }

    private ECPoint add2(ECPoint p, ECPoint q) throws Exception {
        if (p.x == 0 && p.y == 0) {
            return q;
        } else if (q.x == 0 && q.y == 0) {
            return p;
        } else if (p.x == q.x && (p.y == -q.y + M || (p.y == q.y) && (p.y == 0))) {
            throw new Exception("ZERO");
        } else if (p.x != q.x && p.y != q.y) {
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
        throw new Exception("null");
    }

    private int divideModulo(Integer aa, Integer bb, Integer M) {
        return ((aa % M) * new BigInteger(bb.toString()).modInverse(new BigInteger(M.toString())).intValue()) % M;
    }

    private ECPoint doubling2(ECPoint p) throws Exception {
        if (p.y == 0) {
            throw new Exception("ZERO");
        }
        int s = divideModulo(((Double) (3 * Math.pow(p.x, 2))).intValue() + a, 2 * p.y, M);
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
    //def double_point(p, a, M):
    //   if p[1] == 0:
    //       return 0, 0
    //   s = divide_modulo((3 * math.pow(p[0], 2)) + a, 2 * p[1], M)
    //   xr = (math.pow(s, 2) - (2 * p[0])) % M
    //   yr = (-p[1] + (s * (p[0] - xr))) % M
    //   return int(xr), int(yr)

    //    def divide_modulo(a, b, M):
    //      return ((a % M) * number.inverse(b, M)) % M
    //    def add_points(p, q, a, M):
    //   if p[0] == 0 and p[1] == 0:
    //       return q
    //   if q[0] == 0 and q[1] == 0:
    //       return p
    //   if p[0] == q[0] and (p[1] == -q[1] + M or p[1] == q[1] == 0):
    //       return 0, 0
    //   if p[0] != q[0] or p[1] != q[1]:
    //       s = divide_modulo(p[1] - q[1], p[0] - q[0], M)
    //       xr = (math.pow(s, 2) - p[0] - q[0]) % M
    //       yr = (-p[1] + (s * (p[0] - xr))) % M
    //       return int(xr), int(yr)
    //   else:
    //       return double_point(p, a, M)
    // Gdy P i Q są różne
    private Integer lambda1(ECPoint p, ECPoint q) {
        Integer a = p.y - q.y;
        if (a < 0) {
            a += M;
        }
        a %= M;
        Integer b = 2 * p.y;
        if (b < 0) {
            b += M;
        }
        BigInteger bb = new BigInteger(b.toString());
        bb = bb.modInverse(new BigInteger(((Integer) M).toString()));
        return (a * bb.intValue()) % M;
    }

    // Gdy P i Q są takie same
    private Integer lambda2(ECPoint p, ECPoint q) {
        Integer a1 = (3 * (p.x * p.x)) + a;
        if (a1 < 0) {
            a1 += M;
        }
        a1 %= M;
        Integer b = 2 * p.y;
        if (b < 0) {
            b += M;
        }
        BigInteger bb = new BigInteger(b.toString());
        bb = bb.modInverse(new BigInteger(((Integer) M).toString()));
        return (a1 * bb.intValue()) % M;
    }

    // Gdy P i Q są takie same X, ale różne Y
//    private BigInteger Lambda3(ECPoint p, ECPoint q) {
//        BigInteger a1 = (3 * (p.X * p.X)) + a;
//        if (a1 < 0) {
//            a1 += m;
//        }
//        a1 %= m;
//        BigInteger b = (p.Y + q.Y);
//        if (b < 0) {
//            b += m;
//        }
//        b = b.modInverse(m);
//        return (a1 * b) % m;
//    }

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
