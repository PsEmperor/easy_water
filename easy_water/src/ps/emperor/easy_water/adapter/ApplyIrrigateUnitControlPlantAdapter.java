package ps.emperor.easy_water.adapter;


import android.content.Context;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.entity.ApplyIrrigationUnitControlBean;

/**
 * 灌溉单元控制
 * @author 毛国江
 * @version 2016-7-14 上午10:46
 */
public class ApplyIrrigateUnitControlPlantAdapter extends MyBaseAdapter<ApplyIrrigationUnitControlBean> implements OnClickListener {

	private Context context;

	public ApplyIrrigateUnitControlPlantAdapter(Context context) {
		super(context);
		this.context = context;
	}

	@Override
	public View MyGetView(int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.fragment_apply_irrigate_unit_control_plant, null);
			viewHolder = new ViewHolder();
			viewHolder.plant = (TextView) convertView.findViewById(R.id.text_apply_irrigate_unit_control_crop);
			viewHolder.time = (TextView) convertView.findViewById(R.id.text_apply_irrigate_unit_control_time);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		ApplyIrrigationUnitControlBean applyIrrigationUnitControlBean = list.get(position);
		viewHolder.plant.setText(applyIrrigationUnitControlBean.plant);
		viewHolder.time.setText(applyIrrigationUnitControlBean.time);
		return convertView;

	}

	class ViewHolder {
		TextView plant;
		TextView time;
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {

	}
}
