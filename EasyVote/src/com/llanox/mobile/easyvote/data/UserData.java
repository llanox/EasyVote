package com.llanox.mobile.easyvote.data;

import java.util.List;

import android.content.Context;
import android.database.SQLException;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.async.callback.BackendlessCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;
import com.llanox.mobile.easyvote.model.User;

public class UserData implements DataSession<User> {

	
	public UserData(Context ctx) {
		DataLayerManager.getInstance(ctx).startBackend();
	}

	@Override
	public void open() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insert(User item) {	
		Backendless.Persistence.save(item);
	}

	@Override
	public void asyncInsert(User item, final ResponseHandler<User> handler) {
		
		Backendless.Persistence.save( item, new BackendlessCallback<User>()
			    {
					      @Override
					   public void handleResponse( User user ) {
					             handler.succesfull(user);
					  
					    }

						@Override
						public void handleFault(BackendlessFault fault) {
							super.handleFault(fault);
							handler.error(fault.getMessage());
						}
					      
					      
					      
      } );
		
	}

	@Override
	public void update(User item) {
		insert(item);
		
	}

	@Override
	public void asyncUpdate(User item, com.llanox.mobile.easyvote.data.DataSession.ResponseHandler<User> handler) {
		asyncInsert(item,handler);
		
	}

	@Override
	public User findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User asyncFindById(String id,final com.llanox.mobile.easyvote.data.DataSession.ResponseHandler<User> handler) {
		
		String whereClause = "id like '"+id+"'";
		BackendlessDataQuery dataQuery = new BackendlessDataQuery();
		dataQuery.setWhereClause( whereClause );
		
		Backendless.Persistence.of( User.class ).find( dataQuery, 
                new AsyncCallback<BackendlessCollection<User>>(){

					@Override
					public void handleFault(BackendlessFault fault) {					
						handler.error(fault.getMessage());						
					}

					@Override
					public void handleResponse(	BackendlessCollection<User> response) {						
						handler.succesfull(response.getData()!=null && response.getTotalObjects()>0?response.getData().get(0):null);
						
		  }} );
		return null;
	}

	@Override
	public List<User> findAll() {

		return null;
	}

	@Override
	public List<User> asyncFindAll(com.llanox.mobile.easyvote.data.DataSession.ResponseHandler<List<User>> handler) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
