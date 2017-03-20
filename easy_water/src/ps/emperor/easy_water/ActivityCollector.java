package ps.emperor.easy_water;

import java.util.ArrayList;

import java.util.List;

import android.app.Activity;

/**
 * 
 * activity 管理器
 * 
 * 
 * @author purplesea
 *
 */
public class ActivityCollector {
	
	public static List<Activity> list = new ArrayList<Activity>();
	
	public static void addAct(Activity act){
		list.add(act);
	}
	
	public static void removeAct(Activity act){
		list.remove(act);
	}
	
	public static void removeAll(){
		for(Activity act : list){
			if(!act.isFinishing()){
				act.finish();
			}
		}
	}

}
