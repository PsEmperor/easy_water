package ps.emperor.easy_water.fragment;

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
import ps.emperor.easy_water.adapter.ApplyWaterGateLinkageAdapter;
import ps.emperor.easy_water.entity.ApplyWaterGateLinkageBean;
import ps.emperor.easy_water.entity.FindDisWaterInfoOneBean.DisWaterInfoBean;
import ps.emperor.easy_water.entity.FindDisWaterInfoOneBean.SluiceGateInfoBean;
import ps.emperor.easy_water.utils.DensityUtil;
import ps.emperor.easy_water.utils.SharedUtils;
import ps.emperor.easy_water.utils.URL;
import ps.emperor.easy_water.view.MainActionBar;
import ps.emperor.easy_water.view.MainActionBars;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

/**
 * 单孔闸门控制
 * 
 * @author 毛国江
 * @version 2016-9-9 下午16:49
 */
public class AppayWaterGateHaploporeFragment extends Fragment implements
		OnClickListener, OnSeekBarChangeListener ,OnTouchListener{
	private LayoutInflater mInflater;
	private MainActionBars actionBar;
	private SeekBar seekBar;
	private TextView tv_show,hight,aperture,tv_apply_water_poreId;
	private RelativeLayout layout_hight_change;
	private int OpenProportion,all;
	private Button btn_apply_water_haplopore;
	private String OpenHigh,PoreId;
	private ProgressDialog progressDialog;
	
	@SuppressLint("CutPasteId")
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflater = inflater;
		View view = inflater.inflate(R.layout.fragment_haplopore_gate_control,
				container, false);
		actionBar = (MainActionBars) view
				.findViewById(R.id.actionbar_apply_water_haplopore_gate_control);
		actionBar.setLeftIcon(R.drawable.btn_back_selector);
		actionBar.setRightText("保存");
		actionBar.setActionBarOnClickListener(this);
		all = (Integer) SharedUtils.getParam(getActivity(), "all", 2);
		if(all == 1){
			actionBar.setTitle("多孔闸门联动");
		}else if(all == 0){
			actionBar.setTitle("单孔闸门控制");
		}
		OpenProportion = getArguments().getInt("OpenProportion");
		OpenHigh = getArguments().getString("OpenHigh");
		PoreId = getArguments().getString("PoreId");
		hight = (TextView) view.findViewById(R.id.tv_apply_water_haplopore_gate_hight);
		aperture = (TextView) view.findViewById(R.id.tv_apply_water_haplopore_gate_aperture);
		tv_show = (TextView) view.findViewById(R.id.tv_apply_water_haplopore_1);
		tv_apply_water_poreId = (TextView) view.findViewById(R.id.tv_apply_water_poreId);
		
		seekBar = (SeekBar) view
				.findViewById(R.id.mySeekBar_apply_water_haplopore);
		seekBar.setProgress(OpenProportion);
		seekBar.setOnSeekBarChangeListener(this);

		layout_hight_change = (RelativeLayout) view.findViewById(R.id.layout_hight_change);
		layout_hight_change.setOnClickListener(this);
		
		hight.setText(OpenProportion + "%");
		aperture.setText(OpenHigh + "m³");
		tv_show.setText(OpenProportion + "%");
		tv_apply_water_poreId.setText(PoreId);
		
		RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)layout_hight_change.getLayoutParams();
		int height = DensityUtil.dip2px(getActivity(), Float.valueOf((float) ((100-Integer.valueOf(OpenProportion)) * 2.7)));
		layoutParams.height = height;
		layout_hight_change.setLayoutParams(layoutParams);
		layout_hight_change.requestLayout();
		layout_hight_change.setOnTouchListener(this);
		
		
		return view;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
	}
	@Override
	public void onClick(View v) {
		FragmentManager fgManager = getFragmentManager();
		FragmentTransaction transaction = fgManager.beginTransaction();
		switch (v.getId()) {
		case R.id.acitionbar_left:
			ApplyWaterDistrbutionGate fragment = new ApplyWaterDistrbutionGate();
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			transaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.replace(R.id.fl, fragment, "main");
			transaction.commit();
			break;
		case R.id.acitionbar_right:
			if(all == 0){
				RequestParams param2 = new RequestParams(URL.openProportionOne);  // 网址(请替换成实际的网址) 
//				 params.addQueryStringParameter("key", "value"); // 参数(请替换成实际的参数与值)   
				progressDialog = ProgressDialog.show(getActivity(), "Loading...",
						"Please wait...", true, false);
				JSONObject js_request = new JSONObject();
				try {
					param2.setAsJsonContent(true);
					js_request.put("disEquID", "配水设备5");
					js_request.put("poreID", PoreId);
					js_request.put("openProportion", OpenProportion);
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
			            	Toast.makeText(getActivity(), "走了网络请求", Toast.LENGTH_SHORT);
			            }  
			  
			            @Override  
			            public void onSuccess(String arg0) {  
			                  Toast.makeText(getActivity(), "请求成功", Toast.LENGTH_SHORT);
			                  Gson gson = new Gson();
			                  System.out.println(arg0);
			                  progressDialog.dismiss();
//			                  UserReleDisInfoBeanAdd fromJson = gson.fromJson(arg0, UserReleDisInfoBeanAdd.class);
////			                  authorizedBeen = new AuthorizedBeen();
////			                  authorizedBeen = gson.fromJson(arg0, AuthorizedBeen.class);
//			                  List<infoList> beens = fromJson.getAuthNameList();
//			                  for (infoList authNameListBean : beens) {
//			                	authNameListBean.getAuthName();
//							}
//			                  adapter.addData(beans, true);
//			                  listView.setAdapter(adapter);
			                FragmentManager fgManager = getFragmentManager();
			          		FragmentTransaction transaction = fgManager.beginTransaction();
			                ApplyWaterDistrbutionGate fragment1 = new ApplyWaterDistrbutionGate();
			      			// transaction.setCustomAnimations(R.anim.right_in,
			      			// R.anim.right_out);
			      			transaction
			      					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			      			transaction.replace(R.id.fl, fragment1, "main");
			      			transaction.commit();
			            }  
			        }); 
			}else{
				RequestParams param2 = new RequestParams(URL.openProportion);  // 网址(请替换成实际的网址) 
//				 params.addQueryStringParameter("key", "value"); // 参数(请替换成实际的参数与值)   
				progressDialog = ProgressDialog.show(getActivity(), "Loading...",
						"Please wait...", true, false);
				JSONObject js_request = new JSONObject();
				try {
					param2.setAsJsonContent(true);
					js_request.put("disEquID", "配水设备5");
					js_request.put("openProportion",OpenProportion);
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
			            	Toast.makeText(getActivity(), "走了网络请求", Toast.LENGTH_SHORT);
			            }  
			  
			            @Override  
			            public void onSuccess(String arg0) {  
			                  Toast.makeText(getActivity(), "请求成功", Toast.LENGTH_SHORT);
			                  Gson gson = new Gson();
			                  System.out.println(arg0);
			                  progressDialog.dismiss();
//			                  UserReleDisInfoBeanAdd fromJson = gson.fromJson(arg0, UserReleDisInfoBeanAdd.class);
////			                  authorizedBeen = new AuthorizedBeen();
////			                  authorizedBeen = gson.fromJson(arg0, AuthorizedBeen.class);
//			                  List<infoList> beens = fromJson.getAuthNameList();
//			                  for (infoList authNameListBean : beens) {
//			                	authNameListBean.getAuthName();
//							}
//			                  adapter.addData(beans, true);
//			                  listView.setAdapter(adapter);
			                FragmentManager fgManager = getFragmentManager();
			          		FragmentTransaction transaction = fgManager.beginTransaction();
			                ApplyWaterDistrbutionGate fragment1 = new ApplyWaterDistrbutionGate();
			      			// transaction.setCustomAnimations(R.anim.right_in,
			      			// R.anim.right_out);
			      			transaction
			      					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			      			transaction.replace(R.id.fl, fragment1, "main");
			      			transaction.commit();
			            }  
			        }); 
			}
			break;
		}

	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		//这个部分就是拖动进度条变化上方布局高度
		hight.setText(progress + "%");
		tv_show.setText(progress + "%");
		RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)layout_hight_change.getLayoutParams();
		int height = DensityUtil.dip2px(getActivity(), Float.valueOf((float) ((100-progress) * 2.7)));
		layoutParams.height = height;
		layout_hight_change.setLayoutParams(layoutParams);
		layout_hight_change.requestLayout();
		OpenProportion = progress;
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {

	}


	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return false;
	}


	
	
}
