package ps.emperor.easy_water.entity;

import java.util.List;

/**
 * Created by PS on 2017/1/9.
 */

public class FindDisEquInfoOneBean {


    private List<infoList> infoList;

    public List<infoList> getAuthNameList() {
        return infoList;
    }

    public void setAuthNameList(List<infoList> authNameList) {
        this.infoList = authNameList;
    }

    public static class infoList {

    	private String DisEquID;
    	private String PoreNum;
        private String DisEquType;
        private String Area;
        private String Latitude;
        private String DisEquName;
        private String Longitude;
        private String SuperEqu;
        private String AuthName;
		public String getDisEquID() {
			return DisEquID;
		}
		public void setDisEquID(String disEquID) {
			DisEquID = disEquID;
		}
		public String getPoreNum() {
			return PoreNum;
		}
		public void setPoreNum(String poreNum) {
			PoreNum = poreNum;
		}
		public String getDisEquType() {
			return DisEquType;
		}
		public void setDisEquType(String disEquType) {
			DisEquType = disEquType;
		}
		public String getArea() {
			return Area;
		}
		public void setArea(String area) {
			Area = area;
		}
		public String getLatitude() {
			return Latitude;
		}
		public void setLatitude(String latitude) {
			Latitude = latitude;
		}
		public String getDisEquName() {
			return DisEquName;
		}
		public void setDisEquName(String disEquName) {
			DisEquName = disEquName;
		}
		public String getLongitude() {
			return Longitude;
		}
		public void setLongitude(String longitude) {
			Longitude = longitude;
		}
		public String getSuperEqu() {
			return SuperEqu;
		}
		public void setSuperEqu(String superEqu) {
			SuperEqu = superEqu;
		}
		public String getAuthName() {
			return AuthName;
		}
		public void setAuthName(String authName) {
			AuthName = authName;
		}
        
    }
}
