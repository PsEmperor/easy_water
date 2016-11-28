package ps.emperor.easy_water;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xutils.x;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;

import cn.jpush.android.api.JPushInterface;
import ps.emperor.easy_water.application.AppBeen;
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
			RequestParams params = new RequestParams("http://192.168.1.20:8080/RESTfulWS/app/IrriSys/text/photo");    // 网址(请替换成实际的网址)  
//			 params.addQueryStringParameter("key", "value"); // 参数(请替换成实际的参数与值)  
	          
		        x.http().get(params, new CommonCallback<String>() {  
		  
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
	public static List<Person> readXML(InputStream inStream) {

		XmlPullParser parser = Xml.newPullParser();

		try {

		parser.setInput(inStream, "UTF-8");

		int eventType = parser.getEventType();

		Person currentPerson = null;

		List<Person> persons = null;

		while (eventType != XmlPullParser.END_DOCUMENT) {

		switch (eventType) {

		case XmlPullParser.START_DOCUMENT://文档开始事件,可以进行数据初始化处理

		persons = new ArrayList<Person>();

		break;

		case XmlPullParser.START_TAG://开始元素事件

		String name = parser.getName();

		if (name.equalsIgnoreCase("name")) {

		currentPerson = new Person();

		currentPerson.setName(new String(parser.getAttributeValue(null, "name")));

		} else if (currentPerson != null) {

		if (name.equalsIgnoreCase("name")) {

		currentPerson.setName(parser.nextText());// 如果后面是Text元素,即返回它的值

		} 

		}

		break;

		case XmlPullParser.END_TAG://结束元素事件

		if (parser.getName().equalsIgnoreCase("person") && currentPerson != null) {

		persons.add(currentPerson);

		currentPerson = null;

		}

		break;

		}

		eventType = parser.next();

		}

		inStream.close();

		return persons;

		} catch (Exception e) {

		e.printStackTrace();

		}

		return null;

		}
}
