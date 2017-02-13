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
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.adapter.MineUserDistrictAdapter;
import ps.emperor.easy_water.adapter.MineUserEvenAdapter;
import ps.emperor.easy_water.adapter.MineUserStateAdapter;
import ps.emperor.easy_water.adapter.MineUserProvinceAdapter;
import ps.emperor.easy_water.adapter.MineUserUnitAdapter;
import ps.emperor.easy_water.entity.AuthorizedBeen.infoList;
import ps.emperor.easy_water.entity.AuthorizedBeen;
import ps.emperor.easy_water.utils.CheckUtil;
import ps.emperor.easy_water.utils.SharedUtils;
import ps.emperor.easy_water.utils.URL;
import ps.emperor.easy_water.view.MainActionBar;
import ps.emperor.easy_water.view.MainActionBars;

/**
 * 授权单位
 * 
 * @author 毛国江
 * @version 2016-5-17 上午9:55
 */

@SuppressLint("NewApi")
public class MineUserUnitFragment extends android.app.Fragment implements
		OnClickListener,OnItemClickListener{
	private LayoutInflater mInflater;
	private MainActionBars actionBar;
	private TextView hint,tvProvince, tvState, tvDistrict, tvUnits,tvEven;
	private PopupWindow popupWindow;
	private MineUserProvinceAdapter adapter;
	private MineUserStateAdapter adapter1;
	private MineUserDistrictAdapter adapter2;
	private MineUserUnitAdapter adapter3;
	private MineUserEvenAdapter adapter4;
	private ListView listView;
	private String shareUnitID,province,state,district,units,event,shareProvince,shareState,shareDistrict,shareEven,shareUnit;//淇濆瓨鍦╯hard涓殑
	private int chose;
	AuthorizedBeen authorizedBeen;
	private ProgressDialog progressDialog;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflater = inflater;
		View view = inflater.inflate(
				R.layout.fragment_mine_user_authorized_unit, container, false);
		hint = (TextView) view.findViewById(R.id.hint);
		String str = "提示:选择所处行政区划后自动出现管理单位列表，若授权单位不在列表中请与客服联系。";
		SpannableStringBuilder style = new SpannableStringBuilder(str);
		// str代表要显示的全部字符串
		ClickableSpan what = new ClickableSpan() {

			@Override
			public void onClick(View widget) {
				
				Intent intent=new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+"13641052895"));
				startActivity(intent);
			}
		};
		style.setSpan(what, 35, 39, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		actionBar = (MainActionBars) view.findViewById(R.id.actionbar_user_unit);
		hint.setText(style);
		hint.setMovementMethod(LinkMovementMethod.getInstance());
		actionBar.setLeftIcon(R.drawable.btn_back_selector);
		actionBar.setRightText("保存");
		actionBar.setTitle("授权单位");
		actionBar.setActionBarOnClickListener(this);
		
		init();
		
		tvProvince = (TextView) view.findViewById(R.id.text_mine_user_info_province);
		tvState = (TextView) view.findViewById(R.id.text_mine_user_info_state);
		tvDistrict = (TextView) view.findViewById(R.id.text_mine_user_info_district);
		tvUnits = (TextView) view.findViewById(R.id.text_mine_user_info_units);
		tvEven = (TextView) view.findViewById(R.id.text_mine_user_info_even);
		
		if(!CheckUtil.IsEmpty(shareProvince)){
			tvProvince.setText(shareProvince);
		}
		if(!CheckUtil.IsEmpty(shareState)){
			tvState.setText(shareState);
		}
		if(!CheckUtil.IsEmpty(shareDistrict)){
			tvDistrict.setText(shareDistrict);
		}
		if(!CheckUtil.IsEmpty(shareEven)){
			tvEven.setText(shareEven);
		}
		if(!CheckUtil.IsEmpty(shareUnit)){
			tvUnits.setText(shareUnit);
		}
		tvProvince.setOnClickListener(this);
		tvState.setOnClickListener(this);
		tvDistrict.setOnClickListener(this);
		tvUnits.setOnClickListener(this);
		tvEven.setOnClickListener(this);
		return view;
	}

	private void init() {
		adapter = new MineUserProvinceAdapter(getActivity());
		adapter1 = new MineUserStateAdapter(getActivity());
		adapter2 = new MineUserDistrictAdapter(getActivity());
		adapter3 = new MineUserUnitAdapter(getActivity());
		adapter4 = new MineUserEvenAdapter(getActivity());
		
		shareProvince = (String) SharedUtils.getParam(getActivity(), "shareProvince", "无数据");
		shareState = (String) SharedUtils.getParam(getActivity(), "shareState", "无数据");
		shareDistrict = (String) SharedUtils.getParam(getActivity(), "shareDistrict", "无数据");
		shareUnit = (String) SharedUtils.getParam(getActivity(), "shareUnit", "无数据");
		shareEven = (String) SharedUtils.getParam(getActivity(), "shareEven", "无数据");
	}

	@Override
	public void onClick(View v) {
		android.app.FragmentManager fgManager = getFragmentManager();
		android.app.FragmentTransaction transaction = fgManager
				.beginTransaction();
		switch (v.getId()) {
		case R.id.acitionbar_left:
			MineUserInfoFragment fragment = new MineUserInfoFragment();
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			transaction.setCustomAnimations(R.anim.slide_fragment_horizontal_right_in, R.anim.slide_fragment_horizontal_left_out);
			transaction.replace(R.id.fl, fragment, "main");
			transaction.commit();
			break;
		case R.id.text_mine_user_info_province:
			chose = 1;
			RequestParams param1 = new RequestParams(URL.urluserAuthInfo); //  网址(请替换成实际的网址) 
			progressDialog = ProgressDialog.show(getActivity(), "Loading...",
					"Please wait...", true, false);
//			 params.addQueryStringParameter("key", "value");// 参数(请替换成实际的参数与值)   
			JSONObject js_request = new JSONObject();
			try {
				param1.setAsJsonContent(true);
//				
				} catch (Exception e) {
				// TODO Auto-generated catch block
//				param1.setBodyContent("Content-Type: application/json"+js_request.toString());
				e.printStackTrace();
				param1.setAsJsonContent(true);
			}//根据实际需求添加相应键值对
			
		        x.http().request(HttpMethod.GET ,param1, new CommonCallback<String>() {  
		            @Override  
		            public void onCancelled(CancelledException arg0) {  
		                  
		            }  
		  
		            // 注意:如果是自己onSuccess回调方法里写了一些导致程序崩溃的代码，也会回调道该方法，因此可以用以下方法区分是网络错误还是其他错误  
		            // 还有一点，网络超时也会也报成其他错误，还需具体打印出错误内容比较容易跟踪查看    
		            @Override  
		            public void onError(Throwable ex, boolean isOnCallback) {  
		                  
		                Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();  
		                if (ex instanceof HttpException) { // 网络错误  
		                    HttpException httpEx = (HttpException) ex;  
		                    int responseCode = httpEx.getCode();  
		                    String responseMsg = httpEx.getMessage();  
		                    String errorResult = httpEx.getResult();  
		                    Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT);
		                    // ...  
		                    progressDialog.dismiss();
		                } else {  // 其他错误  
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
		                  AuthorizedBeen fromJson = gson.fromJson(arg0, AuthorizedBeen.class);
		                  authorizedBeen = new AuthorizedBeen();
		                  authorizedBeen = gson.fromJson(arg0, AuthorizedBeen.class);
		                  List<infoList> beens = fromJson.getAuthNameList();
		                 if(!CheckUtil.IsEmpty(beens)){
		                	 adapter.addData(beens, true);
		                 }
		                  progressDialog.dismiss();
		            }  
		        }); 
			View view1 = mInflater.inflate(
					R.layout.layout_mine_user_units_popu, null);
			popupWindow = new PopupWindow(view1,
					ViewGroup.LayoutParams.WRAP_CONTENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			popupWindow.setFocusable(true);
			popupWindow.setBackgroundDrawable(new ColorDrawable());
			listView = (ListView) view1.findViewById(R.id.list_mine_user_units);
			listView.setAdapter(adapter);
			listView.setOnItemClickListener(this);
		popupWindow.showAsDropDown(tvProvince);
		break;
		case R.id.text_mine_user_info_state:
			shareProvince = (String) SharedUtils.getParam(getActivity(), "shareProvince", "");
			chose = 2;
			String str = "";
			try {
				str = java.net.URLEncoder.encode(shareProvince,"UTF-8");
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println(str);
			if(CheckUtil.IsEmpty(shareProvince)){
				Toast.makeText(getActivity(), "请选择省份后进行此操作！", Toast.LENGTH_SHORT).show();
			}else{
			RequestParams param2 = new RequestParams(URL.urluserAuthInfo+str);  // 网址(请替换成实际的网址) 
//			 params.addQueryStringParameter("key", "value"); // 参数(请替换成实际的参数与值)   
			progressDialog = ProgressDialog.show(getActivity(), "Loading...",
					"Please wait...", true, false);
			JSONObject js_request1 = new JSONObject();
			try {
				param2.setAsJsonContent(true);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				param2.setAsJsonContent(true);
			}//根据实际需求添加相应键值对
			
		        x.http().request(HttpMethod.GET ,param2, new CommonCallback<String>() {  
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
		                  AuthorizedBeen fromJson = gson.fromJson(arg0, AuthorizedBeen.class);
		                  authorizedBeen = new AuthorizedBeen();
		                  authorizedBeen = gson.fromJson(arg0, AuthorizedBeen.class);
		                  List<infoList> beens = fromJson.getAuthNameList();
		                  if(!CheckUtil.IsEmpty(beens)){
		                	  adapter1.addData(beens, true);
		                  }
		                  progressDialog.dismiss();
		            }  
		        }); 
				View view2 = mInflater.inflate(
						R.layout.layout_mine_user_units_popu, null);
				popupWindow = new PopupWindow(view2,
						ViewGroup.LayoutParams.WRAP_CONTENT,
						ViewGroup.LayoutParams.WRAP_CONTENT);
				popupWindow.setFocusable(true);
				popupWindow.setBackgroundDrawable(new ColorDrawable());
				listView = (ListView) view2.findViewById(R.id.list_mine_user_units);
				listView.setAdapter(adapter1);
				listView.setOnItemClickListener(this);
			popupWindow.showAsDropDown(tvState);
			}
			break;
		case R.id.text_mine_user_info_district:
			shareProvince = (String) SharedUtils.getParam(getActivity(), "shareProvince", "");
			shareState = (String) SharedUtils.getParam(getActivity(), "shareState", "");
			chose = 3;
			String str1 = "";
			String str2 = "";
			try {
				str1 = java.net.URLEncoder.encode(shareProvince,"UTF-8");
				str2 = java.net.URLEncoder.encode(shareState,"UTF-8");
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(CheckUtil.IsEmpty(shareProvince)){
				Toast.makeText(getActivity(), "请选择省份后进行此操作！", Toast.LENGTH_SHORT).show();
			}else if(CheckUtil.IsEmpty(shareState)){
				Toast.makeText(getActivity(), "请选择市后进行此操作！", Toast.LENGTH_SHORT).show();
			}else{
			RequestParams param3 = new RequestParams(URL.urluserAuthInfo+str1+"/"+str2);  // 网址(请替换成实际的网址) 
//			 params.addQueryStringParameter("key", "value"); // 参数(请替换成实际的参数与值)   
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
		                  AuthorizedBeen fromJson = gson.fromJson(arg0, AuthorizedBeen.class);
		                  authorizedBeen = new AuthorizedBeen();
		                  authorizedBeen = gson.fromJson(arg0, AuthorizedBeen.class);
		                  List<infoList> beens = fromJson.getAuthNameList();
		                  if(!CheckUtil.IsEmpty(beens)){
		                	  adapter2.addData(beens, true);
		                  }
		                  progressDialog.dismiss();
		            }  
		        }); 
				View view3 = mInflater.inflate(
						R.layout.layout_mine_user_units_popu, null);
				popupWindow = new PopupWindow(view3,
						ViewGroup.LayoutParams.WRAP_CONTENT,
						ViewGroup.LayoutParams.WRAP_CONTENT);
				popupWindow.setFocusable(true);
				popupWindow.setBackgroundDrawable(new ColorDrawable());
				listView = (ListView) view3.findViewById(R.id.list_mine_user_units);
				listView.setAdapter(adapter2);
				listView.setOnItemClickListener(this);
			// popupWindow.showAtLocation(getActivity().findViewById(R.id.setting),
			// Gravity.TOP, 0, 0);
			popupWindow.showAsDropDown(tvDistrict);
			}
			break;
		case R.id.text_mine_user_info_even:
			shareProvince = (String) SharedUtils.getParam(getActivity(), "shareProvince", "");
			shareState = (String) SharedUtils.getParam(getActivity(), "shareState", "");
			shareDistrict = (String) SharedUtils.getParam(getActivity(), "shareDistrict", "");
			chose = 4;
			String str3 = "";
			String str4 = "";
			String str5 = "";
			try {
				str3 = java.net.URLEncoder.encode(shareProvince,"UTF-8");
				str4 = java.net.URLEncoder.encode(shareState,"UTF-8");
				str5 = java.net.URLEncoder.encode(shareDistrict,"UTF-8");
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(CheckUtil.IsEmpty(shareProvince)){
				Toast.makeText(getActivity(), "请选择省份后进行此操作！", Toast.LENGTH_SHORT).show();
			}else if(CheckUtil.IsEmpty(shareState)){
				Toast.makeText(getActivity(), "请选择市后进行此操作！", Toast.LENGTH_SHORT).show();
			}else if(CheckUtil.IsEmpty(shareDistrict)){
				Toast.makeText(getActivity(), "请选择区后进行此操作！", Toast.LENGTH_SHORT).show();
			}else{
			RequestParams param4 = new RequestParams(URL.urluserAuthInfo+str3+"/"+str4+"/"+str5);  // 网址(请替换成实际的网址) 
//			 params.addQueryStringParameter("key", "value"); // 参数(请替换成实际的参数与值)   
			progressDialog = ProgressDialog.show(getActivity(), "Loading...",
					"Please wait...", true, false);
			JSONObject js_request3 = new JSONObject();
			try {
				param4.setAsJsonContent(true);
			} catch (Exception e) {
				e.printStackTrace();
				param4.setAsJsonContent(true);
			}//根据实际需求添加相应键值对
			
		        x.http().request(HttpMethod.GET ,param4, new CommonCallback<String>() {  
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
		                  AuthorizedBeen fromJson = gson.fromJson(arg0, AuthorizedBeen.class);
		                  authorizedBeen = new AuthorizedBeen();
		                  authorizedBeen = gson.fromJson(arg0, AuthorizedBeen.class);
		                  List<infoList> beens = fromJson.getAuthNameList();
		                  if(!CheckUtil.IsEmpty(beens)){
		                	  adapter4.addData(beens, true);
		                  }
		                  progressDialog.dismiss();
		            }  
		        }); 
			View view4 = mInflater.inflate(
					R.layout.layout_mine_user_units_popu, null);
			popupWindow = new PopupWindow(view4,
					ViewGroup.LayoutParams.WRAP_CONTENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			popupWindow.setFocusable(true);
			popupWindow.setBackgroundDrawable(new ColorDrawable());
			listView = (ListView) view4.findViewById(R.id.list_mine_user_units);
			listView.setAdapter(adapter4);
			listView.setOnItemClickListener(this);
			// popupWindow.showAtLocation(getActivity().findViewById(R.id.setting),
			// Gravity.TOP, 0, 0);
			popupWindow.showAsDropDown(tvEven);
		}
			break;
		case R.id.text_mine_user_info_units:
			shareProvince = (String) SharedUtils.getParam(getActivity(), "shareProvince", "");
			shareState = (String) SharedUtils.getParam(getActivity(), "shareState", "");
			shareDistrict = (String) SharedUtils.getParam(getActivity(), "shareDistrict", "");
			shareEven = (String) SharedUtils.getParam(getActivity(), "shareEven", "");
			chose = 5;
			String str6 = "";
			String str7 = "";
			String str8 = "";
			String str9 = "";
			try {
				str6 = java.net.URLEncoder.encode(shareProvince,"UTF-8");
				str7 = java.net.URLEncoder.encode(shareState,"UTF-8");
				str8 = java.net.URLEncoder.encode(shareDistrict,"UTF-8");
				str9 = java.net.URLEncoder.encode(shareEven,"UTF-8");
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(CheckUtil.IsEmpty(shareProvince)){
				Toast.makeText(getActivity(), "请选择省份后进行此操作！", Toast.LENGTH_SHORT).show();
			}else if(CheckUtil.IsEmpty(shareState)){
				Toast.makeText(getActivity(), "请选择市后进行此操作！", Toast.LENGTH_SHORT).show();
			}else if(CheckUtil.IsEmpty(shareDistrict)){
				Toast.makeText(getActivity(), "请选择区后进行此操作！", Toast.LENGTH_SHORT).show();
			}else if(CheckUtil.IsEmpty(shareEven)){
				Toast.makeText(getActivity(), "请选择连后进行此操作！", Toast.LENGTH_SHORT).show();
			}else{
			RequestParams param5 = new RequestParams(URL.urluserAuthInfo+str6+"/"+str7+"/"+str8+"/"+str9);  // 网址(请替换成实际的网址) 
//			 params.addQueryStringParameter("key", "value"); // 参数(请替换成实际的参数与值)   
			progressDialog = ProgressDialog.show(getActivity(), "Loading...",
					"Please wait...", true, false);
			JSONObject js_request4 = new JSONObject();
			try {
				param5.setAsJsonContent(true);
			} catch (Exception e) {
				e.printStackTrace();
				param5.setAsJsonContent(true);
			}//根据实际需求添加相应键值对
			
		        x.http().request(HttpMethod.GET ,param5, new CommonCallback<String>() {  
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
		                  AuthorizedBeen fromJson = gson.fromJson(arg0, AuthorizedBeen.class);
		                  authorizedBeen = new AuthorizedBeen();
		                  authorizedBeen = gson.fromJson(arg0, AuthorizedBeen.class);
		                  List<infoList> beens = fromJson.getAuthNameList();
		                  if(!CheckUtil.IsEmpty(beens)){
		                	  adapter3.addData(beens, true);
		                  }
		                  progressDialog.dismiss();
		            }  
		        }); 
		        View view5 = mInflater.inflate(
						R.layout.layout_mine_user_units_popu, null);
				popupWindow = new PopupWindow(view5,
						ViewGroup.LayoutParams.WRAP_CONTENT,
						ViewGroup.LayoutParams.WRAP_CONTENT);
				popupWindow.setFocusable(true);
				popupWindow.setBackgroundDrawable(new ColorDrawable());
				listView = (ListView) view5.findViewById(R.id.list_mine_user_units);
				listView.setAdapter(adapter3);
				listView.setOnItemClickListener(this);
			// popupWindow.showAtLocation(getActivity().findViewById(R.id.setting),
			// Gravity.TOP, 0, 0);
			popupWindow.showAsDropDown(tvUnits);
			}
			break;
		
		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if(chose == 1){
			province = authorizedBeen.getAuthNameList().get(position).getAuthProvince();
			tvProvince.setText(province);
			SharedUtils.setParam(getActivity(), "shareProvince", province);
			popupWindow.dismiss();
		}
		if(chose == 2){
			state = authorizedBeen.getAuthNameList().get(position).getAuthCity();
			tvState.setText(state);
			SharedUtils.setParam(getActivity(), "shareState", state);
			popupWindow.dismiss();
		}
		if(chose == 3){
			district = authorizedBeen.getAuthNameList().get(position).getAuthCounty();
			tvDistrict.setText(district);
			SharedUtils.setParam(getActivity(), "shareDistrict", district);
			popupWindow.dismiss();
		}
		if(chose == 4){
			event = authorizedBeen.getAuthNameList().get(position).getAuthTown();
			tvEven.setText(event);
			SharedUtils.setParam(getActivity(), "shareEven", event);
			popupWindow.dismiss();
		}
		if(chose == 5){
			units = authorizedBeen.getAuthNameList().get(position).getAuthManage();
			shareUnitID = authorizedBeen.getAuthNameList().get(position).getAuthID();
			tvUnits.setText(units);
			SharedUtils.setParam(getActivity(), "shareUnit", units);
			SharedUtils.setParam(getActivity(), "shareUnitID", shareUnitID);
			popupWindow.dismiss();
		}
	}
}
