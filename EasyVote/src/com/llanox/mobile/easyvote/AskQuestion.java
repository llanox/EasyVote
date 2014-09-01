package com.llanox.mobile.easyvote;

import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.BackendlessCallback;
import com.llanox.mobile.easyvote.data.DataLayerManager;
import com.llanox.mobile.easyvote.data.DataSession;
import com.llanox.mobile.easyvote.data.QuestionData;
import com.llanox.mobile.easyvote.model.Question;

public class AskQuestion extends Activity {

	protected static final String TAG = AskQuestion.class.getSimpleName();



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ask_question);
		
	
	
	}
	
	
	
	public void askQuestion(View view){
		
		//Save question and send broadcast
		String question = ((EditText) this.findViewById(R.id.et_question)).getText().toString();
		
		if(question.isEmpty()){
			Toast.makeText(this, R.string.toast_msg_requeried_text, Toast.LENGTH_LONG).show();
			return;
		}
		
		Question oQuestion = new Question();
		oQuestion.setCreator(null);
		oQuestion.setContent(question);
		
		DataSession<Question> data = new QuestionData(this);
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
