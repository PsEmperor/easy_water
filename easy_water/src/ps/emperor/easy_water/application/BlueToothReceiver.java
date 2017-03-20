package ps.emperor.easy_water.application;

import java.io.IOException;
import java.lang.reflect.Method;

import ps.emperor.easy_water.utils.BlueDevice;
import ps.emperor.easy_water.utils.PsUtils;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BlueToothReceiver extends BroadcastReceiver {
	
	private BluetoothAdapter ba = BluetoothAdapter.getDefaultAdapter();
	private int connectState;
	private String name ="slider led";

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();  
        if (BluetoothDevice.ACTION_FOUND.equals(action)) {  
            // 获取查找到的蓝牙设备   
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE); 
			BlueDevice bd = new BlueDevice(device.getName(), device.getAddress());

            System.out.println(device.getName());  
            // 如果查找到的设备符合要连接的设备，处理   
            if (device.getName().equalsIgnoreCase(name)) {  
                // 搜索蓝牙设备的过程占用资源比较多，一旦找到需要连接的设备后需要及时关闭搜索   
            	ba.cancelDiscovery();  
//            	pd.dismiss();
                // 获取蓝牙设备的连接状态   
                connectState = device.getBondState();  
                switch (connectState) {  
                    // 未配对   
                    case BluetoothDevice.BOND_NONE:  
                        // 配对   
                        try {  
                            Method createBondMethod = BluetoothDevice.class.getMethod("createBond");  
                            createBondMethod.invoke(device);  
                        } catch (Exception e) {   
                            e.printStackTrace();  
                        }  
                        break;  
                    // 已配对   
                    case BluetoothDevice.BOND_BONDED:  
                        try {  
                            // 连接   
                           PsUtils.connect(device);  
                        } catch (IOException e) {  
                            e.printStackTrace();  
                        }  
                        break;  
                }  
            }  
       } else if(BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {  
            // 状态改变的广播   
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);  
            if (device.getName().equalsIgnoreCase(name)) {   
                connectState = device.getBondState();  
                switch (connectState) {  
                    case BluetoothDevice.BOND_NONE:  
                        break;  
                    case BluetoothDevice.BOND_BONDING:  
                        break;  
                    case BluetoothDevice.BOND_BONDED:  
                        try {  
                            // 连接   
                            PsUtils.connect(device);  
                        } catch (IOException e) {  
                            e.printStackTrace();  
                        }  
                        break;  
                }  
            }  
        }  
    }  

}
