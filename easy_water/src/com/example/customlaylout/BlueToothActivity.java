package com.example.customlaylout;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import org.xutils.x;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import ps.emperor.easy_water.BaseActivity;
import ps.emperor.easy_water.R;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
@ContentView(R.layout.activity_bluetooth)
public class BlueToothActivity extends BaseActivity implements OnSeekBarChangeListener,
		OnClickListener {
	
	@ViewInject(R.id.title)
	private ps.emperor.easy_water.utils.Ltitle title;
	
	
	//标记
	private int tem=0;

	private static final int REQUEST_ENABLE_BT = 110;

	private static String TAG = BlueToothActivity.class.getSimpleName();
	//透明百分比
	@ViewInject(R.id.tv_percent)
	private TextView tvp;
	//蓝色百分比
	@ViewInject(R.id.tv_percentB)
	private TextView tvpb;

	// 闸门底板颜色
	@ViewInject(R.id.tv_white)
	private TextView tvW;
	//透明布局
	@ViewInject(R.id.ll_tran)
	private LinearLayout llt;
	//蓝色布局
	@ViewInject(R.id.ll_blue)
	private LinearLayout llb;
	// 移动seekbar
	@ViewInject(R.id.sb_setBlue)
	private SeekBar sbb;  //设置实际闸门的变化
	@ViewInject(R.id.sb_setting)
	private SeekBar sb;   //设置计划闸门的变化
	//记录计划闸门开启高度
	private int tag;
	//执行闸门开启动作时的对话框
	private ProgressDialog pd;
	@ViewInject(R.id.rl)
	private RelativeLayout rl;
	// 执行按钮
//	@ViewInject(R.id.bt_execute)
//	private Button btE;
//	// 输入阀门开度S
//	@ViewInject(R.id.et_height)
//	private EditText etD;
	// 显示基本信息
	@ViewInject(R.id.tv_display1)
	private TextView tv1;
	@ViewInject(R.id.tv_display2)
	private TextView tv2;
	@ViewInject(R.id.tv_display3)
	private TextView tv3;
	@ViewInject(R.id.tv_display4)
	private TextView tv4;
	// 上按钮
//	@ViewInject(R.id.bt_up)
//	private Button up;
//	// 下按钮
//	@ViewInject(R.id.bt_down)
//	private Button down;
//	// 停止按钮
//	@ViewInject(R.id.bt_stop)
	private Button stop;
	// 满开关
	@ViewInject(R.id.bt_full)
	private Button full;
	// 接收的阀门高度
	private int num;
	byte[] WriteBytes = new byte[20];
	private BluetoothGattCharacteristic mNotifyCharacteristic;
	// 服务
	BluetoothGattService supportedGattServices;
	// 特征
	BluetoothGattCharacteristic characteristic, characteristic1,
			characteristic2, characteristic3, characteristic4;
	
	public static final  int TIMEROUT = 0x1109;

	private boolean mScanning;
	private Handler mHandler = new Handler(){
		

		

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case Utils.START_MOVE_GATE:
				//用蓝牙向闸门发布命令并定时读取闸门的高度和开度
				Toast.makeText(context, "向闸门发送命令", 0).show();
				
				writeData(characteristic1);
				
				
				//恢复初始值
//				tag = 0;
				
				
				//弹出加载框，并不允许消除
				
//				pd = Utils.showProgressDialog(context,null, "执行中。。。",false);
//				pd.getWindow().setGravity(Gravity.TOP);
				
				
				
				break;
			case TIMEROUT:
				Toast.makeText(context, "没有搜索到设备，请确认后重试!!!", 0).show();
				BlueToothActivity.this.finish();
				
				break;

			default:
				break;
			}
		}
	};

	// 10s搜索时间
	private static final long SCAN_PERIOD = 10000;
	private Context context = BlueToothActivity.this;

	private BluetoothAdapter mBluetoothAdapter;

	private BluetoothLeService mBluetoothLeService;
	// 目标地址
	private String address;

	private boolean mConnected = false;

	// 管理服务的生命周期
	private final ServiceConnection mServiceConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName componentName,
				IBinder service) {
			mBluetoothLeService = ((BluetoothLeService.LocalBinder) service)
					.getService();
			if (!mBluetoothLeService.initialize()) {
				Log.e(TAG, "Unable to initialize Bluetooth");
				finish();
			}
			// Automatically connects to the device upon successful start-up
			// initialization.

//			if (address == null)
//				return;
//			mBluetoothLeService.connect(address);
		}

		@Override
		public void onServiceDisconnected(ComponentName componentName) {
			mBluetoothLeService = null;
		}
	};

	@Override
	protected void onPause() {
		super.onPause();
		scanLeDevice(false);
		
	}
	
	

	@SuppressLint("NewApi")
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
	private void scanLeDevice(final boolean enable) {
		if (enable) {
			// Stops scanning after a pre-defined scan period.
//			mHandler.postDelayed(new Runnable() {
//				@Override
//				public void run() {
//					mScanning = false;
//					mBluetoothAdapter.stopLeScan(mLeScanCallback);
//					pdg.dismiss();
//					BlueToothActivity.this.finish();
//					Toast.makeText(context, "没有搜索到设备，请确认后重试!!!", 0).show();
//				}
//			}, SCAN_PERIOD);

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
	


	private void read(BluetoothGattCharacteristic characteristic) {
		// mBluetoothLeService.readCharacteristic(readCharacteristic);
		// readCharacteristic的数据发生变化，发出通知
		// mBluetoothLeService.setCharacteristicNotification(readCharacteristic,
		// true);
		// Toast.makeText(this, "读成功", Toast.LENGTH_SHORT).show();

		if (mNotifyCharacteristic != null) {
			mBluetoothLeService.setCharacteristicNotification(
					mNotifyCharacteristic, true);
			mNotifyCharacteristic = null;
		}
		mBluetoothLeService.readCharacteristic(characteristic);
	}

	// Handles various events fired by the Service.处理服务所激发的各种事件
	// ACTION_GATT_CONNECTED: connected to a GATT server.连接一个GATT服务
	// ACTION_GATT_DISCONNECTED: disconnected from a GATT server.从GATT服务中断开连接
	// ACTION_GATT_SERVICES_DISCOVERED: discovered GATT services.查找GATT服务
	// ACTION_DATA_AVAILABLE: received data from the device. This can be a
	// result of read
	// or notification operations.从服务中接受数据
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
				// 特征1uuid 闸门设置开度 可读可写
				characteristic1 = supportedGattServices.getCharacteristic(UUID
						.fromString(Utils.uuid_c1));
				// 特征2uuid 闸门开高范围 可读可写
				characteristic2 = supportedGattServices.getCharacteristic(UUID
						.fromString(Utils.uuid_c2));
				// 特征3uuid 闸门Location
				characteristic3 = supportedGattServices.getCharacteristic(UUID
						.fromString(Utils.uuid_c3));
				// 闸门序列号
				characteristic4 = supportedGattServices.getCharacteristic(UUID
						.fromString(Utils.uuid_c4));

				if (mNotifyCharacteristic != null) {
					mBluetoothLeService.setCharacteristicNotification(
							mNotifyCharacteristic, true);
					mNotifyCharacteristic = null;
				}
				
				
				t = new Timer();
				TimerTask task = new TimerTask() {
					
					@Override
					public void run() {
						read(characteristic1);
//						read(characteristic2);
//						read(characteristic3);
//						read(characteristic4);
						
					}
				};
				t.schedule(task, 3000, 8000);
			

			}
			// 显示数据
			else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
				pdg.dismiss();
				
				// 将数据显示在mDataField上
				String data = intent
						.getStringExtra(BluetoothLeService.EXTRA_DATA);
				String uuid = intent.getStringExtra("uuid");
				System.out.println("uuid========================:"+uuid);
				if(Utils.uuid_c1.equals(uuid)){
					System.out.println("data==========:"+data+"..."+(tem++));
					sbb.setProgress(Integer.parseInt(data,16));
					read(characteristic2);
				}else if(Utils.uuid_c2.equals(uuid)){
					
					displayData("\t闸门量程："+data,tv2);
					System.out.println(" 执行。。。。。c2c2c2c2c2c2c2---------------");
					read(characteristic3);
				}else if(Utils.uuid_c3.equals(uuid)){
					displayData("\t闸门Location："+data,tv3);
					System.out.println(" 执行。。。。。c3333333333333---------------");
					read(characteristic4);
				}else if(Utils.uuid_c4.equals(uuid)){
					displayData("\t闸门ID："+data,tv4);
					System.out.println(" 执行。。。。。c4---------------");
					
				}

			

			}
		}
	};

	private Timer t;


	//进入界面加载
	private ProgressDialog pdg;
	
	

	private void displayData(String data,TextView tv) {
		if (data != null) {

			tv.setText(data);
			
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		x.view().inject(this);
		
		title.setText("闸门测试");

		Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
		bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
		

		checkBluetooth();

		control();
		pdg = Utils.showProgressDialog(context, null, "闸门连接中。。", false);
		
		
		Timer tr = new Timer();
		TimerTask tt = new TimerTask(){
			@Override
			public void run() {
				if(pdg.isShowing()){
					pdg.dismiss();
					Message msg = new Message();
					msg.what = TIMEROUT;
					mHandler.sendMessage(msg);
				}
				
			}
		};
		tr.schedule(tt,SCAN_PERIOD);



	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unbindService(mServiceConnection);
		mBluetoothLeService = null;
	}

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

//		scanLeDevice(true);
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		if(characteristic4!=null){
			t = new Timer();
			TimerTask task = new TimerTask() {
				
				@Override
				public void run() {
					read(characteristic1);
//					read(characteristic2);
//					read(characteristic3);
//					read(characteristic4);
					
				}
			};
			t.schedule(task, 1000, 8000);
		}
			
		
		

		// Ensures Bluetooth is enabled on the device. If Bluetooth is not
		// currently enabled,
		// fire an intent to display a dialog asking the user to grant
		// permission to enable it.
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
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// User chose not to enable Bluetooth.
		if (requestCode == REQUEST_ENABLE_BT
				&& resultCode == Activity.RESULT_CANCELED) {
			finish();
			return;
		}
		// 搜索并连接指定设备

		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 控制方法
	 */
	private void control() {
		sb.setOnSeekBarChangeListener(this);
		sbb.setOnSeekBarChangeListener(this);
		llt.bringToFront();
//		btE.setOnClickListener(this);
//		up.setOnClickListener(this);
		full.setOnClickListener(this);

	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		
		switch (seekBar.getId()) {
		case R.id.sb_setting:
			int out_h = rl.getHeight();

			ViewGroup.LayoutParams in_lp = llt.getLayoutParams();

			in_lp.height = out_h * (100 - progress) / 100;

			llt.setLayoutParams(in_lp);
			tvp.setText(progress+"%");
			llt.requestLayout();

			
			
			tag = progress;
			
			
			
			break;
		case R.id.sb_setBlue:
			int out_b = rl.getHeight();
			ViewGroup.LayoutParams in_b = llb.getLayoutParams();

			in_b.height = out_b * (100 - progress) / 100;

			llb.setLayoutParams(in_b);
			tvpb.setText(progress+"%");
			llb.requestLayout();
			
			break;

		default:
			break;
		}

		

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
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		
		
		
		switch (seekBar.getId()) {
		case R.id.sb_setting:
	
			Utils.showDialog(context, "提示", "您确定要执行闸门操作吗？", mHandler);
			
			
			
			
			break;
		case R.id.sb_setBlue:
		
			
			break;

		default:
			break;
		}

	}

	/**
	 * 所有点击事件
	 * 
	 * @param v
	 */
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
//		case R.id.bt_execute:
//			String et = etD.getText().toString();
//			if (TextUtils.isEmpty(et)) {
//				return;
//			}
//			int paEt = Integer.parseInt(et);
//
//			sb.setProgress(paEt);
//			characteristic = characteristic1;
//			writeData(characteristic, etD);
//			read(characteristic);
//
//			break;
//		case R.id.bt_up:
//			Toast.makeText(context, "上被点击", 0).show();
//			System.out.println("地址===================：" + address);
//			// mBluetoothLeService.connect(address);
//			characteristic = characteristic1;
//			etD.setText((num + 10) + "");
//			writeData(characteristic, etD);
//			read(characteristic);
//
//			break;

		case R.id.bt_full:
			Toast.makeText(context, "满被点击", 0).show();
			sb.setProgress(100);
			tag = 100;
			characteristic=characteristic1;
			writeData(characteristic1);
			read(characteristic);

			break;

		default:
			break;
		}

	}

	/**
	 * 写入数据的方法
	 * 
	 * @param characteristic
	 * @param et
	 */
//	private void writeData(BluetoothGattCharacteristic characteristic,
//			EditText et) {
		@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
		private void writeData(BluetoothGattCharacteristic characteristic
				) {

		if (characteristic == null) {
			return;
		}

		final int charaProp = characteristic.getProperties();

		// 如果该char可写
		if ((charaProp | BluetoothGattCharacteristic.PROPERTY_READ) > 0) {
			// If there is an active notification on a characteristic, clear
			// it first so it doesn't update the data field on the user
			// interface.
			if (mNotifyCharacteristic != null) {
				mBluetoothLeService.setCharacteristicNotification(
						mNotifyCharacteristic, false);
				mNotifyCharacteristic = null;
			}
			// 读取数据，数据将在回调函数中
			// mBluetoothLeService.readCharacteristic(characteristic);
			byte[] value = new byte[20];
			value[0] = (byte) 0x00;
//			if (et.getText().toString().equals("")) {
//				Toast.makeText(getApplicationContext(), "请输入！",
//						Toast.LENGTH_SHORT).show();
//				return;
//			} else {

//				int dec = Integer.parseInt(et.getText().toString());
			    int dec = tag;
				String hexString = Integer.toHexString(dec);
				WriteBytes = Utils.getHexBytes(hexString);
				characteristic.setValue(value[0],
						BluetoothGattCharacteristic.FORMAT_UINT8, 0);
				characteristic.setValue(WriteBytes);
				mBluetoothLeService.wirteCharacteristic(characteristic);
				Toast.makeText(getApplicationContext(), "写入成功！",
						Toast.LENGTH_SHORT).show();

//			}
		}
		if ((charaProp | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
			mNotifyCharacteristic = characteristic;
			mBluetoothLeService.setCharacteristicNotification(characteristic,
					true);
		}
//		et.setText("");

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
