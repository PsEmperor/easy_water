package ps.emperor.easy_water.fragment;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.EditText;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.view.MainActionBar;

/**
 * 意见与反馈
 * @author 毛国江
 * @version 2016-5-18 下午15:56
 */

@SuppressLint("NewApi")
public class MineCommentsFragmt extends Fragment implements OnClickListener {
	private MainActionBar actionBar;
	private LayoutInflater mInflater;
	private EditText ed_comments;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mInflater = inflater;
		View view = inflater.inflate(R.layout.fragment_mine_comments, container, false);

		actionBar = (MainActionBar) view.findViewById(R.id.actionbar_mine_comments);
		actionBar.setLeftIcon(R.drawable.btn_back_selector);;
		actionBar.setRightGone();
		actionBar.setTitle("意见与反馈");
		actionBar.setActionBarOnClickListener(this);
		return view;
	}
	@Override
	public void onClick(View v) {
		FragmentManager fgManager = getFragmentManager();
		FragmentTransaction transaction = fgManager.beginTransaction();
		switch (v.getId()) {
		case R.id.acitionbar_left:
			MinesFragment fragment = new MinesFragment();
//			transaction.setCustomAnimations(R.anim.right_in, R.anim.right_out);
			transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.replace(R.id.fl, fragment, "main");
			transaction.commit();
			break;

		default:
			break;
		}
	}
}
