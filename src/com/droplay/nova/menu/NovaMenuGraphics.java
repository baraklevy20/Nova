package com.droplay.nova.menu;

import android.graphics.Canvas;

import com.droplay.nova.NovaContext;
import com.droplay.nova.NovaGraphics;
import com.droplay.nova.menu.NovaMenuManager.ButtonState;

public class NovaMenuGraphics extends NovaGraphics {
	private int logoX, logoY;
	private int newGameX, newGameY;
	private int creditsX;
	private int scoresX;
	private int rateX;
	private int buttonsY;
	private ButtonState buttonState;
	
	public NovaMenuGraphics(NovaContext context) {
		super(context);
	}

	@Override
	public void doDraw(Canvas canvas) {
		super.doDraw(canvas);
		
		canvas.drawBitmap(resources.NOVA_LOGO, logoX, logoY, null);
		
		// If new game is pressed
		if (buttonState == ButtonState.NEW_GAME)
			canvas.drawBitmap(resources.NEW_GAME_PRESSED, newGameX, newGameY, null);
		else
			canvas.drawBitmap(resources.NEW_GAME, newGameX, newGameY, null);
		
		if (buttonState == ButtonState.CREDITS)
			canvas.drawBitmap(resources.CREDITS_PRESSED, creditsX, buttonsY, null);
		else
			canvas.drawBitmap(resources.CREDITS, creditsX, buttonsY, null);
		
		if (buttonState == ButtonState.SCORES)
			canvas.drawBitmap(resources.SCORES_PRESSED, scoresX, buttonsY, null);
		else
			canvas.drawBitmap(resources.SCORES, scoresX, buttonsY, null);
		
		if (buttonState == ButtonState.RATE)
			canvas.drawBitmap(resources.RATE_US_PRESSED, rateX, buttonsY, null);
		else
			canvas.drawBitmap(resources.RATE_US, rateX, buttonsY, null);
	}
	
	public void setButtonState(NovaMenuManager.ButtonState buttonState) {
		this.buttonState = buttonState;
	}
	
	@Override
	public void initialize() {
		super.initialize();
		
		logoX = width / 2 - resources.NOVA_LOGO.getWidth() / 2;
		logoY = height * 172 / 800;
		newGameX = width / 2 - resources.NEW_GAME.getWidth() / 2;
		newGameY = height * 318 / 800;
		creditsX = width * 98 / 480;
		scoresX = width * 198 / 480;
		rateX = width * 298 / 480;
		buttonsY = height * 447 / 800;
	}
}