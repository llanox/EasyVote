package com.llanox.mobile.easyvote.data;

import java.util.Date;

import android.content.Context;
import android.database.SQLException;

public class AnswerQuestionData extends DataAccess implements DataSession {

	
	private static final String TAG = AnswerQuestionData.class.getSimpleName();
	
	private Context mCtx;
	

	
	public static final String TABLE = "answers";
	public static final String C_ID = "_id";
	public static final String C_VOTER = "voter";
	public static final String C_QUESTION_ID = "question_id";
	public static final String C_ANSWER_DATE = "answer_date";
	public static final String C_ANSWER = "answer";
	
	
	
	public static final String[] COLUMNS ={C_ID,
											C_VOTER,
											C_QUESTION_ID,
											C_ANSWER_DATE,										 
	};
	

		
	public AnswerQuestionData(Context ctx) { 
		super(ctx,TABLE,COLUMNS);
	}
	
	
	public void open() throws SQLException {		
		mDb = mDbHelper.getWritableDatabase();		
	}
	
	public void close() { 
		mDbHelper.close();
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	@Override
	public String getRestPath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getResourceName() {
		// TODO Auto-generated method stub
		return null;
	}

}
