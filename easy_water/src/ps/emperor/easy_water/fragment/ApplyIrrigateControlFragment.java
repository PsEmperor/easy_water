package ps.emperor.easy_water.fragment;

import java.util.ArrayList;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.adapter.ApplyIrrigateControlAdapter;
import ps.emperor.easy_water.adapter.ArrayWheelAdapter;
import ps.emperor.easy_water.entity.ApplyIrrigateControlBean;
import ps.emperor.easy_water.view.MainActionBar;
import ps.emperor.easy_water.view.MyGridView;
import ps.emperor.easy_water.view.WheelView;

/**
 * 灌溉组控制
 * 
 * @author 毛国江
 * @version 2016-5-24 下午15:00
 */

@SuppressLint("NewApi")
public class ApplyIrrigateControlFragment extends Fragment implements
		OnClickListener, OnItemClickListener {

	private LayoutInflater mInflater;
	private MainActionBar actionBar;
	private MyGridView gridView;
	private ApplyIrrigateControlAdapter adapter; //灌溉组控制adapter
	private List<ApplyIrrigateControlBean> beans; //灌溉组控制实体类
	private ImageView irrigatr_control;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflater = inflater;
		View view = inflater.inflate(R.layout.fragment_apply_irrigate_control,
				container, false);

		actionBar = (MainActionBar) view
				.findViewById(R.id.actionbar_apply_irrigate_control);
		actionBar.setLeftIcon(R.drawable.btn_back_selector);
		actionBar.setRightIcon(R.drawable.ic_launcher);
		actionBar.setTitle("灌溉组控制");
		actionBar.setActionBarOnClickListener(this);

		irrigatr_control = (ImageView) view
				.findViewById(R.id.image_apply_irrigate_control);
		irrigatr_control.setOnClickListener(this);

		gridView = (MyGridView) view
				.findViewById(R.id.grid_apply_irrigate_control_fm);
		gridView.setOnItemClickListener(this);
		adapter = new ApplyIrrigateControlAdapter(getActivity());
		beans = new ArrayList<ApplyIrrigateControlBean>();
		ApplyIrrigateControlBean bean;
		for (int i = 0; i < 4; i++) {
			bean = new ApplyIrrigateControlBean();
			bean.setValve("A");
			beans.add(bean);
		}
		adapter.addData(beans, false);
		gridView.setAdapter(adapter);
		beans = adapter.getData();
		return view;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) {
		FragmentManager fgManager = getFragmentManager();
		FragmentTransaction transaction = fgManager.beginTransaction();
		switch (v.getId()) {
		case R.id.acitionbar_left:
			ApplyIrrigateUnitControlFragment fragment = new ApplyIrrigateUnitControlFragment();
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			transaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.replace(R.id.fl, fragment, "main");
			transaction.commit();
			break;
		case R.id.acitionbar_right:
			ApplyIrrigateSingleValveFragment fragment1 = new ApplyIrrigateSingleValveFragment();
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			transaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.replace(R.id.fl, fragment1, "main");
			transaction.commit();
			break;
		case R.id.image_apply_irrigate_control:// 轮灌组dialog选择
			String[] names = { "   轮灌组A   ", "   轮灌组B   ", "   轮灌组C   ",
					"   轮灌组D   ", "   轮灌组E   ", "   轮灌组F   " };
			final AlertDialog dialog = new AlertDialog.Builder(getActivity())
					.create();
			dialog.setTitle("选择分组");
			final WheelView catalogWheel = new WheelView(getActivity());
			dialog.setButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					int name = catalogWheel.getCurrentItem();
					dialog.dismiss();
					// 实现下ui的刷新
				}
			});
			dialog.setButton2("取消", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});

			catalogWheel.setVisibleItems(5);
			catalogWheel.setCyclic(true);
			catalogWheel.setAdapter(new ArrayWheelAdapter<String>(names));
			dialog.setView(catalogWheel);
			dialog.show();
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view,
			final int position, long id) {
		FragmentManager fgManager = getFragmentManager();
		FragmentTransaction transaction = fgManager.beginTransaction();
		ApplyIrrigateControlValveFragment fragment1 = new ApplyIrrigateControlValveFragment();
		// transaction.setCustomAnimations(R.anim.right_in,
		// R.anim.right_out);
		transaction
				.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		transaction.replace(R.id.fl, fragment1, "main");
		transaction.commit();
	}
	// 初始化时汉字会挤压，在汉字左右两边添加空格即可

}
