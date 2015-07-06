package com.llanox.mobile.easyvote.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.llanox.mobile.easyvote.R;
import com.llanox.mobile.easyvote.data.QuestionStore;
import com.llanox.mobile.easyvote.data.StoreSession;
import com.llanox.mobile.easyvote.model.QuestionModel;


public class AskQuestionActivity extends Activity {

	protected static final String TAG = AskQuestionActivity.class.getSimpleName();



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ask_question);
		
	
	
	}
	
	
	
	public void askQuestionModel(View view){
		
		//Save QuestionModel and send broadcast
		String questionText = ((EditText) this.findViewById(R.id.et_question)).getText().toString();
		
		if(questionText.isEmpty()){
			Toast.makeText(this, R.string.toast_msg_requeried_text, Toast.LENGTH_LONG).show();
			return;
		}
		
		QuestionModel oQuestion = new QuestionModel();
        oQuestion.setCreator(null);
        oQuestion.setContent(questionText);
		
		StoreSession<QuestionModel> data = new QuestionStore(this);
		data.insert(oQuestion);
	
		Toast.makeText(this, R.string.toast_msg_saved_question, Toast.LENGTH_LONG).show();
		Intent intent = new Intent(this,QuestionListActivity.class);
		this.startActivity(intent);
		this.finish();
		
	}



	@Override
	public void onBackPressed() {
				
		Intent intent = new Intent(this,QuestionListActivity.class);
		this.startActivity(intent);
		this.finish();
	}
	
	
	
}
