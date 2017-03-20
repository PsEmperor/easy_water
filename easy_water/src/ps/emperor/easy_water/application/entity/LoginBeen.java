package ps.emperor.easy_water.application.entity;

import java.util.List;

public class LoginBeen {
	  private List<InfoListBean> infoList;
	    private List<PermissionListBean> permissionList;

	    public List<InfoListBean> getInfoList() {
	        return infoList;
	    }

	    public void setInfoList(List<InfoListBean> infoList) {
	        this.infoList = infoList;
	    }

	    public List<PermissionListBean> getPermissionList() {
	        return permissionList;
	    }

	    public void setPermissionList(List<PermissionListBean> permissionList) {
	        this.permissionList = permissionList;
	    }

	    public static class InfoListBean {
	        /**
	         * RoleID : -1
	         * UserID : -1
	         * UserName : -1
	         * Code : 0
	         */

	        private int RoleID;
	        private int UserID;
	        private String UserName;
	        private int Code;

	        public int getRoleID() {
	            return RoleID;
	        }

	        public void setRoleID(int RoleID) {
	            this.RoleID = RoleID;
	        }

	        public int getUserID() {
	            return UserID;
	        }

	        public void setUserID(int UserID) {
	            this.UserID = UserID;
	        }

	        public String getUserName() {
	            return UserName;
	        }

	        public void setUserName(String UserName) {
	            this.UserName = UserName;
	        }

	        public int getCode() {
	            return Code;
	        }

	        public void setCode(int Code) {
	            this.Code = Code;
	        }
	    }

	    public static class PermissionListBean {
	        /**
	         * PermissionID : -1
	         * OpertionStat : -1
	         */

	        private int PermissionID;
	        private String OpertionStat;

	        public int getPermissionID() {
	            return PermissionID;
	        }

	        public void setPermissionID(int PermissionID) {
	            this.PermissionID = PermissionID;
	        }

	        public String getOpertionStat() {
	            return OpertionStat;
	        }

	        public void setOpertionStat(String OpertionStat) {
	            this.OpertionStat = OpertionStat;
	        }
	    }

}
