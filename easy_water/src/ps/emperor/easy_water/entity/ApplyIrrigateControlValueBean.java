package ps.emperor.easy_water.entity;

import java.util.List;

/**
 * Created by PS on 2017/1/9.
 */

public class ApplyIrrigateControlValueBean {

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

    	private String TotalIrriTime;
    	private String IrriDuration;
    	private String IrriCount;
        private String IrriWater;
        private String Area;
        private String CropName;
        private String ValueControlChanID;
        private String GrowersName;
        private String ValueControlID;
        private String ValueControlSwitch;
        
		public String getTotalIrriTime() {
			return TotalIrriTime;
		}
		public void setTotalIrriTime(String totalIrriTime) {
			TotalIrriTime = totalIrriTime;
		}
		public String getIrriDuration() {
			return IrriDuration;
		}
		public void setIrriDuration(String irriDuration) {
			IrriDuration = irriDuration;
		}
		public String getIrriCount() {
			return IrriCount;
		}
		public void setIrriCount(String irriCount) {
			IrriCount = irriCount;
		}
		public String getIrriWater() {
			return IrriWater;
		}
		public void setIrriWater(String irriWater) {
			IrriWater = irriWater;
		}
		public String getArea() {
			return Area;
		}
		public void setArea(String area) {
			Area = area;
		}
		public String getCropName() {
			return CropName;
		}
		public void setCropName(String cropName) {
			CropName = cropName;
		}
		public String getValueControlChanID() {
			return ValueControlChanID;
		}
		public void setValueControlChanID(String valueControlChanID) {
			ValueControlChanID = valueControlChanID;
		}
		public String getGrowersName() {
			return GrowersName;
		}
		public void setGrowersName(String growersName) {
			GrowersName = growersName;
		}
		public String getValueControlID() {
			return ValueControlID;
		}
		public void setValueControlID(String valueControlID) {
			ValueControlID = valueControlID;
		}
		public String getValueControlSwitch() {
			return ValueControlSwitch;
		}
		public void setValueControlSwitch(String valueControlSwitch) {
			ValueControlSwitch = valueControlSwitch;
		}
        
        
    }
}
