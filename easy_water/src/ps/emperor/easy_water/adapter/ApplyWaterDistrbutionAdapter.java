package ps.emperor.easy_water.adapter;


import android.content.Context;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.entity.ApplyWaterDistrbutionBean;
import ps.emperor.easy_water.entity.UserReleDisInfoBean.infoList;

/**
 * 配水（应用）适配器
 * @author 毛国江
 * @version 2016-5-26 下午14:03
 */
public class ApplyWaterDistrbutionAdapter extends MyBaseAdapter<infoList> implements OnClickListener {

	private Context context;

	public ApplyWaterDistrbutionAdapter(Context context) {
		super(context);
		this.context = context;
	}

	@Override
	public View MyGetView(int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.fragment_apply_water_distrbution_list, null);
			viewHolder = new ViewHolder();
			viewHolder.units = (TextView) convertView.findViewById(R.id.text_apply_water_units);
			viewHolder.gate = (TextView) convertView.findViewById(R.id.text_apply_water_gate);
			viewHolder.whether = (TextView) convertView.findViewById(R.id.text_apply_water_whether);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		infoList infoList = list.get(position);
		viewHolder.units.setText(infoList.getDisEquName());
//		viewHolder.gate.setText(infoList.getDisEquName());
		viewHolder.whether.setText(infoList.getDisEquID());
		return convertView;

	}

	class ViewHolder {
		TextView units;
		TextView gate;
		TextView whether;
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {

	}
	
}
