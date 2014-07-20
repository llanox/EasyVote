package com.llanox.mobile.easyvote;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjoe64.graphview.BarGraphView;
import com.jjoe64.graphview.CustomLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewDataInterface;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.GraphViewSeries.GraphViewSeriesStyle;
import com.jjoe64.graphview.LineGraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.llanox.mobile.easyvote.dummy.DummyContent;

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

	/**
	 * The dummy content this fragment is presenting.
	 */
	private DummyContent.DummyItem mItem;

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
			mItem = DummyContent.ITEM_MAP.get(getArguments().getString(
					ARG_ITEM_ID));
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_question_detail,
				container, false);

		// Show the dummy content as text in a TextView.
		if (mItem != null) {
			((TextView) rootView.findViewById(R.id.question_detail)).setText(mItem.content);
		}

		Context ctx = this.getActivity().getBaseContext();
//		// init example series data
//		GraphViewSeries exampleSeries = new GraphViewSeries(new GraphViewData[] {
//		    new GraphViewData(1, 2.0d)
//		    , new GraphViewData(2, 1.5d)
//		    , new GraphViewData(3, 2.5d)
//		    , new GraphViewData(4, 1.0d)
//		});
//		 

		
		GraphViewSeriesStyle seriesStyle = new GraphViewSeriesStyle();
		seriesStyle.setValueDependentColor(new ValueDependentColor() {
		  @Override
		  public int get(GraphViewDataInterface data) {
		    // the higher the more red
		    return Color.rgb((int)(150+((data.getY()/3)*100)), (int)(150-((data.getY()/3)*150)), (int)(150-((data.getY()/3)*150)));
		  }
		});
		GraphViewSeries exampleSeries2 = new GraphViewSeries("Respuestas", seriesStyle, new GraphViewData[] {
		      new GraphViewData(1, 10)
		    , new GraphViewData(2, 20)
		    , new GraphViewData(3, 15) 
		      // another frequency
		
		});
		
		GraphView graphView = new BarGraphView(
			    ctx // context
			    , "Pregunta "+mItem.id // heading
			);
		
		graphView.setCustomLabelFormatter(new CustomLabelFormatter() {
			  @Override
			  public String formatLabel(double value, boolean isValueX) {
			    if (isValueX) {
			     
			    	if (value == 1) {
			          return "Sí";
			        }
			    	
			    	if (value == 2) {
				       return "No";
				    }
			    	
			    	if (value == 3) {
					   return "Ausente";
					}
			    
			      
			      
			      
			    }
			    return null; // let graphview generate Y-axis label for us
			  }
			});
		
		    
	
		
			graphView.addSeries(exampleSeries2); // data
			LinearLayout panelGraphics = (LinearLayout) rootView.findViewById(R.id.pnl_graphics);
			panelGraphics.addView(graphView);
		
		return rootView;
	}
}
