package ps.emperor.easy_water.fragment;

import java.util.ArrayList;
import java.util.List;

import ps.emperor.easy_water.R;
import ps.emperor.easy_water.adapter.ApplyWaterDistrbutionAdapter;
import ps.emperor.easy_water.application.ApplicationFragment;
import ps.emperor.easy_water.entity.ApplyWaterDistrbutionBean;
import ps.emperor.easy_water.entity.ApplyWaterDistrbutionBean;
import ps.emperor.easy_water.view.MainActionBar;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * 配水（应用）
 * 
 * @author 毛国江
 * @version 2016-5-26 下午14:11
 */

@SuppressLint("NewApi")
public class ApplyWaterDistrbutionFragment extends Fragment implements
		OnClickListener, OnItemClickListener {

	private LayoutInflater mInflater;
	private MainActionBar actionBar;
	private ListView listView;
	private ApplyWaterDistrbutionAdapter adapter;
	private List<ApplyWaterDistrbutionBean> beans;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflater = inflater;
		View view = inflater.inflate(R.layout.fragment_apply_irrigate,
				container, false);

		actionBar = (MainActionBar) view
				.findViewById(R.id.actionbar_apply_irrigate);
		actionBar.setLeftIcon(R.drawable.btn_back_selector);
		actionBar.setRightGone();
		actionBar.setTitle("配水");
		actionBar.setActionBarOnClickListener(this);

		listView = (ListView) view.findViewById(R.id.list_apply_irrigate_add);
		listView.setOnItemClickListener(this);
		adapter = new ApplyWaterDistrbutionAdapter(getActivity());
		beans = new ArrayList<ApplyWaterDistrbutionBean>();
		ApplyWaterDistrbutionBean bean;
		bean = new ApplyWaterDistrbutionBean();
		bean.setUnits("141团5连支渠进水闸");
		bean.setWhether("正在供水");
		beans.add(bean);
		bean = new ApplyWaterDistrbutionBean();
		bean.setUnits("141团5连支渠一斗渠进水闸");
		bean.setWhether("正在供水");
		beans.add(bean);
		bean = new ApplyWaterDistrbutionBean();
		bean.setUnits("141团5连支渠二斗渠进水闸");
		bean.setWhether("正在供水");
		beans.add(bean);
		bean = new ApplyWaterDistrbutionBean();
		bean.setUnits("141团6连支渠进水闸");
		bean.setWhether("正在供水");
		beans.add(bean);
		bean = new ApplyWaterDistrbutionBean();
		bean.setUnits("141团5连干渠进水闸");
		bean.setWhether("正在供水");
		beans.add(bean);
		adapter.addData(beans, false);
		listView.setAdapter(adapter);
		beans = adapter.getData();

		return view;
	}

	@Override
	public void onClick(View v) {
		FragmentManager fgManager = getFragmentManager();
		FragmentTransaction transaction = fgManager.beginTransaction();
		switch (v.getId()) {
		case R.id.acitionbar_left:
			ApplicationFragment fragment = new ApplicationFragment();
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			transaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.replace(R.id.fl, fragment, "main");
			transaction.commit();
			break;

		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view,
			final int position, long id) {
		FragmentManager fgManager = getFragmentManager();
		FragmentTransaction transaction = fgManager.beginTransaction();
		ApplyWaterDistrbutionGate fragment = new ApplyWaterDistrbutionGate();
		Bundle bundle = new Bundle();
		bundle.putString("units", beans.get(position).getUnits());
		fragment.setArguments(bundle);
		// transaction.setCustomAnimations(R.anim.right_in, R.anim.right_out);
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		transaction.replace(R.id.fl, fragment, "main");
		transaction.commit();
	}

}
