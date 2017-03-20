package ps.emperor.easy_water.fragment;

import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.ex.HttpException;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.utils.CheckUtil;
import ps.emperor.easy_water.utils.NetStatusUtil;
import ps.emperor.easy_water.utils.SharedUtils;
import ps.emperor.easy_water.utils.URL;
import ps.emperor.easy_water.view.MainActionBar;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;
import android.view.View.OnClickListener;

/**
 * 设置
 * 
 * @author 毛国江
 * @version 2016-5-18 上午11:28
 */
@SuppressLint("NewApi")
public class MineSettingFragment extends Fragment implements OnClickListener,
		OnCheckedChangeListener {

	private LayoutInflater mInflater;
	private MainActionBar actionBar;
	private ToggleButton bt_msg, bt_wifi, bt_connection;
	private Boolean isMsg, isWifi, isConnection;
	private ProgressDialog progressDialog;
	private String str;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflater = inflater;
		View view = inflater.inflate(R.layout.fragment_mine_setting, container,
				false);

		init();

		actionBar = (MainActionBar) view
				.findViewById(R.id.actionbar_mine_setting);
		actionBar.setLeftIcon(R.drawable.btn_back_selector);
		actionBar.setRightGone();
		actionBar.setTitle("设置");
		actionBar.setActionBarOnClickListener(this);

		bt_msg = (ToggleButton) view.findViewById(R.id.toggle_setting_new_msg);
		bt_wifi = (ToggleButton) view.findViewById(R.id.toggle_setting_wifi);
		bt_connection = (ToggleButton) view
				.findViewById(R.id.toggle_setting_connection);

		bt_msg.setChecked(isMsg);
		bt_wifi.setChecked(isWifi);
		bt_connection.setChecked(isConnection);

		bt_msg.setOnCheckedChangeListener(this);
		bt_wifi.setOnCheckedChangeListener(this);
		bt_connection.setOnCheckedChangeListener(this);
		
		bt_msg.setOnClickListener(this);
		// if(!isMsg){
		// initToggle = false;
		// }
		return view;
	}

	private void init() {
		isMsg = (Boolean) SharedUtils.getParam(getActivity(), "msg", false);
		isWifi = (Boolean) SharedUtils.getParam(getActivity(), "wifi", false);
		isConnection = (Boolean) SharedUtils.getParam(getActivity(),
				"connection", false);
	}

	@Override
	public void onClick(View v) {
		FragmentManager fgManager = getFragmentManager();
		FragmentTransaction transaction = fgManager.beginTransaction();
		switch (v.getId()) {
		case R.id.acitionbar_left:
			MinesFragment fragment = new MinesFragment();
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			transaction.setCustomAnimations(
					R.anim.slide_fragment_horizontal_right_in,
					R.anim.slide_fragment_horizontal_left_out);
			transaction.replace(R.id.fl, fragment, "main");
			transaction.commit();
			break;
		case R.id.tv_setting_logo:
			Toast.makeText(getActivity(), "33333333", Toast.LENGTH_SHORT)
					.show();
			break;
		case R.id.toggle_setting_new_msg:
			if (!NetStatusUtil.isNetValid(getActivity())) {
				Toast.makeText(getActivity(), "当前网络不可用！请检查您的网络状态！",
						Toast.LENGTH_SHORT).show();
			}else {
				str = (String) SharedUtils.getParam(getActivity(), "userId",
						"3");
				RequestParams param2 = new RequestParams(URL.updateAPPSettings); // 网址(请替换成实际的网址)
				// params.addQueryStringParameter("key", "value"); //
				// 参数(请替换成实际的参数与值)
				progressDialog = ProgressDialog.show(getActivity(),
						"Loading...", "Please wait...", true, false);
				JSONObject js_request = new JSONObject();
				try {
					param2.setAsJsonContent(true);
					js_request.put("userID", str);
					if(isMsg == true){
						js_request.put("pushStat", 1);
					}else{
						js_request.put("pushStat", 0);
					}
					param2.setBodyContent(js_request.toString());
				} catch (Exception e) {
					e.printStackTrace();
					param2.setAsJsonContent(true);
				}// 根据实际需求添加相应键值对

				x.http().request(HttpMethod.PUT, param2,
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
								progressDialog.dismiss();
								Toast.makeText(getActivity(), "成功",
										Toast.LENGTH_SHORT).show();
							}
						});
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch (buttonView.getId()) {
		case R.id.toggle_setting_new_msg:// 规律
			// Toast.makeText(getActivity(), "开关--" + isChecked,
			// Toast.LENGTH_SHORT).show();
			SharedUtils.setParam(getActivity(), "msg", isChecked);
			break;
		case R.id.toggle_setting_wifi:// 规律
			// Toast.makeText(getActivity(), "开关--" + isChecked,
			// Toast.LENGTH_SHORT).show();
			SharedUtils.setParam(getActivity(), "wifi", isChecked);
			break;
		case R.id.toggle_setting_connection:// 规律
			// Toast.makeText(getActivity(), "开关--" + isChecked,
			// Toast.LENGTH_SHORT).show();
			SharedUtils.setParam(getActivity(), "connection", isChecked);
			break;
		}
	}
}
