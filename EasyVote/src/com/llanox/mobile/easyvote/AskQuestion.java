package com.llanox.mobile.easyvote;

import android.app.Activity;
import android.content.Intent;
import android.net.NetworkInfo.DetailedState;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class AskQuestion extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ask_question);
	}
	
	
	
	public void askQuestion(View view){
		
		Intent intent = new Intent(this,QuestionListActivity.class);
		this.startActivity(intent);
		
	}
	
}
