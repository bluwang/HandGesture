package liuwei.ch.app.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * ����ɫ����е�HSV��ɫֵ���д���
 * @author Administrator
 *
 */
@XmlRootElement(name = "HSVDataWrapper")
public class HSVDataWrapper {
	
	@XmlElement(name = "HSVMap")
	private Map<String, HSVData> HSVMap;
	@XmlElement(name = "sceneName")
	private Vector<String> sceneNames;
	@XmlElement(name = "lastScene")
	private String lastScene;
	
	public HSVDataWrapper() {
		HSVMap = new HashMap<String, HSVData>();
		sceneNames = new Vector<String>();
	}
	
	/**
	 * ��ȡ��Ӧ������HSV��ɫ����
	 * @param sceneName ��Ӧ�ĳ�����
	 * @return HSV����
	 */
	public HSVData getHSVData(String sceneName) {
		HSVData sceneData = null;

		for (int i = 0; i < this.HSVMap.size() ; i++) {
			if (this.sceneNames.get(i).equals(sceneName)) {
				sceneData = HSVMap.get(sceneName);
			}
		}

		return sceneData;
	}
	
	/**
	 * ��ȡ���еĳ�����
	 * @return �������б�
	 */
	public List<String> getScenes() {
		return sceneNames;
	}
	
	/**
	 * ��ӳ���
	 * @param sceneName ������
	 * @param data ����HSV����
	 */
	public void add(String sceneName, HSVData data) {
		boolean isAdd = false;

		//��鳡�����Ƿ����
		for (int i = 0; i < this.sceneNames.size() ; i++) {
			if (this.sceneNames.get(i).equals(sceneName)) {
				this.HSVMap.remove(sceneName);
				this.HSVMap.put(sceneName, data);
				this.sceneNames.add(sceneName);
				isAdd = true;
				break;
			}
		}
		
		//�����ڣ��ȴ�û��ӹ����������
		if (!isAdd) {
			this.HSVMap.put(sceneName, data);
			this.sceneNames.add(sceneName);
		}
		//�������滻��Ӧ����������
		else {
		}
	}
	
	/**
	 * ɾ����Ӧ����
	 * @param sceneName ������
	 */
	public void remove(String sceneName) {
		for (int i = 0; i < this.sceneNames.size() ; i++) {
			if (this.sceneNames.equals(sceneName)) {
				this.HSVMap.remove(sceneName);
			}
		}
	}
	
}
