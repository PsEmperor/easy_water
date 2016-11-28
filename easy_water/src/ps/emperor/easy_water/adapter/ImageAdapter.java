package ps.emperor.easy_water.adapter;

import java.util.Vector;



import ps.emperor.easy_water.R;
import ps.emperor.easy_water.entity.MainTainIrrigationInfoBean;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class ImageAdapter extends BaseAdapter{    
    private Context mContext;			// 定义Context
    private Vector<MainTainIrrigationInfoBean> mImageIds;	// 定义一个向量作为图片源
    private Vector<Boolean> mImage_bs = new Vector<Boolean>();	// 定义一个向量作为选中与否容器
    private int lastPosition = -1;		//记录上一次选中的图片位置，-1表示未选中任何图片
    private boolean multiChoose;		//表示当前适配器是否允许多选
    
    
    public ImageAdapter(Context c, boolean isMulti,Vector<MainTainIrrigationInfoBean>  mImageId){
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
    		mImage_bs.add(false);
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
		TextView textView;
        if (convertView == null)
        {
//        	imageView = new ImageView(mContext);		// 给ImageView设置资源
//            imageView.setLayoutParams(new GridView.LayoutParams(110, 120));	// 设置布局图片
//            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);		// 设置显示比例类型
            textView = new TextView(mContext);
            textView.setLayoutParams(new GridView.LayoutParams(95, 95));
        }
        else
        {
//        	imageView = (ImageView) convertView;
        	textView = (TextView) convertView;
        }
//        imageView.setImageDrawable(makeBmp(mImageIds.elementAt(position).getGates(),
//        		mImage_bs.elementAt(position)));
        textView.setText(mImageIds.elementAt(position).getGate());
        textView.setBackgroundResource(R.color.black);
        textView.setGravity(Gravity.CENTER);
        textView.setPadding(5, 5, 5, 5);
        if(mImageIds.get(position).getState() == 1){
        	textView.setClickable(false);
        	textView.setBackgroundResource(R.color.gray_1);
        }
        else if(mImage_bs.get(position)==true){
        	textView.setBackgroundResource(R.color.red);
        }else{
        	textView.setBackgroundResource(R.color.black);
        }
        return textView;
	}
	private LayerDrawable makeBmp(int id, boolean isChosen){
		Bitmap mainBmp = ((BitmapDrawable)mContext.getResources().getDrawable(id)).getBitmap();
		
		// 根据isChosen来选取对勾的图片
    	Bitmap seletedBmp;
    	if(isChosen == true){
    		seletedBmp = BitmapFactory.decodeResource(mContext.getResources(),
    				R.drawable.btncheck_yes);
    	}else{
    		seletedBmp = BitmapFactory.decodeResource(mContext.getResources(),
    				R.drawable.btncheck_no);
    	}
    	// 产生叠加图
    	Drawable[] array = new Drawable[2];
    	array[0] = new BitmapDrawable(mainBmp);
    	array[1] = new BitmapDrawable(seletedBmp);
    	LayerDrawable la = new LayerDrawable(array);
    	//setLayerInset(int index, int l, int t, int r, int b)
    	la.setLayerInset(0, 0, 0, 0, 0);
    	la.setLayerInset(1, 100, -5, 0, 80 );//右上角
//    	la.setLayerInset(0, 10, 10, 10, 10 ); 
    	 
    	return la;	//返回叠加后的图
    }

	// 修改选中的״̬
    public void changeState(int position){
    	// 多选
    	if(multiChoose == true){	
    		mImage_bs.setElementAt(!mImage_bs.elementAt(position), position);	//直接取反即可	
    		if((!mImage_bs.elementAt(position))== true){
    			mImageIds.get(position).setIstrue(false);
    		}else{
    			mImageIds.get(position).setIstrue(true);
    		}
    	}
    	// 单选
    	else{						
	    	if(lastPosition != -1)
	    		mImage_bs.setElementAt(false, lastPosition);	//取消上一次的选中状态״̬
	    	mImage_bs.setElementAt(!mImage_bs.elementAt(position), position);	//直接取反即可	
	    	lastPosition = position;		//记录本次选中的位置
    	}
    	notifyDataSetChanged();		//通知适配器进行更新
    }
    public void changeAllState(boolean all){
    	if(multiChoose ==true){
    		//��ѡ 
    		if(all){
    			for(int i=0;i<mImage_bs.size();i++){
    				mImage_bs.setElementAt(true, i);
    				mImageIds.get(i).setIstrue(true);
    			}
    			
    		}else{
    			for(int i=0;i<mImage_bs.size();i++){
    				mImage_bs.setElementAt(false, i);
    				mImageIds.get(i).setIstrue(false);
    			}
    		}
    		notifyDataSetChanged();
    	}
    }
}
