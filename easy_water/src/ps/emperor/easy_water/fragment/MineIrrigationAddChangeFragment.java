package ps.emperor.easy_water.fragment;


import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.adapter.IrrigationAddAdapter;
import ps.emperor.easy_water.application.ApplicationFragment;
import ps.emperor.easy_water.entity.KeyWordBean;
import ps.emperor.easy_water.greendao.DBHelper;
import ps.emperor.easy_water.greendao.Irrigation;
import ps.emperor.easy_water.utils.CheckUtil;
import ps.emperor.easy_water.utils.SharedUtils;
import ps.emperor.easy_water.view.MainActionBar;

/**
 * 添加關聯設備
 * 
 * @author 毛國江
 * @version 2016-5-18 上午9:42
 */
@SuppressLint("NewApi")
public class MineIrrigationAddChangeFragment extends Fragment implements OnClickListener {
	private LayoutInflater mInflater;
	private MainActionBar actionBar;
	private ListView listView;
	private IrrigationAddAdapter adapter;
	private List<KeyWordBean> beans;
	private TextView irrigation_add, irrigation_canel;
	private DBHelper dBManager;
	private Irrigation entity ;
	private int tag;
	// private CheckBox checkBox;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mInflater = inflater;
		View view = inflater.inflate(R.layout.fragment_mine_irrigation_add, container, false);

		actionBar = (MainActionBar) view.findViewById(R.id.actionbar_irrigation_add);
		actionBar.setLeftIcon(R.drawable.btn_back_selector);
		actionBar.setRightIcon(R.drawable.ic_launcher);
		actionBar.setTitle("关联新灌溉设备");
		actionBar.setActionBarOnClickListener(this);
		
		listView = (ListView) view.findViewById(R.id.list_irrigation_add);// 关联设备列表
		irrigation_add = (TextView) view.findViewById(R.id.text_irrigation_add);// 关联选中设备
		irrigation_canel = (TextView) view.findViewById(R.id.text_irrigation_canel);// 清空当前选中
		irrigation_add.setOnClickListener(this);
		irrigation_canel.setOnClickListener(this);

		dBManager = DBHelper.getInstance(getActivity());    //得到DBHelper对象  
		List<Irrigation> listentity = dBManager.loadAllSession();  
		tag = (Integer) SharedUtils.getParam(getActivity(), "tag", 100);
	       
	  //下面这一行就把entity对象存数据库了，然后我们新建一个SessionEntity列表再读一下  
	  
	  
	         //下面这个方法是查询Session表里面的所有数据返回一个数据列表，相当于select * from table，然后扫描打印出来  
	      
			
			
			for(int i=0;i<listentity.size();i++)  
			{
				Irrigation tmpEntity = listentity.get(i);  
				Log.v("tmpEntity.getIrrigation()",tmpEntity.getIrrigation());  
				Log.v("tmpEntity.getGroupnumber()",tmpEntity.getGroupnumber()+"");  
				Log.v("tmpEntity.getValuenumber()",tmpEntity.getValuenumber()+"");  
			}  
		// checkBox.setOnCheckedChangeListener(listener1);
		adapter = new IrrigationAddAdapter(getActivity());
		beans = new ArrayList<KeyWordBean>();
		KeyWordBean bean;
		for (int i = 0; i < 10; i++) {
			bean = new KeyWordBean();
			bean.setkeyword("141团5连4#灌溉单元");
			beans.add(bean);
		}
		adapter.addData(beans, false);
		listView.setAdapter(adapter);
		beans = adapter.getData();
		
		for (int i = 0; i < beans.size(); i++) {
	    	   entity = new Irrigation();    //创建一个SessionEntity实体对象，并赋值  
	    	   entity.setIrrigation(beans.get(i).getkeyword());
	    	   entity.setGroupnumber(5);
	    	   entity.setValuenumber(5);
	    	   entity.setIsrelevance(0);
	    	   if(!CheckUtil.IsEmpty(listentity)){
	    		   if(entity.getIrrigation().equals(listentity.get(i).getIrrigation())){
	    	   }
	    	   }else{
	    		   dBManager.saveSession(entity);    //保存到数据库  
	    	   
		}
		}
		return view;
	}

	@Override
	public void onClick(View v) {
		FragmentManager fgManager = getFragmentManager();
		FragmentTransaction transaction = fgManager.beginTransaction();
		switch (v.getId()) {
		case R.id.acitionbar_left:
			MineUserRoleFragment fragment = new MineUserRoleFragment();
//			transaction.setCustomAnimations(R.anim.right_in, R.anim.right_out);
			transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.replace(R.id.fl, fragment, "main");
			transaction.commit();
			break;
		case R.id.text_irrigation_add:
			if(tag == 1){
				for (int i = 0; i < beans.size(); i++) {
					if(beans.get(i).isCheck()==true){
						MineWaterAddChangeFragment fragment1 = new MineWaterAddChangeFragment();
						// transaction.setCustomAnimations(R.anim.right_in,
						// R.anim.right_out);
						transaction
						.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
						transaction.replace(R.id.fl, fragment1, "main");
						transaction.commit();	
					}
				}
			}else{
				ApplicationFragment fragment1 = new ApplicationFragment();
				// transaction.setCustomAnimations(R.anim.right_in,
				// R.anim.right_out);
				transaction
				.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
				transaction.replace(R.id.fl, fragment1, "main");
				transaction.commit();	
			}
			break;
		case R.id.text_irrigation_canel:
			for (int i = 0; i < beans.size(); i++) {
				beans.get(i).isCheck = false;
			}
			adapter.notifyDataSetChanged();
			break;
		default:
			break;
		}
	}
}
