package com.droplay.nova;

import com.droplay.nova.opening.NovaOpeningManager;

import android.view.MotionEvent;

// Responsible for the way the screen acts
public abstract class NovaManager {
	protected GameLoop gameLoop;
	protected NovaContext context;
	protected DynamicScreen dynamicScreen;
	protected StaticScreen staticScreen;
	
	public abstract void onPause();
	public abstract void onResume();
	public abstract void progressGame();
	
	public NovaManager(NovaContext context) {
		this.context = context;
		this.gameLoop = new GameLoop(context);
	}
	
	public void initialize() {
		dynamicScreen = new DynamicScreen(context);
		staticScreen = new StaticScreen(context);
	}
	
	public GameLoop getGameLoop() {
		return gameLoop;
	}
	
	public DynamicScreen getDynamicScreen() {
		return dynamicScreen;
	}
	
	public StaticScreen getStaticScreen() {
		return staticScreen;
	}
	
	// Handle touch events
	public void handleTouch(MotionEvent event) {
		// All screens but the opening screen have mutes buttons
		if (!(this instanceof NovaOpeningManager)) {
			float x = event.getX();
			float y = event.getY();
			int width = context.getGraphics().getWidth();
			int height = context.getGraphics().getHeight();
			
			if (event.getAction() == MotionEvent.ACTION_DOWN && y < 40 * height / 800 + context.getResources().SOUND_ON.getHeight()) {
				if (x < 31 * width / 480 + context.getResources().SOUND_ON.getWidth()) {
					((NovaApplication)context.getActivity().getApplication()).setSoundOn(
							((NovaApplication)context.getActivity().getApplication()).isSoundOn() ? false : true);
					context.getActivity().writeParameters();
				}
				else if (x < 70 * width / 480 + context.getResources().MUSIC_ON.getWidth()) {
					((NovaApplication)context.getActivity().getApplication()).setMusicOn(
							((NovaApplication)context.getActivity().getApplication()).isMusicOn() ? false : true);
					context.getActivity().writeParameters();
				}
			}
		}
	}
}
