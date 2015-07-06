package com.llanox.mobile.easyvote.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class AppSessionManager {
	public static final String DISPLAY_NAME_KEY = "DISPLAY_NAME_KEY";

	public static final String NICKNAME_KEY = "NICKNAME_KEY";

	public static final String ID_USER_KEY = "ID_USER_KEY";
	
	public static final String SESSION_SETTINGS_PREF = "session_settings_pref";

	private static final String EMAIL_KEY = "EMAIL_KEY";
	

	public static void saveUserSession(Context ctx,String id, String nickName,String displayName,String email) {

		SharedPreferences pref = ctx.getSharedPreferences(SESSION_SETTINGS_PREF, Context.MODE_PRIVATE);
		Editor edit = pref.edit();
		edit.putString(ID_USER_KEY, id );
		edit.putString(NICKNAME_KEY, nickName);
		edit.putString(DISPLAY_NAME_KEY, displayName);
		edit.putString(EMAIL_KEY, email);	
		edit.apply();
		
	}
	
	public static void removeUserSession(Context ctx){
		SharedPreferences pref = ctx.getSharedPreferences(SESSION_SETTINGS_PREF, Context.MODE_PRIVATE);
		Editor edit = pref.edit();
		edit.clear();
		edit.apply();
	}
	
	public static String getUserID(Context ctx){
		SharedPreferences pref = ctx.getSharedPreferences(SESSION_SETTINGS_PREF, Context.MODE_PRIVATE);		
		return pref.getString(ID_USER_KEY, null);
	}
	
	public static String getUserEmail(Context ctx){
		SharedPreferences pref = ctx.getSharedPreferences(SESSION_SETTINGS_PREF, Context.MODE_PRIVATE);		
		return pref.getString(EMAIL_KEY, null);
	}
	
	
	
}
