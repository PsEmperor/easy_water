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
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
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
import ps.emperor.easy_water.utils.CheckUtil;
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
	private ImageView isOpen;
	private Dialog dialog;
	private String hour, minute;
	private RelativeLayout valve_control;
	private TextView text_apply_irriagte_valve_control,irriUnit,tv_ChanNum,userName,crop,area,
	valueControlID,count,totalIrriTime,irriWater,irriDuration;
	private String units,ChanNum,ValueControlChanID;
	private int isOpens;
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
		ValueControlChanID = (String) SharedUtils.getParam(getActivity(), "ValueControlChanID", "1");
		ChanNum = (String) SharedUtils.getParam(getActivity(), "ChanNum", "1");
		
		irriUnit = (TextView) view.findViewById(R.id.text_apply_irriagte_valve_control_unit);
		tv_ChanNum = (TextView) view.findViewById(R.id.tv_ChanNum);
		userName = (TextView) view.findViewById(R.id.text_apply_irriagte_valve_control_name);
		crop = (TextView) view.findViewById(R.id.text_apply_irriagte_valve_control_crop);
		area = (TextView) view.findViewById(R.id.text_apply_irriagte_valve_control_area);
		valueControlID = (TextView) view.findViewById(R.id.text_apply_irriagte_valve_control_valve);
		count = (TextView) view.findViewById(R.id.text_apply_irriagte_valve_control_number);
		totalIrriTime = (TextView) view.findViewById(R.id.text_apply_irriagte_valve_control_time);
		irriWater = (TextView) view.findViewById(R.id.text_apply_irriagte_valve_control_water);
		irriDuration = (TextView) view.findViewById(R.id.text_apply_irriagte_valve_control);
		
		init();
		// 灌水延续时间显示
		text_apply_irriagte_valve_control = (TextView) view
				.findViewById(R.id.text_apply_irriagte_valve_control);
		text_apply_irriagte_valve_control.setText(hour + "时" + minute + "分");

		isOpen = (ImageView) view
				.findViewById(R.id.image_irriagte_valve_control_isopen);
		isOpen.setOnClickListener(this);
		return view;
	}


	private void init() {
		hour = (String) SharedUtils.getParam(getActivity(), "hour_control",
				"00");
		minute = (String) SharedUtils.getParam(getActivity(), "minute_control",
				"00");

		String str2 = "";
		try {
			str2 = java.net.URLEncoder.encode(ValueControlChanID, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		RequestParams param3 = new RequestParams(URL.findValueControlInfo + str2); // 网址(请替换成实际的网址)
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
				if(!CheckUtil.IsEmpty(ChanNum)){
					tv_ChanNum.setText(ChanNum);
				}else{
					tv_ChanNum.setText("");
				}
				if(!CheckUtil.IsEmpty(beens)){
					if(!CheckUtil.IsEmpty(beens.get(0).getGrowersName())){
						userName.setText(beens.get(0).getGrowersName());
					}else{
						userName.setText("");
					}
					if(!CheckUtil.IsEmpty(beens.get(0).getCropName())){
						crop.setText(beens.get(0).getCropName());
					}else{
						crop.setText("");
					}
					if(!CheckUtil.IsEmpty(beens.get(0).getArea())){
						area.setText(beens.get(0).getArea());
					}else{
						area.setText("");
					}
					if(!CheckUtil.IsEmpty(beens.get(0).getValueControlID())){
						valueControlID.setText(beens.get(0).getValueControlID());
					}else{
						valueControlID.setText("");
					}
					if(!CheckUtil.IsEmpty(beens.get(0).getIrriCount())){
						count.setText(beens.get(0).getIrriCount());
					}else{
						count.setText("");
					}
					if(!CheckUtil.IsEmpty(beens.get(0).getTotalIrriTime())){
						totalIrriTime.setText(beens.get(0).getTotalIrriTime());
					}else{
						totalIrriTime.setText("");
					}
					if(!CheckUtil.IsEmpty(beens.get(0).getIrriWater())){
						irriWater.setText(beens.get(0).getIrriWater());
					}else{
						irriWater.setText("");
					}
					if(!CheckUtil.IsEmpty(beens.get(0).getIrriDuration())){
						irriDuration.setText(beens.get(0).getIrriDuration());
					}else{
						irriDuration.setText("");
					}
					if(beens.get(0).getValueControlSwitch().equals("0")){
						isOpens = 0;
						isOpen.setImageResource(R.drawable.off);
					}else{
						isOpens = 1;
						isOpen.setImageResource(R.drawable.on);
					}
				}
				if(!CheckUtil.IsEmpty(units)){
					irriUnit.setText(units);
				}else{
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
			transaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.replace(R.id.fl, fragment, "main");
			transaction.commit();
			break;
		case R.id.acitionbar_right:
			Toast.makeText(getActivity(), "保存", Toast.LENGTH_SHORT).show();
			break;
		case R.id.image_irriagte_valve_control_isopen:
			RequestParams param2 = new RequestParams(URL.updateValueControlSwitch); // 网址(请替换成实际的网址)
			// params.addQueryStringParameter("key", "value"); //
			// 参数(请替换成实际的参数与值)
			progressDialog = ProgressDialog.show(getActivity(), "Loading...",
					"Please wait...", true, false);
			JSONObject js_request = new JSONObject();
			try {
				param2.setAsJsonContent(true);
				js_request.put("valueControlChanID", ValueControlChanID);
				if(isOpens == 0){
					isOpens = 1;
					js_request.put("valueControlSwitch", 1);
				}else{
					isOpens = 0;
					js_request.put("valueControlSwitch", 0);
				}
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
						public void onError(Throwable ex, boolean isOnCallback) {

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
							if(isOpens == 0){
								isOpen.setImageResource(R.drawable.off);
							}
							if(isOpens == 1){
								isOpen.setImageResource(R.drawable.on);
							}
							progressDialog.dismiss();
						}
					});
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
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);

		dialog = new Dialog(getActivity());
		dialog.setTitle("请选择时间");
		// 找到dialog的布局文件
		mInflater = inflater;
		View view = inflater.inflate(R.layout.time_filter, null);

		// 时
		final WheelView wv_hours = (WheelView) view
				.findViewById(R.id.hour_filter);
		wv_hours.setAdapter(new NumericWheelAdapter(0, 23));
		wv_hours.setCyclic(true);
		wv_hours.setLabel("时");// 添加文字
		wv_hours.setCurrentItem(hour);

		// 分
		final WheelView wv_minute = (WheelView) view
				.findViewById(R.id.minute_filter);
		wv_minute.setAdapter(new NumericWheelAdapter(0, 59));
		wv_minute.setCyclic(true);
		wv_minute.setLabel("分");// 添加文字
		wv_minute.setCurrentItem(minute);

		// // 秒
		// final WheelView wv_second = (WheelView)
		// view.findViewById(R.id.second);
		// wv_second.setAdapter(new NumericWheelAdapter(0, 59, "%02d"));
		// wv_second.setCyclic(true);
		// wv_second.setCurrentItem(second);
		// wv_second.setLabel("秒");// 添加文字
		// 根据屏幕密度来指定选择器字体的大小
		// int textSize = 0;
		//
		// textSize = 18;
		//
		// wv_hours.TEXT_SIZE = textSize;
		// wv_minute.TEXT_SIZE = textSize;
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
				text_apply_irriagte_valve_control.setText(decimal
						.format(wv_hours.getCurrentItem())
						+ "时"
						+ decimal.format(wv_minute.getCurrentItem()) + "分");
				SharedUtils.setParam(getActivity(), "hour_control",
						decimal.format(wv_hours.getCurrentItem()));
				SharedUtils.setParam(getActivity(), "minute_control",
						decimal.format(wv_minute.getCurrentItem()));
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
