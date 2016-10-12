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

    private int dataLength;
    private int lastBlockBytesLength = 0;
    private int lastBlockFill = 0;
    private byte[] messageInBytes;
    int[] init = {0x67452301, 0xEFCDAB89, 0x98BADCFE, 0x10325476, 0xC3D2E1F0};
    int[] H = null;
    int[] K = {0x5A827999, 0x6ED9EBA1, 0x8F1BBCDC, 0xCA62C1D6};

    public byte[] longToBytes(long x) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(x);
        return buffer.array();
    }

    private void processTheBlock(byte[] processedBlock) {
        int[] W = new int[80];
        H = init;
         for (int outer = 0; outer < 16; outer++) {
                int temp = 0;
                for (int inner = 0; inner < 4; inner++) {
                    temp = (processedBlock[outer * 4 + inner] & 0x000000FF) << (24 - inner * 8);
                    W[outer] = W[outer] | temp;
                }
            }

        for (int j = 16; j < 80; j++) {
            W[j] = rotateLeft(W[j - 3] ^ W[j - 8] ^ W[j - 14] ^ W[j - 16], 1);
        }

        int A = H[0], B = H[1], C = H[2], D = H[3], E = H[4];
        int temp, F;

        for (int j = 0; j < 80; j++) {
            if (j < 20) {
                F = (B & C) | ((~B) & D);
                temp = rotateLeft(A, 5) + F + E + K[0] + W[j];
                System.out.println(Integer.toHexString(K[0]));
                E = D;
                D = C;
                C = rotateLeft(B, 30);
                B = A;
                A = temp;
            } else if (j >= 20 && j < 40) {
                F = B ^ C ^ D;
                temp = rotateLeft(A, 5) + F + E + K[1] + W[j];
                System.out.println(Integer.toHexString(K[1]));
                E = D;
                D = C;
                C = rotateLeft(B, 30);
                B = A;
                A = temp;
            } else if (j >= 40 && j < 60) {
                F = (B & C) | (B & D) | (C & D);
                temp = rotateLeft(A, 5) + F + E + K[2] + W[j];
                E = D;
                D = C;
                C = rotateLeft(B, 30);
                B = A;
                A = temp;
            } else if (j >= 60 && j < 80) {
                F = B ^ C ^ D;
                temp = rotateLeft(A, 5) + F + E + K[3] + W[j];
                E = D;
                D = C;
                C = rotateLeft(B, 30);
                B = A;
                A = temp;
            }
        }

        H[0] += A;
        H[1] += B;
        H[2] += C;
        H[3] += D;
        H[4] += E;
    }

    private void initBasicInfoAboutMessage(String message) {
        messageInBytes = message.getBytes(); // 1 byte = 8 bit!!
        dataLength = messageInBytes.length;
        lastBlockBytesLength = dataLength % 64; //  dl ostatniego bloku
        lastBlockFill = 0;
        if (lastBlockBytesLength <= 55) { // jezeli dl ostatniego bpolu mniejsza niz 55 to wsadzimy tam 1 i 64bit wiadoamosc
            lastBlockFill = 64 - lastBlockBytesLength;
        } else { // jak nie to trzeba dopisac nwy blok
            lastBlockFill = 128 - lastBlockBytesLength;
        }
    }

    private byte[] fillLastBlock() {
        byte[] lastBlockFillingValue = new byte[lastBlockFill]; // tablica uzupelniajaca
        lastBlockFillingValue[0] = (byte) 0x80; // wrzucamy 1 na index 0
        byte[] messageLengthAsByteArray = longToBytes(dataLength * 8); // pobieramy tablice dlugosci wiadomosci w bitach jako tablica bajtowa

        for (int i = 1; i < lastBlockFillingValue.length - 1; i++) { // w petli przepisujemy dl wiadoamosci do tablicy uzupeniajacej
            if (i > messageLengthAsByteArray.length) {
                break;
            }
            lastBlockFillingValue[lastBlockFillingValue.length - i] = messageLengthAsByteArray[messageLengthAsByteArray.length - i];
        }
        return lastBlockFillingValue;
    }

    public String cipher(String message) {
        initBasicInfoAboutMessage(message);
        byte[] messageByteArrayFilled = fillLastBlock();

        byte[] output = new byte[dataLength + lastBlockFill]; // tablica wyjciowa

        System.arraycopy(messageInBytes, 0, output, 0, dataLength);
        System.arraycopy(messageByteArrayFilled, 0, output, dataLength, messageByteArrayFilled.length);

        int blocksQuantity = output.length / 64;
        byte[] temp = new byte[64];
        for (int counter = 0; counter < blocksQuantity; counter++) {
            System.arraycopy(output, counter * 64, temp, 0, 64);
            processTheBlock(temp);
        }
        System.out.print(intArrayToHexStr(H));
        return intArrayToHexStr(H);
    }

    private int rotateLeft(int value, int bits) {
        int q = (value << bits) | (value >>> (32 - bits));
        return q;
    }

    private String intArrayToHexStr(int[] data) {
        String output = "";
        String tempStr = "";
        int tempInt = 0;
        for (int cnt = 0; cnt < data.length; cnt++) {

            tempInt = data[cnt];

            tempStr = Integer.toHexString(tempInt);

            if (tempStr.length() == 1) {
                tempStr = "0000000" + tempStr;
            } else if (tempStr.length() == 2) {
                tempStr = "000000" + tempStr;
            } else if (tempStr.length() == 3) {
                tempStr = "00000" + tempStr;
            } else if (tempStr.length() == 4) {
                tempStr = "0000" + tempStr;
            } else if (tempStr.length() == 5) {
                tempStr = "000" + tempStr;
            } else if (tempStr.length() == 6) {
                tempStr = "00" + tempStr;
            } else if (tempStr.length() == 7) {
                tempStr = "0" + tempStr;
            }
            output = output + tempStr;
        }//end for loop
        return output;
    }//end intArrayToHexStr
}
