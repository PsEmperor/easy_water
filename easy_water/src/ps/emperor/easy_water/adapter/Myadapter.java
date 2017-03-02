package ps.emperor.easy_water.adapter;

import java.util.List;

import ps.emperor.easy_water.entity.ApplyIrrigationProjectBean.infoList;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.entity.ApplyIrrigationProjectBean;
import ps.emperor.easy_water.greendao.IrrigationProject;
import ps.emperor.easy_water.utils.CheckUtil;
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

	private Context con;
	public List<infoList> mType = null;
	private String group;


	// public static WifiType wiType = new WifiType();

	public Myadapter(Context con, List<infoList> list) {
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
			holder.time_continue = (TextView) convertView.findViewById(R.id.text_apply_irriagte_time_continue);
			holder.time_rest = (TextView) convertView.findViewById(R.id.text_apply_irriagte_time_rest);
	
			group = mType.get(pos).getGroupName().substring(1, mType.get(pos).getGroupName().length());
			holder.group.setText(group);
			holder.time_start.setText(mType.get(pos).getStartTime());
			holder.time_end.setText(mType.get(pos).getEndTime());
			holder.time_continue.setText(mType.get(pos).getDuration());
			holder.time_rest.setText(mType.get(pos).getRestTime());
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
		TextView time_continue;
		TextView time_rest;
	}

	
}
