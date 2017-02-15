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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.adapter.ApplyIrrigateControlAdapter;
import ps.emperor.easy_water.adapter.ArrayWheelAdapter;
import ps.emperor.easy_water.adapter.NumericWheelAdapter;
import ps.emperor.easy_water.entity.ApplyIrrigateControlBean;
import ps.emperor.easy_water.entity.ApplyIrrigateControlBean.infoList;
import ps.emperor.easy_water.entity.ApplyIrrigateControlBean.groupList;
import ps.emperor.easy_water.entity.ApplyIrrigateControlBeans;
import ps.emperor.easy_water.utils.CheckUtil;
import ps.emperor.easy_water.utils.SharedUtils;
import ps.emperor.easy_water.utils.URL;
import ps.emperor.easy_water.view.MainActionBars;
import ps.emperor.easy_water.view.MyGridView;
import ps.emperor.easy_water.view.WheelView;

/**
 * 灌溉组控制
 * 
 * @author 毛国江
 * @version 2016-5-24 下午15:00
 */

@SuppressLint("NewApi")
public class ApplyIrrigateControlFragment extends Fragment implements
		OnClickListener, OnItemClickListener {

	private LayoutInflater mInflater;
	private MainActionBars actionBar;
	private MyGridView gridView;
	private ApplyIrrigateControlAdapter adapter; // 灌溉组控制adapter
	private ImageView irrigatr_control;
	private String units,str1,irriDuration;
	private ProgressDialog progressDialog;
	private List<infoList> beens;
	private List<ps.emperor.easy_water.entity.ApplyIrrigateControlBeans.infoList> been;
	private List<groupList> beans;
	private TextView status, start_time, end_time, group_name;
	private String Engroup[] = new String[26];
	private Button button_operation;
	private TextView unit;
	private Dialog dialog;
	String[] group;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflater = inflater;
		View view = inflater.inflate(R.layout.fragment_apply_irrigate_control,
				container, false);

		actionBar = (MainActionBars) view
				.findViewById(R.id.actionbar_apply_irrigate_control);
		actionBar.setLeftIcon(R.drawable.btn_back_selector);
		actionBar.setRightText("切换");
		actionBar.setTitle("灌溉组控制");
		actionBar.setActionBarOnClickListener(this);

		units = getArguments().getString("units");

		irrigatr_control = (ImageView) view
				.findViewById(R.id.image_apply_irrigate_control);
		irrigatr_control.setOnClickListener(this);
		status = (TextView) view
				.findViewById(R.id.text_apply_irrigate_control_status);
		start_time = (TextView) view
				.findViewById(R.id.text_apply_irrigate_control_time_start);
		end_time = (TextView) view
				.findViewById(R.id.text_apply_irrigate_control_time_end);
		group_name = (TextView) view
				.findViewById(R.id.text_apply_irrigate_control_group_cut);
		button_operation = (Button) view
				.findViewById(R.id.button_apply_irriagte_control_operation);
		unit = (TextView) view
				.findViewById(R.id.text_apply_irrigate_control_unit);
		unit.setText(units);
		button_operation.setOnClickListener(this);
		str1 = (String) SharedUtils.getParam(getActivity(),
				"FirstDerviceID", "");

		Engroup[0] = "A";
		Engroup[1] = "B";
		Engroup[2] = "C";
		Engroup[3] = "D";
		Engroup[4] = "E";
		Engroup[5] = "F";
		Engroup[6] = "G";
		Engroup[7] = "H";
		Engroup[8] = "I";
		Engroup[9] = "J";
		Engroup[10] = "K";
		Engroup[11] = "L";
		Engroup[12] = "M";
		Engroup[13] = "N";
		Engroup[14] = "O";
		Engroup[15] = "P";
		Engroup[16] = "Q";
		Engroup[17] = "R";
		Engroup[18] = "S";
		Engroup[19] = "T";
		Engroup[20] = "U";
		Engroup[21] = "V";
		Engroup[22] = "W";
		Engroup[23] = "X";
		Engroup[24] = "Y";
		Engroup[25] = "Z";

		gridView = (MyGridView) view
				.findViewById(R.id.grid_apply_irrigate_control_fm);
		gridView.setOnItemClickListener(this);
		adapter = new ApplyIrrigateControlAdapter(getActivity());
		group_name.setText("组A");
		init();
		return view;
	}

	private void init() {
		String str2 = "";
		try {
			str1 = java.net.URLEncoder.encode(str1, "UTF-8");
			str2 = java.net.URLEncoder.encode(group_name.getText().toString(),
					"UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		RequestParams param3 = new RequestParams(URL.findIrriGroupControl
				+ str1 + "/" + str2); // 网址(请替换成实际的网址)
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
				ApplyIrrigateControlBean fromJson = gson.fromJson(arg0,
						ApplyIrrigateControlBean.class);
				beens = fromJson.getInfoList();
				beans = fromJson.getGroupList();
				if (!CheckUtil.IsEmpty(beens)) {
					adapter.addData(beens, true);
					gridView.setAdapter(adapter);
					gridView.setVerticalSpacing(5);
					gridView.setPadding(10, 10, 5, 10);
					if (CheckUtil.IsEmpty(beens.get(0).getPlanStat())) {
						status.setText("无计划");
					} else {
						status.setText(beens.get(0).getPlanStat());
					}
					if (CheckUtil.IsEmpty(beens.get(0).getStartTime())) {
						start_time.setText("0000-00-00 00:00");
					} else {
						start_time.setText(beens.get(0).getStartTime());
					}
					if (CheckUtil.IsEmpty(beens.get(0).getEndTime())) {
						end_time.setText("0000-00-00 00:00");
					} else {
						end_time.setText(beens.get(0).getEndTime());
					}
					if (!CheckUtil.IsEmpty(beans)) {
						if (beans.get(0).getGroupNum().equals("1")) {
							group = new String[] { "	组A	" };
						} else if (beans.get(0).getGroupNum().equals("2")) {
							group = new String[] { "	组A	", "	组B	" };
						} else if (beans.get(0).getGroupNum().equals("3")) {
							group = new String[] { "	组A	", "	组B	", "	组C	" };
						} else if (beans.get(0).getGroupNum().equals("4")) {
							group = new String[] { "	组A	", "	组B	", "	组C	",
									"	组D	" };
						} else if (beans.get(0).getGroupNum().equals("5")) {
							group = new String[] { "	组A	", "	组B	", "	组C	",
									"	组D	", "	组E	" };
						} else if (beans.get(0).getGroupNum().equals("6")) {
							group = new String[] { "	组A	", "	组B	", "	组C	",
									"	组D	", "	组E	", "	组F	" };
						} else if (beans.get(0).getGroupNum().equals("7")) {
							group = new String[] { "	组A	", "	组B	", "	组C	",
									"	组D	", "	组E	", "	组F	", "	组G	" };
						} else if (beans.get(0).getGroupNum().equals("8")) {
							group = new String[] { "	组A	", "	组B	", "	组C	",
									"	组D	", "	组E	", "	组F	", "	组G	", "	组H	" };
						} else if (beans.get(0).getGroupNum().equals("9")) {
							group = new String[] { "	组A	", "	组B	", "	组C	",
									"	组D	", "	组E	", "	组F	", "	组G	", "	组H	",
									"	组I	" };
						} else if (beans.get(0).getGroupNum().equals("10")) {
							group = new String[] { "	组A	", "	组B	", "	组C	",
									"	组D	", "	组E	", "	组F	", "	组G	", "	组H	",
									"	组I	", "	组J	" };
						} else if (beans.get(0).getGroupNum().equals("11")) {
							group = new String[] { "	组A	", "	组B	", "	组C	",
									"	组D	", "	组E	", "	组F	", "	组G	", "	组H	",
									"	组I	", "	组J", "	组K	" };
						} else if (beans.get(0).getGroupNum().equals("12")) {
							group = new String[] { "	组A	", "	组B	", "	组C	",
									"	组D	", "	组E	", "	组F	", "	组G	", "	组H	",
									"	组I	", "	组J", "	组K	", "	组L	" };
						} else if (beans.get(0).getGroupNum().equals("13")) {
							group = new String[] { "	组A	", "	组B	", "	组C	",
									"	组D	", "	组E	", "	组F	", "	组G	", "	组H	",
									"	组I	", "	组J", "	组K	", "	组L	", "	组M	" };
						} else if (beans.get(0).getGroupNum().equals("14")) {
							group = new String[] { "	组A	", "	组B	", "	组C	",
									"	组D	", "	组E	", "	组F	", "	组G	", "	组H	",
									"	组I	", "	组J", "	组K	", "	组L	", "	组M	",
									"	组N	" };
						} else if (beans.get(0).getGroupNum().equals("15")) {
							group = new String[] { "	组A	", "	组B	", "	组C	",
									"	组D	", "	组E	", "	组F	", "	组G	", "	组H	",
									"	组I	", "	组J", "	组K	", "	组L	", "	组M	",
									"	组N	", "	组O	" };
						} else if (beans.get(0).getGroupNum().equals("16")) {
							group = new String[] { "	组A	", "	组B	", "	组C	",
									"	组D	", "	组E	", "	组F	", "	组G	", "	组H	",
									"	组I	", "	组J", "	组K	", "	组L	", "	组M	",
									"	组N	", "	组O	", "	组P	" };
						} else if (beans.get(0).getGroupNum().equals("17")) {
							group = new String[] { "	组A	", "	组B	", "	组C	",
									"	组D	", "	组E	", "	组F	", "	组G	", "	组H	",
									"	组I	", "	组J", "	组K	", "	组L	", "	组M	",
									"	组N	", "	组O	", "	组P	", "	组Q	" };
						} else if (beans.get(0).getGroupNum().equals("18")) {
							group = new String[] { "	组A	", "	组B	", "	组C	",
									"	组D	", "	组E	", "	组F	", "	组G	", "	组H	",
									"	组I	", "	组J", "	组K	", "	组L	", "	组M	",
									"	组N	", "	组O	", "	组P	", "	组Q	", "	组R	" };
						} else if (beans.get(0).getGroupNum().equals("19")) {
							group = new String[] { "	组A	", "	组B	", "	组C	",
									"	组D	", "	组E	", "	组F	", "	组G	", "	组H	",
									"	组I	", "	组J", "	组K	", "	组L	", "	组M	",
									"	组N	", "	组O	", "	组P	", "	组Q	", "	组R	",
									"	组S	" };
						} else if (beans.get(0).getGroupNum().equals("20")) {
							group = new String[] { "	组A	", "	组B	", "	组C	",
									"	组D	", "	组E	", "	组F	", "	组G	", "	组H	",
									"	组I	", "	组J", "	组K	", "	组L	", "	组M	",
									"	组N	", "	组O	", "	组P	", "	组Q	", "	组R	",
									"	组S	", "	组T	" };
						} else if (beans.get(0).getGroupNum().equals("21")) {
							group = new String[] { "	组A	", "	组B	", "	组C	",
									"	组D	", "	组E	", "	组F	", "	组G	", "	组H	",
									"	组I	", "	组J", "	组K	", "	组L	", "	组M	",
									"	组N	", "	组O	", "	组P	", "	组Q	", "	组R	",
									"	组S	", "	组T	", "	组U	" };
						} else if (beans.get(0).getGroupNum().equals("22")) {
							group = new String[] { "	组A	", "	组B	", "	组C	",
									"	组D	", "	组E	", "	组F	", "	组G	", "	组H	",
									"	组I	", "	组J", "	组K	", "	组L	", "	组M	",
									"	组N	", "	组O	", "	组P	", "	组Q	", "	组R	",
									"	组S	", "	组T	", "	组U	", "	组V	" };
						} else if (beans.get(0).getGroupNum().equals("23")) {
							group = new String[] { "	组A	", "	组B	", "	组C	",
									"	组D	", "	组E	", "	组F	", "	组G	", "	组H	",
									"	组I	", "	组J", "	组K	", "	组L	", "	组M	",
									"	组N	", "	组O	", "	组P	", "	组Q	", "	组R	",
									"	组S	", "	组T	", "	组U	", "	组V	", "	组W	" };
						} else if (beans.get(0).getGroupNum().equals("24")) {
							group = new String[] { "	组A	", "	组B	", "	组C	",
									"	组D	", "	组E	", "	组F	", "	组G	", "	组H	",
									"	组I	", "	组J", "	组K	", "	组L	", "	组M	",
									"	组N	", "	组O	", "	组P	", "	组Q	", "	组R	",
									"	组S	", "	组T	", "	组U	", "	组V	", "	组W	",
									"	组X	" };
						} else if (beans.get(0).getGroupNum().equals("25")) {
							group = new String[] { "	组A	", "	组B	", "	组C	",
									"	组D	", "	组E	", "	组F	", "	组G	", "	组H	",
									"	组I	", "	组J", "	组K	", "	组L	", "	组M	",
									"	组N	", "	组O	", "	组P	", "	组Q	", "	组R	",
									"	组S	", "	组T	", "	组U	", "	组V	", "	组W	",
									"	组X	", "	组Y	" };
						} else if (beans.get(0).getGroupNum().equals("26")) {
							group = new String[] { "	组A	", "	组B	", "	组C	",
									"	组D	", "	组E	", "	组F	", "	组G	", "	组H	",
									"	组I	", "	组J", "	组K	", "	组L	", "	组M	",
									"	组N	", "	组O	", "	组P	", "	组Q	", "	组R	",
									"	组S	", "	组T	", "	组U	", "	组V	", "	组W	",
									"	组X	", "	组Y	", "	组Z	" };
						}
					}
				}
				progressDialog.dismiss();
			}
		});
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) {
		FragmentManager fgManager = getFragmentManager();
		FragmentTransaction transaction = fgManager.beginTransaction();
		switch (v.getId()) {
		case R.id.acitionbar_left:
			ApplyIrrigateUnitControlFragment fragment = new ApplyIrrigateUnitControlFragment();
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
			ApplyIrrigateSingleValveFragment fragment1 = new ApplyIrrigateSingleValveFragment();
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			Bundle bundle1 = new Bundle();
			bundle1.putString("units", units);
			fragment1.setArguments(bundle1);
			transaction.setCustomAnimations(
					R.anim.slide_fragment_horizontal_left_in,
					R.anim.slide_fragment_horizontal_right_out);
			transaction.replace(R.id.fl, fragment1, "main");
			transaction.commit();
			break;
		case R.id.image_apply_irrigate_control:// 轮灌组dialog选择
			final AlertDialog dialog = new AlertDialog.Builder(getActivity())
					.create();
			dialog.setTitle("选择分组");
			final WheelView catalogWheel = new WheelView(getActivity());
			dialog.setButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					int name = catalogWheel.getCurrentItem();
					if (name == 0) {
						group_name.setText("组A");
					} else if (name == 1) {
						group_name.setText("组B");
					} else if (name == 2) {
						group_name.setText("组C");
					} else if (name == 3) {
						group_name.setText("组D");
					} else if (name == 4) {
						group_name.setText("组E");
					} else if (name == 5) {
						group_name.setText("组F");
					} else if (name == 6) {
						group_name.setText("组G");
					} else if (name == 7) {
						group_name.setText("组H");
					} else if (name == 8) {
						group_name.setText("组I");
					} else if (name == 9) {
						group_name.setText("组J");
					} else if (name == 10) {
						group_name.setText("组K");
					} else if (name == 11) {
						group_name.setText("组L");
					} else if (name == 12) {
						group_name.setText("组M");
					} else if (name == 13) {
						group_name.setText("组N");
					} else if (name == 14) {
						group_name.setText("组O");
					} else if (name == 15) {
						group_name.setText("组P");
					} else if (name == 16) {
						group_name.setText("组Q");
					} else if (name == 17) {
						group_name.setText("组R");
					} else if (name == 18) {
						group_name.setText("组S");
					} else if (name == 19) {
						group_name.setText("组T");
					} else if (name == 20) {
						group_name.setText("组U");
					} else if (name == 21) {
						group_name.setText("组V");
					} else if (name == 22) {
						group_name.setText("组W");
					} else if (name == 23) {
						group_name.setText("组X");
					} else if (name == 24) {
						group_name.setText("组Y");
					} else if (name == 25) {
						group_name.setText("组Z");
					}
					init();
					dialog.dismiss();
					// 实现下ui的刷新
				}
			});
			dialog.setButton2("取消", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});

			catalogWheel.setVisibleItems(5);
			catalogWheel.setCyclic(false);
			catalogWheel.setAdapter(new ArrayWheelAdapter<String>(group));
			dialog.setView(catalogWheel);
			dialog.show();
			break;
		case R.id.button_apply_irriagte_control_operation:
			String str2 = "";
			try {
				str1 = java.net.URLEncoder.encode(str1, "UTF-8");
				str2 = java.net.URLEncoder.encode(group_name.getText()
						.toString(), "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			RequestParams param3 = new RequestParams(URL.judgeIrriGroupState
					+ str1 + "/" + str2); // 网址(请替换成实际的网址)
			// params.addQueryStringParameter("key", "value"); //
			// 参数(请替换成实际的参数与值)
			progressDialog = ProgressDialog.show(getActivity(), "Loading...",
					"Please wait...", true, false);
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
						public void onError(Throwable ex, boolean isOnCallback) {

							Toast.makeText(x.app(), ex.getMessage(),
									Toast.LENGTH_LONG).show();
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
							progressDialog.dismiss();
							ApplyIrrigateControlBeans fromJson = gson.fromJson(
									arg0, ApplyIrrigateControlBeans.class);
							been = fromJson.getAuthNameList();
							if ("1".equals(been.get(0).getCode())) {
								new AlertDialog.Builder(getActivity())
										.setTitle("系统提示")
										// 设置对话框标题
										.setMessage(
												"您当前正在打开一个没有灌溉计划的灌溉组，是否设置灌溉时长")
										// 设置显示的内容
										.setPositiveButton(
												"设置时长",
												new DialogInterface.OnClickListener() {// 添加确定按钮

													@Override
													public void onClick(
															DialogInterface dialog,
															int which) {// 确定按钮的响应事件
														showDateTimePicker(mInflater);
													}
												})
										.setNeutralButton(
												"仅开阀",
												new DialogInterface.OnClickListener() {
													@Override
													public void onClick(
															DialogInterface dialog,
															int which) {
														RequestParams param2 = new
																 RequestParams(URL.controlIrriGroupValue); // 网址(请替换成实际的网址)
																 progressDialog = ProgressDialog.show(getActivity(), "Loading...",
																 "Please wait...", true, false);
																 JSONObject js_request = new JSONObject();
																 try {
																 param2.setAsJsonContent(true);
																 js_request.put("paramState", been.get(0).getCode());
																 js_request.put("firstDerviceID",str1);
																 js_request.put("groupID",beens.get(0).getGroupID());
																 js_request.put("isNewPlan","0");
																 js_request.put("irriDuration","");
																 param2.setBodyContent(js_request.toString());
																 } catch (Exception e) {
																 e.printStackTrace();
																 param2.setAsJsonContent(true);
																 }//根据实际需求添加相应键值对
																
														x.http().request(HttpMethod.PUT, param2, new CommonCallback<String>() {
															@Override
															public void onCancelled(CancelledException arg0) {

															}

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
																Toast.makeText(getActivity(), "成功", Toast.LENGTH_SHORT).show();
																progressDialog.dismiss();
															}
														});
													}
												})
										.setNegativeButton(
												"放弃",
												new DialogInterface.OnClickListener() {// 添加返回按钮

													@Override
													public void onClick(
															DialogInterface dialog,
															int which) {// 响应事件
														dialog.dismiss();
													}

												}).show();// 在按键响应事件中显示此对话框
							} else if ("2".equals(been.get(0).getCode())) {
								new AlertDialog.Builder(getActivity())
										.setTitle("系统提示")
										// 设置对话框标题
										.setMessage(
												"您当前正在关闭一个正在执行灌溉计划的灌溉组，该计划将被随之被终止 ，是否继续")
										// 设置显示的内容
										.setPositiveButton(
												"继续",
												new DialogInterface.OnClickListener() {// 添加确定按钮

													@Override
													public void onClick(
															DialogInterface dialog,
															int which) {// 确定按钮的响应事件
														HttpClose();
													}
												})
										.setNegativeButton(
												"放弃",
												new DialogInterface.OnClickListener() {// 添加返回按钮

													@Override
													public void onClick(
															DialogInterface dialog,
															int which) {// 响应事件
														dialog.dismiss();
													}

												}).show();// 在按键响应事件中显示此对话框

							} else if ("3".equals(been.get(0).getCode())) {
								new AlertDialog.Builder(getActivity())
										.setTitle("系统提示")
										// 设置对话框标题

										.setMessage(
												"您当前正在打开一个设有灌溉计划但尚未执行的灌溉组，该计划仍将被执行")
										// 设置显示的内容

										.setPositiveButton(
												"设置时长",
												new DialogInterface.OnClickListener() {// 添加确定按钮

													@Override
													public void onClick(
															DialogInterface dialog,
															int which) {// 确定按钮的响应事件
														showDateTimePicker(mInflater);
													}
												})
												.setNeutralButton(
												"仅开阀",
												new DialogInterface.OnClickListener() {
													@Override
													public void onClick(
															DialogInterface dialog,
															int which) {
														RequestParams param2 = new
																 RequestParams(URL.controlIrriGroupValue); // 网址(请替换成实际的网址)
																 progressDialog = ProgressDialog.show(getActivity(), "Loading...",
																 "Please wait...", true, false);
																 JSONObject js_request = new JSONObject();
																 try {
																 param2.setAsJsonContent(true);
																 js_request.put("paramState", been.get(0).getCode());
																 js_request.put("firstDerviceID",str1);
																 js_request.put("groupID",beens.get(0).getGroupID());
																 js_request.put("isNewPlan","0");
																 js_request.put("irriDuration","");
																 param2.setBodyContent(js_request.toString());
																 } catch (Exception e) {
																 e.printStackTrace();
																 param2.setAsJsonContent(true);
																 }//根据实际需求添加相应键值对
																
														x.http().request(HttpMethod.PUT, param2, new CommonCallback<String>() {
															@Override
															public void onCancelled(CancelledException arg0) {

															}

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
																Toast.makeText(getActivity(), "成功", Toast.LENGTH_SHORT).show();
																progressDialog.dismiss();
															}
														});
													}
												})
										.setNegativeButton(
												"放弃",
												new DialogInterface.OnClickListener() {// 添加返回按钮

													@Override
													public void onClick(
															DialogInterface dialog,
															int which) {// 响应事件
														dialog.dismiss();
													}

												}).show();// 在按键响应事件中显示此对话框

							} else if ("4".equals(been.get(0).getCode())) {
								new AlertDialog.Builder(getActivity())
										.setTitle("系统提示")
										// 设置对话框标题

										.setMessage(
												"您当前正在关闭一个全组开启但无灌溉计划的灌溉组，是否关闭所有阀门")
										// 设置显示的内容

										.setPositiveButton(
												"确定",
												new DialogInterface.OnClickListener() {// 添加确定按钮

													@Override
													public void onClick(
															DialogInterface dialog,
															int which) {// 确定按钮的响应事件
														HttpClose();
													}
												})
										.setNegativeButton(
												"放弃",
												new DialogInterface.OnClickListener() {// 添加返回按钮

													@Override
													public void onClick(
															DialogInterface dialog,
															int which) {// 响应事件
														dialog.dismiss();
													}

												}).show();// 在按键响应事件中显示此对话框

							} else if ("5".equals(been.get(0).getCode())) {
								new AlertDialog.Builder(getActivity())
										.setTitle("系统提示")
										// 设置对话框标题

										.setMessage(
												"您当前正在打开一个设有单阀灌溉计划且正在执行的灌溉组，是否选择开启其余阀门")
										// 设置显示的内容

										.setPositiveButton(
												"设置时长",
												new DialogInterface.OnClickListener() {// 添加确定按钮

													@Override
													public void onClick(
															DialogInterface dialog,
															int which) {// 确定按钮的响应事件
														showDateTimePicker(mInflater);
													}

												})
										.setNeutralButton(
												"仅开阀",
												new DialogInterface.OnClickListener() {
													@Override
													public void onClick(
															DialogInterface dialog,
															int which) {

														RequestParams param2 = new
																 RequestParams(URL.controlIrriGroupValue); // 网址(请替换成实际的网址)
																 progressDialog = ProgressDialog.show(getActivity(), "Loading...",
																 "Please wait...", true, false);
																 JSONObject js_request = new JSONObject();
																 try {
																 param2.setAsJsonContent(true);
																 js_request.put("paramState", been.get(0).getCode());
																 js_request.put("firstDerviceID",str1);
																 js_request.put("groupID",beens.get(0).getGroupID());
																 js_request.put("isNewPlan","0");
																 js_request.put("irriDuration","");
																 param2.setBodyContent(js_request.toString());
																 } catch (Exception e) {
																 e.printStackTrace();
																 param2.setAsJsonContent(true);
																 }//根据实际需求添加相应键值对
																
														x.http().request(HttpMethod.PUT, param2, new CommonCallback<String>() {
															@Override
															public void onCancelled(CancelledException arg0) {

															}

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
																Toast.makeText(getActivity(), "成功", Toast.LENGTH_SHORT).show();
																progressDialog.dismiss();
															}
														});
													}
												})
										.setNegativeButton(
												"放弃",
												new DialogInterface.OnClickListener() {// 添加返回按钮

													@Override
													public void onClick(
															DialogInterface dialog,
															int which) {// 响应事件
														dialog.dismiss();
													}

												}).show();// 在按键响应事件中显示此对话框

							} else if ("6".equals(been.get(0).getCode())) {
								new AlertDialog.Builder(getActivity())
										.setTitle("系统提示")
										// 设置对话框标题

										.setMessage(
												"您当前正在打开一个单阀开启但无灌溉计划的灌溉组，是否选择开启其余阀门")
										// 设置显示的内容

										.setPositiveButton(
												"设置时长",
												new DialogInterface.OnClickListener() {// 添加确定按钮

													@Override
													public void onClick(
															DialogInterface dialog,
															int which) {// 确定按钮的响应事件
														showDateTimePicker(mInflater);
													}
												})
										.setNeutralButton(
												"仅开阀",
												new DialogInterface.OnClickListener() {
													@Override
													public void onClick(
															DialogInterface dialog,
															int which) {

														RequestParams param2 = new
																 RequestParams(URL.controlIrriGroupValue); // 网址(请替换成实际的网址)
																 progressDialog = ProgressDialog.show(getActivity(), "Loading...",
																 "Please wait...", true, false);
																 JSONObject js_request = new JSONObject();
																 try {
																 param2.setAsJsonContent(true);
																 js_request.put("paramState", been.get(0).getCode());
																 js_request.put("firstDerviceID",str1);
																 js_request.put("groupID",beens.get(0).getGroupID());
																 js_request.put("isNewPlan","0");
																 js_request.put("irriDuration","");
																 param2.setBodyContent(js_request.toString());
																 } catch (Exception e) {
																 e.printStackTrace();
																 param2.setAsJsonContent(true);
																 }//根据实际需求添加相应键值对
																
														x.http().request(HttpMethod.PUT, param2, new CommonCallback<String>() {
															@Override
															public void onCancelled(CancelledException arg0) {

															}

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
																Toast.makeText(getActivity(), "成功", Toast.LENGTH_SHORT).show();
																progressDialog.dismiss();
															}
														});
													}
												})
										.setNegativeButton(
												"放弃",
												new DialogInterface.OnClickListener() {// 添加返回按钮

													@Override
													public void onClick(
															DialogInterface dialog,
															int which) {// 响应事件
														dialog.dismiss();
													}

												}).show();// 在按键响应事件中显示此对话框

							}
						}
					});

			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view,
			final int position, long id) {
		FragmentManager fgManager = getFragmentManager();
		FragmentTransaction transaction = fgManager.beginTransaction();
		ApplyIrrigateControlValveFragment fragment1 = new ApplyIrrigateControlValveFragment();
		Bundle bundle = new Bundle();
		bundle.putString("units", units);
		fragment1.setArguments(bundle);
		transaction.setCustomAnimations(
				R.anim.slide_fragment_horizontal_left_in,
				R.anim.slide_fragment_horizontal_right_out);
		transaction.replace(R.id.fl, fragment1, "main");
		transaction.commit();
	}
	// 初始化时汉字会挤压，在汉字左右两边添加空格即可
	/**
	 * @Description: TODO 弹出日期时间选择器
	 */
	private void showDateTimePicker(LayoutInflater inflater) {
		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);

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
		 final WheelView wv_second = (WheelView)
		 view.findViewById(R.id.second_times);
		 wv_second.setAdapter(new NumericWheelAdapter(0, 59, "%02d"));
		 wv_second.setCyclic(true);
		 wv_second.setCurrentItem(second);
		 wv_second.setLabel("秒");// 添加文字

		 
		 Button btn_sure = (Button) view.findViewById(R.id.time_sures_filter);
		 Button btn_cancel = (Button) view.findViewById(R.id.time_canles_filter);
		// 确定
		 btn_sure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// 如果是个数,则显示为"02"的样式
				String parten = "00";
				DecimalFormat decimal = new DecimalFormat(parten);
				// 设置日期的显示
				// tv_time.setText((wv_year.getCurrentItem() + START_YEAR) + "-"
				// + decimal.format((wv_month.getCurrentItem() + 1)) + "-"
				// + decimal.format((wv_day.getCurrentItem() + 1)) + " "
				// + decimal.format(wv_hours.getCurrentItem()) + ":"
				// + decimal.format(wv_minute.getCurrentItem()));
				irriDuration = wv_hours.getCurrentItem() + ":" + wv_minute.getCurrentItem() + ":" + wv_second.getCurrentItem();
				dialog.dismiss();
			}
		});
		// 取消
		btn_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if("1".equals(been.get(0).getCode())){
					RequestParams param2 = new
							 RequestParams(URL.controlIrriGroupValue); // 网址(请替换成实际的网址)
							 progressDialog = ProgressDialog.show(getActivity(), "Loading...",
							 "Please wait...", true, false);
							 JSONObject js_request = new JSONObject();
							 try {
							 param2.setAsJsonContent(true);
							 js_request.put("paramState", been.get(0).getCode());
							 js_request.put("firstDerviceID",str1);
							 js_request.put("groupID",beens.get(0).getGroupID());
							 js_request.put("isNewPlan","1");
							 js_request.put("irriDuration",irriDuration);
							 param2.setBodyContent(js_request.toString());
							 } catch (Exception e) {
							 e.printStackTrace();
							 param2.setAsJsonContent(true);
							 }//根据实际需求添加相应键值对
							
					x.http().request(HttpMethod.PUT, param2, new CommonCallback<String>() {
						@Override
						public void onCancelled(CancelledException arg0) {

						}

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
							Toast.makeText(getActivity(), "成功", Toast.LENGTH_SHORT).show();
							progressDialog.dismiss();
						}
					});
				}
				if("3".equals(been.get(0).getCode())){
					RequestParams param2 = new
							 RequestParams(URL.controlIrriGroupValue); // 网址(请替换成实际的网址)
							 progressDialog = ProgressDialog.show(getActivity(), "Loading...",
							 "Please wait...", true, false);
							 JSONObject js_request = new JSONObject();
							 try {
							 param2.setAsJsonContent(true);
							 js_request.put("paramState", been.get(0).getCode());
							 js_request.put("firstDerviceID",str1);
							 js_request.put("groupID",beens.get(0).getGroupID());
							 js_request.put("isNewPlan","1");
							 js_request.put("irriDuration",irriDuration);
							 param2.setBodyContent(js_request.toString());
							 } catch (Exception e) {
							 e.printStackTrace();
							 param2.setAsJsonContent(true);
							 }//根据实际需求添加相应键值对
							
					x.http().request(HttpMethod.PUT, param2, new CommonCallback<String>() {
						@Override
						public void onCancelled(CancelledException arg0) {

						}

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
							Toast.makeText(getActivity(), "成功", Toast.LENGTH_SHORT).show();
							progressDialog.dismiss();
						}
					});
				}
				if("5".equals(been.get(0).getCode())){
					new AlertDialog.Builder(getActivity())
							.setTitle("系统提示")
							// 设置对话框标题

							.setMessage(
									"当前灌溉组下已有单阀计划存在 是否以单阀计划为灌溉计划？")
							// 设置显示的内容

							.setPositiveButton(
									"确定",
									new DialogInterface.OnClickListener() {// 添加确定按钮

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {// 确定按钮的响应事件
											RequestParams param2 = new
													 RequestParams(URL.controlIrriGroupValue); // 网址(请替换成实际的网址)
													 progressDialog = ProgressDialog.show(getActivity(), "Loading...",
													 "Please wait...", true, false);
													 JSONObject js_request = new JSONObject();
													 try {
													 param2.setAsJsonContent(true);
													 js_request.put("paramState", been.get(0).getCode());
													 js_request.put("firstDerviceID",str1);
													 js_request.put("groupID",beens.get(0).getGroupID());
													 js_request.put("isNewPlan","2");
													 js_request.put("irriDuration",irriDuration);
													 param2.setBodyContent(js_request.toString());
													 } catch (Exception e) {
													 e.printStackTrace();
													 param2.setAsJsonContent(true);
													 }//根据实际需求添加相应键值对
													
											x.http().request(HttpMethod.PUT, param2, new CommonCallback<String>() {
												@Override
												public void onCancelled(CancelledException arg0) {

												}

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
													Toast.makeText(getActivity(), "成功", Toast.LENGTH_SHORT).show();
													progressDialog.dismiss();
												}
											});
										}

									})
							.setNegativeButton(
									"放弃",
									new DialogInterface.OnClickListener() {// 添加返回按钮

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {// 响应事件
											RequestParams param2 = new
													 RequestParams(URL.controlIrriGroupValue); // 网址(请替换成实际的网址)
													 progressDialog = ProgressDialog.show(getActivity(), "Loading...",
													 "Please wait...", true, false);
													 JSONObject js_request = new JSONObject();
													 try {
													 param2.setAsJsonContent(true);
													 js_request.put("paramState", been.get(0).getCode());
													 js_request.put("firstDerviceID",str1);
													 js_request.put("groupID",beens.get(0).getGroupID());
													 js_request.put("isNewPlan","1");
													 js_request.put("irriDuration",irriDuration);
													 param2.setBodyContent(js_request.toString());
													 } catch (Exception e) {
													 e.printStackTrace();
													 param2.setAsJsonContent(true);
													 }//根据实际需求添加相应键值对
													
											x.http().request(HttpMethod.PUT, param2, new CommonCallback<String>() {
												@Override
												public void onCancelled(CancelledException arg0) {

												}

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
													Toast.makeText(getActivity(), "成功", Toast.LENGTH_SHORT).show();
													progressDialog.dismiss();
												}
											});
										
											dialog.dismiss();
										}

									}).show();// 在按键响应事件中显示此对话框

					
				}
				if("6".equals(been.get(0).getCode())){
					RequestParams param2 = new
							 RequestParams(URL.controlIrriGroupValue); // 网址(请替换成实际的网址)
							 progressDialog = ProgressDialog.show(getActivity(), "Loading...",
							 "Please wait...", true, false);
							 JSONObject js_request = new JSONObject();
							 try {
							 param2.setAsJsonContent(true);
							 js_request.put("paramState", been.get(0).getCode());
							 js_request.put("firstDerviceID",str1);
							 js_request.put("groupID",beens.get(0).getGroupID());
							 js_request.put("isNewPlan","1");
							 js_request.put("irriDuration",irriDuration);
							 param2.setBodyContent(js_request.toString());
							 } catch (Exception e) {
							 e.printStackTrace();
							 param2.setAsJsonContent(true);
							 }//根据实际需求添加相应键值对
							
					x.http().request(HttpMethod.PUT, param2, new CommonCallback<String>() {
						@Override
						public void onCancelled(CancelledException arg0) {

						}

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
							Toast.makeText(getActivity(), "成功", Toast.LENGTH_SHORT).show();
							progressDialog.dismiss();
						}
					});
				}
				dialog.dismiss();
			}
		});
		// 设置dialog的布局,并显示
		dialog.setContentView(view);
		dialog.show();
	}
	public void HttpClose(){
		RequestParams param2 = new
				 RequestParams(URL.controlIrriGroupValue); // 网址(请替换成实际的网址)
				 progressDialog = ProgressDialog.show(getActivity(), "Loading...",
				 "Please wait...", true, false);
				 JSONObject js_request = new JSONObject();
				 try {
				 param2.setAsJsonContent(true);
				 js_request.put("paramState", been.get(0).getCode());
				 js_request.put("firstDerviceID",str1);
				 js_request.put("groupID",beens.get(0).getGroupID());
				 param2.setBodyContent(js_request.toString());
				 } catch (Exception e) {
				 e.printStackTrace();
				 param2.setAsJsonContent(true);
				 }//根据实际需求添加相应键值对
				
		x.http().request(HttpMethod.PUT, param2, new CommonCallback<String>() {
			@Override
			public void onCancelled(CancelledException arg0) {

			}

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
				Toast.makeText(getActivity(), "成功", Toast.LENGTH_SHORT).show();
				progressDialog.dismiss();
			}
		});
	}
}
