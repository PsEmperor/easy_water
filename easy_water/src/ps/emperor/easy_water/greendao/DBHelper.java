package ps.emperor.easy_water.greendao;

import java.util.ArrayList;

import java.util.List;

import ps.emperor.easy_water.greendao.IrrigationDao.Properties;

import de.greenrobot.dao.query.QueryBuilder;

import android.content.ContentValues;
import android.content.Context;
import android.widget.Toast;

public class DBHelper {
	private static final String TAG = DBHelper.class.getSimpleName();    
    private static DBHelper instance;    
    private static Context appContext;  
    private DaoSession mDaoSession;    
    private Irrigation irrigation;
    private IrrigationDao irrigationDao;  
    private WaterDao waterDao;   
    private IrrigationProjectDao irrigationprojectDao;
    private IrrigationGroupDao irrigationGroupDao;
    private SingleValueDao valueDao;
    private DBHelper() {  
    }   
    //单例模式，DBHelper只初始化一次  
    public static  DBHelper getInstance(Context context) {    
        if (instance == null) {  
                   instance = new DBHelper();    
                   if (appContext == null){    
                       appContext = context.getApplicationContext();    
                   }    
                   instance.mDaoSession = ControlApp.getDaoSession(context);  
                   instance.irrigationDao = instance.mDaoSession.getIrrigationDao();  
                   instance.waterDao = instance.mDaoSession.getWaterDao();  
                   instance.irrigationprojectDao = instance.mDaoSession.getIrrigationProjectDao();
                   instance.irrigationGroupDao = instance.mDaoSession.getIrrigationGroupDao();
                   instance.valueDao = instance.mDaoSession.getSingleValueDao();
               }   
        return instance;    
    }  
  
  
    //删除Session表  
    public  void dropSessionTable()  
    {  
    	IrrigationDao.dropTable(mDaoSession.getDatabase(), true);  
    }  
    //删除MqttChatEntity表  
    public void dropChatTable()  
    {  
    	WaterDao.dropTable(mDaoSession.getDatabase(), true);  
    }  
    //删除所有表  
    public void dropAllTable()  
    {  
    	IrrigationDao.dropTable(mDaoSession.getDatabase(), true);  
    	WaterDao.dropTable(mDaoSession.getDatabase(), true);  
    	IrrigationProjectDao.dropTable(mDaoSession.getDatabase(), true);  
    	IrrigationGroupDao.dropTable(mDaoSession.getDatabase(), true);
    	SingleValueDao.dropTable(mDaoSession.getDatabase(), true);
    }  
    //创建所有表  
    public void createAllTable()  
    {  
    	IrrigationDao.createTable(mDaoSession.getDatabase(), true);  
    	WaterDao.createTable(mDaoSession.getDatabase(), true);  
    	IrrigationProjectDao.createTable(mDaoSession.getDatabase(), true);  
    	IrrigationGroupDao.createTable(mDaoSession.getDatabase(), true); 
    	IrrigationProjectDao.createTable(mDaoSession.getDatabase(), true); 
    }  
    /**  
     * insert or update note  
     * @param note  
     * @return insert or update note id  
     */    
    //插入或者删除irrigations项  
    public long saveSession(Irrigation irrigation){    
        return irrigationDao.insertOrReplace(irrigation);    
    }    
  
  //插入或者删除irrigations项  
    public long saveSessions(IrrigationProject irrigation){    
        return irrigationprojectDao.insertOrReplace(irrigation);    
    }    
  
    //获得所有的Irrigations倒序排存到List列表里面  
    public List<Irrigation> loadAllSession() {  
        List<Irrigation> irrigations = new ArrayList<Irrigation>();  
        List<Irrigation> tmpIrrigations = irrigationDao.loadAll();  
        int len = tmpIrrigations.size();  
        for (int i = len-1; i >=0; i--) {  
        	irrigations.add(tmpIrrigations.get(i));  
        }  
        return irrigations;  
    }    
  
    //获得所有的Irrigations倒序排存到List列表里面  
    public List<IrrigationProject> loadAllSessions() {  
        List<IrrigationProject> irrigations = new ArrayList<IrrigationProject>();  
        List<IrrigationProject> tmpIrrigations = irrigationprojectDao.loadAll();  
        int len = tmpIrrigations.size();  
        for (int i = len-1; i >=0; i--) {  
        	irrigations.add(tmpIrrigations.get(i));  
        }  
        return irrigations;  
    }  
    
  //获得所有的Irrigations倒序排存到List列表里面  
    public List<IrrigationProject> loadAllProject() {  
        List<IrrigationProject> irrigations = new ArrayList<IrrigationProject>();  
        List<IrrigationProject> tmpIrrigations = irrigationprojectDao.loadAll();  
        int len = tmpIrrigations.size();  
        for (int i = 0; i <len ; i++) {  
        	irrigations.add(tmpIrrigations.get(i));  
        }  
        return irrigations;  
    }  
    
    public void DeleteSession(Irrigation entity) {  
    	irrigationDao.delete(entity);  
    }    
    public void DeleteSessions(IrrigationProject entity) {  
    	irrigationprojectDao.delete(entity);  
    }    
    //删除某一项Session      
    public void DeleteNoteByIrrigation(Irrigation entity) {  
        QueryBuilder<Irrigation> mqBuilder = irrigationDao.queryBuilder();  
        mqBuilder.where(Properties.Irrigation.eq(entity.getIrrigation()));  
        List<Irrigation> chatEntityList = mqBuilder.build().list();  
        irrigationDao.deleteInTx(chatEntityList);  
    }    
  
    public void updateProject(String id,int randomCommon,String start,String end) {
    	IrrigationProject findUser = irrigationprojectDao.queryBuilder().where(ps.emperor.easy_water.greendao.IrrigationProjectDao.Properties.Round.eq(id)).where(IrrigationProjectDao.Properties.Marshalling.eq(randomCommon)).build().unique();
    	if(findUser != null) {
    	    findUser.setProjectstart(start);
    	    findUser.setProjectend(end);
    	    irrigationprojectDao.update(findUser);
    	}
    }
    //根据id找到某一项  
    public Irrigation loadNote(long id) {  
        return irrigationDao.load(id);    
    }    
    //获得所有的MqttChatEntity列表   
    public List<Irrigation> loadAllNote(){    
        return irrigationDao.loadAll();    
    }    
  
  
    /**  
     * query list with where clause  
     * ex: begin_date_time >= ? AND end_date_time <= ?  
     * @param where where clause, include 'where' word  
     * @param params query parameters  
     * @return  
     */    
      //查询满足params条件的列表  
    public List<Irrigation> queryNote(String where, String... params){  
        ArrayList<Irrigation> ad = new ArrayList<Irrigation>();  
        return irrigationDao.queryRaw(where, params);   
    }  
  
  
    //不一一介绍了，大家可以自己写，有些比较难的查询可以使用QueryBuilder来查询  
    public List<Irrigation> loadLastMsgBySessionid(String irrigation){  
        QueryBuilder<Irrigation> mqBuilder = irrigationDao.queryBuilder();  
        mqBuilder.where(Properties.Irrigation.eq(irrigation))  
        .orderDesc(Properties.Id)  
        .limit(1);  
        return mqBuilder.list();  
    }  
  
  
    public List<Irrigation> loadMoreMsgById(String irrigation, Long id){  
        QueryBuilder<Irrigation> mqBuilder = irrigationDao.queryBuilder();  
        mqBuilder.where(IrrigationProjectDao.Properties.Round.lt(id))  
        .where(IrrigationProjectDao.Properties.Irrigation.eq(irrigation))  
        .orderDesc(IrrigationProjectDao.Properties.Id)  
        .limit(20);  
        return mqBuilder.list();  
    }  
    public List<IrrigationProject> loadMoreMsgById(int id){  
    	QueryBuilder<IrrigationProject> mqBuilder = irrigationprojectDao.queryBuilder();  
    	mqBuilder.where(IrrigationProjectDao.Properties.Round.lt(id))  
    	.orderDesc(IrrigationProjectDao.Properties.Id)  
    	.limit(20);  
    	return mqBuilder.list();  
    }  
    
   
    /**  
     * delete all note  
     */    
    public void deleteAllNote(){    
    	irrigationDao.deleteAll();    
    }    
  
  
    /**  
     * delete note by id  
     * @param id  
     */    
    public void deleteNote(long id){    
    	irrigationprojectDao.deleteByKey(id);    
    }    
  
  
    public void deleteNote(Irrigation note){    
    	irrigationDao.delete(note);    
    }    
}
