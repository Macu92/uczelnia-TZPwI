/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kalkulaotr;

/**
 *
 * @author Maciek
 */
public class Kalkulator {

    public static void main(String[] args) {
        int[] tab = {1, 2, 3, 4, 5};
        int wynik = 0;
        for (int i = 0; i < 5; i++) {
            wynik += tab[i];
        }
        System.out.println("wynik " + wynik);
        
        for (int i = tab.length - 1; i>= 0; i--) {
            System.out.print(tab[i]);
        }

    }
}
