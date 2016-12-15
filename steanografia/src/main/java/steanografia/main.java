package steanografia;

public class main {

	public static void main(String[] args) {
		Steganography ste =  new Steganography(8, "tesowa", "./xz.jpg");
		ste.cipher();

	}

}
