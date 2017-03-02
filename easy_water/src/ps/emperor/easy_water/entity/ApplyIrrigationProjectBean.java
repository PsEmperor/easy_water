package ps.emperor.easy_water.entity;

import java.util.List;



public class ApplyIrrigationProjectBean {
	 private List<infoList> infoList;

	    public List<infoList> getAuthNameList() {
	        return infoList;
	    }

	    public void setAuthNameList(List<infoList> authNameList) {
	        this.infoList = authNameList;
	    }

	    public static class infoList {
	        /**
	         * 灌溉计划
	         */

	    	private String RestTime;
	    	private String GroupName;
	    	private String FirstDerviceID;
	        private String Duration;
	        private String IrriUnitName;
	        private String PlanRound;
	        private String EndTime;
	        private String StartTime;
	        private String GroupID;
	        private String PlanStat;
	        private String GroupNum;
	        
	        
			public String getGroupNum() {
				return GroupNum;
			}
			public void setGroupNum(String groupNum) {
				GroupNum = groupNum;
			}
			public String getRestTime() {
				return RestTime;
			}
			public void setRestTime(String restTime) {
				RestTime = restTime;
			}
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
			public String getDuration() {
				return Duration;
			}
			public void setDuration(String duration) {
				Duration = duration;
			}
			public String getIrriUnitName() {
				return IrriUnitName;
			}
			public void setIrriUnitName(String irriUnitName) {
				IrriUnitName = irriUnitName;
			}
			public String getPlanRound() {
				return PlanRound;
			}
			public void setPlanRound(String planRound) {
				PlanRound = planRound;
			}
			public String getEndTime() {
				return EndTime;
			}
			public void setEndTime(String endTime) {
				EndTime = endTime;
			}
			public String getStartTime() {
				return StartTime;
			}
			public void setStartTime(String startTime) {
				StartTime = startTime;
			}
			public String getGroupID() {
				return GroupID;
			}
			public void setGroupID(String groupID) {
				GroupID = groupID;
			}
			public String getPlanStat() {
				return PlanStat;
			}
			public void setPlanStat(String planStat) {
				PlanStat = planStat;
			}
	    }
}
