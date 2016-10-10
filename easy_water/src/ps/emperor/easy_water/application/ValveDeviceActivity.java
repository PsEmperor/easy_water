package ps.emperor.easy_water.application;

import java.util.ArrayList;
import java.util.List;

import org.xutils.x;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import ps.emperor.easy_water.BaseActivity;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.utils.PsUtils;
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
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
@ContentView(R.layout.activity_valvedevice)
public class ValveDeviceActivity extends BaseActivity {
	
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
		final String[] arr = { "1", "2", "3", "4","1","2","3","4"};
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


}
