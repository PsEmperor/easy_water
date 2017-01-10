package ps.emperor.easy_water.entity;

import java.util.List;

/**
 * （我）授权单位
 * @author 毛国江
 * @version 2016-6-23 下午 13:30
 */
public class MineUserProvinceBean {
	
	public List<Bean> beans;

	
	public List<Bean> getBeans() {
		return beans;
	}


	public void setBeans(List<Bean> beans) {
		this.beans = beans;
	}


	public static class Bean{
		public String authID; //授权ID
		public String authName; //授权名称
		public String authProvince; //省
		public String authManage; //省
		public String authCounty; //省
		public String authCity; //省
		public String authTown; //省
		public String getAuthID() {
			return authID;
		}
		public void setAuthID(String authID) {
			this.authID = authID;
		}
		public String getAuthName() {
			return authName;
		}
		public void setAuthName(String authName) {
			this.authName = authName;
		}
		public String getAuthProvince() {
			return authProvince;
		}
		public void setAuthProvince(String authProvince) {
			this.authProvince = authProvince;
		}
		public String getAuthManage() {
			return authManage;
		}
		public void setAuthManage(String authManage) {
			this.authManage = authManage;
		}
		public String getAuthCounty() {
			return authCounty;
		}
		public void setAuthCounty(String authCounty) {
			this.authCounty = authCounty;
		}
		public String getAuthCity() {
			return authCity;
		}
		public void setAuthCity(String authCity) {
			this.authCity = authCity;
		}
		public String getAuthTown() {
			return authTown;
		}
		public void setAuthTown(String authTown) {
			this.authTown = authTown;
		}
		
		
	}
}
 