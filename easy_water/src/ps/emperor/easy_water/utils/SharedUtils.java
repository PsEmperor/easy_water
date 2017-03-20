package ps.emperor.easy_water.utils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

public class SharedUtils {
	/**
	 * 保存在手机里面的文件名
	 */
	private static final String FILE_NAME = "share_date";

	/**
	 * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
	 * 
	 * @param context
	 * @param key
	 * @param object
	 */
	public static void setParam(Context context, String key, Object object) {

		String type = object.getClass().getSimpleName();
		SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();

		if ("String".equals(type)) {
			editor.putString(key, (String) object);
		} else if ("Integer".equals(type)) {
			editor.putInt(key, (Integer) object);
		} else if ("Boolean".equals(type)) {
			editor.putBoolean(key, (Boolean) object);
		} else if ("Float".equals(type)) {
			editor.putFloat(key, (Float) object);
		} else if ("Long".equals(type)) {
			editor.putLong(key, (Long) object);
		}

		editor.commit();
	}

	/**
	 * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
	 * 
	 * @param context
	 * @param key
	 * @param defaultObject
	 * @return
	 */
	public static Object getParam(Context context, String key, Object defaultObject) {
		String type = defaultObject.getClass().getSimpleName();
		SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);

		if ("String".equals(type)) {
			return sp.getString(key, (String) defaultObject);
		} else if ("Integer".equals(type)) {
			return sp.getInt(key, (Integer) defaultObject);
		} else if ("Boolean".equals(type)) {
			return sp.getBoolean(key, (Boolean) defaultObject);
		} else if ("Float".equals(type)) {
			return sp.getFloat(key, (Float) defaultObject);
		} else if ("Long".equals(type)) {
			return sp.getLong(key, (Long) defaultObject);
		}

		return null;
	}
	 private static final String SAVETAG = "list";  
	  
	    /** 
	     *使用SharedPreferences保存对象类型的数据 
	     * 先将对象类型转化为二进制数据，然后用特定的字符集编码成字符串进行保存 
	     * @param object 要保存的对象 
	     * @param context 
	     * @param shaPreName 保存的文件名 
	     */  
	    public static void saveObject(Object object,Context context,String shaPreName){  
	        SharedPreferences sharedPreferences =  
	                context.getSharedPreferences(shaPreName, Activity.MODE_PRIVATE);  
	        SharedPreferences.Editor editor = sharedPreferences.edit();  
	        List<Object> list = getObject(context,shaPreName);  
	        if(null == list){  
	            list = new ArrayList<Object>();  
	        }  
	        list.add(object);  
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
	        try {  
	            ObjectOutputStream oos = new ObjectOutputStream(baos);  
	            oos.writeObject(list);  
	            String strList = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));  
	            editor.putString(SAVETAG, strList);  
	            editor.commit();  
	            oos.close();  
	        } catch (IOException e) {  
	            // TODO Auto-generated catch block  
	            e.printStackTrace();  
	        }finally{  
	            try {  
	                baos.close();  
	            } catch (IOException e) {  
	                // TODO Auto-generated catch block  
	                e.printStackTrace();  
	            }  
	        }  
	    }  
	  
	    /** 
	     *根据文件名取得存储的数据对象 
	     * 先将取得的数据转化成二进制数组，然后转化成对象 
	     * @param context 
	     * @param shaPreName    读取数据的文件名 
	     * @return 
	     */  
	    public static List<Object> getObject(Context context,String shaPreName){  
	        List<Object> list;  
	        SharedPreferences sharedPreferences =  
	                context.getSharedPreferences(shaPreName, Activity.MODE_PRIVATE);  
	        String message  = sharedPreferences.getString(SAVETAG, "");  
	        byte[] buffer = Base64.decode(message.getBytes(), Base64.DEFAULT);  
	        ByteArrayInputStream bais = new ByteArrayInputStream(buffer);  
	        try {  
	            ObjectInputStream ois = new ObjectInputStream(bais);  
	            list = (List<Object>)ois.readObject();  
	            ois.close();  
	            return list;  
	        } catch (StreamCorruptedException e) {  
	            // TODO Auto-generated catch block  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            // TODO Auto-generated catch block  
	            e.printStackTrace();  
	        } catch (ClassNotFoundException e) {  
	            // TODO Auto-generated catch block  
	            e.printStackTrace();  
	        }finally{  
	            try {  
	                bais.close();  
	            } catch (IOException e) {  
	                // TODO Auto-generated catch block  
	                e.printStackTrace();  
	            }  
	        }  
	        return null;  
	    }  
}
