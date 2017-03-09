package ps.emperor.easy_water.entity;


public class ApplyWaterDistrbutionBean {

	/**
	 * 配水（应用）
	 */

	public String units; // 授权单位
	public String gate; // 配水闸门
	public String whether; // 当前状态
	
	public String getUnits() {
		return units;
	}
	public void setUnits(String units) {
		this.units = units;
	}
	public String getGate() {
		return gate;
	}
	public void setGate(String gate) {
		this.gate = gate;
	}
	public String getWhether() {
		return whether;
	}
	public void setWhether(String whether) {
		this.whether = whether;
	}




}
