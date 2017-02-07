package ps.emperor.easy_water.fragment;

import java.io.UnsupportedEncodingException;

import java.util.ArrayList;

import java.util.List;

import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.ex.HttpException;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;

import com.google.gson.Gson;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.adapter.ApplyIrrigateUnitControlPlantAdapter;
import ps.emperor.easy_water.adapter.CustomArrayAdapters;
import ps.emperor.easy_water.entity.ApplyIrrigationUnitControlBean;
import ps.emperor.easy_water.entity.ApplyIrrigationUnitControlBean.GroupNum;
import ps.emperor.easy_water.entity.ApplyIrrigationUnitControlBean.infoList;
import ps.emperor.easy_water.greendao.DBHelper;
import ps.emperor.easy_water.greendao.IrrigationProject;
import ps.emperor.easy_water.utils.CheckUtil;
import ps.emperor.easy_water.utils.SharedUtils;
import ps.emperor.easy_water.utils.URL;
import ps.emperor.easy_water.view.MainActionBars;
import android.view.View.OnClickListener;

/**
 * 灌溉单元控制
 * 
 * @author 毛国江
 * @version 2016-5-24
 */

@SuppressLint("NewApi")
public class ApplyIrrigateUnitControlFragment extends Fragment implements
		OnClickListener {

	private LayoutInflater mInflater;
	private MainActionBars actionBar;
	private ImageView unit_control_plan;
	private Button btn_control_true;
	private ListView listView;
	private List<infoList> customData;
	private TextView mHlvSimpleList;
	private ApplyIrrigateUnitControlPlantAdapter adapter;
	private CustomArrayAdapters adapter1;
	private String text;
	private DBHelper dbHelper;
	private List<IrrigationProject> listentity;
	private String units;
	private int isNot;
	private List<infoList> beens;
	private List<GroupNum> beans;
	private ProgressDialog progressDialog;
	private TextView area,group,irriunit;
	
	
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				if (CheckUtil.IsEmpty(listentity)) {
					unit_control_plan.setImageResource(R.drawable.project1);
				} else {
					unit_control_plan.setImageResource(R.drawable.project);
				}
				break;
			}
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflater = inflater;
		View view = inflater
				.inflate(R.layout.fragment_apply_irrigate_unit_control,
						container, false);

		actionBar = (MainActionBars) view
				.findViewById(R.id.actionbar_apply_irrigate_unit_control);
		actionBar.setLeftIcon(R.drawable.btn_back_selector);
		actionBar.setTitle("灌溉单元管理");
		actionBar.setActionBarOnClickListener(this);

		dbHelper = DBHelper.getInstance(getActivity()); // 得到DBHelper对象
		units = getArguments().getString("units");
		new Thread(new Runnable() {

			@Override
			public void run() {
				listentity = dbHelper.loadLastMsgBySessionids(units);
				handler.sendEmptyMessage(0);
			}
		}).start();
		mHlvSimpleList = (TextView) view.findViewById(R.id.hlvSimpleList);
		listView = (ListView) view
				.findViewById(R.id.list_apply_irrigate_unit_control_plant);
		adapter = new ApplyIrrigateUnitControlPlantAdapter(getActivity());
		customData = new ArrayList<infoList>();
		adapter1 = new CustomArrayAdapters(getActivity());
		area = (TextView) view.findViewById(R.id.tv_apply_unit_control_area);
		group = (TextView) view.findViewById(R.id.tv_apply_unit_control_class_number);
		irriunit = (TextView) view.findViewById(R.id.text_apply_unit_control_units);

		init();

		unit_control_plan = (ImageView) view
				.findViewById(R.id.unit_control_plan);
		unit_control_plan.setOnClickListener(this);
		btn_control_true = (Button) view
				.findViewById(R.id.btn_apply_irrigate_unit_control_true);
		btn_control_true.setOnClickListener(this);

		return view;
	}

	private void init() {

		// irrigation = dbHelper.loadContinue(units);
		String str1 = (String) SharedUtils.getParam(getActivity(),
				"FirstDerviceID", "");
		;
		try {
			str1 = java.net.URLEncoder.encode(str1, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		RequestParams param3 = new RequestParams(URL.findIrriUnitManage
				+ str1); // 网址(请替换成实际的网址)
		// params.addQueryStringParameter("key", "value"); // 参数(请替换成实际的参数与值)
		progressDialog = ProgressDialog.show(getActivity(), "Loading...",
				"Please wait...", true, false);
		JSONObject js_request2 = new JSONObject();
		try {
			param3.setAsJsonContent(true);
		} catch (Exception e) {
			e.printStackTrace();
			param3.setAsJsonContent(true);
		}// 根据实际需求添加相应键值对

		x.http().request(HttpMethod.GET, param3, new CommonCallback<String>() {
			@Override
			public void onCancelled(CancelledException arg0) {

			}

			// 注意:如果是自己onSuccess回调方法里写了一些导致程序崩溃的代码，也会回调道该方法，因此可以用以下方法区分是网络错误还是其他错误
			// 还有一点，网络超时也会也报成其他错误，还需具体打印出错误内容比较容易跟踪查看
			@Override
			public void onError(Throwable ex, boolean isOnCallback) {

				Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG)
						.show();
				if (ex instanceof HttpException) { // 网络错误 
					HttpException httpEx = (HttpException) ex;
					int responseCode = httpEx.getCode();
					String responseMsg = httpEx.getMessage();
					String errorResult = httpEx.getResult();
					Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT);
					// ...
					progressDialog.dismiss();
				} else { // 其他错误 
					// ...
					Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT);
					progressDialog.dismiss();
				}

			}

			// 不管成功或者失败最后都会回调该接口
			@Override
			public void onFinished() {
			}

			@Override
			public void onSuccess(String arg0) {
				Toast.makeText(getActivity(), "请求成功", Toast.LENGTH_SHORT);
				Gson gson = new Gson();
				System.out.println(arg0);
				ApplyIrrigationUnitControlBean fromJson = gson.fromJson(arg0,
						ApplyIrrigationUnitControlBean.class);
				beens = fromJson.getAuthNameList();
				beans = fromJson.getGroupNum();
				adapter1.addData(beens, false);
				adapter.addData(beens, false);
				listView.setAdapter(adapter);
				customData = adapter1.getData();
				if (!CheckUtil.IsEmpty(customData.get(0).getGrowersName())) {
					if (CheckUtil.IsEmpty(text)) {
						text = "";
					}
					text = customData.get(0).getGrowersName();
				}
				mHlvSimpleList.setText(text);
				if(!CheckUtil.IsEmpty(beens.get(0).getArea())){
					area.setText(beens.get(0).getArea()+"亩");
				}else{
					area.setText("0亩");
				}
				if(!CheckUtil.IsEmpty(beans.get(0).getGroupNum())){
					group.setText(beans.get(0).getGroupNum());
				}else{
					group.setText("0");
				}
				if(!CheckUtil.IsEmpty(beens.get(0).getIrriUnitName())){
					irriunit.setText(beens.get(0).getIrriUnitName());
				}else{
					irriunit.setText("");
				}
				progressDialog.dismiss();
			}
		});
	}

	@Override
	public void onClick(View v) {
		FragmentManager fgManager = getFragmentManager();
		FragmentTransaction transaction = fgManager.beginTransaction();
		switch (v.getId()) {
		case R.id.acitionbar_left:
			ApplyIrrigateFragment fragment = new ApplyIrrigateFragment();
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			transaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.replace(R.id.fl, fragment, "main");
			transaction.commit();
			break;
		case R.id.acitionbar_right:
			ApplyirrigatePreludeFragment fragment3 = new ApplyirrigatePreludeFragment();
			Bundle bundle2 = new Bundle();
			bundle2.putString("units", units);
			fragment3.setArguments(bundle2);
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			transaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.replace(R.id.fl, fragment3, "main");
			transaction.commit();
			break;
		case R.id.unit_control_plan: // 设定计划(此处需要传值
										// 需要授权单位、灌溉单元)
			ApplyIrrigateProjectFragment fragment1 = new ApplyIrrigateProjectFragment();
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			transaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			isNot = 1;
			SharedUtils.setParam(getActivity(), "isNot", isNot);
			Bundle bundle = new Bundle();
			bundle.putString("units", units);
			fragment1.setArguments(bundle);
			transaction.replace(R.id.fl, fragment1, "main");
			transaction.commit();
			break;
		case R.id.btn_apply_irrigate_unit_control_true:// 进入单阀界面
			ApplyIrrigateSingleValveFragment fragment2 = new ApplyIrrigateSingleValveFragment();
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			Bundle bundle1 = new Bundle();
			bundle1.putString("units", units);
			fragment2.setArguments(bundle1);
			transaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.replace(R.id.fl, fragment2, "main");
			transaction.commit();
			break;
		}
	}
}
