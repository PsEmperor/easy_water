package ps.emperor.easy_water.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.entity.ApplyIrrigateSingleValveBean;
import ps.emperor.easy_water.entity.ApplyIrrigateSingleValveBean.infoList;

/**
 * 单阀显示
 * 
 * @author 毛国江
 * @version 2016-5-26 上午11:12
 */
public class ApplyIrrigateSingleValveAdapter extends MyBaseAdapter<infoList> implements OnClickListener {

	private Context context;

	public ApplyIrrigateSingleValveAdapter(Context context) {
		super(context);
		this.context = context;
	}

	@Override
	public View MyGetView(int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.fragment_apply_irrigate_single_valve_list, null);
			viewHolder = new ViewHolder();
			viewHolder.valves = (TextView) convertView.findViewById(R.id.text_apply_irrigate_single_valve_list_text);
			viewHolder.names = (TextView) convertView.findViewById(R.id.text_apply_irrigate_single_valve_user_name);
			viewHolder.crops = (TextView) convertView.findViewById(R.id.text_apply_irrigate_single_valve_crop);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		infoList infoList = list.get(position);
		viewHolder.valves.setText(infoList.getChanNum());
		viewHolder.names.setText(infoList.getGrowersName());
//		if(infoList.getGrowersName().length()>=3){
//		}else{
			viewHolder.names.setGravity(Gravity.CENTER_HORIZONTAL);
//		}
		viewHolder.crops.setText(infoList.getCropName());
		return convertView;

	}

	class ViewHolder {
		TextView valves;
		TextView names;
		TextView crops;
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {

	}

}
