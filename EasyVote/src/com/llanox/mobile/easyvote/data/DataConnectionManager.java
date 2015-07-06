package com.llanox.mobile.easyvote.data;



import com.backendless.Backendless;
import com.llanox.mobile.easyvote.util.AppCredentials;

import android.content.Context;

public class DataConnectionManager {
	
	private static DataConnectionManager instance;
	private Context mContext;	
    private boolean started= false;
	
	private DataConnectionManager(Context ctx) {
		mContext = ctx;
	}

	public static synchronized DataConnectionManager getInstance(Context ctx){
		
		if(instance==null){			
			return new DataConnectionManager(ctx);
		}
		
		return instance;
				
	}
	
		
	public void close(){

    }
	
	public void start(){
		
		if(started){
			return;
		}
		
		Backendless.initApp(mContext, AppCredentials.APPLICATION_KEY, AppCredentials.SECRET_KEY, AppCredentials.APP_VERSION );
		
	}
	



}
