package ps.emperor.easy_water.activity;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
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

import ps.emperor.easy_water.R;
import ps.emperor.easy_water.Interface.OnWheelChangedListener;
import ps.emperor.easy_water.BaseActivity;
import ps.emperor.easy_water.adapter.NumbericWheelAdapter;
import ps.emperor.easy_water.entity.IrriGroupStateBean;
import ps.emperor.easy_water.fragment.ApplyIrrigateProjectFragment;
import ps.emperor.easy_water.greendao.DBHelper;
import ps.emperor.easy_water.greendao.IrrigationProject;
import ps.emperor.easy_water.utils.NetStatusUtil;
import ps.emperor.easy_water.utils.SharedUtils;
import ps.emperor.easy_water.utils.URL;
import ps.emperor.easy_water.view.WheelView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

/**
 * 窗口化时间转轮
 * 
 * @author 毛国江
 * @version 2016-5-31 上午8:12
 */

public class TimeAvtivityDialog extends BaseActivity implements OnClickListener {

	private Button time_sure, time_canle;
	private WheelView year;
	private WheelView month;
	private WheelView day;
	private WheelView hour;
	private WheelView minute;
	private WheelView hours;
	private WheelView minutes;
	private int isBefore;
	private String irriDuration, startTime, units, compareTime,
			compareRound,groupID,planRound;
	private int first_setTime = 0;
	private long end, nowEnd, minuteOfDay, start;
	private int nowItem, nowPages, isOne, isSkip,isSkips, position;
	private DBHelper dbHelper;
	private List<IrrigationProject> listentity;
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.time);

		dbHelper = DBHelper.getInstance(this); // 得到DBHelper对象

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		nowItem = bundle.getInt("nowItem");
		nowPages = bundle.getInt("nowPage");
		units = bundle.getString("units");
		compareTime = bundle.getString("compareTime");
		position = bundle.getInt("position");
		groupID = bundle.getString("groupID");
		planRound = bundle.getString("planRound");

		Calendar c = Calendar.getInstance();
		int currentYear = c.get(Calendar.YEAR);
		int currentMonth = c.get(Calendar.MONTH);
		int currentDay = c.get(Calendar.DATE);
		int currentHour = c.get(Calendar.HOUR_OF_DAY);
		int currentMinute = c.get(Calendar.MINUTE)+1;
		int currentHours = 0;
		int currentMinutes = 0;

		String[] months_big = { "1", "3", "5", "7", "8", "10", "12" };
		String[] months_little = { "4", "6", "9", "11" };

		final List<String> list_big = Arrays.asList(months_big);
		final List<String> list_little = Arrays.asList(months_little);

		time_sure = (Button) findViewById(R.id.time_sure);
		time_sure.setOnClickListener(this);
		time_canle = (Button) findViewById(R.id.time_canle);
		time_canle.setOnClickListener(this);

		year = (WheelView) findViewById(R.id.year);
		year.setAdapter(new NumbericWheelAdapter(2016, 2036));
		year.setCyclic(true);
		year.setLabel("年");// 添加文字
		year.setCurrentItem(currentYear - 2016);

		month = (WheelView) findViewById(R.id.month);
		month.setAdapter(new NumbericWheelAdapter(1, 12));
		month.setCyclic(true);
		month.setLabel("月");// 添加文字
		month.setCurrentItem(currentMonth);

		hour = (WheelView) findViewById(R.id.hour);
		hour.setAdapter(new NumbericWheelAdapter(0, 23));
		hour.setCyclic(true);
		hour.setLabel("时");// 添加文字

		day = (WheelView) findViewById(R.id.day);
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

		minute = (WheelView) findViewById(R.id.minute);
		minute.setAdapter(new NumbericWheelAdapter(0, 59));
		minute.setCyclic(true);
		minute.setLabel("分");

		hours = (WheelView) findViewById(R.id.hours);
		hours.setAdapter(new NumbericWheelAdapter(0, 23));
		hours.setCyclic(true);
		hours.setLabel("时");

		minutes = (WheelView) findViewById(R.id.minutes);
		minutes.setAdapter(new NumbericWheelAdapter(0, 59));
		minutes.setCyclic(true);
		minutes.setLabel("分");

		hour.setCurrentItem(currentHour);
		hours.setCurrentItem(currentHours);
		minute.setCurrentItem(currentMinute);
		minutes.setCurrentItem(currentMinutes);

		/*
		 * addChangingListener(hours, "H"); addChangingListener(minutes, "M");
		 */

		// 添加"年"监听
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
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.time_sure:
			String parten = "00";
			DecimalFormat decimal = new DecimalFormat(parten);

			startTime = (year.getCurrentItem() + 2016) + "-"
					+ decimal.format((month.getCurrentItem() + 1)) + "-"
					+ decimal.format((day.getCurrentItem() + 1)) + " "
					+ decimal.format(hour.getCurrentItem()) + ":"
					+ decimal.format(minute.getCurrentItem());
			
			irriDuration = (decimal.format(hours.getCurrentItem()) + ":"
					+ decimal.format(minute.getCurrentItem()));
			Calendar c = Calendar.getInstance();
			int nowYear = c.get(Calendar.YEAR);
			int nowMonth = c.get(Calendar.MONTH) + 1;
			int nowDay = c.get(Calendar.DAY_OF_MONTH);
			int nowHours = c.get(Calendar.HOUR_OF_DAY);
			int nowMinutes = c.get(Calendar.MINUTE);

			int aYear = (year.getCurrentItem() + 2016);
			int aMonth = (month.getCurrentItem() + 1);
			int aDay = (day.getCurrentItem() + 1);
			int aHours = (hour.getCurrentItem());
			int aMinutes = (minute.getCurrentItem());
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
						} else if (aHours == nowHours) {
							if (aMinutes > nowMinutes) {
								isBefore = 1;
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
			} else {
				isBefore = -1;
			}
			if(hours.getCurrentItem()==0&&minutes.getCurrentItem()==0){
				Toast.makeText(this, "请设置正确的持续时间！", Toast.LENGTH_SHORT).show();
			}else if (isBefore == 1) {
				if(NetStatusUtil.isNetValid(this)){
					String str1 = (String) SharedUtils.getParam(this,
							"FirstDerviceID", "");
					RequestParams param2 = new RequestParams(URL.updateIrriGroupPlan); // 网址(请替换成实际的网址)
					// 参数(请替换成实际的参数与值)
					progressDialog = ProgressDialog.show(this, "Loading...",
							"Please wait...", true, false);
					JSONObject js_request = new JSONObject();
					try {
						param2.setAsJsonContent(true);
						js_request.put("firstDerviceID", str1);
						js_request.put("groupID",groupID);
						js_request.put("planRound", planRound);
						js_request.put("startTime", startTime);
						js_request.put("irriDuration", irriDuration);
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
									IrriGroupStateBean fromJson = gson
											.fromJson(arg0,
													IrriGroupStateBean.class);
									if("0".equals(fromJson.getCode())){
										Toast.makeText(getApplicationContext(), "修改失败！服务器异常！", Toast.LENGTH_SHORT).show();
									}else if("1".equals(fromJson.getCode())){
										Toast.makeText(getApplicationContext(), "修改成功！", Toast.LENGTH_SHORT).show();
										isSkip = 2;
										SharedUtils.setParam(getApplication(), "isSkip", isSkip);
										isSkips = 2;
										SharedUtils.setParam(getApplication(), "isSkips", isSkips);
										finish();
									}else if("2".equals(fromJson.getCode())){
										Toast.makeText(getApplicationContext(), "修改失败！该时间段内已存在计划，请选择其他时间段！", Toast.LENGTH_SHORT).show();
									}
								}
							});
				}else{
					Toast.makeText(getApplicationContext(), "当前网络不可用！请检查您的网络状态！", Toast.LENGTH_LONG).show();
				}
				
//				listentity = dbHelper.loadLastMsgBySessionids(units);
//				Calendar cal = Calendar.getInstance();// 当前日期
//				// 从数据库中查出点击的该项对应的计划结束时间
////				for (int i = 0; i < listentity.size(); i++) {
//					time_end = listentity.get(position).getProjectend();
//					time_start = listentity.get(position).getProjectstart();
//
//					java.util.Date date = new java.util.Date();
//					SimpleDateFormat format = new SimpleDateFormat(
//							"yyyy-MM-dd HH:mm");
//					try {
//						date = format.parse(time_end);
//					} catch (ParseException e) {
//						e.printStackTrace();
//					}
//
//					java.util.Date date3 = new java.util.Date();
//					SimpleDateFormat format3 = new SimpleDateFormat(
//							"yyyy-MM-dd HH:mm");
//					try {
//						date3 = format.parse(time_start);
//					} catch (ParseException e) {
//						e.printStackTrace();
//					}
//
//					java.util.Date date1 = new java.util.Date();
//					SimpleDateFormat format1 = new SimpleDateFormat(
//							"yyyy-MM-dd HH:mm");
//					try {
//						date1 = format1.parse(aYear + "-" + aMonth + "-" + aDay
//								+ " " + aHours + ":" + aMinutes);
//					} catch (ParseException e) {
//						e.printStackTrace();
//					}
//					date1.setHours(date1.getHours()
//							+ Integer.valueOf(hours.getCurrentItem()));
//					date1.setMinutes(date1.getMinutes()
//							+ Integer.valueOf(minutes.getCurrentItem()));
//					time_ends = format1.format(date1);
//
//					if (aYear > date3.getYear() + 1900
//							|| aYear < date3.getYear() + 1900) {
//						isOne = 1;
//					} else if (aYear == date3.getYear() + 1900) {
//						minuteOfDay = aMonth * 30 * 24 * 60 + aDay * 24 * 60
//								+ aHours * 60 + aMinutes;// 现在选中的开始时间
//						nowEnd = Integer.valueOf((date1.getMonth() + 1) * 30
//								* 24 * 60 + date1.getDate() * 24 * 60 // 现在设定的结束时间
//								+ date1.getHours() * 60 + date1.getMinutes());
//						start = Integer.valueOf((date3.getMonth() + 1) * 30
//								* 24 * 60 + date3.getDate() * 24 * 60// 起始时间（从数据库中调出）
//								+ date3.getHours() * 60 + date3.getMinutes());
//						end = Integer.valueOf((date.getMonth() + 1) * 30 * 24
//								* 60 + date.getDate() * 24 * 60
//								+ date.getHours() * 60 + date.getMinutes());// 结束时间（从数据库中调出）
//
//						if (minuteOfDay >= start&& nowEnd <= end ) {
//							isOne = 1;
//						} else {
//							isOne = 2;
//						}
////					}
//				}
//				if (isOne == 1) {
//					String nowStar = decimal
//							.format(year.getCurrentItem() + 2016)
//							+ "-"
//							+ decimal.format(month.getCurrentItem() + 1)
//							+ "-"
//							+ decimal.format(day.getCurrentItem() + 1)
//							+ " "
//							+ decimal.format(hour.getCurrentItem())
//							+ ":"
//							+ decimal.format(minute.getCurrentItem());
//					// dbHelper.updateProject(nowPages + "", nowItem,
//					// nowStar + "", time_ends + "");
//					// for (int i = 0; i < listentity.size(); i++) {
//					// if(compareTime.equals(listentity.get(i).getProjectstart())){
//					// compareRound = listentity.get(i).getRound();
//					// break;
//					// }
//					// }
//					compareRound = listentity.get(position).getRound();
//					dbHelper.updateProjects(units, compareRound, nowItem,
//							nowStar + "", time_ends + "");
					
//					finish();
//				} else {
//					Toast.makeText(getApplication(), "在范围内", Toast.LENGTH_SHORT)
//							.show();
//				}

			} else {
				new AlertDialog.Builder(TimeAvtivityDialog.this)
						.setTitle("系统提示")
						// 设置对话框标题

						.setMessage("设定计划的开始时间不可小于当前时间 请重新设定！")
						// 设置显示的内容

						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {// 添加确定按钮

									@Override
									public void onClick(DialogInterface dialog,
											int which) {// 确定按钮的响应事件

										// TODO Auto-generated
										// method stub
										dialog.dismiss();

									}
								}).show();// 在按键响应事件中显示此对话框

			}
			System.out.println(startTime);
			break;
		case R.id.time_canle:
			isSkip = 2;
			SharedUtils.setParam(getApplication(), "isSkip", isSkip);
			isSkips = 0;
			SharedUtils.setParam(getApplication(), "isSkips", isSkips);
			finish();
			break;
		default:
			break;
		}
	}

}
