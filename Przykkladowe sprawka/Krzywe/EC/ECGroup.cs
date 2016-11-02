using RSA;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace EC
{
    public class ECGroup
    {
        public ECGroup(BigInteger a, BigInteger b, BigInteger m)
        {
            // 4 * p^3 + 27 * q^2 != 0
            if (!IsValid(a, b))
                throw new Exception("Nie można stworzyć krzywej.");
            this.a = a;
            this.b = b;
            this.m = m;
        }

        public List<ECPoint> Generate()
        {
            var group = new List<ECPoint>();
            group.Add(new ECPoint());

            for (BigInteger x = 0; x < M; ++x)
            {
                for (BigInteger y = 0; y < M; ++y)
                {
                    // Sprawdź, czy punkt należy do krzywej
                    if (((y * y) % m) == Func(x))
                    {
                        group.Add(new ECPoint(x, y));
                    }
                }
            }

            return group;
        }

        public ECPoint Add(ECPoint p, ECPoint q)
        {
            if (p.IsInfinity)
                return q;
            if (q.IsInfinity)
                return p;
            // Gdy P i Q są różne
            if (p.X != q.X)
            {
                BigInteger l = Lambda1(p, q);
                if (l < 0)
                    l += m;
                BigInteger rx = ((l * l) - p.X - q.X) % m;
                if (rx < 0)
                    rx += m;
                BigInteger ry = (-p.Y + l * (p.X - rx)) % m;
                if (ry < 0)
                    ry += m;
                return new ECPoint(rx, ry);
            }
            // W przeciwnym wypadku
            else
            {
                // Jeśli Q = -P oraz Yp = Yq = 0, to nieskończoność.
                if ((p.Y == -q.Y + M) || ((p.Y == 0) && (q.Y == 0)))
                    return new ECPoint();
                // Ale...
                else if (p.Y == q.Y)
                {
                    BigInteger l = Lambda2(p, q);
                    while (l < 0)
                        l += m;
                    BigInteger rx = ((l * l) - (2 * p.X)) % m;
                    if (rx < 0)
                        rx += m;
                    BigInteger ry = (-p.Y + l * (p.X - rx)) % m;
                    if (ry < 0)
                        ry += m;
                    return new ECPoint(rx, ry);
                }
                else
                {
                    BigInteger l = Lambda3(p, q);
                    while (l < 0)
                        l += m;
                    BigInteger rx = ((l * l) - (2 * p.X)) % m;
                    if (rx < 0)
                        rx += m;
                    BigInteger ry = (-p.Y + l * (p.X - rx)) % m;
                    if (ry < 0)
                        ry += m;
                    return new ECPoint(rx, ry);
                }
            }
        }

        public ECPoint AddToSelf(ECPoint p)
        {
            BigInteger l = Lambda2(p, p);
            while (l < 0)
                l += m;
            BigInteger rx = ((l * l) - (2 * p.X)) % m;
            if (rx < 0)
                rx += m;
            BigInteger ry = (-p.Y + l * (p.X - rx)) % m;
            if (ry < 0)
                ry += m;
            return new ECPoint(rx, ry);
        }

        public ECPoint Substract(ECPoint p, ECPoint q)
        {
            q.Y = -q.Y + M;
            return Add(p, q);
        }

        public BigInteger A { get { return a; } }
        public BigInteger B { get { return b; } }
        public BigInteger M { get { return m; } }

        private BigInteger Func(BigInteger x)
        {
            return ((x * x * x) + (a * x) + b) % m;
        }

        private static bool IsValid(BigInteger a, BigInteger b)
        {
            return (4 * (a * a * a) + 27 * (b * b)) != zero;
        }

        // Gdy P i Q są różne
        private BigInteger Lambda1(ECPoint p, ECPoint q)
        {
            BigInteger a = p.Y - q.Y;
            if (a < 0)
                a += m;
            a %= m;
            BigInteger b = p.X - q.X;
            if (b < 0)
                b += m;
            b = b.modInverse(m);
            return (a * b) % m;
        }

        // Gdy P i Q są takie same
        private BigInteger Lambda2(ECPoint p, ECPoint q)
        {
            BigInteger a1 = (3 * (p.X * p.X)) + a;
            if (a1 < 0)
                a1 += m;
            a1 %= m;
            BigInteger b = 2 * p.Y;
            if (b < 0)
                b += m;
            b = b.modInverse(m);
            return (a1 * b) % m;
        }

        // Gdy P i Q są takie same X, ale różne Y
        private BigInteger Lambda3(ECPoint p, ECPoint q)
        {
            BigInteger a1 = (3 * (p.X * p.X)) + a;
            if (a1 < 0)
                a1 += m;
            a1 %= m;
            BigInteger b = (p.Y + q.Y);
            if (b < 0)
                b += m;
            b = b.modInverse(m);
            return (a1 * b) % m;
        }

        private BigInteger a, b, m;
        private static BigInteger zero = 0;
        private static BigInteger one = 1;
    }
}
