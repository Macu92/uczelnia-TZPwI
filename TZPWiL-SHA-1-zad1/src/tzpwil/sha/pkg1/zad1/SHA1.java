/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tzpwil.sha.pkg1.zad1;

import java.nio.ByteBuffer;

/**
 *
 * @author ziniewiczm
 */
public class SHA1 {

    int[] H = {0x67452301, 0xEFCDAB89, 0x98BADCFE, 0x10325476, 0xC3D2E1F0};
    int[] K = {0x5A827999, 0x6ED9EBA1, 0x8F1BBCDC, 0xCA62C1D6};
//
//    private byte[] stringToByte(String string) {
//        return string.getBytes();
//    }
//
//    public String toHexString(byte[] ba) {
//        StringBuilder str = new StringBuilder();
//        for (int i = 0; i < ba.length; i++) {
//            str.append(String.format("%x", ba[i]));
//        }
//        return str.toString();
//    }
//
//    public String fromHexString(String hex) {
//        StringBuilder str = new StringBuilder();
//        for (int i = 0; i < hex.length(); i += 2) {
//            str.append((char) Integer.parseInt(hex.substring(i, i + 2), 16));
//        }
//        return str.toString();
//    }
//
//    private String binaryToHexString(String binary) {
//        int decimal = Integer.parseInt(binary, 2);
//        return Integer.toString(decimal, 16);
//    }
//
//    static String hexToBinString(String s) {
//        int num = (Integer.parseInt(s, 16));
//        return Integer.toBinaryString(num);
//    }
//
//    public String getText() {
//        return text;
//    }
//
//    public void setText(String text) {
//        this.text = text;
//    }
//
//    private String addOneNumberToLastBlock(String lastHexBlock) {
//        return lastHexBlock.concat("1");
//    }
//
//    private String fillLastHexBlockTo112(String lastHexBlock) {
//        String block = lastHexBlock;
//        while (block.length() < 112) {
//            block = block.concat("0");
//        }
//        return block;
//    }
//
//    private String addMessageLengthToHexBlock(String lastHexBlock, String binaryMessage) {
//        int binaryMessageLength = binaryMessage.length();
//        String lengthAsHexString = Integer.toHexString(binaryMessageLength);
//        //extend string length to 16
//        while (lengthAsHexString.length() < 16) {
//            lengthAsHexString = "0" + lengthAsHexString;
//        }
//        System.out.println(lengthAsHexString);
//        return lastHexBlock+lengthAsHexString;
//    }
//
//    void cipher() {
//        System.out.println(addMessageLengthToHexBlock(fillLastHexBlockTo112(addOneNumberToLastBlock(binaryToHexString("111111011111100"))), "111111011111100"));
//        System.out.println(hexToBinString(addMessageLengthToHexBlock(fillLastHexBlockTo112(addOneNumberToLastBlock(binaryToHexString("111111011111100"))), "111111011111100")));
//    }

//      private separateMessageByteTableIn512BitBlocks(byte[] message){
//          List<byte[]> blockList = new LinkedList<>();
//          for(int i = 0; i<message.length;i+=64){
//              blockList.
//          }
//      }
    public byte[] longToBytes(long x) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(x);
        return buffer.array();
    }
    
    private void processTheBlock(byte[] processedBlock){
        
    }

    public String cipher(String message) {
        byte[] messageInBytes = message.getBytes(); // 1 byte = 8 bit!!
        int dataLength = messageInBytes.length;
        int lastBlockBytesLength = dataLength % 64; //  dl ostatniego bloku
        int lastBlockFilling = 0;
        if (lastBlockBytesLength <= 55) { // jezeli dl ostatniego bpolu mniejsza niz 55 to wsadzimy tam 1 i 64bit wiadoamosc
            lastBlockFilling = 64 - lastBlockBytesLength;
        } else { // jak nie to trzeba dopisac nwy blok
            lastBlockFilling = 128 - lastBlockBytesLength;
        }

        byte[] lastBlockFillingValue = new byte[lastBlockFilling]; // tablica uzupelniajaca
        lastBlockFillingValue[0] = (byte) 0x80; // wrzucamy 1 na index 0
        byte[] messageLengthAsByteArray = longToBytes(dataLength * 8); // pobieramy tablice dlugosci wiadomosci w bitach jako tablica bajtowa

        for (int i = 1; i < lastBlockFillingValue.length - 1; i++) { // w petli przepisujemy dl wiadoamosci do tablicy uzupeniajacej
            if (i > messageLengthAsByteArray.length) {
                break;
            }
            lastBlockFillingValue[lastBlockFillingValue.length - i] = messageLengthAsByteArray[messageLengthAsByteArray.length - i];
        }
        byte[] output = new byte[dataLength + lastBlockFilling]; // tablica wyjciowa
        System.arraycopy(messageInBytes, 0, output, 0, dataLength);
        System.arraycopy(lastBlockFillingValue, 0, output, dataLength, lastBlockFillingValue.length);

        int blocksQuantity = output.length / 64;
        byte[] temp = new byte[64];
        for (int counter = 0; counter < blocksQuantity; counter++) {
            System.arraycopy(output, counter * 64, temp, 0, 64);
        }

        return "";
    }
}
