package ps.emperor.easy_water.utils;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import ps.emperor.easy_water.R;

import android.app.AlertDialog;
import android.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PsUtils {
	
	
	/**
	 * 设置通用ArrayAdapter
	 * @param context
	 * @param list
	 * @param lv
	 * @return
	 */
	public static ArrayAdapter setArrayAdapter(Context context,List<String> list,ListView lv){
		ArrayAdapter adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, list);
		lv.setAdapter(adapter);
		return adapter;
	}

	/**
	 * 检测是否支持nfc功能？是否开启nfc功能？ 二者都满足返回true，否则false
	 */
	public static boolean NfcIf(Context context, NfcAdapter adapter) {

		if (adapter == null) {
			// 此手机不支持nfc功能
			Toast.makeText(context, "此手机不支持nfc功能", 0).show();
			return false;
		} else {
			if (!adapter.isEnabled()) {

				// 跳转nfc设置界面
				Intent nfcSet = new Intent(
						android.provider.Settings.ACTION_NFC_SETTINGS);
				context.startActivity(nfcSet);
				return true;

			} else {
				return true;
			}

		}

	}

	/**
	 * 读取nfc标签内容
	 * 
	 * @param intent
	 */
	public static void readCard(Intent intent) {
		Parcelable[] ps = intent
				.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

		NdefMessage[] ms = new NdefMessage[ps.length];
		int contentSize = 0;

		if (ps != null) {
			for (int i = 0; i < ps.length; i++) {
				ms[i] = (NdefMessage) ps[i];
				contentSize += ms[i].toByteArray().length;
			}
		}

		if (ms != null) {
			NdefRecord nr = ms[0].getRecords()[0];
			TextRecord tr = TextRecord.parse(nr);

			String ver = "";
			ver += tr.getText() + "\n\nContentSize:" + contentSize
					+ "bytes\n\n";

		}

	}

	/**
	 * 开启蓝牙并搜索指定蓝牙设备
	 */
	public static void openBlue(Context context) {
		BluetoothAdapter ba = BluetoothAdapter.getDefaultAdapter();
		if (ba == null) {
			Toast.makeText(context, "此手机不支持蓝牙，请更换手机！！~", 0).show();
			return;
		}

		ba.enable();
		System.out.println("执行过了开启蓝牙选项。。。。。。。。。。。。");

		if (ba.isDiscovering()) {
			ba.cancelDiscovery();
		}
		ba.startDiscovery();
	}

	public static void connect(BluetoothDevice device) throws IOException {
		// 固定的UUID
		final String SPP_UUID = "00001101-0000-1000-8000-00805F9B34FB";
		// final String SPP_UUID = "00001800-0000-1000-8000-00805f9b34fb";
		UUID uuid = UUID.fromString(SPP_UUID);
		BluetoothSocket socket = device.createRfcommSocketToServiceRecord(uuid);
		socket.connect();
	}

	public static void readGPS(final LocationManager lm, final TextView tv) {
		String bestProvider = lm.getBestProvider(getCriteria(), true);
		System.out
				.println("best..................==============================="
						+ bestProvider);
		Location location = lm.getLastKnownLocation(bestProvider);
		upView(location, tv);
		lm.requestLocationUpdates(bestProvider, 2000, 3,
				new LocationListener() {

					@Override
					public void onStatusChanged(String provider, int status,
							Bundle extras) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onProviderEnabled(String provider) {
						upView(lm.getLastKnownLocation(provider), tv);

					}

					@Override
					public void onProviderDisabled(String provider) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onLocationChanged(Location location) {
						upView(location, tv);

					}
				});
	}

	/**
	 * 实时更新gps位置
	 * 
	 * @param location
	 */
	public static void upView(Location lo, TextView tv) {
		if (lo != null) {
			StringBuilder sb = new StringBuilder();
			// sb.append("实时位置信息:\n");
			sb.append("经度:");
			sb.append(lo.getLongitude());
			sb.append("\n纬度:");
			sb.append(lo.getLatitude());
			// sb.append("\n高度:");
			// sb.append(lo.getAltitude());
			// sb.append("\n速度:");
			// sb.append(lo.getSpeed());
			// sb.append("\n方向:");
			// sb.append(lo.getBearing());
			tv.setText(sb.toString());
		}

	}

	/**
	 * 获取过滤条件
	 */
	public static Criteria getCriteria() {
		// 建立过滤条件
		Criteria c = new Criteria();
		c.setAccuracy(c.ACCURACY_FINE);
		c.setSpeedRequired(true);
		// 过滤条件---是否收费
		// c.setCostAllowed(false);
		// 提供高度信息
		// c.setAltitudeRequired(true);
		// 提供方向信息
		// c.setBearingRequired(true);
		return c;

	}
	
	


}
