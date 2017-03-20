package ps.emperor.easy_water.application.adapter;

import java.util.List;

import ps.emperor.easy_water.application.entity.AppBeen;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Application里面配置里面的HandSelectActivity
 * @author ps
 *
 */
public class HandSelectAdapter extends ArrayAdapter<AppBeen> {
	
	private int res;

	public HandSelectAdapter(Context context, int resource, List<AppBeen> objects) {
		super(context, resource, objects);
		res = resource;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		AppBeen ab = getItem(position);
		View view;
		Holder h;
		if(convertView!=null){
			view = convertView;
			h = (Holder) view.getTag();
		}else{
			view = LayoutInflater.from(getContext()).inflate(res, null);
			h = new Holder();
			h.tv = (TextView) view.findViewById(android.R.id.text1);
			h.tv.setGravity(Gravity.CENTER);
//			h.tv.setClickable(true);
			view.setTag(h);
		}
		
		h.tv.setText(ab.getContent());
		//左上右下
		h.tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, ab.getPic(), 0);
		
		return view;
	}
	
	class Holder{
		TextView tv;
		
	}

}
