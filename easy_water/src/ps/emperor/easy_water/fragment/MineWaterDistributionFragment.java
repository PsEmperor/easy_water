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
import android.widget.ListView;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.adapter.IrrigationEquipmentAdapter;
import ps.emperor.easy_water.entity.IrrigationEquipmentBean;
import ps.emperor.easy_water.view.MainActionBar;

/**
 * 我的配水设备
 * 
 * @author 毛国江
 * @version 2016-5-17 下午16:03
 */
@SuppressLint("NewApi")
public class MineWaterDistributionFragment extends Fragment implements
		OnClickListener {

	private LayoutInflater mInflater;
	private MainActionBar actionBar;
	private ListView listView;
	private IrrigationEquipmentAdapter adapter;
	private List<IrrigationEquipmentBean> beans;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflater = inflater;
		View view = inflater.inflate(
				R.layout.fragment_mine_irrigation_equipment, container, false);

		actionBar = (MainActionBar) view
				.findViewById(R.id.actionbar_irrigation_equipment);
		actionBar.setLeftIcon(R.drawable.btn_back_selector);
		actionBar.setRightIcon(R.drawable.t018fdc957b1bbb25b8);
		actionBar.setTitle("我的配水设备");
		actionBar.setActionBarOnClickListener(this);

		listView = (ListView) view.findViewById(R.id.list_irrigation_equipment);
		adapter = new IrrigationEquipmentAdapter(getActivity());
		beans = new ArrayList<IrrigationEquipmentBean>();
		IrrigationEquipmentBean bean;
		for (int i = 0; i < 10; i++) {
			bean = new IrrigationEquipmentBean();
			bean.setIrrigation("141团7连4#地块2#灌溉单元");
			bean.setIsCheck(true);
			beans.add(bean);
		}
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
			MinesFragment fragment = new MinesFragment();
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			transaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.replace(R.id.fl, fragment, "main");
			transaction.commit();
			break;
		case R.id.acitionbar_right:
			MineWaterAddFragment fragment1 = new MineWaterAddFragment();
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			transaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.replace(R.id.fl, fragment1, "main");
			transaction.commit();
			break;
		default:
			break;
		}
	}

}
