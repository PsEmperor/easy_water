package ps.emperor.easy_water.application.adapter;

import java.util.List;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.application.entity.Water_Pressure;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class WaterAdapter extends ArrayAdapter<Water_Pressure> {
	private int resource;
	private Context context;

	public WaterAdapter(Context context, int resource,
			List<Water_Pressure> objects) {
		super(context, resource, objects);
		this.resource = resource;
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Water_Pressure item = getItem(position);
		Holder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(resource, null);
			holder = new Holder();
			x.view().inject(holder, convertView);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}

		holder.wl.setText(item.getLoca());
		holder.wll.setText(item.getRange());
		holder.wc.setText(item.getNum() + "");

		return convertView;
	}

	class Holder {
		@ViewInject(R.id.tv_waterLocation)
		TextView wl;
		@ViewInject(R.id.tv_waterL)
		TextView wll;
		@ViewInject(R.id.tv_waterChannel)
		TextView wc;
	}

}
