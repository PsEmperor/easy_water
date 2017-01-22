package ps.emperor.easy_water.fragment;

import java.io.UnsupportedEncodingException;

import java.util.ArrayList;
import java.util.List;

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
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.entity.FindDisEquInfoOneBean;
import ps.emperor.easy_water.entity.UserReleDisInfoBeanAdd;
import ps.emperor.easy_water.entity.FindDisEquInfoOneBean.infoList;
import ps.emperor.easy_water.entity.UserReleDisInfoBean;
import ps.emperor.easy_water.utils.SharedUtils;
import ps.emperor.easy_water.utils.URL;
import ps.emperor.easy_water.view.MainActionBar;
import ps.emperor.easy_water.view.MainActionBars;

/**
 * 闸门信息（维护）
 * 
 * @author 毛国江
 * @version 2016-6-7 上午10:27
 */

@SuppressLint("NewApi")
public class MainTainGateInfoFragment extends Fragment implements
		OnClickListener {

	private LayoutInflater mInflater;
	private MainActionBars actionBar;
	private TextView gate_info_category,gate_info_id,gate_info_e,gate_info_n,gate_info_units,gate_info_superiro,gate_info_hole;
	private EditText gate_info_name,gate_info_area;
	private List<infoList> beens;
	private ProgressDialog progressDialog;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflater = inflater;
		View view = inflater.inflate(R.layout.fragment_maintain_gate_info,
				container, false);
		actionBar = (MainActionBars) view
				.findViewById(R.id.actionbar_maintain_gate_info);
		actionBar.setLeftIcon(R.drawable.btn_back_selector);
		actionBar.setRightText("保存");
		actionBar.setTitle("闸门信息维护");
		actionBar.setActionBarOnClickListener(this);
		
		gate_info_category = (TextView) view.findViewById(R.id.text_maintain_gate_info_category);
		gate_info_id = (TextView) view.findViewById(R.id.text_maintain_gate_info_id);
		gate_info_e = (TextView) view.findViewById(R.id.text_maintain_gate_info_e);
		gate_info_n = (TextView) view.findViewById(R.id.text_maintain_gate_info_n);
		gate_info_name = (EditText) view.findViewById(R.id.text_maintain_gate_info_name);
		gate_info_units = (TextView) view.findViewById(R.id.text_maintain_gate_info_units);
		gate_info_superiro = (TextView) view.findViewById(R.id.text_maintain_gate_info_superiro);
		gate_info_area = (EditText) view.findViewById(R.id.text_maintain_gate_info_area);
		gate_info_hole = (TextView) view.findViewById(R.id.text_maintain_gate_info_hole);
		
		String str1 = (String) SharedUtils.getParam(getActivity(), "DisEquID", "");
		try {
			str1 = java.net.URLEncoder.encode(str1,"UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		RequestParams param3 = new RequestParams(URL.findDisEquInfoOne+str1);  // 网址(请替换成实际的网址) 
//		 params.addQueryStringParameter("key", "value"); // 参数(请替换成实际的参数与值)   
		progressDialog = ProgressDialog.show(getActivity(), "Loading...",
				"Please wait...", true, false);
		JSONObject js_request2 = new JSONObject();
		try {
			param3.setAsJsonContent(true);
		} catch (Exception e) {
			e.printStackTrace();
			param3.setAsJsonContent(true);
		}//根据实际需求添加相应键值对
		
	        x.http().request(HttpMethod.GET ,param3, new CommonCallback<String>() {  
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
	                  Toast.makeText(getActivity(), "请求成功", Toast.LENGTH_SHORT);
	                  Gson gson = new Gson();
	                  System.out.println(arg0);
	                  FindDisEquInfoOneBean fromJson = gson.fromJson(arg0, FindDisEquInfoOneBean.class);
//	                  authorizedBeen = new AuthorizedBeen();
//	                  authorizedBeen = gson.fromJson(arg0, AuthorizedBeen.class);
	                  beens = fromJson.getAuthNameList();
	                  for (infoList authNameListBean : beens) {
	                	authNameListBean.getAuthName();
	                	authNameListBean.getArea();
	                	gate_info_category.setText(beens.get(0).getDisEquType());
	                	gate_info_id.setText(beens.get(0).getDisEquID());
	                	gate_info_e.setText(beens.get(0).getLongitude());
	                	gate_info_n.setText(beens.get(0).getLatitude());
	                	gate_info_name.setText(beens.get(0).getDisEquName());
	                	gate_info_units.setText(beens.get(0).getAuthName());
	                	gate_info_superiro.setText(beens.get(0).getSuperEqu());
	                	gate_info_area.setText(beens.get(0).getArea());
	                	gate_info_hole.setText(beens.get(0).getPoreNum());
					}
	                  progressDialog.dismiss();
	            }  
	        }); 
		
		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.acitionbar_left:
			getActivity().finish();
			break;
		case R.id.acitionbar_right:
			RequestParams param2 = new RequestParams(URL.updateDisEquInfo);  // 网址(请替换成实际的网址) 
//			 params.addQueryStringParameter("key", "value"); // 参数(请替换成实际的参数与值)   
			progressDialog = ProgressDialog.show(getActivity(), "Loading...",
					"Please wait...", true, false);
			JSONObject js_request = new JSONObject();
			try {
				param2.setAsJsonContent(true);
				js_request.put("disEquID", SharedUtils.getParam(getActivity(), "DisEquID", ""));
				js_request.put("disEquName", gate_info_name.getText().toString());
				js_request.put("area", gate_info_area.getText().toString());
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
		                  Toast.makeText(getActivity(), "请求成功", Toast.LENGTH_SHORT);
		                  Gson gson = new Gson();
		                  System.out.println(arg0);
		                  progressDialog.dismiss();
		            }  
		        }); 
			break;
		default:
			break;
		}
	}

}
