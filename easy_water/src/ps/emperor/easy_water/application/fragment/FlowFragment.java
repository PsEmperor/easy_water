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
@ContentView(R.layout.fragment_flow)
public class FlowFragment extends Fragment {
	
	@ViewInject(R.id.tv_text)
	private TextView tvc;
	@ViewInject(R.id.bt_edit)
	private Button bte;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = x.view().inject(this, inflater, container);
		tvc.setText("流量配置");
		bte.setVisibility(View.VISIBLE);
		bte.setText("保存");
		return view;
	}
	
	@Event(R.id.bt_back)
	private void backOnClick(View v){
		getFragmentManager().popBackStack();
	}
	
	@Event(R.id.bt_edit)
	private void editOnClick(View v){
		Toast.makeText(getActivity(), "保存", 0).show();
	}

}
