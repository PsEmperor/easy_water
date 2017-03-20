package ps.emperor.easy_water.fragment;

import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.text.DecimalFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import java.util.List;

import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.ex.HttpException;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;

import com.google.gson.Gson;

import android.annotation.SuppressLint;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.Interface.OnWheelChangedListener;
import ps.emperor.easy_water.adapter.NumbericWheelAdapter;
import ps.emperor.easy_water.adapter.NumericWheelAdapter;
import ps.emperor.easy_water.entity.IrriGroupStateBean;
import ps.emperor.easy_water.entity.UserReleIrrInfoToOneBean;
import ps.emperor.easy_water.entity.UserReleIrrInfoToOneBean.infoList;
import ps.emperor.easy_water.greendao.DBHelper;
import ps.emperor.easy_water.greendao.Irrigation;
import ps.emperor.easy_water.utils.CheckUtil;
import ps.emperor.easy_water.utils.NetStatusUtil;
import ps.emperor.easy_water.utils.SharedUtils;
import ps.emperor.easy_water.utils.URL;
import ps.emperor.easy_water.view.CustomDialog;
import ps.emperor.easy_water.view.MainActionBars;
import ps.emperor.easy_water.view.WheelView;

/**
 * 基本信息界面 520行 Click事件 1140行 更新基本信息数据库
 * 
 * @author 毛国江
 * @version 2016-5-24 下午15:00
 */

@SuppressLint("NewApi")
public class MainTainBasicInfoFragment extends Fragment implements
		OnClickListener {

	private LayoutInflater mInflater;
	private MainActionBars actionBar;
	private RelativeLayout layout_irriagte_group, layout_orroagte_valve,
			layout_filter, layout__restnight, layout_season, layout_time_long;
	private TextView tv_irriagte_group, tv_orroagte_valve, tv_filter,
			tv_restnight_start, tv_restnight_end, text_season_start,
			text_season_end;
	private Dialog dialog;
	private int id, isFront, first_setting, first_crop;
	private int group, value, long_hour, setLong, setNight, Skip, aNight;
	private String units, filterStart, filterEnd, seasonStart, seasonEnd,
			night_start, night_end;
	private WheelView year;
	private WheelView month;
	private WheelView day;
	private WheelView wv_hour;
	private WheelView wv_minute;
	private WheelView wv_hour_night;
	private WheelView wv_minute_night;
	WheelView wv_hour_nights;
	WheelView wv_minute_nights;
	private TextView tv_time_long;
	private DBHelper dbHelper;
	private List<Irrigation> irrigation;
	private ProgressDialog progressDialog;
	private List<infoList> beens;
	private ToggleButton time_night, time_longs;
	private Boolean isNight = false, isLong = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflater = inflater;
		View view = inflater.inflate(R.layout.fragment_maintain_basic_info,
				container, false);

		actionBar = (MainActionBars) view
				.findViewById(R.id.actionbar_maintain_basic_info);
		actionBar.setLeftIcon(R.drawable.btn_back_selector);
		actionBar.setRightText("保存");
		actionBar.setTitle("基本信息维护");
		actionBar.setActionBarOnClickListener(this);

		dbHelper = DBHelper.getInstance(getActivity()); // 得到DBHelper对象

		units = getArguments().getString("units");

		tv_irriagte_group = (TextView) view
				.findViewById(R.id.text_maintain_basic_info_max_irrigat_group);
		tv_orroagte_valve = (TextView) view
				.findViewById(R.id.text_maintain_basic_info_max_orroagte_valve);
		tv_filter = (TextView) view
				.findViewById(R.id.text_maintain_basic_info_filter);
		tv_restnight_start = (TextView) view
				.findViewById(R.id.text_maintain_basic_info_max_orroagte_restnight_start);
		tv_restnight_end = (TextView) view
				.findViewById(R.id.text_maintain_basic_info_max_orroagte_restnight_end);
		layout_irriagte_group = (RelativeLayout) view
				.findViewById(R.id.layout_maintain_basic_info_max_irrigat_group);
		layout_irriagte_group.setOnClickListener(this);
		layout_orroagte_valve = (RelativeLayout) view
				.findViewById(R.id.layout_maintain_basic_info_max_orroagte_valve);
		layout_orroagte_valve.setOnClickListener(this);
		layout_filter = (RelativeLayout) view
				.findViewById(R.id.layout_maintain_basic_info_max_orroagte_filter);
		layout_filter.setOnClickListener(this);
		layout__restnight = (RelativeLayout) view
				.findViewById(R.id.layout_maintain_basic_info_max_orroagte_restnight);
		layout__restnight.setOnClickListener(this);
		layout_time_long = (RelativeLayout) view
				.findViewById(R.id.layout_apply_irriagte_project_single_time_long);
		layout_time_long.setOnClickListener(this);

		layout_season = (RelativeLayout) view
				.findViewById(R.id.layout_maintain_basic_info_season);
		layout_season.setOnClickListener(this);

		text_season_start = (TextView) view
				.findViewById(R.id.text_maintain_basic_info_max_orroagte_season_start);
		text_season_end = (TextView) view
				.findViewById(R.id.text_maintain_basic_info_max_orroagte_season_end);
		tv_time_long = (TextView) view
				.findViewById(R.id.text_apply_irriagte_project_single_time_long);
		tv_time_long.setText(long_hour + "");
		irrigation = dbHelper.loadContinue(units);

		time_night = (ToggleButton) view
				.findViewById(R.id.toggle_maintain_basic_info_time_night);
		time_longs = (ToggleButton) view
				.findViewById(R.id.toggle_maintain_basic_info_time_longs);
		
		if (NetStatusUtil.isNetValid(getActivity())) {
			init();
		} else {
			Toast.makeText(getActivity(), "当前网络不可用！请检查您的网络状态！", Toast.LENGTH_SHORT)
					.show();
		}
		
		if("00:00".equals(tv_restnight_start.getText().toString())&&"00:00".equals(tv_restnight_end.getText().toString())){
			isNight = false;
		}
		if("24".equals(tv_time_long.getText().toString())){
			isLong = false;
			tv_time_long.setText("连续");
		}
		time_night.setChecked(isNight);
		time_longs.setChecked(isLong);
		
		time_night.setOnClickListener(this);
		time_longs.setOnClickListener(this);
		

		return view;

	}

	@Override
	public void onResume() {
		super.onResume();
		setLong = (int) SharedUtils.getParam(getActivity(), "setLong", 0);
		setNight = (int) SharedUtils.getParam(getActivity(), "setNight", 0);
		Skip = (int) SharedUtils.getParam(getActivity(), "Skip", 0);
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
		RequestParams param3 = new RequestParams(URL.findIrriUnitInfoToOne
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
				UserReleIrrInfoToOneBean fromJson = gson.fromJson(arg0,
						UserReleIrrInfoToOneBean.class);
				beens = fromJson.getAuthNameList();
				if(CheckUtil.IsEmpty(beens)){
					
				}else{
					for (infoList authNameListBean : beens) {
						if (!CheckUtil.IsEmpty(beens.get(0).getMaxGroup())) {
							tv_irriagte_group.setText(beens.get(0).getMaxGroup()
									+ "");
						} else {
							tv_irriagte_group.setText("0");
						}
						if (!CheckUtil.IsEmpty(beens.get(0).getValueNum())) {
							tv_orroagte_valve.setText(beens.get(0).getValueNum()
									+ "");
						} else {
							tv_orroagte_valve.setText("0");
						}
						String parten = "00";
						DecimalFormat decimal = new DecimalFormat(parten);
						if (!CheckUtil.IsEmpty(beens.get(0).getRestStart())) {
							tv_restnight_start.setText(beens.get(0).getRestStart());
						} else {
							tv_restnight_start.setText("00:00");
						}
						if (!CheckUtil.IsEmpty(beens.get(0).getRestEnd())) {
							tv_restnight_end.setText(beens.get(0).getRestEnd());
						} else {
							tv_restnight_end.setText("00:00");
						}
						if("00:00".equals(tv_restnight_start.getText().toString())&&"00:00".equals(tv_restnight_end.getText().toString())){
							isNight = false;
						}
						if (!CheckUtil.IsEmpty(beens.get(0).getFlushTime())) {
							tv_filter.setText(beens.get(0).getFlushTime());
							filterStart = tv_filter.getText().toString()
									.substring(0, 2);
							filterEnd = tv_filter.getText().toString().substring(4, 6);
						} else {
							tv_filter.setText("0");
						}
						if (!CheckUtil.IsEmpty(beens.get(0).getIrriSeasonStart())) {
							text_season_start.setText(beens.get(0)
									.getIrriSeasonStart());
						} else {
							text_season_start.setText("0000-00-00");
						}
						if (!CheckUtil.IsEmpty(beens.get(0).getIrriSeasonEnd())) {
							text_season_end
									.setText(beens.get(0).getIrriSeasonEnd());
						} else {
							text_season_end.setText("0000-00-00");
						}
						if (!CheckUtil.IsEmpty(beens.get(0).getPumpRestTime())) {
							tv_time_long.setText(beens.get(0).getPumpRestTime());
							if("0".equals(beens.get(0).getPumpRestTime())){
								tv_time_long.setText("连续");
								isLong = false;
							}
							long_hour = Integer.valueOf(beens.get(0).getPumpRestTime());
						} else {
							tv_time_long.setText("0");
							long_hour = Integer.valueOf(tv_time_long.getText().toString());
						}
						if(0 == long_hour){
							time_longs.setEnabled(false);
						}else{
							time_longs.setEnabled(true);
						}
						night_start = tv_restnight_start.getText().toString();
						night_end = tv_restnight_end.getText().toString();
						
						group = Integer.valueOf(tv_irriagte_group.getText()
								.toString());
						value = Integer.valueOf(tv_orroagte_valve.getText()
								.toString());

						seasonStart = text_season_start.getText().toString();
						seasonEnd = text_season_end.getText().toString();
						 if (!irrigation.get(0)
						 .getNightStart()
						 .equals(tv_restnight_start.getText()
						 .toString())||!(irrigation.get(0)
						 .getNightEnd()
						 .equals(tv_restnight_end.getText()
						 .toString()))) {
						 dbHelper.updateBasicTime(units,
						 night_start,
						 night_end);
						 }
						 if (!(irrigation.get(0).getIsTimeLong() + "")
						 .equals(tv_time_long.getText().toString())) {
						 dbHelper.updateBasicTimeLong(units, long_hour);
						 }
						 if (!(irrigation.get(0).getGroupnumber() + "")
						 .equals(tv_irriagte_group.getText().toString())) {
						 dbHelper.updateBasicGroup(units,group);
						 }
						 if (!(irrigation.get(0).getValuenumber() + "")
						 .equals(tv_orroagte_valve.getText().toString())) {
						 dbHelper.updateBasicVlaue(units,value);
						 }
						 if (irrigation.get(0).getFlushtime().equals(beens.get(0).getFlushTime()))
						 {
						 }else{
						 dbHelper.updateBasicFilter(units,beens.get(0).getFlushTime());
						 }
						 if((irrigation.get(0).getSeasonStrat()+"")
						 .equals(seasonStart)&&(irrigation.get(0).getSeasonEnd()+"").equals(seasonEnd)){
						 }else{
						 dbHelper.updateBasicSeason(units,seasonStart,seasonEnd);
						 }
						irrigation = dbHelper.loadContinue(units);
				}
				}
				progressDialog.dismiss();
			}
		});
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.acitionbar_left:
			FragmentManager fgManager = getFragmentManager();
			FragmentTransaction transaction = fgManager.beginTransaction();
			if (setLong == 1) {
				setLong = 0;
				SharedUtils.setParam(getActivity(), "setLong", setLong);
				ApplyIrrigateProjectSingleFragment fragment = new ApplyIrrigateProjectSingleFragment();

				// transaction.setCustomAnimations(R.anim.right_in,
				// R.anim.right_out);
				Bundle bundle = new Bundle();
				bundle.putString("units", units);
				isFront = 1;
				bundle.putInt("isFront", isFront);
				fragment.setArguments(bundle);
				transaction.setCustomAnimations(
						R.anim.slide_fragment_horizontal_right_in,
						R.anim.slide_fragment_horizontal_left_out);
				transaction.replace(R.id.fl, fragment, "main");
				transaction.commit();
				break;
			} else if (setLong == 2) {
				setLong = 0;
				SharedUtils.setParam(getActivity(), "setLong", setLong);
				ApplyIrrigateProjectSeasonFragment fragment = new ApplyIrrigateProjectSeasonFragment();
				// transaction.setCustomAnimations(R.anim.right_in,
				// R.anim.right_out);
				Bundle bundle = new Bundle();
				bundle.putString("units", units);
				isFront = 1;
				bundle.putInt("isFront", isFront);
				fragment.setArguments(bundle);
				transaction.setCustomAnimations(
						R.anim.slide_fragment_horizontal_right_in,
						R.anim.slide_fragment_horizontal_left_out);
				transaction.replace(R.id.fl, fragment, "main");
				transaction.commit();
				break;
			}
			if (setNight == 1) {
				setNight = 0;
				SharedUtils.setParam(getActivity(), "setNight", setNight);
				ApplyIrrigateProjectSingleFragment fragment = new ApplyIrrigateProjectSingleFragment();
				Bundle bundle = new Bundle();
				bundle.putString("units", units);
				isFront = 1;
				bundle.putInt("isFront", isFront);
				fragment.setArguments(bundle);
				transaction.setCustomAnimations(
						R.anim.slide_fragment_horizontal_right_in,
						R.anim.slide_fragment_horizontal_left_out);
				transaction.replace(R.id.fl, fragment, "main");
				transaction.commit();
				break;
			} else if (setNight == 2) {
				setNight = 0;
				SharedUtils.setParam(getActivity(), "setNight", setNight);
				ApplyIrrigateProjectSeasonFragment fragment = new ApplyIrrigateProjectSeasonFragment();
				Bundle bundle = new Bundle();
				bundle.putString("units", units);
				isFront = 1;
				bundle.putInt("isFront", isFront);
				fragment.setArguments(bundle);
				transaction.setCustomAnimations(
						R.anim.slide_fragment_horizontal_right_in,
						R.anim.slide_fragment_horizontal_left_out);
				transaction.replace(R.id.fl, fragment, "main");
				transaction.commit();
				break;
			}
			if (Skip == 1) {
				if(tv_irriagte_group.getText().toString().trim().equals(beens.get(0).getMaxGroup())&&
						tv_orroagte_valve.getText().toString().trim().equals(beens.get(0).getValueNum())&&
						tv_filter.getText().toString().trim().equals(beens.get(0).getFlushTime())&&
						(long_hour+"").equals(beens.get(0).getPumpRestTime())&&
						tv_restnight_start.getText().toString().trim().equals(beens.get(0).getRestStart())&&
						tv_restnight_end.getText().toString().trim().equals(beens.get(0).getRestEnd())&&
						text_season_start.getText().toString().trim().equals(beens.get(0).getIrriSeasonStart())&&
						text_season_end.getText().toString().trim().equals(beens.get(0).getIrriSeasonEnd())){
					Skip = 0;
					SharedUtils.setParam(getActivity(), "Skip", Skip);
					MainTainBasicCompileFragment fragment = new MainTainBasicCompileFragment();
					transaction.setCustomAnimations(
							R.anim.slide_fragment_horizontal_right_in,
							R.anim.slide_fragment_horizontal_left_out);
					transaction.replace(R.id.fragment_maintain_present_irrigate,
							fragment, "main");
					transaction.commit();
				}else{
					showAlertDialog(getView());
				}
				break;
			}
		case R.id.acitionbar_right:
			// SharedUtils.setParam(getActivity(), "values",
			// Integer.valueOf(tv_orroagte_valve.getText().toString()));
			// Toast.makeText(getActivity(), "保存成功" + value, Toast.LENGTH_SHORT)
			// .show();
			httpAmend();
			break;
		case R.id.layout_maintain_basic_info_max_irrigat_group:// 最大轮灌组数量
			id = 1;
			showDateTimePicker(mInflater);
			break;
		case R.id.layout_maintain_basic_info_max_orroagte_valve:// 最大阀门开启数量
			id = 2;
			showDateTimePicker(mInflater);
			break;
		case R.id.layout_maintain_basic_info_max_orroagte_filter:// 反冲洗时间
			id = 3;
			showDateTimePicker1(mInflater);
			break;
		case R.id.layout_maintain_basic_info_max_orroagte_restnight:// 夜间灌溉时间段
			showDateTimePicker3(mInflater);
			break;
		case R.id.layout_maintain_basic_info_season:
			showDateTimePicker2(mInflater);
			break;
		case R.id.layout_apply_irriagte_project_single_time_long:// 最长工作时间
			id = 2;
			showDateTimePickers(mInflater);
			break;
		case R.id.toggle_maintain_basic_info_time_night:
			break;
		case R.id.toggle_maintain_basic_info_time_longs:
			break;
		}
	}

	private void httpAmend(){
		if (!NetStatusUtil.isNetValid(getActivity())) {
			Toast.makeText(getActivity(), "当前网络不可用！请检查您的网络状态！", Toast.LENGTH_SHORT)
					.show();
		} else {
			String str1 = (String) SharedUtils.getParam(getActivity(),
					"FirstDerviceID", "");
			RequestParams param2 = new RequestParams(URL.firstDerviceIDIrri); // 网址(请替换成实际的网址)
			// 参数(请替换成实际的参数与值)
			progressDialog = ProgressDialog.show(getActivity(),
					"Loading...", "Please wait...", true, false);
			JSONObject js_request = new JSONObject();
			try {
				param2.setAsJsonContent(true);
				js_request.put("firstDerviceID", str1);
				js_request.put("maxGroup", tv_irriagte_group.getText()
						.toString());
				js_request.put("valueNum", tv_orroagte_valve.getText()
						.toString());
				js_request.put("flushTime", tv_filter.getText().toString());
				if("连续".equals(tv_time_long.getText()
						.toString())){
					js_request.put("pumpRestTime","0");
				}else{
					js_request.put("pumpRestTime" +
							"",tv_time_long.getText()
							.toString() );
				}
				js_request.put("restStart", tv_restnight_start.getText()
						.toString());
				js_request.put("restEnd", tv_restnight_end.getText()
						.toString());
				js_request.put("irriSeasonStart", text_season_start
						.getText().toString());
				js_request.put("irriSeasonEnd", text_season_end.getText()
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
							String parten = "00";
							DecimalFormat decimal = new DecimalFormat(
									parten);
							IrriGroupStateBean fromJson = gson
									.fromJson(arg0,
											IrriGroupStateBean.class);
							progressDialog.dismiss();
							if("0".equals(fromJson.getCode())){
								Toast.makeText(getActivity(), "修改失败！", Toast.LENGTH_SHORT).show();
							}else{
								init();
							}
							// 更新数据库
							if (!irrigation
									.get(0)
									.getNightStart()
									.equals(tv_restnight_start.getText()
											.toString())
									|| !(irrigation.get(0).getNightEnd()
											.equals(tv_restnight_end
													.getText().toString()))) {
								dbHelper.updateBasicTime(units,
										night_start, night_end);
							}
							if (!(irrigation.get(0).getIsTimeLong() + "")
									.equals(tv_time_long.getText()
											.toString())) {
								dbHelper.updateBasicTimeLong(units,
										long_hour);
							}
							if (!(irrigation.get(0).getGroupnumber() + "")
									.equals(tv_irriagte_group.getText()
											.toString())) {
								dbHelper.updateBasicGroup(units, group);
							}
							if (!(irrigation.get(0).getValuenumber() + "")
									.equals(tv_orroagte_valve.getText()
											.toString())) {
								dbHelper.updateBasicVlaue(units, value);
							}
							if ((irrigation.get(0).getSeasonStrat() + "")
									.equals(seasonStart)
									&& (irrigation.get(0).getSeasonEnd() + "")
											.equals(seasonEnd)) {
							} else {
								dbHelper.updateBasicSeason(units,
										seasonStart, seasonEnd);
							}
						}
					});
		}
	}
	
	
	/**
	 * @Description: TODO 弹出日期时间选择器
	 */
	private void showDateTimePickers(LayoutInflater inflater) {
		int hour = 0;

		dialog = new Dialog(getActivity());
		dialog.setTitle("请选择时间");
		// 找到dialog的布局文件
		mInflater = inflater;
		View view = inflater.inflate(R.layout.chose_times, null);

		// 时
		final WheelView wv_hours = (WheelView) view.findViewById(R.id.hour);
		wv_hours.setAdapter(new NumericWheelAdapter(0, 23));
		wv_hours.setCyclic(true);
		wv_hours.setCurrentItem(hour);
		wv_hours.setLabel("时");

		Button btn_sure = (Button) view.findViewById(R.id.chose_time_sures);
		Button btn_cancel = (Button) view.findViewById(R.id.chose_time_canles);
		// 确定
		btn_sure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (id == 1) {
				} else if (id == 2) {
					long_hour = wv_hours.getCurrentItem();
					if(long_hour == 0){
						tv_time_long.setText("连续");
					}else{
						tv_time_long.setText(long_hour + "");
					}
					if(0 == long_hour){
						time_longs.setEnabled(false);
					}else{
						time_longs.setEnabled(true);
					}
					// SharedUtils.setParam(getActivity(), "long_hour",
					// wv_hours.getCurrentItem());
					// }else if(id == 3){
					// //
					// tv_time_interval.setText(wv_hours.getCurrentItem()+"轮");
				}
				// TODO Auto-generated method stub
				// 如果是个数,则显示为"02"的样式
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
				dialog.dismiss();
			}
		});
		// 设置dialog的布局,并显示
		dialog.setContentView(view);
		dialog.show();
	}

	/**
	 * @Description: TODO 弹出日期时间选择器
	 */
	private void showDateTimePicker(LayoutInflater inflater) {
		int hour = 0;

		dialog = new Dialog(getActivity());
		dialog.setTitle("请选择");
		// 找到dialog的布局文件
		mInflater = inflater;
		View view = inflater.inflate(R.layout.chose_times, null);

		// 时
		final WheelView wv_hours = (WheelView) view.findViewById(R.id.hour);
		wv_hours.setAdapter(new NumericWheelAdapter(1, 23));
		wv_hours.setCyclic(true);
		wv_hours.setCurrentItem(hour);

		Button btn_sure = (Button) view.findViewById(R.id.chose_time_sures);
		Button btn_cancel = (Button) view.findViewById(R.id.chose_time_canles);
		// 确定
		btn_sure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (id == 1) {
					first_setting = (Integer) SharedUtils.getParam(
							getActivity(), "first_setting", 0);
					// if (first_setting == 0) {
					group = wv_hours.getCurrentItem() + 1;
					tv_irriagte_group.setText(group + "");
					first_setting = 1;
					SharedUtils.setParam(getActivity(), "first_setting",
							first_setting);
					// } else {
					// if (wv_hours.getCurrentItem() + 1 ==
					// irrigation.get(0).getGroupnumber()) {
					//
					// } else {
					// new AlertDialog.Builder(getActivity())
					// .setTitle("系统提示")
					// // 设置对话框标题
					//
					// .setMessage("更改轮灌组数量将重置所有已设定的编组，您是否确定更改？")
					// // 设置显示的内容
					//
					// .setPositiveButton(
					// "确定",
					// new DialogInterface.OnClickListener() {// 添加确定按钮
					//
					// @Override
					// public void onClick(
					// DialogInterface dialog,
					// int which) {// 确定按钮的响应事件
					//
					// // TODO Auto-generated
					// // method stub
					// Toast.makeText(
					// getActivity(),
					// "更改成功",
					// Toast.LENGTH_SHORT)
					// .show();
					// tv_irriagte_group.setText(wv_hours
					// .getCurrentItem()
					// + 1 + "");
					// // SharedUtils.setParam(
					// // getActivity(),
					// // "groups",
					// // Integer.valueOf(tv_irriagte_group
					// // .getText()
					// // .toString()));
					// dbHelper.updateBasicGroup(units, wv_hours
					// .getCurrentItem()
					// + 1 );
					// dialog.dismiss();
					//
					// }
					//
					// })
					// .setNegativeButton(
					// "返回",
					// new DialogInterface.OnClickListener() {// 添加返回按钮
					//
					// @Override
					// public void onClick(
					// DialogInterface dialog,
					// int which) {// 响应事件
					//
					// // TODO Auto-generated
					// // method stub
					//
					// dialog.dismiss();
					//
					// }
					//
					// }).show();// 在按键响应事件中显示此对话框
					//
					// }

					// }
				} else if (id == 2) {
					first_crop = (Integer) SharedUtils.getParam(getActivity(),
							"first_crop", 0);
					// if (first_crop == 0) {
					value = wv_hours.getCurrentItem() + 1;
					tv_orroagte_valve.setText(value + "");
					first_crop = 1;
					SharedUtils.setParam(getActivity(), "first_crop",
							first_crop);
					// } else {
					// if (wv_hours.getCurrentItem() + 1 ==
					// irrigation.get(0).getValuenumber()) {
					//
					// } else {
					// new AlertDialog.Builder(getActivity())
					// .setTitle("系统提示")
					// // 设置对话框标题
					//
					// .setMessage("更改最大阀门数量将重置所有已设定的编组，您是否确定更改？")
					// // 设置显示的内容
					//
					// .setPositiveButton(
					// "确定",
					// new DialogInterface.OnClickListener() {// 添加确定按钮
					//
					// @Override
					// public void onClick(
					// DialogInterface dialog,
					// int which) {// 确定按钮的响应事件
					//
					// // TODO Auto-generated
					// // method stub
					// Toast.makeText(
					// getActivity(),
					// "更改成功",
					// Toast.LENGTH_SHORT)
					// .show();
					// tv_orroagte_valve.setText(wv_hours
					// .getCurrentItem()
					// + 1 + "");
					// // SharedUtils.setParam(
					// // getActivity(),
					// // "values",
					// // Integer.valueOf(tv_orroagte_valve
					// // .getText()
					// // .toString()));
					// dbHelper.updateBasicVlaue(units,
					// (wv_hours.getCurrentItem() + 1));
					// dialog.dismiss();
					//
					// }
					//
					// })
					// .setNegativeButton(
					// "返回",
					// new DialogInterface.OnClickListener() {// 添加返回按钮
					//
					// @Override
					// public void onClick(
					// DialogInterface dialog,
					// int which) {// 响应事件
					//
					// // TODO Auto-generated
					// // method stub
					//
					// dialog.dismiss();
					//
					// }
					//
					// }).show();// 在按键响应事件中显示此对话框
					//
					// }
					//
					// }
				}
				// TODO Auto-generated method stub
				// 如果是个数,则显示为"02"的样式
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

	/**
	 * @Description: TODO 弹出日期时间选择器
	 */
	private void showDateTimePicker1(LayoutInflater inflater) {
		int hour = 0;
		int minute = 0;

		dialog = new Dialog(getActivity());
		dialog.setTitle("请选择时间段");
		// 找到dialog的布局文件
		mInflater = inflater;
		View view = inflater.inflate(R.layout.time_filter, null);

		final WheelView wv_hour = (WheelView) view
				.findViewById(R.id.hour_filter);
		wv_hour.setAdapter(new NumbericWheelAdapter(0, 23));
		wv_hour.setCyclic(true);
		wv_hour.setLabel("时");// 添加文字

		final WheelView wv_minute = (WheelView) view
				.findViewById(R.id.minute_filter);
		wv_minute.setAdapter(new NumbericWheelAdapter(0, 59));
		wv_minute.setCyclic(true);
		wv_minute.setLabel("分");

		wv_hour.setCurrentItem(hour);
		wv_minute.setCurrentItem(minute);

		Button btn_sure = (Button) view.findViewById(R.id.time_sures_filter);
		Button btn_cancel = (Button) view.findViewById(R.id.time_canles_filter);
		// 确定
		btn_sure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String parten = "00";
				DecimalFormat decimal = new DecimalFormat(parten);
				if (id == 3) {
					// if (wv_hour.getCurrentItem() == 0
					// && wv_minute.getCurrentItem() != 0) {
					// tv_filter.setText(wv_minute.getCurrentItem() + "分钟");
					// dbHelper.updateBasicFilter(units, 0,
					// wv_minute.getCurrentItem());
					// } else if (wv_hour.getCurrentItem() != 0
					// && wv_minute.getCurrentItem() == 0) {
					// tv_filter.setText(wv_hour.getCurrentItem() + "小时");
					// dbHelper.updateBasicFilter(units,
					// wv_hour.getCurrentItem(), 0);
					// } else if (wv_hour.getCurrentItem() == 0
					// && wv_minute.getCurrentItem() == 0) {
					// tv_filter.setText(0 + "");
					// dbHelper.updateBasicFilter(units,
					// wv_hour.getCurrentItem(),
					// wv_minute.getCurrentItem());
					// } else {
					tv_filter.setText(decimal.format(wv_hour.getCurrentItem()) + "小时"
							+ decimal.format(wv_minute.getCurrentItem()) + "分钟");
					// }
					dialog.dismiss();
				}
			}
		});
		// 取消
		btn_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
			}
		});
		// 设置dialog的布局,并显示
		dialog.setContentView(view);
		dialog.show();
	}

	/**
	 * @Description: TODO 弹出日期时间选择器
	 */
	private void showDateTimePicker2(LayoutInflater inflater) {
		mInflater = inflater;
		View view = inflater.inflate(R.layout.time_resean, null);

		dialog = new Dialog(getActivity());
		dialog.setTitle("请选择灌季时长");
		Calendar c = Calendar.getInstance();
		int currentYear = c.get(Calendar.YEAR);
		int currentMonth = c.get(Calendar.MONTH);
		int currentDay = c.get(Calendar.DATE);
		int currentHour = 0;
		int currentMinute = 0;

		String[] months_big = { "1", "3", "5", "7", "8", "10", "12" };
		String[] months_little = { "4", "6", "9", "11" };

		final List<String> list_big = Arrays.asList(months_big);
		final List<String> list_little = Arrays.asList(months_little);

		year = (WheelView) view.findViewById(R.id.year_resean);
		year.setAdapter(new NumbericWheelAdapter(2016, 2036));
		year.setCyclic(true);
		year.setLabel("年");// 添加文字
		year.setCurrentItem(currentYear - 2016);

		month = (WheelView) view.findViewById(R.id.month_resean);
		month.setAdapter(new NumbericWheelAdapter(1, 12));
		month.setCyclic(true);
		month.setLabel("月");// 添加文字
		month.setCurrentItem(currentMonth);

		wv_hour = (WheelView) view.findViewById(R.id.hours_resean);
		wv_hour.setAdapter(new NumbericWheelAdapter(0, 12));
		wv_hour.setCyclic(true);
		wv_hour.setLabel("月");// 添加文字

		wv_minute = (WheelView) view.findViewById(R.id.minutes_resean);
		wv_minute.setAdapter(new NumbericWheelAdapter(0, 23));
		wv_minute.setCyclic(true);
		wv_minute.setLabel("日");

		wv_hour.setCurrentItem(currentHour);
		wv_minute.setCurrentItem(currentMinute);

		day = (WheelView) view.findViewById(R.id.day_resean);
		day.setCyclic(true);
		day.setLabel("日");
		// 判断大小月及是否闰年,用来确定"日"的数据
		if (list_big.contains(String.valueOf(currentMonth + 1))) {
			day.setAdapter(new NumbericWheelAdapter(1, 31));
		} else if (list_little.contains(String.valueOf(currentMonth + 1))) {
			day.setAdapter(new NumbericWheelAdapter(1, 30));
		} else {
			// 闰年
			if ((currentYear % 4 == 0 && currentYear % 100 != 0)
					|| currentYear % 400 == 0)
				day.setAdapter(new NumbericWheelAdapter(1, 29));
			else
				day.setAdapter(new NumbericWheelAdapter(1, 28));
		}
		day.setCurrentItem(currentDay - 1);
		OnWheelChangedListener wheelListener_year = new OnWheelChangedListener() {
			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				int year_num = newValue + 2016;
				// 判断大小月及是否闰年,用来确定"日"的数据
				if (list_big
						.contains(String.valueOf(month.getCurrentItem() + 1))) {
					day.setAdapter(new NumbericWheelAdapter(1, 31));
				} else if (list_little.contains(String.valueOf(month
						.getCurrentItem() + 1))) {
					day.setAdapter(new NumbericWheelAdapter(1, 30));
				} else {
					if ((year_num % 4 == 0 && year_num % 100 != 0)
							|| year_num % 400 == 0)
						day.setAdapter(new NumbericWheelAdapter(1, 29));
					else
						day.setAdapter(new NumbericWheelAdapter(1, 28));
				}
			}
		};

		// 添加"月"监听
		OnWheelChangedListener wheelListener_month = new OnWheelChangedListener() {
			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				int month_num = newValue + 1;
				// 判断大小月及是否闰年,用来确定"日"的数据
				if (list_big.contains(String.valueOf(month_num))) {
					day.setAdapter(new NumbericWheelAdapter(1, 31));
				} else if (list_little.contains(String.valueOf(month_num))) {
					day.setAdapter(new NumbericWheelAdapter(1, 30));
				} else {
					if (((year.getCurrentItem() + 2016) % 4 == 0 && (year
							.getCurrentItem() + 2016) % 100 != 0)
							|| (year.getCurrentItem() + 2016) % 400 == 0)
						day.setAdapter(new NumbericWheelAdapter(1, 29));
					else
						day.setAdapter(new NumbericWheelAdapter(1, 28));
				}
			}
		};

		year.addChangingListener(wheelListener_year);
		month.addChangingListener(wheelListener_month);
		// 找到dialog的布局文件

		Button btn_sure = (Button) view.findViewById(R.id.time_sure_resean);
		Button btn_cancel = (Button) view.findViewById(R.id.time_canle_resean);
		btn_sure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String parten = "00";
				DecimalFormat decimal = new DecimalFormat(parten);
				text_season_start.setText(year.getCurrentItem() + 2016 + "-"
						+ decimal.format(month.getCurrentItem() + 1) + "-"
						+ decimal.format(day.getCurrentItem() + 1));

				Date date1 = new java.util.Date();
				SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
				try {
					date1 = format1.parse(year.getCurrentItem() + 2016 + "-"
							+ decimal.format(month.getCurrentItem() + 1) + "-"
							+ decimal.format(day.getCurrentItem() + 1));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				date1.setMonth(date1.getMonth()
						+ Integer.valueOf(wv_hour.getCurrentItem() + 1));
				date1.setDate(date1.getDate()
						+ Integer.valueOf(wv_minute.getCurrentItem() + 1));
				text_season_end.setText(format1.format(date1));
				dialog.dismiss();
			}
		});
		// 取消
		btn_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
			}
		});
		// 设置dialog的布局,并显示
		dialog.setContentView(view);
		dialog.show();
	}

	private void showDateTimePicker3(LayoutInflater inflater) {
		mInflater = inflater;
		View view = inflater.inflate(R.layout.time_night, null);
		dialog = new Dialog(getActivity());
		dialog.setTitle("请选择夜间休息时间段");
		int currentHour = 0;
		int currentHours = 0;
		int currentMinute = 0;
		int currentMinutes = 0;

		wv_hour_night = (WheelView) view.findViewById(R.id.hour_night);
		wv_hour_night.setAdapter(new NumbericWheelAdapter(0, 23));
		wv_hour_night.setCyclic(true);
		wv_hour_night.setLabel("时");// 添加文字

		wv_minute_night = (WheelView) view.findViewById(R.id.minute_night);
		wv_minute_night.setAdapter(new NumbericWheelAdapter(0, 59));
		wv_minute_night.setCyclic(true);
		wv_minute_night.setLabel("分");

		wv_hour_nights = (WheelView) view.findViewById(R.id.hour_nights);
		wv_hour_nights.setAdapter(new NumbericWheelAdapter(0, 23));
		wv_hour_nights.setCyclic(true);
		wv_hour_nights.setLabel("时");

		wv_minute_nights = (WheelView) view.findViewById(R.id.minute_nights);
		wv_minute_nights.setAdapter(new NumbericWheelAdapter(0, 59));
		wv_minute_nights.setCyclic(true);
		wv_minute_nights.setLabel("分");

		wv_hour_night.setCurrentItem(currentHour);
		wv_minute_night.setCurrentItem(currentHours);
		wv_hour_nights.setCurrentItem(currentMinute);
		wv_minute_nights.setCurrentItem(currentMinutes);

		// 找到dialog的布局文件

		Button btn_sure = (Button) view.findViewById(R.id.time_sure_night);
		Button btn_cancel = (Button) view.findViewById(R.id.time_canle_night);
		btn_sure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String parten = "00";
				DecimalFormat decimal = new DecimalFormat(parten);

				String timestarts = decimal.format(wv_hour_night
						.getCurrentItem())
						+ ":"
						+ decimal.format(wv_minute_night.getCurrentItem());
				Date date = new Date();
				SimpleDateFormat format = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm");
				try {
					date = format.parse("2016-10-24" + " " + timestarts);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				date.setHours(date.getHours()
						+ Integer.valueOf(wv_hour_nights.getCurrentItem()));
				date.setMinutes(date.getMinutes()
						+ Integer.valueOf(wv_minute_nights.getCurrentItem()));

				if (CheckUtil.IsEmpty(timestarts)) {
					tv_restnight_start.setText("00:00");
					tv_restnight_end.setText("00:00");
				} else {
					if (timestarts.equals("0:0")) {
						tv_restnight_start.setText("00:00");
						tv_restnight_end.setText("00:00");
					} else {
						tv_restnight_start.setText(timestarts);
						tv_restnight_end.setText(decimal.format(date.getHours())
								+ ":" + decimal.format(date.getMinutes()));
					}
				}
				if (date.getDate() > 24) {
					SharedUtils.setParam(getActivity(), "isGreaters", 1);
				} else {
					SharedUtils.setParam(getActivity(), "isGreaters", 0);
				}
				night_start = decimal.format(wv_hour_night.getCurrentItem())
						+ ":"
						+ decimal.format(wv_minute_night.getCurrentItem()) + "";
				night_end = decimal.format(date.getHours()) + ":"
						+ decimal.format(date.getMinutes());

				dialog.dismiss();
			}
		});
		// 取消
		btn_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
			}
		});
		// 设置dialog的布局,并显示
		dialog.setContentView(view);
		dialog.show();
	}
	public void showAlertDialog(View view) {

		CustomDialog.Builder builder = new CustomDialog.Builder(getActivity());
		builder.setMessage("当前已进行数据修改但尚未保存 是否确定退出！");
		builder.setTitle("温馨提示");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				MainTainBasicCompileFragment fragment = new MainTainBasicCompileFragment();
				FragmentManager fgManager = getFragmentManager();
				FragmentTransaction transaction = fgManager.beginTransaction();
				transaction.setCustomAnimations(
						R.anim.slide_fragment_horizontal_right_in,
						R.anim.slide_fragment_horizontal_left_out);
				transaction.replace(R.id.fragment_maintain_present_irrigate,
						fragment, "main");
				transaction.commit();
				dialog.dismiss();
				// 设置你的操作事项
			}
		});

		builder.setNegativeButton("取消",
				new android.content.DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});

		builder.create().show();

	}
}
