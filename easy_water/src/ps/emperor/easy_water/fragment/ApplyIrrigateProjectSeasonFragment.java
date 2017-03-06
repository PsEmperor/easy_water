package ps.emperor.easy_water.fragment;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
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
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.Interface.OnWheelChangedListener;
import ps.emperor.easy_water.activity.TimeAvtivityDialogWater;
import ps.emperor.easy_water.adapter.NumbericWheelAdapter;
import ps.emperor.easy_water.adapter.NumericWheelAdapter;
import ps.emperor.easy_water.entity.IrriGroupStateBean;
import ps.emperor.easy_water.greendao.DBHelper;
import ps.emperor.easy_water.greendao.Irrigation;
import ps.emperor.easy_water.greendao.IrrigationGroup;
import ps.emperor.easy_water.greendao.IrrigationIsFirst;
import ps.emperor.easy_water.greendao.IrrigationProject;
import ps.emperor.easy_water.utils.CheckUtil;
import ps.emperor.easy_water.utils.NetworkUtils;
import ps.emperor.easy_water.utils.SharedUtils;
import ps.emperor.easy_water.utils.URL;
import ps.emperor.easy_water.view.CustomDialog;
import ps.emperor.easy_water.view.MainActionBar;
import ps.emperor.easy_water.view.WheelView;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;
import android.view.View.OnClickListener;

/**
 * 全季快速设置
 * 
 * @author 毛国江
 * @version 2016-5-25 上午9：53
 */
@SuppressLint("NewApi")
public class ApplyIrrigateProjectSeasonFragment extends Fragment implements
		OnClickListener, OnCheckedChangeListener {
	private LayoutInflater mInflater;
	private MainActionBar actionBar;
	private ToggleButton time_longs, time_night, time_random;
	private Boolean isLong, isNight, isRandom;
	private Dialog dialog;
	private TextView tv_time_continue, tv_time_number, tv_time_interval,
			tv_time_start;
	private RelativeLayout layout_time_start, layout_time_continue,
			layout_time_number, layout_time_interval;
	private int id, setNight, setLong, round;
	private int isBefore, long_hour, time_number, time_intervals;
	private WheelView current_year;
	private WheelView current_month;
	private WheelView current_day;
	private WheelView current_hour;
	private WheelView current_minute;
	private long temp, compare, compares;
	private DBHelper dbHelper;
	private IrrigationProject irrigationProject;
	private String nContinue, time_start, units, time_max_start, NightStart,
			NightEnd, NContinue;
	private int MatchedNum, isFront;
	private List<Irrigation> irrigation;
	private Button button_season;
	private List<IrrigationIsFirst> firsts;
	private IrrigationIsFirst irrigationIsFirst;
	private List<IrrigationGroup> groups;
	private String[] Continue;
	private ProgressDialog progressDialog;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 100:
				if (CheckUtil.IsEmpty(time_max_start)
						|| "0000-00-00 00:00".equals(time_max_start)) {
					tv_time_start.setText(time_start);
				} else {
					tv_time_start.setText(time_max_start);
				}
				break;
			default:
				break;
			}
		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflater = inflater;
		View view = inflater.inflate(
				R.layout.fragment_apply_irrigate_project_season, container,
				false);

		dbHelper = DBHelper.getInstance(getActivity()); // 得到DBHelper对象
		units = getArguments().getString("units");
		isFront = getArguments().getInt("isFront");
		groups = dbHelper.loadGroupByUnits(units);
		MatchedNum = groups.size();
		irrigationIsFirst = new IrrigationIsFirst();

		actionBar = (MainActionBar) view
				.findViewById(R.id.actionbar_apply_irrigat_project_season);
		actionBar.setLeftIcon(R.drawable.btn_back_selector);
		actionBar.setRightGone();
		actionBar.setTitle("全季快速设定");
		actionBar.setActionBarOnClickListener(this);

		time_night = (ToggleButton) view
				.findViewById(R.id.toggle_apply_irriagte_project_season_time_night);
		time_random = (ToggleButton) view
				.findViewById(R.id.toggle_apply_irriagte_project_season_time_random);
		time_longs = (ToggleButton) view
				.findViewById(R.id.toggle_apply_irriagte_project_season_time_longs);

		layout_time_continue = (RelativeLayout) view
				.findViewById(R.id.layout_apply_irriagte_project_season_time_continue);
		layout_time_continue.setOnClickListener(this);
		layout_time_number = (RelativeLayout) view
				.findViewById(R.id.layout_apply_irriagte_project_season_time_number);
		layout_time_number.setOnClickListener(this);
		layout_time_interval = (RelativeLayout) view
				.findViewById(R.id.layout_apply_irriagte_project_season_time_interval);
		layout_time_interval.setOnClickListener(this);
		layout_time_start = (RelativeLayout) view
				.findViewById(R.id.layout_apply_irriagte_project_season_time_start);
		layout_time_start.setOnClickListener(this);
		button_season = (Button) view
				.findViewById(R.id.button_apply_irriagte_project_season);
		button_season.setOnClickListener(this);

		tv_time_continue = (TextView) view
				.findViewById(R.id.text_apply_irriagte_project_season_time_continue);
		tv_time_number = (TextView) view
				.findViewById(R.id.text_apply_irriagte_project_season_time_number);
		tv_time_interval = (TextView) view
				.findViewById(R.id.text_apply_irriagte_project_season_time_interval);
		tv_time_start = (TextView) view
				.findViewById(R.id.text_apply_irriagte_project_season_time_start);

		init();

		time_night.setChecked(isNight);
		time_random.setChecked(isRandom);
		time_longs.setChecked(isLong);

		time_night.setOnCheckedChangeListener(this);
		time_random.setOnCheckedChangeListener(this);
		time_longs.setOnCheckedChangeListener(this);
		// if(!isMsg){
		// initToggle = false;
		// }

		tv_time_continue.setText(Continue[0] + "小时" + Continue[1] + "分钟");
		tv_time_number.setText(time_number + "次");
		tv_time_interval.setText(time_intervals + "天");
		return view;
	}

	private void init() {
		isNight = (Boolean) SharedUtils.getParam(getActivity(), "Night", false);
		isRandom = (Boolean) SharedUtils.getParam(getActivity(), "Interval",
				false);
		isLong = (Boolean) SharedUtils.getParam(getActivity(), "Long", false);

		irrigation = dbHelper.loadContinue(units);
		NightStart = irrigation.get(0).getNightStart();
		NightEnd = irrigation.get(0).getNightEnd();
		long_hour = irrigation.get(0).getIsTimeLong();
		time_number = irrigation.get(0).getNNumber();
		time_intervals = irrigation.get(0).getNRound();

		if (CheckUtil.IsEmpty(long_hour) || long_hour == 0) {
			isLong = false;
		}
		if (24 == long_hour) {
			isLong = false;
			time_longs.setEnabled(false);
		}
		if ((CheckUtil.IsEmpty(NightStart) || NightStart.equals("00:00"))
				&& (CheckUtil.IsEmpty(NightEnd) || NightEnd.equals("00:00"))) {
			isNight = false;
		}
		if (CheckUtil.IsEmpty(irrigation)) {
			nContinue = "00:00";
		} else {
			nContinue = irrigation.get(0).getNContinue() + "";
		}
		Continue = nContinue.split(":");

		new Thread(new Runnable() {

			@Override
			public void run() {
				List<IrrigationProject> listentity = dbHelper
						.loadLastMsgBySessionids(units);
				if (!CheckUtil.IsEmpty(listentity)) {
					Calendar c = Calendar.getInstance();
					Calendar c1 = Calendar.getInstance();
					try {
						c.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm")
								.parse(listentity.get(0).getProjectend()));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					compares = c.getTimeInMillis();
					for (int j = 0; j < listentity.size(); j++) {
						try {
							c1.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm")
									.parse(listentity.get(j).getProjectend()));
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						compare = c1.getTimeInMillis();
						if (compare > compares) {
							temp = compare;
							compare = compares;
							compares = temp;
							Date date = new Date(compares);
							SimpleDateFormat sdf = new SimpleDateFormat(
									"yyyy-MM-dd HH:mm");
							time_start = sdf.format(date);
						}
					}
				} else {
					Date date = new Date(System.currentTimeMillis());
					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm");
					time_start = sdf.format(date);
					time_max_start = time_start;
				}
				if (!CheckUtil.IsEmpty(isFront) && isFront != 0) {
					time_start = (String) SharedUtils.getParam(getActivity(),
							"time_start", "0000-00-00 00:00");
					time_max_start = time_start;
				}
				Message message = new Message();
				message.what = 100;
				handler.sendMessage(message);
			}
		}).start();
	}

	@Override
	public void onClick(View v) {
		FragmentManager fgManager = getFragmentManager();
		FragmentTransaction transaction = fgManager.beginTransaction();
		switch (v.getId()) {
		case R.id.acitionbar_left:
			ApplyIrrigateProjectFragment fragment = new ApplyIrrigateProjectFragment();
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
		case R.id.layout_apply_irriagte_project_season_time_continue:
			id = 1;
			showDateTimePickers(mInflater);// 灌水持续时间
			break;
		case R.id.layout_apply_irriagte_project_season_time_number:
			id = 3;
			showDateTimePicker(mInflater);// 灌溉次数
			break;
		case R.id.layout_apply_irriagte_project_season_time_interval:// 间隔时间
			id = 4;
			showDateTimePicker(mInflater);
			break;
		case R.id.layout_apply_irriagte_project_season_time_start:// 开始时间
			showDateTimePicker1(mInflater);
			break;
		case R.id.button_apply_irriagte_project_season:
			if (!NetworkUtils.isNetworkAvailable(getActivity())) {
				Toast.makeText(getActivity(), "当前网络不可用！", Toast.LENGTH_SHORT)
						.show();
			} else {
				RequestParams param2 = new RequestParams(URL.fullSeasonRound); // 网址(请替换成实际的网址)
				// params.addQueryStringParameter("key", "value"); //
				// 参数(请替换成实际的参数与值)
				progressDialog = ProgressDialog.show(getActivity(),
						"Loading...", "Please wait...", true, false);
				String str1 = (String) SharedUtils.getParam(getActivity(),
						"FirstDerviceID", "");
				JSONObject js_request = new JSONObject();
				try {
					param2.setAsJsonContent(true);
					js_request.put("firstDerviceID", str1);
					js_request.put("startTime", tv_time_start.getText()
							.toString());
					js_request.put("irriDuration", nContinue);
					js_request.put(
							"irriTimes",
							tv_time_number
									.getText()
									.toString()
									.substring(
											0,
											tv_time_number.getText().toString()
													.length() - 1));
					js_request.put(
							"intervalTime",
							tv_time_interval
									.getText()
									.toString()
									.substring(
											0,
											tv_time_interval.getText()
													.toString().length() - 1));
					if (isNight == true) {
						js_request.put("isNightIrri", 1);
					} else {
						js_request.put("isNightIrri", 0);
					}
					if (isLong == true) {
						js_request.put("isWorkAway", 1);
					} else {
						js_request.put("isWorkAway", 0);
					}
					if (isRandom == true) {
						js_request.put("randomArr", 1);
					} else {
						js_request.put("randomArr", 0);
					}
					param2.setBodyContent(js_request.toString());
				} catch (Exception e) {
					e.printStackTrace();
					param2.setAsJsonContent(true);
				}// 根据实际需求添加相应键值对

				x.http().request(HttpMethod.POST, param2,
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
								System.out.println(arg0);
								progressDialog.dismiss();
								IrriGroupStateBean fromJson = gson
										.fromJson(arg0,
												IrriGroupStateBean.class);
								if("0".equals(fromJson.getCode())){
									Toast.makeText(getActivity(), "添加失败！服务器异常！", Toast.LENGTH_SHORT).show();
								}else if("1".equals(fromJson.getCode())){
									Toast.makeText(getActivity(), "添加成功！", Toast.LENGTH_SHORT).show();
									ApplyIrrigateProjectFragment fragment1 = new ApplyIrrigateProjectFragment();
									// transaction.setCustomAnimations(R.anim.right_in,
									// R.anim.right_out);
									Bundle bundle1 = new Bundle();
									bundle1.putString("units", units);
									fragment1.setArguments(bundle1);
									FragmentManager fgManager = getFragmentManager();
									FragmentTransaction transaction = fgManager.beginTransaction();
									transaction.setCustomAnimations(
											R.anim.slide_fragment_horizontal_right_in,
											R.anim.slide_fragment_horizontal_left_out);
									transaction.replace(R.id.fl, fragment1, "main");
									transaction.commit();
								}else if("2".equals(fromJson.getCode())){
									Toast.makeText(getActivity(), "添加失败！该时间段内已存在计划，请选择其他时间段！", Toast.LENGTH_SHORT).show();
								}
							}
						});
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch (buttonView.getId()) {
		case R.id.toggle_apply_irriagte_project_season_time_night:// 规律
			SharedUtils.setParam(getActivity(), "SingleNight", isChecked);
			if (isChecked) {
				isNight = true;
			} else {
				isNight = false;
			}
			if (isNight == false) {
			} else {
				if ((CheckUtil.IsEmpty(NightStart) || NightStart
						.equals("00:00"))
						&& (CheckUtil.IsEmpty(NightEnd) || NightEnd
								.equals("00:00"))) {
					setNight = 1;
					SharedUtils.setParam(getActivity(), "setNight", setNight);
					Toast.makeText(getActivity(),
							"请设置夜间休息时间，点击右上方按钮保存！" + isChecked,
							Toast.LENGTH_SHORT).show();
					FragmentManager fgManager = getFragmentManager();
					FragmentTransaction transaction = fgManager
							.beginTransaction();
					MainTainBasicInfoFragment fragment = new MainTainBasicInfoFragment();
					// transaction.setCustomAnimations(R.anim.right_in,
					// R.anim.right_out);
					Bundle bundle = new Bundle();
					bundle.putString("units", units);
					fragment.setArguments(bundle);
					transaction.setCustomAnimations(
							R.anim.slide_fragment_horizontal_left_in,
							R.anim.slide_fragment_horizontal_right_out);
					transaction.replace(R.id.fl, fragment, "main");
					transaction.commit();
					Toast.makeText(getActivity(), "开关--" + isChecked,
							Toast.LENGTH_SHORT).show();
				}
			}
			break;
		case R.id.toggle_apply_irriagte_project_season_time_random:// 规律
			Toast.makeText(getActivity(), "开关--" + isChecked,
					Toast.LENGTH_SHORT).show();
			SharedUtils.setParam(getActivity(), "Random", isChecked);
			if (isChecked) {
				isRandom = true;
			} else {
				isRandom = false;
			}
			break;
		case R.id.toggle_apply_irriagte_project_season_time_longs:// 规律
			SharedUtils.setParam(getActivity(), "SingleLong", isChecked);
			if (isChecked) {
				isLong = true;
			} else {
				isLong = false;
			}
			if (isLong == false) {
			} else {
				if (CheckUtil.IsEmpty(long_hour) || long_hour == 0) {
					setLong = 1;
					SharedUtils.setParam(getActivity(), "setLong", setLong);
					Toast.makeText(getActivity(),
							"请设置水泵休息时间，设置0代表连续工作不休息！点击右上方按钮保存！" + isChecked,
							Toast.LENGTH_SHORT).show();
					FragmentManager fgManager = getFragmentManager();
					FragmentTransaction transaction = fgManager
							.beginTransaction();
					MainTainBasicInfoFragment fragment = new MainTainBasicInfoFragment();
					// transaction.setCustomAnimations(R.anim.right_in,
					// R.anim.right_out);
					Bundle bundle = new Bundle();
					bundle.putString("units", units);
					fragment.setArguments(bundle);
					transaction.setCustomAnimations(
							R.anim.slide_fragment_horizontal_left_in,
							R.anim.slide_fragment_horizontal_right_out);
					transaction.replace(R.id.fl, fragment, "main");
					transaction.commit();
					Toast.makeText(getActivity(), "开关--" + isChecked,
							Toast.LENGTH_SHORT).show();
				}
			}
			break;
		}
	}

	/**
	 * @Description: TODO 弹出日期时间选择器
	 */
	private void showDateTimePicker(LayoutInflater inflater) {
		Calendar calendar = Calendar.getInstance();
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

				} else if (id == 3) {
					time_number = wv_hours.getCurrentItem() + 1;
					tv_time_number.setText(time_number + "次");
					dbHelper.updateContinueNum(units, time_number);
				} else if (id == 4) {
					time_intervals = wv_hours.getCurrentItem() + 1;
					tv_time_interval.setText(time_intervals + "天");
					dbHelper.updateContinueRound(units, time_intervals);
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

	@Override
	public void onResume() {
		super.onResume();

	}

	private void showDateTimePickers(LayoutInflater inflater) {
		Calendar calendar = Calendar.getInstance();
		int hour = 0;
		int minute = 0;

		dialog = new Dialog(getActivity());
		dialog.setTitle("请选择");
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
		wv_minute.setCurrentItem(hour);

		Button btn_sure = (Button) view.findViewById(R.id.time_sures_filter);
		Button btn_cancel = (Button) view.findViewById(R.id.time_canles_filter);
		// 确定
		btn_sure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String parten = "00";
				DecimalFormat decimal = new DecimalFormat(parten);
				// tv_time_continue.setText(wv_hours.getCurrentItem()+"小时"+wv_minute.getCurrentItem()+"分钟");
				// if (wv_hours.getCurrentItem() != 0
				// && wv_minute.getCurrentItem() == 0) {
				// nContinue =
				// wv_hours.getCurrentItem()+":"+wv_minute.getCurrentItem();
				// tv_time_continue.setText(wv_hours.getCurrentItem() + "小时");
				// dbHelper.updateContinue(units,wv_hours.getCurrentItem()+":"+wv_minute.getCurrentItem());
				// // SharedUtils.setParam(getActivity(), "nHour", nHour);
				// // SharedUtils.setParam(getActivity(), "nMinutes",
				// // nMinutes);
				// } else if (wv_hours.getCurrentItem() == 0
				// && wv_minute.getCurrentItem() != 0) {
				// nContinue =
				// wv_hours.getCurrentItem()+":"+wv_minute.getCurrentItem();
				// tv_time_continue.setText(wv_minute.getCurrentItem() + "分钟");
				// dbHelper.updateContinue(units,wv_hours.getCurrentItem()+":"+wv_minute.getCurrentItem());
				// } else {
				tv_time_continue.setText(wv_hours.getCurrentItem() + "小时"
						+ wv_minute.getCurrentItem() + "分钟");
				nContinue = decimal.format(wv_hours.getCurrentItem()) + ":"
						+ decimal.format(wv_minute.getCurrentItem());
				dbHelper.updateContinue(units, nContinue);
				// SharedUtils.setParam(getActivity(), "nHour", nHour);
				// SharedUtils.setParam(getActivity(), "nMinutes",
				// nMinutes);
				// }
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
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DATE);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);

		dialog = new Dialog(getActivity());
		dialog.setTitle("请选择时间");
		// 找到dialog的布局文件
		mInflater = inflater;
		View view = inflater.inflate(R.layout.chose_time, null);

		String[] months_big = { "1", "3", "5", "7", "8", "10", "12" };
		String[] months_little = { "4", "6", "9", "11" };

		final List<String> list_big = Arrays.asList(months_big);
		final List<String> list_little = Arrays.asList(months_little);

		current_year = (WheelView) view.findViewById(R.id.chose_year);
		current_year.setAdapter(new NumbericWheelAdapter(2016, 2036));
		current_year.setCyclic(true);
		current_year.setLabel("年");// 添加文字
		current_year.setCurrentItem(year - 2016);

		current_month = (WheelView) view.findViewById(R.id.chose_month);
		current_month.setAdapter(new NumbericWheelAdapter(1, 12));
		current_month.setCyclic(true);
		current_month.setLabel("月");// 添加文字
		current_month.setCurrentItem(month);

		current_hour = (WheelView) view.findViewById(R.id.chose_hour);
		current_hour.setAdapter(new NumbericWheelAdapter(0, 23));
		current_hour.setCyclic(true);
		current_hour.setLabel("时");// 添加文字

		current_day = (WheelView) view.findViewById(R.id.chose_day);
		current_day.setCyclic(true);
		current_day.setLabel("日");
		// 判断大小月及是否闰年,用来确定"日"的数据
		if (list_big.contains(String.valueOf(month + 1))) {
			current_day.setAdapter(new NumbericWheelAdapter(1, 31));
		} else if (list_little.contains(String.valueOf(month + 1))) {
			current_day.setAdapter(new NumbericWheelAdapter(1, 30));
		} else {
			// 闰年
			if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
				current_day.setAdapter(new NumbericWheelAdapter(1, 29));
			else
				current_day.setAdapter(new NumbericWheelAdapter(1, 28));
		}
		current_day.setCurrentItem(day - 1);

		current_minute = (WheelView) view.findViewById(R.id.chose_minute);
		current_minute.setAdapter(new NumbericWheelAdapter(0, 59));
		current_minute.setCyclic(true);
		current_minute.setLabel("分");

		current_hour.setCurrentItem(hour);
		current_minute.setCurrentItem(minute);

		/*
		 * addChangingListener(hours, "H"); addChangingListener(minutes, "M");
		 */

		// 添加"年"监听
		OnWheelChangedListener wheelListener_year = new OnWheelChangedListener() {
			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				int year_num = newValue + 2016;
				// 判断大小月及是否闰年,用来确定"日"的数据
				if (list_big.contains(String.valueOf(current_month
						.getCurrentItem() + 1))) {
					current_day.setAdapter(new NumbericWheelAdapter(1, 31));
				} else if (list_little.contains(String.valueOf(current_month
						.getCurrentItem() + 1))) {
					current_day.setAdapter(new NumbericWheelAdapter(1, 30));
				} else {
					if ((year_num % 4 == 0 && year_num % 100 != 0)
							|| year_num % 400 == 0)
						current_day.setAdapter(new NumbericWheelAdapter(1, 29));
					else
						current_day.setAdapter(new NumbericWheelAdapter(1, 28));
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
					current_day.setAdapter(new NumbericWheelAdapter(1, 31));
				} else if (list_little.contains(String.valueOf(month_num))) {
					current_day.setAdapter(new NumbericWheelAdapter(1, 30));
				} else {
					if (((current_year.getCurrentItem() + 2016) % 4 == 0 && (current_year
							.getCurrentItem() + 2016) % 100 != 0)
							|| (current_year.getCurrentItem() + 2016) % 400 == 0)
						current_day.setAdapter(new NumbericWheelAdapter(1, 29));
					else
						current_day.setAdapter(new NumbericWheelAdapter(1, 28));
				}
			}
		};
		current_year.addChangingListener(wheelListener_year);
		current_month.addChangingListener(wheelListener_month);

		Button btn_sure = (Button) view.findViewById(R.id.chose_time_sure);
		Button btn_cancel = (Button) view.findViewById(R.id.chose_time_canle);
		// 确定
		btn_sure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String parten = "00";
				DecimalFormat decimal = new DecimalFormat(parten);

				String a = (current_year.getCurrentItem() + 2016) + "-"
						+ decimal.format((current_month.getCurrentItem() + 1))
						+ "-"
						+ decimal.format((current_day.getCurrentItem() + 1))
						+ " " + decimal.format(current_hour.getCurrentItem())
						+ ":" + decimal.format(current_minute.getCurrentItem());

				Calendar c = Calendar.getInstance();
				int nowYear = c.get(Calendar.YEAR);
				int nowMonth = c.get(Calendar.MONTH) + 1;
				int nowDay = c.get(Calendar.DAY_OF_MONTH);
				int nowHours = c.get(Calendar.HOUR_OF_DAY);
				int nowMinutes = c.get(Calendar.MINUTE);

				int aYear = (current_year.getCurrentItem() + 2016);
				int aMonth = (current_month.getCurrentItem() + 1);
				int aDay = (current_day.getCurrentItem() + 1);
				int aHours = (current_hour.getCurrentItem());
				int aMinutes = (current_minute.getCurrentItem());
				if (aYear > nowYear) {
					isBefore = 1;
				} else if (aYear == nowYear) {
					if (aMonth > nowMonth) {
						isBefore = 1;
					} else if (aMonth == nowMonth) {
						if (aDay > nowDay) {
							isBefore = 1;
						} else if (aDay == nowDay) {
							if (aHours > nowHours) {
								isBefore = 1;
							} else if (aHours == aHours) {
								if (aMinutes > nowMinutes) {
									isBefore = 1;
								}
							} else {
								isBefore = -1;
							}
						} else {
							isBefore = -1;
						}
					} else {
						isBefore = -1;
					}
				} else {
					isBefore = -1;
				}
				if (isBefore == 1) {
					time_start = (current_year.getCurrentItem() + 2016)
							+ "-"
							+ decimal.format((current_month.getCurrentItem() + 1))
							+ "-"
							+ decimal.format((current_day.getCurrentItem() + 1))
							+ " "
							+ decimal.format(current_hour.getCurrentItem())
							+ ":"
							+ decimal.format(current_minute.getCurrentItem());
					tv_time_start.setText(time_start);
					SharedUtils.setParam(getActivity(), "time_start",
							time_start);
					// TODO Auto-generated method stub
					// 如果是个数,则显示为"02"的样式
					// 设置日期的显示
					// tv_time.setText((wv_year.getCurrentItem() + START_YEAR) +
					// "-"
					// + decimal.format((wv_month.getCurrentItem() + 1)) + "-"
					// + decimal.format((wv_day.getCurrentItem() + 1)) + " "
					// + decimal.format(wv_hours.getCurrentItem()) + ":"
					// + decimal.format(wv_minute.getCurrentItem()));
					dialog.dismiss();
				} else {
					new AlertDialog.Builder(getActivity())
							.setTitle("系统提示")
							// 设置对话框标题

							.setMessage("设定计划的开始时间不可小于当前时间 请重新设定！")
							// 设置显示的内容

							.setPositiveButton("确定",
									new DialogInterface.OnClickListener() {// 添加确定按钮

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {// 确定按钮的响应事件

											// TODO Auto-generated
											// method stub
											dialog.dismiss();

										}
									}).show();// 在按键响应事件中显示此对话框

				}
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

	public static int[] randomCommon(int min, int max, int n) {
		// 不合法参数
		if (n > (max - min + 1) || max < min) {
			return null;
		}
		// 生成一个n位的随机数组
		int[] result = new int[n];
		int count = 0;
		while (count < n) {
			// 随机生成一个数
			int num = (int) (Math.random() * (max - min)) + min;
			boolean flag = true; // 是否存在相同标志
			// 如果随生成的数字和数组中的数字有相同 设置flag=false
			for (int i = 0; i < result.length; i++) {
				if (num == result[i]) {
					flag = false;
					break;
				}
			}
			// 如果随机生成的数字和数组中的数字没有相同就放入数组并count+1
			if (flag) {
				result[count] = num;
				count++;
			}
		}
		return result;
	}
}