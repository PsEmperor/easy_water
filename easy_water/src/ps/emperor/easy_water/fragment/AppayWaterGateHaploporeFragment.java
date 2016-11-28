package ps.emperor.easy_water.fragment;

import java.util.List;


import ps.emperor.easy_water.R;
import ps.emperor.easy_water.adapter.ApplyWaterGateLinkageAdapter;
import ps.emperor.easy_water.entity.ApplyWaterGateLinkageBean;
import ps.emperor.easy_water.utils.DensityUtil;
import ps.emperor.easy_water.utils.SharedUtils;
import ps.emperor.easy_water.view.MainActionBar;
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
 * 单孔闸门控制
 * 
 * @author 毛国江
 * @version 2016-9-9 下午16:49
 */
public class AppayWaterGateHaploporeFragment extends Fragment implements
		OnClickListener, OnSeekBarChangeListener ,OnTouchListener{
	private LayoutInflater mInflater;
	private MainActionBar actionBar;
	private SeekBar seekBar;
	private TextView tv_show,hight,aperture;
	private RelativeLayout layout_hight_change;
	private int progress,all;
	private Button btn_apply_water_haplopore;
	
	
	@SuppressLint("CutPasteId")
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflater = inflater;
		View view = inflater.inflate(R.layout.fragment_haplopore_gate_control,
				container, false);

		actionBar = (MainActionBar) view
				.findViewById(R.id.actionbar_apply_water_haplopore_gate_control);
		actionBar.setLeftIcon(R.drawable.btn_back_selector);
		actionBar.setRightGone();
		all = (Integer) SharedUtils.getParam(getActivity(), "all", 2);
		if(all == 1){
			actionBar.setTitle("多孔闸门联动");
		}else if(all == 0){
			actionBar.setTitle("单孔闸门控制");
		}
		actionBar.setActionBarOnClickListener(this);

		btn_apply_water_haplopore = (Button) view.findViewById(R.id.btn_apply_water_haplopore);
		btn_apply_water_haplopore.setOnClickListener(this);
		
		hight = (TextView) view.findViewById(R.id.tv_apply_water_haplopore_gate_hight);
		aperture = (TextView) view.findViewById(R.id.tv_apply_water_haplopore_gate_aperture);
		
		progress = 30;
		hight.setText(progress + "%");
		
		seekBar = (SeekBar) view
				.findViewById(R.id.mySeekBar_apply_water_haplopore);
		seekBar.setProgress(progress);
		seekBar.setOnSeekBarChangeListener(this);

		layout_hight_change = (RelativeLayout) view.findViewById(R.id.layout_hight_change);
		layout_hight_change.setOnClickListener(this);
		
		tv_show = (TextView) view.findViewById(R.id.tv_apply_water_haplopore_1);
		
		RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)layout_hight_change.getLayoutParams();
		int height = DensityUtil.dip2px(getActivity(), Float.valueOf((float) ((100-progress) * 2.7)));
		layoutParams.height = height;
		layout_hight_change.setLayoutParams(layoutParams);
		layout_hight_change.requestLayout();
		layout_hight_change.setOnTouchListener(this);
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
		case R.id.btn_apply_water_haplopore:
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

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		//这个部分就是拖动进度条变化上方布局高度
		hight.setText(progress + "%");
		tv_show.setText(progress + "%");
		RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)layout_hight_change.getLayoutParams();
		int height = DensityUtil.dip2px(getActivity(), Float.valueOf((float) ((100-progress) * 2.7)));
		layoutParams.height = height;
		layout_hight_change.setLayoutParams(layoutParams);
		layout_hight_change.requestLayout();
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {

	}


	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return false;
	}


	
	
}
