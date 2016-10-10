package ps.emperor.easy_water.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.adapter.ApplyIrrigateSingleValveAdapter;
import ps.emperor.easy_water.adapter.ImageAdapters;
import ps.emperor.easy_water.entity.ApplyIrrigateSingleValveBean;
import ps.emperor.easy_water.entity.MainTainIrrigationInfoBean;
import ps.emperor.easy_water.view.MainActionBar;
import ps.emperor.easy_water.view.MyGridView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.view.View.OnClickListener;

/**
 * 全局浏览
 * 
 * @author 毛国江
 * @version 2016-8-30 下午15:45
 */
@SuppressLint("NewApi")
public class ApplyIrrigatePanoramicFragment extends Fragment implements
		OnClickListener {

	private LayoutInflater mInflater;
	private MainActionBar actionBar;
	private MyGridView gridView;
	private ImageAdapters adapter;
	private Vector<ApplyIrrigateSingleValveBean> beans;
	private ApplyIrrigateSingleValveAdapter adapter2;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflater = inflater;
		View view = inflater
				.inflate(R.layout.fragment_maintain_panoramic,
						container, false);
		actionBar = (MainActionBar) view
				.findViewById(R.id.actionbar_main_panoramic);
		actionBar.setLeftIcon(R.drawable.btn_back_selector);
		actionBar.setRightGone();
		actionBar.setTitle("全局浏览");
		actionBar.setActionBarOnClickListener(this);

		gridView = (MyGridView) view
				.findViewById(R.id.grid_maintain_panoramic);
		beans = new Vector<ApplyIrrigateSingleValveBean>();
		ApplyIrrigateSingleValveBean bean;
		for (int i = 0; i < 10; i++) {
			bean = new ApplyIrrigateSingleValveBean();
			bean.setValve("A");
			bean.setNames("张三");
			bean.setCrops("玉米");
			beans.add(bean);
		}
		adapter = new ImageAdapters(getActivity(), beans);
		adapter2 = new ApplyIrrigateSingleValveAdapter(getActivity());
		adapter2.addData(beans, true);
		gridView.setAdapter(adapter2);
		gridView.setVerticalSpacing(5);
		gridView.setPadding(10, 10, 5, 10);
		return view;

	}

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
		default:
			break;
		}
	}


}
