package ps.emperor.easy_water.fragment;

import java.io.UnsupportedEncodingException;

import java.text.DateFormat;
import java.text.ParseException;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.achartengine.model.Point;
import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback.CancelledException;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.ex.HttpException;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;

import com.google.gson.Gson;

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
import android.widget.Toast;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.adapter.ListViewPagerAdapter1;
import ps.emperor.easy_water.entity.ApplyIrrigationProjectBean;
import ps.emperor.easy_water.entity.ApplyIrrigationProjectBean.infoList;
import ps.emperor.easy_water.greendao.DBHelper;
import ps.emperor.easy_water.greendao.Irrigation;
import ps.emperor.easy_water.greendao.IrrigationGroup;
import ps.emperor.easy_water.greendao.IrrigationIsFirst;
import ps.emperor.easy_water.greendao.IrrigationProject;
import ps.emperor.easy_water.utils.CheckUtil;
import ps.emperor.easy_water.utils.NetStatusUtil;
import ps.emperor.easy_water.utils.SharedUtils;
import ps.emperor.easy_water.utils.URL;
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
	private List<infoList> beans;
	private TextView tv_indicator, indicator, btn_project_add, btn_project_del,
			tv_now_time;
	private ListViewPagerAdapter1 listPager;
	private int pageNum;
	private ViewPager pager;
	private String Engroup[] = new String[52];
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
	private String time, units, compareTime, time_start, time_max_start;
	private Long deletePage, deletePageId;
	private int MatchedNum, names;
	private ProgressDialog progressDialog;
	ApplyIrrigationProjectBean bean;
	private int currentItem, deletePages;
	private long temp, compare, compares;

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
		Engroup[26] = "AA";
		Engroup[27] = "BB";
		Engroup[28] = "CC";
		Engroup[29] = "DD";
		Engroup[30] = "EE";
		Engroup[31] = "FF";
		Engroup[32] = "GG";
		Engroup[33] = "HH";
		Engroup[34] = "II";
		Engroup[35] = "JJ";
		Engroup[36] = "KK";
		Engroup[37] = "LL";
		Engroup[38] = "MM";
		Engroup[39] = "NN";
		Engroup[40] = "OO";
		Engroup[41] = "PP";
		Engroup[42] = "QQ";
		Engroup[43] = "RR";
		Engroup[44] = "SS";
		Engroup[45] = "TT";
		Engroup[46] = "UU";
		Engroup[47] = "VV";
		Engroup[48] = "WW";
		Engroup[49] = "XX";
		Engroup[50] = "YY";
		Engroup[51] = "ZZ";

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
		beans = new ArrayList<infoList>();
		pager = (ViewPager) view.findViewById(R.id.pager);

		if (NetStatusUtil.isNetValid(getActivity())) {
			init();
		} else {
			Toast.makeText(getActivity(), "当前网络不可用！请检查您的网络状态！",
					Toast.LENGTH_SHORT).show();
		}

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

	private void init() {
		isSkips = (int) SharedUtils.getParam(getActivity(), "isSkips", 0);
		if (isSkips == 0) {
			// irrigation = dbHelper.loadContinue(units);
			String str1 = (String) SharedUtils.getParam(getActivity(),
					"FirstDerviceID", "");
			;
			try {
				str1 = java.net.URLEncoder.encode(str1, "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			RequestParams param3 = new RequestParams(URL.forIrriUnit + str1); // 网址(请替换成实际的网址)
			// params.addQueryStringParameter("key", "value"); //
			// 参数(请替换成实际的参数与值)
			progressDialog = ProgressDialog.show(getActivity(), "Loading...",
					"Please wait...", true, false);
			JSONObject js_request2 = new JSONObject();
			try {
				param3.setAsJsonContent(true);
			} catch (Exception e) {
				e.printStackTrace();
				param3.setAsJsonContent(true);
			}// 根据实际需求添加相应键值对

			x.http().request(HttpMethod.GET, param3,
					new CommonCallback<String>() {
						@Override
						public void onCancelled(CancelledException arg0) {

						}

						// 注意:如果是自己onSuccess回调方法里写了一些导致程序崩溃的代码，也会回调道该方法，因此可以用以下方法区分是网络错误还是其他错误
						// 还有一点，网络超时也会也报成其他错误，还需具体打印出错误内容比较容易跟踪查看
						@Override
						public void onError(Throwable ex, boolean isOnCallback) {

							Toast.makeText(x.app(), ex.getMessage(),
									Toast.LENGTH_LONG).show();
							if (ex instanceof HttpException) { // 网络错误 
								HttpException httpEx = (HttpException) ex;
								int responseCode = httpEx.getCode();
								String responseMsg = httpEx.getMessage();
								String errorResult = httpEx.getResult();
								Toast.makeText(getActivity(), "请求失败",
										Toast.LENGTH_SHORT);
								// ...
								progressDialog.dismiss();
							} else { // 其他错误 
								// ...
								Toast.makeText(getActivity(), "请求失败",
										Toast.LENGTH_SHORT);
								progressDialog.dismiss();
							}

						}

						// 不管成功或者失败最后都会回调该接口
						@Override
						public void onFinished() {
						}

						@Override
						public void onSuccess(String arg0) {
							Toast.makeText(getActivity(), "请求成功",
									Toast.LENGTH_SHORT);
							Gson gson = new Gson();
							System.out.println(arg0);
							ApplyIrrigationProjectBean fromJson = gson
									.fromJson(arg0,
											ApplyIrrigationProjectBean.class);
							beans = fromJson.getAuthNameList();
							if (!CheckUtil.IsEmpty(beans)) {
								MatchedNum = Integer.valueOf(beans.get(0)
										.getGroupNum());
								pageNum = beans.size() / MatchedNum;
								listPager = new ListViewPagerAdapter1(
										getActivity(), beans, MatchedNum, units);
								pager.setAdapter(listPager);
								pager.setOnPageChangeListener(listener);
								tv_indicator.setText("第" + 1 + "轮" + "/" + "共"
										+ pageNum + "轮");

								Calendar c = Calendar.getInstance();
								Calendar c1 = Calendar.getInstance();
								try {
									c.setTime(new SimpleDateFormat(
											"yyyy-MM-dd HH:mm").parse(beans
											.get(0).getEndTime()));
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								compares = c.getTimeInMillis();
								for (int j = 0; j < beans.size(); j++) {
									try {
										if (CheckUtil.IsEmpty(beans.get(j)
												.getEndTime())) {
											c1.setTime(new SimpleDateFormat(
													"yyyy-MM-dd HH:mm")
													.parse("0000-00-00 00:00"));
										} else {
											c1.setTime(new SimpleDateFormat(
													"yyyy-MM-dd HH:mm")
													.parse(beans.get(j)
															.getEndTime()));
										}
									} catch (ParseException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									compare = c1.getTimeInMillis();
									if (compare > compares) {
										temp = compare;
										compare = compares;
										compares = temp;
										Date date = new Date(compares);
										SimpleDateFormat sdf = new SimpleDateFormat(
												"yyyy-MM-dd HH:mm");
										time_start = sdf.format(date);
										time_max_start = time_start;
									}
								}
							} else {
								Date date = new Date(System.currentTimeMillis());
								SimpleDateFormat sdf = new SimpleDateFormat(
										"yyyy-MM-dd HH:mm");
								time_start = sdf.format(date);
								time_max_start = time_start;
							}
							// if(CheckUtil.IsEmpty(listentity)){
							// for (int i = 0; i < beans.size(); i++) {
							// irrigationProject.setProjectstart(beans.get(i).getStartTime());
							// irrigationProject.setProjectend(beans.get(i).getEndTime());
							// irrigationProject.setProjectduration(beans.get(i).getDuration());
							// irrigationProject.setProjectrest(beans.get(i).getRestTime());
							// irrigationProject.setModification(0);
							// dbHelper.saveProject(irrigationProject);
							// }
							// }else if(listentity.size()!= beans.size()){
							// for (int i = 0; i < beans.size(); i++) {
							// irrigationProject.setProjectstart(beans.get(i).getStartTime());
							// irrigationProject.setProjectend(beans.get(i).getEndTime());
							// irrigationProject.setProjectduration(beans.get(i).getDuration());
							// irrigationProject.setProjectrest(beans.get(i).getRestTime());
							// irrigationProject.setModification(0);
							// dbHelper.saveProject(irrigationProject);
							// }
							// }else if(listentity.size()== beans.size()){
							// for (int i = 0; i < listentity.size(); i++) {
							// if(listentity.get(i).getModification()==1){
							// //待考虑 以几何形式存
							// }
							// }
							// }
							progressDialog.dismiss();
						}
					});
		}
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
			transaction.replace(R.id.fl, fragment);
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
											bundle.putString("time_max_start",
													time_max_start);
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
											bundle.putString("time_max_start",
													time_max_start);
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
											// irrigationIsFirst = new
											// IrrigationIsFirst();
											// irrigationProject = new
											// IrrigationProject();
											// progressDialog = ProgressDialog
											// .show(getActivity(),
											// "Loading...",
											// "Please wait...",
											// true, false);
											// new Thread(new Runnable() {
											//
											// @Override
											// public void run() {
											// listentity = dbHelper
											// .loadLastMsgBySessionid(units);
											//
											// if (CheckUtil
											// .IsEmpty(listentity)) {
											// nowPage = 0;
											// } else {
											// nowPage = Integer
											// .valueOf(listentity
											// .get(0)
											// .getRound());
											// }
											// SharedUtils.setParam(
											// getActivity(),
											// "nowPage", nowPage);
											// if (CheckUtil
											// .IsEmpty(listentity)) {
											// now_round = 0;
											// } else {
											// now_round = Integer
											// .valueOf(listentity
											// .get(0)
											// .getRound());
											// }
											//
											// for (int i = 1; i <= MatchedNum;
											// i++) {
											// irrigationProject = new
											// IrrigationProject();
											// irrigationProject
											// .setIrrigation(units);
											// irrigationProject
											// .setProjectstart("");
											// irrigationProject
											// .setProjectend("");
											// irrigationProject
											// .setRound((now_round + 1)
											// + "");
											// irrigationProject
											// .setMarshalling(i
											// + "");
											// dbHelper.saveProject(irrigationProject);
											// }
											// beans.clear();
											// beans = new
											// ArrayList<infoList>();
											// listentity = dbHelper
											// .loadLastMsgBySessionids(units);
											// infoList bean;
											// for (int i = 0; i < listentity
											// .size(); i++) {
											// bean = new infoList();
											// if (i > MatchedNum - 1) {
											// bean.setGroupName(Engroup[a]);
											// a++;
											// if (a > MatchedNum - 1) {
											// a = 0;
											// }
											// } else {
											// bean.setGroupName(Engroup[i]);
											// }
											// //
											// bean.setGroup(listentity.get(i).getRound());
											// if (listentity
											// .get(i)
											// .getProjectstart()
											// .equals("0000-00-00 00:00")) {
											// bean.setStartTime("");
											// } else {
											// bean.setStartTime(listentity
											// .get(i)
											// .getProjectstart());
											// }
											// if (listentity
											// .get(i)
											// .getProjectend()
											// .equals("0000-00-00 00:00")) {
											// bean.setEndTime("");
											// } else {
											// bean.setEndTime(listentity
											// .get(i)
											// .getProjectend());
											// }
											// beans.add(bean);
											// }
											// handler.sendEmptyMessage(1);
											// }
											// }).start();
											for (int i = 0; i < MatchedNum; i++) {
												infoList bean = new infoList();
												bean.setStartTime("");
												bean.setEndTime("");
												bean.setRestTime("00:00:00");
												bean.setDuration("00:00:00");
												bean.setGroupName("组"
														+ Engroup[i]);
												beans.add(bean);
											}
											listPager = new ListViewPagerAdapter1(
													getActivity(), beans,
													MatchedNum, units);
											pager.setAdapter(listPager);
											pager.setCurrentItem(beans.size()
													/ MatchedNum);
										}
									}
								}).create();

				dialog.show();
				// }
			}
			break;
		case R.id.btn_project_del:
			if (!NetStatusUtil.isNetValid(getActivity())) {
				Toast.makeText(getActivity(), "当前网络不可用！请检查您的网络状态！",
						Toast.LENGTH_SHORT).show();
			} else {
				// irrigation = dbHelper.loadContinue(units);
				String str1 = (String) SharedUtils.getParam(getActivity(),
						"FirstDerviceID", "");
				try {
					str1 = java.net.URLEncoder.encode(str1, "UTF-8");
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				RequestParams param3 = new RequestParams(URL.forIrriUnitDele); // 网址(请替换成实际的网址)
				// params.addQueryStringParameter("key", "value"); //
				// 参数(请替换成实际的参数与值)
				progressDialog = ProgressDialog.show(getActivity(),
						"Loading...", "Please wait...", true, false);
				JSONObject js_request2 = new JSONObject();
				try {
					param3.setAsJsonContent(true);
					js_request2.put("firstDerviceID", str1);
					js_request2.put("planRound",
							beans.get(nowPages * MatchedNum).getPlanRound());
					param3.setBodyContent(js_request2.toString());
				} catch (Exception e) {
					e.printStackTrace();
					param3.setAsJsonContent(true);
				}// 根据实际需求添加相应键值对

				x.http().request(HttpMethod.PUT, param3,
						new CommonCallback<String>() {
							@Override
							public void onCancelled(CancelledException arg0) {

							}

							// 注意:如果是自己onSuccess回调方法里写了一些导致程序崩溃的代码，也会回调道该方法，因此可以用以下方法区分是网络错误还是其他错误
							// 还有一点，网络超时也会也报成其他错误，还需具体打印出错误内容比较容易跟踪查看
							@Override
							public void onError(Throwable ex,
									boolean isOnCallback) {

								Toast.makeText(x.app(), ex.getMessage(),
										Toast.LENGTH_LONG).show();
								if (ex instanceof HttpException) { // 网络错误 
									HttpException httpEx = (HttpException) ex;
									int responseCode = httpEx.getCode();
									String responseMsg = httpEx.getMessage();
									String errorResult = httpEx.getResult();
									Toast.makeText(getActivity(), "请求失败",
											Toast.LENGTH_SHORT);
									// ...
									progressDialog.dismiss();
								} else { // 其他错误 
									// ...
									Toast.makeText(getActivity(), "请求失败",
											Toast.LENGTH_SHORT);
									progressDialog.dismiss();
								}

							}

							// 不管成功或者失败最后都会回调该接口
							@Override
							public void onFinished() {
							}

							@Override
							public void onSuccess(String arg0) {
								Toast.makeText(getActivity(), "请求成功",
										Toast.LENGTH_SHORT);
								Gson gson = new Gson();
								System.out.println(arg0);
								// ApplyIrrigationProjectBean fromJson = gson
								// .fromJson(
								// arg0,
								// ApplyIrrigationProjectBean.class);
								// beans = fromJson.getAuthNameList();
								progressDialog.dismiss();
								init();
							}
						});
			}

			// progressDialog = ProgressDialog.show(getActivity(), "Loading...",
			// "Please wait...", true, false);
			// btn_project_del.setClickable(false);
			// new Thread(new Runnable() {
			// @Override
			// public void run() {
			// listentity = dbHelper.loadLastMsgBySessionids(units);
			// if (beans.size() > 0 && beans.size() <= MatchedNum) {
			// compareTime = beans.get(0).getStartTime();
			// } else {
			// compareTime = beans.get(
			// nowPage * MatchedNum - MatchedNum)
			// .getStartTime();
			// }
			// if (CheckUtil.IsEmpty(compareTime)) {
			// compareTime = "0000-00-00 00:00";
			// }
			// for (int j = 0; j < listentity.size(); j++) {
			// if (compareTime.equals(listentity.get(j)
			// .getProjectstart())) {
			// deletePage = listentity.get(j).getId();
			// break;
			// }
			// }
			// for (int i = 0; i < MatchedNum; i++) {
			// // dbHelper.updateProjects(units,nowPage+"", i+1, "",
			// // "");
			// dbHelper.deleteNote(deletePage);
			// deletePage++;
			// }
			//
			// irrigationProject = new IrrigationProject();
			// beans.clear();
			// beans = new ArrayList<infoList>();
			// listentity = dbHelper.loadLastMsgBySessionids(units);
			// infoList bean;
			// for (int i = 0; i < listentity.size(); i++) {
			// bean = new infoList();
			// if (i > MatchedNum - 1) {
			// bean.setGroupName(Engroup[a]);
			// a++;
			// if (a > MatchedNum - 1) {
			// a = 0;
			// }
			// } else {
			// bean.setGroupName(Engroup[i]);
			// }
			// // bean.setGroup(listentity.get(i).getRound());
			// if (listentity.get(i).getProjectstart()
			// .equals("0000-00-00 00:00")) {
			// bean.setStartTime("");
			// } else {
			// bean.setStartTime(listentity.get(i)
			// .getProjectstart());
			// }
			// if (listentity.get(i).getProjectend()
			// .equals("0000-00-00 00:00")) {
			// bean.setEndTime("");
			// } else {
			// bean.setEndTime(listentity.get(i).getProjectend());
			// }
			// beans.add(bean);
			// }
			// handler.sendEmptyMessage(2);
			// }
			// }).start();

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
