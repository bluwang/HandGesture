package liuwei.ch.app.model;

import java.util.ArrayList;
import java.util.List;

import org.omg.PortableServer.POAHelper;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfInt4;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
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
	private boolean isDetect;
	private int width = 400;
	private int height = 400;
	private List<Rect> faceRects;
	private List<Rect> motionRects;
	private List<Rect> colorRects;
	private List<MatOfPoint> faceContours;
	private List<MatOfPoint> motionContours;
	private List<MatOfPoint> colorContours;
	private Rect handRect;
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

		faceContours = new ArrayList<MatOfPoint>();
		motionContours = new ArrayList<MatOfPoint>();
		colorContours = new ArrayList<MatOfPoint>();

		currentFrame = new Mat();
		capture = new VideoCapture(videodevice);

		cameraActive = false;
		isDetect = false;
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
						while (cameraActive) {
							capture.read(currentFrame);
							Mat frame = new Mat();
							currentFrame.copyTo(frame);
							
//							Imgproc.blur(currentFrame, currentFrame, new Size(3, 3));
							
							//人脸检测
//							faceRects = faceDetect.detect(currentFrame);
							
							//运动检测
//							motionContours = motionDetect.detect(currentFrame);
//							Imgproc.drawContours(currentFrame, motionContours, -1, new Scalar(255, 255, 255, 255), 3);
//							MyTool.drawRect(currentFrame, motionContours);
							
							//颜色匹配检测
							colorContours = colorDetect.detect(frame);
//							colorDetect.detect(currentFrame, "test");
//							Imgproc.drawContours(currentFrame, colorContours, -1, new Scalar(255, 255, 255, 255), 3);
//							MyTool.drawRect(currentFrame, motionContours);
							
//							showResult();
//							
//							if (isDetect) {
//								currentFrame = currentFrame.submat(handRect).clone();
//							}
//							
//							isDetect = false;
//							findHull();
							drawContours();
							
							Core.flip(currentFrame, currentFrame, 1);
							Imgproc.resize(currentFrame, currentFrame, new Size(350, 300));
							
							currentImage = MyTool.MatToImage(currentFrame);

							Platform.runLater(() -> {
								imageView.setImage(currentImage);
								imageView.setFitWidth(350);
								imageView.setFitHeight(300);
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
			System.out.println(e);
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
		
		//将颜色匹配和运动检测到的轮廓进行融合
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
		
		//保留最大面积的轮廓,并画出
		Rect maxRect;
		if (mergeRects.size() > 0) {
			isDetect = true;
			maxRect = mergeRects.get(0);

			for (int i = 1; i < mergeRects.size(); i++) {
				if (maxRect.area() < mergeRects.get(i).area()) {
					maxRect = mergeRects.get(i);
				}
			}
			
			handRect = maxRect;
			Core.rectangle(currentFrame, maxRect.tl(), maxRect.br(), new Scalar(255, 255, 255, 255), 4);
		}
	}
	
	private void findHull() {
		List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
		
		Imgproc.findContours(currentFrame, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
		for (int i = 0; i < contours.size(); i++) {
			Imgproc.drawContours(currentFrame, contours, -1, new Scalar(255, 255, 255, 255), 3);
		}
	}
	
	private void drawContours() {
		if (colorContours.size() > 0) {
//			Imgproc.drawContours(currentFrame, colorContours, -1, new Scalar(255, 255, 255, 255), 3);
			
			//取得最大面积的轮廓
			int boundPos = 0;
			for (int i = 1; i < colorContours.size(); i++) {
				if (Imgproc.contourArea(colorContours.get(boundPos)) < Imgproc.contourArea(colorContours.get(i))) {
					boundPos = i;
				}
			}

			MatOfPoint maxContour = colorContours.get(boundPos);
			colorContours.clear();
			colorContours.add(maxContour);
//			Imgproc.drawContours(currentFrame, colorContours, -1, new Scalar(0, 0, 255, 255), 3);
			
			MatOfPoint2f pointMat = new MatOfPoint2f();
			//这个起平滑曲线的作用，很强
			Imgproc.approxPolyDP(new MatOfPoint2f(maxContour.toArray()), pointMat, 18, true);
//			MatOfPoint contour = new MatOfPoint();
			pointMat.convertTo(maxContour, CvType.CV_32S);
			
			colorContours.clear();
			colorContours.add(maxContour);
//			Imgproc.drawContours(currentFrame, colorContours, -1, new Scalar(0, 255, 0, 255), 3);
			
			MatOfInt hull = new MatOfInt();
//			Imgproc.convexHull(new MatOfPoint(contour.toArray()), hull);
			Imgproc.convexHull(maxContour, hull, true);
			Imgproc.convexHull(maxContour, hull, false);
			
			// Convert MatOfInt to MatOfPoint for drawing convex hull
			// Loop over all contours
			List<Point[]> hullpoints = new ArrayList<Point[]>();
			Point[] points = new Point[hull.rows()];
			
			// Loop over all points that need to be hulled in current contour
			for(int i=0; i < hull.rows(); i++){
				int index = (int)hull.get(i, 0)[0];
				points[i] = new Point(maxContour.get(index, 0)[0], maxContour.get(index, 0)[1]);
			}
			
			hullpoints.add(points);
			
			// Convert Point arrays into MatOfPoint
			List<MatOfPoint> hullmop = new ArrayList<MatOfPoint>();
			for(int i=0; i < hullpoints.size(); i++){
				MatOfPoint mop = new MatOfPoint();
				mop.fromArray(hullpoints.get(i));
				hullmop.add(mop);
			}
			
//			colorContours.clear();
//			Imgproc.drawContours(currentFrame, hullmop, -1, new Scalar(255, 0, 0, 255), 3);
			
			System.out.println(hull.toArray().length);
			if (hull.toArray().length > 3) {
				MatOfInt4 convexDefect = new MatOfInt4();
//				Imgproc.convexityDefects(new MatOfPoint(contour.toArray()), hull, convexDefect);
				Imgproc.convexityDefects(maxContour, hull, convexDefect);
				
				List<Integer> cdList = convexDefect.toList();
				Point[] datas = maxContour.toArray();
				int fingerNumber = 0;
				
				for (int i = 0; i < cdList.size(); i += 4) {
					Point ptStart = datas[cdList.get(i)];
					Point ptEnd = datas[cdList.get(i+1)];
					Point ptFar = datas[cdList.get(i+2)];
					
//				Core.line(currentFrame, ptStart, ptEnd, new Scalar(255, 0, 0), 3);
//				Core.line(currentFrame, ptStart, ptFar, new Scalar(0, 255, 0), 3);
//				Core.line(currentFrame, ptEnd, ptFar, new Scalar(0, 0, 255), 3);
					
//				Core.circle(currentFrame, ptStart, 5, new Scalar(255, 0, 0), 3);
//				Core.circle(currentFrame, ptEnd, 5, new Scalar(0, 255, 0), 3);
//				Core.circle(currentFrame, ptFar, 5, new Scalar(0, 0, 255), 3);
					
					System.out.println(distanceP2P(ptStart, ptFar));
					System.out.println(distanceP2P(ptEnd, ptFar));
					if (distanceP2P(ptStart, ptFar) > 40.0 && distanceP2P(ptEnd, ptFar) > 40.0 ) {
						Core.circle(currentFrame, ptStart, 5, new Scalar(255, 0, 0), 3);
						Core.circle(currentFrame, ptEnd, 5, new Scalar(0, 255, 0), 3);
						Core.circle(currentFrame, ptFar, 5, new Scalar(0, 0, 255), 3);
						fingerNumber += 1;
					}
				}
				System.out.println("fingerNumber:" + fingerNumber);
			}
		}
	}
	
	private float distanceP2P(Point a, Point b){
		float d= (float) Math.sqrt(Math.abs( Math.pow(a.x-b.x,2) + Math.pow(a.y-b.y,2) )) ;  
		return d;
	}
	
}
