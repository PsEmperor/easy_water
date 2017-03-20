package ps.emperor.easy_water.fragment;

import java.util.ArrayList;

import java.util.List;
import java.util.Random;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.MultipleCategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import ps.emperor.easy_water.R;
import ps.emperor.easy_water.adapter.ApplyWaterGateLinkageAdapter;
import ps.emperor.easy_water.application.ApplicationFragment;
import ps.emperor.easy_water.entity.ApplyWaterGateLinkageBean;
import ps.emperor.easy_water.utils.DensityUtil;
import ps.emperor.easy_water.utils.SharedUtils;
import ps.emperor.easy_water.view.MainActionBar;
import ps.emperor.easy_water.view.MainActionBars;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

/**
 * 数据
 * 
 * @author 毛国江
 * @version 2016-12-14 下午12:30
 */
public class InformationFragment extends Fragment implements OnClickListener {
	private LayoutInflater mInflater;
	private MainActionBar actionBar;
	private GraphicalView mChartView;
	private List<String> list = new ArrayList<String>();
	private int list_num;
	private TextView irrigate, water;
	private List<TextView> itemViews;
	private int index;
	private LinearLayout mLinear;
	
	@SuppressLint("CutPasteId")
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflater = inflater;
		View view = inflater.inflate(R.layout.fragment_infofragment, container,
				false);

		actionBar = (MainActionBar) view
				.findViewById(R.id.fragment_informationfragment);
		actionBar.setLeftIcon(R.drawable.btn_back_selector);
		actionBar.setRightGone();
		actionBar.setActionBarOnClickListener(this);
		irrigate = (TextView) view.findViewById(R.id.histogram);
		water = (TextView) view.findViewById(R.id.piechart);
		mLinear = (LinearLayout) view.findViewById(
				R.id.linear_infofragment_test);
		
		itemViews = new ArrayList<TextView>();
		itemViews.add(irrigate);
		itemViews.add(water);
		
		irrigate.setOnClickListener(this);
		water.setOnClickListener(this);
		initData();
		return view;
	}

	private void initData() {
		gethistogram();
		mLinear.removeAllViews();
		mLinear.addView(mChartView, new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
		index = 1;
		clearStatus();		
	}

	public void gethistogram() {
		list.add("灌溉时间");
		list.add("面积");
		list.add("开闸时间");
		list.add("水量");
		list.add("开闸次数");
		list.add("电量");
		list.add("循环次数");
		list.add("反过滤");
		list.add("流量");
		// String[] titles = new String[] { "2012", "2013" };
		String[] titles = new String[] { "累计数据统计" };
		List<double[]> values = new ArrayList<double[]>();
		values.add(new double[] { 660, 740, 660 ,780, 750, 580 ,690, 522,700});
		// values.add(new double[] { 523, 730, 924, 1054, 790, 920, 1200, 1100,
		// 950, 1500, 1100, 1500 });
		// int[] colors = new int[] { Color.RED, Color.BLUE };
		int[] colors = new int[] { Color.RED };
		XYMultipleSeriesRenderer renderer = buildBarRenderer(colors);// 柱形图颜色设置
		setChartSettings(renderer, "", "", "", 0.5, 9.5, 0, 1000, Color.BLUE,
				Color.BLUE);// 设置柱形图标题，横轴（X轴）、纵轴（Y轴）、最小的伸所刻度、最大的伸所刻度
		renderer.getSeriesRendererAt(0).setDisplayChartValues(true);// 在第0条柱形图上显示数据
		// renderer.getSeriesRendererAt(1).setDisplayChartValues(true);//
		// 在第1条柱形图上显示数据
		renderer.setXLabels(0);

		int count = 1;
		list_num = list.size();
		do {

			renderer.addTextLabel(count, list.get(count - 1));

			count++;
			list_num--;
		} while (list_num > 0);
		renderer.setYLabels(10);
		renderer.setMargins(new int[] { 20, 30, 15, 15 }); // 设置4边留白  
        renderer.setMarginsColor(Color.argb(0, 0xff, 0, 0));// 设置4边留白透明  
		renderer.setXLabelsAlign(Align.CENTER);// 数据从左到右显示
		renderer.setYLabelsAlign(Align.CENTER);
		renderer.setPanEnabled(true, true);
		renderer.setZoomEnabled(false);
		renderer.setAntialiasing(true);
		renderer.setZoomButtonsVisible(false);// 显示放大缩小功能按钮
		renderer.setZoomRate(5f);
		renderer.setInScroll(true);
		renderer.setBarSpacing(0.5f);// 柱形图间隔

		mChartView = ChartFactory.getBarChartView(getActivity(),
				buildBarDataset(titles, values), renderer, Type.DEFAULT);
		renderer.setClickEnabled(true);
		// renderer.set
	}

	@Override
	public void onClick(View v) {
		FragmentManager fgManager = getFragmentManager();
		FragmentTransaction transaction = fgManager.beginTransaction();
		switch (v.getId()) {
		case R.id.acitionbar_left:
			ApplicationFragment fragment = new ApplicationFragment();
			// transaction.setCustomAnimations(R.anim.right_in,
			// R.anim.right_out);
			transaction
					.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			transaction.replace(R.id.fl, fragment, "main");
			transaction.commit();
			break;
	case R.id.histogram:
		gethistogram();
		mLinear.removeAllViews();
		mLinear.addView(mChartView, new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
		index = 1;
		clearStatus();
		break;
	case R.id.piechart:
		mChartView = ChartFactory.getDoughnutChartView(getActivity(),  
                getMulDataset(), getMulRenderer());
		mLinear.removeAllViews();
		mLinear.addView(mChartView, new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
		index = 2;
		clearStatus();
		break;
		}
	}

	protected void setChartSettings(XYMultipleSeriesRenderer renderer,
			String title, String xTitle, String yTitle, double xMin,
			double xMax, double yMin, double yMax, int axesColor,
			int labelsColor) {
		renderer.setChartTitle(title);
		renderer.setXTitle(xTitle);
		renderer.setYTitle(yTitle);
		renderer.setXAxisMin(xMin);
		renderer.setXAxisMax(xMax);
		renderer.setYAxisMin(yMin);
		renderer.setYAxisMax(yMax);
		renderer.setAxesColor(axesColor);
		renderer.setLabelsColor(labelsColor);
	}

	protected XYMultipleSeriesRenderer buildBarRenderer(int[] colors) {
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		renderer.setAxisTitleTextSize(15);
		renderer.setChartTitleTextSize(20);
		renderer.setLabelsTextSize(20);
		renderer.setLegendTextSize(20);
		int length = colors.length;
		for (int i = 0; i < length; i++) {
			SimpleSeriesRenderer r = new SimpleSeriesRenderer();
			r.setColor(colors[i]);
			renderer.addSeriesRenderer(r);
		}
		return renderer;
	}

	protected XYMultipleSeriesDataset buildBarDataset(String[] titles,
			List<double[]> values) {// 柱形图的数据源和饼图差不多，也是由一些键值对组成
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		int length = titles.length;
		for (int i = 0; i < length; i++) {
			CategorySeries series = new CategorySeries(titles[i]);
			double[] v = values.get(i);
			int seriesLength = v.length;
			for (int k = 0; k < seriesLength; k++) {
				series.add(v[k]);
			}
			dataset.addSeries(series.toXYSeries());
		}
		return dataset;
	}

	// 饼图数据
	/**
	 * 饼状图的数据
	 * 
	 * @return
	 */
	public MultipleCategorySeries getMulDataset() {
		MultipleCategorySeries series = new MultipleCategorySeries("title");
		// 添加
		series.add(new String[] { "玉米", "黄瓜", "土豆" },
				new double[] { 20, 30, 50 });
		return series;
	}

	public DefaultRenderer getMulRenderer() {
		// 颜色
		int[] colors = new int[] { Color.BLUE, Color.GREEN, Color.RED };
		DefaultRenderer renderer = buildCategoryRenderer(colors);
		// 设置是否显示背景颜色
		renderer.setApplyBackgroundColor(false);
		// 设置背景颜色
		renderer.setBackgroundColor(Color.DKGRAY);
		// 设置坐标颜色
		renderer.setLabelsColor(Color.MAGENTA);
		// 设置移动
		renderer.setPanEnabled(false);
		// 设置放大
		renderer.setZoomEnabled(false);
		return renderer;
	}

	public DefaultRenderer buildCategoryRenderer(int[] colors) {
		DefaultRenderer renderer = new DefaultRenderer();
		renderer.setLabelsTextSize(25);
		renderer.setLegendTextSize(30);
		renderer.setChartTitle("");
		renderer.setMargins(new int[] { 20, 30, 15 });
		for (int color : colors) {
			SimpleSeriesRenderer r = new SimpleSeriesRenderer();
			r.setColor(color);
			renderer.addSeriesRenderer(r);
		}
		return renderer;
	}
	 private void clearStatus() {  
	        if (index == 1) {  
	        	irrigate.setClickable(false);
	        	irrigate.setTextColor(getResources().getColor(R.color.red));
	        	irrigate.setSelected(false);
	        	
	        	water.setClickable(true);
	        	water.setTextColor(getResources().getColor(R.color.gray_1));
	        	water.setSelected(false);
	        } else if (index == 2) {  
	        	water.setClickable(false);
	        	water.setTextColor(getResources().getColor(R.color.red));
	        	water.setSelected(false);
	        	
	        	irrigate.setClickable(true);
	        	irrigate.setTextColor(getResources().getColor(R.color.gray_1));
	        	irrigate.setSelected(false);
	        } 
	    }  
}
