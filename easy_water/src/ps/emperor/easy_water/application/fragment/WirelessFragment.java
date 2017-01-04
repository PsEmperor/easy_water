package ps.emperor.easy_water.application.fragment;

import org.xutils.x;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import ps.emperor.easy_water.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
@ContentView(R.layout.fragment_wireless_control)
public class WirelessFragment extends Fragment {
	
	@ViewInject(R.id.tv_text)
	private TextView tvc;

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = x.view().inject(this, inflater, container);
		tvc.setText("无线阀控器测试");

		return view;
	}
	
	@Event(R.id.bt_back)
	private void backOnClick(View v){
		getFragmentManager().popBackStack();
	}
	


}
