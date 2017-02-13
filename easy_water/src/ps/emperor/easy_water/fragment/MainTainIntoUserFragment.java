package ps.emperor.easy_water.fragment;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.ex.HttpException;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;

import com.google.gson.Gson;

import ps.emperor.easy_water.R;
import ps.emperor.easy_water.adapter.MainTainIntoCropsAdapter;
import ps.emperor.easy_water.utils.SharedUtils;
import ps.emperor.easy_water.utils.URL;
import ps.emperor.easy_water.view.MainActionBar;
import ps.emperor.easy_water.view.MainActionBars;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 录入种植戸信息
 * 
 * @author 毛国江
 * @version 2016-6-20 下午14:21
 */

public class MainTainIntoUserFragment extends Fragment implements
		OnClickListener {
	private LayoutInflater mInflater;
	private MainActionBars actionBar;
	private ArrayList<String> integers = new ArrayList<String>();
	private ArrayList<String> list = new ArrayList<String>();
	private GridView gridView;
	private int area;
	private ProgressDialog progressDialog;
	private EditText user_name, user_tel;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflater = inflater;
		View view = inflater.inflate(R.layout.fragment_maintain_into_user_info,
				container, false);
		actionBar = (MainActionBars) view
				.findViewById(R.id.actionbar_maintain_into_user);
		user_name = (EditText) view
				.findViewById(R.id.edit__apply_irrigatr_control_user_name);
		user_tel = (EditText) view
				.findViewById(R.id.edit__apply_irrigatr_control_user_tel);

		actionBar.setLeftIcon(R.drawable.btn_back_selector);
		actionBar.setRightText("保存");
		actionBar.setTitle("录入种植户信息");
		actionBar.setActionBarOnClickListener(this);
		integers = getArguments().getStringArrayList("info");
		list = getArguments().getStringArrayList("list");
		area = getArguments().getInt("area");
		gridView = (GridView) view.findViewById(R.id.grid__maintain_into_user);
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_list_item_1, integers);

		/* 设置ListView的Adapter */
		gridView.setAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, integers));
		return view;
	}

	@Override
	public void onClick(View v) {
		FragmentManager fgManager = getFragmentManager();
		FragmentTransaction transaction = fgManager.beginTransaction();
		switch (v.getId()) {
		case R.id.acitionbar_left:
			MainTainIrrigationUserInfoFragment fragment = new MainTainIrrigationUserInfoFragment();
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			transaction.setCustomAnimations(
					R.anim.slide_fragment_horizontal_right_in,
					R.anim.slide_fragment_horizontal_left_out);
			transaction.replace(R.id.fragment_maintain_present_irrigate,
					fragment, "main");
			transaction.commit();
			break;
		case R.id.acitionbar_right:
			RequestParams param2 = new RequestParams(URL.addGrowersInfo); // 网址(请替换成实际的网址)
			progressDialog = ProgressDialog.show(getActivity(), "Loading...",
					"Please wait...", true, false);
			JSONObject js_request = new JSONObject();
			try {
				param2.setAsJsonContent(true);
				String str1 = (String) SharedUtils.getParam(getActivity(),
						"FirstDerviceID", "");
				;
				try {
					str1 = java.net.URLEncoder.encode(str1, "UTF-8");
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				js_request.put("firstDerviceID", str1);
				js_request.put("valueControlChanID", list);
				js_request.put("growersName", user_name.getText().toString());
				js_request
						.put("growersPhoneNum", user_tel.getText().toString());
				js_request.put("area", area);
				param2.setBodyContent(js_request.toString());
			} catch (Exception e) {
				e.printStackTrace();
				param2.setAsJsonContent(true);
			}// 根据实际需求添加相应键值对

			x.http().request(HttpMethod.POST, param2,
					new CommonCallback<String>() {
						@Override
						public void onCancelled(CancelledException arg0) {

						}

						// 注意:如果是自己onSuccess回调方法里写了一些导致程序崩溃的代码，也会回调道该方法，因此可以用以下方法区分是网络错误还是其他错误
						// 还有一点，网络超时也会也报成其他错误，还需具体打印出错误内容比较容易跟踪查看
						@Override
						public void onError(Throwable ex, boolean isOnCallback) {

							Toast.makeText(x.app(), ex.getMessage(),
									Toast.LENGTH_LONG).show();
							if (ex instanceof HttpException) { // 网络错误 
								HttpException httpEx = (HttpException) ex;
								int responseCode = httpEx.getCode();
								String responseMsg = httpEx.getMessage();
								String errorResult = httpEx.getResult();
								Toast.makeText(getActivity(), "请求失败",
										Toast.LENGTH_SHORT);
								// ...
								progressDialog.dismiss();
							} else { // 其他错误 
								// ...
								Toast.makeText(getActivity(), "请求失败",
										Toast.LENGTH_SHORT);
								progressDialog.dismiss();
							}

						}

						// 不管成功或者失败最后都会回调该接口
						@Override
						public void onFinished() {
						}

						@Override
						public void onSuccess(String arg0) {
							Toast.makeText(getActivity(), "录入成功！",
									Toast.LENGTH_SHORT).show();
							FragmentManager fgManager = getFragmentManager();
							FragmentTransaction transaction = fgManager
									.beginTransaction();
							MainTainIrrigationUserInfoFragment fragment = new MainTainIrrigationUserInfoFragment();
							// transaction.setCustomAnimations(R.anim.right_in,
							// R.anim.right_out);
							transaction.setCustomAnimations(
									R.anim.slide_fragment_horizontal_right_in,
									R.anim.slide_fragment_horizontal_left_out);
							transaction.replace(
									R.id.fragment_maintain_present_irrigate,
									fragment, "main");
							transaction.commit();
							progressDialog.dismiss();
						}
					});
			break;
		default:
			break;
		}
	}
}
