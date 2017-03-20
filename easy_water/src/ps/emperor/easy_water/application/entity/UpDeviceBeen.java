package ps.emperor.easy_water.application.entity;

import java.util.List;

public class UpDeviceBeen {
    private List<InfoListBean> infoList;

    public List<InfoListBean> getInfoList() {
        return infoList;
    }

    public void setInfoList(List<InfoListBean> infoList) {
        this.infoList = infoList;
    }

    public static class InfoListBean {
        /**
         * EquName : 灌溉单元1
         * SuperEqu : SB001001
         */

        private String EquName;
        private String SuperEqu;

        public String getEquName() {
            return EquName;
        }

        public void setEquName(String EquName) {
            this.EquName = EquName;
        }

        public String getSuperEqu() {
            return SuperEqu;
        }

        public void setSuperEqu(String SuperEqu) {
            this.SuperEqu = SuperEqu;
        }
    }

}
