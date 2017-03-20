package ps.emperor.easy_water.activity;
/**
 * 灌溉信息维护承载页
 * @author 毛国江
 * @version 2016-6-6 下午12：48
 */

import ps.emperor.easy_water.BaseActivity;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.fragment.MainTainIrrigateInfoFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;

public class MainTainPresentrrigateActivity extends BaseActivity{
	InputMethodManager  manager ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maintain_present_irrigate);
		manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		
		FragmentManager fgManager = getFragmentManager();
		FragmentTransaction transaction = fgManager.beginTransaction();
		MainTainIrrigateInfoFragment fragment = new MainTainIrrigateInfoFragment();
		transaction
		.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		transaction.replace(R.id.fragment_maintain_present_irrigate, fragment, "main");
		transaction.commit();
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
