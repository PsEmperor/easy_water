package ps.emperor.easy_water.entity;

import java.util.List;

import ps.emperor.easy_water.entity.AuthorizedBeen.infoList;

/**
 * 单阀显示
 * @author 毛国江
 * @version 2016-5-26 上午11:19
 */
public class ApplyIrrigateSingleValveBean {


	 private List<infoList> infoList;

	    public List<infoList> getAuthNameList() {
	        return infoList;
	    }

	    public void setAuthNameList(List<infoList> authNameList) {
	        this.infoList = authNameList;
	    }

	    public static class infoList {
	        /**
	         * AuthProvince : 授
	         */

	    	private String ChanNum;
	    	private String CropName;
	    	private String GrowersName;
	    	
	    	public Boolean istrue; // 是否选中
	    	
			public Boolean getIstrue() {
				return istrue;
			}
			public void setIstrue(Boolean istrue) {
				this.istrue = istrue;
			}
			public String getChanNum() {
				return ChanNum;
			}
			public void setChanNum(String chanNum) {
				ChanNum = chanNum;
			}
			public String getCropName() {
				return CropName;
			}
			public void setCropName(String cropName) {
				CropName = cropName;
			}
			public String getGrowersName() {
				return GrowersName;
			}
			public void setGrowersName(String growersName) {
				GrowersName = growersName;
			}
	    	
	    	
	    }

}
