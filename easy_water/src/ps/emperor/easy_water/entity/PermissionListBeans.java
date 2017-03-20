package ps.emperor.easy_water.entity;

import java.util.List;

import ps.emperor.easy_water.entity.ApplyIrrigationUnitControlBean.groupList;
import ps.emperor.easy_water.entity.UserReleDisInfoBeanAdd.infoList;

/**
 * 灌溉组控制（阀门）
 * 
 * @author 毛国江
 * @version 2016-5-26 上午8:00
 */
public class PermissionListBeans {
	  private List<PermissionListBean> permissionList;

	    public List<PermissionListBean> getPermissionList() {
	        return permissionList;
	    }

	    public void setPermissionList(List<PermissionListBean> permissionList) {
	        this.permissionList = permissionList;
	    }

	    public static class PermissionListBean {
	        /**
	         * OpertionStat : 1,2,3,4
	         * PermissionID : 3
	         */

	        private String OpertionStat;
	        private int PermissionID;

	        public String getOpertionStat() {
	            return OpertionStat;
	        }

	        public void setOpertionStat(String OpertionStat) {
	            this.OpertionStat = OpertionStat;
	        }

	        public int getPermissionID() {
	            return PermissionID;
	        }

	        public void setPermissionID(int PermissionID) {
	            this.PermissionID = PermissionID;
	        }
	    }
}
