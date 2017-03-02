package ps.emperor.easy_water.entity;

import java.io.Serializable;
import java.util.List;

import ps.emperor.easy_water.entity.UserReleIrrInfoToOneBean.infoList;


public class MainTainIrrigationInfoBean {

	/**
	 * 轮灌小区信息维护（应用）
	 * @author 毛国江
	 * @version 2016-6-7上午11:54
	 */
	private List<infoList> infoList;
	private List<groupList> groupList;

    public List<infoList> getAuthNameList() {
        return infoList;
    }

    public void setAuthNameList(List<infoList> authNameList) {
        this.infoList = authNameList;
    }

    public List<groupList> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<groupList> groupList) {
		this.groupList = groupList;
	}

	public static class infoList {
    	private String FirstDerviceID;
    	private String Area;
    	private String ChanNum;
    	private String ValueControlChanID;
    	private String IsAllocationGrowers;
    	private String IsAllocationCrop;
    	private String IsAllocationGroup;
    	private String GroupName;
    	private String TotalChanNum;
    	private String GrowersName;
    	private String CropName;
    	
		public Boolean istrue; // 是否选中
    	
		
		public String getGroupName() {
			return GroupName;
		}
		public void setGroupName(String groupName) {
			GroupName = groupName;
		}
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
		public String getChanNum() {
			return ChanNum;
		}
		public void setChanNum(String chanNum) {
			ChanNum = chanNum;
		}
		public String getValueControlChanID() {
			return ValueControlChanID;
		}
		public void setValueControlChanID(String valueControlChanID) {
			ValueControlChanID = valueControlChanID;
		}
		public Boolean getIstrue() {
			return istrue;
		}
		public void setIstrue(Boolean istrue) {
			this.istrue = istrue;
		}
		public String getIsAllocationGrowers() {
			return IsAllocationGrowers;
		}
		public void setIsAllocationGrowers(String isAllocationGrowers) {
			IsAllocationGrowers = isAllocationGrowers;
		}
		public String getIsAllocationCrop() {
			return IsAllocationCrop;
		}
		public void setIsAllocationCrop(String isAllocationCrop) {
			IsAllocationCrop = isAllocationCrop;
		}
		public String getIsAllocationGroup() {
			return IsAllocationGroup;
		}
		public void setIsAllocationGroup(String isAllocationGroup) {
			IsAllocationGroup = isAllocationGroup;
		}
		public String getTotalChanNum() {
			return TotalChanNum;
		}
		public void setTotalChanNum(String totalChanNum) {
			TotalChanNum = totalChanNum;
		}
		public String getGrowersName() {
			return GrowersName;
		}
		public void setGrowersName(String growersName) {
			GrowersName = growersName;
		}
		public String getCropName() {
			return CropName;
		}
		public void setCropName(String cropName) {
			CropName = cropName;
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
