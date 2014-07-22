package com.llanox.mobile.easyvote.data;

import android.database.SQLException;

public interface DataSession {
	
	public void open() throws SQLException;	
	public void close();

}
