package ps.emperor.easy_water.application;

public class Configure_Lv_item {
	
	private String tv;
	private int arrow;
	public Configure_Lv_item() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Configure_Lv_item(String tv, int arrow) {
		super();
		this.tv = tv;
		this.arrow = arrow;
	}
	public String getTv() {
		return tv;
	}
	public void setTv(String tv) {
		this.tv = tv;
	}
	public int getArrow() {
		return arrow;
	}
	public void setArrow(int arrow) {
		this.arrow = arrow;
	}
	@Override
	public String toString() {
		return "Configure_Lv_item [tv=" + tv + ", arrow=" + arrow + "]";
	}
	
	

}
