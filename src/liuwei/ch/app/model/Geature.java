package liuwei.ch.app.model;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.nio.channels.NonWritableChannelException;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.PointerType;
import com.sun.jna.platform.win32.BaseTSD;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinDef.DWORD;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinDef.INT_PTR;
import com.sun.jna.platform.win32.WinDef.LPARAM;
import com.sun.jna.platform.win32.WinDef.LRESULT;
import com.sun.jna.platform.win32.WinDef.WPARAM;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.platform.win32.WinUser.INPUT;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.media.jfxmedia.events.NewFrameEvent;

public class Geature {
	public interface User32 extends StdCallLibrary {
		User32 INSTANCE = (User32) Native.loadLibrary("user32", User32.class);
		HWND GetForegroundWindow();  // add this
		int GetWindowTextA(PointerType hWnd, byte[] lpString, int nMaxCount);
		void SendInput(DWORD dword, INPUT[] array, int size);
		LRESULT SendMessage(HWND hwnd, int message, WPARAM wParam, LPARAM lParam);
	   }

	private Point start;
	private Point lastest;
	private Mat dstHist;
	
	private List<Point> track;
	
	private boolean isStart;
	private int[] channels;
	private float[] histRange;
	
	public Geature() {
		start = new Point();
		lastest = new Point();

		track = new ArrayList<Point>();
		isStart = false;
		channels = new int[]{0, 1};
		dstHist = new Mat();
		histRange = new float[]{0, 255};
	}
	
	public int detect(Point current, Mat image) {
		if (track.size() < 5) {
			if (track.size() == 0) {
				start = current;
				lastest = start;

				track.add(current);
			}
			else if (Math.abs(current.x - lastest.x) > 5 || Math.abs(current.y - lastest.y) > 5) {
				track.add(current);
				lastest = current;
			}
		}
		else {
			if (current.x - start.x < 25) {
				System.out.println("right:ÓÒÒÆ");
				
				byte[] windowText = new byte[512];
				PointerType hwnd = User32.INSTANCE.GetForegroundWindow(); // then you can call it!
				User32.INSTANCE.GetWindowTextA(hwnd, windowText, 512);
				System.out.println(Native.toString(windowText));
				
//				User32.INSTANCE.SendMessage((HWND) hwnd, 0x28, new WPARAM(0), new LPARAM(0));
//				Core.putText(image, "RIGHT", current, Core.FONT_ITALIC, 1.0,new Scalar(0, 0, 255), 5);
				
				try {
					Robot robot = new Robot();
					onward(robot);
				} catch (AWTException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return 1;
			}
			else if (current.x - start.x > 25) {
				System.out.println("left:×óÒÆ");
				return 2;
			}
			else if (current.y - start.y > 25) {
				System.out.println("top");
			}
			else if (current.y - start.y < 25) {
				System.out.println("bottom");
			}
			
			track.clear();
		}
		
		return 0;
	}
	
	public void track(Mat image, Rect rect) {
		Imgproc.cvtColor(image, image, Imgproc.COLOR_RGB2HSV);
	}

	//Ç°½øº¯Êý
	public static void onward(Robot robot)
	
	{
		
		robot.keyPress(KeyEvent.VK_ALT);
		
		robot.keyPress(KeyEvent.VK_RIGHT);
		
		robot.keyRelease(KeyEvent.VK_ALT);
		
		robot.keyRelease(KeyEvent.VK_RIGHT);
		
	}
}
