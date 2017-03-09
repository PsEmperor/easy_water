package ps.emperor.easy_water.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.entity.KeyWordBean;

/**
 * 添加灌溉/配水设备关联适配器
 * 
 * @author 毛国江
 * @version 2016-5-18 上午11:12
 */
public class IrrigationAddAdapter extends MyBaseAdapter<KeyWordBean> implements OnClickListener {

	private Context context;

	public IrrigationAddAdapter(Context context) {
		super(context);
		this.context = context;
	}

	@Override
	public View MyGetView(int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.fragment_mine_irrigation_add_list, null);
			viewHolder = new ViewHolder();
			viewHolder.irrigation = (TextView) convertView.findViewById(R.id.tv_irrigation_add);
			viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.ck_irrigation_add);
			viewHolder.checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					int position = (Integer) viewHolder.checkBox.getTag();
					list.get(position).isCheck = isChecked;
				}
			});
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		KeyWordBean IrrigationAddBean = list.get(position);
		viewHolder.checkBox.setTag(position);
		viewHolder.irrigation.setText(IrrigationAddBean.keyword);
		viewHolder.checkBox.setChecked(IrrigationAddBean.isCheck);
		return convertView;

	}

	class ViewHolder {
		TextView irrigation;
		CheckBox checkBox;
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {

	}

}
