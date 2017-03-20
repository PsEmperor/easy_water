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
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.utils.CheckUtil;
import ps.emperor.easy_water.utils.NetStatusUtil;
import ps.emperor.easy_water.utils.SharedUtils;
import ps.emperor.easy_water.utils.URL;
import ps.emperor.easy_water.view.MainActionBar;
import ps.emperor.easy_water.view.MainActionBars;

/**
 * 意见与反馈
 * 
 * @author 毛国江
 * @version 2016-5-18 下午15:56
 */

@SuppressLint("NewApi")
public class MineCommentsFragmt extends Fragment implements OnClickListener {
	private MainActionBars actionBar;
	private LayoutInflater mInflater;
	private EditText ed_comments;
	private ProgressDialog progressDialog;
	private String str;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflater = inflater;
		View view = inflater.inflate(R.layout.fragment_mine_comments,
				container, false);

		actionBar = (MainActionBars) view
				.findViewById(R.id.actionbar_mine_comments);
		actionBar.setLeftIcon(R.drawable.btn_back_selector);
		actionBar.setRightText("保存");
		actionBar.setTitle("意见与反馈");
		actionBar.setActionBarOnClickListener(this);

		ed_comments = (EditText) view.findViewById(R.id.edit_mine_comments);
		return view;
	}

	@Override
	public void onClick(View v) {
		FragmentManager fgManager = getFragmentManager();
		FragmentTransaction transaction = fgManager.beginTransaction();
		switch (v.getId()) {
		case R.id.acitionbar_left:
			((InputMethodManager) ed_comments.getContext().getSystemService(
					getActivity().INPUT_METHOD_SERVICE))
					.hideSoftInputFromWindow(ed_comments.getWindowToken(),
							InputMethodManager.HIDE_NOT_ALWAYS);
			MinesFragment fragment = new MinesFragment();
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			transaction.setCustomAnimations(
					R.anim.slide_fragment_horizontal_right_in,
					R.anim.slide_fragment_horizontal_left_out);
			transaction.replace(R.id.fl, fragment, "main");
			transaction.commit();
			break;
		case R.id.acitionbar_right:
			if (!NetStatusUtil.isNetValid(getActivity())) {
				Toast.makeText(getActivity(), "当前网络不可用！请检查您的网络状态！",
						Toast.LENGTH_SHORT).show();
			} else if (CheckUtil.IsEmpty(ed_comments.getText().toString()
					.trim())) {
				Toast.makeText(getActivity(), "输入的内容不可为空！请输入意见后再次尝试此操作！",
						Toast.LENGTH_SHORT).show();
			} else {
				str = (String) SharedUtils.getParam(getActivity(), "userId",
						"3");
				RequestParams param2 = new RequestParams(URL.userFeedback); // 网址(请替换成实际的网址)
				// params.addQueryStringParameter("key", "value"); //
				// 参数(请替换成实际的参数与值)
				progressDialog = ProgressDialog.show(getActivity(),
						"Loading...", "Please wait...", true, false);
				JSONObject js_request = new JSONObject();
				try {
					param2.setAsJsonContent(true);
					js_request.put("userID", str);
					js_request.put("message", ed_comments.getText().toString()
							.trim());
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
}
