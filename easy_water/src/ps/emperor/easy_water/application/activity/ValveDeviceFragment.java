package ps.emperor.easy_water.application.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.application.entity.ValueControlBeen;
import ps.emperor.easy_water.application.entity.ValueControlBeen.ChanListBean;
import ps.emperor.easy_water.utils.PsUtils;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.location.LocationManager;
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
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
@ContentView(R.layout.activity_valvedevice)
public class ValveDeviceFragment extends Fragment implements android.view.View.OnClickListener{
	//设备名称
	@ViewInject(R.id.et_mc)
	private EditText et_mc;
	//位置 经度
	@ViewInject(R.id.et_location1)
	private EditText lo1;
	//纬度
	@ViewInject(R.id.et_location2)
	private EditText lo2;
	@ViewInject(R.id.et_id)
	private EditText eid;
	
	
	
	
	
	
	
	//4个通道对应的地块和面积值
	@ViewInject(R.id.et_channel1)
	private EditText et_c1;
	@ViewInject(R.id.et_area1)
	private EditText et_a1;
	@ViewInject(R.id.et_channel2)
	private EditText et_c2;
	@ViewInject(R.id.et_area2)
	private EditText et_a2;
	@ViewInject(R.id.et_channel3)
	private EditText et_c3;
	@ViewInject(R.id.et_area3)
	private EditText et_a3;
	@ViewInject(R.id.et_channel4)
	private EditText et_c4;
	@ViewInject(R.id.et_area4)
	private EditText et_a4;
	
	
	//4个通道对应的4个前缀
	@ViewInject(R.id.tv_pre1)
	private TextView pre1;
	@ViewInject(R.id.tv_pre2)
	private TextView pre2;
	@ViewInject(R.id.tv_pre3)
	private TextView pre3;
	@ViewInject(R.id.tv_pre4)
	private TextView pre4;
	
	
	@ViewInject(R.id.bt_back)
	private Button btb;
	@ViewInject(R.id.bt_next)
	private Button btn;
	@ViewInject(R.id.title)
	private ps.emperor.easy_water.utils.Ltitle title;
	@ViewInject(R.id.tv_sd)
	private TextView  sd;
	@ViewInject(R.id.ll_dw)
	private LinearLayout lldw;
	@ViewInject(R.id.ll_1_td)
	private LinearLayout ll1_td;
	@ViewInject(R.id.ll_1_mj)
	private LinearLayout ll1_mj;
	//判断通道1和面积是否隐藏
	private boolean t1;
	
	@ViewInject(R.id.ll_2_td)
	private LinearLayout ll2_td;
	@ViewInject(R.id.ll_2_mj)
	private LinearLayout ll2_mj;
	private boolean t2;
	
	@ViewInject(R.id.ll_3_td)
	private LinearLayout ll3_td;
	@ViewInject(R.id.ll_3_mj)
	private LinearLayout ll3_mj;
	private boolean t3;
	
	@ViewInject(R.id.ll_4_td)
	private LinearLayout ll4_td;
	@ViewInject(R.id.ll_4_mj)
	private LinearLayout ll4_mj;
	private boolean t4;
	@ViewInject(R.id.sp_fm)
	private Spinner sp_fm;
	@ViewInject(R.id.sp_gd)
	private Spinner sp_gd;
	@ViewInject(R.id.tv_td)
	private TextView tv_td;
	private static int tag;
	private NumberPicker np;
//	@ViewInject(R.id.tv_location)
//	private TextView tv_lo;
	private LocationManager lm;
	//父类名称   隐藏授权单位、上级设备为灌溉单元名称
	private String superName;
	//灌溉单元名称
	private String iName;
	//轮灌方式  辅管--支管      支管--分干
	private String iMode;
	//类别      虚拟--短信阀控器    实体--全部  
	private String ct;
	//管道名称  分干或支管
	private String pipeN="";
	//获取的上级设备id
	String deviceId;
	
	private String virtualF="虚拟首部";
	private String superN = "FirstPartActivity";
	private String branch = "支管轮灌";
	private String auxiliaryPipe = "辅管轮灌";
	//标题栏右选项
	private Button btEdit;
	//阀控器数量
	private int vNum;
	private ValveDeviceActivity1 context;
	private static int ftag ;
	//管道选择的号码
	private int num;

	
	private Handler handler = new Handler(){
		
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case PsUtils.SEND_REGISTER:
				//判断结果----未完成
				System.out.println("add_valveDevice------------result========="+msg.obj.toString());
				
				
				
				
				
				
				
				
				
				break;

			default:
				break;
			}
			
		};
	};
	

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		context = (ValveDeviceActivity1)getActivity();
		
		superName = context.getSuper();
		iName = context.getIrrName();
		iMode = context.getIrrMode();
		ct = context.getClassType();
		vNum = context.getValueNum();
		ftag = context.getFVal();
	    deviceId = context.getDeviceId();
		
		View view = x.view().inject(this, inflater, container);
		
	
		
		
		Button st = title.setText("阀控器配置");
		btEdit = title.setEditText("保存");
		
		btEdit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if("虚拟首部".equals(ct)){
					//添加限制设备id校验
					
					boolean checkPhoneNum = PsUtils.checkPhoneNum(eid.getText().toString());
					if(!checkPhoneNum){
						
						AlertDialog.Builder b = new Builder(context);
						b.setMessage("虚拟首部时设备id应为手机号码，请重新输入！！！~");
						b.setPositiveButton("确定",new Dialog.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								eid.requestFocus();
								
							}
						});
						b.setCancelable(false);
						b.show();
						
						
						
						return;
					}
					
					
				}
				
				String arr[] = {et_c1.getText().toString(),et_c2.getText().toString(),
						et_c3.getText().toString(),et_c4.getText().toString()};
	
		
				
				for (int j = 0; j < arr.length-1; j++) {
					for (int i = j+1; i < arr.length; i++) {
						if(arr[j].equals(arr[i])){
							Toast.makeText(context, "通道的地块名不能相同，请重新输入！！！", 1).show();
							et_c1.requestFocus();
							return;
						}
					}
				}
				
				
				
				//添加数据-----未完成
				/**
				 * 灌溉单元ID    firstDerviceID : String deviceId
				 * 设备类别：  valueControlType : sp_fm.getSelectedItem().toString()
				 * 设备名称：valueControlName : et_mc.getText().toString();
				 * 经度： "longitude": lo1.getText().toString();
				 * 纬度： "latitude":  lo2.getText().toString();
				 * 授权单位 ："authID":"1" 
				 * 上级设备： "supperEqu": iName
				 * 所属管道 ： "thrieChan": sp_gd.getSelectedItem().toString()
				 * 通道数量："totalChanNum": tv_td.getText().toString();
				 * 面积："area":   0  int mj = mj1+mj2+mj3+mj4;
				 * 通道集合："chanList": 
				 * 阀控器ID  ValueControlID
				 * 
				 */
				
				//创建对象集合
				JSONArray lis = createJSONArray();
							
				RequestParams rp  = new RequestParams(PsUtils.add_control_info);
				JSONObject jo = new JSONObject();
				try {
					jo.put("firstDerviceID",deviceId);
					jo.put("valueControlType",sp_fm.getSelectedItem().toString());
					jo.put("valueControlName",et_mc.getText().toString());
					jo.put("longitude",lo1.getText().toString());
					jo.put("latitude",lo2.getText().toString());
					jo.put("authID","1");
					jo.put("supperEqu",iName);
					jo.put("thrieChan",sp_gd.getSelectedItem().toString());
					jo.put("totalChanNum",tv_td.getText().toString());
					jo.put("area","0");
					jo.put("ValueControlID", eid.getText().toString());
					jo.put("chanList",lis);

					rp.setBodyContent(jo.toString());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				PsUtils.send(rp, HttpMethod.POST, handler, context, "数据保存中。。。");
				
				
				
				
			}
			
			/**
			 * 创建对象集合
			 * @return list
			 */
			private JSONArray createJSONArray(){
				JSONArray a = new JSONArray();
				
				
				
				try {
					JSONObject o1 = new JSONObject();
					o1.put("chan", "1");
					o1.put("chanNum", num+"-"+et_c1.getText().toString());
					o1.put("area", et_a1.getText().toString());
					
					JSONObject o2 = new JSONObject();
					o2.put("chan", "2");
					o2.put("chanNum", num+"-"+et_c2.getText().toString());
					o2.put("area", et_a2.getText().toString());
					JSONObject o3 = new JSONObject();
					o3.put("chan", "3");
					o3.put("chanNum", num+"-"+et_c3.getText().toString());
					o3.put("area", et_a3.getText().toString());
					
					JSONObject o4 = new JSONObject();
					o4.put("chan", "4");
					o4.put("chanNum", num+"-"+et_c4.getText().toString());
					o4.put("area", et_a4.getText().toString());
					
					
					switch (tv_td.getText().toString()) {
					case "0":
						
						break;
					case "1":
						a.put(o1);
		
						break;
					case "2":
						a.put(o1);
						a.put(o2);

						break;
					case "3":
						a.put(o1);
						a.put(o2);
						a.put(o3);
						break;
					case "4":
						a.put(o1);
						a.put(o2);
						a.put(o3);
						a.put(o4);
						break;
						

					default:
						break;
					}
					
					
					
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
				
				return a;
			}


		});
		
		btn.setOnClickListener(this);
		btb.setOnClickListener(this);
		sp_gd.setOnItemSelectedListener(new OnItemSelectedListener() {

			
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				num = listN.get(position);
				pre1.setText(num+"-");
				pre2.setText(num+"-");
				pre3.setText(num+"-");
				pre4.setText(num+"-");
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		
		if(branch.equals(iMode)){
			pipeN = "分干";
		}else if(auxiliaryPipe.equals(iMode)){
			pipeN = "支管";
		}
		
		
		if(superN.equals(superName)){
			lldw.setVisibility(View.GONE);
			sd.setText(iName);
			
			if(virtualF.equals(ct)){
				
				sp_fm.setSelection(0);
				sp_fm.setEnabled(false);  
			
			}
			
		}

		lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
//		PsUtils.readGPS(lm,tv_lo);
		initfm();
		initfg();
		if(vNum != ftag){
			if(context.getNum()!=-1){
				sp_fm.setSelection(context.getNum());
			}
			sp_fm.setEnabled(false);
			
		}

		return view;
	}
	
	@Event(R.id.tv_td)
	private void tdOnClick(View v){
		np = new NumberPicker(getActivity());
		final String[] arr = {"0", "1", "2", "3", "4","0","1","2","3","4"};
		np.setDisplayedValues(arr);
		np.setMinValue(0);
		np.setMaxValue(arr.length-1);
		if (tag != 0) {
		np.setValue(tag);
		}
		// np.setValue(4);
		// 关闭可编辑状态
		np.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
		AlertDialog b = new Builder(getActivity()).setView(np)
		.setPositiveButton("确定", new AlertDialog.OnClickListener() {
		// .setNegativeButton("取消", null)
		@Override
		public void onClick(DialogInterface dialog, int which) {
		// 获取当前选中的角标位并记录
		tag = np.getValue();
		// str[tag] 取得才是角标对应数组中的值
		tv_td.setText(arr[tag]);
		tagIf(Integer.parseInt(arr[tag]));

		}
		}).show();
		// 设置弹出框的大小
		b.getWindow().setLayout(500, 500);
		
		
		
	}
	
	private List<Integer> listN;
	
	/**
	 * 初始化分干管
	 */
	private void initfg() {
		List<String> list = new ArrayList<String>();
		listN = new ArrayList<Integer>();
		for(int i =1;i<11;i++){
			list.add(i+pipeN);
			listN.add(i);
		}

		
		ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,list);
		adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		sp_gd.setAdapter(adapter);
		
	}

	/**
	 * 初始化设备类别
	 */
	private void initfm() {
		List<String> list = new ArrayList<String>();
		list.add("短信阀控器");
		list.add("无线阀控器");
		list.add("有线阀控器");
		
		ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,list);
		adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		sp_fm.setAdapter(adapter);
	}
	
	/*
	 * 判断通道是否隐藏方法
	 */
	private void tdIf(){
		if(t1){
			ll1_td.setVisibility(View.VISIBLE);
			ll1_mj.setVisibility(View.VISIBLE);
		}else{
			ll1_td.setVisibility(View.GONE);
			ll1_mj.setVisibility(View.GONE);
			et_c1.setText("");
			et_a1.setText("");
		}
		
		if(t2){
			ll2_td.setVisibility(View.VISIBLE);
			ll2_mj.setVisibility(View.VISIBLE);
		}else{
			ll2_td.setVisibility(View.GONE);
			ll2_mj.setVisibility(View.GONE);
			et_c2.setText("");
			et_a2.setText("");
		}
		if(t3){
			ll3_td.setVisibility(View.VISIBLE);
			ll3_mj.setVisibility(View.VISIBLE);
		}else{
			ll3_td.setVisibility(View.GONE);
			ll3_mj.setVisibility(View.GONE);
			et_c3.setText("");
			et_a3.setText("");
		}
		if(t4){
			ll4_td.setVisibility(View.VISIBLE);
			ll4_mj.setVisibility(View.VISIBLE);
		}else{
			ll4_td.setVisibility(View.GONE);
			ll4_mj.setVisibility(View.GONE);
			et_c4.setText("");
			et_a4.setText("");
		}
		
	}
	
	/**
	 * 根据tag的数量判断td是否隐藏
	 */
	private void tagIf(int tag){
		switch (tag) {
		case 0:
			t1 = false;
			t2 = false;
			t3 = false;
			t4 = false;
			
			break;
		case 1:
			t1 = true;
			t2 = false;
			t3 = false;
			t4 = false;
			
			break;
		case 2:
			t1 = true;
			t2 = true;
			t3 = false;
			t4 = false;
			
			break;
		case 3:
			t1 = true;
			t2 = true;
			t3 = true;
			t4 = false;
			
			break;
		case 4:
			t1 = true;
			t2 = true;
			t3 = true;
			t4 = true;
			
			break;
			
		

		default:
			break;
		}
		
		tdIf();
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_next:
			//下一个点击事件
			if(vNum==ftag){
				context.setNum(sp_fm.getSelectedItemPosition());
			}
			
			sp_fm.setEnabled(false);
			context.numMethod();
			if(vNum<1){
				btn.setClickable(false);
				Toast.makeText(getActivity(), "已经到最后一个阀门了~", 0).show();
			}else{
				ValveDeviceFragment dkf = new ValveDeviceFragment();
				getFragmentManager().beginTransaction().add(R.id.coo, dkf)
						.addToBackStack(null).commit();
			}
			
			break;
			
		case R.id.bt_back:
			vNum++;
			if(vNum>ftag){
				getActivity().finish();
			}
			getFragmentManager().popBackStack();
			
			break;

		default:
			break;
		}
		
	}
	


}
