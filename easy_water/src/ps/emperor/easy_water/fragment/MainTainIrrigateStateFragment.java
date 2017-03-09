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
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.adapter.ApplyIrrigateControlAdapter;
import ps.emperor.easy_water.entity.ApplyIrrigateControlBean;
import ps.emperor.easy_water.view.MainActionBar;

/**
 * 灌溉组控制
 * 
 * @author 毛国江
 * @version 2016-5-24 下午15:00
 */

@SuppressLint("NewApi")
public class MainTainIrrigateStateFragment extends Fragment implements
		OnClickListener, OnItemClickListener {

	private LayoutInflater mInflater;
	private MainActionBar actionBar;
	private GridView gridView;
	private ApplyIrrigateControlAdapter adapter;
	private List<ApplyIrrigateControlBean> beans;
	private ImageView irrigatr_control;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflater = inflater;
		View view = inflater.inflate(R.layout.fragment_apply_irrigate_control,
				container, false);

		actionBar = (MainActionBar) view
				.findViewById(R.id.actionbar_apply_irrigate_control);
		actionBar.setLeftIcon(R.drawable.btn_back_selector);
		actionBar.setRightIcon(R.drawable.ic_launcher);
		actionBar.setTitle("灌溉组控制");
		actionBar.setActionBarOnClickListener(this);

		irrigatr_control = (ImageView) view
				.findViewById(R.id.image_apply_irrigate_control);
		irrigatr_control.setOnClickListener(this);

		gridView = (GridView) view
				.findViewById(R.id.grid_apply_irrigate_control_fm);
		gridView.setOnItemClickListener(this);
		adapter = new ApplyIrrigateControlAdapter(getActivity());
		beans = new ArrayList<ApplyIrrigateControlBean>();
		ApplyIrrigateControlBean bean;
		for (int i = 0; i < 18; i++) {
			bean = new ApplyIrrigateControlBean();
			bean.setValve("A");
			beans.add(bean);
		}
		adapter.addData(beans, false);
		gridView.setAdapter(adapter);
		beans = adapter.getData();
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
			transaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.replace(R.id.fl, fragment, "main");
			transaction.commit();
			break;
		case R.id.acitionbar_right:
			ApplyIrrigateSingleValveFragment fragment1 = new ApplyIrrigateSingleValveFragment();
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			transaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.replace(R.id.fl, fragment1, "main");
			transaction.commit();
			break;
		case R.id.image_apply_irrigate_control:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view,
			final int position, long id) {
		FragmentManager fgManager = getFragmentManager();
		FragmentTransaction transaction = fgManager.beginTransaction();
		ApplyIrrigateControlValveFragment fragment = new ApplyIrrigateControlValveFragment();
		// transaction.setCustomAnimations(R.anim.right_in, R.anim.right_out);
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		transaction.replace(R.id.fl, fragment, "main");
		transaction.commit();
	}

}
