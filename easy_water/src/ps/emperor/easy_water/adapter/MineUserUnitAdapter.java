package ps.emperor.easy_water.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.entity.MineUserUnitBean;

/**
 * (我)授权单位
 * 
 * @author 毛国江
 * @version 2016-5-18 上午11:12
 */
public class MineUserUnitAdapter extends MyBaseAdapter<MineUserUnitBean> {

	private Context context;

	public MineUserUnitAdapter(Context context) {
		super(context);
		this.context = context;
	}

	@Override
	public View MyGetView(int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.fragment_mine_user_units_list, null);
			viewHolder = new ViewHolder();
			viewHolder.unit = (TextView) convertView.findViewById(R.id.text_mine_user_units);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		MineUserUnitBean mineUserUnitBean = list.get(position);
		viewHolder.unit.setText(mineUserUnitBean.unit);
		return convertView;

	}

	class ViewHolder {
		TextView unit;
	}

}
