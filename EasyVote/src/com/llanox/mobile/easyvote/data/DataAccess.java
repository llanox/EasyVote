package com.llanox.mobile.easyvote.data;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.llanox.mobile.easyvote.data.EasyVoteDBHelper;

public abstract class DataAccess implements DataSession {

	public static final String SQL_INSERT_OR_REPLACE_FORMAT = "INSERT OR REPLACE INTO %s (%s) VALUES (%s) ;";
	private  String sqlBulkInsert=null;
	protected static final String TAG = DataAccess.class.getSimpleName();
	protected String tableName = null;
	protected String[] columns = null; 
	
	protected EasyVoteDBHelper mDbHelper;
	protected SQLiteDatabase mDb;
	protected Context mContext;
//	protected ActualizacionesData actualizaciones;
//	protected JSONParser parser;
	
	
	public DataAccess(Context ctx, String tableName, String[] columns) {
		this.mDbHelper = EasyVoteDBHelper.getInstance(ctx);
		this.mContext = ctx;
		this.tableName = tableName;
		this.columns = columns;
//		this.actualizaciones = new ActualizacionesData(ctx);
		this.sqlBulkInsert = buildSQLBulkInsert(tableName, columns);
		
	}
	
	public DataAccess() {
		super();
	}

//	public DataAccess(Context ctx, String table, String[] columns,String[] tags, String resourceName, String idTag) {
//		this(ctx,table,columns);		
//		parser = new GenericParser(resourceName, idTag, tags);		
//	}
	
	
	public static String buildSQLBulkInsert(String tableName, String[] columns) {
		
		StringBuilder intoColumnNames = new StringBuilder();
		StringBuilder questionMarks = new StringBuilder();
		
	    String questionMark ="?";
		for(String col:columns){
			intoColumnNames.append(col).append(",");
			questionMarks.append(questionMark).append(",");
		}
		
		int indexLastComma = (intoColumnNames.length()<=0)?0:intoColumnNames.length()-1;
		intoColumnNames.deleteCharAt(indexLastComma);
		indexLastComma = (questionMarks.length()<=0)?0:questionMarks.length()-1;
		questionMarks.deleteCharAt(indexLastComma);
		
		return String.format(SQL_INSERT_OR_REPLACE_FORMAT,tableName, intoColumnNames.toString(),questionMarks.toString());
	}
    
//	public void bulkInsert(JSONArray arrayClientes) throws JSONException{
//	     this.bulkInsert(arrayClientes, parser);  
//	}
//	
//	public void bulkInsert(JSONObject response) throws JSONException{
//	     this.bulkInsert(parser.getResourceCollection(response), parser);  
//	}
//	
//	
//	public void bulkInsert(JSONArray arrayClientes, JSONParser parser) throws JSONException{		
//		this.bulkInsert(arrayClientes, parser, sqlBulkInsert, tableName, columns);
//	}
	
//	public void bulkInsert(JSONArray arrayClientes, JSONParser parser,String sqlQuery, String tableName, String[] columns) throws JSONException {
//			
//			
//			
//			long lStartTime = System.currentTimeMillis();
//	        
//	        int size = arrayClientes.length();         
//	        mDb.beginTransaction();
//	        int nCols = columns.length;
//	        SQLiteStatement stmt = mDb.compileStatement(sqlQuery);
//	        
//	        for (int i = 0; i < size; i++) {
//	       	 JSONObject jsonData=(JSONObject)arrayClientes.get(i);
//	       	 String[] row = (String[]) parser.process(jsonData);  
//	       	 
//	       	 
//	       	 for(int j=0; j<nCols;j++){
//	       		 stmt.bindString(j+1, row[j]);
//	       	 }
//	       	 	 
//	            stmt.execute();
//	            stmt.clearBindings();
//	        }
//	         
//	        mDb.setTransactionSuccessful();
//	        mDb.endTransaction();
//	        long lEndTime = System.currentTimeMillis();
//	
//	    	long difference = lEndTime - lStartTime;
////	    	Logger.d(TAG,"Records:"+size+ "ElapsedTime: "+difference+" mileseconds. SQL:"+sqlQuery, mContext);
////	
////			
////			registrarActualizacion(size);
//		}

//	public void registrarActualizacion(int records) {	      
//		actualizaciones.insertOrUpdate(tableName, new Date(), records);
//	}
//
//    public Date getUltimaActualizacion(){
//    	return actualizaciones.getUltimaFechaActualizacion(tableName);
//    }
	



	public void open() throws SQLException {
		mDb = mDbHelper.getWritableDatabase();
//		actualizaciones.open();
	
	}

	public void close() {
		mDbHelper.close();
//		actualizaciones.close();
	}
	
	public void deleteAll() {		
		mDb.delete(tableName, null, null);
	}

//	public JSONParser getParser(){
//		return parser;
//	}

	public abstract String getRestPath();


	public abstract String getResourceName() ;

	

}