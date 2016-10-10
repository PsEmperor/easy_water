package ps.emperor.easy_water.fragment;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.Interface.OnWheelChangedListener;
import ps.emperor.easy_water.activity.TimeAvtivityDialogWater;
import ps.emperor.easy_water.adapter.NumbericWheelAdapter;
import ps.emperor.easy_water.adapter.NumericWheelAdapter;
import ps.emperor.easy_water.utils.SharedUtils;
import ps.emperor.easy_water.view.MainActionBar;
import ps.emperor.easy_water.view.WheelView;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;
import android.view.View.OnClickListener;

/**
 * 全集快速设置
 * 
 * @author 毛国江
 * @version 2016-5-25 上午9：53
 */
@SuppressLint("NewApi")
public class ApplyIrrigateProjectSeasonFragment extends Fragment implements
		OnClickListener, OnCheckedChangeListener {
	private LayoutInflater mInflater;
	private MainActionBar actionBar;
	private ToggleButton time_night, time_interval;
	private Boolean isNight, isInterval;
	private Dialog dialog;
	private TextView tv_time_continue,tv_time_long,tv_time_number,tv_time_interval,tv_time_start;
	private RelativeLayout layout_time_start,layout_time_continue,layout_time_long,layout_time_number,layout_time_interval;
	private int id;
	private int isBefore;
	private WheelView current_year;
	private  WheelView current_month;
	private  WheelView current_day;
	private  WheelView current_hour;
	private  WheelView current_minute;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflater = inflater;
		View view = inflater.inflate(
				R.layout.fragment_apply_irrigate_project_season, container,
				false);
		
		init();

		actionBar = (MainActionBar) view
				.findViewById(R.id.actionbar_apply_irrigat_project_season);
		actionBar.setLeftIcon(R.drawable.btn_back_selector);
		actionBar.setRightGone();
		actionBar.setTitle("全季快速设定");
		actionBar.setActionBarOnClickListener(this);

		time_night = (ToggleButton) view
				.findViewById(R.id.toggle_apply_irriagte_project_season_time_night);
		time_interval = (ToggleButton) view
				.findViewById(R.id.toggle_apply_irriagte_project_season_time_random);

		layout_time_continue = (RelativeLayout) view.findViewById(R.id.layout_apply_irriagte_project_season_time_continue);
		layout_time_continue.setOnClickListener(this);
		layout_time_long = (RelativeLayout) view.findViewById(R.id.layout_apply_irriagte_project_season_time_long);
		layout_time_long.setOnClickListener(this);
		layout_time_number = (RelativeLayout) view.findViewById(R.id.layout_apply_irriagte_project_season_time_number);
		layout_time_number.setOnClickListener(this);
		layout_time_interval = (RelativeLayout) view.findViewById(R.id.layout_apply_irriagte_project_season_time_interval);
		layout_time_interval.setOnClickListener(this);
		layout_time_start = (RelativeLayout) view.findViewById(R.id.layout_apply_irriagte_project_season_time_start);
		layout_time_start.setOnClickListener(this);
		
		tv_time_continue = (TextView) view.findViewById(R.id.text_apply_irriagte_project_season_time_continue);
		tv_time_long = (TextView) view.findViewById(R.id.text_apply_irriagte_project_season_time_long);
		tv_time_number = (TextView) view.findViewById(R.id.text_apply_irriagte_project_season_time_number);
		tv_time_interval = (TextView) view.findViewById(R.id.text_apply_irriagte_project_season_time_interval);
		tv_time_start = (TextView) view.findViewById(R.id.text_apply_irriagte_project_season_time_start);
		
		time_night.setChecked(isNight);
		time_interval.setChecked(isInterval);

		time_night.setOnCheckedChangeListener(this);
		time_interval.setOnCheckedChangeListener(this);
		// if(!isMsg){
		// initToggle = false;
		// }
		return view;
	}

	private void init() {
		isNight = (Boolean) SharedUtils.getParam(getActivity(), "Night", false);
		isInterval = (Boolean) SharedUtils.getParam(getActivity(), "Interval",
				false);
	}

	@Override
	public void onClick(View v) {
		FragmentManager fgManager = getFragmentManager();
		FragmentTransaction transaction = fgManager.beginTransaction();
		switch (v.getId()) {
		case R.id.acitionbar_left:
			ApplyIrrigateProjectFragment fragment = new ApplyIrrigateProjectFragment();
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			transaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.replace(R.id.fl, fragment, "main");
			transaction.commit();
			break;
		case R.id.layout_apply_irriagte_project_season_time_continue:
			id = 1;
			showDateTimePickers(mInflater);//灌水持续时间
			break;
		case R.id.layout_apply_irriagte_project_season_time_long:
			id = 2;
			showDateTimePicker(mInflater);//允许最长灌溉时间
			break;
		case R.id.layout_apply_irriagte_project_season_time_number:
			id = 3;
			showDateTimePicker(mInflater);//灌溉次数
			break;
		case R.id.layout_apply_irriagte_project_season_time_interval://间隔时间
			id = 4;
			showDateTimePicker(mInflater);
			break;
		case R.id.layout_apply_irriagte_project_season_time_start://开始时间
			showDateTimePicker1(mInflater);
			break;
		default:
			break;
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch (buttonView.getId()) {
		case R.id.toggle_apply_irriagte_project_season_time_night:// 规律
			Toast.makeText(getActivity(), "开关--" + isChecked,
					Toast.LENGTH_SHORT).show();
			SharedUtils.setParam(getActivity(), "Night", isChecked);
			break;
		case R.id.toggle_apply_irriagte_project_season_time_random:// 规律
			Toast.makeText(getActivity(), "开关--" + isChecked,
					Toast.LENGTH_SHORT).show();
			SharedUtils.setParam(getActivity(), "Interval", isChecked);
			break;
		}
	}

	/**
	 * @Description: TODO 弹出日期时间选择器
	 */
	private void showDateTimePicker(LayoutInflater inflater) {
		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);

		dialog = new Dialog(getActivity());
		dialog.setTitle("请选择");
		// 找到dialog的布局文件
		mInflater = inflater;
		View view = inflater.inflate(R.layout.chose_times, null);

		// 时
		final WheelView wv_hours = (WheelView) view.findViewById(R.id.hour);
		wv_hours.setAdapter(new NumericWheelAdapter(0, 23));
		wv_hours.setCyclic(true);
		wv_hours.setCurrentItem(hour);

		Button btn_sure = (Button) view.findViewById(R.id.chose_time_sures);
		Button btn_cancel = (Button) view.findViewById(R.id.chose_time_canles);
		// 确定
		btn_sure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(id == 1){
					
				}else if(id == 2){
					tv_time_long.setText(wv_hours.getCurrentItem()+"小时");
				}else if(id == 3){
					tv_time_number.setText(wv_hours.getCurrentItem()+"次");
				}else if(id == 4){
					tv_time_interval.setText(wv_hours.getCurrentItem()+"天");
				}
				// TODO Auto-generated method stub
				// 如果是个数,则显示为"02"的样式
				// 设置日期的显示
//				 tv_time.setText((wv_year.getCurrentItem() + START_YEAR) + "-"
//				 + decimal.format((wv_month.getCurrentItem() + 1)) + "-"
//				 + decimal.format((wv_day.getCurrentItem() + 1)) + " "
//				 + decimal.format(wv_hours.getCurrentItem()) + ":"
//				 + decimal.format(wv_minute.getCurrentItem()));

				dialog.dismiss();
			}
		});
		// 取消
		btn_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		// 设置dialog的布局,并显示
		dialog.setContentView(view);
		dialog.show();
	}
	/**
	 * @Description: TODO 弹出日期时间选择器
	 */
	private void showDateTimePickers(LayoutInflater inflater) {
		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);

		dialog = new Dialog(getActivity());
		dialog.setTitle("请选择");
		// 找到dialog的布局文件
		mInflater = inflater;
		View view = inflater.inflate(R.layout.time_filter, null);

		// 时
		final WheelView wv_hours = (WheelView) view.findViewById(R.id.hour_filter);
		wv_hours.setAdapter(new NumericWheelAdapter(0, 23));
		wv_hours.setCyclic(true);
		wv_hours.setCurrentItem(hour);


		// 分
		final WheelView wv_minute = (WheelView) view.findViewById(R.id.minute_filter);
		wv_minute.setAdapter(new NumericWheelAdapter(0, 23));
		wv_minute.setCyclic(true);
		wv_minute.setCurrentItem(hour);
		
		Button btn_sure = (Button) view.findViewById(R.id.time_sures_filter);
		Button btn_cancel = (Button) view.findViewById(R.id.time_canles_filter);
		// 确定
		btn_sure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				tv_time_continue.setText(wv_hours.getCurrentItem()+"小时"+wv_minute.getCurrentItem()+"分钟");
				// TODO Auto-generated method stub
				// 如果是个数,则显示为"02"的样式
				// 设置日期的显示
//				 tv_time.setText((wv_year.getCurrentItem() + START_YEAR) + "-"
//				 + decimal.format((wv_month.getCurrentItem() + 1)) + "-"
//				 + decimal.format((wv_day.getCurrentItem() + 1)) + " "
//				 + decimal.format(wv_hours.getCurrentItem()) + ":"
//				 + decimal.format(wv_minute.getCurrentItem()));

				dialog.dismiss();
			}
		});
		// 取消
		btn_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		// 设置dialog的布局,并显示
		dialog.setContentView(view);
		dialog.show();
	}
/**
 * @Description: TODO 弹出日期时间选择器
 */
private void showDateTimePicker1(LayoutInflater inflater) {
	Calendar calendar = Calendar.getInstance();
	int year = calendar.get(Calendar.YEAR);
	int month = calendar.get(Calendar.MONTH);
	int day = calendar.get(Calendar.DATE);
	int hour = calendar.get(Calendar.HOUR_OF_DAY);
	int minute = calendar.get(Calendar.MINUTE);

	dialog = new Dialog(getActivity());
	dialog.setTitle("请选择时间");
	// 找到dialog的布局文件
	mInflater = inflater;
	View view = inflater.inflate(R.layout.chose_time, null);

	String[] months_big = { "1", "3", "5", "7", "8", "10", "12" };
	String[] months_little = { "4", "6", "9", "11" };
	
	final List<String> list_big = Arrays.asList(months_big);
	final List<String> list_little = Arrays.asList(months_little);

	current_year = (WheelView) view.findViewById(R.id.chose_year);
	current_year.setAdapter(new NumbericWheelAdapter(2016, 2036));
	current_year.setCyclic(true);
	current_year.setLabel("年");// 添加文字
	current_year.setCurrentItem(year-2016);

	current_month = (WheelView) view.findViewById(R.id.chose_month);
	current_month.setAdapter(new NumbericWheelAdapter(1, 12));
	current_month.setCyclic(true);
	current_month.setLabel("月");// 添加文字
	current_month.setCurrentItem(month);



	current_hour = (WheelView) view.findViewById(R.id.chose_hour);
	current_hour.setAdapter(new NumbericWheelAdapter(0, 23));
	current_hour.setCyclic(true);
	current_hour.setLabel("时");// 添加文字
	
	current_day = (WheelView) view.findViewById(R.id.chose_day);
	current_day.setCyclic(true);
	current_day.setLabel("日");
	// 判断大小月及是否闰年,用来确定"日"的数据
	if (list_big.contains(String.valueOf(month + 1))) {
		current_day.setAdapter(new NumbericWheelAdapter(1, 31));
	} else if (list_little.contains(String.valueOf(month + 1))) {
		current_day.setAdapter(new NumbericWheelAdapter(1, 30));
	} else {
		// 闰年
		if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
			current_day.setAdapter(new NumbericWheelAdapter(1, 29));
		else
			current_day.setAdapter(new NumbericWheelAdapter(1, 28));
	}
	current_day.setCurrentItem(day - 1);

	current_minute = (WheelView) view.findViewById(R.id.chose_minute);
	current_minute.setAdapter(new NumbericWheelAdapter(0, 60));
	current_minute.setCyclic(true);
	current_minute.setLabel("分");


	
	current_hour.setCurrentItem(hour);
	current_minute.setCurrentItem(minute);

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
							.valueOf(current_month.getCurrentItem() + 1))) {
						current_day.setAdapter(new NumbericWheelAdapter(1, 31));
					} else if (list_little.contains(String.valueOf(current_month
							.getCurrentItem() + 1))) {
						current_day.setAdapter(new NumbericWheelAdapter(1, 30));
					} else {
						if ((year_num % 4 == 0 && year_num % 100 != 0)
								|| year_num % 400 == 0)
							current_day.setAdapter(new NumbericWheelAdapter(1, 29));
						else
							current_day.setAdapter(new NumbericWheelAdapter(1, 28));
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
						current_day.setAdapter(new NumbericWheelAdapter(1, 31));
					} else if (list_little.contains(String.valueOf(month_num))) {
						current_day.setAdapter(new NumbericWheelAdapter(1, 30));
					} else {
						if (((current_year.getCurrentItem() + 2016) % 4 == 0 && (current_year
								.getCurrentItem() + 2016) % 100 != 0)
								|| (current_year.getCurrentItem() + 2016) % 400 == 0)
							current_day.setAdapter(new NumbericWheelAdapter(1, 29));
						else
							current_day.setAdapter(new NumbericWheelAdapter(1, 28));
					}
				}
			};
			current_year.addChangingListener(wheelListener_year);
			current_month.addChangingListener(wheelListener_month);
			
	Button btn_sure = (Button) view.findViewById(R.id.chose_time_sure);
	Button btn_cancel = (Button) view.findViewById(R.id.chose_time_canle);
	// 确定
	btn_sure.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
			String parten = "00";
			DecimalFormat decimal = new DecimalFormat(parten);

			String a = (current_year.getCurrentItem() + 2016) + "-"
					+ decimal.format((current_month.getCurrentItem() + 1)) + "-"
					+ decimal.format((current_day.getCurrentItem() + 1)) + " "
					+ decimal.format(current_hour.getCurrentItem()) + ":"
					+ decimal.format(current_minute.getCurrentItem());
			 Calendar c = Calendar.getInstance();
		        int nowYear = c.get(Calendar.YEAR);   
		        int nowMonth = c.get(Calendar.MONTH)+1  ;   
		        int nowDay =  c.get(Calendar.DAY_OF_MONTH);
		        int nowHours =  c.get(Calendar.HOUR_OF_DAY);
		        int nowMinutes = c.get(Calendar.MINUTE);

		        int aYear = (current_year.getCurrentItem() + 2016);
		        int aMonth = (current_month.getCurrentItem() + 1);
		        int aDay = (current_day.getCurrentItem() + 1);
		        int aHours = (current_hour.getCurrentItem());
		        int aMinutes = (current_minute.getCurrentItem());
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
			tv_time_start.setText((current_year.getCurrentItem() + 2016) + "-"
			 + decimal.format((current_month.getCurrentItem() + 1)) + "-"
			 + decimal.format((current_day.getCurrentItem() + 1)) + " "
			 + decimal.format(current_hour.getCurrentItem()) + ":"
			 + decimal.format(current_minute.getCurrentItem()));
//			 TODO Auto-generated method stub
			// 如果是个数,则显示为"02"的样式
			// 设置日期的显示
			// tv_time.setText((wv_year.getCurrentItem() + START_YEAR) + "-"
			// + decimal.format((wv_month.getCurrentItem() + 1)) + "-"
			// + decimal.format((wv_day.getCurrentItem() + 1)) + " "
			// + decimal.format(wv_hours.getCurrentItem()) + ":"
			// + decimal.format(wv_minute.getCurrentItem()));
			dialog.dismiss();
			 }else{
				 new AlertDialog.Builder(getActivity())
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
		}
	});
	// 取消
	btn_cancel.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			dialog.dismiss();
		}
	});
	// 设置dialog的布局,并显示
	dialog.setContentView(view);
	dialog.show();
}
}