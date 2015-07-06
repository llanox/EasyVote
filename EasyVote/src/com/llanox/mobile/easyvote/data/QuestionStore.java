package com.llanox.mobile.easyvote.data;

import java.util.List;

import android.content.Context;
import android.database.SQLException;

import com.llanox.mobile.easyvote.model.QuestionModel;

public class QuestionStore implements StoreSession<QuestionModel> {

	public QuestionStore(Context ctx) {
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
	public void insert(QuestionModel item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void asyncInsert(
            QuestionModel item,
			StoreSession.ResponseHandler<QuestionModel> handler) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(QuestionModel item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void asyncUpdate(
            QuestionModel item,
			StoreSession.ResponseHandler<QuestionModel> handler) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public QuestionModel findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void asyncFindById(
			String id,
			StoreSession.ResponseHandler<QuestionModel> handler) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<QuestionModel> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<QuestionModel> asyncFindAll(
			StoreSession.ResponseHandler<List<QuestionModel>> handler) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
