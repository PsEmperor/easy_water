package ps.emperor.easy_water.adapter;


import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.entity.ApplyIrrigationUnitControlBean;
import ps.emperor.easy_water.entity.ApplyWaterGateLinkageBean;
import ps.emperor.easy_water.utils.SharedUtils;

/**
 * 多控闸门联动
 * @author 毛国江
 * @version 2016-9-9 上午10:43
 */
public class ApplyWaterGateLinkageAdapter extends MyBaseAdapter<ApplyWaterGateLinkageBean> {

	private Context context;

	public ApplyWaterGateLinkageAdapter(Context context) {
		super(context);
		this.context = context;
	}

	@Override
	public View MyGetView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.fragment_haplopore_gate_list, null);
			viewHolder = new ViewHolder();
			viewHolder.plant = (SeekBar) convertView.findViewById(R.id.mySeekBar_apply_water_haplopore_gate_control_list);
			viewHolder.textView = (TextView) convertView.findViewById(R.id.tv_apply_water_haplopore_gate_control_list);
			viewHolder.haplopore = (TextView) convertView.findViewById(R.id.tv_apply_water_haplopore_list);
			viewHolder.plant.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
				
				@Override
				public void onStopTrackingTouch(SeekBar seekBar) {
				}
				
				@Override
				public void onStartTrackingTouch(SeekBar seekBar) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onProgressChanged(SeekBar seekBar, int progress,
						boolean fromUser) {
					int position = (Integer) viewHolder.plant.getTag();
					viewHolder.textView.setText(progress+"");
					list.get(position).percentage = progress + "";
				}
			});
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.plant.setTag(position);
		ApplyWaterGateLinkageBean applyWaterGateLinkageBean = list.get(position);
		viewHolder.plant.setProgress(Integer.valueOf(applyWaterGateLinkageBean.getPercentage()));
		viewHolder.textView.setText(applyWaterGateLinkageBean.getPercentage());
		viewHolder.haplopore.setText(applyWaterGateLinkageBean.getHaplopore());
		
		return convertView;

	}

	class ViewHolder {
		SeekBar plant;
		TextView textView;
		TextView haplopore;
	}


}
