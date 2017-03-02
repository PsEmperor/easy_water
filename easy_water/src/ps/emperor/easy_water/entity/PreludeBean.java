package ps.emperor.easy_water.entity;

import java.util.List;

/**
 * Created by PS on 2017/1/9.
 */

public class PreludeBean {

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

    	private String pumpSwitch; //水泵开关状态
    	private String filterSwitch;//过滤器反冲洗开关状态
    	private String workStress;//工作压力
        private String pressThreshold;//过滤器压差
        private String instantFlow;//瞬时流量
        private String accumulateElectric;//累计电量
        private String accumulateWater;//累计水量
        private String pumpEquID;	 //水泵ID
        private String filterEquID;	 //过滤器ID
        private String firstDerviceID;	 //首部ID
        private String fertilizerSwitch;	 //施肥机ID
		
        public String getPumpSwitch() {
			return pumpSwitch;
		}
		public void setPumpSwitch(String pumpSwitch) {
			this.pumpSwitch = pumpSwitch;
		}
		public String getFilterSwitch() {
			return filterSwitch;
		}
		public void setFilterSwitch(String filterSwitch) {
			this.filterSwitch = filterSwitch;
		}
		public String getWorkStress() {
			return workStress;
		}
		public void setWorkStress(String workStress) {
			this.workStress = workStress;
		}
		public String getPressThreshold() {
			return pressThreshold;
		}
		public void setPressThreshold(String pressThreshold) {
			this.pressThreshold = pressThreshold;
		}
		public String getInstantFlow() {
			return instantFlow;
		}
		public void setInstantFlow(String instantFlow) {
			this.instantFlow = instantFlow;
		}
		public String getAccumulateElectric() {
			return accumulateElectric;
		}
		public void setAccumulateElectric(String accumulateElectric) {
			this.accumulateElectric = accumulateElectric;
		}
		public String getAccumulateWater() {
			return accumulateWater;
		}
		public void setAccumulateWater(String accumulateWater) {
			this.accumulateWater = accumulateWater;
		}
		public String getPumpEquID() {
			return pumpEquID;
		}
		public void setPumpEquID(String pumpEquID) {
			this.pumpEquID = pumpEquID;
		}
		public String getFilterEquID() {
			return filterEquID;
		}
		public void setFilterEquID(String filterEquID) {
			this.filterEquID = filterEquID;
		}
		public String getFirstDerviceID() {
			return firstDerviceID;
		}
		public void setFirstDerviceID(String firstDerviceID) {
			this.firstDerviceID = firstDerviceID;
		}
		public String getFertilizerSwitch() {
			return fertilizerSwitch;
		}
		public void setFertilizerSwitch(String fertilizerSwitch) {
			this.fertilizerSwitch = fertilizerSwitch;
		}
		
        
        
    }
}
