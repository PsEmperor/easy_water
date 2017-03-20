package ps.emperor.easy_water.fragment;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import java.text.DecimalFormat;
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

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.entity.FindDisEquInfoOneBean;
import ps.emperor.easy_water.entity.PermissionListBeans.PermissionListBean;
import ps.emperor.easy_water.entity.UserReleIrrInfoToOneBean.infoList;
import ps.emperor.easy_water.entity.PermissionListBeans;
import ps.emperor.easy_water.entity.UserReleIrrInfoBean;
import ps.emperor.easy_water.entity.UserReleIrrInfoToOneBean;
import ps.emperor.easy_water.greendao.DBHelper;
import ps.emperor.easy_water.greendao.Irrigation;
import ps.emperor.easy_water.utils.CheckUtil;
import ps.emperor.easy_water.utils.NetStatusUtil;
import ps.emperor.easy_water.utils.PsUtils;
import ps.emperor.easy_water.utils.SharedUtils;
import ps.emperor.easy_water.utils.URL;
import ps.emperor.easy_water.view.MainActionBar;
import ps.emperor.easy_water.view.MainActionBars;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * 灌溉信息维护
 * 
 * @author 毛国江
 * @version 2016-6-3 上午10:08
 */
@SuppressLint("NewApi")
public class MainTainIrrigateInfoFragment extends Fragment implements
		OnClickListener {

	private LayoutInflater mInflater;
	private MainActionBars actionBar;
	private int group, value;
	private TextView tv_maintain_irrigat_info_id,
			text_maintain_irrigat_info_addres,
			text_maintain_irrigat_info_address,
			text_maintain_irrigat_info_area,
			text_maintain_irrigat_info_equipment, text_max_irrigat_group,
			text_max_orroagte_valve, text_filter,
			text_max_orroagte_restnight_start, text_max_orroagte_restnight_end,
			text_max_orroagte_season_start, text_max_orroagte_season_end;
	private Button btn_maintain_panoramic;
	// private List<Irrigation> irrigation;
	private DBHelper dbHelper;
	private String units;
	private ProgressDialog progressDialog;
	private List<infoList> beens;
	private List<Irrigation> irrigation;
	private List<PermissionListBean> PermissionListBean;
	private PermissionListBeans PermissionListBeans;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflater = inflater;
		View view = inflater.inflate(R.layout.fragment_maintain_irrigate_info,
				container, false);

		actionBar = (MainActionBars) view
				.findViewById(R.id.actionbar_maintain_irrigate_info);
		actionBar.setLeftIcon(R.drawable.btn_back_selector);
		actionBar.setRightText("编辑");
		actionBar.setTitle("灌溉信息维护");
		actionBar.setActionBarOnClickListener(this);

		tv_maintain_irrigat_info_id = (TextView) view
				.findViewById(R.id.tv_maintain_irrigat_info_id);
		text_maintain_irrigat_info_addres = (TextView) view
				.findViewById(R.id.text_maintain_irrigat_info_addres);
		text_maintain_irrigat_info_address = (TextView) view
				.findViewById(R.id.text_maintain_irrigat_info_address);
		text_maintain_irrigat_info_area = (TextView) view
				.findViewById(R.id.text_maintain_irrigat_info_area);
		text_maintain_irrigat_info_equipment = (TextView) view
				.findViewById(R.id.text_maintain_irrigat_info_equipment);
		text_max_irrigat_group = (TextView) view
				.findViewById(R.id.text_maintain_basic_info_max_irrigat_group_examine);
		text_max_orroagte_valve = (TextView) view
				.findViewById(R.id.text_maintain_basic_info_max_orroagte_valve_examine);
		text_filter = (TextView) view
				.findViewById(R.id.text_maintain_basic_info_filter_examine);
		text_max_orroagte_restnight_start = (TextView) view
				.findViewById(R.id.text_maintain_basic_info_max_orroagte_restnight_start_examine);
		text_max_orroagte_restnight_end = (TextView) view
				.findViewById(R.id.text_maintain_basic_info_max_orroagte_restnight_end_examine);
		text_max_orroagte_season_start = (TextView) view
				.findViewById(R.id.text_maintain_basic_info_max_orroagte_season_start_examine);
		text_max_orroagte_season_end = (TextView) view
				.findViewById(R.id.text_maintain_basic_info_max_orroagte_season_end_examine);
		dbHelper = DBHelper.getInstance(getActivity()); // 得到DBHelper对象

		Intent intent = getActivity().getIntent();
		units = intent.getStringExtra("units");

		if (NetStatusUtil.isNetValid(getActivity())) {
			init();
		} else {
			Toast.makeText(getActivity(), "当前网络不可用！请检查您的网络状态！",
					Toast.LENGTH_SHORT).show();
		}

		Gson gson = new Gson();
		PermissionListBeans = gson.fromJson(PsUtils.getShared(getActivity())
				.getString("permissionList", null), PermissionListBeans.class);
		PermissionListBean = PermissionListBeans.getPermissionList();

		String parten = "00";
		DecimalFormat decimal = new DecimalFormat(parten);
		// text_max_irrigat_group.setText(irrigation.get(0).getGroupnumber()+"");
		// text_max_orroagte_valve.setText(irrigation.get(0).getValuenumber()+"");
		// text_filter.setText(irrigation.get(0).getFilterHour()+"小时"+decimal.format(irrigation.get(0).getFilterMinute())+"分钟");
		// text_max_orroagte_restnight_start.setText(irrigation.get(0).getIsNightStartHour()
		// + ":"+decimal.format(irrigation.get(0).getIsNightStartMinute()));
		// text_max_orroagte_restnight_end.setText(irrigation.get(0).getIsNightEndHour()+":"+decimal.format(irrigation.get(0).getIsNightContinueMinute()));

		btn_maintain_panoramic = (Button) view
				.findViewById(R.id.btn_maintain_panoramic);
		btn_maintain_panoramic.setOnClickListener(this);

		return view;
	}

	private void init() {
		// irrigation = dbHelper.loadContinue(units);
		String str1 = (String) SharedUtils.getParam(getActivity(),
				"FirstDerviceID", "");
		try {
			str1 = java.net.URLEncoder.encode(str1, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		RequestParams param3 = new RequestParams(URL.findIrriUnitInfoToOne
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
				UserReleIrrInfoToOneBean fromJson = gson.fromJson(arg0,
						UserReleIrrInfoToOneBean.class);
				// authorizedBeen = new AuthorizedBeen();
				// authorizedBeen = gson.fromJson(arg0, AuthorizedBeen.class);
				beens = fromJson.getAuthNameList();
				for (infoList authNameListBean : beens) {
					if (!CheckUtil.IsEmpty(beens.get(0).getFirstDerviceID())) {
						tv_maintain_irrigat_info_id.setText(beens.get(0)
								.getFirstDerviceID());
					} else {
						tv_maintain_irrigat_info_id.setText("");
					}
					if (!CheckUtil.IsEmpty(beens.get(0).getLongitude())) {
						text_maintain_irrigat_info_addres.setText("S:"
								+ beens.get(0).getLongitude());
					} else {
						text_maintain_irrigat_info_addres.setText("S:0");
					}
					if (!CheckUtil.IsEmpty(beens.get(0).getLatitude())) {
						text_maintain_irrigat_info_address.setText("N:"
								+ beens.get(0).getLatitude());
					} else {
						text_maintain_irrigat_info_address.setText("N:0");
					}
					if (!CheckUtil.IsEmpty(beens.get(0).getArea())) {
						text_maintain_irrigat_info_area.setText(beens.get(0)
								.getArea());
					} else {
						text_maintain_irrigat_info_area.setText("0");
					}
					if (!CheckUtil.IsEmpty(beens.get(0).getSuperEqu())) {
						text_maintain_irrigat_info_equipment.setText(beens.get(
								0).getSuperEqu());
					} else {
						text_maintain_irrigat_info_equipment.setText("");
					}
					if (!CheckUtil.IsEmpty(beens.get(0).getMaxGroup())) {
						group = Integer.valueOf(beens.get(0).getMaxGroup());
						text_max_irrigat_group.setText(group + "");
					} else {
						text_max_irrigat_group.setText("0");
					}
					if (!CheckUtil.IsEmpty(beens.get(0).getValueNum())) {
						value = Integer.valueOf(beens.get(0).getValueNum());
						text_max_orroagte_valve.setText(value + "");
					} else {
						text_max_orroagte_valve.setText(0);
					}
					if (!CheckUtil.IsEmpty(beens.get(0).getFlushTime())) {
						text_filter.setText(beens.get(0).getFlushTime());
					} else {
						text_filter.setText("0");
					}
					if (!CheckUtil.IsEmpty(beens.get(0).getRestStart())) {
						text_max_orroagte_restnight_start.setText(beens.get(0)
								.getRestStart());
					} else {
						text_max_orroagte_restnight_start.setText("00:00");
					}
					if (!CheckUtil.IsEmpty(beens.get(0).getRestEnd())) {
						text_max_orroagte_restnight_end.setText(beens.get(0)
								.getRestEnd());
					} else {
						text_max_orroagte_restnight_end.setText("00:00");
					}
					if (!CheckUtil.IsEmpty(beens.get(0).getIrriSeasonStart())) {
						text_max_orroagte_season_start.setText(beens.get(0)
								.getIrriSeasonStart());
					} else {
						text_max_orroagte_season_start.setText("0000-00-00");
					}
					if (!CheckUtil.IsEmpty(beens.get(0).getIrriSeasonEnd())) {
						text_max_orroagte_season_end.setText(beens.get(0)
								.getIrriSeasonEnd());
					} else {
						text_max_orroagte_season_end.setText("0000-00-00");
					}
				}
				irrigation = dbHelper.loadContinue(beens.get(0)
						.getIrriUnitName());
				if (CheckUtil.IsEmpty(irrigation)) {
					Irrigation irrigation = new Irrigation();
					irrigation.setIrrigation(beens.get(0).getIrriUnitName());
					irrigation.setNContinue("00:00");
					irrigation.setNNumber(0);
					irrigation.setNRound(0);
					irrigation.setNightStart(beens.get(0).getRestStart());
					irrigation.setNightEnd(beens.get(0).getRestEnd());
					irrigation.setIsTimeLong(0);
					irrigation.setFirstDerviceID(beens.get(0)
							.getFirstDerviceID());
					irrigation.setLongitude(beens.get(0).getLongitude());
					irrigation.setLatitude(beens.get(0).getLatitude());
					irrigation.setArea(beens.get(0).getArea());
					irrigation.setSuperEqu(beens.get(0).getSuperEqu());
					irrigation.setGroupnumber(group);
					irrigation.setValuenumber(value);
					irrigation.setNightSwitch(0);
					irrigation.setLongSwitch(0);
					irrigation.setFlushtime(beens.get(0).getFlushTime());
					irrigation
							.setSeasonStrat(beens.get(0).getIrriSeasonStart());
					irrigation.setSeasonEnd(beens.get(0).getIrriSeasonEnd());
					dbHelper.saveSession(irrigation);
				}
				// //夜间休息时间
				// if
				// (!irrigation.get(0).getNightStart().equals(text_max_orroagte_restnight_start.getText().toString())||!(irrigation.get(0)
				// .getNightEnd()
				// .equals(text_max_orroagte_restnight_end.getText()
				// .toString()))) {
				// dbHelper.updateBasicTime(units,
				// beens.get(0).getRestStart(),
				// beens.get(0).getRestEnd());
				// }
				// //最大灌溉组数
				// if (!(irrigation.get(0).getGroupnumber() + "")
				// .equals(text_max_irrigat_group.getText().toString())) {
				// dbHelper.updateBasicGroup(units,group);
				// }
				// //最大灌溉阀门数
				// if (!(irrigation.get(0).getValuenumber() + "")
				// .equals(text_max_orroagte_valve.getText().toString())) {
				// dbHelper.updateBasicVlaue(units,value);
				// }
				// //过滤器
				// if
				// (!(irrigation.get(0).getFlushtime()).equals(beens.get(0).getFlushTime())){
				// dbHelper.updateBasicFilter(units,beens.get(0).getFlushTime());
				// }
				// //灌季
				// if((irrigation.get(0).getSeasonStrat()+"")
				// .equals(beens.get(0).getIrriSeasonStart())&&(irrigation.get(0).getSeasonEnd()+"").equals(beens.get(0).getIrriSeasonEnd())){
				// }else{
				// dbHelper.updateBasicSeason(units,beens.get(0).getIrriSeasonStart(),beens.get(0).getIrriSeasonEnd());
				// }
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
			getActivity().finish();
			break;
		case R.id.acitionbar_right:
			if (PermissionListBean.get(0).getOpertionStat().contains("5")) {
				MainTainIrrigationfarmarcropInfoFragment fragment3 = new MainTainIrrigationfarmarcropInfoFragment();
				// transaction.setCustomAnimations(R.anim.right_in,
				// R.anim.right_out);
				transaction.setCustomAnimations(
						R.anim.slide_fragment_horizontal_left_in,
						R.anim.slide_fragment_horizontal_right_out);
				transaction.replace(R.id.fragment_maintain_present_irrigate,
						fragment3, "main");
				transaction.commit();
			} else if (PermissionListBean.get(0).getOpertionStat()
					.contains("4")) {
				MainTainBasicCompileFragment fragment = new MainTainBasicCompileFragment();
				// transaction.setCustomAnimations(R.anim.right_in,
				// R.anim.right_out);
				SharedUtils.setParam(getActivity(), "isBasic", 1);
				transaction.setCustomAnimations(
						R.anim.slide_fragment_horizontal_left_in,
						R.anim.slide_fragment_horizontal_right_out);
				transaction.replace(R.id.fragment_maintain_present_irrigate,
						fragment, "main");
				transaction.commit();
			} else {
				Toast.makeText(getActivity(), "抱歉，您没有操作此步骤的权限！",
						Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.btn_maintain_panoramic:
			ApplyIrrigatePanoramicFragment fragment1 = new ApplyIrrigatePanoramicFragment();
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			transaction.setCustomAnimations(
					R.anim.slide_fragment_horizontal_left_in,
					R.anim.slide_fragment_horizontal_right_out);
			transaction.replace(R.id.fragment_maintain_present_irrigate,
					fragment1, "main");
			transaction.commit();
			break;
		default:
			break;
		}

	}

}
