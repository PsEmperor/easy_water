package ps.emperor.easy_water.fragment;


import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.adapter.MainTainPresentIrrigateListAdapter;
import ps.emperor.easy_water.entity.MainTainPresentIrrigateGridBean;
import ps.emperor.easy_water.entity.MainTainPresentIrrigateListBean;
import ps.emperor.easy_water.view.MainActionBar;
import android.widget.ListView;
import android.view.View.OnClickListener;

/**
 * 当前灌溉组
 * @author 毛国江
 * @version 2016-5-26 上午11:34
 */
@SuppressLint("NewApi")
public class MainTainPresentIrrigateFragment extends Fragment implements OnClickListener {

	private LayoutInflater mInflater;
	private MainActionBar actionBar;
	private ListView listView;
	
	private MainTainPresentIrrigateListAdapter adapter;
	private List<MainTainPresentIrrigateListBean> listbeans;
	private List<MainTainPresentIrrigateGridBean> gridbeans;
	
	@SuppressLint("CutPasteId")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mInflater = inflater;
		View view = inflater.inflate(R.layout.fragment_maintain_present_irriagte, container, false);

		actionBar = (MainActionBar) view.findViewById(R.id.actionbar_maintain_present_irrigate);
		actionBar.setLeftIcon(R.drawable.btn_back_selector);
		actionBar.setRightGone();
		actionBar.setTitle("当前灌溉组");
		actionBar.setActionBarOnClickListener(this);
		
		gridbeans = new ArrayList<MainTainPresentIrrigateGridBean>();
		MainTainPresentIrrigateGridBean bean1;
		for (int i = 0; i < 9; i++) {
			bean1 = new MainTainPresentIrrigateGridBean();
			bean1.setGroup("2-2");
			gridbeans.add(bean1);
		}
		
		listView = (ListView) view.findViewById(R.id.list_maintain_irrigat_info_group);
		adapter = new MainTainPresentIrrigateListAdapter(getActivity(),gridbeans);
		listbeans = new ArrayList<MainTainPresentIrrigateListBean>();
		MainTainPresentIrrigateListBean bean;
		for (int i = 0; i < 10; i++) {
			bean = new MainTainPresentIrrigateListBean();
			bean.setGroup("组A");
			listbeans.add(bean);
		}
		adapter.addData(listbeans, false);
		listView.setAdapter(adapter);
		listbeans = adapter.getData();
		return view;
		
	}


	@Override
	public void onClick(View v) {
		FragmentManager fgManager = getFragmentManager();
		FragmentTransaction transaction = fgManager.beginTransaction();
		switch (v.getId()) {
		case R.id.acitionbar_left:
			MainTainIrrigationInfoFragment fragment = new MainTainIrrigationInfoFragment();
//			transaction.setCustomAnimations(R.anim.right_in, R.anim.right_out);
			transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.replace(R.id.fragment_maintain_present_irrigate, fragment, "main");
			transaction.commit();
			break;
		default:
			break;
		}
	}




}
