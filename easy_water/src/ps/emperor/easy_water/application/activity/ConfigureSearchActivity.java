package ps.emperor.easy_water.application.activity;

import org.xutils.x;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import ps.emperor.easy_water.BaseActivity;
import ps.emperor.easy_water.R;
import android.os.Bundle;

@ContentView(R.layout.activity_configuresearch)
public class ConfigureSearchActivity extends BaseActivity {
	
	@ViewInject(R.id.title)
	private ps.emperor.easy_water.utils.Ltitle title;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		x.view().inject(this);
		control();
	}

	private void control() {
		title.setText("搜索");
		
	}

}
