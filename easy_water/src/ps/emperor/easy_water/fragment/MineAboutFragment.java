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
 * 关于
 * @author 毛国江
 * @version 2016-5-19 上午11:10
 */
@SuppressLint("NewApi")
public class MineAboutFragment extends Fragment implements OnClickListener{
	private LayoutInflater mInflater;
	private MainActionBar actionBar;
	private TextView Service,secrecy;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mInflater = inflater; 
		View view = inflater.inflate(R.layout.fragment_mine_about, container, false);
		Service = (TextView) view.findViewById(R.id.tv_about_service);
		secrecy = (TextView) view.findViewById(R.id.tv_about_secrecy);
		String str = "服务条款";
		String str1 = "保密协议";
		SpannableStringBuilder style = new SpannableStringBuilder(str);
		// str代表要显示的全部字符串
		ClickableSpan what = new ClickableSpan() {

			@Override
			public void onClick(View widget) {
				Toast.makeText(getActivity(), "666666666666", Toast.LENGTH_LONG).show();
			}
		};
		style.setSpan(what, 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		SpannableStringBuilder style1 = new SpannableStringBuilder(str1);
		// str代表要显示的全部字符串
		ClickableSpan what1 = new ClickableSpan() {

			@Override
			public void onClick(View widget) {
				Toast.makeText(getActivity(), "233333333333", Toast.LENGTH_LONG).show();
			}
		};
		style.setSpan(what, 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		style1.setSpan(what1, 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		Service.setText(style);
		Service.setMovementMethod(LinkMovementMethod.getInstance());
		secrecy.setText(style1);
		secrecy.setMovementMethod(LinkMovementMethod.getInstance());
		actionBar = (MainActionBar) view.findViewById(R.id.actionbar_mine_about);
		actionBar.setLeftIcon(R.drawable.btn_back_selector);
		actionBar.setRightGone();
		actionBar.setTitle("关于");
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
			transaction.setCustomAnimations(R.anim.slide_fragment_horizontal_right_in, R.anim.slide_fragment_horizontal_left_out);
			transaction.replace(R.id.fl, fragment, "main");
			transaction.commit();
			break;

		default:
			break;
		}
	}
}
