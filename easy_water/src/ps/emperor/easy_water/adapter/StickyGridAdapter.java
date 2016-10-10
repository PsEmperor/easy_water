/*
 Copyright 2013 Tonic Artos

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

package ps.emperor.easy_water.adapter;

import java.util.Arrays;


import java.util.List;

import ps.emperor.easy_water.R;
import ps.emperor.easy_water.entity.Image;
import ps.emperor.easy_water.view.StickyGridHeadersSimpleAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author Tonic Artos
 * @param <T>
 */
public class StickyGridAdapter extends BaseAdapter implements
		StickyGridHeadersSimpleAdapter {

	protected static final String TAG = StickyGridAdapter.class
			.getSimpleName();

	private int mHeaderResId;

	private LayoutInflater mInflater;

	private int mItemResId;

	private List<Image> mItems;

	public StickyGridAdapter(Context context,
			List<Image> items, int headerResId, int itemResId) {
		init(context, items, headerResId, itemResId);
	}

	public StickyGridAdapter(Context context, Image[] items,
			int headerResId, int itemResId) {
		init(context, Arrays.asList(items), headerResId, itemResId);
	}
	
	private void init(Context context, List<Image> items, int headerResId,
			int itemResId) {
		this.mItems = items;
		this.mHeaderResId = headerResId;
		this.mItemResId = itemResId;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public boolean areAllItemsEnabled() {
		return false;
	}

	@Override
	public int getCount() {
		return mItems.size();
	}

	@Override
	public long getHeaderId(int position) {
		Image item = getItem(position);
		//ֻҪ��֤headerId��ͬ���Ϳ�����֣������Ϊ�䵥��������һ���ֶ�type
		return item.getType().charAt(0);
	}

	@Override
	public View getHeaderView(int position, View convertView, ViewGroup parent) {
		HeaderViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(mHeaderResId, parent, false);
			holder = new HeaderViewHolder();
			holder.textView = (TextView) convertView
					.findViewById(android.R.id.text1);
			convertView.setTag(holder);
		} else {
			holder = (HeaderViewHolder) convertView.getTag();
		}

		Image item = getItem(position);
		// ��ȡͷ����Ϣ
		holder.textView.setText(item.getName().subSequence(0, 10));
		return convertView;
	}

	@Override
	public Image getItem(int position) {
		return mItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.fragment_maintain_irrigation_info_list, parent, false);
			holder = new ViewHolder();
			holder.img = (ImageView) convertView
					.findViewById(R.id.text_main_irrigate_info_list);
//			holder.textView = (TextView) convertView
//					.findViewById(android.R.id.text1);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Image item = getItem(position);
		//��ǰͼ���Ƿ���ѡ��״̬
//		if(item.isSelect()){
//			holder.icon.setVisibility(View.VISIBLE);
//		}else{
//			holder.icon.setVisibility(View.GONE);
//		}
		
		holder.img.setImageResource(R.drawable.ic_launcher);
//		holder.textView.setText(item.getName());

		return convertView;
	}

	protected class HeaderViewHolder {
		public TextView textView;
	}

	public static class ViewHolder {
		public ImageView img;
		public ImageView icon;
		public TextView textView;
	}
}
