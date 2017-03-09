package ps.emperor.easy_water.application.activity;

import java.util.ArrayList;
import java.util.List;

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
import ps.emperor.easy_water.application.fragment.DanKongFragment;
import ps.emperor.easy_water.utils.PsUtils;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.gson.Gson;

@ContentView(R.layout.activity_watergate)
public class WaterGateActivity extends BaseActivity implements OnClickListener {
	
	/*
	 * 上游水位
	 */
	@ViewInject(R.id.tb_up)
	private ToggleButton tb_up;
	@ViewInject(R.id.ll_upflow_range)
	private LinearLayout upflow;
	@ViewInject(R.id.et_writeUp)
	private EditText wup;
	
	/*
	 * 下游水位
	 */
	@ViewInject(R.id.tb_down)
	private ToggleButton tb_down;
	@ViewInject(R.id.ll_downflow_range)
	private LinearLayout downflow;
	@ViewInject(R.id.et_writeDown)
	private EditText wdown;
	
	
	
	@ViewInject(R.id.tv_dw)
	private TextView dw;
	@ViewInject(R.id.tv_sb)
	private TextView sb;
	
	private TextView tvt;
	private Button bte;
	@ViewInject(R.id.sp_k)
	private Spinner sp_k;
	private LocationManager lm;
	private int authID;
//	@ViewInject(R.id.tv_location)
//	private TextView tv_lo;
	
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
					Toast.makeText(WaterGateActivity.this, "保存完成", 0).show();
				} else {
					Toast.makeText(WaterGateActivity.this, "保存失败", 0).show();
				}

				break;

			case PsUtils.GET_UP_DEV:
				// 获取上级设备

				// ----------------------------没有完成解析
				String result1 = (String) msg.obj;
				Toast.makeText(WaterGateActivity.this,
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

	
	
	
	
	
	
	
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		x.view().inject(this);
		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		findView();
//		PsUtils.readGPS(lm, tv_lo);
		initKong();
	}

	/**
	 * 初始化孔数量
	 */
	private void initKong() {
		List<String> list = new ArrayList<String>();
		for (int i = 1; i < 11; i++) {
			list.add(i+"");
		}

		ArrayAdapter adapter = new ArrayAdapter<String>(this,
				R.layout.listview_custom_item, list);
		adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		sp_k.setAdapter(adapter);
	}

	/**
	 * 初始化控件
	 */
	private void findView() {
		tvt = (TextView) findViewById(R.id.tv_text);
		bte = (Button) findViewById(R.id.bt_edit);

		tvt.setText("配水设备");
		bte.setVisibility(View.VISIBLE);
		bte.setText("保存");
		dw.setOnClickListener(this);
		sb.setOnClickListener(this);
		bte.setOnClickListener(this);
		tb_up.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					upflow.setVisibility(View.VISIBLE);
				}else{
					upflow.setVisibility(View.GONE);
					wup.setText("");
				}
				
			}
		});
		
		
		tb_down.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					downflow.setVisibility(View.VISIBLE);
				}else{
					downflow.setVisibility(View.GONE);
					wdown.setText("");
					
				}
				
				
			}
		});
		
		
		
	}

	@Event(R.id.bt_dk)
	private void configDanKong(View v) {
		// 进入单孔配置fragment
		DanKongFragment dkf = new DanKongFragment();
		getFragmentManager().beginTransaction().replace(R.id.con, dkf)
				.addToBackStack(null).commit();
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
			
		case R.id.tv_sb:
			
			
			Intent intent = new Intent(WaterGateActivity.this,
					GetUpDeviceActivity.class);
			startActivityForResult(intent, 1);
			
			break;
			
			
		case R.id.bt_edit:
			//添加保存操作-----未写
			
			
			
			
			
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
				sb.setText(back);
				
			}

			break;

		default:
			break;
		}

	}
	
	
	
	
	
	
	
}
