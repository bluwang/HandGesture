package liuwei.ch.app.model;

public class HSVData {
	
	private static byte H_MIN1 = 0; 
	private static byte H_MAX1 = 0; 
	private static byte S_MIN1 = 0; 
	private static byte S_MAX1 = 0; 
	private static byte V_MIN1 = 0; 
	private static byte V_MAX1 = 0; 
	
	public byte[] getHSV() {
		byte[] data = {H_MIN1, H_MAX1, S_MIN1, S_MAX1, V_MIN1, V_MAX1};
		return data;
	}
	
	public void setHSV(byte[] data) {
		H_MIN1 = data[0];
		H_MAX1 = data[1];
		S_MIN1 = data[2];
		S_MAX1 = data[3];
		V_MIN1 = data[4];
		V_MAX1 = data[5];
	}
}
