package ps.emperor.easy_water.entity;

import java.io.Serializable;
import java.util.List;

import ps.emperor.easy_water.entity.FindDisWaterInfoOneBean.DisWaterInfoBean;

public class ApplyIrrigationUnitControlBean {

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

		private String FirstDerviceID;
		private String Area;
		private String IrriUnitName;
		private String CropName;
		private String PlantTime;
		private String IrriState;
		private String GrowersName;

		public String getFirstDerviceID() {
			return FirstDerviceID;
		}

		public void setFirstDerviceID(String firstDerviceID) {
			FirstDerviceID = firstDerviceID;
		}

		public String getArea() {
			return Area;
		}

		public void setArea(String area) {
			Area = area;
		}

		public String getIrriUnitName() {
			return IrriUnitName;
		}

		public void setIrriUnitName(String irriUnitName) {
			IrriUnitName = irriUnitName;
		}

		public String getCropName() {
			return CropName;
		}

		public void setCropName(String cropName) {
			CropName = cropName;
		}

		public String getPlantTime() {
			return PlantTime;
		}

		public void setPlantTime(String plantTime) {
			PlantTime = plantTime;
		}

		public String getIrriState() {
			return IrriState;
		}

		public void setIrriState(String irriState) {
			IrriState = irriState;
		}

		public String getGrowersName() {
			return GrowersName;
		}

		public void setGrowersName(String growersName) {
			GrowersName = growersName;
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
