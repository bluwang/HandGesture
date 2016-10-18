package liuwei.ch.app.model;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.video.BackgroundSubtractorMOG;
import liuwei.ch.app.util.MyTool;

/**
 * �˶�����ࣺ
 * ��ȡͼƬ�е��˶�Ŀ��
 * @author Administrator
 *
 */
public class MotionDetect {

	private BackgroundSubtractorMOG MOG;
	private Mat bsMat;
	private List<Rect> rects;
	
	public MotionDetect() {
		MOG = new BackgroundSubtractorMOG();
		bsMat = new Mat();
		rects = new ArrayList<Rect>();
	}
	
	/**
	 * ��ͼ���е��˶�Ŀ����м�⣬����������ľ��ο�
	 * @param image ����ͼ��
	 * @return �����˶�Ŀ�������ľ��ο�
	 */
	public List<Rect> detect(Mat image) {
		rects.clear();
		MOG.apply(image, bsMat);
		rects = MyTool.getContours(bsMat);

		return rects;
	}
}
