package com.droplay.nova.menu;

import android.content.Intent;
import android.os.Bundle;

import com.droplay.nova.NovaActivity;
import com.droplay.nova.NovaApplication;
import com.droplay.nova.NovaConstants;
import com.droplay.nova.R;
import com.droplay.nova.menu.NovaMenuManager.ButtonState;

public class NovaMenuActivity extends NovaActivity implements NovaConstants {
	private boolean isCameFromActivity, isGoingToActivity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		isShowAds = false;
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
		this.graphicsView = new NovaMenuGraphics(context);
		this.manager = new NovaMenuManager(context);
	}
	
	@Override
	protected void onActivityResult(int request, int response, Intent data) {
		super.onActivityResult(request, response, data);
		if (request == REQUEST_CODE_CREDITS) {
			isCameFromActivity = true;
			((NovaMenuManager)context.getManager()).setButtonState(ButtonState.NON);
			((NovaMenuGraphics)context.getGraphics()).setButtonState(ButtonState.NON);
		}
		
		if (request == REQUEST_CODE_RATE) {
			((NovaMenuManager)context.getManager()).setButtonState(ButtonState.NON);
			((NovaMenuGraphics)context.getGraphics()).setButtonState(ButtonState.NON);
		}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		isCameFromActivity = false;
		
		if (!isGoingToActivity)
			((NovaApplication)context.getActivity().getApplication()).releaseMediaPlayers();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		isGoingToActivity = false;
		
		if (!isCameFromActivity)
			((NovaApplication)context.getActivity().getApplication()).playMusic(R.raw.menu_music);
	}
	
	@Override
	public void onDestroy() {
		((NovaApplication)getApplication()).releaseMediaPlayers();
		super.onDestroy();
	}
	
	public void setGoingToActivity(boolean isGoingToActivity) {
		this.isGoingToActivity = isGoingToActivity;
	}

	public void setCameFromActivity(boolean isCameFromActivity) {
		this.isCameFromActivity = isCameFromActivity;
	}
}
