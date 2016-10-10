package ps.emperor.easy_water.application;

import java.util.List;

import ps.emperor.easy_water.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Configure_Lv_Adapter extends ArrayAdapter<Configure_Lv_item> {

	private int resource;

	public Configure_Lv_Adapter(Context context, int resource,
			List<Configure_Lv_item> objects) {
		super(context, resource, objects);
		this.resource = resource;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Configure_Lv_item item = getItem(position);
		View view;
		Holder h;
		if (convertView != null) {
			view = convertView;
			h = (Holder) view.getTag();
		} else {
			view = LayoutInflater.from(getContext()).inflate(resource, null);
			h = new Holder();
			h.tvn = (TextView) view.findViewById(R.id.tv_con_name);
			h.iva = (ImageView) view.findViewById(R.id.iv_con_arrow);

			view.setTag(h);
		}

		h.tvn.setText(item.getTv());
		h.iva.setImageResource(item.getArrow());
		return view;
	}

	class Holder {
		TextView tvn;
		ImageView iva;

	}

}
