package ps.emperor.easy_water.entity;

import java.util.List;

import ps.emperor.easy_water.entity.ApplyIrrigationUnitControlBean.groupList;
import ps.emperor.easy_water.entity.UserReleDisInfoBeanAdd.infoList;

/**
 * 灌溉组控制（阀门）
 * 
 * @author 毛国江
 * @version 2016-5-26 上午8:00
 */
public class ApplyIrrigateControlBean {
	private List<infoList> infoList;
	private List<groupList> groupList;
	
	
	public List<groupList> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<groupList> groupList) {
		this.groupList = groupList;
	}

	public List<infoList> getInfoList() {
		return infoList;
	}

	public void setInfoList(List<infoList> infoList) {
		this.infoList = infoList;
	}

	public static class infoList {
		public String GroupName; // 组名称
		public String ChanNum; // 阀门名称
		public String EndTime; // 结束时间
		public String GroupID; // 组ID
		public String StartTime; // 开始时间
		public String ValueControlChanID; // 阀控器ID
		public String ValueControlSwitch; // 阀控器开关
		public String PlanStat; // 状态

		public String getGroupName() {
			return GroupName;
		}

		public void setGroupName(String groupName) {
			GroupName = groupName;
		}

		public String getChanNum() {
			return ChanNum;
		}

		public void setChanNum(String chanNum) {
			ChanNum = chanNum;
		}

		public String getEndTime() {
			return EndTime;
		}

		public void setEndTime(String endTime) {
			EndTime = endTime;
		}

		public String getGroupID() {
			return GroupID;
		}

		public void setGroupID(String groupID) {
			GroupID = groupID;
		}

		public String getStartTime() {
			return StartTime;
		}

		public void setStartTime(String startTime) {
			StartTime = startTime;
		}

		public String getValueControlChanID() {
			return ValueControlChanID;
		}

		public void setValueControlChanID(String valueControlChanID) {
			ValueControlChanID = valueControlChanID;
		}

		public String getValueControlSwitch() {
			return ValueControlSwitch;
		}

		public void setValueControlSwitch(String valueControlSwitch) {
			ValueControlSwitch = valueControlSwitch;
		}

		public String getPlanStat() {
			return PlanStat;
		}

		public void setPlanStat(String planStat) {
			PlanStat = planStat;
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
