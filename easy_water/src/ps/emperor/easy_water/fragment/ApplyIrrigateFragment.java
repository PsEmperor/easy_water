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

import ps.emperor.easy_water.R;
import ps.emperor.easy_water.adapter.ApplyIrrigationAdapter;
import ps.emperor.easy_water.application.ApplicationFragment;
import ps.emperor.easy_water.entity.IrrigationEquipmentBean;
import ps.emperor.easy_water.entity.UserReleIrrInfoBean;
import ps.emperor.easy_water.entity.UserReleIrrInfoBean.infoList;
import ps.emperor.easy_water.greendao.DBHelper;
import ps.emperor.easy_water.greendao.Irrigation;
import ps.emperor.easy_water.greendao.IrrigationGroup;
import ps.emperor.easy_water.utils.CheckUtil;
import ps.emperor.easy_water.utils.URL;
import ps.emperor.easy_water.view.MainActionBar;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * 灌溉（应用）
 * 
 * @author 毛国江
 * @version 2016-5-24 上午8:34
 */

@SuppressLint("NewApi")
public class ApplyIrrigateFragment extends Fragment implements OnClickListener,
		OnItemClickListener {

	private LayoutInflater mInflater;
	private MainActionBar actionBar;
	private ListView listView;
	private ApplyIrrigationAdapter adapter;
	private DBHelper dbHelper;
	private List<Irrigation> irrigation;
	private List<IrrigationGroup> irrigationGroups; 
	private IrrigationGroup irrigationGroup;
	private ProgressDialog progressDialog;
	private List<infoList> beens;
	private ImageView image_apply_irrigation_add;
	private EditText ed_apply_irrigation_add;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflater = inflater;
		View view = inflater.inflate(R.layout.fragment_apply_irrigate,
				container, false);
		
		dbHelper = DBHelper.getInstance(getActivity()); // 得到DBHelper对象
		
		actionBar = (MainActionBar) view
				.findViewById(R.id.actionbar_apply_irrigate);
		actionBar.setLeftIcon(R.drawable.btn_back_selector);
		actionBar.setRightGone();
		actionBar.setTitle("灌溉");
		actionBar.setActionBarOnClickListener(this);

		listView = (ListView) view.findViewById(R.id.list_apply_irrigate_add);
		listView.setOnItemClickListener(this);
		adapter = new ApplyIrrigationAdapter(getActivity());
		image_apply_irrigation_add = (ImageView) view.findViewById(R.id.image_apply_irrigation_add);
		image_apply_irrigation_add.setOnClickListener(this);
		ed_apply_irrigation_add = (EditText) view.findViewById(R.id.ed_apply_irrigation_add);
		
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
	                  UserReleIrrInfoBean fromJson = gson.fromJson(arg0, UserReleIrrInfoBean.class);
	                  beens = fromJson.getAuthNameList();
	                  if(!CheckUtil.IsEmpty(beens)){
	                	  adapter.addData(beens, true);
	                	  listView.setAdapter(adapter);
	                  }
	          		progressDialog.dismiss();
	            }  
	        }); 
	        
		//数据库操作
//		beans = new ArrayList<ApplyIrrigationBean>();
//		List<Irrigation> listentity = dbHelper.loadAllSession(); 
//		ApplyIrrigationBean bean;
//		for (int i = 0; i < listentity.size(); i++) {
//			bean = new ApplyIrrigationBean();
//			bean.setUnits(listentity.get(i).getIrrigation());
//			bean.setWhether("正在灌溉");
//			bean.setWhether_percent("50%");
//			bean.setCurrent_state(180);
//			beans.add(bean);
//		}
//		adapter.addData(beans, false);
//		listView.setAdapter(adapter);
//		beans = adapter.getData();

		return view;
	}

	@Override
	public void onClick(View v) {
		FragmentManager fgManager = getFragmentManager();
		FragmentTransaction transaction = fgManager.beginTransaction();
		switch (v.getId()) {
		case R.id.acitionbar_left:
			ApplicationFragment fragment = new ApplicationFragment();
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			transaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.replace(R.id.fl, fragment, "main");
			transaction.commit();
			break;
		case R.id.image_apply_irrigation_add:
			String str1 = "";
			String str2 = "";
			try {
				str1 = java.net.URLEncoder.encode("3","UTF-8");
				str2 = java.net.URLEncoder.encode(ed_apply_irrigation_add.getText().toString(),"UTF-8");
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

	@Override
	public void onItemClick(AdapterView<?> parent, View view,
			final int position, long id) {
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
			dbHelper.saveSession(irrigation);
		}
		irrigationGroups = dbHelper.loadGroupByUnits(beens.get(position).getIrriUnitName());
		if(CheckUtil.IsEmpty(irrigationGroups)){
			for (int i = 1; i <= irrigationGroups.size(); i++) {
				irrigationGroup = new IrrigationGroup();
				irrigationGroup	
				.setIrrigation(beens.get(position).getIrriUnitName());
				irrigationGroup
				.setMatchedNum(i);
				dbHelper.saveGroup(irrigationGroup);
			}	
		}
		FragmentManager fgManager = getFragmentManager();
		FragmentTransaction transaction = fgManager.beginTransaction();
		ApplyIrrigateUnitControlFragment fragment = new ApplyIrrigateUnitControlFragment();
		Bundle bundle = new Bundle();
		bundle.putString("units", beens.get(position).getIrriUnitName());
		fragment.setArguments(bundle);
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		transaction.replace(R.id.fl, fragment, "main");
		transaction.commit();
	}

}
