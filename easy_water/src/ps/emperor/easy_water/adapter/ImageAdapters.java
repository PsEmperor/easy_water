package ps.emperor.easy_water.adapter;

import java.util.Vector;



import ps.emperor.easy_water.R;
import ps.emperor.easy_water.entity.ApplyIrrigateSingleValveBean;
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

@SuppressLint("ResourceAsColor")
public class ImageAdapters extends BaseAdapter{    
    private Context mContext;			// 定义Context
    private Vector<ApplyIrrigateSingleValveBean> mImageIds;	// 定义一个向量作为图片源
    
    
    public ImageAdapters(Context c, Vector<ApplyIrrigateSingleValveBean>  mImageId){
    	mContext = c;
    	mImageIds = mImageId;
    }
//    	// װ����Դ 
//    	mImageIds.add(R.drawable.item1);
//    	mImageIds.add(R.drawable.item2);
//    	mImageIds.add(R.drawable.item3);
//    	mImageIds.add(R.drawable.item4);
//    	mImageIds.add(R.drawable.item5);
//    	mImageIds.add(R.drawable.item6);
    	
    
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

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
//		ImageView imageView;
		TextView textView;
		TextView textView1;
        if (convertView == null)
        {
//        	imageView = new ImageView(mContext);		// 给ImageView设置资源
//            imageView.setLayoutParams(new GridView.LayoutParams(110, 120));	// 设置布局图片
//            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);		// 设置显示比例类型
            textView = new TextView(mContext);
            textView.setLayoutParams(new GridView.LayoutParams(85, 85));
            textView1 = new TextView(mContext);
            textView1.setScaleY(20);
            
        }
        else
        {
//        	imageView = (ImageView) convertView;
        	textView = (TextView) convertView;
        	textView1 = (TextView) convertView;
        }
//        imageView.setImageDrawable(makeBmp(mImageIds.elementAt(position).getGates(),
//        		mImage_bs.elementAt(position)));
        textView.setText(mImageIds.elementAt(position).getValve());
        textView.setBackgroundResource(R.color.red);
        textView.setGravity(Gravity.CENTER);
        textView.setPadding(5, 5, 5, 5);
        textView1.setText("王");
        textView1.setBackgroundResource(R.color.blue);
        textView1.setGravity(Gravity.RIGHT);
        return textView;
	}

}
