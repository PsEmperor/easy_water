package ps.emperor.easy_water.entity;

import android.widget.ToggleButton;

/**
 * 设备关联
 * @author 毛国江
 */
public class IrrigationEquipmentBean {
	
	public String equipment;
	public ToggleButton button;
	public Boolean isCheck;
	
	public Boolean getIsCheck() {
		return isCheck;
	}
	public void setIsCheck(Boolean isCheck) {
		this.isCheck = isCheck;
	}
	public String getEquipment() {
		return equipment;
	}
	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}
	public ToggleButton getButton() {
		return button;
	}
	public void setButton(ToggleButton button) {
		this.button = button;
	}
	
}
