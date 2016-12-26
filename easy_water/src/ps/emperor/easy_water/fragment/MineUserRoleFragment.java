package ps.emperor.easy_water.fragment;

import java.util.ArrayList;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.utils.SharedUtils;
import ps.emperor.easy_water.view.MainActionBar;

/**
 * 用户角色
 * 
 * @author 毛国江
 * @version 2016-5-17 下午14：10
 */
@SuppressLint("NewApi")
public class MineUserRoleFragment extends Fragment implements OnClickListener {

	private LayoutInflater mInflater;
	private MainActionBar actionBar;
	private TextView role;
	private ListView listView;
	private List<String> groups;
	private int tag;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflater = inflater;
		View view = inflater.inflate(R.layout.fragment_mine_user_role,
				container, false);
		role = (TextView) view.findViewById(R.id.administrator);

		actionBar = (MainActionBar) view.findViewById(R.id.actionbar_user_role);
		actionBar.setLeftIcon(R.drawable.btn_back_selector);
		actionBar.setRightIcon(R.drawable.ic_launcher);
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

		groups = new ArrayList<String>();

		groups.add("高级管理员");
		groups.add("综合管理员");
		groups.add("灌溉管理员");
		groups.add("配水管理员");
		groups.add("安装调试员");
		groups.add("虚拟测试");
		groups.add("种植户");

		listView.setAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_single_choice, groups));
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

		} else if (position == 1) {
			tag = 1;
//			MineIrrigationAddChangeFragment fragment1 = new MineIrrigationAddChangeFragment();
//			// transaction.setCustomAnimations(R.anim.right_in,
//			// R.anim.right_out);
//			transaction
//					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//			transaction.replace(R.id.fl, fragment1, "main");
//			transaction.commit();
			SharedUtils.setParam(getActivity(), "tag", tag);
		} else if (position == 2) {
//			MineIrrigationAddChangeFragment fragment1 = new MineIrrigationAddChangeFragment();
//			// transaction.setCustomAnimations(R.anim.right_in,
//			// R.anim.right_out);
//			transaction
//					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//			transaction.replace(R.id.fl, fragment1, "main");
//			transaction.commit();
		} else if (position == 3) {
//			MineWaterAddChangeFragment fragment1 = new MineWaterAddChangeFragment();
//			// transaction.setCustomAnimations(R.anim.right_in,
//			// R.anim.right_out);
//			transaction
//					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//			transaction.replace(R.id.fl, fragment1, "main");
//			transaction.commit();
		} else if (position == 4) {

		} else if (position == 5) {

		} else if (position == 6) {

		}
	}

	@Override
	public void onClick(View v) {
		FragmentManager fgManager = getFragmentManager();
		FragmentTransaction transaction = fgManager.beginTransaction();
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
		case R.id.acitionbar_right:
			PickNum();
			break;
		default:
			break;
		}
	}

}
