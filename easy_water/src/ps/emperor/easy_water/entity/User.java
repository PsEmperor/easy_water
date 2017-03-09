package ps.emperor.easy_water.entity;

/**
 * 用户
 * @author 毛国江
 * @version 2016-5-26 上午8:00
 */
public class User {

	private String userId;
	private String userName;
	private String userPhone;
	private String fullName;
	private String authID;
	private String pathtoPhoto;
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
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getAuthID() {
		return authID;
	}
	public void setAuthID(String authID) {
		this.authID = authID;
	}
	public String getPathtoPhoto() {
		return pathtoPhoto;
	}
	public void setPathtoPhoto(String pathtoPhoto) {
		this.pathtoPhoto = pathtoPhoto;
	}
	
	
}
