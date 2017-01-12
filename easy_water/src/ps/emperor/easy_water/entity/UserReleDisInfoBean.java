package ps.emperor.easy_water.entity;

import java.util.List;

/**
 * Created by PS on 2017/1/9.
 */

public class UserReleDisInfoBean {


    private List<infoList> infoList;

    public List<infoList> getAuthNameList() {
        return infoList;
    }

    public void setAuthNameList(List<infoList> authNameList) {
        this.infoList = authNameList;
    }

    public static class infoList {

    	private int StatusCode;
    	private String DisEquID;
    	private String DisEquName;
        private String AuthName;
		public boolean isCheck;
		public int getStatusCode() {
			return StatusCode;
		}
		public void setStatusCode(int statusCode) {
			StatusCode = statusCode;
		}
		public String getDisEquID() {
			return DisEquID;
		}
		public void setDisEquID(String disEquID) {
			DisEquID = disEquID;
		}
		public String getDisEquName() {
			return DisEquName;
		}
		public void setDisEquName(String disEquName) {
			DisEquName = disEquName;
		}
		public String getAuthName() {
			return AuthName;
		}
		public void setAuthName(String authName) {
			AuthName = authName;
		}
		public boolean isCheck() {
			return isCheck;
		}
		public void setCheck(boolean isCheck) {
			this.isCheck = isCheck;
		}
        
    }
}
