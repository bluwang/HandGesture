package liuwei.ch.app.model;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.opencv.video.BackgroundSubtractorMOG;
import org.opencv.video.BackgroundSubtractorMOG2;

import liuwei.ch.app.util.MyTool;

/**
 * �˶�����ࣺ
 * ��ȡͼƬ�е��˶�Ŀ��
 * @author Administrator
 *
 */
public class MotionDetect {

	private BackgroundSubtractorMOG2 MOG2;
	private Mat bsMat;
	private List<MatOfPoint> contours;
	
	public MotionDetect() {
		MOG2 = new BackgroundSubtractorMOG2(-1, 11, false);
		bsMat = new Mat();
		contours = new ArrayList<MatOfPoint>();
	}
	
	/**
	 * ��ͼ���е��˶�Ŀ����м�⣬����������ľ��ο�
	 * @param image ����ͼ��
	 * @return �����˶�Ŀ�������ľ��ο�
	 */
	public List<MatOfPoint> detect(Mat image) {
		contours.clear();
		MOG2.apply(image, image);
//		Imgproc.dilate(bsMat, bsMat, new Mat());
//		Imgproc.erode(bsMat, bsMat, new Mat());
//		contours = MyTool.getContours(bsMat);

		return contours;
	}
	
}
