package ps.emperor.easy_water.activity;

import java.util.List;

import org.xutils.view.annotation.ContentView;


import ps.emperor.easy_water.BaseActivity;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.fragment.MineUserRoleFragment;
import ps.emperor.easy_water.view.MainActionBar;
import ps.emperor.easy_water.view.MainActionBars;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * 单孔闸门控制
 * 
 * @author 毛国江
 * @version 2016-9-9 下午16:49
 */
public class MineUserRoleChooseActivity extends BaseActivity implements
		OnClickListener{
	private LayoutInflater mInflater;
	private MainActionBar actionBar;
	private Button btn_choose;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_mines_role_choose);
		actionBar = (MainActionBar)findViewById(R.id.actionbar_user_role_choose);
		actionBar.setTitle("角色申请");
		actionBar.setLeftIcon(R.drawable.btn_back_selector);
		actionBar.setRightGone();
		actionBar.setActionBarOnClickListener(this);;
	}
	
	@Override
	public void onResume() {
		super.onResume();
	}
	@Override
	public void onClick(View v) {
		FragmentManager fgManager = getFragmentManager();
		FragmentTransaction transaction = fgManager.beginTransaction();
		switch (v.getId()) {
		case R.id.acitionbar_left:
			this.finish();
			break;
		}
	}
}
