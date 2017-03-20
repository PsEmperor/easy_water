package ps.emperor.easy_water;

import android.os.Bundle;
import cn.jpush.android.api.JPushInterface;


public class MainActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
	}
	
	
	@Override
	protected void onResume() {
	super.onResume();
	JPushInterface.onResume(this);
	}

	@Override
	protected void onPause() {
	super.onPause();
	JPushInterface.onPause(this);
	}


}
