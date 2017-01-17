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

    	private String IrriSeasonStart;
    	private String FirstDerviceID;
    	private String ValueNum;
    	private String Area;
    	private String IrriSeasonEnd;
    	private String IrriUnitName;
    	private String MaxGroup;
    	private String Latitude;
    	private String Longitude;
    	private String RestStart;
    	private String RestEnd;
    	private String SuperEqu;
		private String FlushTime;

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
