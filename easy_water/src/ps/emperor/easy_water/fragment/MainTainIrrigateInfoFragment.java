package ps.emperor.easy_water.fragment;


import java.text.DecimalFormat;
import java.util.List;

import android.annotation.SuppressLint;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.greendao.DBHelper;
import ps.emperor.easy_water.greendao.Irrigation;
import ps.emperor.easy_water.utils.SharedUtils;
import ps.emperor.easy_water.view.MainActionBar;
import ps.emperor.easy_water.view.MainActionBars;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * 灌溉信息维护
 * 
 * @author 毛国江
 * @version 2016-6-3 上午10:08
 */
@SuppressLint("NewApi")
public class MainTainIrrigateInfoFragment extends Fragment implements
		OnClickListener {

	private LayoutInflater mInflater;
	private MainActionBars actionBar;
	private int group, value;
	private TextView text_max_irrigat_group, text_max_orroagte_valve,
			text_filter, text_max_orroagte_restnight_start,
			text_max_orroagte_restnight_end, text_max_orroagte_season_start,
			text_max_orroagte_season_end;
	private Button btn_maintain_panoramic;
	private List<Irrigation> irrigation;
	private DBHelper dbHelper;
	private String units;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflater = inflater;
		View view = inflater.inflate(R.layout.fragment_maintain_irrigate_info,
				container, false);

		actionBar = (MainActionBars) view
				.findViewById(R.id.actionbar_maintain_irrigate_info);
		actionBar.setLeftIcon(R.drawable.btn_back_selector);
		actionBar.setRightText("编辑");
		actionBar.setTitle("灌溉信息维护");
		actionBar.setActionBarOnClickListener(this);

		text_max_irrigat_group = (TextView) view
				.findViewById(R.id.text_maintain_basic_info_max_irrigat_group_examine);
		text_max_orroagte_valve = (TextView) view
				.findViewById(R.id.text_maintain_basic_info_max_orroagte_valve_examine);
		text_filter = (TextView) view
				.findViewById(R.id.text_maintain_basic_info_filter_examine);
		text_max_orroagte_restnight_start = (TextView) view
				.findViewById(R.id.text_maintain_basic_info_max_orroagte_restnight_start_examine);
		text_max_orroagte_restnight_end = (TextView) view
				.findViewById(R.id.text_maintain_basic_info_max_orroagte_restnight_end_examine);
		text_max_orroagte_season_start = (TextView) view
				.findViewById(R.id.text_maintain_basic_info_max_orroagte_season_start_examine);
		text_max_orroagte_season_end = (TextView) view
				.findViewById(R.id.text_maintain_basic_info_max_orroagte_season_end_examine);
		dbHelper = DBHelper.getInstance(getActivity()); // 得到DBHelper对象
		
		Intent intent = getActivity().getIntent();
		units = intent.getStringExtra("units");
		
		init();
		
		String parten = "00";
		DecimalFormat decimal = new DecimalFormat(parten);
		text_max_irrigat_group.setText(irrigation.get(0).getGroupnumber()+"");
		text_max_orroagte_valve.setText(irrigation.get(0).getValuenumber()+"");
		text_filter.setText(irrigation.get(0).getFilterHour()+"小时"+decimal.format(irrigation.get(0).getFilterMinute())+"分钟");
		text_max_orroagte_restnight_start.setText(irrigation.get(0).getIsNightStartHour() + ":"+decimal.format(irrigation.get(0).getIsNightStartMinute()));
		text_max_orroagte_restnight_end.setText(irrigation.get(0).getIsNightEndHour()+":"+decimal.format(irrigation.get(0).getIsNightContinueMinute()));
		
		btn_maintain_panoramic = (Button) view.findViewById(R.id.btn_maintain_panoramic);
		btn_maintain_panoramic.setOnClickListener(this);
		
		return view;
	}

	private void init() {
		irrigation = dbHelper.loadContinue(units);
	}

	@Override
	public void onClick(View v) {
		FragmentManager fgManager = getFragmentManager();
		FragmentTransaction transaction = fgManager.beginTransaction();
		switch (v.getId()) {
		case R.id.acitionbar_left:
			getActivity().finish();
			break;
		case R.id.acitionbar_right:
			MainTainBasicCompileFragment fragment = new MainTainBasicCompileFragment();
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			transaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.replace(R.id.fragment_maintain_present_irrigate,
					fragment, "main");
			transaction.commit();
			break;
		case R.id.btn_maintain_panoramic:
			ApplyIrrigatePanoramicFragment fragment1 = new ApplyIrrigatePanoramicFragment();
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			transaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.replace(R.id.fragment_maintain_present_irrigate,
					fragment1, "main");
			transaction.commit();
			break;
		default:
			break;
		}

	}
	 
}
