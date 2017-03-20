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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
@ContentView(R.layout.fragment_flow)
public class FlowFragment extends Fragment {
	
	
	@ViewInject(R.id.et_ed)
	private EditText name;
	@ViewInject(R.id.et_ll)
	private EditText range;
	@ViewInject(R.id.et_yc)
	private EditText zero;
	@ViewInject(R.id.et_channel)
	private EditText chan;
	
	
	
	
	
	//获取过滤器类型
	@ViewInject(R.id.bt_sbinter)
	private  Spinner  sp_type;
	@ViewInject(R.id.tv_text)
	private TextView tvc;
	@ViewInject(R.id.bt_edit)
	private Button bte;
	private List<InfoListBean> lis;
	
	private ArrayAdapter adapter;
	
	private List<String> list = new ArrayList<String>();
	
	private Handler handler = new Handler(){
		

		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case PsUtils.GET_FLOW_TYPE:
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
		tvc.setText("流量配置");
		bte.setVisibility(View.VISIBLE);
		bte.setText("保存");
		getFlowTypeMethod();
		
	    adapter = new ArrayAdapter<String>(getActivity(),R.layout.listview_custom_item,list);
		adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		sp_type.setAdapter(adapter);
		sp_type.setOnItemSelectedListener(new OnItemSelectedListener() {

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
		
		
		return view;
	}
	
	@Event(R.id.bt_back)
	private void backOnClick(View v){
		getFragmentManager().popBackStack();
	}
	
	@Event(R.id.bt_edit)
	private void editOnClick(View v){
//		Toast.makeText(getActivity(), "保存", 0).show();
		
		
		/*
		 * 	"firstDerviceID":"SB001003", 
	"equTypeID":"10",
	"flowType":"4",
	"sensorName":"流量传感器名称",
	"rangeLimit":"100",
	"measurementLimit":"100",
	"chanNum":"1"
		 */
		
		
		
		RequestParams rp = new RequestParams(PsUtils.add_flow_info);
		
		JSONObject jo = new JSONObject();
		try {
			jo.put("firstDerviceID",((FirstPartDeviceActivity)getActivity()).getId() );
			jo.put("equTypeID",equTypeID);
			jo.put("flowType",equTypeName );
			jo.put("sensorName",name.getText().toString());
			jo.put("rangeLimit",range.getText().toString() );
			jo.put("measurementLimit",zero.getText().toString());
			jo.put("chanNum",chan.getText().toString());
	
			rp.setBodyContent(jo.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		PsUtils.send(rp, HttpMethod.POST, handler, getActivity(), "保存数据中。。。。", PsUtils.SAVE_INFO);
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
	/*
	 * 获取流量计类型
	 */
	private void getFlowTypeMethod() {
		RequestParams rp  = new RequestParams(PsUtils.get_flowType);
		
		PsUtils.send(rp, HttpMethod.GET, handler, getActivity(), "获取流量计类型中。。。。", PsUtils.GET_FLOW_TYPE);
	}

}
