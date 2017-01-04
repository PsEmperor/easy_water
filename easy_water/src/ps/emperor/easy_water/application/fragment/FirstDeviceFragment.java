package ps.emperor.easy_water.application.fragment;

import org.xutils.x;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import ps.emperor.easy_water.R;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
@ContentView(R.layout.fragment_water_pump)
public class FirstDeviceFragment extends Fragment implements OnClickListener {
	
	@ViewInject(R.id.bt_back)
	private Button btb;
	@ViewInject(R.id.bt_edit)
	private Button bte;
	@ViewInject(R.id.tv_text)
	private TextView tvc;
	//设备ID  getText()+获取
	@ViewInject(R.id.tv_stitle)
	private TextView tv_t;
	//水源
//	@ViewInject(R.id.tv_qd)
//	private TextView tv_sy;
	//轮灌
//	@ViewInject(R.id.tv_lg)
//	private TextView tv_lg;
	//水泵
	@ViewInject(R.id.tv_sb)
	private TextView tv_sb;
	//冲洗
	@ViewInject(R.id.tv_cx)
	private TextView tv_cx;
	//施肥机
	@ViewInject(R.id.tv_sf)
	private TextView tv_sf;
	//流量计
	@ViewInject(R.id.tv_ll)
	private TextView tv_ll;
	//压力
	@ViewInject(R.id.tv_yl)
	private TextView tv_yl;
	//水位
	@ViewInject(R.id.tv_sw)
	private TextView tv_sw;
	@ViewInject(R.id.ll_sy)
	private LinearLayout ll_sy;
	@ViewInject(R.id.ll_lg)
	private LinearLayout ll_lg;
	@ViewInject(R.id.ll_sb)
	private LinearLayout ll_sb;
	@ViewInject(R.id.ll_cx)
	private LinearLayout ll_cx;
	@ViewInject(R.id.ll_sf)
	private LinearLayout ll_sf;
	@ViewInject(R.id.ll_ll)
	private LinearLayout ll_ll;
	@ViewInject(R.id.ll_yl)
	private LinearLayout ll_yl;
	@ViewInject(R.id.ll_sw)
	private LinearLayout ll_sw;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = x.view().inject(this, inflater, null);
		control();
		return v;
	}
	
	private void control() {
		tvc.setText("首部设备");
//		ll_sy.setOnClickListener(this);
//		ll_lg.setOnClickListener(this);
		ll_sb.setOnClickListener(this);
		ll_cx.setOnClickListener(this);
		ll_sf.setOnClickListener(this);
		ll_ll.setOnClickListener(this);
		ll_yl.setOnClickListener(this);
		ll_sw.setOnClickListener(this);
		
	}




	@Override
	public void onClick(View v) {
		switch (v.getId()) {
//		case R.id.ll_sy:
//			final String[] arr = {"渠道之蓄水池取水","渠道之直接取水","机井取水","管道自压取水"};
//			eject(arr,tv_sy,null);
			
//			break;
//		case R.id.ll_lg:
//			final String[] arr1 = {"支管轮灌","辅管轮灌"};
//			eject(arr1, tv_lg,null);
//			
//			break;
		case R.id.ll_sb://水泵
			final String[] arrsb = {"有","无"};
			WaterPumpFragment sb = new WaterPumpFragment();
			eject(arrsb, tv_sb,sb);
			break;
		case R.id.ll_cx://过滤器
//			Toast.makeText(getActivity(), getResources().getText(v.getId()), 0).show();
			final String[] arrcx = {"有","无"};
			GlqFragment cx = new GlqFragment();
			eject(arrcx, tv_cx,cx);
			break;
/*		case R.id.ll_sf:
			final String[] arr2 = {"有","无"};
			eject(arr2, tv_sf,null);
			break;*/
		case R.id.ll_ll://流量配置
			final String[] arrll = {"有","无"};
			FlowFragment ll = new FlowFragment();
			eject(arrll, tv_ll,ll);
			break;
		case R.id.ll_yl://压力配置
			final String[] arryl = {"有","无"};
			PressureFragment yl = new PressureFragment();
			eject(arryl, tv_yl,yl);
			break;
		case R.id.ll_sw://水位配置
			final String[] arrsw = {"有","无"};
			WaterLevelFragment sw = new WaterLevelFragment();
			eject(arrsw, tv_sw,sw);
			break;

		default:
			break;
		}
		
	}




	/**
	 * 弹出框
	 * @param arr
	 * @param tv
	 */
	private void eject(final String[] arr,final TextView tv,final Fragment fragment) {
		AlertDialog.Builder b = new AlertDialog.Builder(getActivity()).setItems(arr, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				tv.setText(arr[which]);
				switch (which) {
				case 0:
					Toast.makeText(getActivity(),"有", 0).show();
					if(fragment!=null){
						getFragmentManager().beginTransaction().replace(R.id.ff_pump, fragment).addToBackStack(null).commit();
					}
					
					break;
				case 1:
					Toast.makeText(getActivity(),"无", 0).show();
					break;

				default:
					break;
				}
				
			}
		});
		b.show();
	}


}
