package ps.emperor.easy_water.entity;

/**
 * 闸门控制
 * @author 毛国江
 * @version 2016-9-13 上午10:41
 */

public class ApplyWaterDistrbutionGateBean {
	
	public String percentage; //当前百分比
	public String aperture ; //开度
	public String high; //开高
	public String num;//闸门编号
	
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getPercentage() {
		return percentage;
	}
	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}
	public String getAperture() {
		return aperture;
	}
	public void setAperture(String aperture) {
		this.aperture = aperture;
	}
	public String getHigh() {
		return high;
	}
	public void setHigh(String high) {
		this.high = high;
	}
	
}
