package ps.emperor.easy_water.application.entity;

public class Water_Pressure {
	
	private String loca;
	private String range;
	private int num;
	public Water_Pressure() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Water_Pressure(String loca, String range, int num) {
		super();
		this.loca = loca;
		this.range = range;
		this.num = num;
	}
	public String getLoca() {
		return loca;
	}
	public void setLoca(String loca) {
		this.loca = loca;
	}
	public String getRange() {
		return range;
	}
	public void setRange(String range) {
		this.range = range;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	@Override
	public String toString() {
		return "Water_Pressure [loca=" + loca + ", range=" + range + ", num="
				+ num + "]";
	}
	
	

}
