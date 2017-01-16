package ps.emperor.easy_water.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by PS on 2017/1/10.
 */

public class FindDisWaterInfoOneBean {

	private List<SluiceGateInfoBean> sluiceGateInfo;
	private List<DisWaterInfoBean> disWaterInfo;

	public List<SluiceGateInfoBean> getSluiceGateInfo() {
		return sluiceGateInfo;
	}

	public void setSluiceGateInfo(List<SluiceGateInfoBean> sluiceGateInfo) {
		this.sluiceGateInfo = sluiceGateInfo;
	}

	public List<DisWaterInfoBean> getDisWaterInfo() {
		return disWaterInfo;
	}

	public void setDisWaterInfo(List<DisWaterInfoBean> disWaterInfo) {
		this.disWaterInfo = disWaterInfo;
	}

	public static class SluiceGateInfoBean implements Serializable{
		/**
		 * FrontWatLev : null DisEquID : 配水设备5 PoreID : 1 PoreWidth : 50
		 * DesignFlow : 1 Area : null OpenHigh : 1.1 BehindWatLev : null
		 * OpenProportion : 0.22 PoreHigh : 5
		 */

		private String FrontWatLev;
		private String DisEquID;
		private String PoreID;
		private String PoreWidth;
		private String DesignFlow;
		private String Area;
		private String OpenHigh;
		private String BehindWatLev;
		private String OpenProportion;
		private String PoreHigh;

		public Object getFrontWatLev() {
			return FrontWatLev;
		}

		public String getDisEquID() {
			return DisEquID;
		}

		public String getPoreID() {
			return PoreID;
		}

		public void setPoreID(String poreID) {
			PoreID = poreID;
		}

		public String getPoreWidth() {
			return PoreWidth;
		}

		public void setPoreWidth(String poreWidth) {
			PoreWidth = poreWidth;
		}

		public String getDesignFlow() {
			return DesignFlow;
		}

		public void setDesignFlow(String designFlow) {
			DesignFlow = designFlow;
		}

		public String getPoreHigh() {
			return PoreHigh;
		}

		public void setPoreHigh(String poreHigh) {
			PoreHigh = poreHigh;
		}

		public void setFrontWatLev(String frontWatLev) {
			FrontWatLev = frontWatLev;
		}

		public void setArea(String area) {
			Area = area;
		}

		public void setBehindWatLev(String behindWatLev) {
			BehindWatLev = behindWatLev;
		}

		public void setDisEquID(String DisEquID) {
			this.DisEquID = DisEquID;
		}

		public Object getArea() {
			return Area;
		}

		public String getOpenHigh() {
			return OpenHigh;
		}

		public void setOpenHigh(String openHigh) {
			OpenHigh = openHigh;
		}

		public Object getBehindWatLev() {
			return BehindWatLev;
		}

		public String getOpenProportion() {
			return OpenProportion;
		}

		public void setOpenProportion(String openProportion) {
			OpenProportion = openProportion;
		}

	}

	public static class DisWaterInfoBean implements Serializable{
		/**
		 * DisEquID : 配水设备5 OpenPoreTime : 1484118000000 BackWaterLevel : 100
		 * DesignFlow : 4 Area : 100 ClosePoreTime : 1484982000000
		 * ProtocolVersion : null PoreNum : 1 DisEquType : 配水设备类型1 Latitude :
		 * 100 DisEquName : 配水设备5 Longitude : 100 AuthName : 授权单位1 SuperEqu :
		 * 100 AuthID : 1 FrontWaterLevel : 100
		 */

		private String DisEquID;
		private String OpenPoreTime;
		private String BackWaterLevel;
		private String DesignFlow;
		private String Area;
		private String ClosePoreTime;
		private String ProtocolVersion;
		private String PoreNum;
		private String DisEquType;
		private String Latitude;
		private String DisEquName;
		private String Longitude;
		private String AuthName;
		private String SuperEqu;
		private String AuthID;
		private String FrontWaterLevel;

		public String getDisEquID() {
			return DisEquID;
		}

		public void setDisEquID(String DisEquID) {
			this.DisEquID = DisEquID;
		}

		public String getOpenPoreTime() {
			return OpenPoreTime;
		}

		public void setOpenPoreTime(String openPoreTime) {
			OpenPoreTime = openPoreTime;
		}

		public String getBackWaterLevel() {
			return BackWaterLevel;
		}

		public void setBackWaterLevel(String backWaterLevel) {
			BackWaterLevel = backWaterLevel;
		}

		public String getDesignFlow() {
			return DesignFlow;
		}

		public void setDesignFlow(String designFlow) {
			DesignFlow = designFlow;
		}

		public String getArea() {
			return Area;
		}

		public void setArea(String area) {
			Area = area;
		}

		public String getClosePoreTime() {
			return ClosePoreTime;
		}

		public void setClosePoreTime(String closePoreTime) {
			ClosePoreTime = closePoreTime;
		}

		public String getProtocolVersion() {
			return ProtocolVersion;
		}

		public void setProtocolVersion(String protocolVersion) {
			ProtocolVersion = protocolVersion;
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

		public String getAuthName() {
			return AuthName;
		}

		public void setAuthName(String authName) {
			AuthName = authName;
		}

		public String getSuperEqu() {
			return SuperEqu;
		}

		public void setSuperEqu(String superEqu) {
			SuperEqu = superEqu;
		}

		public String getAuthID() {
			return AuthID;
		}

		public void setAuthID(String authID) {
			AuthID = authID;
		}

		public String getFrontWaterLevel() {
			return FrontWaterLevel;
		}

		public void setFrontWaterLevel(String frontWaterLevel) {
			FrontWaterLevel = frontWaterLevel;
		}

	}
}
