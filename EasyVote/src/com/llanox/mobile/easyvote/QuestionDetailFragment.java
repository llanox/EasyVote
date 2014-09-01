package com.llanox.mobile.easyvote;

import java.util.List;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;
import com.jjoe64.graphview.BarGraphView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewDataInterface;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.GraphViewSeries.GraphViewSeriesStyle;
import com.jjoe64.graphview.ValueDependentColor;
import com.llanox.mobile.easyvote.data.DataLayerManager;
import com.llanox.mobile.easyvote.data.DataSession;
import com.llanox.mobile.easyvote.data.QuestionData;
import com.llanox.mobile.easyvote.model.AnswerQuestion;
import com.llanox.mobile.easyvote.model.Question;

/**
 * A fragment representing a single Question detail screen. This fragment is
 * either contained in a {@link QuestionListActivity} in two-pane mode (on
 * tablets) or a {@link QuestionDetailActivity} on handsets.
 */
public class QuestionDetailFragment extends Fragment {
	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_ITEM_ID = "item_id";

	private View mRootView;
	private Question mItem;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public QuestionDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments().containsKey(ARG_ITEM_ID)) {
			// Load the dummy content specified by the fragment
			// arguments. In a real-world scenario, use a Loader
			// to load content from a content provider.
			String idItem = getArguments().getString(	ARG_ITEM_ID);
		
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mRootView = inflater.inflate(R.layout.fragment_question_detail,
				container, false);
		
		
		String idItem = getArguments().getString(ARG_ITEM_ID);
		DataSession<Question> data = new QuestionData(getActivity());
		mItem = data.findById(idItem);
		
		if (mItem != null) {			
			
			((TextView) mRootView.findViewById(R.id.question_detail)).setText(mItem.getContent());
			retrieveAnswers(mItem.getObjectId());
		}

	

		
		return mRootView;
	}

	private void retrieveAnswers(String id) {
	
		String whereClause = "questionId = "+id;
		BackendlessDataQuery dataQuery = new BackendlessDataQuery();
		dataQuery.setWhereClause( whereClause );
		
		Backendless.Persistence.of( AnswerQuestion.class ).find( dataQuery, 
                new AsyncCallback<BackendlessCollection<AnswerQuestion>>(){

					@Override
					public void handleFault(BackendlessFault fault) {					
						Toast.makeText(getActivity(), R.string.toast_err_retrieving_data, Toast.LENGTH_LONG).show();
						
					}

					@Override
					public void handleResponse(	BackendlessCollection<AnswerQuestion> response) {
						
						updatedAnswers(response.getData());					
						
					}} );
		
		
	}

	protected void updatedAnswers(List<AnswerQuestion> data) {

		Context ctx = this.getActivity().getBaseContext();
		
		GraphViewSeriesStyle seriesStyle = new GraphViewSeriesStyle();
		seriesStyle.setValueDependentColor(new ValueDependentColor() {
		  @Override
		  public int get(GraphViewDataInterface data) {
		    // the higher the more red
		    return Color.rgb((int)(150+((data.getY()/3)*100)), (int)(150-((data.getY()/3)*150)), (int)(150-((data.getY()/3)*150)));
		  }
		});
		
		GraphViewData[] viewData = createViewData(data);
		
		GraphViewSeries dataSeries = new GraphViewSeries("Estadisticas", seriesStyle,viewData);
		
		GraphView graphView = new BarGraphView(
			    ctx // context
			    , mItem.getContent() // heading
			);
		
		graphView.setHorizontalLabels(new String[] {"Sí", "No", "Blanco"});

		
		    
	
		
			graphView.addSeries(dataSeries); // data
			LinearLayout panelGraphics = (LinearLayout) mRootView.findViewById(R.id.pnl_graphics);
			panelGraphics.addView(graphView);
		
	}

	private GraphViewData[] createViewData(List<AnswerQuestion> data) {
		
		GraphViewData[] viewData = new GraphViewData[data.size()];
		int[] grupos = new int[4];
		
		for(AnswerQuestion answer : data){
			
			  if("Sí".equalsIgnoreCase(answer.getAnswer())){
				  grupos[0] = answer.getPoints()+ grupos[0]; 
			  }
			  
			  if("No".equalsIgnoreCase(answer.getAnswer())){
				  grupos[1] = answer.getPoints()+ grupos[1]; 
			  }
			 
			  if("En Blanco".equalsIgnoreCase(answer.getAnswer())){
				  grupos[2] = answer.getPoints()+ grupos[2]; 
			  }
			
		}
		
		return new GraphViewData[] {
			      new GraphViewData(1, grupos[0])
			    , new GraphViewData(2, grupos[1])
			    , new GraphViewData(3, grupos[2]) };
			      // another frequency
		
	}
}
