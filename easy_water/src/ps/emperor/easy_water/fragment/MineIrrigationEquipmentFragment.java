package ps.emperor.easy_water.fragment;

import java.util.ArrayList;


import java.util.List;

import ps.emperor.easy_water.R;
import ps.emperor.easy_water.adapter.IrrigationEquipmentAdapter;
import ps.emperor.easy_water.entity.IrrigationEquipmentBean;
import ps.emperor.easy_water.greendao.DBHelper;
import ps.emperor.easy_water.greendao.Irrigation;
import ps.emperor.easy_water.utils.SharedUtils;
import ps.emperor.easy_water.view.MainActionBar;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Toast;

/**
 * 我的灌溉设备
 * 
 * @author 毛国江
 * @version 2016-5-18 上午10:05
 */
@SuppressLint("NewApi")
public class MineIrrigationEquipmentFragment extends Fragment implements OnClickListener {

	private LayoutInflater mInflater;
	private MainActionBar actionBar;
	private ListView listView;
	private IrrigationEquipmentAdapter adapter;
	private List<IrrigationEquipmentBean> beans;
	private DBHelper dBManager;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mInflater = inflater;
		View view = inflater.inflate(R.layout.fragment_mine_irrigation_equipment, container, false);

		actionBar = (MainActionBar) view.findViewById(R.id.actionbar_irrigation_equipment);
		actionBar.setLeftIcon(R.drawable.btn_back_selector);
		actionBar.setRightIcon(R.drawable.t018fdc957b1bbb25b8);
		actionBar.setTitle("我的灌溉设备");
		actionBar.setActionBarOnClickListener(this);
		
		dBManager = DBHelper.getInstance(getActivity()); 
		List<Irrigation> listentity = dBManager.loadAllSession();  
		listView = (ListView) view.findViewById(R.id.list_irrigation_equipment);
		adapter = new IrrigationEquipmentAdapter(getActivity());
		beans = new ArrayList<IrrigationEquipmentBean>();
		IrrigationEquipmentBean bean;
		for (int i = 0; i < listentity.size(); i++) {
			bean = new IrrigationEquipmentBean();
			bean.setIrrigation(listentity.get(i).getIrrigation());
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
//			transaction.setCustomAnimations(R.anim.right_in, R.anim.right_out);
			transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.replace(R.id.fl, fragment, "main");
			transaction.commit();
			break;
		case R.id.acitionbar_right:
			MineIrrigationAddFragment fragment1 = new MineIrrigationAddFragment();
//			transaction.setCustomAnimations(R.anim.right_in, R.anim.right_out);
			transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.replace(R.id.fl, fragment1, "main");
			transaction.commit();
			break;
		default:
			break;
		}
	}

	
	
}
