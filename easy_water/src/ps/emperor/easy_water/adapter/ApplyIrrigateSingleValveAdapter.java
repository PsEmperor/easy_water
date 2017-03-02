package ps.emperor.easy_water.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.entity.ApplyIrrigateSingleValveBean;
import ps.emperor.easy_water.entity.ApplyIrrigateSingleValveBean.infoList;
import ps.emperor.easy_water.utils.CheckUtil;

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
			viewHolder.image = (ImageView) convertView.findViewById(R.id.text_apply_irrigate_single_valve_list);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		infoList infoList = list.get(position);
		if(("组A").equals(list.get(position).getGroupName())){
			viewHolder.image.setImageResource(R.drawable.color_1);
    	}else if(("组B").equals(list.get(position).getGroupName())){
    		viewHolder.image.setImageResource(R.drawable.color_2);
    	}else if(("组C").equals(list.get(position).getGroupName())){
    		viewHolder.image.setImageResource(R.drawable.color_3);
    	}else if(("组D").equals(list.get(position).getGroupName())){
    		viewHolder.image.setImageResource(R.drawable.color_4);
    	}else if(("组E").equals(list.get(position).getGroupName())){
    		viewHolder.image.setImageResource(R.drawable.color_5);
    	}else if(("组F").equals(list.get(position).getGroupName())){
    		viewHolder.image.setImageResource(R.drawable.color_6);
    	}else if(("组G").equals(list.get(position).getGroupName())){
    		viewHolder.image.setImageResource(R.drawable.color_7);
    	}else if(("组H").equals(list.get(position).getGroupName())){
    		viewHolder.image.setImageResource(R.drawable.color_8);
    	}else if(("组I").equals(list.get(position).getGroupName())){
    		viewHolder.image.setImageResource(R.drawable.color_9);
    	}else if(("组J").equals(list.get(position).getGroupName())){
    		viewHolder.image.setImageResource(R.drawable.color_10);
    	}else if(("组K").equals(list.get(position).getGroupName())){
    		viewHolder.image.setImageResource(R.drawable.color_11);
    	}else if(("组L").equals(list.get(position).getGroupName())){
    		viewHolder.image.setImageResource(R.drawable.color_12);
    	}else if(("组M").equals(list.get(position).getGroupName())){
    		viewHolder.image.setImageResource(R.drawable.color_13);
    	}else if(("组N").equals(list.get(position).getGroupName())){
    		viewHolder.image.setImageResource(R.drawable.color_14);
    	}else if(("组O").equals(list.get(position).getGroupName())){
    		viewHolder.image.setImageResource(R.drawable.color_15);
    	}else if(("组P").equals(list.get(position).getGroupName())){
    		viewHolder.image.setImageResource(R.drawable.color_16);
    	}else if(("组Q").equals(list.get(position).getGroupName())){
    		viewHolder.image.setImageResource(R.drawable.color_17);
    	}else if(("组R").equals(list.get(position).getGroupName())){
    		viewHolder.image.setImageResource(R.drawable.color_18);
    	}else if(("组S").equals(list.get(position).getGroupName())){
    		viewHolder.image.setImageResource(R.drawable.color_19);
    	}else if(("组T").equals(list.get(position).getGroupName())){
    		viewHolder.image.setImageResource(R.drawable.color_20);
    	}else if(("组U").equals(list.get(position).getGroupName())){
    		viewHolder.image.setImageResource(R.drawable.color_21);
    	}else if(("组V").equals(list.get(position).getGroupName())){
    		viewHolder.image.setImageResource(R.drawable.color_22);
    	}else if(("组W").equals(list.get(position).getGroupName())){
    		viewHolder.image.setImageResource(R.drawable.color_23);
    	}else if(("组X").equals(list.get(position).getGroupName())){
    		viewHolder.image.setImageResource(R.drawable.color_24);
    	}else if(("组Y").equals(list.get(position).getGroupName())){
    		viewHolder.image.setImageResource(R.drawable.color_25);
    	}else if(("组Z").equals(list.get(position).getGroupName())){
    		viewHolder.image.setImageResource(R.drawable.color_26);
    	}else if(CheckUtil.IsEmpty(list.get(position).getChanNum())&&CheckUtil.IsEmpty(list.get(position).getGroupName())){
        	viewHolder.image.setVisibility(View.INVISIBLE);
        	viewHolder.valves.setVisibility(View.INVISIBLE);
        	viewHolder.names.setVisibility(View.INVISIBLE);
        	viewHolder.crops.setVisibility(View.INVISIBLE);
        }
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
		ImageView image;
		TextView valves;
		TextView names;
		TextView crops;
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {

	}

}
