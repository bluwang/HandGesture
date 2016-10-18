package liuwei.ch.app.model;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import liuwei.ch.app.util.MyTool;

/**
 * 对手势进行检测
 * @author Administrator
 *
 */
public class Detect {
	
	private FaceDetect faceDetect;
	private MotionDetect motionDetect;
	private ColorDetect colorDetect;
	
	private Mat currentFrame;
	private VideoCapture capture;
	private Image currentImage;

	private boolean cameraActive;
	private int width = 400;
	private int height = 400;
	private List<Rect> faceRects;
	private List<Rect> motionRects;
	private List<Rect> colorRects;
	// Set video device
	private static int videodevice = 0;
	
	public Detect() {
		//加载openCV中的这个文件（后面的很多都要用的，先加载）
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		faceDetect = new FaceDetect();
		motionDetect = new MotionDetect();
		colorDetect = new ColorDetect();

		faceRects = new ArrayList<Rect>();
		motionRects = new ArrayList<Rect>();
		colorRects = new ArrayList<Rect>();

		currentFrame = new Mat();
		capture = new VideoCapture(videodevice);

		cameraActive = false;
	}
	
	/**
	 * 进行检测
	 * @param imageView JavaFX的图像显示控件
	 */
	public void start(ImageView imageView) {
		try {
			if (this.capture.isOpened() && !cameraActive) {
				cameraActive = true;

				Thread processFrame = new Thread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						while (cameraActive) {
							capture.read(currentFrame);
							Imgproc.blur(currentFrame, currentFrame, new Size(3, 3));
							
							//人脸检测
							faceRects = faceDetect.detect(currentFrame);
							
							//运动检测
							motionRects = motionDetect.detect(currentFrame);
							
							//颜色匹配检测
							colorRects = colorDetect.detect(currentFrame);
							
							showResult();
							Core.flip(currentFrame, currentFrame, 1);
							Imgproc.resize(currentFrame, currentFrame, new Size(350, 300));
							
							currentImage = MyTool.MatToImage(currentFrame);

							Platform.runLater(() -> {
								imageView.setImage(currentImage);
								imageView.setFitWidth(width);
								imageView.setFitHeight(height);
							});
						}
					}
				});
				
				processFrame.setDaemon(true);
				processFrame.setName("processFrame");
				processFrame.start();
			}
			else {
				System.out.println("capture can't open");
			}
		} 
		catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	/**
	 * 关闭摄像头，停止检测
	 */
	public void stopCamera() {
		if (cameraActive) {
			if (capture.isOpened()) {
				capture.release();
			}
		}
	}
	
	/**
	 * 显示检测的结果
	 */
	private void showResult() {
		MyTool.drawContours(currentFrame, faceRects, "red");
		MyTool.drawContours(currentFrame, motionRects, "green");
		MyTool.drawContours(currentFrame, colorRects, "blue");
		
		List<Rect> mergeRects = new ArrayList<Rect>();
		Rect motionRect, colorRect;
		for (int i = 0; i < motionRects.size(); i++) {
			for (int j = 0; j < colorRects.size(); j++) {
				motionRect = motionRects.get(i);
				colorRect = colorRects.get(j);
				if (motionRect.area() >= colorRect.area()) {
					if (motionRect.x <= colorRect.x && motionRect.y <= colorRect.y) {
						mergeRects.add(motionRect);
					}
				}
				else {
					if (motionRect.x > colorRect.x && motionRect.y > colorRect.y) {
						mergeRects.add(colorRect);
					}
				}
			}
		}
		
		MyTool.drawContours(currentFrame, mergeRects, "white");
	}
	
}
