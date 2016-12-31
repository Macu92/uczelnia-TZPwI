package steanografia;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageReader {

	private BufferedImage image;		
	
	public ImageReader(){
	}
	
	public BufferedImage getImage(String imagePath) throws IOException{
		File imageFile = new File(imagePath);
		try {
			image = ImageIO.read(imageFile);
			return image;
		} catch (IOException e) {
			System.err.println("Blad odczytu obrazka");
			throw e;
		}
	}
	
	public void saveImage(BufferedImage img,String name) throws IOException{
		try {
		    BufferedImage bi = img;
		    File outputfile = new File(name+".png");
		    ImageIO.write(bi, "png", outputfile);
		} catch (IOException e) {
			System.err.println("Blad zapisu obrazka");
			throw e;
		}
	}
	
}
