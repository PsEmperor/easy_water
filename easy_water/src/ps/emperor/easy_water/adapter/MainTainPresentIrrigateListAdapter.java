package ps.emperor.easy_water.adapter;


import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.entity.MainTainPresentIrrigateGridBean;
import ps.emperor.easy_water.entity.MainTainPresentIrrigateListBean;

/**
 * 当前灌溉组外层listview
 * @author 毛国江
 * @version 2016-6-6 下午15：38
 */
public class MainTainPresentIrrigateListAdapter extends MyBaseAdapter<MainTainPresentIrrigateListBean> implements OnClickListener {

	private Context context;
	private MainTainPresentIrrigateGridAdapter adapters;
	private List<MainTainPresentIrrigateGridBean> gridbeans;
	
	public MainTainPresentIrrigateListAdapter(Context context,List<MainTainPresentIrrigateGridBean> beans) {
		super(context);
		this.context = context;
		this.gridbeans = beans;
	}

	@Override
	public View MyGetView(int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.fragment_maintain_present_irrigate_list, null);
			viewHolder = new ViewHolder();
			viewHolder.groups = (TextView) convertView.findViewById(R.id.text_fragment_maintain_present_group);
			viewHolder.gridView = (GridView) convertView.findViewById(R.id.gridView_fragment_maintain_present);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		MainTainPresentIrrigateListBean mainTainPresentIrrigateListBean = list.get(position);
		viewHolder.groups.setText(mainTainPresentIrrigateListBean.groups);
//		gridbeans = new ArrayList<MainTainPresentIrrigateGridBean>();
		adapters = new MainTainPresentIrrigateGridAdapter(context);
		
		adapters.addData(gridbeans, false);
		gridbeans = adapters.getData();
		viewHolder.gridView.setAdapter(adapters);
		return convertView;
	}

	class ViewHolder {
		TextView groups;
		GridView gridView;
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {

	}
}
