package ps.emperor.easy_water;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;

import cn.jpush.android.api.JPushInterface;
import ps.emperor.easy_water.register.ForgotActivity;
import ps.emperor.easy_water.register.RegisterActivity;
import ps.emperor.easy_water.utils.PsUtils;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;

/**
 * 登录界面activity
 * 
 * @author purplesea
 * 
 */
public class LoginActivity extends BaseActivity implements OnClickListener {

	private EditText etUser, etPa;
	private CheckBox cbSave;
	private Button btLogin;
	private TextView tvForgot, tvRegister;
	private Context context = LoginActivity.this;
	private SharedPreferences sp;
	
	
	private Handler mHandler = new Handler(){
		
	

		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case PsUtils.LOGIN_IN :
				
				String result = (String) msg.obj;
				
				if(result.equals("0")){
					Toast.makeText(context, "密码错误,请重新登录！", 0).show();
				}else if(result.equals("-1")){
					Toast.makeText(context, "用户名不存在,请重新登录！", 0).show();
				}else{
					Toast.makeText(context, "登录成功", 0).show();
					
					//保存账号密码、是否保存密码状态
					
					
					Editor e = sp.edit();
					e.putString("user", user);
					e.putString("pass",password);
					e.putString("userId", result);
					e.putBoolean("checked", cbSave.isChecked());
					e.commit();
					System.out.println("userId====----------------=======:"+result);
					
					
					Intent mintent = new Intent(LoginActivity.this,MainActivity.class);
					startActivity(mintent);
					finish();
				}
				
				
				break;
			case PsUtils.SEND_REGISTER_ERROR:
				
				Toast.makeText(context, "登录异常", 0).show();
				
				break;

			default:
				break;
			}
			
		};
		
	};
	private ProgressDialog pd;
	//账号
	private String user;
	//密码
	private String password;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sp = getSharedPreferences("share_date", 0);
		setContentView(R.layout.activity_login);
		findView();
		
		if(cbSave.isChecked()){
			etUser.setText(sp.getString("user", ""));
			etPa.setText(sp.getString("pass", ""));
		}
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

	/**
	 * 控件初始化
	 */
	private void findView() {
		etUser = (EditText) findViewById(R.id.et_user);
		etPa = (EditText) findViewById(R.id.et_password);
		cbSave = (CheckBox) findViewById(R.id.cb_save);
		btLogin = (Button) findViewById(R.id.bt_go);
		tvForgot = (TextView) findViewById(R.id.tv_forgot);
		tvRegister = (TextView) findViewById(R.id.tv_register);
			cbSave.setChecked(sp.getBoolean("checked", false));
		
		// etUser.setOnClickListener(this);
		// etPa.setOnClickListener(this);
		cbSave.setOnClickListener(this);
		btLogin.setOnClickListener(this);
		tvForgot.setOnClickListener(this);
		tvRegister.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// case R.id.et_user:
		// //用户名
		// Toast.makeText(this, "用户名被点击了", 0).show();
		// break;
		// case R.id.et_password:
		// //密码
		// Toast.makeText(this, "密码被点击了", 0).show();
		// break;
		case R.id.cb_save:
			Toast.makeText(this, "checkbox被点击了", 0).show();
			break;
		case R.id.bt_go:
			Toast.makeText(this, "登录按钮", 0).show();
//			Intent mintent = new Intent(LoginActivity.this,MainActivity.class);
//			startActivity(mintent);
			
			user = etUser.getText().toString().trim();
			password = etPa.getText().toString().trim();
			
			RequestParams rp = new RequestParams(PsUtils.urlLogin);
			rp.setAsJsonContent(true);
			JSONObject jo = new JSONObject();
			try {
				jo.put("userName", user);
				jo.put("password", password);
				rp.setBodyContent(jo.toString());
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			PsUtils.send(rp, HttpMethod.POST, mHandler,context,"登录中。。。",PsUtils.LOGIN_IN);

			break;
		case R.id.tv_forgot:

			Toast.makeText(this, "忘记密码", 0).show();
			Intent intent1 = new Intent(LoginActivity.this,ForgotActivity.class);
			startActivity(intent1);
			break;
		case R.id.tv_register:
			Toast.makeText(this, "新用户注册", 0).show();
			Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
			startActivity(intent);
			break;

		}
	}



}
