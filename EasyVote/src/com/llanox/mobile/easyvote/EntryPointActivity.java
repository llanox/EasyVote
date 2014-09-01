package com.llanox.mobile.easyvote;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.backendless.Backendless;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;


public class EntryPointActivity extends PlusBaseActivity  {




	// UI references.
	private View mProgressView;
	private SignInButton mPlusSignInButton;
	private View mSignOutButtons;
	private View mLoginFormView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_google);
		
		
	   String userId = AppSessionManager.getUserID(this);
		
		if(userId!=null){
			Intent intent = new Intent(this,QuestionListActivity.class);
			this.startActivity(intent);
			this.finish();
			return;
		}
		
		
	
		// Find the Google+ sign in button.
		mPlusSignInButton = (SignInButton) findViewById(R.id.plus_sign_in_button);
		if (supportsGooglePlayServices()) {
			// Set a listener to connect the user when the G+ button is clicked.
			mPlusSignInButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					signIn();
				}
			});
		} else {
			// Don't offer G+ sign in if the app's version is too low to support
			// Google Play
			// Services.
			mPlusSignInButton.setVisibility(View.GONE);
			return;
		}

		mLoginFormView = findViewById(R.id.login_form);
		mProgressView = findViewById(R.id.login_progress);

		mSignOutButtons = findViewById(R.id.plus_sign_out_buttons);
	}



	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	public void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});

			mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
			mProgressView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mProgressView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

	@Override
	protected void onPlusClientSignIn() {
		// Set up sign out and disconnect buttons.
		Button signOutButton = (Button) findViewById(R.id.plus_sign_out_button);
		signOutButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				signOut();
			}
		});
		Button disconnectButton = (Button) findViewById(R.id.plus_disconnect_button);
		disconnectButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				revokeAccess();
			}
		});
		
		
		
		
		startApp();
	}

	@Override
	protected void onPlusClientBlockingUI(boolean show) {
		showProgress(show);
	}

	@Override
	protected void updateConnectButtonState() {
		// TODO: Update this logic to also handle the user logged in by email.
		boolean connected = getPlusClient().isConnected();

		mSignOutButtons.setVisibility(connected ? View.VISIBLE : View.GONE);
		mPlusSignInButton.setVisibility(connected ? View.GONE : View.VISIBLE);
	
	}

	@Override
	protected void onPlusClientRevokeAccess() {
		// TODO: Access to the user's G+ account has been revoked. Per the
		// developer terms, delete
		// any stored user data here.
		
		
	}

	@Override
	protected void onPlusClientSignOut() {

	}

	/**
	 * Check if the device supports Google Play Services. It's best practice to
	 * check first rather than handling this as an error case.
	 * 
	 * @return whether the device supports Google Play Services
	 */
	private boolean supportsGooglePlayServices() {
		return GooglePlayServicesUtil.isGooglePlayServicesAvailable(this) == ConnectionResult.SUCCESS;
	}


	@Override
	public void onConnectionSuspended(int cause) {
	
		if(CAUSE_NETWORK_LOST == cause){
			Toast.makeText(this, "Connection suspended", Toast.LENGTH_LONG).show();
			return;
		}
		
		if(CAUSE_SERVICE_DISCONNECTED == cause){			
			Toast.makeText(this, "Service disconnected", Toast.LENGTH_LONG).show();
			return;
		}
		
		
		
	}

	private void startApp() {
		Intent intent = new Intent(EntryPointActivity.this,QuestionListActivity.class);
		Bundle data = new Bundle();			
		intent.putExtras(data);
		EntryPointActivity.this.startActivity(intent);			
		EntryPointActivity.this.finish();
	}



}
