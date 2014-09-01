package com.llanox.mobile.easyvote;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.async.callback.BackendlessCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;
import com.llanox.mobile.easyvote.model.AnswerQuestion;
import com.llanox.mobile.easyvote.model.Question;
import com.llanox.mobile.easyvote.model.User;

/**
 * A fragment representing a single AnswerQuestion detail screen. This fragment
 * is either contained in a {@link AnswerQuestionListActivity} in two-pane mode
 * (on tablets) or a {@link AnswerQuestionDetailActivity} on handsets.
 */
public class AnswerQuestionDetailFragment extends Fragment {
	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_ITEM_ID = "item_id";
	public static final String TAG = AnswerQuestionDetailFragment.class.getSimpleName();
	
	private Question mItem;
	private Spinner spAnswer;
	private Button btnSendAnswer;
	private View mRootView;
	
	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public AnswerQuestionDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	
		mRootView = inflater.inflate(R.layout.fragment_answerquestion_detail, container, false);

		Context ctx = this.getActivity().getBaseContext();
		String idItem = getArguments().getString(ARG_ITEM_ID);
		
		validateQuestionStatus(idItem);
	
		btnSendAnswer = (Button) mRootView.findViewById(R.id.btn_send_answer);	
		spAnswer = (Spinner) mRootView.findViewById(R.id.spn_answers);

		
		
		btnSendAnswer.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			  sendAnswer();

			}
		});

		return mRootView;
	}

	private void validateQuestionStatus(String idItem) {

		User user = getCurrentUserSession();
		
		
		String whereClause = "id like '"+idItem+"'";
		BackendlessDataQuery dataQuery = new BackendlessDataQuery();
		dataQuery.setWhereClause( whereClause );
		
		Backendless.Persistence.of( Question.class ).find( dataQuery, 
                new AsyncCallback<BackendlessCollection<Question>>(){

					@Override
					public void handleFault(BackendlessFault fault) {
					
						Toast.makeText(AnswerQuestionDetailFragment.this.getActivity(),R.string.toast_err_retrieving_data, Toast.LENGTH_LONG).show();
						
					}

					@Override
					public void handleResponse(	BackendlessCollection<Question> response) {
						
						if(response.getData()!= null && response.getData().size()>0){
						   foundQuestion(response.getData().get(0));					
						}
		  }} );
		
		
		
	   			

		
		
	
		
	
     
		
	}

	protected void foundQuestion(Question question) {
		
		
		
		
		   mItem = question;
			if (mItem != null) {
				((TextView) mRootView.findViewById(R.id.question_title))
						.setText(mItem.getContent());
			}
			
			User user = getCurrentUserSession();
		 	String  whereClause = "questionId like '"+question.getObjectId()+"' and voter like '"+user.getUsername()+"'";
				
		 	BackendlessDataQuery	dataQuery = new BackendlessDataQuery();
		    dataQuery.setWhereClause( whereClause );
				
				Backendless.Persistence.of( AnswerQuestion.class ).find( dataQuery, 
		                new AsyncCallback<BackendlessCollection<AnswerQuestion>>(){

							@Override
							public void handleFault(BackendlessFault fault) {
							
								Toast.makeText(AnswerQuestionDetailFragment.this.getActivity(),R.string.toast_err_retrieving_data, Toast.LENGTH_LONG).show();
								
							}

							@Override
							public void handleResponse(	BackendlessCollection<AnswerQuestion> response) {
								
								if(response.getData()!= null && response.getData().size()>0){
								 
								   foundAnswerQuestion(response.getData().get(0));					
								}
				  }} );	
		
	}

	protected void foundAnswerQuestion(AnswerQuestion answerQuestion) {
		
		btnSendAnswer.setEnabled(false);
		
		if (mItem != null) {
			((TextView) mRootView.findViewById(R.id.question_title))
					.setText(mItem.getContent() );
		}
		
		String answer = answerQuestion.getAnswer();
		
		int count = spAnswer.getAdapter().getCount();
		
		for(int i =0; i<= count;i++){
			
			String iAnswer = spAnswer.getAdapter().getItem(i).toString();
			if(iAnswer.equalsIgnoreCase(answer)){
				spAnswer.setSelection(i,true);
				break;
			}
			
		}
		
		
		
		
	}

	protected void sendAnswer() {
		
		
		String answer = spAnswer.getSelectedItem().toString();

		AnswerQuestion answerQuestion = new AnswerQuestion();
        User user = getCurrentUserSession();
        
		answerQuestion.setVoter(user);
		answerQuestion.setAnswer(answer);
		
	
		
		Backendless.Persistence.save(answerQuestion,
				new BackendlessCallback<AnswerQuestion>() {
					@Override
					public void handleResponse(	AnswerQuestion answerQuestion) {
						Log.i("", "Question answered by "+ answerQuestion.getVoter());
						Toast.makeText(AnswerQuestionDetailFragment.this.getActivity(), R.string.toast_msg_answered_question, Toast.LENGTH_LONG).show();
						Intent intent = new Intent(AnswerQuestionDetailFragment.this.getActivity(),AnswerQuestionListActivity.class);
						
						AnswerQuestionDetailFragment.this.getActivity().startActivity(intent);
						AnswerQuestionDetailFragment.this.getActivity().finish();
						
						
					}
				});
		
	}

	private User getCurrentUserSession() {
		
		User user = new User();
		user.setId(AppSessionManager.getUserID(this.getActivity()));
	
		return user;
	}

}
