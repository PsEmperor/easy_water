package ps.emperor.easy_water.fragment;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.Interface.OnWheelChangedListener;
import ps.emperor.easy_water.adapter.NumbericWheelAdapter;
import ps.emperor.easy_water.adapter.NumericWheelAdapter;
import ps.emperor.easy_water.greendao.DBHelper;
import ps.emperor.easy_water.greendao.IrrigationProject;
import ps.emperor.easy_water.utils.CheckUtil;
import ps.emperor.easy_water.utils.SharedUtils;
import ps.emperor.easy_water.view.MainActionBar;
import ps.emperor.easy_water.view.WheelView;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

/**
 * 单轮快速设置
 * 
 * @author 毛国江
 * @version 2016-5-25 上午9：53
 */
@SuppressLint("NewApi")
public class ApplyIrrigateProjectSingleFragment extends Fragment implements
		OnClickListener, OnCheckedChangeListener {

	private LayoutInflater mInflater;
	private MainActionBar actionBar;
	private ToggleButton time_night, time_interval;
	private Boolean isNight, isRandom;
	private RelativeLayout layout_time_start, layout_time_continue,
			layout_time_long, layout_time_interval;
	private TextView tv_time_start, tv_time_continue, tv_time_long;
	private Dialog dialog;
	private int id;
	private int isBefore;
	private WheelView current_year;
	private WheelView current_month;
	private WheelView current_day;
	private WheelView current_hour;
	private WheelView current_minute;
	private Button button_single_this;
	private DBHelper dbHelper;
	private String time_start, time_end, time_starts, time_compare_start,
			time_compare_end, time_db_start, time_db_end;
	private int aYear, aMonth, aDay, aHours, aMinutes, nHour, nMinutes;
	private IrrigationProject irrigationProject;
	private int now_round, notify, isSkip, isTrue = 0, long_hour, isOne=1;
	private long temp, compare, compares;
	private int saveDate,saveDates,loadDate,time_long,onleOne=0;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 100:
				if (CheckUtil.IsEmpty(time_start)) {
					tv_time_start.setText("0000-00-00 00:00");
				} else {
					tv_time_start.setText(time_start);
				}
				break;
			default:
				break;
			}
		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflater = inflater;
		View view = inflater.inflate(
				R.layout.fragment_apply_irrigate_project_single, container,
				false);

		init();
		dbHelper = DBHelper.getInstance(getActivity()); // 得到DBHelper对象

		actionBar = (MainActionBar) view
				.findViewById(R.id.actionbar_apply_irrigat_project_single);
		actionBar.setLeftIcon(R.drawable.btn_back_selector);
		actionBar.setRightGone();
		actionBar.setTitle("单轮快速设定");
		actionBar.setActionBarOnClickListener(this);

		layout_time_start = (RelativeLayout) view
				.findViewById(R.id.layout_apply_irriagte_project_single_time_start);
		layout_time_start.setOnClickListener(this);
		layout_time_continue = (RelativeLayout) view
				.findViewById(R.id.layout_apply_irriagte_project_single_time_continue);
		layout_time_continue.setOnClickListener(this);
		layout_time_long = (RelativeLayout) view
				.findViewById(R.id.layout_apply_irriagte_project_single_time_long);
		layout_time_long.setOnClickListener(this);
		layout_time_interval = (RelativeLayout) view
				.findViewById(R.id.layout_apply_irriagte_project_single_time_interval);
		layout_time_interval.setOnClickListener(this);

		tv_time_start = (TextView) view
				.findViewById(R.id.text_apply_irriagte_project_single_time_start);
		tv_time_continue = (TextView) view
				.findViewById(R.id.text_apply_irriagte_project_single_time_continue);
		tv_time_long = (TextView) view
				.findViewById(R.id.text_apply_irriagte_project_single_time_long);

		time_night = (ToggleButton) view
				.findViewById(R.id.toggle_apply_irriagte_project_single_time_night);
		time_interval = (ToggleButton) view
				.findViewById(R.id.toggle_apply_irriagte_project_single_time_random);

		nHour = (int) SharedUtils.getParam(getActivity(), "nHour", 0);
		nMinutes = (int) SharedUtils.getParam(getActivity(), "nMinutes", 0);
		if (!CheckUtil.IsEmpty(nMinutes)) {
			tv_time_continue.setText(nHour + "时" + nMinutes + "分");
		} else {
			tv_time_continue.setText(nHour + "时");
		}
		long_hour = (int) SharedUtils.getParam(getActivity(), "long_hour", 0);
		tv_time_long.setText(long_hour + "时");

		time_night.setChecked(isNight);
		time_interval.setChecked(isRandom);

		time_night.setOnCheckedChangeListener(this);
		time_interval.setOnCheckedChangeListener(this);

		button_single_this = (Button) view
				.findViewById(R.id.button_apply_irriagte_project_single_this);
		button_single_this.setOnClickListener(this);

		// if(!isMsg){
		// initToggle = false;
		// }
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		new Thread(new Runnable() {

			@Override
			public void run() {
				List<IrrigationProject> listentity = dbHelper.loadAllProject();
				if (!CheckUtil.IsEmpty(listentity)) {
					Calendar c = Calendar.getInstance();
					Calendar c1 = Calendar.getInstance();
					try {
						c.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm")
								.parse(listentity.get(0).getProjectend()));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					compares = c.getTimeInMillis();
					for (int j = 0; j < listentity.size(); j++) {
						try {
							c1.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm")
									.parse(listentity.get(j).getProjectend()));
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						compare = c1.getTimeInMillis();
						if (compare > compares) {
							temp = compare;
							compare = compares;
							compares = temp;
							Date date = new Date(compares);
							SimpleDateFormat sdf = new SimpleDateFormat(
									"yyyy-MM-dd HH:mm");
							time_start = sdf.format(date);

						}
					}
					Message message = new Message();
					message.what = 100;
					handler.sendMessage(message);
				}
			}
		}).start();
	}

	private void init() {
		isNight = (Boolean) SharedUtils.getParam(getActivity(), "SingleNight",
				false);
		isRandom = (Boolean) SharedUtils.getParam(getActivity(),
				"SingleInterval", false);
	}

	@Override
	public void onClick(View v) {
		FragmentManager fgManager = getFragmentManager();
		FragmentTransaction transaction = fgManager.beginTransaction();
		switch (v.getId()) {
		case R.id.acitionbar_left:
			// dbHelper.DeleteSessions(irrigationProject);
			isSkip = 3;
			SharedUtils.setParam(getActivity(), "isSkip", isSkip);
			ApplyIrrigateProjectFragment fragment = new ApplyIrrigateProjectFragment();
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			transaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.replace(R.id.fl, fragment, "main");
			transaction.commit();
			break;
		case R.id.layout_apply_irriagte_project_single_time_start:// 开始时间
			showDateTimePicker1(mInflater);
			break;
		case R.id.layout_apply_irriagte_project_single_time_continue:// 持续时间
			id = 1;
			showDateTimePickers(mInflater);
			break;
		case R.id.layout_apply_irriagte_project_single_time_long:// 最长工作时间
			id = 2;
			showDateTimePicker(mInflater);
			break;
		case R.id.button_apply_irriagte_project_single_this:

			if (CheckUtil.IsEmpty(time_start)
					|| time_start.equals("0000-00-00 00:00")) {

				Toast.makeText(getActivity(), "请设置开始时间", Toast.LENGTH_SHORT)
						.show();
				break;
			} else if (CheckUtil.IsEmpty(tv_time_continue)
					|| tv_time_continue.getText().equals("0时") ||tv_time_continue.getText().equals("0小时")
					) {
				Toast.makeText(getActivity(), "请设置持续时间", Toast.LENGTH_SHORT)
						.show();
				break;
			} else if (CheckUtil.IsEmpty(tv_time_long)
					|| tv_time_continue.getText().equals("0时")
					|| long_hour == 0) {
				Toast.makeText(getActivity(), "请设置最长灌溉时间", Toast.LENGTH_SHORT)
						.show();
				break;
			} else {
				irrigationProject = new IrrigationProject();
				List<IrrigationProject> listentity = dbHelper.loadAllSessions();
				time_compare_start = time_start;
				time_long = 24 - long_hour;
				java.util.Date dates = new java.util.Date();
				SimpleDateFormat formats = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm");
				try {
					dates = formats.parse(time_compare_start);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				saveDates = dates.getDate();
				for (int j = 0; j < 5; j++) {
					for (int i = 0; i < listentity.size(); i++) {
						Calendar c = Calendar.getInstance();
						if (i == 0) {
							try {
								c.setTime(new SimpleDateFormat(
										"yyyy-MM-dd HH:mm")
										.parse(time_compare_start));
							} catch (ParseException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							java.util.Date date1 = new java.util.Date();
							SimpleDateFormat format1 = new SimpleDateFormat(
									"yyyy-MM-dd HH:mm");
							try {
								date1 = format1.parse(time_compare_start);
							} catch (ParseException e) {
								e.printStackTrace();
							}
							if(date1.getDate()!=0&&date1.getMinutes()!= 0){
								date1.setHours(date1.getHours() + nHour);
								date1.setMinutes(date1.getMinutes() + nMinutes);
								saveDate = date1.getDate();
									if((saveDate-saveDates==1)){
										DecimalFormat fnum = new DecimalFormat("##0.0"); 
										String sleep = fnum.format(((float)(24-time_long)/((float)24))*4)+"";
										String[] hours = sleep.split("\\.");
										date1.setHours(date1.getHours() + Integer.valueOf(hours[0]));
										if(!CheckUtil.IsEmpty(hours[1])){
											date1.setMinutes(date1.getMinutes() + Integer.valueOf(hours[1])*6);
										}
								
								}
							} else {
								date1.setHours(date1.getHours() + nHour);
								date1.setMinutes(date1.getMinutes() + nMinutes);
								loadDate = date1.getDate();
								if ((saveDate - saveDates > 1)) {
									date1.setHours(date1.getHours() + 4);
									date1.setMinutes(date1.getMinutes() + 0);
								}
							}
							time_compare_end = format1.format(date1);
						} else {
							try {
								c.setTime(new SimpleDateFormat(
										"yyyy-MM-dd HH:mm")
										.parse(time_compare_end));
							} catch (ParseException e1) {
								e1.printStackTrace();
							}
							java.util.Date date1 = new java.util.Date();
							SimpleDateFormat format1 = new SimpleDateFormat(
									"yyyy-MM-dd HH:mm");
							try {
								date1 = format1.parse(time_compare_end);
							} catch (ParseException e) {
								e.printStackTrace();
							}
							saveDate = date1.getDate();
							if(date1.getDate()!=0&&date1.getMinutes()!= 0){
								date1.setHours(date1.getHours() + nHour);
								date1.setMinutes(date1.getMinutes() + nMinutes);
									if((saveDate-saveDates==1)){
										if(onleOne == 1){
											
										}else{
											DecimalFormat fnum = new DecimalFormat("##0.0"); 
											String sleep = fnum.format(((float)(24-time_long)/((float)24))*4)+"";
											String[] hours = sleep.split("\\.");
											date1.setHours(date1.getHours() + Integer.valueOf(hours[0]));
											if(!CheckUtil.IsEmpty(hours[1])){
												date1.setMinutes(date1.getMinutes() + Integer.valueOf(hours[1])*6);
												onleOne = 1;
											}
										}
										
								
								}else {
									if(saveDate > loadDate){
										date1.setHours(date1.getHours() + 4);
										date1.setMinutes(date1.getMinutes() + 0);
								}
								}
								}else{
									date1.setHours(date1.getHours() + nHour);
									date1.setMinutes(date1.getMinutes() + nMinutes);
									if(saveDate > loadDate){
										date1.setHours(date1.getHours() + 4);
										date1.setMinutes(date1.getMinutes() + 0);
								}
								}
							time_compare_end = format1.format(date1);
						}
						time_db_start = listentity.get(i).getProjectstart();
						time_db_end = listentity.get(i).getProjectend();

						Calendar c1 = Calendar.getInstance();
						try {
							c1.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm")
									.parse(time_compare_end));
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Calendar c2 = Calendar.getInstance();
						try {
							c2.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm")
									.parse(time_db_start));
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Calendar c3 = Calendar.getInstance();
						try {
							c3.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm")
									.parse(time_db_end));
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						long ms_start = c.getTimeInMillis();
						long ms_end = c1.getTimeInMillis();
						long ms_db_start = c2.getTimeInMillis();
						long ms_db_end = c3.getTimeInMillis();
						if (ms_start >= ms_db_end || ms_end <= ms_db_start) {
							isOne = 1;
						} else {
							isOne = 2;
							break;
						}
					}
				}
				if (isOne != 1) {
					Toast.makeText(getActivity(), "在范围内", Toast.LENGTH_SHORT)
							.show();
				} else {
					List<IrrigationProject> listentity1 = dbHelper
							.loadAllSessions();
					if (CheckUtil.IsEmpty(listentity1)) {
						now_round = 0;
					} else {
						now_round = Integer.valueOf(listentity1.get(0)
								.getRound());
					}
					for (int i = 1; i < 5; i++) {
						irrigationProject = new IrrigationProject();
						irrigationProject.setProjectstart("0000-00-00 00:00");
						irrigationProject.setProjectend("0000-00-00 00:00");
						irrigationProject.setRound((now_round + 1) + "");
						irrigationProject.setMarshalling(i + "");
						dbHelper.saveSessions(irrigationProject);
					}
					int randomCommon[] = randomCommon(1, 5, 4);
					for (int i = 0; i < 4; i++) {
						Date date = new Date();
						SimpleDateFormat format = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm");
						try {
							if (i == 0) {
								date = format.parse(time_start);
								time_starts = format.format(date);
								
								if(date.getDate()!=0&&date.getMinutes()!= 0){
									date.setHours(date.getHours() + nHour);
									date.setMinutes(date.getMinutes() + nMinutes);
									saveDate = date.getDate();
										if((saveDate-saveDates==1)){
											DecimalFormat fnum = new DecimalFormat("##0.0"); 
											String sleep = fnum.format(((float)(24-time_long)/((float)24))*4)+"";
											String[] hours = sleep.split("\\.");
											date.setHours(date.getHours() + Integer.valueOf(hours[0]));
											if(!CheckUtil.IsEmpty(hours[1])){
												date.setMinutes(date.getMinutes() + Integer.valueOf(hours[1])*6);
											}
											else {
												if(saveDate > loadDate){
													date.setHours(date.getHours() + 4);
													date.setMinutes(date.getMinutes() + 0);
											}
											}
									}
									}else{
										date.setHours(date.getHours() + nHour);
										date.setMinutes(date.getMinutes() + nMinutes);
										if(saveDate > loadDate){
											date.setHours(date.getHours() + 4);
											date.setMinutes(date.getMinutes() + 0);
									}
									}
							} else {
								date = format.parse(time_end);
								time_starts = format.format(date);
								loadDate = date.getDate();
								if(date.getDate()!=0&&date.getMinutes()!= 0){
									date.setHours(date.getHours() + nHour);
									date.setMinutes(date.getMinutes() + nMinutes);
									saveDate = date.getDate();
										if((saveDate-saveDates==1)){
											if(onleOne == 1){
												
											}else{
												DecimalFormat fnum = new DecimalFormat("##0.0"); 
												String sleep = fnum.format(((float)(24-time_long)/((float)24))*4)+"";
												String[] hours = sleep.split("\\.");
												date.setHours(date.getHours() + Integer.valueOf(hours[0]));
												if(!CheckUtil.IsEmpty(hours[1])){
													date.setMinutes(date.getMinutes() + Integer.valueOf(hours[1])*6);
												}
												onleOne = 1;
											}
									
									}else {
										if(saveDate > loadDate){
											date.setHours(date.getHours() + 4);
											date.setMinutes(date.getMinutes() + 0);
									}
									}
									}else{
										date.setHours(date.getHours() + nHour);
										date.setMinutes(date.getMinutes() + nMinutes);
										if(saveDate > loadDate){
											date.setHours(date.getHours() + 4);
											date.setMinutes(date.getMinutes() + 0);
									}
									}
							}
//							date.setHours(date.getHours()
//									+ Integer.valueOf(nHour));
//							date.setMinutes(date.getMinutes()
//									+ Integer.valueOf(nMinutes));
							time_end = format.format(date);
							if (CheckUtil.IsEmpty(listentity)) {
								now_round = 0;
							}
							// if (listentity.get(0).getRound().equals("1")) {
							// now_round = 0;
							// }
							if (isRandom == true) {
								dbHelper.updateProject(now_round + 1 + "",
										randomCommon[i], time_starts, time_end);
							} else {
								dbHelper.updateProject(now_round + 1 + "",
										i + 1, time_starts, time_end);
							}
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
					notify = 1;
					SharedUtils.setParam(getActivity(), "notify", notify);
					ApplyIrrigateProjectFragment fragment3 = new ApplyIrrigateProjectFragment();
					// transaction.setCustomAnimations(R.anim.right_in,
					// R.anim.right_out);
					transaction
							.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
					transaction.replace(R.id.fl, fragment3, "main");
					transaction.commit();
				}
			}
			// }
			break;
		default:
			break;
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch (buttonView.getId()) {
		case R.id.toggle_apply_irriagte_project_single_time_night:// 规律
			Toast.makeText(getActivity(), "开关--" + isChecked,
					Toast.LENGTH_SHORT).show();
			SharedUtils.setParam(getActivity(), "SingleNight", isChecked);
			if (isChecked) {
				isNight = true;
			} else {
				isNight = false;
			}
			break;
		case R.id.toggle_apply_irriagte_project_single_time_random:// 规律
			Toast.makeText(getActivity(), "开关--" + isChecked,
					Toast.LENGTH_SHORT).show();
			SharedUtils.setParam(getActivity(), "SingleInterval", isChecked);
			if (isChecked) {
				isRandom = true;
			} else {
				isRandom = false;
			}
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
		dialog.setTitle("请选择时间");
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
				if (id == 1) {
				} else if (id == 2) {
					tv_time_long.setText(wv_hours.getCurrentItem() + "小时");
					long_hour = wv_hours.getCurrentItem();
					SharedUtils.setParam(getActivity(), "long_hour",
							wv_hours.getCurrentItem());
					// }else if(id == 3){
					// //
					// tv_time_interval.setText(wv_hours.getCurrentItem()+"轮");
				}
				// TODO Auto-generated method stub
				// 如果是个数,则显示为"02"的样式
				// 设置日期的显示
				// tv_time.setText((wv_year.getCurrentItem() + START_YEAR) + "-"
				// + decimal.format((wv_month.getCurrentItem() + 1)) + "-"
				// + decimal.format((wv_day.getCurrentItem() + 1)) + " "
				// + decimal.format(wv_hours.getCurrentItem()) + ":"
				// + decimal.format(wv_minute.getCurrentItem()));

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
		current_year.setCurrentItem(year - 2016);

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
		current_minute.setAdapter(new NumbericWheelAdapter(0, 59));
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
				if (list_big.contains(String.valueOf(current_month
						.getCurrentItem() + 1))) {
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
						+ decimal.format((current_month.getCurrentItem() + 1))
						+ "-"
						+ decimal.format((current_day.getCurrentItem() + 1))
						+ " " + decimal.format(current_hour.getCurrentItem())
						+ ":" + decimal.format(current_minute.getCurrentItem());
				Calendar c = Calendar.getInstance();
				int nowYear = c.get(Calendar.YEAR);
				int nowMonth = c.get(Calendar.MONTH) + 1;
				int nowDay = c.get(Calendar.DAY_OF_MONTH);
				int nowHours = c.get(Calendar.HOUR_OF_DAY);
				int nowMinutes = c.get(Calendar.MINUTE);

				aYear = (current_year.getCurrentItem() + 2016);
				aMonth = (current_month.getCurrentItem() + 1);
				aDay = (current_day.getCurrentItem() + 1);
				aHours = (current_hour.getCurrentItem());
				aMinutes = (current_minute.getCurrentItem());
				if (aYear > nowYear) {
					isBefore = 1;
				} else if (aYear == nowYear) {
					if (aMonth > nowMonth) {
						isBefore = 1;
					} else if (aMonth == nowMonth) {
						if (aDay > nowDay) {
							isBefore = 1;
						} else if (aDay == nowDay) {
							if (aHours > nowHours) {
								isBefore = 1;
							} else if (aHours == aHours) {
								if (aMinutes > nowMinutes) {
									isBefore = 1;
								}
							} else {
								isBefore = -1;
							}
						} else {
							isBefore = -1;
						}
					} else {
						isBefore = -1;
					}
				} else {
					isBefore = -1;
				}
				if (isBefore == 1) {
					time_start = ((current_year.getCurrentItem() + 2016)
							+ "-"
							+ decimal.format((current_month.getCurrentItem() + 1))
							+ "-"
							+ decimal.format((current_day.getCurrentItem() + 1))
							+ " "
							+ decimal.format(current_hour.getCurrentItem())
							+ ":" + decimal.format(current_minute
							.getCurrentItem()));
					tv_time_start.setText(time_start);

					// isTrue = 1;
					//
					// // 添加数据库
					// List<IrrigationProject> listentity = dbHelper
					// .loadAllSessions();
					// if (CheckUtil.IsEmpty(listentity)) {
					// now_round = 0;
					// } else {
					// now_round = Integer.valueOf(listentity.get(0)
					// .getRound());
					// }
					// for (int i = 1; i < 5; i++) {
					// irrigationProject = new IrrigationProject();
					// irrigationProject.setProjectstart("0000-00-00 00:00");
					// irrigationProject.setProjectend("0000-00-00 00:00");
					// irrigationProject.setRound((now_round + 1) + "");
					// irrigationProject.setMarshalling(i + "");
					// dbHelper.saveSessions(irrigationProject);
					// }
					// TODO Auto-generated method stub
					// 如果是个数,则显示为"02"的样式
					// 设置日期的显示
					// tv_time.setText((wv_year.getCurrentItem() + START_YEAR) +
					// "-"
					// + decimal.format((wv_month.getCurrentItem() + 1)) + "-"
					// + decimal.format((wv_day.getCurrentItem() + 1)) + " "
					// + decimal.format(wv_hours.getCurrentItem()) + ":"
					// + decimal.format(wv_minute.getCurrentItem()));
					dialog.dismiss();
				} else {
					new AlertDialog.Builder(getActivity())
							.setTitle("系统提示")
							// 设置对话框标题

							.setMessage("设定计划的开始时间不可小于当前时间 请重新设定！")
							// 设置显示的内容

							.setPositiveButton("确定",
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
		final WheelView wv_hours = (WheelView) view
				.findViewById(R.id.hour_filter);
		wv_hours.setAdapter(new NumericWheelAdapter(0, 23));
		wv_hours.setCyclic(true);
		wv_hours.setCurrentItem(hour);

		// 分
		final WheelView wv_minute = (WheelView) view
				.findViewById(R.id.minute_filter);
		wv_minute.setAdapter(new NumericWheelAdapter(0, 59));
		wv_minute.setCyclic(true);
		wv_minute.setCurrentItem(hour);

		Button btn_sure = (Button) view.findViewById(R.id.time_sures_filter);
		Button btn_cancel = (Button) view.findViewById(R.id.time_canles_filter);
		// 确定
		btn_sure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (CheckUtil.IsEmpty(wv_minute.getCurrentItem())
						|| wv_minute.getCurrentItem() == 0) {
					nHour = wv_hours.getCurrentItem();
					nMinutes = wv_minute.getCurrentItem();
					tv_time_continue.setText(wv_hours.getCurrentItem() + "小时");
					SharedUtils.setParam(getActivity(), "nHour", nHour);
					SharedUtils.setParam(getActivity(), "nMinutes", nMinutes);
				} else {
					tv_time_continue.setText(wv_hours.getCurrentItem() + "小时"
							+ wv_minute.getCurrentItem() + "分钟");
					nHour = wv_hours.getCurrentItem();
					nMinutes = wv_minute.getCurrentItem();
					SharedUtils.setParam(getActivity(), "nHour", nHour);
					SharedUtils.setParam(getActivity(), "nMinutes", nMinutes);
				}
				// TODO Auto-generated method stub
				// 如果是个数,则显示为"02"的样式
				// 设置日期的显示
				// tv_time.setText((wv_year.getCurrentItem() + START_YEAR) + "-"
				// + decimal.format((wv_month.getCurrentItem() + 1)) + "-"
				// + decimal.format((wv_day.getCurrentItem() + 1)) + " "
				// + decimal.format(wv_hours.getCurrentItem()) + ":"
				// + decimal.format(wv_minute.getCurrentItem()));

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

	public static int[] randomCommon(int min, int max, int n) {
		// 不合法参数
		if (n > (max - min + 1) || max < min) {
			return null;
		}
		// 生成一个n位的随机数组
		int[] result = new int[n];
		int count = 0;
		while (count < n) {
			// 随机生成一个数
			int num = (int) (Math.random() * (max - min)) + min;
			boolean flag = true; // 是否存在相同标志
			// 如果随生成的数字和数组中的数字有相同 设置flag=false
			for (int i = 0; i < result.length; i++) {
				if (num == result[i]) {
					flag = false;
					break;
				}
			}
			// 如果随机生成的数字和数组中的数字没有相同就放入数组并count+1
			if (flag) {
				result[count] = num;
				count++;
			}
		}
		return result;
	}
}
