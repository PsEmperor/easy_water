package ps.emperor.easy_water.adapter;

import java.util.HashMap;


import cn.jpush.a.a.a;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.TextView;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.entity.KeyWordBean;
import ps.emperor.easy_water.entity.UserReleDisInfoBeanAdd.infoList;
import ps.emperor.easy_water.utils.SharedUtils;

/**
 * 添加灌溉/配水设备关联适配器
 * 
 * @author 毛国江
 * @version 2016-5-18 上午11:12
 */
public class IrrigationAddDisAdapter extends MyBaseAdapter<infoList> implements OnClickListener {

	private Context context;
	Activity activity;
	  
	public IrrigationAddDisAdapter(Context context) {
		super(context);
		this.context = context;
		this.activity = (Activity) context;
	}

	@Override
	public View MyGetView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.fragment_mine_irrigation_add_list, null);
			viewHolder = new ViewHolder();
			viewHolder.irrigation = (TextView) convertView.findViewById(R.id.tv_equipment_add);
			viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.ck_equipment_add);
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
		infoList infoList = list.get(position);
		viewHolder.checkBox.setTag(position);
		viewHolder.irrigation.setText(infoList.getDisEquName());
		viewHolder.checkBox.setChecked(infoList.isCheck);
		convertView.setTag(viewHolder);
		return convertView;

	}

	protected RadioButton findViewById(int temp2) {
		// TODO Auto-generated method stub
		return null;
	}

	class ViewHolder {
		TextView irrigation;
		CheckBox checkBox;
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {

	}

}
