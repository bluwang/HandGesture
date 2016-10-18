package liuwei.ch.app.model;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.video.BackgroundSubtractorMOG;
import liuwei.ch.app.util.MyTool;

/**
 * 运动检测类：
 * 获取图片中的运动目标
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
	 * 对图像中的运动目标进行检测，获得其轮廓的矩形框
	 * @param image 输入图像
	 * @return 返回运动目标轮廓的矩形框
	 */
	public List<Rect> detect(Mat image) {
		rects.clear();
		MOG.apply(image, bsMat);
		rects = MyTool.getContours(bsMat);

		return rects;
	}
}
