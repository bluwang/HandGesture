package liuwei.ch.app.util;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import org.opencv.core.Mat;

import com.sun.prism.paint.Color;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public class MyTool {

	/**
	 * Convert image of opencv to image of javaFX
	 * @param frame the image of opencv
	 * @return the image of javaFX
	 */
    public static Image MatToImage(Mat frame) {
		// TODO Auto-generated method stub
    	int width = frame.width(), height = frame.height(), channels = frame.channels();
		byte[] sourcePixels = new byte[width * height * channels];
		frame.get(0, 0, sourcePixels);
		BufferedImage bufferedImage;
		if (frame.channels() > 1) {
			bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		} else {
			bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
		}
		final byte[] targetPixels = ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData();
		System.arraycopy(sourcePixels, 0, targetPixels, 0, sourcePixels.length);
		Image image = SwingFXUtils.toFXImage(bufferedImage, null);
		return image;
	}
    
    public static boolean isSkin(Color c) {
      	/*  boolean e1 = (c.getRed()>95) && (c.getGreen()>40) && (c.getBlue()>15) && ((Math.max(c.getRed(),Math.max(c.getGreen(),c.getBlue())) - Math.min(c.getRed(), Math.min(c.getGreen(),c.getBlue())))>15) && (Math.abs(c.getRed()-c.getGreen())>15) && (c.getRed()>c.getGreen()) && (c.getRed()>c.getBlue());
      	  int R = c.getRed();
      	  int G = c.getGreen();
      	  int B = c.getBlue();
      	  boolean e2 = (R>220) && (G>210) && (B>170) && (Math.abs(R-G)<=15) && (R>B) && (G>B);*/
    		//return ( c.getRed() < 55);
    		//return e1 || e2;
    		boolean e1;
    		int R = (int) c.getRed(); int G = (int) c.getGreen() ; int B = (int) c.getBlue();
    		e1 = R < 62 && G < 62 && B < 62;
    		boolean e2;
    		e2 = Math.abs(R-G) < 15 && Math.abs(R-B) < 15 && Math.abs(B-G) <15 ;
    		
    		return e2 && e1;
    	
    	}

}
