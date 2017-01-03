package ps.emperor.easy_water.fragment;

import java.util.List;


import ps.emperor.easy_water.R;
import ps.emperor.easy_water.view.MainActionBar;
import ps.emperor.easy_water.view.MainActionBars;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * 单孔闸门控制
 * 
 * @author 毛国江
 * @version 2016-9-9 下午16:49
 */
public class MineUserRoleChooseFragment extends Fragment implements
		OnClickListener{
	private LayoutInflater mInflater;
	private MainActionBar actionBar;
	private Button btn_choose;
	
	
	@SuppressLint("CutPasteId")
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflater = inflater;
		View view = inflater.inflate(R.layout.fragment_mines_role_choose,
				container, false);

		actionBar = (MainActionBar) view
				.findViewById(R.id.actionbar_user_role_choose);
		actionBar.setTitle("角色申请");
		actionBar.setLeftIcon(R.drawable.btn_back_selector);
		actionBar.setRightGone();
		actionBar.setActionBarOnClickListener(this);
		
		return view;
	}
	
	@Override
	public void onResume() {
		super.onResume();
	}
	@Override
	public void onClick(View v) {
		FragmentManager fgManager = getFragmentManager();
		FragmentTransaction transaction = fgManager.beginTransaction();
		switch (v.getId()) {
		case R.id.acitionbar_left:
			MineUserRoleFragment fragment = new MineUserRoleFragment();
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			transaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.replace(R.id.fl, fragment, "main");
			transaction.commit();
			break;
		}
	}
}
