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
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.adapter.ImageAdapter;
import ps.emperor.easy_water.adapter.ImageAdapterCrop;
import ps.emperor.easy_water.entity.MainTainIrrigationInfoBean;
import ps.emperor.easy_water.entity.MainTainIrrigationInfoBean.infoList;
import ps.emperor.easy_water.utils.CheckUtil;
import ps.emperor.easy_water.utils.DensityUtil;
import ps.emperor.easy_water.utils.NetStatusUtil;
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
	// private PopupWindow popupWindow;
	ImageAdapterCrop adapter;
	private List<String> infoBeans, list;
	private Button button;
	Button btn_image_cancel, btn_image_choose;
	private RelativeLayout layout_irriagte_group;
	private ProgressDialog progressDialog;
	private List<infoList> beens;
	private int maxNum;
	private FrameLayout frameLayout_gridtableLayout;
	private LinearLayout linearLayout_gridtableLayout;
	

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
		linearLayout_gridtableLayout = (LinearLayout) view.findViewById(R.id.linearLayout_gridtableLayout);

		button = (Button) view.findViewById(R.id.btn_main_irrigate_info_group);
		button.setVisibility(View.GONE);

		btn_image_cancel = (Button) view
				.findViewById(R.id.btn_main_irrigate_info_allcanel);
		btn_image_choose = (Button) view
				.findViewById(R.id.btn_main_irrigate_info_all);

		btn_image_cancel.setOnClickListener(this);
		btn_image_choose.setOnClickListener(this);

		// for (int i = 0; i < 30; i++) {
		// MainTainIrrigationInfoBean bean = new MainTainIrrigationInfoBean();
		// bean.setGate("1-2");
		// beans.add(bean);
		// }
		frameLayout_gridtableLayout = (FrameLayout) view
				.findViewById(R.id.frameLayout_gridtableLayout);
		gridView = (MyGridView) view
				.findViewById(R.id.grid_maintain_irrigate_infos);
		gridView.setVerticalSpacing(5);
		gridView.setPadding(10, 10, 5, 10);
		gridView.setOnItemClickListener(this);
		// beans = adapter.getData();
		actionBar.setActionBarOnClickListener(this);

		if (NetStatusUtil.isNetValid(getActivity())) {
			init();
		} else {
			Toast.makeText(getActivity(), "当前网络不可用！请检查您的网络状态！", Toast.LENGTH_SHORT)
					.show();
		}

		return view;
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
		String str2 = "";
		try {
			str1 = java.net.URLEncoder.encode(str1, "UTF-8");
			str2 = java.net.URLEncoder.encode("2", "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		RequestParams param3 = new RequestParams(URL.findIrriUnitChan + str1
				+ "/" + str2); // 网址(请替换成实际的网址)
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
					Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT);
					// ...
					progressDialog.dismiss();
				} else { // 其他错误 
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
				Gson gson = new Gson();
				MainTainIrrigationInfoBean fromJson = gson.fromJson(arg0,
						MainTainIrrigationInfoBean.class);
				beens = fromJson.getAuthNameList();
				if (!CheckUtil.IsEmpty(beens)) {
					int[] array = new int[beens.size()];
					for (int i = 0; i < array.length; i++) {
						array[i] = Integer.valueOf(beens.get(i).getTotalChanNum());
					}
					int temp;
					for (int i = 0; i < array.length; i++) {
						for (int j = i + 1; j < array.length; j++) {
							if (array[i] < array[j]) {
								temp = array[i];
								array[i] = array[j];
								array[j] = temp; // 两个数交换位置
							}
						}
					}
					for (int i = 0; i < beens.size(); i += array[0]) {
						if (Integer.valueOf(beens.get(i).getTotalChanNum()) < array[0]) {
							for (int j = 0; j < array[0]
									- Integer.valueOf(beens.get(i)
											.getTotalChanNum()); j++) {
								infoList infoList = new infoList();
								infoList.setChanNum("");
								infoList.setGroupName("");
								infoList.setTotalChanNum(beens.get(i)
										.getTotalChanNum());
								beens.add(
										i
												+ Integer.valueOf(beens.get(i)
														.getTotalChanNum()),
										infoList);
							}
						}
					}
					adapter = new ImageAdapterCrop(getActivity(), true, beens);
					if (array[0] == 5) {
						FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) frameLayout_gridtableLayout
								.getLayoutParams();
						int weight = DensityUtil.dip2px(getActivity(), 360);
						layoutParams.width = weight;
						linearLayout_gridtableLayout.setLayoutParams(layoutParams);
						linearLayout_gridtableLayout.requestLayout();
						gridView.setLayoutParams(layoutParams);
					}else if(array[0] == 1){
						FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) frameLayout_gridtableLayout
								.getLayoutParams();
						int weight = DensityUtil.dip2px(getActivity(), 72);
						layoutParams.width = weight;
						linearLayout_gridtableLayout.setLayoutParams(layoutParams);
						linearLayout_gridtableLayout.requestLayout();
						gridView.setLayoutParams(layoutParams);
					}else if(array[0] == 2){
						FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) frameLayout_gridtableLayout
								.getLayoutParams();
						int weight = DensityUtil.dip2px(getActivity(), 144);
						layoutParams.width = weight;
						linearLayout_gridtableLayout.setLayoutParams(layoutParams);
						linearLayout_gridtableLayout.requestLayout();
						gridView.setLayoutParams(layoutParams);
					}else if(array[0] == 3){
						FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) frameLayout_gridtableLayout
								.getLayoutParams();
						int weight = DensityUtil.dip2px(getActivity(), 216);
						layoutParams.width = weight;
						linearLayout_gridtableLayout.setLayoutParams(layoutParams);
						linearLayout_gridtableLayout.requestLayout();
						gridView.setLayoutParams(layoutParams);
					}else if(array[0] == 4){
						FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) frameLayout_gridtableLayout
								.getLayoutParams();
						int weight = DensityUtil.dip2px(getActivity(), 288);
						layoutParams.width = weight;
						linearLayout_gridtableLayout.setLayoutParams(layoutParams);
						linearLayout_gridtableLayout.requestLayout();
						gridView.setLayoutParams(layoutParams);
					}else if(array[0] == 6){
						FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) frameLayout_gridtableLayout
								.getLayoutParams();
						int weight = DensityUtil.dip2px(getActivity(), 432);
						layoutParams.width = weight;
						linearLayout_gridtableLayout.setLayoutParams(layoutParams);
						linearLayout_gridtableLayout.requestLayout();
						gridView.setLayoutParams(layoutParams);
					}else if(array[0] == 7){
						FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) frameLayout_gridtableLayout
								.getLayoutParams();
						int weight = DensityUtil.dip2px(getActivity(), 504);
						layoutParams.width = weight;
						linearLayout_gridtableLayout.setLayoutParams(layoutParams);
						linearLayout_gridtableLayout.requestLayout();
						gridView.setLayoutParams(layoutParams);
					}else if(array[0] == 8){
						FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) frameLayout_gridtableLayout
								.getLayoutParams();
						int weight = DensityUtil.dip2px(getActivity(), 576);
						layoutParams.width = weight;
						linearLayout_gridtableLayout.setLayoutParams(layoutParams);
						linearLayout_gridtableLayout.requestLayout();
						gridView.setLayoutParams(layoutParams);
					}else if(array[0] == 9){
						FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) frameLayout_gridtableLayout
								.getLayoutParams();
						int weight = DensityUtil.dip2px(getActivity(), 648);
						layoutParams.width = weight;
						linearLayout_gridtableLayout.setLayoutParams(layoutParams);
						linearLayout_gridtableLayout.requestLayout();
						gridView.setLayoutParams(layoutParams);
					}else if(array[0] == 10){
						FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) frameLayout_gridtableLayout
								.getLayoutParams();
						int weight = DensityUtil.dip2px(getActivity(), 720);
						layoutParams.width = weight;
						linearLayout_gridtableLayout.setLayoutParams(layoutParams);
						linearLayout_gridtableLayout.requestLayout();
						gridView.setLayoutParams(layoutParams);
					}else if(array[0] == 11){
						FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) frameLayout_gridtableLayout
								.getLayoutParams();
						int weight = DensityUtil.dip2px(getActivity(), 792);
						layoutParams.width = weight;
						linearLayout_gridtableLayout.setLayoutParams(layoutParams);
						linearLayout_gridtableLayout.requestLayout();
						gridView.setLayoutParams(layoutParams);
					}else if(array[0] == 12){
						FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) frameLayout_gridtableLayout
								.getLayoutParams();
						int weight = DensityUtil.dip2px(getActivity(), 864);
						layoutParams.width = weight;
						linearLayout_gridtableLayout.setLayoutParams(layoutParams);
						linearLayout_gridtableLayout.requestLayout();
						gridView.setLayoutParams(layoutParams);
					}else if(array[0] == 13){
						FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) frameLayout_gridtableLayout
								.getLayoutParams();
						int weight = DensityUtil.dip2px(getActivity(), 936);
						layoutParams.width = weight;
						linearLayout_gridtableLayout.setLayoutParams(layoutParams);
						linearLayout_gridtableLayout.requestLayout();
						gridView.setLayoutParams(layoutParams);
					}else if(array[0] == 14){
						FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) frameLayout_gridtableLayout
								.getLayoutParams();
						int weight = DensityUtil.dip2px(getActivity(), 1008);
						layoutParams.width = weight;
						linearLayout_gridtableLayout.setLayoutParams(layoutParams);
						linearLayout_gridtableLayout.requestLayout();
						gridView.setLayoutParams(layoutParams);
					}else if(array[0] == 15){
						FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) frameLayout_gridtableLayout
								.getLayoutParams();
						int weight = DensityUtil.dip2px(getActivity(), 1080);
						layoutParams.width = weight;
						linearLayout_gridtableLayout.setLayoutParams(layoutParams);
						linearLayout_gridtableLayout.requestLayout();
						gridView.setLayoutParams(layoutParams);
					}
					gridView.setNumColumns(array[0]);
					gridView.setAdapter(adapter);
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
			MainTainBasicCompileFragment fragment = new MainTainBasicCompileFragment();
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			transaction.setCustomAnimations(
					R.anim.slide_fragment_horizontal_right_in,
					R.anim.slide_fragment_horizontal_left_out);
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
				Toast.makeText(getActivity(), "并未选中任何阀门", Toast.LENGTH_SHORT)
						.show();
			} else {
				MainTainIntoCropsFragment fragment1 = new MainTainIntoCropsFragment();
				Bundle bundle = new Bundle();
				bundle.putStringArrayList("info", (ArrayList<String>) infoBeans);
				bundle.putStringArrayList("list", (ArrayList<String>) list);
				bundle.putInt("area", area);
				fragment1.setArguments(bundle);
				transaction.setCustomAnimations(
						R.anim.slide_fragment_horizontal_left_in,
						R.anim.slide_fragment_horizontal_right_out);
				transaction.replace(R.id.fragment_maintain_present_irrigate,
						fragment1, "main");
				transaction.commit();
			}
			break;
		case R.id.btn_main_irrigate_info_all:
			adapter.changeAllState(true);
			break;
		case R.id.btn_main_irrigate_info_allcanel:
			adapter.changeAllState(false);
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		adapter.changeState(position);
	}
}