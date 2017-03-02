package ps.emperor.easy_water.fragment;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;

import java.util.Calendar;
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
import android.app.AlertDialog;
import android.app.Dialog;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.adapter.NumericWheelAdapter;
import ps.emperor.easy_water.entity.ApplyIrrigateControlValueBean;
import ps.emperor.easy_water.entity.ApplyIrrigateControlValueBean.infoList;
import ps.emperor.easy_water.entity.IrriGroupStateBean;
import ps.emperor.easy_water.utils.CheckUtil;
import ps.emperor.easy_water.utils.NetStatusUtil;
import ps.emperor.easy_water.utils.SharedUtils;
import ps.emperor.easy_water.utils.URL;
import ps.emperor.easy_water.view.MainActionBars;
import ps.emperor.easy_water.view.WheelView;

/**
 * 灌溉阀门控制
 * 
 * @author 毛国江
 * @version 2016-5-31 下午15:24
 */

@SuppressLint("NewApi")
public class ApplyIrrigateControlValveFragment extends Fragment implements
		OnClickListener {

	private LayoutInflater mInflater;
	private MainActionBars actionBar;
	private ImageView isOpen, imageView;
	private Dialog dialog;
	int hour, minute, second;
	private RelativeLayout valve_control;
	private TextView text_apply_irriagte_valve_control, irriUnit, tv_ChanNum,
			userName, crop, area, valueControlID, count, totalIrriTime,
			irriWater, irriDuration;
	private String units, ChanNum, ValueControlChanID;
	private int isOpens, isPlan;
	private ProgressDialog progressDialog;
	private List<infoList> beens;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflater = inflater;
		View view = inflater.inflate(
				R.layout.fragment_apply_irrigate_valve_control, container,
				false);

		actionBar = (MainActionBars) view
				.findViewById(R.id.actionbar_apply_irrigat_valve_control);
		actionBar.setLeftIcon(R.drawable.btn_back_selector);
		actionBar.setRightText("保存");
		actionBar.setTitle("灌溉阀门控制");
		actionBar.setActionBarOnClickListener(this);
		units = getArguments().getString("units");
		// 灌水延续时间
		valve_control = (RelativeLayout) view
				.findViewById(R.id.layout_apply_irriagte_valve_control);
		valve_control.setOnClickListener(this);
		ValueControlChanID = (String) SharedUtils.getParam(getActivity(),
				"ValueControlChanID", "1");
		ChanNum = (String) SharedUtils.getParam(getActivity(), "ChanNum", "1");

		irriUnit = (TextView) view
				.findViewById(R.id.text_apply_irriagte_valve_control_unit);
		tv_ChanNum = (TextView) view.findViewById(R.id.tv_ChanNum);
		userName = (TextView) view
				.findViewById(R.id.text_apply_irriagte_valve_control_name);
		crop = (TextView) view
				.findViewById(R.id.text_apply_irriagte_valve_control_crop);
		area = (TextView) view
				.findViewById(R.id.text_apply_irriagte_valve_control_area);
		valueControlID = (TextView) view
				.findViewById(R.id.text_apply_irriagte_valve_control_valve);
		count = (TextView) view
				.findViewById(R.id.text_apply_irriagte_valve_control_number);
		totalIrriTime = (TextView) view
				.findViewById(R.id.text_apply_irriagte_valve_control_time);
		irriWater = (TextView) view
				.findViewById(R.id.text_apply_irriagte_valve_control_water);
		irriDuration = (TextView) view
				.findViewById(R.id.text_apply_irriagte_valve_control);
		imageView = (ImageView) view
				.findViewById(R.id.image_apply_irriagte_valve_control_unit);

		if (NetStatusUtil.isNetValid(getActivity())) {
			init();
		} else {
			Toast.makeText(getActivity(), "当前网络不可用！请检查您的网络状态！", Toast.LENGTH_SHORT)
					.show();
		}

		isOpen = (ImageView) view
				.findViewById(R.id.image_irriagte_valve_control_isopen);
		isOpen.setOnClickListener(this);
		return view;
	}

	private void init() {
		String str2 = "";
		try {
			str2 = java.net.URLEncoder.encode(ValueControlChanID, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		RequestParams param3 = new RequestParams(URL.findValueControlInfo
				+ str2); // 网址(请替换成实际的网址)
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
					// ...
					progressDialog.dismiss();
				} else { // 其他错误 
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
				Gson gson = new Gson();
				ApplyIrrigateControlValueBean fromJson = gson.fromJson(arg0,
						ApplyIrrigateControlValueBean.class);
				beens = fromJson.getAuthNameList();
				if (!CheckUtil.IsEmpty(ChanNum)) {
					tv_ChanNum.setText(ChanNum);
				} else {
					tv_ChanNum.setText("");
				}
				if ("1".equals(beens.get(0).getValueControlSwitch())) {
					imageView.setImageResource(R.drawable.color_17);
				}
				if (!CheckUtil.IsEmpty(beens)) {
					if (!CheckUtil.IsEmpty(beens.get(0).getGrowersName())) {
						userName.setText(beens.get(0).getGrowersName());
					} else {
						userName.setText("");
					}
					if (!CheckUtil.IsEmpty(beens.get(0).getCropName())) {
						crop.setText(beens.get(0).getCropName());
					} else {
						crop.setText("");
					}
					if (!CheckUtil.IsEmpty(beens.get(0).getArea())) {
						area.setText(beens.get(0).getArea() + "亩");
					} else {
						area.setText("0亩");
					}
					if (!CheckUtil.IsEmpty(beens.get(0).getValueControlID())) {
						valueControlID
								.setText(beens.get(0).getValueControlID());
					} else {
						valueControlID.setText("");
					}
					if (!CheckUtil.IsEmpty(beens.get(0).getIrriCount())) {
						count.setText(beens.get(0).getIrriCount() + "次");
					} else {
						count.setText("0次");
					}
					if (!CheckUtil.IsEmpty(beens.get(0).getTotalIrriTime())) {
						totalIrriTime.setText(beens.get(0).getTotalIrriTime());
					} else {
						totalIrriTime.setText("00:00:00");
					}
					if (!CheckUtil.IsEmpty(beens.get(0).getIrriWater())) {
						irriWater.setText(beens.get(0).getIrriWater() + "m³");
					} else {
						irriWater.setText("0m³");
					}
					if (!CheckUtil.IsEmpty(beens.get(0).getIrriDuration())) {
						irriDuration.setText(beens.get(0).getIrriDuration());
					} else {
						irriDuration.setText("00:00:00");
					}
					if (beens.get(0).getValueControlSwitch().equals("0")) {
						isOpens = 0;
						isOpen.setImageResource(R.drawable.off);
					} else {
						isOpens = 1;
						isOpen.setImageResource(R.drawable.on);
					}
				}
				if (!CheckUtil.IsEmpty(units)) {
					irriUnit.setText(units);
				} else {
					irriUnit.setText("");
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
			ApplyIrrigateSingleValveFragment fragment = new ApplyIrrigateSingleValveFragment();
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			Bundle bundle = new Bundle();
			bundle.putString("units", units);
			fragment.setArguments(bundle);
			transaction.setCustomAnimations(
					R.anim.slide_fragment_horizontal_right_in,
					R.anim.slide_fragment_horizontal_left_out);
			transaction.replace(R.id.fl, fragment, "main");
			transaction.commit();
			break;
		case R.id.acitionbar_right:
			if (!NetStatusUtil.isNetValid(getActivity())) {
				Toast.makeText(getActivity(), "当前网络不可用！请检查您的网络状态！", Toast.LENGTH_SHORT)
						.show();
			} else {
				RequestParams param2 = new RequestParams(
						URL.updateValueControlIrriDuration); // 网址(请替换成实际的网址)
				// params.addQueryStringParameter("key", "value"); //
				// 参数(请替换成实际的参数与值)
				progressDialog = ProgressDialog.show(getActivity(),
						"Loading...", "Please wait...", true, false);
				JSONObject js_request = new JSONObject();
				try {
					param2.setAsJsonContent(true);
					js_request.put("valueControlChanID", ValueControlChanID);
					js_request.put("irriDuration", irriDuration.getText()
							.toString());
					param2.setBodyContent(js_request.toString());
				} catch (Exception e) {
					e.printStackTrace();
					param2.setAsJsonContent(true);
				}// 根据实际需求添加相应键值对

				x.http().request(HttpMethod.PUT, param2,
						new CommonCallback<String>() {
							@Override
							public void onCancelled(CancelledException arg0) {

							}

							// 注意:如果是自己onSuccess回调方法里写了一些导致程序崩溃的代码，也会回调道该方法，因此可以用以下方法区分是网络错误还是其他错误
							// 还有一点，网络超时也会也报成其他错误，还需具体打印出错误内容比较容易跟踪查看
							@Override
							public void onError(Throwable ex,
									boolean isOnCallback) {

								Toast.makeText(x.app(), ex.getMessage(),
										Toast.LENGTH_LONG).show();
								if (ex instanceof HttpException) { // 网络错误 
									HttpException httpEx = (HttpException) ex;
									int responseCode = httpEx.getCode();
									String responseMsg = httpEx.getMessage();
									String errorResult = httpEx.getResult();
									Toast.makeText(getActivity(), "请求失败",
											Toast.LENGTH_SHORT);
									// ...
									progressDialog.dismiss();
								} else { // 其他错误 
									// ...
									Toast.makeText(getActivity(), "请求失败",
											Toast.LENGTH_SHORT);
									progressDialog.dismiss();
								}

							}

							// 不管成功或者失败最后都会回调该接口
							@Override
							public void onFinished() {
							}

							@Override
							public void onSuccess(String arg0) {
								Toast.makeText(getActivity(), "请求成功",
										Toast.LENGTH_SHORT);
								Gson gson = new Gson();
								progressDialog.dismiss();
							}
						});
			}
			break;
		case R.id.image_irriagte_valve_control_isopen:
			if (!NetStatusUtil.isNetValid(getActivity())) {
				Toast.makeText(getActivity(), "当前网络不可用！", Toast.LENGTH_SHORT)
						.show();
			} else {
				if (1 == isOpens) {
					String str2 = "";
					try {
						str2 = java.net.URLEncoder.encode(ValueControlChanID,
								"UTF-8");
					} catch (UnsupportedEncodingException e1) {
						e1.printStackTrace();
					}
					RequestParams param3 = new RequestParams(
							URL.updateValueControlSwitch); // 网址(请替换成实际的网址)
					// params.addQueryStringParameter("key", "value"); //
					// 参数(请替换成实际的参数与值)
					progressDialog = ProgressDialog.show(getActivity(),
							"Loading...", "Please wait...", true, false);
					JSONObject js_request1 = new JSONObject();
					try {
						param3.setAsJsonContent(true);
						js_request1.put("valueControlChanID",
								ValueControlChanID);
						js_request1.put("valueControlSwitch", 0);
						isOpens = 0;
						param3.setBodyContent(js_request1.toString());
					} catch (Exception e) {
						e.printStackTrace();
						param3.setAsJsonContent(true);
					}// 根据实际需求添加相应键值对

					x.http().request(HttpMethod.PUT, param3,
							new CommonCallback<String>() {
								@Override
								public void onCancelled(CancelledException arg0) {

								}

								// 注意:如果是自己onSuccess回调方法里写了一些导致程序崩溃的代码，也会回调道该方法，因此可以用以下方法区分是网络错误还是其他错误
								// 还有一点，网络超时也会也报成其他错误，还需具体打印出错误内容比较容易跟踪查看
								@Override
								public void onError(Throwable ex,
										boolean isOnCallback) {

									Toast.makeText(x.app(), ex.getMessage(),
											Toast.LENGTH_LONG).show();
									if (ex instanceof HttpException) { // 网络错误 
										HttpException httpEx = (HttpException) ex;
										int responseCode = httpEx.getCode();
										String responseMsg = httpEx
												.getMessage();
										String errorResult = httpEx.getResult();
										// ...
										progressDialog.dismiss();
									} else { // 其他错误 
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
									if (isOpens == 0) {
										isOpen.setImageResource(R.drawable.off);
										imageView
												.setImageResource(R.drawable.value_selected);
									}
									if (isOpens == 1) {
										isOpen.setImageResource(R.drawable.on);
										imageView
												.setImageResource(R.drawable.color_17);
									}
									progressDialog.dismiss();
								}
							});
				} else {
					String str2 = "";
					try {
						str2 = java.net.URLEncoder.encode(ValueControlChanID,
								"UTF-8");
					} catch (UnsupportedEncodingException e1) {
						e1.printStackTrace();
					}
					RequestParams param3 = new RequestParams(
							URL.acquireIsExistsGroupPlan + str2); // 网址(请替换成实际的网址)
					// params.addQueryStringParameter("key", "value"); //
					// 参数(请替换成实际的参数与值)
					progressDialog = ProgressDialog.show(getActivity(),
							"Loading...", "Please wait...", true, false);
					JSONObject js_request2 = new JSONObject();
					try {
						param3.setAsJsonContent(true);
					} catch (Exception e) {
						e.printStackTrace();
						param3.setAsJsonContent(true);
					}// 根据实际需求添加相应键值对

					x.http().request(HttpMethod.GET, param3,
							new CommonCallback<String>() {
								@Override
								public void onCancelled(CancelledException arg0) {

								}

								// 注意:如果是自己onSuccess回调方法里写了一些导致程序崩溃的代码，也会回调道该方法，因此可以用以下方法区分是网络错误还是其他错误
								// 还有一点，网络超时也会也报成其他错误，还需具体打印出错误内容比较容易跟踪查看
								@Override
								public void onError(Throwable ex,
										boolean isOnCallback) {

									Toast.makeText(x.app(), ex.getMessage(),
											Toast.LENGTH_LONG).show();
									if (ex instanceof HttpException) { // 网络错误 
										HttpException httpEx = (HttpException) ex;
										int responseCode = httpEx.getCode();
										String responseMsg = httpEx
												.getMessage();
										String errorResult = httpEx.getResult();
										// ...
										progressDialog.dismiss();
									} else { // 其他错误 
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
									Gson gson = new Gson();
									ApplyIrrigateControlValueBean fromJson = gson
											.fromJson(
													arg0,
													ApplyIrrigateControlValueBean.class);
									progressDialog.dismiss();
									IrriGroupStateBean bean = gson.fromJson(
											arg0, IrriGroupStateBean.class);
									if ("1".equals(bean.getCode())) {
										new AlertDialog.Builder(getActivity())
												.setTitle("系统提示")
												// 设置对话框标题
												.setMessage(
														"您当前操作的阀门已存在灌溉计划，请选择根据原灌溉计划实行或重新设定延续时长 如无需定时关闭可选择不设置时长！")
												// 设置显示的内容
												.setPositiveButton(
														"取消",
														new DialogInterface.OnClickListener() {// 添加确定按钮

															@Override
															public void onClick(
																	DialogInterface dialog,
																	int which) {// 确定按钮的响应事件
																dialog.dismiss();
															}
														})
												.setNegativeButton(
														"按原计划",
														new DialogInterface.OnClickListener() {// 添加确定按钮

															@Override
															public void onClick(
																	DialogInterface dialog,
																	int which) {// 确定按钮的响应事件
																RequestParams param3 = new RequestParams(
																		URL.updateValueControlSwitch); // 网址(请替换成实际的网址)
																// params.addQueryStringParameter("key",
																// "value"); //
																// 参数(请替换成实际的参数与值)
																progressDialog = ProgressDialog
																		.show(getActivity(),
																				"Loading...",
																				"Please wait...",
																				true,
																				false);
																JSONObject js_request1 = new JSONObject();
																try {
																	param3.setAsJsonContent(true);
																	js_request1
																			.put("valueControlChanID",
																					ValueControlChanID);
																	if (isOpens == 0) {
																		isOpens = 1;
																		js_request1
																				.put("valueControlSwitch",
																						1);
																	} else {
																		isOpens = 0;
																		js_request1
																				.put("valueControlSwitch",
																						0);
																	}
																	js_request1
																			.put("isNewPlan",
																					2);
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
																					public void onError(
																							Throwable ex,
																							boolean isOnCallback) {

																						Toast.makeText(
																								x.app(),
																								ex.getMessage(),
																								Toast.LENGTH_LONG)
																								.show();
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
																							progressDialog
																									.dismiss();
																						} else { // 其他错误
																									// 
																							// ...
																							progressDialog
																									.dismiss();
																						}

																					}

																					// 不管成功或者失败最后都会回调该接口
																					@Override
																					public void onFinished() {
																					}

																					@Override
																					public void onSuccess(
																							String arg0) {
																						Toast.makeText(
																								getActivity(),
																								"请求成功",
																								Toast.LENGTH_SHORT);
																						Gson gson = new Gson();
																						if (isOpens == 0) {
																							isOpen.setImageResource(R.drawable.off);
																							imageView
																									.setImageResource(R.drawable.value_selected);
																						}
																						if (isOpens == 1) {
																							isOpen.setImageResource(R.drawable.on);
																							imageView
																									.setImageResource(R.drawable.color_17);
																						}
																						progressDialog
																								.dismiss();
																					}
																				});
															}
														})
												.setNeutralButton(
														"设置时长",
														new DialogInterface.OnClickListener() {// 添加确定按钮

															@Override
															public void onClick(
																	DialogInterface dialog,
																	int which) {// 确定按钮的响应事件
																isPlan = 1;
																showDateTimePicker(mInflater);
															}
														}).show();// 在按键响应事件中显示此对话框
									} else {
										new AlertDialog.Builder(getActivity())
												.setTitle("系统提示")
												// 设置对话框标题
												.setMessage(
														"您正在打开一个没有灌溉计划的阀门 是否设置持续时长？")
												// 设置显示的内容
												.setPositiveButton(
														"取消",
														new DialogInterface.OnClickListener() {// 添加确定按钮

															@Override
															public void onClick(
																	DialogInterface dialog,
																	int which) {// 确定按钮的响应事件
																dialog.dismiss();
															}
														})
												.setNegativeButton(

														"设置时长",
														new DialogInterface.OnClickListener() {// 添加确定按钮

															@Override
															public void onClick(
																	DialogInterface dialog,
																	int which) {// 确定按钮的响应事件
																isPlan = 1;
																showDateTimePicker(mInflater);
															}
														})
												.setNeutralButton(
														"不设置",
														new DialogInterface.OnClickListener() {// 添加确定按钮

															@Override
															public void onClick(
																	DialogInterface dialog,
																	int which) {// 确定按钮的响应事件

																RequestParams param3 = new RequestParams(
																		URL.updateValueControlSwitch); // 网址(请替换成实际的网址)
																// params.addQueryStringParameter("key",
																// "value"); //
																// 参数(请替换成实际的参数与值)
																progressDialog = ProgressDialog
																		.show(getActivity(),
																				"Loading...",
																				"Please wait...",
																				true,
																				false);
																JSONObject js_request1 = new JSONObject();
																try {
																	param3.setAsJsonContent(true);
																	js_request1
																			.put("valueControlChanID",
																					ValueControlChanID);
																	if (isOpens == 0) {
																		isOpens = 1;
																		js_request1
																				.put("valueControlSwitch",
																						1);
																	} else {
																		isOpens = 0;
																		js_request1
																				.put("valueControlSwitch",
																						0);
																	}
																	js_request1
																			.put("isNewPlan",
																					0);
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
																					public void onError(
																							Throwable ex,
																							boolean isOnCallback) {

																						Toast.makeText(
																								x.app(),
																								ex.getMessage(),
																								Toast.LENGTH_LONG)
																								.show();
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
																							progressDialog
																									.dismiss();
																						} else { // 其他错误
																							// ...
																							progressDialog
																									.dismiss();
																						}

																					}

																					// 不管成功或者失败最后都会回调该接口
																					@Override
																					public void onFinished() {
																					}

																					@Override
																					public void onSuccess(
																							String arg0) {
																						Toast.makeText(
																								getActivity(),
																								"请求成功",
																								Toast.LENGTH_SHORT);
																						Gson gson = new Gson();
																						if (isOpens == 0) {
																							isOpen.setImageResource(R.drawable.off);
																							imageView
																									.setImageResource(R.drawable.value_selected);
																						}
																						if (isOpens == 1) {
																							isOpen.setImageResource(R.drawable.on);
																							imageView
																									.setImageResource(R.drawable.color_17);
																						}
																						progressDialog
																								.dismiss();
																					}
																				});
															}
														}).show();// 在按键响应事件中显示此对话框
									}
								}
							});
				}
			}
			break;
		case R.id.layout_apply_irriagte_valve_control:
			showDateTimePicker(mInflater);
			break;
		}
	}

	/**
	 * @Description: TODO 弹出日期时间选择器
	 */
	private void showDateTimePicker(LayoutInflater inflater) {
		Calendar calendar = Calendar.getInstance();
		int hour = 0;
		int minute = 0;
		int second = 0;

		dialog = new Dialog(getActivity());
		dialog.setTitle("请选择灌水持续时间");
		// 找到dialog的布局文件
		mInflater = inflater;
		View view = inflater.inflate(R.layout.times, null);

		// 时
		final WheelView wv_hours = (WheelView) view
				.findViewById(R.id.hour_times);
		wv_hours.setAdapter(new NumericWheelAdapter(0, 23));
		wv_hours.setCyclic(true);
		wv_hours.setLabel("时");// 添加文字
		wv_hours.setCurrentItem(hour);

		// 分
		final WheelView wv_minute = (WheelView) view
				.findViewById(R.id.minute_times);
		wv_minute.setAdapter(new NumericWheelAdapter(0, 59));
		wv_minute.setCyclic(true);
		wv_minute.setLabel("分");// 添加文字
		wv_minute.setCurrentItem(minute);

		// 秒
		final WheelView wv_second = (WheelView) view
				.findViewById(R.id.second_times);
		wv_second.setAdapter(new NumericWheelAdapter(0, 59));
		wv_second.setCyclic(true);
		wv_second.setCurrentItem(second);
		wv_second.setLabel("秒");// 添加文字
		// 根据屏幕密度来指定选择器字体的大小
		// int textSize = 0;
		//
		// textSize = 18;
		//
		// wv_hours.TEXT_SIZE = textSize;
		// wv_minute.TEXT_SIZE = textSize;
		Button btn_sure = (Button) view.findViewById(R.id.time_sures);
		Button btn_cancel = (Button) view.findViewById(R.id.time_canles);
		// 确定
		btn_sure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// 如果是个数,则显示为"02"的样式
				String parten = "00";
				DecimalFormat decimal = new DecimalFormat(parten);
				irriDuration.setText(decimal.format(wv_hours.getCurrentItem())
						+ ":" + decimal.format(wv_minute.getCurrentItem())
						+ ":" + decimal.format(wv_second.getCurrentItem()));
				ApplyIrrigateControlValveFragment.this.hour = wv_hours
						.getCurrentItem();
				ApplyIrrigateControlValveFragment.this.minute = wv_minute
						.getCurrentItem();
				ApplyIrrigateControlValveFragment.this.second = wv_second
						.getCurrentItem();

				if (isPlan == 1) {
					RequestParams param3 = new RequestParams(
							URL.updateValueControlSwitch); // 网址(请替换成实际的网址)
					// params.addQueryStringParameter("key", "value"); //
					// 参数(请替换成实际的参数与值)
					progressDialog = ProgressDialog.show(getActivity(),
							"Loading...", "Please wait...", true, false);
					JSONObject js_request1 = new JSONObject();
					try {
						param3.setAsJsonContent(true);
						js_request1.put("valueControlChanID",
								ValueControlChanID);
						if (isOpens == 0) {
							isOpens = 1;
							js_request1.put("valueControlSwitch", 1);
						} else {
							isOpens = 0;
							js_request1.put("valueControlSwitch", 0);
						}
						if (CheckUtil
								.IsEmpty(irriDuration.getText().toString())
								|| "00:00:00".equals(irriDuration.getText()
										.toString())) {
							js_request1.put("isNewPlan", 0);
						}
						js_request1.put("isNewPlan", 1);
						js_request1.put("irriDuration", irriDuration.getText()
								.toString());
						param3.setBodyContent(js_request1.toString());
					} catch (Exception e) {
						e.printStackTrace();
						param3.setAsJsonContent(true);
					}// 根据实际需求添加相应键值对

					x.http().request(HttpMethod.PUT, param3,
							new CommonCallback<String>() {
								@Override
								public void onCancelled(CancelledException arg0) {

								}

								// 注意:如果是自己onSuccess回调方法里写了一些导致程序崩溃的代码，也会回调道该方法，因此可以用以下方法区分是网络错误还是其他错误
								// 还有一点，网络超时也会也报成其他错误，还需具体打印出错误内容比较容易跟踪查看
								@Override
								public void onError(Throwable ex,
										boolean isOnCallback) {

									Toast.makeText(x.app(), ex.getMessage(),
											Toast.LENGTH_LONG).show();
									if (ex instanceof HttpException) { // 网络错误 
										HttpException httpEx = (HttpException) ex;
										int responseCode = httpEx.getCode();
										String responseMsg = httpEx
												.getMessage();
										String errorResult = httpEx.getResult();
										// ...
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
									Toast.makeText(getActivity(), "请求成功",
											Toast.LENGTH_SHORT);
									Gson gson = new Gson();
									if (isOpens == 0) {
										isOpen.setImageResource(R.drawable.off);
										imageView
												.setImageResource(R.drawable.value_selected);
									}
									if (isOpens == 1) {
										isOpen.setImageResource(R.drawable.on);
										imageView
												.setImageResource(R.drawable.color_17);
									}
									progressDialog.dismiss();
								}
							});
				}
				isPlan = 0;
				// 设置日期的显示
				// tv_time.setText((wv_year.getCurrentItem() + START_YEAR) + "-"
				// + decimal.format((wv_month.getCurrentItem() + 1)) + "-"
				// + decimal.format((wv_day.getCurrentItem() + 1)) + " "
				// + decimal.format(wv_hours.getCurrentItem()) + ":"
				// + decimal.format(wv_minute.getCurrentItem()));

				dialog.dismiss();
			}
		});
		// 取消
		btn_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		// 设置dialog的布局,并显示
		dialog.setContentView(view);
		dialog.show();
	}

}
