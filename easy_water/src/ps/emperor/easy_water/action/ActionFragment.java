package ps.emperor.easy_water.action;

import ps.emperor.easy_water.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class ActionFragment extends Fragment {

	private Button btB, btE;
	private TextView tv_midd;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_action, null);

		findView(view);
		return view;
	}

	private void findView(View view) {
		btB = (Button) view.findViewById(R.id.bt_back);
		btE = (Button) view.findViewById(R.id.bt_edit);
		tv_midd = (TextView) view.findViewById(R.id.tv_text);

		btB.setVisibility(View.INVISIBLE);
		tv_midd.setText("事件");
		btE.setVisibility(View.INVISIBLE);

	}

}
