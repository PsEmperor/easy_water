package ps.emperor.easy_water;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xutils.x;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.ex.HttpException;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;

import cn.jpush.android.api.JPushInterface;
import ps.emperor.easy_water.application.entity.AppBeen;
import ps.emperor.easy_water.entity.Person;
import ps.emperor.easy_water.register.ForgotActivity;
import ps.emperor.easy_water.register.RegisterActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Xml;
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
			RequestParams params = new RequestParams("http://192.168.1.20:8080/RESTfulWS/app/IrriSys/test/FullName");    // 网址(请替换成实际的网址)  
//			 params.addQueryStringParameter("key", "value"); // 参数(请替换成实际的参数与值)  
			JSONObject js_request = new JSONObject();
			try {
//				js_request.put("groupID", "4");
//				js_request.put("irriUnitID", "2");
//				js_request.put("deviceID", "4");
//				js_request.put("area", "28.0");
//				js_request.put("irriModel", "5");
//				js_request.put("memo", "c++");
				params.setAsJsonContent(true);
				params.setBodyContent("{\"userId\":\"\",\"userName\":\"\",\"userPhone\":\"\",\"fullName\":\"二狗子\",\"authID\":\"\",\"pathtoPhoto\":\"\"}");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
//				params.setAsJsonContent(true);
//				params.setBodyContent("Content-Type: application/json"+js_request.toString());
			}//根据实际需求添加相应键值对
			
		        x.http().request(HttpMethod.PUT ,params, new CommonCallback<String>() {  
		            @Override  
		            public void onCancelled(CancelledException arg0) {  
		                  
		            }  
		  
		            // 注意:如果是自己onSuccess回调方法里写了一些导致程序崩溃的代码，也会回调道该方法，因此可以用以下方法区分是网络错误还是其他错误  
		            // 还有一点，网络超时也会也报成其他错误，还需具体打印出错误内容比较容易跟踪查看  
		            @Override  
		            public void onError(Throwable ex, boolean isOnCallback) {  
		                  
		                Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();  
		                if (ex instanceof HttpException) { // 网络错误  
		                    HttpException httpEx = (HttpException) ex;  
		                    int responseCode = httpEx.getCode();  
		                    String responseMsg = httpEx.getMessage();  
		                    String errorResult = httpEx.getResult();  
		                    Toast.makeText(LoginActivity.this, "请求失败", Toast.LENGTH_SHORT);
		                    // ...  
		                } else { // 其他错误  
		                    // ...  
		                	Toast.makeText(LoginActivity.this, "请求失败", Toast.LENGTH_SHORT);
		                }  
		                  
		            }  
		  
		            // 不管成功或者失败最后都会回调该接口  
		            @Override  
		            public void onFinished() {    
		            	Toast.makeText(LoginActivity.this, "走了网络请求", Toast.LENGTH_SHORT);
		            }  
		  
		            @Override  
		            public void onSuccess(String arg0) {  
		                  Toast.makeText(LoginActivity.this, "请求成功", Toast.LENGTH_SHORT);
		                  btLogin.setText(arg0);
		            }  
		        }); 
//			Toast.makeText(this, "忘记密码", 0).show();
//			Intent intent1 = new Intent(LoginActivity.this,ForgotActivity.class);
//			startActivity(intent1);
			break;
		case R.id.tv_register:
			Toast.makeText(this, "新用户注册", 0).show();
			Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
			startActivity(intent);
			break;

		}
	}
}
