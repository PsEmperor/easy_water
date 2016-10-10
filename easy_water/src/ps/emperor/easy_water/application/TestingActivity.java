package ps.emperor.easy_water.application;

import org.xutils.x;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import ps.emperor.easy_water.BaseActivity;
import ps.emperor.easy_water.R;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
@ContentView(R.layout.activity_testing)
public class TestingActivity extends BaseActivity {
	@ViewInject(R.id.bt_edit)
	private Button bte;
	@ViewInject(R.id.tv_change)
	private Button tvc;
	@ViewInject(R.id.tv_text)
	private TextView tvt;
	@ViewInject(R.id.bt_testGo)
	private Button bt_go;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		x.view().inject(this);
		tvt.setText("测试");
	}
	

	
	@Event(R.id.tv_change)
	private void onClickTvc(View v){
		String [] arr = {"无线阀控器","首部控制器","闸门控制器"};
		eject(arr, tvc);
	}
	@Event(R.id.bt_testGo)
	private void onClickbtG(View v){
		switch (tvc.getText().toString()) {
		case "无线阀控器":
			WirelessFragment wf = new WirelessFragment();
//			Toast.makeText(TestingActivity.this,"进入无线阀控器", 0).show();
			getFragmentManager().beginTransaction().replace(R.id.container, wf).addToBackStack(null).commit();
			
			break;
		case "首部控制器":
			HeaderFragment hf = new HeaderFragment();
//			Toast.makeText(TestingActivity.this,"进入首部控制器", 0).show();
			getFragmentManager().beginTransaction().replace(R.id.container, hf).addToBackStack(null).commit();
			break;

		case "闸门控制器":
			GateFragment gf = new GateFragment();
//			Toast.makeText(TestingActivity.this,"进入闸门控制器", 0).show();
			getFragmentManager().beginTransaction().replace(R.id.container, gf).addToBackStack(null).commit();
			break;
			

		}
		
	}
	
	
	/**
	 * 弹出框
	 * @param arr
	 * @param tv
	 */
	private void eject( final String[] arr,final TextView tv) {
		AlertDialog.Builder b = new AlertDialog.Builder(TestingActivity.this).setItems(arr, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				tv.setText(arr[which]);
				switch (arr[which]) {
				case "无线阀控器":
					Toast.makeText(TestingActivity.this,"无线阀控器", 0).show();

					
					break;
				case "首部控制器":
					Toast.makeText(TestingActivity.this,"首部控制器", 0).show();
					break;

				default:
				case "闸门控制器":
					Toast.makeText(TestingActivity.this,"闸门控制器", 0).show();
					break;
					

				}
				
			}
		});
		b.show();
	}


}
