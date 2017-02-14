package ps.emperor.easy_water.adapter;


import android.content.Context;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.entity.ApplyIrrigationBean;
import ps.emperor.easy_water.entity.UserReleIrrInfoBean.infoList;
import ps.emperor.easy_water.view.PieChatView;

/**
 * 灌溉（应用）适配器
 * @author 毛国江
 * @version 2016-5-18 上午11:12
 */
public class ApplyIrrigationDBAdapter extends MyBaseAdapter<ApplyIrrigationBean> implements OnClickListener {

	private Context context;

	public ApplyIrrigationDBAdapter(Context context) {
		super(context);
		this.context = context;
	}

	@Override
	public View MyGetView(int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.fragment_apply_irrigate_list, null);
			viewHolder = new ViewHolder();
			viewHolder.units = (TextView) convertView.findViewById(R.id.text_apply_units);
			viewHolder.element = (TextView) convertView.findViewById(R.id.text_apply_element);
			viewHolder.whether = (TextView) convertView.findViewById(R.id.text_apply_whether);
			viewHolder.current_state = (PieChatView) convertView.findViewById(R.id.image_apply_status);
			viewHolder.whether_percent = (TextView) convertView.findViewById(R.id.text_apply_whether_percent);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		ApplyIrrigationBean infoList = list.get(position);
		viewHolder.units.setText(infoList.getUnits());
//		viewHolder.element.setText(applyIrrigationBean.element);
		viewHolder.whether.setText("正在灌溉");
		viewHolder.current_state.setAngle(180);
		viewHolder.whether_percent.setText("50%");
		return convertView;

	}

	class ViewHolder {
		TextView units;
		TextView element;
		TextView whether;
		TextView whether_percent;
		PieChatView current_state;
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {

	}
}
