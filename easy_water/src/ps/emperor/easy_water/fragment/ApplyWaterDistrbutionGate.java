package ps.emperor.easy_water.fragment;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import java.text.DecimalFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


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

import cn.jpush.a.a.be;
import cn.jpush.a.a.w;

import android.annotation.SuppressLint;
import android.app.AlertDialog.Builder;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnWindowFocusChangeListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.Interface.OnWheelChangedListener;
import ps.emperor.easy_water.activity.TimeAvtivityDialog;
import ps.emperor.easy_water.adapter.ApplyWaterDistrbutionGateAdapter;
import ps.emperor.easy_water.adapter.ApplyWaterGateLinkageAdapter;
import ps.emperor.easy_water.adapter.NumbericWheelAdapter;
import ps.emperor.easy_water.entity.ApplyWaterGateLinkageBean;
import ps.emperor.easy_water.entity.FindDisWaterInfoOneBean;
import ps.emperor.easy_water.entity.FindDisWaterInfoOneBean.DisWaterInfoBean;
import ps.emperor.easy_water.entity.FindDisWaterInfoOneBean.SluiceGateInfoBean;
import ps.emperor.easy_water.utils.CheckUtil;
import ps.emperor.easy_water.utils.DensityUtil;
import ps.emperor.easy_water.utils.ScreenUtils;
import ps.emperor.easy_water.utils.SharedUtils;
import ps.emperor.easy_water.utils.URL;
import ps.emperor.easy_water.view.HorizontalListView;
import ps.emperor.easy_water.view.HorizontalListView.OnScrollStateChangedListener;
import ps.emperor.easy_water.view.HorizontalListView.OnScrollStateChangedListener.ScrollState;
import ps.emperor.easy_water.view.HorizontalListView.OnScrollStopListner;
import ps.emperor.easy_water.view.MainActionBar;
import ps.emperor.easy_water.view.WheelView;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
/**
 * 闸门控制（应用）
 * 
 * @author 毛国江
 * @version 2016-5-24 上午8:34
 */

@SuppressLint("NewApi")
public class ApplyWaterDistrbutionGate extends Fragment implements
		OnClickListener ,OnItemClickListener{

	private LayoutInflater mInflater;
	private MainActionBar actionBar;
	private TextView tv_indicator,tv_superior,tv_time_operation_start,tv_time_operation_end;
	private Button btn_time_operation;
	private RelativeLayout tv_time_operation;
	private Dialog dialog;
	private WheelView year;
	private WheelView month;
	private WheelView day;
	private WheelView wv_hour;
	private WheelView wv_minute;
	private WheelView hour;
	private WheelView minute;
	private RelativeLayout layout,layout_relative_changes,layout_relative_changes_left,layout_relative_changes_right;
	private LinearLayout layout_linear_change,layout_linear_changes;
	private HorizontalListView list_apply_water_distrbution_gate_control;
	private ApplyWaterDistrbutionGateAdapter adapter;
	private RelativeLayout layout_relative_changes_one,layout_relative_changes_two,layout_relative_changes_three;
	private RelativeLayout layout_relative_change,layout_relative_change_left,layout_relative_change_right,layout_relative_changes_ones,
	layout_relative_changes_twos,layout_relative_changes_threes,layout_show_left_and_right;
	private boolean scrollFlag = false;// 标记是否滑动
	private int lastVisibleItemPosition = 0;// 标记上次滑动位置
	private ImageView imageLeft,imageRight;
	private int isBefore,all;
	private String timestart,timeend;
	private List<DisWaterInfoBean> beens;
	private List<SluiceGateInfoBean> beans;
	private TextView tv_one_OpenHigh,tv_one_OpenProportion,tv_one_PoreID,
	tv_two_OpenProportion_left,tv_two_OpenHigh_left,tv_two_PoreID_left,tv_two_OpenProportion_right,tv_two_OpenHigh_right,tv_two_PoreID_right,
	tv_three_OpenProportion_left,tv_three_OpenHigh_left,tv_three_PoreID_left,tv_three_OpenProportion,tv_three_OpenHigh,tv_three_PoreID,
	tv_three_OpenProportion_right,tv_three_OpenHigh_right,tv_three_PoreID_right;
	private TextView text_apply_water_distrbution_gate_control,
	tv_apply_water_before,tv_apply_water_after,tv_apply_water_flow;
	private ProgressDialog progressDialog;
	
	@SuppressLint("CutPasteId")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflater = inflater;
		View view = inflater.inflate(
				R.layout.fragment_apply_water_distrbution_gate_control,
				container, false);

		actionBar = (MainActionBar) view
				.findViewById(R.id.actionbar_apply_water_distrbution_gate_control);
		actionBar.setLeftIcon(R.drawable.btn_back_selector);
		actionBar.setRightGone();
		actionBar.setTitle("闸门控制");
		actionBar.setActionBarOnClickListener(this);

		tv_indicator = (TextView) view.findViewById(R.id.tv_indicator);
		tv_indicator.setOnClickListener(this);
		tv_time_operation = (RelativeLayout) view.findViewById(R.id.tv_time_operation);
		tv_time_operation.setOnClickListener(this);
		
		btn_time_operation = (Button) view.findViewById(R.id.btn_time_operation);
		btn_time_operation.setOnClickListener(this);
		
		adapter = new ApplyWaterDistrbutionGateAdapter(getActivity());
		
		list_apply_water_distrbution_gate_control = (HorizontalListView) view.findViewById(R.id.list_apply_water_distrbution_gate_control);
		layout = (RelativeLayout) view.findViewById(R.id.layout_relative_change);
		layout_linear_change = (LinearLayout) view.findViewById(R.id.layout_linear_change);
		text_apply_water_distrbution_gate_control = (TextView) view.findViewById(R.id.text_apply_water_distrbution_gate_control);
		
		layout_relative_changes = (RelativeLayout) view.findViewById(R.id.layout_relative_changess);
		layout_relative_changes_left = (RelativeLayout) view.findViewById(R.id.layout_relative_changes_lefts);
		layout_relative_changes_right = (RelativeLayout) view.findViewById(R.id.layout_relative_changes_rights);
		
		layout_relative_changes_one = (RelativeLayout) view.findViewById(R.id.layout_relative_changes_ones);
		layout_relative_changes_two = (RelativeLayout) view.findViewById(R.id.layout_relative_changes_twos);
		layout_relative_changes_three = (RelativeLayout) view.findViewById(R.id.layout_relative_changes_threes);
		layout_linear_changes = (LinearLayout) view.findViewById(R.id.layout_linear_changes);
		tv_time_operation_start = (TextView) view.findViewById(R.id.tv_time_operation_start);
		tv_time_operation_end = (TextView) view.findViewById(R.id.tv_time_operation_end);
		
		tv_superior = (TextView) view.findViewById(R.id.tv_apply_water_distrbution_gate_control_superior);
		
		layout_relative_change = (RelativeLayout) view.findViewById(R.id.layout_relative_changes);
		layout_relative_change_left = (RelativeLayout) view.findViewById(R.id.layout_relative_changes_left);
		layout_relative_change_right = (RelativeLayout) view.findViewById(R.id.layout_relative_changes_right);
		layout_relative_changes_ones = (RelativeLayout) view.findViewById(R.id.layout_relative_changes_one);
		layout_relative_changes_twos = (RelativeLayout) view.findViewById(R.id.layout_relative_changes_two);
		layout_relative_changes_threes = (RelativeLayout) view.findViewById(R.id.layout_relative_changes_three);

		tv_one_OpenProportion = (TextView) view.findViewById(R.id.tv_one_OpenProportion);
		tv_one_OpenHigh = (TextView) view.findViewById(R.id.tv_one_OpenHigh);
		tv_one_PoreID = (TextView) view.findViewById(R.id.tv_one_PoreID);
		tv_two_OpenProportion_left = (TextView) view.findViewById(R.id.tv_two_OpenProportion_left);
		tv_two_OpenHigh_left = (TextView) view.findViewById(R.id.tv_two_OpenHigh_left);
		tv_two_PoreID_left = (TextView) view.findViewById(R.id.tv_two_PoreID_left);
		tv_two_OpenProportion_right = (TextView) view.findViewById(R.id.tv_two_OpenProportion_right);
		tv_two_OpenHigh_right = (TextView) view.findViewById(R.id.tv_two_OpenHigh_right);
		tv_two_PoreID_right = (TextView) view.findViewById(R.id.tv_two_PoreID_right);
		tv_three_OpenProportion_left = (TextView) view.findViewById(R.id.tv_three_OpenProportion_left);
		tv_three_OpenHigh_left = (TextView) view.findViewById(R.id.tv_three_OpenHigh_left);
		tv_three_PoreID_left = (TextView) view.findViewById(R.id.tv_three_PoreID_left);
		tv_three_OpenProportion = (TextView) view.findViewById(R.id.tv_three_OpenProportion);
		tv_three_OpenHigh = (TextView) view.findViewById(R.id.tv_three_OpenHigh);
		tv_three_PoreID = (TextView) view.findViewById(R.id.tv_three_PoreID);
		tv_three_OpenProportion_right = (TextView) view.findViewById(R.id.tv_three_OpenProportion_right);
		tv_three_OpenHigh_right = (TextView) view.findViewById(R.id.tv_three_OpenHigh_right);
		tv_three_PoreID_right = (TextView) view.findViewById(R.id.tv_three_PoreID_right);
		tv_apply_water_before = (TextView) view.findViewById(R.id.tv_apply_water_before);
		tv_apply_water_after = (TextView) view.findViewById(R.id.tv_apply_water_after);
		tv_apply_water_flow = (TextView) view.findViewById(R.id.tv_apply_water_flow);
		layout_show_left_and_right = (RelativeLayout) view.findViewById(R.id.layout_show_left_and_right);
		
		layout_relative_change.setOnClickListener(this);
		layout_relative_change_left.setOnClickListener(this);
		layout_relative_change_right.setOnClickListener(this);
		layout_relative_changes_ones.setOnClickListener(this);
		layout_relative_changes_twos.setOnClickListener(this);
		layout_relative_changes_threes.setOnClickListener(this);
		
		imageLeft = (ImageView) view.findViewById(R.id.image_show_left);
		imageRight = (ImageView) view.findViewById(R.id.image_show_right);
		//获取屏幕高宽
		WindowManager wm = (WindowManager) getActivity()
                .getSystemService(Context.WINDOW_SERVICE);

		int width = wm.getDefaultDisplay().getWidth()/3;
		 
		SharedUtils.setParam(getActivity(), "width", width);
		
		imageLeft.setVisibility(View.GONE);
		list_apply_water_distrbution_gate_control.setOnItemClickListener(this);
		list_apply_water_distrbution_gate_control.setOnScrollStateChangedListener(new OnScrollStateChangedListener() {
			
			@Override
			public void onScrollStateChanged(ScrollState scrollState) {
				 switch (scrollState) {
	                // 当不滚动时
	                case SCROLL_STATE_IDLE:// 是当屏幕停止滚动时
	                    // 判断滚动到底部
	                    if (list_apply_water_distrbution_gate_control.getLastVisiblePosition() == (list_apply_water_distrbution_gate_control
	                            .getCount() )) {
	                    	imageRight.setVisibility(View.VISIBLE);
	                    }
	                    // 判断滚动到顶部
	                    if (list_apply_water_distrbution_gate_control.getFirstVisiblePosition() == 0) {
	                    	imageLeft.setVisibility(View.GONE);
	                    }
	                    scrollFlag = false;
	                    break;
	                case SCROLL_STATE_TOUCH_SCROLL:// 滚动时
	                	if (list_apply_water_distrbution_gate_control.getFirstVisiblePosition() > lastVisibleItemPosition) {// 上滑
							imageLeft.setVisibility(View.VISIBLE);
							imageRight.setVisibility(View.GONE);
							
						} else if (list_apply_water_distrbution_gate_control.getFirstVisiblePosition() < lastVisibleItemPosition) {// 下滑
							imageLeft.setVisibility(View.VISIBLE);
							imageRight.setVisibility(View.VISIBLE);
						} else {
							return;
						}
	                	lastVisibleItemPosition = list_apply_water_distrbution_gate_control.getFirstVisiblePosition();
	                    scrollFlag = true;
	                    break;
	                case SCROLL_STATE_FLING:// 是当用户由于之前划动屏幕并抬起手指，屏幕产生惯性滑动时
	                    scrollFlag = false;
	                    break;
				 }
			}
		});
		layout_show_left_and_right.setVisibility(View.GONE);
		layout.setVisibility(View.GONE);
		layout_linear_change.setVisibility(View.GONE);
		layout_linear_changes.setVisibility(View.GONE);
		init();
		
		return view;
	}

	
//	OnScrollListener listener = new OnScrollListener() {
//		
//		@Override
//		public void onScrollStateChanged(AbsListView view, int scrollState) {
//			// TODO Auto-generated method stub
//			
//		}
//		
//		@Override
//		public void onScroll(AbsListView view, int firstVisibleItem,
//				int visibleItemCount, int totalItemCount) {
//			   if (scrollFlag
//                       && ScreenUtils.getScreenViewBottomHeight(list_apply_water_distrbution_gate_control) >= ScreenUtils
//                               .getScreenHeight(getActivity())) {
//                   if (firstVisibleItem > lastVisibleItemPosition) {// 上滑
//                	   imageLeft.setVisibility(View.GONE);
//                	   imageRight.setVisibility(View.VISIBLE);
//                   } else if (firstVisibleItem < lastVisibleItemPosition) {// 下滑
//                	   imageLeft.setVisibility(View.VISIBLE);
//                	   imageRight.setVisibility(View.GONE);
//                   } else {
//                       return;
//                   }
//                   lastVisibleItemPosition = firstVisibleItem;
//               }
//		}
//	};
	
	private void init() {
		String str1 = (String) SharedUtils.getParam(getActivity(), "DisEquID", "");
		try {
			str1 = java.net.URLEncoder.encode(str1,"UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		RequestParams param3 = new RequestParams(URL.findDisWaterInfoOne+str1);  // 网址(请替换成实际的网址) 
//		 params.addQueryStringParameter("key", "value"); // 参数(请替换成实际的参数与值)   
		progressDialog = ProgressDialog.show(getActivity(), "Loading...",
				"Please wait...", true, false);
		JSONObject js_request2 = new JSONObject();
		try {
			param3.setAsJsonContent(true);
		} catch (Exception e) {
			e.printStackTrace();
			param3.setAsJsonContent(true);
		}//根据实际需求添加相应键值对
		
	        x.http().request(HttpMethod.GET ,param3, new CommonCallback<String>() {  
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
	            }  
	  
	            @Override  
	            public void onSuccess(String arg0) {  
	                  Toast.makeText(getActivity(), "请求成功", Toast.LENGTH_SHORT);
	                  Gson gson = new Gson();
	                  System.out.println(arg0);
	                  FindDisWaterInfoOneBean fromJson = gson.fromJson(arg0, FindDisWaterInfoOneBean.class);
	                  beens = fromJson.getDisWaterInfo();
	                  beans = fromJson.getSluiceGateInfo();
	                  if(!CheckUtil.IsEmpty(beans)){
	                	  adapter.addData(beans, true);
	                	  
	                	  if(beans.size() == 1){
	  	                	layout.setVisibility(View.VISIBLE);
	  	        			layout_linear_change.setVisibility(View.GONE);
	  	        			layout_linear_changes.setVisibility(View.GONE);
	  	        			list_apply_water_distrbution_gate_control.setVisibility(View.GONE);
	  	        			RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)layout_relative_changes.getLayoutParams();
	  	        			int height;
	  	        			if(CheckUtil.IsEmpty(beans.get(0).getOpenProportion())){
	  	        				height = DensityUtil.dip2px(getActivity(),(int)((100-Float.valueOf(0)*100)*2));
	  	        			}else{
	  	        				height = DensityUtil.dip2px(getActivity(),(int)((100-Float.valueOf(beans.get(0).getOpenProportion())*100)*2));
	  	        			}
	  	        			layoutParams.height = height;
	  	        			layout_relative_changes.setLayoutParams(layoutParams);
	  	        			layout_relative_changes.requestLayout();
	  	        			if(CheckUtil.IsEmpty(beans.get(0).getOpenProportion())){
	  	        				tv_one_OpenProportion.setText(0+"%");
	  	        			}else{
	  	        				tv_one_OpenProportion.setText((int)(Float.valueOf(beans.get(0).getOpenProportion())*100)+"%");
	  	        			}
	  	        			if(CheckUtil.IsEmpty(beans.get(0).getOpenHigh())){
	  	        				tv_one_OpenHigh.setText(0+"m³");
	  	        			}else{
	  	        				tv_one_OpenHigh.setText(beans.get(0).getOpenHigh()+"m³");
	  	        			}
	  	        			tv_one_PoreID.setText(beans.get(0).getPoreID()+"");
	  	        		}
	  	                else if(beans.size() == 2){
	  	        			layout.setVisibility(View.GONE);
	  	        			layout_linear_change.setVisibility(View.VISIBLE);
	  	        			layout_linear_changes.setVisibility(View.GONE);
	  	        			list_apply_water_distrbution_gate_control.setVisibility(View.GONE);
	  	        			RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)layout_relative_changes_left.getLayoutParams();
	  	        			int height;
	  	        			if(CheckUtil.IsEmpty(beans.get(0).getOpenProportion())){
	  	        				height = DensityUtil.dip2px(getActivity(),(int)((100-Float.valueOf(0)*100)*2));
	  	        			}else{
	  	        				height = DensityUtil.dip2px(getActivity(),(int)((100-Float.valueOf(beans.get(0).getOpenProportion())*100)*2));
	  	        			}
	  	        			layoutParams.height = height;
	  	        			layout_relative_changes_left.setLayoutParams(layoutParams);
	  	        			layout_relative_changes_left.requestLayout();
	  	        			if(CheckUtil.IsEmpty(beans.get(0).getOpenProportion())){
	  	        				tv_two_OpenProportion_left.setText(0+"%");
	  	        			}else{
	  	        				tv_two_OpenProportion_left.setText((int)(Float.valueOf(beans.get(0).getOpenProportion())*100)+"%");
	  	        			}
	  	        			if(CheckUtil.IsEmpty(beans.get(0).getOpenHigh())){
	  	        				tv_two_OpenHigh_left.setText(0+"m³");
	  	        			}else{
	  	        				tv_two_OpenHigh_left.setText(beans.get(0).getOpenHigh()+"m³");
	  	        			}
	  	        			
	  	        			tv_two_PoreID_left.setText(beans.get(0).getPoreID());
	  	        			
	  	        			RelativeLayout.LayoutParams layoutParam1 = (RelativeLayout.LayoutParams)layout_relative_changes_right.getLayoutParams();
	  	        			int heights;
	  	        			if(CheckUtil.IsEmpty(beans.get(1).getOpenProportion())){
	  	        				heights = DensityUtil.dip2px(getActivity(),(int)((100-Float.valueOf(0)*100)*2));
	  	        			}else{
	  	        				heights = DensityUtil.dip2px(getActivity(),(int)((100-Float.valueOf(beans.get(1).getOpenProportion())*100)*2));
	  	        			}
	  	        			layoutParam1.height = heights;
	  	        			layout_relative_changes_right.setLayoutParams(layoutParam1);
	  	        			layout_relative_changes_right.requestLayout();
	  	        			if(CheckUtil.IsEmpty(beans.get(1).getOpenProportion())){
	  	        				tv_two_OpenProportion_right.setText(0+"%");
	  	        			}else{
	  	        				tv_two_OpenProportion_right.setText((int)(Float.valueOf(beans.get(1).getOpenProportion())*100)+"%");
	  	        			}
	  	        			if(CheckUtil.IsEmpty(beans.get(1).getOpenHigh())){
	  	        				tv_two_OpenHigh_right.setText(0+"m³");
	  	        			}else{
	  	        				tv_two_OpenHigh_right.setText(beans.get(1).getOpenHigh()+"m³");
	  	        			}
	  	        			tv_two_PoreID_right.setText(beans.get(1).getPoreID());
	  	        			}
	  	        		else if(beans.size() == 3){
	  	        			layout.setVisibility(View.GONE);
	  	        			layout_linear_change.setVisibility(View.GONE);
	  	        			layout_linear_changes.setVisibility(View.VISIBLE);
	  	        			list_apply_water_distrbution_gate_control.setVisibility(View.GONE);
	  	        			RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)layout_relative_changes_one.getLayoutParams();
	  	        			int height;
	  	        			if(CheckUtil.IsEmpty(beans.get(0).getOpenProportion())){
	  	        				height = DensityUtil.dip2px(getActivity(),(int)((100-Float.valueOf(0)*100)*2));
	  	        			}else{
	  	        				height = DensityUtil.dip2px(getActivity(),(int)((100-Float.valueOf(beans.get(0).getOpenProportion())*100)*2));
	  	        			}
	  	        			layoutParams.height = height;
	  	        			layout_relative_changes_one.setLayoutParams(layoutParams);
	  	        			layout_relative_changes_one.requestLayout();
	  	        			if(CheckUtil.IsEmpty(beans.get(0).getOpenProportion())){
	  	        				tv_three_OpenProportion_left.setText(0+"%");
	  	        			}else{
	  	        				tv_three_OpenProportion_left.setText((int)(Float.valueOf(beans.get(0).getOpenProportion())*100)+"%");
	  	        			}
	  	        			if(CheckUtil.IsEmpty(beans.get(0).getOpenHigh())){
	  	        				tv_three_OpenHigh_left.setText(0+"m³");
	  	        			}else{
	  	        				tv_three_OpenHigh_left.setText(beans.get(0).getOpenHigh()+"m³");
	  	        			}
	  	        			tv_three_PoreID_left.setText(beans.get(0).getPoreID());
	  	        			
	  	        			RelativeLayout.LayoutParams layoutParam1 = (RelativeLayout.LayoutParams)layout_relative_changes_two.getLayoutParams();
	  	        			int heights;
	  	        			if(CheckUtil.IsEmpty(beans.get(1).getOpenProportion())){
	  	        				heights = DensityUtil.dip2px(getActivity(),(int)((100-Float.valueOf(0)*100)*2));
	  	        			}else{
	  	        				heights = DensityUtil.dip2px(getActivity(),(int)((100-Float.valueOf(beans.get(1).getOpenProportion())*100)*2));
	  	        			}
	  	        			layoutParam1.height = heights;
	  	        			layout_relative_changes_two.setLayoutParams(layoutParam1);
	  	        			layout_relative_changes_two.requestLayout();
	  	        			if(CheckUtil.IsEmpty(beans.get(1).getOpenProportion())){
	  	        				tv_three_OpenProportion.setText(0+"%");
	  	        			}else{
	  	        				tv_three_OpenProportion.setText((int)(Float.valueOf(beans.get(1).getOpenProportion())*100)+"%");
	  	        			}
	  	        			if(CheckUtil.IsEmpty(beans.get(1).getOpenHigh())){
	  	        				tv_three_OpenHigh.setText(0+"m³");
	  	        			}else{
	  	        				tv_three_OpenHigh.setText(beans.get(1).getOpenHigh()+"m³");
	  	        			}
	  	        			tv_three_PoreID.setText(beans.get(1).getPoreID());
	  	        			
	  	        			RelativeLayout.LayoutParams layoutParam2 = (RelativeLayout.LayoutParams)layout_relative_changes_three.getLayoutParams();
	  	        			int height2;
	  	        			if(CheckUtil.IsEmpty(beans.get(2).getOpenProportion())){
	  	        				height2 = DensityUtil.dip2px(getActivity(),(int)((100-Float.valueOf(0)*100)*2));
	  	        			}else{
	  	        				height2 = DensityUtil.dip2px(getActivity(),(int)((100-Float.valueOf(beans.get(2).getOpenProportion())*100)*2));
	  	        			}
	  	        			layoutParam2.height = height2;
	  	        			layout_relative_changes_three.setLayoutParams(layoutParam2);
	  	        			layout_relative_changes_three.requestLayout();
	  	        			if(CheckUtil.IsEmpty(beans.get(2).getOpenProportion())){
	  	        				tv_three_OpenProportion_right.setText(0+"%");
	  	        			}else{
	  	        				tv_three_OpenProportion_right.setText((int)(Float.valueOf(beans.get(2).getOpenProportion())*100)+"%");
	  	        			}
	  	        			if(CheckUtil.IsEmpty(beans.get(2).getOpenHigh())){
	  	        				tv_three_OpenHigh_right.setText(0+"m³");
	  	        			}else{
	  	        				tv_three_OpenHigh_right.setText(beans.get(2).getOpenHigh()+"m³");
	  	        			}
	  	        			tv_three_PoreID_right.setText(beans.get(2).getPoreID());
	  	        		}
	  	        		else if(beans.size()> 3){
	  	        			layout_show_left_and_right.setVisibility(View.VISIBLE);
	  	        			layout.setVisibility(View.GONE);
	  	        			layout_linear_change.setVisibility(View.GONE);
	  	        			layout_linear_changes.setVisibility(View.GONE);
	  	        			list_apply_water_distrbution_gate_control.setAdapter(adapter);
	  	        		}
	  	        		if(beans.size()<= 3){
	  	        			layout_show_left_and_right.setVisibility(View.GONE);
	  	        		}else{
	  	        			layout_show_left_and_right.setVisibility(View.VISIBLE);
	  	        		}
	                  }else{
	  	        			layout_show_left_and_right.setVisibility(View.GONE);
	  	        			layout.setVisibility(View.GONE);
	  	        			layout_linear_change.setVisibility(View.GONE);
	  	        			layout_linear_changes.setVisibility(View.GONE);
	  	        		}
	                  if(!CheckUtil.IsEmpty(beens)){
	                	  if(CheckUtil.IsEmpty(beens.get(0).getDisEquName())){
	  	                	text_apply_water_distrbution_gate_control.setText("");
	  	                }else{
	  	                	text_apply_water_distrbution_gate_control.setText(beens.get(0).getDisEquName());
	  	                }
	  	                if(CheckUtil.IsEmpty(beens.get(0).getPoreNum())){
	  	                	tv_indicator.setText("");
	  	                }else{
	  	                	tv_indicator.setText(beens.get(0).getPoreNum());
	  	                }
	  	                if(CheckUtil.IsEmpty(beens.get(0).getFrontWaterLevel())){
	  	                	tv_apply_water_before.setText("0");
	  	                }else{
	  	                	tv_apply_water_before.setText(beens.get(0).getFrontWaterLevel());
	  	                }
	  	                if(CheckUtil.IsEmpty(beens.get(0).getBackWaterLevel())){
	  	                	tv_apply_water_after.setText("0");
	  	                }else{
	  	                	tv_apply_water_after.setText(beens.get(0).getBackWaterLevel());
	  	                }
	  	                if(CheckUtil.IsEmpty(beens.get(0).getDesignFlow())){
	  	                	tv_apply_water_flow.setText("0");
	  	                }else{
	  	                	tv_apply_water_flow.setText(beens.get(0).getDesignFlow());
	  	                }
	  	                if(CheckUtil.IsEmpty(beens.get(0).getOpenPoreTime())){
	  	                	tv_time_operation_start.setText("开始时间: "+ "0000-00-00 00:00");
	  	                }else{
	  	                	tv_time_operation_start.setText("开始时间: "+beens.get(0).getOpenPoreTime());
	  	                }
	  	                if(CheckUtil.IsEmpty(beens.get(0).getClosePoreTime())){
	  	                	tv_time_operation_end.setText("结束时间: "+ "0000-00-00 00:00");
	  	                }else{
	  	                	tv_time_operation_end.setText("结束时间: "+beens.get(0).getClosePoreTime());
	  	                }
	                  }
	        		
	        		String str;
	        		if(CheckUtil.IsEmpty(beens.get(0).getSuperEqu())){
	        			str = "";
	        		}else{
	        			str = beens.get(0).getSuperEqu();
	        		}
	        		SpannableStringBuilder style = new SpannableStringBuilder("上级设备: "+str);
	        		// str代表要显示的全部字符串
	        		ClickableSpan what = new ClickableSpan() {

	        			@Override
	        			public void onClick(View widget) {
	        				Fetch();
	        			}
	        		};
	        		style.setSpan(what, 6, beens.get(0).getSuperEqu().length()+6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	        		tv_superior.setText(style);
	        		tv_superior.setMovementMethod(LinkMovementMethod.getInstance());
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
			ApplyWaterDistrbutionFragment fragment = new ApplyWaterDistrbutionFragment();
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			transaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.replace(R.id.fl, fragment, "main");
			transaction.commit();
			break;
		case R.id.tv_indicator:
			all = 1;
			SharedUtils.setParam(getActivity(), "all", all);
			AppayWaterGateHaploporeFragment fragment1 = new AppayWaterGateHaploporeFragment();
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			Bundle bundle2 = new Bundle();
			if(CheckUtil.IsEmpty(beans)){
				
			}else{
				if(CheckUtil.IsEmpty(beans.get(0).getOpenProportion())){
					bundle2.putInt("OpenProportion",0);
				}else{
					bundle2.putInt("OpenProportion",(int)(Float.valueOf(beans.get(0).getOpenProportion())*100));
				}
				if(CheckUtil.IsEmpty(beans.get(0).getOpenHigh())){
					bundle2.putString("OpenHigh", 0+"");
				}else{
					bundle2.putString("OpenHigh", beans.get(0).getOpenHigh());
				}
				bundle2.putString("PoreId", beans.get(0).getPoreID());
				fragment1.setArguments(bundle2);
				transaction
				.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
				transaction.replace(R.id.fl, fragment1, "main");
				transaction.commit();
			}
			break;
		case R.id.btn_time_operation:
			AppayWaterGateLinkageFragment fragment2 = new AppayWaterGateLinkageFragment();
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			Bundle bundle = new Bundle();
			bundle.putSerializable("beans", (Serializable) beans);
			fragment2.setArguments(bundle);
			transaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.replace(R.id.fl, fragment2, "main");
			transaction.commit();
			break;
		case R.id.tv_time_operation:
			showDateTimePicker(mInflater);
			break;
		case R.id.layout_relative_changes:
			all = 0;
			SharedUtils.setParam(getActivity(), "all", all);
			AppayWaterGateHaploporeFragment fragment8 = new AppayWaterGateHaploporeFragment();
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			Bundle bundle3 = new Bundle();
			if(CheckUtil.IsEmpty(beans)){
				
			}else{
				if(CheckUtil.IsEmpty(beans.get(0).getOpenProportion())){
					bundle3.putInt("OpenProportion",0);
				}else{
					bundle3.putInt("OpenProportion",(int)(Float.valueOf(beans.get(0).getOpenProportion())*100));
				}
				if(CheckUtil.IsEmpty(beans.get(0).getOpenHigh())){
					bundle3.putString("OpenHigh", 0+"");
				}else{
					bundle3.putString("OpenHigh", beans.get(0).getOpenHigh());
				}
				bundle3.putString("PoreId", beans.get(0).getPoreID());
				fragment8.setArguments(bundle3);
			}
			transaction
			.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.replace(R.id.fl, fragment8, "main");
			transaction.commit();
			break;
		case R.id.layout_relative_changes_left:
			all = 0;
			SharedUtils.setParam(getActivity(), "all", all);
			AppayWaterGateHaploporeFragment fragment3 = new AppayWaterGateHaploporeFragment();
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			Bundle bundle4 = new Bundle();
			if(CheckUtil.IsEmpty(beans)){
				
			}else{
				if(CheckUtil.IsEmpty(beans.get(0).getOpenProportion())){
					bundle4.putInt("OpenProportion",0);
				}else{
					bundle4.putInt("OpenProportion",(int)(Float.valueOf(beans.get(0).getOpenProportion())*100));
				}
				if(CheckUtil.IsEmpty(beans.get(0).getOpenHigh())){
					bundle4.putString("OpenHigh", 0+"");
				}else{
					bundle4.putString("OpenHigh", beans.get(0).getOpenHigh());
				}
				bundle4.putString("PoreId", beans.get(0).getPoreID());
				fragment3.setArguments(bundle4);
			}
			transaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.replace(R.id.fl, fragment3, "main");
			transaction.commit();
			break;
		case R.id.layout_relative_changes_right:
			all = 0;
			SharedUtils.setParam(getActivity(), "all", all);
			AppayWaterGateHaploporeFragment fragment4 = new AppayWaterGateHaploporeFragment();
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			Bundle bundle5 = new Bundle();
			if(CheckUtil.IsEmpty(beans)){
				
			}else{
				if(CheckUtil.IsEmpty(beans.get(0).getOpenProportion())){
					bundle5.putInt("OpenProportion",0);
				}else{
					bundle5.putInt("OpenProportion",(int)(Float.valueOf(beans.get(0).getOpenProportion())*100));
				}
				if(CheckUtil.IsEmpty(beans.get(0).getOpenHigh())){
					bundle5.putString("OpenHigh", 0+"");
				}else{
					bundle5.putString("OpenHigh", beans.get(0).getOpenHigh());
				}
				bundle5.putString("PoreId", beans.get(0).getPoreID());
				fragment4.setArguments(bundle5);
			}
			transaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.replace(R.id.fl, fragment4, "main");
			transaction.commit();
			break;
		case R.id.layout_relative_changes_one:
			all = 0;
			SharedUtils.setParam(getActivity(), "all", all);
			AppayWaterGateHaploporeFragment fragment5 = new AppayWaterGateHaploporeFragment();
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			Bundle bundle6 = new Bundle();
			if(CheckUtil.IsEmpty(beans)){
				
			}else{
				if(CheckUtil.IsEmpty(beans.get(0).getOpenProportion())){
					bundle6.putInt("OpenProportion",0);
				}else{
					bundle6.putInt("OpenProportion",(int)(Float.valueOf(beans.get(0).getOpenProportion())*100));
				}
				if(CheckUtil.IsEmpty(beans.get(0).getOpenHigh())){
					bundle6.putString("OpenHigh", 0+"");
				}else{
					bundle6.putString("OpenHigh", beans.get(0).getOpenHigh());
				}
				bundle6.putString("PoreId", beans.get(0).getPoreID());
				fragment5.setArguments(bundle6);
			}
			transaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.replace(R.id.fl, fragment5, "main");
			transaction.commit();
			break;
		case R.id.layout_relative_changes_two:
			all = 0;
			SharedUtils.setParam(getActivity(), "all", all);
			AppayWaterGateHaploporeFragment fragment6 = new AppayWaterGateHaploporeFragment();
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			Bundle bundle7 = new Bundle();
			if(CheckUtil.IsEmpty(beans)){
				
			}else{
				if(CheckUtil.IsEmpty(beans.get(0).getOpenProportion())){
					bundle7.putInt("OpenProportion",0);
				}else{
					bundle7.putInt("OpenProportion",(int)(Float.valueOf(beans.get(0).getOpenProportion())*100));
				}
				if(CheckUtil.IsEmpty(beans.get(0).getOpenHigh())){
					bundle7.putString("OpenHigh", 0+"");
				}else{
					bundle7.putString("OpenHigh", beans.get(0).getOpenHigh());
				}
				bundle7.putString("PoreId", beans.get(0).getPoreID());
				fragment6.setArguments(bundle7);
			}
			transaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.replace(R.id.fl, fragment6, "main");
			transaction.commit();
			break;
		case R.id.layout_relative_changes_three:
			all = 0;
			SharedUtils.setParam(getActivity(), "all", all);
			AppayWaterGateHaploporeFragment fragment7 = new AppayWaterGateHaploporeFragment();
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			Bundle bundle8 = new Bundle();
			if(CheckUtil.IsEmpty(beans)){
				
			}else{
				if(CheckUtil.IsEmpty(beans.get(0).getOpenProportion())){
					bundle8.putInt("OpenProportion",0);
				}else{
					bundle8.putInt("OpenProportion",(int)(Float.valueOf(beans.get(0).getOpenProportion())*100));
				}
				if(CheckUtil.IsEmpty(beans.get(0).getOpenHigh())){
					bundle8.putString("OpenHigh", 0+"");
				}else{
					bundle8.putString("OpenHigh", beans.get(0).getOpenHigh());
				}
				bundle8.putString("PoreId", beans.get(0).getPoreID());
				fragment7.setArguments(bundle8);
			}
			transaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.replace(R.id.fl, fragment7, "main");
			transaction.commit();
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
		dialog.setTitle("请选择预约时间");
		Calendar c = Calendar.getInstance();
		int currentYear = c.get(Calendar.YEAR);
		int currentMonth = c.get(Calendar.MONTH);
		int currentDay = c.get(Calendar.DATE);
		int currentHour = c.get(Calendar.HOUR_OF_DAY);
		int currentMinute = c.get(Calendar.MINUTE);
//		int Hour = c.get(Calendar.HOUR_OF_DAY);
//		int Minute = c.get(Calendar.MINUTE);

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
		wv_hour.setAdapter(new NumbericWheelAdapter(0, 23));
		wv_hour.setCyclic(true);
		wv_hour.setLabel("时");// 添加文字

		wv_minute = (WheelView) view.findViewById(R.id.minutes_gate);
		wv_minute.setAdapter(new NumbericWheelAdapter(0, 59));
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
					tv_time_operation_start.setText("开始时间: "+timestart);
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
					tv_time_operation_end.setText("结束时间: "+timeend);
					dialog.dismiss();
				}
				 else {
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
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		all = 0;
		SharedUtils.setParam(getActivity(), "all", all);
		FragmentManager fgManager = getFragmentManager();
		FragmentTransaction transaction = fgManager.beginTransaction();
		AppayWaterGateHaploporeFragment fragment1 = new AppayWaterGateHaploporeFragment();
		Bundle bundle = new Bundle();
		if(CheckUtil.IsEmpty(beans.get(position).getOpenProportion())){
			bundle.putInt("OpenProportion",0);
		}else{
			bundle.putInt("OpenProportion",(int)(Float.valueOf(beans.get(position).getOpenProportion())*100));
		}
		if(CheckUtil.IsEmpty(beans.get(position).getOpenHigh())){
			bundle.putString("OpenHigh", 0+"");
		}else{
			bundle.putString("OpenHigh", beans.get(position).getOpenHigh());
		}
		bundle.putString("PoreId", beans.get(position).getPoreID());
		fragment1.setArguments(bundle);
		// transaction.setCustomAnimations(R.anim.right_in,
		// R.anim.right_out);
		transaction
				.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		transaction.replace(R.id.fl, fragment1, "main");
		transaction.commit();
	}
	private void Fetch(){
		if(!CheckUtil.IsEmpty(beens.get(0).getSuperEqu())){
			String str1 = beens.get(0).getSuperEqu();
			SharedUtils.setParam(getActivity(), "DisEquID", beens.get(0).getSuperEqu());
			try {
				str1 = java.net.URLEncoder.encode(str1,"UTF-8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			RequestParams param3 = new RequestParams(URL.findDisWaterInfoOne+str1);  // 网址(请替换成实际的网址) 
//		 params.addQueryStringParameter("key", "value"); // 参数(请替换成实际的参数与值)   
			progressDialog = ProgressDialog.show(getActivity(), "Loading...",
					"Please wait...", true, false);
			JSONObject js_request2 = new JSONObject();
			try {
				param3.setAsJsonContent(true);
			} catch (Exception e) {
				e.printStackTrace();
				param3.setAsJsonContent(true);
			}//根据实际需求添加相应键值对
			
			x.http().request(HttpMethod.GET ,param3, new CommonCallback<String>() {  
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
				}  
				
				@Override  
				public void onSuccess(String arg0) {  
					Toast.makeText(getActivity(), "请求成功", Toast.LENGTH_SHORT);
					Gson gson = new Gson();
					System.out.println(arg0);
					FindDisWaterInfoOneBean fromJson = gson.fromJson(arg0, FindDisWaterInfoOneBean.class);
//	                  authorizedBeen = new AuthorizedBeen();
//	                  authorizedBeen = gson.fromJson(arg0, AuthorizedBeen.class);
					beens = fromJson.getDisWaterInfo();
					beans = fromJson.getSluiceGateInfo();
					if(!CheckUtil.IsEmpty(beans)){
						adapter.addData(beans, true);
						
						if(beans.size() == 1){
							layout.setVisibility(View.VISIBLE);
							layout_linear_change.setVisibility(View.GONE);
							layout_linear_changes.setVisibility(View.GONE);
							list_apply_water_distrbution_gate_control.setVisibility(View.GONE);
							RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)layout_relative_changes.getLayoutParams();
							int height;
							if(CheckUtil.IsEmpty(beans.get(0).getOpenProportion())){
								height = DensityUtil.dip2px(getActivity(),(int)((100-Float.valueOf(0)*100)*2));
							}else{
								height = DensityUtil.dip2px(getActivity(),(int)((100-Float.valueOf(beans.get(0).getOpenProportion())*100)*2));
							}
							layoutParams.height = height;
							layout_relative_changes.setLayoutParams(layoutParams);
							layout_relative_changes.requestLayout();
							if(CheckUtil.IsEmpty(beans.get(0).getOpenProportion())){
								tv_one_OpenProportion.setText(0+"%");
							}else{
								tv_one_OpenProportion.setText((int)(Float.valueOf(beans.get(0).getOpenProportion())*100)+"%");
							}
							if(CheckUtil.IsEmpty(beans.get(0).getOpenHigh())){
								tv_one_OpenHigh.setText(0+"m³");
							}else{
								tv_one_OpenHigh.setText(beans.get(0).getOpenHigh()+"m³");
							}
							tv_one_PoreID.setText(beans.get(0).getPoreID()+"");
						}
						else if(beans.size() == 2){
							layout.setVisibility(View.GONE);
							layout_linear_change.setVisibility(View.VISIBLE);
							layout_linear_changes.setVisibility(View.GONE);
							list_apply_water_distrbution_gate_control.setVisibility(View.GONE);
							RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)layout_relative_changes_left.getLayoutParams();
							int height;
							if(CheckUtil.IsEmpty(beans.get(0).getOpenProportion())){
								height = DensityUtil.dip2px(getActivity(),(int)((100-Float.valueOf(0)*100)*2));
							}else{
								height = DensityUtil.dip2px(getActivity(),(int)((100-Float.valueOf(beans.get(0).getOpenProportion())*100)*2));
							}
							layoutParams.height = height;
							layout_relative_changes_left.setLayoutParams(layoutParams);
							layout_relative_changes_left.requestLayout();
							if(CheckUtil.IsEmpty(beans.get(0).getOpenProportion())){
								tv_two_OpenProportion_left.setText(0+"%");
							}else{
								tv_two_OpenProportion_left.setText((int)(Float.valueOf(beans.get(0).getOpenProportion())*100)+"%");
							}
							if(CheckUtil.IsEmpty(beans.get(0).getOpenHigh())){
								tv_two_OpenHigh_left.setText(0+"m³");
							}else{
								tv_two_OpenHigh_left.setText(beans.get(0).getOpenHigh()+"m³");
							}
							
							tv_two_PoreID_left.setText(beans.get(0).getPoreID());
							
							RelativeLayout.LayoutParams layoutParam1 = (RelativeLayout.LayoutParams)layout_relative_changes_right.getLayoutParams();
							int heights;
							if(CheckUtil.IsEmpty(beans.get(1).getOpenProportion())){
								heights = DensityUtil.dip2px(getActivity(),(int)((100-Float.valueOf(0)*100)*2));
							}else{
								heights = DensityUtil.dip2px(getActivity(),(int)((100-Float.valueOf(beans.get(1).getOpenProportion())*100)*2));
							}
							layoutParam1.height = heights;
							layout_relative_changes_right.setLayoutParams(layoutParam1);
							layout_relative_changes_right.requestLayout();
							if(CheckUtil.IsEmpty(beans.get(1).getOpenProportion())){
								tv_two_OpenProportion_right.setText(0+"%");
							}else{
								tv_two_OpenProportion_right.setText((int)(Float.valueOf(beans.get(1).getOpenProportion())*100)+"%");
							}
							if(CheckUtil.IsEmpty(beans.get(1).getOpenHigh())){
								tv_two_OpenHigh_right.setText(0+"m³");
							}else{
								tv_two_OpenHigh_right.setText(beans.get(1).getOpenHigh()+"m³");
							}
							tv_two_PoreID_right.setText(beans.get(1).getPoreID());
						}
						else if(beans.size() == 3){
							layout.setVisibility(View.GONE);
							layout_linear_change.setVisibility(View.GONE);
							layout_linear_changes.setVisibility(View.VISIBLE);
							list_apply_water_distrbution_gate_control.setVisibility(View.GONE);
							RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)layout_relative_changes_one.getLayoutParams();
							int height;
							if(CheckUtil.IsEmpty(beans.get(0).getOpenProportion())){
								height = DensityUtil.dip2px(getActivity(),(int)((100-Float.valueOf(0)*100)*2));
							}else{
								height = DensityUtil.dip2px(getActivity(),(int)((100-Float.valueOf(beans.get(0).getOpenProportion())*100)*2));
							}
							layoutParams.height = height;
							layout_relative_changes_one.setLayoutParams(layoutParams);
							layout_relative_changes_one.requestLayout();
							if(CheckUtil.IsEmpty(beans.get(0).getOpenProportion())){
								tv_three_OpenProportion_left.setText(0+"%");
							}else{
								tv_three_OpenProportion_left.setText((int)(Float.valueOf(beans.get(0).getOpenProportion())*100)+"%");
							}
							if(CheckUtil.IsEmpty(beans.get(0).getOpenHigh())){
								tv_three_OpenHigh_left.setText(0+"m³");
							}else{
								tv_three_OpenHigh_left.setText(beans.get(0).getOpenHigh()+"m³");
							}
							tv_three_PoreID_left.setText(beans.get(0).getPoreID());
							
							RelativeLayout.LayoutParams layoutParam1 = (RelativeLayout.LayoutParams)layout_relative_changes_two.getLayoutParams();
							int heights;
							if(CheckUtil.IsEmpty(beans.get(1).getOpenProportion())){
								heights = DensityUtil.dip2px(getActivity(),(int)((100-Float.valueOf(0)*100)*2));
							}else{
								heights = DensityUtil.dip2px(getActivity(),(int)((100-Float.valueOf(beans.get(1).getOpenProportion())*100)*2));
							}
							layoutParam1.height = heights;
							layout_relative_changes_two.setLayoutParams(layoutParam1);
							layout_relative_changes_two.requestLayout();
							if(CheckUtil.IsEmpty(beans.get(1).getOpenProportion())){
								tv_three_OpenProportion.setText(0+"%");
							}else{
								tv_three_OpenProportion.setText((int)(Float.valueOf(beans.get(1).getOpenProportion())*100)+"%");
							}
							if(CheckUtil.IsEmpty(beans.get(1).getOpenHigh())){
								tv_three_OpenHigh.setText(0+"m³");
							}else{
								tv_three_OpenHigh.setText(beans.get(1).getOpenHigh()+"m³");
							}
							tv_three_PoreID.setText(beans.get(1).getPoreID());
							
							RelativeLayout.LayoutParams layoutParam2 = (RelativeLayout.LayoutParams)layout_relative_changes_three.getLayoutParams();
							int height2;
							if(CheckUtil.IsEmpty(beans.get(2).getOpenProportion())){
								height2 = DensityUtil.dip2px(getActivity(),(int)((100-Float.valueOf(0)*100)*2));
							}else{
								height2 = DensityUtil.dip2px(getActivity(),(int)((100-Float.valueOf(beans.get(2).getOpenProportion())*100)*2));
							}
							layoutParam2.height = height2;
							layout_relative_changes_three.setLayoutParams(layoutParam2);
							layout_relative_changes_three.requestLayout();
							if(CheckUtil.IsEmpty(beans.get(2).getOpenProportion())){
								tv_three_OpenProportion_right.setText(0+"%");
							}else{
								tv_three_OpenProportion_right.setText((int)(Float.valueOf(beans.get(2).getOpenProportion())*100)+"%");
							}
							if(CheckUtil.IsEmpty(beans.get(2).getOpenHigh())){
								tv_three_OpenHigh_right.setText(0+"m³");
							}else{
								tv_three_OpenHigh_right.setText(beans.get(2).getOpenHigh()+"m³");
							}
							tv_three_PoreID_right.setText(beans.get(2).getPoreID());
						}
						else if(beans.size()> 3){
							layout_show_left_and_right.setVisibility(View.VISIBLE);
							layout.setVisibility(View.GONE);
							layout_linear_change.setVisibility(View.GONE);
							layout_linear_changes.setVisibility(View.GONE);
							list_apply_water_distrbution_gate_control.setAdapter(adapter);
						}
						if(beans.size()<= 3){
							layout_show_left_and_right.setVisibility(View.GONE);
						}else{
							layout_show_left_and_right.setVisibility(View.VISIBLE);
						}
					}else{
						layout_show_left_and_right.setVisibility(View.GONE);
						layout.setVisibility(View.GONE);
						layout_linear_change.setVisibility(View.GONE);
						layout_linear_changes.setVisibility(View.GONE);
					}
					if(!CheckUtil.IsEmpty(beens)){
						if(CheckUtil.IsEmpty(beens.get(0).getDisEquName())){
							text_apply_water_distrbution_gate_control.setText("");
						}else{
							text_apply_water_distrbution_gate_control.setText(beens.get(0).getDisEquName());
						}
						if(CheckUtil.IsEmpty(beens.get(0).getPoreNum())){
							tv_indicator.setText("");
						}else{
							tv_indicator.setText(beens.get(0).getPoreNum());
						}
						if(CheckUtil.IsEmpty(beens.get(0).getFrontWaterLevel())){
							tv_apply_water_before.setText("0");
						}else{
							tv_apply_water_before.setText(beens.get(0).getFrontWaterLevel());
						}
						if(CheckUtil.IsEmpty(beens.get(0).getBackWaterLevel())){
							tv_apply_water_after.setText("0");
						}else{
							tv_apply_water_after.setText(beens.get(0).getBackWaterLevel());
						}
						if(CheckUtil.IsEmpty(beens.get(0).getDesignFlow())){
							tv_apply_water_flow.setText("0");
						}else{
							tv_apply_water_flow.setText(beens.get(0).getDesignFlow());
						}
						if(CheckUtil.IsEmpty(beens.get(0).getOpenPoreTime())){
							tv_time_operation_start.setText("开始时间: "+ "0000-00-00 00:00");
						}else{
							tv_time_operation_start.setText("开始时间: "+beens.get(0).getOpenPoreTime());
						}
						if(CheckUtil.IsEmpty(beens.get(0).getClosePoreTime())){
							tv_time_operation_end.setText("结束时间: "+ "0000-00-00 00:00");
						}else{
							tv_time_operation_end.setText("结束时间: "+beens.get(0).getClosePoreTime());
						}
					}
					
					String str;
					if(CheckUtil.IsEmpty(beens.get(0).getSuperEqu())){
						str = "";
					}else{
						str = beens.get(0).getSuperEqu();
					}
					SpannableStringBuilder style = new SpannableStringBuilder("上级设备: "+str);
					// str代表要显示的全部字符串
					ClickableSpan what = new ClickableSpan() {
						
						@Override
						public void onClick(View widget) {
							Fetch();
						}
					};
					style.setSpan(what, 6, beens.get(0).getSuperEqu().length()+6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
					tv_superior.setText(style);
					tv_superior.setMovementMethod(LinkMovementMethod.getInstance());
					progressDialog.dismiss();
				}
			}); 
		}
	}
	}