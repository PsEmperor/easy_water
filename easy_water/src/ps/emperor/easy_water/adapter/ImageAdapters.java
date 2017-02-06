package ps.emperor.easy_water.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;



import ps.emperor.easy_water.R;
import ps.emperor.easy_water.entity.MainTainIrrigationInfoBean.infoList;
import ps.emperor.easy_water.utils.CheckUtil;
import ps.emperor.easy_water.utils.SharedUtils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

@SuppressLint("ResourceAsColor")
public class ImageAdapters extends BaseAdapter{    
    private Context mContext;			// 定义Context
    private List<infoList> mImageIds;	// 定义一个向量作为图片源
    private List<Integer> mImage_bs = new ArrayList<Integer>();	// 定义一个向量作为选中与否容器
    private int lastPosition = -1;		//记录上一次选中的图片位置，-1表示未选中任何图片
    private boolean multiChoose;		//表示当前适配器是否允许多选
    int mImage = 0;
    
    public ImageAdapters(Context c, boolean isMulti,List<infoList>  mImageId){
    	mContext = c;
    	multiChoose = isMulti;
    	mImageIds = mImageId;
    	
//    	// װ����Դ 
//    	mImageIds.add(R.drawable.item1);
//    	mImageIds.add(R.drawable.item2);
//    	mImageIds.add(R.drawable.item3);
//    	mImageIds.add(R.drawable.item4);
//    	mImageIds.add(R.drawable.item5);
//    	mImageIds.add(R.drawable.item6);
    	
    	for(int i=0; i<mImageIds.size(); i++)
    		mImage_bs.add(0);
    }
    
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mImageIds.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		 return position;
	}

	@SuppressLint("NewApi")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
//		ImageView imageView;
		final ViewHolder viewHolder;
        if (convertView == null)
        {	
        	viewHolder = new ViewHolder();
        	convertView = LayoutInflater.from(mContext).inflate(R.layout.image_adapter_list, null);
        	viewHolder.textView = (TextView) convertView.findViewById(R.id.text_adapter_list);
        	viewHolder.textView1 = (TextView) convertView.findViewById(R.id.text_adapter_list1);
        	if(mImage < mImageIds.size()){
        		if(!CheckUtil.IsEmpty(mImageIds.get(position).getIsAllocationGrowers())){
        			if(mImageIds.get(position).getIsAllocationGrowers().equals("1")){
        				mImage_bs.set(position, 2);
        			}  
        		}else if(!CheckUtil.IsEmpty(mImageIds.get(position).getIsAllocationCrop())){
        			if(mImageIds.get(position).getIsAllocationCrop().equals("1")){
        				mImage_bs.set(position, 2);
        			}  
        		}
        		mImage ++;
        	}
            convertView.setTag(viewHolder);
        }
        else
        {
//        	imageView = (ImageView) convertView;
        	viewHolder = (ViewHolder) convertView.getTag();
        }
//        imageView.setImageDrawable(makeBmp(mImageIds.elementAt(position).getGates(),
//        		mImage_bs.elementAt(position)));
        viewHolder. textView.setText(mImageIds.get(position).getChanNum());
        viewHolder.textView.setBackgroundResource(R.drawable.value_on);
        viewHolder.textView.setGravity(Gravity.BOTTOM|Gravity.CENTER);
        viewHolder.textView.setPadding(10, 10, 5, 10);
        viewHolder.textView1.setText("王");
        viewHolder.textView1.setGravity(Gravity.RIGHT);
        if(mImage_bs.get(position)==1){
        	viewHolder.textView.setBackgroundResource(R.drawable.value_selected);
        }else if(mImage_bs.get(position)==2){
        	viewHolder.textView.setBackgroundResource(R.drawable.value_grower);
        }else{
        	viewHolder.textView.setBackgroundResource(R.drawable.value_on);
        }
        return convertView;
	}
	class ViewHolder {
		TextView textView;
		TextView textView1;
	}
	// 修改选中的״̬
    public void changeState(int position){
    	// 多选
    	if(multiChoose == true){
    		if(mImage_bs.get(position) == 2){
	    		mImage_bs.set(position, 1);	//直接取反即可	
	    	}else if(mImage_bs.get(position) == 1){
    			mImage_bs.set(position, 0);	//直接取反即可	
    		}else if(mImage_bs.get(position) == 0){
    			mImage_bs.set(position, 1);	//直接取反即可	
    		}
    		if((mImage_bs.get(position))== 0){
    			mImageIds.get(position).setIstrue(false);
    		}else if((mImage_bs.get(position))== 1){
    			mImageIds.get(position).setIstrue(true);
    		}
    	}
    	// 单选
    	else{						
	    	if(lastPosition != -1)
	    		mImage_bs.set(lastPosition, 0);	//取消上一次的选中状态
	    	if(mImage_bs.get(position) == 2){
	    		mImage_bs.set(position, 1);	//直接取反即可	
	    	}else if(mImage_bs.get(position) == 1){
    			mImage_bs.set(position, 0);	//直接取反即可	
    		}else if(mImage_bs.get(position) == 0){
    			mImage_bs.set(position, 1);	//直接取反即可	
    		}
	    	lastPosition = position;		//记录本次选中的位置
    	}
    	notifyDataSetChanged();		//通知适配器进行更新
    }
    public void changeAllState(boolean all){
    	if(multiChoose ==true){
    		//��ѡ 
    		if(all){
    			for(int i=0;i<mImage_bs.size();i++){
    				mImage_bs.set(i, 1);
    				mImageIds.get(i).setIstrue(true);
    			}
    			
    		}else{
    			for(int i=0;i<mImage_bs.size();i++){
    				mImage_bs.set(i, 0);
    				mImageIds.get(i).setIstrue(false);
    			}
    		}
    		notifyDataSetChanged();
    	}
    }
}
