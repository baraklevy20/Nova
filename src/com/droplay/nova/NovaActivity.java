package com.droplay.nova;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.droplay.nova.game.NovaGameManager;
import com.droplay.nova.menu.NovaMenuActivity;
import com.droplay.nova.menu.NovaMenuGraphics;
import com.droplay.nova.menu.NovaMenuManager;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesStatusCodes;
import com.google.android.gms.games.leaderboard.LeaderboardVariant;
import com.google.android.gms.games.leaderboard.Leaderboards;
import com.google.example.games.basegameutils.BaseGameActivity;

public abstract class NovaActivity extends BaseGameActivity implements NovaConstants {
	protected NovaContext context;
	protected NovaGraphics graphicsView;
	protected NovaManager manager;
	private boolean isShowLeaderboard;
	private AdView adView;
	private boolean isInitialized;
	
	// Not all screen should show ads. Default is true
	protected boolean isShowAds = true;
	
	// Ads
	public static final String AD_UNIT_ID = "ca-app-pub-1600129559779321/6312829096";
	
	// Shared preferences
	public static final String GAME_PREFS = "GAME_PREFS"; // Name of the file
	public static final String GAME_PREFS_BEST_SCORE = "GAME_PREFS_BEST_SCORE";
	public static final String GAME_PREFS_SOUND = "GAME_PREFS_SOUND";
	public static final String GAME_PREFS_MUSIC = "GAME_PREFS_MUSIC";
	
	public View getAdView() {
		return adView;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getGameHelper().setConnectOnStart(false);
		getGameHelper().setMaxAutoSignInAttempts(0);
		setContext();
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		
		if (isShowAds) {
			adView = new AdView(this);
			adView.setAdSize(AdSize.SMART_BANNER); // Adapt the banner to the screen size
		    adView.setAdUnitId(AD_UNIT_ID);
		}

	    LinearLayout layout = new LinearLayout(this);
	    layout.setOrientation(LinearLayout.VERTICAL);
	    setContentView(layout);
	    if (isShowAds)
	    	layout.addView(adView);
	    layout.addView(graphicsView);
	    
	    if (isShowAds) {
			AdRequest adRequest = new AdRequest.Builder()
	        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
	        .addTestDevice("E6F3B1A44CC4F152A845F9F1DE53E41E")
	        .build();
			// Start loading the ad in the background.
		    adView.loadAd(adRequest);
	    }
	    
	    // Read shared prefs.
	    readParameters();
	}
	
	public void readParameters() {
		// If we're at the game screen, also load the best score
		if (manager instanceof NovaGameManager)
			((NovaGameManager)manager).setBestScore(getSharedPreferences(GAME_PREFS, Context.MODE_PRIVATE).getInt(GAME_PREFS_BEST_SCORE, 0));
		((NovaApplication)getApplication()).setMusicOn(getSharedPreferences(GAME_PREFS, Context.MODE_PRIVATE).getBoolean(GAME_PREFS_MUSIC, true));
		((NovaApplication)getApplication()).setSoundOn(getSharedPreferences(GAME_PREFS, Context.MODE_PRIVATE).getBoolean(GAME_PREFS_SOUND, true));
	}
	
	public void writeParameters() {
		Editor edit = getSharedPreferences(GAME_PREFS, Context.MODE_PRIVATE).edit();
		
		// If we're at the game screen, also save the best score
		if (manager instanceof NovaGameManager)
			edit.putInt(GAME_PREFS_BEST_SCORE, ((NovaGameManager)manager).getBestScore());
			
		edit.putBoolean(GAME_PREFS_SOUND, ((NovaApplication)getApplication()).isSoundOn())
		.putBoolean(GAME_PREFS_MUSIC, ((NovaApplication)getApplication()).isMusicOn())
		.apply();
	}
	
	public boolean isShowAds() {
		return isShowAds;
	}
	
	public void showLeaderboard() {
		isShowLeaderboard = true;
		if(getApiClient().isConnected()) {
			sendAndShowLeaderboard();
            isShowLeaderboard = false;
        }
		else
			beginUserInitiatedSignIn();
	}
	
	@Override
	protected void onActivityResult(int request, int response, Intent data) {
		if (request == REQUEST_CODE_LEADERSBOARD) {
			if (manager instanceof NovaMenuManager)
				((NovaMenuManager)manager).setButtonState(NovaMenuManager.ButtonState.NON);
			else if (manager instanceof NovaGameManager)
				((NovaGameManager)manager).setButtonState(NovaGameManager.ButtonState.NON);
			
			if (this instanceof NovaMenuActivity)
				((NovaMenuActivity)this).setCameFromActivity(true);
		}
		else
			super.onActivityResult(request, response, data);
	}
	
	public void sendAndShowLeaderboard() {
		int bestScore = getSharedPreferences(GAME_PREFS, Context.MODE_PRIVATE).getInt(GAME_PREFS_BEST_SCORE, 0); 
		Games.Leaderboards.submitScore(getApiClient(),
                getString(R.string.leaderboard_id),
                bestScore);
		loadScoreOfLeaderBoard(bestScore);
		
        startActivityForResult(Games.Leaderboards.getLeaderboardIntent(
                getApiClient(), getString(R.string.leaderboard_id)),
                REQUEST_CODE_LEADERSBOARD);
	}
	
	private void loadScoreOfLeaderBoard(final int currentBestScore) {
		// Eh, Google stuff
	    Games.Leaderboards.loadCurrentPlayerLeaderboardScore(getApiClient(), getString(R.string.leaderboard_id), LeaderboardVariant.TIME_SPAN_ALL_TIME, LeaderboardVariant.COLLECTION_PUBLIC).setResultCallback(new ResultCallback<Leaderboards.LoadPlayerScoreResult>() {
	        @Override
	        public void onResult(final Leaderboards.LoadPlayerScoreResult scoreResult) {
	            if (isScoreResultValid(scoreResult)) {
	            	int currentScore = (int)scoreResult.getScore().getRawScore();
	    
	            	if (currentScore > currentBestScore)
	            		getSharedPreferences(GAME_PREFS, Context.MODE_PRIVATE).edit().putInt(GAME_PREFS_BEST_SCORE, currentScore).commit();
	            }
	        }
	    });
	}

	private boolean isScoreResultValid(final Leaderboards.LoadPlayerScoreResult scoreResult) {
	    return scoreResult != null && GamesStatusCodes.STATUS_OK == scoreResult.getStatus().getStatusCode() && scoreResult.getScore() != null;
	}

	@Override
	public void onSignInFailed() {
		if (manager instanceof NovaMenuManager && ((NovaMenuManager)manager).getButtonState() == NovaMenuManager.ButtonState.SCORES) {
			((NovaMenuManager)manager).setButtonState(NovaMenuManager.ButtonState.NON);
			((NovaMenuGraphics)context.getGraphics()).setButtonState(NovaMenuManager.ButtonState.NON);
		}
		else if (manager instanceof NovaGameManager && ((NovaGameManager)manager).getButtonState() == NovaGameManager.ButtonState.SCORES)
			((NovaGameManager)manager).setButtonState(NovaGameManager.ButtonState.NON);
		
		if (this instanceof NovaMenuActivity)
			((NovaMenuActivity)this).setCameFromActivity(true);
	}

	@Override
	public void onSignInSucceeded() {
		if (manager instanceof NovaMenuManager && ((NovaMenuManager)manager).getButtonState() == NovaMenuManager.ButtonState.SCORES) {
			((NovaMenuManager)manager).setButtonState(NovaMenuManager.ButtonState.NON);
			((NovaMenuGraphics)context.getGraphics()).setButtonState(NovaMenuManager.ButtonState.NON);
		}
		else if (manager instanceof NovaGameManager && ((NovaGameManager)manager).getButtonState() == NovaGameManager.ButtonState.SCORES)
			((NovaGameManager)manager).setButtonState(NovaGameManager.ButtonState.NON);
		
		if (this instanceof NovaMenuActivity)
			((NovaMenuActivity)this).setCameFromActivity(true);
		
		if (isShowLeaderboard) {
			sendAndShowLeaderboard();
	        isShowLeaderboard = false;
		}
	}
	
	@Override
	protected void onPause() {
		if (isShowAds && adView != null)
	      adView.pause();

		super.onPause();
		
		manager.onPause();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		if (isShowAds && adView != null)
	      adView.resume();

		manager.onResume();
	}
	
	@Override
	public void onDestroy() {
    	// Destroy the AdView.
		if (isShowAds && adView != null)
			adView.destroy();
		
		super.onDestroy();
	}
	
	public void setContext() {
		context = new NovaContext();
		context.setActivity(this);
		context.setResources(((NovaApplication)getApplication()).getNovaResources());
		setManagerAndGraphics();
		context.setGraphics(graphicsView);
		context.setManager(manager);
		isInitialized = true;
	}
	
	public abstract void setManagerAndGraphics();
	
	public boolean isInitialized() {
		return isInitialized;
	}
}
