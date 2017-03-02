package ps.emperor.easy_water.fragment;

import java.util.ArrayList;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.activity.MineUserRoleChooseActivity;
import ps.emperor.easy_water.adapter.RoleAdapter;
import ps.emperor.easy_water.entity.RoleBean;
import ps.emperor.easy_water.entity.RoleBean;
import ps.emperor.easy_water.utils.SharedUtils;
import ps.emperor.easy_water.view.MainActionBar;

/**
 * 用户角色
 * 
 * @author 毛国江
 * @version 2016-5-17 下午14：10
 */
@SuppressLint("NewApi")
public class MineUserRoleFragment extends Fragment implements OnClickListener,OnItemClickListener {

	private LayoutInflater mInflater;
	private MainActionBar actionBar;
	private TextView role;
	private ListView listView;
	private List<RoleBean> RoleBeans;
	private RoleAdapter adapter;
	private int tag;
	RoleBean bean;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflater = inflater;
		View view = inflater.inflate(R.layout.fragment_mine_user_role,
				container, false);
		role = (TextView) view.findViewById(R.id.administrator);

		actionBar = (MainActionBar) view.findViewById(R.id.actionbar_user_role);
		actionBar.setLeftIcon(R.drawable.btn_back_selector);
		actionBar.setRightGone();
		actionBar.setTitle("用户角色申请");
		actionBar.setActionBarOnClickListener(this);

		String str = "提示：您所申请的用户角色需要经授权单位管理员审批通过后方可生效，在此之前您无法进行任何设备操作，如有问题请与客服联系。";
		SpannableStringBuilder style = new SpannableStringBuilder(str);
		// str代表要显示的全部字符串
		ClickableSpan what = new ClickableSpan() {

			@Override
			public void onClick(View widget) {
				Toast.makeText(getActivity(), "666666666666", Toast.LENGTH_LONG)
						.show();
			}
		};
		style.setSpan(what, 54, 58, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		role.setText(style);
		role.setMovementMethod(LinkMovementMethod.getInstance());

		listView = (ListView) view.findViewById(R.id.list_user_role);

		RoleBeans = new ArrayList<RoleBean>();
		 
		bean = new RoleBean();
		bean.setRole("灌溉管理员");
		bean.setMsg("灌溉管理员是管理灌溉单元信息的角色 可以查看并操作有关灌溉的所有数据！");
		RoleBeans.add(bean);
		bean = new RoleBean();
		bean.setRole("配水管理员");
		bean.setMsg("配水管理员是管理配水设备信息的角色 可以查看并操作有关配水的所有数据！");
		RoleBeans.add(bean);
		bean = new RoleBean();
		bean.setRole("综合管理员");
		bean.setMsg("综合管理员是灌溉管理员与配水管理员的结合 可以查看并操作有关灌溉和配水的所有数据！");
		RoleBeans.add(bean);
		bean = new RoleBean();
		bean.setRole("种植户");
		bean.setMsg("种植户是仅可以进行查询并修改有关自身的灌溉信息和配水信息的角色！");
		RoleBeans.add(bean);
		bean = new RoleBean();
		bean.setRole("高级管理员");
		bean.setMsg("高级管理员是该系统中最高等级的管理员 可以查看并操作所有的数据！");
		RoleBeans.add(bean);
		bean = new RoleBean();
		bean.setRole("安装调试员");
		bean.setMsg("安装调试员是管理设备配置信息的角色 可以查看并操作有关设备配置的所有数据！");
		RoleBeans.add(bean);
		
		adapter = new RoleAdapter(getActivity());
		adapter.addData(RoleBeans, true);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
		return view;

	}

	private void PickNum() {
		int position = listView.getCheckedItemPosition();
//		if (ListView.INVALID_POSITION != position) {
//			Toast.makeText(getActivity(), position + "", 0).show();
//		}
		FragmentManager fgManager = getFragmentManager();
		FragmentTransaction transaction = fgManager.beginTransaction();
		if (position == 0) {
			Intent intent = new Intent(getActivity(),MineUserRoleChooseActivity.class);
			startActivity(intent);
			SharedUtils.setParam(getActivity(), "tag", tag);
		} else if (position == 1) {
			tag = 1;
			Intent intent = new Intent(getActivity(),MineUserRoleChooseActivity.class);
			startActivity(intent);
			SharedUtils.setParam(getActivity(), "tag", tag);
		} else if (position == 2) {
			Intent intent = new Intent(getActivity(),MineUserRoleChooseActivity.class);
			startActivity(intent);
			SharedUtils.setParam(getActivity(), "tag", tag);
		} else if (position == 3) {
			Intent intent = new Intent(getActivity(),MineUserRoleChooseActivity.class);
			startActivity(intent);
			SharedUtils.setParam(getActivity(), "tag", tag);
		} else if (position == 4) {
			Intent intent = new Intent(getActivity(),MineUserRoleChooseActivity.class);
			startActivity(intent);
			SharedUtils.setParam(getActivity(), "tag", tag);
		} else if (position == 5) {
			Intent intent = new Intent(getActivity(),MineUserRoleChooseActivity.class);
			startActivity(intent);
			SharedUtils.setParam(getActivity(), "tag", tag);
		} 
	}

	@Override
	public void onClick(View v) {
		FragmentManager fgManager = getFragmentManager();
		FragmentTransaction transaction = fgManager.beginTransaction();
		switch (v.getId()) {
		case R.id.acitionbar_left:
//			MineUserInfoFragment fragment = new MineUserInfoFragment();
//			// transaction.setCustomAnimations(R.anim.right_in,
//			// R.anim.right_out);
//			transaction.setCustomAnimations(R.anim.slide_fragment_horizontal_right_in, R.anim.slide_fragment_horizontal_left_out);
//			transaction.replace(R.id.fl, fragment, "main");
//			transaction.commit();
			fgManager.popBackStack();
			break;
		case R.id.acitionbar_right:
			PickNum();
			break;
		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		PickNum();
	}

}
