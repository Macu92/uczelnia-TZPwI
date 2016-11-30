/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krzywe.eliptyczne;

import java.math.BigInteger;

/**
 *
 * @author Maciek
 */
public class ECPoint {

    int x;
    int y;
    boolean isInfinity;

    public ECPoint(){
        this.isInfinity = true;
        this.x = 0;
        this.y = 0;
    }
    
    public ECPoint(int x, int y){
        this.isInfinity = false;
        this.x = x;
        this.y = y;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.x;
        hash = 67 * hash + this.y;
        hash = 67 * hash + (this.isInfinity ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ECPoint other = (ECPoint) obj;
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "("+x+","+y+")";
    }
    
}
