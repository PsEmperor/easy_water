package ps.emperor.easy_water.entity;

import android.widget.ToggleButton;

/**
 * 设备关联
 * @author 毛国江
 */
public class IrrigationEquipmentBean {
	
	public String irrigation;
	public ToggleButton button;
	public Boolean isCheck;
	
	public Boolean getIsCheck() {
		return isCheck;
	}
	public void setIsCheck(Boolean isCheck) {
		this.isCheck = isCheck;
	}
	public String getIrrigation() {
		return irrigation;
	}
	public void setIrrigation(String irrigation) {
		this.irrigation = irrigation;
	}
	public ToggleButton getButton() {
		return button;
	}
	public void setButton(ToggleButton button) {
		this.button = button;
	}
	
}
