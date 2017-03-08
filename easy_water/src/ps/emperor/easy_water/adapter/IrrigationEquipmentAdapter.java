package ps.emperor.easy_water.adapter;


import java.util.List;


import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.ex.HttpException;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;

import com.google.gson.Gson;

import android.app.ProgressDialog;
import android.content.Context;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.entity.UserReleIrrInfoBean;
import ps.emperor.easy_water.entity.UserReleIrrInfoBean.infoList;
import ps.emperor.easy_water.utils.SharedUtils;
import ps.emperor.easy_water.utils.URL;
import android.widget.CompoundButton.OnCheckedChangeListener;
/**
 * 灌溉设备适配器(设备关联)
 * @author 毛国江
 * @version 2016-5-18 上午11:12
 */

public class IrrigationEquipmentAdapter extends MyBaseAdapter<infoList>{
	private Context context;
	private int statusCode,IrrCode,IrrCodes,mark;
	private ProgressDialog progressDialog;
	
	public IrrigationEquipmentAdapter(Context context,int IrrCode) {
		super(context);
		this.context = context;
		this.IrrCode = IrrCode;
	}

	@Override
	public View MyGetView(int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		mark = (int) SharedUtils.getParam(context, "EquipmentMark", 0);
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.fragment_mine_irrigation_equipment_list, null);
			viewHolder = new ViewHolder();
			viewHolder.Irrigation = (TextView) convertView.findViewById(R.id.tv_rrigation_equipment);
			viewHolder.button = (ToggleButton) convertView.findViewById(R.id.toggle_persona_menstruation_message_sets);
			viewHolder.button.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					int position = (Integer) viewHolder.button.getTag();
					list.get(position).isCheck = isChecked;
				}
			});
			viewHolder.button.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					int position = (Integer) viewHolder.button.getTag();
					RequestParams param3 = new RequestParams(URL.IrrstatusCode);  // 网址(请替换成实际的网址) 
//					 params.addQueryStringParameter("key", "value"); // 参数(请替换成实际的参数与值)   
					progressDialog = ProgressDialog.show(context, "Loading...",
							"Please wait...", true, false);
					JSONObject js_request = new JSONObject();
					try {
						param3.setAsJsonContent(true);
						js_request.put("userID", "3");
						js_request.put("firstDerviceID", list.get(position).getFirstDerviceID());
						if(list.get(position).isCheck){
							statusCode = 1;
						}else{
							statusCode = 0;
						}
						js_request.put("statusCode", statusCode);
						param3.setBodyContent(js_request.toString());
					} catch (Exception e) {
						e.printStackTrace();
//						param3.setBodyContent("Content-Type: application/json"+js_request.toString());
						param3.setAsJsonContent(true);
					}//根据实际需求添加相应键值对
					
				        x.http().request(HttpMethod.PUT ,param3, new CommonCallback<String>() {  
				            @Override  
				            public void onCancelled(CancelledException arg0) {  
				                  
				            }  
				  
				         // 注意:如果是自己onSuccess回调方法里写了一些导致程序崩溃的代码，也会回调道该方法，因此可以用以下方法区分是网络错误还是其他错误  
				            // 还有一点，网络超时也会也报成其他错误，还需具体打印出错误内容比较容易跟踪查看  
				            @Override  
				            public void onError(Throwable ex, boolean isOnCallback) {  
				                  
				                Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();  
				                if (ex instanceof HttpException) { // 网络错误    
				                    HttpException httpEx = (HttpException) ex;  
				                    int responseCode = httpEx.getCode();  
				                    String responseMsg = httpEx.getMessage();  
				                    String errorResult = httpEx.getResult();  
				                    Toast.makeText(context, "请求失败", Toast.LENGTH_SHORT);
				                    progressDialog.dismiss();
				                    // ...  
				                } else { // 其他错误    
				                    // ...  
				                	Toast.makeText(context, "请求失败", Toast.LENGTH_SHORT);
				                	progressDialog.dismiss();
				                }  
				                  
				            }  
				  
				         // 不管成功或者失败最后都会回调该接口  
				            @Override  
				            public void onFinished() {    
				            	Toast.makeText(context, "走了网络请求", Toast.LENGTH_SHORT);
				            }  
				  
				            @Override  
				            public void onSuccess(String arg0) {  
				                  Toast.makeText(context, "请求成功", Toast.LENGTH_SHORT);
				                  progressDialog.dismiss();
				            }  
				        }); 
				}
			});
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		infoList infoList = list.get(position);
		viewHolder.button.setTag(position);
		viewHolder.Irrigation.setText(infoList.getIrriUnitName());
		int status = infoList.getStatusCode();
		if(1 == status){
			viewHolder.button.setChecked(true);
		}else if(0 == status){
			viewHolder.button.setChecked(false);
		}
		return convertView;
	}

	class ViewHolder {
		TextView Irrigation; // 授权单位
		ToggleButton button; // 是否关联
	}

	
	public interface CallBack {
		public void OnCheckedChangeListener();
	}
}
