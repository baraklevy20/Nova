package com.droplay.nova.objects;

import com.droplay.nova.NovaResources;
import com.droplay.nova.NovaSprite;
import com.droplay.nova.ProcessGameListener;

public class NovaMovingObject extends NovaObject implements ProcessGameListener {
	public float startX;
	protected float xVelocity, yVelocity;
	protected float yAcceleration;
	public static int SCREEN_WIDTH, KING_WIDTH;
	public static float VELOCITY_FACTOR;
	
	public NovaMovingObject(NovaSprite sprite, float x, float y) {
		super(sprite, x, y);
		startX = x;
	}
	
	public NovaMovingObject(NovaSprite sprite, float x, float y, float xVelocity) {
		super(sprite, x, y);
		startX = x;
		this.xVelocity = xVelocity;
	}
	
	@Override
	public void onGameProgress() {
		super.onGameProgress();
		yVelocity += yAcceleration * VELOCITY_FACTOR;
		x += xVelocity * VELOCITY_FACTOR;
		y += yVelocity * VELOCITY_FACTOR;
	}
	
	public float getRealXVelocity() {
		return xVelocity * VELOCITY_FACTOR;
	}
	
	public float getRealYVelocity() {
		return yVelocity * VELOCITY_FACTOR;
	}
	
	// Return true if the object is in the screen
	public boolean isInScreen(final NovaResources res) {
		return x + sprite.getFrame().getWidth() > 0 && x < SCREEN_WIDTH;
	}

	// Check if the object left the screen
	public boolean isOutOfScreen(final NovaResources res) {
		// If the object is moving left, check its right size (width).
		// If the object is moving right, check the screen width
		return (xVelocity < 0 && x + sprite.getFrame().getWidth() < 0) || (xVelocity > 0 && x >= SCREEN_WIDTH);
	}

	public float getxVelocity() {
		return xVelocity;
	}

	public void setxVelocity(float xVelocity) {
		this.xVelocity = xVelocity;
	}

	public float getyVelocity() {
		return yVelocity;
	}

	public void setyVelocity(float yVelocity) {
		this.yVelocity = yVelocity;
	}

	public float getyAcceleration() {
		return yAcceleration;
	}

	public void setyAcceleration(float yAcceleration) {
		this.yAcceleration = yAcceleration;
	}

	public float getStartX() {
		return startX;
	}
}
