package com.example.customlaylout;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Handler;
import android.os.Message;



public class Utils {
	
	//目标设备的key
	 public static final String EXTRAS_DEVICE_NAME = "DEVICE_NAME";
	 public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";
	 
	 public static final int START_MOVE_GATE = 0x11;
	 
	 //蓝牙名称
	 public static final String name = "Timely";
	//服务uuid
	public static String uuid_s = "00000000-0000-1000-8000-00805f9b34fb";
	//特征1uuid
	public static String uuid_c1 = "00000000-0001-1000-8000-00805f9b34fb";
	//特征2uuid
	public static String uuid_c2 = "00000000-0002-1000-8000-00805f9b34fb";
	//特征3uuid
	public static String uuid_c3 = "00000000-0003-1000-8000-00805f9b34fb";
	//特征3uuid
	public static String uuid_c4 = "00000000-0004-1000-8000-00805f9b34fb";
	
	
	
	/**
	*  AlertDialog
	*/
	public static void showDialog(Context context,String title,String msg ,final Handler handler) {

	  AlertDialog.Builder builder = new AlertDialog.Builder(context);
	  builder.setTitle(title);
	  builder.setMessage(msg);
	  builder.setNegativeButton("取消", null);
	  builder.setPositiveButton("确定", new OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int which) {
			Message ms = new Message();
			ms.what=START_MOVE_GATE;
			handler.sendMessage(ms);
			
			
		}
	});
	  builder.show();
	}
	
	
	
	/**
	*  ProgressDialog
	*  结束时，用返回的对象调用dismiss消除，例如：pd.dismiss();
	*/
	public static ProgressDialog showProgressDialog(Context context,String title,String msg,boolean cancelable ) {

		
	  ProgressDialog pd = new ProgressDialog(context);
	  pd.setTitle(title);
	  pd.setMessage(msg);
	  pd.setCancelable(cancelable);
	  
	  pd.show();
	  
	  return pd;
	  
	  
	  
	}
	
	
	
    /**
     * 字符串转换成16进制Byte数组（进行串口传输时需使用）
     * @param message
     * @return
     */
    public static byte[] getHexBytes(String message) {
        int len = message.length() / 2;
        char[] chars = message.toCharArray();
        String[] hexStr = new String[len];
        byte[] bytes = new byte[len];
        for (int i = 0, j = 0; j < len; i += 2, j++) {
            hexStr[j] = "" + chars[i] + chars[i + 1];
            bytes[j] = (byte) Integer.parseInt(hexStr[j], 16);
        }
        return bytes;
    }
    
    /**
     * 16进制Byte数组转换成  （接收串口的数据进行转换时需使用）
     * @param bytes
     * @return
     */
    public static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }
    
    


}



