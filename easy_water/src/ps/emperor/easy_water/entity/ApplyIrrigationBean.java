package ps.emperor.easy_water.entity;


public class ApplyIrrigationBean {

	/**
	 * 灌溉（应用）
	 */

	public String units; // 授权单位
	public String element; // 灌溉单元
	public String whether; // 当前状态
	public int current_state; // 饼状图角度

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	public String getElement() {
		return element;
	}

	public void setElement(String element) {
		this.element = element;
	}

	public String getWhether() {
		return whether;
	}

	public void setWhether(String whether) {
		this.whether = whether;
	}

	public int getCurrent_state() {
		return current_state;
	}

	public void setCurrent_state(int current_state) {
		this.current_state = current_state;
	}

}
