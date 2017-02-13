package ps.emperor.easy_water.entity;

import java.util.List;

/**
 * Created by PS on 2017/1/9.
 */

public class ApplyIrrigateControlBeans {

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

    	private String code;

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}
    	
    	
    }
}
