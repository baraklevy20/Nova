package com.droplay.nova.objects;

import com.droplay.nova.AnimationFrames;
import com.droplay.nova.AnimationSprite;

public class Crown extends NovaObject {
	private int direction = 1;
	private long newTime;
	private ClickState clickState;
	
	public enum ClickState {
		NON_CLICKED,
		DOWN,
		UP
	}
	
	public Crown(AnimationFrames frames, float x, float y) {
		super(new AnimationSprite(frames), x, y);
		clickState = ClickState.NON_CLICKED;
	}
	
	@Override
	public void onGameProgress() {
		if (clickState != ClickState.NON_CLICKED) {
			newTime = System.currentTimeMillis();
			
			if (((AnimationSprite)((AnimationSprite)sprite)).getCurrentInterval() == 0)
				((AnimationSprite)((AnimationSprite)sprite)).setCurrentInterval(newTime);
			
			// If it's time to change a frame
			if (newTime - ((AnimationSprite)((AnimationSprite)sprite)).getCurrentInterval() >= ((AnimationSprite)((AnimationSprite)sprite)).getInterval()) {
				((AnimationSprite)((AnimationSprite)sprite)).setCurrentInterval(newTime);
				
				if (direction < 0 && ((AnimationSprite)sprite).getFrameIndex() > 0 || direction > 0 && ((AnimationSprite)sprite).getFrameIndex() < ((AnimationSprite)sprite).getNumberOfFrames() - 1)
					((AnimationSprite)sprite).setFrameIndex(((AnimationSprite)sprite).getFrameIndex() + direction);
			}
		}
	}

	public void setClickState(ClickState clickState) {
		this.clickState = clickState;
		((AnimationSprite)sprite).setCurrentInterval(0);
		
		switch (clickState) {
		case DOWN: ((AnimationSprite)sprite).setFrameIndex(0); direction = 1; break;
		case UP: ((AnimationSprite)sprite).setFrameIndex(((AnimationSprite)sprite).getNumberOfFrames() - 1); direction = -1; break;
		case NON_CLICKED: ((AnimationSprite)sprite).setFrameIndex(0); break;
		}
	}

	public ClickState getClickState() {
		return clickState;
	}
}