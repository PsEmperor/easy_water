package ps.emperor.easy_water.entity;


public class GateBean {

	/**
	 * 轮灌小区信息维护（应用）
	 * @author 毛国江
	 * @version 2016-6-7上午11:54
	 */


	public Boolean istrue; // 是否选中

	public String gate;
	
	public int state;

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getGate() {
		return gate;
	}

	public void setGate(String gate) {
		this.gate = gate;
	}

	public Boolean getIstrue() {
		return istrue;
	}

	public void setIstrue(Boolean istrue) {
		this.istrue = istrue;
	}



}
