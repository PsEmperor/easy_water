package ps.emperor.easy_water.entity;

/**
 * 设备添加
 * @author 毛国江
 *
 */
public class KeyWordBean {
	
	public boolean isCheck; //是否选中
	public String keyword; //关键字


	public String getkeyword() {
		return keyword;
	}

	public void setkeyword(String keyword) {
		this.keyword = keyword;
	}

	public boolean isCheck() {
		return isCheck;
	}

	public void setCheck(boolean isCheck) {
		this.isCheck = isCheck;
	}
	
	
}
