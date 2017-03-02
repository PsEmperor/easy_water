package ps.emperor.easy_water.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by PS on 2017/1/9.
 */

public class UserReleIrrInfoBean {


    private List<infoList> infoList;

    public List<infoList> getAuthNameList() {
        return infoList;
    }

    public void setAuthNameList(List<infoList> authNameList) {
        this.infoList = authNameList;
    }

    public static class infoList implements Serializable{

    	private int StatusCode;
    	private String FirstDerviceID;
    	private String IrriUnitName;
    	private String IrriState;
    	
    	
    	public String getIrriState() {
			return IrriState;
		}
		public void setIrriState(String irriState) {
			IrriState = irriState;
		}
		public String getAuthName() {
			return AuthName;
		}
		public void setAuthName(String authName) {
			AuthName = authName;
		}
		private String AuthName;
		public boolean isCheck;
        
		public int getStatusCode() {
			return StatusCode;
		}
		public void setStatusCode(int statusCode) {
			StatusCode = statusCode;
		}
		public boolean isCheck() {
			return isCheck;
		}
		public void setCheck(boolean isCheck) {
			this.isCheck = isCheck;
		}
		public String getFirstDerviceID() {
			return FirstDerviceID;
		}
		public void setFirstDerviceID(String firstDerviceID) {
			FirstDerviceID = firstDerviceID;
		}
		public String getIrriUnitName() {
			return IrriUnitName;
		}
		public void setIrriUnitName(String irriUnitName) {
			IrriUnitName = irriUnitName;
		}
    }
}
