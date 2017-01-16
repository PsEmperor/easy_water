package ps.emperor.easy_water.fragment;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import ps.emperor.easy_water.activity.TimeAvtivityDialog;
import ps.emperor.easy_water.adapter.ApplyWaterGateLinkageAdapter;
import ps.emperor.easy_water.adapter.NumbericWheelAdapter;
import ps.emperor.easy_water.entity.ApplyWaterGateLinkageBean;
import ps.emperor.easy_water.entity.UserReleDisInfoBeanAdd;
import ps.emperor.easy_water.entity.FindDisWaterInfoOneBean.SluiceGateInfoBean;
import ps.emperor.easy_water.entity.UserReleDisInfoBeanAdd.infoList;
import ps.emperor.easy_water.utils.SharedUtils;
import ps.emperor.easy_water.utils.URL;
import ps.emperor.easy_water.view.MainActionBar;
import ps.emperor.easy_water.view.MainActionBars;
import ps.emperor.easy_water.view.WheelView;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 多孔闸门联动
 * @author 毛国江
 * @version 2016-9-9 下午16:50
 */

public class AppayWaterGateLinkageFragment extends Fragment implements
		OnClickListener {
	private LayoutInflater mInflater;
	private MainActionBars actionBar;
	private ApplyWaterGateLinkageAdapter adapter;
	private List<ApplyWaterGateLinkageBean> beans;
	private RelativeLayout layout_time_operation,layout_apply_water_time_operation;
	private ListView listView;
	private Dialog dialog;
	private WheelView year;
	private WheelView month;
	private WheelView day;
	private WheelView wv_hour;
	private WheelView wv_minute;
	private WheelView hour;
	private WheelView minute;
	private TextView tv_apply_water_time_operation,tv_apply_water_time_operations;
	private int isBefore;
	private String timestart,timeend;
	private List<SluiceGateInfoBean> list;
	private List<String> list1;
	
	@SuppressLint("CutPasteId")
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflater = inflater;
		View view = inflater.inflate(R.layout.fragment_haplopore_gate,
				container, false);

		actionBar = (MainActionBars) view
				.findViewById(R.id.actionbar_apply_water_haplopore_gate);
		actionBar.setLeftIcon(R.drawable.btn_back_selector);
		actionBar.setRightText("保存");
		actionBar.setTitle("预约闸门调整");
		actionBar.setActionBarOnClickListener(this);

		 layout_time_operation = (RelativeLayout)
		 view.findViewById(R.id.layout_apply_water_time_operation);
		 layout_time_operation.setOnClickListener(this);
		 listView = (ListView)
		 view.findViewById(R.id.list_apply_water_haplopore_gate);

		 tv_apply_water_time_operation = (TextView) view.findViewById(R.id.tv_apply_water_time_starts);
		 tv_apply_water_time_operations = (TextView) view.findViewById(R.id.tv_apply_water_time_ends);
		 
		 layout_apply_water_time_operation = (RelativeLayout) view.findViewById(R.id.layout_apply_water_time_operation);
		 layout_apply_water_time_operation.setOnClickListener(this);
		 list = (List<SluiceGateInfoBean>) getArguments().getSerializable("beans");
		 beans = new ArrayList<ApplyWaterGateLinkageBean>();
		 ApplyWaterGateLinkageBean bean;
		 for (int i = 0; i < list.size(); i++) {
			bean = new ApplyWaterGateLinkageBean();
			bean.setPercentage((int)(Float.valueOf(list.get(i).getOpenProportion())*100)+"");
			bean.setHaplopore(list.get(i).getPoreID()+"");
			bean.setShow((Float.valueOf(list.get(i).getOpenProportion())*100)+"");
			beans.add(bean);
			System.out.println(beans.get(i).getPercentage());
		}
		 adapter = new ApplyWaterGateLinkageAdapter(getActivity());
		 adapter.addData(beans, false);
		 listView.setAdapter(adapter);
		return view;
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
			RequestParams param2 = new RequestParams(URL.openProportionAll);  // 网址(请替换成实际的网址) 
//			 params.addQueryStringParameter("key", "value"); // 参数(请替换成实际的参数与值)   
			JSONObject js_request = new JSONObject();
			try {
				param2.setAsJsonContent(true);
				js_request.put("disEquID","配水设备5");
				list1 = new ArrayList<String>();
				adapter.notifyDataSetChanged();
				for (int i = 0; i < beans.size(); i++) {
					list1.add(beans.get(i).getPercentage());
				}
				js_request.put("openProportion",list1);
				js_request.put("openPoreTime",tv_apply_water_time_operation.getText().toString());
				js_request.put("closePoreTime",tv_apply_water_time_operations.getText().toString());
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
		                } else { // 其他错误    
		                    // ...  
		                	Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT);
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
//		                  UserReleDisInfoBeanAdd fromJson = gson.fromJson(arg0, UserReleDisInfoBeanAdd.class);
////		                  authorizedBeen = new AuthorizedBeen();
////		                  authorizedBeen = gson.fromJson(arg0, AuthorizedBeen.class);
//		                  List<infoList> beens = fromJson.getAuthNameList();
//		                  for (infoList authNameListBean : beens) {
//		                	authNameListBean.getAuthName();
//						}
//		                  adapter.addData(beans, true);
//		                  listView.setAdapter(adapter);
		            }  
		        }); 
			break;
		case R.id.layout_apply_water_time_operation:
			showDateTimePicker(mInflater);
			break;
		}

	}
	/**
	 * @Description: TODO 弹出日期时间选择器
	 */
	private void showDateTimePicker(LayoutInflater inflater) {
		mInflater = inflater;
		View view = inflater.inflate(R.layout.time_gate_control, null);

		dialog = new Dialog(getActivity());
		dialog.setTitle("请选择开始时间");
		Calendar c = Calendar.getInstance();
		int currentYear = c.get(Calendar.YEAR);
		int currentMonth = c.get(Calendar.MONTH);
		int currentDay = c.get(Calendar.DATE);
		int currentHour = c.get(Calendar.HOUR_OF_DAY);
		int currentMinute = c.get(Calendar.MINUTE);

		String[] months_big = { "1", "3", "5", "7", "8", "10", "12" };
		String[] months_little = { "4", "6", "9", "11" };

		final List<String> list_big = Arrays.asList(months_big);
		final List<String> list_little = Arrays.asList(months_little);

		year = (WheelView) view.findViewById(R.id.year_gate);
		year.setAdapter(new NumbericWheelAdapter(2016, 2036));
		year.setCyclic(true);
		year.setLabel("年");// 添加文字
		year.setCurrentItem(currentYear - 2016);

		month = (WheelView) view.findViewById(R.id.month_gate);
		month.setAdapter(new NumbericWheelAdapter(1, 12));
		month.setCyclic(true);
		month.setLabel("月");// 添加文字
		month.setCurrentItem(currentMonth);

		wv_hour = (WheelView) view.findViewById(R.id.hours_gate);
		wv_hour.setAdapter(new NumbericWheelAdapter(1, 12));
		wv_hour.setCyclic(true);
		wv_hour.setLabel("时");// 添加文字

		wv_minute = (WheelView) view.findViewById(R.id.minutes_gate);
		wv_minute.setAdapter(new NumbericWheelAdapter(0, 23));
		wv_minute.setCyclic(true);
		wv_minute.setLabel("分");
		
		hour = (WheelView) view.findViewById(R.id.hour_gate);
		hour.setAdapter(new NumbericWheelAdapter(0, 23));
		hour.setCyclic(true);
		hour.setLabel("时");// 添加文字

		minute = (WheelView) view.findViewById(R.id.minute_gate);
		minute.setAdapter(new NumbericWheelAdapter(0, 59));
		minute.setCyclic(true);
		minute.setLabel("分");

		wv_hour.setCurrentItem(currentHour);
		wv_minute.setCurrentItem(currentMinute);

		day = (WheelView) view.findViewById(R.id.day_gate);
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

		Button btn_sure = (Button) view.findViewById(R.id.time_sure_gate);
		Button btn_cancel = (Button) view.findViewById(R.id.time_canle_gate);
		btn_sure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String parten = "00";
				DecimalFormat decimal = new DecimalFormat(parten);
				Calendar c = Calendar.getInstance();
				int nowYear = c.get(Calendar.YEAR);
				int nowMonth = c.get(Calendar.MONTH) + 1;
				int nowDay = c.get(Calendar.DAY_OF_MONTH);
				int nowHours = c.get(Calendar.HOUR_OF_DAY);
				int nowMinutes = c.get(Calendar.MINUTE);

				int aYear = (year.getCurrentItem() + 2016);
				int aMonth = (month.getCurrentItem() + 1);
				int aDay = (day.getCurrentItem() + 1);
				int aHours = (wv_hour.getCurrentItem());
				int aMinutes = (wv_minute.getCurrentItem());
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
				if (isBefore == 1) {
					timestart = decimal.format(year.getCurrentItem() + 2016) + "-"
							+ decimal.format(month.getCurrentItem() + 1) + "-"
							+ decimal.format(day.getCurrentItem() + 1)+" "+ decimal.format(wv_hour.getCurrentItem())
							+":"+decimal.format(wv_minute.getCurrentItem());
					tv_apply_water_time_operation.setText(timestart);
					java.util.Date date = new java.util.Date();
					SimpleDateFormat format = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm");
					try {
						date = format.parse(timestart);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					date.setHours(date.getHours()
							+ Integer.valueOf(hour.getCurrentItem()));
					date.setMinutes(date.getMinutes()
							+ Integer.valueOf(minute.getCurrentItem()));
					timeend = format.format(date);
					tv_apply_water_time_operations.setText(timeend);
					dialog.dismiss();
				}else{
						new AlertDialog.Builder(getActivity())
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

}
