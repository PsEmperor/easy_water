package ps.emperor.easy_water.utils;


public class URL {
	//个人信息
	public static final String userInfo = "http://192.168.2.188:8080/cms/app/UserSys/userInfo";
	//修改个人信息
	public static final String updateUserinfo = "http://192.168.2.188:8080/cms/app/UserSys/updateUserinfo/userName";
	//授权单位前缀
	public static final String urluserAuthInfo = "http://192.168.2.188:8080/cms/app/UserSys/userAuthInfo/";
	
	//查询已关联灌溉设备
	public static final String findUserReleIrrInfo = "http://192.168.2.188:8080/cms/app/UserSys/findUserReleIrrInfo/";
	//查询未关联灌溉设备
	public static final String findUserNoReleIrrInfo = "http://192.168.2.188:8080/cms/app/UserSys/findUserNoReleIrrInfo/";
	//模糊搜索未关联灌溉设备
	public static final String UserNoReleIrrInfo = "http://192.168.2.188:8080/cms/app/UserSys/fuzzyQuery/UserNoReleIrrInfo/";
	//选择是否关联灌溉设备开关
	public static final String IrrstatusCode = "http://192.168.2.188:8080/cms/app/UserSys/updateUserReleIrriInfo/userID/firstDerviceID/statusCode";
	//关联新灌溉设备
	public static final String firstDerviceID = "http://192.168.2.188:8080/cms/app/UserSys/userReleIrriInfo/userID/firstDerviceID";
	
	
	//查询已关联配水设备
	public static final String findUserReleDisInfo = "http://192.168.2.188:8080/cms/app/UserSys/findUserReleDisInfo/";
	//查询未关联配水设备
	public static final String findUserNoReleDisInfo = "http://192.168.2.188:8080/cms/app/UserSys/findUserNoReleDisInfo/";
	//模糊搜索未关联配水设备
	public static final String userNoReleDisInfo = "http://192.168.2.188:8080/cms/app/UserSys/fuzzyQuery/userNoReleDisInfo/";
	//选择是否关联配水设备开关
	public static final String DisstatusCode = "http://192.168.2.188:8080/cms/app/UserSys/updateUserReleDisInfo/userID/disEquIDI/statusCode";
	//关联新配水设备
	public static final String disEquID = "http://192.168.2.188:8080/cms/app/UserSys/userReleDisInfo/userID/disEquID";
	
	//维护显示用户已关联灌溉设备信息
	public static final String findUserReleIrrInfoIrri = "http://192.168.2.188:8080/cms/app/IrriUnitSys/findUserReleIrriInfo/";
	//维护已关联灌溉设备信息模糊查询
	public static final String userReleIrriInfo = "http://192.168.2.188:8080/cms/app/IrriUnitSys/fuzzyQuery/userReleIrriInfo/";
	//维护显示灌溉详细信息
	public static final String findIrriUnitInfoToOne = "http://192.168.2.188:8080/cms/app/IrriUnitSys/findIrriUnitInfoToOne/";
	//维护灌溉单元基本信息维护
	public static final String firstDerviceIDIrri = "http://192.168.2.188:8080/cms/app/IrriUnitSys/updateIrriUnitInfo/firstDerviceID";
	
	//种植户、种植作物信息维护
	public static final String findIrriUnitChan = "http://192.168.2.188:8080/cms/app/IrriUnitSys/findIrriUnitChan/";
	//录入种植户
	public static final String addGrowersInfo = "http://192.168.2.188:8080/cms/app/IrriUnitSys/addGrowersInfo/releChan";
	//录入种植作物
	public static final String addChanCropInfo = "http://192.168.2.188:8080/cms/app/IrriUnitSys/addChanCropInfo/releChan";
	//轮灌组维护
	public static final String addChanGroupInfo = "http://192.168.2.188:8080/cms/app/IrriUnitSys/addChanGroupInfo/releChan";
	
	//维护模糊搜索已关联配水设备
	public static final String findUserReleDisInfoYet = "http://192.168.2.188:8080/cms/app/DisWaterSys/fuzzyQuery/findUserReleDisInfo/";
	//维护界面配水设备列表查询
	public static final String findUserReleDisInfoApply = "http://192.168.2.188:8080/cms/app/DisWaterSys/findUserReleDisInfo/";
	//维护界面配水设备信息查询
	public static final String findDisEquInfoOne = "http://192.168.2.188:8080/cms/app/DisWaterSys/findDisEquInfoOne/";
	//维护界面配水设备信息更改
	public static final String updateDisEquInfo = "http://192.168.2.188:8080/cms/app/DisWaterSys/updateDisEquInfo";
	
	
	//应用配水设备信息
	public static final String findDisWaterInfoOne = "http://192.168.2.188:8080/cms/app/DisWaterSys/findDisWaterInfoOne/";
	//多孔闸门闸宽联动设置
	public static final String openProportion = "http://192.168.2.188:8080/cms/app/DisWaterSys/updatePoreWidthToAll/disEquID/openProportion";
	//单孔闸门闸宽设置
	public static final String openProportionOne = "http://192.168.2.188:8080/cms/app/DisWaterSys/updatePoreHighToOne/disEquID/poreID/openProportion";
	//多孔闸门闸宽预约设置
	public static final String openProportionAll = "http://192.168.2.188:8080/cms/app/DisWaterSys/bookingAdjust/updatePoreHighToAll/disEquID/openProportion";
}
