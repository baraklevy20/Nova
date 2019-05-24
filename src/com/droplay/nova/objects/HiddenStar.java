package com.droplay.nova.objects;

import com.droplay.nova.AnimationSprite;
import com.droplay.nova.NovaConstants;
import com.droplay.nova.NovaResources;
import com.droplay.nova.ProcessGameListener;

public class HiddenStar extends NovaObject implements NovaConstants, ProcessGameListener {
	private long oldTime, newTime;
	private int waitTime;
	private State state;
	
	private enum State {
		NOT_VISIBLE,
		STARTED
	}
	
	public HiddenStar(NovaResources resources, int x, int y, int waitTime) {
		super(new AnimationSprite(resources.HIDDEN_STAR), x, y);
		oldTime = System.currentTimeMillis();
		this.waitTime = waitTime;
	}
	
	@Override
	public void onGameProgress() {
		newTime = System.currentTimeMillis();
		if (newTime - oldTime > waitTime) {
			state = State.STARTED;
		}
		
		if (state == State.STARTED) {
			((AnimationSprite)sprite).normalUpdate();
			
			if (((AnimationSprite)sprite).isDone()) {
				((AnimationSprite)sprite).resetAnimation();
				oldTime = newTime;
				waitTime = HIDDEN_STAR_WAIT;
				state = State.NOT_VISIBLE;
			}
		}
	}
}
