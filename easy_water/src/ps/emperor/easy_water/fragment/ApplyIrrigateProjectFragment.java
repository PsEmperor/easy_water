package ps.emperor.easy_water.fragment;

import java.text.DateFormat;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.achartengine.model.Point;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.TextView;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.adapter.ListViewPagerAdapter1;
import ps.emperor.easy_water.entity.ApplyIrrigationProjectBean;
import ps.emperor.easy_water.greendao.DBHelper;
import ps.emperor.easy_water.greendao.IrrigationGroup;
import ps.emperor.easy_water.greendao.IrrigationIsFirst;
import ps.emperor.easy_water.greendao.IrrigationProject;
import ps.emperor.easy_water.utils.CheckUtil;
import ps.emperor.easy_water.utils.SharedUtils;
import ps.emperor.easy_water.view.CustomDialog;
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
	private List<IrrigationIsFirst> firsts;
	private IrrigationIsFirst irrigationIsFirst;
	private List<IrrigationGroup> irrigationGroups;
	private int isNot, now_round, nowPage, nowPages, notify, isSkip, isFirst,
			notifys, IsEmpty, empty = 0, isSkips;
	EditText runPager;
	private String time, units, compareTime;
	private Long deletePage, deletePageId;
	private int MatchedNum, names;
	private ProgressDialog progressDialog;
	ApplyIrrigationProjectBean bean;
	private int currentItem, deletePages;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 100:
				tv_now_time.setText(time);
				break;
			case 0:
				if (CheckUtil.IsEmpty(listentity)) {
					btn_project_del.setVisibility(View.GONE);
				} else {
					btn_project_del.setVisibility(View.VISIBLE);
				}
				listPager = new ListViewPagerAdapter1(getActivity(), beans,
						MatchedNum, units);
				pager.setAdapter(listPager);
				pager.setOnPageChangeListener(listener);
				pageNum = (int) Math.ceil(beans.size() / MatchedNum);
				if (beans.size() > 0 && beans.size() <= MatchedNum) {
					pageNum = 1;
				}
				if (!CheckUtil.IsEmpty(beans)) {
					tv_indicator.setText("第" + 1 + "轮" + "/" + "共" + pageNum
							+ "轮");
					indicator.setText(1 + "");
				} else {
					tv_indicator.setText("第" + 0 + "轮" + "/" + "共" + pageNum
							+ "轮");
					indicator.setText(0 + "");
				}
				if (CheckUtil.IsEmpty(listentity)) {
					nowPage = 0;
				} else {
					nowPage = 1;
				}
				SharedUtils.setParam(getActivity(), "nowPage", nowPage);
				notify = (Integer) SharedUtils.getParam(getActivity(),
						"notify", 0);
				notifys = (Integer) SharedUtils.getParam(getActivity(),
						"notifys", 0);
				isSkip = (Integer) SharedUtils.getParam(getActivity(),
						"isSkip", 0);
				if (notify == 1) {
					listentity = dbHelper.loadLastMsgBySessionid(units);
					if (CheckUtil.IsEmpty(listentity)) {
						now_round = 0;
					} else {
						now_round = Integer.valueOf(listentity.get(0)
								.getRound());
					}
					pager.setCurrentItem(now_round + 1);
					notify = 0;
					SharedUtils.setParam(getActivity(), "notify", notify);
				} else {
					pager.setCurrentItem(0);
				}
				if (notifys == 1) {
					pager.setCurrentItem(0);
					notifys = 0;
					SharedUtils.setParam(getActivity(), "notifys", notifys);
				}
				nowPage = (Integer) SharedUtils.getParam(getActivity(),
						"nowPage", 1);
				if (isSkip == 1) {
					isSkip = 0;
					SharedUtils.setParam(getActivity(), "isSkip", isSkip);
					pager.setCurrentItem(nowPage - 1);
				}
				if (isSkip == 2) {
					isSkip = 0;
					SharedUtils.setParam(getActivity(), "isSkip", isSkip);
					pager.setCurrentItem(nowPage - 1);
				}
				if (isSkip == 3) {
					isSkip = 0;
					SharedUtils.setParam(getActivity(), "isSkip", isSkip);
					nowPages = (int) SharedUtils.getParam(getActivity(),
							"nowPages", isSkip);
					pager.setCurrentItem(nowPages - 1);
				}
				if (CheckUtil.IsEmpty(listentity)) {
					btn_project_del.setVisibility(View.GONE);
				} else {
					btn_project_del.setVisibility(View.VISIBLE);
				}
				progressDialog.dismiss();
				break;
			case 1:
				if (CheckUtil.IsEmpty(listentity.size())) {
					btn_project_del.setVisibility(View.GONE);
				} else {
					btn_project_del.setVisibility(View.VISIBLE);
				}
				listPager = new ListViewPagerAdapter1(getActivity(), beans,
						MatchedNum, units);
				pager.setAdapter(listPager);
				pager.setOnPageChangeListener(listener);
				// 此处为轮次组数 限定多少组为一轮
				pageNum = (int) Math.ceil(beans.size() / MatchedNum);
				if (beans.size() > 0 && beans.size() <= MatchedNum) {
					pageNum = 1;
				}
				if (!CheckUtil.IsEmpty(beans)) {
					tv_indicator.setText("第" + 1 + "轮" + "/" + "共" + pageNum
							+ "轮");
					indicator.setText(1 + "");
				} else {
					tv_indicator.setText("第" + 0 + "轮" + "/" + "共" + pageNum
							+ "轮");
					indicator.setText(0 + "");
				}
				pager.setCurrentItem(now_round + 1);
				firsts = dbHelper.loadisFirst(units);
				if (CheckUtil.IsEmpty(firsts)) {
					irrigationIsFirst.setIrrigation(units);
					irrigationIsFirst.setIsFirst(0);
					dbHelper.saveIsFirst(irrigationIsFirst);
				}
				progressDialog.dismiss();
				break;
			case 2:
				listPager = new ListViewPagerAdapter1(getActivity(), beans,
						MatchedNum, units);
				pager.setAdapter(listPager);
				pager.setOnPageChangeListener(listener);
				// 此处为轮次组数 限定多少组为一轮
				pageNum = (int) Math.ceil(beans.size() / MatchedNum);
				if (beans.size() > 0 && beans.size() <= MatchedNum) {
					pageNum = 1;
				}
				if (!CheckUtil.IsEmpty(beans)) {
					tv_indicator.setText("第" + 1 + "轮" + "/" + "共" + pageNum
							+ "轮");
					indicator.setText(1 + "");
				} else {
					tv_indicator.setText("第" + 0 + "轮" + "/" + "共" + pageNum
							+ "轮");
					indicator.setText(0 + "");
				}
				pager.setCurrentItem(nowPage - 1);
				if (CheckUtil.IsEmpty(beans)) {
					btn_project_del.setVisibility(View.INVISIBLE);
				} else {
					btn_project_del.setVisibility(View.VISIBLE);
				}
				progressDialog.dismiss();
				btn_project_del.setClickable(true);
				break;
			case 3:
				listPager = new ListViewPagerAdapter1(getActivity(), beans,
						MatchedNum, units);
				pager.setAdapter(listPager);
				pager.setOnPageChangeListener(listener);
				// 此处为轮次组数 限定多少组为一轮
				pageNum = (int) Math.ceil(beans.size() / MatchedNum);
				if (beans.size() > 0 && beans.size() <= MatchedNum) {
					pageNum = 1;
				}
				if (!CheckUtil.IsEmpty(beans)) {
					tv_indicator.setText("第" + 1 + "轮" + "/" + "共" + pageNum
							+ "轮");
					indicator.setText(1 + "");
				} else {
					tv_indicator.setText("第" + 0 + "轮" + "/" + "共" + pageNum
							+ "轮");
					indicator.setText(0 + "");
				}
				notify = (Integer) SharedUtils.getParam(getActivity(),
						"notify", 0);
				notifys = (Integer) SharedUtils.getParam(getActivity(),
						"notifys", 0);
				isSkip = (Integer) SharedUtils.getParam(getActivity(),
						"isSkip", 0);
				if (notify == 1) {
					listentity = dbHelper.loadLastMsgBySessionid(units);
					if (CheckUtil.IsEmpty(listentity)) {
						now_round = 0;
					} else {
						now_round = Integer.valueOf(listentity.get(0)
								.getRound());
					}
					pager.setCurrentItem(now_round + 1);
					notify = 0;
					SharedUtils.setParam(getActivity(), "notify", notify);
				} else {
					pager.setCurrentItem(0);
				}
				if (notifys == 1) {
					pager.setCurrentItem(0);
					notifys = 0;
					SharedUtils.setParam(getActivity(), "notifys", notifys);
				}
				nowPage = (Integer) SharedUtils.getParam(getActivity(),
						"nowPage", 1);
				if (isSkip == 1) {
					isSkip = 0;
					SharedUtils.setParam(getActivity(), "isSkip", isSkip);
					pager.setCurrentItem(nowPage - 1);
				}
				if (isSkip == 2) {
					isSkip = 0;
					SharedUtils.setParam(getActivity(), "isSkip", isSkip);
					pager.setCurrentItem(nowPage - 1);
				}
				if (isSkip == 3) {
					isSkip = 0;
					SharedUtils.setParam(getActivity(), "isSkip", isSkip);
					nowPages = (int) SharedUtils.getParam(getActivity(),
							"nowPages", isSkip);
					pager.setCurrentItem(nowPages - 1);
				}
				if (CheckUtil.IsEmpty(listentity)) {
					btn_project_del.setVisibility(View.GONE);
				} else {
					btn_project_del.setVisibility(View.VISIBLE);
				}
				progressDialog.dismiss();
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

		units = getArguments().getString("units");

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
		tv_indicator.setOnClickListener(this);
		indicator = (TextView) view.findViewById(R.id.tv_indicator);
		btn_project_add = (TextView) view.findViewById(R.id.btn_project_add);
		btn_project_add.setOnClickListener(this);
		btn_project_del = (TextView) view.findViewById(R.id.btn_project_del);
		btn_project_del.setOnClickListener(this);
		tv_now_time = (TextView) view
				.findViewById(R.id.text_apply_irrigatr_now_time);
		isNot = (int) SharedUtils.getParam(getActivity(), "isNot", 0);
		// listView = (ListView) view
		// .findViewById(R.id.list_apply_irrigatr_project);
		beans = new ArrayList<ApplyIrrigationProjectBean>();
		pager = (ViewPager) view.findViewById(R.id.pager);
		isSkips = (int) SharedUtils.getParam(getActivity(), "isSkips", 0);
		if (isSkips == 0) {
			progressDialog = ProgressDialog.show(getActivity(), "Loading...",
					"Please wait...", true, false);
			new Thread() {

				@Override
				public void run() {
					// 需要花时间计算的方法
					irrigationGroups = dbHelper.loadGroupByUnits(units);
					MatchedNum = irrigationGroups.size();
					// MatchedNum = 5;
					// listentity = dbHelper.loadLastMsgBySessionidTen(units,
					// MatchedNum);
					listentity = dbHelper.loadLastMsgBySessionids(units);
					for (int i = 0; i < listentity.size(); i++) {
						bean = new ApplyIrrigationProjectBean();
						if (i > MatchedNum - 1) {
							bean.setGroup(Engroup[a]);
							a++;
							if (a > MatchedNum - 1) {
								a = 0;
							}
						} else {
							bean.setGroup(Engroup[i]);
						}
						// bean.setGroup(listentity.get(i).getRound());
						if (listentity.get(i).getProjectstart()
								.equals("0000-00-00 00:00")) {
							bean.setTime_start("");
						} else {
							bean.setTime_start(listentity.get(i)
									.getProjectstart());
						}
						if (listentity.get(i).getProjectend()
								.equals("0000-00-00 00:00")) {
							bean.setTime_end("");
						} else {
							bean.setTime_end(listentity.get(i).getProjectend());
						}
						beans.add(bean);
					}
					if (isNot == 1) {
						beans.clear();
						deletePages = 0;
						// listentity =
						// dbHelper.loadLastMsgBySessionidTen(units,
						// MatchedNum);
						listentity = dbHelper.loadLastMsgBySessionids(units);
						for (int j = 0; j < listentity.size(); j++) {
							if (nowPage < 2) {
								nowPage = 1;
							}
							if (j > MatchedNum * nowPage - 1) {
								nowPage++;
							}
							if (CheckUtil.IsEmpty(listentity.get(j)
									.getProjectstart())
									&& CheckUtil.IsEmpty(listentity.get(j)
											.getProjectend())) {
								if (j == MatchedNum * (nowPage - 1)) {
									deletePageId = listentity.get(j).getId();
								}
								deletePages++;
								if (deletePages == MatchedNum) {
									for (int i = 0; i < MatchedNum; i++) {
										dbHelper.deleteNote(deletePageId);
										deletePageId++;
									}
									deletePages = 0;
								}
							}
						}
						listentity = dbHelper.loadLastMsgBySessionids(units);
						for (int i = 0; i < listentity.size(); i++) {
							bean = new ApplyIrrigationProjectBean();
							if (i > MatchedNum - 1) {
								bean.setGroup(Engroup[a]);
								a++;
								if (a > MatchedNum - 1) {
									a = 0;
								}
							} else {
								bean.setGroup(Engroup[i]);
							}
							// bean.setGroup(listentity.get(i).getRound());
							if (listentity.get(i).getProjectstart()
									.equals("0000-00-00 00:00")) {
								bean.setTime_start("");
							} else {
								bean.setTime_start(listentity.get(i)
										.getProjectstart());
							}
							if (listentity.get(i).getProjectend()
									.equals("0000-00-00 00:00")) {
								bean.setTime_end("");
							} else {
								bean.setTime_end(listentity.get(i)
										.getProjectend());
							}
							beans.add(bean);
						}
						isNot = 0;
						SharedUtils.setParam(getActivity(), "isNot", isNot);
					} else {
						isNot = 0;
						SharedUtils.setParam(getActivity(), "isNot", isNot);
					}
					// 向handler发消息
					handler.sendEmptyMessage(0);
				}
			}.start();

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
		}

		pager.setOnTouchListener(new OnTouchListener() {
			float startX;
			float startY;
			float endX;
			float endY;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					startX = event.getX();
					startY = event.getY();
					break;
				case MotionEvent.ACTION_UP:
					endX = event.getX();
					endY = event.getY();
					// 首先要确定的是，是否到了最后一页，然后判断是否向左滑动，并且滑动距离是否符合，我这里的判断距离是屏幕宽度的4分之一（这里可以适当控制）
					int width = (int) SharedUtils.getParam(getActivity(),
							"screenWidth", 0);
					if (currentItem == (beans.size() - 1) && startX - endX > 0
							&& startX - endX >= (width / 4)) {
						Log.i("1111", "进入了触摸");
					}
					break;
				}
				return false;
			}
		});
		// adapter = new ApplyIrrigationProjectAdapter(getActivity(), beans);
		return view;
	}

	private OnPageChangeListener listener = new OnPageChangeListener() {

		@Override
		public void onPageSelected(int arg0) {
			pageNum = (int) Math.ceil(beans.size() / MatchedNum);
			tv_indicator.setText("第" + (arg0 + 1) + "轮" + "/" + "共" + pageNum
					+ "轮");
			indicator.setText((arg0 + 1) + "");
			System.out.println(arg0 + "");
			nowPage = arg0 + 1;
			currentItem = arg0;
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
		isSkips = (int) SharedUtils.getParam(getActivity(), "isSkips", 0);
		if (isSkips == 1) {
			progressDialog = ProgressDialog.show(getActivity(), "Loading...",
					"Please wait...", true, false);
			new Thread(new Runnable() {

				@Override
				public void run() {
					irrigationProject = new IrrigationProject();
					beans.clear();
					beans = new ArrayList<ApplyIrrigationProjectBean>();
					listentity = dbHelper.loadLastMsgBySessionids(units);
					irrigationGroups = dbHelper.loadGroupByUnits(units);
					MatchedNum = irrigationGroups.size();
					ApplyIrrigationProjectBean bean;
					for (int i = 0; i < listentity.size(); i++) {
						bean = new ApplyIrrigationProjectBean();
						if (i > MatchedNum - 1) {
							bean.setGroup(Engroup[a]);
							a++;
							if (a > MatchedNum - 1) {
								a = 0;
							}
						} else {
							bean.setGroup(Engroup[i]);
						}
						// bean.setGroup(listentity.get(i).getRound());
						if (listentity.get(i).getProjectstart()
								.equals("0000-00-00 00:00")) {
							bean.setTime_start("");
						} else {
							bean.setTime_start(listentity.get(i)
									.getProjectstart());
						}
						if (listentity.get(i).getProjectend()
								.equals("0000-00-00 00:00")) {
							bean.setTime_end("");
						} else {
							bean.setTime_end(listentity.get(i).getProjectend());
						}
						beans.add(bean);
					}
					SharedUtils.setParam(getActivity(), "isSkips", 0);
					handler.sendEmptyMessage(3);
				}
			}).start();
		}
	};

	@Override
	public void onClick(View v) {
		FragmentManager fgManager = getFragmentManager();
		final FragmentTransaction transaction = fgManager.beginTransaction();
		switch (v.getId()) {
		case R.id.acitionbar_left:
			ApplyIrrigateUnitControlFragment fragment = new ApplyIrrigateUnitControlFragment();
			Bundle bundle = new Bundle();
			bundle.putString("units", units);
			fragment.setArguments(bundle);
			transaction.setCustomAnimations(
					R.anim.slide_fragment_horizontal_right_in,
					R.anim.slide_fragment_horizontal_left_out);
			transaction.replace(R.id.fl, fragment, "main");
			transaction.commit();
			break;
		case R.id.indicator:
			Builder builder = new Builder(getActivity());

			final View contentview = LayoutInflater.from(getActivity())
					.inflate(R.layout.dialog_pager, null);
			builder.setPositiveButton("确定",
					new android.content.DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							runPager = (EditText) contentview
									.findViewById(R.id.edit_pager);
							if (CheckUtil.IsEmpty(runPager.getText().toString()
									.trim())
									|| "".equals(runPager.getText().toString()
											.trim())) {
								names = 0;
							} else {
								names = Integer.valueOf(runPager.getText()
										.toString().trim()) - 1;
								if (CheckUtil.IsEmpty(names)) {
									pager.setCurrentItem(nowPage);
								} else {
									pager.setCurrentItem(names);
								}
							}
						}
					});
			builder.setView(contentview);
			builder.show();
			break;
		case R.id.btn_project_add:
			if (pageNum > 30) {
				showAlertDialog(getView());
			} else {
				// firsts = dbHelper.loadisFirst(units);
				// if (CheckUtil.IsEmpty(firsts)) {
				// isFirst = 0;
				// } else {
				// isFirst = 1;
				// }
				// if (isFirst == 1) {
				// Dialog dialog = new AlertDialog.Builder(getActivity())
				// .setIcon(R.drawable.six)
				// .setTitle("请选择设定方式 ")
				// // 设置多选提示框
				// .setItems(R.array.selinterests,
				// new DialogInterface.OnClickListener() {
				// public void onClick(DialogInterface dialog,
				// int which) {
				// if (which == 0) {
				// SharedUtils.setParam(getActivity(),
				// "nowPages", nowPage);
				// ApplyIrrigateProjectSingleFragment fragment1 = new
				// ApplyIrrigateProjectSingleFragment();
				// // transaction.setCustomAnimations(R.anim.right_in,
				// // R.anim.right_out);
				// Bundle bundle = new Bundle();
				// bundle.putString("units", units);
				// fragment1.setArguments(bundle);
				// transaction
				// .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
				// transaction.replace(R.id.fl,
				// fragment1, "main");
				// transaction.commit();
				// }
				// if (which == 1) {
				// irrigationIsFirst = new IrrigationIsFirst();
				// irrigationProject = new IrrigationProject();
				// listentity = dbHelper
				// .loadLastMsgBySessionid(units);
				// if (CheckUtil.IsEmpty(listentity
				// .size())) {
				// btn_project_del
				// .setVisibility(View.GONE);
				// } else {
				// btn_project_del
				// .setVisibility(View.VISIBLE);
				// }
				// if (CheckUtil.IsEmpty(listentity)) {
				// nowPage = 0;
				// } else {
				// nowPage = Integer
				// .valueOf(listentity
				// .get(0)
				// .getRound());
				// }
				// SharedUtils.setParam(getActivity(),
				// "nowPage", nowPage);
				// if (CheckUtil.IsEmpty(listentity)) {
				// now_round = 0;
				// } else {
				// now_round = Integer
				// .valueOf(listentity
				// .get(0)
				// .getRound());
				// }
				// for (int i = 1; i <= MatchedNum; i++) {
				// irrigationProject = new IrrigationProject();
				// irrigationProject
				// .setIrrigation(units);
				// irrigationProject
				// .setProjectstart("0000-00-00 00:00");
				// irrigationProject
				// .setProjectend("0000-00-00 00:00");
				// irrigationProject
				// .setRound((now_round + 1)
				// + "");
				// irrigationProject
				// .setMarshalling(i + "");
				// dbHelper.saveSessions(irrigationProject);
				// }
				// beans.clear();
				// beans = new ArrayList<ApplyIrrigationProjectBean>();
				// listentity = dbHelper
				// .loadLastMsgBySessionids(units);
				// ApplyIrrigationProjectBean bean;
				// for (int i = 0; i < listentity
				// .size(); i++) {
				// bean = new ApplyIrrigationProjectBean();
				// if (i > MatchedNum - 1) {
				// bean.setGroup(Engroup[a]);
				// a++;
				// if (a > MatchedNum - 1) {
				// a = 0;
				// }
				// } else {
				// bean.setGroup(Engroup[i]);
				// }
				// // bean.setGroup(listentity.get(i).getRound());
				// if (listentity
				// .get(i)
				// .getProjectstart()
				// .equals("0000-00-00 00:00")) {
				// bean.setTime_start("");
				// } else {
				// bean.setTime_start(listentity
				// .get(i)
				// .getProjectstart());
				// }
				// if (listentity
				// .get(i)
				// .getProjectend()
				// .equals("0000-00-00 00:00")) {
				// bean.setTime_end("");
				// } else {
				// bean.setTime_end(listentity
				// .get(i)
				// .getProjectend());
				// }
				// beans.add(bean);
				// }
				// listPager = new ListViewPagerAdapter1(
				// getActivity(), beans, MatchedNum,
				// units);
				// pager.setAdapter(listPager);
				// pager.setOnPageChangeListener(listener);
				// // 此处为轮次组数 限定多少组为一轮
				// pageNum = (int) Math.ceil(beans
				// .size() / MatchedNum);
				// if (beans.size() > 0
				// && beans.size() <= MatchedNum) {
				// pageNum = 1;
				// }
				// if (!CheckUtil.IsEmpty(beans)) {
				// tv_indicator.setText("第" + 1
				// + "轮" + "/" + "共"
				// + pageNum + "轮");
				// indicator.setText(1 + "");
				// } else {
				// tv_indicator.setText("第" + 0
				// + "轮" + "/" + "共"
				// + pageNum + "轮");
				// indicator.setText(0 + "");
				// }
				// pager.setCurrentItem(now_round + 1);
				// firsts = dbHelper
				// .loadisFirst(units);
				// if (CheckUtil.IsEmpty(firsts)) {
				// irrigationIsFirst
				// .setIrrigation(units);
				// irrigationIsFirst.setIsFirst(0);
				// dbHelper.saveIsFirst(irrigationIsFirst);
				// }
				// }
				// }
				// }).create();
				//
				// dialog.show();
				// } else {
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
											Bundle bundle = new Bundle();
											bundle.putString("units", units);
											fragment.setArguments(bundle);
											transaction
													.setCustomAnimations(
															R.anim.slide_fragment_horizontal_left_in,
															R.anim.slide_fragment_horizontal_right_out);
											transaction.replace(R.id.fl,
													fragment, "main");
											transaction.commit();
										}
										if (which == 1) {
											SharedUtils.setParam(getActivity(),
													"nowPages", nowPage);
											ApplyIrrigateProjectSingleFragment fragment1 = new ApplyIrrigateProjectSingleFragment();
											// transaction.setCustomAnimations(R.anim.right_in,
											// R.anim.right_out);
											Bundle bundle = new Bundle();
											bundle.putString("units", units);
											fragment1.setArguments(bundle);
											transaction
													.setCustomAnimations(
															R.anim.slide_fragment_horizontal_left_in,
															R.anim.slide_fragment_horizontal_right_out);
											transaction.replace(R.id.fl,
													fragment1, "main");
											transaction.commit();
										}
										if (which == 2) {
											irrigationIsFirst = new IrrigationIsFirst();
											irrigationProject = new IrrigationProject();
											progressDialog = ProgressDialog
													.show(getActivity(),
															"Loading...",
															"Please wait...",
															true, false);
											new Thread(new Runnable() {

												@Override
												public void run() {
													listentity = dbHelper
															.loadLastMsgBySessionid(units);

													if (CheckUtil
															.IsEmpty(listentity)) {
														nowPage = 0;
													} else {
														nowPage = Integer
																.valueOf(listentity
																		.get(0)
																		.getRound());
													}
													SharedUtils.setParam(
															getActivity(),
															"nowPage", nowPage);
													if (CheckUtil
															.IsEmpty(listentity)) {
														now_round = 0;
													} else {
														now_round = Integer
																.valueOf(listentity
																		.get(0)
																		.getRound());
													}

													for (int i = 1; i <= MatchedNum; i++) {
														irrigationProject = new IrrigationProject();
														irrigationProject
																.setIrrigation(units);
														irrigationProject
																.setProjectstart("");
														irrigationProject
																.setProjectend("");
														irrigationProject
																.setRound((now_round + 1)
																		+ "");
														irrigationProject
																.setMarshalling(i
																		+ "");
														dbHelper.saveProject(irrigationProject);
													}
													beans.clear();
													beans = new ArrayList<ApplyIrrigationProjectBean>();
													listentity = dbHelper
															.loadLastMsgBySessionids(units);
													ApplyIrrigationProjectBean bean;
													for (int i = 0; i < listentity
															.size(); i++) {
														bean = new ApplyIrrigationProjectBean();
														if (i > MatchedNum - 1) {
															bean.setGroup(Engroup[a]);
															a++;
															if (a > MatchedNum - 1) {
																a = 0;
															}
														} else {
															bean.setGroup(Engroup[i]);
														}
														// bean.setGroup(listentity.get(i).getRound());
														if (listentity
																.get(i)
																.getProjectstart()
																.equals("0000-00-00 00:00")) {
															bean.setTime_start("");
														} else {
															bean.setTime_start(listentity
																	.get(i)
																	.getProjectstart());
														}
														if (listentity
																.get(i)
																.getProjectend()
																.equals("0000-00-00 00:00")) {
															bean.setTime_end("");
														} else {
															bean.setTime_end(listentity
																	.get(i)
																	.getProjectend());
														}
														beans.add(bean);
													}
													handler.sendEmptyMessage(1);
												}
											}).start();
										}
									}
								}).create();

				dialog.show();
				// }
			}
			break;
		case R.id.btn_project_del:
			progressDialog = ProgressDialog.show(getActivity(), "Loading...",
					"Please wait...", true, false);
			btn_project_del.setClickable(false);
			new Thread(new Runnable() {
				@Override
				public void run() {
					listentity = dbHelper.loadLastMsgBySessionids(units);
					if (beans.size() > 0 && beans.size() <= MatchedNum) {
						compareTime = beans.get(0).getTime_start();
					} else {
						compareTime = beans.get(
								nowPage * MatchedNum - MatchedNum)
								.getTime_start();
					}
					if (CheckUtil.IsEmpty(compareTime)) {
						compareTime = "0000-00-00 00:00";
					}
					for (int j = 0; j < listentity.size(); j++) {
						if (compareTime.equals(listentity.get(j)
								.getProjectstart())) {
							deletePage = listentity.get(j).getId();
							break;
						}
					}
					for (int i = 0; i < MatchedNum; i++) {
						// dbHelper.updateProjects(units,nowPage+"", i+1, "",
						// "");
						dbHelper.deleteNote(deletePage);
						deletePage++;
					}

					irrigationProject = new IrrigationProject();
					beans.clear();
					beans = new ArrayList<ApplyIrrigationProjectBean>();
					listentity = dbHelper.loadLastMsgBySessionids(units);
					ApplyIrrigationProjectBean bean;
					for (int i = 0; i < listentity.size(); i++) {
						bean = new ApplyIrrigationProjectBean();
						if (i > MatchedNum - 1) {
							bean.setGroup(Engroup[a]);
							a++;
							if (a > MatchedNum - 1) {
								a = 0;
							}
						} else {
							bean.setGroup(Engroup[i]);
						}
						// bean.setGroup(listentity.get(i).getRound());
						if (listentity.get(i).getProjectstart()
								.equals("0000-00-00 00:00")) {
							bean.setTime_start("");
						} else {
							bean.setTime_start(listentity.get(i)
									.getProjectstart());
						}
						if (listentity.get(i).getProjectend()
								.equals("0000-00-00 00:00")) {
							bean.setTime_end("");
						} else {
							bean.setTime_end(listentity.get(i).getProjectend());
						}
						beans.add(bean);
					}
					handler.sendEmptyMessage(2);
				}
			}).start();

			// listentity = dbHelper.loadProjectByOne(units, MatchedNum *
			// pageNum,
			// MatchedNum);
			// if (CheckUtil.IsEmpty(listentity)) {
			//
			// }
			// else {
			// do {
			// empty = 0;
			// for (int i = 0; i < listentity.size() ; i++) {
			// if ("0000-00-00 00:00".equals(listentity.get(
			// i).getProjectstart())) {
			// IsEmpty = 1;
			// empty++;
			// } else {
			// IsEmpty = 2;
			// }
			// if (empty == MatchedNum) {
			// deletePage = listentity.get(i)
			// .getId()-MatchedNum+1;
			// for (int c = 0; c < MatchedNum; c++) {
			// // dbHelper.updateProjects(units,nowPage+"",
			// // i+1,
			// // "", "");
			// dbHelper.deleteNote(deletePage);
			// deletePage++;
			// empty = 0;
			// }
			// }
			// }
			// listentity = dbHelper.loadProjectByOne(units, MatchedNum *
			// pageNum,
			// MatchedNum);
			// for (int i = 0; i < listentity.size() ; i++) {
			// if ("0000-00-00 00:00".equals(listentity.get(
			// i).getProjectstart())) {
			// empty = MatchedNum;
			// } else {
			// empty = 0;
			// }
			// }
			// } while (empty == MatchedNum);
			// if (CheckUtil.IsEmpty(listentity)) {
			//
			// }else{
			// for (int i = 0; i < listentity.size(); i++) {
			// bean = new ApplyIrrigationProjectBean();
			// if (i > MatchedNum - 1) {
			// bean.setGroup(Engroup[a]);
			// a++;
			// if (a > MatchedNum - 1) {
			// a = 0;
			// }
			// } else {
			// bean.setGroup(Engroup[i]);
			// }
			// // bean.setGroup(listentity.get(i).getRound());
			// if (listentity.get(i).getProjectstart()
			// .equals("0000-00-00 00:00")) {
			// bean.setTime_start("");
			// } else {
			// bean.setTime_start(listentity.get(i).getProjectstart());
			// }
			// if (listentity.get(i).getProjectend()
			// .equals("0000-00-00 00:00")) {
			// bean.setTime_end("");
			// } else {
			// bean.setTime_end(listentity.get(i).getProjectend());
			// }
			// beans.add(bean);
			// }
			// listPager = new ListViewPagerAdapter1(getActivity(), beans,
			// MatchedNum, units);
			// pager.setAdapter(listPager);
			// pager.setOnPageChangeListener(listener);
			// // 此处为轮次组数 限定多少组为一轮
			// pageNum = (int) Math.ceil(beans.size() / MatchedNum);
			// if (beans.size() > 0 && beans.size() <= MatchedNum) {
			// pageNum = 1;
			// }
			// if (!CheckUtil.IsEmpty(beans)) {
			// tv_indicator.setText("第" + 1 + "轮" + "/" + "共" + pageNum
			// + "轮");
			// indicator.setText(1 + "");
			// } else {
			// tv_indicator.setText("第" + 0 + "轮" + "/" + "共" + pageNum
			// + "轮");
			// indicator.setText(0 + "");
			// }
			// pager.setCurrentItem(pageNum - 1);
			// firsts = dbHelper.loadisFirst(units);
			// if (CheckUtil.IsEmpty(firsts)) {
			// irrigationIsFirst.setIrrigation(units);
			// irrigationIsFirst.setIsFirst(0);
			// dbHelper.saveIsFirst(irrigationIsFirst);
			// }
			// break;
			// }
			// }
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view,
			final int position, long id) {

	}

	public void showAlertDialog(View view) {

		CustomDialog.Builder builder = new CustomDialog.Builder(getActivity());
		builder.setMessage("当前灌溉轮次较多!	请在网络状态良好的环境下进行此操作！ ");
		builder.setTitle("温馨提示");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				// 设置你的操作事项
			}
		});

		builder.setNegativeButton("取消",
				new android.content.DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});

		builder.create().show();

	}
}
