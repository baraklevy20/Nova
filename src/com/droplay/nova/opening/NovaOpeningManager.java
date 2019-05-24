package com.droplay.nova.opening;

import android.view.MotionEvent;

import com.droplay.nova.AnimationSprite;
import com.droplay.nova.NovaContext;
import com.droplay.nova.NovaManager;
import com.droplay.nova.objects.NovaObject;

public class NovaOpeningManager extends NovaManager {
	private NovaObject logo;
	
	public NovaOpeningManager(NovaContext context) {
		super(context);
	}

	@Override
	public void initialize() {
		super.initialize();
		
		logo = new NovaObject(new AnimationSprite(context.getResources().DROPLAY_LOGO),
				context.getGraphics().getWidth() / 2 - context.getResources().DROPLAY_LOGO.getWidth() / 2,
				context.getGraphics().getHeight() * 45 / 100 - context.getResources().DROPLAY_LOGO.getHeight() / 2);
	}
	
	public NovaObject getLogo() {
		return logo;
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void progressGame() {
		dynamicScreen.onGameProgress();
		staticScreen.onGameProgress();
		logo.onGameProgress();
	}

	@Override
	public void handleTouch(MotionEvent event) {
		// TODO Auto-generated method stub

	}
}
