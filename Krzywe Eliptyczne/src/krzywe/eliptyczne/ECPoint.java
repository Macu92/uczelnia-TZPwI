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
    
}
