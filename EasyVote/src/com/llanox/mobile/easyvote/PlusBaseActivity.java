package com.llanox.mobile.easyvote;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.PlusClient;
import com.google.android.gms.plus.model.people.Person;
import com.llanox.mobile.easyvote.data.DataSession.ResponseHandler;
import com.llanox.mobile.easyvote.data.UserData;
import com.llanox.mobile.easyvote.model.User;

/**
 * A base class to wrap communication with the Google Play Services PlusClient.
 */
public abstract class PlusBaseActivity extends Activity implements
//		GooglePlayServicesClient.ConnectionCallbacks,
//		GooglePlayServicesClient.OnConnectionFailedListener 
ConnectionCallbacks, OnConnectionFailedListener
		
		{

	

	public static final String SESSION_SETTINGS_PREF = "session_settings_pref";

	private static final String TAG = PlusBaseActivity.class.getSimpleName();

	// A magic number we will use to know that our sign-in error resolution
	// activity has completed
	private static final int OUR_REQUEST_CODE = 49404;

	// A flag to stop multiple dialogues appearing for the user
	private boolean mAutoResolveOnFail;

	// A flag to track when a connection is already in progress
	public boolean mPlusClientIsConnecting = false;

	// This is the helper object that connects to Google Play Services.
	private GoogleApiClient mPlusClient;

	// The saved result from {@link #onConnectionFailed(ConnectionResult)}. If a
	// connection
	// attempt has been made, this is non-null.
	// If this IS null, then the connect method is still running.
	private ConnectionResult mConnectionResult;

	/**
	 * Called when the {@link PlusClient} revokes access to this app.
	 */
	protected abstract void onPlusClientRevokeAccess();

	/**
	 * Called when the PlusClient is successfully connected.
	 */
	protected abstract void onPlusClientSignIn();

	/**
	 * Called when the {@link PlusClient} is disconnected.
	 */
	protected abstract void onPlusClientSignOut();

	/**
	 * Called when the {@link PlusClient} is blocking the UI. If you have a
	 * progress bar widget, this tells you when to show or hide it.
	 */
	protected abstract void onPlusClientBlockingUI(boolean show);

	/**
	 * Called when there is a change in connection state. If you have "Sign in"/
	 * "Connect", "Sign out"/ "Disconnect", or "Revoke access" buttons, this
	 * lets you know when their states need to be updated.
	 */
	protected abstract void updateConnectButtonState();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Initialize the PlusClient connection.
		// Scopes indicate the information about the user your application will
		// be able to access.
		mPlusClient =  new GoogleApiClient.Builder(this)
        .addConnectionCallbacks(this)
        .addOnConnectionFailedListener(this)
        .addApi(Plus.API)
        .addScope(Plus.SCOPE_PLUS_LOGIN)
        .build();
	}

	/**
	 * Try to sign in the user.
	 */
	public void signIn() {
		if (!mPlusClient.isConnected()) {
			// Show the dialog as we are now signing in.
			setProgressBarVisible(true);
			// Make sure that we will start the resolution (e.g. fire the intent
			// and pop up a
			// dialog for the user) for any errors that come in.
			mAutoResolveOnFail = true;
			// We should always have a connection result ready to resolve,
			// so we can start that process.
			if (mConnectionResult != null) {
				startResolution();
			} else {
				// If we don't have one though, we can start connect in
				// order to retrieve one.
				initiatePlusClientConnect();
			}
		}

		updateConnectButtonState();
	}

	/**
	 * Connect the {@link PlusClient} only if a connection isn't already in
	 * progress. This will call back to {@link #onConnected(android.os.Bundle)}
	 * or
	 * {@link #onConnectionFailed(com.google.android.gms.common.ConnectionResult)}
	 * .
	 */
	private void initiatePlusClientConnect() {
		if (!mPlusClient.isConnected() && !mPlusClient.isConnecting()) {
			mPlusClient.connect();
		}
	}

	/**
	 * Disconnect the {@link PlusClient} only if it is connected (otherwise, it
	 * can throw an error.) This will call back to {@link #onDisconnected()}.
	 */
	private void initiatePlusClientDisconnect() {
		if (mPlusClient.isConnected()) {
			mPlusClient.disconnect();
		}
	}

	/**
	 * Sign out the user (so they can switch to another account).
	 */
	public void signOut() {

		// We only want to sign out if we're connected.
		if (mPlusClient.isConnected()) {
		
			// Disconnect from Google Play Services, then reconnect in order to
			// restart the
			// process from scratch.
			initiatePlusClientDisconnect();

			Log.v(TAG, "Sign out successful!");
		}

		updateConnectButtonState();
	}

	/**
	 * Revoke Google+ authorization completely.
	 */
	public void revokeAccess() {

		if (mPlusClient.isConnected()) {
			// Clear the default account as in the Sign Out.
			mPlusClient.disconnect();

		}

	}

	@Override
	protected void onStart() {
		super.onStart();
		initiatePlusClientConnect();
	}

	@Override
	protected void onStop() {
		super.onStop();
		initiatePlusClientDisconnect();
	}

	public boolean isPlusClientConnecting() {
		return mPlusClientIsConnecting;
	}

	private void setProgressBarVisible(boolean flag) {
		mPlusClientIsConnecting = flag;
		onPlusClientBlockingUI(flag);
	}

	/**
	 * A helper method to flip the mResolveOnFail flag and start the resolution
	 * of the ConnectionResult from the failed connect() call.
	 */
	private void startResolution() {
		try {
			// Don't start another resolution now until we have a result from
			// the activity we're
			// about to start.
			mAutoResolveOnFail = false;
			// If we can resolve the error, then call start resolution and pass
			// it an integer tag
			// we can use to track.
			// This means that when we get the onActivityResult callback we'll
			// know it's from
			// being started here.
			mConnectionResult.startResolutionForResult(this, OUR_REQUEST_CODE);
		} catch (IntentSender.SendIntentException e) {
			// Any problems, just try to connect() again so we get a new
			// ConnectionResult.
			mConnectionResult = null;
			initiatePlusClientConnect();
		}
	}

	/**
	 * An earlier connection failed, and we're now receiving the result of the
	 * resolution attempt by PlusClient.
	 * 
	 * @see #onConnectionFailed(ConnectionResult)
	 */
	@Override
	protected void onActivityResult(int requestCode, int responseCode,
			Intent intent) {
		updateConnectButtonState();
		if (requestCode == OUR_REQUEST_CODE && responseCode == RESULT_OK) {
			// If we have a successful result, we will want to be able to
			// resolve any further
			// errors, so turn on resolution with our flag.
			mAutoResolveOnFail = true;
			// If we have a successful result, let's call connect() again. If
			// there are any more
			// errors to resolve we'll get our onConnectionFailed, but if not,
			// we'll get onConnected.
			initiatePlusClientConnect();
		} else if (requestCode == OUR_REQUEST_CODE && responseCode != RESULT_OK) {
			// If we've got an error we can't resolve, we're no longer in the
			// midst of signing
			// in, so we can stop the progress spinner.
			setProgressBarVisible(false);
		}
	}

	/**
	 * Successfully connected (called by PlusClient)
	 */
	@Override
	public void onConnected(Bundle connectionHint) {
		updateConnectButtonState();
		setProgressBarVisible(false);
		saveSession();
		onPlusClientSignIn();
		
	}

	private void saveSession() {
		Person person = Plus.PeopleApi.getCurrentPerson(mPlusClient);
		String email = Plus.AccountApi.getAccountName(mPlusClient)!=null? Plus.AccountApi.getAccountName(mPlusClient):"Without email";
		String id = person.getId();
		String nickName = person.hasNickname()? person.getNickname():"Without Nickname";
		String fullname = person.hasDisplayName()? person.getDisplayName():"Without Display Name";
		
		AppSessionManager.saveUserSession(this,id,
				nickName,
			    fullname,	
			    email				    	
				);
		
		User user = new User(id,nickName,null,fullname,email,User.GOOGLE_PLUS_USER_ROLE);
		UserData data = new UserData(this);
		data.asyncInsert(user, new ResponseHandler<User>() {
			
			@Override
			public void succesfull(User item) {
				Toast.makeText(PlusBaseActivity.this, "User saved succesfully", Toast.LENGTH_LONG).show();				
			}
			
			@Override
			public void error(Object error) {
				Toast.makeText(PlusBaseActivity.this, "Error "+error, Toast.LENGTH_LONG).show();	
				
			}
		});
		
	}

	/**
	 * Successfully disconnected (called by PlusClient)
	 */
	public void onDisconnected() {
		updateConnectButtonState();
		onPlusClientSignOut();
		removeSession();
	}

	private void removeSession() {
	
		AppSessionManager.removeUserSession(this);
	}

	/**
	 * Connection failed for some reason (called by PlusClient) Try and resolve
	 * the result. Failure here is usually not an indication of a serious error,
	 * just that the user's input is needed.
	 * 
	 * @see #onActivityResult(int, int, Intent)
	 */
	@Override
	public void onConnectionFailed(ConnectionResult result) {
		updateConnectButtonState();

		// Most of the time, the connection will fail with a user resolvable
		// result. We can store
		// that in our mConnectionResult property ready to be used when the user
		// clicks the
		// sign-in button.
		if (result.hasResolution()) {
			mConnectionResult = result;
			if (mAutoResolveOnFail) {
				// This is a local helper function that starts the resolution of
				// the problem,
				// which may be showing the user an account chooser or similar.
				startResolution();
			}
		}
	}

	public GoogleApiClient getPlusClient() {
		return mPlusClient;
	}

}
