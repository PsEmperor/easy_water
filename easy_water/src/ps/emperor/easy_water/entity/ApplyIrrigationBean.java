package ps.emperor.easy_water.entity;

import java.util.List;

import ps.emperor.easy_water.entity.ApplyIrrigationUnitControlBean.groupList;
import ps.emperor.easy_water.entity.UserReleDisInfoBeanAdd.infoList;

public class ApplyIrrigationBean {

	/**
	 * 灌溉（应用）
	 */

	public String units; // 授权单位
	public String element; // 灌溉单元
	public String whether; // 当前状态
	public int current_state; // 饼状图角度
	public String whether_percent; // 饼状图角度


	public String getWhether_percent() {
		return whether_percent;
	}

	public void setWhether_percent(String whether_percent) {
		this.whether_percent = whether_percent;
	}

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
