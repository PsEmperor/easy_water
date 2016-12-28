package ps.emperor.easy_water.fragment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
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
import ps.emperor.easy_water.entity.KeyWordBean;
import ps.emperor.easy_water.view.MainActionBars;

/**
 * 授权单位
 * 
 * @author 毛国江
 * @version 2016-5-17 上午9:55
 */

@SuppressLint("NewApi")
public class MineUserUnitsFragment extends android.app.Fragment implements
		OnClickListener{
	private LayoutInflater mInflater;
	private MainActionBars actionBar;
	private TextView hint;
	private ListView listView;
	private List<String> beans;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflater = inflater;
		View view = inflater.inflate(
				R.layout.fragment_mine_user_authorized_units, container, false);
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
		actionBar = (MainActionBars) view.findViewById(R.id.actionbar_user_unit);
		actionBar.setLeftIcon(R.drawable.btn_back_selector);
		actionBar.setRightText("保存");
		actionBar.setTitle("授权单位");
		actionBar.setActionBarOnClickListener(this);
		
		hint.setText(style);
		hint.setMovementMethod(LinkMovementMethod.getInstance());
		
		listView = (ListView) view.findViewById(R.id.list_mine_user_authorized_utils);
		beans = new ArrayList<String>();
		KeyWordBean bean;
		for (int i = 0; i < 10; i++) {
			bean = new KeyWordBean();
			bean.setkeyword("第八师141团");
			beans.add(bean.getkeyword());
		}
		listView.setAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_single_choice, beans));
		
		init();
		
		
		return view;
	}

	private void init() {
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
		case R.id.acitionbar_right:
			int position = listView.getCheckedItemPosition();
	        Toast.makeText(getActivity(), position+"", Toast.LENGTH_LONG).show();
			break;
		default:
			break;
		}
	}

}
