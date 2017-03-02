package ps.emperor.easy_water.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.utils.SharedUtils;
import ps.emperor.easy_water.view.MainActionBar;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;
import android.view.View.OnClickListener;

/**
 * 设置
 * 
 * @author 毛国江
 * @version 2016-5-18 上午11:28
 */
@SuppressLint("NewApi")
public class MineSettingFragment extends Fragment implements OnClickListener,
		OnCheckedChangeListener {

	private LayoutInflater mInflater;
	private MainActionBar actionBar;
	private ToggleButton bt_msg, bt_wifi, bt_connection;
	private Boolean isMsg, isWifi, isConnection;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflater = inflater;
		View view = inflater.inflate(R.layout.fragment_mine_setting, container,
				false);

		init();

		actionBar = (MainActionBar) view
				.findViewById(R.id.actionbar_mine_setting);
		actionBar.setLeftIcon(R.drawable.btn_back_selector);
		actionBar.setRightGone();
		actionBar.setTitle("设置");
		actionBar.setActionBarOnClickListener(this);

		bt_msg = (ToggleButton) view.findViewById(R.id.toggle_setting_new_msg);
		bt_wifi = (ToggleButton) view.findViewById(R.id.toggle_setting_wifi);
		bt_connection = (ToggleButton) view
				.findViewById(R.id.toggle_setting_connection);

		bt_msg.setChecked(isMsg);
		bt_wifi.setChecked(isWifi);
		bt_connection.setChecked(isConnection);

		bt_msg.setOnCheckedChangeListener(this);
		bt_wifi.setOnCheckedChangeListener(this);
		bt_connection.setOnCheckedChangeListener(this);
		// if(!isMsg){
		// initToggle = false;
		// }
		return view;
	}

	private void init() {
		isMsg = (Boolean) SharedUtils.getParam(getActivity(), "msg", false);
		isWifi = (Boolean) SharedUtils.getParam(getActivity(), "wifi", false);
		isConnection = (Boolean) SharedUtils.getParam(getActivity(),
				"connection", false);
	}

	@Override
	public void onClick(View v) {
		FragmentManager fgManager = getFragmentManager();
		FragmentTransaction transaction = fgManager.beginTransaction();
		switch (v.getId()) {
		case R.id.acitionbar_left:
			MinesFragment fragment = new MinesFragment();
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			transaction.setCustomAnimations(
					R.anim.slide_fragment_horizontal_right_in,
					R.anim.slide_fragment_horizontal_left_out);
			transaction.replace(R.id.fl, fragment, "main");
			transaction.commit();
			break;
		case R.id.tv_setting_logo:
			Toast.makeText(getActivity(), "33333333", Toast.LENGTH_SHORT)
					.show();
			break;
		default:
			break;
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch (buttonView.getId()) {
		case R.id.toggle_setting_new_msg:// 规律
			// Toast.makeText(getActivity(), "开关--" + isChecked,
			// Toast.LENGTH_SHORT).show();
			SharedUtils.setParam(getActivity(), "msg", isChecked);
			break;
		case R.id.toggle_setting_wifi:// 规律
			// Toast.makeText(getActivity(), "开关--" + isChecked,
			// Toast.LENGTH_SHORT).show();
			SharedUtils.setParam(getActivity(), "wifi", isChecked);
			break;
		case R.id.toggle_setting_connection:// 规律
			// Toast.makeText(getActivity(), "开关--" + isChecked,
			// Toast.LENGTH_SHORT).show();
			SharedUtils.setParam(getActivity(), "connection", isChecked);
			break;
		}
	}
}
