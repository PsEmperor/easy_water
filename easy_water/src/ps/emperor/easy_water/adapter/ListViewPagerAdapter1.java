package ps.emperor.easy_water.adapter;

import java.util.ArrayList;


import java.util.List;

import ps.emperor.easy_water.R;
import ps.emperor.easy_water.activity.TimeAvtivityDialog;
import ps.emperor.easy_water.entity.ApplyIrrigationProjectBean;
import ps.emperor.easy_water.greendao.IrrigationProject;
import ps.emperor.easy_water.utils.SharedUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ListViewPagerAdapter1 extends PagerAdapter implements OnItemClickListener{
	Context context;

	List<View> mListViewPager = new ArrayList<View>(); // ViewPager���������
	List<ApplyIrrigationProjectBean> list = new ArrayList<ApplyIrrigationProjectBean>();
	List<List<ApplyIrrigationProjectBean>> lcontant = null;
	int pageNum = 1;
	int pageRows=5;
	private int mChildCount = 0;
	private int nowItem,nowPage;
	private String units,compareTime;
	
	/**
	 * 
	 * @param context 活动窗体
	 * @param listentity 数据
	 * @param customIndicator 圆点控件
	 * @param rows 每页显示多少条数据
	 */ 
	public ListViewPagerAdapter1(final Context context, List<ApplyIrrigationProjectBean> listentity,int rows,final String units) {
		this.pageRows=rows;
		int count = 0;  //ѭ������
		int pos = 0;		//��ǰλ��
		this.context = context;
		this.list = listentity;
		this.units = units;
		//����ҳ��
		pageNum = (int) Math.ceil(list.size() / pageRows);
		int a=list.size() % pageRows;
		if (a>0) {
			pageNum=pageNum+1;
		}
		
//		mCustomIndicator.setCount(pageNum);
		Log.d("hx2", String.valueOf(pageNum));
		if (Math.ceil(listentity.size() / pageRows) == 0) {
			pageNum = 1;
		}
		lcontant = new ArrayList<List<ApplyIrrigationProjectBean>>();
		for (int i = 0; i < pageNum; i++) {
			Log.d("hx2", String.valueOf(i));
			List<ApplyIrrigationProjectBean> item = new ArrayList<ApplyIrrigationProjectBean>();
			for(int k = pos;k<listentity.size();k++){
				count++;
				pos = k;
				item.add(listentity.get(k));
				//ÿ��List������¼������N�����
				if(count == pageRows){
					count = 0;
					pos = pos+1;
					break;
				}
			}
			lcontant.add(item);
		}

		for (int j = 0; j < pageNum; j++) {
			View viewPager = LayoutInflater.from(context).inflate(
					R.layout.list, null);
			ListView mList = (ListView) viewPager.findViewById(R.id.wifi_list);
			final Myadapter myadapter=new Myadapter(context, lcontant.get(j));
			mList.setAdapter(myadapter);
			mListViewPager.add(viewPager);
			myadapter.notifyDataSetChanged();
			mList.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					
					if(list.get(position).getGroup().equals("A")){
						nowItem = 1;
					}
					if(list.get(position).getGroup().equals("B")){
						nowItem = 2;
					}
					if(list.get(position).getGroup().equals("C")){
						nowItem = 3;
					}
					if(list.get(position).getGroup().equals("D")){
						nowItem = 4;
					}
					if(list.get(position).getGroup().equals("E")){
						nowItem = 5;
					}
					if(list.get(position).getGroup().equals("F")){
						nowItem = 6;
					}
					if(list.get(position).getGroup().equals("G")){
						nowItem = 7;
					}
					if(list.get(position).getGroup().equals("H")){
						nowItem = 8;
					}
					if(list.get(position).getGroup().equals("I")){
						nowItem = 9;
					}
					if(list.get(position).getGroup().equals("J")){
						nowItem = 10;
					}
					if(list.get(position).getGroup().equals("K")){
						nowItem = 11;
					}
					if(list.get(position).getGroup().equals("L")){
						nowItem = 12;
					}
					if(list.get(position).getGroup().equals("M")){
						nowItem = 13;
					}
					if(list.get(position).getGroup().equals("N")){
						nowItem = 14;
					}
					if(list.get(position).getGroup().equals("O")){
						nowItem = 15;
					}
					if(list.get(position).getGroup().equals("P")){
						nowItem = 16;
					}
					if(list.get(position).getGroup().equals("Q")){
						nowItem = 17;
					}
					if(list.get(position).getGroup().equals("R")){
						nowItem = 18;
					}
					if(list.get(position).getGroup().equals("S")){
						nowItem = 19;
					}
					if(list.get(position).getGroup().equals("T")){
						nowItem = 20;
					}
					if(list.get(position).getGroup().equals("U")){
						nowItem = 21;
					}
					if(list.get(position).getGroup().equals("V")){
						nowItem = 22;
					}
					if(list.get(position).getGroup().equals("W")){
						nowItem = 23;
					}
					if(list.get(position).getGroup().equals("X")){
						nowItem = 24;
					}
					if(list.get(position).getGroup().equals("Y")){
						nowItem = 25;
					}
					if(list.get(position).getGroup().equals("Z")){
						nowItem = 26;
					}
					nowPage = (Integer) SharedUtils.getParam(context, "nowPage", 1);
					compareTime = list.get((nowPage-1) * 4 + position).getTime_start();
					position = (nowPage-1) * 4 +position;
					Bundle bundle = new Bundle();
						bundle.putInt("nowItem", nowItem);
						bundle.putInt("nowPage", nowPage);
						bundle.putString("compareTime", compareTime);
						bundle.putString("units", units);
						bundle.putInt("position", position);
						Intent intent = new Intent(context, TimeAvtivityDialog.class);
						intent.putExtras(bundle);
						context.startActivity(intent);
//					((Activity)context).finish();
				}
			});
		}

	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public int getCount() {
		return mListViewPager.size();
	}

	@Override
	public Object instantiateItem(View container, int position) {
		((ViewPager) container).addView(mListViewPager.get(position));
		return mListViewPager.get(position);

	}

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {

	}

	@Override
	public Parcelable saveState() {
		return null;
	}

	@Override
	public void startUpdate(View arg0) {
	}

	@Override
	public void destroyItem(View container, int position, Object arg2) {
		ViewPager pViewPager = ((ViewPager) container);
		pViewPager.removeView(mListViewPager.get(position));
	}

	@Override
	public void finishUpdate(View arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
	}

	@Override
    public int getItemPosition(Object object)   {          
          if ( mChildCount > 0) {
          mChildCount --;
          return POSITION_NONE;
          }
          return super.getItemPosition(object);
    }
	 @Override
     public void notifyDataSetChanged() {         
           mChildCount = getCount();
           super.notifyDataSetChanged();
     }

}
