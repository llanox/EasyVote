package com.llanox.mobile.easyvote.data;

import java.util.List;

import android.database.Cursor;
import android.database.SQLException;

public interface DataSession<T> {
	
	public void open() throws SQLException;	
	public void close();	
	
	public void insert(T item);	
	public void asyncInsert(T item, ResponseHandler<T> handler);
	
	public void update(T item);
	public void asyncUpdate(T item, ResponseHandler<T> handler);
	
	public T findById(String id);
	
	public T asyncFindById(String id, ResponseHandler<T> handler);
	
	public List<T> findAll();
	
	public List<T> asyncFindAll( ResponseHandler<List<T>> handler);
	
	
	
	interface ResponseHandler<T>{
		
		public void succesfull(T item);
		public void error(Object error);
		
	}

}
