package ps.emperor.easy_water.entity;

import java.util.List;

import ps.emperor.easy_water.entity.ApplyIrrigationUnitControlBean.groupList;
import ps.emperor.easy_water.entity.AuthorizedBeen.infoList;

/**
 * 单阀显示
 * @author 毛国江
 * @version 2016-5-26 上午11:19
 */
public class ApplyIrrigateSingleValveBean {


	 private List<infoList> infoList;
	 private List<groupList> groupList;
	 
	 
	    public List<infoList> getInfoList() {
		return infoList;
	}

	public void setInfoList(List<infoList> infoList) {
		this.infoList = infoList;
	}

	public List<groupList> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<groupList> groupList) {
		this.groupList = groupList;
	}

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
	    	private String ValueControlChanID;
	    	private String ValueControlSwitch;
	    	private String GroupName;
	    	private String TotalChanNum;
	    	
	    	public String getGroupName() {
				return GroupName;
			}
			public void setGroupName(String groupName) {
				GroupName = groupName;
			}
			public String getValueControlSwitch() {
				return ValueControlSwitch;
			}
			public void setValueControlSwitch(String valueControlSwitch) {
				ValueControlSwitch = valueControlSwitch;
			}
			public String getValueControlChanID() {
				return ValueControlChanID;
			}
			public void setValueControlChanID(String valueControlChanID) {
				ValueControlChanID = valueControlChanID;
			}
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
			public String getTotalChanNum() {
				return TotalChanNum;
			}
			public void setTotalChanNum(String totalChanNum) {
				TotalChanNum = totalChanNum;
			}
	    	
	    	
	    }
	    public static class groupList{
			private String GroupNum;

			public String getGroupNum() {
				return GroupNum;
			}

			public void setGroupNum(String groupNum) {
				GroupNum = groupNum;
			}

		}
}
