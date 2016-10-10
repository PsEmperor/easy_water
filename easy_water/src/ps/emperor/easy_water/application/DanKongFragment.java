package ps.emperor.easy_water.application;

import org.xutils.x;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import ps.emperor.easy_water.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
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

	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = x.view().inject(this, inflater, null);
		control();
		tv_kn.setText(tag+"");
		return v;
	}

	private void control() {
		btb.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(tag>1){
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
				//保存
				Toast.makeText(getActivity(),"保存数据返回上级菜单",Toast.LENGTH_SHORT).show();
				
			}
		});
		
		tvc.setText("单孔闸门配置");
		
	}
	
	@Event(R.id.bt_next)
	private void nextKong(View v){
		tag++;
		DanKongFragment dkf = new DanKongFragment();
		getFragmentManager().beginTransaction().replace(R.id.con, dkf)
				.addToBackStack(null).commit();
	}
	
	

}
