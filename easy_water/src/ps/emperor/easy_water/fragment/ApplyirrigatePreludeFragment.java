package ps.emperor.easy_water.fragment;

import java.io.UnsupportedEncodingException;


import java.text.DecimalFormat;
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
import ps.emperor.easy_water.entity.ApplyIrrigateControlValueBean;
import ps.emperor.easy_water.entity.IrriGroupStateBean;
import ps.emperor.easy_water.entity.PreludeBean;
import ps.emperor.easy_water.entity.PreludeBean.infoList;
import ps.emperor.easy_water.utils.CheckUtil;
import ps.emperor.easy_water.utils.SharedUtils;
import ps.emperor.easy_water.utils.URL;
import ps.emperor.easy_water.view.MainActionBar;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

/**
 * 首部
 * 
 * @author lenovo-pc
 * @version 2016-7-14 上午9：36
 */
public class ApplyirrigatePreludeFragment extends Fragment implements
		OnClickListener, OnCheckedChangeListener {
	private LayoutInflater mInflater;
	private MainActionBar actionBar;
	private ToggleButton bt_water_pump, bt_filter, bt_fertilizer_drill; //水泵、反过滤冲洗器、施肥机
	private Boolean isWaterPump, isFilter, isFertilizer_drill;
	private String units,str1;
	private ProgressDialog progressDialog;
	private List<infoList> beens;
	private TextView work_stress,filter_pressure,flow_rate,cumulative_water,cumulative_power;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflater = inflater;
		View view = inflater.inflate(R.layout.fragment_prelude, container,
				false);


		actionBar = (MainActionBar) view
				.findViewById(R.id.actionbar_prelude);
		actionBar.setLeftIcon(R.drawable.btn_back_selector);
		actionBar.setRightGone();
		actionBar.setTitle("设置");
		actionBar.setActionBarOnClickListener(this);

		bt_water_pump = (ToggleButton) view.findViewById(R.id.toggle_prelude_water_pump);
		bt_filter = (ToggleButton) view.findViewById(R.id.Toggle_prelude_filter);
		bt_fertilizer_drill = (ToggleButton) view
				.findViewById(R.id.Toggle_prelude_fertilizer_drill);
		work_stress = (TextView) view.findViewById(R.id.text_prelude_work_stress);
		filter_pressure = (TextView) view.findViewById(R.id.text_prelude_filter_pressure);
		flow_rate = (TextView) view.findViewById(R.id.text_prelude_flow_rate);
		cumulative_water = (TextView) view.findViewById(R.id.text_prelude_cumulative_water);
		cumulative_power = (TextView) view.findViewById(R.id.text_prelude_cumulative_power);
		init();
		str1 = (String) SharedUtils.getParam(getActivity(),
				"FirstDerviceID", "");
		bt_water_pump.setOnCheckedChangeListener(this);
		bt_filter.setOnCheckedChangeListener(this);
		bt_fertilizer_drill.setOnCheckedChangeListener(this);
		bt_water_pump.setOnClickListener(this);
		bt_filter.setOnClickListener(this);
		bt_fertilizer_drill.setOnClickListener(this);
		return view;
	}
	private void init() {

		String str1 = (String) SharedUtils.getParam(getActivity(),
				"FirstDerviceID", "");
		try {
			str1 = java.net.URLEncoder.encode(str1, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		RequestParams param3 = new RequestParams(URL.acquireFirstDerviceInfo
				+ str1); // 网址(请替换成实际的网址)
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
					progressDialog.dismiss();
				} else { // 其他错误 
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
				PreludeBean fromJson = gson.fromJson(arg0,
						PreludeBean.class);
				beens = fromJson.getAuthNameList();
				if("0".equals(beens.get(0).getPumpSwitch())){
					isWaterPump = false;
				}else{
					isWaterPump = true;
				}
				if("0".equals(beens.get(0).getFilterSwitch())){
					isFilter = false;
				}else{
					isFilter = true;
				}
				if("0".equals(beens.get(0).getFertilizerSwitch())){
					isFertilizer_drill = false;
				}else{
					isFertilizer_drill = true;
				}
				if(!CheckUtil.IsEmpty(beens.get(0).getWorkStress())){
					work_stress.setText(beens.get(0).getWorkStress());
				}else{
					work_stress.setText("");
				}
				if(!CheckUtil.IsEmpty(beens.get(0).getPressThreshold())){
					filter_pressure.setText(beens.get(0).getPressThreshold());
				}else{
					filter_pressure.setText("");
				}
				if(!CheckUtil.IsEmpty(beens.get(0).getInstantFlow())){
					flow_rate.setText(beens.get(0).getInstantFlow());
				}else{
					flow_rate.setText("");
				}
				if(!CheckUtil.IsEmpty(beens.get(0).getAccumulateWater())){
					cumulative_water.setText(beens.get(0).getAccumulateWater());
				}else{
					cumulative_water.setText("");
				}
				if(!CheckUtil.IsEmpty(beens.get(0).getAccumulateElectric())){
					cumulative_power.setText(beens.get(0).getAccumulateElectric());
				}else{
					cumulative_power.setText("");
				}
				bt_water_pump.setChecked(isWaterPump);
				bt_filter.setChecked(isFilter);
				bt_fertilizer_drill.setChecked(isFertilizer_drill);
				progressDialog.dismiss();
			}
		});
	
//		isWaterPump = (Boolean) SharedUtils.getParam(getActivity(), "water_pump", false);
//		isFilter = (Boolean) SharedUtils.getParam(getActivity(), "filter", false);
//		isFertilizer_drill = (Boolean) SharedUtils.getParam(getActivity(), "fertilizer_drill", false);
		
		units = getArguments().getString("units");
	}
	@Override
	public void onClick(View v) {
		FragmentManager fgManager = getFragmentManager();
		FragmentTransaction transaction = fgManager.beginTransaction();
		switch (v.getId()) {
		case R.id.acitionbar_left:
			ApplyIrrigateUnitControlFragment fragment = new ApplyIrrigateUnitControlFragment();
			Bundle bundle = new Bundle();
			bundle.putString("units", units);
			fragment.setArguments(bundle);
//			transaction.setCustomAnimations(R.anim.right_in, R.anim.right_out);
			transaction.setCustomAnimations(R.anim.slide_fragment_horizontal_right_in, R.anim.slide_fragment_horizontal_left_out);
			transaction.replace(R.id.fl, fragment, "main");
			transaction.commit();
			break;
		case R.id.toggle_prelude_water_pump:
			if(isWaterPump == false){
				RequestParams param3 = new RequestParams(
						URL.updatePumpSwitchState); // 网址(请替换成实际的网址)
				// params.addQueryStringParameter("key",
				// "value"); //
				// 参数(请替换成实际的参数与值)
				progressDialog = ProgressDialog.show(getActivity(),"Loading...","Please wait...",true,false);
				JSONObject js_request1 = new JSONObject();
				try {
					param3.setAsJsonContent(true);
					js_request1.put("firstDerviceID",str1);
					js_request1.put("pumpEquID",beens.get(0).getPumpEquID());
					js_request1.put("pumpSwitch",1);
					
					param3.setBodyContent(js_request1
							.toString());
				} catch (Exception e) {
					e.printStackTrace();
					param3.setAsJsonContent(true);
				}// 根据实际需求添加相应键值对

				x.http()
						.request(
								HttpMethod.PUT,
								param3,
								new CommonCallback<String>() {
									@Override
									public void onCancelled(
											CancelledException arg0) {

									}

									// 注意:如果是自己onSuccess回调方法里写了一些导致程序崩溃的代码，也会回调道该方法，因此可以用以下方法区分是网络错误还是其他错误
									// 还有一点，网络超时也会也报成其他错误，还需具体打印出错误内容比较容易跟踪查看
									@Override
									public void onError(Throwable ex,boolean isOnCallback) {

										Toast.makeText(x.app(),ex.getMessage(),Toast.LENGTH_LONG).show();
										if (ex instanceof HttpException) { // 网络错误
																			// 
											HttpException httpEx = (HttpException) ex;
											int responseCode = httpEx
													.getCode();
											String responseMsg = httpEx
													.getMessage();
											String errorResult = httpEx
													.getResult();
											// ...
											progressDialog.dismiss();
										} else { // 其他错误
													// 
											// ...
											progressDialog.dismiss();
										}

									}

									// 不管成功或者失败最后都会回调该接口
									@Override
									public void onFinished() {
									}

									@Override
									public void onSuccess(
											String arg0) {
										Toast.makeText(getActivity(),"请求成功",	Toast.LENGTH_SHORT);
										Gson gson = new Gson();
										IrriGroupStateBean fromJson = gson.fromJson(arg0,
												IrriGroupStateBean.class);
										isWaterPump = true;
										if("0".equals(fromJson.getCode())){
											Toast.makeText(getActivity(), "开启水泵失败！", Toast.LENGTH_SHORT).show();
										}
										if("2".equals(fromJson.getCode())){
											Toast.makeText(getActivity(), "当前灌溉单元下无阀门开启 开启水泵失败", Toast.LENGTH_SHORT).show();
											isWaterPump = false;
										}
										bt_water_pump.setChecked(isWaterPump);
										SharedUtils.setParam(getActivity(), "water_pump", isWaterPump);
										progressDialog
												.dismiss();
									}
								});
			}else{
				try {
					str1 = java.net.URLEncoder.encode(str1, "UTF-8");
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				RequestParams param3 = new RequestParams(URL.acquireIrriState
						+ str1); // 网址(请替换成实际的网址)
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
						progressDialog.dismiss();
					} else { // 其他错误 
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
					final IrriGroupStateBean fromJson = gson.fromJson(arg0,
							IrriGroupStateBean.class);
					progressDialog.dismiss();
					if("1".equals(fromJson.getCode())){
						new AlertDialog.Builder(getActivity())
						.setTitle("系统提示")
						// 设置对话框标题
						.setMessage(
								"当前正有灌溉计划执行 是否确认关闭！")
						// 设置显示的内容
						.setPositiveButton(
								"取消",
								new DialogInterface.OnClickListener() {// 添加确定按钮

									@Override
									public void onClick(
											DialogInterface dialog,
											int which) {// 确定按钮的响应事件
										isWaterPump = true;
										bt_water_pump.setChecked(isWaterPump);
										dialog.dismiss();
									}
								})
						.setNegativeButton(
								"确定",
								new DialogInterface.OnClickListener() {// 添加确定按钮

									@Override
									public void onClick(
											DialogInterface dialog,
											int which) {// 确定按钮的响应事件
										RequestParams param3 = new RequestParams(
												URL.updatePumpSwitchState); // 网址(请替换成实际的网址)
										// params.addQueryStringParameter("key",
										// "value"); //
										// 参数(请替换成实际的参数与值)
										progressDialog = ProgressDialog.show(getActivity(),"Loading...","Please wait...",true,false);
										JSONObject js_request1 = new JSONObject();
										try {
											param3.setAsJsonContent(true);
											js_request1.put("firstDerviceID",str1);
											js_request1.put("pumpEquID",beens.get(0).getPumpEquID());
											js_request1.put("pumpSwitch",0);
											js_request1.put("irriState",fromJson.getCode());
											
											param3.setBodyContent(js_request1
													.toString());
										} catch (Exception e) {
											e.printStackTrace();
											param3.setAsJsonContent(true);
										}// 根据实际需求添加相应键值对

										x.http()
												.request(
														HttpMethod.PUT,
														param3,
														new CommonCallback<String>() {
															@Override
															public void onCancelled(
																	CancelledException arg0) {

															}

															// 注意:如果是自己onSuccess回调方法里写了一些导致程序崩溃的代码，也会回调道该方法，因此可以用以下方法区分是网络错误还是其他错误
															// 还有一点，网络超时也会也报成其他错误，还需具体打印出错误内容比较容易跟踪查看
															@Override
															public void onError(Throwable ex,boolean isOnCallback) {

																Toast.makeText(x.app(),ex.getMessage(),Toast.LENGTH_LONG).show();
																if (ex instanceof HttpException) { // 网络错误
																									// 
																	HttpException httpEx = (HttpException) ex;
																	int responseCode = httpEx
																			.getCode();
																	String responseMsg = httpEx
																			.getMessage();
																	String errorResult = httpEx
																			.getResult();
																	// ...
																	progressDialog.dismiss();
																} else { // 其他错误
																			// 
																	// ...
																	progressDialog.dismiss();
																}

															}

															// 不管成功或者失败最后都会回调该接口
															@Override
															public void onFinished() {
															}

															@Override
															public void onSuccess(
																	String arg0) {
																Toast.makeText(getActivity(),"请求成功",	Toast.LENGTH_SHORT);
																Gson gson = new Gson();
																isWaterPump = false;
																bt_water_pump.setChecked(isWaterPump);
																progressDialog
																		.dismiss();
															}
														});
									}
								}).show();// 在按键响应事件中显示此对话框
					}else{
						RequestParams param3 = new RequestParams(
								URL.updatePumpSwitchState); // 网址(请替换成实际的网址)
						// params.addQueryStringParameter("key",
						// "value"); //
						// 参数(请替换成实际的参数与值)
						progressDialog = ProgressDialog.show(getActivity(),"Loading...","Please wait...",true,false);
						JSONObject js_request1 = new JSONObject();
						try {
							param3.setAsJsonContent(true);
							js_request1.put("firstDerviceID",str1);
							js_request1.put("pumpEquID",beens.get(0).getPumpEquID());
							js_request1.put("pumpSwitch",0);
							js_request1.put("irriState",fromJson.getCode());
							
							param3.setBodyContent(js_request1
									.toString());
						} catch (Exception e) {
							e.printStackTrace();
							param3.setAsJsonContent(true);
						}// 根据实际需求添加相应键值对

						x.http()
								.request(
										HttpMethod.PUT,
										param3,
										new CommonCallback<String>() {
											@Override
											public void onCancelled(
													CancelledException arg0) {

											}

											// 注意:如果是自己onSuccess回调方法里写了一些导致程序崩溃的代码，也会回调道该方法，因此可以用以下方法区分是网络错误还是其他错误
											// 还有一点，网络超时也会也报成其他错误，还需具体打印出错误内容比较容易跟踪查看
											@Override
											public void onError(Throwable ex,boolean isOnCallback) {

												Toast.makeText(x.app(),ex.getMessage(),Toast.LENGTH_LONG).show();
												if (ex instanceof HttpException) { // 网络错误
																					// 
													HttpException httpEx = (HttpException) ex;
													int responseCode = httpEx
															.getCode();
													String responseMsg = httpEx
															.getMessage();
													String errorResult = httpEx
															.getResult();
													// ...
													progressDialog.dismiss();
												} else { // 其他错误
															// 
													// ...
													progressDialog.dismiss();
												}

											}

											// 不管成功或者失败最后都会回调该接口
											@Override
											public void onFinished() {
											}

											@Override
											public void onSuccess(
													String arg0) {
												Toast.makeText(getActivity(),"请求成功",	Toast.LENGTH_SHORT);
												Gson gson = new Gson();
												isWaterPump = false;
												bt_water_pump.setChecked(isWaterPump);
												progressDialog
														.dismiss();
											}
										});
					}
					progressDialog.dismiss();
				}
			});
			}
			break;
		case R.id.Toggle_prelude_filter:
			if(isFilter == false){
				RequestParams param3 = new RequestParams(
						URL.updateFilterSwitchState); // 网址(请替换成实际的网址)
				// params.addQueryStringParameter("key",
				// "value"); //
				// 参数(请替换成实际的参数与值)
				progressDialog = ProgressDialog.show(getActivity(),"Loading...","Please wait...",true,false);
				JSONObject js_request1 = new JSONObject();
				try {
					param3.setAsJsonContent(true);
					js_request1.put("firstDerviceID",str1);
					js_request1.put("filterEquID",beens.get(0).getFilterEquID());
					js_request1.put("filterSwitch",1);
					
					param3.setBodyContent(js_request1
							.toString());
				} catch (Exception e) {
					e.printStackTrace();
					param3.setAsJsonContent(true);
				}// 根据实际需求添加相应键值对

				x.http()
						.request(
								HttpMethod.PUT,
								param3,
								new CommonCallback<String>() {
									@Override
									public void onCancelled(
											CancelledException arg0) {

									}

									// 注意:如果是自己onSuccess回调方法里写了一些导致程序崩溃的代码，也会回调道该方法，因此可以用以下方法区分是网络错误还是其他错误
									// 还有一点，网络超时也会也报成其他错误，还需具体打印出错误内容比较容易跟踪查看
									@Override
									public void onError(Throwable ex,boolean isOnCallback) {

										Toast.makeText(x.app(),ex.getMessage(),Toast.LENGTH_LONG).show();
										if (ex instanceof HttpException) { // 网络错误
																			// 
											HttpException httpEx = (HttpException) ex;
											int responseCode = httpEx
													.getCode();
											String responseMsg = httpEx
													.getMessage();
											String errorResult = httpEx
													.getResult();
											// ...
											progressDialog.dismiss();
										} else { // 其他错误
													// 
											// ...
											progressDialog.dismiss();
										}

									}

									// 不管成功或者失败最后都会回调该接口
									@Override
									public void onFinished() {
									}

									@Override
									public void onSuccess(
											String arg0) {
										Toast.makeText(getActivity(),"请求成功",	Toast.LENGTH_SHORT);
										Gson gson = new Gson();
										isFilter = true;
										bt_filter.setChecked(isFilter);
										progressDialog
												.dismiss();
									}
								});
			}else{
				RequestParams param3 = new RequestParams(
						URL.updateFilterSwitchState); // 网址(请替换成实际的网址)
				// params.addQueryStringParameter("key",
				// "value"); //
				// 参数(请替换成实际的参数与值)
				progressDialog = ProgressDialog.show(getActivity(),"Loading...","Please wait...",true,false);
				JSONObject js_request1 = new JSONObject();
				try {
					param3.setAsJsonContent(true);
					js_request1.put("firstDerviceID",str1);
					js_request1.put("filterEquID",beens.get(0).getFilterEquID());
					js_request1.put("filterSwitch",0);
					
					param3.setBodyContent(js_request1
							.toString());
				} catch (Exception e) {
					e.printStackTrace();
					param3.setAsJsonContent(true);
				}// 根据实际需求添加相应键值对

				x.http()
						.request(
								HttpMethod.PUT,
								param3,
								new CommonCallback<String>() {
									@Override
									public void onCancelled(
											CancelledException arg0) {

									}

									// 注意:如果是自己onSuccess回调方法里写了一些导致程序崩溃的代码，也会回调道该方法，因此可以用以下方法区分是网络错误还是其他错误
									// 还有一点，网络超时也会也报成其他错误，还需具体打印出错误内容比较容易跟踪查看
									@Override
									public void onError(Throwable ex,boolean isOnCallback) {

										Toast.makeText(x.app(),ex.getMessage(),Toast.LENGTH_LONG).show();
										if (ex instanceof HttpException) { // 网络错误
																			// 
											HttpException httpEx = (HttpException) ex;
											int responseCode = httpEx
													.getCode();
											String responseMsg = httpEx
													.getMessage();
											String errorResult = httpEx
													.getResult();
											// ...
											progressDialog.dismiss();
										} else { // 其他错误
													// 
											// ...
											progressDialog.dismiss();
										}

									}

									// 不管成功或者失败最后都会回调该接口
									@Override
									public void onFinished() {
									}

									@Override
									public void onSuccess(
											String arg0) {
										Toast.makeText(getActivity(),"请求成功",	Toast.LENGTH_SHORT);
										Gson gson = new Gson();
										isFilter = false;
										bt_filter.setChecked(isFilter);
										progressDialog
												.dismiss();
									}
								});
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
		switch (buttonView.getId()) {
		case R.id.toggle_prelude_water_pump:// 规律
			break;
		case R.id.Toggle_prelude_filter:// 规律
			break;
		case R.id.Toggle_prelude_fertilizer_drill:// 规律
			Toast.makeText(getActivity(), "开关--" + isChecked, Toast.LENGTH_SHORT).show();
			SharedUtils.setParam(getActivity(), "fertilizer_drill", isChecked);
			break;
		}
	}		
}
