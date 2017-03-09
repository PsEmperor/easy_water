package ps.emperor.easy_water.adapter;


import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.entity.IrrigationEquipmentBean;
import android.widget.CompoundButton.OnCheckedChangeListener;
/**
 * 灌溉/配水适配器(设备关联)
 * @author 毛国江
 * @version 2016-5-18 上午11:12
 */

public class IrrigationEquipmentAdapter extends MyBaseAdapter<IrrigationEquipmentBean> implements OnClickListener {
	private Context context;

	public IrrigationEquipmentAdapter(Context context) {
		super(context);
		this.context = context;
	}

	@Override
	public View MyGetView(int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.fragment_mine_irrigation_equipment_list, null);
			viewHolder = new ViewHolder();
			viewHolder.Irrigation = (TextView) convertView.findViewById(R.id.tv_rrigation_equipment);
			viewHolder.button = (ToggleButton) convertView.findViewById(R.id.toggle_persona_menstruation_message_sets);
			viewHolder.button.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					int position = (Integer) viewHolder.button.getTag();
					list.get(position).isCheck = isChecked;
					Toast.makeText(context, "开关--" + position + isChecked , Toast.LENGTH_SHORT).show();
				}
			});
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		IrrigationEquipmentBean bean = list.get(position);
		viewHolder.button.setTag(position);
		viewHolder.Irrigation.setText(bean.irrigation);
		viewHolder.button.setChecked(bean.isCheck);
		return convertView;
	}

	class ViewHolder {
		TextView Irrigation; // 授权单位
		ToggleButton button; // 是否关联
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		
	}

}
