package ps.emperor.easy_water.application.activity;


import org.xutils.x;
import org.xutils.view.annotation.ContentView;


import ps.emperor.easy_water.BaseActivity;
import ps.emperor.easy_water.R;
import android.content.Intent;
import android.os.Bundle;

@ContentView(R.layout.activity_valvedevice1)
public class ValveDeviceActivity1 extends BaseActivity {
	

	private String s;
	private String name;
	private String mode;
	private String type;
	private String val;
	public  static int parseInt;
	public static int tag;
	public static int num = -1;
	private String id;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent upData = getIntent();
		if(upData!=null&&upData.getIntExtra("from",0)==1){
			s = upData.getStringExtra("super");
			name = upData.getStringExtra("irrName");
			mode = upData.getStringExtra("irrMode");
			type = upData.getStringExtra("classType");
			val = upData.getStringExtra("valueNum");
			tag = Integer.parseInt(val)-1;
			parseInt= Integer.parseInt(val)-1;
			id = upData.getStringExtra("ID");
			
		}
		
		
		
		x.view().inject(this);
		ValveDeviceFragment dkf = new ValveDeviceFragment();
		getFragmentManager().beginTransaction().add(R.id.coo, dkf)
				.addToBackStack(null).commit();
		
	}
	
	//获取设备id
	public String getDeviceId(){
		return id;
	}
	
	public void setNum(int tar){
		num =tar;
	}
	public int getNum(){
		return num;
	}
	
	public int getFVal(){
		return tag;
	}
	
	public void numMethod(){
		--parseInt;
	}
	
	
	public int getValueNum(){
		return parseInt;
	}
	
	public String getSuper(){
		return s;
	}
	
	public String getIrrName(){
		return name;
	}
	
	public String getIrrMode(){
		return mode;
	}
	
	public String getClassType(){
		return type;
	}
	
	@Override
	protected void onDestroy() {
		finish();
		super.onDestroy();
	}


}
