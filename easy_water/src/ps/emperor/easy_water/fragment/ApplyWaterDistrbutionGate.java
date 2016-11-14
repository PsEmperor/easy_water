package ps.emperor.easy_water.fragment;

import java.text.DecimalFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.jpush.a.a.be;
import cn.jpush.a.a.w;

import android.annotation.SuppressLint;
import android.app.AlertDialog.Builder;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnWindowFocusChangeListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.Interface.OnWheelChangedListener;
import ps.emperor.easy_water.activity.TimeAvtivityDialog;
import ps.emperor.easy_water.adapter.ApplyWaterDistrbutionGateAdapter;
import ps.emperor.easy_water.adapter.ApplyWaterGateLinkageAdapter;
import ps.emperor.easy_water.adapter.NumbericWheelAdapter;
import ps.emperor.easy_water.entity.ApplyWaterDistrbutionGateBean;
import ps.emperor.easy_water.entity.ApplyWaterGateLinkageBean;
import ps.emperor.easy_water.utils.DensityUtil;
import ps.emperor.easy_water.utils.ScreenUtils;
import ps.emperor.easy_water.utils.SharedUtils;
import ps.emperor.easy_water.view.HorizontalListView;
import ps.emperor.easy_water.view.HorizontalListView.OnScrollStateChangedListener;
import ps.emperor.easy_water.view.HorizontalListView.OnScrollStateChangedListener.ScrollState;
import ps.emperor.easy_water.view.HorizontalListView.OnScrollStopListner;
import ps.emperor.easy_water.view.MainActionBar;
import ps.emperor.easy_water.view.WheelView;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
/**
 * 闸门控制（应用）
 * 
 * @author 毛国江
 * @version 2016-5-24 上午8:34
 */

@SuppressLint("NewApi")
public class ApplyWaterDistrbutionGate extends Fragment implements
		OnClickListener ,OnItemClickListener{

	private LayoutInflater mInflater;
	private MainActionBar actionBar;
	private TextView tv_indicator,tv_superior;
	private Button btn_time_operation;
	private TextView tv_time_operation;
	private Dialog dialog;
	private WheelView year;
	private WheelView month;
	private WheelView day;
	private WheelView wv_hour;
	private WheelView wv_minute;
	private RelativeLayout layout,layout_relative_changes,layout_relative_changes_left,layout_relative_changes_right;
	private LinearLayout layout_linear_change,layout_linear_changes;
	private List<ApplyWaterDistrbutionGateBean> beans;
	private HorizontalListView list_apply_water_distrbution_gate_control;
	private ApplyWaterDistrbutionGateAdapter adapter;
	private RelativeLayout layout_relative_changes_one,layout_relative_changes_two,layout_relative_changes_three;
	private RelativeLayout layout_relative_change,layout_relative_change_left,layout_relative_change_right,layout_relative_changes_ones,
	layout_relative_changes_twos,layout_relative_changes_threes;
	private boolean scrollFlag = false;// 标记是否滑动
	private int lastVisibleItemPosition = 0;// 标记上次滑动位置
	private ImageView imageLeft,imageRight;
	private int isBefore,all;
	    
	@SuppressLint("CutPasteId")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflater = inflater;
		View view = inflater.inflate(
				R.layout.fragment_apply_water_distrbution_gate_control,
				container, false);

		actionBar = (MainActionBar) view
				.findViewById(R.id.actionbar_apply_water_distrbution_gate_control);
		actionBar.setLeftIcon(R.drawable.btn_back_selector);
		actionBar.setRightGone();
		actionBar.setTitle("闸门控制");
		actionBar.setActionBarOnClickListener(this);

		tv_indicator = (TextView) view.findViewById(R.id.tv_indicator);
		tv_indicator.setOnClickListener(this);
		tv_time_operation = (TextView) view.findViewById(R.id.tv_time_operation);
		tv_time_operation.setOnClickListener(this);
		
		btn_time_operation = (Button) view.findViewById(R.id.btn_time_operation);
		btn_time_operation.setOnClickListener(this);
		
		adapter = new ApplyWaterDistrbutionGateAdapter(getActivity());
		
		list_apply_water_distrbution_gate_control = (HorizontalListView) view.findViewById(R.id.list_apply_water_distrbution_gate_control);
		layout = (RelativeLayout) view.findViewById(R.id.layout_relative_change);
		layout_linear_change = (LinearLayout) view.findViewById(R.id.layout_linear_change);
		
		layout_relative_changes = (RelativeLayout) view.findViewById(R.id.layout_relative_changess);
		layout_relative_changes_left = (RelativeLayout) view.findViewById(R.id.layout_relative_changes_lefts);
		layout_relative_changes_right = (RelativeLayout) view.findViewById(R.id.layout_relative_changes_rights);
		
		layout_relative_changes_one = (RelativeLayout) view.findViewById(R.id.layout_relative_changes_ones);
		layout_relative_changes_two = (RelativeLayout) view.findViewById(R.id.layout_relative_changes_twos);
		layout_relative_changes_three = (RelativeLayout) view.findViewById(R.id.layout_relative_changes_threes);
		layout_linear_changes = (LinearLayout) view.findViewById(R.id.layout_linear_changes);
		
		tv_superior = (TextView) view.findViewById(R.id.tv_apply_water_distrbution_gate_control_superior);
		
		layout_relative_change = (RelativeLayout) view.findViewById(R.id.layout_relative_changes);
		layout_relative_change_left = (RelativeLayout) view.findViewById(R.id.layout_relative_changes_left);
		layout_relative_change_right = (RelativeLayout) view.findViewById(R.id.layout_relative_changes_right);
		layout_relative_changes_ones = (RelativeLayout) view.findViewById(R.id.layout_relative_changes_one);
		layout_relative_changes_twos = (RelativeLayout) view.findViewById(R.id.layout_relative_changes_two);
		layout_relative_changes_threes = (RelativeLayout) view.findViewById(R.id.layout_relative_changes_three);
		
		layout_relative_change.setOnClickListener(this);
		layout_relative_change_left.setOnClickListener(this);
		layout_relative_change_right.setOnClickListener(this);
		layout_relative_changes_ones.setOnClickListener(this);
		layout_relative_changes_twos.setOnClickListener(this);
		layout_relative_changes_threes.setOnClickListener(this);
		
		imageLeft = (ImageView) view.findViewById(R.id.image_show_left);
		imageRight = (ImageView) view.findViewById(R.id.image_show_right);
		//获取屏幕高宽
		WindowManager wm = (WindowManager) getActivity()
                .getSystemService(Context.WINDOW_SERVICE);

		int width = wm.getDefaultDisplay().getWidth()/3;
		 
		SharedUtils.setParam(getActivity(), "width", width);
		
		imageLeft.setVisibility(View.GONE);
		list_apply_water_distrbution_gate_control.setOnItemClickListener(this);
		list_apply_water_distrbution_gate_control.setOnScrollStateChangedListener(new OnScrollStateChangedListener() {
			
			@Override
			public void onScrollStateChanged(ScrollState scrollState) {
				 switch (scrollState) {
	                // 当不滚动时
	                case SCROLL_STATE_IDLE:// 是当屏幕停止滚动时
	                    // 判断滚动到底部
	                    if (list_apply_water_distrbution_gate_control.getLastVisiblePosition() == (list_apply_water_distrbution_gate_control
	                            .getCount() )) {
	                    	imageRight.setVisibility(View.VISIBLE);
	                    }
	                    // 判断滚动到顶部
	                    if (list_apply_water_distrbution_gate_control.getFirstVisiblePosition() == 0) {
	                    	imageLeft.setVisibility(View.GONE);
	                    }
	                    scrollFlag = false;
	                    break;
	                case SCROLL_STATE_TOUCH_SCROLL:// 滚动时
	                	if (list_apply_water_distrbution_gate_control.getFirstVisiblePosition() > lastVisibleItemPosition) {// 上滑
							imageLeft.setVisibility(View.VISIBLE);
							imageRight.setVisibility(View.GONE);
							
						} else if (list_apply_water_distrbution_gate_control.getFirstVisiblePosition() < lastVisibleItemPosition) {// 下滑
							imageLeft.setVisibility(View.VISIBLE);
							imageRight.setVisibility(View.VISIBLE);
						} else {
							return;
						}
	                	lastVisibleItemPosition = list_apply_water_distrbution_gate_control.getFirstVisiblePosition();
	                    scrollFlag = true;
	                    break;
	                case SCROLL_STATE_FLING:// 是当用户由于之前划动屏幕并抬起手指，屏幕产生惯性滑动时
	                    scrollFlag = false;
	                    break;
				 }
			}
		});
		init();

		String str = "上级设备：	142团北干渠节制闸";
		SpannableStringBuilder style = new SpannableStringBuilder(str);
		// str代表要显示的全部字符串
		ClickableSpan what = new ClickableSpan() {

			@Override
			public void onClick(View widget) {
				Toast.makeText(getActivity(), "666666666666", Toast.LENGTH_LONG).show();
			}
		};
		style.setSpan(what, 6, 16, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		tv_superior.setText(style);
		tv_superior.setMovementMethod(LinkMovementMethod.getInstance());
		
		return view;
	}

	
//	OnScrollListener listener = new OnScrollListener() {
//		
//		@Override
//		public void onScrollStateChanged(AbsListView view, int scrollState) {
//			// TODO Auto-generated method stub
//			
//		}
//		
//		@Override
//		public void onScroll(AbsListView view, int firstVisibleItem,
//				int visibleItemCount, int totalItemCount) {
//			   if (scrollFlag
//                       && ScreenUtils.getScreenViewBottomHeight(list_apply_water_distrbution_gate_control) >= ScreenUtils
//                               .getScreenHeight(getActivity())) {
//                   if (firstVisibleItem > lastVisibleItemPosition) {// 上滑
//                	   imageLeft.setVisibility(View.GONE);
//                	   imageRight.setVisibility(View.VISIBLE);
//                   } else if (firstVisibleItem < lastVisibleItemPosition) {// 下滑
//                	   imageLeft.setVisibility(View.VISIBLE);
//                	   imageRight.setVisibility(View.GONE);
//                   } else {
//                       return;
//                   }
//                   lastVisibleItemPosition = firstVisibleItem;
//               }
//		}
//	};
	
	private void init() {
		beans = new ArrayList<ApplyWaterDistrbutionGateBean>();
		ApplyWaterDistrbutionGateBean bean;
			bean = new ApplyWaterDistrbutionGateBean();
			bean.setHigh(0.5+"m");
			bean.setAperture(30+"%");
			bean.setPercentage(1+"");
			beans.add(bean);
			bean = new ApplyWaterDistrbutionGateBean();
			bean.setHigh(0.6+"m");
			bean.setAperture(40+"%");
			bean.setPercentage(50+"");
			beans.add(bean);
//			bean = new ApplyWaterDistrbutionGateBean();
//			bean.setHigh(0.8+"m");
//			bean.setAperture(80+"%");
//			bean.setPercentage(30+"");
//			beans.add(bean);
//			bean = new ApplyWaterDistrbutionGateBean();
//			bean.setHigh(0.8+"m");
//			bean.setAperture(80+"%");
//			bean.setPercentage(30+"");
//			beans.add(bean);
//			bean = new ApplyWaterDistrbutionGateBean();
//			bean.setHigh(0.8+"m");
//			bean.setAperture(80+"%");
//			bean.setPercentage(30+"");
//			beans.add(bean);
//			bean = new ApplyWaterDistrbutionGateBean();
//			bean.setHigh(0.8+"m");
//			bean.setAperture(80+"%");
//			bean.setPercentage(30+"");
//			beans.add(bean);
//			
			adapter.addData(beans, false);
		
		
		if(beans.size() == 1){
			list_apply_water_distrbution_gate_control.setVisibility(View.GONE);
			layout_linear_change.setVisibility(View.GONE);
			layout_linear_changes.setVisibility(View.GONE);
			RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)layout_relative_changes.getLayoutParams();
			int height = DensityUtil.dip2px(getActivity(),(100-Integer.valueOf(beans.get(0).percentage))*2);
			layoutParams.height = height;
			layout_relative_changes.setLayoutParams(layoutParams);
			layout_relative_changes.requestLayout();
		}
		if(beans.size() == 2){
			list_apply_water_distrbution_gate_control.setVisibility(View.GONE);
			layout.setVisibility(View.GONE);
			layout_linear_changes.setVisibility(View.GONE);
			RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)layout_relative_changes_left.getLayoutParams();
			int height = DensityUtil.dip2px(getActivity(), (100-Integer.valueOf(beans.get(0).percentage))*2);
			layoutParams.height = height;
			layout_relative_changes_left.setLayoutParams(layoutParams);
			layout_relative_changes_left.requestLayout();
			RelativeLayout.LayoutParams layoutParam1 = (RelativeLayout.LayoutParams)layout_relative_changes_right.getLayoutParams();
			int heights = DensityUtil.dip2px(getActivity(), (100-Integer.valueOf(beans.get(1).percentage))*2);
			layoutParam1.height = heights;
			layout_relative_changes_right.setLayoutParams(layoutParam1);
			layout_relative_changes_right.requestLayout();
			}
		if(beans.size() == 3){
			list_apply_water_distrbution_gate_control.setVisibility(View.GONE);
			layout.setVisibility(View.GONE);
			layout_linear_change.setVisibility(View.GONE);
			RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)layout_relative_changes_one.getLayoutParams();
			int height = DensityUtil.dip2px(getActivity(),(100-Integer.valueOf(beans.get(0).percentage))*2);
			layoutParams.height = height;
			layout_relative_changes_one.setLayoutParams(layoutParams);
			layout_relative_changes_one.requestLayout();
			RelativeLayout.LayoutParams layoutParam1 = (RelativeLayout.LayoutParams)layout_relative_changes_two.getLayoutParams();
			int heights = DensityUtil.dip2px(getActivity(), (100-Integer.valueOf(beans.get(1).percentage))*2);
			layoutParam1.height = heights;
			layout_relative_changes_two.setLayoutParams(layoutParam1);
			layout_relative_changes_two.requestLayout();
			RelativeLayout.LayoutParams layoutParam2 = (RelativeLayout.LayoutParams)layout_relative_changes_three.getLayoutParams();
			int height2 = DensityUtil.dip2px(getActivity(), (100-Integer.valueOf(beans.get(2).percentage))*2);
			layoutParam2.height = height2;
			layout_relative_changes_three.setLayoutParams(layoutParam2);
			layout_relative_changes_three.requestLayout();
		}
		if(beans.size()> 3){
			layout.setVisibility(View.GONE);
			layout_linear_change.setVisibility(View.GONE);
			layout_linear_changes.setVisibility(View.GONE);
			list_apply_water_distrbution_gate_control.setAdapter(adapter);
		}
	}

	@Override
	public void onClick(View v) {
		FragmentManager fgManager = getFragmentManager();
		FragmentTransaction transaction = fgManager.beginTransaction();
		switch (v.getId()) {
		case R.id.acitionbar_left:
			ApplyWaterDistrbutionFragment fragment = new ApplyWaterDistrbutionFragment();
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			transaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.replace(R.id.fl, fragment, "main");
			transaction.commit();
			break;
		case R.id.tv_indicator:
			all = 1;
			SharedUtils.setParam(getActivity(), "all", all);
			AppayWaterGateHaploporeFragment fragment1 = new AppayWaterGateHaploporeFragment();
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			transaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.replace(R.id.fl, fragment1, "main");
			transaction.commit();
			break;
		case R.id.btn_time_operation:
			AppayWaterGateLinkageFragment fragment2 = new AppayWaterGateLinkageFragment();
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			transaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.replace(R.id.fl, fragment2, "main");
			transaction.commit();
			break;
		case R.id.tv_time_operation:
			showDateTimePicker(mInflater);
			break;
		case R.id.layout_relative_changes:
			all = 0;
			SharedUtils.setParam(getActivity(), "all", all);
			AppayWaterGateHaploporeFragment fragment8 = new AppayWaterGateHaploporeFragment();
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			transaction
			.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.replace(R.id.fl, fragment8, "main");
			transaction.commit();
			break;
		case R.id.layout_relative_changes_left:
			all = 0;
			SharedUtils.setParam(getActivity(), "all", all);
			AppayWaterGateHaploporeFragment fragment3 = new AppayWaterGateHaploporeFragment();
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			transaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.replace(R.id.fl, fragment3, "main");
			transaction.commit();
			break;
		case R.id.layout_relative_changes_right:
			all = 0;
			SharedUtils.setParam(getActivity(), "all", all);
			AppayWaterGateHaploporeFragment fragment4 = new AppayWaterGateHaploporeFragment();
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			transaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.replace(R.id.fl, fragment4, "main");
			transaction.commit();
			break;
		case R.id.layout_relative_changes_one:
			all = 0;
			SharedUtils.setParam(getActivity(), "all", all);
			AppayWaterGateHaploporeFragment fragment5 = new AppayWaterGateHaploporeFragment();
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			transaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.replace(R.id.fl, fragment5, "main");
			transaction.commit();
			break;
		case R.id.layout_relative_changes_two:
			all = 0;
			SharedUtils.setParam(getActivity(), "all", all);
			AppayWaterGateHaploporeFragment fragment6 = new AppayWaterGateHaploporeFragment();
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			transaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.replace(R.id.fl, fragment6, "main");
			transaction.commit();
			break;
		case R.id.layout_relative_changes_three:
			all = 0;
			SharedUtils.setParam(getActivity(), "all", all);
			AppayWaterGateHaploporeFragment fragment7 = new AppayWaterGateHaploporeFragment();
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			transaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.replace(R.id.fl, fragment7, "main");
			transaction.commit();
			break;
	}

}
	/**
	 * @Description: TODO 弹出日期时间选择器
	 */
	private void showDateTimePicker(LayoutInflater inflater) {
		mInflater = inflater;
		View view = inflater.inflate(R.layout.time_gate_control, null);

		dialog = new Dialog(getActivity());
		dialog.setTitle("请选择预约时间");
		Calendar c = Calendar.getInstance();
		int currentYear = c.get(Calendar.YEAR);
		int currentMonth = c.get(Calendar.MONTH);
		int currentDay = c.get(Calendar.DATE);
		int currentHour = c.get(Calendar.HOUR_OF_DAY);
		int currentMinute = c.get(Calendar.MINUTE);

		String[] months_big = { "1", "3", "5", "7", "8", "10", "12" };
		String[] months_little = { "4", "6", "9", "11" };

		final List<String> list_big = Arrays.asList(months_big);
		final List<String> list_little = Arrays.asList(months_little);

		year = (WheelView) view.findViewById(R.id.year_gate);
		year.setAdapter(new NumbericWheelAdapter(2016, 2036));
		year.setCyclic(true);
		year.setLabel("年");// 添加文字
		year.setCurrentItem(currentYear - 2016);

		month = (WheelView) view.findViewById(R.id.month_gate);
		month.setAdapter(new NumbericWheelAdapter(1, 12));
		month.setCyclic(true);
		month.setLabel("月");// 添加文字
		month.setCurrentItem(currentMonth);

		wv_hour = (WheelView) view.findViewById(R.id.hours_gate);
		wv_hour.setAdapter(new NumbericWheelAdapter(1, 12));
		wv_hour.setCyclic(true);
		wv_hour.setLabel("时");// 添加文字

		wv_minute = (WheelView) view.findViewById(R.id.minutes_gate);
		wv_minute.setAdapter(new NumbericWheelAdapter(0, 23));
		wv_minute.setCyclic(true);
		wv_minute.setLabel("分");

		wv_hour.setCurrentItem(currentHour);
		wv_minute.setCurrentItem(currentMinute);

		day = (WheelView) view.findViewById(R.id.day_gate);
		day.setCyclic(true);
		day.setLabel("日");
		// 判断大小月及是否闰年,用来确定"日"的数据
		if (list_big.contains(String.valueOf(currentMonth + 1))) {
			day.setAdapter(new NumbericWheelAdapter(1, 31));
		} else if (list_little.contains(String.valueOf(currentMonth + 1))) {
			day.setAdapter(new NumbericWheelAdapter(1, 30));
		} else {
			// 闰年
			if ((currentYear % 4 == 0 && currentYear % 100 != 0)
					|| currentYear % 400 == 0)
				day.setAdapter(new NumbericWheelAdapter(1, 29));
			else
				day.setAdapter(new NumbericWheelAdapter(1, 28));
		}
		day.setCurrentItem(currentDay - 1);
		OnWheelChangedListener wheelListener_year = new OnWheelChangedListener() {
			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				int year_num = newValue + 2016;
				// 判断大小月及是否闰年,用来确定"日"的数据
				if (list_big
						.contains(String.valueOf(month.getCurrentItem() + 1))) {
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
		// 找到dialog的布局文件

		Button btn_sure = (Button) view.findViewById(R.id.time_sure_gate);
		Button btn_cancel = (Button) view.findViewById(R.id.time_canle_gate);
		btn_sure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String parten = "00";
				DecimalFormat decimal = new DecimalFormat(parten);
				Calendar c = Calendar.getInstance();
				int nowYear = c.get(Calendar.YEAR);
				int nowMonth = c.get(Calendar.MONTH) + 1;
				int nowDay = c.get(Calendar.DAY_OF_MONTH);
				int nowHours = c.get(Calendar.HOUR_OF_DAY);
				int nowMinutes = c.get(Calendar.MINUTE);

				int aYear = (year.getCurrentItem() + 2016);
				int aMonth = (month.getCurrentItem() + 1);
				int aDay = (day.getCurrentItem() + 1);
				int aHours = (wv_hour.getCurrentItem());
				int aMinutes = (wv_minute.getCurrentItem());
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
							} else if (aHours == nowHours) {
								if (aMinutes > nowMinutes) {
									isBefore = 1;
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
				} else {
					isBefore = -1;
				}
				if (isBefore == 1) {
					tv_time_operation.setText("预约调闸   "+decimal.format(year.getCurrentItem() + 2016) + "-"
							+ decimal.format(month.getCurrentItem() + 1) + "-"
							+ decimal.format(day.getCurrentItem() + 1)+"	"+ decimal.format(wv_hour.getCurrentItem())
							+":"+decimal.format(wv_minute.getCurrentItem()));
					dialog.dismiss();
				}
				 else {
						new AlertDialog.Builder(getActivity())
								.setTitle("系统提示")
								// 设置对话框标题

								.setMessage("设定计划的开始时间不可小于当前时间 请重新设定！")
								// 设置显示的内容

								.setPositiveButton("确定",
										new DialogInterface.OnClickListener() {// 添加确定按钮

											@Override
											public void onClick(DialogInterface dialog,
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
				dialog.dismiss();
			}
		});
		// 设置dialog的布局,并显示
		dialog.setContentView(view);
		dialog.show();
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		FragmentManager fgManager = getFragmentManager();
		FragmentTransaction transaction = fgManager.beginTransaction();
		AppayWaterGateHaploporeFragment fragment1 = new AppayWaterGateHaploporeFragment();
		// transaction.setCustomAnimations(R.anim.right_in,
		// R.anim.right_out);
		transaction
				.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		transaction.replace(R.id.fl, fragment1, "main");
		transaction.commit();
	}
	}