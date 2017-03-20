package ps.emperor.easy_water.application.entity;

import java.util.List;

/**
 * Created by PS on 2017/1/10.
 */

public class ValueControlBeen {



    /**
     * firstDerviceID : SB001001
     * valueControlType : 短信阀控器
     * valueControlName : 阀控器名称1
     * longitude : 100
     * latitude : 200
     * authID : 1
     * supperEqu : 灌溉单元1
     * thrieChan : 1分干
     * totalChanNum : 4
     * area : 100
     * chanList : [{"chan":"1","chanNum":"1-1","area":"20"},{"chan":"2","chanNum":"1-2","area":"20"},{"chan":"3","chanNum":"1-3","area":"20"},{"chan":"4","chanNum":"1-4","area":"40"}]
     */

    private String firstDerviceID;
    private String valueControlType;
    private String valueControlName;
    private String longitude;
    private String latitude;
    private String authID;
    private String supperEqu;
    private String thrieChan;
    private String totalChanNum;
    private String area;
    private List<ChanListBean> chanList;

    public String getFirstDerviceID() {
        return firstDerviceID;
    }

    public void setFirstDerviceID(String firstDerviceID) {
        this.firstDerviceID = firstDerviceID;
    }

    public String getValueControlType() {
        return valueControlType;
    }

    public void setValueControlType(String valueControlType) {
        this.valueControlType = valueControlType;
    }

    public String getValueControlName() {
        return valueControlName;
    }

    public void setValueControlName(String valueControlName) {
        this.valueControlName = valueControlName;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getAuthID() {
        return authID;
    }

    public void setAuthID(String authID) {
        this.authID = authID;
    }

    public String getSupperEqu() {
        return supperEqu;
    }

    public void setSupperEqu(String supperEqu) {
        this.supperEqu = supperEqu;
    }

    public String getThrieChan() {
        return thrieChan;
    }

    public void setThrieChan(String thrieChan) {
        this.thrieChan = thrieChan;
    }

    public String getTotalChanNum() {
        return totalChanNum;
    }

    public void setTotalChanNum(String totalChanNum) {
        this.totalChanNum = totalChanNum;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public List<ChanListBean> getChanList() {
        return chanList;
    }

    public void setChanList(List<ChanListBean> chanList) {
        this.chanList = chanList;
    }

    public static class ChanListBean {
        /**
         * chan : 1
         * chanNum : 1-1
         * area : 20
         */

        private String chan;
        private String chanNum;
        private String area;

        public String getChan() {
            return chan;
        }

        public void setChan(String chan) {
            this.chan = chan;
        }

        public String getChanNum() {
            return chanNum;
        }

        public void setChanNum(String chanNum) {
            this.chanNum = chanNum;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

		@Override
		public String toString() {
			return "chan=" + chan + ", chanNum=" + chanNum
					+ ", area=" + area ;
		}
        
        
    }
}
