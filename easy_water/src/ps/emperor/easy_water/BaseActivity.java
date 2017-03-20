package ps.emperor.easy_water;


import org.xutils.x;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * 基础activity
 * @author purplesea
 *
 */

public class BaseActivity extends Activity {
	
	private long lastClickTime =0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("BaseActivity", getClass().getSimpleName());
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		ActivityCollector.addAct(this);
//		x.Ext.init(getApplication());
		

	}
	
	@Override
	protected void onDestroy() {
		ActivityCollector.removeAct(this);
		super.onDestroy();
	}
	
	@Override
	public void onBackPressed() {
		if(lastClickTime <= 0 ){
			Toast.makeText(this,R.string.exit_app,0).show();
			lastClickTime = System.currentTimeMillis();
		}else {  
			long currentClickTime = System.currentTimeMillis();
			if(currentClickTime - lastClickTime < 1000){
				ActivityCollector.removeAll();
			}else{
				Toast.makeText(this,R.string.exit_app,0).show();
				lastClickTime = currentClickTime;
			}
		}
	}

}
