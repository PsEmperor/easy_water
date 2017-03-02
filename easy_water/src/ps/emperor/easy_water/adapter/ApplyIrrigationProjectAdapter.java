package ps.emperor.easy_water.adapter;


import java.util.List;

import ps.emperor.easy_water.entity.ApplyIrrigationProjectBean.infoList;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.entity.ApplyIrrigationProjectBean;

/**
 * 灌溉计划
 * 
 * @author 毛国江
 * @version 2016-5-18 上午11:12
 */
public class ApplyIrrigationProjectAdapter extends MyBaseAdapter<infoList> implements OnClickListener {

	private Context context;
	public List<ApplyIrrigationProjectBean> mType = null;
	
	public ApplyIrrigationProjectAdapter(Context context,List<ApplyIrrigationProjectBean> beans) {
		super(context);
		this.context = context;
		this.mType = beans;
	}

	@Override
	public View MyGetView(int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.fragment_apply_irrigate_project_list, null);
			viewHolder = new ViewHolder();
			viewHolder.group = (TextView) convertView.findViewById(R.id.image_apply_group);
			viewHolder.time_start = (TextView) convertView.findViewById(R.id.text_apply_irriagte_time_start);
			viewHolder.time_end = (TextView) convertView.findViewById(R.id.text_apply_irriagte_time_end);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		infoList applyIrrigationProjectBean = list.get(position);
		viewHolder.group.setText(applyIrrigationProjectBean.getGroupName());
		viewHolder.time_start.setText(applyIrrigationProjectBean.getStartTime());
		viewHolder.time_end.setText(applyIrrigationProjectBean.getEndTime());
		notifyDataSetChanged();
		return convertView;

	}

	class ViewHolder {
		TextView group;
		TextView time_start;
		TextView time_end;
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {

	}

}
