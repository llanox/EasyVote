package com.llanox.mobile.easyvote.data;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;

public class DataLayerManager {
	
	private static DataLayerManager instance;
	private HashMap<String,DataAccess> dataSessions = new HashMap<String,DataAccess>();	
	private Context mContext;	
	
	private DataLayerManager(Context ctx) {
		mContext = ctx;
	}

	public static synchronized DataLayerManager getInstance(Context ctx){
		
		if(instance==null){			
			return new DataLayerManager(ctx);
		}
		
		return instance;
				
	}
	
	
	
	
	
	public void startDataBase(String databaseName, Class<?>... data ){		
	
		EasyVoteDBHelper.getInstance(mContext,databaseName);
		
		if(data==null)
			return;
		
		for(Class<?> clazz:data){
		     registerDataSession(clazz).open();	
		}
	}
	
	public void startDataBase(String databaseName ){		
		startDataBase(databaseName, null);
	}
	
	
	
	
	
	
	
	private DataAccess registerDataSession(Class<?> clazzName) {
		
		
	     
	     if(compareClass(QuestionData.class,clazzName)){
	    	 DataAccess dataSession  = new QuestionData(mContext);	    	 
	    	 dataSessions.put(clazzName.getName(), dataSession);
	    	 return dataSession ;
	     }
	   
		   if(compareClass(AnswerQuestionData.class,clazzName)){
		    	 DataAccess dataSession  = new AnswerQuestionData(mContext);	    	 
		    	 dataSessions.put(clazzName.getName(), dataSession);
		    	 return dataSession ;
		     } 
		  
		   if(compareClass(UsersData.class,clazzName)){
		    	 DataAccess dataSession  = new UsersData(mContext);	    	 
		    	 dataSessions.put(clazzName.getName(), dataSession);
		    	 return dataSession ;
		     } 
		
	     throw new DataException("No se ha matriculado la clase DataAccess: "+clazzName);
	     
	   
	    
		
	}

	private boolean compareClass(Class<?> toInstance,	Class<?> requested) {
	   
		if(toInstance.getName().equalsIgnoreCase(requested.getName())){
			return true;
		}
		
		return false;
	}
	
	
	public void cleanUp(){
		
		for(String key :dataSessions.keySet()){
			dataSessions.remove(key).close();
		}
	}
	
	public DataAccess getDataSession(Class<?> clazz){
		
		DataAccess session = dataSessions.get(clazz.getName());
		session = (session!=null)? session :registerDataSession(clazz);
		session.open();
		
		return session;
	}

	
	public List<String> getDatabasesNames() {	
		String[] dataBases = null;
		dataBases = mContext.databaseList();		
		
		return filterDatabaseName(dataBases);
	}


	private List<String> filterDatabaseName(String[] dataBases) {
		List<String> dbsNames = new ArrayList<String>();

		
		for(String databaseName: dataBases){
			
			int indexDbName = databaseName.indexOf(EasyVoteDBHelper.DATABASE_NAME);
			
			if(indexDbName == -1){
				continue;
			}
			
			String database = databaseName.substring(0,indexDbName);
			
			if(!dbsNames.contains(database)){
				dbsNames.add(database);
			}
			
		}
		
		return dbsNames;
	}

	public void openAll() {
		
		for(String key :dataSessions.keySet()){
			dataSessions.get(key).open();
		}
		
	}

	public void closeAll() {
		for(String key :dataSessions.keySet()){
			dataSessions.get(key).close();
		}
		
	}

}
