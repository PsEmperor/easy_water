package ps.emperor.easy_water.application.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import ps.emperor.easy_water.BaseActivity;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.application.entity.AuthUnitBeen;
import ps.emperor.easy_water.application.entity.AuthUnitBeen.InfoListBean;
import ps.emperor.easy_water.application.entity.BaseBeen;
import ps.emperor.easy_water.application.entity.SearchBeen.IrriListBean;
import ps.emperor.easy_water.utils.PsUtils;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

@ContentView(R.layout.activity_firstpart)
public class FirstPartActivity extends BaseActivity implements OnClickListener {

	// 授权单位
	@ViewInject(R.id.tv_dw)
	private TextView dw;

	@ViewInject(R.id.et_mj)
	private EditText mj;
	@ViewInject(R.id.et_location1)
	private EditText jd;
	@ViewInject(R.id.et_location2)
	private EditText wd;
	@ViewInject(R.id.tv_authUnit)
	private TextView tv_au;

	@ViewInject(R.id.et_fk)
	private EditText fk;
	@ViewInject(R.id.et_mc)
	private EditText irrName;
	@ViewInject(R.id.bt_inFirstDevice)
	private Button bif;
	@ViewInject(R.id.sp_lb)
	private Spinner sp;
	private TextView tvt;
	private Button bte;
	@ViewInject(R.id.et_id)
	private EditText eid;
	private LocationManager lm;
	private Criteria c;
	private String bestProvider;
	@ViewInject(R.id.tv_location)
	private TextView tv_lo;
	// @ViewInject(R.id.tv_change)
	// private Button tvc;
	// 水源
	@ViewInject(R.id.ll_sy)
	private LinearLayout ll_sy;
	@ViewInject(R.id.tv_qd)
	private TextView tv_sy;
	// 轮灌
	@ViewInject(R.id.tv_lg)
	private TextView tv_lg;
	@ViewInject(R.id.ll_lg)
	private LinearLayout ll_lg;
	@ViewInject(R.id.tv_up_device)
	private TextView upS;
	private ProgressDialog pd_edit;
	private int authID;

	private Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case PsUtils.SAVE_INFO:
				// 添加解析判断
				String result = (String) msg.obj;
				// Toast.makeText(FirstPartActivity.this,
				// "信息为-----------："+result, 0).show();
				System.out.println("信息为-----------：" + result);
				Gson g = new Gson();
				BaseBeen bb = g.fromJson(result, BaseBeen.class);
				String code = bb.getCode();

				if (code.equals("1")) {
					Toast.makeText(FirstPartActivity.this, "保存完成", 0).show();
				} else {
					Toast.makeText(FirstPartActivity.this, "保存失败", 0).show();
				}

				break;

			case PsUtils.GET_UP_DEV:
				// 获取上级设备

				// ----------------------------没有完成解析
				String result1 = (String) msg.obj;
				Toast.makeText(FirstPartActivity.this,
						"名字为-----------：" + result1, 0).show();

				break;

			case PsUtils.GET_UNIT_NAME:

				// 获取授权ID

				String re = (String) msg.obj;
				if (re.equals(null)) {
					dw.setText("未知");
					authID = 1;
					return;
				}

				Gson gs = new Gson();
				AuthUnitBeen fj = gs.fromJson(re, AuthUnitBeen.class);

				InfoListBean ilb = fj.getInfoList().get(0);
				dw.setText(ilb.getAuthName());
				authID = ilb.getAuthID();

				break;

			default:
				break;
			}
		};
	};

	private IrriListBean ib;
	int tag3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		x.view().inject(this);
		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		Intent i = getIntent();
		if (i != null) {

			ib = (IrriListBean) i.getSerializableExtra("sbContent");
		}

		if (ib != null) {

			String arr;
			String arr1 = "渠道";
			if (ib.getIrriMothed() == 0) {
				arr = "支管轮灌";
			} else {

				arr = "辅管轮灌";
			}

			if (ib.getClassID() == 0) {

				arr1 = "渠道";

			} else if (ib.getClassID() == 1) {
				arr1 = "机井";

			} else if (ib.getClassID() == 2) {
				arr1 = "自压";
			}

			if (ib.getIrriEquTpye().equals("虚拟首部")) {
				tag3 = 0;
			} else {
				int ta3 = 1;
			}

			sp.setSelection(tag3);
			irrName.setText(ib.getIrriUnitName());
			eid.setText(ib.getFirstDerviceID());
			jd.setText(ib.getLongitude() + "");
			wd.setText(ib.getLatitude() + "");
			upS.setText(ib.getSuperEqu());
			tv_lg.setText(arr);
			tv_sy.setText(arr1);
			fk.setText(ib.getValueControlNum() + "");
			dw.setText(ib.getAuthName());
			mj.setText(ib.getArea() + "");

		}
		initfm();
		findView();

		// 获取模糊查询条件，添加到灌溉单元界面内
		Intent intent = getIntent();
		IrriListBean ilb = (IrriListBean) intent
				.getSerializableExtra("sbContent");

		if (sp.getSelectedItem().toString().equals("虚拟首部")) {
			bif.setVisibility(View.GONE);

		}
		PsUtils.readGPS(lm, tv_lo);

	}

	/**
	 * 初始化控件
	 */
	private void findView() {
		tvt = (TextView) findViewById(R.id.tv_text);
		bte = (Button) findViewById(R.id.bt_edit);

		tvt.setText("灌溉单元配置");
		bte.setVisibility(View.VISIBLE);
		bte.setText("保存");
		bte.setOnClickListener(this);
		ll_sy.setOnClickListener(this);
		ll_lg.setOnClickListener(this);
		upS.setOnClickListener(this);
		tv_au.setOnClickListener(this);
		dw.setOnClickListener(this);
		sp.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case 0:
					// 隐藏首部设备选项
					bif.setVisibility(View.GONE);
					break;
				case 1:
					bif.setVisibility(View.VISIBLE);
					break;

				default:
					break;
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
	}

	/**
	 * 初始化设备类别
	 */
	private void initfm() {
		List<String> list = new ArrayList<String>();
		list.add("虚拟首部");
		list.add("实体首部");

		ArrayAdapter adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, list);
		adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		sp.setAdapter(adapter);
	}

	/**
	 * 进入阀控器配置
	 * 
	 * @param v
	 */
	@Event(R.id.bt_inConDevice)
	private void inValueDevice(View v) {
		Intent i = new Intent(this, ValveDeviceActivity1.class);
		// 父类名称 隐藏授权单位、上级设备为灌溉单元名称
		i.putExtra("super", "FirstPartActivity");
		// 灌溉单元名称
		i.putExtra("irrName", irrName.getText().toString());
		// 轮灌方式 辅管--支管 支管--分干
		i.putExtra("irrMode", tv_lg.getText().toString());
		// 类别 虚拟--短信阀控器 实体--全部
		i.putExtra("classType", sp.getSelectedItem().toString());
		// 阀控器数量
		i.putExtra("valueNum", fk.getText().toString());
		// 写入传入标记
		i.putExtra("from", 1);
		// 写入设备id
		i.putExtra("ID", eid.getText().toString());

		startActivity(i);
	}

	/**
	 * 进入首部设备activity
	 * 
	 * @param v
	 */
	@Event(R.id.bt_inFirstDevice)
	private void inFirstDevice(View v) {
		String content = tv_sy.getText().toString();

		// 首部类型
		String type = sp.getSelectedItem().toString();

		String id = eid.getText().toString();

		Intent i = new Intent(this, FirstPartDeviceActivity.class);
		i.putExtra("water_type", content);
		i.putExtra("fid", id);
		i.putExtra("ftype", type);
		startActivity(i);
	}

	/**
	 * 弹出框
	 * 
	 * @param arr
	 * @param tv
	 */
	private void eject(final String[] arr, final TextView tv) {
		AlertDialog.Builder b = new AlertDialog.Builder(FirstPartActivity.this)
				.setItems(arr, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						tv.setText(arr[which]);
						switch (arr[which]) {
						case "虚拟首部":
							Toast.makeText(FirstPartActivity.this, "虚拟首部", 0)
									.show();
							break;
						case "实体首部":
							Toast.makeText(FirstPartActivity.this, "实体首部", 0)
									.show();
							break;
						case "渠道":
							Toast.makeText(FirstPartActivity.this, "闸门控制器", 0)
									.show();
							break;
						case "机井":
							Toast.makeText(FirstPartActivity.this, "闸门控制器", 0)
									.show();
							break;
						case "自压":
							Toast.makeText(FirstPartActivity.this, "自压", 0)
									.show();
							break;

						}

					}
				});
		b.show();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_dw:
			// 获取授权单位
			SharedPreferences sh = PsUtils.getShared(this);
			String url = String.format(PsUtils.get_auth,
					sh.getString("user", ""));

			RequestParams rrp = new RequestParams(url);

			PsUtils.send(rrp, HttpMethod.GET, handler, this, "获取授权名称中。。。",
					PsUtils.GET_UNIT_NAME);

			break;

		case R.id.ll_sy:
			final String[] arr = { "渠道", "机井", "自压" };
			eject(arr, tv_sy);

			break;
		case R.id.ll_lg:
			final String[] arr1 = { "支管轮灌", "辅管轮灌" };
			eject(arr1, tv_lg, null);

			break;

		case R.id.tv_up_device:
			// 获取上级设备
			Intent intent = new Intent(FirstPartActivity.this,
					GetUpDeviceActivity.class);
			startActivityForResult(intent, 1);
			break;

		case R.id.bt_edit:

			if (sp.getSelectedItem().toString().equals("虚拟首部")) {
				// 添加限制设备id校验

				boolean checkPhoneNum = PsUtils.checkPhoneNum(eid.getText()
						.toString());
				if (!checkPhoneNum) {

					AlertDialog.Builder b = new Builder(FirstPartActivity.this);
					b.setMessage("虚拟首部时ID应为手机号码，请重新输入！！！~");
					b.setPositiveButton("确定", new Dialog.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							eid.requestFocus();

						}
					});
					b.setCancelable(false);
					b.show();

					return;
				}

			}

			RequestParams rp = new RequestParams(PsUtils.add_irr_info);
			// String tag ;
			String tag2;
			String tag3 = "0";
			// if(sp.getSelectedItem().toString().equals("虚拟首部")){
			// tag = "0";
			// }else{
			// tag = "1";
			// }

			if (tv_lg.getText().toString().equals("支管轮灌")) {
				tag2 = "0";
			} else {
				tag2 = "1";
			}

			if (tv_sy.getText().toString().equals("渠道")) {
				tag3 = "0";

			} else if (tv_sy.getText().toString().equals("机井")) {
				tag3 = "1";

			} else if (tv_sy.getText().toString().equals("自压")) {
				tag3 = "2";
			}

			JSONObject jo = new JSONObject();

			try {
				jo.put("irriEquTpye", sp.getSelectedItem().toString()); // 类别
																		// 0虚拟
																		// 1实体
				jo.put("irriUnitName", irrName.getText().toString());// 灌溉单元名称
				jo.put("firstDerviceID", eid.getText().toString());
				jo.put("longitude", jd.getText().toString());
				jo.put("latitude", wd.getText().toString());
				jo.put("authID", authID); // 授权单位暂时为假数据
				jo.put("superEqu", upS.getText().toString());// 上级设备
				jo.put("area", mj.getText().toString()); // 面积
				jo.put("irriMothed", tag2); // 轮灌方式 0是支管 1是辅管
				jo.put("classID", tag3); // 水源类型 0是渠道，1，机井，2，自压
				jo.put("valueControlNum", fk.getText().toString()); // 闸门数量
				rp.setBodyContent(jo.toString());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}// ID

			// 上传灌溉单元配置
			PsUtils.send(rp, HttpMethod.POST, handler, FirstPartActivity.this,
					"上传保存数据中。。。", PsUtils.SAVE_INFO);

			break;

		default:
			break;
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		switch (requestCode) {
		case 1:
			if (resultCode == RESULT_OK) {
				// 获取模糊搜索接口返回的数据
				String back = data.getStringExtra("up_dev");
				// 设置上级设备显示名称
				upS.setText(back);
			}

			break;

		default:
			break;
		}

	}

	/**
	 * 弹出框
	 * 
	 * @param arr
	 * @param tv
	 */
	private void eject(final String[] arr, final TextView tv,
			final Fragment fragment) {
		AlertDialog.Builder b = new AlertDialog.Builder(FirstPartActivity.this)
				.setItems(arr, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						tv.setText(arr[which]);
						switch (which) {
						case 0:
							if (fragment != null) {
								getFragmentManager().beginTransaction()
										.replace(R.id.ff_pump, fragment)
										.addToBackStack(null).commit();
							}

							break;
						case 1:
							break;

						default:
							break;
						}

					}
				});
		b.show();
	}

}
