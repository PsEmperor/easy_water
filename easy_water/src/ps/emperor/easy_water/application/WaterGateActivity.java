package ps.emperor.easy_water.application;

import java.util.ArrayList;
import java.util.List;

import org.xutils.x;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import ps.emperor.easy_water.BaseActivity;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.utils.PsUtils;
import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

@ContentView(R.layout.activity_watergate)
public class WaterGateActivity extends BaseActivity {
	private TextView tvt;
	private Button bte;
	@ViewInject(R.id.sp_k)
	private Spinner sp_k;
	private LocationManager lm;
//	@ViewInject(R.id.tv_location)
//	private TextView tv_lo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		x.view().inject(this);
		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		findView();
//		PsUtils.readGPS(lm, tv_lo);
		initKong();
	}

	/**
	 * 初始化孔数量
	 */
	private void initKong() {
		List<String> list = new ArrayList<String>();
		for (int i = 1; i < 11; i++) {
			list.add("\t\t\t\t\t\t\t" + i);
		}

		ArrayAdapter adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, list);
		adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		sp_k.setAdapter(adapter);
	}

	/**
	 * 初始化控件
	 */
	private void findView() {
		tvt = (TextView) findViewById(R.id.tv_text);
		bte = (Button) findViewById(R.id.bt_edit);

		tvt.setText("闸门配置");
		bte.setVisibility(View.VISIBLE);
		bte.setText("保存");
	}

	@Event(R.id.bt_dk)
	private void configDanKong(View v) {
		// 进入单孔配置fragment
		DanKongFragment dkf = new DanKongFragment();
		getFragmentManager().beginTransaction().replace(R.id.con, dkf)
				.addToBackStack(null).commit();
	}
}
