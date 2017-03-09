package ps.emperor.easy_water.fragment;

import java.util.ArrayList;

import java.util.List;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ListView;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.activity.MainTainPresentrrigateActivity;
import ps.emperor.easy_water.adapter.MainTainIrrigationAdapter;
import ps.emperor.easy_water.entity.ApplyIrrigationBean;
import ps.emperor.easy_water.entity.MainTainIrrigationBean;
import ps.emperor.easy_water.greendao.DBHelper;
import ps.emperor.easy_water.greendao.Irrigation;
import ps.emperor.easy_water.utils.CheckUtil;
import ps.emperor.easy_water.utils.SharedUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 灌溉/配水（维护）
 * 
 * @author 毛国江
 * @version 2016-6-2 下午14:09
 */

@SuppressLint("NewApi")
public class MainTainIrrigateFragment extends Fragment implements OnClickListener,OnItemClickListener {

	private LayoutInflater mInflater;
	private ListView listView;
	private MainTainIrrigationAdapter adapter;
	private List<MainTainIrrigationBean> beans;
	private DBHelper dbHelper;
	private List<Irrigation> irrigation;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mInflater = inflater;
		View view = inflater.inflate(R.layout.fragment_maintain_irrigate, container, false);

		dbHelper = DBHelper.getInstance(getActivity()); // 得到DBHelper对象

		listView = (ListView) view.findViewById(R.id.list_maintain_irrigate_add);
		listView.setOnItemClickListener(this);
		adapter = new MainTainIrrigationAdapter(getActivity());
		beans = new ArrayList<MainTainIrrigationBean>();
		MainTainIrrigationBean bean;
		bean = new MainTainIrrigationBean();
		bean.setMaintain("第二大队第四中队第三小队");
		beans.add(bean);
		bean = new MainTainIrrigationBean();
		bean.setMaintain("第三大队第四中队第三小队");
		beans.add(bean);
		bean = new MainTainIrrigationBean();
		bean.setMaintain("第四大队第四中队第三小队");
		beans.add(bean);
		bean = new MainTainIrrigationBean();
		bean.setMaintain("第五大队第四中队第三小队");
		beans.add(bean);
		adapter.addData(beans, false);
		listView.setAdapter(adapter);
		beans = adapter.getData();

		return view;
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
//		FragmentManager fgManager = getFragmentManager();
//		FragmentTransaction transaction = fgManager.beginTransaction();
//		MainTainIrrigateInfoFragment fragment = new MainTainIrrigateInfoFragment();
////		transaction.setCustomAnimations(R.anim.right_in, R.anim.right_out);
//		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//		transaction.replace(R.id.fragment_home_recommend, fragment, "main");
//		transaction.commit();
		SharedUtils.setParam(getActivity(), "units", beans.get(position).getMaintain());
		Intent intent = new Intent(getActivity(),MainTainPresentrrigateActivity.class);
		intent.putExtra("units", beans.get(position).getMaintain());
		irrigation = dbHelper.loadContinue(beans.get(position).getMaintain());
		if(CheckUtil.IsEmpty(irrigation)){
			Irrigation irrigation = new Irrigation();
			irrigation.setIrrigation(beans.get(position).getMaintain());
			irrigation.setNHour(0);
			irrigation.setNMinutes(0);
			irrigation.setNNumber(0);
			irrigation.setNRound(0);
			irrigation.setIsNightStartHour(0);
			irrigation.setIsNightStartMinute(0);
			irrigation.setIsNightContinueHour(0);
			irrigation.setIsNightContinueMinute(0);
			irrigation.setIsNightEndHour(0);
			irrigation.setIsNightEndMinute(0);
			irrigation.setIsTimeLong(0);
			irrigation.setGroupnumber(0);
			irrigation.setValuenumber(0);
			irrigation.setFilterHour(0);
			irrigation.setFilterMinute(0);
			dbHelper.saveSession(irrigation);
		}
		startActivity(intent);
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
