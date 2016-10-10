package ps.emperor.easy_water.fragment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.activity.TimeAvtivityDialog;
import ps.emperor.easy_water.adapter.ListViewPagerAdapter1;
import ps.emperor.easy_water.entity.ApplyIrrigationProjectBean;
import ps.emperor.easy_water.greendao.DBHelper;
import ps.emperor.easy_water.greendao.IrrigationProject;
import ps.emperor.easy_water.utils.CheckUtil;
import ps.emperor.easy_water.utils.SharedUtils;
import ps.emperor.easy_water.view.MainActionBar;

/**
 * 灌溉计划
 * 
 * @author 毛国江
 * @version 2016-5-24 下午15:00
 */
@SuppressLint("NewApi")
public class ApplyIrrigateProjectFragment extends Fragment implements
		OnClickListener, OnItemClickListener {
	private LayoutInflater mInflater;
	private MainActionBar actionBar;
	private List<ApplyIrrigationProjectBean> beans;
	private TextView tv_indicator, indicator, btn_project_add, btn_project_del,
			tv_now_time;
	private ListViewPagerAdapter1 listPager;
	private int pageNum;
	private ViewPager pager;
	private String Engroup[] = new String[26];
	private int a = 0;
	private DBHelper dbHelper;
	private List<IrrigationProject> listentity;
	private IrrigationProject irrigationProject;
	private int now_round, nowPage,nowPages, notify,isSkip,firstSet;
	private String time;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 100:
				tv_now_time.setText(time);
				break;
			default:
				break;
			}
		};
	};

	@SuppressLint("SimpleDateFormat")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflater = inflater;
		View view = inflater.inflate(R.layout.fragment_apply_irrigate_project,
				container, false);

		actionBar = (MainActionBar) view
				.findViewById(R.id.actionbar_apply_irrigat_project);
		actionBar.setLeftIcon(R.drawable.btn_back_selector);
		actionBar.setRightGone();
		actionBar.setTitle("灌溉计划");
		actionBar.setActionBarOnClickListener(this);

		Engroup[0] = "A";
		Engroup[1] = "B";
		Engroup[2] = "C";
		Engroup[3] = "D";
		Engroup[4] = "E";
		Engroup[5] = "F";
		Engroup[6] = "G";
		Engroup[7] = "H";
		Engroup[8] = "I";
		Engroup[9] = "J";
		Engroup[10] = "K";
		Engroup[11] = "L";
		Engroup[12] = "M";
		Engroup[13] = "N";
		Engroup[14] = "O";
		Engroup[15] = "P";
		Engroup[16] = "Q";
		Engroup[17] = "R";
		Engroup[18] = "S";
		Engroup[19] = "T";
		Engroup[20] = "U";
		Engroup[21] = "V";
		Engroup[22] = "W";
		Engroup[23] = "X";
		Engroup[24] = "Y";
		Engroup[25] = "Z";

		dbHelper = DBHelper.getInstance(getActivity()); // 得到DBHelper对象

		tv_indicator = (TextView) view.findViewById(R.id.indicator);
		indicator = (TextView) view.findViewById(R.id.tv_indicator);
		btn_project_add = (TextView) view.findViewById(R.id.btn_project_add);
		btn_project_add.setOnClickListener(this);
		btn_project_del = (TextView) view.findViewById(R.id.btn_project_del);
		btn_project_del.setOnClickListener(this);
		tv_now_time = (TextView) view
				.findViewById(R.id.text_apply_irrigatr_now_time);

		// listView = (ListView) view
		// .findViewById(R.id.list_apply_irrigatr_project);
		beans = new ArrayList<ApplyIrrigationProjectBean>();
		try {
			init();
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		listentity = dbHelper.loadAllProject();
		ApplyIrrigationProjectBean bean;
		if (CheckUtil.IsEmpty(listentity)) {
			btn_project_del.setVisibility(View.GONE);
		} else {
			btn_project_del.setVisibility(View.VISIBLE);
		}
		for (int i = 0; i < listentity.size(); i++) {
			bean = new ApplyIrrigationProjectBean();
			if (i > 3) {
				bean.setGroup(Engroup[a]);
				a++;
				if (a > 3) {
					a = 0;
				}
			} else {
				bean.setGroup(Engroup[i]);
			}
			// bean.setGroup(listentity.get(i).getRound());
			if (listentity.get(i).getProjectstart().equals("0000-00-00 00:00")) {
				bean.setTime_start("");
			} else {
				bean.setTime_start(listentity.get(i).getProjectstart());
			}
			if (listentity.get(i).getProjectend().equals("0000-00-00 00:00")) {
				bean.setTime_end("");
			} else {
				bean.setTime_end(listentity.get(i).getProjectend());
			}
			beans.add(bean);
		}
		pager = (ViewPager) view.findViewById(R.id.pager);
		listPager = new ListViewPagerAdapter1(getActivity(), beans, 4);
		pager.setAdapter(listPager);
		pager.setOnPageChangeListener(listener);
		pageNum = (int) Math.ceil(beans.size() / 4);
		if (beans.size() > 0 && beans.size() <= 4) {
			pageNum = 1;
		}
		if (!CheckUtil.IsEmpty(beans)) {
			tv_indicator.setText("第" + 1 + "轮" + "/" + "共" + pageNum + "轮");
			indicator.setText(1 + "");
		} else {
			tv_indicator.setText("第" + 0 + "轮" + "/" + "共" + pageNum + "轮");
			indicator.setText(0 + "");
		}
		if (CheckUtil.IsEmpty(listentity)) {
			nowPage = 0;
		} else {
			nowPage = 1;
		}
		SharedUtils.setParam(getActivity(), "nowPage", nowPage);

		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						Date date = new Date();
						DateFormat format = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss");
						time = format.format(date);
						Message message = new Message();   
	                    message.what = 100;   
	                    handler.sendMessage(message);
	                    Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
		
		// adapter = new ApplyIrrigationProjectAdapter(getActivity(), beans);
		return view;
	}

	private OnPageChangeListener listener = new OnPageChangeListener() {

		@Override
		public void onPageSelected(int arg0) {
			tv_indicator.setText("第" + (arg0 + 1) + "轮" + "/" + "共" + pageNum
					+ "轮");
			indicator.setText((arg0 + 1) + "");
			System.out.println(arg0 + "");
			nowPage = arg0 + 1;
			SharedUtils.setParam(getActivity(), "nowPage", nowPage);
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}
	};

	public void onResume() {
		super.onResume();
		irrigationProject = new IrrigationProject();
		beans = new ArrayList<ApplyIrrigationProjectBean>();
		listentity = dbHelper.loadAllProject();
		ApplyIrrigationProjectBean bean;
		for (int i = 0; i < listentity.size(); i++) {
			bean = new ApplyIrrigationProjectBean();
			if (i > 3) {
				bean.setGroup(Engroup[a]);
				a++;
				if (a > 3) {
					a = 0;
				}
			} else {
				bean.setGroup(Engroup[i]);
			}
			// bean.setGroup(listentity.get(i).getRound());
			if (listentity.get(i).getProjectstart().equals("0000-00-00 00:00")) {
				bean.setTime_start("");
			} else {
				bean.setTime_start(listentity.get(i).getProjectstart());
			}
			if (listentity.get(i).getProjectend().equals("0000-00-00 00:00")) {
				bean.setTime_end("");
			} else {
				bean.setTime_end(listentity.get(i).getProjectend());
			}
			beans.add(bean);
		}
		listPager = new ListViewPagerAdapter1(getActivity(), beans, 4);
		pager.setAdapter(listPager);
		pager.setOnPageChangeListener(listener);
		// 此处为轮次组数 限定多少组为一轮
		pageNum = (int) Math.ceil(beans.size() / 4);
		if (beans.size() > 0 && beans.size() <= 4) {
			pageNum = 1;
		}
		if (!CheckUtil.IsEmpty(beans)) {
			tv_indicator.setText("第" + 1 + "轮" + "/" + "共" + pageNum + "轮");
			indicator.setText(1 + "");
		} else {
			tv_indicator.setText("第" + 0 + "轮" + "/" + "共" + pageNum + "轮");
			indicator.setText(0 + "");
		}
		notify = (Integer) SharedUtils.getParam(getActivity(), "notify", 0);
		isSkip = (Integer) SharedUtils.getParam(getActivity(), "isSkip", 0);
		if (notify == 1) {
			listentity = dbHelper.loadAllSessions();
			if (CheckUtil.IsEmpty(listentity)) {
				now_round = 0;
			} else {
				now_round = Integer.valueOf(listentity.get(0).getRound());
			}
			pager.setCurrentItem(now_round + 1);
			notify = 0;
			SharedUtils.setParam(getActivity(), "notify", notify);
		} else {
			pager.setCurrentItem(0);
		}
		nowPage = (Integer) SharedUtils.getParam(getActivity(), "nowPage", 1);
		if(isSkip == 1){
			isSkip = 0;
			SharedUtils.setParam(getActivity(), "isSkip", isSkip);
			pager.setCurrentItem(nowPage-1);
		}
		if(isSkip == 2){
			isSkip = 0;
			SharedUtils.setParam(getActivity(), "isSkip", isSkip);
			pager.setCurrentItem(nowPage-1);
		}
		if(isSkip == 3){
			isSkip = 0;
			SharedUtils.setParam(getActivity(), "isSkip", isSkip);
			nowPages = (int) SharedUtils.getParam(getActivity(), "nowPages", isSkip);
			pager.setCurrentItem(nowPages-1);
		}
		if (CheckUtil.IsEmpty(listentity)) {
			btn_project_del.setVisibility(View.GONE);
		} else {
			btn_project_del.setVisibility(View.VISIBLE);
		}
	};

	private void init() throws ParseException {
	}

	@Override
	public void onClick(View v) {
		FragmentManager fgManager = getFragmentManager();
		final FragmentTransaction transaction = fgManager.beginTransaction();
		switch (v.getId()) {
		case R.id.acitionbar_left:
			ApplyIrrigateUnitControlFragment fragment = new ApplyIrrigateUnitControlFragment();
			transaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.replace(R.id.fl, fragment, "main");
			transaction.commit();
			break;
		case R.id.btn_project_add:
			Dialog dialog = new AlertDialog.Builder(getActivity())
					.setIcon(R.drawable.six)
					.setTitle("请选择设定方式 ")
					// 设置多选提示框
					.setItems(R.array.selinterest,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									if (which == 0) {
										ApplyIrrigateProjectSeasonFragment fragment = new ApplyIrrigateProjectSeasonFragment();
										// transaction.setCustomAnimations(R.anim.right_in,
										// R.anim.right_out);
										transaction
												.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
										transaction.replace(R.id.fl, fragment,
												"main");
										transaction.commit();
									}
									if (which == 1) {
										SharedUtils.setParam(getActivity(), "nowPages", nowPage);
										ApplyIrrigateProjectSingleFragment fragment1 = new ApplyIrrigateProjectSingleFragment();
										// transaction.setCustomAnimations(R.anim.right_in,
										// R.anim.right_out);
										transaction
												.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
										transaction.replace(R.id.fl, fragment1,
												"main");
										transaction.commit();
									}
									if (which == 2) {
										firstSet = (int) SharedUtils.getParam(getActivity(), "firstSet", 0);
										irrigationProject = new IrrigationProject();
										listentity = dbHelper.loadAllSessions();
										if(firstSet == 0){
											btn_project_del.setVisibility(View.VISIBLE);
											SharedUtils.setParam(getActivity(), "nowPage", 1);
											firstSet = 1;
											SharedUtils.setParam(getActivity(), "firstSet", firstSet);
										}else{
											if (CheckUtil.IsEmpty(listentity)) {
												btn_project_del.setVisibility(View.GONE);
											} else {
												btn_project_del.setVisibility(View.VISIBLE);
											}
											if (CheckUtil.IsEmpty(listentity)) {
												nowPage = 0;
											}else{
												nowPage = Integer.valueOf(listentity.get(0).getRound());
											}
											SharedUtils.setParam(getActivity(), "nowPage", nowPage);
										}
										if (CheckUtil.IsEmpty(listentity)) {
											now_round = 0;
										} else {
											now_round = Integer
													.valueOf(listentity.get(0)
															.getRound());
										}
										for (int i = 1; i < 5; i++) {
											irrigationProject = new IrrigationProject();
											irrigationProject
													.setProjectstart("0000-00-00 00:00");
											irrigationProject
													.setProjectend("0000-00-00 00:00");
											irrigationProject
													.setRound((now_round + 1)
															+ "");
											irrigationProject.setMarshalling(i
													+ "");
											dbHelper.saveSessions(irrigationProject);
										}
										beans = new ArrayList<ApplyIrrigationProjectBean>();
										listentity = dbHelper.loadAllProject();
										ApplyIrrigationProjectBean bean;
										for (int i = 0; i < listentity.size(); i++) {
											bean = new ApplyIrrigationProjectBean();
											if (i > 3) {
												bean.setGroup(Engroup[a]);
												a++;
												if (a > 3) {
													a = 0;
												}
											} else {
												bean.setGroup(Engroup[i]);
											}
											// bean.setGroup(listentity.get(i).getRound());
											if (listentity.get(i)
													.getProjectstart()
													.equals("0000-00-00 00:00")) {
												bean.setTime_start("");
											} else {
												bean.setTime_start(listentity
														.get(i)
														.getProjectstart());
											}
											if (listentity.get(i)
													.getProjectend()
													.equals("0000-00-00 00:00")) {
												bean.setTime_end("");
											} else {
												bean.setTime_end(listentity
														.get(i).getProjectend());
											}
											beans.add(bean);
										}
										listPager = new ListViewPagerAdapter1(
												getActivity(), beans, 4);
										pager.setAdapter(listPager);
										pager.setOnPageChangeListener(listener);
										// 此处为轮次组数 限定多少组为一轮
										pageNum = (int) Math.ceil(beans.size() / 4);
										if (beans.size() > 0
												&& beans.size() <= 4) {
											pageNum = 1;
										}
										if (!CheckUtil.IsEmpty(beans)) {
											tv_indicator
													.setText("第" + 1 + "轮"
															+ "/" + "共"
															+ pageNum + "轮");
											indicator.setText(1 + "");
										} else {
											tv_indicator
													.setText("第" + 0 + "轮"
															+ "/" + "共"
															+ pageNum + "轮");
											indicator.setText(0 + "");
										}
										pager.setCurrentItem(now_round + 1);
									}
								}
							}).create();
			
			dialog.show();
			break;
		case R.id.btn_project_del:
			for (int i = 0; i < 4; i++) {
				dbHelper.updateProject(nowPage+"", i+1, "", "");
			}
			irrigationProject = new IrrigationProject();
			beans = new ArrayList<ApplyIrrigationProjectBean>();
			listentity = dbHelper.loadAllProject();
			ApplyIrrigationProjectBean bean;
			for (int i = 0; i < listentity.size(); i++) {
				bean = new ApplyIrrigationProjectBean();
				if (i > 3) {
					bean.setGroup(Engroup[a]);
					a++;
					if (a > 3) {
						a = 0;
					}
				} else {
					bean.setGroup(Engroup[i]);
				}
				// bean.setGroup(listentity.get(i).getRound());
				if (listentity.get(i).getProjectstart().equals("0000-00-00 00:00")) {
					bean.setTime_start("");
				} else {
					bean.setTime_start(listentity.get(i).getProjectstart());
				}
				if (listentity.get(i).getProjectend().equals("0000-00-00 00:00")) {
					bean.setTime_end("");
				} else {
					bean.setTime_end(listentity.get(i).getProjectend());
				}
				beans.add(bean);
			}
			listPager = new ListViewPagerAdapter1(getActivity(), beans, 4);
			pager.setAdapter(listPager);
			pager.setOnPageChangeListener(listener);
			// 此处为轮次组数 限定多少组为一轮
			pageNum = (int) Math.ceil(beans.size() / 4);
			if (beans.size() > 0 && beans.size() <= 4) {
				pageNum = 1;
			}
			if (!CheckUtil.IsEmpty(beans)) {
				tv_indicator.setText("第" + 1 + "轮" + "/" + "共" + pageNum + "轮");
				indicator.setText(1 + "");
			} else {
				tv_indicator.setText("第" + 0 + "轮" + "/" + "共" + pageNum + "轮");
				indicator.setText(0 + "");
			}
			pager.setCurrentItem(nowPage-1);
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view,
			final int position, long id) {

	}

}
