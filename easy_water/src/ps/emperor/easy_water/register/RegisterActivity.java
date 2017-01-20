package ps.emperor.easy_water.register;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;

import com.google.gson.JsonObject;

import ps.emperor.easy_water.BaseActivity;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.utils.PsUtils;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends BaseActivity implements OnClickListener {
	
	
	private EditText etCf,etPa,etAu,etP;
	private Button btAu,btrg;
	private TextView tvPro;
	private CheckBox cbPro;
	private int s  = 60;
	private Context context = RegisterActivity.this;
	private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case PsUtils.SEND_REGISTER:
				String result = (String) msg.obj;
				if(result.equals("1")){
					Toast.makeText(context, "注册成功", 0).show();
				}else if(result.equals("0")){
					Toast.makeText(context, "注册失败", 0).show();
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
		setContentView(R.layout.activity_register);
		findView();

		
	}
	
	
	/**
	 * 初始化控件
	 */
	private void findView() {

		
		etCf = (EditText) findViewById(R.id.et_comfirmPass);
		etPa = (EditText) findViewById(R.id.et_pass);
		etAu = (EditText) findViewById(R.id.et_authNum);
		etP = (EditText) findViewById(R.id.et_phone);
		
		btAu = (Button) findViewById(R.id.bt_auth);
		btrg = (Button) findViewById(R.id.bt_register);
		tvPro = (TextView) findViewById(R.id.tv_protocol);
		cbPro = (CheckBox) findViewById(R.id.cb_protocol);
		
		btAu.setOnClickListener(this);
		btrg.setOnClickListener(this);
		tvPro.setOnClickListener(this);
		cbPro.setOnClickListener(this);
		
	}


	//验证码加载秒
	public void AuthNum(){
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (s > 0) {
					s--;
					mHandler.post(new Runnable() {

						@Override
						public void run() {
//							btp.setText(s + "秒");

						}
					});

					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				mHandler.post(new Runnable() {

					@Override
					public void run() {
//						btp.setText("发送验证信息");
//						btp.setClickable(true);
//						tv_dw.setVisibility(View.INVISIBLE);
						s = 60;
					}
				});

			}
		}).start();

	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_auth:
			//点击获取验证码
			
			break;
		case R.id.bt_register:
			String user = etP.getText().toString();
			String password =  etPa.getText().toString();
			String code = etAu.getText().toString();
			
			RequestParams rp = new RequestParams(PsUtils.urlRegister);
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
			


			
			//注册
			PsUtils.send(rp, HttpMethod.POST, mHandler,context);
			
			
			break;
		case R.id.tv_protocol:
			//跳转协议界面
			Intent pintent = new Intent(RegisterActivity.this,ProtocolActivity.class);
			startActivity(pintent);
			
			
			
			break;
		case R.id.cb_protocol:
			//是否勾选协议
			break;

	
		}
		
	}

}
