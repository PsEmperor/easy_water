package ps.emperor.easy_water.application.fragment;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.application.activity.WaterGateActivity;
import ps.emperor.easy_water.application.entity.BaseBeen;
import ps.emperor.easy_water.utils.PsUtils;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

@ContentView(R.layout.fragment_dankong)
public class DanKongFragment extends Fragment {
	@ViewInject(R.id.bt_back)
	private Button btb;
	@ViewInject(R.id.bt_edit)
	private Button bte;
	@ViewInject(R.id.tv_text)
	private TextView tvc;
	private static int tag = 1;
	@ViewInject(R.id.tv_kongNum)
	private TextView tv_kn;
	@ViewInject(R.id.bt_next)
	private Button next;
	@ViewInject(R.id.et_getUpInfo)
	private EditText info;
	@ViewInject(R.id.et_gateWid)
	private EditText gateWid;
	@ViewInject(R.id.et_high)
	private EditText high;

	private Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case PsUtils.SAVE_INFO:
				// 添加解析判断
				String result = (String) msg.obj;
				// Toast.makeText(FirstPartActivity.this,
				// "信息为-----------："+result, 0).show();
				System.out.println("信息为-----------：" + result);
				Gson g = new Gson();
				BaseBeen bb = g.fromJson(result, BaseBeen.class);
				String code = bb.getCode();

				if (code.equals("1")) {
					Toast.makeText(getActivity(), "保存完成", 0).show();
				} else {
					Toast.makeText(getActivity(), "保存失败", 0).show();
				}

				break;

			default:
				break;
			}
		};
	};

	@SuppressLint("ResourceAsColor")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = x.view().inject(this, inflater, null);
		control();
		int pore = ((WaterGateActivity) getActivity()).getPore();
		if (tag >= pore) {
			next.setEnabled(false);
			next.setBackgroundResource(R.drawable.btn_shape_p);
		}
		tv_kn.setText(tag + "");
		return v;
	}

	private void control() {
		btb.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (tag > 1) {
					--tag;
				}
				getFragmentManager().popBackStack();

			}
		});

		bte.setVisibility(View.VISIBLE);
		bte.setText("保存");
		bte.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 保存
				/*
				 * "disEquID":"配水设备1", "poreID":"1", "poreWidth":"10",
				 * "poreHigh":"10"
				 */

				String ur = PsUtils.add_gate_info;

				RequestParams rp = new RequestParams(ur);

				JSONObject jo = new JSONObject();

				try {
					jo.put("disEquID", info.getText().toString());
					jo.put("poreID", tv_kn.getText().toString());
					jo.put("poreWidth", gateWid.getText().toString());
					jo.put("poreHigh", high.getText().toString());

					rp.setBodyContent(jo.toString());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				PsUtils.send(rp, HttpMethod.POST, handler, getActivity(),
						"保存数据中。。。", PsUtils.SAVE_INFO);

			}
		});

		tvc.setText("单孔闸门配置");

	}

	@Event(R.id.bt_next)
	private void nextKong(View v) {
		tag++;
		DanKongFragment dkf = new DanKongFragment();
		getFragmentManager().beginTransaction().replace(R.id.con, dkf)
				.addToBackStack(null).commit();
	}

}
