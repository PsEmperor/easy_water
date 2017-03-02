package ps.emperor.easy_water.adapter;


import android.content.Context;


import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.entity.CropBeen;
import ps.emperor.easy_water.entity.CropBeen.infoList;

/**
 * 录入作物信息
 * 
 * @author 毛国江
 * @version 2016-5-18 上午11:12
 */
public class MainTainIntoCropsAdapter extends MyBaseAdapter<infoList> implements OnClickListener {

	private Context context;

	public MainTainIntoCropsAdapter(Context context) {
		super(context);
		this.context = context;
	}

	@Override
	public View MyGetView(int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.layout_irrigation_user_popu, null);
			viewHolder = new ViewHolder();
			viewHolder.valves = (TextView) convertView.findViewById(R.id.text_mine_user_units);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		infoList infoList = list.get(position);
		viewHolder.valves.setText(infoList.getCropName());
		return convertView;

	}

	class ViewHolder {
		TextView valves;
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {

	}
}
