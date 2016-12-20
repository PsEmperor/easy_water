package ps.emperor.easy_water.greendao;

import java.util.ArrayList;

import java.util.List;

import ps.emperor.easy_water.greendao.IrrigationDao.Properties;

import de.greenrobot.dao.query.QueryBuilder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
    private IrrigationIsFirstDao irrigationIsFirstDao;
    private SingleValueDao valueDao;
    private Cursor cursor;
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
                   instance.irrigationIsFirstDao = instance.mDaoSession.getIrrigationIsFirstDao();
        }   
        return instance;    
    }  
  
    
    //根据灌溉单元查询计划 倒序
      public List<IrrigationProject> loadLastMsgBySessionid(String irrigation){  
          QueryBuilder<IrrigationProject> mqBuilder = irrigationprojectDao.queryBuilder();  
          mqBuilder.where(ps.emperor.easy_water.greendao.IrrigationProjectDao.Properties.Irrigation.eq(irrigation))  
          .orderDesc(ps.emperor.easy_water.greendao.IrrigationProjectDao.Properties.Irrigation)  
          ;  
          List<IrrigationProject> irrigations = new ArrayList<IrrigationProject>();  
          int len = mqBuilder.list().size();  
          for (int i = len-1; i >=0; i--) {  
          	irrigations.add(mqBuilder.list().get(i));  
          }  
          return irrigations;  
      }
      //根据灌溉单元查询计划 倒序
      public List<IrrigationProject> loadLastMsgBySessionidOnle(String irrigation){  
    	  QueryBuilder<IrrigationProject> mqBuilder = irrigationprojectDao.queryBuilder();  
    	  mqBuilder.where(ps.emperor.easy_water.greendao.IrrigationProjectDao.Properties.Irrigation.eq(irrigation))  
    	  .orderDesc(ps.emperor.easy_water.greendao.IrrigationProjectDao.Properties.Irrigation).limit(10) 
    	  ;  
    	  List<IrrigationProject> irrigations = new ArrayList<IrrigationProject>();  
    	  int len = mqBuilder.list().size();  
    	  for (int i = len-1; i >=0; i--) {  
    		  irrigations.add(mqBuilder.list().get(i));  
    	  }  
    	  return irrigations;  
      }
    //根据灌溉单元查询计划 正序
      public List<IrrigationProject> loadLastMsgBySessionids(String irrigation){  
          QueryBuilder<IrrigationProject> mqBuilder = irrigationprojectDao.queryBuilder();  
          mqBuilder.where(ps.emperor.easy_water.greendao.IrrigationProjectDao.Properties.Irrigation.eq(irrigation))  
          .orderDesc(ps.emperor.easy_water.greendao.IrrigationProjectDao.Properties.Irrigation)  
          ;  
          List<IrrigationProject> irrigations = new ArrayList<IrrigationProject>();  
          int len = mqBuilder.list().size();  
          for (int i = 0; i <len ; i++) {  
          	irrigations.add(mqBuilder.list().get(i));  
          }  
          return irrigations;  
      }
      //根据灌溉单元查询灌溉组 正序
      public List<IrrigationGroup> loadGroupByUnits(String irrigation){  
          QueryBuilder<IrrigationGroup> mqBuilder = irrigationGroupDao.queryBuilder();  
          mqBuilder.where(IrrigationGroupDao.Properties.Irrigation.eq(irrigation))  
          .orderDesc(IrrigationGroupDao.Properties.Irrigation)  
          ;  
          List<IrrigationGroup> irrigations = new ArrayList<IrrigationGroup>();  
          int len = mqBuilder.list().size();  
          for (int i = 0; i <len ; i++) {  
          	irrigations.add(mqBuilder.list().get(i));  
          }  
          return irrigations;  
      }
    //根据灌溉单元查询是否第一次设置 正序
      public List<IrrigationIsFirst> loadisFirst(String irrigation){  
          QueryBuilder<IrrigationIsFirst> mqBuilder = irrigationIsFirstDao.queryBuilder();  
          mqBuilder.where(IrrigationIsFirstDao.Properties.Irrigation.eq(irrigation))  
          .orderDesc(IrrigationIsFirstDao.Properties.Irrigation)  
          ;  
          List<IrrigationIsFirst> irrigations = new ArrayList<IrrigationIsFirst>();  
          int len = mqBuilder.list().size();  
          for (int i = 0; i <len ; i++) {  
          	irrigations.add(mqBuilder.list().get(i));  
          }  
          return irrigations;  
      }
    //根据灌溉单元查询灌水持续时间  正序
      public List<Irrigation> loadContinue(String irrigation){  
          QueryBuilder<Irrigation> mqBuilder = irrigationDao.queryBuilder();  
          mqBuilder.where(Properties.Irrigation.eq(irrigation))  
          .orderDesc(Properties.Irrigation)  
          ;  
          List<Irrigation> irrigations = new ArrayList<Irrigation>();  
          int len = mqBuilder.list().size();  
          for (int i = 0; i <len ; i++) {  
          	irrigations.add(mqBuilder.list().get(i));  
          }  
          return irrigations;  
      }
  
      /**  
       * delete note by id  
       * @param id  
       */     
      //根据灌溉单元查询-id删除 正序
      public List<IrrigationProject> loadLastMsgByDelete(String irrigation,int round){  
          QueryBuilder<IrrigationProject> mqBuilder = irrigationprojectDao.queryBuilder();  
          mqBuilder.where(ps.emperor.easy_water.greendao.IrrigationProjectDao.Properties.Irrigation.eq(irrigation)).where(ps.emperor.easy_water.greendao.IrrigationProjectDao.Properties.Round.eq(round))  
          .orderDesc(ps.emperor.easy_water.greendao.IrrigationProjectDao.Properties.Irrigation)  
          ;  
          List<IrrigationProject> irrigations = new ArrayList<IrrigationProject>();  
          int len = mqBuilder.list().size();  
          for (int i = 0; i <len ; i++) {  
          	irrigations.add(mqBuilder.list().get(i));  
          }  
          return irrigations;  
      }
      
      public void updateContinue(String id,int nHour,int nMinute) {
      	Irrigation findUser = irrigationDao.queryBuilder().where(Properties.Irrigation.eq(id)).build().unique();
      	if(findUser != null) {
      	    findUser.setNHour(nHour);
      	    findUser.setNMinutes(nMinute);
      	    irrigationDao.update(findUser);
      	}
      }
      
      
      public void updateContinueNum(String id,int nNumber) {
        	Irrigation findUser = irrigationDao.queryBuilder().where(Properties.Irrigation.eq(id)).build().unique();
        	if(findUser != null) {
        	    findUser.setNNumber(nNumber);
        	    irrigationDao.update(findUser);
        	}
        }
      public void updateContinueRound(String id,int nRound) {
      	Irrigation findUser = irrigationDao.queryBuilder().where(Properties.Irrigation.eq(id)).build().unique();
      	if(findUser != null) {
      	    findUser.setNRound(nRound);
      	    irrigationDao.update(findUser);
      	}
      }
      //更新夜间休息时间
      public void updateBasicTime(String id,int isNightStartHour,int isNightStartMinute,int isNightContinueHour,int isNightContinueMinute,int isNightEndHour,int isNightEndMinute) {
      	Irrigation findUser = irrigationDao.queryBuilder().where(Properties.Irrigation.eq(id)).build().unique();
      	if(findUser != null) {
      	    findUser.setIsNightStartHour(isNightStartHour);
      	    findUser.setIsNightStartMinute(isNightStartMinute);
      	    findUser.setIsNightContinueHour(isNightContinueHour);
      	    findUser.setIsNightContinueMinute(isNightContinueMinute);
      	    findUser.setIsNightEndHour(isNightEndHour);
      	    findUser.setIsNightEndMinute(isNightEndMinute);
      	    irrigationDao.update(findUser);
      	}
      }
      //更新最大灌溉时长
      public void updateBasicTimeLong(String id,int isTimeLong) {
        	Irrigation findUser = irrigationDao.queryBuilder().where(Properties.Irrigation.eq(id)).build().unique();
        	if(findUser != null) {
        	    findUser.setIsTimeLong(isTimeLong);
        	    irrigationDao.update(findUser);
        	}
        }
      //更新阀门数
      public void updateBasicVlaue(String id,int valuenumber) {
      	Irrigation findUser = irrigationDao.queryBuilder().where(Properties.Irrigation.eq(id)).build().unique();
      	if(findUser != null) {
      	    findUser.setValuenumber(valuenumber);
      	    irrigationDao.update(findUser);
      	}
      }
      //更新组数
      public void updateBasicGroup(String id,int groupnumber) {
      	Irrigation findUser = irrigationDao.queryBuilder().where(Properties.Irrigation.eq(id)).build().unique();
      	if(findUser != null) {
      	    findUser.setGroupnumber(groupnumber);
      	    irrigationDao.update(findUser);
      	}
      }
      //更新过滤器
      public void updateBasicFilter(String id,int filterHour,int filterMinute) {
      	Irrigation findUser = irrigationDao.queryBuilder().where(Properties.Irrigation.eq(id)).build().unique();
      	if(findUser != null) {
      	    findUser.setFilterHour(filterHour);
      	    findUser.setFilterMinute(filterMinute);
      	    irrigationDao.update(findUser);
      	}
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
    public long saveIsFirst(IrrigationIsFirst irrigation){    
        return irrigationIsFirstDao.insertOrReplace(irrigation);    
    } 
  //插入或者删除灌溉计划项  
    public long saveProject(IrrigationProject irrigation){    
        return irrigationprojectDao.insertOrReplace(irrigation);    
    } 
  //插入或者删除阀门项  
    public long saveValue(SingleValue value){    
        return valueDao.insertOrReplace(value);    
    } 
  //插入或者删除irrigations项  
    public long saveGroup(IrrigationGroup irrigationGroup){    
        return irrigationGroupDao.insertOrReplace(irrigationGroup);    
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
    
  //获得所有的Irrigations正序排存到List列表里面  
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
    public void updateRound(String irrigation,String id,String round) {
    	IrrigationProject findUser = irrigationprojectDao.queryBuilder().where(ps.emperor.easy_water.greendao.IrrigationProjectDao.Properties.Irrigation.eq(irrigation)).where(IrrigationProjectDao.Properties.Id.eq(id)).build().unique();
    	if(findUser != null) {
    	    findUser.setProjectstart(round);
    	    irrigationprojectDao.update(findUser);
    	}
    }
    public void updateProjects(String irrigation ,String id,int randomCommon,String start,String end) {
    	IrrigationProject findUser = irrigationprojectDao.queryBuilder().where(ps.emperor.easy_water.greendao.IrrigationProjectDao.Properties.Irrigation.eq(irrigation)).where(ps.emperor.easy_water.greendao.IrrigationProjectDao.Properties.Round.eq(id)).where(IrrigationProjectDao.Properties.Marshalling.eq(randomCommon)).build().unique();
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
  
  
    public void deleteNote(long id){    
        irrigationprojectDao.deleteByKey(id);
    }    
  
}
