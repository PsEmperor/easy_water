package ps.emperor.easy_water.application.auxiliary;


import ps.emperor.easy_water.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class ItemWater extends RelativeLayout{
	//名称或位置
	private EditText tvl;
	//最大量程
	private EditText tvlc;
	//通道号
	private EditText tvtd;

	public ItemWater(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.water_item, this);
		
		tvl = (EditText) findViewById(R.id.tv_waterLocation);
		tvlc = (EditText) findViewById(R.id.tv_waterL);
		tvtd = (EditText) findViewById(R.id.tv_waterChannel);
		
		
		
	}
	
	/**
	 * 获取名称位置
	 */
	public String getName(){
		return tvl.getText().toString();
	}
	/**
	 * 获取量程
	 */
	public String getRange(){
		return tvlc.getText().toString();
	}
	/**
	 * 获取通道号
	 */
	public String getPassageway(){
		return tvtd.getText().toString();
	}
	
	public String getAll(){
		return "a空=="+getName()+"b空=="+getRange()+"c空=="+getPassageway();
	}
	/**
	 * 清空item的值
	 */
	public void clear(){
		tvl.setText("");
		tvlc.setText("");
		tvtd.setText("");
	}
	
	

}
