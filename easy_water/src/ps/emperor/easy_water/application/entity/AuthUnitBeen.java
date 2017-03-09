package ps.emperor.easy_water.application.entity;

import java.util.List;

/**
 * 获取授权单位信息
 * @author PS
 *
 */
public class AuthUnitBeen {
	

    private List<InfoListBean> infoList;

    public List<InfoListBean> getInfoList() {
        return infoList;
    }

    public void setInfoList(List<InfoListBean> infoList) {
        this.infoList = infoList;
    }

    public static class InfoListBean {
        /**
         * AuthName : 河北省石家庄市长安区第一连管理单位1
         * AuthID : 1
         */

        private String AuthName;
        private int AuthID;

        public String getAuthName() {
            return AuthName;
        }

        public void setAuthName(String AuthName) {
            this.AuthName = AuthName;
        }

        public int getAuthID() {
            return AuthID;
        }

        public void setAuthID(int AuthID) {
            this.AuthID = AuthID;
        }
    }

}
