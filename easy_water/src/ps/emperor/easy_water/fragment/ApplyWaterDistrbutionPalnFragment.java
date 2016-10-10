package ps.emperor.easy_water.fragment;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.Interface.OnWheelChangedListener;
import ps.emperor.easy_water.activity.TimeAvtivityDialog;
import ps.emperor.easy_water.activity.TimeAvtivityDialogWater;
import ps.emperor.easy_water.adapter.NumbericWheelAdapter;
import ps.emperor.easy_water.adapter.NumericWheelAdapter;
import ps.emperor.easy_water.utils.CheckUtil;
import ps.emperor.easy_water.utils.SharedUtils;
import ps.emperor.easy_water.view.MainActionBar;
import ps.emperor.easy_water.view.WheelView;

/**
 * 配水计划
 * 
 * @author 毛国江
 * @version 2016-5-18 下午15:56
 */

@SuppressLint("NewApi")
public class ApplyWaterDistrbutionPalnFragment extends Fragment implements
		OnClickListener {
	private MainActionBar actionBar;
	private LayoutInflater mInflater;
	private RelativeLayout layout_apply_water_distrbution_plan,layout_plan_hight;
	private TextView tv_plan_start,tv_plan_end,tv_plan_hight;
	private Dialog dialog;
	private int id;
	private String year,minutes, month, hours, minute, hour, day,timestart,timeend;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflater = inflater;
		View view = inflater.inflate(
				R.layout.fragment_apply_water_distrbution_plan, container,
				false);

		actionBar = (MainActionBar) view
				.findViewById(R.id.actionbar_apply_water_distrbution_plan);
		actionBar.setLeftIcon(R.drawable.btn_back_selector);
		;
		actionBar.setRightGone();
		actionBar.setTitle("配水计划");
		actionBar.setActionBarOnClickListener(this);
		
		init();
		
		layout_apply_water_distrbution_plan = (RelativeLayout) view.findViewById(R.id.layout_apply_water_distrbution_plan);
		layout_apply_water_distrbution_plan.setOnClickListener(this);
		layout_plan_hight = (RelativeLayout) view.findViewById(R.id.layout_apply_water_distrbution_plan_hight);
		layout_plan_hight.setOnClickListener(this);
		
		tv_plan_start = (TextView) view.findViewById(R.id.text_apply_water_distrbution_plan_start);
		tv_plan_end = (TextView) view.findViewById(R.id.text_apply_water_distrbution_plan_end);
		tv_plan_hight = (TextView)view.findViewById(R.id.text_apply_water_distrbution_plan_hight);
		
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			date = format.parse(timestart);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		date.setHours(date.getHours() + Integer.valueOf(hours));
		date.setMinutes(date.getMinutes() + Integer.valueOf(minutes));
		timeend = format.format(date);
		
		if(CheckUtil.IsEmpty(timestart)){
			tv_plan_start.setText("0000-00-00 00:00");
			tv_plan_end.setText("0000-00-00 00:00");
		}else{
			if (timestart.equals("0-0-0 0:0")) {
				tv_plan_start.setText("0000-00-00 00:00");
				tv_plan_end.setText("0000-00-00 00:00");
			} else {
				tv_plan_start.setText(timestart);
				tv_plan_end.setText(timeend);
			}
		}
		
		return view;
	}

	@Override
	public void onResume() {
		init();
		
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			date = format.parse(timestart);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		date.setHours(date.getHours() + Integer.valueOf(hours));
		date.setMinutes(date.getMinutes() + Integer.valueOf(minutes));
		timeend = format.format(date);
		
		if(CheckUtil.IsEmpty(timestart)){
			tv_plan_start.setText("0000-00-00 00:00");
			tv_plan_end.setText("0000-00-00 00:00");
		}else{
			if (timestart.equals("0-0-0 0:0")) {
				tv_plan_start.setText("0000-00-00 00:00");
				tv_plan_end.setText("0000-00-00 00:00");
			} else {
				tv_plan_start.setText(timestart);
				tv_plan_end.setText(timeend);
			}
		}
		super.onResume();
	}
	
	private void init() {
		year = (String) SharedUtils.getParam(getActivity(), "water_year", "0");
		month = (String) SharedUtils.getParam(getActivity(), "water_month", "0");
		day = (String) SharedUtils.getParam(getActivity(), "water_day", "0");
		hour = (String) SharedUtils.getParam(getActivity(), "water_hour", "0");
		minute = (String) SharedUtils.getParam(getActivity(), "water_minute", "0");
		hours = (String) SharedUtils.getParam(getActivity(), "water_hours", "0");
		minutes = (String) SharedUtils.getParam(getActivity(), "water_minutes", "0");

		timestart = year + "-" + month + "-" + day + " " + hour + ":" + minute;
		}

	@Override
	public void onClick(View v) {
		FragmentManager fgManager = getFragmentManager();
		FragmentTransaction transaction = fgManager.beginTransaction();
		switch (v.getId()) {
		case R.id.acitionbar_left:
			ApplyWaterDistrbutionGate fragment = new ApplyWaterDistrbutionGate();
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			transaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.replace(R.id.fl, fragment, "main");
			transaction.commit();
			break;
		case R.id.layout_apply_water_distrbution_plan:
			Intent intent = new Intent(getActivity(), TimeAvtivityDialogWater.class);
			startActivity(intent);
			break;
		case R.id.layout_apply_water_distrbution_plan_hight:
			showDateTimePicker(mInflater);
			break;
		default:
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
		dialog.setTitle("请选择闸门开度");
		// 找到dialog的布局文件
		mInflater = inflater;
		View view = inflater.inflate(R.layout.chose_times, null);

		// 时
		final WheelView wv_hours = (WheelView) view.findViewById(R.id.hour);
		wv_hours.setAdapter(new NumericWheelAdapter(0, 100));
		wv_hours.setCyclic(true);
		wv_hours.setCurrentItem(hour);

		Button btn_sure = (Button) view.findViewById(R.id.chose_time_sures);
		Button btn_cancel = (Button) view.findViewById(R.id.chose_time_canles);
		// 确定
		btn_sure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				tv_plan_hight.setText(wv_hours.getCurrentItem()+"％");
				
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
	
}
