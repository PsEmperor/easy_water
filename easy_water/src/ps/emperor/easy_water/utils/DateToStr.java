package ps.emperor.easy_water.utils;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateToStr {
	public static String DateToStr(Date date) {
		/**
		* 日期转换字符串
		* @param str
		* @return date
		*/
		   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		   String str = format.format(date);
		   return str;
		} 

		/**
		* 字符串转换成日期
		* @param str
		* @return date
		*/
		public static Date StrToDate(String str) {
		  
		   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		   Date date = null;
		   try {
		    date = format.parse(str);
		   } catch (ParseException e) {
		    e.printStackTrace();
		   }
		   return date;
		}

}
