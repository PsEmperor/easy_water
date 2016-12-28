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
import ps.emperor.easy_water.activity.MainTainGateInfoActivity;
import ps.emperor.easy_water.adapter.MainTainIrrigationAdapter;
import ps.emperor.easy_water.entity.MainTainIrrigationBean;
import ps.emperor.easy_water.entity.MainTainIrrigationBean;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 灌溉/配水（维护）
 * 
 * @author 毛国江
 * @version 2016-6-2 下午14:09
 */

@SuppressLint("NewApi")
public class MainTainWaterFragment extends Fragment implements OnClickListener,
		OnItemClickListener {

	private LayoutInflater mInflater;
	private ListView listView;
	private MainTainIrrigationAdapter adapter;
	private List<MainTainIrrigationBean> beans;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflater = inflater;
		View view = inflater.inflate(R.layout.fragment_maintain_irrigate,
				container, false);

		listView = (ListView) view
				.findViewById(R.id.list_maintain_irrigate_add);
		listView.setOnItemClickListener(this);
		adapter = new MainTainIrrigationAdapter(getActivity());
		beans = new ArrayList<MainTainIrrigationBean>();
		MainTainIrrigationBean bean;
		bean = new MainTainIrrigationBean();
		bean.setMaintain("141团5连支渠进水闸");
		beans.add(bean);
		bean = new MainTainIrrigationBean();
		bean.setMaintain("141团5连支渠一斗渠进水闸");
		beans.add(bean);
		bean = new MainTainIrrigationBean();
		bean.setMaintain("141团5连支渠二斗渠进水闸");
		beans.add(bean);
		bean = new MainTainIrrigationBean();
		bean.setMaintain("141团6连支渠进水闸");
		beans.add(bean);
		bean = new MainTainIrrigationBean();
		bean.setMaintain("141团5连干渠进水闸");
		beans.add(bean);
		adapter.addData(beans, false);
		listView.setAdapter(adapter);
		beans = adapter.getData();

		return view;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view,
			final int position, long id) {
		Intent intent = new Intent(getActivity(),
				MainTainGateInfoActivity.class);
		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}
