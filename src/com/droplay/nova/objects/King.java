package com.droplay.nova.objects;

import com.droplay.nova.AnimationSprite;
import com.droplay.nova.NovaConstants;
import com.droplay.nova.NovaContext;

public class King extends NovaMovingObject implements NovaConstants {
	private Crown crown = Crown.NORMAL;
	private Crown oldCrown = Crown.NORMAL;
	private NovaContext context;
	private Portal portal;
	float timeToCollision;
	boolean isDead;
	private NovaMovingObject oh;
	private TimedAnimation sweat, mouth;
	
	public enum Crown {
		NORMAL,
		BLUE,
		ORANGE,
		GREEN,
		PURPLE,
	}
	
	public Crown getOldCrown() {
		return oldCrown;
	}
	
	public Crown getCrown() {
		return crown;
	}
	
	// Change the crown of the king
	public void setCrown(Crown crown) {
		this.oldCrown = this.crown;
		this.crown = crown;
		int currentFrame = ((AnimationSprite)sprite).getFrameIndex();
		((AnimationSprite)sprite).setFrames(context.getResources().KING[oldCrown.ordinal()][crown.ordinal()]);
		
		if (oldCrown != Crown.NORMAL && crown == Crown.NORMAL) {
			if (currentFrame <= 2)
				((AnimationSprite)sprite).setFrameIndex(4);
			else if (currentFrame == 3)
				((AnimationSprite)sprite).setFrameIndex(2);
			else
				((AnimationSprite)sprite).setFrameIndex(0);
			
			((AnimationSprite)sprite).setCurrentInterval(0);
		}
		else
			((AnimationSprite)sprite).resetAnimation();
	}
	
	public TimedAnimation getSweat() {
		return sweat;
	}
	
	public TimedAnimation getMouth() {
		return mouth;
	}
	
	public King(NovaContext context, float x, float y, int topBound, int bottomBound, Portal portal) {
		super(new AnimationSprite(), x, y);
		this.portal = portal;
		this.context = context;
		timeToCollision = Math.abs((x + context.getResources().KING[King.Crown.BLUE.ordinal()][King.Crown.GREEN.ordinal()].getWidth() - portal.getX() - context.getResources().BLUE_PORTAL.getWidth()) / portal.getxVelocity());
		yVelocity = (2 * portal.getY() - 2 * this.y + context.getResources().BLUE_PORTAL.getHeight() - context.getResources().KING[King.Crown.BLUE.ordinal()][King.Crown.GREEN.ordinal()].getHeight()) / timeToCollision;
		yAcceleration = -yVelocity / timeToCollision;
		oldCrown = Crown.NORMAL;
		crown = Crown.NORMAL;
		sweat = new TimedAnimation(context.getResources().SWEAT, context.getResources(), this, 36, 78, 4, 500, 1500);
		mouth = new TimedAnimation(context.getResources().MOUTH, context.getResources(), this, 56, 92, 1000, 2000);
		sweat.yAcceleration = yAcceleration;
		sweat.yVelocity = yVelocity;
		if (sweat.isVisible())
			sweat.yVelocity += sweat.extraYVelocity;
		mouth.yAcceleration = yAcceleration;
		mouth.yVelocity = yVelocity;
		((AnimationSprite)sprite).setFrames(null);
	}
	
	public void notifyOh() {
		oh = new NovaMovingObject(new AnimationSprite(context.getResources().OH), x + 51 * context.getResources().KING[King.Crown.BLUE.ordinal()][King.Crown.GREEN.ordinal()].getWidth() / 96,
				y - 73 * context.getResources().KING[King.Crown.BLUE.ordinal()][King.Crown.GREEN.ordinal()].getHeight() / 137);
		oh.yVelocity = yVelocity;
		oh.yAcceleration = yAcceleration;
	}
	
	public void notifyPortalTouched() {
		yAcceleration = 0;
		yVelocity = 0;
		
		if (oh != null) {
			oh.yAcceleration = 0;
			oh.yVelocity = 0;
		}
		
		sweat.yAcceleration = yAcceleration;
		sweat.yVelocity = yVelocity;
		if (sweat.isVisible())
			sweat.yVelocity += sweat.extraYVelocity;
		mouth.yAcceleration = yAcceleration;
		mouth.yVelocity = yVelocity;
	}

	// When you pass a portal, change the acceleration and speed
	public void notifyPortalPassed(float portalX, float portalY, float portalVelocity) {
		if (!isDead) {
			// Calculate the y velocity and y acceleration
			
			// Time to collision = abs((king.x + king.width - portal.x - portal.width) / portal.velocity) + abs(portal.x + portal.xVelocity)
			timeToCollision = Math.abs((x + context.getResources().KING[King.Crown.BLUE.ordinal()][King.Crown.GREEN.ordinal()].getWidth() - portalX - context.getResources().BLUE_PORTAL.getWidth()) / portalVelocity) + Math.abs(portal.getX() / portal.getxVelocity());
			
			yVelocity = (2 * portalY - 2 * this.y + context.getResources().BLUE_PORTAL.getHeight() - context.getResources().KING[King.Crown.BLUE.ordinal()][King.Crown.GREEN.ordinal()].getHeight()) / timeToCollision;
			yAcceleration = -yVelocity / timeToCollision;
			
			if (oh != null) {
				oh.yVelocity = yVelocity;
				oh.yAcceleration = yAcceleration;
			}
			
			sweat.yAcceleration = yAcceleration;
			sweat.yVelocity = yVelocity;
			if (sweat.isVisible())
				sweat.yVelocity += sweat.extraYVelocity;
			mouth.yAcceleration = yAcceleration;
			mouth.yVelocity = yVelocity;
		}
	}
	
	public boolean isDead() {
		return isDead;
	}
	
	@Override
	public void onGameProgress() {
		super.onGameProgress();
		
		if (oh != null) {
			oh.onGameProgress();
			
			if (((AnimationSprite)oh.getSprite()).isReverse() && ((AnimationSprite)oh.getSprite()).isDone())
				oh = null;
		}
		
		sweat.onGameProgress();
		mouth.onGameProgress();
	}

	// When the king is dead, this will be called
	public void notifyDeath() {
		this.isDead = true;
		
		if (crown == Crown.NORMAL)
			((AnimationSprite)sprite).setFrames(context.getResources().NORMAL_DEATH);
		else
			((AnimationSprite)sprite).setFrames(context.getResources().COLORFUL_DEATH);
	}

	public NovaMovingObject getOh() {
		return oh;
	}

	public void removeOh() {
		// If this is the first game, you shouldn't remove oh.
		if (oh != null)
			((AnimationSprite)oh.getSprite()).setReverse(true);
	}
}