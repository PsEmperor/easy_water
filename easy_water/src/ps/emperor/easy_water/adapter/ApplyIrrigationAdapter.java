package ps.emperor.easy_water.adapter;


import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.entity.ApplyIrrigationBean;
import ps.emperor.easy_water.view.PieChatView;

/**
 * 灌溉（应用）适配器
 * @author 毛国江
 * @version 2016-5-18 上午11:12
 */
public class ApplyIrrigationAdapter extends MyBaseAdapter<ApplyIrrigationBean> implements OnClickListener {

	private Context context;

	public ApplyIrrigationAdapter(Context context) {
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
		ApplyIrrigationBean applyIrrigationBean = list.get(position);
		viewHolder.units.setText(applyIrrigationBean.units);
		viewHolder.element.setText(applyIrrigationBean.element);
		viewHolder.whether.setText(applyIrrigationBean.whether);
		viewHolder.current_state.setAngle(applyIrrigationBean.current_state);
		viewHolder.whether_percent.setText(applyIrrigationBean.whether_percent);
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
