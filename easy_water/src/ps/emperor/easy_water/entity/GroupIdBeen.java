package ps.emperor.easy_water.entity;

import java.util.List;

/**
 * Created by PS on 2017/1/9.
 */

public class GroupIdBeen {

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

    	private String GroupName;
    	private String GroupID;
    	
		public String getGroupName() {
			return GroupName;
		}
		public void setGroupName(String groupName) {
			GroupName = groupName;
		}
		public String getGroupID() {
			return GroupID;
		}
		public void setGroupID(String groupID) {
			GroupID = groupID;
		}

    	
    }
}
