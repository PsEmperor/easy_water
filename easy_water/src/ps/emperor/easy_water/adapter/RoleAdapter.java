package ps.emperor.easy_water.adapter;


import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.entity.ApplyIrrigateControlBean;
import ps.emperor.easy_water.entity.UserBean;

/**
 * 角色申请
 * 
 * @author 毛国江
 * @version 2017-1-3 16:10
 */
public class RoleAdapter extends MyBaseAdapter<UserBean> implements OnClickListener {

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
			viewHolder.msg = (TextView) convertView.findViewById(R.id.tv_role_msg);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		UserBean userBean = list.get(position);
		viewHolder.role.setText(userBean.role);
		viewHolder.msg.setText(userBean.msg);
		return convertView;

	}

	class ViewHolder {
		TextView role;
		TextView msg;
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {

	}
}
