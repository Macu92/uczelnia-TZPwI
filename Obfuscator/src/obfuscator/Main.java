/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obfuscator;

/**
 *
 * @author Maciek
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String file = new FileLoader().readFile("Puppy.java");
        Obfuscator ob = new Obfuscator();
//        System.out.println(ob.deleteComments(file));
//        System.out.println(ob.deleteWhiteSpaces(file));
//        System.out.println(ob.changeIdentifiers(file));
        
        System.out.println(ob.deleteWhiteSpaces(ob.changeIdentifiers(ob.deleteComments(file))));
    }

}
