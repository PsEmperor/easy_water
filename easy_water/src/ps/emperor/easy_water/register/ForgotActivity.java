package ps.emperor.easy_water.register;

import org.json.JSONException;
import org.json.JSONObject;
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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

@ContentView(R.layout.activity_forgot)
public class ForgotActivity extends BaseActivity implements OnClickListener {
	@ViewInject(R.id.bt_change)
	private Button btc;
	@ViewInject(R.id.etf_phone)
	private EditText etP;
	@ViewInject(R.id.etf_pass)
	private EditText etPa;
	@ViewInject(R.id.etf_authNum)
	private EditText etAu;
	@ViewInject(R.id.title)
	private ps.emperor.easy_water.utils.Ltitle title;
	
	
	
	
	
	
	private Context context = ForgotActivity.this;
	
	private Handler mHandler = new Handler(){
		
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case PsUtils.SEND_REGISTER:
				String result = (String) msg.obj;
				if(result.equals("1")){
					Toast.makeText(context, "重置成功", 0).show();
				}else if(result.equals("0")){
					Toast.makeText(context, "重置失败", 0).show();
				}
				
				
				break;
			case PsUtils.SEND_REGISTER_ERROR:
				
				Toast.makeText(context, "注册异常", 0).show();
				
				break;

			default:
				break;
			}
			
		};
		
	};
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		x.view().inject(this);
		control();
	}

	
	


	/**
	 * 控制方法
	 */
	private void control() {
		btc.setOnClickListener(this);
		title.setText("重置密码");
	}



	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_change:
			//重置
			
			
			String user = etP.getText().toString();
			String password =  etPa.getText().toString();
			String code = etAu.getText().toString();
			
			RequestParams rp = new RequestParams(PsUtils.urlPassword);
			rp.setAsJsonContent(true);
//			rp.addHeader("Content-Type", "application/json");
			JSONObject jo = new JSONObject();
			try {
				jo.put("phoneNum", user);
				jo.put("password", password);
				jo.put("verificationCode",code );
				rp.setBodyContent(jo.toString());
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			PsUtils.send(rp, HttpMethod.PUT, mHandler,context);
			
			
			break;

		default:
			break;
		}
		
	}
	
	

}
