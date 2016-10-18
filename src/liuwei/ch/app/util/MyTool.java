package liuwei.ch.app.util;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

/**
 * 通用的工具类，其中定义了图片格式转换、绘制轮廓、获取轮廓等功能
 * @author Administrator
 *
 */
public class MyTool {
	
	/**
	 * Convert image of opencv to image of javaFX
	 * @param frame the image of opencv
	 * @return the image of javaFX
	 */
	public static Image MatToImage(Mat frame) {
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
	
	/**
	 * 绘制输入图像的轮廓
	 * @param imagae 输入的图像
	 */
	public static void drawContours(Mat image) {
		List<MatOfPoint> contours = new ArrayList<MatOfPoint>();   

		Imgproc.threshold(image, image, 100, 255, Imgproc.THRESH_BINARY);
		Imgproc.findContours(image, contours, new Mat(), Imgproc.RETR_LIST,Imgproc.CHAIN_APPROX_SIMPLE);

		if (contours.size() > 0) {
			Rect r = Imgproc.boundingRect(contours.get(0));
			for (int i = 1; i < contours.size(); i++) {
				r = Imgproc.boundingRect(contours.get(i));
				if (r.area() > 1500) {
					Core.rectangle(image, r.tl(), r.br(), new Scalar(255, 0, 0, 255), 3);
				}
			}
		}
	}
	
	/**
	 * 把相应的矩形框绘制到图像中
	 * @param image 需要绘制的图像
	 * @param rects 相应的矩形框
	 */
	public static void drawContours(Mat image, List<Rect> rects) {
		for (int i = 1; i < rects.size(); i++) {
			Core.rectangle(image, rects.get(i).tl(), rects.get(i).br(), new Scalar(255, 0, 0, 255), 3);
		}
	}
	
	/**
	 * 把相应的矩形框绘制到图像中,可以指定相应颜色
	 * @param image 需要绘制的图像
	 * @param rects 相应的矩形框
	 * @param colorName 相应的颜色名
	 */
	public static void drawContours(Mat image, List<Rect> rects, String colorName) {
		Scalar color;

		switch (colorName) {
		case "red":
			color = new Scalar(0, 0, 255, 255);
			break;

		case "green":
			color = new Scalar(0, 255, 0, 255);
			break;

		case "blue":
			color = new Scalar(255, 0, 0, 255);
			break;
			
		case "white":
			color = new Scalar(255, 255, 255, 255);
			break;
		
		case "black":
			color = new Scalar(0, 0, 0, 255);
			break;

		default:
			color = new Scalar(0, 0, 0, 0);
			break;
		}

		if (rects != null) {
			for (int i = 1; i < rects.size(); i++) {
				Core.rectangle(image, rects.get(i).tl(), rects.get(i).br(), color, 3);
			}
		}
	}

	/**
	 * 获取相应图像的轮廓矩形框
	 * @param image 输入的图像
	 * @return 图像轮廓矩形框
	 */
	public static List<Rect> getContours(Mat image) {
		List<Rect> rects = new ArrayList<Rect>();
		List<MatOfPoint> contours = new ArrayList<MatOfPoint>();   

		Imgproc.threshold(image, image, 100, 255, Imgproc.THRESH_BINARY);
		Imgproc.findContours(image, contours, new Mat(), Imgproc.RETR_LIST,Imgproc.CHAIN_APPROX_SIMPLE);

		if (contours.size() > 0) {
			Rect rect = Imgproc.boundingRect(contours.get(0));
			for (int i = 1; i < contours.size(); i++) {
				rect = Imgproc.boundingRect(contours.get(i));
				if (rect.area() > 1500) {
					rects.add(rect);
				}
			}
		}

		return rects;
	}

}
