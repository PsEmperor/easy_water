package ps.emperor.easy_water.entity;

import java.io.Serializable;

public class Image implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;//���
	private String url;//ͼƬ
	private String type;//�������ͼ��Ĳ�ͬ
	private boolean isselect;//���ڱ���Ƿ���ѡ��״̬--ɾ��
	
	public boolean isSelect() {
		return isselect;
	}
	public void setSelect(boolean isselect) {
		this.isselect = isselect;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
