package ps.emperor.easy_water.application.entity;

import java.util.List;


/**
 * 获取水泵类型等格式
 * @author PS
 *
 */
public class FirstDeviceTypeBeen {
	

    private List<InfoListBean> infoList;

    public List<InfoListBean> getInfoList() {
        return infoList;
    }

    public void setInfoList(List<InfoListBean> infoList) {
        this.infoList = infoList;
    }

    public static class InfoListBean {
        /**
         * EquTypeName : 过滤器类型1
         * EquTypeNum : 2      //设备上传必须号   例如：阀控器、闸门、等
         * EquTypeID : 4		//设备类型号  例如：水泵类型1、水泵类型2等
         */

        private String EquTypeName;
        private int EquTypeNum;
        private int EquTypeID;

        public String getEquTypeName() {
            return EquTypeName;
        }

        public void setEquTypeName(String EquTypeName) {
            this.EquTypeName = EquTypeName;
        }

        public int getEquTypeNum() {
            return EquTypeNum;
        }

        public void setEquTypeNum(int EquTypeNum) {
            this.EquTypeNum = EquTypeNum;
        }

        public int getEquTypeID() {
            return EquTypeID;
        }

        public void setEquTypeID(int EquTypeID) {
            this.EquTypeID = EquTypeID;
        }
    }

}
