package com.llanox.mobile.easyvote.data;

import java.util.List;

import android.content.Context;
import android.database.SQLException;

import com.llanox.mobile.easyvote.model.Question;

public class QuestionData implements DataSession<Question> {

	public QuestionData(Context ctx) {
		// TODO Auto-generated constructor stub
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
	public void insert(Question item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void asyncInsert(
			Question item,
			com.llanox.mobile.easyvote.data.DataSession.ResponseHandler<Question> handler) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Question item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void asyncUpdate(
			Question item,
			com.llanox.mobile.easyvote.data.DataSession.ResponseHandler<Question> handler) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Question findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Question asyncFindById(
			String id,
			com.llanox.mobile.easyvote.data.DataSession.ResponseHandler<Question> handler) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Question> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Question> asyncFindAll(
			com.llanox.mobile.easyvote.data.DataSession.ResponseHandler<List<Question>> handler) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
