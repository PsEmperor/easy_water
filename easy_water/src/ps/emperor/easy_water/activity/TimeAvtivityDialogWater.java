package ps.emperor.easy_water.activity;


import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import ps.emperor.easy_water.R;
import ps.emperor.easy_water.Interface.OnWheelChangedListener;
import ps.emperor.easy_water.BaseActivity;
import ps.emperor.easy_water.adapter.NumbericWheelAdapter;
import ps.emperor.easy_water.utils.SharedUtils;
import ps.emperor.easy_water.view.WheelView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

/**
 * 窗口化时间转轮
 * @author 毛国江
 * @version 2016-5-31 上午8:12
 */

public class TimeAvtivityDialogWater extends BaseActivity implements OnClickListener {


	private Button time_sure,time_canle;
	
	private WheelView year;
	private  WheelView month;
	private  WheelView day;
	private  WheelView hour;
	private  WheelView minute;
	private  WheelView hours;
	private  WheelView minutes;
	private int isBefore;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.time);

		Calendar c = Calendar.getInstance();
		int currentYear = c.get(Calendar.YEAR);
		int currentMonth = c.get(Calendar.MONTH);
		int currentDay = c.get(Calendar.DATE);
		int currentHour = c.get(Calendar.HOUR_OF_DAY);
		int currentHours = c.get(Calendar.MINUTE);
		int currentMinute = c.get(Calendar.HOUR_OF_DAY);
		int currentMinutes = c.get(Calendar.MINUTE);
		
		String[] months_big = { "1", "3", "5", "7", "8", "10", "12" };
		String[] months_little = { "4", "6", "9", "11" };
		
		final List<String> list_big = Arrays.asList(months_big);
		final List<String> list_little = Arrays.asList(months_little);
		
		time_sure = (Button) findViewById(R.id.time_sure);
		time_sure.setOnClickListener(this);
		time_canle = (Button) findViewById(R.id.time_canle);
		time_canle.setOnClickListener(this);

		year = (WheelView) findViewById(R.id.year);
		year.setAdapter(new NumbericWheelAdapter(2016, 2036));
		year.setCyclic(true);
		year.setLabel("年");// 添加文字
		year.setCurrentItem(currentYear-2016);

		month = (WheelView) findViewById(R.id.month);
		month.setAdapter(new NumbericWheelAdapter(1, 12));
		month.setCyclic(true);
		month.setLabel("月");// 添加文字
		month.setCurrentItem(currentMonth);

	

		hour = (WheelView) findViewById(R.id.hour);
		hour.setAdapter(new NumbericWheelAdapter(0, 23));
		hour.setCyclic(true);
		hour.setLabel("时");// 添加文字
		
		day = (WheelView)findViewById(R.id.day);
		day.setCyclic(true);
		day.setLabel("日");
		// 判断大小月及是否闰年,用来确定"日"的数据
		if (list_big.contains(String.valueOf(currentMonth + 1))) {
			day.setAdapter(new NumbericWheelAdapter(1, 31));
		} else if (list_little.contains(String.valueOf(currentMonth + 1))) {
			day.setAdapter(new NumbericWheelAdapter(1, 30));
		} else {
			// 闰年
			if ((currentYear % 4 == 0 && currentYear % 100 != 0) || currentYear % 400 == 0)
				day.setAdapter(new NumbericWheelAdapter(1, 29));
			else
				day.setAdapter(new NumbericWheelAdapter(1, 28));
		}
		day.setCurrentItem(currentDay - 1);

		minute = (WheelView) findViewById(R.id.minute);
		minute.setAdapter(new NumbericWheelAdapter(0, 59));
		minute.setCyclic(true);
		minute.setLabel("分");

		hours = (WheelView) findViewById(R.id.hours);
		hours.setAdapter(new NumbericWheelAdapter(0, 23));
		hours.setCyclic(true);
		hours.setLabel("时");

		minutes = (WheelView) findViewById(R.id.minutes);
		minutes.setAdapter(new NumbericWheelAdapter(0, 59));
		minutes.setCyclic(true);
		minutes.setLabel("分");

		
		hour.setCurrentItem(currentHour);
		hours.setCurrentItem(currentHours);
		minute.setCurrentItem(currentMinute);
		minutes.setCurrentItem(currentMinutes);

		/*
		 * addChangingListener(hours, "H"); addChangingListener(minutes, "M");
		 */

		// 添加"年"监听
				OnWheelChangedListener wheelListener_year = new OnWheelChangedListener() {
					@Override
					public void onChanged(WheelView wheel, int oldValue, int newValue) {
						int year_num = newValue + 2016;
						// 判断大小月及是否闰年,用来确定"日"的数据
						if (list_big.contains(String
								.valueOf(month.getCurrentItem() + 1))) {
							day.setAdapter(new NumbericWheelAdapter(1, 31));
						} else if (list_little.contains(String.valueOf(month
								.getCurrentItem() + 1))) {
							day.setAdapter(new NumbericWheelAdapter(1, 30));
						} else {
							if ((year_num % 4 == 0 && year_num % 100 != 0)
									|| year_num % 400 == 0)
								day.setAdapter(new NumbericWheelAdapter(1, 29));
							else
								day.setAdapter(new NumbericWheelAdapter(1, 28));
						}
					}
				};
				// 添加"月"监听
				OnWheelChangedListener wheelListener_month = new OnWheelChangedListener() {
					@Override
					public void onChanged(WheelView wheel, int oldValue, int newValue) {
						int month_num = newValue + 1;
						// 判断大小月及是否闰年,用来确定"日"的数据
						if (list_big.contains(String.valueOf(month_num))) {
							day.setAdapter(new NumbericWheelAdapter(1, 31));
						} else if (list_little.contains(String.valueOf(month_num))) {
							day.setAdapter(new NumbericWheelAdapter(1, 30));
						} else {
							if (((year.getCurrentItem() + 2016) % 4 == 0 && (year
									.getCurrentItem() + 2016) % 100 != 0)
									|| (year.getCurrentItem() + 2016) % 400 == 0)
								day.setAdapter(new NumbericWheelAdapter(1, 29));
							else
								day.setAdapter(new NumbericWheelAdapter(1, 28));
						}
					}
				};
				year.addChangingListener(wheelListener_year);
				month.addChangingListener(wheelListener_month);
	}
		

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.time_sure:
			String parten = "00";
			DecimalFormat decimal = new DecimalFormat(parten);

			String a = (year.getCurrentItem() + 2016) + "-"
					+ decimal.format((month.getCurrentItem() + 1)) + "-"
					+ decimal.format((day.getCurrentItem() + 1)) + " "
					+ decimal.format(hours.getCurrentItem()) + ":"
					+ decimal.format(minute.getCurrentItem());
			 Calendar c = Calendar.getInstance();
		        int nowYear = c.get(Calendar.YEAR);   
		        int nowMonth = c.get(Calendar.MONTH)+1  ;   
		        int nowDay =  c.get(Calendar.DAY_OF_MONTH);
		        int nowHours =  c.get(Calendar.HOUR_OF_DAY);
		        int nowMinutes = c.get(Calendar.MINUTE);

		        int aYear = (year.getCurrentItem() + 2016);
		        int aMonth = (month.getCurrentItem() + 1);
		        int aDay = (day.getCurrentItem() + 1);
		        int aHours = (hour.getCurrentItem());
		        int aMinutes = (minute.getCurrentItem());
			 if(aYear>nowYear){
				 isBefore = 1;
				   }else if(aYear==nowYear){
				     if(aMonth>nowMonth){
				    	 isBefore = 1;
				     }else if(aMonth==nowMonth){
				       if(aDay>nowDay){
				    	   isBefore = 1;
				    }else if(aDay==nowDay){
				      if(aHours>nowHours){
				    	  isBefore = 1;
				      }else if(aHours==aHours){
				        if(aMinutes>nowMinutes){
				        	isBefore = 1;
				     }else if(aMinutes==nowMinutes){
				    	   isBefore = 0;
				       }
				     }else{
				    	 isBefore = -1;
				     }
				      }else{
				    	  isBefore = -1;
				      }
				    }else{
				    	isBefore = -1;
				    }
				     }else{
				    	 isBefore = -1;
				     }
			 if(isBefore == 1){
				 SharedUtils.setParam(this, "water_year",
						 decimal.format(year.getCurrentItem() + 2016));
				 SharedUtils.setParam(this, "water_month",
						 decimal.format(month.getCurrentItem() + 1));
				 SharedUtils.setParam(this, "water_day",
						 decimal.format(day.getCurrentItem() + 1));
				 SharedUtils.setParam(this, "water_hour",
						 decimal.format(hour.getCurrentItem()));
				 SharedUtils.setParam(this, "water_minute",
						 decimal.format(minute.getCurrentItem()));
				 SharedUtils.setParam(this, "water_hours",
						 decimal.format(hours.getCurrentItem()));
				 SharedUtils.setParam(this, "water_minutes",
						 decimal.format(minutes.getCurrentItem()));
				 finish();
			 }else{
				 new AlertDialog.Builder(TimeAvtivityDialogWater.this)
					.setTitle("系统提示")
					// 设置对话框标题

					.setMessage("设定计划的开始时间不可小于当前时间 请重新设定！")
					// 设置显示的内容

					.setPositiveButton(
							"确定",
							new DialogInterface.OnClickListener() {// 添加确定按钮

								@Override
								public void onClick(
										DialogInterface dialog,
										int which) {// 确定按钮的响应事件

									// TODO Auto-generated
									// method stub
									dialog.dismiss();

								}
							}).show();// 在按键响应事件中显示此对话框

			 }
			System.out.println(a);
			break;
		case R.id.time_canle:
			finish();
			break;
		default:
			break;
		}
	}
}
