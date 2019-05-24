package com.droplay.nova.game;

import android.content.Intent;
import android.os.Bundle;

import com.droplay.nova.NovaActivity;
import com.droplay.nova.NovaApplication;
import com.droplay.nova.R;
import com.droplay.nova.game.NovaGameManager.ButtonState;

public class NovaGameActivity extends NovaActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		isShowAds = true;
		super.onCreate(savedInstanceState);
		
		// Wait until all of the resources are loaded
		while (!context.getResources().isOpeningDone())
			try {
				Thread.sleep(16);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}
	@Override
	public void setManagerAndGraphics() {
		manager = new NovaGameManager(context);
		graphicsView = new NovaGameGraphics(context);
	}
	
	@Override
	protected void onActivityResult(int request, int response, Intent data) {
		if (request == REQUEST_CODE_SHARE) {
			((NovaGameManager)manager).setButtonState(ButtonState.NON);
		}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		((NovaApplication)context.getActivity().getApplication()).releaseMediaPlayers();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		((NovaApplication)context.getActivity().getApplication()).releaseMediaPlayers();
		((NovaApplication)context.getActivity().getApplication()).playMusic(R.raw.bg_music);
	}
}
