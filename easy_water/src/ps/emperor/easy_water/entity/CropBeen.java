package ps.emperor.easy_water.entity;

import java.util.List;

/**
 * Created by PS on 2017/1/9.
 */

public class CropBeen {

    private List<infoList> infoList;

    public List<infoList> getAuthNameList() {
        return infoList;
    }

    public void setAuthNameList(List<infoList> authNameList) {
        this.infoList = authNameList;
    }

    public static class infoList {
        /**
         * AuthProvince : 作物
         */

    	private String CropName;
    	private String ClassID;
    	
		public String getCropName() {
			return CropName;
		}
		public void setCropName(String cropName) {
			CropName = cropName;
		}
		public String getClassID() {
			return ClassID;
		}
		public void setClassID(String classID) {
			ClassID = classID;
		}
    	
    	
    }
}
