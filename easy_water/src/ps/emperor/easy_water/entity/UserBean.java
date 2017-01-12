package ps.emperor.easy_water.entity;

import java.util.List;

import ps.emperor.easy_water.entity.UserReleDisInfoBean.infoList;

/**
 * 角色申请
 * 
 * @author 毛国江
 * @version 2017-1-3 16:11
 */
public class UserBean {

	private List<infoList> infoList;

	public List<infoList> getAuthNameList() {
		return infoList;
	}

	public void setAuthNameList(List<infoList> authNameList) {
		this.infoList = authNameList;
	}

	public static class infoList {

		private String userId; // 用户ID
		private String userName; // 用户名
		private String password; // 用户密码
		private String PhoneNum; // 手机号
		private String FullName; // 姓名
		private String AuthID; // 授权ID
		private String AuthName; // 授权单位名称
		private String RoleID; // 用户角色ID
		private String RoleName; // 用户角色名称
		private String PathtoPhoto;

		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getPhoneNum() {
			return PhoneNum;
		}

		public void setPhoneNum(String phoneNum) {
			PhoneNum = phoneNum;
		}

		public String getFullName() {
			return FullName;
		}

		public void setFullName(String fullName) {
			FullName = fullName;
		}

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

		public String getRoleID() {
			return RoleID;
		}

		public void setRoleID(String roleID) {
			RoleID = roleID;
		}

		public String getRoleName() {
			return RoleName;
		}

		public void setRoleName(String roleName) {
			RoleName = roleName;
		}

		public String getPathtoPhoto() {
			return PathtoPhoto;
		}

		public void setPathtoPhoto(String pathtoPhoto) {
			PathtoPhoto = pathtoPhoto;
		}
	}

}
