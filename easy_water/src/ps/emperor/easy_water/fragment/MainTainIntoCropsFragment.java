package ps.emperor.easy_water.fragment;

import java.io.UnsupportedEncodingException;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.ex.HttpException;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;

import com.google.gson.Gson;

import ps.emperor.easy_water.R;
import ps.emperor.easy_water.adapter.MainTainIntoCropsAdapter;
import ps.emperor.easy_water.adapter.MineUserProvinceAdapter;
import ps.emperor.easy_water.adapter.NumbericWheelAdapter;
import ps.emperor.easy_water.adapter.NumericWheelAdapter;
import ps.emperor.easy_water.entity.CropBeen;
import ps.emperor.easy_water.entity.CropBeen.infoList;
import ps.emperor.easy_water.utils.CheckUtil;
import ps.emperor.easy_water.utils.NetStatusUtil;
import ps.emperor.easy_water.utils.SharedUtils;
import ps.emperor.easy_water.utils.URL;
import ps.emperor.easy_water.view.MainActionBar;
import ps.emperor.easy_water.view.MainActionBars;
import ps.emperor.easy_water.view.WheelView;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 录入种植作物信息
 * 
 * @author 毛国江
 * @version 2016-6-16 下午14:33
 */

public class MainTainIntoCropsFragment extends Fragment implements
		OnClickListener,OnItemClickListener {
	private LayoutInflater mInflater;
	private MainActionBars actionBar;
	private ArrayList<String> integers = new ArrayList<String>();
	private ArrayList<String> list = new ArrayList<String>();
	private MainTainIntoCropsAdapter adapter;
	private GridView gridView;
	private EditText control_crop, control_time;// 播种时间
	private Dialog dialog;
	private int area,isTrue = 0;
	private String year, month, day;
	private ProgressDialog progressDialog;
	private PopupWindow popupWindow;
	private MainTainIntoCropsAdapter adapter1;
	private ListView listView;
	CropBeen authorizedBeen;
	private String classId;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflater = inflater;
		View view = inflater.inflate(
				R.layout.fragment_maintain_into_crops_info, container, false);
		actionBar = (MainActionBars) view
				.findViewById(R.id.actionbar_maintain_into_crops);
		actionBar.setLeftIcon(R.drawable.btn_back_selector);
		actionBar.setRightText("保存");
		actionBar.setTitle("录入种植信息");
		actionBar.setActionBarOnClickListener(this);
		
		adapter1 = new MainTainIntoCropsAdapter(getActivity());
		
		list = getArguments().getStringArrayList("list");
		integers = getArguments().getStringArrayList("info");
		area = getArguments().getInt("area");
		adapter = new MainTainIntoCropsAdapter(getActivity());
		gridView = (GridView) view.findViewById(R.id.grid__maintain_into_crops);
		control_crop = (EditText) view
				.findViewById(R.id.edit__apply_irrigatr_control_crop);
		control_time = (EditText) view
				.findViewById(R.id.text__apply_irrigatr_control_time);
		control_crop.setOnClickListener(this);
		control_time.setOnClickListener(this);
		init();
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_list_item_1, integers);

		/* 设置ListView的Adapter */
		gridView.setAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, integers));
		return view;
	}

	private void init() {
		year = (String) SharedUtils.getParam(getActivity(), "year_info_crops",
				"0000");
		month = (String) SharedUtils.getParam(getActivity(),
				"month_info_crops", "00");
		day = (String) SharedUtils.getParam(getActivity(), "day_info_crops",
				"00");
	}

	@Override
	public void onClick(View v) {
		FragmentManager fgManager = getFragmentManager();
		FragmentTransaction transaction = fgManager.beginTransaction();
		switch (v.getId()) {
		case R.id.acitionbar_left:
			((InputMethodManager)control_crop.getContext().getSystemService(getActivity().INPUT_METHOD_SERVICE)). 
		     hideSoftInputFromWindow(control_crop.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS); 
			MainTainIrrigationfarmarcropInfoFragment fragment = new MainTainIrrigationfarmarcropInfoFragment();
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			transaction.setCustomAnimations(
					R.anim.slide_fragment_horizontal_right_in,
					R.anim.slide_fragment_horizontal_left_out);
			transaction.replace(R.id.fragment_maintain_present_irrigate,
					fragment, "main");
			transaction.commit();
			break;
		case R.id.edit__apply_irrigatr_control_crop:
			control_crop.setInputType(InputType.TYPE_DATETIME_VARIATION_NORMAL);
			control_crop.setFocusable(false);
			control_crop.setFocusableInTouchMode(false);
			if (!NetStatusUtil.isNetValid(getActivity())) {
				Toast.makeText(getActivity(), "当前网络不可用！请检查您的网络状态！", Toast.LENGTH_SHORT)
				.show();
			} else {
			RequestParams param1 = new RequestParams(URL.acquireCropInfoToAll); //  网址(请替换成实际的网址) 
			progressDialog = ProgressDialog.show(getActivity(), "Loading...",
					"Please wait...", true, false);
//			 params.addQueryStringParameter("key", "value");// 参数(请替换成实际的参数与值)   
			JSONObject js_request = new JSONObject();
			try {
				param1.setAsJsonContent(true);
//				
				} catch (Exception e) {
				// TODO Auto-generated catch block
//				param1.setBodyContent("Content-Type: application/json"+js_request.toString());
				e.printStackTrace();
				param1.setAsJsonContent(true);
			}//根据实际需求添加相应键值对
			
		        x.http().request(HttpMethod.GET ,param1, new CommonCallback<String>() {  
		            @Override  
		            public void onCancelled(CancelledException arg0) {  
		                  
		            }  
		  
		            // 注意:如果是自己onSuccess回调方法里写了一些导致程序崩溃的代码，也会回调道该方法，因此可以用以下方法区分是网络错误还是其他错误  
		            // 还有一点，网络超时也会也报成其他错误，还需具体打印出错误内容比较容易跟踪查看    
		            @Override  
		            public void onError(Throwable ex, boolean isOnCallback) {  
		                  
		                Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();  
		                if (ex instanceof HttpException) { // 网络错误  
		                    HttpException httpEx = (HttpException) ex;  
		                    int responseCode = httpEx.getCode();  
		                    String responseMsg = httpEx.getMessage();  
		                    String errorResult = httpEx.getResult();  
		                    Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT);
		                    // ...  
		                    progressDialog.dismiss();
		                } else {  // 其他错误  
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
		                  CropBeen fromJson = gson.fromJson(arg0, CropBeen.class);
		                  authorizedBeen = new CropBeen();
		                  authorizedBeen = gson.fromJson(arg0, CropBeen.class);
		                  List<infoList> beens = fromJson.getAuthNameList();
		                 if(!CheckUtil.IsEmpty(beens)){
		                	 adapter1.addData(beens, true);
		                 }
		                  progressDialog.dismiss();
		            }  
		        }); 
			View view1 = mInflater.inflate(
					R.layout.layout_mine_user_units_popu, null);
			popupWindow = new PopupWindow(view1,
					ViewGroup.LayoutParams.WRAP_CONTENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			popupWindow.setFocusable(true);
			popupWindow.setBackgroundDrawable(new ColorDrawable());
			listView = (ListView) view1.findViewById(R.id.list_mine_user_units);
			listView.setAdapter(adapter1);
			listView.setOnItemClickListener(this);
			popupWindow.showAsDropDown(control_crop);
			}
			break;
		case R.id.text__apply_irrigatr_control_time:
			showDateTimePicker(mInflater);
			break;
		case R.id.acitionbar_right:
			if (!checkNameChese(control_crop.getText().toString()) == true) {
				Toast.makeText(getActivity(), "请输入正确的作物名称！", Toast.LENGTH_SHORT)
						.show();
			} else if (!NetStatusUtil.isNetValid(getActivity())) {
				Toast.makeText(getActivity(), "当前网络不可用！请检查您的网络状态！", Toast.LENGTH_SHORT)
						.show();
			} else {
				RequestParams param2 = new RequestParams(URL.addChanCropInfo); // 网址(请替换成实际的网址)
				progressDialog = ProgressDialog.show(getActivity(),
						"Loading...", "Please wait...", true, false);
				JSONObject js_request = new JSONObject();
				try {
					param2.setAsJsonContent(true);
					String str1 = (String) SharedUtils.getParam(getActivity(),
							"FirstDerviceID", "");
					;
					try {
						str1 = java.net.URLEncoder.encode(str1, "UTF-8");
					} catch (UnsupportedEncodingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					js_request.put("firstDerviceID", str1);
					js_request.put("valueControlChanID", list);
					js_request.put("cropName", control_crop.getText()
							.toString().trim());
					js_request.put("plantTime", control_time.getText()
							.toString().trim());
					js_request.put("classID", classId);
					js_request.put("area", area);
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
								Toast.makeText(getActivity(), "录入成功！",
										Toast.LENGTH_SHORT).show();
								FragmentManager fgManager = getFragmentManager();
								FragmentTransaction transaction = fgManager
										.beginTransaction();
								MainTainIrrigationfarmarcropInfoFragment fragment = new MainTainIrrigationfarmarcropInfoFragment();
								// transaction.setCustomAnimations(R.anim.right_in,
								// R.anim.right_out);
								transaction
										.setCustomAnimations(
												R.anim.slide_fragment_horizontal_right_in,
												R.anim.slide_fragment_horizontal_left_out);
								transaction
										.replace(
												R.id.fragment_maintain_present_irrigate,
												fragment, "main");
								transaction.commit();
								progressDialog.dismiss();
							}
						});
			}
			break;
		default:
			break;
		}
	}

	/**
	 * @Description: TODO 弹出日期时间选择器
	 */
	private void showDateTimePicker(LayoutInflater inflater) {
		Calendar calendar = Calendar.getInstance();
		int currentYear = calendar.get(Calendar.YEAR);
		int currentMonth = calendar.get(Calendar.MONTH);
		int currentDay = calendar.get(Calendar.DATE);

		dialog = new Dialog(getActivity());
		dialog.setTitle("请选择");
		// 找到dialog的布局文件
		mInflater = inflater;
		View view = inflater.inflate(R.layout.times, null);

		String[] months_big = { "1", "3", "5", "7", "8", "10", "12" };
		String[] months_little = { "4", "6", "9", "11" };

		final List<String> list_big = Arrays.asList(months_big);
		final List<String> list_little = Arrays.asList(months_little);

		// 年
		final WheelView year = (WheelView) view.findViewById(R.id.hour_times);
		year.setAdapter(new NumericWheelAdapter(2016, 2036));
		year.setCyclic(true);
		year.setLabel("年");// 添加文字
		year.setCurrentItem(currentYear - 2016);

		// 月
		final WheelView month = (WheelView) view
				.findViewById(R.id.minute_times);
		month.setAdapter(new NumericWheelAdapter(1, 12));
		month.setCyclic(true);
		month.setLabel("月");// 添加文字
		month.setCurrentItem(currentMonth);

		// 日
		final WheelView day = (WheelView) view.findViewById(R.id.second_times);
		day.setCyclic(true);
		day.setLabel("日");// 添加文字
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
				control_time.setText(decimal.format(year.getCurrentItem() + 2016)
						+ "-"
						+ decimal.format(month.getCurrentItem() + 1)
						+ "-" + decimal.format(day.getCurrentItem() + 1));
				SharedUtils.setParam(getActivity(), "year_info_crops",
						decimal.format(year.getCurrentItem() + 2016));
				SharedUtils.setParam(getActivity(), "month_info_crops",
						decimal.format(month.getCurrentItem() + 1));
				SharedUtils.setParam(getActivity(), "day_info_crops",
						decimal.format(day.getCurrentItem() + 1));
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
	 * 判定输入汉字
	 * 
	 * @param c
	 * @return
	 */
	public boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}

	/**
	 * 检测String是否全是中文
	 * 
	 * @param name
	 * @return
	 */
	public boolean checkNameChese(String name) {
		boolean res = true;
		char[] cTemp = name.toCharArray();
		for (int i = 0; i < name.length(); i++) {
			if (!isChinese(cTemp[i])) {
				res = false;
				break;
			}
		}
		return res;
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
			control_crop.setText(authorizedBeen.getAuthNameList().get(position).getCropName());
			if("99999".equals(authorizedBeen.getAuthNameList().get(position).getClassID())){
				control_crop.setText("");
				control_crop.setFocusable(true);
				control_crop.setFocusableInTouchMode(true);
				control_crop.requestFocus();
				Timer timer = new Timer();
		        timer.schedule(new TimerTask()
		        {
		            public void run()
		            {
		                InputMethodManager inputManager =
		                    (InputMethodManager)control_crop.getContext().getSystemService(getActivity().INPUT_METHOD_SERVICE);
		                inputManager.showSoftInput(control_crop, 0);
		            }
		            
		        },
		            100);
			}else{
				isTrue = 0;
			}
			classId = authorizedBeen.getAuthNameList().get(position).getClassID();
			popupWindow.dismiss();
	}
}
