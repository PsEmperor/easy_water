package ps.emperor.easy_water.fragment;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.ex.HttpException;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;

import com.google.gson.Gson;


import ps.emperor.easy_water.BaseActivity;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.adapter.ApplyIrrigateSingleValveAdapter;
import ps.emperor.easy_water.adapter.RoleAdapter;
import ps.emperor.easy_water.entity.RoleBean;
import ps.emperor.easy_water.entity.UserBean;
import ps.emperor.easy_water.utils.NetStatusUtil;
import ps.emperor.easy_water.utils.SharedUtils;
import ps.emperor.easy_water.utils.URL;
import ps.emperor.easy_water.view.MainActionBar;
import ps.emperor.easy_water.view.MainActionBars;
import ps.emperor.easy_water.view.MyGridView;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 用户角色申请
 * 
 * @author 毛国江
 * @version 2016-9-9 下午16:49
 */
public class MineUserRoleChooseFragment extends Fragment implements
		OnClickListener,OnItemClickListener{
	private LayoutInflater mInflater;
	private MainActionBar actionBar;
	private Button btn_choose;
	private ListView listview;
	private RoleAdapter adapter;
	private List<RoleBean> RoleBeans;
	private RoleBean bean;
	private int tag;
	private List<String> list = new ArrayList<String>();
	private ProgressDialog progressDialog;
	private String str;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflater = inflater;
		View view = inflater.inflate(R.layout.fragment_mines_role_choose,
				container, false);
		actionBar = (MainActionBar)view.findViewById(R.id.actionbar_user_role_choose);
		actionBar.setTitle("权限申请");
		actionBar.setLeftIcon(R.drawable.btn_back_selector);
		actionBar.setRightGone();
		actionBar.setActionBarOnClickListener(this);;

		btn_choose = (Button) view.findViewById(R.id.btn_permission_choose);
		listview = (ListView) view.findViewById(R.id.list_permission_choose);
		btn_choose.setOnClickListener(this);
		
		tag = getArguments().getInt("tag");
		
		RoleBeans = new ArrayList<RoleBean>();
		if(1 == tag){
			bean = new RoleBean();
			bean.setRole("灌溉管理员");
			RoleBeans.add(bean);
			
			bean = new RoleBean();
			bean.setRole("配水管理员");
			RoleBeans.add(bean);
		}else if(2 == tag){
			bean = new RoleBean();
			bean.setRole("角色审批");
			RoleBeans.add(bean);
			
			bean = new RoleBean();
			bean.setRole("数据浏览");
			RoleBeans.add(bean);
			
			bean = new RoleBean();
			bean.setRole("灌溉管理员");
			RoleBeans.add(bean);
			
			bean = new RoleBean();
			bean.setRole("配水管理员");
			RoleBeans.add(bean);
		}else if(3 == tag){
			bean = new RoleBean();
			bean.setRole("配置");
			RoleBeans.add(bean);
			
			bean = new RoleBean();
			bean.setRole("测试");
			RoleBeans.add(bean);
			
			bean = new RoleBean();
			bean.setRole("灌溉管理员");
			RoleBeans.add(bean);
			
			bean = new RoleBean();
			bean.setRole("配水管理员");
			RoleBeans.add(bean);
		}
		
		adapter = new RoleAdapter(getActivity());
		adapter.addData(RoleBeans, true);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(this);
		return view;
	}
	
	@Override
	public void onResume() {
		super.onResume();
	}
	@Override
	public void onClick(View v) {
		FragmentManager fgManager = getFragmentManager();
		FragmentTransaction transaction = fgManager.beginTransaction();
		switch (v.getId()) {
		case R.id.acitionbar_left:
			MineUserRoleFragment fragment = new MineUserRoleFragment();
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			transaction.setCustomAnimations(R.anim.slide_fragment_horizontal_right_in, R.anim.slide_fragment_horizontal_left_out);
			transaction.replace(R.id.fl, fragment, "main");
			transaction.commit();
			break;
		case R.id.btn_permission_choose:
			for (int i = 0; i < RoleBeans.size(); i++) {
				if(RoleBeans.get(i).getCheck()==1){
					list.add(RoleBeans.get(i).getRole());
				}
			}
			str = (String) SharedUtils.getParam(getActivity(), "userId", "3");
			try {
				str = java.net.URLEncoder.encode(str, "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			RequestParams params = new RequestParams(URL.forUserInfo); // 网址(请替换成实际的网址)
			// params.addQueryStringParameter("key", "value"); // 参数(请替换成实际的参数与值)
			progressDialog = ProgressDialog.show(getActivity(), "Loading...",
					"Please wait...", true, false);
			JSONObject js_request = new JSONObject();
			try {
				params.setAsJsonContent(true);
				js_request.put("userID", str);
				js_request.put("newRoleID", tag);
				js_request.put("permissionName", list);
				params.setBodyContent(js_request.toString());
				// params.setBodyContent("{\"userId\":\"\",\"userName\":\"\",\"userPhone\":\"\",\"fullName\":\"二狗子\",\"authID\":\"\",\"pathtoPhoto\":\"\"}");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				// params.setAsJsonContent(true);
				// params.setBodyContent("Content-Type: application/json"+js_request.toString());
			}// 根据实际需求添加相应键值对

			x.http().request(HttpMethod.PUT, params, new CommonCallback<String>() {
				@Override
				public void onCancelled(CancelledException arg0) {

				}

				// 注意:如果是自己onSuccess回调方法里写了一些导致程序崩溃的代码，也会回调道该方法，因此可以用以下方法区分是网络错误还是其他错误
				// 还有一点，网络超时也会也报成其他错误，还需具体打印出错误内容比较容易跟踪查看
				@Override
				public void onError(Throwable ex, boolean isOnCallback) {

					Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG)
							.show();
					if (ex instanceof HttpException) { // 网络错误
						HttpException httpEx = (HttpException) ex;
						int responseCode = httpEx.getCode();
						String errorResult = httpEx.getResult();
						Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT);
						// ...
						progressDialog.dismiss();
					} else { // 其他错误
						// ...
						Toast.makeText(getActivity(), "网络请求超时！", Toast.LENGTH_SHORT);
						progressDialog.dismiss();
					}

				}

				// 不管成功或者失败最后都会回调该接口
				@Override
				public void onFinished() {
				}

				@Override
				public void onSuccess(String arg0) {
					Toast.makeText(getActivity(), "请求成功", Toast.LENGTH_SHORT);
					Gson gson = new Gson();
					UserBean fromJson = gson.fromJson(arg0, UserBean.class);
					progressDialog.dismiss();
				}
			});
			list.clear();
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if(RoleBeans.get(position).getCheck()==0){
			RoleBeans.get(position).setCheck(1);
		}else{
			RoleBeans.get(position).setCheck(0);
		}
		adapter.notifyDataSetChanged();
	}
}
