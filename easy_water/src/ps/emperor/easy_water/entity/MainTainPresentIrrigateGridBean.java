package ps.emperor.easy_water.entity;

import java.util.List;


import ps.emperor.easy_water.entity.MainTainIrrigationInfoBean.groupList;


/**
 * 当前灌溉组
 * @author 毛国江
 * @version 2016-5-26 上午8:00
 */
public class MainTainPresentIrrigateGridBean {


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

    	private String GroupName;
    	private String ChanNum;
    	
		public String getGroupName() {
			return GroupName;
		}
		public void setGroupName(String groupName) {
			GroupName = groupName;
		}
		public String getChanNum() {
			return ChanNum;
		}
		public void setChanNum(String chanNum) {
			ChanNum = chanNum;
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
