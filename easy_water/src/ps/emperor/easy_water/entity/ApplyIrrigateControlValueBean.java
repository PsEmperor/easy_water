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

    	private String TotalIrriTime;//累计灌水时间
    	private String IrriDuration;//灌水持续时间
    	private String IrriCount;//累计灌溉次数
        private String IrriWater;//累计水量
        private String Area;//面积
        private String CropName;//作物名称
        private String ValueControlChanID;//阀控器ID
        private String GrowersName;//用户名
        private String ValueControlID;//阀门ID
        private String ValueControlSwitch;//开关状态
        
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
