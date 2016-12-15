package steanografia;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

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
	
	public void saveImage(BufferedImage img) throws IOException{
		try {
		    // retrieve image
		    BufferedImage bi = img;
		    File outputfile = new File("saved.jpg");
		    ImageIO.write(bi, "jpg", outputfile);
		} catch (IOException e) {
			System.err.println("Blad zapisu obrazka");
			throw e;
		}
	}
	
}
