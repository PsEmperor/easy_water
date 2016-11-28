package ps.emperor.easy_water.application;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xmlpull.v1.XmlPullParser;
import org.xutils.BuildConfig;
import org.xutils.x;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;

import ps.emperor.easy_water.R;
import ps.emperor.easy_water.activity.IrriagteOrWaterActivity;
import ps.emperor.easy_water.entity.Person;
import ps.emperor.easy_water.fragment.ApplyIrrigateFragment;
import ps.emperor.easy_water.fragment.ApplyWaterDistrbutionFragment;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ApplicationFragment extends Fragment implements
		OnItemClickListener {

	private Button btB, btE;
	private TextView tv_midd;
	private GridView gvApp;
	private SimpleAdapter sa;
	private AppGvAdapter aga;
	private List<AppBeen> list = new ArrayList<AppBeen>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_application, null);
		findView(view);
		return view;

	}

	private void findView(View view) {
		btB = (Button) view.findViewById(R.id.bt_back);
		btE = (Button) view.findViewById(R.id.bt_edit);
		tv_midd = (TextView) view.findViewById(R.id.tv_text);
		gvApp = (GridView) view.findViewById(R.id.gv_app);

		btB.setVisibility(View.INVISIBLE);
		tv_midd.setText(R.string.application_name);
		btE.setVisibility(View.INVISIBLE);

		aga = new AppGvAdapter(getActivity(),
				android.R.layout.simple_list_item_1, list);
		addContent();
		gvApp.setAdapter(aga);

		gvApp.setOnItemClickListener(this);

	}
	
	/**
	 * 给list集合赋值 AppBeen 名称为汉字缩写
	 */
	private void addContent() {
		AppBeen gg = new AppBeen(R.drawable.ic_launcher, "灌溉");
		AppBeen ps = new AppBeen(R.drawable.ic_launcher, "配水");
		AppBeen wh = new AppBeen(R.drawable.ic_launcher, "维护");
		AppBeen sj = new AppBeen(R.drawable.ic_launcher, "数据");
		AppBeen pz = new AppBeen(R.drawable.ic_launcher, "配置");
		AppBeen cs = new AppBeen(R.drawable.ic_launcher, "测试");

		list.add(gg);
		list.add(ps);
		list.add(wh);
		list.add(sj);
		list.add(pz);
		list.add(cs);

		aga.notifyDataSetChanged();

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// Toast.makeText(getActivity(),
		// position+"----view.getId:"+view.getId(), 0).show();
		switch (position) {
		case 0:
			//ApplyIrrigateFragment  灌溉
//			Toast.makeText(getActivity(),
//					position + "" + ((TextView) view).getText() + "被点击", 0)
//					.show();
			
			
			ApplyIrrigateFragment af = new ApplyIrrigateFragment();
			getActivity().getFragmentManager().beginTransaction()
					.replace(R.id.fl, af).addToBackStack(null).commit();
			
			
			break;
		case 1:
			//ApplyWaterDistrbutionFragment  配水
//			Toast.makeText(getActivity(),
//					position + "" + ((TextView) view).getText() + "被点击", 0)
//					.show();
			
			ApplyWaterDistrbutionFragment ps = new ApplyWaterDistrbutionFragment();
			getActivity().getFragmentManager().beginTransaction()
					.replace(R.id.fl, ps).addToBackStack(null).commit();
			break;
		case 2:
			//IrriagteOrWaterActivity  维护
//			Toast.makeText(getActivity(),
//					position + "" + ((TextView) view).getText() + "被点击", 0)
//					.show();
			Intent intent = new Intent(getActivity(),IrriagteOrWaterActivity.class);
			startActivity(intent);
			break;
		case 3:
			//数据
			Toast.makeText(getActivity(),
					position + "" + ((TextView) view).getText() + "被点击", 0)
					.show();
			break;
		case 4:
			// 配置
			// Toast.makeText(getActivity(),
			// position+""+((TextView)view).getText()+"被点击", 0).show();

			Intent i4 = new Intent(getActivity(), ConfigureActivity.class);
			startActivity(i4);

			break;
		case 5:
			// 测试
			// Toast.makeText(getActivity(),
			// position+""+((TextView)view).getText()+"被点击", 0).show();
			Intent i5 = new Intent(getActivity(), TestingActivity.class);
			startActivity(i5);
			break;

		}


	}
	
}
