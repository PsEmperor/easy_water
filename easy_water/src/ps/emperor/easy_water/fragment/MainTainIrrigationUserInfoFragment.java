package ps.emperor.easy_water.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
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
import ps.emperor.easy_water.utils.CheckUtil;
import ps.emperor.easy_water.utils.SharedUtils;
import ps.emperor.easy_water.view.MainActionBar;
import ps.emperor.easy_water.view.MainActionBars;
import ps.emperor.easy_water.view.MyGridView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.PopupWindow.OnDismissListener;

/**
 * 种植户信息维护（应用）
 * 
 * @author 毛国江
 * @version 2016-7-15 下午16:20
 */

@SuppressLint("NewApi")
public class MainTainIrrigationUserInfoFragment extends Fragment implements
		OnClickListener, OnItemClickListener {

	private LayoutInflater mInflater;
	private MainActionBars actionBar;
	private MyGridView gridView;
//	private PopupWindow popupWindow;
	Button btn_image_cancel, btn_image_choose;
	ImageAdapter adapter;
	private List<String> infoBeans;
	private Vector<MainTainIrrigationInfoBean> beans = new Vector<MainTainIrrigationInfoBean>();
	private RelativeLayout layout_irriagte_group;//当前轮灌组复用隐藏
	private Button btn_main_irrigate_info_group;//重设轮灌组隐藏
	
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
		actionBar.setTitle("种植户信息维护");
		
		layout_irriagte_group = (RelativeLayout) view.findViewById(R.id.text_maintain_irrigat_info_round_of_irrigation_group);
		layout_irriagte_group.setVisibility(View.GONE);
		
		btn_main_irrigate_info_group = (Button) view.findViewById(R.id.btn_main_irrigate_info_group);
		btn_main_irrigate_info_group.setVisibility(View.GONE);
		
		infoBeans = new ArrayList<String>();
		for (int i = 0; i < 364; i++) {
			MainTainIrrigationInfoBean bean = new MainTainIrrigationInfoBean();
			bean.setGate("1-2");
			beans.add(bean);
		}
		gridView = (MyGridView) view
				.findViewById(R.id.grid_maintain_irrigate_infos);
		btn_image_cancel = (Button) view
				.findViewById(R.id.btn_main_irrigate_info_allcanel);
		btn_image_choose = (Button) view
				.findViewById(R.id.btn_main_irrigate_info_all);
		adapter = new ImageAdapter(getActivity(), true, beans);
		gridView.setAdapter(adapter);
		gridView.setVerticalSpacing(5);
		gridView.setPadding(10, 10, 5, 10);
		gridView.setOnItemClickListener(this);
		btn_image_cancel.setOnClickListener(this);
		btn_image_choose.setOnClickListener(this);
		// beans = adapter.getData();
		actionBar.setActionBarOnClickListener(this);
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
		case R.id.btn_main_irrigate_info_all:
			adapter.changeAllState(true);
			// value.removeAll(value);
			// for (int i = 0; i < beans.size(); i++) {
			// list[i] = i;
			// }
			break;
		case R.id.btn_main_irrigate_info_allcanel:
			adapter.changeAllState(false);
			break;
		case R.id.acitionbar_right:
			for (int i = 0; i < beans.size(); i++) {
				if (!CheckUtil.IsEmpty(beans.get(i).getIstrue())) {
					if (beans.get(i).getIstrue() == true) {
						infoBeans.add(beans.get(i).getGate());
					}
				}
			}
			if (CheckUtil.IsEmpty(infoBeans)) {
				Toast.makeText(getActivity(), "并未选中任何阀门",
						Toast.LENGTH_SHORT).show();
			} else {
				MainTainIntoUserFragment fragment1 = new MainTainIntoUserFragment();
				Bundle bundle = new Bundle();
				bundle.putStringArrayList("info",
						(ArrayList<String>) infoBeans);
				fragment1.setArguments(bundle);
				transaction
						.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
				transaction.replace(
						R.id.fragment_maintain_present_irrigate, fragment1,
						"main");
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
//			break;
		}
	}

//	private View.OnClickListener changlistener = new View.OnClickListener() {
//
//		@Override
//		public void onClick(View v) {
//			FragmentManager fgManager = getFragmentManager();
//			FragmentTransaction transaction = fgManager.beginTransaction();
//			switch (v.getId()) {
//			case R.id.text_irrigation_info_save:// 录入
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
//					MainTainIntoUserFragment fragment = new MainTainIntoUserFragment();
//					Bundle bundle = new Bundle();
//					bundle.putStringArrayList("info",
//							(ArrayList<String>) infoBeans);
//					fragment.setArguments(bundle);
//					transaction
//							.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//					transaction.replace(
//							R.id.fragment_maintain_present_irrigate, fragment,
//							"main");
//					transaction.commit();
//				}
//				popupWindow.dismiss();
//				break;
//			case R.id.text_irrigation_info_group:// 重置
//				new AlertDialog.Builder(getActivity())
//						.setTitle("系统提示")
//						// 设置对话框标题
//
//						.setMessage("重置所有信息将清除已录入的种植户信息！您确认重置吗？")
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
		// SharedUtils.setParam(getActivity(), "123", position);
		// if (beans.get(position).getIstrue() == true) {
		// list[position] = position;// 当前选中的下标
		// String a = beans.get(position).getGate();
		// infoBeans[position]=a;
		// Log.i("test", list[position] + "");
		// } else if (beans.get(position).getIstrue() == false) {
		// list[position] = 100;// 未选中状态分配00
		// String a = beans.get(position).getGate();
		// infoBeans.remove(a);
		// Log.i("tests", list[position] + "");
	}
}