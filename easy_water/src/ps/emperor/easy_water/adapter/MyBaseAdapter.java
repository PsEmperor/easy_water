package ps.emperor.easy_water.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class MyBaseAdapter<T> extends BaseAdapter {
	protected List<T> list;
	protected LayoutInflater inflater;

	public MyBaseAdapter(Context context) {
		inflater = LayoutInflater.from(context);
		list = new ArrayList<T>();
	}

	public void addData(List<T> data, boolean isclearOle) {
		if (isclearOle) {
			list.clear();
		}
		list.addAll(data);
	}

	public void addDataToTop(List<T> data, boolean isClearOle) {
		if (isClearOle) {
			list.clear();
		}
		list.addAll(0, data);
	}

	public void addDataDown(List<T> data, boolean isClearOle) {
		if (isClearOle) {
			list.clear();
		}
		list.addAll(data);
	}

	public List<T> getData() {
		return list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return MyGetView(position, convertView, parent);
	}

	public abstract View MyGetView(int position, View convertView, ViewGroup parent);
}
