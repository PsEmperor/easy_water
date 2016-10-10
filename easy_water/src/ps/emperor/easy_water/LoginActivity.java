package ps.emperor.easy_water;

import cn.jpush.android.api.JPushInterface;
import ps.emperor.easy_water.register.ForgotActivity;
import ps.emperor.easy_water.register.RegisterActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		findView();
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
			Intent mintent = new Intent(LoginActivity.this,MainActivity.class);
			startActivity(mintent);
			finish();
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
