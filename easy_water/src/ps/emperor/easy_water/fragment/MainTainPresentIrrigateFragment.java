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
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.adapter.MainTainPresentIrrigateGridAdapter;
import ps.emperor.easy_water.entity.MainTainPresentIrrigateGridBean.infoList;
import ps.emperor.easy_water.entity.MainTainPresentIrrigateGridBean;
import ps.emperor.easy_water.utils.CheckUtil;
import ps.emperor.easy_water.utils.SharedUtils;
import ps.emperor.easy_water.utils.URL;
import ps.emperor.easy_water.view.MainActionBar;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;
import android.view.View.OnClickListener;

/**
 * 当前灌溉组
 * @author 毛国江
 * @version 2016-5-26 上午11:34
 */
@SuppressLint("NewApi")
public class MainTainPresentIrrigateFragment extends Fragment implements OnClickListener {

	private LayoutInflater mInflater;
	private MainActionBar actionBar;
	private GridView listView;
	
	private MainTainPresentIrrigateGridAdapter adapter;
	private List<infoList> beens;
	private ProgressDialog progressDialog;
	
	@SuppressLint("CutPasteId")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mInflater = inflater;
		View view = inflater.inflate(R.layout.fragment_maintain_present_irriagte, container, false);

		actionBar = (MainActionBar) view.findViewById(R.id.actionbar_maintain_present_irrigate);
		actionBar.setLeftIcon(R.drawable.btn_back_selector);
		actionBar.setRightGone();
		actionBar.setTitle("当前灌溉组");
		actionBar.setActionBarOnClickListener(this);
		
		listView = (GridView) view.findViewById(R.id.list_maintain_irrigat_info_group);
//		MainTainPresentIrrigateListBean bean;
//		for (int i = 0; i < 10; i++) {
//			bean = new MainTainPresentIrrigateListBean();
//			bean.setGroup("组A");
//			listbeans.add(bean);
//		}
//		adapter.addData(listbeans, false);
//		listView.setAdapter(adapter);
//		listbeans = adapter.getData();
		init();
		return view;
		
	}


	private void init() {
		String str1 = (String) SharedUtils.getParam(getActivity(), "FirstDerviceID", "");;
		try {
			str1 = java.net.URLEncoder.encode(str1,"UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		RequestParams param3 = new RequestParams(URL.findIrriGroupInfo+str1);  // 网址(请替换成实际的网址) 
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
				Gson gson = new Gson();
				MainTainPresentIrrigateGridBean fromJson = gson.fromJson(arg0,
						MainTainPresentIrrigateGridBean.class);
				beens = fromJson.getAuthNameList();
				System.out.println(arg0);
				if (!CheckUtil.IsEmpty(beens)) {
					adapter = new MainTainPresentIrrigateGridAdapter(
							getActivity());
					adapter.addData(beens, false);
					listView.setAdapter(adapter);
				}
				progressDialog.dismiss();
			}  
	        }); 		
	}


	@Override
	public void onClick(View v) {
		FragmentManager fgManager = getFragmentManager();
		FragmentTransaction transaction = fgManager.beginTransaction();
		switch (v.getId()) {
		case R.id.acitionbar_left:
			MainTainIrrigationInfoFragment fragment = new MainTainIrrigationInfoFragment();
//			transaction.setCustomAnimations(R.anim.right_in, R.anim.right_out);
			transaction.setCustomAnimations(R.anim.slide_fragment_horizontal_right_in, R.anim.slide_fragment_horizontal_left_out);
			transaction.replace(R.id.fragment_maintain_present_irrigate, fragment, "main");
			transaction.commit();
			break;
		default:
			break;
		}
	}
}
