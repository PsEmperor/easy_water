package ps.emperor.easy_water.register;

import ps.emperor.easy_water.BaseActivity;
import ps.emperor.easy_water.R;
import android.os.Bundle;
import android.widget.TextView;

public class ProtocolActivity extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_protocol);
		TextView  tv = (TextView) findViewById(R.id.tv_text);
		tv.setText("用户协议");
	}

}
