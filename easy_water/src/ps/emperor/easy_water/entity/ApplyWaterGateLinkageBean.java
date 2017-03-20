package ps.emperor.easy_water.entity;

/**
 * 多孔闸门联动
 * @author 毛国江
 * @version 2016-9-13 上午10:41
 */

public class ApplyWaterGateLinkageBean {
	
	public String percentage; //当前进度条
	public String show; //文字显示
	public String haplopore; //当前孔
	

	public String getHaplopore() {
		return haplopore;
	}

	public void setHaplopore(String haplopore) {
		this.haplopore = haplopore;
	}

	public String getShow() {
		return show;
	}

	public void setShow(String show) {
		this.show = show;
	}

	public String getPercentage() {
		return percentage;
	}

	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}
	
	
}
