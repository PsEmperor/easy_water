package ps.emperor.easy_water.activity;
/**
 * 闸门信息维护承载页
 * @author 毛国江
 * @version 2016-6-6 下午12：48
 */

import ps.emperor.easy_water.BaseActivity;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.fragment.MainTainGateInfoFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class MainTainGateInfoActivity extends BaseActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maintain_gate_info);
		FragmentManager fgManager = getFragmentManager();
		FragmentTransaction transaction = fgManager.beginTransaction();
		MainTainGateInfoFragment fragment = new MainTainGateInfoFragment();
		transaction
				.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		transaction.replace(R.id.fragment_maintain_gate_info, fragment, "main");
		transaction.commit();
	}


	
}
