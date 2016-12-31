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
	byte[] pixels2;

	public Steganography(int bitsNumber, String message, String imagePath) {
		if (bitsNumber > 7) {
			System.out.println("There is only 8 bits. Setting 8");
			this.bitsNumber = 7;
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

	public void cipher(String fileNameToSave) {
		String bytesAsString = messageToBinaryString(message);
		Color oldOne;
		int red,green,blue,messageByteCounter = 0;
		for (int x = 0; x < orginal.getWidth(); x++) {
			for (int y = 0; y < orginal.getHeight(); y++) {
				oldOne = new Color(orginal.getRGB(x, y));
				red = oldOne.getRed();
				for (int bitPosition = 0; bitPosition < bitsNumber; bitPosition++) {
					if (messageByteCounter < bytesAsString.length()) {
						red = changeByte(red, bitPosition,
								bytesAsString.charAt(messageByteCounter++)) & 0xFF;
					}
				}
				green = oldOne.getGreen();
				for (int bitPosition = 0; bitPosition < bitsNumber; bitPosition++) {
					if (messageByteCounter < bytesAsString.length()) {
						green = changeByte(green, bitPosition,
								bytesAsString.charAt(messageByteCounter++));
					}
				}
				blue = oldOne.getBlue();
				for (int bitPosition = 0; bitPosition < bitsNumber; bitPosition++) {
					if (messageByteCounter < bytesAsString.length()) {
						blue = changeByte(blue, bitPosition,
								bytesAsString.charAt(messageByteCounter++));
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
		try {
			new ImageReader().saveImage(output,fileNameToSave);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String decipher(String path) {
		BufferedImage imageToDecode = null;
		try {
			imageToDecode = new ImageReader().getImage(path);
		} catch (IOException e) {
			System.out.println("FILE PARSING ERR");
		}
		String messageInBinary = "";
		decode: {
			for (int x = 0; x < imageToDecode.getWidth(); x++) {
				for (int y = 0; y < imageToDecode.getHeight(); y++) {
					Color pixColor = new Color(imageToDecode.getRGB(x, y));
					for (int bitPosition = 0; bitPosition < bitsNumber; bitPosition++) {
						messageInBinary += readBytes(pixColor.getRed(), bitPosition);
					}
					for (int bitPosition = 0; bitPosition < bitsNumber; bitPosition++) {
						messageInBinary += readBytes(pixColor.getGreen(), bitPosition);
					}
					for (int bitPosition = 0; bitPosition < bitsNumber; bitPosition++) {
						messageInBinary += readBytes(pixColor.getBlue(), bitPosition);
					}
					if (checkEndOffMessage(messageInBinary)) {
						break decode;
					}
					;
				}
			}
		}

		return binaryToMessage(messageInBinary);
	}

	private boolean checkEndOffMessage(String message) {
		if (message.length() > 13) {
			if (message.contains("00000000000000")) {
				return true;
			}
			
		}
		return false;
	}

	public String binaryToMessage(String messageInBinary) {
		String stringMsg = "";
		int val;
		int endIndex = messageInBinary.indexOf("00000000000000");
		messageInBinary = messageInBinary.substring(0, endIndex);
		for (int i = 0; i * 7 < messageInBinary.length(); i++) {
			String substring = messageInBinary.substring(i * 7, (i * 7) + 7);
			val = Integer.parseInt(substring, 2);
			stringMsg += (char) val;
		}
		return stringMsg;
	}

	public String messageToBinaryString(String message) {
		byte[] messageBytes = message.getBytes();
		String bytesAsString = "";
		for (byte b : messageBytes) {
			String converted = Integer.toString(b, 2);
			while (converted.length() < 7) {
				converted = "0" + converted;
			}
			bytesAsString += converted;
		}
		return bytesAsString + "00000000000000";
	}

	private int changeByte(int color, int bitPosition, char byteValue) {
		char[] colorAsCharArray = Integer.toString(color, 2).toCharArray();
		Character[] temp = ArrayUtils.toObject(colorAsCharArray);
		List<Character> chars = Arrays.asList(temp);
		chars = new LinkedList<Character>(chars);
		Collections.reverse(chars);
		while (chars.size() < 8) {
			chars.add('0');
		}
		Collections.reverse(chars);
		chars.set(chars.size() - 1 - bitPosition, byteValue);
		temp = chars.toArray(temp);
		Integer intVal = Integer.valueOf(String.valueOf(ArrayUtils.toPrimitive(temp)), 2);
		return intVal;
	}

	private char readBytes(int color, int bit) {
		char[] colorAsCharArray = Integer.toString(color, 2).toCharArray();
		Character[] temp = ArrayUtils.toObject(colorAsCharArray);
		List<Character> chars = Arrays.asList(temp);
		chars = new LinkedList<Character>(chars);
		Collections.reverse(chars);
		while (chars.size() < 8) {
			chars.add('0');
		}
		Collections.reverse(chars);
		return chars.get(chars.size() - 1 - bit);
	}
}
