package ps.emperor.easy_water.fragment;


import android.annotation.SuppressLint;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.utils.SharedUtils;
import ps.emperor.easy_water.view.MainActionBar;
import android.view.View.OnClickListener;

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
	private MainActionBar actionBar;
	private int group, value;
	private TextView text_max_irrigat_group, text_max_orroagte_valve,
			text_filter, text_max_orroagte_restnight_start,
			text_max_orroagte_restnight_end, text_max_orroagte_season_start,
			text_max_orroagte_season_end;
	private Button btn_maintain_panoramic;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflater = inflater;
		View view = inflater.inflate(R.layout.fragment_maintain_irrigate_info,
				container, false);

		actionBar = (MainActionBar) view
				.findViewById(R.id.actionbar_maintain_irrigate_info);
		actionBar.setLeftIcon(R.drawable.btn_back_selector);
		actionBar.setRightIcon(R.drawable.ic_launcher);
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

		group = (Integer) SharedUtils.getParam(getActivity(), "groups", 0);
		value = (Integer) SharedUtils.getParam(getActivity(), "values", 0);
		text_max_irrigat_group.setText(group+"");
		text_max_orroagte_valve.setText(value+"");
		
		btn_maintain_panoramic = (Button) view.findViewById(R.id.btn_maintain_panoramic);
		btn_maintain_panoramic.setOnClickListener(this);
		
		return view;
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
//	<ps.emperor.easy_water.utils.BottomNavigation
//    android:layout_weight="0"
//        android:layout_width="match_parent"
//        android:layout_height="wrap_content" >
//    </ps.emperor.easy_water.utils.BottomNavigation>
}
