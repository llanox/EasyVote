package com.llanox.mobile.easyvote;


import com.llanox.mobile.easyvote.data.UserData;
import com.llanox.mobile.easyvote.model.User;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * An activity representing a list of Questions. This activity has different
 * presentations for handset and tablet-size devices. On handsets, the activity
 * presents a list of items, which when touched, lead to a
 * {@link QuestionDetailActivity} representing item details. On tablets, the
 * activity presents the list of items and item details side-by-side using two
 * vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link QuestionListFragment} and the item details (if present) is a
 * {@link QuestionDetailFragment}.
 * <p>
 * This activity also implements the required
 * {@link QuestionListFragment.Callbacks} interface to listen for item
 * selections.
 */
public class QuestionListActivity extends Activity implements
		QuestionListFragment.Callbacks {

	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 * device.
	 */
	private boolean mTwoPane;
	private String role;
	

	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question_list);
        
	
		

		
		
	
		if (findViewById(R.id.question_detail_container) != null) {
			// The detail container view will be present only in the
			// large-screen layouts (res/values-large and
			// res/values-sw600dp). If this view is present, then the
			// activity should be in two-pane mode.
			mTwoPane = true;

			// In two-pane mode, list items should be given the
			// 'activated' state when touched.
			((QuestionListFragment) getFragmentManager().findFragmentById(
					R.id.question_list)).setActivateOnItemClick(true);
		}

		
	
		
		
	}
	


	/**
	 * Callback method from {@link QuestionListFragment.Callbacks} indicating
	 * that the item with the given ID was selected.
	 */
	@Override
	public void onItemSelected(String id) {
		if (mTwoPane) {
			// In two-pane mode, show the detail view in this activity by
			// adding or replacing the detail fragment using a
			// fragment transaction.
			Bundle arguments = new Bundle();
			arguments.putString(QuestionDetailFragment.ARG_ITEM_ID, id);
		
			
			
			Fragment fragment = null;
			int idFragment = 0;
			
		
			fragment = new QuestionDetailFragment();
			idFragment = R.id.question_detail_container;
						
			fragment.setArguments(arguments);
			getFragmentManager().beginTransaction().replace(idFragment, fragment).commit();

		} else {
			// In single-pane mode, simply start the detail activity
			// for the selected item ID.
			
			Intent detailIntent = null;
			detailIntent = new Intent(this, QuestionDetailActivity.class);			
			detailIntent.putExtra(QuestionDetailFragment.ARG_ITEM_ID, id);

			startActivity(detailIntent);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu_actions, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_new:
	            openNewQuestion();
	            return true;
	       
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	private void openNewQuestion() {
		Intent detailIntent = null;
		detailIntent = new Intent(this, AskQuestion.class);
		startActivity(detailIntent);
		this.finish();
	}
}
