using RSA;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace EC
{
    public class ECPoint
    {
        public ECPoint()
        {
            this.isInfinity = true;
            X = 0;
            Y = 0;
        }

        public ECPoint(BigInteger x, BigInteger y)
        {
            this.X = x;
            this.Y = y;
        }

        public ECPoint(ECPoint rhs)
        {
            this.X = rhs.X;
            this.Y = rhs.Y;
        }

        public ECPoint(string str)
        {
            str = str.Trim(new char[] { '(', ')' });
            string[] xy = str.Split(',');
            X = new BigInteger(xy[0], 10);
            Y = new BigInteger(xy[1], 10);
        }

        public override string ToString()
        {
            if (!IsInfinity)
                return "(" + X.ToString() + ", " + Y.ToString() + ")";
            else
                return "INFINITY";
        }

        public override bool Equals(object obj)
        {
            ECPoint rhs = obj as ECPoint;
            return ((this.X == rhs.X) && (this.Y == rhs.Y)) || (this.IsInfinity && rhs.IsInfinity);
        }

        public override int GetHashCode()
        {
            return base.GetHashCode();
        }

        public BigInteger X { get; set; }
        public BigInteger Y { get; set; }
        public bool IsInfinity { get { return isInfinity; } }

        private bool isInfinity = false;
    }
}
