package ps.emperor.easy_water.fragment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.adapter.MineUserDistrictAdapter;
import ps.emperor.easy_water.adapter.MineUserStateAdapter;
import ps.emperor.easy_water.adapter.MineUserProvinceAdapter;
import ps.emperor.easy_water.adapter.MineUserUnitAdapter;
import ps.emperor.easy_water.entity.MineUserDistrictBean;
import ps.emperor.easy_water.entity.MineUserProvinceBean;
import ps.emperor.easy_water.entity.MineUserStateBean;
import ps.emperor.easy_water.entity.MineUserUnitBean;
import ps.emperor.easy_water.utils.CheckUtil;
import ps.emperor.easy_water.utils.SharedUtils;
import ps.emperor.easy_water.view.MainActionBar;

/**
 * 授权单位
 * 
 * @author 毛国江
 * @version 2016-5-17 上午9:55
 */

@SuppressLint("NewApi")
public class MineUserUnitFragment extends android.app.Fragment implements
		OnClickListener,OnItemClickListener{
	private LayoutInflater mInflater;
	private MainActionBar actionBar;
	private TextView hint,tvProvince, tvState, tvDistrict, tvUnits;
	private PopupWindow popupWindow;
	private List<MineUserProvinceBean> provinceBeans;
	private List<MineUserStateBean> stateBeans;
	private List<MineUserDistrictBean> districtBeans;
	private List<MineUserUnitBean> unitBeans;
	private MineUserProvinceAdapter adapter;
	private MineUserStateAdapter adapter1;
	private MineUserDistrictAdapter adapter2;
	private MineUserUnitAdapter adapter3;
	private ListView listView;
	private String province,state,district,units,shareProvince,shareState,shareDistrict,shareUnit;//保存在shard中的
	private int chose;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflater = inflater;
		View view = inflater.inflate(
				R.layout.fragment_mine_user_authorized_unit, container, false);
		hint = (TextView) view.findViewById(R.id.hint);
		String str = "提示:选择所处行政区划后自动出现管理单位列表，若授权单位不在列表中请与客服联系。";
		SpannableStringBuilder style = new SpannableStringBuilder(str);
		// str代表要显示的全部字符串
		ClickableSpan what = new ClickableSpan() {

			@Override
			public void onClick(View widget) {
				
				Intent intent=new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+"13641052895"));
				startActivity(intent);
			}
		};
		style.setSpan(what, 35, 39, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		actionBar = (MainActionBar) view.findViewById(R.id.actionbar_user_unit);
		hint.setText(style);
		hint.setMovementMethod(LinkMovementMethod.getInstance());
		actionBar.setLeftIcon(R.drawable.btn_back_selector);
		actionBar.setRightGone();
		actionBar.setTitle("授权单位");
		actionBar.setActionBarOnClickListener(this);
		
		init();
		
		tvProvince = (TextView) view.findViewById(R.id.text_mine_user_info_province);
		tvState = (TextView) view.findViewById(R.id.text_mine_user_info_state);
		tvDistrict = (TextView) view.findViewById(R.id.text_mine_user_info_district);
		tvUnits = (TextView) view.findViewById(R.id.text_mine_user_info_units);
		
		if(!CheckUtil.IsEmpty(shareProvince)){
			tvProvince.setText(shareProvince);
		}
		if(!CheckUtil.IsEmpty(shareState)){
			tvState.setText(shareState);
		}
		if(!CheckUtil.IsEmpty(shareDistrict)){
			tvDistrict.setText(shareDistrict);
		}
		if(!CheckUtil.IsEmpty(shareUnit)){
			tvUnits.setText(shareUnit);
		}
		tvProvince.setOnClickListener(this);
		tvState.setOnClickListener(this);
		tvDistrict.setOnClickListener(this);
		tvUnits.setOnClickListener(this);
		return view;
	}

	private void init() {
		adapter = new MineUserProvinceAdapter(getActivity());
		adapter1 = new MineUserStateAdapter(getActivity());
		adapter2 = new MineUserDistrictAdapter(getActivity());
		adapter3 = new MineUserUnitAdapter(getActivity());
		
		shareProvince = (String) SharedUtils.getParam(getActivity(), "shareProvince", "无数据");
		shareState = (String) SharedUtils.getParam(getActivity(), "shareState", "无数据");
		shareDistrict = (String) SharedUtils.getParam(getActivity(), "shareDistrict", "无数据");
		shareUnit = (String) SharedUtils.getParam(getActivity(), "shareUnit", "无数据");
	}

	@Override
	public void onClick(View v) {
		android.app.FragmentManager fgManager = getFragmentManager();
		android.app.FragmentTransaction transaction = fgManager
				.beginTransaction();
		switch (v.getId()) {
		case R.id.acitionbar_left:
			MineUserInfoFragment fragment = new MineUserInfoFragment();
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			transaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.replace(R.id.fl, fragment, "main");
			transaction.commit();
			break;
		case R.id.text_mine_user_info_province:
			chose = 1;
			provinceBeans = new ArrayList<MineUserProvinceBean>();
			for (int i = 0; i < 4; i++) {
				MineUserProvinceBean bean = new MineUserProvinceBean();
				bean.setProvince("第一大队第二小队");
				provinceBeans.add(bean);
			}
			adapter.addData(provinceBeans, true);
			View view1 = mInflater.inflate(
					R.layout.layout_mine_user_units_popu, null);
			popupWindow = new PopupWindow(view1,
					ViewGroup.LayoutParams.WRAP_CONTENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			popupWindow.setFocusable(true);
			popupWindow.setBackgroundDrawable(new ColorDrawable());
			listView = (ListView) view1.findViewById(R.id.list_mine_user_units);
			listView.setAdapter(adapter);
			listView.setOnItemClickListener(this);
		// popupWindow.showAtLocation(getActivity().findViewById(R.id.setting),
		// Gravity.TOP, 0, 0);
		popupWindow.showAsDropDown(tvProvince);
		break;
		case R.id.text_mine_user_info_state:
			chose = 2;
			stateBeans = new ArrayList<MineUserStateBean>();
			for (int i = 0; i < 6; i++) {
				MineUserStateBean bean = new MineUserStateBean();
				bean.setState("第二大队第三小队");
				stateBeans.add(bean);
			}
			adapter1.addData(stateBeans, true);
				View view2 = mInflater.inflate(
						R.layout.layout_mine_user_units_popu, null);
				popupWindow = new PopupWindow(view2,
						ViewGroup.LayoutParams.WRAP_CONTENT,
						ViewGroup.LayoutParams.WRAP_CONTENT);
				popupWindow.setFocusable(true);
				popupWindow.setBackgroundDrawable(new ColorDrawable());
				listView = (ListView) view2.findViewById(R.id.list_mine_user_units);
				listView.setAdapter(adapter1);
				listView.setOnItemClickListener(this);
			// popupWindow.showAtLocation(getActivity().findViewById(R.id.setting),
			// Gravity.TOP, 0, 0);
			popupWindow.showAsDropDown(tvState);
			break;
		case R.id.text_mine_user_info_district:
			chose = 3;
			districtBeans = new ArrayList<MineUserDistrictBean>();
			for (int i = 0; i < 8; i++) {
				MineUserDistrictBean bean = new MineUserDistrictBean();
				bean.setDistrict("第三大队第四小队");
				districtBeans.add(bean);
			}
			adapter2.addData(districtBeans, true);
				View view3 = mInflater.inflate(
						R.layout.layout_mine_user_units_popu, null);
				popupWindow = new PopupWindow(view3,
						ViewGroup.LayoutParams.WRAP_CONTENT,
						ViewGroup.LayoutParams.WRAP_CONTENT);
				popupWindow.setFocusable(true);
				popupWindow.setBackgroundDrawable(new ColorDrawable());
				listView = (ListView) view3.findViewById(R.id.list_mine_user_units);
				listView.setAdapter(adapter2);
				listView.setOnItemClickListener(this);
			// popupWindow.showAtLocation(getActivity().findViewById(R.id.setting),
			// Gravity.TOP, 0, 0);
			popupWindow.showAsDropDown(tvDistrict);
			break;
		case R.id.text_mine_user_info_units:
			chose = 4;
			unitBeans = new ArrayList<MineUserUnitBean>();
			for (int i = 0; i < 11; i++) {
				MineUserUnitBean bean = new MineUserUnitBean();
				bean.setUnit("第四大队第五小队");
				unitBeans.add(bean);
			}
			adapter3.addData(unitBeans, true);
				View view4 = mInflater.inflate(
						R.layout.layout_mine_user_units_popu, null);
				popupWindow = new PopupWindow(view4,
						ViewGroup.LayoutParams.WRAP_CONTENT,
						ViewGroup.LayoutParams.WRAP_CONTENT);
				popupWindow.setFocusable(true);
				popupWindow.setBackgroundDrawable(new ColorDrawable());
				listView = (ListView) view4.findViewById(R.id.list_mine_user_units);
				listView.setAdapter(adapter3);
				listView.setOnItemClickListener(this);
			// popupWindow.showAtLocation(getActivity().findViewById(R.id.setting),
			// Gravity.TOP, 0, 0);
			popupWindow.showAsDropDown(tvUnits);
			break;
		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if(chose == 1){
			province = provinceBeans.get(position).getProvince();
			tvProvince.setText(province);
			SharedUtils.setParam(getActivity(), "shareProvince", province);
			popupWindow.dismiss();
		}
		if(chose == 2){
			state = stateBeans.get(position).getState();
			tvState.setText(state);
			SharedUtils.setParam(getActivity(), "shareState", state);
			popupWindow.dismiss();
		}
		if(chose == 3){
			district = districtBeans.get(position).getDistrict();
			tvDistrict.setText(district);
			SharedUtils.setParam(getActivity(), "shareDistrict", district);
			popupWindow.dismiss();
		}
		if(chose == 4){
			units = unitBeans.get(position).getUnit();
			tvUnits.setText(units);
			SharedUtils.setParam(getActivity(), "shareUnit", units);
			popupWindow.dismiss();
		}
	}
}
