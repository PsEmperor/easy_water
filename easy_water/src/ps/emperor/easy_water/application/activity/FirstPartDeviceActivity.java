package ps.emperor.easy_water.application.activity;

import java.util.ArrayList;
import java.util.List;

import org.xutils.x;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import ps.emperor.easy_water.BaseActivity;
import ps.emperor.easy_water.R;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

@ContentView(R.layout.activity_firstpartdevice)
public class FirstPartDeviceActivity extends BaseActivity {
	
	
	
	
	private String water_type;
	private String fid;
	private String type;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent data = getIntent();
		water_type = data.getStringExtra("water_type");
		fid = data.getStringExtra("fid");
		type = data.getStringExtra("ftype");
		x.view().inject(this);
	}

	
	/**
	 * 水源类型
	 * @return
	 */
	public String getWaterType(){
		return water_type;
	}
	/**
	 * 首部类别
	 * @return
	 */
	public String getType(){
		return type;
	}
	/**
	 * 首部ID
	 * @return
	 */
	public String getId(){
		return fid;
	}
	
	


	
}
