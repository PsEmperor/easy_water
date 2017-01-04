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
import android.app.Fragment;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
@ContentView(R.layout.fragment_waterlevel)
public class WaterLevelFragment extends Fragment {
	@ViewInject(R.id.bt_waterlevel)
	private Button bt_wl;
	@ViewInject(R.id.lv_waterSensor)
	private ListView lv;
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
		tvc.setText("水位配置");
		adapter = new WaterAdapter(getActivity(), R.layout.water_item, list);
		lv.setAdapter(adapter);
		return view;
	}
	
	private int tag = 0;
	private NumberPicker np;
	private WaterAdapter adapter;
	@Event(R.id.bt_waterlevel)
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
		bt_wl.setText("\t\t\t\t"+arr[tag]);
		for (int i = -1; i < tag; i++) {
			list.add(new Water_Pressure());
		}
		adapter.notifyDataSetChanged();

		}
		}).show();
		// 设置弹出框的大小
		b.getWindow().setLayout(500, 500);

	}

}
