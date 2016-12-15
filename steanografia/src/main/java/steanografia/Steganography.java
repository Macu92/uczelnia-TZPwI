package steanografia;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;

public class Steganography {

	private int bitsNumber;
	private String message;
	private BufferedImage orginal;
	private BufferedImage output;

	public Steganography(int bitsNumber, String message, String imagePath) {
		if (bitsNumber > 8) {
			System.out.println("There is only 8 bits. Setting 8");
			this.bitsNumber = 8;
		}
		this.bitsNumber = bitsNumber;
		this.message = message;
		try {
			this.orginal = new ImageReader().getImage(imagePath);
			this.output = new ImageReader().getImage(imagePath);
		} catch (IOException e) {
			System.exit(0);
		}
	}

	public void cipher() {
		byte[] messageBytes = message.getBytes();
		String bytesAsString = "";
		for (byte b : messageBytes) {
			bytesAsString += Integer.toString(b, 2);
		}
		int messageByteCounter = 0;
		Color oldOne;
		int red;
		int green;
		int blue;
		for (int x = 0; x < orginal.getWidth(); x++) {
			for (int y = 0; y < orginal.getHeight(); y++) {
				oldOne = new Color(orginal.getRGB(x, y));
				red = oldOne.getRed();
				for (int bitPosition = 0; bitPosition < bitsNumber; bitPosition++) {
					if (messageByteCounter < bytesAsString.length()) {
						red = changeByte(red, bitPosition, bytesAsString.charAt(messageByteCounter++));
					}
				}
				green = oldOne.getGreen();
				for (int bitPosition = 0; bitPosition < bitsNumber; bitPosition++) {
					if (messageByteCounter < bytesAsString.length()) {
						green = changeByte(green, bitPosition, bytesAsString.charAt(messageByteCounter++));
					}
				}

				blue = oldOne.getBlue();
				for (int bitPosition = 0; bitPosition < bitsNumber; bitPosition++) {
					if (messageByteCounter < bytesAsString.length()) {
						blue = changeByte(blue, bitPosition, bytesAsString.charAt(messageByteCounter++));
					}
				}
				output.setRGB(x, y, new Color(red, green, blue).getRGB());
				if (messageByteCounter >= bytesAsString.length()) {
					break;
				}
			}
			if (messageByteCounter >= bytesAsString.length()) {
				break;
			}
		}
		// Color c = new Color(image.getRGB(0,0));
		// int red = c.getRed();
		// int green = c.getGreen();
		// int blue = c.getBlue();
		byte[] pixels = ((DataBufferByte) orginal.getRaster().getDataBuffer()).getData();
		byte[] pixels2 = ((DataBufferByte) output.getRaster().getDataBuffer()).getData();
		try {
			new ImageReader().saveImage(output);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("There is only 8 bits. Setting 8");
	}

	private int changeByte(int color, int bitPosition, char byteValue) {
		char[] colorAsCharArray = Integer.toString(color, 2).toCharArray();
		Character[] temp =  ArrayUtils.toObject(colorAsCharArray);
		List<Character> chars = Arrays.asList(temp);
		chars =  new LinkedList<Character>(chars);
		Collections.reverse(chars);
		while(chars.size()<8){
			chars.add('0');
		}
		Collections.reverse(chars);
		chars.set(chars.size() - 1 - bitPosition,'1');
//		colorAsCharArray[colorAsCharArray.length - 1 - bitPosition] = byteValue;
		temp = chars.toArray(temp);
		Integer intVal = Integer.valueOf(String.valueOf(ArrayUtils.toPrimitive(temp)), 2);
		return intVal;
	}
}
