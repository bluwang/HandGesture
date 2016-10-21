package liuwei.ch.app.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.management.MBeanConstructorInfo;

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
import org.opencv.imgproc.Imgproc;

import liuwei.ch.app.util.MyTool;

/**
 * 对手进行颜色检测
 * @author Administrator
 *
 */
public class ColorDetect {
	
	private static int H_MIN1 = 0; 
	private static int H_MAX1 = 40; 
	private static int S_MIN1 = 30; 
	private static int S_MAX1 = 170; 
	private static int V_MIN1 = 30; 
	private static int V_MAX1 = 256; 
	private static int H_MIN2 = 156; 
	private static int H_MAX2 = 180; 
	private static int S_MIN2 = 30; 
	private static int S_MAX2 = 170; 
	private static int V_MIN2 = 30; 
	private static int V_MAX2 = 256; 
	
	public ColorDetect() {
	}
	
	/**
	 * 用颜色对手进行检测
	 * @param inputMat 输入图像
	 * @return 返回检测到的在颜色范围内的轮廓矩形框
	 */
	public List<MatOfPoint> detect(Mat inputMat) {
		Mat mat1 = new Mat();
		Mat mat2 = new Mat();
		Mat resultMat = new Mat();

//		Imgproc.medianBlur(inputMat, inputMat, 5);
//		Imgproc.blur(inputMat, inputMat, new Size(3, 3));
//		Imgproc.GaussianBlur(inputMat, inputMat, 5.0, 1.0);
//		Imgproc.bilateralFilter(inputMat, inputMat, d, sigmaColor, sigmaSpace);

		//原始的效果差点
//		Imgproc.cvtColor(inputMat, resultMat, Imgproc.COLOR_BGR2HSV);
		Imgproc.cvtColor(inputMat, inputMat, Imgproc.COLOR_BGR2YCrCb);
		//人脸干扰大
//		Imgproc.cvtColor(inputMat, inputMat, Imgproc.COLOR_BGR2HLS);
		//效果好像不错啊
//		Imgproc.cvtColor(inputMat, inputMat, Imgproc.COLOR_BGR2Lab);
		//比上面的差点
//		Imgproc.cvtColor(inputMat, inputMat, Imgproc.COLOR_BGR2Luv);
		//和lab好像差不多
//		Imgproc.cvtColor(inputMat, inputMat, Imgproc.COLOR_BGR2XYZ);
		//和luv差不多
//		Imgproc.cvtColor(inputMat, inputMat, Imgproc.COLOR_BGR2YUV);
		//卧槽，效果竟然和lab差不多！！
//		Imgproc.cvtColor(inputMat, inputMat, Imgproc.COLOR_BGR2GRAY);

//		Core.inRange(resultMat, new Scalar(H_MIN, S_MIN, V_MIN, 255), new Scalar(H_MAX, S_MAX, V_MAX, 255), resultMat);
		Core.inRange(inputMat, new Scalar(H_MIN1, S_MIN1, V_MIN1), new Scalar(H_MAX1, S_MAX1, V_MAX1), inputMat);
//		Core.inRange(inputMat, new Scalar(H_MIN1, S_MIN1, V_MIN1), new Scalar(H_MAX1, S_MAX1, V_MAX1), inputMat);
		Core.bitwise_not(inputMat, inputMat);
//		Core.inRange(inputMat, new Scalar(H_MIN2, S_MIN2, V_MIN2), new Scalar(H_MAX2, S_MAX2, V_MAX2), mat2);
//		Core.bitwise_or(mat1, mat2, inputMat);
		
//		Imgproc.threshold(inputMat, inputMat, 100, 255, Imgproc.THRESH_BINARY);
		Mat element = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(2, 2));
		Imgproc.dilate(inputMat, inputMat, element, new Point(-1, -1), 5);
		Imgproc.erode(inputMat, inputMat, element, new Point(-1, -1), 5);
		List<MatOfPoint> contours = new ArrayList<MatOfPoint>();   
		Imgproc.findContours(inputMat, contours, new Mat(), Imgproc.RETR_LIST,Imgproc.CHAIN_APPROX_SIMPLE);
		
		return contours;
//		return null;
	}
	
	
	/**
	 * 设置颜色检测范围值
	 * @param data 颜色检测HSV数据
	 */
	public static void setHSVData(int[] data) {
		H_MIN1 = data[0];
		H_MAX1 = data[1];
		S_MIN1 = data[2];
		S_MAX1 = data[3];
		V_MIN1 = data[4];
		V_MAX1 = data[5];
		H_MIN2 = data[6];
		H_MAX2 = data[7];
		S_MIN2 = data[8];
		S_MAX2 = data[9];
		V_MIN2 = data[10];
		V_MAX2 = data[11];
	}

	public static int getH_MIN1() {
		return H_MIN1;
	}

	public static void setH_MIN1(int h_MIN1) {
		H_MIN1 = h_MIN1;
	}

	public static int getH_MAX1() {
		return H_MAX1;
	}

	public static void setH_MAX1(int h_MAX1) {
		H_MAX1 = h_MAX1;
	}

	public static int getS_MIN1() {
		return S_MIN1;
	}

	public static void setS_MIN1(int s_MIN1) {
		S_MIN1 = s_MIN1;
	}

	public static int getS_MAX1() {
		return S_MAX1;
	}

	public static void setS_MAX1(int s_MAX1) {
		S_MAX1 = s_MAX1;
	}

	public static int getV_MIN1() {
		return V_MIN1;
	}

	public static void setV_MIN1(int v_MIN1) {
		V_MIN1 = v_MIN1;
	}

	public static int getV_MAX1() {
		return V_MAX1;
	}

	public static void setV_MAX1(int v_MAX1) {
		V_MAX1 = v_MAX1;
	}

	public static int getH_MIN2() {
		return H_MIN2;
	}

	public static void setH_MIN2(int h_MIN2) {
		H_MIN2 = h_MIN2;
	}

	public static int getH_MAX2() {
		return H_MAX2;
	}

	public static void setH_MAX2(int h_MAX2) {
		H_MAX2 = h_MAX2;
	}

	public static int getS_MIN2() {
		return S_MIN2;
	}

	public static void setS_MIN2(int s_MIN2) {
		S_MIN2 = s_MIN2;
	}

	public static int getS_MAX2() {
		return S_MAX2;
	}

	public static void setS_MAX2(int s_MAX2) {
		S_MAX2 = s_MAX2;
	}

	public static int getV_MIN2() {
		return V_MIN2;
	}

	public static void setV_MIN2(int v_MIN2) {
		V_MIN2 = v_MIN2;
	}

	public static int getV_MAX2() {
		return V_MAX2;
	}

	public static void setV_MAX2(int v_MAX2) {
		V_MAX2 = v_MAX2;
	}

}
