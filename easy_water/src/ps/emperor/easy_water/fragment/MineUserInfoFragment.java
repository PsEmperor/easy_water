package ps.emperor.easy_water.fragment;

import java.lang.reflect.Field;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.utils.CheckUtil;
import ps.emperor.easy_water.utils.PutToFile;
import ps.emperor.easy_water.utils.SharedUtils;
import ps.emperor.easy_water.view.MainActionBar;

/**
 * 个人信息
 * 
 * @author 毛国江
 * @version 2016-5-16 下午 14:45
 */
@SuppressLint("NewApi")
public class MineUserInfoFragment extends Fragment implements OnClickListener {
	private LayoutInflater mInflater;
	private PopupWindow popupWindow;
	private RelativeLayout ivHead, user_tel, info_units, info_role,
			info_user_name;
	private Bitmap bitmap;
	private ImageView image_info_head;
	private MainActionBar actionBar;
	private String names;
	private EditText name;
	private TextView name_show;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflater = inflater;
		View view = inflater.inflate(R.layout.fragment_mine_user_info,
				container, false);

		actionBar = (MainActionBar) view.findViewById(R.id.actionbar_user_info);
		actionBar.setLeftIcon(R.drawable.btn_back_selector);
		actionBar.setRightGone();
		actionBar.setTitle("个人信息");
		actionBar.setActionBarOnClickListener(this);

		ivHead = (RelativeLayout) view.findViewById(R.id.layout_info_head);// 用户头像
		image_info_head = (ImageView) view.findViewById(R.id.image_info_ivhead); // 显示头像的image
		user_tel = (RelativeLayout) view
				.findViewById(R.id.layout_info_user_tel);// 用户电话
		info_units = (RelativeLayout) view.findViewById(R.id.layout_info_units);// 授权单位
		info_role = (RelativeLayout) view.findViewById(R.id.layout_info_role);// 角色申请
		info_user_name = (RelativeLayout) view
				.findViewById(R.id.layout_info_user_name);// 用户姓名
		info_user_name.setOnClickListener(this);
		info_role.setOnClickListener(this);
		info_units.setOnClickListener(this);
		ivHead.setOnClickListener(this);
		user_tel.setOnClickListener(this);
		if (PutToFile.getBitmapFromFile() != null) {
			image_info_head.setImageBitmap(PutToFile.getBitmapFromFile());
		}
		init();

		name_show = (TextView) view.findViewById(R.id.tv_info_user_name);
		name_show.setText(names);

		return view;
	}
	
	private void init() {
		names = (String) SharedUtils.getParam(getActivity(),
				"dialog_user_name", "	");
	}

	@SuppressLint("ResourceAsColor")
	@Override
	public void onClick(View v) {
		FragmentManager fgManager = getFragmentManager();
		FragmentTransaction transaction = fgManager.beginTransaction();
		switch (v.getId()) {
		case R.id.acitionbar_left://回退
			MinesFragment fragment = new MinesFragment();
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			transaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.replace(R.id.fl, fragment, "main");
			transaction.commit();
			break;
		case R.id.layout_info_head://头像上传
			if (popupWindow == null) {
				View view = mInflater
						.inflate(R.layout.layout_accout_popu, null);
				popupWindow = new PopupWindow(view,
						ViewGroup.LayoutParams.WRAP_CONTENT,
						ViewGroup.LayoutParams.WRAP_CONTENT);
				popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
				popupWindow.setFocusable(true);
				WindowManager.LayoutParams params=getActivity().getWindow().getAttributes();  
		        params.alpha=0.7f;  
		        getActivity().getWindow().setAttributes(params);  
				popupWindow.setOnDismissListener(new OnDismissListener() {
					
					@Override
					public void onDismiss() {
						 popupWindow.dismiss();     
					      popupWindow = null;     
					      WindowManager.LayoutParams params=getActivity().getWindow().getAttributes();  
					      params.alpha=1f;  
					      getActivity().getWindow().setAttributes(params);  
					}
				});
				popupWindow.setBackgroundDrawable(new ColorDrawable());
				view.findViewById(R.id.text_user_info_photograph)
						.setOnClickListener(changlistener);
				view.findViewById(R.id.text_user_info_photo_album)
						.setOnClickListener(changlistener);
				view.findViewById(R.id.text_user_info_canel)
						.setOnClickListener(changlistener);
			}
			
			popupWindow.showAtLocation(
					getActivity().findViewById(R.id.account), Gravity.BOTTOM,
					0, 0);
			break;
		case R.id.layout_info_user_name: //姓名弹出dialog
			Builder builder = new Builder(getActivity());

			final View contentview = LayoutInflater.from(getActivity())
					.inflate(R.layout.dialog_mine_control, null);
			builder.setPositiveButton("确定",
					new android.content.DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							
							name = (EditText) contentview
									.findViewById(R.id.edit_mine_user_name);
							String names = name.getText().toString().trim();
							if(checkNameChese(names) == true && !CheckUtil.IsEmpty(names)){
								name.setText(names);
								name_show.setText(names);
								SharedUtils.setParam(getActivity(),
										"dialog_user_name", names);
							}else{
								if(CheckUtil.IsEmpty(names)){
								Toast.makeText(getActivity(), "姓名不能为空！", Toast.LENGTH_SHORT).show();
								}else{
								Toast.makeText(getActivity(), "请输入正确的姓名！", Toast.LENGTH_SHORT).show();
								}
							}
						}
					});
			builder.setView(contentview);
			builder.show();
			break;
		case R.id.layout_info_user_tel: // 修改电话
			MineUserTelFragment fragment1 = new MineUserTelFragment();
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			transaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.replace(R.id.fl, fragment1, "main");
			transaction.commit();
			break;
		case R.id.layout_info_units: // 授权单位
			MineUserUnitsFragment fragment2 = new MineUserUnitsFragment();
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			transaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.replace(R.id.fl, fragment2, "main");
			transaction.commit();
			break;
		case R.id.layout_info_role: // 角色申请
			MineUserRoleFragment fragment3 = new MineUserRoleFragment();
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			transaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.replace(R.id.fl, fragment3, "main");
			transaction.commit();
			break;
		default:
			break;
		}
	}
		
	protected void setOnKeyListener(OnKeyListener onKeyListener) {
		// TODO Auto-generated method stub
		
	}

	private View.OnClickListener changlistener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.text_user_info_photograph:// 拍照
				Intent intent = new Intent();
				intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(intent, 100);
				popupWindow.dismiss();
				break;
			case R.id.text_user_info_photo_album:// 相册取出
					startActivityForResult(getPhonePic(), 1080);
					popupWindow.dismiss();
				break;
			case R.id.text_user_info_canel:
				popupWindow.dismiss();
				break;
			}
		}
	};

	public static Intent getPhonePic() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
		intent.setType("image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 70);
		intent.putExtra("outputY", 70);
		intent.putExtra("return-data", true);
		return intent;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 100) {
			if (resultCode == Activity.RESULT_OK) {
				Bundle bundle = data.getExtras();
				bitmap = (Bitmap) bundle.get("data");// 获取相机返回的图片
				image_info_head.setImageBitmap(bitmap);
				SharedUtils.setParam(getActivity(), "ivHead", bitmap);
				PutToFile.putToFile(bitmap);
			}
		} else if (requestCode == 1080) {
			if(!CheckUtil.IsEmpty(data)){
				Bundle bundle = data.getExtras();
				if(!CheckUtil.IsEmpty(bundle)){
					bitmap = (Bitmap) bundle.get("data");
					if (!CheckUtil.IsEmpty(bitmap)) {
						image_info_head.setImageBitmap(bitmap);
						SharedUtils.setParam(getActivity(), "ivHead", bitmap);
						PutToFile.putToFile(bitmap);
					} else {
						image_info_head.setImageResource(R.drawable.ic_launcher);
					}
				}else{
					Toast.makeText(getActivity(), "未选中任何图片", Toast.LENGTH_SHORT).show();
				}
				}
		}
		// sendIcon();
	}
	
	 /**
     * 判定输入汉字
     * @param c
     * @return
     */
    public  boolean isChinese(char c) {
    Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
    if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
         || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
        || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
        || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
        || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
        || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
        return true;
    }
    return false;
}
    
    /**
     * 检测String是否全是中文
     * @param name
     * @return
     */
	 public  boolean checkNameChese(String name)
	   {
	           boolean res=true;
	           char [] cTemp = name.toCharArray(); 
	           for(int i=0;i<name.length();i++)
	           {
	                   if(!isChinese(cTemp[i]))
	                   {
	                           res=false;
	                           break;
	                   }
	           }           
	           return res;
	   }
}
