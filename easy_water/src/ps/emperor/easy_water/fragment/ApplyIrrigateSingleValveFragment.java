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
import ps.emperor.easy_water.greendao.DBHelper;
import ps.emperor.easy_water.greendao.SingleValue;
import ps.emperor.easy_water.view.MainActionBar;
import ps.emperor.easy_water.view.MainActionBars;
import ps.emperor.easy_water.view.MyGridView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.view.View.OnClickListener;

/**
 * 单阀显示
 * 
 * @author 毛国江
 * @version 2016-5-26 上午11:34
 */
@SuppressLint("NewApi")
public class ApplyIrrigateSingleValveFragment extends Fragment implements
		OnClickListener, OnItemClickListener {

	private LayoutInflater mInflater;
	private MainActionBars actionBar;
	private MyGridView gridView;
	private Vector<ApplyIrrigateSingleValveBean> beans;
	private ApplyIrrigateSingleValveAdapter adapter2;
	private String units;
//	private DBHelper dbHelper;
//	private SingleValue singleValue;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflater = inflater;
		View view = inflater
				.inflate(R.layout.fragment_apply_irrigate_single_valve,
						container, false);
		actionBar = (MainActionBars) view
				.findViewById(R.id.actionbar_apply_irrigat_single_valve);
		actionBar.setLeftIcon(R.drawable.btn_back_selector);
		actionBar.setRightText("切换");	
		actionBar.setTitle("单阀显示");
		actionBar.setActionBarOnClickListener(this);

		units = getArguments().getString("units");
//		dbHelper = DBHelper.getInstance(getActivity()); // 得到DBHelper对象
		gridView = (MyGridView) view
				.findViewById(R.id.grid_maintain_irrigate_infos_single);
		gridView.setOnItemClickListener(this);
		beans = new Vector<ApplyIrrigateSingleValveBean>();
		ApplyIrrigateSingleValveBean bean;
		bean = new ApplyIrrigateSingleValveBean();
		bean.setValve("1-1");
		bean.setNames("王");
		bean.setCrops("土");
		beans.add(bean);
		bean = new ApplyIrrigateSingleValveBean();
		bean.setValve("1-2");
		bean.setNames("张");
		bean.setCrops("玉");
		beans.add(bean);
		bean = new ApplyIrrigateSingleValveBean();
		bean.setValve("1-3");
		bean.setNames("李");
		bean.setCrops("苹");
		beans.add(bean);
		bean = new ApplyIrrigateSingleValveBean();
		bean.setValve("1-4");
		bean.setNames("王");
		bean.setCrops("土");
		beans.add(bean);
		bean = new ApplyIrrigateSingleValveBean();
		bean.setValve("1-5");
		bean.setNames("王");
		bean.setCrops("土");
		beans.add(bean);
		adapter2 = new ApplyIrrigateSingleValveAdapter(getActivity());
		adapter2.addData(beans, true);
		gridView.setAdapter(adapter2);
		gridView.setVerticalSpacing(5);
		gridView.setPadding(10, 10, 5, 10);
//		for (int i = 0; i < beans.size(); i++) {
//			singleValue = new SingleValue();
//			singleValue.setValueID(beans.get(i).getValve());
//			dbHelper.saveValue(singleValue);
//		}
		return view;

	}

	@Override
	public void onClick(View v) {
		FragmentManager fgManager = getFragmentManager();
		FragmentTransaction transaction = fgManager.beginTransaction();
		switch (v.getId()) {
		case R.id.acitionbar_left:
			ApplyIrrigateUnitControlFragment fragment = new ApplyIrrigateUnitControlFragment();
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
			ApplyIrrigateControlFragment fragment1 = new ApplyIrrigateControlFragment();
			Bundle bundle1 = new Bundle();
			bundle1.putString("units", units);
			fragment1.setArguments(bundle1);
			transaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.replace(R.id.fl, fragment1, "main");
			transaction.commit();
			break;
		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		FragmentManager fgManager = getFragmentManager();
		FragmentTransaction transaction = fgManager.beginTransaction();
		ApplyIrrigateControlValveFragment fragment2 = new ApplyIrrigateControlValveFragment();
		Bundle bundle = new Bundle();
		bundle.putString("units", units);
		fragment2.setArguments(bundle);
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		transaction.replace(R.id.fl, fragment2, "main");
		transaction.commit();
	}

}
