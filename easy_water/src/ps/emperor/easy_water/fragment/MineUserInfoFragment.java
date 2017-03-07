package ps.emperor.easy_water.fragment;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.SocketTimeoutException;

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
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import ps.emperor.easy_water.LoginActivity;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.entity.UserBean;
import ps.emperor.easy_water.entity.UserBean.infoList;
import ps.emperor.easy_water.utils.CheckUtil;
import ps.emperor.easy_water.utils.NetStatusUtil;
import ps.emperor.easy_water.utils.PutToFile;
import ps.emperor.easy_water.utils.SharedUtils;
import ps.emperor.easy_water.utils.URL;
import ps.emperor.easy_water.view.MainActionBar;
import ps.emperor.easy_water.view.MainActionBars;

/**
 * 个人信息
 * 
 * @author 毛国江
 * @version 2016-5-16 下午 14:45
 */
@SuppressLint("NewApi")
public class MineUserInfoFragment extends Fragment implements OnClickListener {
	private LayoutInflater mInflater;
	private PopupWindow popupWindow;
	private RelativeLayout ivHead, user_tel, info_units, info_role,
			info_user_name;
	private Bitmap bitmap;
	private ImageView image_info_head;
	private MainActionBars actionBar;
	private String names, units, tel, role;
	private EditText name;
	private TextView name_show, tv_info_units, tv_info_user_tel, tv_info_role;
	private ProgressDialog progressDialog;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflater = inflater;
		View view = inflater.inflate(R.layout.fragment_mine_user_info,
				container, false);

		actionBar = (MainActionBars) view
				.findViewById(R.id.actionbar_user_info);
		actionBar.setLeftIcon(R.drawable.btn_back_selector);
		actionBar.setRightText("保存");
		actionBar.setTitle("个人信息");
		actionBar.setActionBarOnClickListener(this);

		ivHead = (RelativeLayout) view.findViewById(R.id.layout_info_head);// 用户头像
		image_info_head = (ImageView) view.findViewById(R.id.image_info_ivhead); // 显示头像的image
		user_tel = (RelativeLayout) view
				.findViewById(R.id.layout_info_user_tel);// 用户电话
		info_units = (RelativeLayout) view.findViewById(R.id.layout_info_units);// 授权单位
		info_role = (RelativeLayout) view.findViewById(R.id.layout_info_role);// 角色申请
		info_user_name = (RelativeLayout) view
				.findViewById(R.id.layout_info_user_name);// 用户姓名

		info_user_name.setOnClickListener(this);
		info_role.setOnClickListener(this);
		info_units.setOnClickListener(this);
		ivHead.setOnClickListener(this);
		user_tel.setOnClickListener(this);

		tv_info_units = (TextView) view.findViewById(R.id.tv_info_units);
		tv_info_user_tel = (TextView) view.findViewById(R.id.tv_info_user_tel);
		tv_info_role = (TextView) view.findViewById(R.id.tv_info_role);

		if (PutToFile.getBitmapFromFile() != null) {
			image_info_head.setImageBitmap(PutToFile.getBitmapFromFile());
		}
		if (NetStatusUtil.isNetValid(getActivity())) {
			init();
		} else {
			Toast.makeText(getActivity(), "当前网络不可用！请检查您的网络状态！", Toast.LENGTH_SHORT)
					.show();
		}

		name_show = (TextView) view.findViewById(R.id.tv_info_user_name);
		name_show.setText(names);

		return view;
	}

	private void init() {
		names = (String) SharedUtils.getParam(getActivity(),
				"dialog_user_name", "	");
		String str = (String) SharedUtils.getParam(getActivity(), "ps", "12345");
		try {
			str = java.net.URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		RequestParams params = new RequestParams(URL.userInfo + "/" + str); // 网址(请替换成实际的网址)
		// params.addQueryStringParameter("key", "value"); // 参数(请替换成实际的参数与值)
		progressDialog = ProgressDialog.show(getActivity(), "Loading...",
				"Please wait...", true, false);
		JSONObject js_request = new JSONObject();
		try {
			params.setAsJsonContent(true);
			// params.setBodyContent("{\"userId\":\"\",\"userName\":\"\",\"userPhone\":\"\",\"fullName\":\"二狗子\",\"authID\":\"\",\"pathtoPhoto\":\"\"}");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// params.setAsJsonContent(true);
			// params.setBodyContent("Content-Type: application/json"+js_request.toString());
		}// 根据实际需求添加相应键值对

		x.http().request(HttpMethod.GET, params, new CommonCallback<String>() {
			@Override
			public void onCancelled(CancelledException arg0) {

			}

			// 注意:如果是自己onSuccess回调方法里写了一些导致程序崩溃的代码，也会回调道该方法，因此可以用以下方法区分是网络错误还是其他错误
			// 还有一点，网络超时也会也报成其他错误，还需具体打印出错误内容比较容易跟踪查看
			@Override
			public void onError(Throwable ex, boolean isOnCallback) {

				Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG)
						.show();
				if (ex instanceof HttpException) { // 网络错误
					HttpException httpEx = (HttpException) ex;
					int responseCode = httpEx.getCode();
					String errorResult = httpEx.getResult();
					Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT);
					// ...
					progressDialog.dismiss();
				} else { // 其他错误
					// ...
					Toast.makeText(getActivity(), "网络请求超时！", Toast.LENGTH_SHORT);
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
				UserBean fromJson = gson.fromJson(arg0, UserBean.class);
				List<infoList> infoList = fromJson.getAuthNameList();
				if (!CheckUtil.IsEmpty(infoList)) {
					names = infoList.get(0).getFullName();
					units = infoList.get(0).getAuthName();
					tel = infoList.get(0).getPhoneNum();
					role = infoList.get(0).getRoleName();
					if (!CheckUtil.IsEmpty(names)) {
						name_show.setText(names);
					} else {
						name_show.setText("");
					}
					if (!CheckUtil.IsEmpty(units)) {
						tv_info_units.setText(units);
					} else {
						tv_info_units.setText("");
					}
					if (!CheckUtil.IsEmpty(tel)) {
						tv_info_user_tel.setText(tel);
					} else {
						tv_info_user_tel.setText("");
					}
					if (!CheckUtil.IsEmpty(role)) {
						tv_info_role.setText(role);
					} else {
						tv_info_role.setText("");
					}
				}
				progressDialog.dismiss();
			}
		});

	}

	@SuppressLint("ResourceAsColor")
	@Override
	public void onClick(View v) {
		FragmentManager fgManager = getFragmentManager();
		FragmentTransaction transaction = fgManager.beginTransaction();
		switch (v.getId()) {
		case R.id.acitionbar_left:// 回退
			MinesFragment fragment = new MinesFragment();
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			// transaction
			// .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.setCustomAnimations(
					R.anim.slide_fragment_horizontal_right_in,
					R.anim.slide_fragment_horizontal_left_out);
			transaction.replace(R.id.fl, fragment, "main");
			transaction.commit();
//			fgManager.popBackStack();
			break;
		case R.id.acitionbar_right:
			if (!NetStatusUtil.isNetValid(getActivity())) {
				Toast.makeText(getActivity(), "当前网络不可用！请检查您的网络状态！", Toast.LENGTH_SHORT)
				.show();
			} else {
			RequestParams param1 = new RequestParams(URL.updateUserinfo); // 网址(请替换成实际的网址)
			// params.addQueryStringParameter("key", "value"); //
			// 参数(请替换成实际的参数与值)
			progressDialog = ProgressDialog.show(getActivity(), "Loading...",
					"Please wait...", true, false);
			JSONObject js_request = new JSONObject();
			try {
				param1.setAsJsonContent(true);
				js_request.put("userName", "12345");
				js_request.put("fullName", name_show.getText().toString());
				js_request.put("authID", "1");
				param1.setBodyContent(js_request.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				param1.setBodyContent("Content-Type: application/json"
						+ js_request.toString());
				e.printStackTrace();
				param1.setAsJsonContent(true);
			}// 根据实际需求添加相应键值对

			x.http().request(HttpMethod.PUT, param1,
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
							if (ex instanceof HttpException) { // 网络错误
								HttpException httpEx = (HttpException) ex;
								int responseCode = httpEx.getCode();
								String responseMsg = httpEx.getMessage();
								String errorResult = httpEx.getResult();
								Toast.makeText(getActivity(), "请求失败",
										Toast.LENGTH_SHORT);
								// ...
								progressDialog.dismiss();
							} else { // 其他错误
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
							Toast.makeText(getActivity(), "请求成功",
									Toast.LENGTH_SHORT);
							progressDialog.dismiss();
						}
					});
			}
			break;
		case R.id.layout_info_head:// 头像上传
			if (popupWindow == null) {
				View view = mInflater
						.inflate(R.layout.layout_accout_popu, null);
				popupWindow = new PopupWindow(view,
						ViewGroup.LayoutParams.MATCH_PARENT,
						ViewGroup.LayoutParams.WRAP_CONTENT);
				popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
				popupWindow.setFocusable(true);
				WindowManager.LayoutParams params = getActivity().getWindow()
						.getAttributes();
				params.alpha = 0.7f;
				getActivity().getWindow().setAttributes(params);
				popupWindow.setOnDismissListener(new OnDismissListener() {

					@Override
					public void onDismiss() {
						popupWindow.dismiss();
						popupWindow = null;
						WindowManager.LayoutParams params = getActivity()
								.getWindow().getAttributes();
						params.alpha = 1f;
						getActivity().getWindow().setAttributes(params);
					}
				});
				popupWindow.setBackgroundDrawable(new ColorDrawable());
				view.findViewById(R.id.text_user_info_photograph)
						.setOnClickListener(changlistener);
				view.findViewById(R.id.text_user_info_photo_album)
						.setOnClickListener(changlistener);
				view.findViewById(R.id.text_user_info_canel)
						.setOnClickListener(changlistener);
			}

			popupWindow.showAtLocation(
					getActivity().findViewById(R.id.account), Gravity.BOTTOM,
					0, 0);
			break;
		case R.id.layout_info_user_name: // 姓名弹出dialog
			Builder builder = new Builder(getActivity());

			final View contentview = LayoutInflater.from(getActivity())
					.inflate(R.layout.dialog_mine_control, null);
			builder.setPositiveButton("确定",
					new android.content.DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {

							name = (EditText) contentview
									.findViewById(R.id.edit_mine_user_name);
							String names = name.getText().toString().trim();
							if (checkNameChese(names) == true
									&& !CheckUtil.IsEmpty(names)) {
								name.setText(names);
								name_show.setText(names);
								SharedUtils.setParam(getActivity(),
										"dialog_user_name", names);
							} else {
								if (CheckUtil.IsEmpty(names)) {
									Toast.makeText(getActivity(), "姓名不能为空！",
											Toast.LENGTH_SHORT).show();
								} else {
									Toast.makeText(getActivity(), "请输入正确的姓名！",
											Toast.LENGTH_SHORT).show();
								}
							}
						}
					});
			builder.setView(contentview);
			builder.show();
			break;
		case R.id.layout_info_user_tel: // 修改电话
			MineUserTelFragment fragment1 = new MineUserTelFragment();
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			transaction.setCustomAnimations(
					R.anim.slide_fragment_horizontal_left_in,
					R.anim.slide_fragment_horizontal_right_out);
			transaction.add(R.id.fl, fragment1, "main").addToBackStack(null);
			transaction.commit();
			break;
		case R.id.layout_info_units: // 授权单位
			MineUserUnitFragment fragment2 = new MineUserUnitFragment();
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			transaction.setCustomAnimations(
					R.anim.slide_fragment_horizontal_left_in,
					R.anim.slide_fragment_horizontal_right_out);
			transaction.add(R.id.fl, fragment2, "main").addToBackStack(null);
			transaction.commit();
			break;
		case R.id.layout_info_role: // 角色申请
			if (CheckUtil.IsEmpty(units)) {

			} else {
				MineUserRoleFragment fragment3 = new MineUserRoleFragment();
				// transaction.setCustomAnimations(R.anim.right_in,
				// R.anim.right_out);
				transaction.setCustomAnimations(
						R.anim.slide_fragment_horizontal_left_in,
						R.anim.slide_fragment_horizontal_right_out);
				transaction.add(R.id.fl, fragment3, "main").addToBackStack(null);
				transaction.commit();
			}
			break;
		default:
			break;
		}
	}

	protected void setOnKeyListener(OnKeyListener onKeyListener) {
		// TODO Auto-generated method stub

	}

	private View.OnClickListener changlistener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.text_user_info_photograph:// 拍照
				Intent intent = new Intent();
				intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(intent, 100);
				popupWindow.dismiss();
				break;
			case R.id.text_user_info_photo_album:// 相册取出
				startActivityForResult(getPhonePic(), 1080);
				popupWindow.dismiss();
				break;
			case R.id.text_user_info_canel:
				popupWindow.dismiss();
				break;
			}
		}
	};

	public static Intent getPhonePic() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
		intent.setType("image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 70);
		intent.putExtra("outputY", 70);
		intent.putExtra("return-data", true);
		return intent;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 100) {
			if (resultCode == Activity.RESULT_OK) {
				Bundle bundle = data.getExtras();
				bitmap = (Bitmap) bundle.get("data");// 获取相机返回的图片
				image_info_head.setImageBitmap(bitmap);
				SharedUtils.setParam(getActivity(), "ivHead", bitmap);
				PutToFile.putToFile(bitmap);
			}
		} else if (requestCode == 1080) {
			if (!CheckUtil.IsEmpty(data)) {
				Bundle bundle = data.getExtras();
				if (!CheckUtil.IsEmpty(bundle)) {
					bitmap = (Bitmap) bundle.get("data");
					if (!CheckUtil.IsEmpty(bitmap)) {
						image_info_head.setImageBitmap(bitmap);
						SharedUtils.setParam(getActivity(), "ivHead", bitmap);
						PutToFile.putToFile(bitmap);
					} else {
						image_info_head
								.setImageResource(R.drawable.ic_launcher);
					}
				} else {
					Toast.makeText(getActivity(), "未选中任何图片", Toast.LENGTH_SHORT)
							.show();
				}
			}
		}
		// sendIcon();
	}

	/**
	 * 判定输入汉字
	 * 
	 * @param c
	 * @return
	 */
	public boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}

	/**
	 * 检测String是否全是中文
	 * 
	 * @param name
	 * @return
	 */
	public boolean checkNameChese(String name) {
		boolean res = true;
		char[] cTemp = name.toCharArray();
		for (int i = 0; i < name.length(); i++) {
			if (!isChinese(cTemp[i])) {
				res = false;
				break;
			}
		}
		return res;
	}
}
