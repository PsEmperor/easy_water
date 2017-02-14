package ps.emperor.easy_water.fragment;

import java.io.UnsupportedEncodingException;


import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.ex.HttpException;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;

import com.google.gson.Gson;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.utils.CheckUtil;
import ps.emperor.easy_water.utils.SharedUtils;
import ps.emperor.easy_water.utils.URL;
import ps.emperor.easy_water.view.MainActionBar;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 修改手机号
 * 
 * @author 毛国江
 * @version 2016-5-16 下午16:50
 */
@SuppressLint("NewApi")
public class MineUserTelFragment extends Fragment implements OnClickListener {
	private LayoutInflater mInflater;
	private MainActionBar actionBar;
	private TextView verification_code,btn_control_true,text_mine_user_info_tel;
	private EditText edit_mine_user_info_tel,verification_codes;
	private ProgressDialog progressDialog;
	private String[] strs;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflater = inflater;
		View view = inflater.inflate(R.layout.fragment_mine_user_tel,
				container, false);
		actionBar = (MainActionBar) view.findViewById(R.id.actionbar_user_tel);
		actionBar.setLeftIcon(R.drawable.btn_back_selector);
		actionBar.setRightGone();
		actionBar.setTitle("修改手机号");
		actionBar.setActionBarOnClickListener(this);
		
		verification_code = (TextView) view.findViewById(R.id.verification_code);
		verification_code.setOnClickListener(this);
		btn_control_true = (TextView) view.findViewById(R.id.btn_apply_irrigate_unit_control_true);
		btn_control_true.setOnClickListener(this);
		edit_mine_user_info_tel = (EditText) view.findViewById(R.id.edit_mine_user_info_tel);
		text_mine_user_info_tel = (TextView) view.findViewById(R.id.text_mine_user_info_tel);
		verification_codes = (EditText) view.findViewById(R.id.verification_codes);
		text_mine_user_info_tel = (TextView) view.findViewById(R.id.text_mine_user_info_tel);
		strs = text_mine_user_info_tel.getText().toString().split("[：]");
		return view;
	}

	@Override
	public void onClick(View v) {
		FragmentManager fgManager = getFragmentManager();
		FragmentTransaction transaction = fgManager.beginTransaction();
		switch (v.getId()) {
		case R.id.acitionbar_left:
			MineUserInfoFragment fragment = new MineUserInfoFragment();
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			transaction.setCustomAnimations(R.anim.slide_fragment_horizontal_right_in, R.anim.slide_fragment_horizontal_left_out);
			transaction.replace(R.id.fl, fragment, "main");
			transaction.commit();
			break;
		case R.id.verification_code:
			String str1 = edit_mine_user_info_tel.getText().toString();
			String str2 = "3";
			try {
				str1 = java.net.URLEncoder.encode(str1, "UTF-8");
				str2 = java.net.URLEncoder.encode(str2, "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			RequestParams param3 = new RequestParams(URL.getAuthCode
					+ str1+"/"+str2); // 网址(请替换成实际的网址)
			// params.addQueryStringParameter("key", "value"); // 参数(请替换成实际的参数与值)
			progressDialog = ProgressDialog.show(getActivity(), "Loading...",
					"Please wait...", true, false);
			JSONObject js_request2 = new JSONObject();
			try {
				param3.setAsJsonContent(true);
			} catch (Exception e) {
				e.printStackTrace();
				param3.setAsJsonContent(true);
			}// 根据实际需求添加相应键值对

			x.http().request(HttpMethod.GET, param3, new CommonCallback<String>() {
				@Override
				public void onCancelled(CancelledException arg0) {

				}

				// 注意:如果是自己onSuccess回调方法里写了一些导致程序崩溃的代码，也会回调道该方法，因此可以用以下方法区分是网络错误还是其他错误
				// 还有一点，网络超时也会也报成其他错误，还需具体打印出错误内容比较容易跟踪查看
				@Override
				public void onError(Throwable ex, boolean isOnCallback) {

					Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG)
							.show();
					if (ex instanceof HttpException) { // 网络错误 
						HttpException httpEx = (HttpException) ex;
						int responseCode = httpEx.getCode();
						String responseMsg = httpEx.getMessage();
						String errorResult = httpEx.getResult();
						Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT);
						// ...
						progressDialog.dismiss();
					} else { // 其他错误 
						// ...
						Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT);
						progressDialog.dismiss();
					}

				}

				// 不管成功或者失败最后都会回调该接口
				@Override
				public void onFinished() {
				}

				@Override
				public void onSuccess(String arg0) {
					Toast.makeText(getActivity(), "请求成功", Toast.LENGTH_SHORT);
					Gson gson = new Gson();
					progressDialog.dismiss();
				}
			});
			break;
		case R.id.btn_apply_irrigate_unit_control_true:
			if(CheckUtil.IsEmpty(edit_mine_user_info_tel.getText().toString())){
				Toast.makeText(getActivity(), "请输入新手机号码！", Toast.LENGTH_SHORT).show();
			}else if(CheckUtil.IsEmpty(verification_codes.getText().toString())){
				Toast.makeText(getActivity(), "请输入验证码！", Toast.LENGTH_SHORT).show();
			}else{
			RequestParams param2 = new RequestParams(URL.updatePhoneNum);  // 网址(请替换成实际的网址) 
			progressDialog = ProgressDialog.show(getActivity(), "Loading...",
					"Please wait...", true, false);
			JSONObject js_request = new JSONObject();
			try {
				param2.setAsJsonContent(true);
				js_request.put("phoneNum", edit_mine_user_info_tel.getText().toString());
				js_request.put("verificationCode",verification_codes.getText().toString());
				js_request.put("oldPhoneNum",strs[1]);
				param2.setBodyContent(js_request.toString());
			} catch (Exception e) {
				e.printStackTrace();
				param2.setAsJsonContent(true);
			}//根据实际需求添加相应键值对
			
		        x.http().request(HttpMethod.PUT ,param2, new CommonCallback<String>() {  
		            @Override  
		            public void onCancelled(CancelledException arg0) {  
		                  
		            }  
		  
		         // 注意:如果是自己onSuccess回调方法里写了一些导致程序崩溃的代码，也会回调道该方法，因此可以用以下方法区分是网络错误还是其他错误  
		            // 还有一点，网络超时也会也报成其他错误，还需具体打印出错误内容比较容易跟踪查看  
		            @Override  
		            public void onError(Throwable ex, boolean isOnCallback) {  
		                  
		                Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();  
		                if (ex instanceof HttpException) { // 网络错误    
		                    HttpException httpEx = (HttpException) ex;  
		                    int responseCode = httpEx.getCode();  
		                    String responseMsg = httpEx.getMessage();  
		                    String errorResult = httpEx.getResult();  
		                    Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT);
		                    // ...  
		                    progressDialog.dismiss();
		                } else { // 其他错误    
		                    // ...  
		                	Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT);
		                	progressDialog.dismiss();
		                }  
		                  
		            }  
		  
		         // 不管成功或者失败最后都会回调该接口  
		            @Override  
		            public void onFinished() {    
		            }  
		  
		            @Override  
		            public void onSuccess(String arg0) { 
		            	  Toast.makeText(getActivity(), "成功", Toast.LENGTH_SHORT).show(); 
		                  progressDialog.dismiss();
		            }  
		        }); 
			}
			break;
		default:
			break;
		}
	}
}
