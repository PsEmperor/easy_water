package ps.emperor.easy_water.entity;


public class ApplyIrrigationProjectBean {

	/**
	 * 灌溉计划
	 */

	public String group; // 灌溉组
	public String time_start; // 开始时间
	public String time_end; // 结束时间

	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getTime_start() {
		return time_start;
	}
	public void setTime_start(String time_start) {
		this.time_start = time_start;
	}
	public String getTime_end() {
		return time_end;
	}
	public void setTime_end(String time_end) {
		this.time_end = time_end;
	}



}
