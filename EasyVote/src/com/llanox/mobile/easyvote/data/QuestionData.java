package com.llanox.mobile.easyvote.data;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import co.com.abakosoluciones.mobile.util.DateTimeUtils;

import com.llanox.mobile.easyvote.model.Question;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class QuestionData extends DataAccess implements DataSession {
	
	private static final String TAG = QuestionData.class.getSimpleName();
	
	private Context mCtx;
	
	
	public static final String TABLE = "questions";
	public static final String C_ID = "_id";
	public static final String C_CREATOR = "creator";
	public static final String C_CONTENT = "content";
	public static final String C_CREATION_DATE = "creation_date";
	
	
	
	public static final String[] COLUMNS ={C_ID,
										   C_CREATOR,
										   C_CONTENT,
										   C_CREATION_DATE,										 
	};
	

		
	public QuestionData(Context ctx) { 
		super(ctx,TABLE,COLUMNS);
	}
	
	
	public void open() throws SQLException {		
		mDb = mDbHelper.getWritableDatabase();		
	}
	
	public void close() { 
		mDbHelper.close();
	}

	
	
	public void insert(Question question) { 
	
	ContentValues values = new ContentValues();		
	values.put(C_CREATOR, question.getCreator());
	values.put(C_CONTENT, question.getContent());
	values.put(C_CREATION_DATE,DateTimeUtils.changeDateToFormatString(question.getCreationDate(),DateTimeUtils.FORMAT_DATETIME_SHORT));
	
	Log.d(TAG, "insert on " + values);
	
	long id = mDb.insert(TABLE, null, values);
	question.setId(id);

}
	
	

	
	public List<Question> findAll() {
		List<Question> questions = new ArrayList<Question>();
		
		Cursor cursor = mDb.query(TABLE, columns, null, null, null,	null, null);
		int rows = cursor.getCount();
		Question question = null;
		
		while (cursor.moveToNext()) {
			question = new Question();
			extractQuestion(cursor, question);			
			questions.add(question);
		}
		
		return questions;
		
	}


	private void extractQuestion(Cursor cursor, Question question) {
		question.setContent(cursor.getString(cursor.getColumnIndex(C_CONTENT)));
		question.setId(cursor.getLong(cursor.getColumnIndex(C_ID)));
		question.setCreator(cursor.getString(cursor.getColumnIndex(C_CREATOR)));
		String dateString = cursor.getString(cursor.getColumnIndex(C_CREATION_DATE));
		Date creationDate;
		try {
			creationDate = DateTimeUtils.changeStringToDate(dateString,DateTimeUtils.FORMAT_DATETIME_SHORT);
		} catch (ParseException e) {
			throw new DataException("Error parsing string creation date table "+TABLE);
		}
		question.setCreationDate(creationDate);

	} 
	
	
	
	public Question findQuestionById(String id){
		
		Question question = null;
		String selectionClause = String.format(C_ID +"= %s",id);
		Cursor cursor = mDb.query(TABLE, columns,selectionClause, null, null,	null, null);
		
		while (cursor.moveToNext()) {
			question = new Question();
			extractQuestion(cursor, question);		
		}
		
		return question;
		
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
