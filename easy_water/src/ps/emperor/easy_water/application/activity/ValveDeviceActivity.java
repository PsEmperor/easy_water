package ps.emperor.easy_water.application.activity;

import java.util.ArrayList;
import java.util.List;

import org.xutils.x;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import ps.emperor.easy_water.BaseActivity;
import ps.emperor.easy_water.R;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
@ContentView(R.layout.activity_valvedevice)
public class ValveDeviceActivity extends BaseActivity {
	@ViewInject(R.id.ll_1_td)
	private LinearLayout ll1_td;
	@ViewInject(R.id.ll_1_mj)
	private LinearLayout ll1_mj;
	//判断通道1和面积是否隐藏
	private boolean t1;
	
	@ViewInject(R.id.ll_2_td)
	private LinearLayout ll2_td;
	@ViewInject(R.id.ll_2_mj)
	private LinearLayout ll2_mj;
	private boolean t2;
	
	@ViewInject(R.id.ll_3_td)
	private LinearLayout ll3_td;
	@ViewInject(R.id.ll_3_mj)
	private LinearLayout ll3_mj;
	private boolean t3;
	
	@ViewInject(R.id.ll_4_td)
	private LinearLayout ll4_td;
	@ViewInject(R.id.ll_4_mj)
	private LinearLayout ll4_mj;
	private boolean t4;
	
	private TextView tvt;
	private Button bte;
	@ViewInject(R.id.sp_fm)
	private Spinner sp_fm;
	@ViewInject(R.id.sp_gd)
	private Spinner sp_gd;
	@ViewInject(R.id.tv_td)
	private TextView tv_td;
	private static int tag;
	private NumberPicker np;
//	@ViewInject(R.id.tv_location)
//	private TextView tv_lo;
	private LocationManager lm;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		x.view().inject(this);
		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//		PsUtils.readGPS(lm,tv_lo);
		findView();
		initfm();
		initfg();
	}
	
	@Event(R.id.tv_td)
	private void tdOnClick(View v){
		np = new NumberPicker(this);
		final String[] arr = {"0", "1", "2", "3", "4","0","1","2","3","4"};
		np.setDisplayedValues(arr);
		np.setMinValue(0);
		np.setMaxValue(arr.length-1);
		if (tag != 0) {
		np.setValue(tag);
		}
		// np.setValue(4);
		// 关闭可编辑状态
		np.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
		AlertDialog b = new Builder(this).setView(np)
		.setPositiveButton("确定", new OnClickListener() {
		// .setNegativeButton("取消", null)
		@Override
		public void onClick(DialogInterface dialog, int which) {
		// 获取当前选中的角标位并记录
		tag = np.getValue();
		// str[tag] 取得才是角标对应数组中的值
		tv_td.setText("\t\t\t\t"+arr[tag]);
		System.out.println("11111111111111111========================"+arr[tag]);
		tagIf(Integer.parseInt(arr[tag]));

		}
		}).show();
		// 设置弹出框的大小
		b.getWindow().setLayout(500, 500);
		
		
		
	}
	
	/**
	 * 初始化分干管
	 */
	private void initfg() {
		List<String> list = new ArrayList<String>();
		for(int i =1;i<11;i++){
			list.add("\t\t\t\t\t\t\t"+i+"分干");
		}

		
		ArrayAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
		adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		sp_gd.setAdapter(adapter);
		
	}

	/**
	 * 初始化设备类别
	 */
	private void initfm() {
		List<String> list = new ArrayList<String>();
		list.add("\t\t\t\t短信阀控器");
		list.add("\t\t\t\t无线阀控器");
		list.add("\t\t\t\t有线阀控器");
		
		ArrayAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
		adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		sp_fm.setAdapter(adapter);
	}

	/**
	 * 初始化控件
	 */
	private void findView() {
		tvt = (TextView) findViewById(R.id.tv_text);
		bte = (Button) findViewById(R.id.bt_edit);
		
		tvt.setText("阀控器配置");
		bte.setVisibility(View.VISIBLE);
		bte.setText("保存");
	}
	
	/*
	 * 判断通道是否隐藏方法
	 */
	private void tdIf(){
		if(t1){
			ll1_td.setVisibility(View.VISIBLE);
			ll1_mj.setVisibility(View.VISIBLE);
		}else{
			ll1_td.setVisibility(View.GONE);
			ll1_mj.setVisibility(View.GONE);
		}
		
		if(t2){
			ll2_td.setVisibility(View.VISIBLE);
			ll2_mj.setVisibility(View.VISIBLE);
		}else{
			ll2_td.setVisibility(View.GONE);
			ll2_mj.setVisibility(View.GONE);
		}
		if(t3){
			ll3_td.setVisibility(View.VISIBLE);
			ll3_mj.setVisibility(View.VISIBLE);
		}else{
			ll3_td.setVisibility(View.GONE);
			ll3_mj.setVisibility(View.GONE);
		}
		if(t4){
			ll4_td.setVisibility(View.VISIBLE);
			ll4_mj.setVisibility(View.VISIBLE);
		}else{
			ll4_td.setVisibility(View.GONE);
			ll4_mj.setVisibility(View.GONE);
		}
		
	}
	
	/**
	 * 根据tag的数量判断td是否隐藏
	 */
	private void tagIf(int tag){
		switch (tag) {
		case 0:
			t1 = false;
			t2 = false;
			t3 = false;
			t4 = false;
			
			break;
		case 1:
			t1 = true;
			t2 = false;
			t3 = false;
			t4 = false;
			
			break;
		case 2:
			t1 = true;
			t2 = true;
			t3 = false;
			t4 = false;
			
			break;
		case 3:
			t1 = true;
			t2 = true;
			t3 = true;
			t4 = false;
			
			break;
		case 4:
			t1 = true;
			t2 = true;
			t3 = true;
			t4 = true;
			
			break;
			
		

		default:
			break;
		}
		
		tdIf();
		
	}
	


}
