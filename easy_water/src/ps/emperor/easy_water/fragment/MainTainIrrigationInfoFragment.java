package ps.emperor.easy_water.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.adapter.ImageAdapter;
import ps.emperor.easy_water.entity.MainTainIrrigationInfoBean;
import ps.emperor.easy_water.utils.CheckUtil;
import ps.emperor.easy_water.utils.SharedUtils;
import ps.emperor.easy_water.view.MainActionBar;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.PopupWindow.OnDismissListener;

/**
 * 轮灌组维护（应用）
 * 
 * @author 毛国江
 * @version 2016-6-7 下午13:47
 */

@SuppressLint("NewApi")
public class MainTainIrrigationInfoFragment extends Fragment implements
		OnClickListener, OnItemClickListener {

	private LayoutInflater mInflater;
	private MainActionBar actionBar;
	private GridView gridView;
//	private PopupWindow popupWindow;
	
	ImageAdapter adapter;
	private Vector<MainTainIrrigationInfoBean> beans = new Vector<MainTainIrrigationInfoBean>();
	private int list[];
	private List<String> infoBeans;
	private List<Integer> value;
	private int values;
	private RelativeLayout layout_irriagte_group;
	private Button btn_main_irrigate_info_group;
	private Button btn_image_cancel, btn_image_choose;//全选、取消 隐藏
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflater = inflater;
		View view = inflater.inflate(
				R.layout.fragment_maintain_irrigation_info, container, false);
		actionBar = (MainActionBar) view
				.findViewById(R.id.actionbar_main_irrigate_info);
		actionBar.setLeftIcon(R.drawable.btn_back_selector);
		actionBar.setRightIcon(R.drawable.ic_launcher);
		actionBar.setTitle("轮灌组维护");

		value = new ArrayList<Integer>();
		for (int i = 0; i < 6; i++) {
			MainTainIrrigationInfoBean bean = new MainTainIrrigationInfoBean();
			bean.setGate("1-1");
			beans.add(bean);
		}
		gridView = (GridView) view
				.findViewById(R.id.grid_maintain_irrigate_infos);
		
		list = new int[beans.size()];
		infoBeans = new ArrayList<String>();
		adapter = new ImageAdapter(getActivity(), true, beans);
		gridView.setAdapter(adapter);
		gridView.setVerticalSpacing(10);
		gridView.setPadding(10, 5, 5, 5);
		gridView.setOnItemClickListener(this);
	
		// beans = adapter.getData();
		actionBar.setActionBarOnClickListener(this);
		layout_irriagte_group = (RelativeLayout) view
				.findViewById(R.id.text_maintain_irrigat_info_round_of_irrigation_group);
		layout_irriagte_group.setOnClickListener(this);
		btn_image_cancel = (Button) view
				.findViewById(R.id.btn_main_irrigate_info_allcanel);
		btn_image_choose = (Button) view
				.findViewById(R.id.btn_main_irrigate_info_all);
		btn_image_cancel.setVisibility(View.GONE);
		btn_image_choose.setVisibility(View.GONE);
		return view;
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
//			MainTainIntoUserFragment fragment2 = new MainTainIntoUserFragment();
//			Bundle bundle = new Bundle();
//			values = (Integer) SharedUtils.getParam(getActivity(),
//					"values", 1);
//			for (int i = 0; i < beans.size(); i++) {
//				if (!CheckUtil.IsEmpty(beans.get(i).getIstrue())) {
//					if (beans.get(i).getIstrue() == true) {
//						value.add(i);
//					}
//				}
//			}
//			if (!CheckUtil.IsEmpty(beans)) {
//				if (value.size() > values) {
//					Toast.makeText(getActivity(), "超出最大阀门配置",
//							Toast.LENGTH_SHORT).show();
//				} else {
//					for (int i = 0; i < beans.size(); i++) {
//						if (!CheckUtil.IsEmpty(beans.get(i).getIstrue())) {
//							if (beans.get(i).getIstrue() == true) {
//								infoBeans.add(beans.get(i).getGate());
//							}
//						}
//					}
//					if (CheckUtil.IsEmpty(infoBeans)) {
//						Toast.makeText(getActivity(), "并未选中任何阀门",
//								Toast.LENGTH_SHORT).show();
//					} else {
//						bundle.putStringArrayList("info",
//								(ArrayList<String>) infoBeans);
//						fragment2.setArguments(bundle);
//						transaction
//								.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//						transaction.replace(
//								R.id.fragment_maintain_present_irrigate,
//								fragment2, "main");
//						transaction.commit();
//					}
//				}
//			}
			
//			if (popupWindow == null) {
//				View view = mInflater.inflate(
//						R.layout.layout_irrigation_info_popu, null);
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
//			// Gravity.TOP, 0, 0);
//			popupWindow.showAsDropDown(actionBar);
			
			Toast.makeText(getActivity(), "保存", Toast.LENGTH_SHORT).show();
			break;
		case R.id.text_maintain_irrigat_info_round_of_irrigation_group:
		MainTainPresentIrrigateFragment fragment1 = new MainTainPresentIrrigateFragment();
		// transaction.setCustomAnimations(R.anim.right_in,
		// R.anim.right_out);
		transaction
				.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		transaction.replace(R.id.fragment_maintain_present_irrigate,
				fragment1, "main");
		transaction.commit();
		break;
		}
	}

//	private View.OnClickListener changlistener = new View.OnClickListener() {
//
//		@Override
//		public void onClick(View v) {
//			value.removeAll(value);
//			FragmentManager fgManager = getFragmentManager();
//			FragmentTransaction transaction = fgManager.beginTransaction();
//			switch (v.getId()) {
//			case R.id.text_irrigation_info_save:// 保存
//				MainTainIntoUserFragment fragment = new MainTainIntoUserFragment();
//				Bundle bundle = new Bundle();
//				values = (Integer) SharedUtils.getParam(getActivity(),
//						"values", 1);
//				for (int i = 0; i < beans.size(); i++) {
//					if (!CheckUtil.IsEmpty(beans.get(i).getIstrue())) {
//						if (beans.get(i).getIstrue() == true) {
//							value.add(i);
//						}
//					}
//				}
//				if (!CheckUtil.IsEmpty(beans)) {
//					if (value.size() > values) {
//						Toast.makeText(getActivity(), "超出最大阀门配置",
//								Toast.LENGTH_SHORT).show();
//						popupWindow.dismiss();
//					} else {
//						for (int i = 0; i < beans.size(); i++) {
//							if (!CheckUtil.IsEmpty(beans.get(i).getIstrue())) {
//								if (beans.get(i).getIstrue() == true) {
//									infoBeans.add(beans.get(i).getGate());
//								}
//							}
//						}
//						if (CheckUtil.IsEmpty(infoBeans)) {
//							Toast.makeText(getActivity(), "并未选中任何阀门",
//									Toast.LENGTH_SHORT).show();
//						} else {
//							bundle.putStringArrayList("info",
//									(ArrayList<String>) infoBeans);
//							fragment.setArguments(bundle);
//							transaction
//									.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//							transaction.replace(
//									R.id.fragment_maintain_present_irrigate,
//									fragment, "main");
//							transaction.commit();
//						}
//						popupWindow.dismiss();
//					}
//				}
//				break;
//			case R.id.text_irrigation_info_group:// 重置编组信息
//				new AlertDialog.Builder(getActivity())
//						.setTitle("系统提示")
//						// 设置对话框标题
//
//						.setMessage("重置轮灌组信息将删除当前所有已编组的阀门信息！您确认要重置轮灌组吗？")
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
//
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
//
//			}
//		}
//	};

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		adapter.changeState(position);
		// SharedUtils.setParam(getActivity(), "123", position);
		if (beans.get(position).getIstrue() == true) {
			list[position] = position;// 当前选中的下标
			// String a = beans.get(position).getGate();
			// infoBeans[position]=a;
			Log.i("test", list[position] + "");
		} else if (beans.get(position).getIstrue() == false) {
			list[position] = 100;// 未选中状态分配00
			// String a = beans.get(position).getGate();
			// infoBeans.remove(a);
			Log.i("tests", list[position] + "");
		}
	}
}