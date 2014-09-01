package com.llanox.mobile.easyvote.data;



import com.backendless.Backendless;
import com.llanox.mobile.easyvote.AppCredentials;

import android.content.Context;

public class DataLayerManager {
	
	private static DataLayerManager instance;
	private Context mContext;	
    private boolean started= false;
	
	private DataLayerManager(Context ctx) {
		mContext = ctx;
	}

	public static synchronized DataLayerManager getInstance(Context ctx){
		
		if(instance==null){			
			return new DataLayerManager(ctx);
		}
		
		return instance;
				
	}
	
		
	
	
	public void startBackend(){	
		
		if(started){
			return;
		}
		
		Backendless.initApp(mContext, AppCredentials.APPLICATION_KEY, AppCredentials.SECRET_KEY, AppCredentials.APP_VERSION );
		
	}
	



}
