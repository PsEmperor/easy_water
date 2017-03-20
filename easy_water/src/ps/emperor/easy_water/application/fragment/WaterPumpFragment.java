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
import ps.emperor.easy_water.application.activity.FirstPartActivity;
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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.gson.Gson;
@ContentView(R.layout.fragment_shuibeng)
public class WaterPumpFragment extends Fragment implements OnClickListener {
	
	//额定功率
	@ViewInject(R.id.et_ed)
	private EditText ratedPower;
	//额定流量
	@ViewInject(R.id.et_ll)
	private EditText ratedFlow;
	//额定扬程
	@ViewInject(R.id.et_yc)
	private EditText ratedHead;
	//变频控制
	@ViewInject(R.id.tb_control)
	private ToggleButton control;
	//日期
	@ViewInject(R.id.et_date)
	private EditText date;
	//维修电话
	@ViewInject(R.id.et_tel)
	private EditText tel;
	
	
	//获取水泵类型
	@ViewInject(R.id.bt_sbinter)
	private  Spinner  pumpType;
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
			case PsUtils.GET_PUMP_TYPE:
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
					System.out.println("baocunwancheng............");
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
		tvc.setText("水泵配置");
		bte.setVisibility(View.VISIBLE);
		bte.setText("保存");
		
		getPumpTypeMethod();
		
		
	    adapter = new ArrayAdapter<String>(getActivity(),R.layout.listview_custom_item,list);
		adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		pumpType.setAdapter(adapter);
		pumpType.setOnItemSelectedListener(new OnItemSelectedListener() {

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

	/*
	 * 获取水泵数据类型
	 */
	private void getPumpTypeMethod() {
		RequestParams rp  = new RequestParams(PsUtils.get_pumpType);
		
		PsUtils.send(rp, HttpMethod.GET, handler, getActivity(), "获取水泵类型中。。。。", PsUtils.GET_PUMP_TYPE);
	}
	
	@Event(R.id.bt_back)
	private void backOnClick(View v){
		getFragmentManager().popBackStack();
	}
	
	private int ta = -1;
	
	@Event(R.id.bt_edit)
	private void editOnClick(View v){
		Toast.makeText(getActivity(), "所属灌溉单元的ID===："+((FirstPartDeviceActivity)getActivity()).getId() , 0).show();

		if(control.isActivated()){
			ta = 1;
		}else{
			ta = 0;
		}
		
		
		//添加保存数据
		RequestParams rp = new RequestParams(PsUtils.add_pump_info);
		
		JSONObject jo = new JSONObject();
		try {
			jo.put("firstDerviceID",((FirstPartDeviceActivity)getActivity()).getId() );
			
			
			jo.put("equTypeID",equTypeID);
			jo.put("pumpType",equTypeName );
			jo.put("ratedPow",ratedPower.getText().toString() );
			jo.put("rateFlu", ratedFlow.getText().toString());
			jo.put("rateLift",ratedHead.getText().toString() );
			jo.put("isPWM", ta);
			jo.put("maintenanceTele", date.getText().toString());
			jo.put("maintenanceTele", tel.getText().toString());
	
			rp.setBodyContent(jo.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		PsUtils.send(rp, HttpMethod.POST, handler, getActivity(), "保存数据中。。。。", PsUtils.SAVE_INFO);
		
		
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_sbinter:
			getPumpTypeMethod();
			
			
			break;

		default:
			break;
		}
		
	}

}
