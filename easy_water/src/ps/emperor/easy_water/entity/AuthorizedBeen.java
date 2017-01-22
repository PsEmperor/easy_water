package ps.emperor.easy_water.entity;

import java.util.List;

/**
 * Created by PS on 2017/1/9.
 */

public class AuthorizedBeen {


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

    	private String AuthID;
    	private String AuthName;
    	private String AuthManage;
        private String AuthProvince;
        private String AuthCity;
        private String AuthTown;
        private String AuthCounty;
        
        
		public String getAuthID() {
			return AuthID;
		}
		public void setAuthID(String authID) {
			AuthID = authID;
		}
		public String getAuthName() {
			return AuthName;
		}
		public void setAuthName(String authName) {
			AuthName = authName;
		}
		public String getAuthManage() {
			return AuthManage;
		}
		public void setAuthManage(String authManage) {
			AuthManage = authManage;
		}
		public String getAuthProvince() {
			return AuthProvince;
		}
		public void setAuthProvince(String authProvince) {
			AuthProvince = authProvince;
		}
		public String getAuthCity() {
			return AuthCity;
		}
		public void setAuthCity(String authCity) {
			AuthCity = authCity;
		}
		public String getAuthTown() {
			return AuthTown;
		}
		public void setAuthTown(String authTown) {
			AuthTown = authTown;
		}
		public String getAuthCounty() {
			return AuthCounty;
		}
		public void setAuthCounty(String authCounty) {
			AuthCounty = authCounty;
		}
    }
}
