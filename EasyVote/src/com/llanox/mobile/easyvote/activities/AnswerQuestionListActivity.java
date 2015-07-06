package com.llanox.mobile.easyvote.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.llanox.mobile.easyvote.fragments.AnswerQuestionDetailFragment;
import com.llanox.mobile.easyvote.fragments.AnswerQuestionListFragment;
import com.llanox.mobile.easyvote.R;

/**
 * An activity representing a list of AnswerQuestions. This activity has
 * different presentations for handset and tablet-size devices. On handsets, the
 * activity presents a list of items, which when touched, lead to a
 * {@link AnswerQuestionDetailActivity} representing item details. On tablets,
 * the activity presents the list of items and item details side-by-side using
 * two vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link com.llanox.mobile.easyvote.fragments.AnswerQuestionListFragment} and the item details (if present) is a
 * {@link com.llanox.mobile.easyvote.fragments.AnswerQuestionDetailFragment}.
 * <p>
 * This activity also implements the required
 * {@link com.llanox.mobile.easyvote.fragments.AnswerQuestionListFragment.Callbacks} interface to listen for item
 * selections.
 */
public class AnswerQuestionListActivity extends Activity implements
        AnswerQuestionListFragment.Callbacks {

	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 * device.
	 */
	private boolean mTwoPane;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_answerquestion_list);

		if (findViewById(R.id.answerquestion_detail_container) != null) {
			// The detail container view will be present only in the
			// large-screen layouts (res/values-large and
			// res/values-sw600dp). If this view is present, then the
			// activity should be in two-pane mode.
			mTwoPane = true;

			// In two-pane mode, list items should be given the
			// 'activated' state when touched.
			((AnswerQuestionListFragment) getFragmentManager()
					.findFragmentById(R.id.answerquestion_list))
					.setActivateOnItemClick(true);
		}

		// TODO: If exposing deep links into your app, handle intents here.
	}

	/**
	 * Callback method from {@link AnswerQuestionListFragment.Callbacks}
	 * indicating that the item with the given ID was selected.
	 */
	@Override
	public void onItemSelected(String id) {
		if (mTwoPane) {
			// In two-pane mode, show the detail view in this activity by
			// adding or replacing the detail fragment using a
			// fragment transaction.
			Bundle arguments = new Bundle();
			arguments.putString(AnswerQuestionDetailFragment.ARG_ITEM_ID, id);
			AnswerQuestionDetailFragment fragment = new AnswerQuestionDetailFragment();
			fragment.setArguments(arguments);
			getFragmentManager().beginTransaction()
					.replace(R.id.answerquestion_detail_container, fragment)
					.commit();

		} else {
			// In single-pane mode, simply start the detail activity
			// for the selected item ID.
			Intent detailIntent = new Intent(this,
					AnswerQuestionDetailActivity.class);
			detailIntent.putExtra(AnswerQuestionDetailFragment.ARG_ITEM_ID, id);
			startActivity(detailIntent);
		}
	}
}
