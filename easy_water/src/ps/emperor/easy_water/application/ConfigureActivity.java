package ps.emperor.easy_water.application;

import java.util.ArrayList;

import java.util.List;

import org.xutils.x;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

import ps.emperor.easy_water.BaseActivity;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.application.activity.FirstPartActivity;
import ps.emperor.easy_water.application.activity.WaterGateActivity;
import ps.emperor.easy_water.application.adapter.Configure_Lv_Adapter;
import ps.emperor.easy_water.application.entity.Configure_Lv_item;
import ps.emperor.easy_water.utils.PsUtils;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
@ContentView(R.layout.activity_configure1)
public class ConfigureActivity extends BaseActivity implements
		OnItemClickListener, OnClickListener {

	private TextView tvt;
	private Button btr;
//	private ListView lvc;
	private Configure_Lv_Adapter cla;
	private List<Configure_Lv_item> list = new ArrayList<Configure_Lv_item>();
	private NfcAdapter nfc_ada;
	ProgressDialog pd;
	private LocationManager lm;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		x.view().inject(this);
		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		findView();

	}
	
	/**
	 * 打开NFC功能
	 */
	@Event(R.id.bt_nfc)
	private void onClickNFC(View v){
		nfc_ada = NfcAdapter.getDefaultAdapter(this);

		if (PsUtils.NfcIf(this, nfc_ada)) {
			pd = ProgressDialog.show(this, "", "请将手机背部靠近NFC标签");
			pd.setCancelable(true);
		}

	}
	/**
	 * 手动进入进行配置
	 */
	@Event(R.id.bt_hand)
	private void onClickHand(View v){
		AlertDialog.Builder ab = new AlertDialog.Builder(this)
		.setMessage("设备是否拥有蓝牙功能？").setPositiveButton("是", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(ConfigureActivity.this, "打开蓝牙进行配对~", 0).show();
				
				//开启蓝牙进行配对操作
				
				
				
				//进入配置界面
				Intent i0 = new Intent(ConfigureActivity.this,
						FirstPartActivity.class);
				startActivity(i0);
				
			}
		}).setNegativeButton("否", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(ConfigureActivity.this, "直接进入界面手动配置~", 0).show();
				
				//进入配置界面
				Intent i0 = new Intent(ConfigureActivity.this,
						FirstPartActivity.class);
				startActivity(i0);
			}
		});
		
		ab.show();
	}
	
	

	private void findView() {
		tvt = (TextView) findViewById(R.id.tv_text);
		btr = (Button) findViewById(R.id.bt_edit);
//		lvc = (ListView) findViewById(R.id.lv_configure);

		tvt.setText(R.string.title_configure);
		btr.setText(R.string.find);
//		btr.setVisibility(View.VISIBLE);

		cla = new Configure_Lv_Adapter(ConfigureActivity.this,
				R.layout.item_configure_lv, list);
		addData();
//		lvc.setAdapter(cla);
//		lvc.setOnItemClickListener(this);
		btr.setOnClickListener(this);
	}

	/**
	 * listView 集合添加数据
	 */
	private void addData() {
		Configure_Lv_item c1 = new Configure_Lv_item("首部",
				R.drawable.icon_arrow);
		Configure_Lv_item c2 = new Configure_Lv_item("阀控器/出地桩",
				R.drawable.icon_arrow);
		Configure_Lv_item c3 = new Configure_Lv_item("闸门",
				R.drawable.icon_arrow);

		list.add(c1);
//		list.add(c2);
		list.add(c3);

		cla.notifyDataSetChanged();

	}
	
	



	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		switch (position) {
		case 0:
			if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
				AlertDialog.Builder ab = new AlertDialog.Builder(this)
						.setMessage("请打开GPS定位模块,方便更好定位！~")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										startActivityForResult(
												new Intent(
														android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS),
												0);

									}
								})
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {

										Intent i0 = new Intent(
												ConfigureActivity.this,
												FirstPartActivity.class);
										startActivity(i0);

									}
								}).setCancelable(true);

				ab.show();

			} else {

				Intent i0 = new Intent(ConfigureActivity.this,
						FirstPartActivity.class);
				startActivity(i0);
			}

			break;
		case 1:
//			if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//				AlertDialog.Builder ab = new AlertDialog.Builder(this)
//						.setMessage("请打开GPS定位模块,方便更好定位！~")
//						.setPositiveButton("确定",
//								new DialogInterface.OnClickListener() {
//
//									@Override
//									public void onClick(DialogInterface dialog,
//											int which) {
//										startActivityForResult(
//												new Intent(
//														android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS),
//												0);
//
//									}
//								})
//						.setNegativeButton("取消",
//								new DialogInterface.OnClickListener() {
//
//									@Override
//									public void onClick(DialogInterface dialog,
//											int which) {
//
//										Intent i1 = new Intent(
//												ConfigureActivity.this,
//												ValveDeviceActivity.class);
//										startActivity(i1);
//
//									}
//								}).setCancelable(true);
//
//				ab.show();
//
//			} else {
//				Intent i1 = new Intent(ConfigureActivity.this,
//						ValveDeviceActivity.class);
//				startActivity(i1);
//			}
//
//			break;
//		case 2:
			if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
				AlertDialog.Builder ab = new AlertDialog.Builder(this)
						.setMessage("请打开GPS定位模块,方便更好定位！~")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										startActivityForResult(
												new Intent(
														android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS),
												0);

									}
								})
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										Intent i2 = new Intent(
												ConfigureActivity.this,
												WaterGateActivity.class);
										startActivity(i2);

									}
								}).setCancelable(true);

				ab.show();

			} else {
				Intent i2 = new Intent(ConfigureActivity.this,
						WaterGateActivity.class);
				startActivity(i2);
			}

			break;

		}

	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);

		if (pd != null) {

			pd.dismiss();
		}
		PsUtils.openBlue(ConfigureActivity.this);
		Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

		Ndef ndef = Ndef.get(tag);

		String ver = "card type:" + ndef.getType() + "\n card MaxSize:"
				+ ndef.getMaxSize() + "bytes\n";

		Toast.makeText(this, "版本:" + ver, 1).show();
		PsUtils.readCard(intent);
		
		//记住NFC标签数据
		
		
		//打开蓝牙配对
		
		
		//进入配置界面
		Intent i0 = new Intent(ConfigureActivity.this,
				FirstPartActivity.class);
		startActivity(i0);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_edit:
			// 检测是否开启nfc功能，没有则跳转界面
			nfc_ada = NfcAdapter.getDefaultAdapter(this);

			if (PsUtils.NfcIf(this, nfc_ada)) {
				pd = ProgressDialog.show(this, "", "请将手机背部靠近NFC标签");
				pd.setCancelable(true);
			}

			break;

		default:
			break;
		}

	}

}
