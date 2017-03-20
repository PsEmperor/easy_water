package ps.emperor.easy_water.homepage;

import java.util.ArrayList;
import java.util.List;

import org.xutils.x;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import cn.jpush.android.api.JPushInterface;

import ps.emperor.easy_water.R;
import ps.emperor.easy_water.utils.PsUtils;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
@ContentView(R.layout.fragment_homepage)
public class HomePageFragment extends Fragment {
	private Button btB,btE;
	private TextView tv_midd;
	@ViewInject(R.id.lv_msg)
	private ListView lv_msg;
	private List list= new ArrayList<String>();
	private ArrayAdapter<String> adapter;
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = x.view().inject(this, inflater, container);
		findView(view);
		return view;
	}

	private void findView(View view) {
		btB = (Button) view.findViewById(R.id.bt_back);
		btE = (Button) view.findViewById(R.id.bt_edit);
		tv_midd = (TextView)view.findViewById(R.id.tv_text);
		
		btB.setVisibility(View.INVISIBLE);
		tv_midd.setText("首页");
		btE.setVisibility(View.VISIBLE);
		adapter=PsUtils.setArrayAdapter(getActivity(), list, lv_msg);
		
	}

}

