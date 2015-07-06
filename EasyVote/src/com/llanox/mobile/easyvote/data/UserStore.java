package com.llanox.mobile.easyvote.data;

import android.content.Context;
import android.database.SQLException;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.async.callback.BackendlessCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;
import com.llanox.mobile.easyvote.model.UserModel;


import java.util.List;

public class UserStore implements StoreSession<UserModel> {

    private DataConnectionManager dataManager;
	
	public UserStore(Context ctx) {
		dataManager = DataConnectionManager.getInstance(ctx);
	}

	@Override
	public void open() throws SQLException {
        dataManager.start();
		
	}

	@Override
	public void close() {
        dataManager.close();
	}

	@Override
	public void insert(UserModel item) {

       // Backendless.Persistence.save(item);
	}

	@Override
	public void asyncInsert(final UserModel item, final ResponseHandler<UserModel> handler) {

        BackendlessUser user = new BackendlessUser();
        user.setProperty( "email", item.getEmail() );
        user.setPassword( item.getPassword() );


        Backendless.UserService.register( user, new AsyncCallback<BackendlessUser>()
        {
            public void handleResponse( BackendlessUser registeredUser )
            {
                handler.succesfull(item);
            }

            public void handleFault( BackendlessFault fault )
            {
                handler.error(fault.getMessage());
            }
        } );



		
	}

	@Override
	public void update(UserModel item) {
	//	insert(item);
		
	}

	@Override
	public void asyncUpdate(UserModel item, StoreSession.ResponseHandler<UserModel> handler) {
	//	asyncInsert(item,handler);
		
	}

	@Override
	public UserModel findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void asyncFindById(String id,final StoreSession.ResponseHandler<UserModel> handler) {
		
//		String whereClause = "id like '"+id+"'";
//		BackendlessDataQuery dataQuery = new BackendlessDataQuery();
//		dataQuery.setWhereClause( whereClause );
//
//		Backendless.Persistence.of( UserModel.class ).find( dataQuery,
//                new AsyncCallback<BackendlessCollection<UserModel>>(){
//
//					@Override
//					public void handleFault(BackendlessFault fault) {
//						handler.error(fault.getMessage());
//					}
//
//					@Override
//					public void handleResponse(	BackendlessCollection<UserModel> response) {
//						handler.succesfull(response.getData()!=null && response.getTotalObjects()>0?response.getData().get(0):null);
//
//		  }} );


	}

	@Override
	public List<UserModel> findAll() {

		return null;
	}

	@Override
	public List<UserModel> asyncFindAll(StoreSession.ResponseHandler<List<UserModel>> handler) {
		// TODO Auto-generated method stub
		return null;
	}


    public void logIn(String user, String password,final StoreSession.ResponseHandler<String> handler){

        Backendless.UserService.login(user,password, new AsyncCallback<BackendlessUser>() {
            @Override
            public void handleResponse(BackendlessUser backendlessUser) {
                handler.succesfull(backendlessUser.getEmail());
            }

            @Override
            public void handleFault(BackendlessFault backendlessFault) {

                backendlessFault.getMessage();
                handler.error(backendlessFault.getMessage());
            }


        }, true);

    }

	

}
