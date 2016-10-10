package ps.emperor.easy_water.adapter;


import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.entity.MainTainIrrigationInfoBean;

/**
 * 轮灌小区信息维护适配器
 * @author 毛国江
 * @version 2016-6-7 下午13:24
 */
public class MainTainIrrigationInfoAdapter extends MyBaseAdapter<MainTainIrrigationInfoBean> implements OnClickListener {

	private Context context;

	public MainTainIrrigationInfoAdapter(Context context) {
		super(context);
		this.context = context;
	}

	@Override
	public View MyGetView(int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.fragment_maintain_irrigation_info_list, null);
			viewHolder = new ViewHolder();
			viewHolder.gates = (ImageView) convertView.findViewById(R.id.text_main_irrigate_info_list);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		MainTainIrrigationInfoBean mainTainIrrigationInfoBean = list.get(position);
		viewHolder.gates.setImageResource(R.id.actionbar_apply_irrigate_control);
		return convertView;

	}

	class ViewHolder {
		ImageView gates;
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {

	}

}
