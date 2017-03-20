package ps.emperor.easy_water;

import org.xutils.x;

import android.app.Application;
import cn.jpush.android.api.JPushInterface;

public class App extends Application {
	
		@Override
		public void onCreate() {
		super.onCreate();
		//设置极光推送的debug
		JPushInterface.setDebugMode(true);
		//初始化极光推送
		JPushInterface.init(this);
		x.Ext.init(this);
		}
}
