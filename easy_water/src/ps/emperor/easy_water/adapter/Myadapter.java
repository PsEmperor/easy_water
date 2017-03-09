package ps.emperor.easy_water.adapter;

import java.util.List;



import ps.emperor.easy_water.R;
import ps.emperor.easy_water.entity.ApplyIrrigationProjectBean;
import ps.emperor.easy_water.greendao.IrrigationProject;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 灌溉计划
 * 
 * @author 毛国江
 * @version 2016-5-18 上午11:12
 */
public class Myadapter extends BaseAdapter {

//	private Context context;
//	public List<ApplyIrrigationProjectBean> mType = null;
//	
//	public Myadapter(Context context,List<ApplyIrrigationProjectBean> list) {
//		super(context);
//		this.context = context;
//		this.mType = list;
//	}
//
//	@Override
//	public View MyGetView(int position, View convertView, ViewGroup parent) {
//		final ViewHolder viewHolder;
//		if (convertView == null) {
//			convertView = inflater.inflate(R.layout.fragment_apply_irrigate_project_list, null);
//			viewHolder = new ViewHolder();
//			viewHolder.group = (TextView) convertView.findViewById(R.id.image_apply_group);
//			viewHolder.time_start = (TextView) convertView.findViewById(R.id.text_apply_irriagte_time_start);
//			viewHolder.time_end = (TextView) convertView.findViewById(R.id.text_apply_irriagte_time_end);
//			convertView.setTag(viewHolder);
//		} else {
//			viewHolder = (ViewHolder) convertView.getTag();
//		}
//		ApplyIrrigationProjectBean applyIrrigationProjectBean = list.get(position);
//		viewHolder.group.setText(applyIrrigationProjectBean.group);
//		viewHolder.time_start.setText(applyIrrigationProjectBean.time_start);
//		viewHolder.time_end.setText(applyIrrigationProjectBean.time_end);
//		notifyDataSetChanged();
//		return convertView;
//
//	}
//
//	class ViewHolder {
//		TextView group;
//		TextView time_start;
//		TextView time_end;
//	}
//
//	@Override
//	public void onClick(View v) {
//		// TODO Auto-generated method stub
//		
//	}

	private Context con;
	public List<ApplyIrrigationProjectBean> mType = null;


	// public static WifiType wiType = new WifiType();

	public Myadapter(Context con, List<ApplyIrrigationProjectBean> list) {
		this.con = con;
		this.mType = list;
	
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mType.size();
	}

	@Override
	public Object getItem(int pos) {
		// TODO Auto-generated method stub
		return pos;
	}

	@Override
	public long getItemId(int pos) {
		// TODO Auto-generated method stub
		return pos;
	}

	@Override
	public View getView(final int pos, View convertView, ViewGroup parent) {
		myV holder = null;

		if (convertView == null) {
			convertView = LayoutInflater.from(con).inflate(R.layout.fragment_apply_irrigate_project_list, null);
			holder = new myV();
			holder.group = (TextView) convertView.findViewById(R.id.image_apply_group);
			holder.time_start = (TextView) convertView.findViewById(R.id.text_apply_irriagte_time_start);
			holder.time_end = (TextView) convertView.findViewById(R.id.text_apply_irriagte_time_end);
	
			holder.group.setText(mType.get(pos).group);
			holder.time_start.setText(mType.get(pos).time_start);
			holder.time_end.setText(mType.get(pos).time_end);
			convertView.setTag(holder);

		} else {
			holder = (myV) convertView.getTag();
		}
		return convertView;
	}

	static class myV {
		TextView group;
		TextView time_start;
		TextView time_end;
	}

	
}
