package ps.emperor.easy_water.entity;


public class ApplyIrrigationUnitControlBean {

	/**
	 * 灌溉单元控制（应用）
	 */

	public String plant; //种植物
	public String time; //时间
	public String name; //种植户

	public String getPlant() {
		return plant;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPlant(String plant) {
		this.plant = plant;
	}
	
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
}
