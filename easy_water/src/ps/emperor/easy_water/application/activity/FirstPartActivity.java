package ps.emperor.easy_water.application.activity;

import org.xutils.x;
import org.xutils.view.annotation.ContentView;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import ps.emperor.easy_water.BaseActivity;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.utils.PsUtils;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

@ContentView(R.layout.activity_firstpart)
public class FirstPartActivity extends BaseActivity {
	
	private TextView tvt;
	private Button bte;
	private LocationManager lm;
	private Criteria c;
	private String bestProvider;
	@ViewInject(R.id.tv_location)
	private TextView tv_lo;
	@ViewInject(R.id.tv_change)
	private Button tvc;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		x.view().inject(this);
		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		
		findView();
		PsUtils.readGPS(lm, tv_lo);
		
	}

	/**
	 * 初始化控件
	 */
	private void findView() {
		tvt = (TextView) findViewById(R.id.tv_text);
		bte = (Button) findViewById(R.id.bt_edit);
		
		tvt.setText("灌溉单元配置");
		bte.setVisibility(View.VISIBLE);
		bte.setText("保存");
	}
	
	/**
	 * 进入阀控器配置
	 * @param v
	 */
//	@Event(R.id.bt_inConDevice)
	private void inValueDevice(View v){
		startActivity(new Intent(this,ValveDeviceActivity.class));
		finish();
	}
	
	/**
	 * 进入阀控器配置
	 * @param v
	 */
//	@Event(R.id.bt_inFirstDevice)
	private void inFirstDevice(View v){
		startActivity(new Intent(this,FirstPartDeviceActivity.class));
	}
	
	@Event(R.id.tv_change)
	private void onClickTvc(View v){
		String [] arr = {"阀控器","首部控制器","闸门控制器"};
		eject(arr, tvc);
	}
	
	
	/**
	 * 弹出框
	 * @param arr
	 * @param tv
	 */
	private void eject( final String[] arr,final TextView tv) {
		AlertDialog.Builder b = new AlertDialog.Builder(FirstPartActivity.this).setItems(arr, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				tv.setText(arr[which]);
				switch (arr[which]) {
				case "阀控器":
					Toast.makeText(FirstPartActivity.this,"阀控器", 0).show();

					
					break;
				case "首部控制器":
					Toast.makeText(FirstPartActivity.this,"首部控制器", 0).show();
					break;

				default:
				case "闸门控制器":
					Toast.makeText(FirstPartActivity.this,"闸门控制器", 0).show();
					break;
					

				}
				
			}
		});
		b.show();
	}
	
/*	@Event(R.id.bt_nextStep)
	private void onClickbtG(View v){
		switch (tvc.getText().toString()) {
		case "阀控器":
			startActivity(new Intent(this,ValveDeviceActivity.class));
//			finish();
			
			break;
		case "首部控制器":
			startActivity(new Intent(this,FirstPartDeviceActivity.class));
			break;

		case "闸门控制器":
			Intent i2 = new Intent(
					this,
					WaterGateActivity.class);
			startActivity(i2);
			break;
			

		}
		
	}*/

	
	



}
