package com.droplay.nova.menu;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.view.MotionEvent;

import com.droplay.nova.NovaApplication;
import com.droplay.nova.NovaConstants;
import com.droplay.nova.NovaContext;
import com.droplay.nova.NovaManager;
import com.droplay.nova.NovaResources;
import com.droplay.nova.R;
import com.droplay.nova.credits.NovaCreditsActivity;
import com.droplay.nova.game.NovaGameActivity;

public class NovaMenuManager extends NovaManager implements NovaConstants {
	private ButtonState buttonState;
	
	public enum ButtonState {
		NON,
		NEW_GAME,
		CREDITS,
		SCORES,
		RATE
	}
	
	public NovaMenuManager(NovaContext context) {
		super(context);
	}
	
	public ButtonState getButtonState() {
		return buttonState;
	}

	@Override
	public void initialize() {
		super.initialize();
		buttonState = ButtonState.NON;
		((NovaMenuGraphics)context.getGraphics()).setButtonState(buttonState);
	}
	
	@Override
	public void onPause() {
		
	}

	@Override
	public void onResume() {
		
	}

	@Override
	public void progressGame() {
		dynamicScreen.onGameProgress();
		staticScreen.onGameProgress();
	}

	@Override
	public void handleTouch(MotionEvent event) {
		super.handleTouch(event);
		int width = context.getGraphics().getWidth();
		int height = context.getGraphics().getHeight();
		NovaResources resources = context.getResources();
		float x = event.getX();
		float y = event.getY();
		
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (buttonState == ButtonState.NON) {
				// New game
				if (x >= width / 2 - resources.NEW_GAME.getWidth() / 2 && x < width / 2 + resources.NEW_GAME.getWidth() / 2 &&
						y >= height * 318 / 800 && y < height * 318 / 800 + resources.NEW_GAME.getHeight()) {
					buttonState = ButtonState.NEW_GAME;
					((NovaMenuGraphics)context.getGraphics()).setButtonState(buttonState);
					if (((NovaApplication)context.getActivity().getApplication()).isSoundOn())
						((NovaApplication)context.getActivity().getApplication()).playSound(R.raw.button);
				}
				else if (y >= height * 447 / 800 && y < height * 447 / 800 + resources.RATE_US.getHeight()) {
					// Credits
					if (x >= width * 98 / 480 && x < width * 98 / 480 + resources.SCORES.getWidth()) {
						buttonState = ButtonState.CREDITS;
						((NovaMenuGraphics)context.getGraphics()).setButtonState(buttonState);
						if (((NovaApplication)context.getActivity().getApplication()).isSoundOn())
							((NovaApplication)context.getActivity().getApplication()).playSound(R.raw.button);
					}
					// Scores
					else if (x >= width * 198 / 480 && x < width * 198 / 480 + resources.SCORES.getWidth()) {
						buttonState = ButtonState.SCORES;
						((NovaMenuGraphics)context.getGraphics()).setButtonState(buttonState);
						if (((NovaApplication)context.getActivity().getApplication()).isSoundOn())
							((NovaApplication)context.getActivity().getApplication()).playSound(R.raw.button);
					}
					// Rate
					else if (x >= width * 298 / 480 && x < width * 298 / 480 + resources.SHARE.getWidth()) {
						buttonState = ButtonState.RATE;
						((NovaMenuGraphics)context.getGraphics()).setButtonState(buttonState);
						if (((NovaApplication)context.getActivity().getApplication()).isSoundOn())
							((NovaApplication)context.getActivity().getApplication()).playSound(R.raw.button);
					}
				}
			}
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			switch (buttonState) {
			case NEW_GAME:
				context.getActivity().startActivityForResult(new Intent(context.getActivity(), NovaGameActivity.class), REQUEST_CODE_NEW_GAME);
				buttonState = ButtonState.NON;
				((NovaMenuGraphics)context.getGraphics()).setButtonState(buttonState);
				break;
			case CREDITS:
				((NovaMenuActivity)context.getActivity()).setGoingToActivity(true);
				context.getActivity().startActivityForResult(new Intent(context.getActivity(), NovaCreditsActivity.class), REQUEST_CODE_CREDITS);
				((NovaMenuGraphics)context.getGraphics()).setButtonState(buttonState);
				break;
			case RATE:
				Uri uri = Uri.parse("market://details?id=" + context.getActivity().getPackageName());
				Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
				try {
					context.getActivity().startActivityForResult(goToMarket, REQUEST_CODE_RATE);
				} catch (ActivityNotFoundException e) {
					context.getActivity().startActivityForResult(new Intent(Intent.ACTION_VIEW,
							Uri.parse("http://play.google.com/store/apps/details?id=" + context.getActivity().getPackageName())),
							REQUEST_CODE_RATE);
				}
				((NovaMenuGraphics)context.getGraphics()).setButtonState(buttonState);
				break;
			case SCORES:
				((NovaMenuActivity)context.getActivity()).setGoingToActivity(true);
				context.getActivity().showLeaderboard();
				break;
			case NON:
				break;
			}
		}
	}

	public void setButtonState(ButtonState buttonState) {
		this.buttonState = buttonState;
	}
}
