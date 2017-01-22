package ps.emperor.easy_water.entity;

import java.util.List;


/**
 * 当前灌溉组
 * @author 毛国江
 * @version 2016-5-26 上午8:00
 */
public class MainTainPresentIrrigateGridBean {


    private List<infoList> infoList;

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
}
