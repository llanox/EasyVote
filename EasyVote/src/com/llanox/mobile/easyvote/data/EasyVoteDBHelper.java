package com.llanox.mobile.easyvote.data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;


public class EasyVoteDBHelper extends SQLiteOpenHelper {

	public static final String TAG = EasyVoteDBHelper.class.getSimpleName();
	
	public static final int DATABASE_VERSION = 1;
	public static final String SOURCE_DATABASE_FILENAME = "db_easy_vote.sqlite";
	public static final String DATABASE_BASE_NAME ="db_easy_vote";
	public static final String DATABASE_NAME =DATABASE_BASE_NAME+"."+DATABASE_VERSION;
	
	
	
	private static File DATABASE_FILE;
	private static EasyVoteDBHelper instance;
	private Context mContext;

	private boolean mInvalidDatabaseFile = false;
	private boolean mIsUpgraded = false;
	/**
	 * number of users of the database connection.
	 * */
	private int mOpenConnections = 0;

	private EasyVoteDBHelper(Context context, String databaseName) {
		super(context, databaseName, null, DATABASE_VERSION);
		this.mContext = context;		
		restartDB(context, databaseName);
	}

	public void restartDB(Context context, String databaseName) {
		SQLiteDatabase db = null;
		File dataBaseFile = null;
		try {
			db = getReadableDatabase();
			if (db != null && db.isOpen()) {
				db.close();
			}
		    
			dataBaseFile = context.getDatabasePath(databaseName);
		
			if (mInvalidDatabaseFile || !dataBaseFile.exists()) {
				copyDatabase(dataBaseFile, SOURCE_DATABASE_FILENAME);
			}
			if (mIsUpgraded) {
				doUpgrade();							
			}
		
			setDatabaseVersion(dataBaseFile);
			
		} catch (SQLiteException e) {
			Toast.makeText(context, "Database Error:"+e.getMessage(), Toast.LENGTH_LONG).show();
			Log.e(TAG, "Database Error:"+e.getMessage());
			
		} finally {
			if (db != null && db.isOpen()) {
				db.close();
			}
		}
				
	}
	

	private void doUpgrade() {
	

	}
	
	public synchronized static  EasyVoteDBHelper getInstance(Context ctx,String additionalDataBase) {
		
		if(additionalDataBase == null){
			throw new DataException("No puede ser null como el nombre de la base de datos a crear");
		}
		
		    if("".equalsIgnoreCase(additionalDataBase) && instance !=null){
		    	return instance;
		    }		    
		
		    if(instance !=null){		    	
		    	instance.close();
		    	instance = null;
		    }
			
			instance = new EasyVoteDBHelper(ctx,buildDatabaseName(additionalDataBase));
		
		return instance;
		
	}

	private static String buildDatabaseName(String additionalDataBase) {
		String oDataBaseName = additionalDataBase.concat(DATABASE_NAME);
		return oDataBaseName;
	}

	public static EasyVoteDBHelper getInstance(Context ctx) {
		return getInstance(ctx, "");
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		mInvalidDatabaseFile = true;
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int old_version,	int new_version) {
		mInvalidDatabaseFile = true;
		mIsUpgraded = true;
	}

	@Override
	public synchronized void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
		// increment the number of users of the database connection.
		mOpenConnections++;
		if (!db.isReadOnly()) {
			// Enable foreign key constraints
			db.execSQL("PRAGMA foreign_keys=ON;");
		}
	}

	/**
	 * implementation to avoid closing the database connection while it is in
	 * use by others.
	 */
	@Override
	public synchronized void close() {
		mOpenConnections--;		
		if(mOpenConnections == 0){
			 super.close();
		}
		   
	}
	
	public void forceClose() {
		super.close();
		mOpenConnections = 0;		
	}

	private void copyDatabase(File dataBaseFile, String sourceDataBaseName) {
		AssetManager assetManager = mContext.getResources().getAssets();
		InputStream in = null;
		OutputStream out = null;
		try {
			in = assetManager.open(sourceDataBaseName);
			out = new FileOutputStream(dataBaseFile);
			byte[] buffer = new byte[1024];
			int read = 0;
			while ((read = in.read(buffer)) != -1) {
				out.write(buffer, 0, read);
			}
		} catch (IOException e) {
			Log.e(TAG, "Error copiando base de datos", e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					Log.e(TAG, "Error cerrando stream al copiar la base de datos", e);
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					Log.e(TAG, "Error cerrando stream al copiar la base de datos", e);
				}
			}
		}
		
		mInvalidDatabaseFile = false;
	}

	private void setDatabaseVersion(File dataBaseFile) {
		SQLiteDatabase db = null;
		try {
			Log.d(TAG, "active connections"+mOpenConnections);
			forceClose();
			db = SQLiteDatabase.openDatabase(dataBaseFile.getAbsolutePath(), null, SQLiteDatabase.OPEN_READWRITE);			
			db.execSQL("PRAGMA user_version = " + DATABASE_VERSION);
			db.execSQL("ATTACH DATABASE '" + dataBaseFile.getAbsolutePath() + "' AS abako_db");
			
			
			
		} catch (SQLiteException e) {
			Log.e(TAG, "Error abriendo base de datos copiada al sistema.Db:"+DATABASE_FILE+" version:"+DATABASE_VERSION+" Db name:"+DATABASE_NAME);
		} finally {
			if (db != null && db.isOpen()) {
				db.close();
			}
		}
	}

	public String getDataBasePath() {
        SQLiteDatabase db = this.getReadableDatabase();
		if (db != null ) {

		  	return db.getPath();
		}
		return null;
	}
	

	public void changeDabase(String prefixDataBase) {

		restartDB(mContext, buildDatabaseName(prefixDataBase));
		
	}

	public boolean deleteDatabase(String dataBaseName) {
		 forceClose();
		 return mContext.deleteDatabase(buildDatabaseName(dataBaseName));
		
	}





}