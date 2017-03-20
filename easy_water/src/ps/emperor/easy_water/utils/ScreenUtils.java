package ps.emperor.easy_water.utils;

import java.io.FileOutputStream;

import ps.emperor.easy_water.view.HorizontalListView;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * 获得屏幕相关的辅助类
 * 
 * 
 * 
 */
public class ScreenUtils {
	/** 获取屏幕密度 */
    public static float getScreenDensity(Context context) {
        return getDisplayMetrics(context).density;
    }

    /** 获取屏幕宽度(像素) */
    public static int getScreenWidthPixels(Context context) {
        return getDisplayMetrics(context).widthPixels;
    }

    /** 获取屏幕宽度(dp) */
    public static float getScreenWidthDp(Context context) {
        DisplayMetrics displayMetrics = getDisplayMetrics(context);
        return displayMetrics.widthPixels / displayMetrics.density;
    }

    /** 获取屏幕高度(像素) */
    public static int getScreenHeightPixels(Context context) {
        return getDisplayMetrics(context).heightPixels;
    }

    /** 获取屏幕高度(dp) */
    public static float getScreenHeightDp(Context context) {
        DisplayMetrics displayMetrics = getDisplayMetrics(context);
        return displayMetrics.heightPixels / displayMetrics.density;
    }

    /** 获取状态栏高度 */
    public static int getStatusHeight(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 保存屏幕截图到本地
     *
     * @param activity
     * @param strFileName 文件全路径:例如 "/sdcard/screen_shot_20160424.jpg"
     */
    public static void savScreenShot(Activity activity, String strFileName) {
        Bitmap takeShot = takeShot(activity);
        savePic(takeShot, strFileName);
    }

    /**
     * 截图
     * 也可以调用shell命令去截图  screencap -p test.png
     *
     * @param activity 截取activity 所在的页面的截图,即使退到后台也是截取这个activity
     */
    private static Bitmap takeShot(Activity activity) {
        // 获取windows中最顶层的view
        View view = activity.getWindow().getDecorView();
        // 允许当前窗口保存缓存信息
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();

        //状态栏高度
        Rect outRect = new Rect();
        view.getWindowVisibleDisplayFrame(outRect);
        int statusBarHeight = outRect.top;

        //状态栏+标题栏目的高度
        statusBarHeight = activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();

        //屏幕宽高
        int height = getScreenHeightPixels(activity);
        int width = getScreenWidthPixels(activity);

        // 如果需要状态栏,则使用 Bitmap b = Bitmap.createBitmap(view.getDrawingCache(), 0, 0, width, height - statusBarHeight);
        Bitmap b = Bitmap.createBitmap(view.getDrawingCache(), 0, statusBarHeight, width, height - statusBarHeight);
        // 销毁缓存信息
        view.destroyDrawingCache();

        return b;
    }


    private static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics outMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics;
    }


    // 保存到sdcard
    private static void savePic(Bitmap b, String strFileName) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(strFileName);
            if (null != fos) {
                // 第一参数是图片格式，第二个是图片质量，第三个是输出流
                b.compress(Bitmap.CompressFormat.PNG, 90, fos);
                fos.flush();
                fos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
	 * 获取屏幕的宽度
	 * 
	 * @param context
	 * @return
	 */
	public static int getScreenWidth(Context context) {
		WindowManager manager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = manager.getDefaultDisplay();
		
		return display.getWidth();
	}

	/**
	 * 获取屏幕的高度
	 * 
	 * @param context
	 * @return
	 */
	public static int getScreenHeight(Context context) {
		WindowManager manager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = manager.getDefaultDisplay();
		return display.getHeight();
	}

	/**
	 * 获取屏幕中控件顶部位置的高度--即控件顶部的Y点
	 * 
	 * @return
	 */
	public static int getScreenViewTopHeight(View view) {
		return view.getTop();
	}

	/**
	 * 获取屏幕中控件底部位置的高度--即控件底部的Y点
	 * 
	 * @return
	 */
	public static int getScreenViewBottomHeight(View view) {
		return view.getBottom();
	}

	/**
	 * 获取屏幕中控件左侧的位置--即控件左侧的X点
	 * 
	 * @return
	 */
	public static int getScreenViewLeftHeight(View view) {
		return view.getLeft();
	}

	/**
	 * 获取屏幕中控件右侧的位置--即控件右侧的X点
	 * 
	 * @return
	 */
	public static int getScreenViewRightHeight(View view) {
		return view.getRight();
	}

}
