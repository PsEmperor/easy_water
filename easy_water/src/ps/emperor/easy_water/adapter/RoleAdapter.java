package ps.emperor.easy_water.adapter;


import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.entity.ApplyIrrigateControlBean;
import ps.emperor.easy_water.entity.RoleBean;
import ps.emperor.easy_water.entity.RoleBean;

/**
 * 角色申请
 * 
 * @author 毛国江
 * @version 2017-1-3 16:10
 */
public class RoleAdapter extends MyBaseAdapter<RoleBean> implements OnClickListener {

	private Context context;

	public RoleAdapter(Context context) {
		super(context);
		this.context = context;
	}

	@Override
	public View MyGetView(int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.adapter_role, null);
			viewHolder = new ViewHolder();
			viewHolder.role = (TextView) convertView.findViewById(R.id.tv_role);
			viewHolder.image = (ImageView) convertView.findViewById(R.id.image_chose_true);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		RoleBean roleBean = list.get(position);
		viewHolder.role.setText(roleBean.role);
		if(list.get(position).getCheck()==0){
			viewHolder.image.setVisibility(View.INVISIBLE);
		}else{
			viewHolder.image.setVisibility(View.VISIBLE);
		}
		notifyDataSetChanged();
		return convertView;
	}

	class ViewHolder {
		TextView role;
		ImageView image;
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		
	}
}
