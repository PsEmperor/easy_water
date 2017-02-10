package ps.emperor.easy_water.fragment;

import java.util.Set;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import ps.emperor.easy_water.MainActivity;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.utils.PutToFile;
import ps.emperor.easy_water.utils.SharedUtils;
import ps.emperor.easy_water.view.MainActionBar;

/**
 * （我）界面
 * 
 * @author 毛国江
 * @version 2016-5-16 上午10:48
 */
@SuppressLint("NewApi")
public class MinesFragment extends Fragment implements OnClickListener {
	private MainActionBar actionBar;
	private LayoutInflater mInflater;
	private RelativeLayout user_info, main_pour, main_water,main_setting,main_help,main_comments,about;
	private ImageView image_info_head;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mInflater = inflater;
		View view = inflater.inflate(R.layout.fragment_mines, container, false);

		actionBar = (MainActionBar) view.findViewById(R.id.actionbar_mine);
		actionBar.setLeftGone();
		actionBar.setRightGone();
		actionBar.setTitle("我");
		actionBar.setActionBarOnClickListener(this);

		user_info = (RelativeLayout) view.findViewById(R.id.layout_main_user_info);// 个人信息
		user_info.setOnClickListener(this);
		main_pour = (RelativeLayout) view.findViewById(R.id.layout_main_pour);// 灌溉设备
		main_pour.setOnClickListener(this);
		main_water = (RelativeLayout) view.findViewById(R.id.layout_main_water);// 配水设备
		main_water.setOnClickListener(this);
		main_setting = (RelativeLayout) view.findViewById(R.id.layout_main_setting);//设置
		main_setting.setOnClickListener(this);
		main_help = (RelativeLayout) view.findViewById(R.id.layout_main_help);//客服与帮助
		main_help.setOnClickListener(this);
		main_comments = (RelativeLayout) view.findViewById(R.id.layout_main_comments);//意见与反馈
		main_comments.setOnClickListener(this);
		about = (RelativeLayout) view.findViewById(R.id.layout_main_about);//意见与反馈
		about.setOnClickListener(this);
		image_info_head = (ImageView) view.findViewById(R.id.image_main_head); // 显示头像的image
		if (PutToFile.getBitmapFromFile() != null) {
			image_info_head.setImageBitmap(PutToFile.getBitmapFromFile());
		}else{
			image_info_head.setImageResource(R.drawable.ic_launcher);
		}
		return view;
	}

	@SuppressLint("NewApi")
	@Override
	public void onClick(View v) {
		FragmentManager fgManager = getFragmentManager();
		FragmentTransaction transaction = fgManager.beginTransaction();
		switch (v.getId()) {
		case R.id.layout_main_user_info://个人信息
			MineUserInfoFragment fragment = new MineUserInfoFragment();
//			transaction.setCustomAnimations(R.anim.right_in, R.anim.left_out);
//			transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.setCustomAnimations(R.anim.slide_fragment_horizontal_left_in, R.anim.slide_fragment_horizontal_right_out);
			transaction.replace(R.id.fl, fragment, "main");
			transaction.commit();
			break;
		case R.id.layout_main_pour://灌溉设备
			MineIrrigationEquipmentFragment fragment1 = new MineIrrigationEquipmentFragment();
//			transaction.setCustomAnimations(R.anim.right_in, R.anim.left_out);
			transaction.setCustomAnimations(R.anim.slide_fragment_horizontal_left_in, R.anim.slide_fragment_horizontal_right_out);
			transaction.replace(R.id.fl, fragment1, "main");
			transaction.commit();
			break;
		case R.id.layout_main_water://配水
			MineWaterDistributionFragment fragment2 = new MineWaterDistributionFragment();
//			transaction.setCustomAnimations(R.anim.right_in, R.anim.left_out);
			transaction.setCustomAnimations(R.anim.slide_fragment_horizontal_left_in, R.anim.slide_fragment_horizontal_right_out);
			transaction.replace(R.id.fl, fragment2, "main");
			transaction.commit();
			break;
		case R.id.layout_main_setting://设置
			MineSettingFragment fragment3 = new MineSettingFragment();
//			transaction.setCustomAnimations(R.anim.right_in, R.anim.left_out);
			transaction.setCustomAnimations(R.anim.slide_fragment_horizontal_left_in, R.anim.slide_fragment_horizontal_right_out);
			transaction.replace(R.id.fl, fragment3, "main");
			transaction.commit();
//		    BluetoothAdapter BA;
//			BA = BluetoothAdapter.getDefaultAdapter();
//			Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//			startActivityForResult(turnOn, 0);   
//			Set<BluetoothDevice>pairedDevices;
//			pairedDevices = BA.getBondedDevices();
			break;
		case R.id.layout_main_help://帮助
			MineHelpFragment fragment4 = new MineHelpFragment();
//			transaction.setCustomAnimations(R.anim.right_in, R.anim.left_out);
			transaction.setCustomAnimations(R.anim.slide_fragment_horizontal_left_in, R.anim.slide_fragment_horizontal_right_out);
			transaction.replace(R.id.fl, fragment4, "main");
			transaction.commit();
			break;
		case R.id.layout_main_comments://反馈
			MineCommentsFragmt fragment5 = new MineCommentsFragmt();
//			transaction.setCustomAnimations(R.anim.right_in, R.anim.left_out);
//			ApplyIrrigateFragment fragment5 = new ApplyIrrigateFragment(); //灌溉（应用）测试
			transaction.setCustomAnimations(R.anim.slide_fragment_horizontal_left_in, R.anim.slide_fragment_horizontal_right_out);
			transaction.replace(R.id.fl, fragment5, "main");
			transaction.commit();
			break;
		case R.id.layout_main_about://关于
			MineAboutFragment fragment6 = new MineAboutFragment();
//			ApplyWaterDistrbutionFragment fragment6 = new ApplyWaterDistrbutionFragment();//配水(应用)测试
			transaction.setCustomAnimations(R.anim.slide_fragment_horizontal_left_in, R.anim.slide_fragment_horizontal_right_out);
			transaction.replace(R.id.fl, fragment6, "main");
			transaction.commit();
//			Intent intent = new Intent(getActivity(),IrriagteOrWaterActivity.class);
//			startActivity(intent);
			break;
		default:
			break;
		}
	}
	
}
