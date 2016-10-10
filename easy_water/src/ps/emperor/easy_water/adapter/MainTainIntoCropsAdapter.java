package ps.emperor.easy_water.adapter;


import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.entity.MainTainIrrigationInfoBean;

/**
 * 录入作物信息
 * 
 * @author 毛国江
 * @version 2016-5-18 上午11:12
 */
public class MainTainIntoCropsAdapter extends MyBaseAdapter<MainTainIrrigationInfoBean> implements OnClickListener {

	private Context context;

	public MainTainIntoCropsAdapter(Context context) {
		super(context);
		this.context = context;
	}

	@Override
	public View MyGetView(int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.fragment_maintain_into_crops_list, null);
			viewHolder = new ViewHolder();
			viewHolder.valves = (TextView) convertView.findViewById(R.id.text_maintain_into_crops_list);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		MainTainIrrigationInfoBean mainTainIrrigationInfoBean = list.get(position);
		viewHolder.valves.setText(mainTainIrrigationInfoBean.gate);
		return convertView;

	}

	class ViewHolder {
		TextView valves;
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {

	}
}
