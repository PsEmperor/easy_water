package ps.emperor.easy_water.entity;

import java.util.List;

/**
 * Created by PS on 2017/1/9.
 */

public class UserReleIrrInfoToOneBean {


    private List<infoList> infoList;

    public List<infoList> getAuthNameList() {
        return infoList;
    }

    public void setAuthNameList(List<infoList> authNameList) {
        this.infoList = authNameList;
    }

    public static class infoList {

    	private String IrriSeasonStart;//灌季开始
    	private String FirstDerviceID;//设备ID
    	private String ValueNum;//最大阀门
    	private String Area;//面积
    	private String IrriSeasonEnd;//灌季结束
    	private String IrriUnitName;//灌溉单元名称
    	private String MaxGroup;//最大组
    	private String Latitude;//S
    	private String Longitude;//N
    	private String RestStart;//夜间休息开始
    	private String RestEnd;//夜间休息结束
    	private String SuperEqu;//上级设备
		private String FlushTime;//反过滤冲洗时间
		private String PumpRestTime;//水泵休息时间

		
		
		public String getPumpRestTime() {
			return PumpRestTime;
		}

		public void setPumpRestTime(String pumpRestTime) {
			this.PumpRestTime = pumpRestTime;
		}

		public String getIrriSeasonStart() {
			return IrriSeasonStart;
		}

		public void setIrriSeasonStart(String irriSeasonStart) {
			IrriSeasonStart = irriSeasonStart;
		}

		public String getFirstDerviceID() {
			return FirstDerviceID;
		}

		public void setFirstDerviceID(String firstDerviceID) {
			FirstDerviceID = firstDerviceID;
		}

		public String getValueNum() {
			return ValueNum;
		}

		public void setValueNum(String valueNum) {
			ValueNum = valueNum;
		}

		public String getArea() {
			return Area;
		}

		public void setArea(String area) {
			Area = area;
		}

		public String getIrriSeasonEnd() {
			return IrriSeasonEnd;
		}

		public void setIrriSeasonEnd(String irriSeasonEnd) {
			IrriSeasonEnd = irriSeasonEnd;
		}

		public String getIrriUnitName() {
			return IrriUnitName;
		}

		public void setIrriUnitName(String irriUnitName) {
			IrriUnitName = irriUnitName;
		}

		public String getMaxGroup() {
			return MaxGroup;
		}

		public void setMaxGroup(String maxGroup) {
			MaxGroup = maxGroup;
		}

		public String getLatitude() {
			return Latitude;
		}

		public void setLatitude(String latitude) {
			Latitude = latitude;
		}

		public String getLongitude() {
			return Longitude;
		}

		public void setLongitude(String longitude) {
			Longitude = longitude;
		}

		public String getRestStart() {
			return RestStart;
		}

		public void setRestStart(String restStart) {
			RestStart = restStart;
		}

		public String getRestEnd() {
			return RestEnd;
		}

		public void setRestEnd(String restEnd) {
			RestEnd = restEnd;
		}

		public String getSuperEqu() {
			return SuperEqu;
		}

		public void setSuperEqu(String superEqu) {
			SuperEqu = superEqu;
		}

		public String getFlushTime() {
			return FlushTime;
		}

		public void setFlushTime(String flushTime) {
			FlushTime = flushTime;
		}
    }
}
