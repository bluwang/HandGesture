package liuwei.ch.app.model;

import java.util.ArrayList;
import java.util.List;

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
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;

import com.sun.javafx.scene.traversal.Direction;

import javafx.application.Platform;
import javafx.scene.control.TextField;
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
	private Geature geature;
	private Match match;
	
	private Mat currentFrame;
	private VideoCapture capture;
	private Image currentImage;
	private MatOfPoint maxContour;
	private MatOfPoint handCoutour;
	private Rect facePosition;
	private Rect matchPosition;
	private List<Rect> faceRects;
//	private List<Rect> motionRects;
	private MotionRects motionRects;
	private List<Rect> colorRects;
	private List<MatOfPoint> faceContours;
	private List<MatOfPoint> motionContours;
	private List<MatOfPoint> colorContours;
	private List<MatOfPoint> mixContours;

	private boolean cameraActive;
	private boolean isDetect;
	private boolean haveHand;
	private boolean openBrowser;
	private int width = 400;
	private int height = 400;
	private int direction;
	// Set video device
	private static int videodevice = 0;
	
	public Detect() {
		//加载openCV中的这个文件（后面的很多都要用的，先加载）
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		faceDetect = new FaceDetect();
		motionDetect = new MotionDetect();
		colorDetect = new ColorDetect();
		geature = new Geature();
		match = new Match();
		
		handCoutour = new MatOfPoint();
		facePosition = new Rect();
		matchPosition = new Rect();

		faceRects = new ArrayList<Rect>();
//		motionRects = new ArrayList<Rect>();
		motionRects = new MotionRects();
		colorRects = new ArrayList<Rect>();

		faceContours = new ArrayList<MatOfPoint>();
		motionContours = new ArrayList<MatOfPoint>();
		colorContours = new ArrayList<MatOfPoint>();

		currentFrame = new Mat();
		capture = new VideoCapture(videodevice);

		cameraActive = false;
		isDetect = false;
		haveHand = false;
		openBrowser = false;
	}
	
	/**
	 * 进行检测
	 * @param imageView JavaFX的图像显示控件
	 * @param direction2 
	 */
	public void start(ImageView imageView, TextField direction2) {
		try {
			if (this.capture.isOpened() && !cameraActive) {
				cameraActive = true;

				Thread processFrame = new Thread(new Runnable() {
					@Override
					public void run() {
						while (cameraActive) {
							capture.read(currentFrame);
							Mat frame = new Mat();
							
							Imgproc.blur(currentFrame, currentFrame, new Size(3, 3));
							
							//人脸检测
							currentFrame.copyTo(frame);
							faceRects = faceDetect.detect(frame);
							if (faceRects.size() > 0) {
								Rect maxRect = new Rect();
								for (int i = 0; i < faceRects.size() ; i++) {
									if (faceRects.get(i).area() > 10000) {
//										Core.rectangle(currentFrame, faceRects.get(i).tl(), faceRects.get(i).br(), new Scalar(255, 0, 0), 3);
										if (i == 0) {
											maxRect = faceRects.get(i);
											facePosition = maxRect;
										}
										else {
											if (faceRects.get(i).area() > maxRect.area()) {
												maxRect = faceRects.get(i);
												facePosition = maxRect;
											}
										}
									}
								}
								Core.rectangle(currentFrame, facePosition.tl(), facePosition.br(), new Scalar(255, 0, 0), 3);
							}
							else {
								Core.rectangle(currentFrame, facePosition.tl(), facePosition.br(), new Scalar(255, 0, 0), 3);
							}
							
							//运动检测
							currentFrame.copyTo(frame);
//							motionDetect.setDetectMethod("MOG");
//							motionDetect.setDetectMethod("MOG2");
							motionDetect.setDetectMethod("MyBS");
							motionContours = motionDetect.detect(frame, true);
							
							motionRects.clear();
							if (motionContours.size() > 0) {
								Rect rect = new Rect();
								for (int i = 0; i < motionContours.size() ; i++) {
									rect = Imgproc.boundingRect(motionContours.get(i));
									if (rect.area() > 10000) {
										motionRects.add(rect, i);
//										Core.rectangle(currentFrame, rect.tl(), rect.br(), new Scalar(0, 255, 0), 3);
									}
								}
							}
//							
							//颜色匹配检测
							currentFrame.copyTo(frame);
							colorContours = colorDetect.detect(frame);
							if (colorContours.size() > 0) {
								Rect rect = new Rect();
								for (int i = 0; i < colorContours.size() ; i++) {
									rect = Imgproc.boundingRect(colorContours.get(i));
									if (rect.area() > 10000) {
//										Core.rectangle(currentFrame, rect.tl(), rect.br(), new Scalar(0, 0, 255), 3);
									}
								}
							}
//							Imgproc.drawContours(currentFrame, colorContours, -1, new Scalar(255, 255, 255, 255), 3);
//							MyTool.drawRect(currentFrame, motionContours);
							
							//图像匹配
							matchPosition = match.pictureMatch(currentFrame, 3);
//							Core.rectangle(currentFrame, matchPosition.tl(), matchPosition.br(), new Scalar(255, 255, 255), 3);
							
//							showResult();
							handCoutour = mergeContours();
							
							if (handCoutour != null) {
								if (!openBrowser) {
									openBrowser = true;
									
									Windows.openBrowser();
								}
//								drawContours(currentFrame, handCoutour);
								
								//Draw the center point of the max contours
								Moments center = Imgproc.moments(handCoutour);
								int x = (int)(center.get_m10()/center.get_m00());
								int y = (int)(center.get_m01()/center.get_m00());
//								Core.circle(currentFrame, new Point(x, y), 7, new Scalar(255, 0, 125), 5);
								direction = geature.detect(new Point(x, y), currentFrame);
								
								switch (direction) {
								case 1:
									Core.putText(currentFrame, "RIGHT", new Point(0, 0), Core.FONT_ITALIC, 1.0,new Scalar(0, 0, 255), 5);
									direction2.setText("右移");
									break;

								case 2:
									Core.putText(currentFrame, "LEFT", new Point(0, 0), Core.FONT_ITALIC, 1.0,new Scalar(0, 0, 255), 5);
									direction2.setText("左移");
									break;

								default:
									break;
								}
							}
//							
//							if (isDetect) {
//								currentFrame = currentFrame.submat(handRect).clone();
//							}
//							
//							isDetect = false;
//							findHull();
							
//							drawContours(currentFrame);

//							if (filterContours()) {
//								
//								drawContours(frame, colorContours);
//							}

							Imgproc.cvtColor(currentFrame, currentFrame, Imgproc.COLOR_BGR2GRAY);
							Imgproc.threshold(currentFrame, currentFrame, 125, 255, Imgproc.THRESH_BINARY);
							Core.flip(currentFrame, currentFrame, 1);
							Imgproc.resize(currentFrame, currentFrame, new Size(350, 300));
							currentImage = MyTool.MatToImage(currentFrame);

//							Core.flip(frame, frame, 1);
//							Imgproc.resize(frame, frame, new Size(350, 300));
//							currentImage = MyTool.MatToImage(frame);

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
	
	public void getHandContours() {
		if (capture.isOpened()) {
			Mat hand = new Mat();
			capture.read(hand);
			String file = "D:\\Coding\\Java\\java\\HandGesture\\resources\\picture\\hand.png";
			Highgui.imwrite(file, hand);
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
	 * Processing contours of face, motion and color to a merge contours
	 */
	private MatOfPoint mergeContours() {
		// Use match rectangle act as breakthrough point
		// If match rectangle contain face rectangle or be contained by face rectangle, no hand
		if (isContain(facePosition, matchPosition) > 0.8) {
			haveHand = false;
			System.out.println("NO HAND");
		}
		else if (motionRects.size() > 0) {
			int maxIndex = -1;
			float maxArea = 0;
			for (int i = 0; i < motionRects.size(); i++) {
				if (isContain(motionRects.getRect(i), matchPosition) > maxArea) {
					maxIndex = i;
				}
			}
			
			if (maxIndex != -1) {
//				System.out.println("HAND");
				haveHand = true;
				Rect rect = motionRects.getRect(maxIndex);
				Core.rectangle(currentFrame, rect.tl(), rect.br(), new Scalar(0, 255, 255), 4);
				
				return motionContours.get(motionRects.getIndex(maxIndex));
			}
		}
		
		return null;
	}
	
	/**
	 * 判断两个矩形是否相互包含（当交叉区域达到一定比例时判断为包含）
	 * @param facePosition2
	 * @param matchPosition2
	 * @return
	 */
	private float isContain(Rect rect1, Rect rect2) {
		int top_x = rect1.x >= rect2.x ? rect1.x : rect2.x;
		int top_y = rect1.y >= rect2.y ? rect1.y : rect2.y;
		int bottom_x = (rect1.x+rect1.width) <= (rect2.x+rect2.width) ? (rect1.x+rect1.width) : (rect2.x+rect2.width);
		int bottom_y = (rect1.y+rect1.height) <= (rect2.y+rect2.height) ? (rect1.y+rect1.height) : (rect2.y+rect2.height);
		
		if (!(top_x > bottom_x || top_y > bottom_y)) {
			float crossArea = (bottom_x-top_x) * (bottom_y-top_y);
//			System.out.println(crossArea/rect1.area());
			if ((crossArea/rect1.area()) >= 0.5) {
				return (float) (crossArea/rect1.area());
			}
		}
		
		return 0;
	}

	private boolean filterContours() {
		if (colorContours.size() > 0) {
			//取得最大面积的轮廓
			int boundPos = 0;
			for (int i = 1; i < colorContours.size(); i++) {
				if (Imgproc.contourArea(colorContours.get(boundPos)) < Imgproc.contourArea(colorContours.get(i))) {
					boundPos = i;
				}
			}
			maxContour = colorContours.get(boundPos);
			
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * 显示检测的结果
	 */
	private void showResult() {
//		MyTool.drawContours(currentFrame, faceRects, "red");
//		MyTool.drawContours(currentFrame, motionRects, "green");
//		MyTool.drawContours(currentFrame, colorRects, "blue");
		
		//将颜色匹配和运动检测到的轮廓进行融合
//		List<Rect> mergeRects = new ArrayList<Rect>();
//		Rect motionRect, colorRect;
//
//		for (int i = 0; i < motionRects.size(); i++) {
//			for (int j = 0; j < colorRects.size(); j++) {
//				motionRect = motionRects.get(i);
//				colorRect = colorRects.get(j);
//
//				if (motionRect.area() >= colorRect.area()) {
//					if (motionRect.x <= colorRect.x && motionRect.y <= colorRect.y) {
//						mergeRects.add(motionRect);
//					}
//				}
//				else {
//					if (motionRect.x > colorRect.x && motionRect.y > colorRect.y) {
//						mergeRects.add(colorRect);
//					}
//				}
//			}
//		}
//		
//		//保留最大面积的轮廓,并画出
//		Rect maxRect;
//		if (mergeRects.size() > 0) {
//			isDetect = true;
//			maxRect = mergeRects.get(0);
//
//			for (int i = 1; i < mergeRects.size(); i++) {
//				if (maxRect.area() < mergeRects.get(i).area()) {
//					maxRect = mergeRects.get(i);
//				}
//			}
//			
//			handRect = maxRect;
//			Core.rectangle(currentFrame, maxRect.tl(), maxRect.br(), new Scalar(255, 255, 255, 255), 4);
//		}
	}
	
	private void findHull() {
		List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
		
		Imgproc.findContours(currentFrame, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
		for (int i = 0; i < contours.size(); i++) {
			Imgproc.drawContours(currentFrame, contours, -1, new Scalar(255, 255, 255, 255), 3);
		}
	}
	
private void drawContours(Mat frame, MatOfPoint contour) {
		//Draw all contours
//		Imgproc.drawContours(currentFrame, colorContours, -1, new Scalar(0, 255, 255, 255), 3);
		
		//Draw the max contours
//		colorContours.clear();
//		colorContours.add(maxContour);
//		Imgproc.drawContours(frame, colorContours, -1, new Scalar(0, 0, 255, 255), 3);
		
		//Smoothing the contours
		MatOfPoint2f pointMat = new MatOfPoint2f();
		//这个起平滑曲线的作用，很强
		Imgproc.approxPolyDP(new MatOfPoint2f(contour.toArray()), pointMat, 18, true);
		pointMat.convertTo(contour, CvType.CV_32S);
		
		//Draw the smooth contours
//		colorContours.clear();
//		colorContours.add(contour);
//		Imgproc.drawContours(frame, colorContours, -1, new Scalar(0, 255, 0, 255), 3);
		
		//Find hull of the max contours
		MatOfInt hull = new MatOfInt();
		Imgproc.convexHull(contour, hull, true);
		Imgproc.convexHull(contour, hull, false);
		
		// Convert MatOfInt to MatOfPoint for drawing convex hull
		// Loop over all contours
		List<Point[]> hullpoints = new ArrayList<Point[]>();
		Point[] points = new Point[hull.rows()];
		
		// Loop over all points that need to be hulled in current contour
		for(int i=0; i < hull.rows(); i++){
			int index = (int)hull.get(i, 0)[0];
			points[i] = new Point(contour.get(index, 0)[0], contour.get(index, 0)[1]);
		}
		
		hullpoints.add(points);
		
		// Convert Point arrays into MatOfPoint
		List<MatOfPoint> hullmop = new ArrayList<MatOfPoint>();
		for(int i=0; i < hullpoints.size(); i++){
			MatOfPoint mop = new MatOfPoint();
			mop.fromArray(hullpoints.get(i));
			hullmop.add(mop);
		}
		
		//Draw the hull of max contours
		Imgproc.drawContours(frame, hullmop, -1, new Scalar(255, 0, 0, 255), 3);
		
		if (hull.toArray().length > 3) {
			MatOfInt4 convexDefect = new MatOfInt4();
			Imgproc.convexityDefects(contour, hull, convexDefect);
			
			List<Integer> cdList = convexDefect.toList();
			Point[] datas = contour.toArray();
			
			for (int i = 0; i < cdList.size(); i += 4) {
				Point ptStart = datas[cdList.get(i)];
				Point ptEnd = datas[cdList.get(i+1)];
				Point ptFar = datas[cdList.get(i+2)];
				
				Core.line(currentFrame, ptStart, ptEnd, new Scalar(255, 0, 0), 3);
				Core.line(currentFrame, ptStart, ptFar, new Scalar(0, 255, 0), 3);
				Core.line(currentFrame, ptEnd, ptFar, new Scalar(0, 0, 255), 3);
				
				Core.circle(currentFrame, ptStart, 5, new Scalar(255, 0, 0), 3);
				Core.circle(currentFrame, ptEnd, 5, new Scalar(0, 255, 0), 3);
				Core.circle(currentFrame, ptFar, 5, new Scalar(0, 0, 255), 3);
				
//				System.out.println(distanceP2P(ptStart, ptFar));
//				System.out.println(distanceP2P(ptEnd, ptFar));
//				if (distanceP2P(ptStart, ptFar) > 40.0 && distanceP2P(ptEnd, ptFar) > 40.0 ) {
//					Core.circle(frame, ptStart, 5, new Scalar(255, 0, 0), 3);
//					Core.circle(frame, ptEnd, 5, new Scalar(0, 255, 0), 3);
//					Core.circle(frame, ptFar, 5, new Scalar(0, 0, 255), 3);
//				}
			}
		}
	}
	
	private float distanceP2P(Point a, Point b){
		float d= (float) Math.sqrt(Math.abs( Math.pow(a.x-b.x,2) + Math.pow(a.y-b.y,2) )) ;  
		return d;
	}
	
}
