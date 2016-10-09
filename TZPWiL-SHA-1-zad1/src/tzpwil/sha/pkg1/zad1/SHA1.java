 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tzpwil.sha.pkg1.zad1;

/**
 *
 * @author ziniewiczm
 */
public class SHA1 {

    private String text;

    private byte[] stringToByte(String string) {
        return string.getBytes();
    }

    public String toHexString(byte[] ba) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < ba.length; i++) {
            str.append(String.format("%x", ba[i]));
        }
        return str.toString();
    }

    public String fromHexString(String hex) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < hex.length(); i += 2) {
            str.append((char) Integer.parseInt(hex.substring(i, i + 2), 16));
        }
        return str.toString();
    }

    private String binaryToHexString(String binary) {
        int decimal = Integer.parseInt(binary, 2);
        return Integer.toString(decimal, 16);
    }

    static String hexToBinString(String s) {
        int num = (Integer.parseInt(s, 16));
        return Integer.toBinaryString(num);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    private String addOneNumberToLastBlock(String lastHexBlock) {
        return lastHexBlock.concat("1");
    }

    private String fillLastHexBlockTo112(String lastHexBlock) {
        String block = lastHexBlock;
        while (block.length() < 112) {
            block = block.concat("0");
        }
        return block;
    }

    private String addMessageLengthToHexBlock(String lastHexBlock, String binaryMessage) {
        int binaryMessageLength = binaryMessage.length();
        String lengthAsHexString = Integer.toHexString(binaryMessageLength);
        //extend string length to 16
        while (lengthAsHexString.length() < 16) {
            lengthAsHexString = "0" + lengthAsHexString;
        }
        System.out.println(lengthAsHexString);
        return lastHexBlock+lengthAsHexString;
    }

    void cipher() {
        System.out.println(addMessageLengthToHexBlock(fillLastHexBlockTo112(addOneNumberToLastBlock(binaryToHexString("111111011111100"))), "111111011111100"));
        System.out.println(addMessageLengthToHexBlock(fillLastHexBlockTo112(addOneNumberToLastBlock(binaryToHexString("111111011111100"))), "111111011111100").length());
    }

}
