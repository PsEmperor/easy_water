package ps.emperor.easy_water.utils;

import ps.emperor.easy_water.R;
import ps.emperor.easy_water.action.ActionFragment;
import ps.emperor.easy_water.application.fragment.ApplicationFragment;
import ps.emperor.easy_water.fragment.MinesFragment;
import ps.emperor.easy_water.homepage.HomePageFragment;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BottomNavigation extends RelativeLayout implements OnClickListener {

	//F1S2T3Fo4
	private RelativeLayout rlF, rlS, rlT, rlFo;
	private ImageView ivF, ivS, ivT, ivFo;
	private TextView tvF, tvS, tvT, tvFo;

	public BottomNavigation(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.bottom_navigation, this);
		findView();
		set();

		changeStateClick(ivF, tvF);

		changeStateNormal(ivS, tvS);
		changeStateNormal(ivT, tvT);
		changeStateNormal(ivFo, tvFo);

	}

	private void set() {
		rlF.setOnClickListener(this);
		rlS.setOnClickListener(this);
		rlT.setOnClickListener(this);
		rlFo.setOnClickListener(this);

	}

	private void findView() {
		rlF = (RelativeLayout) findViewById(R.id.rl_first);
		ivF = (ImageView) findViewById(R.id.iv_first);
		tvF = (TextView) findViewById(R.id.tv_first);
		tvF.setText(R.string.home_first);

		rlS = (RelativeLayout) findViewById(R.id.rl_second);
		ivS = (ImageView) findViewById(R.id.iv_second);
		tvS = (TextView) findViewById(R.id.tv_second);
		tvS.setText(R.string.home_second);

		rlT = (RelativeLayout) findViewById(R.id.rl_third);
		ivT = (ImageView) findViewById(R.id.iv_third);
		tvT = (TextView) findViewById(R.id.tv_third);
		tvT.setText(R.string.home_third);

		rlFo = (RelativeLayout) findViewById(R.id.rl_forth);
		ivFo = (ImageView) findViewById(R.id.iv_forth);
		tvFo = (TextView) findViewById(R.id.tv_forth);
		tvFo.setText(R.string.home_forth);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_first:

			HomePageFragment hf = new HomePageFragment();

			((Activity) getContext()).getFragmentManager().beginTransaction()
					.replace(R.id.fl, hf).addToBackStack(null).commit();

			changeStateClick(ivF, tvF);

			changeStateNormal(ivS, tvS);
			changeStateNormal(ivT, tvT);
			changeStateNormal(ivFo, tvFo);

			break;
		case R.id.rl_second:
			ApplicationFragment apf = new ApplicationFragment();
			((Activity) getContext()).getFragmentManager().beginTransaction()
					.replace(R.id.fl, apf).addToBackStack(null).commit();

			changeStateClick(ivS, tvS);
			changeStateNormal(ivF, tvF);
			changeStateNormal(ivT, tvT);
			changeStateNormal(ivFo, tvFo);
			break;
		case R.id.rl_third:
			ActionFragment af = new ActionFragment();
			((Activity) getContext()).getFragmentManager().beginTransaction()
					.replace(R.id.fl, af).addToBackStack(null).commit();

			changeStateClick(ivT, tvT);
			changeStateNormal(ivF, tvF);
			changeStateNormal(ivS, tvS);
			changeStateNormal(ivFo, tvFo);

			break;
		case R.id.rl_forth:

			MinesFragment mf = new MinesFragment();


			((Activity) getContext()).getFragmentManager().beginTransaction()
					.replace(R.id.fl, mf).addToBackStack(null).commit();

			changeStateClick(ivFo, tvFo);
			changeStateNormal(ivF, tvF);
			changeStateNormal(ivT, tvT);
			changeStateNormal(ivS, tvS);

			break;

		}

	}

	private void changeStateNormal(ImageView iv, TextView tv) {
		iv.setBackgroundResource(R.drawable.apple_black);
		tv.setTextColor(getResources()
				.getColor(android.R.color.background_dark));
	}

	private void changeStateClick(ImageView iv, TextView tv) {
		iv.setBackgroundResource(R.drawable.apple_red);
		tv.setTextColor(getResources().getColor(android.R.color.holo_blue_dark));
	}

}
