package ps.emperor.easy_water.adapter;


import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.entity.MainTainPresentIrrigateGridBean;

/**
 * 当前灌溉组内层gridview
 * @author 毛国江
 * @version 2016-6-6 上午11:12
 */
public class MainTainPresentIrrigateGridAdapter extends MyBaseAdapter<MainTainPresentIrrigateGridBean> implements OnClickListener {

	private Context context;

	public MainTainPresentIrrigateGridAdapter(Context context) {
		super(context);
		this.context = context;
	}

	@Override
	public View MyGetView(int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.fragment_maintain_irrigate_grid_list, null);
			viewHolder = new ViewHolder();
			viewHolder.group = (TextView) convertView.findViewById(R.id.text_maintain_present_grid_list);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		MainTainPresentIrrigateGridBean mainTainPresentIrrigateBean = list.get(position);
		viewHolder.group.setText(mainTainPresentIrrigateBean.group);
		return convertView;

	}

	class ViewHolder {
		TextView group;
		
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {

	}
}
