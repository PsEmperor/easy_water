package ps.emperor.easy_water.entity;

import java.util.List;

/**
 * Created by PS on 2017/1/9.
 */

public class UserReleDisInfoBeanAdd {

	private List<infoList> infoList;

	public List<infoList> getAuthNameList() {
		return infoList;
	}

	public void setAuthNameList(List<infoList> authNameList) {
		this.infoList = authNameList;
	}

	public static class infoList {
		private String DisEquID;
		private String DisEquName;
		private String AuthName;
		public boolean isCheck;


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

		public boolean isCheck() {
			return isCheck;
		}

		public void setCheck(boolean isCheck) {
			this.isCheck = isCheck;
		}

		public String getAuthName() {
			return AuthName;
		}

		public void setAuthName(String authName) {
			AuthName = authName;
		}
	}
}
