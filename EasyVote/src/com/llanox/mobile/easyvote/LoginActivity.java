package com.llanox.mobile.easyvote;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
				
	}
	
	
	
	public void validateUser(View view){
		
		String user =  ((EditText)this.findViewById(R.id.ed_user)).getText().toString();
		
		if("admin".equalsIgnoreCase(user)){			
			return;
		}
		
		if("moderator".equalsIgnoreCase(user)){	
			
			Intent intent = new Intent(this,QuestionListActivity.class);
			Bundle data = new Bundle();
			data.putCharSequence("role", "moderator");
			intent.putExtras(data);
			this.startActivity(intent);			
			this.finish();
			return;			
		}
		
		if("voter".equalsIgnoreCase(user)){			
			Intent intent = new Intent(this,AnswerQuestionListActivity.class);
			Bundle data = new Bundle();
			data.putCharSequence("role", "voter");
			intent.putExtras(data);
			this.startActivity(intent);			
			this.finish();
			return;
		}
		
		
		
	}
}
