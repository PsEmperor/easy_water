package ps.emperor.easy_water.utils;
import java.io.IOException;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;


/**
 * @功能:公用工具类
 * @author hwl
 * @version 1.0
 * @param <T>
 * @date 2017-01-05
 *
 */
public class CommonUtil {

/**
 * 使用MD5密码加密
 * @param 	String password
 * @return	String
 */
public static String MD5(String password){
try {
byte[] btInput = password.getBytes();
MessageDigest mdInst = MessageDigest.getInstance("MD5");
mdInst.update(btInput);
byte[] md = mdInst.digest();
StringBuffer sb = new StringBuffer();
for (int i = 0; i < md.length; i++) {
int val = ((int) md[i]) & 0xff;
if (val < 16)
sb.append("0");
sb.append(Integer.toHexString(val));
}
return sb.toString();
} catch (Exception e) {
return null;
}
}

/**
 * 获取当前时间
 * @return	String
 */
public static String getCurrentTime(){
Date date = new Date();
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");
return sdf.format(date);
}

/**
 * 字符串时间格式转换date时间格式  String-date
 * @param 	strDate	
 * @return	"yyyy-MM-dd" or "yyyy-MM-dd HH:mm:ss"
 */
public static Date str_to_date(String strDate){
SimpleDateFormat sdf;
Date date = null;
if(strDate.trim().length() > 0 && strDate.trim().length() < 11){
sdf = new SimpleDateFormat("yyyy-MM-dd");
}else if(strDate.trim().length() > 10 && strDate.trim().length() < 18){
sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
}else{
sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
}
try {
date = sdf.parse(strDate);
} catch (ParseException e) {
e.printStackTrace();
}
return date;
}

/**
 * date时间格式转换为String字符串时间格式  date-String
 * @param 	Date	
 * @return	"yyyy-MM-dd HH:mm:ss"
 */
public static String date_to_str1(Date date){
String strDate ="";
if(date != null){
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
strDate = sdf.format(date);
}
return strDate;
}

/**
 * 毫秒时间格式转换String字符串时间格式 long - String
 * @param 	long	 
 * @return	"yyyy-MM-dd HH:mm:ss"
 */
public static String date_to_str(Long date){
SimpleDateFormat sdf;
String strDate = "";
if(date != null){
sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
strDate = sdf.format(date);
}
return strDate;
}


/**
 * 毫秒时间格式转换Date   毫秒 - Date
 * @param 	毫秒	
 * @return	Date
 */
@SuppressWarnings("unused")
public static Date millis_to_date(Long millis){
SimpleDateFormat sdf;
Date date = null;
try {
if(millis != null){
sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
date = sdf.parse(date_to_str(millis));
}
} catch (ParseException e) {
e.printStackTrace();
}
return date;
}

/**
 * 时分秒时间格式转换为毫秒值	
 * @param date	 date时间(时分秒/时分)
 * @return	 Long
 */
public static Long time_to_millis(Date date){
Long time = 0L;
String s = date.toString() +"";
return time;
}

/**
 * String时间格式转换为毫秒      String  - date(毫秒)
 * @param Date	"yyyy-MM-dd"  "yyyy-MM-dd HH:mm:ss"
 * @return	String
 */
public static Long str_to_long(String strDate){
Long millis = 0L;
try {
if(strDate != null){
SimpleDateFormat sdf;
if(strDate.length() <= 9){
String[] split = strDate.split(":");
millis = Long.parseLong(split[0]) * 60L * 60L * 1000L;
millis += Long.parseLong(split[1]) * 60L * 1000L;
if(split.length > 2){
millis += Long.parseLong(split[2]) * 1000L;
}
}else {
if(strDate.length() <= 16){
sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
}else{
sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
}
Date date = sdf.parse(strDate);
millis = sdf.parse(strDate).getTime();
}
}
} catch (ParseException e) {
e.printStackTrace();
}
return millis;
}

//时间格式转换 String-time	
/**
 * 字符串时间格式转换为date时间格式, 传入格式有三种0小时0分钟/0小时/0分钟 转换为 00:00
 * @param 	时间格式字符串
 * @return	date
 */
public static Date str_to_time1(String strTime){
Date date = null;
if(strTime.length() > 0){
SimpleDateFormat sdfDateFormat = new SimpleDateFormat("HH小时mm分钟") ;
if(strTime.endsWith("小时")){	//判断是否是以小时为结尾
sdfDateFormat = new SimpleDateFormat("HH小时");
}else{
if(strTime.length() <= 4){ //如果长度小于等于4,说明是只有分钟没有小时
sdfDateFormat = new SimpleDateFormat("mm分钟");
}
} 
try {
date = sdfDateFormat.parse(strTime);
} catch (ParseException e) {
e.printStackTrace();
}
}
return date;
}

/**
 * 字符串时间格式转换为date时间格式
 * @param 	时间格式字符串,传入格式00:00
 * @return	date
 */
public static Date str_to_time2(String strTime){
Date date = null;
if(strTime.length() > 0){
SimpleDateFormat sdfDateFormat = new SimpleDateFormat("HH:mm") ;
try {
date = sdfDateFormat.parse(strTime);
} catch (ParseException e) {
e.printStackTrace();
}
}
return date;
}


/**
 * 字符串时间格式转换为date时间格式
 * @param 	时间格式字符串,传入格式00:00 or 00:00:00
 * @return	date
 */
public static Date str_to_time3(String strTime){
Date date = null;
if(strTime.length() > 0){
SimpleDateFormat sdfDateFormat ;
if(strTime.length() <= 5){
sdfDateFormat = new SimpleDateFormat("HH:mm");
}else{
sdfDateFormat = new SimpleDateFormat("HH:mm:ss") ;
}
try {
date = sdfDateFormat.parse(strTime);
} catch (ParseException e) {
e.printStackTrace();
}
}
return date;
}



/**
 * 使用UUID生成随机ID
 * @return	String UUID
 */
public static String createUUID(){
return UUID.randomUUID().toString().replaceAll("-", "");
}

//字符串转数组
/**
 * 字符串转换为数组
 * @param param	 字符串
 * @return	 String[] Array
 */
public static String[] str_to_strArr(String param){
String[] strArr= null;
String para = param.trim();
if(para.length()>0){
strArr = param.substring(1,para.length()-1).split(",");
}
return strArr;
}

/**
 * 传入当前时间,持续时间,以当前时间为开始时间,计算返回结束时间
 * @param currentTime	当前时间
 * @param duration	 持续时间
 * @return	 String
 */
public static String getEndTime(String currentTime , String duration){
SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");  
String endTime = "";
try {
Date date = df.parse(currentTime);
String[] str = duration.trim().split(":");
endTime = df.format(date.getTime() + (Integer.parseInt(str[0]) * 60 + Integer.parseInt(str[1])) * 60 * 1000);
} catch (NumberFormatException e) {
e.printStackTrace();
} catch (ParseException e) {
e.printStackTrace();
}
return endTime;
}

/**
 * 传入app设定开始时间,app设定结束时间,灌溉计划开始时间,结束时间,判断计划是否冲突
 * @param appSetStartTime	app设定开始时间
 * @param appSetEndTime	 app设定结束时间
 * @param irriGroupPlanList 灌溉计划集合(灌溉计划开始时间,结束时间,判断计划是否冲突)
 * @return	 int 1表示冲突 0 表示不冲突
 */
public static int isTimeConflict(String appSetStartTime,String appSetEndTime,List irriGroupPlanList){
int result = 0;
for (int i = 0; i < irriGroupPlanList.size(); i++) {
Map map = (Map)irriGroupPlanList.get(i);
if(map.get("StartTime") !=null){
String startTime = map.get("StartTime").toString().trim();
String endTime = map.get("EndTime").toString().trim();
if(CommonUtil.str_to_date(appSetStartTime).getTime() >= CommonUtil.str_to_date(startTime).getTime() && CommonUtil.str_to_date(appSetStartTime).getTime() < CommonUtil.str_to_date(endTime).getTime() || CommonUtil.str_to_date(appSetEndTime).getTime() >= CommonUtil.str_to_date(startTime).getTime() && CommonUtil.str_to_date(appSetEndTime).getTime() < CommonUtil.str_to_date(endTime).getTime()){
result = 1;
break;
}
}
}
return result ;
}

/**
 * 判断传入手机号是否符合手机号码正则
 * @param phoneNum	手机号
 * @return	 boolean
 */
public static boolean checkPhoneNum(String phoneNum){
boolean b = false;
phoneNum = phoneNum.trim();
if(phoneNum != null && phoneNum.length() == 11){
//手机号码正则
String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}$";
b = phoneNum.matches(regex);
}
return b;
}

}