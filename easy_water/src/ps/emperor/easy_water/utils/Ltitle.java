package ps.emperor.easy_water.utils;

import ps.emperor.easy_water.R;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Ltitle extends RelativeLayout  {

	private TextView text;
	private Button btEdit;

	public Ltitle(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.title, this);
		Button btBack = (Button) findViewById(R.id.bt_back);
		btEdit = (Button) findViewById(R.id.bt_edit);
		text = (TextView) findViewById(R.id.tv_text);
		
		
		
		
		btBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				((Activity)getContext()).finish();
				
			}
		});
		
		
		btEdit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getContext(), "未定义", 0).show();
				
			}
		});
	}
	//设置标题栏名称
	public void setText(String tv){
		text.setText(tv);
	}
	//设置右菜单名称
	public void setEditText(String tv){
		btEdit.setVisibility(View.VISIBLE);
		btEdit.setText(tv);
	}

}
