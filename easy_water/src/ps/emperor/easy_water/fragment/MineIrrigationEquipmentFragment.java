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
import ps.emperor.easy_water.adapter.IrrigationEquipmentAdapter;
import ps.emperor.easy_water.adapter.IrrigationEquipmentAdapter.CallBack;
import ps.emperor.easy_water.entity.IrrigationEquipmentBean;
import ps.emperor.easy_water.entity.UserReleIrrInfoBean;
import ps.emperor.easy_water.entity.UserReleIrrInfoBean.infoList;
import ps.emperor.easy_water.greendao.DBHelper;
import ps.emperor.easy_water.greendao.Irrigation;
import ps.emperor.easy_water.utils.CheckUtil;
import ps.emperor.easy_water.utils.SharedUtils;
import ps.emperor.easy_water.utils.URL;
import ps.emperor.easy_water.view.MainActionBar;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Toast;

/**
 * 我的灌溉设备
 * 
 * @author 毛国江
 * @version 2016-5-18 上午10:05
 */
@SuppressLint("NewApi")
public class MineIrrigationEquipmentFragment extends Fragment implements OnClickListener{

	private LayoutInflater mInflater;
	private MainActionBar actionBar;
	private ListView listView;
	private IrrigationEquipmentAdapter adapter;
	private List<IrrigationEquipmentBean> beans;
	private DBHelper dBManager;
	private int IrrCode = 0;
	private ProgressDialog progressDialog;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mInflater = inflater;
		View view = inflater.inflate(R.layout.fragment_mine_irrigation_equipment, container, false);

		actionBar = (MainActionBar) view.findViewById(R.id.actionbar_irrigation_equipment);
		actionBar.setLeftIcon(R.drawable.btn_back_selector);
		actionBar.setRightIcon(R.drawable.t018fdc957b1bbb25b8);
		actionBar.setTitle("我的灌溉设备");
		actionBar.setActionBarOnClickListener(this);
		
		dBManager = DBHelper.getInstance(getActivity()); 
	    String str1 = "";
	    listView = (ListView) view.findViewById(R.id.list_irrigation_equipment);
		try {
			str1 = java.net.URLEncoder.encode("3","UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		RequestParams param3 = new RequestParams(URL.findUserReleIrrInfo+str1);  // 网址(请替换成实际的网址) 
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
	                  UserReleIrrInfoBean fromJson = gson.fromJson(arg0, UserReleIrrInfoBean.class);
//	                  authorizedBeen = new AuthorizedBeen();
//	                  authorizedBeen = gson.fromJson(arg0, AuthorizedBeen.class);
	                  List<infoList> beens = fromJson.getAuthNameList();
	                  if(!CheckUtil.IsEmpty(beens)){
	                	  for (infoList authNameListBean : beens) {
	                		  authNameListBean.getIrriUnitName();
	                		  authNameListBean.getStatusCode();
	                		  if(authNameListBean.getStatusCode()==1){
	                			  IrrCode ++;
	                		  }
	                	  }
	                	  if(IrrCode == 0){
	                		  IrrCode = beens.size();
	                	  }
	                	  adapter = new IrrigationEquipmentAdapter(getActivity(),IrrCode);
	                	  adapter.addData(beens, true);
	                	  listView.setAdapter(adapter);
	                	  SharedUtils.setParam(getActivity(), "EquipmentMark", 1);
	                  }
	          		progressDialog.dismiss();
	            }  
	        }); 
//	    	List<Irrigation> listentity = dBManager.loadAllSession();  
//			beans = new ArrayList<IrrigationEquipmentBean>();
//			IrrigationEquipmentBean bean;
//			for (int i = 0; i < listentity.size(); i++) {
//				bean = new IrrigationEquipmentBean();
//				bean.setEquipment(listentity.get(i).getIrrigation());
//				bean.setIsCheck(true);
//				beans.add(bean);
//			}
//		adapter.addData(beans, false);
//		beans = adapter.getData();
		return view;
	}

	@Override
	public void onClick(View v) {
		FragmentManager fgManager = getFragmentManager();
		FragmentTransaction transaction = fgManager.beginTransaction();
		switch (v.getId()) {
		case R.id.acitionbar_left:
			SharedUtils.setParam(getActivity(), "EquipmentMark", 0);
			MinesFragment fragment = new MinesFragment();
//			transaction.setCustomAnimations(R.anim.right_in, R.anim.right_out);
			transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.replace(R.id.fl, fragment, "main");
			transaction.commit();
			break;
		case R.id.acitionbar_right:
			SharedUtils.setParam(getActivity(), "EquipmentMark", 0);
			MineIrrigationAddFragment fragment1 = new MineIrrigationAddFragment();
//			transaction.setCustomAnimations(R.anim.right_in, R.anim.right_out);
			transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.replace(R.id.fl, fragment1, "main");
			transaction.commit();
			break;
		default:
			break;
		}
	}

}
