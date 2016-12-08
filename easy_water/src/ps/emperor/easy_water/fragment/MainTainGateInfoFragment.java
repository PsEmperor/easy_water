package ps.emperor.easy_water.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.view.MainActionBar;
import ps.emperor.easy_water.view.MainActionBars;

/**
 * 闸门信息（维护）
 * 
 * @author 毛国江
 * @version 2016-6-7 上午10:27
 */

@SuppressLint("NewApi")
public class MainTainGateInfoFragment extends Fragment implements
		OnClickListener {

	private LayoutInflater mInflater;
	private MainActionBars actionBar;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflater = inflater;
		View view = inflater.inflate(R.layout.fragment_maintain_gate_info,
				container, false);
		actionBar = (MainActionBars) view
				.findViewById(R.id.actionbar_maintain_gate_info);
		actionBar.setLeftIcon(R.drawable.btn_back_selector);
		actionBar.setRightText("保存");
		actionBar.setTitle("闸门信息维护");
		actionBar.setActionBarOnClickListener(this);

		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.acitionbar_left:
			getActivity().finish();
			break;
		default:
			break;
		}
	}

}
