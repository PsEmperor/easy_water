package ps.emperor.easy_water.homepage;

import ps.emperor.easy_water.LoginActivity;
import ps.emperor.easy_water.MainActivity;
import cn.jpush.android.api.JPushInterface;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class JPushReceiver extends BroadcastReceiver {

	

	@Override
	public void onReceive(Context context, Intent intent) {
		

		Intent i = new Intent(context,LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
        context.startActivity(i);

	if(intent.getAction().equals(JPushInterface.ACTION_MESSAGE_RECEIVED)){
	Bundle b= intent.getExtras();
	String title = b.getString(JPushInterface.EXTRA_TITLE);
	String content = b.getString(JPushInterface.EXTRA_MESSAGE);

	Toast.makeText(context, "title========="+title+"............msg======="+content, 1).show();
	}


	}


}
