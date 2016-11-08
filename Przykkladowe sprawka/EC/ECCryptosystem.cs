using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace EC
{
    public class ECCryptosystem
    {
        public ECCryptosystem(ECGroup group)
        {
            this.group = group;
        }

        public ECPoint[] Encipher(ECPoint Pm, int k, int nb)
        {
            this.pb = multiply(nb, g);
            return new ECPoint[] { multiply(k, g), group.Add(Pm, multiply(k, pb)) };
        }

        public ECPoint Decipher(ECPoint Cm1, ECPoint Cm2, int k, int nb)
        {
            this.pb = multiply(nb, g);
            return group.Substract(Cm2, multiply(nb, Cm1));
        }

        public List<ECPoint> DeterminekG(ECPoint g)
        {
            this.g = g;
            var current = new ECPoint(g);
            var kGGroup = new List<ECPoint>();
            kGGroup.Add(g);
            kGGroup.Add(current = group.AddToSelf(g));

            while (!g.Equals(current))
                kGGroup.Add(current = group.Add(g, current));
            kGGroup.RemoveAt(kGGroup.Count - 1);
            this.kGGroup = kGGroup;
            return kGGroup;
        }

        public ECPoint[] KeyExchange(int na, int nb)
        {
            this.pa = multiply(na, g);
            this.pb = multiply(nb, g);

            return new ECPoint[] { multiply(na, pb), multiply(nb, pa) };
        }

        public ECPoint G { get { return g; } }
        public ECPoint Pa { get { return pa; } }
        public ECPoint Pb { get { return pb; } }
        public int C { get { return kGGroup.Count; } }

        private ECPoint multiply(int k, ECPoint p)
        {
            if (p.Equals(g))
                return kGGroup[k - 1];
            if (p.IsInfinity)
                return p;
            int offset = 0; // Odległość Pm od początku listy k*G
            int pos = 0; // Pozycja k*Pb
            // Szukamy punktu na liście kG i zwracamy jego odległość od początku
            for (int i = 0; i < kGGroup.Count; ++i)
            {
                if (kGGroup[i].Equals(p))
                {
                    offset = i + 1;
                    pos = offset;
                    break;
                }
            }

            var c = kGGroup.Count;
            // Szukamy pozycji k*Pb
            for (int i = 0; i < k - 1; ++i)
            {
                pos = ((pos + offset) % c);
                Console.WriteLine(pos);
            }
            return kGGroup[pos > 0 ? pos - 1 : pos] as ECPoint;
        }

        private ECGroup group = null;
        private ECPoint pa, pb, g;
        private List<ECPoint> kGGroup;
    }
}
