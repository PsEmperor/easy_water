package ps.emperor.easy_water.view;


import android.app.Service;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ps.emperor.easy_water.R;
/**
 * Actionbar
 * @author 毛国江
 * @version 2016-5-16 上午9:50
 */
public class MainActionBars extends LinearLayout {
	private Context context;
	private ImageButton acition_bar_left;
	private Button acition_bar_right;
	private TextView acition_bar_title;
	private RelativeLayout layout_container;
	private View layout;

	public MainActionBars(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initView();
	}

	public MainActionBars(Context context) {
		super(context);
		this.context = context;
		initView();
	}

	public void setActionBarOnClickListener(View.OnClickListener listener) {
		acition_bar_title.setOnClickListener(listener);
		acition_bar_left.setOnClickListener(listener);
		acition_bar_right.setOnClickListener(listener);
	}

	private void initView() {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layout = inflater.inflate(R.layout.layout_action_bars, this);
		acition_bar_left = (ImageButton) layout.findViewById(R.id.acitionbar_left);// 返回
		layout_container = (RelativeLayout) layout.findViewById(R.id.layout_container);
		acition_bar_right = (Button) layout.findViewById(R.id.acitionbar_right);// 分享
		acition_bar_title = (TextView) layout.findViewById(R.id.acitionbar_title);// 标题栏
	}

	public void setActionBarBG(int drable) {
		layout_container.setBackgroundResource(drable);
	}

	public void setTitle(String title) {
		acition_bar_title.setText(title);
	}

	public void setTitleBackground(int resourceId) {
		acition_bar_title.setBackgroundDrawable(getResources().getDrawable(resourceId));
	}
	
	public void setLeftGone() {
		acition_bar_left.setVisibility(View.GONE);
	}
	
	public void setLeftVisible() {
		acition_bar_left.setVisibility(View.VISIBLE);
	}
	
	public void setRightGone() {
		acition_bar_right.setVisibility(View.GONE);
	}

	public void setRightVisible() {
		acition_bar_right.setVisibility(View.VISIBLE);
	}

	public void setLeftIcon(int resourceId) {
		acition_bar_left.setImageResource(resourceId);
	}


	public void setRightOnClick(boolean bool) {
		if(bool){
			acition_bar_right.setEnabled(true);
		}else{
			acition_bar_right.setEnabled(false);
		}
	}
	public void setRightText(String Right){
		acition_bar_right.setText(Right);
	}
}

