package ps.emperor.easy_water.fragment;

import java.util.ArrayList;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.adapter.ApplyIrrigateUnitControlPlantAdapter;
import ps.emperor.easy_water.adapter.CustomArrayAdapter;
import ps.emperor.easy_water.adapter.CustomArrayAdapters;
import ps.emperor.easy_water.entity.ApplyIrrigationProject;
import ps.emperor.easy_water.entity.ApplyIrrigationUnitControlBean;
import ps.emperor.easy_water.entity.CustomData;
import ps.emperor.easy_water.greendao.DBHelper;
import ps.emperor.easy_water.greendao.IrrigationProject;
import ps.emperor.easy_water.utils.CheckUtil;
import ps.emperor.easy_water.utils.SharedUtils;
import ps.emperor.easy_water.view.HorizontalListView;
import ps.emperor.easy_water.view.MainActionBar;
import android.view.View.OnClickListener;

/**
 * 灌溉单元控制
 * @author 毛国江
 * @version 2016-5-24
 */

@SuppressLint("NewApi")
public class ApplyIrrigateUnitControlFragment extends Fragment implements OnClickListener{
	
	private LayoutInflater mInflater;
	private MainActionBar actionBar;
	private TextView unit_control_plan;
	private Button btn_control_true;
	private ListView listView;
	private List<ApplyIrrigationUnitControlBean> beans; 
	private List<ApplyIrrigationProject> customData; 
	private TextView mHlvSimpleList;
	private ApplyIrrigateUnitControlPlantAdapter adapter;
	private CustomArrayAdapters adapter1;
	private String text;
	private DBHelper dbHelper;
	private List<IrrigationProject> listentity;
	private String units;
	private int isNot;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mInflater = inflater;
		View view = inflater.inflate(R.layout.fragment_apply_irrigate_unit_control, container, false);

		actionBar = (MainActionBar) view.findViewById(R.id.actionbar_apply_irrigate_unit_control);
		actionBar.setLeftIcon(R.drawable.btn_back_selector);
		actionBar.setRightIcon(R.drawable.ic_launcher);
		actionBar.setTitle("灌溉单元管理");
		actionBar.setActionBarOnClickListener(this);
		
		dbHelper = DBHelper.getInstance(getActivity()); // 得到DBHelper对象
		units = getArguments().getString("units");
		listentity = dbHelper.loadLastMsgBySessionids(units);
		mHlvSimpleList = (TextView)view.findViewById(R.id.hlvSimpleList);
		listView = (ListView) view.findViewById(R.id.list_apply_irrigate_unit_control_plant);
		beans = new ArrayList<ApplyIrrigationUnitControlBean>();
		adapter = new ApplyIrrigateUnitControlPlantAdapter(getActivity());
		customData = new ArrayList<ApplyIrrigationProject>();
		adapter1 = new CustomArrayAdapters(getActivity());
		
		init();
		
		unit_control_plan = (TextView) view.findViewById(R.id.text_apply_irrigate_unit_control_plan);
		unit_control_plan.setOnClickListener(this);
		btn_control_true = (Button) view.findViewById(R.id.btn_apply_irrigate_unit_control_true);
		btn_control_true.setOnClickListener(this);

		if(CheckUtil.IsEmpty(listentity)){
			unit_control_plan.setText("设定计划");
		}else{
			unit_control_plan.setText("更改计划");
		}
		
		return view;
	}
	
	private void init() {
		ApplyIrrigationUnitControlBean bean;
		for (int i = 0; i < 15; i++) {
			bean = new ApplyIrrigationUnitControlBean();
			bean.setPlant("大米");
			bean.setTime("2016-7-14 11:00");
			beans.add(bean);
		}
		adapter.addData(beans, false);
		listView.setAdapter(adapter);
		beans = adapter.getData();
		
		ApplyIrrigationProject data;
		for (int i = 0; i < 5; i++) {
			data = new ApplyIrrigationProject();
			data.setName("李青、");
			customData.add(data);
		}
		adapter1.addData(customData,false);
		customData = adapter1.getData();
		for (int i = 0; i < customData.size(); i++) {
			if(!CheckUtil.IsEmpty(customData.get(i).getName())){
				if(CheckUtil.IsEmpty(text)){
					text = "";
				}
				text += customData.get(i).getName();
			}
		}
		mHlvSimpleList.setText(text);
	}

	@Override
	public void onClick(View v) {
		FragmentManager fgManager = getFragmentManager();
		FragmentTransaction transaction = fgManager.beginTransaction();
		switch (v.getId()) {
		case R.id.acitionbar_left:
			ApplyIrrigateFragment fragment = new ApplyIrrigateFragment();
//			transaction.setCustomAnimations(R.anim.right_in, R.anim.right_out);
			transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.replace(R.id.fl, fragment, "main");
			transaction.commit();
			break;
		case R.id.acitionbar_right:
			ApplyirrigatePreludeFragment fragment3 = new ApplyirrigatePreludeFragment();
			Bundle bundle2 = new Bundle();
			bundle2.putString("units", units);
			fragment3.setArguments(bundle2);
//			transaction.setCustomAnimations(R.anim.right_in, R.anim.right_out);
			transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.replace(R.id.fl, fragment3, "main");
			transaction.commit();
			break;
		case R.id.text_apply_irrigate_unit_control_plan: //设定计划(此处需要传值 需要授权单位、灌溉单元)
			ApplyIrrigateProjectFragment fragment1 = new ApplyIrrigateProjectFragment();
//			transaction.setCustomAnimations(R.anim.right_in, R.anim.right_out);
			transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			isNot = 1;
			SharedUtils.setParam(getActivity(), "isNot", isNot);
			Bundle bundle = new Bundle();
			bundle.putString("units", units);
			fragment1.setArguments(bundle);
			transaction.replace(R.id.fl, fragment1, "main");
			transaction.commit();
			break;
		case R.id.btn_apply_irrigate_unit_control_true://进入单阀界面
			ApplyIrrigateSingleValveFragment fragment2 = new ApplyIrrigateSingleValveFragment();
//			transaction.setCustomAnimations(R.anim.right_in, R.anim.right_out);
			Bundle bundle1 = new Bundle();
			bundle1.putString("units", units);
			fragment2.setArguments(bundle1);
			transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.replace(R.id.fl, fragment2, "main");
			transaction.commit();
			break;
		}
	}
}
