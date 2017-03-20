package ps.emperor.easy_water.utils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;

public class PutToFile {
	public static Bitmap getBitmapFromFile(){
		File file = new File(Environment.getExternalStorageDirectory(),"icons.jpg");
		if(file.exists()){
			Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
			return bitmap;
			}
		return null;
	}
	
	public static void putToFile(Bitmap bitmap){
		File file=new File(Environment.getExternalStorageDirectory(),"icons.jpg");
		try {
			bitmap.compress(CompressFormat.PNG, 100, new FileOutputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}