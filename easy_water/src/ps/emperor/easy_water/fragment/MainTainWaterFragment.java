package ps.emperor.easy_water.fragment;

import java.io.UnsupportedEncodingException;

import java.util.ArrayList;

import java.util.List;

import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.ex.HttpException;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;

import com.google.gson.Gson;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.activity.MainTainGateInfoActivity;
import ps.emperor.easy_water.adapter.MainTainDisAdapter;
import ps.emperor.easy_water.adapter.MainTainIrrigationAdapter;
import ps.emperor.easy_water.entity.UserReleDisInfoBean;
import ps.emperor.easy_water.entity.UserReleDisInfoBean.infoList;
import ps.emperor.easy_water.utils.CheckUtil;
import ps.emperor.easy_water.utils.NetStatusUtil;
import ps.emperor.easy_water.utils.SharedUtils;
import ps.emperor.easy_water.utils.URL;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 配水（维护）
 * 
 * @author 毛国江
 * @version 2016-6-2 下午14:09
 */

@SuppressLint("NewApi")
public class MainTainWaterFragment extends Fragment implements OnClickListener,
		OnItemClickListener {

	private LayoutInflater mInflater;
	private ListView listView;
	private MainTainDisAdapter adapter;
	private List<infoList> beens;
	private ImageView image_maintain_water_add;
	private EditText ed_maintain_water_add;
	private ProgressDialog progressDialog;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflater = inflater;
		View view = inflater.inflate(R.layout.fragment_maintain_irrigate,
				container, false);

		listView = (ListView) view.findViewById(R.id.list_maintain_water_add);
		listView.setOnItemClickListener(this);
		adapter = new MainTainDisAdapter(getActivity());

		image_maintain_water_add = (ImageView) view
				.findViewById(R.id.image_maintain_water_add);
		image_maintain_water_add.setOnClickListener(this);
		ed_maintain_water_add = (EditText) view
				.findViewById(R.id.ed_maintain_water_add);

		if (NetStatusUtil.isNetValid(getActivity())) {
			init();
		} else {
			Toast.makeText(getActivity(), "当前网络不可用！请检查您的网络状态！", Toast.LENGTH_SHORT)
					.show();
		}

		// MainTainIrrigationBean bean;
		// bean = new MainTainIrrigationBean();
		// bean.setMaintain("141团5连支渠进水闸");
		// beans.add(bean);
		// bean = new MainTainIrrigationBean();
		// bean.setMaintain("141团5连支渠一斗渠进水闸");
		// beans.add(bean);
		// bean = new MainTainIrrigationBean();
		// bean.setMaintain("141团5连支渠二斗渠进水闸");
		// beans.add(bean);
		// bean = new MainTainIrrigationBean();
		// bean.setMaintain("141团6连支渠进水闸");
		// beans.add(bean);
		// bean = new MainTainIrrigationBean();
		// bean.setMaintain("141团5连干渠进水闸");
		// beans.add(bean);
		// adapter.addData(beans, false);
		// listView.setAdapter(adapter);
		// beans = adapter.getData();

		return view;
	}

	private void init() {
		String str1 = "";
		try {
			str1 = java.net.URLEncoder.encode("3", "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		RequestParams param3 = new RequestParams(URL.findUserReleDisInfoApply
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
				Gson gson = new Gson();
				UserReleDisInfoBean fromJson = gson.fromJson(arg0,
						UserReleDisInfoBean.class);
				beens = fromJson.getAuthNameList();
				if (!CheckUtil.IsEmpty(beens)) {
					adapter.addData(beens, true);
					listView.setAdapter(adapter);
				}
				progressDialog.dismiss();
			}
		});
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view,
			final int position, long id) {
		SharedUtils.setParam(getActivity(), "DisEquID", beens.get(position)
				.getDisEquID());
		Intent intent = new Intent(getActivity(),
				MainTainGateInfoActivity.class);
		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.image_maintain_water_add:
			if (!NetStatusUtil.isNetValid(getActivity())) {
				Toast.makeText(getActivity(), "当前网络不可用！请检查您的网络状态！", Toast.LENGTH_SHORT)
						.show();
			} else {
				String str1 = "";
				String str2 = "";
				try {
					str1 = java.net.URLEncoder.encode("3", "UTF-8");
					str2 = java.net.URLEncoder.encode(ed_maintain_water_add
							.getText().toString().trim(), "UTF-8");
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (CheckUtil.IsEmpty(str2)) {
					Toast.makeText(getActivity(), "请输入需要查询的关键字！",
							Toast.LENGTH_SHORT).show();
				} else {
					RequestParams param3 = new RequestParams(
							URL.findUserReleDisInfoYet + str1 + "/" + str2); // 网址(请替换成实际的网址)
					// params.addQueryStringParameter("key", "value"); //
					// 参数(请替换成实际的参数与值)
					progressDialog = ProgressDialog.show(getActivity(),
							"Loading...", "Please wait...", true, false);
					JSONObject js_request2 = new JSONObject();
					try {
						param3.setAsJsonContent(true);
					} catch (Exception e) {
						e.printStackTrace();
						param3.setAsJsonContent(true);
					}// 根据实际需求添加相应键值对

					x.http().request(HttpMethod.GET, param3,
							new CommonCallback<String>() {
								@Override
								public void onCancelled(CancelledException arg0) {

								}

								// 注意:如果是自己onSuccess回调方法里写了一些导致程序崩溃的代码，也会回调道该方法，因此可以用以下方法区分是网络错误还是其他错误
								// 还有一点，网络超时也会也报成其他错误，还需具体打印出错误内容比较容易跟踪查看
								@Override
								public void onError(Throwable ex,
										boolean isOnCallback) {

									Toast.makeText(x.app(), ex.getMessage(),
											Toast.LENGTH_LONG).show();
									if (ex instanceof HttpException) { // 网络错误 
										HttpException httpEx = (HttpException) ex;
										int responseCode = httpEx.getCode();
										String responseMsg = httpEx
												.getMessage();
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
									Gson gson = new Gson();
									UserReleDisInfoBean fromJson = gson
											.fromJson(arg0,
													UserReleDisInfoBean.class);
									List<infoList> beens = fromJson
											.getAuthNameList();
									if (!CheckUtil.IsEmpty(beens)) {
										adapter.addData(beens, true);
										listView.setAdapter(adapter);
									}
									progressDialog.dismiss();
								}
							});
				}
			}
			break;

		default:
			break;
		}
	}

}
