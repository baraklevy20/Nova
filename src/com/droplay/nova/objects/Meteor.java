package com.droplay.nova.objects;

import com.droplay.nova.AnimationSprite;
import com.droplay.nova.NovaConstants;
import com.droplay.nova.NovaResources;

public class Meteor extends NovaMovingObject implements NovaConstants {
	private long oldTime;
	private State state = State.NOT_VISIBLE;
	private NovaResources resources;
	
	public enum State {
		NOT_VISIBLE,
		STARTING,
		STARTED,
		ENDED
	}
	
	public Meteor(NovaResources resources, final int SCREEN_WIDTH, float x, float y, float xVelocity) {
		super(new AnimationSprite(), x, y, xVelocity);
		
		this.resources = resources;
		oldTime = System.currentTimeMillis();
	}
	
	public State getState() {
		return state;
	}
	
	public void setState(State state) {
		this.state = state;
	}
	
	@Override
	public void onGameProgress() {
		long dif = (System.currentTimeMillis() - oldTime);
		if (state == State.NOT_VISIBLE && dif / 1000 >= METEOR_TIME) {
			state = State.STARTING;
			((AnimationSprite)sprite).setFrames(resources.METEOR_STARTING);
		}
		
		if (state == State.STARTING && ((AnimationSprite)sprite).getFrameIndex() == resources.METEOR_STARTING.getNumberOfFrames() - 1) {
			state = State.STARTED;
			((AnimationSprite)sprite).setFrames(resources.METEOR_STARTED);
			((AnimationSprite)sprite).resetAnimation();
		}
		
		if (state == State.STARTED || state == State.STARTING)
			super.onGameProgress();
	}

	public void setOldTime(long oldTime) {
		this.oldTime = oldTime;
	}
}
