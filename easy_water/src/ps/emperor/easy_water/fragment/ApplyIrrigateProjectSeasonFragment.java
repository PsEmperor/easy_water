package ps.emperor.easy_water.fragment;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;
import ps.emperor.easy_water.R;
import ps.emperor.easy_water.Interface.OnWheelChangedListener;
import ps.emperor.easy_water.activity.TimeAvtivityDialogWater;
import ps.emperor.easy_water.adapter.NumbericWheelAdapter;
import ps.emperor.easy_water.adapter.NumericWheelAdapter;
import ps.emperor.easy_water.greendao.DBHelper;
import ps.emperor.easy_water.greendao.Irrigation;
import ps.emperor.easy_water.greendao.IrrigationGroup;
import ps.emperor.easy_water.greendao.IrrigationIsFirst;
import ps.emperor.easy_water.greendao.IrrigationProject;
import ps.emperor.easy_water.utils.CheckUtil;
import ps.emperor.easy_water.utils.NetworkUtils;
import ps.emperor.easy_water.utils.SharedUtils;
import ps.emperor.easy_water.view.CustomDialog;
import ps.emperor.easy_water.view.MainActionBar;
import ps.emperor.easy_water.view.WheelView;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;
import android.view.View.OnClickListener;

/**
 * 全季快速设置
 * 
 * @author 毛国江
 * @version 2016-5-25 上午9：53
 */
@SuppressLint("NewApi")
public class ApplyIrrigateProjectSeasonFragment extends Fragment implements
		OnClickListener, OnCheckedChangeListener {
	private LayoutInflater mInflater;
	private MainActionBar actionBar;
	private ToggleButton time_longs, time_night, time_random;
	private Boolean isLong, isNight, isRandom;
	private Dialog dialog;
	private TextView tv_time_continue, tv_time_number, tv_time_interval,
			tv_time_start;
	private RelativeLayout layout_time_start, layout_time_continue,
			layout_time_number, layout_time_interval;
	private int id, now_round, now_rounds, now_round1, setNight, setLong,round;
	private int isBefore, nHour, nHours, nMinutes, long_hour, time_number,
			time_intervals, isNightStartHour, isNightStartMinute, isNightEnd,
			isNightEndMinutes, isNightContinueHour, isNightContinueMinute;
	private WheelView current_year;
	private WheelView current_month;
	private WheelView current_day;
	private WheelView current_hour;
	private WheelView current_minute;
	private long temp, compare, compares;
	private DBHelper dbHelper;
	private IrrigationProject irrigationProject;
	private String time_start, time_end, time_starts, time_compare_start,
			units, time_max_start;
	private int MatchedNum, a = 1, s = 0, Marshalling = 1, isLegal, time_long,
			onleOnes = 0, isNightStartHours, compareDay, compareYear,
			compareYears, compareMonth, compareMonths, compareDays,
			compareDayf, isGreater, isHave, isTrue, isTrue1, isTrues, nHour2,
			notifys, conpare, isGreaters, isGreater1,isFront;
	private List<Irrigation> irrigation;
	private Button button_season;
	private List<IrrigationIsFirst> firsts;
	private IrrigationIsFirst irrigationIsFirst;
	private List<IrrigationGroup> groups;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 100:
				if (CheckUtil.IsEmpty(time_max_start)||"0000-00-00 00:00".equals(time_max_start)) {
					tv_time_start.setText(time_start);
				} else {
					tv_time_start.setText(time_max_start);
				}
				break;
			default:
				break;
			}
		};
	};
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflater = inflater;
		View view = inflater.inflate(
				R.layout.fragment_apply_irrigate_project_season, container,
				false);

		dbHelper = DBHelper.getInstance(getActivity()); // 得到DBHelper对象
		units = getArguments().getString("units");
		isFront = getArguments().getInt("isFront");
		groups = dbHelper.loadGroupByUnits(units);
		MatchedNum = groups.size();
		irrigationIsFirst = new IrrigationIsFirst();
		init();

		actionBar = (MainActionBar) view
				.findViewById(R.id.actionbar_apply_irrigat_project_season);
		actionBar.setLeftIcon(R.drawable.btn_back_selector);
		actionBar.setRightGone();
		actionBar.setTitle("全季快速设定");
		actionBar.setActionBarOnClickListener(this);

		time_night = (ToggleButton) view
				.findViewById(R.id.toggle_apply_irriagte_project_season_time_night);
		time_random = (ToggleButton) view
				.findViewById(R.id.toggle_apply_irriagte_project_season_time_random);
		time_longs = (ToggleButton) view
				.findViewById(R.id.toggle_apply_irriagte_project_season_time_longs);

		layout_time_continue = (RelativeLayout) view
				.findViewById(R.id.layout_apply_irriagte_project_season_time_continue);
		layout_time_continue.setOnClickListener(this);
		layout_time_number = (RelativeLayout) view
				.findViewById(R.id.layout_apply_irriagte_project_season_time_number);
		layout_time_number.setOnClickListener(this);
		layout_time_interval = (RelativeLayout) view
				.findViewById(R.id.layout_apply_irriagte_project_season_time_interval);
		layout_time_interval.setOnClickListener(this);
		layout_time_start = (RelativeLayout) view
				.findViewById(R.id.layout_apply_irriagte_project_season_time_start);
		layout_time_start.setOnClickListener(this);
		button_season = (Button) view
				.findViewById(R.id.button_apply_irriagte_project_season);
		button_season.setOnClickListener(this);

		tv_time_continue = (TextView) view
				.findViewById(R.id.text_apply_irriagte_project_season_time_continue);
		tv_time_number = (TextView) view
				.findViewById(R.id.text_apply_irriagte_project_season_time_number);
		tv_time_interval = (TextView) view
				.findViewById(R.id.text_apply_irriagte_project_season_time_interval);
		tv_time_start = (TextView) view
				.findViewById(R.id.text_apply_irriagte_project_season_time_start);

		time_night.setChecked(isNight);
		time_random.setChecked(isRandom);
		time_longs.setChecked(isLong);

		time_night.setOnCheckedChangeListener(this);
		time_random.setOnCheckedChangeListener(this);
		time_longs.setOnCheckedChangeListener(this);
		// if(!isMsg){
		// initToggle = false;
		// }
		
		nHour = irrigation.get(0).getNHour();
		nMinutes = irrigation.get(0).getNMinutes();
		if (!CheckUtil.IsEmpty(nHour) && !CheckUtil.IsEmpty(nMinutes)) {
			tv_time_continue.setText(nHour + "小时" + nMinutes + "分钟");
		} else if (CheckUtil.IsEmpty(nHour) && !CheckUtil.IsEmpty(nMinutes)) {
			tv_time_continue.setText(nMinutes + "分钟");
		} else {
			tv_time_continue.setText(nHour + "小时");
		}
		tv_time_number.setText(time_number + "次");
		tv_time_interval.setText(time_intervals + "天");
		return view;
	}

	private void init() {
		isNight = (Boolean) SharedUtils.getParam(getActivity(), "Night", false);
		isRandom = (Boolean) SharedUtils.getParam(getActivity(), "Interval",
				false);
		isLong = (Boolean) SharedUtils.getParam(getActivity(), "Long", false);

		irrigation = dbHelper.loadContinue(units);
		isNightStartHour = irrigation.get(0).getIsNightStartHour();
		isNightStartMinute = irrigation.get(0).getIsNightStartMinute();
		isNightContinueHour = irrigation.get(0).getIsNightContinueHour();
		isNightContinueMinute = irrigation.get(0).getIsNightContinueMinute();
		isNightEnd = irrigation.get(0).getIsNightEndHour();
		isNightEndMinutes = irrigation.get(0).getIsNightEndMinute();
		long_hour = irrigation.get(0).getIsTimeLong();
		time_number = irrigation.get(0).getNNumber();
		time_intervals = irrigation.get(0).getNRound();

	}

	@Override
	public void onClick(View v) {
		FragmentManager fgManager = getFragmentManager();
		FragmentTransaction transaction = fgManager.beginTransaction();
		switch (v.getId()) {
		case R.id.acitionbar_left:
			ApplyIrrigateProjectFragment fragment = new ApplyIrrigateProjectFragment();
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			Bundle bundle = new Bundle();
			bundle.putString("units", units);
			fragment.setArguments(bundle);
			transaction.setCustomAnimations(R.anim.slide_fragment_horizontal_right_in, R.anim.slide_fragment_horizontal_left_out);
			transaction.replace(R.id.fl, fragment, "main");
			transaction.commit();
			break;
		case R.id.layout_apply_irriagte_project_season_time_continue:
			id = 1;
			showDateTimePickers(mInflater);// 灌水持续时间
			break;
		case R.id.layout_apply_irriagte_project_season_time_number:
			id = 3;
			showDateTimePicker(mInflater);// 灌溉次数
			break;
		case R.id.layout_apply_irriagte_project_season_time_interval:// 间隔时间
			id = 4;
			showDateTimePicker(mInflater);
			break;
		case R.id.layout_apply_irriagte_project_season_time_start:// 开始时间
			showDateTimePicker1(mInflater);
			break;
		case R.id.button_apply_irriagte_project_season:
			if(NetworkUtils.isNetworkAvailable(getActivity())==false){
				Toast.makeText(getActivity(), "当前无网络！请检查后重试！", Toast.LENGTH_SHORT).show();
			}else{
			if (CheckUtil.IsEmpty(time_start)
					|| time_start.equals("0000-00-00 00:00")) {

				Toast.makeText(getActivity(), "请设置开始时间", Toast.LENGTH_SHORT)
						.show();
				break;
			} else if (CheckUtil.IsEmpty(tv_time_continue)
					|| tv_time_continue.getText().equals("0时")
					|| tv_time_continue.getText().equals("0小时")
					|| tv_time_continue.getText().equals("0时0分")) {
				Toast.makeText(getActivity(), "请设置持续时间", Toast.LENGTH_SHORT)
						.show();
				break;
			} else if (CheckUtil.IsEmpty(time_number) || time_number == 0) {
				Toast.makeText(getActivity(), "请设置灌溉次数", Toast.LENGTH_SHORT)
						.show();
				break;
			} else if (CheckUtil.IsEmpty(time_intervals) || time_intervals == 0) {
				Toast.makeText(getActivity(), "请设置间隔时间", Toast.LENGTH_SHORT)
						.show();
				break;
			}
			if (time_start.equals(time_max_start)) {
				onleOnes = 1;
			}
			if (isLong == true && isNight == false) {
				irrigationProject = new IrrigationProject();
				List<IrrigationProject> listentity = dbHelper
						.loadLastMsgBySessionid(units);
				time_compare_start = time_start;
				time_long = 24 - long_hour;
				java.util.Date date2 = new java.util.Date();
				SimpleDateFormat format2 = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm");
				try {
					date2 = format2.parse(time_compare_start);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				Date dates = new Date();
				SimpleDateFormat formats = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm");
				try {
					dates = formats.parse(time_start);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (dates.getHours() > 0 && dates.getHours() < time_long) {
					Toast.makeText(getActivity(), "非工作区间", Toast.LENGTH_LONG)
							.show();
				} else {
					List<IrrigationProject> listentity1 = dbHelper
							.loadLastMsgBySessionid(units);
					if (CheckUtil.IsEmpty(listentity1)) {
						now_round = 0;
					} else {
						now_round = Integer.valueOf(listentity1.get(0)
								.getRound());
					}
					now_rounds = now_round;
					now_round1 = now_round;
					round = listentity.size() / MatchedNum + time_number;
					for (int i = 1; i <= MatchedNum * time_number; i++) {
						irrigationProject = new IrrigationProject();
						irrigationProject.setIrrigation(units);
						irrigationProject.setProjectstart("0000-00-00 00:00");
						irrigationProject.setProjectend("0000-00-00 00:00");
						if (a >= MatchedNum + 1) {
							now_rounds = now_rounds + 1;
							a = 1;
						}
						if (Marshalling >= MatchedNum + 1) {
							Marshalling = 1;
						}
						irrigationProject.setRound(now_rounds + 1 + "");
						irrigationProject.setMarshalling(Marshalling + "");
						dbHelper.saveProject(irrigationProject);
						a++;
						Marshalling++;
					}
					int randomCommon[] = randomCommon(1, MatchedNum + 1,
							MatchedNum);
					time_long = 24 - long_hour;
					nHours = nHour;
					a = 1;
					Marshalling = 1;
					for (int i = 1; i <= MatchedNum * time_number; i++) {
						Date date = new Date();
						SimpleDateFormat format = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm");
						if (a >= MatchedNum + 1) {
							now_round1 = now_round1 + 1;
							a = 1;
						}
						if (Marshalling >= MatchedNum + 1) {
							Marshalling = 1;
						}
						s++;
						try {
							if (i == 1) {
								date = format.parse(time_start);
								time_starts = format.format(date);
								// date.setHours(date.getHours() + nHour);
								// date.setMinutes(date.getMinutes() +
								// nMinutes);

								if (date.getHours() == time_long) {
									onleOnes = 1;
								}
								while (nHours > 0) {
									if (nHours >= long_hour) {
										date.setHours(date.getHours()
												+ time_long + long_hour);
										date.setMinutes(date.getMinutes()
												+ nMinutes);
										nHours = nHours - long_hour;
									} else {
										compareDayf = date.getDate();
										compareMonth = date.getMonth();
										compareYear = date.getYear();
										date.setHours(date.getHours() + nHours);
										date.setMinutes(date.getMinutes()
												+ nMinutes);
										nHours = 0;
										compareDays = date.getDate();
										compareMonths = date.getMonth();
										compareYears = date.getYear();
										if (date.getHours() == time_long) {
											onleOnes = 1;
										}
										if (compareYears > compareYear) {
											if (date.getHours() == 0) {
												isHave = 1;
											} else {
												if (onleOnes != 1) {
													DecimalFormat fnum = new DecimalFormat(
															"##0.0");
													String sleep = fnum
															.format(((float) (24 - nHour)
																	/ ((float) 24) * time_long))
															+ "";
													String[] hours = sleep
															.split("\\.");
													date.setHours(date
															.getHours()
															+ Integer
																	.valueOf(hours[0]));
													if (!CheckUtil
															.IsEmpty(hours[1])) {
														date.setMinutes(date
																.getMinutes()
																+ Integer
																		.valueOf(hours[1])
																* 6);
													}
													onleOnes = 1;
												} else {
													date.setHours(date
															.getHours()
															+ time_long);
												}
											}
										} else if (compareMonths > compareMonth) {
											if (date.getHours() == 0) {
												isHave = 1;
											} else {
												if (onleOnes != 1) {
													DecimalFormat fnum = new DecimalFormat(
															"##0.0");
													String sleep = fnum
															.format(((float) (24 - nHour)
																	/ ((float) 24) * time_long))
															+ "";
													String[] hours = sleep
															.split("\\.");
													date.setHours(date
															.getHours()
															+ Integer
																	.valueOf(hours[0]));
													if (!CheckUtil
															.IsEmpty(hours[1])) {
														date.setMinutes(date
																.getMinutes()
																+ Integer
																		.valueOf(hours[1])
																* 6);
													}
													onleOnes = 1;
												} else {
													date.setHours(date
															.getHours()
															+ time_long);
												}
											}
										} else if (compareDays > compareDayf) {
											if (date.getHours() == 0) {
												isHave = 1;
											} else {
												if (onleOnes != 1) {
													DecimalFormat fnum = new DecimalFormat(
															"##0.0");
													String sleep = fnum
															.format(((float) (24 - nHour)
																	/ ((float) 24) * time_long))
															+ "";
													String[] hours = sleep
															.split("\\.");
													date.setHours(date
															.getHours()
															+ Integer
																	.valueOf(hours[0]));
													if (!CheckUtil
															.IsEmpty(hours[1])) {
														date.setMinutes(date
																.getMinutes()
																+ Integer
																		.valueOf(hours[1])
																* 6);
													}
													onleOnes = 1;
												} else {
													date.setHours(date
															.getHours()
															+ time_long);
												}
											}
										}
									}
								}
							} else {
								date = format.parse(time_end);
								if (s > MatchedNum) {
									date.setDate(date.getDate() + 10);
									s = 1;
								}
								if (isHave == 1) {
									if (onleOnes != 1) {
										DecimalFormat fnum = new DecimalFormat(
												"##0.0");
										String sleep = fnum
												.format(((float) (24 - nHour)
														/ ((float) 24) * time_long))
												+ "";
										String[] hours = sleep.split("\\.");
										date.setHours(date.getHours()
												+ Integer.valueOf(hours[0]));
										if (!CheckUtil.IsEmpty(hours[1])) {
											date.setMinutes(date.getMinutes()
													+ Integer.valueOf(hours[1])
													* 6);
										}
										onleOnes = 1;
									} else {
										date.setHours(date.getHours()
												+ time_long);
									}
									isHave = 0;
								}
								time_starts = format.format(date);
								nHour2 = nHour;
								while (nHour2 > 0) {
									if (nHour2 >= long_hour) {
										date.setHours(date.getHours()
												+ time_long + long_hour);
										date.setMinutes(date.getMinutes()
												+ nMinutes);
										nHour2 = nHour2 - long_hour;

									} else {
										compareDayf = date.getDate();
										compareMonth = date.getMonth();
										compareYear = date.getYear();
										date.setHours(date.getHours() + nHour2);
										date.setMinutes(date.getMinutes()
												+ nMinutes);
										nHour2 = 0;
										compareDays = date.getDate();
										compareMonths = date.getMonth();
										compareYears = date.getYear();
										if (compareYears > compareYear) {
											if (date.getHours() == 0) {
												isHave = 1;
											} else {
												if (onleOnes != 1) {
													DecimalFormat fnum = new DecimalFormat(
															"##0.0");
													String sleep = fnum
															.format(((float) (24 - nHour)
																	/ ((float) 24) * time_long))
															+ "";
													String[] hours = sleep
															.split("\\.");
													date.setHours(date
															.getHours()
															+ Integer
																	.valueOf(hours[0]));
													if (!CheckUtil
															.IsEmpty(hours[1])) {
														date.setMinutes(date
																.getMinutes()
																+ Integer
																		.valueOf(hours[1])
																* 6);
													}
													onleOnes = 1;
												} else {
													date.setHours(date
															.getHours()
															+ time_long);
												}
											}
										} else if (compareMonths > compareMonth) {
											if (date.getHours() == 0) {
												isHave = 1;
											} else {
												if (onleOnes != 1) {
													DecimalFormat fnum = new DecimalFormat(
															"##0.0");
													String sleep = fnum
															.format(((float) (24 - nHour)
																	/ ((float) 24) * time_long))
															+ "";
													String[] hours = sleep
															.split("\\.");
													date.setHours(date
															.getHours()
															+ Integer
																	.valueOf(hours[0]));
													if (!CheckUtil
															.IsEmpty(hours[1])) {
														date.setMinutes(date
																.getMinutes()
																+ Integer
																		.valueOf(hours[1])
																* 6);
													}
													onleOnes = 1;
												} else {
													date.setHours(date
															.getHours()
															+ time_long);
												}
											}
										} else if (compareDays > compareDayf) {
											if (date.getHours() == 0) {
												isHave = 1;
											} else {
												if (onleOnes != 1) {
													DecimalFormat fnum = new DecimalFormat(
															"##0.0");
													String sleep = fnum
															.format(((float) (24 - nHour)
																	/ ((float) 24) * time_long))
															+ "";
													String[] hours = sleep
															.split("\\.");
													date.setHours(date
															.getHours()
															+ Integer
																	.valueOf(hours[0]));
													if (!CheckUtil
															.IsEmpty(hours[1])) {
														date.setMinutes(date
																.getMinutes()
																+ Integer
																		.valueOf(hours[1])
																* 6);
													}
													onleOnes = 1;
												} else {
													date.setHours(date
															.getHours()
															+ time_long);
												}
											}
										}
									}
								}
							}
							time_end = format.format(date);
							if (CheckUtil.IsEmpty(listentity)) {
								now_round = 0;
							}
							if (isRandom == true) {
								dbHelper.updateProjects(units, now_round1 + 1
										+ "", randomCommon[i], time_starts,
										time_end);
							} else {
								dbHelper.updateProjects(units, now_round1 + 1
										+ "", Marshalling, time_starts,
										time_end);
							}
							a++;
							Marshalling++;
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
					notifys = 1;
					SharedUtils.setParam(getActivity(), "notifys", notifys);
					ApplyIrrigateProjectFragment fragment3 = new ApplyIrrigateProjectFragment();
					Bundle bundle1 = new Bundle();
					bundle1.putString("units", units);
					fragment3.setArguments(bundle1);
					transaction
							.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
					transaction.replace(R.id.fl, fragment3, "main");
					transaction.commit();
					firsts = dbHelper.loadisFirst(units);
					if (CheckUtil.IsEmpty(firsts)) {
						irrigationIsFirst.setIrrigation(units);
						irrigationIsFirst.setIsFirst(0);
						dbHelper.saveIsFirst(irrigationIsFirst);
					}
				}
			} else if (isLong == true && isNight == true) {
				irrigationProject = new IrrigationProject();
				List<IrrigationProject> listentity = dbHelper
						.loadLastMsgBySessionid(units);
				time_compare_start = time_start;
				time_long = 24 - long_hour;
				if (isNightContinueHour >= long_hour) {
					Date dates = new Date();
					SimpleDateFormat formats = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm");
					try {
						dates = formats.parse(time_start);
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
					compareDay = dates.getDate();
					//
					if (isNightStartHour == 0) {
						isNightStartHours = 24;
					} else {
						isNightStartHours = isNightStartHour;
					}
					isGreaters = (int) SharedUtils.getParam(getActivity(),
							"isGreaters", 0);
					if (isGreaters == 1) {
						if (dates.getHours() < isNightStartHours
								&& dates.getHours() > isNightEnd) {
							isLegal = 1;
						} else {
							isLegal = 0;
						}
					} else {
						if (dates.getHours() > isNightStartHours
								&& dates.getHours() < isNightEnd) {
							isLegal = 0;
						} else {
							isLegal = 1;
						}
					}

					if (dates.getHours() == isNightStartHour) {
						if (dates.getMinutes() < isNightStartMinute) {
							isLegal = 1;
						} else {
							isLegal = 0;
						}
					}

					if (dates.getHours() == isNightEnd) {
						if (dates.getMinutes() >= isNightEndMinutes) {
							isLegal = 1;
						} else {
							isLegal = 0;
						}
					}
					if (isLegal != 1) {
						Toast.makeText(getActivity(), "非工作区间",
								Toast.LENGTH_SHORT).show();
					} else {
						List<IrrigationProject> listentity1 = dbHelper
								.loadLastMsgBySessionid(units);
						if (CheckUtil.IsEmpty(listentity1)) {
							now_round = 0;
						} else {
							now_round = Integer.valueOf(listentity1.get(0)
									.getRound());
						}
						now_rounds = now_round;
						now_round1 = now_round;
						round = listentity.size() / MatchedNum + time_number;
						for (int i = 1; i <= MatchedNum * time_number; i++) {
							irrigationProject = new IrrigationProject();
							irrigationProject.setIrrigation(units);
							irrigationProject
									.setProjectstart("0000-00-00 00:00");
							irrigationProject.setProjectend("0000-00-00 00:00");
							if (a >= MatchedNum + 1) {
								now_rounds = now_rounds + 1;
								a = 1;
							}
							if (Marshalling >= MatchedNum + 1) {
								Marshalling = 1;
							}
							irrigationProject.setRound((now_rounds + 1) + "");
							irrigationProject.setMarshalling(Marshalling + "");
							dbHelper.saveProject(irrigationProject);
							a++;
							Marshalling++;
						}
						a = 1;
						Marshalling = 1;
						int randomCommon[] = randomCommon(1, MatchedNum + 1,
								MatchedNum);
						time_long = 24 - long_hour;
						for (int i = 1; i <= MatchedNum * time_number; i++) {
							Date date = new Date();
							SimpleDateFormat format = new SimpleDateFormat(
									"yyyy-MM-dd HH:mm");
							if (a >= MatchedNum + 1) {
								now_round1 = now_round1 + 1;
								a = 1;
							}
							if (Marshalling >= MatchedNum + 1) {
								Marshalling = 1;
							}
							s++;
							try {
								if (i == 1) {
									date = format.parse(time_start);
									time_starts = format.format(date);
									compareDayf = date.getDate();
									compareMonth = date.getMonth();
									compareYear = date.getYear();
									date.setHours(date.getHours() + nHour);
									date.setMinutes(date.getMinutes()
											+ nMinutes);
									compareDays = date.getDate();
									compareMonths = date.getMonth();
									compareYears = date.getYear();
									if (compareYears > compareYear) {
										conpare = 0;
										if (isNightStartHour != 0) {
											if (date.getHours() == isNightEnd
													&& date.getMinutes() <= isNightEndMinutes) {
												isTrues = 1;
											}
											if (date.getHours() >= isNightStartHour) {
												isGreater = 1;
												if (date.getHours() == isNightStartHour
														&& date.getMinutes() > isNightStartMinute) {
													isTrues = 1;
												} else {
													isTrues = 2;
												}
											} else {
												isGreater = 0;
											}
											if (isGreater == 1) {
												isTrues = 1;
												isTrue = 0;
												isTrue1 = 0;
											} else {
												if (isGreaters == 1) {
													isTrues = 1;
													isTrue = 0;
													isTrue1 = 0;
												} else {
													isTrues = 0;
												}
											}
										} else {
											if (isNightStartHour == 0
													&& date.getMinutes() <= isNightStartMinute) {
												isTrues = 3;
												isTrue1 = 0;
											} else {
												isTrues = 2;
												isTrue1 = 0;
											}
										}
										if (isTrues == 1) {
											if (date.getHours() == isNightStartHour
													&& date.getMinutes() <= isNightStartMinute) {
												conpare = 1;
											} else {
												if (isTrue1 == 0) {
													date.setHours(date
															.getHours()
															+ isNightContinueHour);
													date.setMinutes(date
															.getMinutes()
															+ isNightContinueMinute);
													isTrues = 0;
													isTrue1 = 1;
													conpare = 1;
												}
											}
										} else if (isTrues == 2) {
											if (date.getHours() == isNightStartHour
													&& date.getMinutes() <= isNightStartMinute) {
												conpare = 1;
											} else {
												if (isTrue1 == 0) {
													date.setHours(date
															.getHours()
															+ isNightContinueHour);
													date.setMinutes(date
															.getMinutes()
															+ isNightContinueMinute);
													isTrues = 0;
													isTrue1 = 1;
													conpare = 1;
												}
											}
										} else if (isTrues == 3) {
											if (date.getHours() == isNightStartHour
													&& date.getMinutes() <= isNightStartMinute) {

											} else {
												if (isTrue1 == 0) {
													date.setHours(date
															.getHours()
															+ isNightContinueHour);
													date.setMinutes(date
															.getMinutes()
															+ isNightContinueMinute);
													isTrues = 0;
													isTrue1 = 1;
													conpare = 1;
												}
											}
										}
									} else if (compareMonths > compareMonth) {
										conpare = 0;
										if (isNightStartHour != 0) {
											if (date.getHours() == isNightEnd
													&& date.getMinutes() <= isNightEndMinutes) {
												isTrues = 1;
											}
											if (date.getHours() >= isNightStartHour) {
												isGreater = 1;
												if (date.getHours() == isNightStartHour
														&& date.getMinutes() > isNightStartMinute) {
													isTrues = 1;
												} else {
													isTrues = 2;
												}
											} else {
												isGreater = 0;
											}
											if (isGreater == 1) {
												isTrues = 1;
												isTrue = 0;
												isTrue1 = 0;
											} else {
												if (isGreaters == 1) {
													isTrues = 1;
													isTrue = 0;
													isTrue1 = 0;
												} else {
													isTrues = 0;
												}
											}
										} else {
											if (isNightStartHour == 0
													&& date.getMinutes() <= isNightStartMinute) {
												isTrues = 3;
												isTrue1 = 0;
											} else {
												isTrues = 2;
												isTrue1 = 0;
											}
										}
										if (isTrues == 1) {
											if (date.getHours() == isNightStartHour
													&& date.getMinutes() <= isNightStartMinute) {
												conpare = 1;
											} else {
												if (isTrue1 == 0) {
													date.setHours(date
															.getHours()
															+ isNightContinueHour);
													date.setMinutes(date
															.getMinutes()
															+ isNightContinueMinute);
													isTrues = 0;
													isTrue1 = 1;
													conpare = 1;
												}
											}
										} else if (isTrues == 2) {
											if (date.getHours() == isNightStartHour
													&& date.getMinutes() <= isNightStartMinute) {
												conpare = 1;
											} else {
												if (isTrue1 == 0) {
													date.setHours(date
															.getHours()
															+ isNightContinueHour);
													date.setMinutes(date
															.getMinutes()
															+ isNightContinueMinute);
													isTrues = 0;
													isTrue1 = 1;
													conpare = 1;
												}
											}
										} else if (isTrues == 3) {
											if (date.getHours() == isNightStartHour
													&& date.getMinutes() <= isNightStartMinute) {

											} else {
												if (isTrue1 == 0) {
													date.setHours(date
															.getHours()
															+ isNightContinueHour);
													date.setMinutes(date
															.getMinutes()
															+ isNightContinueMinute);
													isTrues = 0;
													isTrue1 = 1;
													conpare = 1;
												}
											}
										}
									} else if (compareDays > compareDayf) {
										conpare = 0;
										if (isNightStartHour != 0) {
											if (date.getHours() == isNightEnd
													&& date.getMinutes() <= isNightEndMinutes) {
												isTrues = 1;
											}
											if (date.getHours() >= isNightStartHour) {
												isGreater = 1;
												if (date.getHours() == isNightStartHour
														&& date.getMinutes() > isNightStartMinute) {
													isTrues = 1;
												} else {
													isTrues = 2;
												}
											} else {
												isGreater = 0;
											}
											if (isGreater == 1) {
												isTrues = 1;
												isTrue = 0;
												isTrue1 = 0;
											} else {
												if (isGreaters == 1) {
													isTrues = 1;
													isTrue = 0;
													isTrue1 = 0;
												} else {
													isTrues = 0;
												}
											}
										} else {
											if (isNightStartHour == 0
													&& date.getMinutes() <= isNightStartMinute) {
												isTrues = 3;
												isTrue1 = 0;
											} else {
												isTrues = 2;
												isTrue1 = 0;
											}
										}
										if (isTrues == 1) {
											if (date.getHours() == isNightStartHour
													&& date.getMinutes() <= isNightStartMinute) {
												conpare = 1;
											} else {
												if (isTrue1 == 0) {
													date.setHours(date
															.getHours()
															+ isNightContinueHour);
													date.setMinutes(date
															.getMinutes()
															+ isNightContinueMinute);
													isTrues = 0;
													isTrue1 = 1;
													conpare = 1;
												}
											}
										} else if (isTrues == 2) {
											if (date.getHours() == isNightStartHour
													&& date.getMinutes() <= isNightStartMinute) {
												conpare = 1;
											} else {
												if (isTrue1 == 0) {
													date.setHours(date
															.getHours()
															+ isNightContinueHour);
													date.setMinutes(date
															.getMinutes()
															+ isNightContinueMinute);
													isTrues = 0;
													isTrue1 = 1;
													conpare = 1;
												}
											}
										} else if (isTrues == 3) {
											if (date.getHours() == isNightStartHour
													&& date.getMinutes() <= isNightStartMinute) {

											} else {
												if (isTrue1 == 0) {
													date.setHours(date
															.getHours()
															+ isNightContinueHour);
													date.setMinutes(date
															.getMinutes()
															+ isNightContinueMinute);
													isTrues = 0;
													isTrue1 = 1;
													conpare = 1;
												}
											}
										}
									} else {
										if (isNightStartHour != 0) {
											if (date.getHours() == isNightEnd
													&& date.getMinutes() <= isNightEndMinutes) {
												isTrues = 1;
											}
											if (date.getHours() >= isNightStartHour) {
												isGreater = 1;
												if (date.getHours() == isNightStartHour
														&& date.getMinutes() > isNightStartMinute) {
													isTrues = 1;
												} else {
													isTrues = 2;
												}
											} else {
												isGreater = 0;
											}
											if (isGreater == 1) {
												if (conpare == 0) {
													isTrues = 1;
													isTrue1 = 0;
												}
											} else {
												isTrues = 0;
											}
										} else {
											if (isNightStartHour == 0
													&& date.getMinutes() <= isNightStartMinute) {
												isTrues = 0;
											} else {
												isTrues = 2;
												isTrue1 = 0;
											}
											isNightStartHours = 24;
											if (isNightStartHours
													- date.getHours() == 0) {
												isTrues = 2;
												isTrue1 = 0;
											} else {
												isTrues = 0;
											}
										}
										Date dateStart = new Date();
										SimpleDateFormat formatStart = new SimpleDateFormat(
												"yyyy-MM-dd HH:mm");
										dateStart = format.parse(time_start);
										if (dateStart.getHours() > isNightStartHour) {
											isTrue1 = 1;
										} else {
											isTrue1 = 0;
										}

										if (isTrues == 1) {
											if (date.getHours() == isNightStartHour
													&& date.getMinutes() <= isNightStartMinute) {
												conpare = 1;
											} else {
												if (isTrue1 == 0) {
													date.setHours(date
															.getHours()
															+ isNightContinueHour);
													date.setMinutes(date
															.getMinutes()
															+ isNightContinueMinute);
													isTrues = 0;
													isTrue1 = 1;
												}
											}
										} else if (isTrues == 2) {
											if (date.getHours() == isNightStartHour
													&& date.getMinutes() <= isNightStartMinute) {
												conpare = 1;
											} else {
												if (isTrue1 == 0) {
													date.setHours(date
															.getHours()
															+ isNightContinueHour);
													date.setMinutes(date
															.getMinutes()
															+ isNightContinueMinute);
													isTrues = 0;
													isTrue1 = 1;
												}
											}
										} else if (isTrues == 3) {
											if (date.getHours() == isNightStartHour
													&& date.getMinutes() <= isNightStartMinute) {

											} else {
												if (isTrue1 == 0) {
													date.setHours(date
															.getHours()
															+ isNightContinueHour);
													date.setMinutes(date
															.getMinutes()
															+ isNightContinueMinute);
													isTrues = 0;
													isTrue1 = 1;
												}
											}
										}
									}
								} else {
									date = format.parse(time_end);
									if (s > MatchedNum) {
										date.setDate(date.getDate() + 10);
										s = 1;
									}
									if (isTrues == 1) {
										if (isTrue1 == 0) {
											date.setHours(date.getHours()
													+ isNightContinueHour);
											date.setMinutes(date.getMinutes()
													+ isNightContinueMinute);
											isTrues = 0;
											isTrue1 = 1;
										}
										isTrues = 0;
										conpare = 1;
									} else if (isTrues == 2) {
										if (isTrue1 == 0) {
											date.setHours(date.getHours()
													+ isNightContinueHour);
											date.setMinutes(date.getMinutes()
													+ isNightContinueMinute);
											isTrues = 0;
											isTrue1 = 1;
										}
										isTrues = 0;
										conpare = 1;
									} else if (isTrues == 3) {
										if (isTrue1 == 0) {
											date.setHours(date.getHours()
													+ isNightContinueHour);
											date.setMinutes(date.getMinutes()
													+ isNightContinueMinute);
											isTrues = 0;
											isTrue1 = 1;
										}
										isTrues = 0;
										conpare = 1;
									}
									time_starts = format.format(date);
									compareDayf = date.getDate();
									compareMonth = date.getMonth();
									compareYear = date.getYear();
									date.setHours(date.getHours() + nHour);
									date.setMinutes(date.getMinutes()
											+ nMinutes);
									compareDays = date.getDate();
									compareMonths = date.getMonth();
									compareYears = date.getYear();
									if (compareYears > compareYear) {
										conpare = 0;
										isGreater1 = 0;
										if (isNightStartHour != 0) {
											if (date.getHours() == isNightEnd
													&& date.getMinutes() <= isNightEndMinutes) {
												isTrues = 1;
											}
											if (date.getHours() >= isNightStartHour) {
												isGreater = 1;
												if (date.getHours() == isNightStartHour
														&& date.getMinutes() > isNightStartMinute) {
													isTrues = 1;
												} else {
													isTrues = 2;
												}
											} else {
												isGreater = 0;
											}
											if (isGreater == 1) {
												if (conpare == 0) {
													isTrues = 1;
													isTrue = 0;
													isTrue1 = 0;
												}
											} else {
												if (isGreaters == 1) {
													isTrues = 1;
													isTrue = 0;
													isTrue1 = 0;
												} else {
													isTrues = 0;
												}

											}
										} else {
											if (isNightStartHour == 0
													&& date.getMinutes() <= isNightStartMinute) {
												isTrues = 3;
												isTrue1 = 0;
											} else {
												isTrues = 2;
												isTrue1 = 0;
											}
										}
										if (isTrues == 1) {
											if (date.getHours() == isNightStartHour
													&& date.getMinutes() <= isNightStartMinute) {
												compare = 1;
											} else {
												if (isTrue1 == 0) {
													date.setHours(date
															.getHours()
															+ isNightContinueHour);
													date.setMinutes(date
															.getMinutes()
															+ isNightContinueMinute);
													isTrues = 0;
													isTrue1 = 1;
													conpare = 1;
												}
											}
										} else if (isTrues == 2) {
											if (date.getHours() == isNightStartHour
													&& date.getMinutes() <= isNightStartMinute) {
												compare = 1;
											} else {
												if (isTrue1 == 0) {
													date.setHours(date
															.getHours()
															+ isNightContinueHour);
													date.setMinutes(date
															.getMinutes()
															+ isNightContinueMinute);
													isTrues = 0;
													isTrue1 = 1;
													conpare = 1;
												}
											}
										} else if (isTrues == 3) {
											if (date.getHours() == isNightStartHour
													&& date.getMinutes() <= isNightStartMinute) {

											} else {
												if (isTrue1 == 0) {
													date.setHours(date
															.getHours()
															+ isNightContinueHour);
													date.setMinutes(date
															.getMinutes()
															+ isNightContinueMinute);
													isTrues = 0;
													isTrue1 = 1;
													conpare = 1;
												}
											}
										}
									} else if (compareMonths > compareMonth) {
										conpare = 0;
										isGreater1 = 0;
										if (isNightStartHour != 0) {
											if (date.getHours() == isNightEnd
													&& date.getMinutes() <= isNightEndMinutes) {
												isTrues = 1;
											}
											if (date.getHours() >= isNightStartHour) {
												isGreater = 1;
												if (date.getHours() == isNightStartHour
														&& date.getMinutes() > isNightStartMinute) {
													isTrues = 1;
												} else {
													isTrues = 2;
												}
											} else {
												isGreater = 0;
											}
											if (isGreater == 1) {
												if (conpare == 0) {
													isTrues = 1;
													isTrue = 0;
													isTrue1 = 0;
												}
											} else {
												if (isGreaters == 1) {
													isTrues = 1;
													isTrue = 0;
													isTrue1 = 0;
												} else {
													isTrues = 0;
												}

											}
										} else {
											if (isNightStartHour == 0
													&& date.getMinutes() <= isNightStartMinute) {
												isTrues = 3;
												isTrue1 = 0;
											} else {
												isTrues = 2;
												isTrue1 = 0;
											}
										}
										if (isTrues == 1) {
											if (date.getHours() == isNightStartHour
													&& date.getMinutes() <= isNightStartMinute) {
												compare = 1;
											} else {
												if (isTrue1 == 0) {
													date.setHours(date
															.getHours()
															+ isNightContinueHour);
													date.setMinutes(date
															.getMinutes()
															+ isNightContinueMinute);
													isTrues = 0;
													isTrue1 = 1;
													conpare = 1;
												}
											}
										} else if (isTrues == 2) {
											if (date.getHours() == isNightStartHour
													&& date.getMinutes() <= isNightStartMinute) {
												compare = 1;
											} else {
												if (isTrue1 == 0) {
													date.setHours(date
															.getHours()
															+ isNightContinueHour);
													date.setMinutes(date
															.getMinutes()
															+ isNightContinueMinute);
													isTrues = 0;
													isTrue1 = 1;
													conpare = 1;
												}
											}
										} else if (isTrues == 3) {
											if (date.getHours() == isNightStartHour
													&& date.getMinutes() <= isNightStartMinute) {

											} else {
												if (isTrue1 == 0) {
													date.setHours(date
															.getHours()
															+ isNightContinueHour);
													date.setMinutes(date
															.getMinutes()
															+ isNightContinueMinute);
													isTrues = 0;
													isTrue1 = 1;
													conpare = 1;
												}
											}
										}
									} else if (compareDays > compareDayf) {
										conpare = 0;
										isGreater1 = 0;
										if (isNightStartHour != 0) {
											if (date.getHours() == isNightEnd
													&& date.getMinutes() <= isNightEndMinutes) {
												isTrues = 1;
											}
											if (date.getHours() >= isNightStartHour) {
												isGreater = 1;
												if (date.getHours() == isNightStartHour
														&& date.getMinutes() > isNightStartMinute) {
													isTrues = 1;
												} else {
													isTrues = 2;
												}
											} else {
												isGreater = 0;
											}
											if (isGreater == 1) {
												if (conpare == 0) {
													isTrues = 1;
													isTrue = 0;
													isTrue1 = 0;
												}
											} else {
												if (isGreaters == 1) {
													isTrues = 1;
													isTrue = 0;
													isTrue1 = 0;
												} else {
													isTrues = 0;
												}

											}
										} else {
											if (isNightStartHour == 0
													&& date.getMinutes() <= isNightStartMinute) {
												isTrues = 3;
												isTrue1 = 0;
											} else {
												isTrues = 2;
												isTrue1 = 0;
											}
										}
										if (isTrues == 1) {
											if (date.getHours() == isNightStartHour
													&& date.getMinutes() <= isNightStartMinute) {
												compare = 1;
											} else {
												if (isTrue1 == 0) {
													date.setHours(date
															.getHours()
															+ isNightContinueHour);
													date.setMinutes(date
															.getMinutes()
															+ isNightContinueMinute);
													isTrues = 0;
													isTrue1 = 1;
													conpare = 1;
												}
											}
										} else if (isTrues == 2) {
											if (date.getHours() == isNightStartHour
													&& date.getMinutes() <= isNightStartMinute) {
												compare = 1;
											} else {
												if (isTrue1 == 0) {
													date.setHours(date
															.getHours()
															+ isNightContinueHour);
													date.setMinutes(date
															.getMinutes()
															+ isNightContinueMinute);
													isTrues = 0;
													isTrue1 = 1;
													conpare = 1;
												}
											}
										} else if (isTrues == 3) {
											if (date.getHours() == isNightStartHour
													&& date.getMinutes() <= isNightStartMinute) {

											} else {
												if (isTrue1 == 0) {
													date.setHours(date
															.getHours()
															+ isNightContinueHour);
													date.setMinutes(date
															.getMinutes()
															+ isNightContinueMinute);
													isTrues = 0;
													isTrue1 = 1;
													conpare = 1;
												}
											}
										}
									} else {
										if (isNightStartHour != 0) {
											if (date.getHours() == isNightEnd
													&& date.getMinutes() <= isNightEndMinutes) {
												isTrues = 1;
											}
											if (date.getHours() >= isNightStartHour) {
												isGreater = 1;
												if (isGreaters == 1) {
													conpare = 0;
													isGreater1 = 0;
												}
												if (date.getHours() == isNightStartHour
														&& date.getMinutes() > isNightStartMinute) {
													isTrues = 1;
												} else {
													if (isGreater1 == 0) {
														isTrues = 2;
														if (conpare == 0) {
															isTrue1 = 0;
															isGreater1 = 1;
														}
													} else {
														isTrues = 2;
														isTrue1 = 1;
													}
												}
											} else {
												isGreater = 0;
											}
											if (isGreater == 1) {
												if (conpare == 0) {
													isTrues = 1;
													isTrue1 = 0;
												}
											} else {
												isTrues = 0;
											}
										} else {
											if (isNightStartHour == 0
													&& date.getMinutes() <= isNightStartMinute) {
												isTrues = 0;
											} else {
												isTrues = 2;
											}
											isNightStartHours = 24;
											if (isNightStartHours
													- date.getHours() == 0) {
												isTrues = 2;
											} else {
												isTrues = 0;
											}
										}
										if (isTrues == 1) {
											if (date.getHours() == isNightStartHour
													&& date.getMinutes() <= isNightStartMinute) {
												conpare = 1;
											} else {
												if (isTrue1 == 0) {
													date.setHours(date
															.getHours()
															+ isNightContinueHour);
													date.setMinutes(date
															.getMinutes()
															+ isNightContinueMinute);
													isTrues = 0;
													isTrue1 = 1;
													conpare = 1;
												}
											}
										} else if (isTrues == 2) {
											if (date.getHours() == isNightStartHour
													&& date.getMinutes() <= isNightStartMinute) {
												conpare = 1;
											} else {
												if (isTrue1 == 0) {
													date.setHours(date
															.getHours()
															+ isNightContinueHour);
													date.setMinutes(date
															.getMinutes()
															+ isNightContinueMinute);
													isTrues = 0;
													isTrue1 = 1;
													conpare = 1;
												}
											}
										} else if (isTrues == 3) {
											if (date.getHours() == isNightStartHour
													&& date.getMinutes() <= isNightStartMinute) {

											} else {
												if (isTrue1 == 0) {
													date.setHours(date
															.getHours()
															+ isNightContinueHour);
													date.setMinutes(date
															.getMinutes()
															+ isNightContinueMinute);
													isTrues = 0;
													isTrue1 = 1;
													conpare = 1;
												}
											}
										}
									}
								}
								time_end = format.format(date);
								if (CheckUtil.IsEmpty(listentity)) {
									now_round = 0;
								}
								if (isRandom == true) {
									dbHelper.updateProjects(units, now_round1
											+ 1 + "", randomCommon[i],
											time_starts, time_end);
								} else {
									dbHelper.updateProjects(units, now_round1
											+ 1 + "", Marshalling, time_starts,
											time_end);
								}
								a++;
								Marshalling++;
							} catch (ParseException e) {
								e.printStackTrace();
							}
						}
						notifys = 1;
						SharedUtils.setParam(getActivity(), "notifys", notifys);
						ApplyIrrigateProjectFragment fragment3 = new ApplyIrrigateProjectFragment();
						Bundle bundle1 = new Bundle();
						bundle1.putString("units", units);
						fragment3.setArguments(bundle1);
						transaction
								.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
						transaction.replace(R.id.fl, fragment3, "main");
						transaction.commit();
						firsts = dbHelper.loadisFirst(units);
						if (CheckUtil.IsEmpty(firsts)) {
							irrigationIsFirst.setIrrigation(units);
							irrigationIsFirst.setIsFirst(0);
							dbHelper.saveIsFirst(irrigationIsFirst);
						}
						// }
					}
				} else if (isNightContinueHour < long_hour) {
					java.util.Date date2 = new java.util.Date();
					SimpleDateFormat format2 = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm");
					try {
						date2 = format2.parse(time_compare_start);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					Date dates = new Date();
					SimpleDateFormat formats = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm");
					try {
						dates = formats.parse(time_start);
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if (dates.getHours() > 0 && dates.getHours() < time_long) {
						Toast.makeText(getActivity(), "非工作区间",
								Toast.LENGTH_LONG).show();
					} else {
						List<IrrigationProject> listentity1 = dbHelper
								.loadLastMsgBySessionid(units);
						if (CheckUtil.IsEmpty(listentity1)) {
							now_round = 0;
						} else {
							now_round = Integer.valueOf(listentity1.get(0)
									.getRound());
						}
						now_rounds = now_round;
						now_round1 = now_round;
						round = listentity.size() / MatchedNum + time_number;
						for (int i = 1; i <= MatchedNum * time_number; i++) {
							irrigationProject = new IrrigationProject();
							irrigationProject.setIrrigation(units);
							irrigationProject
									.setProjectstart("0000-00-00 00:00");
							irrigationProject.setProjectend("0000-00-00 00:00");
							if (a >= MatchedNum + 1) {
								now_rounds = now_rounds + 1;
								a = 1;
							}
							if (Marshalling >= MatchedNum + 1) {
								Marshalling = 1;
							}
							irrigationProject.setRound((now_rounds + 1) + "");
							irrigationProject.setMarshalling(Marshalling + "");
							dbHelper.saveProject(irrigationProject);
							a++;
							Marshalling++;
						}
						int randomCommon[] = randomCommon(1, MatchedNum + 1,
								MatchedNum);
						time_long = 24 - long_hour;
						nHours = nHour;
						a = 1;
						Marshalling = 1;
						for (int i = 1; i <= MatchedNum * time_number; i++) {
							Date date = new Date();
							SimpleDateFormat format = new SimpleDateFormat(
									"yyyy-MM-dd HH:mm");
							if (a >= MatchedNum + 1) {
								now_round1 = now_round1 + 1;
								a = 1;
							}
							if (Marshalling >= MatchedNum + 1) {
								Marshalling = 1;
							}
							s++;
							try {
								if (i == 1) {
									date = format.parse(time_start);
									time_starts = format.format(date);
									if (date.getHours() == time_long) {
										onleOnes = 1;
									}
									while (nHours > 0) {
										if (nHours >= long_hour) {
											date.setHours(date.getHours()
													+ time_long + long_hour);
											date.setMinutes(date.getMinutes()
													+ nMinutes);
											nHours = nHours - long_hour;
										} else {
											compareDayf = date.getDate();
											compareMonth = date.getMonth();
											compareYear = date.getYear();
											date.setHours(date.getHours()
													+ nHours);
											date.setMinutes(date.getMinutes()
													+ nMinutes);
											nHours = 0;
											compareDays = date.getDate();
											compareMonths = date.getMonth();
											compareYears = date.getYear();
											if (date.getHours() == time_long) {
												onleOnes = 1;
											}
											if (compareYears > compareYear) {
												if (date.getHours() == 0) {
													isHave = 1;
												} else {
													if (onleOnes != 1) {
														DecimalFormat fnum = new DecimalFormat(
																"##0.0");
														String sleep = fnum
																.format(((float) (24 - nHour)
																		/ ((float) 24) * time_long))
																+ "";
														String[] hours = sleep
																.split("\\.");
														date.setHours(date
																.getHours()
																+ Integer
																		.valueOf(hours[0]));
														if (!CheckUtil
																.IsEmpty(hours[1])) {
															date.setMinutes(date
																	.getMinutes()
																	+ Integer
																			.valueOf(hours[1])
																	* 6);
														}
														onleOnes = 1;
													} else {
														date.setHours(date
																.getHours()
																+ time_long);
													}
												}
											} else if (compareMonths > compareMonth) {
												if (date.getHours() == 0) {
													isHave = 1;
												} else {
													if (onleOnes != 1) {
														DecimalFormat fnum = new DecimalFormat(
																"##0.0");
														String sleep = fnum
																.format(((float) (24 - nHour)
																		/ ((float) 24) * time_long))
																+ "";
														String[] hours = sleep
																.split("\\.");
														date.setHours(date
																.getHours()
																+ Integer
																		.valueOf(hours[0]));
														if (!CheckUtil
																.IsEmpty(hours[1])) {
															date.setMinutes(date
																	.getMinutes()
																	+ Integer
																			.valueOf(hours[1])
																	* 6);
														}
														onleOnes = 1;
													} else {
														date.setHours(date
																.getHours()
																+ time_long);
													}
												}
											} else if (compareDays > compareDayf) {
												if (date.getHours() == 0) {
													isHave = 1;
												} else {
													if (onleOnes != 1) {
														DecimalFormat fnum = new DecimalFormat(
																"##0.0");
														String sleep = fnum
																.format(((float) (24 - nHour)
																		/ ((float) 24) * time_long))
																+ "";
														String[] hours = sleep
																.split("\\.");
														date.setHours(date
																.getHours()
																+ Integer
																		.valueOf(hours[0]));
														if (!CheckUtil
																.IsEmpty(hours[1])) {
															date.setMinutes(date
																	.getMinutes()
																	+ Integer
																			.valueOf(hours[1])
																	* 6);
														}
														onleOnes = 1;
													} else {
														date.setHours(date
																.getHours()
																+ time_long);
													}
												}
											}
										}
									}
								} else {
									date = format.parse(time_end);
									if (s > MatchedNum) {
										date.setDate(date.getDate() + 10);
										s = 1;
									}
									if (isHave == 1) {
										if (onleOnes != 1) {
											DecimalFormat fnum = new DecimalFormat(
													"##0.0");
											String sleep = fnum
													.format(((float) (24 - nHour)
															/ ((float) 24) * time_long))
													+ "";
											String[] hours = sleep.split("\\.");
											date.setHours(date.getHours()
													+ Integer.valueOf(hours[0]));
											if (!CheckUtil.IsEmpty(hours[1])) {
												date.setMinutes(date
														.getMinutes()
														+ Integer
																.valueOf(hours[1])
														* 6);
											}
											onleOnes = 1;
										} else {
											date.setHours(date.getHours()
													+ time_long);
										}
										isHave = 0;
									}
									time_starts = format.format(date);
									nHour2 = nHour;
									while (nHour2 > 0) {
										if (nHour2 >= long_hour) {
											date.setHours(date.getHours()
													+ time_long + long_hour);
											date.setMinutes(date.getMinutes()
													+ nMinutes);
											nHour2 = nHour2 - long_hour;

										} else {
											compareDayf = date.getDate();
											compareMonth = date.getMonth();
											compareYear = date.getYear();
											date.setHours(date.getHours()
													+ nHour2);
											date.setMinutes(date.getMinutes()
													+ nMinutes);
											nHour2 = 0;
											compareDays = date.getDate();
											compareMonths = date.getMonth();
											compareYears = date.getYear();
											if (compareYears > compareYear) {
												if (date.getHours() == 0) {
													isHave = 1;
												} else {
													if (onleOnes != 1) {
														DecimalFormat fnum = new DecimalFormat(
																"##0.0");
														String sleep = fnum
																.format(((float) (24 - nHour)
																		/ ((float) 24) * time_long))
																+ "";
														String[] hours = sleep
																.split("\\.");
														date.setHours(date
																.getHours()
																+ Integer
																		.valueOf(hours[0]));
														if (!CheckUtil
																.IsEmpty(hours[1])) {
															date.setMinutes(date
																	.getMinutes()
																	+ Integer
																			.valueOf(hours[1])
																	* 6);
														}
														onleOnes = 1;
													} else {
														date.setHours(date
																.getHours()
																+ time_long);
													}
												}
											} else if (compareMonths > compareMonth) {
												if (date.getHours() == 0) {
													isHave = 1;
												} else {
													if (onleOnes != 1) {
														DecimalFormat fnum = new DecimalFormat(
																"##0.0");
														String sleep = fnum
																.format(((float) (24 - nHour)
																		/ ((float) 24) * time_long))
																+ "";
														String[] hours = sleep
																.split("\\.");
														date.setHours(date
																.getHours()
																+ Integer
																		.valueOf(hours[0]));
														if (!CheckUtil
																.IsEmpty(hours[1])) {
															date.setMinutes(date
																	.getMinutes()
																	+ Integer
																			.valueOf(hours[1])
																	* 6);
														}
														onleOnes = 1;
													} else {
														date.setHours(date
																.getHours()
																+ time_long);
													}
												}
											} else if (compareDays > compareDayf) {
												if (date.getHours() == 0) {
													isHave = 1;
												} else {
													if (onleOnes != 1) {
														DecimalFormat fnum = new DecimalFormat(
																"##0.0");
														String sleep = fnum
																.format(((float) (24 - nHour)
																		/ ((float) 24) * time_long))
																+ "";
														String[] hours = sleep
																.split("\\.");
														date.setHours(date
																.getHours()
																+ Integer
																		.valueOf(hours[0]));
														if (!CheckUtil
																.IsEmpty(hours[1])) {
															date.setMinutes(date
																	.getMinutes()
																	+ Integer
																			.valueOf(hours[1])
																	* 6);
														}
														onleOnes = 1;
													} else {
														date.setHours(date
																.getHours()
																+ time_long);
													}
												}
											}
										}
									}
								}
								time_end = format.format(date);
								if (CheckUtil.IsEmpty(listentity)) {
									now_round = 0;
								}
								if (isRandom == true) {
									dbHelper.updateProjects(units, now_round1
											+ 1 + "", randomCommon[i],
											time_starts, time_end);
								} else {
									dbHelper.updateProjects(units, now_round1
											+ 1 + "", Marshalling, time_starts,
											time_end);
								}
								a++;
								Marshalling++;
							} catch (ParseException e) {
								e.printStackTrace();
							}
						}
						notifys = 1;
						SharedUtils.setParam(getActivity(), "notifys", notifys);
						ApplyIrrigateProjectFragment fragment3 = new ApplyIrrigateProjectFragment();
						Bundle bundle1 = new Bundle();
						bundle1.putString("units", units);
						fragment3.setArguments(bundle1);
						transaction
								.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
						transaction.replace(R.id.fl, fragment3, "main");
						transaction.commit();
						firsts = dbHelper.loadisFirst(units);
						if (CheckUtil.IsEmpty(firsts)) {
							irrigationIsFirst.setIrrigation(units);
							irrigationIsFirst.setIsFirst(0);
							dbHelper.saveIsFirst(irrigationIsFirst);
						}
						 }
					}
			} else if (isLong == false && isNight == true) {
				irrigationProject = new IrrigationProject();
				List<IrrigationProject> listentity = dbHelper
						.loadLastMsgBySessionid(units);
				time_compare_start = time_start;
				time_long = 24 - long_hour;
				Date dates = new Date();
				SimpleDateFormat formats = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm");
				try {
					dates = formats.parse(time_start);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				compareDay = dates.getDate();
				//
				if (isNightStartHour == 0) {
					isNightStartHours = 24;
				} else {
					isNightStartHours = isNightStartHour;
				}
				isGreaters = (int) SharedUtils.getParam(getActivity(),
						"isGreaters", 0);
				if (isGreaters == 1) {
					if (dates.getHours() < isNightStartHours
							&& dates.getHours() > isNightEnd) {
						isLegal = 1;
					} else {
						isLegal = 0;
					}
				} else {
					if (dates.getHours() > isNightStartHours
							&& dates.getHours() < isNightEnd) {
						isLegal = 0;
					} else {
						isLegal = 1;
					}
				}

				if (dates.getHours() == isNightStartHour) {
					if (dates.getMinutes() < isNightStartMinute) {
						isLegal = 1;
					} else {
						isLegal = 0;
					}
				}

				if (dates.getHours() == isNightEnd) {
					if (dates.getMinutes() >= isNightEndMinutes) {
						isLegal = 1;
					} else {
						isLegal = 0;
					}
				}
				if (isLegal != 1) {
					Toast.makeText(getActivity(), "非工作区间", Toast.LENGTH_SHORT)
							.show();
				} else {
					List<IrrigationProject> listentity1 = dbHelper
							.loadLastMsgBySessionid(units);
					if (CheckUtil.IsEmpty(listentity1)) {
						now_round = 0;
					} else {
						now_round = Integer.valueOf(listentity1.get(0)
								.getRound());
					}
					now_rounds = now_round;
					now_round1 = now_round;
					round = listentity.size() / MatchedNum + time_number;
					for (int i = 1; i <= MatchedNum * time_number; i++) {
						irrigationProject = new IrrigationProject();
						irrigationProject.setIrrigation(units);
						irrigationProject.setProjectstart("0000-00-00 00:00");
						irrigationProject.setProjectend("0000-00-00 00:00");
						if (a >= MatchedNum + 1) {
							now_rounds = now_rounds + 1;
							a = 1;
						}
						if (Marshalling >= MatchedNum + 1) {
							Marshalling = 1;
						}
						irrigationProject.setRound((now_rounds + 1) + "");
						irrigationProject.setMarshalling(Marshalling + "");
						dbHelper.saveProject(irrigationProject);
						a++;
						Marshalling++;
					}
					a = 1;
					Marshalling = 1;
					int randomCommon[] = randomCommon(1, MatchedNum + 1,
							MatchedNum);
					time_long = 24 - long_hour;
					for (int i = 1; i <= MatchedNum * time_number; i++) {
						Date date = new Date();
						SimpleDateFormat format = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm");
						if (a >= MatchedNum + 1) {
							now_round1 = now_round1 + 1;
							a = 1;
						}
						if (Marshalling >= MatchedNum + 1) {
							Marshalling = 1;
						}
						s++;
						try {
							if (i == 1) {
								date = format.parse(time_start);
								time_starts = format.format(date);
								compareDayf = date.getDate();
								compareMonth = date.getMonth();
								compareYear = date.getYear();
								date.setHours(date.getHours() + nHour);
								date.setMinutes(date.getMinutes() + nMinutes);
								compareDays = date.getDate();
								compareMonths = date.getMonth();
								compareYears = date.getYear();
								if (compareYears > compareYear) {
									conpare = 0;
									if (isNightStartHour != 0) {
										if (date.getHours() == isNightEnd
												&& date.getMinutes() <= isNightEndMinutes) {
											isTrues = 1;
										}
										if (date.getHours() >= isNightStartHour) {
											isGreater = 1;
											if (date.getHours() == isNightStartHour
													&& date.getMinutes() > isNightStartMinute) {
												isTrues = 1;
											} else {
												isTrues = 2;
											}
										} else {
											isGreater = 0;
										}
										if (isGreater == 1) {
											isTrues = 1;
											isTrue = 0;
											isTrue1 = 0;
										} else {
											if (isGreaters == 1) {
												isTrues = 1;
												isTrue = 0;
												isTrue1 = 0;
											} else {
												isTrues = 0;
											}
										}
									} else {
										if (isNightStartHour == 0
												&& date.getMinutes() <= isNightStartMinute) {
											isTrues = 3;
											isTrue1 = 0;
										} else {
											isTrues = 2;
											isTrue1 = 0;
										}
									}
									if (isTrues == 1) {
										if (date.getHours() == isNightStartHour
												&& date.getMinutes() <= isNightStartMinute) {
											conpare = 1;
										} else {
											if (isTrue1 == 0) {
												date.setHours(date.getHours()
														+ isNightContinueHour);
												date.setMinutes(date
														.getMinutes()
														+ isNightContinueMinute);
												isTrues = 0;
												isTrue1 = 1;
												conpare = 1;
											}
										}
									} else if (isTrues == 2) {
										if (date.getHours() == isNightStartHour
												&& date.getMinutes() <= isNightStartMinute) {
											conpare = 1;
										} else {
											if (isTrue1 == 0) {
												date.setHours(date.getHours()
														+ isNightContinueHour);
												date.setMinutes(date
														.getMinutes()
														+ isNightContinueMinute);
												isTrues = 0;
												isTrue1 = 1;
												conpare = 1;
											}
										}
									} else if (isTrues == 3) {
										if (date.getHours() == isNightStartHour
												&& date.getMinutes() <= isNightStartMinute) {

										} else {
											if (isTrue1 == 0) {
												date.setHours(date.getHours()
														+ isNightContinueHour);
												date.setMinutes(date
														.getMinutes()
														+ isNightContinueMinute);
												isTrues = 0;
												isTrue1 = 1;
												conpare = 1;
											}
										}
									}
								} else if (compareMonths > compareMonth) {
									conpare = 0;
									if (isNightStartHour != 0) {
										if (date.getHours() == isNightEnd
												&& date.getMinutes() <= isNightEndMinutes) {
											isTrues = 1;
										}
										if (date.getHours() >= isNightStartHour) {
											isGreater = 1;
											if (date.getHours() == isNightStartHour
													&& date.getMinutes() > isNightStartMinute) {
												isTrues = 1;
											} else {
												isTrues = 2;
											}
										} else {
											isGreater = 0;
										}
										if (isGreater == 1) {
											isTrues = 1;
											isTrue = 0;
											isTrue1 = 0;
										} else {
											if (isGreaters == 1) {
												isTrues = 1;
												isTrue = 0;
												isTrue1 = 0;
											} else {
												isTrues = 0;
											}
										}
									} else {
										if (isNightStartHour == 0
												&& date.getMinutes() <= isNightStartMinute) {
											isTrues = 3;
											isTrue1 = 0;
										} else {
											isTrues = 2;
											isTrue1 = 0;
										}
									}
									if (isTrues == 1) {
										if (date.getHours() == isNightStartHour
												&& date.getMinutes() <= isNightStartMinute) {
											conpare = 1;
										} else {
											if (isTrue1 == 0) {
												date.setHours(date.getHours()
														+ isNightContinueHour);
												date.setMinutes(date
														.getMinutes()
														+ isNightContinueMinute);
												isTrues = 0;
												isTrue1 = 1;
												conpare = 1;
											}
										}
									} else if (isTrues == 2) {
										if (date.getHours() == isNightStartHour
												&& date.getMinutes() <= isNightStartMinute) {
											conpare = 1;
										} else {
											if (isTrue1 == 0) {
												date.setHours(date.getHours()
														+ isNightContinueHour);
												date.setMinutes(date
														.getMinutes()
														+ isNightContinueMinute);
												isTrues = 0;
												isTrue1 = 1;
												conpare = 1;
											}
										}
									} else if (isTrues == 3) {
										if (date.getHours() == isNightStartHour
												&& date.getMinutes() <= isNightStartMinute) {

										} else {
											if (isTrue1 == 0) {
												date.setHours(date.getHours()
														+ isNightContinueHour);
												date.setMinutes(date
														.getMinutes()
														+ isNightContinueMinute);
												isTrues = 0;
												isTrue1 = 1;
												conpare = 1;
											}
										}
									}
								} else if (compareDays > compareDayf) {
									conpare = 0;
									if (isNightStartHour != 0) {
										if (date.getHours() == isNightEnd
												&& date.getMinutes() <= isNightEndMinutes) {
											isTrues = 1;
										}
										if (date.getHours() >= isNightStartHour) {
											isGreater = 1;
											if (date.getHours() == isNightStartHour
													&& date.getMinutes() > isNightStartMinute) {
												isTrues = 1;
											} else {
												isTrues = 2;
											}
										} else {
											isGreater = 0;
										}
										if (isGreater == 1) {
											isTrues = 1;
											isTrue = 0;
											isTrue1 = 0;
										} else {
											if (isGreaters == 1) {
												isTrues = 1;
												isTrue = 0;
												isTrue1 = 0;
											} else {
												isTrues = 0;
											}
										}
									} else {
										if (isNightStartHour == 0
												&& date.getMinutes() <= isNightStartMinute) {
											isTrues = 3;
											isTrue1 = 0;
										} else {
											isTrues = 2;
											isTrue1 = 0;
										}
									}
									if (isTrues == 1) {
										if (date.getHours() == isNightStartHour
												&& date.getMinutes() <= isNightStartMinute) {
											conpare = 1;
										} else {
											if (isTrue1 == 0) {
												date.setHours(date.getHours()
														+ isNightContinueHour);
												date.setMinutes(date
														.getMinutes()
														+ isNightContinueMinute);
												isTrues = 0;
												isTrue1 = 1;
												conpare = 1;
											}
										}
									} else if (isTrues == 2) {
										if (date.getHours() == isNightStartHour
												&& date.getMinutes() <= isNightStartMinute) {
											conpare = 1;
										} else {
											if (isTrue1 == 0) {
												date.setHours(date.getHours()
														+ isNightContinueHour);
												date.setMinutes(date
														.getMinutes()
														+ isNightContinueMinute);
												isTrues = 0;
												isTrue1 = 1;
												conpare = 1;
											}
										}
									} else if (isTrues == 3) {
										if (date.getHours() == isNightStartHour
												&& date.getMinutes() <= isNightStartMinute) {

										} else {
											if (isTrue1 == 0) {
												date.setHours(date.getHours()
														+ isNightContinueHour);
												date.setMinutes(date
														.getMinutes()
														+ isNightContinueMinute);
												isTrues = 0;
												isTrue1 = 1;
												conpare = 1;
											}
										}
									}
								} else {
									if (isNightStartHour != 0) {
										if (date.getHours() == isNightEnd
												&& date.getMinutes() <= isNightEndMinutes) {
											isTrues = 1;
										}
										if (date.getHours() >= isNightStartHour) {
											isGreater = 1;
											if (date.getHours() == isNightStartHour
													&& date.getMinutes() > isNightStartMinute) {
												isTrues = 1;
											} else {
												isTrues = 2;
											}
										} else {
											isGreater = 0;
										}
										if (isGreater == 1) {
											if (conpare == 0) {
												isTrues = 1;
												isTrue1 = 0;
											}
										} else {
											isTrues = 0;
										}
									} else {
										if (isNightStartHour == 0
												&& date.getMinutes() <= isNightStartMinute) {
											isTrues = 0;
										} else {
											isTrues = 2;
											isTrue1 = 0;
										}
										isNightStartHours = 24;
										if (isNightStartHours - date.getHours() == 0) {
											isTrues = 2;
											isTrue1 = 0;
										} else {
											isTrues = 0;
										}
									}
									Date dateStart = new Date();
									SimpleDateFormat formatStart = new SimpleDateFormat(
											"yyyy-MM-dd HH:mm");
									dateStart = format.parse(time_start);
									if (dateStart.getHours() > isNightStartHour) {
										isTrue1 = 1;
									} else {
										isTrue1 = 0;
									}

									if (isTrues == 1) {
										if (date.getHours() == isNightStartHour
												&& date.getMinutes() <= isNightStartMinute) {
											conpare = 1;
										} else {
											if (isTrue1 == 0) {
												date.setHours(date.getHours()
														+ isNightContinueHour);
												date.setMinutes(date
														.getMinutes()
														+ isNightContinueMinute);
												isTrues = 0;
												isTrue1 = 1;
											}
										}
									} else if (isTrues == 2) {
										if (date.getHours() == isNightStartHour
												&& date.getMinutes() <= isNightStartMinute) {
											conpare = 1;
										} else {
											if (isTrue1 == 0) {
												date.setHours(date.getHours()
														+ isNightContinueHour);
												date.setMinutes(date
														.getMinutes()
														+ isNightContinueMinute);
												isTrues = 0;
												isTrue1 = 1;
											}
										}
									} else if (isTrues == 3) {
										if (date.getHours() == isNightStartHour
												&& date.getMinutes() <= isNightStartMinute) {

										} else {
											if (isTrue1 == 0) {
												date.setHours(date.getHours()
														+ isNightContinueHour);
												date.setMinutes(date
														.getMinutes()
														+ isNightContinueMinute);
												isTrues = 0;
												isTrue1 = 1;
											}
										}
									}
								}
							} else {
								date = format.parse(time_end);
								if (s > MatchedNum) {
									date.setDate(date.getDate() + 10);
									s = 1;
								}
								if (isTrues == 1) {
									if (isTrue1 == 0) {
										date.setHours(date.getHours()
												+ isNightContinueHour);
										date.setMinutes(date.getMinutes()
												+ isNightContinueMinute);
										isTrues = 0;
										isTrue1 = 1;
									}
									isTrues = 0;
									conpare = 1;
								} else if (isTrues == 2) {
									if (isTrue1 == 0) {
										date.setHours(date.getHours()
												+ isNightContinueHour);
										date.setMinutes(date.getMinutes()
												+ isNightContinueMinute);
										isTrues = 0;
										isTrue1 = 1;
									}
									isTrues = 0;
									conpare = 1;
								} else if (isTrues == 3) {
									if (isTrue1 == 0) {
										date.setHours(date.getHours()
												+ isNightContinueHour);
										date.setMinutes(date.getMinutes()
												+ isNightContinueMinute);
										isTrues = 0;
										isTrue1 = 1;
									}
									isTrues = 0;
									conpare = 1;
								}
								time_starts = format.format(date);
								compareDayf = date.getDate();
								compareMonth = date.getMonth();
								compareYear = date.getYear();
								date.setHours(date.getHours() + nHour);
								date.setMinutes(date.getMinutes() + nMinutes);
								compareDays = date.getDate();
								compareMonths = date.getMonth();
								compareYears = date.getYear();
								if (compareYears > compareYear) {
									conpare = 0;
									isGreater1 = 0;
									if (isNightStartHour != 0) {
										if (date.getHours() == isNightEnd
												&& date.getMinutes() <= isNightEndMinutes) {
											isTrues = 1;
										}
										if (date.getHours() >= isNightStartHour) {
											isGreater = 1;
											if (date.getHours() == isNightStartHour
													&& date.getMinutes() > isNightStartMinute) {
												isTrues = 1;
											} else {
												isTrues = 2;
											}
										} else {
											isGreater = 0;
										}
										if (isGreater == 1) {
											if (conpare == 0) {
												isTrues = 1;
												isTrue = 0;
												isTrue1 = 0;
											}
										} else {
											if (isGreaters == 1) {
												isTrues = 1;
												isTrue = 0;
												isTrue1 = 0;
											} else {
												isTrues = 0;
											}

										}
									} else {
										if (isNightStartHour == 0
												&& date.getMinutes() <= isNightStartMinute) {
											isTrues = 3;
											isTrue1 = 0;
										} else {
											isTrues = 2;
											isTrue1 = 0;
										}
									}
									if (isTrues == 1) {
										if (date.getHours() == isNightStartHour
												&& date.getMinutes() <= isNightStartMinute) {
											compare = 1;
										} else {
											if (isTrue1 == 0) {
												date.setHours(date.getHours()
														+ isNightContinueHour);
												date.setMinutes(date
														.getMinutes()
														+ isNightContinueMinute);
												isTrues = 0;
												isTrue1 = 1;
												conpare = 1;
											}
										}
									} else if (isTrues == 2) {
										if (date.getHours() == isNightStartHour
												&& date.getMinutes() <= isNightStartMinute) {
											compare = 1;
										} else {
											if (isTrue1 == 0) {
												date.setHours(date.getHours()
														+ isNightContinueHour);
												date.setMinutes(date
														.getMinutes()
														+ isNightContinueMinute);
												isTrues = 0;
												isTrue1 = 1;
												conpare = 1;
											}
										}
									} else if (isTrues == 3) {
										if (date.getHours() == isNightStartHour
												&& date.getMinutes() <= isNightStartMinute) {

										} else {
											if (isTrue1 == 0) {
												date.setHours(date.getHours()
														+ isNightContinueHour);
												date.setMinutes(date
														.getMinutes()
														+ isNightContinueMinute);
												isTrues = 0;
												isTrue1 = 1;
												conpare = 1;
											}
										}
									}
								} else if (compareMonths > compareMonth) {
									conpare = 0;
									isGreater1 = 0;
									if (isNightStartHour != 0) {
										if (date.getHours() == isNightEnd
												&& date.getMinutes() <= isNightEndMinutes) {
											isTrues = 1;
										}
										if (date.getHours() >= isNightStartHour) {
											isGreater = 1;
											if (date.getHours() == isNightStartHour
													&& date.getMinutes() > isNightStartMinute) {
												isTrues = 1;
											} else {
												isTrues = 2;
											}
										} else {
											isGreater = 0;
										}
										if (isGreater == 1) {
											if (conpare == 0) {
												isTrues = 1;
												isTrue = 0;
												isTrue1 = 0;
											}
										} else {
											if (isGreaters == 1) {
												isTrues = 1;
												isTrue = 0;
												isTrue1 = 0;
											} else {
												isTrues = 0;
											}

										}
									} else {
										if (isNightStartHour == 0
												&& date.getMinutes() <= isNightStartMinute) {
											isTrues = 3;
											isTrue1 = 0;
										} else {
											isTrues = 2;
											isTrue1 = 0;
										}
									}
									if (isTrues == 1) {
										if (date.getHours() == isNightStartHour
												&& date.getMinutes() <= isNightStartMinute) {
											compare = 1;
										} else {
											if (isTrue1 == 0) {
												date.setHours(date.getHours()
														+ isNightContinueHour);
												date.setMinutes(date
														.getMinutes()
														+ isNightContinueMinute);
												isTrues = 0;
												isTrue1 = 1;
												conpare = 1;
											}
										}
									} else if (isTrues == 2) {
										if (date.getHours() == isNightStartHour
												&& date.getMinutes() <= isNightStartMinute) {
											compare = 1;
										} else {
											if (isTrue1 == 0) {
												date.setHours(date.getHours()
														+ isNightContinueHour);
												date.setMinutes(date
														.getMinutes()
														+ isNightContinueMinute);
												isTrues = 0;
												isTrue1 = 1;
												conpare = 1;
											}
										}
									} else if (isTrues == 3) {
										if (date.getHours() == isNightStartHour
												&& date.getMinutes() <= isNightStartMinute) {

										} else {
											if (isTrue1 == 0) {
												date.setHours(date.getHours()
														+ isNightContinueHour);
												date.setMinutes(date
														.getMinutes()
														+ isNightContinueMinute);
												isTrues = 0;
												isTrue1 = 1;
												conpare = 1;
											}
										}
									}
								} else if (compareDays > compareDayf) {
									conpare = 0;
									isGreater1 = 0;
									if (isNightStartHour != 0) {
										if (date.getHours() == isNightEnd
												&& date.getMinutes() <= isNightEndMinutes) {
											isTrues = 1;
										}
										if (date.getHours() >= isNightStartHour) {
											isGreater = 1;
											if (date.getHours() == isNightStartHour
													&& date.getMinutes() > isNightStartMinute) {
												isTrues = 1;
											} else {
												isTrues = 2;
											}
										} else {
											isGreater = 0;
										}
										if (isGreater == 1) {
											if (conpare == 0) {
												isTrues = 1;
												isTrue = 0;
												isTrue1 = 0;
											}
										} else {
											if (isGreaters == 1) {
												isTrues = 1;
												isTrue = 0;
												isTrue1 = 0;
											} else {
												isTrues = 0;
											}

										}
									} else {
										if (isNightStartHour == 0
												&& date.getMinutes() <= isNightStartMinute) {
											isTrues = 3;
											isTrue1 = 0;
										} else {
											isTrues = 2;
											isTrue1 = 0;
										}
									}
									if (isTrues == 1) {
										if (date.getHours() == isNightStartHour
												&& date.getMinutes() <= isNightStartMinute) {
											compare = 1;
										} else {
											if (isTrue1 == 0) {
												date.setHours(date.getHours()
														+ isNightContinueHour);
												date.setMinutes(date
														.getMinutes()
														+ isNightContinueMinute);
												isTrues = 0;
												isTrue1 = 1;
												conpare = 1;
											}
										}
									} else if (isTrues == 2) {
										if (date.getHours() == isNightStartHour
												&& date.getMinutes() <= isNightStartMinute) {
											compare = 1;
										} else {
											if (isTrue1 == 0) {
												date.setHours(date.getHours()
														+ isNightContinueHour);
												date.setMinutes(date
														.getMinutes()
														+ isNightContinueMinute);
												isTrues = 0;
												isTrue1 = 1;
												conpare = 1;
											}
										}
									} else if (isTrues == 3) {
										if (date.getHours() == isNightStartHour
												&& date.getMinutes() <= isNightStartMinute) {

										} else {
											if (isTrue1 == 0) {
												date.setHours(date.getHours()
														+ isNightContinueHour);
												date.setMinutes(date
														.getMinutes()
														+ isNightContinueMinute);
												isTrues = 0;
												isTrue1 = 1;
												conpare = 1;
											}
										}
									}
								} else {
									if (isNightStartHour != 0) {
										if (date.getHours() == isNightEnd
												&& date.getMinutes() <= isNightEndMinutes) {
											isTrues = 1;
										}
										if (date.getHours() >= isNightStartHour) {
											isGreater = 1;
											if (isGreaters == 1) {
												conpare = 0;
												isGreater1 = 0;
											}
											if (date.getHours() == isNightStartHour
													&& date.getMinutes() > isNightStartMinute) {
												isTrues = 1;
											} else {
												if (isGreater1 == 0) {
													isTrues = 2;
													if (conpare == 0) {
														isTrue1 = 0;
														isGreater1 = 1;
													}
												} else {
													isTrues = 2;
													isTrue1 = 1;
												}
											}
										} else {
											isGreater = 0;
										}
										if (isGreater == 1) {
											if (conpare == 0) {
												isTrues = 1;
												isTrue1 = 0;
											}
										} else {
											isTrues = 0;
										}
									} else {
										if (isNightStartHour == 0
												&& date.getMinutes() <= isNightStartMinute) {
											isTrues = 0;
										} else {
											isTrues = 2;
										}
										isNightStartHours = 24;
										if (isNightStartHours - date.getHours() == 0) {
											isTrues = 2;
										} else {
											isTrues = 0;
										}
									}
									if (isTrues == 1) {
										if (date.getHours() == isNightStartHour
												&& date.getMinutes() <= isNightStartMinute) {
											conpare = 1;
										} else {
											if (isTrue1 == 0) {
												date.setHours(date.getHours()
														+ isNightContinueHour);
												date.setMinutes(date
														.getMinutes()
														+ isNightContinueMinute);
												isTrues = 0;
												isTrue1 = 1;
												conpare = 1;
											}
										}
									} else if (isTrues == 2) {
										if (date.getHours() == isNightStartHour
												&& date.getMinutes() <= isNightStartMinute) {
											conpare = 1;
										} else {
											if (isTrue1 == 0) {
												date.setHours(date.getHours()
														+ isNightContinueHour);
												date.setMinutes(date
														.getMinutes()
														+ isNightContinueMinute);
												isTrues = 0;
												isTrue1 = 1;
												conpare = 1;
											}
										}
									} else if (isTrues == 3) {
										if (date.getHours() == isNightStartHour
												&& date.getMinutes() <= isNightStartMinute) {

										} else {
											if (isTrue1 == 0) {
												date.setHours(date.getHours()
														+ isNightContinueHour);
												date.setMinutes(date
														.getMinutes()
														+ isNightContinueMinute);
												isTrues = 0;
												isTrue1 = 1;
												conpare = 1;
											}
										}
									}
								}
							}
							time_end = format.format(date);
							if (CheckUtil.IsEmpty(listentity)) {
								now_round = 0;
							}
							if (isRandom == true) {
								dbHelper.updateProjects(units, now_round1 + 1
										+ "", randomCommon[i], time_starts,
										time_end);
							} else {
								dbHelper.updateProjects(units, now_round1 + 1
										+ "", Marshalling, time_starts,
										time_end);
							}
							a++;
							Marshalling++;
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
					notifys = 1;
					SharedUtils.setParam(getActivity(), "notifys", notifys);
					ApplyIrrigateProjectFragment fragment3 = new ApplyIrrigateProjectFragment();
					Bundle bundle1 = new Bundle();
					bundle1.putString("units", units);
					fragment3.setArguments(bundle1);
					transaction
							.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
					transaction.replace(R.id.fl, fragment3, "main");
					transaction.commit();
					firsts = dbHelper.loadisFirst(units);
					if (CheckUtil.IsEmpty(firsts)) {
						irrigationIsFirst.setIrrigation(units);
						irrigationIsFirst.setIsFirst(0);
						dbHelper.saveIsFirst(irrigationIsFirst);
					}
					 }
			} else if (isLong == false && isNight == false) {
				irrigationProject = new IrrigationProject();
				List<IrrigationProject> listentity = dbHelper
						.loadLastMsgBySessionid(units);
				time_compare_start = time_start;
				time_long = 24 - long_hour;
				Date dates = new Date();
				SimpleDateFormat formats = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm");
				try {
					dates = formats.parse(time_start);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				List<IrrigationProject> listentity1 = dbHelper
						.loadLastMsgBySessionid(units);
				if (CheckUtil.IsEmpty(listentity1)) {
					now_round = 0;
				} else {
					now_round = Integer.valueOf(listentity1.get(0).getRound());
				}
				now_rounds = now_round;
				now_round1 = now_round;
				round = listentity.size() / MatchedNum + time_number;
				for (int i = 1; i <= MatchedNum * time_number; i++) {
					irrigationProject = new IrrigationProject();
					irrigationProject.setIrrigation(units);
					irrigationProject.setProjectstart("0000-00-00 00:00");
					irrigationProject.setProjectend("0000-00-00 00:00");
					if (a >= MatchedNum + 1) {
						now_rounds = now_rounds + 1;
						a = 1;
					}
					if (Marshalling >= MatchedNum + 1) {
						Marshalling = 1;
					}
					irrigationProject.setRound((now_rounds + 1) + "");
					irrigationProject.setMarshalling(Marshalling + "");
					dbHelper.saveProject(irrigationProject);
					a++;
					Marshalling++;
				}
				a = 1;
				Marshalling = 1;
				int randomCommon[] = randomCommon(1, MatchedNum + 1, MatchedNum);
				time_long = 24 - long_hour;
				for (int i = 1; i <= MatchedNum * time_number; i++) {
					Date date = new Date();
					SimpleDateFormat format = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm");
					if (a >= MatchedNum + 1) {
						now_round1 = now_round1 + 1;
						a = 1;
					}
					if (Marshalling >= MatchedNum + 1) {
						Marshalling = 1;
					}
					s++;
					try {
						if (i == 1) {
							date = format.parse(time_start);
							time_starts = format.format(date);
							date.setHours(date.getHours() + nHour);
							date.setMinutes(date.getMinutes() + nMinutes);
						} else {
							date = format.parse(time_end);
							if (s > MatchedNum) {
								date.setDate(date.getDate() + 10);
								s = 1;
							}
							time_starts = format.format(date);
							date.setHours(date.getHours() + nHour);
							date.setMinutes(date.getMinutes() + nMinutes);
						}
						time_end = format.format(date);
						if (isRandom == true) {
							dbHelper.updateProjects(units, now_round1 + 1 + "",
									randomCommon[i], time_starts, time_end);
						} else {
							dbHelper.updateProjects(units, now_round1 + 1 + "",
									Marshalling, time_starts, time_end);
						}
						a++;
						Marshalling++;
					} catch (ParseException e) {
						e.printStackTrace();
						// }
					}
				}
				notifys = 1;
				SharedUtils.setParam(getActivity(), "notifys", notifys);
				ApplyIrrigateProjectFragment fragment3 = new ApplyIrrigateProjectFragment();
				Bundle bundle1 = new Bundle();
				bundle1.putString("units", units);
				fragment3.setArguments(bundle1);
				transaction
						.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
				transaction.replace(R.id.fl, fragment3, "main");
				transaction.commit();
				firsts = dbHelper.loadisFirst(units);
				if (CheckUtil.IsEmpty(firsts)) {
					irrigationIsFirst.setIrrigation(units);
					irrigationIsFirst.setIsFirst(0);
					dbHelper.saveIsFirst(irrigationIsFirst);
				}
			}
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch (buttonView.getId()) {
		case R.id.toggle_apply_irriagte_project_season_time_night:// 规律
			Toast.makeText(getActivity(), "开关--" + isChecked,
					Toast.LENGTH_SHORT).show();
			SharedUtils.setParam(getActivity(), "Night", isChecked);
			if (isChecked) {
				isNight = true;
				if ((CheckUtil.IsEmpty(isNightStartHour) || isNightStartHour == 0)
						&& (CheckUtil.IsEmpty(isNightEnd) || isNightEnd == 0)) {
					setNight = 2;
					SharedUtils.setParam(getActivity(), "setNight", setNight);
					FragmentManager fgManager = getFragmentManager();
					FragmentTransaction transaction = fgManager
							.beginTransaction();
					MainTainBasicInfoFragment fragment = new MainTainBasicInfoFragment();
					// transaction.setCustomAnimations(R.anim.right_in,
					// R.anim.right_out);
					Bundle bundle = new Bundle();
					bundle.putString("units", units);
					fragment.setArguments(bundle);
					transaction.setCustomAnimations(R.anim.slide_fragment_horizontal_left_in, R.anim.slide_fragment_horizontal_right_out);
					transaction.replace(R.id.fl, fragment, "main");
					transaction.commit();
				}
			} else {
				isNight = false;
			}
			break;
		case R.id.toggle_apply_irriagte_project_season_time_random:// 规律
			Toast.makeText(getActivity(), "开关--" + isChecked,
					Toast.LENGTH_SHORT).show();
			SharedUtils.setParam(getActivity(), "Random", isChecked);
			if (isChecked) {
				isRandom = true;
			} else {
				isRandom = false;
			}
			break;
		case R.id.toggle_apply_irriagte_project_season_time_longs:// 规律
			Toast.makeText(getActivity(), "开关--" + isChecked,
					Toast.LENGTH_SHORT).show();
			SharedUtils.setParam(getActivity(), "Long", isChecked);
			if (isChecked) {
				isLong = true;
				if (CheckUtil.IsEmpty(long_hour) || long_hour == 0) {
					setLong = 2;
					SharedUtils.setParam(getActivity(), "setLong", setLong);
					FragmentManager fgManager = getFragmentManager();
					FragmentTransaction transaction = fgManager
							.beginTransaction();
					MainTainBasicInfoFragment fragment = new MainTainBasicInfoFragment();
					// transaction.setCustomAnimations(R.anim.right_in,
					// R.anim.right_out);
					Bundle bundle = new Bundle();
					bundle.putString("units", units);
					fragment.setArguments(bundle);
					transaction.setCustomAnimations(R.anim.slide_fragment_horizontal_left_in, R.anim.slide_fragment_horizontal_right_out);
					transaction.replace(R.id.fl, fragment, "main");
					transaction.commit();
				}

			} else {
				isLong = false;
			}
			break;
		}
	}

	/**
	 * @Description: TODO 弹出日期时间选择器
	 */
	private void showDateTimePicker(LayoutInflater inflater) {
		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);

		dialog = new Dialog(getActivity());
		dialog.setTitle("请选择");
		// 找到dialog的布局文件
		mInflater = inflater;
		View view = inflater.inflate(R.layout.chose_times, null);

		// 时
		final WheelView wv_hours = (WheelView) view.findViewById(R.id.hour);
		wv_hours.setAdapter(new NumericWheelAdapter(0, 23));
		wv_hours.setCyclic(true);
		wv_hours.setCurrentItem(hour);

		Button btn_sure = (Button) view.findViewById(R.id.chose_time_sures);
		Button btn_cancel = (Button) view.findViewById(R.id.chose_time_canles);
		// 确定
		btn_sure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (id == 1) {

				} else if (id == 3) {
					tv_time_number.setText(wv_hours.getCurrentItem() + "次");
					time_number = wv_hours.getCurrentItem();
					dbHelper.updateContinueNum(units, time_number);
				} else if (id == 4) {
					tv_time_interval.setText(wv_hours.getCurrentItem() + "天");
					time_intervals = wv_hours.getCurrentItem();
					dbHelper.updateContinueRound(units, time_intervals);
				}
				// TODO Auto-generated method stub
				// 如果是个数,则显示为"02"的样式
				// 设置日期的显示
				// tv_time.setText((wv_year.getCurrentItem() + START_YEAR) + "-"
				// + decimal.format((wv_month.getCurrentItem() + 1)) + "-"
				// + decimal.format((wv_day.getCurrentItem() + 1)) + " "
				// + decimal.format(wv_hours.getCurrentItem()) + ":"
				// + decimal.format(wv_minute.getCurrentItem()));

				dialog.dismiss();
			}
		});
		// 取消
		btn_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		// 设置dialog的布局,并显示
		dialog.setContentView(view);
		dialog.show();
	}

	/**
	 * @Description: TODO 弹出日期时间选择器
	 */

	@Override
	public void onResume() {
		super.onResume();
		isLong = (Boolean) SharedUtils.getParam(getActivity(), "Long", false);
		isNight = (Boolean) SharedUtils.getParam(getActivity(), "Night", false);

		if (CheckUtil.IsEmpty(long_hour) || long_hour == 0) {
			isLong = false;
		}
		if ((CheckUtil.IsEmpty(isNightStartHour) || isNightStartHour == 0)
				&& (CheckUtil.IsEmpty(isNightEnd) || isNightEnd == 0)) {
			isNight = false;
		}

		isTrue = 0;
		isTrue1 = 0;

		time_night.setChecked(isNight);
		time_random.setChecked(isRandom);
		time_longs.setChecked(isLong);

		new Thread(new Runnable() {

			@Override
			public void run() {
				List<IrrigationProject> listentity = dbHelper
						.loadLastMsgBySessionids(units);
				if (!CheckUtil.IsEmpty(listentity)) {
					Calendar c = Calendar.getInstance();
					Calendar c1 = Calendar.getInstance();
					try {
						c.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm")
								.parse(listentity.get(0).getProjectend()));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					compares = c.getTimeInMillis();
					for (int j = 0; j < listentity.size(); j++) {
						try {
							c1.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm")
									.parse(listentity.get(j).getProjectend()));
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
						}
					}
				}else {
					Date date = new Date(System.currentTimeMillis());
					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm");
					time_start = sdf.format(date);
					time_max_start = time_start;
				}
				if (!CheckUtil.IsEmpty(isFront) && isFront != 0) {
					time_start = (String) SharedUtils.getParam(getActivity(),
							"time_start", "0000-00-00 00:00");
					time_max_start = time_start;
				} 
				Message message = new Message();
				message.what = 100;
				handler.sendMessage(message);
			}
		}).start();
	}

	private void showDateTimePickers(LayoutInflater inflater) {
		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);

		dialog = new Dialog(getActivity());
		dialog.setTitle("请选择");
		// 找到dialog的布局文件
		mInflater = inflater;
		View view = inflater.inflate(R.layout.time_filter, null);

		// 时
		final WheelView wv_hours = (WheelView) view
				.findViewById(R.id.hour_filter);
		wv_hours.setAdapter(new NumericWheelAdapter(0, 23));
		wv_hours.setCyclic(true);
		wv_hours.setCurrentItem(hour);

		// 分
		final WheelView wv_minute = (WheelView) view
				.findViewById(R.id.minute_filter);
		wv_minute.setAdapter(new NumericWheelAdapter(0, 59));
		wv_minute.setCyclic(true);
		wv_minute.setCurrentItem(hour);

		Button btn_sure = (Button) view.findViewById(R.id.time_sures_filter);
		Button btn_cancel = (Button) view.findViewById(R.id.time_canles_filter);
		// 确定
		btn_sure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// tv_time_continue.setText(wv_hours.getCurrentItem()+"小时"+wv_minute.getCurrentItem()+"分钟");
				if (wv_hours.getCurrentItem() != 0
						&& wv_minute.getCurrentItem() == 0) {
					nHour = wv_hours.getCurrentItem();
					nMinutes = wv_minute.getCurrentItem();
					tv_time_continue.setText(wv_hours.getCurrentItem() + "小时");
					dbHelper.updateContinue(units, nHour, nMinutes);
					// SharedUtils.setParam(getActivity(), "nHour", nHour);
					// SharedUtils.setParam(getActivity(), "nMinutes",
					// nMinutes);
				} else if (wv_hours.getCurrentItem() == 0
						&& wv_minute.getCurrentItem() != 0) {
					nHour = wv_hours.getCurrentItem();
					nMinutes = wv_minute.getCurrentItem();
					tv_time_continue.setText(wv_minute.getCurrentItem() + "分钟");
					dbHelper.updateContinue(units, nHour, nMinutes);
				} else {
					tv_time_continue.setText(wv_hours.getCurrentItem() + "小时"
							+ wv_minute.getCurrentItem() + "分钟");
					nHour = wv_hours.getCurrentItem();
					nMinutes = wv_minute.getCurrentItem();
					dbHelper.updateContinue(units, nHour, nMinutes);
					// SharedUtils.setParam(getActivity(), "nHour", nHour);
					// SharedUtils.setParam(getActivity(), "nMinutes",
					// nMinutes);
				}
				// TODO Auto-generated method stub
				// 如果是个数,则显示为"02"的样式
				// 设置日期的显示
				// tv_time.setText((wv_year.getCurrentItem() + START_YEAR) + "-"
				// + decimal.format((wv_month.getCurrentItem() + 1)) + "-"
				// + decimal.format((wv_day.getCurrentItem() + 1)) + " "
				// + decimal.format(wv_hours.getCurrentItem()) + ":"
				// + decimal.format(wv_minute.getCurrentItem()));

				dialog.dismiss();
			}
		});
		// 取消
		btn_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		// 设置dialog的布局,并显示
		dialog.setContentView(view);
		dialog.show();
	}

	/**
	 * @Description: TODO 弹出日期时间选择器
	 */
	private void showDateTimePicker1(LayoutInflater inflater) {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DATE);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);

		dialog = new Dialog(getActivity());
		dialog.setTitle("请选择时间");
		// 找到dialog的布局文件
		mInflater = inflater;
		View view = inflater.inflate(R.layout.chose_time, null);

		String[] months_big = { "1", "3", "5", "7", "8", "10", "12" };
		String[] months_little = { "4", "6", "9", "11" };

		final List<String> list_big = Arrays.asList(months_big);
		final List<String> list_little = Arrays.asList(months_little);

		current_year = (WheelView) view.findViewById(R.id.chose_year);
		current_year.setAdapter(new NumbericWheelAdapter(2016, 2036));
		current_year.setCyclic(true);
		current_year.setLabel("年");// 添加文字
		current_year.setCurrentItem(year - 2016);

		current_month = (WheelView) view.findViewById(R.id.chose_month);
		current_month.setAdapter(new NumbericWheelAdapter(1, 12));
		current_month.setCyclic(true);
		current_month.setLabel("月");// 添加文字
		current_month.setCurrentItem(month);

		current_hour = (WheelView) view.findViewById(R.id.chose_hour);
		current_hour.setAdapter(new NumbericWheelAdapter(0, 23));
		current_hour.setCyclic(true);
		current_hour.setLabel("时");// 添加文字

		current_day = (WheelView) view.findViewById(R.id.chose_day);
		current_day.setCyclic(true);
		current_day.setLabel("日");
		// 判断大小月及是否闰年,用来确定"日"的数据
		if (list_big.contains(String.valueOf(month + 1))) {
			current_day.setAdapter(new NumbericWheelAdapter(1, 31));
		} else if (list_little.contains(String.valueOf(month + 1))) {
			current_day.setAdapter(new NumbericWheelAdapter(1, 30));
		} else {
			// 闰年
			if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
				current_day.setAdapter(new NumbericWheelAdapter(1, 29));
			else
				current_day.setAdapter(new NumbericWheelAdapter(1, 28));
		}
		current_day.setCurrentItem(day - 1);

		current_minute = (WheelView) view.findViewById(R.id.chose_minute);
		current_minute.setAdapter(new NumbericWheelAdapter(0, 59));
		current_minute.setCyclic(true);
		current_minute.setLabel("分");

		current_hour.setCurrentItem(hour);
		current_minute.setCurrentItem(minute);

		/*
		 * addChangingListener(hours, "H"); addChangingListener(minutes, "M");
		 */

		// 添加"年"监听
		OnWheelChangedListener wheelListener_year = new OnWheelChangedListener() {
			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				int year_num = newValue + 2016;
				// 判断大小月及是否闰年,用来确定"日"的数据
				if (list_big.contains(String.valueOf(current_month
						.getCurrentItem() + 1))) {
					current_day.setAdapter(new NumbericWheelAdapter(1, 31));
				} else if (list_little.contains(String.valueOf(current_month
						.getCurrentItem() + 1))) {
					current_day.setAdapter(new NumbericWheelAdapter(1, 30));
				} else {
					if ((year_num % 4 == 0 && year_num % 100 != 0)
							|| year_num % 400 == 0)
						current_day.setAdapter(new NumbericWheelAdapter(1, 29));
					else
						current_day.setAdapter(new NumbericWheelAdapter(1, 28));
				}
			}
		};
		// 添加"月"监听
		OnWheelChangedListener wheelListener_month = new OnWheelChangedListener() {
			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				int month_num = newValue + 1;
				// 判断大小月及是否闰年,用来确定"日"的数据
				if (list_big.contains(String.valueOf(month_num))) {
					current_day.setAdapter(new NumbericWheelAdapter(1, 31));
				} else if (list_little.contains(String.valueOf(month_num))) {
					current_day.setAdapter(new NumbericWheelAdapter(1, 30));
				} else {
					if (((current_year.getCurrentItem() + 2016) % 4 == 0 && (current_year
							.getCurrentItem() + 2016) % 100 != 0)
							|| (current_year.getCurrentItem() + 2016) % 400 == 0)
						current_day.setAdapter(new NumbericWheelAdapter(1, 29));
					else
						current_day.setAdapter(new NumbericWheelAdapter(1, 28));
				}
			}
		};
		current_year.addChangingListener(wheelListener_year);
		current_month.addChangingListener(wheelListener_month);

		Button btn_sure = (Button) view.findViewById(R.id.chose_time_sure);
		Button btn_cancel = (Button) view.findViewById(R.id.chose_time_canle);
		// 确定
		btn_sure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String parten = "00";
				DecimalFormat decimal = new DecimalFormat(parten);

				String a = (current_year.getCurrentItem() + 2016) + "-"
						+ decimal.format((current_month.getCurrentItem() + 1))
						+ "-"
						+ decimal.format((current_day.getCurrentItem() + 1))
						+ " " + decimal.format(current_hour.getCurrentItem())
						+ ":" + decimal.format(current_minute.getCurrentItem());
				
				Calendar c = Calendar.getInstance();
				int nowYear = c.get(Calendar.YEAR);
				int nowMonth = c.get(Calendar.MONTH) + 1;
				int nowDay = c.get(Calendar.DAY_OF_MONTH);
				int nowHours = c.get(Calendar.HOUR_OF_DAY);
				int nowMinutes = c.get(Calendar.MINUTE);

				int aYear = (current_year.getCurrentItem() + 2016);
				int aMonth = (current_month.getCurrentItem() + 1);
				int aDay = (current_day.getCurrentItem() + 1);
				int aHours = (current_hour.getCurrentItem());
				int aMinutes = (current_minute.getCurrentItem());
				if (aYear > nowYear) {
					isBefore = 1;
				} else if (aYear == nowYear) {
					if (aMonth > nowMonth) {
						isBefore = 1;
					} else if (aMonth == nowMonth) {
						if (aDay > nowDay) {
							isBefore = 1;
						} else if (aDay == nowDay) {
							if (aHours > nowHours) {
								isBefore = 1;
							} else if (aHours == aHours) {
								if (aMinutes > nowMinutes) {
									isBefore = 1;
								}
							} else {
								isBefore = -1;
							}
						} else {
							isBefore = -1;
						}
					} else {
						isBefore = -1;
					}
				} else {
					isBefore = -1;
				}
				if (isBefore == 1) {
					time_start = (current_year.getCurrentItem() + 2016)
							+ "-"
							+ decimal.format((current_month.getCurrentItem() + 1))
							+ "-"
							+ decimal.format((current_day.getCurrentItem() + 1))
							+ " "
							+ decimal.format(current_hour.getCurrentItem())
							+ ":"
							+ decimal.format(current_minute.getCurrentItem());
					tv_time_start.setText(time_start);
					SharedUtils.setParam(getActivity(), "time_start", time_start);
					// TODO Auto-generated method stub
					// 如果是个数,则显示为"02"的样式
					// 设置日期的显示
					// tv_time.setText((wv_year.getCurrentItem() + START_YEAR) +
					// "-"
					// + decimal.format((wv_month.getCurrentItem() + 1)) + "-"
					// + decimal.format((wv_day.getCurrentItem() + 1)) + " "
					// + decimal.format(wv_hours.getCurrentItem()) + ":"
					// + decimal.format(wv_minute.getCurrentItem()));
					dialog.dismiss();
				} else {
					new AlertDialog.Builder(getActivity())
							.setTitle("系统提示")
							// 设置对话框标题

							.setMessage("设定计划的开始时间不可小于当前时间 请重新设定！")
							// 设置显示的内容

							.setPositiveButton("确定",
									new DialogInterface.OnClickListener() {// 添加确定按钮

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {// 确定按钮的响应事件

											// TODO Auto-generated
											// method stub
											dialog.dismiss();

										}
									}).show();// 在按键响应事件中显示此对话框

				}
			}
		});
		// 取消
		btn_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		// 设置dialog的布局,并显示
		dialog.setContentView(view);
		dialog.show();
	}

	public static int[] randomCommon(int min, int max, int n) {
		// 不合法参数
		if (n > (max - min + 1) || max < min) {
			return null;
		}
		// 生成一个n位的随机数组
		int[] result = new int[n];
		int count = 0;
		while (count < n) {
			// 随机生成一个数
			int num = (int) (Math.random() * (max - min)) + min;
			boolean flag = true; // 是否存在相同标志
			// 如果随生成的数字和数组中的数字有相同 设置flag=false
			for (int i = 0; i < result.length; i++) {
				if (num == result[i]) {
					flag = false;
					break;
				}
			}
			// 如果随机生成的数字和数组中的数字没有相同就放入数组并count+1
			if (flag) {
				result[count] = num;
				count++;
			}
		}
		return result;
	}
}