package ps.emperor.easy_water.fragment;

import java.io.Serializable;


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

import cn.jpush.a.a.b;

import com.google.gson.Gson;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.activity.MainTainPresentrrigateActivity;
import ps.emperor.easy_water.adapter.MainTainIrrigationAdapter;
import ps.emperor.easy_water.entity.UserReleDisInfoBean;
import ps.emperor.easy_water.entity.UserReleIrrInfoBean;
import ps.emperor.easy_water.entity.UserReleIrrInfoBean.infoList;
import ps.emperor.easy_water.greendao.DBHelper;
import ps.emperor.easy_water.greendao.Irrigation;
import ps.emperor.easy_water.utils.CheckUtil;
import ps.emperor.easy_water.utils.SharedUtils;
import ps.emperor.easy_water.utils.URL;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 灌溉（维护）
 * 
 * @author 毛国江
 * @version 2016-6-2 下午14:09
 */

@SuppressLint("NewApi")
public class MainTainIrrigateFragment extends Fragment implements OnClickListener,OnItemClickListener {

	private LayoutInflater mInflater;
	private ListView listView;
	private MainTainIrrigationAdapter adapter;
	private DBHelper dbHelper;
	private List<Irrigation> irrigation;
	private ProgressDialog progressDialog;
	private List<infoList> beens;
	private ImageView image_maintain_water_add;
	private EditText ed_maintain_water_add;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mInflater = inflater;
		View view = inflater.inflate(R.layout.fragment_maintain_irrigate, container, false);

		dbHelper = DBHelper.getInstance(getActivity()); // 得到DBHelper对象

		listView = (ListView) view.findViewById(R.id.list_maintain_water_add);
		listView.setOnItemClickListener(this);
		adapter = new MainTainIrrigationAdapter(getActivity());
		image_maintain_water_add = (ImageView) view.findViewById(R.id.image_maintain_water_add);
		image_maintain_water_add.setOnClickListener(this);
		ed_maintain_water_add = (EditText) view.findViewById(R.id.ed_maintain_water_add);
		
		String str1 = "";
		try {
			str1 = java.net.URLEncoder.encode("3","UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		RequestParams param3 = new RequestParams(URL.findUserReleIrrInfoIrri+str1);  // 网址(请替换成实际的网址) 
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
				UserReleIrrInfoBean fromJson = gson.fromJson(arg0,
						UserReleIrrInfoBean.class);
				// authorizedBeen = new AuthorizedBeen();
				// authorizedBeen = gson.fromJson(arg0, AuthorizedBeen.class);
				beens = fromJson.getAuthNameList();
				if (!CheckUtil.IsEmpty(beens)) {
					adapter.addData(beens, true);
					listView.setAdapter(adapter);
				}
				progressDialog.dismiss();
			}  
	        }); 
//		List<Irrigation> listentity = dbHelper.loadAllSession(); 
//		MainTainIrrigationBean bean;
//		for (int i = 0; i < listentity.size(); i++) {
//			bean = new MainTainIrrigationBean();
//			bean.setMaintain(listentity.get(i).getIrrigation());
//			beans.add(bean);
//		}
//		adapter.addData(beans, false);
//		listView.setAdapter(adapter);
//		beans = adapter.getData();
		return view;
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
//		FragmentManager fgManager = getFragmentManager();
//		FragmentTransaction transaction = fgManager.beginTransaction();
//		MainTainIrrigateInfoFragment fragment = new MainTainIrrigateInfoFragment();
////		transaction.setCustomAnimations(R.anim.right_in, R.anim.right_out);
//		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//		transaction.replace(R.id.fragment_home_recommend, fragment, "main");
//		transaction.commit();
		//数据库存储
		SharedUtils.setParam(getActivity(), "units", beens.get(position).getIrriUnitName());
		SharedUtils.setParam(getActivity(), "FirstDerviceID", beens.get(position).getFirstDerviceID());
		
		Intent intent = new Intent(getActivity(),MainTainPresentrrigateActivity.class);
		intent.putExtra("units", beens.get(position).getIrriUnitName());
		irrigation = dbHelper.loadContinue(beens.get(position).getIrriUnitName());
		if(CheckUtil.IsEmpty(irrigation)){
			Irrigation irrigation = new Irrigation();
			irrigation.setIrrigation(beens.get(position).getIrriUnitName());
			irrigation.setNHour(0);
			irrigation.setNMinutes(0);
			irrigation.setNNumber(0);
			irrigation.setNRound(0);
			irrigation.setIsNightStartHour(0);
			irrigation.setIsNightStartMinute(0);
			irrigation.setIsNightContinueHour(0);
			irrigation.setIsNightContinueMinute(0);
			irrigation.setIsNightEndHour(0);
			irrigation.setIsNightEndMinute(0);
			irrigation.setIsTimeLong(0);
			irrigation.setGroupnumber(0);
			irrigation.setValuenumber(0);
			irrigation.setFilterHour(0);
			irrigation.setFilterMinute(0);
			irrigation.setFilterMinute(0);
			irrigation.setFilterMinute(0);
			irrigation.setFilterMinute(0);
			irrigation.setFilterMinute(0);
			irrigation.setSeasonStrat(0);
			irrigation.setSeasonEnd(0);
			dbHelper.saveSession(irrigation);
		}
		startActivity(intent);
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.image_maintain_water_add:
			String str1 = "";
			String str2 = "";
			try {
				str1 = java.net.URLEncoder.encode("3","UTF-8");
				str2 = java.net.URLEncoder.encode(ed_maintain_water_add.getText().toString(),"UTF-8");
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(CheckUtil.IsEmpty(str2)){
				Toast.makeText(getActivity(), "请输入需要查询的关键字！", Toast.LENGTH_SHORT).show();
			}else{
			RequestParams param3 = new RequestParams(URL.userReleIrriInfo+str1+"/"+str2);  // 网址(请替换成实际的网址) 
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
		                  UserReleIrrInfoBean fromJson = gson.fromJson(arg0, UserReleIrrInfoBean.class);
		                  List<infoList> beens = fromJson.getAuthNameList();
		                  if(!CheckUtil.IsEmpty(beens)){
		                	  adapter.addData(beens, true);
		                	  listView.setAdapter(adapter);
		                  }
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
