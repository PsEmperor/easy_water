package ps.emperor.easy_water.fragment;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.AdapterView.OnItemClickListener;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.utils.CheckUtil;
import ps.emperor.easy_water.utils.SharedUtils;
import ps.emperor.easy_water.view.MainActionBar;

/**
 * 编辑界面（灌溉信息维护右上角进入）
 * @author 毛国江
 * @version 2016-8-23 下午14:36
 */

@SuppressLint("NewApi")
public class MainTainBasicCompileFragment extends Fragment implements
		OnClickListener, OnItemClickListener {

	private LayoutInflater mInflater;
	private MainActionBar actionBar;
	private Button button_marshalling, button_user_name,
	button_user_crop,button_basic_info;
	private int Skip;
	private String units;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflater = inflater;
		View view = inflater.inflate(R.layout.fragment_maintain_compile,
				container, false);

		actionBar = (MainActionBar) view
				.findViewById(R.id.actionbar_maintain_compile);
		actionBar.setLeftIcon(R.drawable.btn_back_selector);
		actionBar.setRightGone();
		actionBar.setTitle("编辑");
		actionBar.setActionBarOnClickListener(this);

		
		button_basic_info = (Button) view
				.findViewById(R.id.button_maintain_compile_basic_info);
		button_user_name = (Button) view
				.findViewById(R.id.button_maintain_compile_user_name);
		button_user_crop = (Button) view
				.findViewById(R.id.button_maintain_compile_user_farmarcrop);
		button_marshalling = (Button) view
				.findViewById(R.id.button_maintain_compile_reset);

		button_basic_info.setOnClickListener(this);
		button_user_crop.setOnClickListener(this);
		button_user_name.setOnClickListener(this);
		button_marshalling.setOnClickListener(this);
		units = (String) SharedUtils.getParam(getActivity(), "units", 1+"");
		return view;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) {
		FragmentManager fgManager = getFragmentManager();
		FragmentTransaction transaction = fgManager.beginTransaction();
		switch (v.getId()) {
		case R.id.acitionbar_left:
			MainTainIrrigateInfoFragment fragment = new MainTainIrrigateInfoFragment();
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			transaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.replace(R.id.fragment_maintain_present_irrigate, fragment, "main");
			transaction.commit();
			break;
		case R.id.button_maintain_compile_basic_info:// 基本信息
			Skip = 1;
			SharedUtils.setParam(getActivity(), "setLong", 0);
			SharedUtils.setParam(getActivity(), "setNight", 0);
			SharedUtils.setParam(getActivity(), "Skip", Skip);
			MainTainBasicInfoFragment fragment1 = new MainTainBasicInfoFragment();
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			Bundle bundle = new Bundle();
			bundle.putString("units", units);
			fragment1.setArguments(bundle);
			transaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.replace(R.id.fragment_maintain_present_irrigate,
					fragment1, "main");
			transaction.commit();
			break;
		case R.id.button_maintain_compile_user_name:// 种植户信息
			MainTainIrrigationUserInfoFragment fragment2 = new MainTainIrrigationUserInfoFragment();
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			transaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.replace(R.id.fragment_maintain_present_irrigate,
					fragment2, "main");
			transaction.commit();
			break;
		case R.id.button_maintain_compile_user_farmarcrop:// 种植作物信息
			MainTainIrrigationfarmarcropInfoFragment fragment3 = new MainTainIrrigationfarmarcropInfoFragment();
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			transaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.replace(R.id.fragment_maintain_present_irrigate,
					fragment3, "main");
			transaction.commit();
			break;
		case R.id.button_maintain_compile_reset: // 轮灌组编辑
			MainTainIrrigationInfoFragment fragment4 = new MainTainIrrigationInfoFragment();
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			transaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.replace(R.id.fragment_maintain_present_irrigate,
					fragment4, "main");
			transaction.commit();
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view,
			final int position, long id) {
	}

}
