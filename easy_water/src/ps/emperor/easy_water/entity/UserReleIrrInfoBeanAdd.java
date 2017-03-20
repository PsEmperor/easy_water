package ps.emperor.easy_water.entity;

import java.util.List;

/**
 * Created by PS on 2017/1/9.
 */

public class UserReleIrrInfoBeanAdd {

	private List<infoList> infoList;

	public List<infoList> getAuthNameList() {
		return infoList;
	}

	public void setAuthNameList(List<infoList> authNameList) {
		this.infoList = authNameList;
	}

	public static class infoList {
		private String FirstDerviceID;
		private String IrriUnitName;
		private String AuthName;
		public boolean isCheck;

		public boolean isCheck() {
			return isCheck;
		}

		public void setCheck(boolean isCheck) {
			this.isCheck = isCheck;
		}

		public String getFirstDerviceID() {
			return FirstDerviceID;
		}

		public void setFirstDerviceID(String firstDerviceID) {
			FirstDerviceID = firstDerviceID;
		}

		public String getIrriUnitName() {
			return IrriUnitName;
		}

		public void setIrriUnitName(String irriUnitName) {
			IrriUnitName = irriUnitName;
		}

		public String getAuthName() {
			return AuthName;
		}

		public void setAuthName(String authName) {
			AuthName = authName;
		}
	}
}
