package ps.emperor.easy_water.utils;


public class URL {
	//个人信息
	public static final String userInfo = "http://192.168.2.101:8080/cms/app/UserSys/userInfo";
	//修改个人信息
	public static final String updateUserinfo = "http://192.168.2.101:8080/cms/app/UserSys/updateUserinfo/userName";
	//授权单位前缀
	public static final String urluserAuthInfo = "http://192.168.2.101:8080/cms/app/UserSys/userAuthInfo/";
	
	//查询已关联灌溉设备
	public static final String findUserReleIrrInfo = "http://192.168.2.101:8080/cms/app/UserSys/findUserReleIrrInfo/";
	//查询未关联灌溉设备
	public static final String findUserNoReleIrrInfo = "http://192.168.2.101:8080/cms/app/UserSys/findUserNoReleIrrInfo/";
	//模糊搜索未关联灌溉设备
	public static final String UserNoReleIrrInfo = "http://192.168.2.101:8080/cms/app/UserSys/fuzzyQuery/UserNoReleIrrInfo/";
	//选择是否关联灌溉设备开关
	public static final String IrrstatusCode = "http://192.168.2.101:8080/cms/app/UserSys/updateUserReleIrriInfo/userID/firstDerviceID/statusCode";
	//关联新灌溉设备
	public static final String firstDerviceID = "http://192.168.2.101:8080/cms/app/UserSys/userReleIrriInfo/userID/firstDerviceID";
	
	
	//查询已关联配水设备
	public static final String findUserReleDisInfo = "http://192.168.2.101:8080/cms/app/UserSys/findUserReleDisInfo/";
	//查询未关联配水设备
	public static final String findUserNoReleDisInfo = "http://192.168.2.101:8080/cms/app/UserSys/findUserNoReleDisInfo/";
	//模糊搜索未关联配水设备
	public static final String userNoReleDisInfo = "http://192.168.2.101:8080/cms/app/UserSys/fuzzyQuery/userNoReleDisInfo/";
	//选择是否关联配水设备开关
	public static final String DisstatusCode = "http://192.168.2.101:8080/cms/app/UserSys/updateUserReleDisInfo/userID/disEquIDI/statusCode";
	//关联新配水设备
	public static final String disEquID = "http://192.168.2.101:8080/cms/app/UserSys/userReleDisInfo/userID/disEquID";
	
	//模糊搜索已关联配水设备
	public static final String findUserReleDisInfoYet = "http://192.168.2.101:8080/cms/app/DisWaterSys/fuzzyQuery/findUserReleDisInfo/";
	//应用 维护界面配水设备列表查询
	public static final String findUserReleDisInfoApply = "http://192.168.2.101:8080/cms/app/DisWaterSys/findUserReleDisInfo/";
	//应用 维护界面配水设备信息查询
	public static final String findDisEquInfoOne = "http://192.168.2.101:8080/cms/app/DisWaterSys/findDisEquInfoOne/";
}
