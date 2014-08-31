package com.llanox.mobile.easyvote;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;
import com.llanox.mobile.easyvote.model.User;

public class LoginActivity extends Activity {

	public static final String TAG = LoginActivity.class.getSimpleName();



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		Backendless.initApp( this, AppCredentials.APPLICATION_KEY, AppCredentials.SECRET_KEY, AppCredentials.APP_VERSION );
		

		
	}
	
	
	
	public void validateUser(View view){
	
		
		Toast.makeText(this,R.string.toast_msg_user_auth, Toast.LENGTH_LONG).show();
		
		String user =  ((EditText)this.findViewById(R.id.ed_user)).getText().toString();
		
		String whereClause = "username like '"+user+"'";
		BackendlessDataQuery dataQuery = new BackendlessDataQuery();
		dataQuery.setWhereClause( whereClause );
		
		Backendless.Persistence.of( User.class ).find( dataQuery, 
                new AsyncCallback<BackendlessCollection<User>>(){

					@Override
					public void handleFault(BackendlessFault fault) {
					
						Toast.makeText(LoginActivity.this,R.string.toast_err_user_auth, Toast.LENGTH_LONG).show();
						
					}

					@Override
					public void handleResponse(	BackendlessCollection<User> response) {
						
						if(response.getData()!= null && response.getData().size()>0){
						   foundUser(response.getData().get(0));					
						}
					}} );
		
			
		
	
		
		
		
	}



	protected void foundUser(User user) {
		
		
		
		if("admin".equalsIgnoreCase(user.getRole())){			
			return;
		}
		
		if(ConstantsEasyVote.MODERATOR_ROLE.equalsIgnoreCase(user.getRole())){	
			
			Intent intent = new Intent(this,QuestionListActivity.class);
			Bundle data = new Bundle();			
			data.putCharSequence(ConstantsEasyVote.ROLE_KEY, user.getRole());
			
			saveSession(user);
			
			intent.putExtras(data);
			this.startActivity(intent);			
			this.finish();
			return;			
		}
		
		if(ConstantsEasyVote.VOTER_ROLE.equalsIgnoreCase(user.getRole())){			
			Intent intent = new Intent(this,AnswerQuestionListActivity.class);
			Bundle data = new Bundle();
			data.putCharSequence(ConstantsEasyVote.ROLE_KEY, user.getRole());
			saveSession(user);
			intent.putExtras(data);
			this.startActivity(intent);			
			this.finish();
			return;
		}
		
	}



	private void saveSession(User user) {
	 
		SharedPreferences preferences = this.getSharedPreferences(ConstantsEasyVote.SHARED_PREF_NAME, Activity.MODE_MULTI_PROCESS);
		Editor ed = preferences.edit();
		ed.putString(ConstantsEasyVote.USER_SESSION, user.getUsername());
		ed.putInt(ConstantsEasyVote.USER_WEIGHT, user.getWeight());
		
		ed.commit();
		
	}
}
