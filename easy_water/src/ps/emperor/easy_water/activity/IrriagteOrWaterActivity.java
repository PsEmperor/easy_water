package ps.emperor.easy_water.activity;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import ps.emperor.easy_water.BaseActivity;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.entity.PermissionListBeans;
import ps.emperor.easy_water.entity.PermissionListBeans.PermissionListBean;
import ps.emperor.easy_water.fragment.MainTainIrrigateFragment;
import ps.emperor.easy_water.fragment.MainTainWaterFragment;
import ps.emperor.easy_water.utils.PsUtils;
import ps.emperor.easy_water.utils.SharedUtils;
import ps.emperor.easy_water.view.MainActionBar;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

public class IrriagteOrWaterActivity extends BaseActivity implements
		OnClickListener {
	private MainActionBar actionBar;
	private TextView irrigate, water;
	private List<TextView> itemViews;
	private int index;
	private InputMethodManager  manager ;
	private List<PermissionListBean> PermissionListBean;
	private PermissionListBeans PermissionListBeans;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_irrigate_or_water);
		actionBar = (MainActionBar) findViewById(R.id.actionbar_apply_irriagte_or_water);
		actionBar.setLeftIcon(R.drawable.btn_back_selector);
		actionBar.setRightGone();
		actionBar.setTitle("维护");
		actionBar.setActionBarOnClickListener(this);
		manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);  
		irrigate = (TextView) findViewById(R.id.tv_apply_irriagte);
		water = (TextView) findViewById(R.id.tv_apply_water);
		
		itemViews = new ArrayList<TextView>();
		itemViews.add(irrigate);
		itemViews.add(water);
		
		irrigate.setOnClickListener(this);
		water.setOnClickListener(this);
		Gson gson = new Gson();
		PermissionListBeans = gson.fromJson(PsUtils.getShared(this).getString("permissionList", null),PermissionListBeans.class);
		PermissionListBean = PermissionListBeans.getPermissionList();
		initDate();

//		index = 1;
//		clearStatus();
	}
	 private void clearStatus() {  
	        if (index == 1) {  
	        	irrigate.setClickable(false);
	        	irrigate.setTextColor(getResources().getColor(R.color.red));
	        	irrigate.setSelected(false);
	        	
	        	water.setClickable(true);
	        	water.setTextColor(getResources().getColor(R.color.gray_1));
	        	water.setSelected(false);
	        } else if (index == 2) {  
	        	water.setClickable(false);
	        	water.setTextColor(getResources().getColor(R.color.red));
	        	water.setSelected(false);
	        	
	        	irrigate.setClickable(true);
	        	irrigate.setTextColor(getResources().getColor(R.color.gray_1));
	        	irrigate.setSelected(false);
	        } 
	    }  
	private void initDate() {
		if(PermissionListBean.get(0).getOpertionStat().equals("-1")){
			clearStatus();
			FragmentManager fgManager = getFragmentManager();
			FragmentTransaction transaction = fgManager.beginTransaction();
			MainTainWaterFragment fragment1 = new MainTainWaterFragment();
			transaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction
					.replace(R.id.fragment_home_recommend, fragment1, "main");
			transaction.commit();
			index = 2;
			clearStatus();
			irrigate.setVisibility(View.GONE);
			water.setTranslationX(15);
		}else if(PermissionListBean.get(1).getOpertionStat().equals("-1")){
			clearStatus();
			FragmentManager fgManager = getFragmentManager();
			FragmentTransaction transaction = fgManager.beginTransaction();
			MainTainIrrigateFragment fragment = new MainTainIrrigateFragment();
			transaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.replace(R.id.fragment_home_recommend, fragment, "main");
			transaction.commit();
			index = 1;
			clearStatus();
			water.setVisibility(View.GONE);
			irrigate.setTranslationX(-15);
		}else{
			clearStatus();
			FragmentManager fgManager = getFragmentManager();
			FragmentTransaction transaction = fgManager.beginTransaction();
			MainTainIrrigateFragment fragment = new MainTainIrrigateFragment();
			transaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.replace(R.id.fragment_home_recommend, fragment, "main");
			transaction.commit();
			index = 1;
			clearStatus();
		}
	}

	@Override
	public void onClick(View v) {
		FragmentManager fgManager = getFragmentManager();
		FragmentTransaction transaction = fgManager.beginTransaction();
		switch (v.getId()) {
		case R.id.acitionbar_left:
			finish();
			break;
		case R.id.tv_apply_irriagte:
			MainTainIrrigateFragment fragment = new MainTainIrrigateFragment();
			transaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.replace(R.id.fragment_home_recommend, fragment, "main");
			transaction.commit();
			index = 1;
			clearStatus();
			break;
		case R.id.tv_apply_water:
			MainTainWaterFragment fragment1 = new MainTainWaterFragment();
			transaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction
					.replace(R.id.fragment_home_recommend, fragment1, "main");
			transaction.commit();
			index = 2;
			clearStatus();
			break;
		default:
			break;
		}
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {  
		  // TODO Auto-generated method stub  
		  if(event.getAction() == MotionEvent.ACTION_DOWN){  
		     if(getCurrentFocus()!=null && getCurrentFocus().getWindowToken()!=null){  
		    	 manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);  
		     }  
		  }  
		  return super.onTouchEvent(event);  
		 }  
	
}
