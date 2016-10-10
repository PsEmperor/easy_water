package ps.emperor.easy_water.fragment;

import java.util.ArrayList;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.adapter.MainTainIntoCropsAdapter;
import ps.emperor.easy_water.view.MainActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

/**
 * 录入种植戸信息
 * @author 毛国江
 * @version 2016-6-20 下午14:21
 */

public class MainTainIntoUserFragment extends Fragment implements
OnClickListener {
	private LayoutInflater mInflater;
	private MainActionBar actionBar;
	private ArrayList<String> integers = new ArrayList<String>();
	private GridView gridView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflater = inflater;
		View view = inflater.inflate(R.layout.fragment_maintain_into_user_info,
				container, false);
		actionBar = (MainActionBar) view
				.findViewById(R.id.actionbar_maintain_into_user);
		actionBar.setLeftIcon(R.drawable.btn_back_selector);
		actionBar.setRightIcon(R.drawable.ic_launcher);
		actionBar.setTitle("录入种植户信息");
		actionBar.setActionBarOnClickListener(this);
		integers = getArguments().getStringArrayList("info");
		gridView = (GridView) view.findViewById(R.id.grid__maintain_into_user);
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>( 
		         getActivity(), android.R.layout.simple_list_item_1, 
		         integers); 
		 
		        /* 设置ListView的Adapter */ 
				gridView.setAdapter(new ArrayAdapter<String>(getActivity(), 
		                android.R.layout.simple_list_item_1, integers)); 
				return view;
	}
	
	@Override
	public void onClick(View v) {
		FragmentManager fgManager = getFragmentManager();
		FragmentTransaction transaction = fgManager.beginTransaction();
		switch (v.getId()) {
		case R.id.acitionbar_left:
			MainTainIrrigationUserInfoFragment fragment = new MainTainIrrigationUserInfoFragment();
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			transaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.replace(R.id.fragment_maintain_present_irrigate,
					fragment, "main");
			transaction.commit();
			break;
		case R.id.acitionbar_right:
			
			break;
		default:
			break;
		}
	}
}
