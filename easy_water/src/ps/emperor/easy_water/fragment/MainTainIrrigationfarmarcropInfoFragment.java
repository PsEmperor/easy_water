package ps.emperor.easy_water.fragment;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

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
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.adapter.ImageAdapter;
import ps.emperor.easy_water.entity.MainTainIrrigationInfoBean;
import ps.emperor.easy_water.entity.MainTainIrrigationInfoBean.infoList;
import ps.emperor.easy_water.utils.CheckUtil;
import ps.emperor.easy_water.utils.SharedUtils;
import ps.emperor.easy_water.utils.URL;
import ps.emperor.easy_water.view.MainActionBar;
import ps.emperor.easy_water.view.MainActionBars;
import ps.emperor.easy_water.view.MyGridView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.PopupWindow.OnDismissListener;

/**
 * 种植作物信息维护（应用）
 * 
 * @author 毛国江
 * @version 2016-7-16 下午13：43
 */

@SuppressLint("NewApi")
public class MainTainIrrigationfarmarcropInfoFragment extends Fragment
		implements OnClickListener, OnItemClickListener {

	private LayoutInflater mInflater;
	private MainActionBars actionBar;
	private MyGridView gridView;
//	private PopupWindow popupWindow;
	ImageAdapter adapter;
	private List<String> infoBeans,list;
	private Button button;
	Button btn_image_cancel, btn_image_choose;
	private RelativeLayout layout_irriagte_group;
	private ProgressDialog progressDialog;
	private List<infoList> beens;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflater = inflater;
		View view = inflater.inflate(
				R.layout.fragment_maintain_irrigation_info, container, false);
		actionBar = (MainActionBars) view
				.findViewById(R.id.actionbar_main_irrigate_info);
		actionBar.setLeftIcon(R.drawable.btn_back_selector);
		actionBar.setRightText("下一步");
		actionBar.setTitle("种植作物信息维护");
		infoBeans = new ArrayList<String>();
		
		layout_irriagte_group = (RelativeLayout) view
				.findViewById(R.id.text_maintain_irrigat_info_round_of_irrigation_group);
		layout_irriagte_group.setVisibility(View.GONE);
		
		button = (Button) view.findViewById(R.id.btn_main_irrigate_info_group);
		button.setVisibility(View.GONE);
		
		btn_image_cancel = (Button) view
				.findViewById(R.id.btn_main_irrigate_info_allcanel);
		btn_image_choose = (Button) view
				.findViewById(R.id.btn_main_irrigate_info_all);
		
		btn_image_cancel.setOnClickListener(this);
		btn_image_choose.setOnClickListener(this);
		
//		for (int i = 0; i < 30; i++) {
//			MainTainIrrigationInfoBean bean = new MainTainIrrigationInfoBean();
//			bean.setGate("1-2");
//			beans.add(bean);
//		}
		gridView = (MyGridView) view
				.findViewById(R.id.grid_maintain_irrigate_infos);
		gridView.setVerticalSpacing(5);
		gridView.setPadding(10, 10, 5, 10);
		gridView.setOnItemClickListener(this);
		// beans = adapter.getData();
		actionBar.setActionBarOnClickListener(this);
		init();
		return view;
	}

	private void init() {
		String str1 = "";
		String str2 = "";
		try {
			str1 = java.net.URLEncoder.encode("SB001001","UTF-8");
			str2 = java.net.URLEncoder.encode("2","UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		RequestParams param3 = new RequestParams(URL.findIrriUnitChan+str1+"/"+str2);  // 网址(请替换成实际的网址) 
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
	                  MainTainIrrigationInfoBean fromJson = gson.fromJson(arg0, MainTainIrrigationInfoBean.class);
//	                  authorizedBeen = new AuthorizedBeen();
//	                  authorizedBeen = gson.fromJson(arg0, AuthorizedBeen.class);
	                  beens = fromJson.getAuthNameList();
	                  for (infoList authNameListBean : beens) {
	                	authNameListBean.getChanNum();
					}
	                adapter = new ImageAdapter(getActivity(), true, beens);
	          		gridView.setAdapter(adapter);
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
			MainTainBasicCompileFragment fragment = new MainTainBasicCompileFragment();
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			transaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.replace(R.id.fragment_maintain_present_irrigate,
					fragment, "main");
			transaction.commit();
			break;
		case R.id.acitionbar_right:
			int area = 0;
			list = new ArrayList<String>();
			for (int i = 0; i < beens.size(); i++) {
				if (!CheckUtil.IsEmpty(beens.get(i).getIstrue())) {
					if (beens.get(i).getIstrue() == true) {
						infoBeans.add(beens.get(i).getChanNum());
						area += Float.valueOf(beens.get(i).getArea());
						list.add(beens.get(i).getValueControlChanID());
					}
				}
			}
			if (CheckUtil.IsEmpty(infoBeans)) {
				Toast.makeText(getActivity(), "并未选中任何阀门",
						Toast.LENGTH_SHORT).show();
			} else {
				MainTainIntoCropsFragment fragment1 = new MainTainIntoCropsFragment();
				Bundle bundle = new Bundle();
				bundle.putStringArrayList("info",
						(ArrayList<String>) infoBeans);
				bundle.putStringArrayList("list",
						(ArrayList<String>) list);
				bundle.putInt("area", area);
				fragment1.setArguments(bundle);
				transaction
						.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
				transaction.replace(
						R.id.fragment_maintain_present_irrigate,
						fragment1, "main");
				transaction.commit();
			}
//			if (popupWindow == null) {
//				View view = mInflater.inflate(
//						R.layout.layout_irrigation_user_popu, null);
//				popupWindow = new PopupWindow(view,
//						ViewGroup.LayoutParams.WRAP_CONTENT,
//						ViewGroup.LayoutParams.WRAP_CONTENT);
//				popupWindow.setFocusable(true);
//				WindowManager.LayoutParams params = getActivity().getWindow()
//						.getAttributes();
//				params.alpha = 0.7f;
//				getActivity().getWindow().setAttributes(params);
//				popupWindow.setOnDismissListener(new OnDismissListener() {
//
//					@Override
//					public void onDismiss() {
//						popupWindow.dismiss();
//						popupWindow = null;
//						WindowManager.LayoutParams params = getActivity()
//								.getWindow().getAttributes();
//						params.alpha = 1f;
//						getActivity().getWindow().setAttributes(params);
//					}
//				});
//				popupWindow.setFocusable(true);
//				popupWindow.setBackgroundDrawable(new ColorDrawable());
//				view.findViewById(R.id.text_irrigation_info_save)
//						.setOnClickListener(changlistener);
//				view.findViewById(R.id.text_irrigation_info_group)
//						.setOnClickListener(changlistener);
//			}
//			// popupWindow.showAtLocation(getActivity().findViewById(R.id.setting),
//			popupWindow.showAsDropDown(actionBar);
			break;
		case R.id.btn_main_irrigate_info_all:
			adapter.changeAllState(true);
			break;
		case R.id.btn_main_irrigate_info_allcanel:
			adapter.changeAllState(false);
			break;
		}
	}

//	private View.OnClickListener changlistener = new View.OnClickListener() {
//
//		@Override
//		public void onClick(View v) {
//			FragmentManager fgManager = getFragmentManager();
//			FragmentTransaction transaction = fgManager.beginTransaction();
//			switch (v.getId()) {
//			case R.id.text_irrigation_info_save:// 下一步
//				for (int i = 0; i < beans.size(); i++) {
//					if (!CheckUtil.IsEmpty(beans.get(i).getIstrue())) {
//						if (beans.get(i).getIstrue() == true) {
//							infoBeans.add(beans.get(i).getGate());
//						}
//					}
//				}
//				if (CheckUtil.IsEmpty(infoBeans)) {
//					Toast.makeText(getActivity(), "并未选中任何阀门",
//							Toast.LENGTH_SHORT).show();
//				} else {
//					MainTainIntoCropsFragment fragment = new MainTainIntoCropsFragment();
//					Bundle bundle = new Bundle();
//					bundle.putStringArrayList("info",
//							(ArrayList<String>) infoBeans);
//					fragment.setArguments(bundle);
//					transaction
//							.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//					transaction.replace(
//							R.id.fragment_maintain_present_irrigate,
//							fragment, "main");
//					transaction.commit();
//				}
//				popupWindow.dismiss();
//				break;
//			case R.id.text_irrigation_info_group:// 重置
//				new AlertDialog.Builder(getActivity())
//						.setTitle("系统提示")
//						// 设置对话框标题
//
//						.setMessage("重置所有信息将清除已录入的种植作物信息！您确认重置吗？")
//						// 设置显示的内容
//
//						.setPositiveButton("确定",
//								new DialogInterface.OnClickListener() {// 添加确定按钮
//
//									@Override
//									public void onClick(DialogInterface dialog,
//											int which) {// 确定按钮的响应事件
//
//										// TODO Auto-generated method stub
//										Toast.makeText(getActivity(), "重置成功",
//												Toast.LENGTH_SHORT).show();
//										dialog.dismiss();
//										popupWindow.dismiss();
//									}
//
//								})
//						.setNegativeButton("返回",
//								new DialogInterface.OnClickListener() {// 添加返回按钮
//
//									@Override
//									public void onClick(DialogInterface dialog,
//											int which) {// 响应事件
//
//										// TODO Auto-generated method stub
//
//										dialog.dismiss();
//										popupWindow.dismiss();
//									}
//
//								}).show();// 在按键响应事件中显示此对话框
//				break;
//			}
//		}
//	};

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		adapter.changeState(position);
	}
}