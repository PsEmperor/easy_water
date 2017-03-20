package ps.emperor.easy_water.application.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import ps.emperor.easy_water.R;
import ps.emperor.easy_water.application.activity.FirstPartDeviceActivity;
import ps.emperor.easy_water.application.entity.BaseBeen;
import ps.emperor.easy_water.application.entity.FirstDeviceTypeBeen;
import ps.emperor.easy_water.application.entity.FirstDeviceTypeBeen.InfoListBean;
import ps.emperor.easy_water.utils.PsUtils;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
@ContentView(R.layout.fragment_guolvqi)
public class GlqFragment extends Fragment {
	@ViewInject(R.id.et_telphone)
	private EditText tel;
	
	@ViewInject(R.id.ll_fz)
	private LinearLayout ll_fz;
	
	@ViewInject(R.id.ll_time)
	private LinearLayout ll_time;
	
	@ViewInject(R.id.et_fz)
	private EditText et_fz;
	@ViewInject(R.id.et_time)
	private EditText et_time;
	
	
	
	//获取过滤器类型
	@ViewInject(R.id.bt_sbinter)
	private  Spinner  filterType;
	@ViewInject(R.id.tv_text)
	private TextView tvc;
	@ViewInject(R.id.bt_edit)
	private Button bte;
	@ViewInject(R.id.et_maxFlow)
	private EditText maxFlow;
	@ViewInject(R.id.rg)
	private RadioGroup rg;

	int tag = 1;
	@ViewInject(R.id.rb_yc)
	private RadioButton yc;
	@ViewInject(R.id.rb_time)
	private RadioButton time;

	private List<InfoListBean> lis;
	
	private ArrayAdapter adapter;
	
	private List<String> list = new ArrayList<String>();
	private Handler handler = new Handler(){
		

		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case PsUtils.GET_FILTER_TYPE:
				System.out.println("获取水泵的数据类型------------------------------："+msg.obj);
				String result = (String)msg.obj;
				//解析
				Gson g = new Gson();
				FirstDeviceTypeBeen fb = g.fromJson(result, FirstDeviceTypeBeen.class);
				lis = fb.getInfoList();
				for (InfoListBean ib : lis) {
					list.add(ib.getEquTypeName());
					
				}
				adapter.notifyDataSetChanged();
				
				
				break;
				
			case PsUtils.SAVE_INFO:
				//保存数据中
				
				String re = (String) msg.obj;
				Gson gi = new Gson();
				BaseBeen bb = gi.fromJson(re, BaseBeen.class);
				String code = bb.getCode();

				if (code.equals("1")) {
					Toast.makeText(getActivity(), "保存完成", 1).show();
					System.out.println("baocun完成。。。。。............");
				} else {
					Toast.makeText(getActivity(), "保存失败", 1).show();
				}

				
				break;
 
			default:
				break;
			}
		};
	};
	
	//点击的选项的equTypeID
	private int equTypeID;
	private String equTypeName;
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = x.view().inject(this, inflater, container);
		tvc.setText("过滤器配置");
		bte.setVisibility(View.VISIBLE);
		bte.setText("保存");
		
		getFilterTypeMethod();
		
		
	    adapter = new ArrayAdapter<String>(getActivity(),R.layout.listview_custom_item,list);
		adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		filterType.setAdapter(adapter);
		filterType.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				
				//获取对应的设备id
				InfoListBean ib = lis.get(position);
				equTypeID = ib.getEquTypeID();
				equTypeName = ib.getEquTypeName();
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		

		
		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.rb_yc :
					tag = 1;
					ll_time.setVisibility(View.GONE);
					ll_fz.setVisibility(View.VISIBLE);
					et_time.setText("");
					break;
				case R.id.rb_time:
					tag = 2;
					ll_fz.setVisibility(View.GONE);
					ll_time.setVisibility(View.VISIBLE);
					et_fz.setText("");
					break;

				default:
					break;
				}
				
			}
		});
		
		
		return view;
	}
	
	
	
	/*
	 * 获取过滤器类型
	 */
	private void getFilterTypeMethod() {
		RequestParams rp  = new RequestParams(PsUtils.get_filterType);
		
		PsUtils.send(rp, HttpMethod.GET, handler, getActivity(), "获取过滤器类型中。。。。", PsUtils.GET_FILTER_TYPE);
	}
	
	@Event(R.id.bt_back)
	private void backOnClick(View v){
		getFragmentManager().popBackStack();
	}
	
	@Event(R.id.bt_edit)
	private void editOnClick(View v){
//		Toast.makeText(getActivity(), "保存", 0).show();
		
		
		RequestParams rp = new RequestParams(PsUtils.add_filter_info);
		
		JSONObject jo = new JSONObject();
		try {
			jo.put("firstDerviceID",((FirstPartDeviceActivity)getActivity()).getId() );
			jo.put("equTypeID",equTypeID);
			jo.put("filterType",equTypeName );
			jo.put("maxFlu",maxFlow.getText().toString());
			jo.put("flushModel",tag );
			jo.put("pressThreshold",et_fz.getText().toString());
			jo.put("timer",et_time.getText().toString());
			jo.put("maintenanceTele", tel.getText().toString());
	
			rp.setBodyContent(jo.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		PsUtils.send(rp, HttpMethod.POST, handler, getActivity(), "保存数据中。。。。", PsUtils.SAVE_INFO);
		
		
		
		
		
		
		
		
		
		
		
	}

}
