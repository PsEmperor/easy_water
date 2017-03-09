package ps.emperor.easy_water.utils;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import org.xutils.x;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PsUtils {
	
	/**
	 * 				//保存手机号账号信息
	 * 		        e.putString("user", user);
	 * 				//保存密码
					e.putString("pass",password);
					//保存checkd状态
					e.putBoolean("checked", cbSave.isChecked());
	 */
	
	
	
	public final static int SEND_REGISTER = 0x99;
	public final static int SEND_REGISTER_ERROR = 0x98;
	//获取授权单位
	public final static int GET_UNIT_NAME = 0x97;
	//保存信息
	public final static int SAVE_INFO = 0x96;
	//登录
	public final static int LOGIN_IN  = 0x95;
	//查询
	public final static int GET_SEARCH = 0x94;
	//获取上级设备
	public final static int GET_UP_DEV= 0x93;
	//获取验证码
	public final static int GET_CHECK_CODE = 0x92;
	
	
	/**
	 * get 请求
	 */
	
	public static final String urlRegister = "http://60.205.220.245:8080/cms/app/UserSys/addAccount/phoneNum/password";
	public static final String urlPassword = "http://60.205.220.245:8080/cms/app/UserSys/resetUserPass/phoneNum/password";
	//登录
	public static final String urlLogin = "http://60.205.220.245:8080/cms/app/UserSys/login/userName/password";
	//模糊查询  -------未添加
	public static final String urlSearch ="http://60.205.220.245:8080/cms/app/DisWaterSys/fuzzyQuery/findEquInfos/%s";
	//灌溉单元ID重名检测
	public static final String urlCheck = "http://60.205.220.245:8080/cms/app/IrriUnitSys/duplicatecheck/%s";
	//注册验证码
	public static final String urlCode_reg= "http://60.205.220.245:8080/cms/app/UserSys/getAuthCode/%s/1";
	//密码重置验证码
	public static final String urlCode_pas= "http://60.205.220.245:8080/cms/app/UserSys/getAuthCode/%s/2";
	//获取授权单位
	public static final String get_auth = "http://60.205.220.245:8080/cms/app/UserSys/findUserAuthInfos/authName/%s";
	//获取上级单位
	public static final String get_upDevice = "http://60.205.220.245:8080/cms/app/IrriUnitSys/findSuperEquInfo/%s";
	
	/**
	 * POST请求
	 */
	//配水信息添加
	public static final String  add_water_info= "http://60.205.220.245:8080/cms/app/DisWaterSys/addDisWaterEquInfo";
	//闸门信息添加
	public static final String add_gate_info = "http://60.205.220.245:8080/cms/app/DisWaterSys/addSluiceGateInfo";
	
	//灌溉信息添加
	public static final String add_irr_info = "http://60.205.220.245:8080/cms/app/IrriUnitSys/addIrriUnitInfo";
	//水泵信息添加
	public static final String add_pump_info = "http://60.205.220.245:8080/cms/app/IrriUnitSys/addPumpInfo/firstDerviceID";
	//过滤器信息添加
	public static final String add_filter_info = "http://60.205.220.245:8080/cms/app/IrriUnitSys/addFilterInfo/firstDerviceID";
	//压力传感器信息添加
	public static final String add_press_info ="http://60.205.220.245:8080/cms/app/IrriUnitSys/addPressureSensorInfo/firstDerviceID";
	//流量传感器信息添加
	public static final String add_flow_info  = "http://60.205.220.245:8080/cms/app/IrriUnitSys/addFlowSensorInfo/firstDerviceID";
	//阀控器信息添加
	public static final String add_control_info = "http://60.205.220.245:8080/cms/app/IrriUnitSys/addValueControInfo/firstDerviceID";

	
	
	/**
	 * 判断传入手机号是否符合手机号码正则
	 * @param phoneNum	手机号
	 * @return	 boolean
	 */
	public static boolean checkPhoneNum(String phoneNum){
	boolean b = false;
	phoneNum = phoneNum.trim();
	if(phoneNum != null && phoneNum.length() == 11){
	//手机号码正则
	String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}$";
	b = phoneNum.matches(regex);
	}
	return b;
	}
	
	
	
	
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
	 * 检查蓝牙并开启
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
	
	
	
	
	

	


	/**
	 * 加载布局
	 * @param msg
	 */
	public static ProgressDialog proDiag(String msg,Context context) {
		ProgressDialog pd = new ProgressDialog(context);
		pd.setCancelable(false);
		pd.setMessage(msg);
		pd.show();
		
		return pd;
	}

	
	/*
	 * 
	private Handler handler  = new Handler(){
	public void handleMessage(Message msg) {
		switch (msg.what) {
		case Utils.SEND_REGISTER:
			//获取信息成功操作
			System.out.println("结果是--------------："+msg.obj);
			Gson g = new Gson();
			Test t = g.fromJson((String) msg.obj, Test.class);
			List<AuthNameListBean> list = t.getAuthNameList();
			for (AuthNameListBean a : list) {
				System.out.println("省---------------------------："+a.getAuthProvince());
			}
			break;
		}
	};
};

*/
	/**
	 * 发送请求方法
	 * 
	 * 需要在每个请求中加入handler，上面为Handler样例
	 */
	public static void send(RequestParams rp,HttpMethod httpMethod ,final Handler handler,Context context,String msg,final int whatContent){
		final ProgressDialog pd = proDiag(msg, context);
		
		x.http().request(httpMethod, rp, new CommonCallback<String>() {

			@Override
			public void onSuccess(String result) {
				Message msg = new Message();
				msg.obj = result;
				msg.what = whatContent;
				handler.sendMessage(msg);
				
			}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
				System.out.println("异常信息："+ex.toString());
				Message msg = new Message();
				msg.obj = ex.toString();
				msg.what = SEND_REGISTER_ERROR;
				handler.sendMessage(msg);
				
			}

			@Override
			public void onCancelled(CancelledException cex) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onFinished() {
				pd.dismiss();
				
			}
		});
		
		
	}
	
	
    /** 
     * 字符串转换为16进制字符串 
     *  
     * @param s 
     * @return 
     */  
    public static String strToHexStr(String s) {  
        String str = "";  
        for (int i = 0; i < s.length(); i++) {  
            int ch = (int) s.charAt(i);  
            String s4 = Integer.toHexString(ch);  
            str = str + s4;  
        }  
        return str;  
    }  
  
    /** 
     * 16进制字符串转换为字符串 
     *  
     * @param s 
     * @return 
     */  
    public static String hexStrToStr(String s) {  
        if (s == null || s.equals("")) {  
            return null;  
        }  
        s = s.replace(" ", "");  
        byte[] baKeyword = new byte[s.length() / 2];  
        for (int i = 0; i < baKeyword.length; i++) {  
            try {  
                baKeyword[i] = (byte) (0xff & Integer.parseInt(  
                        s.substring(i * 2, i * 2 + 2), 16));  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
        try {  
            s = new String(baKeyword, "gbk");  
            new String();  
        } catch (Exception e1) {  
            e1.printStackTrace();  
        }  
        return s;  
    }  
	
    /**
     * 获取SharedPre
     * @param context
     * @return
     */
    public static SharedPreferences getShared(Context context){
    	SharedPreferences sp = context.getSharedPreferences("share_date", 0);
    	
    	return sp;
    }
    
    

    
    
//    TimerTask task = new TimerTask() {    
//        @Override    
//        public void run() {    
//   
//            runOnUiThread(new Runnable() {      // UI thread    
//                @Override    
//                public void run() {    
//                    recLen--;    
//                    if(recLen < 0){    
//                        timer.cancel();    
//                    }    
//                }    
//            });    
//        }    
//    };    
	


}
