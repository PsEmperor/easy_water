package ps.emperor.easy_water.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.view.MainActionBar;
import android.view.View.OnClickListener;

/**
 * 修改手机号
 * 
 * @author 毛国江
 * @version 2016-5-16 下午16:50
 */
@SuppressLint("NewApi")
public class MineUserTelFragment extends Fragment implements OnClickListener {
	private LayoutInflater mInflater;
	private MainActionBar actionBar;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflater = inflater;
		View view = inflater.inflate(R.layout.fragment_mine_user_tel,
				container, false);
		actionBar = (MainActionBar) view.findViewById(R.id.actionbar_user_tel);
		actionBar.setLeftIcon(R.drawable.btn_back_selector);
		actionBar.setRightGone();
		actionBar.setTitle("修改手机号");
		actionBar.setActionBarOnClickListener(this);
		return view;
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
		default:
			break;
		}
	}
}
