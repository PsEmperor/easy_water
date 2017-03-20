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
public class ImageAdapterGroup extends BaseAdapter{    
    private Context mContext;			// 定义Context
    private List<infoList> mImageIds;	// 定义一个向量作为图片源
    private List<Integer> mImage_bs = new ArrayList<Integer>();	// 定义一个向量作为选中与否容器
    private int lastPosition = -1;		//记录上一次选中的图片位置，-1表示未选中任何图片
    private boolean multiChoose;		//表示当前适配器是否允许多选
    int mImage = 0;
    
    public ImageAdapterGroup(Context c, boolean isMulti,List<infoList>  mImageId){
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
        		if(CheckUtil.IsEmpty(mImageIds.get(position).getChanNum())&&CheckUtil.IsEmpty(mImageIds.get(position).getGroupName())){
        			mImage_bs.set(position,100);
        		}else if(CheckUtil.IsEmpty(mImageIds.get(position).getGroupName())){
        			mImage_bs.set(position, 0);
        		}else if(("组A").equals(mImageIds.get(position).getGroupName())){
            		mImage_bs.set(position, 3);
            	}else if(("组B").equals(mImageIds.get(position).getGroupName())){
            		mImage_bs.set(position, 4);
            	}else if(("组C").equals(mImageIds.get(position).getGroupName())){
            		mImage_bs.set(position, 5);
            	}else if(("组D").equals(mImageIds.get(position).getGroupName())){
            		mImage_bs.set(position, 6);
            	}else if(("组E").equals(mImageIds.get(position).getGroupName())){
            		mImage_bs.set(position, 7);
            	}else if(("组F").equals(mImageIds.get(position).getGroupName())){
            		mImage_bs.set(position, 8);
            	}else if(("组G").equals(mImageIds.get(position).getGroupName())){
            		mImage_bs.set(position, 9);
            	}else if(("组H").equals(mImageIds.get(position).getGroupName())){
            		mImage_bs.set(position, 10);
            	}else if(("组I").equals(mImageIds.get(position).getGroupName())){
            		mImage_bs.set(position, 11);
            	}else if(("组J").equals(mImageIds.get(position).getGroupName())){
            		mImage_bs.set(position, 12);
            	}else if(("组K").equals(mImageIds.get(position).getGroupName())){
            		mImage_bs.set(position, 13);
            	}else if(("组L").equals(mImageIds.get(position).getGroupName())){
            		mImage_bs.set(position, 14);
            	}else if(("组M").equals(mImageIds.get(position).getGroupName())){
            		mImage_bs.set(position, 15);
            	}else if(("组N").equals(mImageIds.get(position).getGroupName())){
            		mImage_bs.set(position, 16);
            	}else if(("组O").equals(mImageIds.get(position).getGroupName())){
            		mImage_bs.set(position, 17);
            	}else if(("组P").equals(mImageIds.get(position).getGroupName())){
            		mImage_bs.set(position, 18);
            	}else if(("组Q").equals(mImageIds.get(position).getGroupName())){
            		mImage_bs.set(position, 19);
            	}else if(("组R").equals(mImageIds.get(position).getGroupName())){
            		mImage_bs.set(position, 20);
            	}else if(("组S").equals(mImageIds.get(position).getGroupName())){
            		mImage_bs.set(position, 21);
            	}else if(("组T").equals(mImageIds.get(position).getGroupName())){
            		mImage_bs.set(position, 22);
            	}else if(("组U").equals(mImageIds.get(position).getGroupName())){
            		mImage_bs.set(position, 23);
            	}else if(("组V").equals(mImageIds.get(position).getGroupName())){
            		mImage_bs.set(position, 24);
            	}else if(("组W").equals(mImageIds.get(position).getGroupName())){
            		mImage_bs.set(position, 25);
            	}else if(("组X").equals(mImageIds.get(position).getGroupName())){
            		mImage_bs.set(position, 26);
            	}else if(("组Y").equals(mImageIds.get(position).getGroupName())){
            		mImage_bs.set(position, 27);
            	}else if(("组Z").equals(mImageIds.get(position).getGroupName())){
            		mImage_bs.set(position, 28);
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
        viewHolder.textView1.setText("");
        viewHolder.textView1.setGravity(Gravity.RIGHT);
        if(mImage_bs.get(position)==1){
        	viewHolder.textView.setBackgroundResource(R.drawable.value_selected);
        }else if(mImage_bs.get(position)==2){
        	viewHolder.textView.setBackgroundResource(R.drawable.value_grower);
        }else if(mImage_bs.get(position)==3){
        	viewHolder.textView.setBackgroundResource(R.drawable.color_1);
        }else if(mImage_bs.get(position)==4){
        	viewHolder.textView.setBackgroundResource(R.drawable.color_2);
        }else if(mImage_bs.get(position)==5){
        	viewHolder.textView.setBackgroundResource(R.drawable.color_3);
        }else if(mImage_bs.get(position)==6){
        	viewHolder.textView.setBackgroundResource(R.drawable.color_4);
        }else if(mImage_bs.get(position)==7){
        	viewHolder.textView.setBackgroundResource(R.drawable.color_5);
        }else if(mImage_bs.get(position)==8){
        	viewHolder.textView.setBackgroundResource(R.drawable.color_6);
        }else if(mImage_bs.get(position)==9){
        	viewHolder.textView.setBackgroundResource(R.drawable.color_7);
        }else if(mImage_bs.get(position)==10){
        	viewHolder.textView.setBackgroundResource(R.drawable.color_8);
        }else if(mImage_bs.get(position)==11){
        	viewHolder.textView.setBackgroundResource(R.drawable.color_9);
        }else if(mImage_bs.get(position)==12){
        	viewHolder.textView.setBackgroundResource(R.drawable.color_10);
        }else if(mImage_bs.get(position)==13){
        	viewHolder.textView.setBackgroundResource(R.drawable.color_11);
        }else if(mImage_bs.get(position)==14){
        	viewHolder.textView.setBackgroundResource(R.drawable.color_12);
        }else if(mImage_bs.get(position)==15){
        	viewHolder.textView.setBackgroundResource(R.drawable.color_13);
        }else if(mImage_bs.get(position)==16){
        	viewHolder.textView.setBackgroundResource(R.drawable.color_14);
        }else if(mImage_bs.get(position)==17){
        	viewHolder.textView.setBackgroundResource(R.drawable.color_15);
        }else if(mImage_bs.get(position)==18){
        	viewHolder.textView.setBackgroundResource(R.drawable.color_16);
        }else if(mImage_bs.get(position)==19){
        	viewHolder.textView.setBackgroundResource(R.drawable.color_17);
        }else if(mImage_bs.get(position)==20){
        	viewHolder.textView.setBackgroundResource(R.drawable.color_18);
        }else if(mImage_bs.get(position)==21){
        	viewHolder.textView.setBackgroundResource(R.drawable.color_19);
        }else if(mImage_bs.get(position)==22){
        	viewHolder.textView.setBackgroundResource(R.drawable.color_20);
        }else if(mImage_bs.get(position)==23){
        	viewHolder.textView.setBackgroundResource(R.drawable.color_21);
        }else if(mImage_bs.get(position)==24){
        	viewHolder.textView.setBackgroundResource(R.drawable.color_22);
        }else if(mImage_bs.get(position)==25){
        	viewHolder.textView.setBackgroundResource(R.drawable.color_23);
        }else if(mImage_bs.get(position)==26){
        	viewHolder.textView.setBackgroundResource(R.drawable.color_24);
        }else if(mImage_bs.get(position)==27){
        	viewHolder.textView.setBackgroundResource(R.drawable.color_25);
        }else if(mImage_bs.get(position)==28){
        	viewHolder.textView.setBackgroundResource(R.drawable.color_26);
        }else if(mImage_bs.get(position)==100){
        	viewHolder.textView.setVisibility(View.INVISIBLE);
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
    		 if(mImage_bs.get(position) == 1){
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
	    	if(mImage_bs.get(position) == 1){
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
