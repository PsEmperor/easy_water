package ps.emperor.easy_water.fragment;

import java.text.DecimalFormat;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.adapter.NumericWheelAdapter;
import ps.emperor.easy_water.utils.SharedUtils;
import ps.emperor.easy_water.view.MainActionBar;
import ps.emperor.easy_water.view.MainActionBars;
import ps.emperor.easy_water.view.WheelView;

/**
 * 灌溉阀门控制
 * 
 * @author 毛国江
 * @version 2016-5-31 下午15:24
 */

@SuppressLint("NewApi")
public class ApplyIrrigateControlValveFragment extends Fragment implements
		OnClickListener {

	private LayoutInflater mInflater;
	private MainActionBars actionBar;
	private ImageView isOpen;
	private Dialog dialog;
	private String hour, minute;
	private RelativeLayout valve_control;
	private TextView text_apply_irriagte_valve_control;
	private String units;
	private int isOpens;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflater = inflater;
		View view = inflater.inflate(
				R.layout.fragment_apply_irrigate_valve_control, container,
				false);

		actionBar = (MainActionBars) view
				.findViewById(R.id.actionbar_apply_irrigat_valve_control);
		actionBar.setLeftIcon(R.drawable.btn_back_selector);
		actionBar.setRightText("保存");
		actionBar.setTitle("灌溉阀门控制");
		actionBar.setActionBarOnClickListener(this);
		units = getArguments().getString("units");
		// 灌水延续时间
		valve_control = (RelativeLayout) view
				.findViewById(R.id.layout_apply_irriagte_valve_control);
		valve_control.setOnClickListener(this);

		init();
		// 灌水延续时间显示
		text_apply_irriagte_valve_control = (TextView) view
				.findViewById(R.id.text_apply_irriagte_valve_control);
		text_apply_irriagte_valve_control.setText(hour + "时" + minute + "分");

		isOpen = (ImageView) view
				.findViewById(R.id.image_irriagte_valve_control_isopen);
		isOpen.setOnClickListener(this);
		return view;
	}


	private void init() {
		hour = (String) SharedUtils.getParam(getActivity(), "hour_control",
				"00");
		minute = (String) SharedUtils.getParam(getActivity(), "minute_control",
				"00");
	}

	@Override
	public void onClick(View v) {
		FragmentManager fgManager = getFragmentManager();
		FragmentTransaction transaction = fgManager.beginTransaction();
		switch (v.getId()) {
		case R.id.acitionbar_left:
			ApplyIrrigateSingleValveFragment fragment = new ApplyIrrigateSingleValveFragment();
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			Bundle bundle = new Bundle();
			bundle.putString("units", units);
			fragment.setArguments(bundle);
			transaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.replace(R.id.fl, fragment, "main");
			transaction.commit();
			break;
		case R.id.acitionbar_right:
			Toast.makeText(getActivity(), "保存", Toast.LENGTH_SHORT).show();
			break;
		case R.id.image_irriagte_valve_control_isopen:
			if (isOpens == 0) {
				isOpens = 1;
			} else {
				isOpens = 0;
			}
			if(isOpens == 0){
				isOpen.setImageResource(R.drawable.off);
			}else{
				isOpen.setImageResource(R.drawable.on);
			}
			break;
		case R.id.layout_apply_irriagte_valve_control:
			showDateTimePicker(mInflater);
			break;
		}
	}

	/**
	 * @Description: TODO 弹出日期时间选择器
	 */
	private void showDateTimePicker(LayoutInflater inflater) {
		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);

		dialog = new Dialog(getActivity());
		dialog.setTitle("请选择时间");
		// 找到dialog的布局文件
		mInflater = inflater;
		View view = inflater.inflate(R.layout.time_filter, null);

		// 时
		final WheelView wv_hours = (WheelView) view
				.findViewById(R.id.hour_filter);
		wv_hours.setAdapter(new NumericWheelAdapter(0, 23));
		wv_hours.setCyclic(true);
		wv_hours.setLabel("时");// 添加文字
		wv_hours.setCurrentItem(hour);

		// 分
		final WheelView wv_minute = (WheelView) view
				.findViewById(R.id.minute_filter);
		wv_minute.setAdapter(new NumericWheelAdapter(0, 59));
		wv_minute.setCyclic(true);
		wv_minute.setLabel("分");// 添加文字
		wv_minute.setCurrentItem(minute);

		// // 秒
		// final WheelView wv_second = (WheelView)
		// view.findViewById(R.id.second);
		// wv_second.setAdapter(new NumericWheelAdapter(0, 59, "%02d"));
		// wv_second.setCyclic(true);
		// wv_second.setCurrentItem(second);
		// wv_second.setLabel("秒");// 添加文字
		// 根据屏幕密度来指定选择器字体的大小
		// int textSize = 0;
		//
		// textSize = 18;
		//
		// wv_hours.TEXT_SIZE = textSize;
		// wv_minute.TEXT_SIZE = textSize;
		Button btn_sure = (Button) view.findViewById(R.id.time_sures_filter);
		Button btn_cancel = (Button) view.findViewById(R.id.time_canles_filter);
		// 确定
		btn_sure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// 如果是个数,则显示为"02"的样式
				String parten = "00";
				DecimalFormat decimal = new DecimalFormat(parten);
				text_apply_irriagte_valve_control.setText(decimal
						.format(wv_hours.getCurrentItem())
						+ "时"
						+ decimal.format(wv_minute.getCurrentItem()) + "分");
				SharedUtils.setParam(getActivity(), "hour_control",
						decimal.format(wv_hours.getCurrentItem()));
				SharedUtils.setParam(getActivity(), "minute_control",
						decimal.format(wv_minute.getCurrentItem()));
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
