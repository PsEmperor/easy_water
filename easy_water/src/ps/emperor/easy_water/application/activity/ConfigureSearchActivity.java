package ps.emperor.easy_water.application.activity;

import org.xutils.x;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import ps.emperor.easy_water.BaseActivity;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.utils.PsUtils;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

@ContentView(R.layout.activity_configuresearch)
public class ConfigureSearchActivity extends BaseActivity implements OnClickListener {
	
	private Context context = ConfigureSearchActivity.this;
	
	private Handler handler  = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case PsUtils.GET_SEARCH:
				
				break;
			case PsUtils.SEND_REGISTER_ERROR:
				
				break;

			default:
				break;
			}
		}
	};
	
	@ViewInject(R.id.title)
	private ps.emperor.easy_water.utils.Ltitle title;
	@ViewInject(R.id.iv_search)
	private ImageView ivs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		x.view().inject(this);
		control();
	}

	private void control() {
		title.setText("搜索");
		ivs.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.iv_search:
//			PsUtils.urlSearch
			RequestParams rp  = new RequestParams(PsUtils.urlSearch);
			PsUtils.send(rp, HttpMethod.GET, handler, context,"搜索中。。。",PsUtils.GET_SEARCH);
			
			break;
//		case:
//			break;
		}
		
	}

}
