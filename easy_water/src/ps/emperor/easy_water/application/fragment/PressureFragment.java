package ps.emperor.easy_water.application.fragment;

import java.util.ArrayList;
import java.util.List;

import org.xutils.x;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import ps.emperor.easy_water.R;
import ps.emperor.easy_water.application.adapter.WaterAdapter;
import ps.emperor.easy_water.application.entity.Water_Pressure;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
@ContentView(R.layout.fragment_pressure)
public class PressureFragment extends Fragment {
	
	
	
	
	
	@ViewInject(R.id.ip1)
	private ps.emperor.easy_water.application.auxiliary.ItemWater ti1;
	@ViewInject(R.id.ip2)
	private ps.emperor.easy_water.application.auxiliary.ItemWater ti2;
	@ViewInject(R.id.ip3)
	private ps.emperor.easy_water.application.auxiliary.ItemWater ti3;
	@ViewInject(R.id.ip4)
	private ps.emperor.easy_water.application.auxiliary.ItemWater ti4;
	@ViewInject(R.id.ip5)
	private ps.emperor.easy_water.application.auxiliary.ItemWater ti5;
	@ViewInject(R.id.ip6)
	private ps.emperor.easy_water.application.auxiliary.ItemWater ti6;
	@ViewInject(R.id.ip7)
	private ps.emperor.easy_water.application.auxiliary.ItemWater ti7;
	@ViewInject(R.id.ip8)
	private ps.emperor.easy_water.application.auxiliary.ItemWater ti8;
	@ViewInject(R.id.ip9)
	private ps.emperor.easy_water.application.auxiliary.ItemWater ti9;
	@ViewInject(R.id.ip10)
	private ps.emperor.easy_water.application.auxiliary.ItemWater ti10;
	@ViewInject(R.id.ip11)
	private ps.emperor.easy_water.application.auxiliary.ItemWater ti11;
	@ViewInject(R.id.ip12)
	private ps.emperor.easy_water.application.auxiliary.ItemWater ti12;
	@ViewInject(R.id.ip13)
	private ps.emperor.easy_water.application.auxiliary.ItemWater ti13;
	@ViewInject(R.id.ip14)
	private ps.emperor.easy_water.application.auxiliary.ItemWater ti14;
	@ViewInject(R.id.ip15)
	private ps.emperor.easy_water.application.auxiliary.ItemWater ti15;
	@ViewInject(R.id.ip16)
	private ps.emperor.easy_water.application.auxiliary.ItemWater ti16;
	@ViewInject(R.id.ip17)
	private ps.emperor.easy_water.application.auxiliary.ItemWater ti17;
	@ViewInject(R.id.ip18)
	private ps.emperor.easy_water.application.auxiliary.ItemWater ti18;
	@ViewInject(R.id.ip19)
	private ps.emperor.easy_water.application.auxiliary.ItemWater ti19;
	@ViewInject(R.id.ip20)
	private ps.emperor.easy_water.application.auxiliary.ItemWater ti20;
	@ViewInject(R.id.ptitle)
	private ps.emperor.easy_water.utils.Ltitle title;

	
	
	
	
	
	
	@ViewInject(R.id.bt_pressure)
	private Button bt_p;

	@ViewInject(R.id.tv_text)
	private TextView tvc;
	private List<Water_Pressure> list = new ArrayList<Water_Pressure>();
	@Event(R.id.bt_back)
	private void backOnClick(View v){
		getFragmentManager().popBackStack();
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = x.view().inject(this, inflater, container);
		tvc.setText("压力配置");
//		 adapter = new WaterAdapter(getActivity(), R.layout.pressure_item, list);
		
		title.setEditText("保存");
		return view;
	}
	
	
	private int tag = 0;
	private NumberPicker np;
	private WaterAdapter adapter;
	@Event(R.id.bt_pressure)
	private void onClickSensor(View v){
		 np = new NumberPicker(getActivity());
		final String[] arr = { "1", "2", "3", "4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20"};
		np.setDisplayedValues(arr);
		np.setMinValue(0);
		np.setMaxValue(arr.length-1);
		if (tag != 0) {
		np.setValue(tag);
		}
		// np.setValue(4);
		// 关闭可编辑状态
		np.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
		AlertDialog b = new Builder(getActivity()).setView(np)
		.setPositiveButton("确定", new OnClickListener() {
		// .setNegativeButton("取消", null)
		@Override
		public void onClick(DialogInterface dialog, int which) {
			list.clear();
		// 获取当前选中的角标位并记录
		tag = np.getValue();
		// str[tag] 取得才是角标对应数组中的值
		bt_p.setText("\t\t\t\t"+arr[tag]);
		for (int i = -1; i < tag; i++) {
			list.add(new Water_Pressure());
		}
//		adapter.notifyDataSetChanged();
		selectVG(Integer.parseInt(arr[tag]));

		}
		}).show();
		// 设置弹出框的大小
		b.getWindow().setLayout(500, 500);

	}
	
	/**
	 * 标题栏右菜单点击事件
	 * @param v
	 */
	@Event(R.id.bt_edit)
	private void onClickEdit(View v){
		
		System.out.println("显示1的:"+ti1.getAll()+"显示10的:"+ti10.getAll());
		
	}
	
	

	/**
	 * 设置item visible及相关属性
	 * 
	 * @param t
	 */
	private void tVisible(ps.emperor.easy_water.application.auxiliary.ItemWater t) {

		t.setVisibility(View.VISIBLE);

	}

	/**
	 * 设置item gone及相关属性
	 * 
	 * @param t
	 */
	private void tGone(ps.emperor.easy_water.application.auxiliary.ItemWater t) {
		t.setVisibility(View.GONE);
	}

	/**
	 * 选择哪些显示，哪些不显示 选择什么时候用tGone，什么时候用tVisible
	 */
	private void selectVG(int n) {
		ps.emperor.easy_water.application.auxiliary.ItemWater[] arr = { ti1,
				ti2, ti3, ti4, ti5, ti6, ti7, ti8, ti9, ti10, ti11, ti12, ti13,
				ti14, ti15, ti16, ti17, ti18, ti19, ti20 };

		for (int i = 0; i < arr.length; i++) {
			if (i < n) {
				tVisible(arr[i]);
			} else {
				tGone(arr[i]);
			}
		}

	}

}
