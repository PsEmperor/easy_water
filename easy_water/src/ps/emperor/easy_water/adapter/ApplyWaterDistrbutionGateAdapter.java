package ps.emperor.easy_water.adapter;


import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.entity.ApplyIrrigateControlBean;
import ps.emperor.easy_water.entity.FindDisWaterInfoOneBean.SluiceGateInfoBean;
import ps.emperor.easy_water.utils.DensityUtil;
import ps.emperor.easy_water.utils.SharedUtils;

/**
 * 闸门控制
 * 
 * @author 毛国江
 * @version 2016-9-13 下午14:25
 */
public class ApplyWaterDistrbutionGateAdapter extends MyBaseAdapter<SluiceGateInfoBean> implements OnClickListener {

	private Context context;

	public ApplyWaterDistrbutionGateAdapter(Context context) {
		super(context);
		this.context = context;
	}

	@Override
	public View MyGetView(int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.fragment_apply_water_distrbution_gate_control_list, null);
			viewHolder = new ViewHolder();
			viewHolder.aperture = (TextView) convertView.findViewById(R.id.tv_gate_control_aperture_list);
			viewHolder.high = (TextView) convertView.findViewById(R.id.tv_gate_control_hight_list);
			viewHolder.layout = (RelativeLayout) convertView.findViewById(R.id.layout_hight_change_list);
			viewHolder.layout_relative_change_list = (RelativeLayout) convertView.findViewById(R.id.layout_relative_change_list);
			viewHolder.num = (TextView) convertView.findViewById(R.id.tv_gate_control_number_list);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		SluiceGateInfoBean applyWaterDistrbutionGateBean = list.get(position);
		viewHolder.aperture.setText((int)((Float.valueOf(applyWaterDistrbutionGateBean.getOpenProportion())*100))+"%");
		viewHolder.high.setText(applyWaterDistrbutionGateBean.getOpenHigh()+"m³");
		viewHolder.num.setText(applyWaterDistrbutionGateBean.getPoreID());
		RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)viewHolder.layout.getLayoutParams();
		int height = DensityUtil.dip2px(context,(int)((100-Float.valueOf(applyWaterDistrbutionGateBean.getOpenProportion())*100)*1.85));
		layoutParams.height = height;
		viewHolder.layout.setLayoutParams(layoutParams);
		viewHolder.layout.requestLayout();
		
		LinearLayout.LayoutParams layoutParam1 = (LinearLayout.LayoutParams)viewHolder.layout_relative_change_list.getLayoutParams();
		int widths = (Integer) SharedUtils.getParam(context, "width", 0);
		layoutParam1.width = widths;
		viewHolder.layout_relative_change_list.setLayoutParams(layoutParam1);
		viewHolder.layout_relative_change_list.requestLayout();
		
		return convertView;

	}

	class ViewHolder {
		TextView percentage;
		TextView aperture;
		TextView high;
		TextView num;
		RelativeLayout layout,layout_relative_change_list;
		
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {

	}
}
