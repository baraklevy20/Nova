package com.droplay.nova.objects;

import com.droplay.nova.AnimationFrames;
import com.droplay.nova.AnimationSprite;
import com.droplay.nova.NovaConstants;
import com.droplay.nova.NovaResources;
import com.droplay.nova.ProcessGameListener;

public class TimedAnimation extends NovaMovingObject implements NovaConstants, ProcessGameListener {
	private long oldTime, newTime;
	private int waitTime, minWaitTime, maxWaitTime;
	private State state;
	private King king;
	private int startX, startY;
	protected float extraYVelocity;
	private NovaResources resources;
	
	private enum State {
		NOT_VISIBLE,
		STARTED
	}
	
//	public TimedAnimation(AnimationFrames frames, NovaResources resources, King king, int x, int y, int minWaitTime, int maxWaitTime) {
//		super(new AnimationSprite(frames), x, y);
//		oldTime = System.currentTimeMillis();
//		state = State.NOT_VISIBLE;
//		this.resources = resources;
//		this.minWaitTime = minWaitTime;
//		this.maxWaitTime = maxWaitTime;
//		this.startX = x;
//		this.startY = y;
//		this.king = king;
//		this.x = king.x + startX * resources.KING[King.Crown.BLUE.ordinal()][King.Crown.GREEN.ordinal()].getWidth() / 96;
//		this.y = king.y + startY * resources.KING[King.Crown.BLUE.ordinal()][King.Crown.GREEN.ordinal()].getHeight() / 137;
//		this.waitTime = (int)(Math.random() * (maxWaitTime - minWaitTime + 1)) + minWaitTime;
//	}
	
	public TimedAnimation(AnimationFrames frames, NovaResources resources, King king, int x, int y, float extraYVelocity, int minWaitTime, int maxWaitTime) {
		super(new AnimationSprite(frames), x, y);
		oldTime = System.currentTimeMillis();
		state = State.NOT_VISIBLE;
		this.resources = resources;
		this.minWaitTime = minWaitTime;
		this.maxWaitTime = maxWaitTime;
		this.startX = x;
		this.startY = y;
		this.extraYVelocity = extraYVelocity;
		this.king = king;
		this.x = king.x + startX * resources.KING[King.Crown.BLUE.ordinal()][King.Crown.GREEN.ordinal()].getWidth() / 96;
		this.y = king.y + startY * resources.KING[King.Crown.BLUE.ordinal()][King.Crown.GREEN.ordinal()].getHeight() / 137;
		this.waitTime = (int)(Math.random() * (maxWaitTime - minWaitTime + 1)) + minWaitTime;
	}
	
	public boolean isVisible() {
		return state == State.STARTED;
	}
	
	@Override
	public void onGameProgress() {
		super.onGameProgress();
		newTime = System.currentTimeMillis();
		
		if (state == State.NOT_VISIBLE && newTime - oldTime > waitTime) {
			state = State.STARTED;
			((AnimationSprite)sprite).resetAnimation();
			yVelocity += extraYVelocity;
		}
		
		if (state == State.STARTED) {
			if (((AnimationSprite)sprite).isDone()) {
				this.x = king.x + startX * resources.KING[King.Crown.BLUE.ordinal()][King.Crown.GREEN.ordinal()].getWidth() / 96;
				this.y = king.y + startY * resources.KING[King.Crown.BLUE.ordinal()][King.Crown.GREEN.ordinal()].getHeight() / 137;
				yVelocity -= extraYVelocity;
				((AnimationSprite)sprite).resetAnimation();
				oldTime = System.currentTimeMillis();
				this.waitTime = (int)(Math.random() * (maxWaitTime - minWaitTime + 1)) + minWaitTime;
				state = State.NOT_VISIBLE;
			}
		}
	}
}
