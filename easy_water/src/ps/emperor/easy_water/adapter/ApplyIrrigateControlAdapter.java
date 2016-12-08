package ps.emperor.easy_water.adapter;


import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.entity.ApplyIrrigateControlBean;

/**
 * 灌溉组控制
 * 
 * @author 毛国江
 * @version 2016-5-18 上午11:12
 */
public class ApplyIrrigateControlAdapter extends MyBaseAdapter<ApplyIrrigateControlBean> implements OnClickListener {

	private Context context;

	public ApplyIrrigateControlAdapter(Context context) {
		super(context);
		this.context = context;
	}

	@Override
	public View MyGetView(int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.fragment_apply_irrigate_control_list, null);
			viewHolder = new ViewHolder();
			viewHolder.valves = (TextView) convertView.findViewById(R.id.text_irriagte_control_list_text);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		ApplyIrrigateControlBean applyIrrigateControlBean = list.get(position);
		viewHolder.valves.setText(applyIrrigateControlBean.valve);
		return convertView;

	}

	class ViewHolder {
		TextView valves;
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {

	}
}
