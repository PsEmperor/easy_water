package ps.emperor.easy_water.register;

import java.util.Timer;
import java.util.TimerTask;

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
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
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
	@ViewInject(R.id.etf_comfirmPass)
	private EditText etCp;
	
	@ViewInject(R.id.etf_authNum)
	private EditText etAu;
	@ViewInject(R.id.title)
	private ps.emperor.easy_water.utils.Ltitle title;
	@ViewInject(R.id.btf_auth)
	private Button bt_getCode;
	
	
	
	
	
	
	private Context context = ForgotActivity.this;
	
	private Handler mHandler = new Handler(){
		
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case PsUtils.SAVE_INFO:
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
		title.setText("忘记密码");
		bt_getCode.setOnClickListener(this);
	}



	 private int recLen = 30;

	
    /**
     * 获取倒计时timer对象
     * 
     * @param task
     * @return
     */
    public  void backTimer(){
    	bt_getCode.setText(recLen+"");
    	bt_getCode.setEnabled(false);

    	final Timer t = new Timer();
    	
	    TimerTask task = new TimerTask() {    
	        @Override    
	        public void run() {    

	           runOnUiThread(new Runnable() {      // UI thread    
	                @Override    
	                public void run() {    
	                	
	                    recLen--;    
	                    bt_getCode.setText(recLen+"");
	                    if(recLen < 0){    
	                    	t.cancel();    
	                    	bt_getCode.setText("获取验证码");
	                    	bt_getCode.setEnabled(true);
	                    	recLen = 30;
	                    }    
	                }    
	            });    
	        }    
	    };

    	t.schedule(task, 1000, 1000);
	
    }
	


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btf_auth:
			
			

			backTimer();
			
			//获取验证码
			String url  = String.format(PsUtils.urlCode_pas, PsUtils.getShared(context).getString("pass", ""));
			
			RequestParams rc = new RequestParams(url);
			
			PsUtils.send(rc, HttpMethod.GET, mHandler, context,  "获取验证中。。。",PsUtils.GET_CHECK_CODE);
			
			
			break;
		
		
		case R.id.bt_change:
			
			//etP 手机号码       etPa 手机密码
			
			
			if(!PsUtils.checkPhoneNum(etP.getText().toString())){
				AlertDialog.Builder ab = new Builder(ForgotActivity.this);
				ab.setMessage("手机号码不正确，请重新输入！！！");
				ab.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						etP.requestFocus();
						
						
					}
				});
				ab.show();
				
				return;
			}
			
			if(TextUtils.isEmpty(etPa.getText().toString())||TextUtils.isEmpty(etCp.getText().toString())){
				Toast.makeText(this, "密码不能为空！！", 0).show();
				return;
			}
			
			
			if(!etPa.getText().toString().equals(etCp.getText().toString())){
				
				
				AlertDialog.Builder b = new Builder(this);
				b.setMessage("两次输入密码不相同，请重新输入！！！");
				b.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						etP.requestFocus();
						
					}
				});
				b.show();
				
				return;
			}
			
			
			
			
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
			
			PsUtils.send(rp, HttpMethod.PUT, mHandler,context,"重设密码中。。。",PsUtils.SAVE_INFO);
			
			
			break;

		default:
			break;
		}
		
	}
	
	

}
