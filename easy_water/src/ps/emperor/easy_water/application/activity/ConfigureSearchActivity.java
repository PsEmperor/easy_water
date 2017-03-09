package ps.emperor.easy_water.application.activity;

import java.util.ArrayList;
import java.util.List;

import org.xutils.x;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import ps.emperor.easy_water.BaseActivity;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.application.entity.SearchBeen;
import ps.emperor.easy_water.application.entity.SearchBeen.IrriListBean;
import ps.emperor.easy_water.utils.PsUtils;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;

@ContentView(R.layout.activity_configuresearch)
public class ConfigureSearchActivity extends BaseActivity implements OnClickListener {
	
	private Context context = ConfigureSearchActivity.this;
	@ViewInject(R.id.lv_deviceName)
	private ListView lv_d;
	@ViewInject(R.id.et_search)
	private EditText ets;
	private List<String> list = new ArrayList<String>();
	//所有灌溉单元的集合
	private List<IrriListBean> irriList;
	private Handler handler  = new Handler(){
		

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case PsUtils.GET_SEARCH:
				String content = (String)msg.obj;
//				System.out.println("content-----------------:"+content);
				Gson g = new Gson();
				SearchBeen sb = g.fromJson(content, SearchBeen.class);
				irriList = sb.getIrriList();
				for (IrriListBean ib : irriList) {
					list.add(ib.getIrriUnitName());
				}
				adapter.notifyDataSetChanged();
				break;
			case PsUtils.SEND_REGISTER_ERROR:
				
				break;

			default:
				break;
			}
		}
	};
	
	@ViewInject(R.id.title)
	private ps.emperor.easy_water.utils.Ltitle title;
	@ViewInject(R.id.iv_search)
	private ImageView ivs;
	private ArrayAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		x.view().inject(this);
		control();
	}

	private void control() {
		title.setText("搜索");
		ivs.setOnClickListener(this);
		
		 adapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, list);
		
		lv_d.setAdapter(adapter);
		lv_d.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				IrriListBean ib = irriList.get(position);
				Intent i  = new Intent (ConfigureSearchActivity.this,FirstPartActivity.class);
				i.putExtra("sbContent", ib);
				startActivity(i);
				
				
				
			}
		});
		
		
		
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.iv_search:
//			PsUtils.urlSearch
			String url = String.format(PsUtils.urlSearch,ets.getText().toString());
			RequestParams rp  = new RequestParams(url);
			PsUtils.send(rp, HttpMethod.GET, handler, context,"搜索中。。。",PsUtils.GET_SEARCH);
			
			break;
//		case:
//			break;
		}
		
	}

}
