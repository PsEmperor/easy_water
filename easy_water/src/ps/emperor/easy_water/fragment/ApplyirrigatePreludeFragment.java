package ps.emperor.easy_water.fragment;

import ps.emperor.easy_water.R;
import ps.emperor.easy_water.utils.SharedUtils;
import ps.emperor.easy_water.view.MainActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;

/**
 * 首部
 * 
 * @author lenovo-pc
 * @version 2016-7-14 上午9：36
 */
public class ApplyirrigatePreludeFragment extends Fragment implements
		OnClickListener, OnCheckedChangeListener {
	private LayoutInflater mInflater;
	private MainActionBar actionBar;
	private ToggleButton bt_water_pump, bt_filter, bt_fertilizer_drill; //水泵、反过滤冲洗器、施肥机
	private Boolean isWaterPump, isFilter, isFertilizer_drill;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflater = inflater;
		View view = inflater.inflate(R.layout.fragment_prelude, container,
				false);


		actionBar = (MainActionBar) view
				.findViewById(R.id.actionbar_prelude);
		actionBar.setLeftIcon(R.drawable.btn_back_selector);
		actionBar.setRightGone();
		actionBar.setTitle("设置");
		actionBar.setActionBarOnClickListener(this);

		bt_water_pump = (ToggleButton) view.findViewById(R.id.toggle_prelude_water_pump);
		bt_filter = (ToggleButton) view.findViewById(R.id.Toggle_prelude_filter);
		bt_fertilizer_drill = (ToggleButton) view
				.findViewById(R.id.Toggle_prelude_fertilizer_drill);
		init();
		bt_water_pump.setChecked(isWaterPump);
		bt_filter.setChecked(isFilter);
		bt_fertilizer_drill.setChecked(isFertilizer_drill);

		bt_water_pump.setOnCheckedChangeListener(this);
		bt_filter.setOnCheckedChangeListener(this);
		bt_fertilizer_drill.setOnCheckedChangeListener(this);
		// if(!isMsg){
		// initToggle = false;
		// }
		return view;
	}
	private void init() {
		isWaterPump = (Boolean) SharedUtils.getParam(getActivity(), "water_pump", false);
		isFilter = (Boolean) SharedUtils.getParam(getActivity(), "filter", false);
		isFertilizer_drill = (Boolean) SharedUtils.getParam(getActivity(), "fertilizer_drill", false);
	}
	@Override
	public void onClick(View v) {
		FragmentManager fgManager = getFragmentManager();
		FragmentTransaction transaction = fgManager.beginTransaction();
		switch (v.getId()) {
		case R.id.acitionbar_left:
			ApplyIrrigateUnitControlFragment fragment = new ApplyIrrigateUnitControlFragment();
//			transaction.setCustomAnimations(R.anim.right_in, R.anim.right_out);
			transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.replace(R.id.fl, fragment, "main");
			transaction.commit();
			break;

		default:
			break;
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch (buttonView.getId()) {
		case R.id.toggle_prelude_water_pump:// 规律
			Toast.makeText(getActivity(), "开关--" + isChecked, Toast.LENGTH_SHORT).show();
			SharedUtils.setParam(getActivity(), "water_pump", isChecked);
			break;
		case R.id.Toggle_prelude_filter:// 规律
			Toast.makeText(getActivity(), "开关--" + isChecked, Toast.LENGTH_SHORT).show();
			SharedUtils.setParam(getActivity(), "filter", isChecked);
			break;
		case R.id.Toggle_prelude_fertilizer_drill:// 规律
			Toast.makeText(getActivity(), "开关--" + isChecked, Toast.LENGTH_SHORT).show();
			SharedUtils.setParam(getActivity(), "fertilizer_drill", isChecked);
			break;
		}
	}		
}
