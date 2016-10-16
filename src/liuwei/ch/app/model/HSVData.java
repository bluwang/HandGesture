package liuwei.ch.app.model;

public class HSVData {
	
	private static byte H_MIN = 0; 
	private static byte H_MAX = 0; 
	private static byte S_MIN = 0; 
	private static byte S_MAX = 0; 
	private static byte V_MIN = 0; 
	private static byte V_MAX = 0; 
	
	public byte[] getHSV() {
		byte[] data = {H_MIN, H_MAX, S_MIN, S_MAX, V_MIN, V_MAX};
		return data;
	}
	
	public void setHSV(byte[] data) {
		H_MIN = data[0];
		H_MAX = data[1];
		S_MIN = data[2];
		S_MAX = data[3];
		V_MIN = data[4];
		V_MAX = data[5];
	}
}
