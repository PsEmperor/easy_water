package ps.emperor.easy_water.utils;


public class URL {
	//个人信息
	public static final String userInfo = "http://60.205.220.245:8080/cms/app/UserSys/userInfo";
	//修改个人信息
	public static final String updateUserinfo = "http://60.205.220.245:8080/cms/app/UserSys/updateUserinfo/userName";
	//授权单位前缀
	public static final String urluserAuthInfo = "http://60.205.220.245:8080/cms/app/UserSys/userAuthInfo/";
	//我-个人信息-手机号码修改-最终修改
	public static final String updatePhoneNum = "http://60.205.220.245:8080/cms/app/UserSys/updatePhoneNum";
	//我-个人信息-手机号码修改-获取手机验证码
	public static final String getAuthCode = "http://60.205.220.245:8080/cms/app/UserSys/getAuthCode/";
	
	//查询已关联灌溉设备
	public static final String findUserReleIrrInfo = "http://60.205.220.245:8080/cms/app/UserSys/findUserReleIrrInfo/";
	//查询未关联灌溉设备
	public static final String findUserNoReleIrrInfo = "http://60.205.220.245:8080/cms/app/UserSys/findUserNoReleIrrInfo/";
	//模糊搜索未关联灌溉设备
	public static final String UserNoReleIrrInfo = "http://60.205.220.245:8080/cms/app/UserSys/fuzzyQuery/UserNoReleIrrInfo/";
	//选择是否关联灌溉设备开关
	public static final String IrrstatusCode = "http://60.205.220.245:8080/cms/app/UserSys/updateUserReleIrriInfo/userID/firstDerviceID/statusCode";
	//关联新灌溉设备
	public static final String firstDerviceID = "http://60.205.220.245:8080/cms/app/UserSys/userReleIrriInfo/userID/firstDerviceID";
	
	
	//查询已关联配水设备
	public static final String findUserReleDisInfo = "http://60.205.220.245:8080/cms/app/UserSys/findUserReleDisInfo/";
	//查询未关联配水设备
	public static final String findUserNoReleDisInfo = "http://60.205.220.245:8080/cms/app/UserSys/findUserNoReleDisInfo/";
	//模糊搜索未关联配水设备
	public static final String userNoReleDisInfo = "http://60.205.220.245:8080/cms/app/UserSys/fuzzyQuery/userNoReleDisInfo/";
	//选择是否关联配水设备开关
	public static final String DisstatusCode = "http://60.205.220.245:8080/cms/app/UserSys/updateUserReleDisInfo/userID/disEquIDI/statusCode";
	//关联新配水设备
	public static final String disEquID = "http://60.205.220.245:8080/cms/app/UserSys/userReleDisInfo/userID/disEquID";
	
	//维护显示用户已关联灌溉设备信息
	public static final String findUserReleIrrInfoIrri = "http://60.205.220.245:8080/cms/app/IrriUnitSys/findUserReleIrriInfo/";
	//维护已关联灌溉设备信息模糊查询
	public static final String userReleIrriInfo = "http://60.205.220.245:8080/cms/app/IrriUnitSys/fuzzyQuery/userReleIrriInfo/";
	//维护显示灌溉详细信息
	public static final String findIrriUnitInfoToOne = "http://60.205.220.245:8080/cms/app/IrriUnitSys/findIrriUnitInfoToOne/";
	//维护灌溉单元基本信息维护
	public static final String firstDerviceIDIrri = "http://60.205.220.245:8080/cms/app/IrriUnitSys/updateIrriUnitInfo/firstDerviceID";
	
	//种植户、种植作物信息维护
	public static final String findIrriUnitChan = "http://60.205.220.245:8080/cms/app/IrriUnitSys/findIrriUnitChan/";
	//录入种植户
	public static final String addGrowersInfo = "http://60.205.220.245:8080/cms/app/IrriUnitSys/addGrowersInfo/releChan";
	//应用-维护-获取作物信息
	public static final String acquireCropInfoToAll = "http://60.205.220.245:8080/cms/app/IrriUnitSys/acquireCropInfoToAll";
	//录入种植作物
	public static final String addChanCropInfo = "http://60.205.220.245:8080/cms/app/IrriUnitSys/addChanCropInfo/releChan";
	//轮灌组维护
	public static final String addChanGroupInfo = "http://60.205.220.245:8080/cms/app/IrriUnitSys/addChanGroupInfo/releChan";
	//轮灌组计划查询
	public static final String queryIrriPlan = "http://60.205.220.245:8080/cms/app/IrriUnitSys/resetIrriGroupInfo/queryIrriPlan/";
	//轮灌组重置
	public static final String resetIrriGroupInfo = "http://60.205.220.245:8080/cms/app/IrriUnitSys/resetIrriGroupInfo/releChan";
	//已编组阀门查询
	public static final String findIrriGroupInfo = "http://60.205.220.245:8080/cms/app/IrriUnitSys/findIrriGroupInfo/";
	//全局浏览
	public static final String findIrriGrowersAndCropInfo = "http://60.205.220.245:8080/cms/app/IrriUnitSys/findIrriGrowersAndCropInfo/";
	
	//维护模糊搜索已关联配水设备
	public static final String findUserReleDisInfoYet = "http://60.205.220.245:8080/cms/app/DisWaterSys/fuzzyQuery/findUserReleDisInfo/";
	//维护界面配水设备列表查询
	public static final String findUserReleDisInfoApply = "http://60.205.220.245:8080/cms/app/DisWaterSys/findUserReleDisInfo/";
	//维护界面配水设备信息查询
	public static final String findDisEquInfoOne = "http://60.205.220.245:8080/cms/app/DisWaterSys/findDisEquInfoOne/";
	//维护界面配水设备信息更改
	public static final String updateDisEquInfo = "http://60.205.220.245:8080/cms/app/DisWaterSys/updateDisEquInfo";
	
	
	//应用配水设备信息
	public static final String findDisWaterInfoOne = "http://60.205.220.245:8080/cms/app/DisWaterSys/findDisWaterInfoOne/";
	//多孔闸门闸宽联动设置
	public static final String openProportion = "http://60.205.220.245:8080/cms/app/DisWaterSys/updatePoreWidthToAll/disEquID/openProportion";
	//单孔闸门闸宽设置
	public static final String openProportionOne = "http://60.205.220.245:8080/cms/app/DisWaterSys/updatePoreHighToOne/disEquID/poreID/openProportion";
	//多孔闸门闸宽预约设置
	public static final String openProportionAll = "http://60.205.220.245:8080/cms/app/DisWaterSys/bookingAdjust/updatePoreHighToAll/disEquID/openProportion";

	//应用-灌溉-灌溉计划-灌溉单元管理
	public static final String findIrriUnitManage = "http://60.205.220.245:8080/cms/app/IrriUnitSys/findIrriUnitManage/";
	//应用-灌溉-灌溉计划-立即控制-灌溉阀门控制-显示详细信息
	public static final String findValueControlInfo = "http://60.205.220.245:8080/cms/app/IrriUnitSys/findValueControlInfo/";
	//应用-灌溉-灌溉计划-立即控制-灌溉阀门控制开关
	public static final String updateValueControlSwitch = "http://60.205.220.245:8080/cms/app/IrriUnitSys/updateValueControlSwitch";
	//应用-灌溉-灌溉计划-立即控制-灌水延续时间修改
	public static final String updateValueControlIrriDuration = "http://60.205.220.245:8080/cms/app/IrriUnitSys/updateValueControlIrriDuration";
	//应用-灌溉-灌溉计划-立即控制-灌溉组控制-显示当前灌溉单元下灌溉组灌溉状态,启始时间,通道号名称
	public static final String findIrriGroupControl = "http://60.205.220.245:8080/cms/app/IrriUnitSys/findIrriGroupControl/";
	//应用-灌溉-灌溉计划-立即控制-灌溉组控制-操作灌溉组按钮(查询状态)
	public static final String judgeIrriGroupState = "http://60.205.220.245:8080/cms/app/IrriUnitSys/judgeIrriGroupState/";
	//应用-灌溉-灌溉计划-立即控制-灌溉组控制-操作灌溉组按钮
	public static final String controlIrriGroupValue = "http://60.205.220.245:8080/cms/app/IrriUnitSys/IrriGroupState/controlIrriGroupValue";
	//应用-灌溉-灌溉计划-立即控制-灌溉阀门控制开关-获取是否存在组计划状态
	public static final String acquireIsExistsGroupPlan = "http://60.205.220.245:8080/cms/app/IrriUnitSys/acquireIsExistsGroupPlan/";
	//应用-灌溉-灌溉单元管理-更改计划-灌溉计划-查询显示
	public static final String forIrriUnit = "http://60.205.220.245:8080/cms/app/IrriUnitSys/queryIrriRoundPlan/forIrriUnit/";
	//应用-灌溉-灌溉单元管理-灌溉计划-单轮快速设定(添加)
	public static final String oneRound = "http://60.205.220.245:8080/cms/app/IrriUnitSys/addRoundIrriPlan/oneRound";
	//	应用-灌溉-灌溉单元管理-灌溉计划-全季快速设定
	public static final String fullSeasonRound = "http://60.205.220.245:8080/cms/app/IrriUnitSys/addRoundIrriPlan/fullSeasonRound";
	//	应用-灌溉-灌溉单元管理-更改计划-灌溉计划-单轮计划删除
	public static final String forIrriUnitDele = "http://60.205.220.245:8080/cms/app/IrriUnitSys/deleteIrriRoundPlan/forIrriUnit";
	
	
	//应用-灌溉-灌溉单元管理-首部-显示
	public static final String acquireFirstDerviceInfo = "http://60.205.220.245:8080/cms/app/IrriUnitSys/acquireFirstDerviceInfo/";
	//应用-灌溉-灌溉单元管理-首部-水泵开启关闭-查询该灌溉单元是否正在灌溉
	public static final String acquireIrriState = "http://60.205.220.245:8080/cms/app/IrriUnitSys/acquireIrriState/";
	//应用-灌溉-灌溉单元管理-首部-水泵开启关闭
	public static final String updatePumpSwitchState = "http://60.205.220.245:8080/cms/app/IrriUnitSys/updatePumpSwitchState";
	//应用-灌溉-灌溉单元管理-首部-过滤器反冲洗开启/关闭
	public static final String updateFilterSwitchState = "http://60.205.220.245:8080/cms/app/IrriUnitSys/updateFilterSwitchState";
	//应用-灌溉-灌溉单元管理-灌溉计划-自定义设定-第一次保存
	public static final String customRound = "http://60.205.220.245:8080/cms/app/IrriUnitSys/addRoundIrriPlan/customRound";
	//应用-灌溉-灌溉单元管理-灌溉计划-单组修改
	public static final String updateIrriGroupPlan = "http://60.205.220.245:8080/cms/app/IrriUnitSys/updateIrriGroupPlan";
	
}
