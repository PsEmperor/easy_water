package ps.emperor.easy_water.fragment;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
import android.widget.TextView;
import android.widget.Toast;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.view.MainActionBar;

/**
 * 帮助
 * 
 * @author 毛国江
 * @version 2016-5-18 下午15:25
 */
@SuppressLint("NewApi")
public class MineHelpFragment extends Fragment implements OnClickListener {
	private MainActionBar actionBar;
	private LayoutInflater mInflater;
	private TextView tel;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mInflater = inflater;
		View view = inflater.inflate(R.layout.fragment_mine_help, container, false);

		actionBar = (MainActionBar) view.findViewById(R.id.actionbar_mine_help);
		actionBar.setLeftIcon(R.drawable.btn_back_selector);
		;
		actionBar.setRightGone();
		actionBar.setTitle("客服与帮助");
		actionBar.setActionBarOnClickListener(this);

		tel = (TextView) view.findViewById(R.id.text_help_tel);

		String str = "13641052885";
		SpannableStringBuilder style = new SpannableStringBuilder(str);
		// str代表要显示的全部字符串
		ClickableSpan what = new ClickableSpan() {

			@Override
			public void onClick(View widget) {
				Toast.makeText(getActivity(), "666666666666", Toast.LENGTH_LONG).show();
			}
		};
		style.setSpan(what, 0, 11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		tel.setText(style);
		tel.setMovementMethod(LinkMovementMethod.getInstance());
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
