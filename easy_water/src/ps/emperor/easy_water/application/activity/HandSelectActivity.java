package ps.emperor.easy_water.application.activity;

import java.util.ArrayList;

import org.xutils.x;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import ps.emperor.easy_water.BaseActivity;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.application.adapter.HandSelectAdapter;
import ps.emperor.easy_water.application.entity.AppBeen;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

@ContentView(R.layout.activity_handselect)
public class HandSelectActivity extends BaseActivity implements OnItemClickListener {
	

	@ViewInject(R.id.title)
	private ps.emperor.easy_water.utils.Ltitle title;
	@ViewInject(R.id.lv_DeviceName)
	private ListView lv;
	private ArrayList list = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		x.view().inject(this);
		control();
	}

	private void control() {
		list.add(new AppBeen(R.drawable.icon_arrow, "配水设备"));
		list.add(new AppBeen(R.drawable.icon_arrow, "灌溉单元"));
		
		title.setText("手动配置选择");
		
		HandSelectAdapter adapter = new HandSelectAdapter(this,android.R.layout.simple_list_item_1,list);
		
		lv.setAdapter(adapter);
		
		adapter.notifyDataSetChanged();
		
		lv.setOnItemClickListener(this);
		
		
		
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		switch (position) {
		case 0:
			
			Intent i = new Intent(HandSelectActivity.this,WaterGateActivity.class);
			startActivity(i);
			
			break;
			
		case 1:
			
			Intent i1 = new Intent(HandSelectActivity.this,FirstPartActivity.class);
			startActivity(i1);
			
			break;

		default:
			break;
		}
		
	}

}
