package ps.emperor.easy_water.fragment;

import java.util.List;


import ps.emperor.easy_water.R;
import ps.emperor.easy_water.adapter.ApplyWaterGateLinkageAdapter;
import ps.emperor.easy_water.entity.ApplyWaterGateLinkageBean;
import ps.emperor.easy_water.utils.DensityUtil;
import ps.emperor.easy_water.utils.SharedUtils;
import ps.emperor.easy_water.view.MainActionBar;
import ps.emperor.easy_water.view.MainActionBars;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

/**
 * 数据
 * 
 * @author 毛国江
 * @version 2016-12-14 下午12:30
 */
public class InformationFragment extends Fragment implements
		OnClickListener{
	private LayoutInflater mInflater;
	private MainActionBars actionBar;
	
	
	@SuppressLint("CutPasteId")
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflater = inflater;
		View view = inflater.inflate(R.layout.fragment_haplopore_gate_control,
				container, false);

		actionBar = (MainActionBars) view
				.findViewById(R.id.actionbar_apply_water_haplopore_gate_control);
		actionBar.setLeftIcon(R.drawable.btn_back_selector);
		actionBar.setRightText("保存");
		return view;
	}

	
	@Override
	public void onClick(View v) {
		FragmentManager fgManager = getFragmentManager();
		FragmentTransaction transaction = fgManager.beginTransaction();
		switch (v.getId()) {
		case R.id.acitionbar_left:
			ApplyWaterDistrbutionGate fragment = new ApplyWaterDistrbutionGate();
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			transaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.replace(R.id.fl, fragment, "main");
			transaction.commit();
			break;
		case R.id.acitionbar_right:
			ApplyWaterDistrbutionGate fragment1 = new ApplyWaterDistrbutionGate();
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			transaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.replace(R.id.fl, fragment1, "main");
			transaction.commit();
			break;
		}

	}
}
