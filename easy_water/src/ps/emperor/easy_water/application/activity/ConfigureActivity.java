package ps.emperor.easy_water.application.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.UUID;

import org.xutils.x;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import ps.emperor.easy_water.BaseActivity;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.application.adapter.Configure_Lv_Adapter;
import ps.emperor.easy_water.application.entity.Configure_Lv_item;
import ps.emperor.easy_water.application.qrcode.MipcaActivityCapture;
import ps.emperor.easy_water.utils.PsUtils;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.customlaylout.BluetoothLeService;
import com.example.customlaylout.Utils;



@ContentView(R.layout.activity_configure2)
public class ConfigureActivity extends BaseActivity implements
		OnItemClickListener, OnClickListener {
	
	private final static int SCANNIN_GREQUEST_CODE =9386;
	@ViewInject(R.id.bt_scan)
	private Button scan;
	@ViewInject(R.id.bt_edit)
	private Button edit;
	@ViewInject(R.id.bt_blue)
	private Button ly;
	private TextView tvt;
//	private ListView lvc;
	private Configure_Lv_Adapter cla;
	private List<Configure_Lv_item> list = new ArrayList<Configure_Lv_item>();
	private NfcAdapter nfc_ada;
	ProgressDialog pd;

	private LocationManager lm;

	private Context context = ConfigureActivity.this;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		x.view().inject(this);
		
		checkBluetooth();
		Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
		bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
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
//		AlertDialog.Builder ab = new AlertDialog.Builder(this)
//		.setMessage("设备是否拥有蓝牙功能？").setPositiveButton("是", new DialogInterface.OnClickListener() {
//			
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				Toast.makeText(ConfigureActivity.this, "打开蓝牙进行配对~", 0).show();
//				
//				//开启蓝牙进行配对操作
//				
//				
//				
//				//进入配置界面
//				Intent i0 = new Intent(ConfigureActivity.this,
//						FirstPartActivity.class);
//				startActivity(i0);
//				
//			}
//		}).setNegativeButton("否", new DialogInterface.OnClickListener() {
//			
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				Toast.makeText(ConfigureActivity.this, "直接进入界面手动配置~", 0).show();
//				
//				//进入配置界面
//				Intent i0 = new Intent(ConfigureActivity.this,
//						FirstPartActivity.class);
//				startActivity(i0);
//			}
//		});
//		
//		ab.show();
		
		
		Intent i = new Intent(ConfigureActivity.this,
				HandSelectActivity.class);
		startActivity(i);
	}
	
	

	private void findView() {
		tvt = (TextView) findViewById(R.id.tv_text);
//		btr = (Button) findViewById(R.id.bt_edit);
//		lvc = (ListView) findViewById(R.id.lv_configure);

		tvt.setText(R.string.title_configure);
		edit.setVisibility(View.VISIBLE);
		edit.setText(R.string.find);
//		btr.setVisibility(View.VISIBLE);

		cla = new Configure_Lv_Adapter(ConfigureActivity.this,
				R.layout.item_configure_lv, list);
		addData();
//		lvc.setAdapter(cla);
//		lvc.setOnItemClickListener(this);
		edit.setOnClickListener(this);
		ly.setOnClickListener(this);
		scan.setOnClickListener(this);
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
		
		//暂时取消记住NFC标签数据
		//打开蓝牙配对连接
		
		
		
		
		
		
		
		//进入配置界面
		Intent i0 = new Intent(ConfigureActivity.this,
				FirstPartActivity.class);
		startActivity(i0);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_edit:
//			Toast.makeText(this, "跳转到搜索界面。。未写", 0).show();
			Intent p = new Intent(ConfigureActivity.this,
					ConfigureSearchActivity.class);                                  
			startActivity(p);

			break;
//		case R.id.bt_nfc:
//			PsUtils.openBlue(ConfigureActivity.this);
//			Toast.makeText(context, "点击了NFC按钮", 0).show();
//			
//			break;
//		
		case R.id.bt_scan:
			Intent intent = new Intent();
			intent.setClass(ConfigureActivity.this, MipcaActivityCapture.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
			break;
			
		case R.id.bt_blue:
			
			//直接蓝牙连接
			PsUtils.openBlue(ConfigureActivity.this);
			Toast.makeText(context, "点击了蓝牙按钮", 0).show();
			read(characteristic4);
			
			
			
			break;
		

		default:
			break;
		}

	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case SCANNIN_GREQUEST_CODE:
			if (resultCode == RESULT_OK) {
				Bundle bundle = data.getExtras();
				//扫描得到的内容
				String content = bundle.getString("result");
				
				String substring = content.substring(2,4);
				
				Toast.makeText(this, "扫描到的内容为："+content+"............:"+substring, 1).show();
				System.out.println("扫描到的内容为："+content+"............:"+substring);
				
				//根据类型跳转到对应设备配置
				
				
				
				
				
				
			

			}
			break;
		}
	}
	
	
	private Timer t;
	@Override
	protected void onResume() {
		super.onResume();
		

		if (!mBluetoothAdapter.isEnabled()) {
			Intent enableBtIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
		}
		registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
		if (mBluetoothLeService != null) {
			final boolean result = mBluetoothLeService.connect(address);
			Log.d(TAG, "Connect request result=" + result);
		}

		scanLeDevice(true);
		
	}
	

	
	@Override
	protected void onPause() {
		super.onPause();
		scanLeDevice(false);
		
	}
	
	private Handler mHandler = new Handler();
	@SuppressLint("NewApi")
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
	private void scanLeDevice(final boolean enable) {
		if (enable) {
			// Stops scanning after a pre-defined scan period.


			mScanning = true;
			mBluetoothAdapter.startLeScan(mLeScanCallback);
		} else {
			mScanning = false;
			mBluetoothAdapter.stopLeScan(mLeScanCallback);
			
		}

	}
	
	
	// Device scan callback.
	@SuppressLint("NewApi")
	private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {

		@Override
		public void onLeScan(final BluetoothDevice device, int rssi,
				byte[] scanRecord) {
			runOnUiThread(new Runnable() {
				@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
				@Override
				public void run() {
					if (device == null)
						return;

					if (Utils.name.equals(device.getName())) {
						// 连接指定地址 device.getAddress()

						address = device.getAddress();
						System.out.println("address=======：" + address
								+ "      deviceName======" + device.getName());
						if (mBluetoothLeService == null)
							return;
						boolean connect = mBluetoothLeService.connect(address);

				
					}

					mBluetoothAdapter.stopLeScan(mLeScanCallback);
					mScanning = false;
					
				}
			});
		}
	};
		
		
		
		/**
		 * 检测蓝牙是否存在，有则开启，没有则退出程序
		 */
		@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
		private void checkBluetooth() {
			// Use this check to determine whether BLE is supported on the device.
			// Then you can
			// selectively disable BLE-related features.
			// 是否支持低功耗蓝牙。。不支持退出
			if (!getPackageManager().hasSystemFeature(
					PackageManager.FEATURE_BLUETOOTH_LE)) {
				Toast.makeText(this, "不支持低功耗蓝牙，请更换设备！～", Toast.LENGTH_SHORT).show();
				finish();
			}

			// Initializes a Bluetooth adapter. For API level 18 and above, get a
			// reference to
			// BluetoothAdapter through BluetoothManager.
			// 获取BluetoothAdapter的对象
			final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
			mBluetoothAdapter = bluetoothManager.getAdapter();

			// Checks if Bluetooth is supported on the device.
			// 检测设备是否支持蓝牙
			if (mBluetoothAdapter == null) {
				Toast.makeText(this, "不支持蓝牙，请更换设备！～", Toast.LENGTH_SHORT).show();
				finish();
				return;
			}

		}
	
		private boolean mScanning;
		private BluetoothAdapter mBluetoothAdapter;
		// 目标地址
		private String address;
		private BluetoothLeService mBluetoothLeService;
		
		private static String TAG = ConfigureActivity.class.getSimpleName();
		
		private static final int REQUEST_ENABLE_BT = 110;
		// 服务
		BluetoothGattService supportedGattServices;
		// 特征
		BluetoothGattCharacteristic  characteristic4;
//		private BluetoothGattCharacteristic mNotifyCharacteristic;
		private boolean mConnected = false;
		private static final long SCAN_PERIOD = 10000;
		
		private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
			@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
			@Override
			public void onReceive(Context context, Intent intent) {
				final String action = intent.getAction();
				System.out.println("action================" + action);
				if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
					mConnected = true;
				} else if (BluetoothLeService.ACTION_GATT_DISCONNECTED
						.equals(action)) {
					mConnected = false;
				}
				// 发现有可支持的服务
				else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED
						.equals(action)) {
					// 服务
					supportedGattServices = mBluetoothLeService
							.getSupportedGattServices(UUID.fromString(Utils.uuid_s));
					
					// 闸门序列号
					characteristic4 = supportedGattServices.getCharacteristic(UUID
							.fromString(Utils.uuid_c4));

//					if (mNotifyCharacteristic != null) {
//						mBluetoothLeService.setCharacteristicNotification(
//								mNotifyCharacteristic, true);
//						mNotifyCharacteristic = null;
//					}
					
					

				

				}
				// 显示数据
				else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
//					pdg.dismiss();
					
					// 将数据显示在mDataField上
					String data = intent
							.getStringExtra(BluetoothLeService.EXTRA_DATA);
					String uuid = intent.getStringExtra("uuid");
					System.out.println("uuid========================:"+uuid);
					if(Utils.uuid_c4.equals(uuid)){
						
						//获取的数据data
						Toast.makeText(context, "获取的数据为===========================： "+data, 1).show();
						
						String sub = data.substring(4, 8);
						
						String result = PsUtils.hexStrToStr(sub);
						
						System.out.println("获取的数据为===========================： "+data+"------------截取数据------："+result);
						
						
						
						//根据result进行判断进入指定界面FK、FM、GG
						if(result.equalsIgnoreCase("fk")){
							
							
						}else if(result.equalsIgnoreCase("fm")){
							
						}else if(result.equalsIgnoreCase("gg")){
							
						}
						
						
						
						
						
					}
 
				

				}
			}
		};

		

		// 管理服务的生命周期
		private final ServiceConnection mServiceConnection = new ServiceConnection() {

			@Override
			public void onServiceConnected(ComponentName componentName,
					IBinder service) {
				mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
				if (!mBluetoothLeService.initialize()) {
					Log.e(TAG, "Unable to initialize Bluetooth");
					finish();
				}
				// Automatically connects to the device upon successful start-up
				// initialization.

//				if (address == null)
//					return;
//				mBluetoothLeService.connect(address);
			}

			@Override
			public void onServiceDisconnected(ComponentName componentName) {
				mBluetoothLeService = null;
			}
		};
		
		
		private void read(BluetoothGattCharacteristic characteristic) {
			// mBluetoothLeService.readCharacteristic(readCharacteristic);
			// readCharacteristic的数据发生变化，发出通知
			// mBluetoothLeService.setCharacteristicNotification(readCharacteristic,
			// true);
			// Toast.makeText(this, "读成功", Toast.LENGTH_SHORT).show();

//			if (mNotifyCharacteristic != null) {
//				mBluetoothLeService.setCharacteristicNotification(
//						mNotifyCharacteristic, true);
//				mNotifyCharacteristic = null;
//			}
			mBluetoothLeService.readCharacteristic(characteristic);
		}
		
		@Override
		protected void onStop() {
			super.onStop();
			//取消定时操作
			if(t!=null){
				t.cancel();
				
			}
		}
		
		
		@Override
		protected void onDestroy() {
			super.onDestroy();
			unbindService(mServiceConnection);
			mBluetoothLeService = null;
		}

		
		
	private static IntentFilter makeGattUpdateIntentFilter() {
		final IntentFilter intentFilter = new IntentFilter();
		// 服务器已经连接
		intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
		// 服务器已经断开
		intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
		// 发现服务
		intentFilter
				.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
		// 发现可用的数据
		intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
		return intentFilter;
	}
	

}
